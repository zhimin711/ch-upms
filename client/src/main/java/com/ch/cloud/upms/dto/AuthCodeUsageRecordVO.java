package com.ch.cloud.upms.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

@Data
@Schema(description = "授权码使用记录VO")
public class AuthCodeUsageRecordVO {
    
    @Schema(description = "使用时间")
    private Date useTime;
    
    @Schema(description = "使用人ID")
    private Long userId;
    
    @Schema(description = "资源类型")
    private String resourceType;
    
    @Schema(description = "资源ID")
    private String resourceId;
    
    @Schema(description = "操作类型")
    private String action;
    
    @Schema(description = "备注")
    private String remark;
} 