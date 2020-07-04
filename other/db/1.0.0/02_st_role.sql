-- ----------------------------
-- Table structure for st_role
-- ----------------------------
DROP TABLE IF EXISTS `st_role`;
CREATE TABLE `st_role` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `NAME` varchar(32) NOT NULL COMMENT '名称',
  `CODE` varchar(64) NOT NULL COMMENT '代码',
  `TYPE` CHAR(1) NOT NULL DEFAULT '2' COMMENT '类型: 1.系统 2.自定义 3.开发 4.测试 5.经理',
  `DESCRIPTION` varchar(255) DEFAULT NULL COMMENT '描述',
  `STATUS` char(1) NOT NULL DEFAULT '1' COMMENT '状态: 0.禁用 1.启动',
  `CREATE_BY` varchar(64) DEFAULT NULL COMMENT '创建人',
  `CREATE_AT` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `UPDATE_BY` varchar(64) DEFAULT NULL COMMENT '更新人',
  `UPDATE_AT` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `UK_ST_R_CODE` (`CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='后台角色表';

-- ----------------------------
-- Records of A_ROLE
-- ----------------------------
INSERT INTO st_role (NAME, CODE, TYPE, DESCRIPTION, STATUS, CREATE_BY, CREATE_AT, UPDATE_BY, UPDATE_AT) VALUES ('超级管理员', 'SUPER_ADMIN', '0', '', '1', 'admin', '2018-08-11 23:51:35', null, null);
INSERT INTO st_role (NAME, CODE, TYPE, DESCRIPTION, STATUS, CREATE_BY, CREATE_AT, UPDATE_BY, UPDATE_AT) VALUES ('系统管理员', 'ADMIN_ROLE', '1', null, '1', 'admin', '2018-08-04 15:56:42', null, null);
INSERT INTO st_role (NAME, CODE, TYPE, DESCRIPTION, STATUS, CREATE_BY, CREATE_AT, UPDATE_BY, UPDATE_AT) VALUES ('系统用户', 'SYS_ROLE', '1', null, '1', 'admin', '2018-08-04 15:56:42', null, null);
