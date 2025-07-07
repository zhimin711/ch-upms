package com.ch.cloud.upms.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

@Data
@Schema(description = "授权码VO")
public class AuthCodeVO {
    
    @Schema(description = "授权码")
    private String code;
    
    @Schema(description = "授权内容（JSON字符串）")
    private String content;
    
    @Schema(description = "过期时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date expireTime;
    
    @Schema(description = "最大使用次数")
    private Integer maxUses;
    
    @Schema(description = "已使用次数")
    private Integer usedCount;
    
    @Schema(description = "状态 1有效 0失效 2撤销")
    private Integer status;
    
    @Schema(description = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
} 