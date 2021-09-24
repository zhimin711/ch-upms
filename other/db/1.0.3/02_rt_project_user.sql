-- ----------------------------
-- Table structure for rt_project_user
-- ----------------------------
DROP TABLE IF EXISTS `rt_project_user`;
CREATE TABLE `rt_project_user`(
  `PROJECT_ID` bigint(20) NOT NULL COMMENT '项目ID',
  `USER_ID` varchar(50) NOT NULL COMMENT '用户名/ID',
  `ROLE` varchar(50) NOT NULL COMMENT '角色：DEV（开发），TEST（测试），OPS（运维）',
  PRIMARY KEY(`PROJECT_ID`,`USER_ID`),
  KEY `IDX_RT_PU_UID` (`USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='关系-项目与用户表';
