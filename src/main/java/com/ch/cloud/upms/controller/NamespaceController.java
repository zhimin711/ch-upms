package com.ch.cloud.upms.controller;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ch.cloud.upms.model.Namespace;
import com.ch.cloud.upms.pojo.NacosNamespace;
import com.ch.cloud.upms.service.INamespaceService;
import com.ch.cloud.upms.utils.RequestUtils;
import com.ch.e.ExceptionUtils;
import com.ch.e.PubError;
import com.ch.pojo.VueRecord;
import com.ch.result.InvokerPage;
import com.ch.result.PageResult;
import com.ch.result.Result;
import com.ch.result.ResultUtils;
import com.ch.toolkit.UUIDGenerator;
import com.ch.utils.CommonUtils;
import com.ch.utils.DateUtils;
import com.ch.utils.VueRecordUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * <p>
 * 关系-命名空间表 前端控制器
 * </p>
 *
 * @author zhimin.ma
 * @since 2021-09-28
 */
@RestController
@RequestMapping("/namespace")
public class NamespaceController {

    @Autowired
    private INamespaceService namespaceService;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${nacos.url:}")
    private String nacosUrl;

    public static final String NAMESPACE_ADDR = "/nacos/v1/console/namespaces";

    @ApiOperation(value = "分页查询", notes = "分页查询命名空间")
    @GetMapping(value = {"{num:[0-9]+}/{size:[0-9]+}"})
    public PageResult<Namespace> page(Namespace record,
                                      @PathVariable(value = "num") int pageNum,
                                      @PathVariable(value = "size") int pageSize) {
        return ResultUtils.wrapPage(() -> {
            Page<Namespace> page = namespaceService.page(record, pageNum, pageSize);
            return InvokerPage.build(page.getTotal(), page.getRecords());
        });
    }

    @ApiOperation(value = "添加", notes = "添加命名空间")
    @PostMapping
    public Result<Boolean> add(@RequestBody Namespace record) {
        return ResultUtils.wrapFail(() -> {
            checkSaveOrUpdate(record);
            boolean syncOk = syncNacosNamespace(record, true);
            if (!syncOk) {
                ExceptionUtils._throw(PubError.CONNECT, "create nacos namespace failed!");
            }
            record.setCreateBy(RequestUtils.getHeaderUser());
            record.setCreateAt(DateUtils.current());
            return namespaceService.save(record);
        });
    }

    private boolean syncNacosNamespace(Namespace record, boolean isNew) {
        if (record.getSyncNacos() == null || !record.getSyncNacos() || CommonUtils.isEmpty(nacosUrl)) {
            return true;
        }
        MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
        param.add("namespaceDesc", record.getDescription());
        if (isNew) {
            record.setUid(UUIDGenerator.generate());
            param.add("customNamespaceId", record.getUid());
            param.add("namespaceName", record.getName());
        }else{
            param.add("namespace", record.getUid());
            param.add("namespaceShowName", record.getName());
        }
        Boolean sync;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(param, headers);
        if (isNew) {
            sync = restTemplate.postForObject(nacosUrl + NAMESPACE_ADDR, httpEntity, Boolean.class);
        } else {
//            restTemplate.put(nacosUrl + NAMESPACE_ADDR, param);
            ResponseEntity<Boolean> resp = restTemplate.exchange(nacosUrl + NAMESPACE_ADDR, HttpMethod.PUT, httpEntity, Boolean.class);
            if (resp.getStatusCode() == HttpStatus.OK) {
                sync = resp.getBody();
            } else {
                return false;
            }
        }
        return sync != null && sync;
    }

    private void checkSaveOrUpdate(Namespace record) {
        if (record.getId() != null) {
            Namespace orig = namespaceService.getById(record.getId());
            record.setUid(orig.getUid());
        }
    }

    @ApiOperation(value = "修改", notes = "修改命名空间")
    @PutMapping({"{id:[0-9]+}"})
    public Result<Boolean> edit(@RequestBody Namespace record) {
        return ResultUtils.wrapFail(() -> {
            checkSaveOrUpdate(record);
            boolean syncOk = syncNacosNamespace(record, false);
            if (!syncOk) {
                ExceptionUtils._throw(PubError.CONNECT, "update nacos namespace failed!");
            }
            record.setUpdateBy(RequestUtils.getHeaderUser());
            record.setUpdateAt(DateUtils.current());
            return namespaceService.updateById(record);
        });
    }

    @GetMapping({"available"})
    public Result<VueRecord> findAvailable() {
        return ResultUtils.wrapList(() -> {
            List<Namespace> list = namespaceService.list();
            return VueRecordUtils.covertIdTree(list);
        });
    }

    @GetMapping({"{id:[0-9]+}"})
    public Result<Namespace> find(@PathVariable Long id) {
        return ResultUtils.wrapFail(() -> namespaceService.getById(id));
    }


    @ApiOperation(value = "删除", notes = "删除命名空间")
    @DeleteMapping({"{id:[0-9]+}"})
    public Result<Boolean> delete(@PathVariable Long id) {
        return ResultUtils.wrapFail(() -> {
            Namespace orig = namespaceService.getById(id);
            if (orig.getSyncNacos()) {
                //todo delete nacos namespace
            }
            return namespaceService.removeById(id);
        });
    }

    @ApiOperation(value = "同步", notes = "同步-NACOS命名空间")
    @GetMapping({"/syncNacos"})
    public Result<Boolean> syncNacos() {
        return ResultUtils.wrapFail(() -> {
            ExceptionUtils.assertEmpty(nacosUrl, PubError.CONFIG, "nacos address");
            JSONObject resp = new RestTemplate().getForObject(nacosUrl + NAMESPACE_ADDR, JSONObject.class);
            if (resp != null && resp.containsKey("data")) {
                JSONArray arr = resp.getJSONArray("data");
                List<NacosNamespace> list = arr.toJavaList(NacosNamespace.class);
                saveNacosNamespaces(list);
            }
            return true;
        });
    }

    private void saveNacosNamespaces(List<NacosNamespace> list) {
        if (list.isEmpty()) return;
        String user = RequestUtils.getHeaderUser();
        list.forEach(e -> {
            Namespace record = new Namespace();
            record.setUid(e.getNamespace());
            record.setName(e.getNamespaceShowName());
            Namespace orig = namespaceService.getOne(Wrappers.lambdaQuery(record).eq(Namespace::getUid, record.getUid()));
            if (orig != null) {
                orig.setName(e.getNamespaceShowName());
                orig.setSyncNacos(true);
                orig.setUpdateBy(user);
                namespaceService.updateById(orig);
            } else {
                record.setSyncNacos(true);
                record.setCreateBy(user);
                namespaceService.save(record);
            }
        });
    }
}

