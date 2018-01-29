/*用户表*/
INSERT INTO sm_user (`pk_user`, `pk_role`, `pk_staff`, `user_code`, `user_name`, `password`, `memo`, `def1`, `def2`, `def3`, `def4`, `def5`, `ts`) VALUES ('1', NULL, '23', 'admin', 'admin', 'jlehfdffcfmohiag', NULL, NULL, NULL, NULL, NULL, NULL, '2017-05-24 14:22:42');

/*目录表*/
INSERT INTO sm_navigation (`pk_navigation`, `pk_father_navigation`, `navigation_code`, `navigation_name`, `navigation_path`, `icon_path`, `memo`, `def1`, `def2`, `def3`, `def4`, `def5`, `ts`) VALUES ('1', NULL, 'system', '系统设置', '', 'iconfont icon-unie600', NULL, NULL, NULL, NULL, NULL, NULL, '2017-05-31 15:40:31');
INSERT INTO sm_navigation (`pk_navigation`, `pk_father_navigation`, `navigation_code`, `navigation_name`, `navigation_path`, `icon_path`, `memo`, `def1`, `def2`, `def3`, `def4`, `def5`, `ts`) VALUES ('2', '1', 'user', '用户管理', 'system/user.html', 'iconfont icon-gongxiangtubiaofenpeijiaose', NULL, NULL, NULL, NULL, NULL, NULL, '2017-05-31 15:43:56');
INSERT INTO sm_navigation (`pk_navigation`, `pk_father_navigation`, `navigation_code`, `navigation_name`, `navigation_path`, `icon_path`, `memo`, `def1`, `def2`, `def3`, `def4`, `def5`, `ts`) VALUES ('3', '1', 'role', '角色管理', 'system/role.html', 'iconfont icon-yuangongzizhu', NULL, NULL, NULL, NULL, NULL, NULL, '2017-05-31 15:44:06');
INSERT INTO sm_navigation (`pk_navigation`, `pk_father_navigation`, `navigation_code`, `navigation_name`, `navigation_path`, `icon_path`, `memo`, `def1`, `def2`, `def3`, `def4`, `def5`, `ts`) VALUES ('4', NULL, 'baseinfo', '基础信息', NULL, 'iconfont icon-jichuxinxi', NULL, NULL, NULL, NULL, NULL, NULL, '2017-05-31 15:44:16');
INSERT INTO sm_navigation (`pk_navigation`, `pk_father_navigation`, `navigation_code`, `navigation_name`, `navigation_path`, `icon_path`, `memo`, `def1`, `def2`, `def3`, `def4`, `def5`, `ts`) VALUES ('5', '4', 'company', '公司档案', 'baseinfo/company.html', 'iconfont icon-gongsi', NULL, NULL, NULL, NULL, NULL, NULL, '2017-05-31 15:44:26');
INSERT INTO sm_navigation (`pk_navigation`, `pk_father_navigation`, `navigation_code`, `navigation_name`, `navigation_path`, `icon_path`, `memo`, `def1`, `def2`, `def3`, `def4`, `def5`, `ts`) VALUES ('6', '4', 'dept', '部门档案', 'baseinfo/dept.html', 'iconfont icon-bumen', NULL, NULL, NULL, NULL, NULL, NULL, '2017-05-31 15:44:33');
INSERT INTO sm_navigation (`pk_navigation`, `pk_father_navigation`, `navigation_code`, `navigation_name`, `navigation_path`, `icon_path`, `memo`, `def1`, `def2`, `def3`, `def4`, `def5`, `ts`) VALUES ('7', '4', 'staff', '人员信息', 'baseinfo/staff.html', 'iconfont icon-renyuanxinxi', NULL, NULL, NULL, NULL, NULL, NULL, '2017-05-31 15:44:40');
INSERT INTO sm_navigation (`pk_navigation`, `pk_father_navigation`, `navigation_code`, `navigation_name`, `navigation_path`, `icon_path`, `memo`, `def1`, `def2`, `def3`, `def4`, `def5`, `ts`) VALUES ('8', NULL, 'card', '卡务管理', NULL, 'iconfont icon-qiawuguanli01', NULL, NULL, NULL, NULL, NULL, NULL, '2017-05-31 15:44:51');
INSERT INTO sm_navigation (`pk_navigation`, `pk_father_navigation`, `navigation_code`, `navigation_name`, `navigation_path`, `icon_path`, `memo`, `def1`, `def2`, `def3`, `def4`, `def5`, `ts`) VALUES ('9', '8', 'issue', '发卡', 'card/issue-card.html', 'iconfont icon-faqia', NULL, NULL, NULL, NULL, NULL, NULL, '2017-05-31 15:45:00');
INSERT INTO sm_navigation (`pk_navigation`, `pk_father_navigation`, `navigation_code`, `navigation_name`, `navigation_path`, `icon_path`, `memo`, `def1`, `def2`, `def3`, `def4`, `def5`, `ts`) VALUES ('10', '8', 'charge', '充值', 'card/charge-card.html', 'iconfont icon-chongzhi1', NULL, NULL, NULL, NULL, NULL, NULL, '2017-05-31 15:45:07');
INSERT INTO sm_navigation (`pk_navigation`, `pk_father_navigation`, `navigation_code`, `navigation_name`, `navigation_path`, `icon_path`, `memo`, `def1`, `def2`, `def3`, `def4`, `def5`, `ts`) VALUES ('11', '8', 'lost', '挂失', 'card/lost-card.html', 'iconfont icon-qiaguashi1', NULL, NULL, NULL, NULL, NULL, NULL, '2017-05-31 15:45:14');
INSERT INTO sm_navigation (`pk_navigation`, `pk_father_navigation`, `navigation_code`, `navigation_name`, `navigation_path`, `icon_path`, `memo`, `def1`, `def2`, `def3`, `def4`, `def5`, `ts`) VALUES ('12', '8', 'refund', '退款', 'card/refund-card.html', 'iconfont icon-cshy-rmb2', NULL, NULL, NULL, NULL, NULL, NULL, '2017-05-31 15:45:21');
INSERT INTO sm_navigation (`pk_navigation`, `pk_father_navigation`, `navigation_code`, `navigation_name`, `navigation_path`, `icon_path`, `memo`, `def1`, `def2`, `def3`, `def4`, `def5`, `ts`) VALUES ('13', '8', 'return', '退卡', 'card/return-card.html', 'iconfont icon-gongxiangtubiaopiliangtuiqia', NULL, NULL, NULL, NULL, NULL, NULL, '2017-05-31 15:45:27');
INSERT INTO sm_navigation (`pk_navigation`, `pk_father_navigation`, `navigation_code`, `navigation_name`, `navigation_path`, `icon_path`, `memo`, `def1`, `def2`, `def3`, `def4`, `def5`, `ts`) VALUES ('14', '8', 'change', '补卡', 'card/change-card.html', 'iconfont icon-duqia', NULL, NULL, NULL, NULL, NULL, NULL, '2017-05-31 15:45:33');
INSERT INTO sm_navigation (`pk_navigation`, `pk_father_navigation`, `navigation_code`, `navigation_name`, `navigation_path`, `icon_path`, `memo`, `def1`, `def2`, `def3`, `def4`, `def5`, `ts`) VALUES ('15', '8', 'browse', '卡浏览', 'card/browse-card.html', 'iconfont icon-shitu', NULL, NULL, NULL, NULL, NULL, NULL, '2017-05-31 15:45:44');
INSERT INTO sm_navigation (`pk_navigation`, `pk_father_navigation`, `navigation_code`, `navigation_name`, `navigation_path`, `icon_path`, `memo`, `def1`, `def2`, `def3`, `def4`, `def5`, `ts`) VALUES ('16', '8', 'allowance', '补贴管理', 'card/allowance-card.html', 'iconfont icon-butie', NULL, NULL, NULL, NULL, NULL, NULL, '2017-05-31 15:45:51');
INSERT INTO sm_navigation (`pk_navigation`, `pk_father_navigation`, `navigation_code`, `navigation_name`, `navigation_path`, `icon_path`, `memo`, `def1`, `def2`, `def3`, `def4`, `def5`, `ts`) VALUES ('17', '8', 'param', '参数设置', 'card/card-param.html', 'iconfont icon-canshushezhi', NULL, NULL, NULL, NULL, NULL, NULL, '2017-05-31 15:45:57');
INSERT INTO sm_navigation (`pk_navigation`, `pk_father_navigation`, `navigation_code`, `navigation_name`, `navigation_path`, `icon_path`, `memo`, `def1`, `def2`, `def3`, `def4`, `def5`, `ts`) VALUES ('18', NULL, 'meal', '消费管理', NULL, 'iconfont icon-xiaofeiguanli', NULL, NULL, NULL, NULL, NULL, NULL, '2017-05-31 15:46:05');
INSERT INTO sm_navigation (`pk_navigation`, `pk_father_navigation`, `navigation_code`, `navigation_name`, `navigation_path`, `icon_path`, `memo`, `def1`, `def2`, `def3`, `def4`, `def5`, `ts`) VALUES ('19', '18', 'merchant', '商户信息', 'meal/merchant.html', 'iconfont icon-shanghuxinxi', NULL, NULL, NULL, NULL, NULL, NULL, '2017-05-31 15:46:13');
INSERT INTO sm_navigation (`pk_navigation`, `pk_father_navigation`, `navigation_code`, `navigation_name`, `navigation_path`, `icon_path`, `memo`, `def1`, `def2`, `def3`, `def4`, `def5`, `ts`) VALUES ('20', '18', 'rule', '消费规则', 'meal/meal-rule.html', 'iconfont icon-xiaofeiguanli', NULL, NULL, NULL, NULL, NULL, NULL, '2017-05-31 15:46:20');
INSERT INTO sm_navigation (`pk_navigation`, `pk_father_navigation`, `navigation_code`, `navigation_name`, `navigation_path`, `icon_path`, `memo`, `def1`, `def2`, `def3`, `def4`, `def5`, `ts`) VALUES ('21', '18', 'dining', '参次餐别', 'meal/dining.html', 'iconfont icon-qia', NULL, NULL, NULL, NULL, NULL, NULL, '2017-05-31 15:46:28');
INSERT INTO sm_navigation (`pk_navigation`, `pk_father_navigation`, `navigation_code`, `navigation_name`, `navigation_path`, `icon_path`, `memo`, `def1`, `def2`, `def3`, `def4`, `def5`, `ts`) VALUES ('22', NULL, 'report', '报表管理', NULL, 'iconfont icon-icon-report', NULL, NULL, NULL, NULL, NULL, NULL, '2017-06-02 15:41:20');
INSERT INTO sm_navigation (`pk_navigation`, `pk_father_navigation`, `navigation_code`, `navigation_name`, `navigation_path`, `icon_path`, `memo`, `def1`, `def2`, `def3`, `def4`, `def5`, `ts`) VALUES ('25', '22', 'rechargerpt', '充值明细表', 'subTablePage/recharge.html', 'iconfont icon-ribaobiao', NULL, NULL, NULL, NULL, NULL, NULL, '2017-06-09 11:32:41');
INSERT INTO sm_navigation (`pk_navigation`, `pk_father_navigation`, `navigation_code`, `navigation_name`, `navigation_path`, `icon_path`, `memo`, `def1`, `def2`, `def3`, `def4`, `def5`, `ts`) VALUES ('26', '22', 'refundrpt', '退款明细表', 'subTablePage/refund.html', 'iconfont icon-ribaobiao', NULL, NULL, NULL, NULL, NULL, NULL, '2017-06-09 11:32:45');
INSERT INTO sm_navigation (`pk_navigation`, `pk_father_navigation`, `navigation_code`, `navigation_name`, `navigation_path`, `icon_path`, `memo`, `def1`, `def2`, `def3`, `def4`, `def5`, `ts`) VALUES ('27', '22', 'return_cardrpt', '退卡明细表', 'subTablePage/return_card.html', 'iconfont icon-ribaobiao', NULL, NULL, NULL, NULL, NULL, NULL, '2017-06-09 11:32:50');
INSERT INTO sm_navigation (`pk_navigation`, `pk_father_navigation`, `navigation_code`, `navigation_name`, `navigation_path`, `icon_path`, `memo`, `def1`, `def2`, `def3`, `def4`, `def5`, `ts`) VALUES ('28', '22', 're-make_cardrpt', '补卡明细表', 'subTablePage/re-make_card.html', 'iconfont icon-ribaobiao', NULL, NULL, NULL, NULL, NULL, NULL, '2017-06-09 11:32:53');
INSERT INTO sm_navigation (`pk_navigation`, `pk_father_navigation`, `navigation_code`, `navigation_name`, `navigation_path`, `icon_path`, `memo`, `def1`, `def2`, `def3`, `def4`, `def5`, `ts`) VALUES ('29', '22', 'personal_meal_sumrpt', '个人余额统计表', 'subTablePage/balance.html', 'iconfont icon-ribaobiao', NULL, NULL, NULL, NULL, NULL, NULL, '2017-06-12 09:49:19');
INSERT INTO sm_navigation (`pk_navigation`, `pk_father_navigation`, `navigation_code`, `navigation_name`, `navigation_path`, `icon_path`, `memo`, `def1`, `def2`, `def3`, `def4`, `def5`, `ts`) VALUES ('30', '31', 'mealdevice', '设备管理', 'device/device.html', 'iconfont icon-shebeiguanli', NULL, NULL, NULL, NULL, NULL, NULL, '2017-06-02 15:40:04');
INSERT INTO sm_navigation (`pk_navigation`, `pk_father_navigation`, `navigation_code`, `navigation_name`, `navigation_path`, `icon_path`, `memo`, `def1`, `def2`, `def3`, `def4`, `def5`, `ts`) VALUES ('31', NULL, 'device_mng', '设备管理', NULL, 'iconfont icon-shebeiguanli', NULL, NULL, NULL, NULL, NULL, NULL, '2017-06-02 15:40:15');
INSERT INTO sm_navigation (`pk_navigation`, `pk_father_navigation`, `navigation_code`, `navigation_name`, `navigation_path`, `icon_path`, `memo`, `def1`, `def2`, `def3`, `def4`, `def5`, `ts`) VALUES ('32', '31', 'device_time_mng', '设备时间段设置', 'device/time-mng.html', 'iconfont icon-shijianshezhitongyong', NULL, NULL, NULL, NULL, NULL, NULL, '2017-06-02 15:40:23');
INSERT INTO sm_navigation (`pk_navigation`, `pk_father_navigation`, `navigation_code`, `navigation_name`, `navigation_path`, `icon_path`, `memo`, `def1`, `def2`, `def3`, `def4`, `def5`, `ts`) VALUES ('33', NULL, 'order_online', '网上商城', NULL, 'iconfont icon-wangshangshangdian', NULL, NULL, NULL, NULL, NULL, NULL, '2017-06-02 15:40:33');
INSERT INTO sm_navigation (`pk_navigation`, `pk_father_navigation`, `navigation_code`, `navigation_name`, `navigation_path`, `icon_path`, `memo`, `def1`, `def2`, `def3`, `def4`, `def5`, `ts`) VALUES ('34', '33', 'commodity_type', '类别管理', 'internetmall/style.html', 'iconfont icon-leibieguanli', NULL, NULL, NULL, NULL, NULL, NULL, '2017-06-02 15:40:41');
INSERT INTO sm_navigation (`pk_navigation`, `pk_father_navigation`, `navigation_code`, `navigation_name`, `navigation_path`, `icon_path`, `memo`, `def1`, `def2`, `def3`, `def4`, `def5`, `ts`) VALUES ('35', '33', 'commodity', '商品管理', 'internetmall/goods.html', 'iconfont icon-shangpinguanli', NULL, NULL, NULL, NULL, NULL, NULL, '2017-06-02 15:40:49');
INSERT INTO sm_navigation (`pk_navigation`, `pk_father_navigation`, `navigation_code`, `navigation_name`, `navigation_path`, `icon_path`, `memo`, `def1`, `def2`, `def3`, `def4`, `def5`, `ts`) VALUES ('36', '33', 'order_mng', '订单管理', 'internetmall/order_manager.html', 'iconfont icon-dingdanguanli', NULL, NULL, NULL, NULL, NULL, NULL, '2017-06-02 15:40:55');
INSERT INTO sm_navigation (`pk_navigation`, `pk_father_navigation`, `navigation_code`, `navigation_name`, `navigation_path`, `icon_path`, `memo`, `def1`, `def2`, `def3`, `def4`, `def5`, `ts`) VALUES ('37', '22', 'daily_recharge_sum', '每日充值表', 'subTablePage/daily_recharge_sum.html', 'iconfont icon-ribaobiao', NULL, NULL, NULL, NULL, NULL, NULL, '2017-05-31 15:48:52');
INSERT INTO sm_navigation (`pk_navigation`, `pk_father_navigation`, `navigation_code`, `navigation_name`, `navigation_path`, `icon_path`, `memo`, `def1`, `def2`, `def3`, `def4`, `def5`, `ts`) VALUES ('38', '22', 'merchants_pos_sum', '商户POS汇总', 'subTablePage/merchants_pos_sum.html', 'iconfont icon-ribaobiao', NULL, NULL, NULL, NULL, NULL, NULL, '2017-05-31 15:49:00');
INSERT INTO sm_navigation (`pk_navigation`, `pk_father_navigation`, `navigation_code`, `navigation_name`, `navigation_path`, `icon_path`, `memo`, `def1`, `def2`, `def3`, `def4`, `def5`, `ts`) VALUES ('39', '22', 'merchants_pos_daily', '商户POS日报表', 'subTablePage/merchants_pos_daily.html', 'iconfont icon-ribaobiao', NULL, NULL, NULL, NULL, NULL, NULL, '2017-05-31 15:49:09');
INSERT INTO sm_navigation (`pk_navigation`, `pk_father_navigation`, `navigation_code`, `navigation_name`, `navigation_path`, `icon_path`, `memo`, `def1`, `def2`, `def3`, `def4`, `def5`, `ts`) VALUES ('41', '22', 'consumer_credit_sum', '消费充值汇总报表', 'subTablePage/consumer_credit_sum.html', 'iconfont icon-ribaobiao', NULL, NULL, NULL, NULL, NULL, NULL, '2017-06-09 11:33:45');
INSERT INTO sm_navigation (`pk_navigation`, `pk_father_navigation`, `navigation_code`, `navigation_name`, `navigation_path`, `icon_path`, `memo`, `def1`, `def2`, `def3`, `def4`, `def5`, `ts`) VALUES ('42', '18', 'Correction_error', '更正收款失误', 'meal/Correction_error.html', 'iconfont icon-shangbiaogengzheng', NULL, NULL, NULL, NULL, NULL, NULL, '2017-06-02 15:41:35');
INSERT INTO sm_navigation (`pk_navigation`, `pk_father_navigation`, `navigation_code`, `navigation_name`, `navigation_path`, `icon_path`, `memo`, `def1`, `def2`, `def3`, `def4`, `def5`, `ts`) VALUES ('43', '22', 'consumer_details', '消费明细报表', 'subTablePage/consumer_details.html', 'iconfont icon-ribaobiao', NULL, NULL, NULL, NULL, NULL, NULL, '2017-06-09 11:31:36');
