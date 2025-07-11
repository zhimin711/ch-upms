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
@Schema(description = "角色DTO")
public class RoleDto implements Serializable {

    @Schema(description = "角色ID",hidden = true)
    private Long id;

    @Schema(description = "角色名称")
    private String name;

    @Schema(description = "角色编码")
    private String code;

    @Schema(description = "角色描述")
    private String description;
}
