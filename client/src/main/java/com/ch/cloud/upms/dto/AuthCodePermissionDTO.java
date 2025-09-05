package com.ch.cloud.upms.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Schema(description = "授权码权限详情DTO")
public class AuthCodePermissionDTO {
    
    @Schema(description = "授权码")
    private String code;
    
    @Schema(description = "过期时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date expireTime;
    
    @Schema(description = "最大使用次数")
    private Integer maxUses;
    
    @Schema(description = "已使用次数")
    private Integer usedCount;
    
    @Schema(description = "状态 1有效 0失效 2撤销")
    private Integer status;
    
    @Schema(description = "权限列表")
    private List<PermissionDto> permissions;
} 