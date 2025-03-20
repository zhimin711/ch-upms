package com.ch.cloud.upms.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * decs:
 *
 * @author 01370603
 * @since 2019/9/10
 */
@Data
@Schema(description = "角色信息")
public class RoleDto implements Serializable {

    @Schema(hidden = true)
    private Long id;
    @Schema(description = "代码")
    private String code;
    @Schema(description = "名称")
    private String name;
}