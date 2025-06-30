package com.ch.cloud.upms.user.manager;

import com.ch.cloud.upms.dto.AuthCodeGenerateDTO;
import com.ch.cloud.upms.dto.AuthCodeRevokeDTO;
import com.ch.cloud.upms.dto.AuthCodeUsageRecordVO;
import com.ch.cloud.upms.dto.AuthCodeVO;
import com.ch.cloud.upms.dto.AuthCodeValidateDTO;

import java.util.List;

/**
 * <p>
 * desc: AuthCodeManager
 * </p>
 *
 * @author zhimin
 * @since 2025/6/30 15:15
 */
public interface AuthCodeManager {
    
    // 可扩展自定义业务方法
    boolean validate(AuthCodeValidateDTO dto);
    
    boolean revoke(AuthCodeRevokeDTO dto);
    
    List<AuthCodeUsageRecordVO> usageRecords(String code);
    
    AuthCodeVO generate(AuthCodeGenerateDTO dto);
}
