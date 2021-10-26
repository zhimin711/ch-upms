DROP TABLE IF EXISTS `bt_namespace`;
CREATE TABLE `bt_namespace`
(
    `ID`          bigint(20)  NOT NULL AUTO_INCREMENT COMMENT '主键',
    `UID`         varchar(64) NOT NULL COMMENT '命名空间唯一标识',
    `NAME`        varchar(150)         DEFAULT NULL COMMENT '空间名称',
    `DESCRIPTION` varchar(255)         DEFAULT NULL COMMENT '描述',
    `SYNC_NACOS`  tinyint(1)  NOT NULL DEFAULT '0' COMMENT '同步到Nacos状态：0.未同步 1.已同步',
    `DELETED`     tinyint(1)  NOT NULL DEFAULT '0' COMMENT '逻辑删除：0.否 1.是',
    `CREATE_AT`   timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `CREATE_BY`   varchar(64)          DEFAULT NULL COMMENT '创建人',
    `UPDATE_AT`   datetime             DEFAULT NULL COMMENT '更新时间',
    `UPDATE_BY`   varchar(64)          DEFAULT NULL COMMENT '更新人',
    PRIMARY KEY (`ID`),
    unique key `UK_BT_N_UID` (`UID`)
) ENGINE = InnoDB COMMENT ='业务-命名空间表';

DROP TABLE IF EXISTS `rt_project_namespace`;
CREATE TABLE `rt_project_namespace`
(
    `PROJECT_ID`   bigint NOT NULL COMMENT '项目ID',
    `NAMESPACE_ID` bigint NOT NULL COMMENT '命名空间ID',
    PRIMARY KEY (`PROJECT_ID`, `NAMESPACE_ID`),
    KEY `IDX_RT_PU_NAMESPACE_ID` (`NAMESPACE_ID`)
) ENGINE = InnoDB COMMENT ='关系-项目与命名空间表';

DROP TABLE IF EXISTS `rt_user_namespace`;
CREATE TABLE `rt_user_namespace`
(
    `USER_ID`      varchar(64)  NOT NULL COMMENT '用户ID',
    `PROJECT_ID`   bigint  NOT NULL COMMENT '项目ID',
    `NAMESPACE_ID` bigint  NOT NULL COMMENT '命名空间ID',
    PRIMARY KEY (`USER_ID`, `PROJECT_ID`, `NAMESPACE_ID`),
    KEY `IDX_RT_PU_PROJECT_ID` (`PROJECT_ID`),
    KEY `IDX_RT_PU_NAMESPACE_ID` (`NAMESPACE_ID`)
) ENGINE = InnoDB COMMENT ='关系-用户与命名空间表';