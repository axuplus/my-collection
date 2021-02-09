/*
 Navicat Premium Data Transfer

 Source Server         : ztgz.amsure.cn
 Source Server Type    : MySQL
 Source Server Version : 50732
 Source Host           : ztgz.amsure.cn:33060
 Source Schema         : campus_organization

 Target Server Type    : MySQL
 Target Server Version : 50732
 File Encoding         : 65001

 Date: 21/01/2021 10:49:26
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `id` bigint(17) NOT NULL COMMENT '主键ID',
  `module_id` bigint(17) NOT NULL COMMENT '模块名称',
  `type` tinyint(1) NOT NULL COMMENT '1：增 2：删 3：改 4：查 5：权限',
  `menu_name` varchar(25) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '菜单名称',
  `description` varchar(25) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '权限描述',
  `create_user` bigint(17) NOT NULL COMMENT '创建用户',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '权限菜单' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (19711725912001, 20337027611711, 1, '/sys/admin', 'admin用户添加', 18555972869325, '2020-08-17 18:08:20');
INSERT INTO `sys_menu` VALUES (19711725912002, 20337027611711, 2, '/sys/admin', 'admin用户删除', 18555972869325, '2020-08-17 18:08:20');
INSERT INTO `sys_menu` VALUES (19711725912003, 20337027611711, 3, '/sys/admin', 'admin用户修改', 18555972869325, '2020-08-17 18:08:20');
INSERT INTO `sys_menu` VALUES (19711725912004, 20337027611711, 4, '/sys/admin', 'admin用户查看', 18555972869325, '2020-08-17 18:08:20');
INSERT INTO `sys_menu` VALUES (19711725912009, 20336869677312, 1, '/sys/role', '角色管理添加', 18555972869325, '2020-08-17 18:08:20');
INSERT INTO `sys_menu` VALUES (19711725912010, 20336869677312, 2, '/sys/role', '角色管理删除', 18555972869325, '2020-08-17 18:08:20');
INSERT INTO `sys_menu` VALUES (19711725912011, 20336869677312, 3, '/sys/role', '角色管理修改', 18555972869325, '2020-08-17 18:08:20');
INSERT INTO `sys_menu` VALUES (19711725912012, 20336869677312, 4, '/sys/role', '角色管理查看', 18555972869325, '2020-08-17 18:08:20');
INSERT INTO `sys_menu` VALUES (19711725912013, 19711725912012, 1, '/sys/menu', '权限设置', 18555972869325, '2020-08-19 20:38:17');
INSERT INTO `sys_menu` VALUES (19711725912014, 19711725912012, 4, '/sys/menu', '权限查看', 18555972869325, '2020-08-29 20:04:25');
INSERT INTO `sys_menu` VALUES (19711725912023, 20336920754624, 1, '/campus/school/section', '部门管理添加', 18555972869325, '2020-08-17 18:08:20');
INSERT INTO `sys_menu` VALUES (19711725912024, 20336920754624, 2, '/campus/school/section', '部门管理删除', 18555972869325, '2020-08-17 18:08:20');

SET FOREIGN_KEY_CHECKS = 1;
