package com.ch.cloud.upms.base.controller;

import com.ch.cloud.upms.base.model.AuthCode;
import com.ch.cloud.upms.service.IAuthCodeService;
import com.ch.cloud.upms.dto.AuthCodeGenerateDTO;
import com.ch.cloud.upms.dto.AuthCodeVO;
import com.ch.cloud.upms.dto.AuthCodeValidateDTO;
import com.ch.cloud.upms.dto.AuthCodeRevokeDTO;
import com.ch.cloud.upms.dto.AuthCodeUsageRecordVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/base/auth-code")
public class AuthCodeController {

    @Autowired
    private IAuthCodeService authCodeService;

    // 示例：生成授权码接口
    @PostMapping("/generate")
    public AuthCodeVO generate(@RequestBody AuthCodeGenerateDTO dto) {
        // 实际项目中 creatorId 应从登录用户获取
        Long creatorId = 1L;
        return authCodeService.generate(dto, creatorId);
    }

    // 示例：查询授权码详情
    @GetMapping("/{code}")
    public AuthCode getByCode(@PathVariable String code) {
        return authCodeService.lambdaQuery().eq(AuthCode::getCode, code).one();
    }

    @PostMapping("/validate")
    public boolean validate(@RequestBody AuthCodeValidateDTO dto) {
        return authCodeService.validate(dto);
    }

    @PostMapping("/revoke")
    public boolean revoke(@RequestBody AuthCodeRevokeDTO dto) {
        return authCodeService.revoke(dto);
    }

    @GetMapping("/usage-records/{code}")
    public List<AuthCodeUsageRecordVO> usageRecords(@PathVariable String code) {
        return authCodeService.usageRecords(code);
    }

    // 其他接口可根据需求扩展
} 