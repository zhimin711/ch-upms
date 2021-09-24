drop TABLE if exists `bt_project`;
CREATE TABLE `bt_project`
(
    `ID`          bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `PARENT_CODE` varchar(100)        DEFAULT NULL COMMENT '上级代码(控制在两层)',
    `PARENT_NAME` varchar(200)        DEFAULT NULL COMMENT '上级名称',
    `NAME`        varchar(50)         DEFAULT NULL COMMENT '名称',
    `CODE`        varchar(50)         DEFAULT NULL COMMENT '代码',
    `DEPARTMENT`  varchar(55)         DEFAULT NULL COMMENT '所属部门',
    `MANAGER`     varchar(55)         DEFAULT NULL COMMENT '负责人',
    `SORT`        int(10)             DEFAULT NULL COMMENT '排序',
    `DESCRIPTION` varchar(255)        DEFAULT NULL COMMENT '描述',
    `STATUS`      char(1)    NOT NULL DEFAULT '0' COMMENT '状态：0.失效 1.生效',
    `DELETED`     tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除：0.否 1.是',
    `CREATE_AT`   timestamp  NOT NULL DEFAULT current_timestamp() COMMENT '创建时间',
    `CREATE_BY`   varchar(64)         DEFAULT NULL COMMENT '创建人',
    `UPDATE_AT`   datetime            DEFAULT NULL COMMENT '更新时间',
    `UPDATE_BY`   varchar(64)         DEFAULT NULL COMMENT '更新人',
    PRIMARY KEY (`ID`),
    UNIQUE KEY `IDX_BT_PC_CODES` (`PARENT_CODE`, `CODE`),
    KEY `IDX_BT_PC_STATUS` (`STATUS`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='业务-项目表';