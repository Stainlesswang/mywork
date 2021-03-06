
DROP TABLE IF EXISTS `meeting_type`;
CREATE TABLE IF NOT EXISTS `meeting_type` (
  `id` int(8) NOT NULL AUTO_INCREMENT,
  `identifier` varchar(20) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `manager_id` varchar(32) DEFAULT NULL,
  `order` int(11) DEFAULT NULL,
  `comment` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='会议类型表';

DROP TABLE IF EXISTS `bt_authcode`;
CREATE TABLE `bt_authcode` (
  `mid` int(8) NOT NULL AUTO_INCREMENT COMMENT '授权码id',
  `authcode` varchar(50) NOT NULL COMMENT '授权码',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `status` int(1) DEFAULT '0' COMMENT '使用状态,0未使用1已使用',
  `prefix` varchar(20) DEFAULT NULL COMMENT '前缀',
  PRIMARY KEY (`mid`),
  UNIQUE KEY `uniqueCode` (`authcode`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='授权码表';
DROP TABLE IF EXISTS `bt_organization`;
CREATE TABLE `bt_organization` (
  `org_id` int(8) NOT NULL AUTO_INCREMENT COMMENT '组织id',
  `org_name` varchar(50) NOT NULL COMMENT '组织名称',
  `parent_code` varchar(500) DEFAULT NULL COMMENT '上级组织id集合，使用-隔开',
  `parent_id` int(8) DEFAULT NULL COMMENT '上级组织id',
  `remark` varchar(2000) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`org_id`),
  KEY `parent_id` (`parent_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;
DROP TABLE IF EXISTS `bt_permission`;
CREATE TABLE `bt_permission` (
  `per_id` int(8) NOT NULL AUTO_INCREMENT COMMENT '权限id',
  `per_type` int(2) NOT NULL COMMENT '权限类型，1会议材料推送，2设备管理',
  `per_name` varchar(50) DEFAULT NULL COMMENT '权限类型名称',
  `per_subtype` int(2) NOT NULL COMMENT '权限类型中的下级权限类型，如果是设备管理，就分为模块管理员，pad信息维护员..',
  `user_id` varchar(36) NOT NULL COMMENT '用户id',
  `savetime` datetime DEFAULT NULL COMMENT '保存时间',
  PRIMARY KEY (`per_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='系统权限表';
DROP TABLE IF EXISTS `bt_user`;
CREATE TABLE `bt_user` (
  `user_id` varchar(36) NOT NULL COMMENT '用户id',
  `user_name` varchar(30) NOT NULL COMMENT '账号,用户名',
  `password` varchar(50) NOT NULL COMMENT '密码',
  `real_name` varchar(50) DEFAULT NULL COMMENT '真实姓名',
  `sex` int(1) NOT NULL DEFAULT '0' COMMENT '性别，0男1女2未知',
  `position` varchar(150) DEFAULT NULL COMMENT '职务',
  `org_id` int(8) DEFAULT NULL COMMENT '所属组织(部门)',
  `mobile` varchar(50) DEFAULT NULL COMMENT '手机号',
  `email` varchar(150) DEFAULT NULL COMMENT '邮箱',
  `admin_type` int(1) DEFAULT '0' COMMENT '管理员类型，0普通人员，2普通管理员',
  `savetime` datetime DEFAULT NULL COMMENT '保存时间',
  `remark` varchar(1000) DEFAULT NULL COMMENT '备注',
  `addr` varchar(1000) DEFAULT NULL COMMENT '住址',
  `tel` varchar(50) DEFAULT NULL COMMENT '固定电话',
  `last_update_time` datetime DEFAULT NULL COMMENT '最近更新时间',
  `is_super` int(1) DEFAULT '0' COMMENT '是否是超级管理员，0否1是',
  `is_temp` INT(1) NULL DEFAULT '0' COMMENT '是否是临时用户，0否1是',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `usernameUnique` (`user_name`),
  KEY `org_id` (`org_id`),
  CONSTRAINT `fk_user_org` FOREIGN KEY (`org_id`) REFERENCES `bt_organization` (`org_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
DROP TABLE IF EXISTS `sub_app_meeting`;
CREATE TABLE IF NOT EXISTS `sub_app_meeting` (
  `mid` varchar(60) NOT NULL COMMENT '会议唯一ID',
  `meet_time` datetime NOT NULL COMMENT '会议开始时间',
  `meet_name` varchar(255) NOT NULL COMMENT '会议标题',
  `meet_type` int(11) DEFAULT NULL COMMENT '会议类型',
  `meet_addr` varchar(255) DEFAULT NULL COMMENT '会议地点',
  `meet_attend` varchar(1024) DEFAULT NULL COMMENT '参会领导-暂不使用',
  `meet_attend_ids` text COMMENT '参会领导id集合，使用,隔开',
  `remark` varchar(2000) DEFAULT NULL COMMENT '备注信息',
  `filename` varchar(2000) DEFAULT NULL COMMENT '附件名称,暂未使用',
  `attach_path` varchar(255) DEFAULT NULL COMMENT '附件存放路径,暂未使用',
  `last_update_time` varchar(100) DEFAULT NULL COMMENT '最后修改时间',
  `is_ipad` int(1) DEFAULT '0' COMMENT '是否已推送ipad,1.是',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `user_id` varchar(50) DEFAULT NULL COMMENT '拟稿人',
  `draft_time` datetime NOT NULL COMMENT '拟稿时间',
  `push_time` datetime DEFAULT NULL COMMENT '推送时间',
  `status` int(1) DEFAULT '0' COMMENT '会议状态，0未启用1已启用2已停用',
  `master` varchar(50) DEFAULT NULL COMMENT '主持人',
  `end_time` datetime DEFAULT NULL COMMENT '会议结束时间',
  PRIMARY KEY (`mid`),
  KEY `fk_sub_app_meeting_user` (`user_id`),
  KEY `FK_sub_app_meeting_meeting_type` (`meet_type`),
  CONSTRAINT `FK_sub_app_meeting_meeting_type` FOREIGN KEY (`meet_type`) REFERENCES `meeting_type` (`id`),
  CONSTRAINT `fk_sub_app_meeting_user` FOREIGN KEY (`user_id`) REFERENCES `bt_user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='会议主表';
DROP TABLE IF EXISTS `sub_app_meeting_file`;
CREATE TABLE `sub_app_meeting_file` (
  `meeting_mid` varchar(60) DEFAULT NULL COMMENT '会议唯一ID',
  `file_mid` varchar(60) DEFAULT NULL COMMENT '附件编码名称',
  `attach_name` varchar(300) DEFAULT NULL COMMENT '附件中文名称',
  `attach_path` varchar(255) DEFAULT NULL COMMENT '附件路径',
  `last_update_time` varchar(100) DEFAULT NULL COMMENT '最后修改时间',
  `attach_realname` varchar(60) DEFAULT NULL COMMENT '附件的真实名字',
  `attach_size` varchar(20) DEFAULT NULL COMMENT '附件大小',
  `prefix` varchar(50) DEFAULT NULL COMMENT '附件前缀',
  `suffix` varchar(50) DEFAULT NULL COMMENT '附件后缀',
  `attach_type` int(1) DEFAULT '0' COMMENT '文档类型，0普通，1合并后的',
  `token` varchar(100) DEFAULT NULL COMMENT '用来区分文件',
  `uploader_id` VARCHAR(50) NULL DEFAULT NULL COMMENT '上传人id'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='会议附件存放表';
DROP TABLE IF EXISTS `sub_app_user`;
CREATE TABLE `sub_app_user` (
  `mid` varchar(100) NOT NULL COMMENT '设备的唯一标识',
  `user_id` varchar(50) DEFAULT NULL COMMENT '设备使用人id',
  `register_id` varchar(50) DEFAULT NULL COMMENT '设备登记人',
  `job_number` varchar(100) DEFAULT NULL COMMENT '工号',
  `passwd_flag` char(1) DEFAULT '1' COMMENT '密码标示 默认为1 暂未使用',
  `passwd` varchar(100) DEFAULT NULL COMMENT '初始密码',
  `ipad_uuid` varchar(100) DEFAULT NULL COMMENT 'Ipad编号',
  `create_time` varchar(100) DEFAULT NULL COMMENT '文档创建时间-登记日期',
  `authorationcode` varchar(100) DEFAULT NULL COMMENT '授权码',
  `status` char(1) NOT NULL DEFAULT '1' COMMENT '状态   默认为1 暂未使用',
  `is_binding` int(1) DEFAULT '0' COMMENT '设置是否绑定,1.已绑定',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `energy` INT(1) NULL DEFAULT NULL COMMENT '设备电量1-100',
  `storage` varchar(100) DEFAULT NULL COMMENT '设备存储信息',
  PRIMARY KEY (`mid`),
  KEY `fk_sub_app_user_u` (`user_id`),
  CONSTRAINT `fk_sub_app_user_u` FOREIGN KEY (`user_id`) REFERENCES `bt_user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='设备使用人表';

DROP TABLE IF EXISTS `file_download`;
CREATE TABLE `file_download` (
	`id` VARCHAR(36) NOT NULL,
	`user_id` VARCHAR(36) NOT NULL COMMENT '下载人id',
	`mid` VARCHAR(36) NOT NULL COMMENT '会议id',
	`download_time` VARCHAR(36) NOT NULL COMMENT '下载时间',
	PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='记录会议文件的下载详情';