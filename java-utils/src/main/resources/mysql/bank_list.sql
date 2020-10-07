/*
 Navicat Premium Data Transfer

 Source Server         : 47.107.33.150
 Source Server Type    : MySQL
 Source Server Version : 80017
 Source Host           : 47.107.33.150:53306
 Source Schema         : backcar

 Target Server Type    : MySQL
 Target Server Version : 80017
 File Encoding         : 65001
 微信支付之企业付款到银行卡的银行编号表
 Date: 13/05/2020 19:26:13
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for bank_list
-- ----------------------------
DROP TABLE IF EXISTS `bank_list`;
CREATE TABLE `bank_list`  (
  `bank_id` int(4) NOT NULL COMMENT '银行ID',
  `bank_name` varchar(25) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '银行名称'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '银行卡信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of bank_list
-- ----------------------------
INSERT INTO `bank_list` VALUES (1002, '工商银行');
INSERT INTO `bank_list` VALUES (1005, '农业银行');
INSERT INTO `bank_list` VALUES (1003, '建设银行');
INSERT INTO `bank_list` VALUES (1026, '中国银行');
INSERT INTO `bank_list` VALUES (1020, '交通银行');
INSERT INTO `bank_list` VALUES (1001, '招商银行');
INSERT INTO `bank_list` VALUES (1066, '邮储银行');
INSERT INTO `bank_list` VALUES (1006, '民生银行');
INSERT INTO `bank_list` VALUES (1010, '平安银行');
INSERT INTO `bank_list` VALUES (1021, '中信银行');
INSERT INTO `bank_list` VALUES (1004, '浦发银行');
INSERT INTO `bank_list` VALUES (1009, '兴业银行');
INSERT INTO `bank_list` VALUES (1022, '光大银行');
INSERT INTO `bank_list` VALUES (1027, '广发银行');
INSERT INTO `bank_list` VALUES (1025, '华夏银行');
INSERT INTO `bank_list` VALUES (1056, '宁波银行');
INSERT INTO `bank_list` VALUES (4836, '北京银行');
INSERT INTO `bank_list` VALUES (1024, '上海银行');
INSERT INTO `bank_list` VALUES (1054, '南京银行');
INSERT INTO `bank_list` VALUES (4755, '长子县融汇村镇银行');
INSERT INTO `bank_list` VALUES (4216, '长沙银行');
INSERT INTO `bank_list` VALUES (4051, '浙江泰隆商业银行');
INSERT INTO `bank_list` VALUES (4753, '中原银行');
INSERT INTO `bank_list` VALUES (4761, '企业银行（中国）');
INSERT INTO `bank_list` VALUES (4036, '顺德农商银行');
INSERT INTO `bank_list` VALUES (4752, '衡水银行');
INSERT INTO `bank_list` VALUES (4756, '长治银行');
INSERT INTO `bank_list` VALUES (4767, '大同银行');
INSERT INTO `bank_list` VALUES (4115, '河南省农村信用社');
INSERT INTO `bank_list` VALUES (4150, '宁夏黄河农村商业银行');
INSERT INTO `bank_list` VALUES (4156, '山西省农村信用社');
INSERT INTO `bank_list` VALUES (4166, '安徽省农村信用社');
INSERT INTO `bank_list` VALUES (4157, '甘肃省农村信用社');
INSERT INTO `bank_list` VALUES (4153, '天津农村商业银行');
INSERT INTO `bank_list` VALUES (4113, '广西壮族自治区农村信用社');
INSERT INTO `bank_list` VALUES (4108, '陕西省农村信用社');
INSERT INTO `bank_list` VALUES (4076, '深圳农村商业银行');
INSERT INTO `bank_list` VALUES (4052, '宁波鄞州农村商业银行');
INSERT INTO `bank_list` VALUES (4764, '浙江省农村信用社联合社');
INSERT INTO `bank_list` VALUES (4217, '江苏省农村信用社联合社');
INSERT INTO `bank_list` VALUES (4072, '江苏紫金农村商业银行股份有限公司');
INSERT INTO `bank_list` VALUES (4769, '北京中关村银行股份有限公司');
INSERT INTO `bank_list` VALUES (4778, '星展银行（中国）有限公司');
INSERT INTO `bank_list` VALUES (4766, '枣庄银行股份有限公司');
INSERT INTO `bank_list` VALUES (4758, '海口联合农村商业银行股份有限公司');
INSERT INTO `bank_list` VALUES (4763, '南洋商业银行（中国）有限公司');

SET FOREIGN_KEY_CHECKS = 1;
