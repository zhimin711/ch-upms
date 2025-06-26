package com.ch.cloud.upms.dto;

import lombok.Data;
import java.util.Date;

@Data
public class AuthCodeUsageRecordVO {
    private Date useTime;
    private Long userId;
    private String resourceType;
    private String resourceId;
    private String action;
    private String remark;
} 