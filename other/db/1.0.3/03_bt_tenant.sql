drop TABLE if exists `bt_tenant`;
CREATE TABLE `bt_tenant`
(
    `ID`              bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `DEPARTMENT_ID`   varchar(55)         DEFAULT NULL COMMENT '租户部门ID',
    `DEPARTMENT_NAME` varchar(250)        DEFAULT NULL COMMENT '租户部门名称',
    `NAME`            varchar(250)        DEFAULT NULL COMMENT '租户别名',
    `MANAGER`         varchar(55)         DEFAULT NULL COMMENT '负责人',
    `SORT`            int(10)             DEFAULT NULL COMMENT '排序',
    `DESCRIPTION`     varchar(255)        DEFAULT NULL COMMENT '描述',
    `STATUS`          char(1)    NOT NULL DEFAULT '0' COMMENT '状态：0.失效 1.生效',
    `DELETED`         tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除：0.否 1.是',
    `CREATE_AT`       timestamp  NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `CREATE_BY`       varchar(64)         DEFAULT NULL COMMENT '创建人',
    `UPDATE_AT`       datetime            DEFAULT NULL COMMENT '更新时间',
    `UPDATE_BY`       varchar(64)         DEFAULT NULL COMMENT '更新人',
    PRIMARY KEY (`ID`),
    UNIQUE KEY `IDX_BT_T_CODE` (`DEPARTMENT_ID`),
    KEY `IDX_BT_T_STATUS` (`STATUS`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='业务-租户表';

CREATE TABLE `rt_tenant_project`
(
    `TENANT_ID`  bigint      NOT NULL COMMENT '租户ID',
    `PROJECT_ID` bigint      NOT NULL COMMENT '项目ID',
    `ROLE`       varchar(50) NOT NULL COMMENT '角色：DEV（开发），TEST（测试），OPS（运维）',
    PRIMARY KEY (`TENANT_ID`, `PROJECT_ID`),
    KEY `IDX_RT_TP_PROJECT_ID` (`PROJECT_ID`)
) ENGINE = InnoDB COMMENT ='关系-项目与租户表';