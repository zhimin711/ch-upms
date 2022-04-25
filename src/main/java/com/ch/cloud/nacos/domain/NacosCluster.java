package com.ch.cloud.nacos.domain;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * desc:
 *
 * @author zhimin
 * @date 2022/4/23 22:27
 */
@Data
@Accessors(chain = true)
@ApiModel("nacos集群")
@TableName("bt_nacos_cluster")
public class NacosCluster {
    /**
     * 集群API
     */
    @ApiModelProperty(name = "集群API")
    private String url;

    /**
     * 空间名称
     */
    @ApiModelProperty(name = "集群名称")
    private String name;

    /**
     * 描述
     */
    @ApiModelProperty(name = "描述")
    private String description;

    @ApiModelProperty(value = "删除标志（0代表存在 1代表删除）")
//    @TableField(fill = FieldFill.INSERT)
    @TableLogic
    private Boolean deleted;

    @ApiModelProperty(value = "创建人")
    private String createBy;

    @ApiModelProperty(value = "创建时间")
    private Date createAt;

    @ApiModelProperty(value = "更新人")
    private String updateBy;

    @ApiModelProperty(value = "更新时间")
    private Date updateAt;
}
