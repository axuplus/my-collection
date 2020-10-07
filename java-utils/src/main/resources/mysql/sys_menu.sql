/*
 Navicat Premium Data Transfer

 Source Server         : 120.78.162.181
 Source Server Type    : MySQL
 Source Server Version : 50731
 Source Host           : 120.78.162.181:53306
 Source Schema         : test_db

 Target Server Type    : MySQL
 Target Server Version : 50731
 File Encoding         : 65001

 Date: 17/08/2020 20:56:12
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `id` bigint(17) NOT NULL COMMENT '主键ID',
  `type` tinyint(1) NOT NULL COMMENT '1：增 2：删 3：改 4：查 5：权限',
  `menu_name` varchar(25) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '菜单名称',
  `description` varchar(25) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '权限描述',
  `create_user` bigint(17) NOT NULL COMMENT '创建用户',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `module_id` bigint(17) NOT NULL COMMENT '模块名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '权限菜单' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (19711725912001, 1, '/sys/admin', 'admin用户添加', 18555972869325, '2020-08-17 18:08:20', 20337027611711);

SET FOREIGN_KEY_CHECKS = 1;
