package com.ch.cloud.upms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ch.cloud.upms.base.model.AuthCode;
import com.ch.cloud.upms.dto.AuthCodeGenerateDTO;
import com.ch.cloud.upms.dto.AuthCodeVO;
import com.ch.cloud.upms.dto.AuthCodeValidateDTO;
import com.ch.cloud.upms.dto.AuthCodeRevokeDTO;
import com.ch.cloud.upms.dto.AuthCodeUsageRecordVO;
import java.util.List;

public interface IAuthCodeService extends IService<AuthCode> {
    // 可扩展自定义业务方法
    boolean validate(AuthCodeValidateDTO dto);
    boolean revoke(AuthCodeRevokeDTO dto);
    List<AuthCodeUsageRecordVO> usageRecords(String code);
    AuthCodeVO generate(AuthCodeGenerateDTO dto, Long creatorId);
} 