package com.ch.cloud.upms.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

@Data
@Schema(description = "授权码生成请求DTO")
public class AuthCodeGenerateDTO {
    
    
    /**
     * 授权人
     */
    private String authUser;
    
    @Schema(description = "授权内容（JSON字符串或对象）", example = "{\"resourceType\":\"article\",\"resourceIds\":[\"123\"],\"actions\":[\"read\"]}")
    private String content;
    
    @Schema(description = "过期时间", example = "2024-12-31 23:59:59")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date expireTime;
    
    @Schema(description = "最大使用次数", example = "10")
    private Integer maxUses;
} 