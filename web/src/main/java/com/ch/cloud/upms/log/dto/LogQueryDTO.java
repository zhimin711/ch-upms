package com.ch.cloud.upms.log.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 描述：
 *
 * @author zhimi
 * @since 2025/5/29
 */
@lombok.Data
public class LogQueryDTO {
    
    @Schema(description = "开始时间",  example = "2025-05-29 00:00:00")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull(message = "开始时间不能为空")
    private Date startTime;
    
    @Schema(description = "结束时间",  example = "2025-05-29 23:59:59")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull(message = "结束时间不能为空")
    private Date endTime;
    
    @Schema(description = "请求地址",  example = "")
    private String url;
    
    @Schema(description = "授权代码",  example = "")
    private String authCode;
}
