use liren;

DROP TABLE IF EXISTS `t_beauty_shop`;
CREATE TABLE `t_beauty_shop` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '美容院编码',
  `name` varchar(128) NOT NULL COMMENT '名称',
  `address` varchar(512) DEFAULT NULL COMMENT '地址',
  `own_name` varchar(32) DEFAULT NULL COMMENT '负责人姓名',
  `phone` varchar(16) DEFAULT NULL COMMENT '负责人电话',
  `join_date` datetime NOT NULL COMMENT '入驻日期',
  `referrer_name` varchar(32) DEFAULT NULL COMMENT '推荐人姓名',
  `referrer_phone` varchar(16) DEFAULT NULL COMMENT '推荐人手机号',
  `creator` bigint(11) DEFAULT NULL COMMENT '记录创建者',
  `is_valid` tinyint(1) DEFAULT NULL COMMENT '删除标志',
  `gmt_create` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modify` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `t_shop_job`;
CREATE TABLE `t_shop_job` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(16) NOT NULL COMMENT '岗位名称',
  `shop_no` bigint(11) NOT NULL COMMENT '美容院编码',
  `creator` bigint(11) DEFAULT NULL COMMENT '记录创建者',
  `is_valid` tinyint(1) NOT NULL COMMENT '删除标志',
  `gmt_create` timestamp NULL NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modify` timestamp NULL NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `t_shop_employee`;
CREATE TABLE `t_shop_employee` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `job_no` bigint(11) NOT NULL COMMENT '岗位编号',
  `name` varchar(16) NOT NULL COMMENT '姓名',
  `phone` varchar(16) NOT NULL COMMENT '手机号',
  `password` varchar(16) NOT NULL COMMENT '登录密码',
  `age` int DEFAULT NULL COMMENT '年龄',
  `birthday` DATETIME DEFAULT NULL COMMENT '生日',
  `is_admin` tinyint(1) NOT NULL COMMENT '是否管理员',
  `manager_no` bigint(11) NOT NULL COMMENT '上级编号',
  `shop_no` bigint(11) NOT NULL COMMENT '美容院编码',
  `creator` bigint(11) DEFAULT NULL COMMENT '记录创建者',
  `is_valid` tinyint(1) NOT NULL COMMENT '删除标志',
  `gmt_create` timestamp NULL NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modify` timestamp NULL NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `t_sales_performance`;
CREATE TABLE `t_sales_performance` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `amount` decimal(25,2) NOT NULL COMMENT '业绩金额',
  `complete_time` datetime NOT NULL COMMENT '业绩完成时间',
  `customer_phone` varchar(11) DEFAULT NULL COMMENT '客户手机号',
  `reward_point` int DEFAULT 0 COMMENT '奖励积分值',
  `emp_no` bigint(11) NOT NULL COMMENT '员工编号',
  `shop_no` bigint(11) NOT NULL COMMENT '美容院编码',
  `creator` bigint(11) DEFAULT NULL COMMENT '记录创建者',
  `is_valid` tinyint(1) NOT NULL COMMENT '删除标志',
  `gmt_create` timestamp NULL NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modify` timestamp NULL NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `t_service_item_record`;
CREATE TABLE `t_service_item_record` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `service_no` bigint(11) NOT NULL COMMENT '项目编号',
  `complete_time` datetime NOT NULL COMMENT '项目完成时间',
  `reward_point` int NOT NULL COMMENT '奖励积分',
  `customer_phone` varchar(11) DEFAULT NULL COMMENT '客户手机号',
  `emp_no` bigint(11) NOT NULL COMMENT '员工编号',
  `shop_no` bigint(11) NOT NULL COMMENT '美容院编码',
  `creator` bigint(11) DEFAULT NULL COMMENT '记录创建者',
  `is_valid` tinyint(1) NOT NULL COMMENT '删除标志',
  `gmt_create` timestamp NULL NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modify` timestamp NULL NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `t_task_record`;
CREATE TABLE `t_task_record` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `task_no` bigint(11) NOT NULL COMMENT '任务编号',
  `complete_time` datetime NOT NULL COMMENT '完成日期',
  `complete_count` int NOT NULL COMMENT '完成次数',
  `emp_no` bigint(11) NOT NULL COMMENT '员工编号',
  `reward_point` int NOT NULL COMMENT '奖励积分',
  `shop_no` bigint(11) NOT NULL COMMENT '美容院编码',
  `creator` bigint(11) DEFAULT NULL COMMENT '记录创建者',
  `is_valid` tinyint(1) NOT NULL COMMENT '删除标志',
  `gmt_create` timestamp NULL NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modify` timestamp NULL NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `t_appointment`;
CREATE TABLE `t_appointment` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `customer_phone` varchar(11) NOT NULL COMMENT '客户手机号',
  `service_no`  bigint(11) DEFAULT NULL COMMENT '项目编号',
  `appoint_time` datetime NOT NULL COMMENT '预约时间',
  `remark` varchar(512) DEFAULT NULL COMMENT '备注',
  `reward_point` int NOT NULL COMMENT '奖励积分',
  `service_emp_no` bigint(11) NOT NULL COMMENT '服务员工编号',
  `appoint_emp_no` bigint(11) NOT NULL COMMENT '预约员工个编号',
  `shop_no` bigint(11) NOT NULL COMMENT '美容院编码',
  `creator` bigint(11) DEFAULT NULL COMMENT '记录创建者',
  `is_valid` tinyint(1) NOT NULL COMMENT '删除标志',
  `gmt_create` timestamp NULL NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modify` timestamp NULL NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `t_attendance_complete`;
CREATE TABLE `t_attendance_complete` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `complete_date` datetime NOT NULL COMMENT '考勤日期',
  `reward_point` int NOT NULL COMMENT '奖励积分',
  `emp_no` bigint(11) NOT NULL COMMENT '员工编号',
  `shop_no` bigint(11) NOT NULL COMMENT '美容院编码',
  `creator` bigint(11) DEFAULT NULL COMMENT '记录创建者',
  `is_valid` tinyint(1) NOT NULL COMMENT '删除标志',
  `gmt_create` timestamp NULL NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modify` timestamp NULL NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `t_daily_record`;
CREATE TABLE `t_daily_record` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `record_date` datetime NOT NULL COMMENT '日记日期',
  `today` text NOT NULL COMMENT '今日总结',
  `tomorrow` text NOT NULL COMMENT '明日计划',
  `manager_no` bigint(11) NOT NULL COMMENT '汇报人编号',
  `shop_no` bigint(11) NOT NULL COMMENT '美容院编码',
  `creator` bigint(11) DEFAULT NULL COMMENT '记录创建者',
  `is_valid` tinyint(1) NOT NULL COMMENT '删除标志',
  `gmt_create` timestamp NULL NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modify` timestamp NULL NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `t_reward_punishment`;
CREATE TABLE `t_reward_punishment` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `point` int NOT NULL COMMENT '奖扣积分值',
  `exec_time` datetime NOT NULL COMMENT '奖扣日期',
  `title` text NOT NULL COMMENT '标题',
  `content` text NOT NULL COMMENT '内容',
  `pic_list` varchar(1024) DEFAULT NULL COMMENT '附件图片',
  `emp_no` bigint(11) NOT NULL COMMENT '员工编号',
  `shop_no` bigint(11) NOT NULL COMMENT '美容院编码',
  `creator` bigint(11) DEFAULT NULL COMMENT '记录创建者',
  `is_valid` tinyint(1) NOT NULL COMMENT '删除标志',
  `gmt_create` timestamp NULL NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modify` timestamp NULL NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `t_apply`;
CREATE TABLE `t_apply` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `apply_emp_no` bigint(11) NOT NULL COMMENT '申请人编号',
  `audit_emp_no` bigint(11) NOT NULL COMMENT '审批人编号',
  `apply_content` text NOT NULL COMMENT '申请内容',
  `pic_list` varchar(1024) DEFAULT NULL COMMENT '附件图片',
  `origin` varchar(16) NOT NULL COMMENT '申请单来源',
  `origin_no` bigint(11) NOT NULL COMMENT '关联单据号',
  `audit_status` varchar(8) NOT NULL COMMENT '审批状态',
  `remark` varchar(1024) DEFAULT NULL COMMENT '审批备注',
  `shop_no` bigint(11) NOT NULL COMMENT '美容院编码',
  `creator` bigint(11) DEFAULT NULL COMMENT '记录创建者',
  `is_valid` tinyint(1) NOT NULL COMMENT '删除标志',
  `gmt_create` timestamp NULL NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modify` timestamp NULL NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `t_apply_dtl`;
CREATE TABLE `t_apply_dtl` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `apply_no` bigint(11) NOT NULL COMMENT '申请单编号',
  `apply_type` varchar(16) NOT NULL COMMENT '申请单类型：通用/考勤',
  `cc_no` bigint(11) NOT NULL COMMENT '抄送人编号',
  `shop_no` bigint(11) NOT NULL COMMENT '美容院编码',
  `creator` bigint(11) DEFAULT NULL COMMENT '记录创建者',
  `is_valid` tinyint(1) NOT NULL COMMENT '删除标志',
  `gmt_create` timestamp NULL NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modify` timestamp NULL NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `t_vacation_apply`;
CREATE TABLE `t_vacation_apply` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `apply_emp_no` bigint(11) NOT NULL COMMENT '申请人编号',
  `audit_emp_no` bigint(11) NOT NULL COMMENT '审批人编号',
  `begin_date` datetime NOT NULL COMMENT '请假开始日期',
  `end_date` datetime NOT NULL COMMENT '请假结束日期',
  `reason` text NOT NULL COMMENT '请假事由',
  `pic_list` varchar(1024) DEFAULT NULL COMMENT '附件图片',
  `audit_status` varchar(8) NOT NULL COMMENT '审批状态',
  `remark` varchar(128) DEFAULT NULL COMMENT '审批备注',
  `shop_no` bigint(11) NOT NULL COMMENT '美容院编码',
  `creator` bigint(11) DEFAULT NULL COMMENT '记录创建者',
  `is_valid` tinyint(1) NOT NULL COMMENT '删除标志',
  `gmt_create` timestamp NULL NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modify` timestamp NULL NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `t_reward_record`;
CREATE TABLE `t_reward_record` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `emp_no` bigint(11) NOT NULL COMMENT '员工编号',
  `reward_time` datetime NOT NULL COMMENT '奖励时间',
  `point` int NOT NULL COMMENT '积分值',
  `origin` varchar(16) NOT NULL COMMENT '积分来源',
  `origin_no` bigint(11) NOT NULL COMMENT '关联单据号',
  `shop_no` bigint(11) NOT NULL COMMENT '美容院编码',
  `creator` bigint(11) DEFAULT NULL COMMENT '记录创建者',
  `is_valid` tinyint(1) NOT NULL COMMENT '删除标志',
  `gmt_create` timestamp NULL NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modify` timestamp NULL NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `t_point`;
CREATE TABLE `t_point` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `emp_no` bigint(11) NOT NULL COMMENT '员工编号',
  `point` int NOT NULL DEFAULT 0 COMMENT '积分值',
  `shop_no` bigint(11) NOT NULL COMMENT '美容院编码',
  `creator` bigint(11) DEFAULT NULL COMMENT '记录创建者',
  `is_valid` tinyint(1) NOT NULL COMMENT '删除标志',
  `gmt_create` timestamp NULL NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modify` timestamp NULL NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `t_task`;
CREATE TABLE `t_task` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `title` varchar(512) NOT NULL COMMENT '任务标题',
  `content` text NOT NULL COMMENT '任务描述',
  `point_no` bigint(11) NOT NULL COMMENT '积分类型',
  `join_limit` int NOT NULL COMMENT '人数限制',
  `scope` varchar(8) NOT NULL COMMENT '可见范围',
  `shop_no` bigint(11) NOT NULL COMMENT '美容院编码',
  `creator` bigint(11) DEFAULT NULL COMMENT '记录创建者',
  `is_valid` tinyint(1) NOT NULL COMMENT '删除标志',
  `gmt_create` timestamp NULL NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modify` timestamp NULL NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `t_task_dtl`;
CREATE TABLE `t_task_dtl` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `task_no` bigint(11) NOT NULL COMMENT '任务编号',
  `emp_no` bigint(11) NOT NULL COMMENT '员工编号',
  `shop_no` bigint(11) NOT NULL COMMENT '美容院编码',
  `creator` bigint(11) DEFAULT NULL COMMENT '记录创建者',
  `is_valid` tinyint(1) NOT NULL COMMENT '删除标志',
  `gmt_create` timestamp NULL NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modify` timestamp NULL NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `t_point_config`;
CREATE TABLE `t_point_config` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `point_name` varchar(128) NOT NULL COMMENT '积分类型名称',
  `point` int NOT NULL COMMENT '奖励分',
  `shop_no` bigint(11) NOT NULL COMMENT '美容院编码',
  `creator` bigint(11) DEFAULT NULL COMMENT '记录创建者',
  `is_valid` tinyint(1) NOT NULL COMMENT '删除标志',
  `gmt_create` timestamp NULL NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modify` timestamp NULL NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `t_customer_info`;
CREATE TABLE `t_customer_info` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(32) NOT NULL COMMENT '客户姓名',
  `sex` varchar(8) NOT NULL COMMENT '性别:MALE,FEMALE',
  `birthday` datetime DEFAULT NULL COMMENT '生日',
  `phone` varchar(11) NOT NULL COMMENT '手机号',
  `favor` varchar(1024) DEFAULT NULL COMMENT '爱好',
  `remark` text DEFAULT NULL COMMENT '备注',
  `year_plan` text DEFAULT NULL COMMENT '年度规划',
  `shop_no` bigint(11) NOT NULL COMMENT '美容院编码',
  `creator` bigint(11) DEFAULT NULL COMMENT '记录创建者',
  `is_valid` tinyint(1) NOT NULL COMMENT '删除标志',
  `gmt_create` timestamp NULL NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modify` timestamp NULL NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `t_customer_trace`;
CREATE TABLE `t_customer_trace` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `customer_no` bigint(11) NOT NULL COMMENT '客户编号',
  `date` datetime NOT NULL COMMENT '日期时间',
  `active_type` varchar(16) NOT NULL COMMENT '活动类型',
  `related_no` bigint(11) NOT NULL COMMENT '关联单据号',
  `shop_no` bigint(11) NOT NULL COMMENT '美容院编码',
  `creator` bigint(11) DEFAULT NULL COMMENT '记录创建者',
  `is_valid` tinyint(1) NOT NULL COMMENT '删除标志',
  `gmt_create` timestamp NULL NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modify` timestamp NULL NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;