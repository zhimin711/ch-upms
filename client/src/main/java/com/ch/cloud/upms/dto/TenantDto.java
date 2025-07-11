package com.ch.cloud.upms.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * <p>
 * desc: 租户信息
 * </p>
 *
 * @author zhimin.ma
 * @since 2021/10/16
 */
@Data
@Schema(description = "租户DTO")
public class TenantDto {

    /**
     * 租户Id
     */
    @Schema(description = "租户ID")
    private Long   id;
    /**
     * 租户名称
     */
    @Schema(description = "租户名称")
    private String name;
    /**
     * 租户部门
     */
    @Schema(description = "租户编码")
    private String code;
    /**
     * 租户描述
     */
    @Schema(description = "租户描述")
    private String description;

}
