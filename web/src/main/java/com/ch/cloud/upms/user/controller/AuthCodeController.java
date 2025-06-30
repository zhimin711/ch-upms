package com.ch.cloud.upms.user.controller;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.ch.cloud.upms.base.model.AuthCode;
import com.ch.cloud.upms.dto.AuthCodeGenerateDTO;
import com.ch.cloud.upms.dto.AuthCodeVO;
import com.ch.cloud.upms.dto.AuthCodeValidateDTO;
import com.ch.cloud.upms.dto.AuthCodeRevokeDTO;
import com.ch.cloud.upms.dto.AuthCodeUsageRecordVO;
import com.ch.cloud.upms.service.IAuthCodeService;
import com.ch.cloud.upms.service.IPermissionService;
import com.ch.cloud.upms.user.manager.AuthCodeManager;
import com.ch.cloud.upms.user.model.Permission;
import com.ch.e.Assert;
import com.ch.e.PubError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/base/auth-code")
public class AuthCodeController {
    
    @Autowired
    private AuthCodeManager authCodeManager;
    
    @Autowired
    private IAuthCodeService authCodeService;
    
    @Autowired
    private IPermissionService permissionService;
    
    // 示例：生成授权码接口
    @PostMapping("/generate")
    public AuthCodeVO generate(@RequestBody AuthCodeGenerateDTO dto) {
        // 实际项目中 creatorId 应从登录用户获取
        Long creatorId = 1L;
        return authCodeManager.generate(dto, creatorId);
    }
    
    // 示例：查询授权码详情
    @GetMapping("/{code}")
    public AuthCodeVO getByCode(@PathVariable String code) {
        AuthCode one = authCodeService.lambdaQuery().eq(AuthCode::getCode, code).one();
        Assert.notNull(one, PubError.NOT_EXISTS, "授权码");
        JSONObject content = JSON.parseObject(one.getContent());
        List<String> permissionCodes = content.getList("permissions", String.class);
        List<Permission> permissions = permissionService.lambdaQuery().in(Permission::getCode, permissionCodes).list();
        
        return null;
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