package com.ch.cloud.upms.base.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

@Data
@TableName("auth_code_usage_record")
public class AuthCodeUsageRecord implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String code;
    private Long userId;
    private String resourceType;
    private String resourceId;
    private String action;
    private Date useTime;
    private String remark;
} 