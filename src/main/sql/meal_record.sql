/*
 Navicat Premium Data Transfer

 Source Server         : minhang
 Source Server Type    : MySQL
 Source Server Version : 50717
 Source Host           : 39.104.64.195
 Source Database       : wwj

 Target Server Type    : MySQL
 Target Server Version : 50717
 File Encoding         : utf-8

 Date: 09/04/2018 19:55:00 PM
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `meal_record`
-- ----------------------------
DROP TABLE IF EXISTS `meal_record`;
CREATE TABLE `meal_record` (
  `pk_meal_record` bigint(20) NOT NULL AUTO_INCREMENT,
  `pk_staff` bigint(20) DEFAULT NULL COMMENT '人员pk',
  `pk_card` bigint(20) DEFAULT NULL COMMENT '卡pk',
  `pk_device` bigint(20) DEFAULT NULL COMMENT '设备pk',
  `card_code` varchar(20) DEFAULT NULL COMMENT '卡号',
  `staff_code` varchar(64) DEFAULT NULL COMMENT '人员编码',
  `device_code` varchar(64) DEFAULT NULL COMMENT '设备编码',
  `meal_money` double DEFAULT NULL COMMENT '消费金额',
  `meal_cash_money` double DEFAULT NULL COMMENT '现金钱包消费金额',
  `meal_allowance` double DEFAULT NULL COMMENT '补贴钱包消费金额',
  `cash_retain` double DEFAULT NULL COMMENT '现金钱包余额',
  `allowance_retain` double DEFAULT NULL COMMENT '补贴钱包余额',
  `money_retain` double DEFAULT NULL COMMENT '总余额',
  `meal_type` char(2) DEFAULT NULL COMMENT '0:正常消费,1:计次,2:冲正,3:更正收款失误,4:消费撤销',
  `meal_way` char(1) DEFAULT NULL COMMENT '0:线下  1:线上',
  `device_meal_type` varchar(8) DEFAULT NULL COMMENT '消费机的消费规则：0仅现金，10仅补贴，20先现金后补贴，40先补贴后现金',
  `meal_ts` char(19) DEFAULT NULL,
  `dining_name` varchar(64) DEFAULT NULL COMMENT '餐次餐别名称',
  `dining_code` varchar(64) DEFAULT NULL COMMENT '餐次餐别编码',
  `serial` bigint(20) DEFAULT NULL,
  `memo` varchar(255) DEFAULT NULL,
  `def1` varchar(255) DEFAULT NULL,
  `def2` varchar(255) DEFAULT NULL,
  `def3` varchar(255) DEFAULT NULL,
  `def4` varchar(255) DEFAULT NULL,
  `def5` varchar(255) DEFAULT NULL,
  `ts` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `staff_name` varchar(50) DEFAULT NULL COMMENT '姓名',
  `staff_type` varchar(8) DEFAULT NULL COMMENT '员工状态:1表示在职，0表示离职',
  `company_code` varchar(50) DEFAULT NULL COMMENT '企业编码',
  `company_name` varchar(50) DEFAULT NULL COMMENT '企业名称',
  `department_code` varchar(25) DEFAULT NULL COMMENT '关联部门',
  `department_name` varchar(50) DEFAULT NULL COMMENT '部门名称',
  `card_state` varchar(8) DEFAULT NULL COMMENT '卡状态  10:正常  20:挂失  40:退卡',
  `device_name` varchar(128) DEFAULT NULL,
  `pk_merchant` bigint(20) DEFAULT NULL COMMENT '关联商户',
  `merchant_code` varchar(64) DEFAULT NULL COMMENT '商户编码',
  `merchant_name` varchar(64) DEFAULT NULL COMMENT '商户名称',
  `from_table_pk_meal_record` bigint(20) NOT NULL COMMENT '本条数据来源半月表所在行的pk值',
  PRIMARY KEY (`pk_meal_record`),
  KEY `meal_ts` (`meal_ts`) USING HASH
) ENGINE=InnoDB AUTO_INCREMENT=1542134 DEFAULT CHARSET=utf8;

SET FOREIGN_KEY_CHECKS = 1;
