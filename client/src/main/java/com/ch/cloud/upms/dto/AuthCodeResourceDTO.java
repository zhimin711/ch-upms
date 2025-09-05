package com.ch.cloud.upms.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Schema(description = "授权码资源对象")
public class AuthCodeResourceDTO {
    
    @Schema(description = "授权码")
    private String code;
    
    @Schema(description = "授权内容（JSON字符串或对象）", example = "{\"resourceType\":\"article\",\"resourceIds\":[\"123\"],\"actions\":[\"read\"]}")
    private String content;
} 