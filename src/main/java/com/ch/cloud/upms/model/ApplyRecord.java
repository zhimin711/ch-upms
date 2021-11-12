package com.ch.cloud.upms.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * <p>
 * 业务-申请记录表
 * </p>
 *
 * @author zhimin.ma
 * @since 2021-11-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("bt_apply_record")
public class ApplyRecord extends Model<ApplyRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    /**
     * 类型：1:project-namespace 2: 3:
     */
    private String type;

    /**
     * 申请内容
     */
    private String content;

    /**
     * 状态：0.待审核 1.已通过 2.拒绝
     */
    private String status;

    /**
     * 审核人
     */
    private String approveBy;

    /**
     * 审核时间
     */
    private Date approveAt;

    /**
     * 申请时间
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

}
