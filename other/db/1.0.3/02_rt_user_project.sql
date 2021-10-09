-- ----------------------------
-- Table structure for rt_user_project
-- ----------------------------
DROP TABLE IF EXISTS `rt_user_project`;
CREATE TABLE `rt_user_project`(
  `PROJECT_ID` bigint(20) NOT NULL COMMENT '项目ID',
  `USER_ID` varchar(50) NOT NULL COMMENT '用户名/ID',
  `ROLE` varchar(50) NOT NULL COMMENT '角色：DEV（开发），TEST（测试），MGR（负责）',
  PRIMARY KEY(`PROJECT_ID`,`USER_ID`),
  KEY `IDX_RT_PU_UID` (`USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='关系-项目与用户表';
