-- ----------------------------
-- Table structure for st_menu
-- ----------------------------
DROP TABLE if EXISTS `st_menu`;
CREATE TABLE `st_menu` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `PID` VARCHAR(80) DEFAULT NULL COMMENT '上级菜单ID',
  `NAME` varchar(32) NOT NULL COMMENT '菜单名称',
  `CODE` varchar(64) NOT NULL COMMENT '菜单代码',
  `ICON` varchar(64) DEFAULT NULL COMMENT '图标代码',
  `URL` varchar(150) DEFAULT NULL COMMENT '地址',
  `SORT` int(10) DEFAULT '0' COMMENT '菜单顺序',
  `TYPE` char(1) NOT NULL DEFAULT '2' COMMENT '类型: 1.目录 2.菜单 3.按钮 4.链接)',
  `DESCRIPTION` varchar(255) DEFAULT NULL COMMENT '描述',
  `STATUS` char(1) NOT NULL DEFAULT '1' COMMENT '状态: s.常量 0.禁用 1.启用',
  `CREATE_BY` varchar(64) DEFAULT NULL COMMENT '创建人',
  `CREATE_AT` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `UPDATE_BY` varchar(64) DEFAULT NULL COMMENT '更新人',
  `UPDATE_AT` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `UK_ST_M_CODE` (`CODE`),
  KEY `IDX_ST_M_PID` (`PID`),
  KEY `IDX_ST_M_URL` (`URL`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统表-菜单';
-- ----------------------------
-- Records of st_menu
-- ----------------------------
INSERT INTO `st_menu` (`ID`, `PID`, `NAME`, `CODE`, `ICON`, `URL`, `SORT`, `TYPE`, `DESCRIPTION`, `STATUS`, `CREATE_BY`, `CREATE_AT`, `UPDATE_BY`, `UPDATE_AT`) VALUES ('1', NULL, '系统管理', 'sys', 'el-icon-lx-settings', NULL, '1', '1', NULL, '1', 'sys', '2018-12-22 21:46:57', NULL, NULL);
INSERT INTO `st_menu` (`ID`, `PID`, `NAME`, `CODE`, `ICON`, `URL`, `SORT`, `TYPE`, `DESCRIPTION`, `STATUS`, `CREATE_BY`, `CREATE_AT`, `UPDATE_BY`, `UPDATE_AT`) VALUES ('2', '1', '用户管理', 'sysUser', NULL, NULL, '1', '2', NULL, '1', 'sys', '2018-12-22 21:47:17', NULL, NULL);
INSERT INTO `st_menu` (`ID`, `PID`, `NAME`, `CODE`, `ICON`, `URL`, `SORT`, `TYPE`, `DESCRIPTION`, `STATUS`, `CREATE_BY`, `CREATE_AT`, `UPDATE_BY`, `UPDATE_AT`) VALUES ('3', '1', '角色管理', 'sysRole', NULL, NULL, '2', '2', NULL, '1', 'sys', '2018-12-22 21:47:58', NULL, NULL);
INSERT INTO `st_menu` (`ID`, `PID`, `NAME`, `CODE`, `ICON`, `URL`, `SORT`, `TYPE`, `DESCRIPTION`, `STATUS`, `CREATE_BY`, `CREATE_AT`, `UPDATE_BY`, `UPDATE_AT`) VALUES ('4', '1', '菜单管理', 'sysPermission', NULL, NULL, '3', '2', NULL, '1', 'sys', '2018-12-22 21:48:19', NULL, NULL);
INSERT INTO `st_menu` (`ID`, `PID`, `NAME`, `CODE`, `ICON`, `URL`, `SORT`, `TYPE`, `DESCRIPTION`, `STATUS`, `CREATE_BY`, `CREATE_AT`, `UPDATE_BY`, `UPDATE_AT`) VALUES ('5', NULL, '博客管理', 'blog', 'el-icon-tickets', NULL, '2', '2', NULL, '1', NULL, '2018-12-28 20:14:25', 'admin', '2019-02-28 14:01:21');
INSERT INTO `st_menu` (`ID`, `PID`, `NAME`, `CODE`, `ICON`, `URL`, `SORT`, `TYPE`, `DESCRIPTION`, `STATUS`, `CREATE_BY`, `CREATE_AT`, `UPDATE_BY`, `UPDATE_AT`) VALUES ('6', '5', '分类管理', 'category', '', '2', '0', '2', NULL, '1', 'admin', '2019-02-28 11:41:21', 'admin', '2019-02-28 14:27:11');
INSERT INTO `st_menu` (`ID`, `PID`, `NAME`, `CODE`, `ICON`, `URL`, `SORT`, `TYPE`, `DESCRIPTION`, `STATUS`, `CREATE_BY`, `CREATE_AT`, `UPDATE_BY`, `UPDATE_AT`) VALUES ('7', '5', '文章管理', 'Article', NULL, '1', '0', '2', NULL, '1', 'admin', '2019-02-28 14:05:47', 'admin', '2019-02-28 14:29:12');
INSERT INTO `st_menu` (`ID`, `PID`, `NAME`, `CODE`, `ICON`, `URL`, `SORT`, `TYPE`, `DESCRIPTION`, `STATUS`, `CREATE_BY`, `CREATE_AT`, `UPDATE_BY`, `UPDATE_AT`) VALUES ('8', NULL, '其他', 'other', 'el-icon-star-on', NULL, '3', '2', NULL, '1', 'admin', '2019-02-28 14:30:39', 'admin', '2019-02-28 14:36:07');
INSERT INTO `st_menu` (`ID`, `PID`, `NAME`, `CODE`, `ICON`, `URL`, `SORT`, `TYPE`, `DESCRIPTION`, `STATUS`, `CREATE_BY`, `CREATE_AT`, `UPDATE_BY`, `UPDATE_AT`) VALUES ('9', '8', '书签管理', 'bookmark', NULL, NULL, '1', '2', NULL, '1', 'admin', '2019-02-28 14:36:56', 'admin', '2019-02-28 14:38:01');
