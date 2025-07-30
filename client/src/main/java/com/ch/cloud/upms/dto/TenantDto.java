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
    @Schema(description = "租户所属部门")
    private String deptId;

    @Schema(description = "有管理权限")
    private boolean hasAdmin;
}
