package com.ch.cloud.upms.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * desc:用户部门职位表
 *
 * @author zhimin
 * @date 2018/12/21 11:11 PM
 */
@Data
public class DepartmentDuty implements Serializable {

    /**
     * 账号
     */
    private String userId;
    /**
     * 部门
     */
    private String department;
    /**
     * 职位
     */
    private String duty;

    private String orgId;

    private String departmentName;
    private String dutyName;

}
