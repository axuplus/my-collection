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

 Date: 21/01/2021 10:49:35
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` bigint(17) NOT NULL COMMENT '主键ID',
  `master_id` bigint(17) NULL DEFAULT NULL COMMENT '学校ID',
  `role_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色',
  `state` tinyint(1) NOT NULL COMMENT ' 0 未启用 1已启用  -1已停用',
  `description` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色描述',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `create_user` bigint(17) NOT NULL COMMENT '创建用户',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (28298353569280, 26870358579328, '班主任', 1, '班主任', '2020-11-15 20:25:11', 26869859154496);
INSERT INTO `sys_role` VALUES (28305188572096, 26870358579328, '宿管', 1, '宿舍管理员', '2020-11-15 22:16:26', 26869859154496);
INSERT INTO `sys_role` VALUES (28471559633472, 26870358579328, '小超管', 1, '小超管\n', '2020-11-17 19:24:17', 26869859154496);
INSERT INTO `sys_role` VALUES (31708499508224, 26870358579328, '保安', 1, '保安', '2020-12-24 09:28:52', 26869859154496);

SET FOREIGN_KEY_CHECKS = 1;
