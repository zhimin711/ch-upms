DROP TABLE IF EXISTS `lt_op_detail`;
CREATE TABLE `lt_op_detail`
(
    `id`           bigint   NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `op_id`        bigint   NOT NULL DEFAULT '0' COMMENT 'opID',
    `AUTH_CODE`    varchar(255)      DEFAULT NULL COMMENT '请求权限',
    `REQUEST`      longtext COMMENT '请求信息',
    `PROXY`        longtext COMMENT '代理信息',
    `RESPONSE`     longtext COMMENT '响应信息',
    `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`, `created_time`)
) PARTITION BY RANGE (TO_DAYS(created_time))(
PARTITION p20221101 VALUES LESS THAN ( TO_DAYS('20221101')),
PARTITION p20221201 VALUES LESS THAN ( TO_DAYS('20221201'))
);