/*
 Navicat Premium Data Transfer

 Source Server         : ch_work
 Source Server Type    : MySQL
 Source Server Version : 50726
 Source Host           : 192.168.0.104:3306
 Source Schema         : ch_upms

 Target Server Type    : MySQL
 Target Server Version : 50726
 File Encoding         : 65001

 Date: 18/02/2021 08:16:39
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for lt_op_record
-- ----------------------------
DROP TABLE IF EXISTS `lt_op_record`;
CREATE TABLE `lt_op_record`  (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `URL` varchar(250) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '请求地址',
  `METHOD` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '请求方法',
  `REQUEST` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '请求信息',
  `PROXY` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '代理信息',
  `RESPONSE` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '响应信息',
  `AUTH_CODE` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '请求权限',
  `STATUS` int(10) NULL DEFAULT NULL COMMENT '响应状态',
  `OPERATOR` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '请求用户',
  `REQUEST_TIME` bigint(20) NOT NULL COMMENT '请求时间',
  `RESPONSE_TIME` bigint(20) NOT NULL COMMENT '响应时间',
  `REQUEST_IP` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '请求IP',
  `ERROR_CODE` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '错误代码',
  `ERROR_MESSAGE` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '错误消息',
  PRIMARY KEY (`ID`) USING BTREE,
  INDEX `IDX_LT_OR_OPERATOR`(`OPERATOR`) USING BTREE,
  INDEX `IDX_LT_OR_U_S`(`URL`, `STATUS`) USING BTREE,
  INDEX `IDX_LT_OR_AC`(`AUTH_CODE`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 61338 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '日志表-操作记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of lt_op_record
-- ----------------------------

-- ----------------------------
-- Table structure for lt_op_record2
-- ----------------------------
DROP TABLE IF EXISTS `lt_op_record2`;
CREATE TABLE `lt_op_record2`  (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `URL` varchar(250) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '请求地址',
  `METHOD` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '请求方法',
  `REQUEST` longtext CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL COMMENT '请求信息',
  `PROXY` longtext CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL COMMENT '代理信息',
  `RESPONSE` longtext CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL COMMENT '响应信息',
  `AUTH_CODE` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '请求权限',
  `STATUS` int(10) NULL DEFAULT NULL COMMENT '响应状态',
  `OPERATOR` varchar(64) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '请求用户',
  `REQUEST_TIME` bigint(20) NOT NULL COMMENT '请求时间',
  `RESPONSE_TIME` bigint(20) NOT NULL COMMENT '响应时间',
  `REQUEST_IP` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '请求IP',
  `ERROR_CODE` varchar(10) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '错误代码',
  `ERROR_MESSAGE` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '错误消息',
  PRIMARY KEY (`ID`, `REQUEST_TIME`) USING BTREE,
  INDEX `IDX_LT_OR_OPERATOR`(`OPERATOR`) USING BTREE,
  INDEX `IDX_LT_OR_U_S`(`URL`, `STATUS`) USING BTREE,
  INDEX `IDX_LT_OR_AC`(`AUTH_CODE`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci COMMENT = '日志表-操作记录' ROW_FORMAT = Dynamic PARTITION BY RANGE (REQUEST_TIME)
PARTITIONS 13
(PARTITION `p_first` VALUES LESS THAN (1475280000) ENGINE = InnoDB MAX_ROWS = 0 MIN_ROWS = 0 ,
PARTITION `p201610` VALUES LESS THAN (1477958400) ENGINE = InnoDB MAX_ROWS = 0 MIN_ROWS = 0 ,
PARTITION `p201611` VALUES LESS THAN (1480550400) ENGINE = InnoDB MAX_ROWS = 0 MIN_ROWS = 0 ,
PARTITION `p201612` VALUES LESS THAN (1483228800) ENGINE = InnoDB MAX_ROWS = 0 MIN_ROWS = 0 ,
PARTITION `p201701` VALUES LESS THAN (1485907200) ENGINE = InnoDB MAX_ROWS = 0 MIN_ROWS = 0 ,
PARTITION `p201702` VALUES LESS THAN (1488326400) ENGINE = InnoDB MAX_ROWS = 0 MIN_ROWS = 0 ,
PARTITION `p201703` VALUES LESS THAN (1491004800) ENGINE = InnoDB MAX_ROWS = 0 MIN_ROWS = 0 ,
PARTITION `p201704` VALUES LESS THAN (1493596800) ENGINE = InnoDB MAX_ROWS = 0 MIN_ROWS = 0 ,
PARTITION `p201705` VALUES LESS THAN (1496275200) ENGINE = InnoDB MAX_ROWS = 0 MIN_ROWS = 0 ,
PARTITION `p201706` VALUES LESS THAN (1498867200) ENGINE = InnoDB MAX_ROWS = 0 MIN_ROWS = 0 ,
PARTITION `p201707` VALUES LESS THAN (1501545600) ENGINE = InnoDB MAX_ROWS = 0 MIN_ROWS = 0 ,
PARTITION `p201708` VALUES LESS THAN (1504224000) ENGINE = InnoDB MAX_ROWS = 0 MIN_ROWS = 0 ,
PARTITION `p_future` VALUES LESS THAN (MAXVALUE) ENGINE = InnoDB MAX_ROWS = 0 MIN_ROWS = 0 )
;

-- ----------------------------
-- Table structure for mt_dict
-- ----------------------------
DROP TABLE IF EXISTS `mt_dict`;
CREATE TABLE `mt_dict`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '字典主键',
  `pid` bigint(20) NULL DEFAULT 0 COMMENT '上级id',
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT '' COMMENT '字典名称',
  `code` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT '' COMMENT '字典类型',
  `sort` int(4) NULL DEFAULT 1 COMMENT '显示顺序',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '备注',
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT '1' COMMENT '状态（0停用 1正常）',
  `deleted` tinyint(1) NULL DEFAULT 0 COMMENT '删除标志（0代表存在 1代表删除）',
  `create_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_at` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_at` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_pid_code`(`pid`, `code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci COMMENT = '数据字典表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of mt_dict
-- ----------------------------
INSERT INTO `mt_dict` VALUES (1, 0, '状态', 'status', 1, NULL, '1', 0, 'admin', '2020-07-06 00:58:40', 'admin', '2020-07-06 00:58:40');
INSERT INTO `mt_dict` VALUES (2, 1, '001001', '001001', 11, NULL, '0', 1, 'admin', '2020-07-06 00:58:40', 'admin', '2020-07-06 14:17:21');
INSERT INTO `mt_dict` VALUES (3, 1, '禁用', '0', 2, NULL, '1', 0, 'admin', '2020-07-06 00:59:00', 'admin', '2020-07-06 14:08:16');
INSERT INTO `mt_dict` VALUES (4, 0, '性别', 'sex', 1, NULL, '1', 0, 'admin', '2020-07-06 13:54:00', 'admin', '2020-07-06 13:54:00');
INSERT INTO `mt_dict` VALUES (5, 4, '男', '1', 1, NULL, '1', 0, 'admin', '2020-07-06 13:54:01', 'admin', '2020-07-06 13:54:01');
INSERT INTO `mt_dict` VALUES (6, 1, '启用', '1', 1, NULL, '1', 0, 'admin', '2020-07-06 14:02:54', 'admin', '2020-07-06 14:08:16');
INSERT INTO `mt_dict` VALUES (7, 4, '女', '2', 2, NULL, '1', 0, 'admin', '2020-07-16 00:49:35', 'admin', '2020-07-16 00:49:35');
INSERT INTO `mt_dict` VALUES (8, 4, '未知', '3', 3, NULL, '1', 0, 'admin', '2020-07-16 00:50:52', 'admin', '2020-07-16 00:50:52');

-- ----------------------------
-- Table structure for st_department
-- ----------------------------
DROP TABLE IF EXISTS `st_department`;
CREATE TABLE `st_department`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '部门id',
  `pid` bigint(20) NULL DEFAULT NULL COMMENT '上级部门id',
  `parent_id` varchar(150) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT '' COMMENT '上级部门ids',
  `parent_name` varchar(254) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '上级部门名称',
  `name` varchar(152) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT '' COMMENT '部门名称',
  `sort` int(4) NULL DEFAULT 1 COMMENT '显示顺序',
  `leader` varchar(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '负责人',
  `phone` varchar(11) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '联系电话',
  `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '邮箱',
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT '0' COMMENT '部门状态（0正常 1停用）',
  `deleted` tinyint(1) NULL DEFAULT 0 COMMENT '删除标志（0代表存在 1代表删除）',
  `create_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_at` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_at` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_pid`(`parent_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci COMMENT = '部门表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of st_department
-- ----------------------------
INSERT INTO `st_department` VALUES (1, 0, '0', NULL, '朝华科技', 1, '朝华', '17600807713', NULL, '1', 0, 'sys', '2020-07-03 14:24:05', 'admin', '2020-09-10 22:50:46');
INSERT INTO `st_department` VALUES (2, 1, '1', '朝华科技', '深圳总部', 2, 'sz', NULL, NULL, '1', 0, 'admin', '2020-07-04 00:11:00', 'admin', '2020-09-10 22:51:17');
INSERT INTO `st_department` VALUES (3, 1, '1', '朝华科技', '懂事会', 1, 'ch', NULL, NULL, '1', 0, 'admin', '2020-07-04 00:11:41', 'admin', '2020-09-10 22:51:13');
INSERT INTO `st_department` VALUES (4, 2, '1,2', '朝华科技,深圳总部', '业务研发部', 4, 'yf', NULL, NULL, '1', 0, 'admin', '2020-07-04 00:12:14', 'admin', '2020-09-10 22:51:44');
INSERT INTO `st_department` VALUES (5, 2, '1,2', '朝华科技,深圳总部', '架构部门', 2, 'a', NULL, NULL, '1', 0, 'admin', '2020-07-04 03:12:27', 'admin', '2020-09-10 22:51:25');
INSERT INTO `st_department` VALUES (6, 2, '1,2', '朝华科技,深圳总部', '大数据部门', 3, 'b', NULL, NULL, '1', 0, 'admin', '2020-07-04 03:13:10', 'admin', '2020-09-10 22:51:28');
INSERT INTO `st_department` VALUES (7, 4, '1,2,4', '朝华科技,深圳总部,业务研发部', '产品组', 1, 'av', NULL, NULL, '1', 0, 'admin', '2020-07-04 03:16:20', '', '2020-09-10 22:51:47');
INSERT INTO `st_department` VALUES (8, 4, '1,2,4', '朝华科技,深圳总部,业务研发部', '研发组', 5, 'b1', NULL, NULL, '1', 0, 'admin', '2020-07-04 03:16:45', 'admin', '2020-09-10 22:48:02');
INSERT INTO `st_department` VALUES (9, 4, '1,2,4', '朝华科技,深圳总部,业务研发部', '测试组', 3, 'a3', NULL, NULL, '1', 0, 'admin', '2020-07-04 03:17:11', '', '2020-09-10 22:51:49');
INSERT INTO `st_department` VALUES (10, 2, '1,2', '朝华科技,深圳总部', 'IT运维部', 5, 'a', '', '', '1', NULL, NULL, '2020-09-10 22:53:25', NULL, '2020-09-10 22:53:33');
INSERT INTO `st_department` VALUES (11, 6, '1,2,6', '朝华科技,深圳总部,大数据部门', '研发组', 1, 'a', '', '', '1', NULL, NULL, '2020-09-10 22:54:12', NULL, '2020-09-10 22:54:12');
INSERT INTO `st_department` VALUES (12, 6, '1,2,6', '朝华科技,深圳总部,大数据部门', '产品组', 2, 'b', '', '', '1', NULL, NULL, '2020-09-10 22:54:33', NULL, '2020-09-10 22:54:33');
INSERT INTO `st_department` VALUES (13, 6, '1,2,6', '朝华科技,深圳总部,大数据部门', '测试组', 3, 'c', '', '', '1', NULL, NULL, '2020-09-10 22:54:51', NULL, '2020-09-10 22:54:51');

-- ----------------------------
-- Table structure for st_department_position
-- ----------------------------
DROP TABLE IF EXISTS `st_department_position`;
CREATE TABLE `st_department_position`  (
  `department_id` bigint(20) NOT NULL COMMENT '组织ID',
  `position_id` bigint(20) NOT NULL COMMENT '职位ID',
  `position_amount` int(6) NULL DEFAULT 1 COMMENT '职位配额',
  PRIMARY KEY (`department_id`, `position_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_unicode_ci COMMENT = '组织和职位关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of st_department_position
-- ----------------------------
INSERT INTO `st_department_position` VALUES (2, 5, 1);
INSERT INTO `st_department_position` VALUES (3, 1, 1);
INSERT INTO `st_department_position` VALUES (4, 4, 1);
INSERT INTO `st_department_position` VALUES (5, 4, 1);
INSERT INTO `st_department_position` VALUES (6, 4, 1);
INSERT INTO `st_department_position` VALUES (8, 3, 1);
INSERT INTO `st_department_position` VALUES (9, 2, 1);

-- ----------------------------
-- Table structure for st_menu
-- ----------------------------
DROP TABLE IF EXISTS `st_menu`;
CREATE TABLE `st_menu`  (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `PARENT_ID` varchar(80) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '上级菜单ID',
  `PARENT_NAME` varchar(180) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '上级菜单ID',
  `NAME` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '菜单名称',
  `CODE` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '菜单代码',
  `ICON` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图标代码',
  `URL` varchar(150) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '地址',
  `METHOD` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '请求方法',
  `SORT` int(10) NULL DEFAULT 0 COMMENT '菜单顺序',
  `TYPE` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '2' COMMENT '类型: C.目录 M.菜单 B.按钮)',
  `DESCRIPTION` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  `STATUS` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '1' COMMENT '状态: 0.禁用 1.启用',
  `CREATE_BY` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `CREATE_AT` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `UPDATE_BY` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `UPDATE_AT` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`ID`) USING BTREE,
  UNIQUE INDEX `UK_ST_M_CODE`(`CODE`) USING BTREE,
  INDEX `IDX_ST_M_PID`(`PARENT_ID`) USING BTREE,
  INDEX `IDX_ST_M_URL`(`URL`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 30 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统表-菜单' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of st_menu
-- ----------------------------
INSERT INTO `st_menu` VALUES (1, '0', '', '系统管理', 'upms', NULL, 'upms', NULL, 1, 'C', NULL, '1', NULL, '2020-09-08 08:48:27', NULL, '2020-09-09 22:52:17');
INSERT INTO `st_menu` VALUES (2, '1', '系统管理', '用户管理', 'upmsUser', NULL, 'user', NULL, 1, 'M', NULL, '1', NULL, '2020-09-09 22:53:42', NULL, '2020-09-09 23:50:19');
INSERT INTO `st_menu` VALUES (3, '1', '系统管理', '角色管理', 'upmsRole', NULL, 'role', NULL, 2, 'M', NULL, '1', NULL, '2020-09-09 23:31:50', NULL, '2020-09-09 23:50:53');
INSERT INTO `st_menu` VALUES (4, '1', '系统管理', '权限管理', 'upmsPermission', NULL, 'permission', NULL, 3, 'M', NULL, '1', NULL, '2020-09-09 23:32:18', NULL, '2020-09-09 23:50:59');
INSERT INTO `st_menu` VALUES (5, '1', '系统管理', '部门管理', 'upmsDepartment', NULL, 'department', NULL, 4, 'M', NULL, '1', NULL, '2020-09-09 23:38:40', NULL, '2020-09-09 23:51:06');
INSERT INTO `st_menu` VALUES (6, '1', '系统管理', '职位管理', 'upmsPosition', NULL, 'position', NULL, 5, 'M', NULL, '1', NULL, '2020-09-09 23:39:33', NULL, '2020-09-09 23:51:10');
INSERT INTO `st_menu` VALUES (7, '1', '系统管理', '数据字典', 'upmsDict', NULL, 'dict', NULL, 6, 'M', NULL, '1', NULL, '2020-09-09 23:43:58', NULL, '2020-09-09 23:57:02');
INSERT INTO `st_menu` VALUES (8, '1,2', '系统管理,用户管理', '查询用户-分页', 'upmsUserSearchPage', NULL, '/api/v1/user/{page:[0-9]+}/{size:[0-9]+}', 'GET', 1, 'B', NULL, '1', NULL, '2020-09-10 00:00:13', NULL, '2020-09-19 14:24:18');
INSERT INTO `st_menu` VALUES (9, '1,2', '系统管理,用户管理', '新增用户', 'upmsUserAdd', NULL, '/api/v1/user', 'POST', 2, 'B', NULL, '1', NULL, '2020-09-10 06:56:13', NULL, '2020-09-19 14:25:15');
INSERT INTO `st_menu` VALUES (10, '1,2', '系统管理,用户管理', '编辑用户', 'upmsUserEdit', NULL, '/api/v1/user/{id:[0-9]+}', 'PUT', 3, 'B', NULL, '1', NULL, '2020-09-10 07:21:59', NULL, '2020-09-19 14:25:19');
INSERT INTO `st_menu` VALUES (11, '1,5', '系统管理,部门管理', '新增部门', 'upmsDepartmentAdd', NULL, '/api/v1/department', NULL, 1, 'B', NULL, '1', NULL, '2020-09-19 01:05:58', NULL, '2020-09-19 01:05:58');
INSERT INTO `st_menu` VALUES (12, '1,5', '系统管理,部门管理', '编辑部门', 'upmsDepartmentEdit', NULL, '/api/v1/department/{id:[0-9]+}', NULL, 2, 'B', NULL, '1', NULL, '2020-09-19 01:06:25', NULL, '2020-09-19 01:06:25');
INSERT INTO `st_menu` VALUES (13, '1,5', '系统管理,部门管理', '删除部门', 'upmsDepartmentDel', NULL, '/api/v1/department/{id:[0-9]+}', NULL, 3, 'B', NULL, '1', NULL, '2020-09-19 01:06:42', NULL, '2020-09-19 01:06:42');
INSERT INTO `st_menu` VALUES (14, '1,5', '系统管理,部门管理', '分配部门职位', 'upmsDepartmentPostSave', NULL, '/api/v1/department/{id:[0-9]+}/positions', NULL, 5, 'B', NULL, '1', NULL, '2020-09-19 09:59:49', NULL, '2020-09-19 14:53:18');
INSERT INTO `st_menu` VALUES (15, '1,3', '系统管理,角色管理', '查询角色-分页', 'upmsRoleSearchPage', NULL, '/api/v1/role/{page:[0-9]+}/{size:[0-9]+}', 'GET', 1, 'B', NULL, '1', NULL, '2020-09-19 14:26:21', NULL, '2020-09-19 14:26:21');
INSERT INTO `st_menu` VALUES (16, '1,3', '系统管理,角色管理', '新增角色', 'upmsRoleAdd', NULL, '/api/v1/role', 'POST', 2, 'B', NULL, '1', NULL, '2020-09-19 14:29:30', NULL, '2020-09-19 14:29:30');
INSERT INTO `st_menu` VALUES (17, '1,3', '系统管理,角色管理', '编辑角色', 'upmsRoleEdit', NULL, '/api/v1/role/{id:[0-9]+}', 'PUT', 4, 'B', NULL, '1', NULL, '2020-09-19 14:30:34', NULL, '2020-09-19 14:30:34');
INSERT INTO `st_menu` VALUES (18, '1,2', '系统管理,用户管理', '分配角色', 'upmsUserRolesSave', NULL, '/api/v1/user/{id:[0-9]+}/roles', 'PUT', 5, 'B', NULL, '1', NULL, '2020-09-19 14:40:15', NULL, '2020-09-19 14:40:15');
INSERT INTO `st_menu` VALUES (19, '1,2', '系统管理,用户管理', '获取角色', 'upmsUserRolesGet', NULL, '/api/v1/user/{id:[0-9]+}/roles', 'GET', 4, 'B', NULL, '1', NULL, '2020-09-19 14:40:58', NULL, '2020-09-19 14:40:58');
INSERT INTO `st_menu` VALUES (20, '1,3', '系统管理,角色管理', '查询分配权限', 'upmsRolePermissionsGet', NULL, '/api/v1/role/{id:[0-9]+}/permissions', 'GET', 5, 'B', NULL, '1', NULL, '2020-09-19 14:41:47', NULL, '2020-09-19 14:42:25');
INSERT INTO `st_menu` VALUES (21, '1,3', '系统管理,角色管理', '分配权限', 'upmsRolePermissionsSave', NULL, '/api/v1/role/{id:[0-9]+}/permissions', 'PUT', 6, 'B', NULL, '1', NULL, '2020-09-19 14:42:07', NULL, '2020-09-19 14:42:07');
INSERT INTO `st_menu` VALUES (22, '1,4', '系统管理,权限管理', '查询权限-分页', 'upmsPermissionSearchPage', NULL, '/api/v1/permission/{page:[0-9]+}/{size:[0-9]+}', 'GET', 1, 'B', NULL, '1', NULL, '2020-09-19 14:46:27', NULL, '2020-09-19 14:46:27');
INSERT INTO `st_menu` VALUES (23, '1,4', '系统管理,权限管理', '新增权限', 'upmsPermissionAdd', NULL, '/api/v1/permission', 'POST', 2, 'B', NULL, '1', NULL, '2020-09-19 14:47:43', NULL, '2020-09-19 14:47:43');
INSERT INTO `st_menu` VALUES (24, '1,4', '系统管理,权限管理', '编辑权限', 'upmsPermissionEdit', NULL, '/api/v1/permission/{id:[0-9]+}', 'PUT', 3, 'B', NULL, '1', NULL, '2020-09-19 14:48:11', NULL, '2020-09-19 14:48:26');
INSERT INTO `st_menu` VALUES (25, '1,5', '系统管理,部门管理', '获取部门职位', 'upmsDepartmentPostGet', NULL, '/api/v1/department/{id:[0-9]+}/positions', 'GET', 4, 'B', NULL, '1', NULL, '2020-09-19 14:51:59', NULL, '2020-09-19 14:53:09');
INSERT INTO `st_menu` VALUES (26, '1,5', '系统管理,部门管理', '获取组织树', 'upmsDepartmentTreeGet', NULL, '/api/v1/department/t2/{type}', 'GET', 11, 'B', NULL, '1', NULL, '2020-09-19 14:57:17', NULL, '2020-09-19 14:57:44');
INSERT INTO `st_menu` VALUES (27, '1,6', '系统管理,职位管理', '查询职位-分页', 'upmsPositionSearchPage', NULL, '/api/v1/position/{page:[0-9]+}/{size:[0-9]+}', 'GET', 1, 'B', NULL, '1', NULL, '2020-09-19 14:58:38', NULL, '2020-09-19 14:58:38');
INSERT INTO `st_menu` VALUES (28, '1,6', '系统管理,职位管理', '新增职位', 'upmsPositionAdd', NULL, '/api/v1/position', 'POST', 2, 'B', NULL, '1', NULL, '2020-09-19 14:59:12', NULL, '2020-09-19 14:59:12');
INSERT INTO `st_menu` VALUES (29, '1,6', '系统管理,职位管理', '编辑职位', 'upmsPositionEdit', NULL, '/api/v1/position/{id:[0-9]+}', 'PUT', 5, 'B', NULL, '1', NULL, '2020-09-19 14:59:41', NULL, '2020-09-19 14:59:41');

-- ----------------------------
-- Table structure for st_permission
-- ----------------------------
DROP TABLE IF EXISTS `st_permission`;
CREATE TABLE `st_permission`  (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `TYPE` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '类型(1.目录 2.菜单页 3.按钮)',
  `NAME` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '名称',
  `CODE` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '代码',
  `URL` varchar(150) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '链接地址',
  `REDIRECT` varchar(150) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '转发地址',
  `ICON` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图标',
  `SORT` int(4) NOT NULL DEFAULT 0 COMMENT '菜单顺序',
  `hidden` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否隐藏(0.否 1.是)',
  `IS_SYS` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否为系统权限(0.否 1.是)',
  `PARENT_ID` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '上级菜单ID',
  `PARENT_NAME` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `METHOD` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '请求方法',
  `STATUS` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '1' COMMENT '状态: 0.禁用 1.启用',
  `deleted` tinyint(1) NULL DEFAULT 0 COMMENT '删除标志（0代表存在 1代表删除）',
  `CREATE_BY` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `CREATE_AT` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `UPDATE_BY` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `UPDATE_AT` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`ID`) USING BTREE,
  UNIQUE INDEX `UK_ST_P_CODE`(`CODE`) USING BTREE,
  INDEX `IDX_ST_P_URL`(`URL`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 342 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '后台权限表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of st_permission
-- ----------------------------
INSERT INTO `st_permission` VALUES (1, '1', '系统管理', 'Upms', 'upms', '/upms/user', 'user', 1, 0, 1, '0', NULL, NULL, '1', 0, NULL, '2018-07-28 01:35:33', 'admin', '2020-10-01 14:37:26');
INSERT INTO `st_permission` VALUES (2, '2', '用户管理', 'UpmsUser', 'user', NULL, NULL, 1, 0, 1, '1', '系统管理', NULL, '1', 0, NULL, '2018-07-28 01:35:33', NULL, NULL);
INSERT INTO `st_permission` VALUES (3, '2', '角色管理', 'UpmsRole', 'role', NULL, NULL, 2, 0, 1, '1', '系统管理', NULL, '1', 0, NULL, '2018-07-28 01:35:33', 'admin', '2020-10-01 14:41:55');
INSERT INTO `st_permission` VALUES (4, '2', '权限管理', 'UpmsPermission', 'permission', NULL, NULL, 3, 0, 1, '1', '系统管理', NULL, '1', 0, NULL, '2018-07-28 01:35:33', 'admin', '2020-10-01 14:41:41');
INSERT INTO `st_permission` VALUES (5, '3', '分页查询用户', 'UPMS_USER_PAGE_SEARCH', '/upms/user/{page:[0-9]+}/{size:[0-9]+}', NULL, NULL, 0, 1, 1, '1,2', '用户管理', 'GET', '1', 0, NULL, '2018-07-28 01:35:33', 'admin', '2020-07-04 11:54:08');
INSERT INTO `st_permission` VALUES (6, '3', '添加用户', 'UPMS_USER_ADD', '/upms/user', NULL, NULL, 1, 1, 1, '1,2', '用户管理', 'POST', '1', 0, NULL, '2018-07-28 01:35:33', 'admin', '2020-07-04 11:54:21');
INSERT INTO `st_permission` VALUES (7, '3', '修改用户', 'UPMS_USER_EDIT', '/upms/user/{id:[0-9]+}', NULL, NULL, 2, 1, 1, '1,2', '用户管理', 'PUT', '1', 0, NULL, '2018-07-28 01:35:33', 'admin', '2020-07-04 11:54:32');
INSERT INTO `st_permission` VALUES (8, '3', '查询用户详细', 'UPMS_USER_DETAIL', '/upms/user/{id:[0-9]+}', NULL, NULL, 3, 1, 1, '1,2', '用户管理', 'GET', '1', 0, NULL, '2018-07-28 01:35:33', 'admin', '2020-07-04 11:55:10');
INSERT INTO `st_permission` VALUES (9, '3', '删除用户', 'UPMS_USER_DELETE', '/upms/user/{id:[0-9]+}', NULL, NULL, 4, 1, 1, '1,2', '用户管理', 'DELETE', '1', 0, NULL, '2018-07-28 01:35:33', 'admin', '2020-07-04 11:54:55');
INSERT INTO `st_permission` VALUES (10, '3', '用户角色授权', 'UPMS_USER_ROLE', '/upms/user/{id:[0-9]+}/roles', NULL, NULL, 5, 1, 1, '1,2', '用户管理', '', '1', 0, NULL, '2018-07-28 01:35:33', NULL, NULL);
INSERT INTO `st_permission` VALUES (11, '3', '分页查询角色', 'UPMS_ROLE_PAGE_SEARCH', '/upms/role/{page:[0-9]+}/{size:[0-9]+}', NULL, NULL, 0, 1, 1, '1,3', '角色管理', 'GET', '1', 0, NULL, '2018-07-28 01:35:33', 'admin', '2020-07-04 11:58:02');
INSERT INTO `st_permission` VALUES (12, '3', '添加角色', 'UPMS_ROLE_ADD', '/upms/role', NULL, NULL, 1, 1, 1, '1,3', '角色管理', 'POST', '1', 0, NULL, '2018-07-28 01:35:33', 'admin', '2020-07-04 11:57:36');
INSERT INTO `st_permission` VALUES (13, '3', '修改角色', 'UPMS_ROLE_EDIT', '/upms/role/{id:[0-9]+}', NULL, NULL, 2, 1, 1, '1,3', '角色管理', 'PUT', '1', 0, NULL, '2018-07-28 01:35:33', 'admin', '2020-07-04 11:57:29');
INSERT INTO `st_permission` VALUES (14, '3', '查询角色详细', 'UPMS_ROLE_DETAIL', '/upms/role/{id:[0-9]+}', NULL, NULL, 3, 1, 1, '1,3', '角色管理', 'GET', '1', 0, NULL, '2018-07-28 01:35:33', 'admin', '2020-07-04 11:58:25');
INSERT INTO `st_permission` VALUES (15, '3', '删除角色', 'UPMS_ROLE_DELETE', '/upms/role/{id:[0-9]+}', NULL, NULL, 4, 1, 1, '1,3', '角色管理', 'DELETE', '1', 0, NULL, '2018-07-28 01:35:33', 'admin', '2020-07-04 11:57:20');
INSERT INTO `st_permission` VALUES (16, '3', '分配角色权限', 'UPMS_ROLE_PERMISSION', '/upms/role/{id:[0-9]+}/permissions', NULL, NULL, 5, 1, 1, '1,3', '角色管理', NULL, '1', 0, NULL, '2018-07-28 01:35:33', 'admin', '2020-07-04 11:57:05');
INSERT INTO `st_permission` VALUES (17, '3', '分页查询权限', 'UPMS_PERMISSION_PAGE_SEARCH', '/upms/permission/{page:[0-9]+}/{size:[0-9]+}', NULL, NULL, 0, 1, 1, '1,4', '权限管理', 'GET', '1', 0, NULL, '2018-07-28 01:35:33', 'admin', '2020-07-04 11:58:43');
INSERT INTO `st_permission` VALUES (18, '3', '添加权限', 'UPMS_PERMISSION_ADD', '/upms/permission', NULL, NULL, 1, 1, 1, '1,4', '权限管理', 'POST', '1', 0, NULL, '2018-07-28 01:35:33', 'admin', '2020-07-04 11:58:52');
INSERT INTO `st_permission` VALUES (19, '3', '修改权限', 'UPMS_PERMISSION_EDIT', '/upms/permission/{id:[0-9]+}', NULL, NULL, 2, 1, 1, '1,4', '权限管理', 'PUT', '1', 0, NULL, '2018-07-28 01:35:33', 'admin', '2020-07-04 11:59:00');
INSERT INTO `st_permission` VALUES (20, '3', '查询权限详细', 'UPMS_PERMISSION_DETAIL', '/upms/permission/{id:[0-9]+}', NULL, NULL, 3, 1, 1, '1,4', '权限管理', 'GET', '1', 0, NULL, '2018-07-28 01:35:33', 'admin', '2020-07-04 11:59:37');
INSERT INTO `st_permission` VALUES (21, '3', '删除权限', 'UPMS_PERMISSION_DELETE', '/upms/permission/{id:[0-9]+}', NULL, NULL, 4, 1, 1, '1,4', '权限管理', 'DELETE', '1', 0, NULL, '2018-07-28 01:35:33', 'admin', '2020-07-04 11:59:09');
INSERT INTO `st_permission` VALUES (22, '2', '分类管理', 'WikiCategory', 'category', NULL, NULL, 11, 1, 0, '36,201', '基础管理', '业务系统', '1', 0, 'admin', '2017-03-17 23:18:37', 'admin', '2020-04-04 12:23:54');
INSERT INTO `st_permission` VALUES (23, '2', '数据字典', 'SYS_PROJECT_DICT', 'dict', NULL, NULL, 8, 1, 0, '119,130', '业务系统管理', '数据字典', '1', 0, 'admin', '2017-03-17 23:35:34', 'admin', '2019-12-05 14:24:47');
INSERT INTO `st_permission` VALUES (24, '1', '日志管理', 'Logs', 'logs', '/logs/login', 'log', 2, 1, 0, '0', NULL, '', '1', 0, 'admin', '2018-07-28 11:06:11', 'test', '2020-06-30 23:55:03');
INSERT INTO `st_permission` VALUES (25, '2', '实例管理', 'CanalInstance', 'instance', NULL, NULL, 3, 0, 0, '94', 'Canal管理', '', '1', 0, 'admin', '2018-07-28 11:34:35', 'admin', '2020-10-03 05:13:01');
INSERT INTO `st_permission` VALUES (26, '2', '项目代码', 'SYS_PROJECT_CODE', 'code', NULL, NULL, 1, 1, 0, '119,130', '业务系统管理', '', '1', 0, 'admin', '2018-07-28 12:20:55', 'admin', '2019-11-10 20:25:00');
INSERT INTO `st_permission` VALUES (27, '3', '添加', 'CANAL_CLUSTERS_ADD', '/canal/{env:[a-zA-Z_0-9]+}/cluster', NULL, NULL, 2, 1, 0, '94,29', '集群管理', 'POST', '1', 0, 'admin', '2018-08-04 21:12:44', 'admin', '2020-06-06 08:28:05');
INSERT INTO `st_permission` VALUES (28, '2', '操作日志', 'LogsOperate', 'operate', NULL, NULL, 2, 1, 0, '24', '日志管理', '123', '1', 0, 'admin', '2018-08-05 16:27:18', 'admin', '2020-03-20 18:03:31');
INSERT INTO `st_permission` VALUES (29, '2', '集群管理', 'CanalCluster', 'cluster', NULL, NULL, 1, 0, 0, '94', 'Canal管理', '', '1', 0, 'admin', '2018-08-06 10:34:32', 'admin', '2020-10-03 05:12:44');
INSERT INTO `st_permission` VALUES (30, '2', '服务管理', 'CanalServer', 'server', NULL, NULL, 2, 0, 0, '94', 'Canal管理', '', '1', 0, 'admin', '2018-08-06 10:36:52', 'admin', '2020-10-03 05:12:54');
INSERT INTO `st_permission` VALUES (31, '3', '角色应用分配', 'UPMS_ROLE_SYS', '/role/sys', NULL, NULL, 6, 1, 0, '1,3', '角色管理', '', '0', 0, 'admin', '2018-08-06 11:04:48', NULL, NULL);
INSERT INTO `st_permission` VALUES (32, '2', '项目版本', 'SYS_PROJECT_VERSION', 'version', NULL, NULL, 2, 1, 0, '119,130', '业务系统管理', '', '1', 0, 'admin', '2018-08-06 15:31:14', 'admin', '2019-11-10 20:25:16');
INSERT INTO `st_permission` VALUES (33, '2', '数据源', 'IaaSDataSource', 'datasource', NULL, NULL, 3, 0, 0, '119,337', 'IaaS管理', '', '1', 0, 'admin', '2018-08-06 15:31:55', 'admin', '2021-01-30 05:33:18');
INSERT INTO `st_permission` VALUES (34, '2', 'SQL脚本', 'SYS_PROJECT_SQL', 'sql', NULL, NULL, 3, 1, 0, '119,130', '业务系统管理', '1', '1', 0, 'admin', '2018-08-06 15:32:41', 'admin', '2019-11-10 16:05:18');
INSERT INTO `st_permission` VALUES (35, '3', '初始化用户密码', 'UPMS_USER_PASSWORD_INIT', '/upms/user/{id:[0-9]+}/initPwd', NULL, NULL, 6, 1, 0, '1,2', '用户管理', 'POST', '1', 0, 'admin', '2018-08-10 17:59:54', 'admin', '2020-07-04 11:56:31');
INSERT INTO `st_permission` VALUES (36, '1', '极客管理', 'Wiki', 'wiki', '/wiki/base/user', 'education', 3, 1, 0, '0', NULL, '', '1', 0, 'admin', '2018-08-11 10:52:15', 'test', '2020-06-30 23:54:42');
INSERT INTO `st_permission` VALUES (37, '2', '添加书籍章节', 'WikiBookChapterAdd', 'bookChapterAdd', '/wiki/books/chapter', NULL, 112, 1, 0, '36,203', '运营管理', 'GET', '1', 0, 'admin', '2018-08-11 10:53:26', 'admin', '2020-10-02 15:20:38');
INSERT INTO `st_permission` VALUES (38, '3', '添加', 'SYS_PROJECT_CODE_ADD', '/sys/project/code', NULL, NULL, 2, 1, 0, '119,130,26', '系统代码', 'POST', '1', 0, 'admin', '2018-08-11 10:55:42', 'admin', '2019-11-05 11:11:42');
INSERT INTO `st_permission` VALUES (39, '3', '分页查询', 'SYS_PROJECT_CODE_PAGE_SEARCH', '/sys/project/code/{page:[0-9]+}/{size:[0-9]+}', NULL, NULL, 1, 0, 0, '119,130,26', '系统代码', 'GET', '1', 0, 'admin', '2018-08-11 11:09:22', 'admin', '2019-11-05 11:10:36');
INSERT INTO `st_permission` VALUES (40, '3', '修改', 'SYS_PROJECT_CODE_EDIT', '/sys/project/code/{id:[0-9]+}', NULL, NULL, 3, 1, 0, '119,130,26', '系统代码', 'PUT', '1', 0, 'admin', '2018-08-11 11:11:20', 'admin', '2019-11-05 11:11:02');
INSERT INTO `st_permission` VALUES (41, '3', '删除', 'SYS_PROJECT_CODE_DEL', '/sys/project/code/{id:[0-9]+}', NULL, NULL, 4, 1, 0, '119,130,26', '系统代码', 'DELETE', '1', 0, 'admin', '2018-08-11 11:11:47', 'admin', '2019-11-05 11:11:26');
INSERT INTO `st_permission` VALUES (42, '3', '添加', 'SYS_PROJECT_VERSION_ADD', '/sys/project/version', NULL, NULL, 2, 1, 0, '119,130,32', '系统版本', 'POST', '1', 0, 'admin', '2018-08-11 13:54:27', 'admin', '2019-11-05 11:13:38');
INSERT INTO `st_permission` VALUES (43, '3', '删除', 'SYS_PROJECT_VERSION_DEL', '/sys/project/version/{id:[0-9]+}', NULL, NULL, 4, 1, 0, '119,130,32', '系统版本', 'DELETE', '1', 0, 'admin', '2018-08-11 13:55:24', 'admin', '2019-11-05 11:13:27');
INSERT INTO `st_permission` VALUES (44, '3', '修改', 'SYS_PROJECT_VERSION_EDIT', '/sys/project/version/{id:[0-9]+}', NULL, NULL, 3, 1, 0, '119,130,32', '系统版本', 'PUT', '1', 0, 'admin', '2018-08-11 13:55:40', 'admin', '2019-11-05 11:13:13');
INSERT INTO `st_permission` VALUES (45, '3', '分页查询', 'SYS_PROJECT_VERSION_PAGE_SEARCH', '/sys/project/version/{page:[0-9]+}/{size:[0-9]+}', NULL, NULL, 1, 0, 0, '119,130,32', '系统版本', 'GET', '1', 0, 'admin', '2018-08-11 13:56:02', 'admin', '2019-11-05 11:12:48');
INSERT INTO `st_permission` VALUES (46, '3', '添加', 'SYS_DATASOURCE_ADD', '/sys/app/datasource', NULL, NULL, 2, 1, 0, '119,337,33', '数据源', 'POST', '1', 0, 'admin', '2018-08-11 13:57:30', 'admin', '2021-01-30 05:33:44');
INSERT INTO `st_permission` VALUES (47, '3', '删除', 'SYS_DATASOURCE_DEL', '/sys/datasource/{id:[0-9]+}', NULL, NULL, 4, 1, 0, '119,337,33', '数据源', 'DELETE', '1', 0, 'admin', '2018-08-11 13:57:55', 'admin', '2021-01-30 05:34:24');
INSERT INTO `st_permission` VALUES (48, '3', '修改', 'SYS_DATASOURCE_EDIT', '/sys/app/datasource/{id:[0-9]+}', NULL, NULL, 3, 1, 0, '119,337,33', '数据源', 'PUT', '1', 0, 'admin', '2018-08-11 13:58:18', 'admin', '2021-01-30 05:34:04');
INSERT INTO `st_permission` VALUES (49, '3', '查询', 'SYS_DATASOURCE_SEARCH', '/sys/app/datasource/{page:[0-9]+}/{size:[0-9]+}', NULL, NULL, 1, 0, 0, '119,337,33', '数据源', 'GET', '1', 0, 'admin', '2018-08-11 13:58:44', 'admin', '2021-01-30 05:33:13');
INSERT INTO `st_permission` VALUES (50, '3', '添加', 'SYS_PROJECT_SQL_ADD', '/sys/project/sql', NULL, NULL, 2, 1, 0, '119,130,34', 'SQL脚本', 'POST', '1', 0, 'admin', '2018-08-11 13:59:23', 'admin', '2019-11-06 10:14:44');
INSERT INTO `st_permission` VALUES (51, '3', '删除', 'SYS_PROJECT_SQL_DEL', '/sys/project/sql/{id:[0-9]+}', NULL, NULL, 4, 1, 0, '119,130,34', 'SQL脚本', 'DELETE', '1', 0, 'admin', '2018-08-11 13:59:43', 'admin', '2019-11-06 10:15:05');
INSERT INTO `st_permission` VALUES (52, '3', '修改', 'SYS_PROJECT_SQL_EDIT', '/sys/project/sql/{id:[0-9]+}', NULL, NULL, 3, 1, 0, '119,130,34', 'SQL脚本', 'PUT', '1', 0, 'admin', '2018-08-11 14:00:31', 'admin', '2019-11-06 10:14:53');
INSERT INTO `st_permission` VALUES (53, '3', '分页查询', 'SYS_PROJECT_SQL_PAGE_SEARCH', '/sys/project/sql/{page:[0-9]+}/{size:[0-9]+}', NULL, NULL, 1, 0, 0, '119,130,34', 'SQL脚本', 'GET', '1', 0, 'admin', '2018-08-11 14:00:45', 'admin', '2019-11-06 10:15:44');
INSERT INTO `st_permission` VALUES (54, '3', '导出', 'SYS_PROJECT_SQL_EXPORT', '/sys/sql/script/export', NULL, NULL, 4, 0, 0, '119,130,34', 'SQL脚本', '', '0', 0, 'admin', '2018-08-11 14:02:43', 'admin', '2019-11-27 15:05:42');
INSERT INTO `st_permission` VALUES (55, '3', '添加', 'SYS_DICT_ADD', '/sys/dict/add', NULL, NULL, 1, 1, 0, '119,130,23', '数据字典', '', '1', 0, 'admin', '2018-08-11 14:03:28', 'admin', '2019-12-05 14:24:47');
INSERT INTO `st_permission` VALUES (56, '3', '删除', 'SYS_DICT_DEL', '/sys/dict/delete', NULL, NULL, 3, 1, 0, '119,130,23', '数据字典', '', '1', 0, 'admin', '2018-08-11 14:04:40', 'admin', '2019-12-05 14:24:47');
INSERT INTO `st_permission` VALUES (57, '3', '修改', 'SYS_DICT_EDIT', '/sys/dict/edit', NULL, NULL, 2, 1, 0, '119,130,23', '数据字典', '', '1', 0, 'admin', '2018-08-11 14:04:54', 'admin', '2019-12-05 14:24:47');
INSERT INTO `st_permission` VALUES (58, '3', '查询', 'SYS_DICT_LIST', '/sys/dict/list', NULL, NULL, 0, 0, 0, '119,130,23', '数据字典', '', '1', 0, 'admin', '2018-08-11 14:05:04', 'admin', '2019-12-05 14:24:47');
INSERT INTO `st_permission` VALUES (59, '3', '分页查询操作日志', 'LOGS_OPERATE_PAGE_SEARCH', '/upms/op/record/{page:[0-9]+}/{size:[0-9]+}', NULL, NULL, 1, 0, 0, '24,28', '操作日志', 'GET', '1', 0, 'admin', '2018-08-11 14:06:56', 'admin', '2020-03-21 17:45:49');
INSERT INTO `st_permission` VALUES (60, '3', '执行', 'SYS_PROJECT_SQL_RUN', '/sys/sql/script/run', NULL, NULL, 5, 1, 0, '119,130,34', 'SQL脚本', '', '0', 0, 'admin', '2018-08-11 14:47:23', 'admin', '2019-11-27 15:05:46');
INSERT INTO `st_permission` VALUES (61, '3', '详细', 'SYS_PROJECT_SQL_DETAIL', '/sys/project/sql/{id:[0-9]+}', NULL, NULL, 5, 1, 0, '119,130,34', 'SQL脚本', 'GET', '0', 0, 'admin', '2018-08-13 11:42:03', 'admin', '2019-11-27 15:05:51');
INSERT INTO `st_permission` VALUES (62, '2', '主机服务', 'IaaSHost', 'host', NULL, NULL, 1, 0, 0, '119,337', 'IaaS管理', '', '1', 0, 'admin', '2018-08-15 09:53:39', 'admin', '2021-01-30 02:35:59');
INSERT INTO `st_permission` VALUES (63, '3', '查询', 'SYS_HOST_SEARCH', '/sys/app/host/{page:[0-9]+}/{size:[0-9]+}', NULL, NULL, 1, 0, 0, '119,337,62', '主机服务', 'GET', '1', 0, 'admin', '2018-08-15 09:54:44', 'admin', '2021-01-30 05:24:21');
INSERT INTO `st_permission` VALUES (64, '4', '弹出框', 'SYS_HOST_WIN', '/sys/host/win', NULL, NULL, 100, 0, 0, '119,337,62', '主机服务', '', '1', 0, 'admin', '2018-08-15 10:46:58', 'admin', '2021-01-30 02:27:15');
INSERT INTO `st_permission` VALUES (65, '5', '用户实例节点查询', 'SYS_APP_INSTANCE_USER_NODES', '/sys/app/instance/{id:[0-9]+}/user/nodes', NULL, NULL, 101, 0, 0, '119,131,71', '数据源', 'GET', '1', 0, 'admin', '2018-08-15 18:09:59', 'admin', '2019-11-14 21:03:06');
INSERT INTO `st_permission` VALUES (66, '3', '添加书箱', 'WIKI_BOOKS_ADD', '/wiki/admin/books', NULL, NULL, 2, 1, 0, '36,203,157', '书籍管理', 'POST', '1', 0, 'admin', '2018-08-16 19:32:31', 'test', '2020-06-29 15:24:07');
INSERT INTO `st_permission` VALUES (67, '1', '任务管理', 'JOB', 'task', NULL, 'example', 7, 1, 0, '0', NULL, '', '0', 0, 'admin', '2018-08-17 14:56:41', 'test', '2020-06-29 13:43:13');
INSERT INTO `st_permission` VALUES (68, '2', '定时任务', 'JOB_TASK', '/job/task', NULL, NULL, 1, 1, 0, '67', '任务管理', '', '1', 0, 'admin', '2018-08-17 14:57:38', NULL, NULL);
INSERT INTO `st_permission` VALUES (69, '3', '添加', 'JOB_TASK_ADD', '/job/task/add', NULL, NULL, 1, 1, 0, '67,68', '定时任务', '', '1', 0, 'admin', '2018-08-17 14:58:18', NULL, NULL);
INSERT INTO `st_permission` VALUES (70, '3', '查询', 'JOB_TASK_SEARCH', '/job/task/list', NULL, NULL, 0, 1, 0, '67,68', '定时任务', '', '1', 0, 'admin', '2018-08-17 14:58:54', NULL, NULL);
INSERT INTO `st_permission` VALUES (71, '2', '应用实例', 'SYS_APP_INSTANCE', 'instance', NULL, NULL, 3, 1, 0, '119,131', NULL, '', '1', 0, 'admin', '2018-08-21 11:04:25', 'admin', '2019-11-12 14:16:33');
INSERT INTO `st_permission` VALUES (72, '3', '分页查询', 'SYS_APP_INSTANCE_PAGE_SEARCH', '/sys/app/instance/{page:[0-9]+}/{size:[0-9]+}', NULL, NULL, 1, 0, 0, '119,131,71', NULL, 'GET', '1', 0, 'admin', '2018-08-21 11:04:50', 'admin', '2019-11-12 20:23:37');
INSERT INTO `st_permission` VALUES (73, '3', '添加', 'SYS_APP_INSTANCE_ADD', '/sys/app/instance', NULL, NULL, 2, 1, 0, '119,131,71', NULL, 'POST', '1', 0, 'admin', '2018-08-21 11:05:28', 'admin', '2019-11-12 20:23:33');
INSERT INTO `st_permission` VALUES (74, '3', '修改', 'SYS_APP_INSTANCE_EDIT', '/sys/app/instance/{id:[0-9]+}', NULL, NULL, 3, 1, 0, '119,131,71', NULL, 'PUT', '1', 0, 'admin', '2018-08-21 11:05:38', 'admin', '2019-11-12 20:23:54');
INSERT INTO `st_permission` VALUES (75, '3', '删除', 'SYS_APP_INSTANCE_DEL', '/sys/app/instance/{id:[0-9]+}', NULL, NULL, 4, 1, 0, '119,131,71', NULL, 'DELETE', '1', 0, 'admin', '2018-08-21 11:05:59', 'admin', '2019-11-14 20:37:19');
INSERT INTO `st_permission` VALUES (76, '3', 'Kafka搜索执行状态', 'KAFKA_CONTENT_SEARCH_STATUS', '/kafka/content/search/{sid}/status', NULL, NULL, 6, 1, 0, '103,109', NULL, 'GET', '1', 0, 'admin', '2018-08-21 11:06:24', 'admin', '2019-11-14 21:43:56');
INSERT INTO `st_permission` VALUES (77, '3', 'Kafka消息发送', 'KAFKA_CONTENT_SEND', '/kafka/content/send', NULL, NULL, 4, 1, 0, '103,109', NULL, 'POST', '1', 0, 'admin', '2018-08-21 11:07:05', 'admin', '2019-11-14 21:41:51');
INSERT INTO `st_permission` VALUES (78, '3', 'Kafka消息重发', 'KAFKA_CONTENT_RESEND', '/kafka/content/resend/{sid}', NULL, NULL, 5, 1, 0, '103,109', NULL, 'PUT', '1', 0, 'admin', '2018-08-21 11:07:26', 'admin', '2019-11-14 21:42:16');
INSERT INTO `st_permission` VALUES (79, '3', '添加', 'SYS_APP_HOST_ADD', '/sys/host/add', NULL, NULL, 1, 1, 0, '119,337,62', NULL, '', '1', 0, 'admin', '2018-08-22 10:57:48', 'admin', '2021-01-30 02:27:15');
INSERT INTO `st_permission` VALUES (80, '3', '修改', 'SYS_APP_HOST_EDIT', '/sys/host/edit', NULL, NULL, 2, 1, 0, '119,337,62', NULL, '', '1', 0, 'admin', '2018-08-22 10:58:10', 'admin', '2021-01-30 02:27:15');
INSERT INTO `st_permission` VALUES (81, '3', 'Kafka搜索记录', 'KAFKA_CONTENT_SEARCH_RECORDS', '/kafka/content/search/{sid}/records', NULL, NULL, 7, 0, 0, '103,109', NULL, 'GET', '1', 0, 'admin', '2018-08-22 11:12:46', 'admin', '2019-11-14 21:45:06');
INSERT INTO `st_permission` VALUES (82, '3', '发布', 'SYS_PROJECT_VERSION_RELEASE', '/sys/project/version/{id:[0-9]+}/release', NULL, NULL, 5, 1, 0, '119,130,32', NULL, 'POST', '1', 0, 'admin', '2018-08-23 17:49:55', 'admin', '2019-11-05 11:15:55');
INSERT INTO `st_permission` VALUES (83, '2', '应用日志', 'SYS_APP_LOGS', 'logs', NULL, NULL, 5, 1, 0, '119,131', NULL, '', '1', 0, 'admin', '2018-08-27 10:03:24', 'admin', '2019-11-12 14:16:46');
INSERT INTO `st_permission` VALUES (84, '3', '搜索', 'SYS_APP_LOGS_SEARCH', '/sys/app/logs/search', NULL, NULL, 1, 1, 0, '119,131,83', NULL, 'POST', '1', 0, 'admin', '2018-08-27 10:05:14', 'admin', '2019-11-15 14:45:31');
INSERT INTO `st_permission` VALUES (85, '3', '搜索上一页', 'SYS_APP_LOGS_SEARCH_PRE', '/sys/app/logs/search/pre', NULL, NULL, 2, 1, 0, '119,131,83', NULL, '', '0', 0, 'admin', '2018-08-27 15:57:20', 'admin', '2019-11-15 14:45:55');
INSERT INTO `st_permission` VALUES (86, '3', '搜索下一页', 'SYS_APP_LOGS_SEARCH_NEXT', '/sys/app/logs/search/next', NULL, NULL, 3, 1, 0, '119,131,83', NULL, '', '0', 0, 'admin', '2018-08-27 15:57:49', 'admin', '2019-11-15 14:46:06');
INSERT INTO `st_permission` VALUES (87, '3', '搜索明细', 'SYS_APP_LOGS_SEARCH_DETAIL', '/sys/app/logs/search/detail', NULL, NULL, 4, 1, 0, '119,131,83', NULL, 'GET', '0', 0, 'admin', '2018-08-27 17:13:35', 'admin', '2019-11-27 14:57:47');
INSERT INTO `st_permission` VALUES (88, '4', '执行日志', 'SYS_PROJECT_SQL_RUN_LOG', '/sys/sql/script/run/log', NULL, NULL, 6, 1, 0, '119,130,34', NULL, 'GET', '0', 0, 'admin', '2018-09-01 20:02:18', 'admin', '2019-11-27 15:05:56');
INSERT INTO `st_permission` VALUES (89, '1', '事务管理', 'TX', 'tx', NULL, 'table', 18, 1, 0, '0', NULL, '', '0', 0, 'admin', '2018-09-03 11:51:43', 'admin', '2020-07-11 08:30:17');
INSERT INTO `st_permission` VALUES (90, '2', '集群信息', 'TX_CLUSTER', '/tx/cluster', NULL, NULL, 1, 1, 0, '89', NULL, '', '1', 0, 'admin', '2018-09-03 11:53:10', 'admin', '2018-09-08 16:03:29');
INSERT INTO `st_permission` VALUES (91, '2', '服务列表', 'TX_SERVICE', '/tx/service', NULL, NULL, 2, 1, 0, '89', NULL, '', '1', 0, 'admin', '2018-09-05 16:36:05', 'admin', '2018-09-18 13:52:15');
INSERT INTO `st_permission` VALUES (92, '2', '事务补偿', 'TX_COMPENSATE', '/tx/compensate', NULL, NULL, 3, 1, 0, '89', NULL, '', '1', 0, 'admin', '2018-09-05 16:38:35', NULL, NULL);
INSERT INTO `st_permission` VALUES (93, '2', '事务策略', 'TX_STRATEGY', '/tx/strategy.html', NULL, NULL, 4, 1, 0, '89', NULL, '', '1', 0, 'admin', '2018-09-05 16:40:02', NULL, NULL);
INSERT INTO `st_permission` VALUES (94, '1', 'Canal管理', 'Canal', 'canal', 'canal/cluster', 'tree', 12, 1, 0, '0', NULL, '', '0', 0, 'admin', '2018-09-06 10:02:04', 'admin', '2021-01-16 03:17:18');
INSERT INTO `st_permission` VALUES (95, '3', '查询', 'CANAL_CLUSTERS_SEARCH', '/canal/{env:[a-zA-Z_0-9]+}/clusters', NULL, NULL, 1, 1, 0, '94,29', '集群管理', 'GET', '1', 0, 'admin', '2018-09-06 10:27:18', 'admin', '2020-06-02 13:54:18');
INSERT INTO `st_permission` VALUES (96, '2', '实时日志', 'LOGS:REALTIME', 'rt', NULL, NULL, 3, 1, 0, '24', '日志管理', '123', '0', 0, 'admin', '2018-09-06 11:54:26', 'admin', '2020-03-21 17:49:53');
INSERT INTO `st_permission` VALUES (97, '3', '查询', 'LOGS_REAL_TIME_LIST', '/log/real-time/list', NULL, NULL, 0, 1, 0, '24,96', NULL, '', '1', 0, 'admin', '2018-09-06 11:57:18', NULL, NULL);
INSERT INTO `st_permission` VALUES (98, '4', '事务', 'TX_CLUSTER_TRANSACTION', '/tx/cluster/transaction/*', NULL, NULL, 2, 0, 0, '89,90', NULL, '', '1', 0, 'admin', '2018-09-11 09:58:04', 'admin', '2018-09-11 10:03:02');
INSERT INTO `st_permission` VALUES (99, '2', '表字段备注', 'DB_TABLE_COLUMN', 'tableColumn', NULL, NULL, 8, 1, 0, '119,131', NULL, '', '0', 0, 'admin', '2018-09-15 16:07:25', 'admin', '2019-11-12 14:15:58');
INSERT INTO `st_permission` VALUES (100, '3', '查询', 'DB_TABLE_COLUMN_LIST', '/table/column/list', NULL, NULL, 0, 0, 0, '119,131,99', NULL, '', '1', 0, 'admin', '2018-09-15 16:08:24', 'admin', '2019-11-04 21:39:05');
INSERT INTO `st_permission` VALUES (101, '3', '添加', 'DB_TABLE_COLUMN_ADD', '/table/column/add', NULL, NULL, 1, 1, 0, '119,131,99', NULL, '', '1', 0, 'admin', '2018-09-15 16:08:41', 'admin', '2019-11-04 21:39:05');
INSERT INTO `st_permission` VALUES (102, '3', '修改', 'DB_TABLE_COLUMN_EDIT', '/table/column/edit', NULL, NULL, 2, 1, 0, '119,131,99', NULL, '', '1', 0, 'admin', '2018-09-15 16:08:50', 'admin', '2019-11-04 21:39:05');
INSERT INTO `st_permission` VALUES (103, '1', 'Kafka管理', 'Kafka', 'kafka', '/kafka/content', 'list', 14, 1, 0, '0', NULL, '', '1', 0, 'admin', '2018-09-26 11:04:21', 'admin', '2020-10-11 05:41:50');
INSERT INTO `st_permission` VALUES (104, '2', '集群管理', 'KafkaCluster', 'cluster', NULL, NULL, 1, 0, 0, '103', NULL, '', '1', 0, 'admin', '2018-09-26 11:06:16', 'admin', '2018-09-26 11:11:46');
INSERT INTO `st_permission` VALUES (105, '3', '分页查询Kafka集群', 'KAFKA_CLUSTER_PAGE_SEARCH', '/kafka/cluster/{page:[0-9]+}/{size:[0-9]+}', NULL, NULL, 1, 0, 0, '103,104', NULL, '', '1', 0, 'admin', '2018-09-26 11:06:46', 'admin', '2018-09-26 11:11:58');
INSERT INTO `st_permission` VALUES (106, '3', '添加', 'KAFKA_CLUSTER_ADD', '/kafka/cluster', NULL, NULL, 2, 1, 0, '103,104', NULL, '', '1', 0, 'admin', '2018-09-26 11:07:13', 'admin', '2018-09-26 11:12:06');
INSERT INTO `st_permission` VALUES (107, '3', '修改', 'KAFKA_CLUSTER_EDIT', '/kafka/cluster/{id:[0-9]+}', NULL, NULL, 3, 1, 0, '103,104', NULL, '', '1', 0, 'admin', '2018-09-26 11:07:33', 'admin', '2018-09-26 11:12:12');
INSERT INTO `st_permission` VALUES (108, '2', '主题管理', 'KafkaTopic', 'topic', NULL, NULL, 2, 0, 0, '103', 'Kafka管理', '', '1', 0, 'admin', '2018-09-26 11:08:39', 'admin', '2020-10-11 05:38:57');
INSERT INTO `st_permission` VALUES (109, '2', '消息搜索', 'KafkaContent', 'content', NULL, NULL, 3, 0, 0, '103', 'Kafka管理', '', '1', 0, 'admin', '2018-09-26 11:09:37', 'admin', '2020-10-11 05:39:22');
INSERT INTO `st_permission` VALUES (110, '3', '分页查询Kafka主题', 'KAFKA_TOPIC_PAGE_SEARCH', '/kafka/topic/{page:[0-9]+}/{size:[0-9]+}', NULL, NULL, 1, 0, 0, '103,108', '主题管理', 'GET', '1', 0, 'admin', '2018-09-26 11:12:32', 'admin', '2020-04-21 10:30:01');
INSERT INTO `st_permission` VALUES (111, '3', '添加', 'KAFKA_TOPIC_ADD', '/kafka/topic', NULL, NULL, 2, 1, 0, '103,108', NULL, '', '1', 0, 'admin', '2018-09-26 11:12:47', 'admin', '2018-09-26 11:13:29');
INSERT INTO `st_permission` VALUES (112, '3', '修改', 'KAFKA_TOPIC_EDIT', '/kafka/topic/{id:[0-9]+}', NULL, NULL, 3, 1, 0, '103,108', NULL, '', '1', 0, 'admin', '2018-09-26 11:13:02', 'admin', '2018-09-26 11:13:37');
INSERT INTO `st_permission` VALUES (113, '2', '文件记录', 'WikiResourcesRecord', 'uploadFile', NULL, NULL, 10, 0, 0, '36,202', '资源管理', '', '1', 0, 'admin', '2018-09-26 19:21:31', 'admin', '2020-10-02 15:38:55');
INSERT INTO `st_permission` VALUES (114, '4', 'Jar包弹出框', 'FILE_UPLOAD_JAR_WIN', '/sys/upload/file/jar', NULL, NULL, 10, 0, 0, '103,116', NULL, '', '1', 0, 'admin', '2018-09-27 10:49:00', 'test', '2020-06-29 13:39:29');
INSERT INTO `st_permission` VALUES (115, '2', 'Dubbo调用', 'DubboCall', 'DubboCall', NULL, NULL, 7, 0, 0, '103', 'Kafka管理', '', '1', 0, 'admin', '2018-09-27 19:29:37', 'admin', '2020-12-30 13:30:55');
INSERT INTO `st_permission` VALUES (116, '2', 'Jar包管理', 'KafkaFileJar', 'FileJar', NULL, NULL, 10, 1, 0, '103', 'KAFKA管理', '', '0', 0, 'admin', '2018-09-27 19:34:40', 'admin', '2020-07-20 23:59:49');
INSERT INTO `st_permission` VALUES (117, '3', '分页查询上传文件', 'WIKI_UPLOAD_FILE_PAGE_SEARCH', '/wiki/admin/upload/{page:[0-9]+}/{size:[0-9]+}', NULL, NULL, 1, 1, 0, '36,202,113', NULL, 'GET', '1', 0, 'admin', '2018-09-28 15:50:33', 'admin', '2020-04-04 12:23:05');
INSERT INTO `st_permission` VALUES (118, '3', '查询', 'FILE_JAR_LIST', '/sys/file/jar/list', NULL, NULL, 1, 1, 0, '103,116', NULL, '', '1', 0, 'admin', '2018-09-28 15:59:45', 'test', '2020-06-29 13:39:29');
INSERT INTO `st_permission` VALUES (119, '1', 'Cloud 管理', 'Cloud', 'cloud', '/sys/project/code', 'tree-table', 10, 1, 0, '0', NULL, '业务系统', '1', 0, 'admin', '2018-10-08 14:39:31', 'admin', '2021-01-30 02:34:46');
INSERT INTO `st_permission` VALUES (120, '3', '配置查询', 'SYS_INSTANCE_CONFIG', '/sys/instance/config', NULL, NULL, 1000, 1, 0, '119,131,71', NULL, '', '0', 0, 'admin', '2018-10-17 15:00:47', 'admin', '2019-11-14 20:34:30');
INSERT INTO `st_permission` VALUES (121, '3', '配置转换', 'SYS_INSTANCE_CONVERT', '/sys/instance/convert', NULL, NULL, 1000, 1, 0, '119,131,71', NULL, '', '0', 0, 'admin', '2018-10-17 19:55:40', 'admin', '2019-11-04 21:38:46');
INSERT INTO `st_permission` VALUES (122, '3', '事务补偿查询', 'TX_COMPENSATE_SEARCH', '/tx/compensate/search', NULL, NULL, 1, 1, 0, '89,92', NULL, '', '1', 0, 'admin', '2018-10-24 22:21:18', NULL, NULL);
INSERT INTO `st_permission` VALUES (123, '3', '事务补偿执行', 'TX_COMPENSATE_RUN', '/tx/compensate/run', NULL, NULL, 3, 1, 0, '89,92', NULL, '', '1', 0, 'admin', '2018-10-24 22:21:59', NULL, NULL);
INSERT INTO `st_permission` VALUES (124, '3', '事务补偿详细', 'TX_COMPENSATE_DETAIL', '/tx/compensate/detail', NULL, NULL, 2, 1, 0, '89,92', NULL, '', '1', 0, 'admin', '2018-10-24 22:22:35', NULL, NULL);
INSERT INTO `st_permission` VALUES (125, '3', '配置修改', 'SYS_INSTANCE_CONFIG_SAVE', '/sys/instance/config/save', NULL, NULL, 1001, 1, 0, '119,131,71', NULL, '', '0', 0, 'admin', '2018-11-10 20:30:10', 'admin', '2019-11-14 20:34:41');
INSERT INTO `st_permission` VALUES (126, '3', '升级文档', 'SYS_PROJECT_VERSION_DOC_LIST', '/sys/version/doc/list', NULL, NULL, 5, 1, 0, '119,130,32', NULL, '', '0', 0, 'admin', '2018-11-17 10:26:02', 'admin', '2019-11-27 15:05:04');
INSERT INTO `st_permission` VALUES (127, '5', '查询权限树（类型）', 'UPMS_PERMISSION_TREE', '/upms/permission/tree/{type:[0-9]+}', NULL, NULL, 100, 1, 0, '1,4', '权限管理', 'GET', '1', 0, NULL, '2019-10-28 14:38:45', 'admin', '2020-07-04 11:59:52');
INSERT INTO `st_permission` VALUES (128, '5', '查询用户授权角色', 'UPMS_USER_ROLES', '/upms/user/roles', NULL, NULL, 100, 1, 0, '1,2', '用户管理', 'GET', '1', 0, NULL, '2019-10-28 15:24:25', 'admin', '2020-07-04 11:55:26');
INSERT INTO `st_permission` VALUES (129, '2', '组织管理', 'UpmsDepartment', 'department', NULL, NULL, 4, 0, 1, '1', '系统管理', NULL, '1', 0, 'admin', '2019-11-01 08:50:55', 'admin', '2020-07-06 22:56:32');
INSERT INTO `st_permission` VALUES (130, '1', '项目管理', 'SYS_PROJECT', 'project', '/sys/project/code', 'list', 2, 1, 0, '119', 'Cloud 管理', NULL, '1', 0, 'admin', '2019-11-04 21:26:12', 'admin', '2021-01-30 02:25:36');
INSERT INTO `st_permission` VALUES (131, '1', '应用管理', 'SYS_APP', 'app', '/sys/app/instance', 'table', 3, 1, 0, '119', 'Cloud 管理', NULL, '1', 0, 'admin', '2019-11-04 21:38:24', 'admin', '2021-01-30 02:25:42');
INSERT INTO `st_permission` VALUES (132, '3', '项目树', 'SYS_PROJECT_CODE_TREE', '/sys/project/code/tree/*', NULL, NULL, 10, 1, 0, '119,130,26', '1', 'GET', '1', 0, 'admin', '2019-11-05 16:32:54', 'admin', '2019-11-12 21:54:10');
INSERT INTO `st_permission` VALUES (134, '3', '项目用户列表', 'SYS_PROJECT_CODE_USERS', '/sys/project/code/users', NULL, NULL, 7, 1, 0, '119,130,26', NULL, 'GET', '1', 0, 'admin', '2019-11-07 18:25:15', NULL, NULL);
INSERT INTO `st_permission` VALUES (135, '3', '查询项目用户组', 'SYS_PROJECT_CODE_USERS_GROUP', '/sys/project/code/{id:[0-9]+}/users', NULL, NULL, 5, 1, 0, '119,130,26', NULL, 'GET', '1', 0, 'admin', '2019-11-07 19:11:14', 'admin', '2019-11-07 19:12:55');
INSERT INTO `st_permission` VALUES (136, '3', '项目授权用户组', 'SYS_PROJECT_CODE_USERS_ASSIGN', '/sys/project/code/{id:[0-9]+}/users', NULL, NULL, 6, 1, 0, '119,130,26', NULL, 'POST', '1', 0, 'admin', '2019-11-07 19:12:32', NULL, NULL);
INSERT INTO `st_permission` VALUES (137, '3', 'Kafka集群查询', 'KAFKA_CONTENT_CLUSTER', '/kafka/content/clusters', NULL, NULL, 2, 1, 0, '103,109', NULL, 'GET', '1', 0, 'admin', '2019-11-10 21:06:50', 'admin', '2019-11-14 21:40:07');
INSERT INTO `st_permission` VALUES (138, '3', 'Kafka集群查询', 'KAFKA_TOPIC_CLUSTERS', '/kafka/topic/clusters', NULL, NULL, 5, 1, 0, '103,108', NULL, 'GET', '1', 0, 'admin', '2019-11-10 21:09:11', 'admin', '2019-11-10 21:09:31');
INSERT INTO `st_permission` VALUES (139, '3', 'Kafka主题查询', 'KAFKA_CONTENT_TOPICS', '/kafka/content/topics', NULL, NULL, 3, 1, 0, '103,109', NULL, 'GET', '1', 0, 'admin', '2019-11-10 21:12:35', 'admin', '2019-11-14 21:40:14');
INSERT INTO `st_permission` VALUES (142, '3', 'Kafka内容搜索', 'KAFKA_CONTENT_SEARCH', '/kafka/content/search', NULL, NULL, 1, 1, 0, '103,109', NULL, 'GET', '1', 0, 'admin', '2019-11-10 21:43:11', NULL, NULL);
INSERT INTO `st_permission` VALUES (143, '5', '用户项目树', 'SYS_PROJECT_CODE_USER_TREE', '/sys/project/code/user/tree', NULL, NULL, 100, 1, 0, '119,130,26', NULL, 'GET', '1', 0, 'admin', '2019-11-12 21:55:04', NULL, NULL);
INSERT INTO `st_permission` VALUES (144, '3', '实例节点查询', 'SYS_APP_INSTANCE_NODES', '/sys/app/instance/{id:[0-9]+}/nodes', NULL, NULL, 5, 1, 0, '119,131,71', NULL, 'GET', '1', 0, 'admin', '2019-11-14 11:26:12', 'admin', '2019-11-14 20:55:02');
INSERT INTO `st_permission` VALUES (145, '5', '用户实例查询', 'SYS_APP_INSTANCE_USER_SEARCH', '/sys/app/instance/user', NULL, NULL, 100, 1, 0, '119,131,71', NULL, 'GET', '1', 0, 'admin', '2019-11-14 15:09:32', NULL, NULL);
INSERT INTO `st_permission` VALUES (146, '3', '集群主题查询', 'KAFKA_TOPIC_TOPICS', '/kafka/topic/topics', NULL, NULL, 7, 1, 0, '103,108', NULL, 'GET', '1', 0, 'admin', '2019-11-19 15:06:15', 'admin', '2019-11-25 19:35:40');
INSERT INTO `st_permission` VALUES (147, '3', 'Kafka集群主题同步', 'KAFKA_TOPIC_SYNC', '/kafka/topic/sync', NULL, NULL, 6, 1, 0, '103,108', NULL, 'POST', '1', 0, 'admin', '2019-11-25 16:44:49', NULL, NULL);
INSERT INTO `st_permission` VALUES (148, '3', '删除', 'KAFKA_TOPIC_DELETE', '/kafka/topic/{id:[0-9]+}', NULL, NULL, 4, 1, 0, '103,108', NULL, 'DELETE', '1', 0, 'admin', '2019-11-26 10:02:41', NULL, NULL);
INSERT INTO `st_permission` VALUES (149, '3', '主题重建', 'KAFKA_TOPIC_REFRESH', '/kafka/topic/refresh', NULL, NULL, 8, 1, 0, '103,108', NULL, 'POST', '1', 0, 'admin', '2019-11-26 10:03:38', NULL, NULL);
INSERT INTO `st_permission` VALUES (150, '5', '修改用户密码', 'UPMS_USER_CHANGE_PASSWORD', '/upms/user/changePwd', NULL, NULL, 101, 1, 0, '1,2', '用户管理', 'GET', '1', 0, 'admin', '2019-11-26 14:54:02', 'admin', '2020-07-04 11:55:53');
INSERT INTO `st_permission` VALUES (151, '5', '修改用户默认角色', 'UPMS_USER_CHANGE_ROLE', '/upms/user/changeRole', NULL, NULL, 102, 1, 0, '1,2', '用户管理', 'GET', '1', 0, 'admin', '2019-11-26 14:55:13', 'admin', '2020-07-04 11:56:15');
INSERT INTO `st_permission` VALUES (152, '5', '修改用户个人信息', 'UPMS_USER_CHANGE_INFO', '/upms/user/changeInfo', NULL, NULL, 104, 1, 0, '1,2', '用户管理', 'GET', '1', 0, 'admin', '2019-11-26 14:58:33', 'admin', '2020-07-04 11:56:05');
INSERT INTO `st_permission` VALUES (153, '2', '广告管理', 'WikiAd', 'ad', NULL, NULL, 1, 1, 0, '36,203', '运营管理', NULL, '1', 0, 'admin', '2019-12-05 15:21:40', 'admin', '2020-05-20 04:15:08');
INSERT INTO `st_permission` VALUES (154, '3', '分页查询分类', 'WIKI_CATEGORY_PAGE_SEARCH', '/wiki/admin/classify/{page:[0-9]+}/{size:[0-9]+}', NULL, NULL, 1, 1, 0, '36,201,22', NULL, 'GET', '1', 0, 'admin', '2019-12-05 15:41:37', 'admin', '2020-04-04 12:23:54');
INSERT INTO `st_permission` VALUES (155, '3', '添加分类', 'WIKI_CATEGORY_ADD', '/wiki/admin/classify', NULL, NULL, 2, 1, 0, '36,201,22', NULL, 'POST', '1', 0, 'admin', '2019-12-05 15:52:39', 'admin', '2020-04-04 12:23:54');
INSERT INTO `st_permission` VALUES (156, '3', '分类编辑', 'WIKI_CATEGORY_EDIT', '/wiki/admin/classify/{id:[0-9]+}', NULL, NULL, 3, 1, 0, '36,201,22', NULL, 'PUT', '1', 0, 'admin', '2019-12-05 16:21:50', 'admin', '2020-04-04 12:23:54');
INSERT INTO `st_permission` VALUES (157, '2', '书籍管理', 'WikiBooks', 'books', NULL, NULL, 6, 1, 0, '36,203', '运营管理', NULL, '1', 0, 'admin', '2019-12-05 16:39:31', 'admin', '2020-04-04 12:24:44');
INSERT INTO `st_permission` VALUES (158, '2', '创建书籍信息', 'WikiBookCatalogAdd', 'bookCatalogAdd', '/wiki/books/add', NULL, 110, 1, 0, '36,203', '运营管理', 'GET', '0', 0, 'admin', '2019-12-05 16:43:32', 'admin', '2020-10-02 15:25:00');
INSERT INTO `st_permission` VALUES (159, '5', '分类树查询', 'WIKI_CLASSIFY_TREE_SEARCH', '/wiki/admin/classify/tree/{pid}', NULL, NULL, 100, 1, 0, '36,201,22', NULL, 'GET', '1', 0, 'admin', '2019-12-06 18:24:55', 'admin', '2020-04-04 12:23:54');
INSERT INTO `st_permission` VALUES (160, '3', '文件上传', 'WIKI_UPLOAD_FILE', '/wiki/admin/upload', NULL, NULL, 2, 1, 0, '36,202,113', NULL, 'POST', '1', 0, 'admin', '2019-12-31 13:54:33', 'admin', '2020-04-04 12:23:05');
INSERT INTO `st_permission` VALUES (161, '1', '文件管理', 'SYS_FILE', 'file', NULL, NULL, 3, 1, 0, '119', 'Cloud 管理', NULL, '0', 0, 'admin', '2020-01-07 09:25:09', 'admin', '2021-01-30 02:25:47');
INSERT INTO `st_permission` VALUES (162, '2', '上传记录', 'SYS_FILE_RECORD', 'fileRecord', NULL, NULL, 3, 1, 0, '119,161', '文件管理', NULL, '1', 0, 'admin', '2020-01-07 09:26:28', 'admin', '2020-01-15 10:48:38');
INSERT INTO `st_permission` VALUES (163, '3', '分页查询书籍', 'WIKI_BOOKS_PAGE_SEARCH', '/wiki/admin/books/{page:[0-9]+}/{size:[0-9]+}', NULL, NULL, 1, 1, 0, '36,203,157', '书籍管理', 'GET', '1', 0, 'admin', '2020-01-13 16:32:19', 'test', '2020-06-30 00:44:08');
INSERT INTO `st_permission` VALUES (164, '3', '查询书籍', 'WIKI_BOOKS_INFO', '/wiki/admin/books/{id:[0-9]+}', NULL, NULL, 3, 1, 0, '36,203,157', '书籍管理', 'GET', '1', 0, 'admin', '2020-01-13 16:33:55', 'test', '2020-06-29 15:24:20');
INSERT INTO `st_permission` VALUES (167, '2', '编辑书籍章节', 'WikiBookChapterEdit', 'bookChapterEdit', '/wiki/books/chapter/:id([a-zA-Z_0-9]+)', NULL, 113, 1, 0, '36,203', '运营管理', 'GET', '1', 0, 'admin', '2020-01-13 19:07:13', 'admin', '2020-10-02 15:23:55');
INSERT INTO `st_permission` VALUES (168, '2', '编辑书籍信息', 'WikiBookCatalogEdit', 'bookCatalogEdit', '/wiki/books/:id(\\d+)', NULL, 111, 1, 0, '36,203', '运营管理', 'GET', '1', 0, 'admin', '2020-01-14 09:39:34', 'admin', '2020-10-02 15:18:50');
INSERT INTO `st_permission` VALUES (169, '3', '修改书籍', 'WIKI_BOOKS_EDIT', '/wiki/admin/books/{id:[0-9]+}', NULL, NULL, 4, 1, 0, '36,203,157', '书籍管理', 'PUT', '1', 0, 'admin', '2020-01-14 09:44:42', 'test', '2020-06-29 15:24:31');
INSERT INTO `st_permission` VALUES (170, '3', '查询书籍章节', 'WIKI_BOOKS_CHAPTERS', '/wiki/admin/books/{id:[0-9]+}/chapters', NULL, NULL, 100, 1, 0, '36,203,157', '书籍管理', 'GET', '1', 0, 'admin', '2020-01-15 11:11:31', 'admin', '2020-04-04 12:24:44');
INSERT INTO `st_permission` VALUES (171, '3', '修改书籍章节', 'WIKI_BOOKS_CHAPTER_EDIT', '/wiki/admin/books/chapter/{id:[a-zA-Z_0-9]+}', NULL, NULL, 22, 1, 0, '36,203,157', '书籍管理', 'PUT', '1', 0, 'admin', '2020-01-15 17:22:49', 'test', '2020-06-29 15:34:33');
INSERT INTO `st_permission` VALUES (172, '3', '查询书籍章节内容', 'WIKI_BOOKS_CHAPTER_INFO', '/wiki/admin/books/chapter/{id:[a-zA-Z_0-9]+}', NULL, NULL, 23, 1, 0, '36,203,157', '书籍管理', 'GET', '1', 0, 'admin', '2020-01-15 18:56:37', 'test', '2020-06-29 15:37:51');
INSERT INTO `st_permission` VALUES (173, '3', '添加书籍章节', 'WIKI_BOOKS_CHAPTER', '/wiki/admin/books/chapter', NULL, NULL, 21, 1, 0, '36,203,157', '书籍管理', 'POST', '1', 0, 'admin', '2020-01-15 18:57:55', 'test', '2020-06-29 15:34:21');
INSERT INTO `st_permission` VALUES (174, '3', '修复书籍目录/章节', 'WIKI_BOOKS_CATALOG_FIX', '/wiki/admin/books/{id:[0-9]+}/fix', NULL, NULL, 25, 1, 0, '36,203,157', '书籍管理', 'POST', '1', 0, 'admin', '2020-01-20 02:43:45', 'test', '2020-06-29 15:37:31');
INSERT INTO `st_permission` VALUES (175, '3', '删除书籍目录/章节', 'WIKI_BOOKS_CHAPTER_DEL', '/wiki/admin/books/chapter/{id:[a-zA-Z_0-9]+}', NULL, NULL, 24, 1, 0, '36,203,157', '书籍管理', 'DELETE', '1', 0, 'admin', '2020-01-20 03:27:49', 'test', '2020-06-29 15:35:08');
INSERT INTO `st_permission` VALUES (176, '2', '书签管理', 'WikiBookmark', 'bookmark', NULL, NULL, 7, 1, 0, '36,203', '运营管理', NULL, '1', 0, 'admin', '2020-03-13 13:53:22', 'admin', '2020-04-04 12:24:30');
INSERT INTO `st_permission` VALUES (177, '3', '分页查询广告', 'WIKI_AD_PAGE_SEARCH', '/wiki/admin/ad/{page:[0-9]+}/{size:[0-9]+}', NULL, NULL, 1, 1, 0, '36,203,153', '广告管理', 'GET', '1', 0, 'admin', '2020-03-14 01:41:48', 'test', '2020-06-30 00:43:56');
INSERT INTO `st_permission` VALUES (178, '3', '分页查询书签', 'WIKI_BOOKMARK_PAGE_SEARCH', '/wiki/admin/bookmark/{page:[0-9]+}/{size:[0-9]+}', NULL, NULL, 1, 1, 0, '36,203,176', '书签管理', 'GET', '1', 0, 'admin', '2020-03-14 01:44:11', 'test', '2020-06-30 00:44:22');
INSERT INTO `st_permission` VALUES (179, '3', '创建书签', 'WIKI_BOOKMARK_ADD', '/wiki/admin/bookmark', NULL, NULL, 2, 1, 0, '36,203,176', '书签管理', 'POST', '1', 0, 'admin', '2020-03-15 01:43:21', 'admin', '2020-04-04 12:24:30');
INSERT INTO `st_permission` VALUES (180, '3', '修改书签', 'WIKI_BOOKMARK_EDIT', '/wiki/admin/bookmark/{id:[0-9]+}', NULL, NULL, 3, 1, 0, '36,203,176', '书签管理', 'PUT', '1', 0, 'admin', '2020-03-15 01:44:27', 'test', '2020-06-30 00:44:37');
INSERT INTO `st_permission` VALUES (181, '3', '删除书签', 'WIKI_BOOKMARK_DEL', '/wiki/admin/bookmark/{id:[0-9]+}', NULL, NULL, 4, 1, 0, '36,203,176', '书签管理', 'DELETE', '1', 0, 'admin', '2020-03-15 01:45:16', 'admin', '2020-04-04 12:24:30');
INSERT INTO `st_permission` VALUES (182, '2', '文章管理', 'WikiArticle', 'article', NULL, NULL, 2, 1, 0, '36,203', '运营管理', NULL, '1', 0, 'admin', '2020-03-15 05:09:04', 'admin', '2020-05-20 04:15:17');
INSERT INTO `st_permission` VALUES (183, '3', '分页查询文章', 'WIKI_ARTICLE_PAGE_SEARCH', '/wiki/admin/article/{page:[0-9]+}/{size:[0-9]+}', NULL, NULL, 1, 1, 0, '36,203,182', '文章管理', 'GET', '1', 0, 'admin', '2020-03-15 05:26:39', 'test', '2020-06-30 00:43:37');
INSERT INTO `st_permission` VALUES (184, '3', '添加文章', 'WIKI_ARTICLE_ADD', '/wiki/admin/article', NULL, NULL, 2, 1, 0, '36,203,182', '文章管理', 'POST', '1', 0, 'admin', '2020-03-15 05:28:02', 'admin', '2020-04-04 12:24:24');
INSERT INTO `st_permission` VALUES (185, '3', '修改文章', 'WIKI_ARTICLE_EDIT', '/wiki/admin/article/{id:[0-9]+}', NULL, NULL, 3, 1, 0, '36,203,182', '文章管理', 'PUT', '1', 0, 'admin', '2020-03-15 05:29:03', 'test', '2020-06-29 15:21:48');
INSERT INTO `st_permission` VALUES (186, '3', '文章查询', 'WIKI_ARTICLE_INFO', '/wiki/admin/article/{id:[0-9]+}', NULL, NULL, 4, 1, 0, '36,203,182', '文章管理', 'GET', '1', 0, 'admin', '2020-03-15 05:30:15', 'admin', '2020-04-04 12:24:24');
INSERT INTO `st_permission` VALUES (187, '2', '创建文章', 'WikiArticleAdd', 'articleAdd', '/wiki/article/add', NULL, 100, 1, 0, '36,203', '运营管理', 'GET', '1', 0, 'admin', '2020-03-15 05:33:25', 'admin', '2020-10-02 15:10:43');
INSERT INTO `st_permission` VALUES (188, '3', '复制权限', 'UPMS_PERMISSION_COPY', '/upms/permission', NULL, NULL, 6, 1, 0, '1,4', '权限管理', 'POST', '1', 0, 'admin', '2020-03-15 05:39:44', 'admin', '2020-07-04 11:59:17');
INSERT INTO `st_permission` VALUES (189, '2', '编辑文章', 'WikiArticleEdit', 'articleEdit', '/wiki/article/:id(\\d+)', NULL, 101, 1, 0, '36,203', '运营管理', 'GET', '1', 0, 'admin', '2020-03-15 05:33:25', 'admin', '2020-10-02 15:10:33');
INSERT INTO `st_permission` VALUES (190, '3', '添加广告', 'WIKI_AD_ADD', '/wiki/admin/ad', NULL, NULL, 2, 1, 0, '36,203,153', '广告管理', 'POST', '1', 0, 'admin', '2020-03-14 01:41:48', 'test', '2020-06-29 15:23:19');
INSERT INTO `st_permission` VALUES (191, '3', '修改广告', 'WIKI_AD_EDIT', '/wiki/admin/ad/{id:[0-9]+}', NULL, NULL, 3, 1, 0, '36,203,153', '广告管理', 'PUT', '1', 0, 'admin', '2020-03-14 01:41:48', 'test', '2020-06-29 15:23:28');
INSERT INTO `st_permission` VALUES (192, '2', '图片库', 'WikiResourcesImage', 'images', NULL, NULL, 1, 0, 0, '36,202', '资源管理', NULL, '1', 0, 'admin', '2019-12-05 16:39:31', 'admin', '2020-10-02 15:38:10');
INSERT INTO `st_permission` VALUES (193, '2', '登录日志', 'LogsLogin', 'login', NULL, NULL, 1, 1, 0, '24', '日志管理', '123', '1', 0, 'admin', '2018-09-06 11:54:26', NULL, NULL);
INSERT INTO `st_permission` VALUES (194, '3', '分页查询登录日志', 'LOGS_LOGIN_PAGE_SEARCH', '/upms/login/record/{page:[0-9]+}/{size:[0-9]+}', NULL, NULL, 1, 0, 0, '24,193', '登录日志', 'GET', '1', 0, 'admin', '2018-08-11 14:06:56', 'admin', '2020-03-21 17:45:49');
INSERT INTO `st_permission` VALUES (195, '3', '分页查询图片', 'WIKI_AD_IMAGE_PAGE_SEARCH', '/wiki/admin/images/{page:[0-9]+}/{size:[0-9]+}', NULL, NULL, 1, 1, 0, '36,202,192', '图片管理', 'GET', '1', 0, 'admin', '2020-03-14 01:41:48', 'admin', '2020-04-04 12:53:08');
INSERT INTO `st_permission` VALUES (196, '5', '图片上传', 'WIKI_UPLOAD_IMAGE', '/wiki/admin/upload/img', NULL, NULL, 3, 1, 0, '36,202,113', '文件上传', NULL, '1', 0, 'admin', '2019-12-31 13:54:33', 'admin', '2020-04-04 12:23:05');
INSERT INTO `st_permission` VALUES (197, '5', '分页模糊查询图片', 'WIKI_IMAGE_SEARCH2', '/wiki/admin/images/_search/{page:[0-9]+}/{size:[0-9]+}', NULL, NULL, 100, 1, 0, '36,202,192', '图片管理', 'GET', '1', 0, 'admin', '2020-03-14 01:41:48', 'admin', '2020-04-04 12:53:19');
INSERT INTO `st_permission` VALUES (198, '2', '用户管理', 'WikiUser', 'user', NULL, NULL, 5, 1, 0, '36,201', '基础管理', NULL, '1', 0, 'admin', '2019-12-05 16:39:31', 'admin', '2020-10-02 05:52:15');
INSERT INTO `st_permission` VALUES (199, '3', '分页查询用户', 'WIKI_USER_PAGE_SEARCH', '/wiki/admin/user/{page:[0-9]+}/{size:[0-9]+}', NULL, NULL, 1, 1, 0, '36,201,198', '用户管理', 'GET', '1', 0, 'admin', '2020-03-14 01:41:48', 'admin', '2020-04-04 12:24:09');
INSERT INTO `st_permission` VALUES (200, '5', '用户模糊查询', 'WIKI_USER_SEARCH2', '/wiki/admin/user/_search/{name}', NULL, NULL, 100, 1, 0, '36,201,198', '用户管理', 'GET', '1', 0, 'admin', '2020-03-14 01:41:48', 'admin', '2020-04-04 12:24:09');
INSERT INTO `st_permission` VALUES (201, '1', '基础管理', 'WikiBase', 'base', '/wiki/base/user', 'list', 1, 1, 0, '36', '极客管理', NULL, '1', 0, 'admin', '2020-04-03 13:35:11', 'admin', '2020-12-07 00:08:22');
INSERT INTO `st_permission` VALUES (202, '1', '资源管理', 'WikiResources', 'resources', '/wiki/resources/images', 'clipboard', 3, 1, 0, '36', '极客管理', NULL, '1', 0, 'admin', '2020-04-03 13:43:52', 'admin', '2020-12-07 00:09:01');
INSERT INTO `st_permission` VALUES (203, '1', '运营管理', 'WikiBusiness', 'business', '/wiki/business/books', 'excel', 2, 1, 0, '36', '极客管理', NULL, '1', 0, 'admin', '2020-04-04 04:10:24', 'admin', '2020-12-07 00:08:40');
INSERT INTO `st_permission` VALUES (204, '2', '资源库', 'WikiResourcesFile', 'files', NULL, NULL, 2, 0, 0, '36,202', '资源管理', NULL, '1', 0, 'admin', '2020-04-04 04:20:39', 'admin', '2020-10-02 15:39:00');
INSERT INTO `st_permission` VALUES (205, '3', '分页查询资源文件', 'WIKI_RESOURCES_FILE_PAGE_SEARCH', '/wiki/admin/resources/{page:[0-9]+}/{size:[0-9]+}', NULL, NULL, 1, 1, 0, '36,202,204', '共享资源', 'GET', '1', 0, 'admin', '2018-09-28 15:50:33', 'admin', '2020-04-04 12:23:05');
INSERT INTO `st_permission` VALUES (206, '3', '下载', 'WIKI_RESOURCES_FILE_DOWNLOAD', '/wiki/admin/resources/download/{id:[0-9]+}', NULL, NULL, 10, 1, 0, '36,202,204', '共享资源', 'GET', '1', 0, 'admin', '2018-09-28 15:50:33', 'admin', '2020-04-04 12:23:05');
INSERT INTO `st_permission` VALUES (207, '3', '图片信息编辑', 'WIKI_AD_IMAGE_EDIT', '/wiki/admin/images/{id:[0-9]+}', NULL, NULL, 2, 1, 0, '36,202,192', '图片信息管理', 'PUT', '1', 0, 'admin', '2020-03-14 01:41:48', 'admin', '2020-05-20 01:57:10');
INSERT INTO `st_permission` VALUES (208, '3', '编辑', 'WIKI_RESOURCES_FILE_EDIT', '/wiki/admin/resources/{id:[0-9]+}', NULL, NULL, 2, 1, 0, '36,202,204', '共享资源', 'PUT', '1', 0, 'admin', '2018-09-28 15:50:33', 'admin', '2020-04-04 12:23:05');
INSERT INTO `st_permission` VALUES (209, '3', '图片信息批量编辑', 'WIKI_AD_IMAGE_BATCH_EDIT', '/wiki/admin/images/batchEdit', NULL, NULL, 3, 1, 0, '36,202,192', '图片信息库', 'POST', '1', 0, 'admin', '2020-03-14 01:41:48', 'admin', '2020-05-20 09:00:22');
INSERT INTO `st_permission` VALUES (210, '3', '批量审核', 'WIKI_RESOURCES_FILE_BATCH_APPROVE', '/wiki/admin/resources/batchApprove', NULL, NULL, 3, 1, 0, '36,202,204', '共享资源库', 'POST', '1', 0, 'admin', '2018-09-28 15:50:33', 'admin', '2020-04-04 12:23:05');
INSERT INTO `st_permission` VALUES (211, '2', '阅读书籍', 'WikiBookPreview', 'bookPreview', '/wiki/books/preview/:id(\\d+)', NULL, 114, 1, 0, '36,203', '运营管理', 'GET', '1', 0, 'admin', '2020-01-13 19:07:13', 'admin', '2020-10-03 00:51:07');
INSERT INTO `st_permission` VALUES (212, '3', '书籍阅读', 'WikiBookRead', '/wiki/admin/books/images/**', NULL, NULL, 1, 1, 0, '36,203,211', '阅读书籍', 'GET', '1', 0, 'admin', '2020-01-13 16:33:55', 'admin', '2020-10-03 00:50:45');
INSERT INTO `st_permission` VALUES (213, '3', '查询配置信息', 'CANAL_CLUSTERS_CONFIG', '/canal/{env:[a-zA-Z_0-9]+}/config/template/{id:[0-9]+}', NULL, NULL, 10, 1, 0, '94,29', '集群管理', 'GET', '1', 0, 'admin', '2018-08-04 21:12:44', 'admin', '2020-06-05 13:33:43');
INSERT INTO `st_permission` VALUES (214, '3', '查询配置信息', 'CANAL_CLUSTERS_CONFIG_ADD', '/canal/{env:[a-zA-Z_0-9]+}/config/template', NULL, NULL, 11, 1, 0, '94,29', '集群管理', 'POST', '1', 0, 'admin', '2018-08-04 21:12:44', 'admin', '2020-06-02 13:54:30');
INSERT INTO `st_permission` VALUES (215, '3', '保存配置信息', 'CANAL_CLUSTERS_CONFIG_EDIT', '/canal/{env:[a-zA-Z_0-9]+}/config/template', NULL, NULL, 12, 1, 0, '94,29', '集群管理', 'PUT', '1', 0, 'admin', '2018-08-04 21:12:44', 'admin', '2020-06-06 03:34:23');
INSERT INTO `st_permission` VALUES (216, '3', '修改', 'CANAL_CLUSTERS_EDIT', '/canal/{env:[a-zA-Z_0-9]+}/cluster', NULL, NULL, 3, 1, 0, '94,29', '集群管理', 'PUT', '1', 0, 'admin', '2018-08-04 21:12:44', 'admin', '2020-06-06 08:28:05');
INSERT INTO `st_permission` VALUES (217, '3', '删除', 'CANAL_CLUSTERS_DELETE', '/canal/{env:[a-zA-Z_0-9]+}/cluster/{id:[0-9]+}', NULL, NULL, 4, 1, 0, '94,29', '集群管理', 'DELETE', '1', 0, 'admin', '2018-08-04 21:12:44', 'admin', '2020-06-06 08:28:05');
INSERT INTO `st_permission` VALUES (218, '3', '查询', 'CANAL_SERVERS_SEARCH', '/canal/{env:[a-zA-Z_0-9]+}/nodeServers', NULL, NULL, 1, 1, 0, '94,30', '服务管理', 'GET', '1', 0, 'admin', '2018-09-06 10:27:18', 'admin', '2020-06-06 10:37:04');
INSERT INTO `st_permission` VALUES (219, '3', '添加', 'CANAL_SERVERS_ADD', '/canal/{env:[a-zA-Z_0-9]+}/nodeServer', NULL, NULL, 2, 1, 0, '94,30', '服务管理', 'POST', '1', 0, 'admin', '2018-09-06 10:27:18', 'admin', '2020-06-06 10:37:04');
INSERT INTO `st_permission` VALUES (220, '3', '修改', 'CANAL_SERVERS_EDIT', '/canal/{env:[a-zA-Z_0-9]+}/nodeServer', NULL, NULL, 3, 1, 0, '94,30', '服务管理', 'PUT', '1', 0, 'admin', '2018-09-06 10:27:18', 'admin', '2020-06-06 10:37:04');
INSERT INTO `st_permission` VALUES (221, '3', '删除', 'CANAL_SERVERS_DELETE', '/canal/{env:[a-zA-Z_0-9]+}/nodeServer/{id:[0-9]+}', NULL, NULL, 4, 1, 0, '94,30', '服务管理', 'DELETE', '1', 0, 'admin', '2018-09-06 10:27:18', 'admin', '2020-06-06 10:37:04');
INSERT INTO `st_permission` VALUES (222, '4', '服务日志', 'CANAL_SERVER_LOG', 'serverLog', '/canal/nodeServer/log', NULL, 100, 1, 0, '94,30', '服务管理', 'GET', '1', 0, 'admin', '2020-06-06 10:46:35', 'admin', '2020-06-06 10:50:13');
INSERT INTO `st_permission` VALUES (223, '3', '服务日志查询', 'CANAL_SERVERS_LOG', '/canal/{env:[a-zA-Z_0-9]+}/nodeServer/log/{id:[0-9]+}', NULL, NULL, 11, 1, 0, '94,30', '服务管理', 'GET', '1', 0, 'admin', '2018-09-06 10:27:18', 'admin', '2020-06-06 10:37:04');
INSERT INTO `st_permission` VALUES (224, '3', '查询', 'CANAL_INSTANCE_SEARCH', '/canal/{env:[a-zA-Z_0-9]+}/instances', NULL, NULL, 1, 1, 0, '94,25', '实例管理', 'GET', '1', 0, 'admin', '2018-09-06 10:27:18', 'admin', '2020-06-06 10:37:04');
INSERT INTO `st_permission` VALUES (225, '3', '查询游标', 'CANAL_INSTANCE_META_POS', '/canal/{env:[a-zA-Z_0-9]+}/instance/meta/position/{id:[0-9]+}', NULL, NULL, 11, 1, 0, '94,25', '实例管理', 'GET', '1', 0, 'admin', '2018-09-06 10:27:18', 'admin', '2020-06-06 11:49:52');
INSERT INTO `st_permission` VALUES (226, '3', '更新游标', 'CANAL_INSTANCE_META_POS_EDIT', '/canal/{env:[a-zA-Z_0-9]+}/instance/meta/position/{id:[0-9]+}', NULL, NULL, 12, 1, 0, '94,25', '实例管理', 'POST', '1', 0, 'admin', '2018-09-06 10:27:18', 'admin', '2020-06-06 11:49:52');
INSERT INTO `st_permission` VALUES (227, '4', '服务日志', 'CANAL_INSTANCE_LOG', 'instanceLog', '/canal/instance/log', NULL, 101, 1, 0, '94,25', '实例管理', 'GET', '1', 0, 'admin', '2020-06-06 10:46:35', 'admin', '2020-07-20 00:43:31');
INSERT INTO `st_permission` VALUES (228, '3', '服务日志查询', 'CANAL_INSTANCE_LOG_Q', '/canal/{env:[a-zA-Z_0-9]+}/instance/log/{id1:[0-9]+}/{id2:[0-9]+}', NULL, NULL, 12, 1, 0, '94,25', '实例管理', 'GET', '1', 0, 'admin', '2018-09-06 10:27:18', 'admin', '2020-06-06 14:37:25');
INSERT INTO `st_permission` VALUES (229, '5', '查询集群服务', 'CANAL_SERVERS_NODE_SEARCH', '/canal/{env:[a-zA-Z_0-9]+}/clustersAndServers', NULL, NULL, 2, 1, 0, '94,30', '服务管理', 'GET', '1', 0, 'admin', '2018-09-06 10:27:18', 'admin', '2020-06-06 10:37:04');
INSERT INTO `st_permission` VALUES (230, '3', '实例远程操作', 'CANAL_INSTANCE_OPTION', '/canal/{env:[a-zA-Z_0-9]+}/instance/status/{id:[0-9]+}', NULL, NULL, 10, 1, 0, '94,25', '实例管理', 'PUT', '1', 0, 'admin', '2018-09-06 10:27:18', 'admin', '2020-06-06 16:39:09');
INSERT INTO `st_permission` VALUES (231, '3', '新增', 'CANAL_INSTANCE_ADD', '/canal/{env:[a-zA-Z_0-9]+}/instance/template', NULL, NULL, 2, 1, 0, '94,25', '实例管理', 'POST', '1', 0, 'admin', '2018-09-06 10:27:18', 'admin', '2020-06-07 00:54:49');
INSERT INTO `st_permission` VALUES (232, '3', '修改', 'CANAL_INSTANCE_EDIT', '/canal/{env:[a-zA-Z_0-9]+}/instance/template', NULL, NULL, 3, 1, 0, '94,25', '实例管理', 'PUT', '1', 0, 'admin', '2018-09-06 10:27:18', 'admin', '2020-06-07 00:54:40');
INSERT INTO `st_permission` VALUES (233, '3', '删除', 'CANAL_INSTANCE_DELETE', '/canal/{env:[a-zA-Z_0-9]+}/instance/template/{id:[0-9]+}', NULL, NULL, 4, 1, 0, '94,25', '实例管理', 'DELETE', '1', 0, 'admin', '2018-09-06 10:27:18', 'admin', '2020-06-08 12:52:45');
INSERT INTO `st_permission` VALUES (234, '3', '详细信息', 'CANAL_INSTANCE_DETAIL', '/canal/{env:[a-zA-Z_0-9]+}/instance/template/{id:[0-9]+}', NULL, NULL, 5, 1, 0, '94,25', '实例管理', 'GET', '1', 0, 'admin', '2018-09-06 10:27:18', 'admin', '2020-06-06 16:40:53');
INSERT INTO `st_permission` VALUES (235, '3', '服务实例查询', 'CANAL_SERVER_INSTANCES', '/canal/{env:[a-zA-Z_0-9]+}/active/instances/{id:[0-9]+}', NULL, NULL, 6, 1, 0, '94,30', '服务管理', 'GET', '1', 0, 'admin', '2018-09-06 10:27:18', 'admin', '2020-06-08 14:28:53');
INSERT INTO `st_permission` VALUES (236, '3', '停止服务', 'CANAL_SERVER_STOP', '/canal/{env:[a-zA-Z_0-9]+}/nodeServer/stop/{id:[0-9]+}', NULL, NULL, 8, 1, 0, '94,30', '服务管理', 'PUT', '1', 0, 'admin', '2018-09-06 10:27:18', 'admin', '2020-06-08 14:37:17');
INSERT INTO `st_permission` VALUES (237, '3', '启动服务', 'CANAL_SERVER_START', '/canal/{env:[a-zA-Z_0-9]+}/nodeServer/start/{id:[0-9]+}', NULL, NULL, 7, 1, 0, '94,30', '服务管理', 'PUT', '1', 0, 'admin', '2018-09-06 10:27:18', 'admin', '2020-06-08 14:37:28');
INSERT INTO `st_permission` VALUES (238, '2', '告警管理', 'CanalAlarm', 'alarm', NULL, NULL, 4, 0, 0, '94', 'Canal管理', '', '1', 0, 'admin', '2018-07-28 11:34:35', 'admin', '2020-10-03 05:13:07');
INSERT INTO `st_permission` VALUES (239, '3', '查询', 'CANAL_ALARM_SEARCH', '/canal/{env:[a-zA-Z_0-9]+}/alarms', NULL, NULL, 1, 1, 0, '94,238', '告警管理', 'GET', '1', 0, 'admin', '2018-09-06 10:27:18', 'admin', '2020-06-06 10:37:04');
INSERT INTO `st_permission` VALUES (240, '1', '报表管理', 'WIKI_REPORT', 'report', '/wiki/report/user', NULL, 10, 1, 0, '36', '极客管理', 'GET', '0', 0, 'admin', '2020-06-15 14:35:10', 'admin', '2020-06-16 13:18:37');
INSERT INTO `st_permission` VALUES (241, '2', '用户报表', 'WIKI:REPORT:USER', 'user', NULL, NULL, 1, 1, 0, '36,240', '报表管理', NULL, '1', 0, 'admin', '2020-06-15 14:38:08', NULL, NULL);
INSERT INTO `st_permission` VALUES (242, '2', '文章报表', 'WIKI:REPORT:ARTICLE', 'article', NULL, NULL, 2, 1, 0, '36,240', '报表管理', NULL, '1', 0, 'admin', '2020-06-15 14:38:08', NULL, NULL);
INSERT INTO `st_permission` VALUES (243, '2', '资源报表', 'WIKI:REPORT:RECOURCE', 'recource', NULL, NULL, 3, 1, 0, '36,240', '报表管理', NULL, '1', 0, 'admin', '2020-06-15 14:38:08', NULL, NULL);
INSERT INTO `st_permission` VALUES (244, '3', '当天访问量查询', 'WIKI_REPORT_USER_NEW_VISITS', '/wiki/admin/report/newVisits', NULL, NULL, 1, 1, 0, '36,240,241', '用户报表', 'GET', '1', 0, 'admin', '2020-06-15 14:41:42', 'admin', '2020-06-15 14:43:15');
INSERT INTO `st_permission` VALUES (245, '3', '最近一周访问量查询', 'WIKI_REPORT_USER_WEEK_VISITS', '/wiki/admin/report/weekVisits', NULL, NULL, 2, 1, 0, '36,240,241', '用户报表', 'GET', '1', 0, 'admin', '2020-06-15 14:41:42', NULL, NULL);
INSERT INTO `st_permission` VALUES (246, '3', '当天文章创建量查询', 'WIKI_REPORT_ARTICLE_NEW_ARTICLES', '/wiki/admin/report/newArticles', NULL, NULL, 1, 1, 0, '36,240,242', '文章报表', 'GET', '1', 0, 'admin', '2020-06-15 14:41:42', 'admin', '2020-06-15 14:44:25');
INSERT INTO `st_permission` VALUES (247, '3', ' 最近一周文章量查询', 'WIKI_REPORT_ARTICLE_WEEK_ARTICLES', '/wiki/admin/report/weekArticles', NULL, NULL, 2, 1, 0, '36,240,242', '文章报表', 'GET', '1', 0, 'admin', '2020-06-15 14:41:42', 'admin', '2020-06-15 14:47:54');
INSERT INTO `st_permission` VALUES (248, '3', '当天资源共享量查询', 'WIKI_REPORT_RESOURCES_NEW_RESOURCES', '/wiki/admin/report/newResources', NULL, NULL, 1, 1, 0, '36,240,243', '资源报表', 'GET', '1', 0, 'admin', '2020-06-15 14:41:42', 'admin', '2020-06-15 14:44:25');
INSERT INTO `st_permission` VALUES (249, '3', ' 最近一周资源量查询', 'WIKI_REPORT_RESOURCES_WEEK_RESOURCES', '/wiki/admin/report/weekResources', NULL, NULL, 2, 1, 0, '36,240,243', '资源报表', 'GET', '1', 0, 'admin', '2020-06-15 14:41:42', 'admin', '2020-06-15 14:47:28');
INSERT INTO `st_permission` VALUES (250, '4', '配置properties', 'CANAL_CLUSTER_CONFG_P', 'config', '/canalServer/nodeServer/config', NULL, 20, 1, 0, '94,29', '集群管理', 'GET', '1', 0, 'admin', '2018-09-06 10:27:18', 'admin', '2020-06-17 16:07:12');
INSERT INTO `st_permission` VALUES (251, '3', '查询配置properties信息', 'CANAL_CLUSTERS_CONFIG_PROPERTIES', '/canal/{env:[a-zA-Z_0-9]+}/config/{id:[0-9]+}/{id2:[0-9]+}', NULL, NULL, 21, 1, 0, '94,29', '集群管理', 'GET', '1', 0, 'admin', '2018-08-04 21:12:44', 'admin', '2020-06-05 13:33:43');
INSERT INTO `st_permission` VALUES (252, '2', '推荐文章', 'WikiArticleRecommend', 'articleRecommend', '/wiki/article/recommend', NULL, 103, 1, 0, '36,203', '运营管理', 'GET', '1', 0, 'test', '2020-03-15 05:33:25', 'admin', '2020-10-02 15:10:07');
INSERT INTO `st_permission` VALUES (253, '3', '修改文章推荐位', 'WikiArticleRecommendEdit', '/wiki/admin/article/recommend', NULL, NULL, 2, 1, 0, '36,203,252', '推荐文章', 'POST', '1', 0, 'test', '2020-03-15 05:30:15', 'admin', '2020-10-02 15:12:32');
INSERT INTO `st_permission` VALUES (254, '3', '查询文章推荐位', 'WikiArticleRecommendSearch', '/wiki/admin/article/recommend', NULL, NULL, 1, 1, 0, '36,203,252', '推荐文章', 'GET', '1', 0, 'test', '2020-03-15 05:30:15', 'admin', '2020-10-02 15:11:55');
INSERT INTO `st_permission` VALUES (255, '2', '数据字典', 'UpmsDict', 'dict', NULL, NULL, 6, 0, 1, '1', '系统管理', NULL, '1', 0, 'test', '2020-07-02 00:49:23', 'admin', '2020-07-04 12:01:29');
INSERT INTO `st_permission` VALUES (256, '2', '职位管理', 'UpmsPosition', 'position', NULL, NULL, 5, 0, 1, '1', '系统管理', NULL, '1', 0, 'test', '2020-07-02 16:34:10', 'admin', '2020-07-04 12:01:22');
INSERT INTO `st_permission` VALUES (257, '3', '分页查询组织', 'UPMS_DEPARTMENT_PAGE_SEARCH', '/upms/department/{page:[0-9]+}/{size:[0-9]+}', NULL, NULL, 1, 1, 1, '1,129', '组织管理', 'GET', '1', 0, 'admin', '2018-07-28 01:35:33', 'test', '2020-09-09 23:09:19');
INSERT INTO `st_permission` VALUES (258, '3', '添加组织', 'UPMS_DEPARTMENT_ADD', '/upms/department', NULL, NULL, 2, 1, 1, '1,129', '组织管理', 'POST', '1', 0, 'admin', '2018-07-28 01:35:33', NULL, NULL);
INSERT INTO `st_permission` VALUES (259, '3', '修改组织', 'UPMS_DEPARTMENT_EDIT', '/upms/department/{id:[0-9]+}', NULL, NULL, 3, 1, 1, '1,129', '组织管理', 'PUT', '1', 0, 'admin', '2018-07-28 01:35:33', NULL, NULL);
INSERT INTO `st_permission` VALUES (260, '3', '删除组织', 'UPMS_DEPARTMENT_DEL', '/upms/department/{id:[0-9]+}', NULL, NULL, 4, 1, 1, '1,129', '组织管理', 'DELETE', '1', 0, 'admin', '2018-07-28 01:35:33', 'test', '2020-07-04 08:19:36');
INSERT INTO `st_permission` VALUES (261, '5', '查询组织树', 'UPMS_DEPARTMENT_TREE', '/upms/department/tree/{pid:[0-9]+}', NULL, NULL, 100, 1, 1, '1,129', '组织管理', 'GET', '1', 0, 'test', '2018-07-28 01:35:33', 'admin', '2020-07-06 22:45:38');
INSERT INTO `st_permission` VALUES (262, '3', '分页查询职位', 'UPMS_POSITION_PAGE_SEARCH', '/upms/position/{page:[0-9]+}/{size:[0-9]+}', NULL, NULL, 1, 1, 1, '1,256', '职位管理', 'GET', '1', 0, 'admin', '2018-07-28 01:35:33', 'admin', '2020-07-04 12:03:25');
INSERT INTO `st_permission` VALUES (263, '3', '分页查询字典', 'UPMS_DICT_PAGE_SEARCH', '/upms/dict/{page:[0-9]+}/{size:[0-9]+}', NULL, NULL, 1, 1, 1, '1,255', '数据字典', 'GET', '1', 0, 'admin', '2018-07-28 01:35:33', 'admin', '2020-07-04 12:03:25');
INSERT INTO `st_permission` VALUES (264, '3', '添加字典', 'UPMS_DICT_ADD', '/upms/dict', NULL, NULL, 2, 1, 1, '1,255', '数据字典', 'POST', '1', 0, 'admin', '2018-07-28 01:35:33', 'admin', '2020-07-04 12:03:25');
INSERT INTO `st_permission` VALUES (265, '3', '修改字典', 'UPMS_DICT_EDIT', '/upms/dict/{id:[0-9]+}', NULL, NULL, 3, 1, 1, '1,255', '数据字典', 'PUT', '1', 0, 'admin', '2018-07-28 01:35:33', 'admin', '2020-07-04 12:03:25');
INSERT INTO `st_permission` VALUES (266, '3', '删除字典', 'UPMS_DICT_DEL', '/upms/dict/{id:[0-9]+}', NULL, NULL, 4, 1, 1, '1,255', '数据字典', 'DELETE', '1', 0, 'admin', '2018-07-28 01:35:33', 'admin', '2020-07-04 12:03:25');
INSERT INTO `st_permission` VALUES (267, '3', '查询字典数据', 'UPMS_DICT_DATA', '/upms/dict/data/{code}', NULL, NULL, 10, 1, 1, '1,255', '数据字典', 'GET', '1', 0, 'admin', '2018-07-28 01:35:33', 'admin', '2020-07-06 22:21:34');
INSERT INTO `st_permission` VALUES (268, '3', '添加职位', 'UPMS_POSITION_ADD', '/upms/position', NULL, NULL, 2, 1, 1, '1,256', '职位管理', 'POST', '1', 0, 'admin', '2018-07-28 01:35:33', 'admin', '2020-07-04 12:03:25');
INSERT INTO `st_permission` VALUES (269, '3', '修改职位', 'UPMS_POSITION_EDIT', '/upms/position/{id:[0-9]+}', NULL, NULL, 3, 1, 1, '1,256', '职位管理', 'PUT', '1', 0, 'admin', '2018-07-28 01:35:33', 'admin', '2020-07-04 12:03:25');
INSERT INTO `st_permission` VALUES (270, '3', '删除职位', 'UPMS_POSITION_DEL', '/upms/position/{id:[0-9]+}', NULL, NULL, 4, 1, 1, '1,256', '职位管理', 'DELETE', '1', 0, 'admin', '2018-07-28 01:35:33', 'admin', '2020-07-04 12:03:25');
INSERT INTO `st_permission` VALUES (271, '3', '分配组织职位', 'UPMS_DEPARTMENT_POSITIONS', '/upms/department/{id:[0-9]+}/positions', NULL, NULL, 20, 1, 1, '1,129', '组织管理', '', '1', 0, 'admin', '2018-07-28 01:35:33', 'admin', '2020-07-04 21:38:36');
INSERT INTO `st_permission` VALUES (272, '5', '查询组织职位', 'UPMS_DEPARTMENT_POSITIONS_SEARCH', '/upms/department/{id:[0-9]+}/positions/*', NULL, NULL, 101, 1, 1, '1,129', '组织管理', 'GET', '1', 0, 'admin', '2018-07-28 01:35:33', 'test', '2020-07-06 23:06:19');
INSERT INTO `st_permission` VALUES (273, '5', '查询职位列表', 'UPMS_POSITION_SEARCH', '/upms/position/search', NULL, NULL, 100, 1, 1, '1,256', '职位管理', 'GET', '1', 0, 'admin', '2018-07-28 01:35:33', 'admin', '2020-07-05 09:49:32');
INSERT INTO `st_permission` VALUES (274, '3', '查询字典明细', 'UPMS_DICT_DETAIL', '/upms/dict/{id:[0-9]+}', NULL, NULL, 5, 1, 1, '1,255', '数据字典', 'GET', '1', 0, 'admin', '2018-07-28 01:35:33', 'admin', '2020-07-04 12:03:25');
INSERT INTO `st_permission` VALUES (275, '1', 'Nacos管理', 'Nacos', 'nacos', '/nacos/cluster/nodes', 'table', 13, 0, 0, '0', NULL, NULL, '1', 0, 'admin', '2020-09-28 14:33:31', 'admin', '2020-10-01 14:22:16');
INSERT INTO `st_permission` VALUES (276, '1', '租户管理', 'NacosTetant', 'tenant', '/nacos/tenant/namespace', 'user', 1, 0, 0, '275', 'Nacos管理', NULL, '1', 0, 'admin', '2020-09-28 14:42:20', 'admin', '2020-10-01 14:22:39');
INSERT INTO `st_permission` VALUES (277, '2', '命名空间', 'NacosTenantNamespace', 'namespace', NULL, NULL, 1, 0, 0, '275,276', '租户管理', NULL, '1', 0, 'admin', '2020-09-28 14:43:57', NULL, NULL);
INSERT INTO `st_permission` VALUES (278, '1', '配置管理', 'NacosConfigs', 'configs', '/nacos/configs/index', 'tree-table', 2, 0, 0, '275', 'Nacos管理', NULL, '1', 0, 'admin', '2020-09-28 14:42:20', 'admin', '2020-10-01 14:24:58');
INSERT INTO `st_permission` VALUES (279, '2', '配置列表', 'NacosConfigsIndex', 'index', NULL, NULL, 1, 0, 0, '275,278', '配置管理', NULL, '1', 0, 'admin', '2020-09-28 14:43:57', 'admin', '2020-10-01 14:24:50');
INSERT INTO `st_permission` VALUES (280, '1', '服务管理', 'NacosServices', 'services', '/nacos/services/publish', 'dashboard', 3, 0, 0, '275', 'Nacos管理', NULL, '1', 0, 'admin', '2020-09-28 14:42:20', NULL, NULL);
INSERT INTO `st_permission` VALUES (281, '1', '集群管理', 'NacosCluster', 'cluster', '/nacos/cluster/index', 'tree', 4, 0, 0, '275', 'Nacos管理', NULL, '1', 0, 'admin', '2020-09-28 14:42:20', 'admin', '2020-09-28 14:55:12');
INSERT INTO `st_permission` VALUES (282, '2', '服务列表', 'NacosServicesIndex', 'index', NULL, NULL, 1, 0, 0, '275,280', '服务管理', NULL, '1', 0, 'admin', '2020-09-28 14:43:57', 'admin', '2020-10-02 00:18:57');
INSERT INTO `st_permission` VALUES (283, '2', '订阅者列表', 'NacosServiceSubscribes', 'subscribes', NULL, NULL, 2, 0, 0, '275,280', '服务管理', NULL, '1', 0, 'admin', '2020-09-28 14:43:57', 'admin', '2020-10-02 00:18:44');
INSERT INTO `st_permission` VALUES (284, '2', '节点列表', 'NacosClusterNodes', 'nodes', NULL, NULL, 1, 0, 0, '275,281', '集群管理', NULL, '1', 0, 'admin', '2020-09-28 14:43:57', 'admin', '2020-10-02 00:15:33');
INSERT INTO `st_permission` VALUES (285, '2', '历史版本', 'NacosConfigsHistory', 'history', NULL, NULL, 2, 0, 0, '275,278', '配置管理', NULL, '1', 0, 'admin', '2020-09-28 14:43:57', NULL, NULL);
INSERT INTO `st_permission` VALUES (286, '2', '监听查询', 'NacosConfigsListener', 'listener', NULL, NULL, 3, 0, 0, '275,278', '配置管理', NULL, '1', 0, 'admin', '2020-09-28 14:43:57', 'admin', '2020-10-01 14:29:21');
INSERT INTO `st_permission` VALUES (287, '3', '创建', 'NacosTenantNamespaceAdd', '/nacos/v1/console/namespaces', NULL, NULL, 2, 0, 0, '275,276,277', '命名空间', 'POST', '1', 0, 'admin', '2020-10-01 09:27:56', 'admin', '2020-10-02 00:46:12');
INSERT INTO `st_permission` VALUES (288, '3', '修改', 'NacosTenantNamespaceEdit', '/nacos/v1/console/namespaces', NULL, NULL, 3, 0, 0, '275,276,277', '命名空间', 'PUT', '1', 0, 'admin', '2020-10-01 09:27:56', 'admin', '2020-10-02 00:46:19');
INSERT INTO `st_permission` VALUES (289, '3', '详细', 'NacosTenantNamespaceDetail', '/nacos/v1/console/namespaces', NULL, NULL, 5, 0, 0, '275,276,277', '命名空间', 'GET', '1', 0, 'admin', '2020-10-01 09:27:56', 'admin', '2020-10-02 00:46:28');
INSERT INTO `st_permission` VALUES (290, '3', '删除', 'NacosTenantNamespaceDelete', '/nacos/v1/console/namespaces', NULL, NULL, 4, 0, 0, '275,276,277', '命名空间', 'DELETE', '1', 0, 'admin', '2020-10-01 09:27:56', NULL, NULL);
INSERT INTO `st_permission` VALUES (291, '2', '添加配置', 'NacosConfigAdd', 'add', NULL, NULL, 100, 1, 0, '275,278', '配置管理', NULL, '1', 0, 'admin', '2020-09-28 14:43:57', 'admin', '2020-10-01 14:24:50');
INSERT INTO `st_permission` VALUES (292, '2', '配置详情', 'NacosConfigDetail', 'detail', NULL, NULL, 101, 1, 0, '275,278', '配置管理', NULL, '1', 0, 'test', '2020-09-28 14:43:57', 'admin', '2020-10-01 14:24:50');
INSERT INTO `st_permission` VALUES (293, '2', '编辑配置', 'NacosConfigEdit', 'edit', NULL, NULL, 102, 1, 0, '275,278', '配置管理', NULL, '1', 0, 'test', '2020-09-28 14:43:57', 'admin', '2020-10-02 00:46:55');
INSERT INTO `st_permission` VALUES (294, '2', '历史详情', 'NacosConfigHistory', 'history', NULL, NULL, 103, 1, 0, '275,278', '配置管理', NULL, '1', 0, 'admin', '2020-09-28 14:43:57', 'admin', '2020-10-01 14:24:50');
INSERT INTO `st_permission` VALUES (295, '2', '配置回滚', 'NacosConfigRollback', 'rollback', NULL, NULL, 104, 1, 0, '275,278', '配置管理', NULL, '1', 0, 'admin', '2020-09-28 14:43:57', 'admin', '2020-10-01 14:24:50');
INSERT INTO `st_permission` VALUES (296, '2', '服务详情', 'NacosServiceDetail', 'detail', NULL, NULL, 100, 1, 0, '275,280', '服务管理', NULL, '1', 0, 'admin', '2020-09-28 14:43:57', 'admin', '2020-10-01 14:24:50');
INSERT INTO `st_permission` VALUES (297, '3', '查询', 'NacosConfigsIndexSearch', '/nacos/v1/cs/configs', NULL, NULL, 1, 0, 0, '275,278,279', '配置列表', 'GET', '1', 0, 'admin', '2020-10-01 09:27:56', 'admin', '2020-10-02 00:25:04');
INSERT INTO `st_permission` VALUES (298, '3', '创建', 'NacosConfigsIndexAdd', '/nacos/v1/cs/configs', NULL, NULL, 2, 0, 0, '275,278,279', '配置列表', 'POST', '1', 0, 'admin', '2020-10-01 09:27:56', 'admin', '2020-10-02 00:25:04');
INSERT INTO `st_permission` VALUES (299, '3', '删除', 'NacosConfigsIndexDelete', '/nacos/v1/cs/configs', NULL, NULL, 3, 0, 0, '275,278,279', '配置列表', 'DELETE', '1', 0, 'admin', '2020-10-01 09:27:56', 'admin', '2020-10-02 00:25:04');
INSERT INTO `st_permission` VALUES (300, '3', '编辑', 'NacosConfigsIndexEdit', '/nacos/v1/cs/configs', NULL, NULL, 3, 0, 0, '275,278,279', '配置列表', 'POST', '1', 0, 'admin', '2020-10-01 09:27:56', 'admin', '2020-10-02 00:25:04');
INSERT INTO `st_permission` VALUES (301, '3', '导出', 'NacosConfigsIndexExport', '/nacos/v1/cs/configs', NULL, NULL, 4, 0, 0, '275,278,279', '配置列表', 'GET', '1', 0, 'admin', '2020-10-01 09:27:56', 'admin', '2020-10-02 00:25:04');
INSERT INTO `st_permission` VALUES (302, '3', '导入', 'NacosConfigsIndexImport', '/nacos/v1/cs/configs', NULL, NULL, 5, 0, 0, '275,278,279', '配置列表', 'POST', '1', 0, 'admin', '2020-10-01 09:27:56', 'admin', '2020-10-02 00:25:04');
INSERT INTO `st_permission` VALUES (303, '3', '克隆', 'NacosConfigsIndexClone', '/nacos/v1/cs/configs', NULL, NULL, 6, 0, 0, '275,278,279', '配置列表', 'POST', '1', 0, 'admin', '2020-10-01 09:27:56', 'admin', '2020-10-02 00:25:04');
INSERT INTO `st_permission` VALUES (304, '3', '查询', 'NacosTenantNamespaceSearch', '/nacos/v1/console/namespaces', NULL, NULL, 1, 0, 0, '275,276,277', '命名空间', 'GET', '1', 0, 'admin', '2020-10-01 09:27:56', 'admin', '2020-10-02 00:46:02');
INSERT INTO `st_permission` VALUES (305, '3', '查询', 'NacosClusterNodesSearch', '/nacos/v1/core/cluster/nodes', NULL, NULL, 1, 0, 0, '275,281,284', '节点列表', 'GET', '1', 0, 'admin', '2020-10-01 09:27:56', 'admin', '2020-10-02 00:46:02');
INSERT INTO `st_permission` VALUES (306, '3', '查询', 'NacosServiceSubscribesSearch', '/nacos/v1/ns/service/subscribers', NULL, NULL, 1, 0, 0, '275,280,283', '订阅者列表', 'GET', '1', 0, 'admin', '2020-10-01 09:27:56', 'admin', '2020-10-02 00:46:02');
INSERT INTO `st_permission` VALUES (307, '3', 'Data查询', 'NacosConfigsListenerSearch', '/nacos/v1/cs/configs/listener', NULL, NULL, 1, 0, 0, '275,278,286', '监听查询', 'GET', '1', 0, 'admin', '2020-10-01 09:27:56', 'admin', '2020-10-02 01:14:51');
INSERT INTO `st_permission` VALUES (308, '3', 'IP查询', 'NacosConfigsListenerSearch2', '/nacos/v1/cs/listener', NULL, NULL, 2, 0, 0, '275,278,286', '监听查询', 'GET', '1', 0, 'admin', '2020-10-01 09:27:56', 'admin', '2020-10-02 01:14:51');
INSERT INTO `st_permission` VALUES (309, '3', '查询', 'NacosServicesIndexSearch', '/nacos/v1/ns/catalog/services', NULL, NULL, 1, 0, 0, '275,280,282', '服务列表', 'GET', '1', 0, 'admin', '2020-10-01 09:27:56', 'admin', '2020-10-02 00:46:02');
INSERT INTO `st_permission` VALUES (310, '3', '创建', 'NacosServicesIndexAdd', '/nacos/v1/ns/service', NULL, NULL, 2, 0, 0, '275,280,282', '服务列表', 'POST', '1', 0, 'admin', '2020-10-01 09:27:56', 'admin', '2020-10-02 00:46:02');
INSERT INTO `st_permission` VALUES (311, '3', '详情', 'NacosServicesIndexDetail', '/nacos/v1/ns/catalog/service', NULL, NULL, 3, 0, 0, '275,280,282', '服务列表', 'GET', '1', 0, 'admin', '2020-10-01 09:27:56', 'admin', '2020-10-02 00:46:02');
INSERT INTO `st_permission` VALUES (312, '3', '删除', 'NacosServicesIndexDelete', '/nacos/v1/ns/service', NULL, NULL, 4, 0, 0, '275,280,282', '服务列表', 'DELETE', '1', 0, 'admin', '2020-10-01 09:27:56', 'admin', '2020-10-02 00:46:02');
INSERT INTO `st_permission` VALUES (313, '3', '编辑服务', 'NacosServiceDetailEdit', '/nacos/v1/ns/service', NULL, NULL, 1, 0, 0, '275,280,296', '服务详情', 'PUT', '1', 0, 'admin', '2020-10-01 09:27:56', 'admin', '2020-10-02 01:30:06');
INSERT INTO `st_permission` VALUES (314, '3', '编辑服务集群', 'NacosServiceClusterEdit', '/nacos/v1/ns/cluster', NULL, NULL, 2, 0, 0, '275,280,296', '服务详情', 'PUT', '1', 0, 'admin', '2020-10-01 09:27:56', 'admin', '2020-10-02 01:32:28');
INSERT INTO `st_permission` VALUES (315, '3', '查询服务实例', 'NacosServiceInstancesSearch', '/nacos/v1/ns/catalog/instances', NULL, NULL, 3, 0, 0, '275,280,296', '服务详情', 'GET', '1', 0, 'admin', '2020-10-01 09:27:56', 'admin', '2020-10-02 01:30:06');
INSERT INTO `st_permission` VALUES (316, '3', '编辑服务实例', 'NacosServiceInstanceEdit', '/nacos/v1/ns/instance', NULL, NULL, 4, 0, 0, '275,280,296', '服务详情', 'PUT', '1', 0, 'admin', '2020-10-01 09:27:56', 'admin', '2020-10-02 01:30:06');
INSERT INTO `st_permission` VALUES (317, '3', '查询', 'NacosConfigsHistorySearch', '/nacos/v1/cs/history', NULL, NULL, 1, 0, 0, '275,278,285', '历史版本', 'GET', '1', 0, 'admin', '2020-10-01 09:27:56', 'admin', '2020-10-02 00:25:04');
INSERT INTO `st_permission` VALUES (318, '3', '详情', 'NacosConfigsHistoryDetail', '/nacos/v1/cs/history', NULL, NULL, 2, 0, 0, '275,278,285', '历史版本', 'GET', '1', 0, 'admin', '2020-10-01 09:27:56', 'admin', '2020-10-02 00:25:04');
INSERT INTO `st_permission` VALUES (319, '3', '回滚', 'NacosConfigsHistoryRollback', '/nacos/v1/cs/configs', NULL, NULL, 3, 0, 0, '275,278,285', '历史版本', 'POST', '1', 0, 'admin', '2020-10-01 09:27:56', 'admin', '2020-10-02 00:25:04');
INSERT INTO `st_permission` VALUES (320, '2', 'Mock数据', 'KafkaMocker', 'mocker', NULL, NULL, 4, 0, 0, '103', 'Kafka管理', '', '1', 0, 'admin', '2018-09-27 19:29:37', 'admin', '2020-07-20 23:55:34');
INSERT INTO `st_permission` VALUES (321, '5', '用户验证码', 'SSO_CAPTCHA', '/auth/captcha/*', NULL, NULL, 1000, 0, 0, '1,2', NULL, NULL, '1', 0, 'admin', '2018-08-15 18:09:59', 'admin', '2019-11-14 21:03:06');
INSERT INTO `st_permission` VALUES (322, '2', 'Mock GPS', 'MockGPS', 'MockGPS', NULL, NULL, 6, 0, 0, '103', 'Kafka管理', '', '1', 0, 'admin', '2018-09-27 19:29:37', 'admin', '2021-01-02 07:28:31');
INSERT INTO `st_permission` VALUES (323, '3', '执行Mock', 'mockExe', '/kafka/topic/ext/mock', NULL, NULL, 1, 0, 0, '103,320', 'Mock数据', 'POST', '1', 0, 'admin', '2020-12-09 14:15:10', NULL, NULL);
INSERT INTO `st_permission` VALUES (324, '3', '执行MockGPS', 'mockGPSExe', '/kafka/gps/mock', NULL, NULL, 1, 0, 0, '103,322', 'MockGPS', 'POST', '1', 0, 'admin', '2020-12-09 14:15:10', 'admin', '2020-12-09 14:25:13');
INSERT INTO `st_permission` VALUES (325, '1', 'RocketMQ管理', 'RocketMQ', 'rocketMQ', NULL, 'list', 11, 0, 0, '0', NULL, NULL, '0', 0, 'admin', '2021-01-08 12:11:10', 'admin', '2021-01-24 14:42:18');
INSERT INTO `st_permission` VALUES (326, '2', '驾驶舱', 'RocketMQDashboard', 'dashboard', NULL, NULL, 1, 0, 0, '325', 'RocketMQ Management', NULL, '1', 0, 'admin', '2021-01-08 12:12:31', NULL, NULL);
INSERT INTO `st_permission` VALUES (327, '2', '集群管理', 'RocketMQCluster', 'cluster', NULL, NULL, 2, 0, 0, '325', 'RocketMQ管理', NULL, '1', 0, 'admin', '2021-01-08 12:13:34', 'admin', '2021-01-08 12:13:55');
INSERT INTO `st_permission` VALUES (328, '2', '主题管理', 'RocketMQTopic', 'topic', NULL, NULL, 3, 0, 0, '325', 'RocketMQ管理', NULL, '1', 0, 'admin', '2021-01-08 12:14:33', NULL, NULL);
INSERT INTO `st_permission` VALUES (329, '2', '消费者管理', 'RocketMQConsumer', 'consumer', NULL, NULL, 4, 0, 0, '325', 'RocketMQ管理', NULL, '1', 0, 'admin', '2021-01-08 12:28:47', NULL, NULL);
INSERT INTO `st_permission` VALUES (330, '2', '生产者管理', 'RocketMQProducer', 'producer', NULL, NULL, 5, 0, 0, '325', 'RocketMQ管理', NULL, '1', 0, 'admin', '2021-01-08 12:29:21', NULL, NULL);
INSERT INTO `st_permission` VALUES (331, '2', '消息搜索', 'RocketMQMessage', 'message', NULL, NULL, 6, 0, 0, '325', 'RocketMQ管理', NULL, '1', 0, 'admin', '2021-01-08 12:29:56', 'admin', '2021-01-08 12:30:04');
INSERT INTO `st_permission` VALUES (332, '3', '查询', 'RocketMQClusterSearch', '/kafka/rocketmq/cluster', NULL, NULL, 1, 1, 0, '325,327', '集群管理', 'GET', '1', 0, 'admin', '2018-09-06 10:27:18', 'admin', '2020-06-02 13:54:18');
INSERT INTO `st_permission` VALUES (333, '3', '查询Topic', 'RocketMQDashboardTopic', '/kafka/rocketmq/dashboard/topicCurrent', NULL, NULL, 1, 1, 0, '325,326', '驾驶舱', 'GET', '1', 0, 'admin', '2018-09-06 10:27:18', 'admin', '2021-01-08 12:49:54');
INSERT INTO `st_permission` VALUES (334, '3', '查询Broker', 'RocketMQDashboardBroker', '/kafka/rocketmq/dashboard/broker', NULL, NULL, 2, 1, 0, '325,326', '驾驶舱', 'GET', '1', 0, 'admin', '2018-09-06 10:27:18', 'admin', '2021-01-08 12:49:48');
INSERT INTO `st_permission` VALUES (335, '3', '查询TopicTrend', 'RocketMQDashboardTopicTrend', '/kafka/rocketmq/dashboard/topic', NULL, NULL, 3, 1, 0, '325,326', '驾驶舱', 'GET', '1', 0, 'admin', '2018-09-06 10:27:18', 'admin', '2021-01-08 12:49:54');
INSERT INTO `st_permission` VALUES (336, '3', '主题查询', 'ROCKET_MQ_TOPIC_SEARCH', '/kafka/rocketmq/topic', NULL, NULL, 1, 0, 0, '325,328', '主题管理', 'GET', '1', 0, 'admin', '2021-01-11 14:02:29', NULL, NULL);
INSERT INTO `st_permission` VALUES (337, '1', 'IaaS管理', 'IaaSManagement', 'IaaS', '/sys/project/code', 'tree', 1, 1, 0, '119', 'Cloud 管理', NULL, '1', 0, 'admin', '2019-11-04 21:26:12', 'admin', '2021-01-30 02:25:36');
INSERT INTO `st_permission` VALUES (338, '2', 'Database', 'IaaSDatabase', 'database', NULL, NULL, 2, 0, 0, '119,337', 'IaaS管理', '', '1', 0, 'admin', '2018-08-15 09:53:39', 'admin', '2021-01-30 02:35:59');
INSERT INTO `st_permission` VALUES (339, '3', '查询', 'IAAS_DATABASE_SEARCH', '/sys/iaas/database/{page:[0-9]+}/{size:[0-9]+}', NULL, NULL, 1, 0, 0, '119,337,338', 'Database', 'GET', '1', 0, 'admin', '2018-08-11 13:58:44', 'admin', '2021-01-30 05:24:13');
INSERT INTO `st_permission` VALUES (340, '3', '添加', 'IAAS_DATABASE_ADD', '/sys/iaas/database', NULL, NULL, 2, 1, 0, '119,337,338', 'Database', 'POST', '1', 0, 'admin', '2018-09-26 11:07:13', 'admin', '2018-09-26 11:12:06');
INSERT INTO `st_permission` VALUES (341, '3', '修改', 'IAAS_DATABASE_EDIT', '/sys/iaas/database/{id:[0-9]+}', NULL, NULL, 3, 1, 0, '119,337,338', 'Database', 'PUT', '1', 0, 'admin', '2018-09-26 11:07:13', 'admin', '2021-01-30 05:32:37');

-- ----------------------------
-- Table structure for st_position
-- ----------------------------
DROP TABLE IF EXISTS `st_position`;
CREATE TABLE `st_position`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `code` varchar(64) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '职位编码',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '职位名称',
  `sort` int(4) NOT NULL COMMENT '显示顺序',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '备注',
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_at` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_at` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_code`(`code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci COMMENT = '职位信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of st_position
-- ----------------------------
INSERT INTO `st_position` VALUES (1, 'CEO', 'CEO', 1, NULL, '1', 'admin', '2020-07-04 09:58:00', 'admin', '2020-07-04 09:58:00');
INSERT INTO `st_position` VALUES (2, 'TEST_T1', '初级测试', 101, NULL, '1', 'admin', '2020-07-04 13:27:25', '', '2020-07-04 13:27:25');
INSERT INTO `st_position` VALUES (3, 'DEV_JAVA_T1', '初级Java开发', 201, NULL, '1', 'admin', '2020-07-04 13:28:11', 'admin', '2020-07-04 13:28:11');
INSERT INTO `st_position` VALUES (4, 'MANAGER_T1', '部门经理', 2, NULL, '1', 'admin', '2020-07-05 03:22:03', '', '2020-07-05 03:22:03');
INSERT INTO `st_position` VALUES (5, 'MANAGER_T3', '总经理', 1, NULL, '1', 'admin', '2020-07-05 03:22:28', '', '2020-07-05 03:22:28');

-- ----------------------------
-- Table structure for st_role
-- ----------------------------
DROP TABLE IF EXISTS `st_role`;
CREATE TABLE `st_role`  (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `NAME` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '名称',
  `CODE` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '代码',
  `TYPE` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '2' COMMENT '类型: 1.系统 2.自定义)',
  `DESCRIPTION` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  `STATUS` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '1' COMMENT '状态: 0.禁用 1.启动',
  `deleted` tinyint(1) NULL DEFAULT 0 COMMENT '删除标志（0代表存在 1代表删除）',
  `CREATE_BY` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `CREATE_AT` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `UPDATE_BY` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `UPDATE_AT` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`ID`) USING BTREE,
  UNIQUE INDEX `UK_ST_R_CODE`(`CODE`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '后台角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of st_role
-- ----------------------------
INSERT INTO `st_role` VALUES (1, '超级管理员', 'SUPER_ADMIN', '0', '', '1', 0, 'admin', '2018-08-11 23:51:35', NULL, NULL);
INSERT INTO `st_role` VALUES (2, '系统管理员', 'ADMIN_ROLE', '1', '平台管理', '1', 0, 'admin', '2018-08-04 15:56:42', 'admin', '2020-04-08 22:29:47');
INSERT INTO `st_role` VALUES (3, '系统用户', 'SYS_ROLE', '1', 'abc', '1', 0, 'admin', '2018-08-04 15:56:42', 'admin', '2020-03-26 14:22:13');
INSERT INTO `st_role` VALUES (4, '测试01', 'CH_TEST', '2', 'test', '1', 0, 'admin', '2018-08-18 10:21:37', 'admin', '2019-10-28 21:26:13');
INSERT INTO `st_role` VALUES (5, 'Kafka管理员', 'Kafka_Admin', '2', '', '1', 0, 'admin', '2020-04-21 10:24:51', 'admin', '2020-06-07 08:45:05');
INSERT INTO `st_role` VALUES (6, 'Kafka观察员', 'KAFKA_VIEWER', '2', '', '1', 0, 'admin', '2020-04-21 10:26:20', NULL, NULL);
INSERT INTO `st_role` VALUES (7, '极客管理员', 'WIKI_ADMIN', '2', '', '1', 0, 'admin', '2020-05-27 06:00:29', 'admin', '2020-06-07 08:45:11');
INSERT INTO `st_role` VALUES (8, 'canal管理员', 'CANAL_ADMIN', '2', '', '1', 0, 'admin', '2020-06-07 01:57:52', NULL, NULL);
INSERT INTO `st_role` VALUES (9, '测试1', 'TEST1', '2', NULL, '0', 0, NULL, '2020-09-06 10:14:53', NULL, '2020-09-06 10:31:24');
INSERT INTO `st_role` VALUES (10, '测试2', 'TEST2', '1', NULL, '1', 0, NULL, '2020-09-06 10:32:50', NULL, '2020-09-06 10:32:50');
INSERT INTO `st_role` VALUES (11, '测试3', 'TEST3', '1', NULL, '0', 0, NULL, '2020-09-06 10:33:06', NULL, '2020-09-06 10:33:06');
INSERT INTO `st_role` VALUES (12, 'Nacos Admin', 'NACOS_ADMIN', '2', '', '1', 0, 'admin', '2020-10-02 00:43:32', NULL, NULL);

-- ----------------------------
-- Table structure for st_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `st_role_menu`;
CREATE TABLE `st_role_menu`  (
  `ROLE_ID` bigint(20) NOT NULL COMMENT '角色ID',
  `PERMISSION_ID` bigint(20) NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (`ROLE_ID`, `PERMISSION_ID`) USING BTREE,
  INDEX `IDX_ST_RM_MENU_ID`(`PERMISSION_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_unicode_ci COMMENT = '后台角色菜单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of st_role_menu
-- ----------------------------
INSERT INTO `st_role_menu` VALUES (2, 1);
INSERT INTO `st_role_menu` VALUES (3, 1);
INSERT INTO `st_role_menu` VALUES (5, 1);
INSERT INTO `st_role_menu` VALUES (2, 2);
INSERT INTO `st_role_menu` VALUES (3, 2);
INSERT INTO `st_role_menu` VALUES (2, 3);
INSERT INTO `st_role_menu` VALUES (3, 3);
INSERT INTO `st_role_menu` VALUES (2, 4);
INSERT INTO `st_role_menu` VALUES (3, 4);
INSERT INTO `st_role_menu` VALUES (2, 5);
INSERT INTO `st_role_menu` VALUES (3, 5);
INSERT INTO `st_role_menu` VALUES (2, 6);
INSERT INTO `st_role_menu` VALUES (3, 6);
INSERT INTO `st_role_menu` VALUES (5, 7);
INSERT INTO `st_role_menu` VALUES (2, 8);
INSERT INTO `st_role_menu` VALUES (3, 8);
INSERT INTO `st_role_menu` VALUES (2, 9);
INSERT INTO `st_role_menu` VALUES (3, 9);
INSERT INTO `st_role_menu` VALUES (2, 10);
INSERT INTO `st_role_menu` VALUES (3, 10);
INSERT INTO `st_role_menu` VALUES (2, 11);
INSERT INTO `st_role_menu` VALUES (3, 11);
INSERT INTO `st_role_menu` VALUES (2, 12);
INSERT INTO `st_role_menu` VALUES (3, 12);
INSERT INTO `st_role_menu` VALUES (2, 13);
INSERT INTO `st_role_menu` VALUES (3, 13);
INSERT INTO `st_role_menu` VALUES (2, 14);
INSERT INTO `st_role_menu` VALUES (3, 14);
INSERT INTO `st_role_menu` VALUES (2, 15);
INSERT INTO `st_role_menu` VALUES (3, 15);
INSERT INTO `st_role_menu` VALUES (2, 16);
INSERT INTO `st_role_menu` VALUES (3, 16);
INSERT INTO `st_role_menu` VALUES (2, 17);
INSERT INTO `st_role_menu` VALUES (3, 17);
INSERT INTO `st_role_menu` VALUES (2, 18);
INSERT INTO `st_role_menu` VALUES (3, 18);
INSERT INTO `st_role_menu` VALUES (2, 19);
INSERT INTO `st_role_menu` VALUES (3, 19);
INSERT INTO `st_role_menu` VALUES (2, 20);
INSERT INTO `st_role_menu` VALUES (3, 20);
INSERT INTO `st_role_menu` VALUES (2, 21);
INSERT INTO `st_role_menu` VALUES (3, 21);
INSERT INTO `st_role_menu` VALUES (2, 22);
INSERT INTO `st_role_menu` VALUES (3, 22);
INSERT INTO `st_role_menu` VALUES (3, 23);
INSERT INTO `st_role_menu` VALUES (3, 24);
INSERT INTO `st_role_menu` VALUES (2, 25);
INSERT INTO `st_role_menu` VALUES (3, 25);
INSERT INTO `st_role_menu` VALUES (2, 26);
INSERT INTO `st_role_menu` VALUES (3, 26);
INSERT INTO `st_role_menu` VALUES (2, 27);
INSERT INTO `st_role_menu` VALUES (3, 27);
INSERT INTO `st_role_menu` VALUES (2, 28);
INSERT INTO `st_role_menu` VALUES (3, 28);
INSERT INTO `st_role_menu` VALUES (2, 29);
INSERT INTO `st_role_menu` VALUES (3, 29);

-- ----------------------------
-- Table structure for st_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `st_role_permission`;
CREATE TABLE `st_role_permission`  (
  `ROLE_ID` bigint(20) NOT NULL COMMENT '角色ID',
  `PERMISSION_ID` bigint(20) NOT NULL COMMENT '用户ID',
  PRIMARY KEY (`ROLE_ID`, `PERMISSION_ID`) USING BTREE,
  INDEX `IDX_ST_RP_PERMISSION_ID`(`PERMISSION_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '后台角色权限表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of st_role_permission
-- ----------------------------
INSERT INTO `st_role_permission` VALUES (2, 1);
INSERT INTO `st_role_permission` VALUES (4, 1);
INSERT INTO `st_role_permission` VALUES (2, 2);
INSERT INTO `st_role_permission` VALUES (4, 2);
INSERT INTO `st_role_permission` VALUES (2, 3);
INSERT INTO `st_role_permission` VALUES (2, 4);
INSERT INTO `st_role_permission` VALUES (2, 5);
INSERT INTO `st_role_permission` VALUES (2, 6);
INSERT INTO `st_role_permission` VALUES (2, 7);
INSERT INTO `st_role_permission` VALUES (2, 8);
INSERT INTO `st_role_permission` VALUES (2, 9);
INSERT INTO `st_role_permission` VALUES (2, 10);
INSERT INTO `st_role_permission` VALUES (2, 11);
INSERT INTO `st_role_permission` VALUES (2, 12);
INSERT INTO `st_role_permission` VALUES (2, 13);
INSERT INTO `st_role_permission` VALUES (2, 14);
INSERT INTO `st_role_permission` VALUES (2, 15);
INSERT INTO `st_role_permission` VALUES (2, 16);
INSERT INTO `st_role_permission` VALUES (2, 17);
INSERT INTO `st_role_permission` VALUES (2, 18);
INSERT INTO `st_role_permission` VALUES (2, 19);
INSERT INTO `st_role_permission` VALUES (2, 20);
INSERT INTO `st_role_permission` VALUES (2, 21);
INSERT INTO `st_role_permission` VALUES (3, 22);
INSERT INTO `st_role_permission` VALUES (7, 22);
INSERT INTO `st_role_permission` VALUES (2, 24);
INSERT INTO `st_role_permission` VALUES (8, 25);
INSERT INTO `st_role_permission` VALUES (8, 27);
INSERT INTO `st_role_permission` VALUES (2, 28);
INSERT INTO `st_role_permission` VALUES (8, 29);
INSERT INTO `st_role_permission` VALUES (8, 30);
INSERT INTO `st_role_permission` VALUES (4, 35);
INSERT INTO `st_role_permission` VALUES (3, 36);
INSERT INTO `st_role_permission` VALUES (7, 36);
INSERT INTO `st_role_permission` VALUES (3, 37);
INSERT INTO `st_role_permission` VALUES (7, 37);
INSERT INTO `st_role_permission` VALUES (2, 59);
INSERT INTO `st_role_permission` VALUES (3, 66);
INSERT INTO `st_role_permission` VALUES (7, 66);
INSERT INTO `st_role_permission` VALUES (5, 76);
INSERT INTO `st_role_permission` VALUES (6, 76);
INSERT INTO `st_role_permission` VALUES (5, 77);
INSERT INTO `st_role_permission` VALUES (6, 77);
INSERT INTO `st_role_permission` VALUES (5, 78);
INSERT INTO `st_role_permission` VALUES (6, 78);
INSERT INTO `st_role_permission` VALUES (5, 81);
INSERT INTO `st_role_permission` VALUES (6, 81);
INSERT INTO `st_role_permission` VALUES (8, 94);
INSERT INTO `st_role_permission` VALUES (8, 95);
INSERT INTO `st_role_permission` VALUES (5, 103);
INSERT INTO `st_role_permission` VALUES (6, 103);
INSERT INTO `st_role_permission` VALUES (5, 104);
INSERT INTO `st_role_permission` VALUES (5, 105);
INSERT INTO `st_role_permission` VALUES (5, 106);
INSERT INTO `st_role_permission` VALUES (5, 107);
INSERT INTO `st_role_permission` VALUES (5, 108);
INSERT INTO `st_role_permission` VALUES (6, 108);
INSERT INTO `st_role_permission` VALUES (5, 109);
INSERT INTO `st_role_permission` VALUES (6, 109);
INSERT INTO `st_role_permission` VALUES (5, 110);
INSERT INTO `st_role_permission` VALUES (6, 110);
INSERT INTO `st_role_permission` VALUES (5, 111);
INSERT INTO `st_role_permission` VALUES (5, 112);
INSERT INTO `st_role_permission` VALUES (3, 113);
INSERT INTO `st_role_permission` VALUES (7, 113);
INSERT INTO `st_role_permission` VALUES (3, 117);
INSERT INTO `st_role_permission` VALUES (7, 117);
INSERT INTO `st_role_permission` VALUES (2, 129);
INSERT INTO `st_role_permission` VALUES (5, 137);
INSERT INTO `st_role_permission` VALUES (6, 137);
INSERT INTO `st_role_permission` VALUES (5, 138);
INSERT INTO `st_role_permission` VALUES (6, 138);
INSERT INTO `st_role_permission` VALUES (5, 139);
INSERT INTO `st_role_permission` VALUES (6, 139);
INSERT INTO `st_role_permission` VALUES (5, 142);
INSERT INTO `st_role_permission` VALUES (6, 142);
INSERT INTO `st_role_permission` VALUES (5, 146);
INSERT INTO `st_role_permission` VALUES (5, 147);
INSERT INTO `st_role_permission` VALUES (6, 147);
INSERT INTO `st_role_permission` VALUES (5, 148);
INSERT INTO `st_role_permission` VALUES (5, 149);
INSERT INTO `st_role_permission` VALUES (6, 149);
INSERT INTO `st_role_permission` VALUES (3, 153);
INSERT INTO `st_role_permission` VALUES (7, 153);
INSERT INTO `st_role_permission` VALUES (3, 154);
INSERT INTO `st_role_permission` VALUES (7, 154);
INSERT INTO `st_role_permission` VALUES (3, 155);
INSERT INTO `st_role_permission` VALUES (7, 155);
INSERT INTO `st_role_permission` VALUES (3, 156);
INSERT INTO `st_role_permission` VALUES (7, 156);
INSERT INTO `st_role_permission` VALUES (3, 157);
INSERT INTO `st_role_permission` VALUES (7, 157);
INSERT INTO `st_role_permission` VALUES (3, 160);
INSERT INTO `st_role_permission` VALUES (7, 160);
INSERT INTO `st_role_permission` VALUES (3, 163);
INSERT INTO `st_role_permission` VALUES (7, 163);
INSERT INTO `st_role_permission` VALUES (3, 164);
INSERT INTO `st_role_permission` VALUES (7, 164);
INSERT INTO `st_role_permission` VALUES (3, 167);
INSERT INTO `st_role_permission` VALUES (7, 167);
INSERT INTO `st_role_permission` VALUES (3, 168);
INSERT INTO `st_role_permission` VALUES (7, 168);
INSERT INTO `st_role_permission` VALUES (3, 169);
INSERT INTO `st_role_permission` VALUES (7, 169);
INSERT INTO `st_role_permission` VALUES (3, 170);
INSERT INTO `st_role_permission` VALUES (7, 170);
INSERT INTO `st_role_permission` VALUES (3, 171);
INSERT INTO `st_role_permission` VALUES (7, 171);
INSERT INTO `st_role_permission` VALUES (3, 172);
INSERT INTO `st_role_permission` VALUES (7, 172);
INSERT INTO `st_role_permission` VALUES (3, 173);
INSERT INTO `st_role_permission` VALUES (7, 173);
INSERT INTO `st_role_permission` VALUES (3, 174);
INSERT INTO `st_role_permission` VALUES (7, 174);
INSERT INTO `st_role_permission` VALUES (3, 175);
INSERT INTO `st_role_permission` VALUES (7, 175);
INSERT INTO `st_role_permission` VALUES (3, 176);
INSERT INTO `st_role_permission` VALUES (7, 176);
INSERT INTO `st_role_permission` VALUES (3, 177);
INSERT INTO `st_role_permission` VALUES (7, 177);
INSERT INTO `st_role_permission` VALUES (3, 178);
INSERT INTO `st_role_permission` VALUES (7, 178);
INSERT INTO `st_role_permission` VALUES (3, 179);
INSERT INTO `st_role_permission` VALUES (7, 179);
INSERT INTO `st_role_permission` VALUES (3, 180);
INSERT INTO `st_role_permission` VALUES (7, 180);
INSERT INTO `st_role_permission` VALUES (3, 181);
INSERT INTO `st_role_permission` VALUES (7, 181);
INSERT INTO `st_role_permission` VALUES (3, 182);
INSERT INTO `st_role_permission` VALUES (7, 182);
INSERT INTO `st_role_permission` VALUES (3, 183);
INSERT INTO `st_role_permission` VALUES (7, 183);
INSERT INTO `st_role_permission` VALUES (3, 184);
INSERT INTO `st_role_permission` VALUES (7, 184);
INSERT INTO `st_role_permission` VALUES (3, 185);
INSERT INTO `st_role_permission` VALUES (7, 185);
INSERT INTO `st_role_permission` VALUES (3, 186);
INSERT INTO `st_role_permission` VALUES (7, 186);
INSERT INTO `st_role_permission` VALUES (3, 187);
INSERT INTO `st_role_permission` VALUES (7, 187);
INSERT INTO `st_role_permission` VALUES (2, 188);
INSERT INTO `st_role_permission` VALUES (3, 189);
INSERT INTO `st_role_permission` VALUES (7, 189);
INSERT INTO `st_role_permission` VALUES (3, 190);
INSERT INTO `st_role_permission` VALUES (7, 190);
INSERT INTO `st_role_permission` VALUES (3, 191);
INSERT INTO `st_role_permission` VALUES (7, 191);
INSERT INTO `st_role_permission` VALUES (3, 192);
INSERT INTO `st_role_permission` VALUES (7, 192);
INSERT INTO `st_role_permission` VALUES (2, 193);
INSERT INTO `st_role_permission` VALUES (2, 194);
INSERT INTO `st_role_permission` VALUES (3, 195);
INSERT INTO `st_role_permission` VALUES (7, 195);
INSERT INTO `st_role_permission` VALUES (3, 198);
INSERT INTO `st_role_permission` VALUES (7, 198);
INSERT INTO `st_role_permission` VALUES (3, 199);
INSERT INTO `st_role_permission` VALUES (7, 199);
INSERT INTO `st_role_permission` VALUES (3, 201);
INSERT INTO `st_role_permission` VALUES (7, 201);
INSERT INTO `st_role_permission` VALUES (3, 202);
INSERT INTO `st_role_permission` VALUES (7, 202);
INSERT INTO `st_role_permission` VALUES (3, 203);
INSERT INTO `st_role_permission` VALUES (7, 203);
INSERT INTO `st_role_permission` VALUES (3, 204);
INSERT INTO `st_role_permission` VALUES (7, 204);
INSERT INTO `st_role_permission` VALUES (3, 205);
INSERT INTO `st_role_permission` VALUES (7, 205);
INSERT INTO `st_role_permission` VALUES (3, 206);
INSERT INTO `st_role_permission` VALUES (7, 206);
INSERT INTO `st_role_permission` VALUES (7, 207);
INSERT INTO `st_role_permission` VALUES (7, 208);
INSERT INTO `st_role_permission` VALUES (7, 209);
INSERT INTO `st_role_permission` VALUES (7, 210);
INSERT INTO `st_role_permission` VALUES (7, 211);
INSERT INTO `st_role_permission` VALUES (7, 212);
INSERT INTO `st_role_permission` VALUES (8, 213);
INSERT INTO `st_role_permission` VALUES (8, 214);
INSERT INTO `st_role_permission` VALUES (8, 215);
INSERT INTO `st_role_permission` VALUES (8, 216);
INSERT INTO `st_role_permission` VALUES (8, 217);
INSERT INTO `st_role_permission` VALUES (8, 218);
INSERT INTO `st_role_permission` VALUES (8, 219);
INSERT INTO `st_role_permission` VALUES (8, 220);
INSERT INTO `st_role_permission` VALUES (8, 221);
INSERT INTO `st_role_permission` VALUES (8, 222);
INSERT INTO `st_role_permission` VALUES (8, 223);
INSERT INTO `st_role_permission` VALUES (8, 224);
INSERT INTO `st_role_permission` VALUES (8, 225);
INSERT INTO `st_role_permission` VALUES (8, 226);
INSERT INTO `st_role_permission` VALUES (8, 227);
INSERT INTO `st_role_permission` VALUES (8, 228);
INSERT INTO `st_role_permission` VALUES (8, 230);
INSERT INTO `st_role_permission` VALUES (8, 231);
INSERT INTO `st_role_permission` VALUES (8, 232);
INSERT INTO `st_role_permission` VALUES (8, 234);
INSERT INTO `st_role_permission` VALUES (7, 252);
INSERT INTO `st_role_permission` VALUES (7, 253);
INSERT INTO `st_role_permission` VALUES (7, 254);
INSERT INTO `st_role_permission` VALUES (2, 255);
INSERT INTO `st_role_permission` VALUES (2, 256);
INSERT INTO `st_role_permission` VALUES (2, 257);
INSERT INTO `st_role_permission` VALUES (2, 258);
INSERT INTO `st_role_permission` VALUES (2, 259);
INSERT INTO `st_role_permission` VALUES (2, 260);
INSERT INTO `st_role_permission` VALUES (2, 262);
INSERT INTO `st_role_permission` VALUES (2, 263);
INSERT INTO `st_role_permission` VALUES (2, 264);
INSERT INTO `st_role_permission` VALUES (2, 265);
INSERT INTO `st_role_permission` VALUES (2, 266);
INSERT INTO `st_role_permission` VALUES (2, 267);
INSERT INTO `st_role_permission` VALUES (2, 268);
INSERT INTO `st_role_permission` VALUES (2, 269);
INSERT INTO `st_role_permission` VALUES (2, 270);
INSERT INTO `st_role_permission` VALUES (2, 271);
INSERT INTO `st_role_permission` VALUES (2, 274);
INSERT INTO `st_role_permission` VALUES (12, 275);
INSERT INTO `st_role_permission` VALUES (12, 276);
INSERT INTO `st_role_permission` VALUES (12, 277);
INSERT INTO `st_role_permission` VALUES (12, 278);
INSERT INTO `st_role_permission` VALUES (12, 279);
INSERT INTO `st_role_permission` VALUES (12, 280);
INSERT INTO `st_role_permission` VALUES (12, 281);
INSERT INTO `st_role_permission` VALUES (12, 282);
INSERT INTO `st_role_permission` VALUES (12, 283);
INSERT INTO `st_role_permission` VALUES (12, 284);
INSERT INTO `st_role_permission` VALUES (12, 285);
INSERT INTO `st_role_permission` VALUES (12, 286);
INSERT INTO `st_role_permission` VALUES (12, 287);
INSERT INTO `st_role_permission` VALUES (12, 288);
INSERT INTO `st_role_permission` VALUES (12, 289);
INSERT INTO `st_role_permission` VALUES (12, 290);
INSERT INTO `st_role_permission` VALUES (12, 291);
INSERT INTO `st_role_permission` VALUES (12, 292);
INSERT INTO `st_role_permission` VALUES (12, 293);
INSERT INTO `st_role_permission` VALUES (12, 294);
INSERT INTO `st_role_permission` VALUES (12, 295);
INSERT INTO `st_role_permission` VALUES (12, 296);
INSERT INTO `st_role_permission` VALUES (12, 297);
INSERT INTO `st_role_permission` VALUES (12, 298);
INSERT INTO `st_role_permission` VALUES (12, 299);
INSERT INTO `st_role_permission` VALUES (12, 300);
INSERT INTO `st_role_permission` VALUES (12, 301);
INSERT INTO `st_role_permission` VALUES (12, 302);
INSERT INTO `st_role_permission` VALUES (12, 303);
INSERT INTO `st_role_permission` VALUES (12, 304);
INSERT INTO `st_role_permission` VALUES (12, 305);
INSERT INTO `st_role_permission` VALUES (12, 306);
INSERT INTO `st_role_permission` VALUES (12, 307);
INSERT INTO `st_role_permission` VALUES (12, 308);
INSERT INTO `st_role_permission` VALUES (12, 309);
INSERT INTO `st_role_permission` VALUES (12, 310);
INSERT INTO `st_role_permission` VALUES (12, 311);
INSERT INTO `st_role_permission` VALUES (12, 312);
INSERT INTO `st_role_permission` VALUES (12, 313);
INSERT INTO `st_role_permission` VALUES (12, 314);
INSERT INTO `st_role_permission` VALUES (12, 315);
INSERT INTO `st_role_permission` VALUES (12, 316);
INSERT INTO `st_role_permission` VALUES (12, 317);
INSERT INTO `st_role_permission` VALUES (12, 318);
INSERT INTO `st_role_permission` VALUES (12, 319);

-- ----------------------------
-- Table structure for st_user
-- ----------------------------
DROP TABLE IF EXISTS `st_user`;
CREATE TABLE `st_user`  (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `USER_ID` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户编号',
  `USERNAME` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户帐号',
  `PASSWORD` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
  `REAL_NAME` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '真实姓名',
  `BIRTH` date NULL DEFAULT NULL COMMENT '出生日期',
  `SEX` tinyint(1) NULL DEFAULT NULL COMMENT '性别: 0.女 1.男',
  `EMAIL` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '电子邮箱',
  `MOBILE` char(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机号码',
  `LOCKED` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '0' COMMENT '用户是否锁定: 0.否 1.是',
  `EXPIRED` date NULL DEFAULT NULL COMMENT '过期日期',
  `TYPE` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '1' COMMENT '类型: 0.系统 1.普通',
  `STATUS` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '1' COMMENT '状态: 0.禁用 1.启动',
  `deleted` tinyint(1) NULL DEFAULT 0 COMMENT '删除标志（0代表存在 1代表删除）',
  `LAST_LOGIN_AT` datetime(0) NULL DEFAULT NULL COMMENT '登录时间',
  `LAST_LOGIN_IP` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户登录IP地址',
  `ERROR_COUNT` int(2) NOT NULL DEFAULT 0 COMMENT '当天登录错误次数',
  `REMARK` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `CREATE_AT` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `CREATE_BY` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `UPDATE_AT` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `UPDATE_BY` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `department_id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '组织ID',
  `position_id` bigint(20) NULL DEFAULT NULL COMMENT '职位ID',
  `department_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '组织名称',
  `position_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '职位名称',
  PRIMARY KEY (`ID`) USING BTREE,
  UNIQUE INDEX `UK_ST_U_USER_ID`(`USER_ID`) USING BTREE,
  UNIQUE INDEX `UK_ST_U_USERNAME`(`USERNAME`) USING BTREE,
  UNIQUE INDEX `UK_ST_U_EMAIL`(`EMAIL`) USING BTREE,
  INDEX `IDX_MOBILE_REAL_NAME`(`MOBILE`, `REAL_NAME`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '后台用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of st_user
-- ----------------------------
INSERT INTO `st_user` VALUES (1, '7131630043', 'admin', '$2a$10$qtu98dMdajRqbQULLjixpe7vK7c9r7Zmu84Gu7lcpD/H9X2/GUf1.', '超级管理员', '2017-02-28', 1, 'admin@ch.com', NULL, '0', NULL, '0', '1', 0, '2018-12-08 23:50:17', '0:0:0:0:0:0:0:1', 0, NULL, '2017-03-09 09:26:44', NULL, '2020-12-06 01:57:48', 'admin', '1,3', 1, '朝华科技/懂事会', 'CEO');
INSERT INTO `st_user` VALUES (2, '8900595819', 'test', '$2a$10$RSi.YFkCFhBDMeM6gHArfucTK9.CnEHF4DEB0XEmsv78QZ93CKa/K', '测试01', '2018-12-23', NULL, 'test', '152', '0', '2017-12-08', '1', '1', 0, '2018-08-12 22:40:51', '0:0:0:0:0:0:0:1', 32, '', '2018-08-05 23:02:09', 'admin', '2020-09-19 14:02:15', 'admin', '1,2,6', NULL, '朝华科技/深圳总部/大数据部门', NULL);
INSERT INTO `st_user` VALUES (6, '7378792661', 'test01', '$2a$10$IFG2LZ2pwzVG.S9eErjlW.jLi9lxkOc8TVYNhgWE9QE3GV.Zy38w6', 'test', NULL, 0, '', NULL, '0', NULL, '1', '1', 0, NULL, NULL, 0, NULL, '2018-12-26 15:28:15', NULL, '2020-09-19 13:59:58', 'admin', '1,2,6,13', NULL, '朝华科技/深圳总部/大数据部门/测试组', NULL);
INSERT INTO `st_user` VALUES (7, '0864338766', 'test02', '$2a$10$zBLon5ln.YoYVBNOafBfVOmQFfKagoMuybW1HLVp4k8g9z290681.', 'test', '2019-03-26', 1, NULL, NULL, '0', NULL, '1', '1', 0, NULL, NULL, 0, NULL, '2018-12-26 15:29:40', NULL, '2020-09-19 13:59:49', 'admin', '1,2,5', NULL, '朝华科技/深圳总部/架构部门', NULL);
INSERT INTO `st_user` VALUES (8, '6548095053', 'test03', '$2a$10$jSxfSOGESAdw/inSWHBjiOTWbH.P9LCK0yfkrifN1/XRprw.B6yne', 'test0', '2019-04-06', NULL, NULL, '123', '0', NULL, '1', '1', 0, NULL, NULL, 0, NULL, '2018-12-26 15:30:45', NULL, '2020-09-19 13:59:40', 'admin', '1,2,4,7', NULL, '朝华科技/深圳总部/业务研发部/产品组', NULL);
INSERT INTO `st_user` VALUES (9, '7985143144', 'test04', '$2a$10$NuCRKKzO4uOlR06ZOyQY2.M2bdLFpp3k5XO8PHapnmF4mXVKVk1r2', 'test', '2019-03-26', NULL, 'test01@ch.com', NULL, '0', NULL, '1', '1', 0, NULL, NULL, 0, NULL, '2018-12-26 15:31:11', NULL, '2020-09-19 13:59:24', 'admin', '1,2,10', NULL, '朝华科技/深圳总部/IT运维部', NULL);

-- ----------------------------
-- Table structure for st_user_department_position
-- ----------------------------
DROP TABLE IF EXISTS `st_user_department_position`;
CREATE TABLE `st_user_department_position`  (
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `department_id` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '组织ID',
  `position_id` bigint(20) NOT NULL COMMENT '职位ID',
  PRIMARY KEY (`user_id`, `department_id`, `position_id`) USING BTREE,
  INDEX `idx_dept_id`(`department_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_unicode_ci COMMENT = '用户与组织和职位关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of st_user_department_position
-- ----------------------------
INSERT INTO `st_user_department_position` VALUES (8, '1,2,4', 4);
INSERT INTO `st_user_department_position` VALUES (2, '1,2,4,8', 3);
INSERT INTO `st_user_department_position` VALUES (6, '1,2,4,9', 2);
INSERT INTO `st_user_department_position` VALUES (7, '1,2,4,9', 2);
INSERT INTO `st_user_department_position` VALUES (9, '1,2,4,9', 2);
INSERT INTO `st_user_department_position` VALUES (1, '1,3', 1);

-- ----------------------------
-- Table structure for st_user_role
-- ----------------------------
DROP TABLE IF EXISTS `st_user_role`;
CREATE TABLE `st_user_role`  (
  `USER_ID` bigint(20) NOT NULL COMMENT '用户ID',
  `ROLE_ID` bigint(20) NOT NULL COMMENT '角色ID',
  `STATUS` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '0' COMMENT '状态：1.用户默认角色',
  PRIMARY KEY (`USER_ID`, `ROLE_ID`) USING BTREE,
  INDEX `IDX_ST_UR_ROLE_ID`(`ROLE_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of st_user_role
-- ----------------------------
INSERT INTO `st_user_role` VALUES (1, 1, '1');
INSERT INTO `st_user_role` VALUES (1, 2, '0');
INSERT INTO `st_user_role` VALUES (1, 3, '0');
INSERT INTO `st_user_role` VALUES (1, 4, '0');
INSERT INTO `st_user_role` VALUES (2, 2, '1');
INSERT INTO `st_user_role` VALUES (2, 7, '0');
INSERT INTO `st_user_role` VALUES (6, 7, '1');
INSERT INTO `st_user_role` VALUES (7, 12, '0');

-- ----------------------------
-- Table structure for tb_article
-- ----------------------------
DROP TABLE IF EXISTS `tb_article`;
CREATE TABLE `tb_article`  (
  `t_aid` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '文章ID',
  `t_cover_image` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '封面图片',
  `t_title` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '标题',
  `t_introduce` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '简介',
  `t_content` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文章内容',
  `t_source` char(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '来源',
  `t_total` int(11) NOT NULL COMMENT '总浏览量',
  `t_weight` tinyint(4) NOT NULL COMMENT '权重：10最高 默认1',
  `t_user_id` char(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '作者ID',
  `t_category_id` int(11) NOT NULL COMMENT '类目ID',
  `t_state` tinyint(4) NOT NULL COMMENT '状态[0.删除，1.正常 2.草稿]',
  `t_create_time` datetime(0) NOT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '添加时间',
  `t_up_time` datetime(0) NOT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  PRIMARY KEY (`t_aid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '[三农网] 文章表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_article
-- ----------------------------
INSERT INTO `tb_article` VALUES (1, '[]', '这是一篇好文章', '一篇好文章', '一篇好文章', '广东三农', 100, 10, 'dwqewqeqw546181wefwerfve5', 1, 1, '2018-11-16 11:46:21', '2018-11-16 11:46:21');

-- ----------------------------
-- Table structure for tb_category
-- ----------------------------
DROP TABLE IF EXISTS `tb_category`;
CREATE TABLE `tb_category`  (
  `t_cid` int(11) NOT NULL AUTO_INCREMENT COMMENT '类目id',
  `t_title` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '类目标题',
  `t_describe` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '描述',
  `t_create_time` datetime(0) NOT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '添加时间',
  `t_up_time` datetime(0) NOT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `t_region` char(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '区域： gd.广东三农   gx.广西三农 gz.贵州',
  PRIMARY KEY (`t_cid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '[三农网] 文章类目表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_category
-- ----------------------------
INSERT INTO `tb_category` VALUES (1, '三农资讯', '三农资讯【广东三农】', '2018-11-15 16:49:15', '2018-11-15 16:49:15', 'gd');
INSERT INTO `tb_category` VALUES (2, '人物风采', '人物风采【广东三农】', '2018-11-15 16:49:38', '2018-11-15 16:49:38', 'gd');
INSERT INTO `tb_category` VALUES (3, '特色旅游', '特色旅游【广东三农】', '2018-11-15 16:49:53', '2018-11-15 16:49:53', 'gd');

-- ----------------------------
-- Table structure for tb_dept
-- ----------------------------
DROP TABLE IF EXISTS `tb_dept`;
CREATE TABLE `tb_dept`  (
  `t_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '部门id',
  `t_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '部门名称',
  `t_parent_id` int(11) NOT NULL COMMENT '上级部门',
  `t_level` tinyint(4) NOT NULL COMMENT '部门排序',
  `t_describe` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '部门描述',
  `t_up_time` datetime(0) NOT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `t_create_time` datetime(0) NOT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`t_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '[权限管理] 部门表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_dept
-- ----------------------------
INSERT INTO `tb_dept` VALUES (1, '阿里巴巴集团', 0, 0, '阿里巴巴集团', '2018-11-15 03:23:13', '2018-11-15 03:23:13');
INSERT INTO `tb_dept` VALUES (2, '阿里云', 1, 1, '阿里云科技', '2018-11-15 03:23:15', '2018-11-15 03:23:15');
INSERT INTO `tb_dept` VALUES (3, '事业部门', 2, 1, '【阿里云】事业部门', '2018-11-17 16:21:56', '2018-10-23 04:50:33');
INSERT INTO `tb_dept` VALUES (4, '开发部门', 2, 2, '【阿里云】开发部门', '2018-10-23 04:50:11', '2018-10-23 04:50:11');
INSERT INTO `tb_dept` VALUES (5, '安全部门', 2, 3, '【阿里云】安全部门', '2018-11-17 16:21:45', '2018-10-23 04:50:13');
INSERT INTO `tb_dept` VALUES (6, '淘宝网', 1, 2, '淘宝网络科技', '2018-11-15 03:23:26', '2018-11-15 03:23:26');
INSERT INTO `tb_dept` VALUES (8, '客服部门', 6, 1, '【淘宝网】客服部门', '2018-11-17 16:22:13', '2018-11-15 03:24:11');
INSERT INTO `tb_dept` VALUES (9, '菜鸟网络', 1, 3, '菜鸟网络', '2018-10-31 09:28:28', '2018-10-23 12:53:01');
INSERT INTO `tb_dept` VALUES (12, '菜鸟物流', 9, 1, '【菜鸟网络】菜鸟物流', '2018-10-31 09:28:33', '2018-10-23 12:53:01');

-- ----------------------------
-- Table structure for tb_permission
-- ----------------------------
DROP TABLE IF EXISTS `tb_permission`;
CREATE TABLE `tb_permission`  (
  `t_pid` int(11) NOT NULL AUTO_INCREMENT COMMENT '权限唯一ID',
  `t_parent_id` int(11) NOT NULL COMMENT '上级ID',
  `t_resources` char(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '权限资源 ',
  `t_title` char(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '资源名称',
  `t_icon` char(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '资源图标',
  `t_type` char(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '类型，menu或者button',
  `t_create_time` datetime(0) NOT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `t_up_time` datetime(0) NOT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `t_describe` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '权限描述',
  PRIMARY KEY (`t_pid`) USING BTREE,
  UNIQUE INDEX `t_resources`(`t_resources`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 41 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '[权限管理] 权限表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_permission
-- ----------------------------
INSERT INTO `tb_permission` VALUES (1, 0, 'pre', '权限设置', 'pre_admin', 'menu', '2018-11-17 11:20:54', '2018-11-17 11:20:54', '权限设置');
INSERT INTO `tb_permission` VALUES (2, 0, 'sys', '系统设置', 'sys_set', 'menu', '2018-11-17 11:21:31', '2018-11-17 11:21:31', '系统设置');
INSERT INTO `tb_permission` VALUES (4, 0, 'article', '文章管理', 'article_admin', 'menu', '2018-11-17 11:20:54', '2018-11-17 11:20:54', '文章管理');
INSERT INTO `tb_permission` VALUES (5, 1, 'pre_perm', '权限管理', 'pre_perm_admin', 'menu', '2018-11-17 11:20:54', '2018-11-17 11:20:54', '权限管理');
INSERT INTO `tb_permission` VALUES (6, 1, 'pre_user', '用户管理', 'pre_user_admin', 'menu', '2018-11-17 11:20:54', '2018-11-17 11:20:54', '用户管理');
INSERT INTO `tb_permission` VALUES (7, 1, 'pre_role', '角色管理', 'pre_role_admin', 'menu', '2018-11-17 11:20:54', '2018-11-17 11:20:54', '角色管理');
INSERT INTO `tb_permission` VALUES (8, 1, 'pre_dept', '部门管理', 'pre_dept_admin', 'menu', '2018-11-17 11:20:54', '2018-11-17 11:20:54', '部门管理');
INSERT INTO `tb_permission` VALUES (9, 4, 'article_write', '发布文章', 'article_article_write', 'menu', '2018-11-17 11:33:14', '2018-11-17 11:33:14', '发布文章');
INSERT INTO `tb_permission` VALUES (10, 4, 'article_my', '我的文章', 'article_article_my', 'menu', '2018-11-17 11:33:20', '2018-11-17 11:33:20', '我的文章');
INSERT INTO `tb_permission` VALUES (11, 4, 'article_category', '类目管理', 'article_article_category', 'menu', '2018-11-17 11:33:27', '2018-11-17 11:33:27', '类目管理');
INSERT INTO `tb_permission` VALUES (12, 4, 'article_list', '文章列表', 'article_article_list', 'menu', '2018-11-17 11:33:34', '2018-11-17 11:33:34', '文章列表');
INSERT INTO `tb_permission` VALUES (13, 5, 'pre_perm:new', '新增权限', '', 'button', '2018-11-17 11:20:54', '2018-11-17 11:20:54', '新增权限');
INSERT INTO `tb_permission` VALUES (14, 5, 'pre_perm:delete', '删除权限', '', 'button', '2018-11-17 11:20:54', '2018-11-17 11:20:54', '删除权限');
INSERT INTO `tb_permission` VALUES (15, 5, 'pre_perm:update', '修改权限', '', 'button', '2018-11-17 11:20:54', '2018-11-17 11:20:54', '修改权限');
INSERT INTO `tb_permission` VALUES (16, 5, 'pre_perm:view', '查看权限', '', 'button', '2018-11-17 11:20:54', '2018-11-17 11:20:54', '查看权限');
INSERT INTO `tb_permission` VALUES (17, 6, 'pre_user:new', '新增用户', '', 'button', '2018-11-17 11:20:54', '2018-11-17 11:20:54', '新增角色');
INSERT INTO `tb_permission` VALUES (18, 6, 'pre_user:delete', '删除用户', '', 'button', '2018-11-17 11:20:54', '2018-11-17 11:20:54', '删除角色');
INSERT INTO `tb_permission` VALUES (19, 6, 'pre_user:update', '修改用户', '', 'button', '2018-11-17 11:20:54', '2018-11-17 11:20:54', '修改角色');
INSERT INTO `tb_permission` VALUES (20, 6, 'pre_user:view', '查看用户', '', 'button', '2018-11-17 11:20:54', '2018-11-17 11:20:54', '查看角色');
INSERT INTO `tb_permission` VALUES (21, 7, 'pre_role:new', '新增角色', '', 'button', '2018-11-17 11:20:54', '2018-11-17 11:20:54', '新增角色');
INSERT INTO `tb_permission` VALUES (22, 7, 'pre_role:delete', '删除角色', '', 'button', '2018-11-17 11:20:54', '2018-11-17 11:20:54', '删除角色');
INSERT INTO `tb_permission` VALUES (23, 7, 'pre_role:update', '修改角色', '', 'button', '2018-11-17 11:20:54', '2018-11-17 11:20:54', '修改角色');
INSERT INTO `tb_permission` VALUES (24, 7, 'pre_role:view', '查看角色', '', 'button', '2018-11-17 11:20:54', '2018-11-17 11:20:54', '查看角色');
INSERT INTO `tb_permission` VALUES (25, 8, 'pre_dept:new', '新增部门', '', 'button', '2018-11-17 11:20:54', '2018-11-17 11:20:54', '新增部门');
INSERT INTO `tb_permission` VALUES (26, 8, 'pre_dept:delete', '删除部门', '', 'button', '2018-11-17 11:20:54', '2018-11-17 11:20:54', '删除部门');
INSERT INTO `tb_permission` VALUES (27, 8, 'pre_dept:update', '修改部门', '', 'button', '2018-11-17 11:20:54', '2018-11-17 11:20:54', '修改部门');
INSERT INTO `tb_permission` VALUES (28, 8, 'pre_dept:view', '查看部门', '', 'button', '2018-11-17 11:20:54', '2018-11-17 11:20:54', '查看部门');
INSERT INTO `tb_permission` VALUES (29, 11, 'article_category:new', '新增类目', '', 'button', '2018-11-17 11:20:54', '2018-11-17 11:20:54', '新增类目');
INSERT INTO `tb_permission` VALUES (30, 11, 'article_category:delete', '删除类目', '', 'button', '2018-11-17 11:20:54', '2018-11-17 11:20:54', '删除类目');
INSERT INTO `tb_permission` VALUES (31, 11, 'article_category:update', '修改类目', '', 'button', '2018-11-17 11:20:54', '2018-11-17 11:20:54', '修改类目');
INSERT INTO `tb_permission` VALUES (32, 11, 'article_category:view', '查看类目', '', 'button', '2018-11-17 11:20:54', '2018-11-17 11:20:54', '查看类目');
INSERT INTO `tb_permission` VALUES (33, 2, 'sys_database', '数据库监控', 'sys_database', 'menu', '2018-10-28 22:11:11', '2018-10-28 22:11:11', '数据库监控');
INSERT INTO `tb_permission` VALUES (34, 2, 'sys_logs', '系统日志', 'sys_logs', 'menu', '2018-10-28 22:11:18', '2018-10-28 22:11:18', '系统日志监控');
INSERT INTO `tb_permission` VALUES (35, 2, 'sys_wechat', '微信设置', 'sys_wechat', 'menu', '2018-10-29 22:26:54', '2018-10-29 22:26:56', '微信相关设置');
INSERT INTO `tb_permission` VALUES (36, 2, 'sys_backstage', '后台设置', 'sys_backstage', 'menu', '2018-10-29 22:29:45', '2018-10-29 22:29:47', '后台设置');
INSERT INTO `tb_permission` VALUES (38, 0, 'test', '测试权限', 'test', 'menu', '2018-11-18 12:24:32', '2018-11-18 12:24:32', '测试权限');
INSERT INTO `tb_permission` VALUES (39, 38, 'test_haha', '测试权限二', 'twqeqw', 'menu', '2018-11-18 12:32:24', '2018-11-18 12:32:24', '测试权限二');
INSERT INTO `tb_permission` VALUES (40, 39, 'testwqeqwewqe', '测试按钮', 'testwqeqwewqe', 'button', '2018-11-18 12:33:06', '2018-11-18 12:33:06', '测试按钮');

-- ----------------------------
-- Table structure for tb_role
-- ----------------------------
DROP TABLE IF EXISTS `tb_role`;
CREATE TABLE `tb_role`  (
  `t_rid` int(11) NOT NULL AUTO_INCREMENT COMMENT '系统角色ID',
  `t_describe` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '系统角色描述',
  `t_name` char(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '系统角色名称',
  `t_state` tinyint(4) NOT NULL COMMENT '系统角色状态[0.删除，1.正常]',
  `t_up_time` datetime(0) NOT NULL COMMENT '修改时间',
  `t_create_time` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`t_rid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '[权限管理] 角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_role
-- ----------------------------
INSERT INTO `tb_role` VALUES (3, '超级管理员', 'ROLE_ROOT', 1, '2018-10-23 12:32:10', '2018-10-23 12:32:13');
INSERT INTO `tb_role` VALUES (4, '管理员', 'ROLE_ADMIN', 1, '2018-10-23 12:32:29', '2018-10-23 12:32:31');
INSERT INTO `tb_role` VALUES (5, '编辑', 'ROLE_EDIT', 1, '2018-10-23 12:32:50', '2018-10-23 12:32:52');
INSERT INTO `tb_role` VALUES (6, '普通用户', 'ROLE_USER', 1, '2018-10-23 12:33:11', '2018-10-23 12:33:13');
INSERT INTO `tb_role` VALUES (9, '运维人员', 'ROLE_OPERATION', 1, '2018-11-02 17:11:00', '2018-10-31 14:48:16');
INSERT INTO `tb_role` VALUES (10, '测试用户', 'ROLE_TEST', 1, '2018-11-17 16:58:14', '2018-11-17 16:51:26');

-- ----------------------------
-- Table structure for tb_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `tb_role_permission`;
CREATE TABLE `tb_role_permission`  (
  `t_permission_id` int(11) NOT NULL COMMENT '权限ID',
  `t_role_id` int(11) NOT NULL COMMENT '角色ID'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '[权限管理] 角色和权限表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_role_permission
-- ----------------------------
INSERT INTO `tb_role_permission` VALUES (1, 3);
INSERT INTO `tb_role_permission` VALUES (2, 3);
INSERT INTO `tb_role_permission` VALUES (4, 3);
INSERT INTO `tb_role_permission` VALUES (5, 3);
INSERT INTO `tb_role_permission` VALUES (6, 3);
INSERT INTO `tb_role_permission` VALUES (7, 3);
INSERT INTO `tb_role_permission` VALUES (8, 3);
INSERT INTO `tb_role_permission` VALUES (9, 3);
INSERT INTO `tb_role_permission` VALUES (10, 3);
INSERT INTO `tb_role_permission` VALUES (11, 3);
INSERT INTO `tb_role_permission` VALUES (12, 3);
INSERT INTO `tb_role_permission` VALUES (13, 3);
INSERT INTO `tb_role_permission` VALUES (14, 3);
INSERT INTO `tb_role_permission` VALUES (15, 3);
INSERT INTO `tb_role_permission` VALUES (16, 3);
INSERT INTO `tb_role_permission` VALUES (17, 3);
INSERT INTO `tb_role_permission` VALUES (18, 3);
INSERT INTO `tb_role_permission` VALUES (19, 3);
INSERT INTO `tb_role_permission` VALUES (20, 3);
INSERT INTO `tb_role_permission` VALUES (21, 3);
INSERT INTO `tb_role_permission` VALUES (22, 3);
INSERT INTO `tb_role_permission` VALUES (23, 3);
INSERT INTO `tb_role_permission` VALUES (24, 3);
INSERT INTO `tb_role_permission` VALUES (25, 3);
INSERT INTO `tb_role_permission` VALUES (26, 3);
INSERT INTO `tb_role_permission` VALUES (27, 3);
INSERT INTO `tb_role_permission` VALUES (28, 3);
INSERT INTO `tb_role_permission` VALUES (29, 3);
INSERT INTO `tb_role_permission` VALUES (30, 3);
INSERT INTO `tb_role_permission` VALUES (31, 3);
INSERT INTO `tb_role_permission` VALUES (32, 3);
INSERT INTO `tb_role_permission` VALUES (33, 3);
INSERT INTO `tb_role_permission` VALUES (34, 3);
INSERT INTO `tb_role_permission` VALUES (35, 3);
INSERT INTO `tb_role_permission` VALUES (36, 3);
INSERT INTO `tb_role_permission` VALUES (1, 4);
INSERT INTO `tb_role_permission` VALUES (5, 4);
INSERT INTO `tb_role_permission` VALUES (6, 4);
INSERT INTO `tb_role_permission` VALUES (7, 4);
INSERT INTO `tb_role_permission` VALUES (8, 4);
INSERT INTO `tb_role_permission` VALUES (13, 4);
INSERT INTO `tb_role_permission` VALUES (14, 4);
INSERT INTO `tb_role_permission` VALUES (15, 4);
INSERT INTO `tb_role_permission` VALUES (16, 4);
INSERT INTO `tb_role_permission` VALUES (17, 4);
INSERT INTO `tb_role_permission` VALUES (18, 4);
INSERT INTO `tb_role_permission` VALUES (19, 4);
INSERT INTO `tb_role_permission` VALUES (20, 4);
INSERT INTO `tb_role_permission` VALUES (21, 4);
INSERT INTO `tb_role_permission` VALUES (22, 4);
INSERT INTO `tb_role_permission` VALUES (23, 4);
INSERT INTO `tb_role_permission` VALUES (24, 4);
INSERT INTO `tb_role_permission` VALUES (25, 4);
INSERT INTO `tb_role_permission` VALUES (26, 4);
INSERT INTO `tb_role_permission` VALUES (27, 4);
INSERT INTO `tb_role_permission` VALUES (28, 4);
INSERT INTO `tb_role_permission` VALUES (32, 5);
INSERT INTO `tb_role_permission` VALUES (4, 5);
INSERT INTO `tb_role_permission` VALUES (9, 5);
INSERT INTO `tb_role_permission` VALUES (10, 5);
INSERT INTO `tb_role_permission` VALUES (11, 5);
INSERT INTO `tb_role_permission` VALUES (12, 5);
INSERT INTO `tb_role_permission` VALUES (29, 5);
INSERT INTO `tb_role_permission` VALUES (30, 5);
INSERT INTO `tb_role_permission` VALUES (31, 5);
INSERT INTO `tb_role_permission` VALUES (16, 10);
INSERT INTO `tb_role_permission` VALUES (1, 10);
INSERT INTO `tb_role_permission` VALUES (20, 10);
INSERT INTO `tb_role_permission` VALUES (5, 10);
INSERT INTO `tb_role_permission` VALUES (6, 10);
INSERT INTO `tb_role_permission` VALUES (7, 10);
INSERT INTO `tb_role_permission` VALUES (24, 10);
INSERT INTO `tb_role_permission` VALUES (8, 10);
INSERT INTO `tb_role_permission` VALUES (27, 10);
INSERT INTO `tb_role_permission` VALUES (16, 6);
INSERT INTO `tb_role_permission` VALUES (32, 6);
INSERT INTO `tb_role_permission` VALUES (1, 6);
INSERT INTO `tb_role_permission` VALUES (20, 6);
INSERT INTO `tb_role_permission` VALUES (4, 6);
INSERT INTO `tb_role_permission` VALUES (5, 6);
INSERT INTO `tb_role_permission` VALUES (6, 6);
INSERT INTO `tb_role_permission` VALUES (7, 6);
INSERT INTO `tb_role_permission` VALUES (24, 6);
INSERT INTO `tb_role_permission` VALUES (8, 6);
INSERT INTO `tb_role_permission` VALUES (11, 6);
INSERT INTO `tb_role_permission` VALUES (28, 6);
INSERT INTO `tb_role_permission` VALUES (16, 9);
INSERT INTO `tb_role_permission` VALUES (1, 9);
INSERT INTO `tb_role_permission` VALUES (20, 9);
INSERT INTO `tb_role_permission` VALUES (5, 9);
INSERT INTO `tb_role_permission` VALUES (6, 9);

-- ----------------------------
-- Table structure for tb_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user`  (
  `t_uid` char(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户ID',
  `t_avatar` char(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '头像',
  `t_account` char(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '账号',
  `t_mail` char(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '邮箱',
  `t_open_id` char(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '微信open Id',
  `t_nickname` char(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名称',
  `t_password` char(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
  `t_gender` tinyint(4) NOT NULL COMMENT '性别[ 0.女  1.男  2.未知]',
  `t_birthday` datetime(0) NOT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '生日',
  `t_state` tinyint(4) NOT NULL COMMENT '状态 【0.禁用 1.正常 2.被删除】',
  `t_create_time` datetime(0) NOT NULL COMMENT '添加时间',
  `t_up_time` datetime(0) NOT NULL COMMENT '修改时间',
  `t_dept` int(11) NOT NULL COMMENT '部门id',
  PRIMARY KEY (`t_uid`) USING BTREE,
  UNIQUE INDEX `UK_6i5ixxulo5s2i7qoksp54tgwl`(`t_account`) USING BTREE,
  UNIQUE INDEX `t_mail`(`t_mail`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '[权限管理] 用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_user
-- ----------------------------
INSERT INTO `tb_user` VALUES ('402881e7672672df016726730baa0000', 'https://lzy-file.oss-cn-shenzhen.aliyuncs.com/2018/11/18/18/47/8e4227d68b74444bb5e77b460e1696ca.jpg', 'user_test', 'user_test@163.com', '', '隔壁小王', '$2a$10$Ks5PVJfFqqE1/gLpGStYaeLVbKMetwFN8uPpp4bSkt2F2HBrbcOlS', 0, '1975-06-03 00:00:00', 1, '2018-11-18 18:52:13', '2018-11-18 19:17:07', 3);
INSERT INTO `tb_user` VALUES ('402881e7672689ac0167268b687a0000', 'https://lzy-file.oss-cn-shenzhen.aliyuncs.com/2018/11/18/19/18/84922afda435453caffc12c7739c6bad.jpg', 'root_admin', 'root_admin@163.com', '', '超级管理员', '$2a$10$F47JY5Yt2DGoPuG8Fra8XuyiA20Q9g3.4J5eKXB0DrmvacVO1Olya', 1, '1989-11-23 00:00:00', 1, '2018-11-18 19:18:50', '2018-11-18 19:18:50', 5);
INSERT INTO `tb_user` VALUES ('402881e7672699f60167269cdd2f0000', 'https://lzy-file.oss-cn-shenzhen.aliyuncs.com/2018/11/18/19/37/f499ea6a683548a6bb6c986a38284c1d.jpeg', 'xiaoxianv', 'xiaoxianv@qq.com', '', '小仙女', '$2a$10$PeWGgYaPq3dIIV5fNgdT1.DSq9w6696T.pyBJl6XZRfNKVwBGvoSG', 0, '2018-11-14 00:00:00', 1, '2018-11-18 19:37:54', '2018-11-18 19:37:54', 8);

-- ----------------------------
-- Table structure for tb_user_role
-- ----------------------------
DROP TABLE IF EXISTS `tb_user_role`;
CREATE TABLE `tb_user_role`  (
  `t_user_id` char(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户ID',
  `t_role_id` int(11) NOT NULL COMMENT '角色ID'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '[权限管理] 用户表和角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_user_role
-- ----------------------------
INSERT INTO `tb_user_role` VALUES ('402881e7672672df016726730baa0000', 4);
INSERT INTO `tb_user_role` VALUES ('402881e7672689ac0167268b687a0000', 3);
INSERT INTO `tb_user_role` VALUES ('402881e7672699f60167269cdd2f0000', 5);

SET FOREIGN_KEY_CHECKS = 1;
