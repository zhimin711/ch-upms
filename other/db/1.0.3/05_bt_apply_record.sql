DROP TABLE IF EXISTS `bt_apply_record`;
CREATE TABLE `bt_apply_record`
(
    `ID`         bigint(20)  NOT NULL AUTO_INCREMENT COMMENT '主键',
    `TYPE`       varchar(64) NOT NULL COMMENT '类型：1:project-namespace 2: 3:',
    `CONTENT`    text                 DEFAULT NULL COMMENT '申请内容',
    `STATUS`     char(1)  NOT NULL DEFAULT '0' COMMENT '状态：0.待审核 1.已通过 2.拒绝',
    `APPROVE_BY` varchar(64)          DEFAULT NULL COMMENT '审核人',
    `APPROVE_AT` datetime             DEFAULT NULL COMMENT '审核时间',
    `CREATE_AT`  timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '申请时间',
    `CREATE_BY`  varchar(64)          DEFAULT NULL COMMENT '创建人',
    `UPDATE_AT`  datetime             DEFAULT NULL COMMENT '更新时间',
    `UPDATE_BY`  varchar(64)          DEFAULT NULL COMMENT '更新人',
    PRIMARY KEY (`ID`),
    INDEX IDX_BT_AR_TYPE_STATUS (`TYPE`, `STATUS`)
) ENGINE = InnoDB COMMENT ='业务-申请记录表';
