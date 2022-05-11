package com.ch.cloud.upms.dto;

import io.swagger.annotations.ApiModelProperty;
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
public class TenantDto {

    /**
     * 租户Id
     */
    @ApiModelProperty("ID")
    private Long   id;
    /**
     * 租户名称
     */
    @ApiModelProperty("名称")
    private String name;
    /**
     * 租户部门
     */
    @ApiModelProperty("部门")
    private String deptId;

}
