DROP TABLE IF EXISTS `card_card`;
CREATE TABLE `card_card` (
  `pk_card` bigint(20) NOT NULL AUTO_INCREMENT,
  `pk_staff` bigint(20) DEFAULT NULL COMMENT '关联人员',
  `card_code` varchar(20) CHARACTER SET utf8 DEFAULT NULL COMMENT '卡号',
  `card_state` varchar(8) CHARACTER SET utf8 DEFAULT NULL COMMENT '卡状态  10:正常  20:挂失  40:退卡',
  `card_issue_ts` char(19) CHARACTER SET utf8 DEFAULT NULL COMMENT '发卡时间',
  `card_ineffectived_ts` char(19) CHARACTER SET utf8 DEFAULT NULL COMMENT '失效时间',
  `card_costing` double(20,0) DEFAULT NULL COMMENT '卡成本',
  `card_deposit` double(20,0) DEFAULT NULL COMMENT '卡押金',
  `money_cash` double(20,0) DEFAULT NULL COMMENT '现金钱包',
  `money_allowance` double(20,0) DEFAULT NULL COMMENT '补贴钱包',
  `pk_meal_rule` bigint(20) DEFAULT NULL COMMENT '关联消费规则',
  `password` varchar(64) CHARACTER SET utf8 DEFAULT NULL COMMENT '密码',
  `serial` int(11) DEFAULT NULL COMMENT '流水号',
  `memo` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `def1` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `def2` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `def3` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `def4` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `def5` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `ts` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`pk_card`)
);

DROP TABLE IF EXISTS `card_change_record`;
CREATE TABLE `card_change_record` (
  `pk_change_record` bigint(20) NOT NULL AUTO_INCREMENT,
  `pk_card` bigint(20) DEFAULT NULL,
  `pk_staff` bigint(20) DEFAULT NULL,
  `operator` bigint(20) DEFAULT NULL COMMENT '关联操作人员pk',
  `card_code` varchar(20) DEFAULT NULL,
  `staff_code` varchar(64) DEFAULT NULL,
  `operation_type` varchar(8) DEFAULT NULL COMMENT '发卡0、退卡1、补卡2',
  `operation_ts` char(19) DEFAULT NULL COMMENT '操作时间',
  `cardcode_new` varchar(20) DEFAULT NULL COMMENT '新卡号(补卡操作时有数据)',
  `money_remain` double(20,0) DEFAULT NULL COMMENT '补卡时余额(补卡操作时有数据)',
  `old_serial_number` int(11) DEFAULT NULL COMMENT '旧卡流水号(补卡操作时有数据)',
  `memo` varchar(255) DEFAULT NULL,
  `def1` varchar(255) DEFAULT NULL,
  `def2` varchar(255) DEFAULT NULL,
  `def3` varchar(255) DEFAULT NULL,
  `def4` varchar(255) DEFAULT NULL,
  `def5` varchar(255) DEFAULT NULL,
  `ts` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`pk_change_record`)
);

DROP TABLE IF EXISTS `card_charge_record`;
CREATE TABLE `card_charge_record` (
  `pk_charge_record` bigint(20) NOT NULL AUTO_INCREMENT,
  `pk_card` bigint(20) DEFAULT NULL,
  `pk_staff` bigint(20) DEFAULT NULL,
  `card_code` varchar(20) DEFAULT NULL,
  `staff_code` varchar(64) DEFAULT NULL,
  `charge_money` double(20,0) DEFAULT NULL COMMENT '充值金额(分)',
  `money_retain` double(20,0) DEFAULT NULL COMMENT '充值后总余额',
  `operator` bigint(20) DEFAULT NULL COMMENT '关联操作人员pk',
  `charge_type` varchar(8) DEFAULT NULL COMMENT '0线下现金 1卡押金 2卡成本 3清零补贴 4累加补贴',
  `charge_ts` char(19) DEFAULT NULL,
  `card_batchnum` int(11) DEFAULT NULL COMMENT '流水号',
  `memo` varchar(255) DEFAULT NULL,
  `def1` varchar(255) DEFAULT NULL,
  `def2` varchar(255) DEFAULT NULL,
  `def3` varchar(255) DEFAULT NULL,
  `def4` varchar(255) DEFAULT NULL,
  `def5` varchar(255) DEFAULT NULL,
  `ts` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`pk_charge_record`)
);

DROP TABLE IF EXISTS `card_lost_record`;
CREATE TABLE `card_lost_record` (
  `pk_lost_record` bigint(20) NOT NULL AUTO_INCREMENT,
  `pk_card` bigint(20) DEFAULT NULL,
  `pk_staff` bigint(20) DEFAULT NULL,
  `card_code` varchar(20) DEFAULT NULL,
  `staff_code` varchar(64) DEFAULT NULL,
  `type` varchar(8) DEFAULT NULL COMMENT '类型  lost:挂失   unlost:解挂',
  `operator` bigint(20) DEFAULT NULL COMMENT '关联操作人员pk',
  `lost_ts` char(19) DEFAULT NULL COMMENT '操作时间',
  `memo` varchar(255) DEFAULT NULL,
  `def1` varchar(255) DEFAULT NULL,
  `def2` varchar(255) DEFAULT NULL,
  `def3` varchar(255) DEFAULT NULL,
  `def4` varchar(255) DEFAULT NULL,
  `def5` varchar(255) DEFAULT NULL,
  `ts` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`pk_lost_record`)
);

DROP TABLE IF EXISTS `card_param`;
CREATE TABLE `card_param` (
  `pk_card_param` bigint(20) NOT NULL AUTO_INCREMENT,
  `pk_company` bigint(20) DEFAULT NULL COMMENT '关联公司(未启用)',
  `name` varchar(64) CHARACTER SET utf8 DEFAULT NULL COMMENT '名称',
  `costing` double(20,0) DEFAULT NULL COMMENT '卡成本',
  `deposit` double(20,0) DEFAULT NULL COMMENT '卡押金',
  `effective_months` int(8) DEFAULT NULL COMMENT '有效月份',
  `password` varchar(64) CHARACTER SET utf8 DEFAULT NULL COMMENT '密码',
  `memo` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `def1` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `def2` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `def3` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `def4` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `def5` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `ts` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`pk_card_param`)
);

DROP TABLE IF EXISTS `card_refund_record`;
CREATE TABLE `card_refund_record` (
  `pk_refund_record` bigint(20) NOT NULL AUTO_INCREMENT,
  `pk_card` bigint(20) DEFAULT NULL,
  `pk_staff` bigint(20) DEFAULT NULL,
  `card_code` varchar(20) DEFAULT NULL,
  `staff_code` varchar(64) DEFAULT NULL,
  `refund_money` double(20,0) DEFAULT NULL COMMENT '退款金额',
  `money_retain` double(20,0) DEFAULT NULL COMMENT '退款后总余额',
  `refund_type` varchar(8) DEFAULT NULL COMMENT '0:现金,1:补贴,2押金',
  `refund_ts` char(19) DEFAULT NULL COMMENT '退款时间',
  `operator` bigint(20) DEFAULT NULL COMMENT '关联操作人员pk',
  `card_batchnum` int(11) DEFAULT NULL COMMENT '流水号',
  `memo` varchar(255) DEFAULT NULL,
  `def1` varchar(255) DEFAULT NULL,
  `def2` varchar(255) DEFAULT NULL,
  `def3` varchar(255) DEFAULT NULL,
  `def4` varchar(255) DEFAULT NULL,
  `def5` varchar(255) DEFAULT NULL,
  `ts` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`pk_refund_record`)
);

DROP TABLE IF EXISTS `db_company`;
CREATE TABLE `db_company` (
  `pk_company` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '记录ID',
  `company_code` varchar(50) NOT NULL COMMENT '企业编码',
  `company_name` varchar(50) NOT NULL COMMENT '企业名称',
  `company_type` int(2) DEFAULT NULL COMMENT '企业类型',
  `person_in_charge` varchar(25) DEFAULT NULL COMMENT '企业负责人',
  `address` varchar(60) NOT NULL COMMENT '办公地址',
  `telephone` varchar(18) NOT NULL COMMENT '企业电话',
  `memo` varchar(500) DEFAULT NULL COMMENT '企业介绍',
  `def1` varchar(255) DEFAULT NULL,
  `def2` varchar(255) DEFAULT NULL,
  `def3` varchar(255) DEFAULT NULL,
  `def4` varchar(255) DEFAULT NULL,
  `def5` varchar(255) DEFAULT NULL,
  `bd_std` char(1) DEFAULT NULL,
  `dr` int(1) DEFAULT NULL,
  `ts` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`pk_company`)
);

DROP TABLE IF EXISTS `db_department`;
CREATE TABLE `db_department` (
  `pk_department` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '记录ID',
  `department_code` varchar(50) NOT NULL COMMENT '部门编码',
  `department_name` varchar(50) NOT NULL COMMENT '部门名称',
  `company_code` varchar(50) DEFAULT NULL COMMENT '关联企业',
  `parent_code` varchar(25) DEFAULT NULL COMMENT '上级部门',
  `type` int(1) DEFAULT NULL COMMENT '部门状态',
  `haschild` varchar(18) DEFAULT NULL COMMENT '是否有子节点',
  `memo` varchar(500) DEFAULT NULL COMMENT '部门介绍',
  `def1` varchar(255) DEFAULT NULL,
  `def2` varchar(255) DEFAULT NULL,
  `def3` varchar(255) DEFAULT NULL,
  `def4` varchar(255) DEFAULT NULL,
  `def5` varchar(255) DEFAULT NULL,
  `bd_std` char(1) DEFAULT NULL,
  `dr` int(1) DEFAULT NULL,
  `ts` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`pk_department`)
);

DROP TABLE IF EXISTS `db_staff`;
CREATE TABLE `db_staff` (
  `pk_staff` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '记录ID',
  `staff_code` varchar(50) NOT NULL COMMENT '员工编码',
  `staff_name` varchar(50) NOT NULL COMMENT '姓名',
  `company_code` varchar(50) DEFAULT NULL COMMENT '关联企业',
  `department_code` varchar(25) DEFAULT NULL COMMENT '关联部门',
  `staff_type` varchar(8) DEFAULT NULL COMMENT '员工状态:1表示在职，0表示离职',
  `sex` varchar(8) DEFAULT NULL COMMENT '性别：1表示男，0表示女',
  `id_card` varchar(18) DEFAULT NULL COMMENT '身份证',
  `job_code` varchar(50) DEFAULT NULL COMMENT '职务编码',
  `edu_code` varchar(50) DEFAULT NULL COMMENT '学历编码',
  `nation_code` varchar(50) DEFAULT NULL COMMENT '名族编码',
  `birth_date` char(10) DEFAULT NULL COMMENT '生日',
  `hire_date` char(10) DEFAULT NULL COMMENT '入职日期',
  `leave_date` char(10) DEFAULT NULL COMMENT '离职日期',
  `email` varchar(28) DEFAULT NULL COMMENT '电子邮箱',
  `company_card` varchar(50) DEFAULT NULL COMMENT '工号',
  `update_date` char(19) DEFAULT NULL COMMENT '修改时间',
  `memo` varchar(255) DEFAULT NULL,
  `def1` varchar(255) DEFAULT NULL,
  `def2` varchar(255) DEFAULT NULL,
  `def3` varchar(255) DEFAULT NULL,
  `def4` varchar(255) DEFAULT NULL,
  `def5` varchar(255) DEFAULT NULL,
  `bd_std` char(1) DEFAULT NULL,
  `dr` int(1) DEFAULT NULL,
  `ts` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`pk_staff`)
);

DROP TABLE IF EXISTS `device_time`;
CREATE TABLE `device_time` (
  `pk_device_time` bigint(20) NOT NULL AUTO_INCREMENT,
  `time_name` varchar(64) DEFAULT NULL,
  `memo` varchar(255) DEFAULT NULL,
  `def1` varchar(255) DEFAULT NULL,
  `def2` varchar(255) DEFAULT NULL,
  `def3` varchar(255) DEFAULT NULL,
  `def4` varchar(255) DEFAULT NULL,
  `def5` varchar(255) DEFAULT NULL,
  `ts` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`pk_device_time`)
);

DROP TABLE IF EXISTS `device_time_sub`;
CREATE TABLE `device_time_sub` (
  `pk_device_time_sub` bigint(20) NOT NULL AUTO_INCREMENT,
  `pk_device_time` bigint(20) DEFAULT NULL,
  `sub_name` varchar(64) DEFAULT NULL,
  `start_time` char(8) DEFAULT NULL,
  `end_time` char(8) DEFAULT NULL,
  `memo` varchar(255) DEFAULT NULL,
  `def1` varchar(255) DEFAULT NULL,
  `def2` varchar(255) DEFAULT NULL,
  `def3` varchar(255) DEFAULT NULL,
  `def4` varchar(255) DEFAULT NULL,
  `def5` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`pk_device_time_sub`)
);

DROP TABLE IF EXISTS `meal_device`;
CREATE TABLE `meal_device` (
  `pk_device` bigint(20) NOT NULL AUTO_INCREMENT,
  `pk_merchant` bigint(20) DEFAULT NULL COMMENT '关联商户',
  `device_code` varchar(64) DEFAULT NULL,
  `device_name` varchar(128) DEFAULT NULL,
  `device_ip` varchar(64) DEFAULT NULL COMMENT 'IP',
  `device_port` varchar(10) DEFAULT NULL COMMENT '端口',
  `device_meal_type` varchar(8) DEFAULT NULL COMMENT '消费机的消费规则：0仅现金，10仅补贴，20先现金后补贴，40先补贴后现金',
  `be_control_time` char(1) DEFAULT NULL COMMENT '是否启用时间断控制  Y/N',
  `memo` varchar(255) DEFAULT NULL,
  `def1` varchar(255) DEFAULT NULL,
  `def2` varchar(255) DEFAULT NULL,
  `def3` varchar(255) DEFAULT NULL,
  `def4` varchar(255) DEFAULT NULL,
  `def5` varchar(255) DEFAULT NULL,
  `ts` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`pk_device`)
);

DROP TABLE IF EXISTS `meal_dining`;
CREATE TABLE `meal_dining` (
  `pk_dining` bigint(20) NOT NULL AUTO_INCREMENT,
  `dining_code` varchar(64) DEFAULT NULL COMMENT '餐次餐别编码',
  `dining_name` varchar(64) DEFAULT NULL COMMENT '餐次餐别名称',
  `begin_time` char(8) DEFAULT NULL COMMENT '开始时间',
  `end_time` char(8) DEFAULT NULL COMMENT '结束时间',
  `be_valid` char(1) DEFAULT NULL COMMENT '是否启用  Y/N',
  `memo` varchar(255) DEFAULT NULL,
  `def1` varchar(255) DEFAULT NULL,
  `def2` varchar(255) DEFAULT NULL,
  `def3` varchar(255) DEFAULT NULL,
  `def4` varchar(255) DEFAULT NULL,
  `def5` varchar(255) DEFAULT NULL,
  `ts` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`pk_dining`)
);

DROP TABLE IF EXISTS `meal_merchant`;
CREATE TABLE `meal_merchant` (
  `pk_merchant` bigint(20) NOT NULL AUTO_INCREMENT,
  `merchant_code` varchar(64) DEFAULT NULL COMMENT '商户编码',
  `merchant_name` varchar(64) DEFAULT NULL COMMENT '商户名称',
  `merchant_phone` varchar(20) DEFAULT NULL COMMENT '联系电话',
  `merchant_psn` varchar(64) DEFAULT NULL COMMENT '联系人',
  `memo` varchar(255) DEFAULT NULL,
  `def1` varchar(255) DEFAULT NULL,
  `def2` varchar(255) DEFAULT NULL,
  `def3` varchar(255) DEFAULT NULL,
  `def4` varchar(255) DEFAULT NULL,
  `def5` varchar(255) DEFAULT NULL,
  `ts` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`pk_merchant`)
);

DROP TABLE IF EXISTS `meal_rule`;
CREATE TABLE `meal_rule` (
  `pk_meal_rule` bigint(20) NOT NULL AUTO_INCREMENT,
  `meal_rule_code` varchar(64) DEFAULT NULL COMMENT '消费规则编码',
  `meal_rule_name` varchar(64) DEFAULT NULL COMMENT '消费规则名称',
  `frequency_day` int(11) DEFAULT NULL COMMENT '日限次',
  `money_day` int(11) DEFAULT NULL COMMENT '日限额',
  `frequency_time` int(11) DEFAULT NULL COMMENT '时间段限次',
  `money_time` int(11) DEFAULT NULL COMMENT '时间段限额',
  `memo` varchar(255) DEFAULT NULL,
  `def1` varchar(255) DEFAULT NULL,
  `def2` varchar(255) DEFAULT NULL,
  `def3` varchar(255) DEFAULT NULL,
  `def4` varchar(255) DEFAULT NULL,
  `def5` varchar(255) DEFAULT NULL,
  `ts` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`pk_meal_rule`)
);

DROP TABLE IF EXISTS `order_detail`;
CREATE TABLE `order_detail` (
  `detail` int(11) NOT NULL AUTO_INCREMENT COMMENT '明细ID',
  `fk_formcode` int(11) DEFAULT NULL COMMENT '订单编号',
  `foodname` varchar(20) DEFAULT NULL COMMENT '商品名称',
  `price` double(5,3) DEFAULT NULL COMMENT '商品单价',
  `count` int(3) DEFAULT NULL COMMENT '数量',
  `memo` varchar(255) DEFAULT NULL,
  `def1` varchar(255) DEFAULT NULL,
  `def2` varchar(255) DEFAULT NULL,
  `def3` varchar(255) DEFAULT NULL,
  `def4` varchar(255) DEFAULT NULL,
  `def5` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`detail`)
);

DROP TABLE IF EXISTS `order_form`;
CREATE TABLE `order_form` (
  `pk_formcode` int(11) NOT NULL AUTO_INCREMENT COMMENT '订单编号',
  `formstatus` int(5) DEFAULT NULL COMMENT '订单状态',
  `formpeople` varchar(10) DEFAULT NULL COMMENT '订单人',
  `formtime` datetime DEFAULT NULL COMMENT '下单日期',
  `amount` double(7,3) DEFAULT NULL COMMENT '总金额',
  `operationtime` varchar(20) DEFAULT NULL COMMENT '操作时间',
  `operator` varchar(10) DEFAULT NULL COMMENT '操作人',
  `memo` varchar(255) DEFAULT NULL,
  `def1` varchar(255) DEFAULT NULL,
  `def2` varchar(255) DEFAULT NULL,
  `def3` varchar(255) DEFAULT NULL,
  `def4` varchar(255) DEFAULT NULL,
  `def5` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`pk_formcode`)
);

DROP TABLE IF EXISTS `order_goods`;
CREATE TABLE `order_goods` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `goodstype` int(11) DEFAULT NULL COMMENT '商品大类',
  `goodsname` varchar(30) DEFAULT NULL COMMENT '商品名称',
  `norm` varchar(20) DEFAULT NULL COMMENT '规格',
  `unit` varchar(10) DEFAULT NULL COMMENT '单位',
  `price` double(8,3) DEFAULT NULL COMMENT '价格',
  `state` int(5) DEFAULT NULL COMMENT '状态',
  `barcode` int(11) DEFAULT NULL COMMENT '条码',
  `addedtime` varchar(20) DEFAULT NULL COMMENT '上架时间',
  `image` varchar(100) DEFAULT NULL COMMENT '图片',
  `styleid` varchar(255) DEFAULT NULL COMMENT '大类id',
  `def1` varchar(255) DEFAULT NULL,
  `def2` varchar(255) DEFAULT NULL,
  `def3` varchar(255) DEFAULT NULL,
  `def4` varchar(255) DEFAULT NULL,
  `def5` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`Id`)
);

DROP TABLE IF EXISTS `order_goodstyle`;
CREATE TABLE `order_goodstyle` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `stylename` varchar(10) DEFAULT NULL,
  `memo` varchar(255) DEFAULT NULL,
  `def1` varchar(255) DEFAULT NULL,
  `def2` varchar(255) DEFAULT NULL,
  `def3` varchar(255) DEFAULT NULL,
  `def4` varchar(255) DEFAULT NULL,
  `def5` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`Id`)
);

DROP TABLE IF EXISTS `order_goodstype`;
CREATE TABLE `order_goodstype` (
  `pk_only` int(15) NOT NULL AUTO_INCREMENT COMMENT '类型id',
  `pk_parentid` bigint(25) DEFAULT NULL COMMENT '父类id',
  `typeid` int(11) DEFAULT NULL COMMENT '子类id',
  `typename` varchar(255) DEFAULT NULL COMMENT '本类名称',
  `def2` varchar(255) DEFAULT NULL,
  `def3` varchar(255) DEFAULT NULL,
  `def4` varchar(255) DEFAULT NULL,
  `def5` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`pk_only`)
);

DROP TABLE IF EXISTS `sm_jurisdiction`;
CREATE TABLE `sm_jurisdiction` (
  `pk_jurisdiction` bigint(20) NOT NULL AUTO_INCREMENT,
  `pk_navigation` bigint(20) NOT NULL,
  `pk_role` bigint(20) NOT NULL,
  `memo` varchar(255) DEFAULT NULL,
  `def1` varchar(255) DEFAULT NULL,
  `def2` varchar(255) DEFAULT NULL,
  `def3` varchar(255) DEFAULT NULL,
  `def4` varchar(255) DEFAULT NULL,
  `def5` varchar(255) DEFAULT NULL,
  `ts` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`pk_jurisdiction`)
);

DROP TABLE IF EXISTS `sm_role`;
CREATE TABLE `sm_role` (
  `pk_role` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_code` varchar(64) NOT NULL,
  `role_name` varchar(64) NOT NULL,
  `memo` varchar(255) DEFAULT NULL,
  `def1` varchar(255) DEFAULT NULL,
  `def2` varchar(255) DEFAULT NULL,
  `def3` varchar(255) DEFAULT NULL,
  `def4` varchar(255) DEFAULT NULL,
  `def5` varchar(255) DEFAULT NULL,
  `ts` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`pk_role`)
);

DROP TABLE IF EXISTS `sm_user`;
CREATE TABLE `sm_user` (
  `pk_user` bigint(20) NOT NULL AUTO_INCREMENT,
  `pk_role` bigint(20) DEFAULT NULL,
  `pk_staff` bigint(20) DEFAULT NULL,
  `user_code` varchar(64) DEFAULT NULL,
  `user_name` varchar(64) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `memo` varchar(255) DEFAULT NULL,
  `def1` varchar(255) DEFAULT NULL,
  `def2` varchar(255) DEFAULT NULL,
  `def3` varchar(255) DEFAULT NULL,
  `def4` varchar(255) DEFAULT NULL,
  `def5` varchar(255) DEFAULT NULL,
  `ts` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`pk_user`)
);

DROP TABLE IF EXISTS `sm_navigation`;
CREATE TABLE `sm_navigation` (
  `pk_navigation` bigint(20) NOT NULL AUTO_INCREMENT,
  `pk_father_navigation` bigint(20) DEFAULT NULL,
  `navigation_code` varchar(64) DEFAULT NULL,
  `navigation_name` varchar(64) DEFAULT NULL,
  `navigation_path` varchar(255) DEFAULT NULL,
  `icon_path` varchar(255) DEFAULT NULL,
  `memo` varchar(255) DEFAULT NULL,
  `def1` varchar(255) DEFAULT NULL,
  `def2` varchar(255) DEFAULT NULL,
  `def3` varchar(255) DEFAULT NULL,
  `def4` varchar(255) DEFAULT NULL,
  `def5` varchar(255) DEFAULT NULL,
  `ts` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`pk_navigation`)
);

DROP TABLE IF EXISTS `meal_allowance`;
CREATE TABLE `meal_allowance` (
  `pk_allowance` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'PK',
  `pk_allowance_num` bigint(20) NOT NULL COMMENT '补贴批次ID',
  `pk_staff` bigint(20) NOT NULL,
  `pk_card` bigint(20) NOT NULL COMMENT '卡号',
  `money_allowance` decimal(8,2) DEFAULT NULL COMMENT '补贴金额',
  `allowance_type` char(1) DEFAULT NULL COMMENT '发放补贴方式',
  `memo` varchar(512) DEFAULT NULL,
  `def1` varchar(128) DEFAULT NULL,
  `def2` varchar(128) DEFAULT NULL,
  `def3` varchar(128) DEFAULT NULL,
  `def4` varchar(128) DEFAULT NULL,
  `def5` varchar(128) DEFAULT NULL,
  `ts` char(19) DEFAULT NULL,
  PRIMARY KEY (`pk_allowance`)
);

DROP TABLE IF EXISTS `meal_allowance_num`;
CREATE TABLE `meal_allowance_num` (
  `pk_allowance_num` bigint(20) NOT NULL AUTO_INCREMENT,
  `allowance_num_code` char(14) NOT NULL COMMENT '补贴批号\n',
  `pk_staff` bigint(20) DEFAULT NULL,
  `state` bigint(20) DEFAULT '0' COMMENT '/*0 未发放  2已发放*/\n',
  `ts` char(19) DEFAULT NULL COMMENT '创建时间',
  `memo` varchar(512) DEFAULT NULL,
  `def1` varchar(128) DEFAULT NULL,
  `def2` varchar(128) DEFAULT NULL,
  `def3` varchar(128) DEFAULT NULL,
  `def4` varchar(128) DEFAULT NULL,
  `def5` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`pk_allowance_num`)
);