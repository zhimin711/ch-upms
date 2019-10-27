-- ----------------------------
-- Table structure for st_menu
-- ----------------------------
DROP TABLE if EXISTS `st_menu`;
CREATE TABLE `st_permission` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `NAME` varchar(50) NOT NULL COMMENT '名称',
  `CODE` varchar(64) NOT NULL COMMENT '代码',
  `URL` varchar(150) DEFAULT NULL COMMENT '链接地址',
  `TYPE` varchar(10) DEFAULT NULL COMMENT '类型(1.目录 2.菜单页 3.按钮)',
  `ICON` varchar(30) DEFAULT NULL COMMENT '图标',
  `SORT` int(10) DEFAULT 0 COMMENT '菜单顺序',
  `IS_SHOW` char(1) NOT NULL COMMENT '是否显示(0.否 1.是)',
  `IS_SYS` char(1) NOT NULL DEFAULT '0' COMMENT '是否为系统权限(0.否 1.是)',
  `PARENT_ID` varchar(64) DEFAULT NULL COMMENT '上级菜单ID',
  `PARENT_NAME` varchar(20) DEFAULT NULL,
  `METHOD` varchar(50) DEFAULT NULL COMMENT '请求方法',
  `STATUS` char(1) NOT NULL DEFAULT '1' COMMENT '状态: 0.禁用 1.启用',
  `CREATE_BY` varchar(64) DEFAULT NULL COMMENT '创建人',
  `CREATE_AT` timestamp NOT NULL DEFAULT current_timestamp() COMMENT '创建时间',
  `UPDATE_BY` varchar(64) DEFAULT NULL COMMENT '更新人',
  `UPDATE_AT` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`ID`) USING BTREE,
  UNIQUE KEY `UK_ST_P_CODE` (`CODE`) USING BTREE,
  KEY `IDX_ST_P_URL` (`URL`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='后台权限表';
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
