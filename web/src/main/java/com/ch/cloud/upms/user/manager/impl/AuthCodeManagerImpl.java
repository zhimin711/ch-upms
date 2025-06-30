package com.ch.cloud.upms.user.manager.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ch.cloud.upms.base.model.AuthCode;
import com.ch.cloud.upms.base.model.AuthCodeUsageRecord;
import com.ch.cloud.upms.dto.AuthCodeGenerateDTO;
import com.ch.cloud.upms.dto.AuthCodeRevokeDTO;
import com.ch.cloud.upms.dto.AuthCodeUsageRecordVO;
import com.ch.cloud.upms.dto.AuthCodeVO;
import com.ch.cloud.upms.dto.AuthCodeValidateDTO;
import com.ch.cloud.upms.user.manager.AuthCodeManager;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * <p>
 * desc: AuthCodeManagerImpl
 * </p>
 *
 * @author zhimin
 * @since 2025/6/30 15:15
 */
@Service
public class AuthCodeManagerImpl implements AuthCodeManager {
    
    @Autowired
    private com.ch.cloud.upms.service.IAuthCodeService authCodeService;
    
    @Autowired
    private com.ch.cloud.upms.service.IAuthCodeUsageRecordService authCodeUsageRecordService;
    
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean validate(AuthCodeValidateDTO dto) {
        AuthCode authCode = authCodeService.lambdaQuery().eq(AuthCode::getCode, dto.getCode()).one();
        if (authCode == null || authCode.getStatus() != 1) {
            return false;
        }
        if (authCode.getExpireTime() != null && authCode.getExpireTime().before(new Date())) {
            return false;
        }
        if (authCode.getMaxUses() != null && authCode.getUsedCount() != null
                && authCode.getUsedCount() >= authCode.getMaxUses()) {
            return false;
        }
        // 校验授权内容
        // 这里只做简单包含校验，实际可根据 content 结构做更细粒度校验
        if (authCode.getContent() != null && !authCode.getContent().contains(dto.getResourceId())) {
            return false;
        }
        // 记录使用
        authCode.setUsedCount(authCode.getUsedCount() == null ? 1 : authCode.getUsedCount() + 1);
        authCodeService.updateById(authCode);
        AuthCodeUsageRecord record = new AuthCodeUsageRecord();
        record.setCode(dto.getCode());
        record.setUserId(null); // 可根据实际登录用户设置
        record.setResourceType(dto.getResourceType());
        record.setResourceId(dto.getResourceId());
        record.setAction(dto.getAction());
        record.setUseTime(new Date());
        authCodeUsageRecordService.save(record);
        return true;
    }
    
    @Override
    public boolean revoke(AuthCodeRevokeDTO dto) {
        AuthCode authCode = authCodeService.lambdaQuery().eq(AuthCode::getCode, dto.getCode()).one();
        if (authCode == null) {
            return false;
        }
        authCode.setStatus(2); // 2=撤销
        return authCodeService.updateById(authCode);
    }
    
    @Override
    public List<AuthCodeUsageRecordVO> usageRecords(String code) {
        List<AuthCodeUsageRecord> list = authCodeUsageRecordService.lambdaQuery().eq(AuthCodeUsageRecord::getCode, code)
                .list();
        return list.stream().map(r -> {
            AuthCodeUsageRecordVO vo = new AuthCodeUsageRecordVO();
            BeanUtils.copyProperties(r, vo);
            return vo;
        }).collect(Collectors.toList());
    }
    
    @Override
    public AuthCodeVO generate(AuthCodeGenerateDTO dto, Long creatorId) {
        AuthCode authCode = new AuthCode();
        // 生成唯一授权码
        authCode.setCode(UUID.randomUUID().toString().replace("-", ""));
        authCode.setCreatorId(creatorId);
        authCode.setContent(dto.getContent());
        authCode.setExpireTime(dto.getExpireTime());
        authCode.setMaxUses(dto.getMaxUses() == null ? 1 : dto.getMaxUses());
        authCode.setUsedCount(0);
        authCode.setStatus(1);
        authCode.setCreateTime(new java.util.Date());
        authCode.setUpdateTime(new java.util.Date());
        authCodeService.save(authCode);
        AuthCodeVO vo = new AuthCodeVO();
        vo.setCode(authCode.getCode());
        vo.setContent(authCode.getContent());
        vo.setExpireTime(authCode.getExpireTime());
        vo.setMaxUses(authCode.getMaxUses());
        vo.setUsedCount(authCode.getUsedCount());
        vo.setStatus(authCode.getStatus());
        vo.setCreateTime(authCode.getCreateTime());
        return vo;
    }
}
