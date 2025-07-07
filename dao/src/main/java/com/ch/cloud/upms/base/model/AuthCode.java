package com.ch.cloud.upms.base.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("bt_auth_code")
public class AuthCode implements Serializable {
    
    @TableId(type = IdType.AUTO)
    private Long id;
    
    /**
     * 授权码
     */
    private String code;
    
    /**
     * 授权内容（JSON字符串）
     */
    private String content;
    
    /**
     * 过期时间
     */
    private Date expireTime;
    
    /**
     * 最大使用次数
     */
    private Integer maxUses;
    
    /**
     * 已使用次数
     */
    private Integer usedCount;
    
    /**
     * 状态（1有效 0失效 2撤销）
     */
    private Integer status;
    
    
    /**
     * 创建人
     */
    private String createBy;
    /**
     * 创建时间
     */
    private Date createTime;
    
    /**
     * 更新时间
     */
    private Date updateTime;
} 