package com.ch.cloud.upms.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 关系-命名空间表
 * </p>
 *
 * @author zhimin.ma
 * @since 2021-09-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("bt_namespace")
public class Namespace extends Model<Namespace> {

    private static final long serialVersionUID = 1L;

    /**
     * 命名空间ID
     */
    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    /**
     * 类型：0. 1.NACOS 2.RocketMQ 3.Kafka
     */
    private String type;

    /**
     * 集群ID
     */
    private Long clusterId;

    /**
     * 命名空间唯一标识
     */
    private String uid;

    /**
     * 空间名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 逻辑删除：0.否 1.是
     */
    private Boolean deleted;

    /**
     * 创建时间
     */
    private Date createAt;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 更新时间
     */
    private Date updateAt;

    /**
     * 更新人
     */
    private String updateBy;


    @Override
    public Serializable pkVal() {
        return this.id;
    }

}
