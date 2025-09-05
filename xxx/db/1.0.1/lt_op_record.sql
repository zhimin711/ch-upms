DROP TABLE IF EXISTS `lt_op_record`;
CREATE TABLE `lt_op_record`
(
    `ID`            bigint(20)   NOT NULL AUTO_INCREMENT COMMENT '主键',
    `ID`            bigint(20)   NOT NULL AUTO_INCREMENT COMMENT '主键',
    `URL`           VARCHAR(250) NULL COMMENT '请求地址',
    `METHOD`        VARCHAR(50)  NOT NULL COMMENT '请求方法',
    `REQUEST`       text         NULL COMMENT '请求信息',
    `PROXY`         text         NULL COMMENT '代理信息',
    `RESPONSE`      text         NULL COMMENT '响应信息',
    `AUTH_CODE`     VARCHAR(255) NULL COMMENT '请求权限',
    `STATUS`        int(10)      NULL COMMENT '响应状态',
    `OPERATOR`      VARCHAR(64)  NULL COMMENT '请求用户',
    `REQUEST_TIME`  TIMESTAMP    NOT NULL COMMENT '请求时间',
    `RESPONSE_TIME` TIMESTAMP    NOT NULL COMMENT '响应时间',
    `ERROR_CODE`    VARCHAR(10)  NULL COMMENT '错误代码',
    `ERROR_MESSAGE` VARCHAR(255) NULL COMMENT '错误消息',
    PRIMARY KEY (`ID`),
    KEY `IDX_LT_OR_STATUS` (`STATUS`),
    KEY `IDX_LT_OR_OPERATOR` (`OPERATOR`)
) ENGINE = INNODB
  DEFAULT CHARSET = UTF8 COMMENT '日志表-操作记录';

CREATE TABLE `lt_op_record_1`
(
    `ID`            bigint      NOT NULL AUTO_INCREMENT COMMENT '主键',
    `URL`           varchar(250) DEFAULT NULL COMMENT '请求地址',
    `METHOD`        varchar(50) NOT NULL COMMENT '请求方法',
    `REQUEST`       longtext COMMENT '请求信息',
    `PROXY`         longtext COMMENT '代理信息',
    `RESPONSE`      longtext COMMENT '响应信息',
    `AUTH_TYPE`     int          DEFAULT NULL COMMENT '授权类型：1.登录 2.操作 3.',
    `AUTH_CODE`     varchar(255) DEFAULT NULL COMMENT '请求权限',
    `STATUS`        int          DEFAULT NULL COMMENT '响应状态',
    `OPERATOR`      varchar(64)  DEFAULT NULL COMMENT '请求用户',
    `REQUEST_TIME`  bigint      NOT NULL COMMENT '请求时间',
    `RESPONSE_TIME` bigint      NOT NULL COMMENT '响应时间',
    `REQUEST_IP`    varchar(255) DEFAULT NULL COMMENT '请求IP',
    `ERROR_CODE`    varchar(10)  DEFAULT NULL COMMENT '错误代码',
    `ERROR_MESSAGE` varchar(255) DEFAULT NULL COMMENT '错误消息',
    PRIMARY KEY (`ID`) USING BTREE,
    KEY `IDX_LT_OR_OPERATOR` (`OPERATOR`) USING BTREE,
    KEY `IDX_LT_OR_U_S` (`URL`, `STATUS`) USING BTREE,
    KEY `IDX_LT_OR_AC` (`AUTH_CODE`) USING BTREE
) COMMENT ='日志表-操作记录';