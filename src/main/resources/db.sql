DROP TABLE IF EXISTS `sys_admin`;
CREATE TABLE `sys_admin` (
  `id` varchar(32) NOT NULL COMMENT '管理员用户信息表',
  `name` varchar(10) NOT NULL COMMENT '管理员名称',
  `phone_number` varchar(13) NOT NULL COMMENT '手机号码',
  `account` varchar(20) NOT NULL COMMENT '管理员帐号',
  `password` varchar(32) NOT NULL COMMENT '管理员登录密码，MD5加密串',
  `is_activity` tinyint(1) NOT NULL COMMENT '1启用，2作废',
  `add_time` bigint(13) NOT NULL COMMENT '添加时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='后台管理员表';

DROP TABLE IF EXISTS `sys_admin_role`;
CREATE TABLE `sys_admin_role` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `admin_id` varchar(32) NOT NULL,
  `code` varchar(20) NOT NULL COMMENT '角色编码',
  PRIMARY KEY (`id`,`admin_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='管理员角色';

DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config` (
  `id` varchar(32) NOT NULL,
  `item_key` varchar(20) DEFAULT NULL COMMENT '系统配置项key值',
  `item_value` varchar(1) DEFAULT NULL COMMENT '配置开启值 Y开启 N关闭',
  `sys_damin_id` varchar(32) DEFAULT NULL COMMENT '管理员主键',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `sys_dictionary`;
CREATE TABLE `sys_dictionary` (
  `id` varchar(32) NOT NULL,
  `title` varchar(20) NOT NULL COMMENT '词典标题',
  `tag_code` varchar(20) NOT NULL COMMENT '字典 编码 唯一标识',
  `description` varchar(50) NOT NULL COMMENT '描述简介',
  `res_code` varchar(20) NOT NULL COMMENT '资源组标识',
  `content` varchar(1000) NOT NULL COMMENT '字典显示内容',
  `add_time` bigint(13) NOT NULL COMMENT '添加时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='数据词典';


DROP TABLE IF EXISTS `sys_resources`;
CREATE TABLE `sys_resources` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `menu_name` varchar(20) NOT NULL COMMENT '菜单名称，菜单显示名称',
  `uri` varchar(100) NOT NULL COMMENT '菜单uri地址',
  `is_activity` tinyint(1) NOT NULL COMMENT '活动状态 1 活动 2 作废',
  `group_id` varchar(32) NOT NULL COMMENT '资源组主键',
  `sort` int(4) NOT NULL COMMENT '排序',
  `icon` varchar(20) NOT NULL COMMENT '显示图标',
  `code` varchar(30) NOT NULL COMMENT '资源编码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='管理员资源表';


DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `name` varchar(30) NOT NULL COMMENT '角色名称',
  `code` varchar(20) NOT NULL COMMENT '角色编码',
  `is_activity` tinyint(1) NOT NULL COMMENT '角色活动状态 1启用2停用',
  `sort` int(4) NOT NULL COMMENT '排序',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='管理员角色';


DROP TABLE IF EXISTS `sys_role_resources`;
CREATE TABLE `sys_role_resources` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `role_code` varchar(20) NOT NULL COMMENT '角色编码',
  `resources_id` varchar(32) NOT NULL COMMENT '资源主键',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色资源管理表';



