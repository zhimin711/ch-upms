package com.ch.cloud.upms.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 日志表-操作记录
 * </p>
 *
 * @author zhimin.ma
 * @since 2020-03-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("lt_op_record")
public class OPRecord extends Model<OPRecord> {

    private static final long serialVersionUID=1L;

    /**
     * 主键
     */
      @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    /**
     * 请求地址
     */
    private String url;

    /**
     * 请求方法
     */
    private String method;

    /**
     * 请求信息
     */
    private String request;

    /**
     * 代理信息
     */
    private String proxy;

    /**
     * 响应信息
     */
    private String response;

    /**
     * 请求权限
     */
    private String authCode;

    /**
     * 响应状态
     */
    private Integer status;

    /**
     * 请求用户
     */
    private String operator;

    /**
     * 请求IP
     */
    private String requestIp;
    /**
     * 请求时间
     */
    private Long requestTime;

    /**
     * 响应时间
     */
    private Long responseTime;
    /**
     * 错误代码
     */
    private String errorCode;
    /**
     * 错误消息
     */
    private String errorMessage;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
