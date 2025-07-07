package com.ch.cloud.upms.user.controller.client;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.ch.cloud.upms.base.model.AuthCode;
import com.ch.cloud.upms.client.UpmsAuthCodeClient;
import com.ch.cloud.upms.dto.AuthCodeGenerateDTO;
import com.ch.cloud.upms.dto.AuthCodePermissionDTO;
import com.ch.cloud.upms.dto.AuthCodeVO;
import com.ch.cloud.upms.dto.AuthCodeValidateDTO;
import com.ch.cloud.upms.dto.AuthCodeRevokeDTO;
import com.ch.cloud.upms.dto.AuthCodeUsageRecordVO;
import com.ch.cloud.upms.dto.PermissionDto;
import com.ch.cloud.upms.service.IAuthCodeService;
import com.ch.cloud.upms.service.IPermissionService;
import com.ch.cloud.upms.user.manager.AuthCodeManager;
import com.ch.cloud.upms.user.model.Permission;
import com.ch.e.Assert;
import com.ch.e.PubError;
import com.ch.result.Result;
import com.ch.result.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fc/auth")
public class AuthCodeClientController implements UpmsAuthCodeClient {
    
    @Autowired
    private AuthCodeManager authCodeManager;
    
    @Autowired
    private IAuthCodeService authCodeService;
    
    @Autowired
    private IPermissionService permissionService;
    
    // 示例：生成授权码接口
    @PostMapping("/generate")
    public Result<AuthCodeVO> generate(@RequestBody AuthCodeGenerateDTO dto) {
        return ResultUtils.wrapFail(()-> authCodeManager.generate(dto));
    }
    
    // 示例：查询授权码详情
    @GetMapping("/{code}")
    public Result<AuthCodePermissionDTO> getPermission(@PathVariable String code) {
        
        return ResultUtils.wrap(()-> {
            AuthCode one = authCodeService.lambdaQuery().eq(AuthCode::getCode, code).one();
            Assert.notNull(one, PubError.NOT_EXISTS, "授权码");
            JSONObject content = JSON.parseObject(one.getContent());
            List<String> permissionCodes = content.getList("permissions", String.class);
            Assert.notEmpty(permissionCodes, PubError.NOT_AUTH, "授权码未配置权限",code);
            List<Permission> permissions = permissionService.lambdaQuery().in(Permission::getCode, permissionCodes).list();
            List<PermissionDto> permissionList = BeanUtil.copyToList(permissions, PermissionDto.class);
            AuthCodePermissionDTO dto = BeanUtil.copyProperties(one, AuthCodePermissionDTO.class);
            dto.setPermissions(permissionList);
            return dto;
        });
    }
    
    @PostMapping("/validate")
    public boolean validate(@RequestBody AuthCodeValidateDTO dto) {
        return authCodeManager.validate(dto);
    }
    
    @PostMapping("/revoke")
    public boolean revoke(@RequestBody AuthCodeRevokeDTO dto) {
        return authCodeManager.revoke(dto);
    }
    
    @GetMapping("/usage-records/{code}")
    public List<AuthCodeUsageRecordVO> usageRecords(@PathVariable String code) {
        return authCodeManager.usageRecords(code);
    }
    
    // 其他接口可根据需求扩展
} 