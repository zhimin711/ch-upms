-- ----------------------------
-- Table structure for st_permission
-- ----------------------------
DROP TABLE if EXISTS `st_permission`;
CREATE TABLE `st_permission` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `TYPE` varchar(10) DEFAULT NULL COMMENT '类型(1.目录 2.菜单页 3.按钮)',
  `NAME` varchar(50) NOT NULL COMMENT '名称',
  `CODE` varchar(64) NOT NULL COMMENT '代码',
  `URL` varchar(150) DEFAULT NULL COMMENT '链接地址',
  `REDIRECT` varchar(150) DEFAULT NULL COMMENT '转发地址',
  `ICON` varchar(30) DEFAULT NULL COMMENT '图标',
  `SORT` int(10) DEFAULT '0' COMMENT '菜单顺序',
  `IS_SHOW` char(1) NOT NULL COMMENT '是否显示(0.否 1.是)',
  `IS_SYS` char(1) NOT NULL DEFAULT '0' COMMENT '是否为系统权限(0.否 1.是)',
  `PARENT_ID` varchar(64) DEFAULT NULL COMMENT '上级菜单ID',
  `PARENT_NAME` varchar(20) DEFAULT NULL,
  `METHOD` varchar(50) DEFAULT NULL COMMENT '请求方法',
  `STATUS` char(1) NOT NULL DEFAULT '1' COMMENT '状态: 0.禁用 1.启用',
  `CREATE_BY` varchar(64) DEFAULT NULL COMMENT '创建人',
  `CREATE_AT` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `UPDATE_BY` varchar(64) DEFAULT NULL COMMENT '更新人',
  `UPDATE_AT` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`ID`) USING BTREE,
  UNIQUE KEY `UK_ST_P_CODE` (`CODE`) USING BTREE,
  KEY `IDX_ST_P_URL` (`URL`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=137 DEFAULT CHARSET=utf8 COMMENT='后台权限表';



-- ----------------------------
-- Records of st_permission
-- ----------------------------
