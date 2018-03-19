/*
Navicat MySQL Data Transfer

Source Server         : cxzp
Source Server Version : 50621
Source Host           : 192.168.1.8:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 50621
File Encoding         : 65001

Date: 2017-03-17 11:17:30
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_admin
-- ----------------------------
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

-- ----------------------------
-- Records of sys_admin
-- ----------------------------
INSERT INTO `sys_admin` VALUES ('7cbd6f8d277345c396866e126b7449de', 'admin', 'none', 'admin', 'e10adc3949ba59abbe56e057f20f883e', '1', '1');

-- ----------------------------
-- Table structure for sys_admin_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_admin_role`;
CREATE TABLE `sys_admin_role` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `admin_id` varchar(32) NOT NULL,
  `code` varchar(20) NOT NULL COMMENT '角色编码',
  PRIMARY KEY (`id`,`admin_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='管理员角色';

-- ----------------------------
-- Records of sys_admin_role
-- ----------------------------
INSERT INTO `sys_admin_role` VALUES ('098f5d979a2a4cdbb83eca61bdbab6a6', '7cbd6f8d277345c396866e126b7449de', 'admin');

-- ----------------------------
-- Table structure for sys_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config` (
  `id` varchar(32) NOT NULL,
  `item_key` varchar(20) DEFAULT NULL COMMENT '系统配置项key值',
  `item_value` varchar(1) DEFAULT NULL COMMENT '配置开启值 Y开启 N关闭',
  `sys_damin_id` varchar(32) DEFAULT NULL COMMENT '管理员主键',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_config
-- ----------------------------

-- ----------------------------
-- Table structure for sys_dictionary
-- ----------------------------
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

-- ----------------------------
-- Records of sys_dictionary
-- ----------------------------

-- ----------------------------
-- Table structure for sys_resources
-- ----------------------------
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

-- ----------------------------
-- Records of sys_resources
-- ----------------------------
INSERT INTO `sys_resources` VALUES ('009288b298ef46e19674678184854643', '数据词典', '/manage/sysdictionary/main.html', '1', 'root', '1', 'fa-database', 'sysdictionary');
INSERT INTO `sys_resources` VALUES ('0312043b388e4767b0a820754036b6ab', '菜单', '/manage/resource/menumain.html', '1', 'f180061547bc44479af2a73d1e99614d', '1', 'none', 'sys-menumain');
INSERT INTO `sys_resources` VALUES ('09a43d0a7a7541c1bd1dac1c67e108be', '角色', '/manage/resource/rolemain.html', '1', 'f180061547bc44479af2a73d1e99614d', '2', 'none', 'sys-rolemain');
INSERT INTO `sys_resources` VALUES ('ad11b7f2cefe402890d38b7ea2d767da', '管理员', '/manage/resource/adminmain.html', '1', 'f180061547bc44479af2a73d1e99614d', '3', 'none', 'sys-adminmain');
INSERT INTO `sys_resources` VALUES ('f180061547bc44479af2a73d1e99614d', '系统管理', 'none', '1', 'root', '20', 'fa-rocket', 'sys');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `name` varchar(30) NOT NULL COMMENT '角色名称',
  `code` varchar(20) NOT NULL COMMENT '角色编码',
  `is_activity` tinyint(1) NOT NULL COMMENT '角色活动状态 1启用2停用',
  `sort` int(4) NOT NULL COMMENT '排序',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='管理员角色';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('7cbd6f8d277345c396866e126b7449de', '超级管理员', 'admin', '1', '0');

-- ----------------------------
-- Table structure for sys_role_resources
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_resources`;
CREATE TABLE `sys_role_resources` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `role_code` varchar(20) NOT NULL COMMENT '角色编码',
  `resources_id` varchar(32) NOT NULL COMMENT '资源主键',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色资源管理表';

-- ----------------------------
-- Records of sys_role_resources
-- ----------------------------
INSERT INTO `sys_role_resources` VALUES ('402f93374abe4bf89f848a5f684a9fb9', 'admin', 'f180061547bc44479af2a73d1e99614d');
INSERT INTO `sys_role_resources` VALUES ('4fe67a12d82b465f9aece5b1fc23f195', 'admin', '09a43d0a7a7541c1bd1dac1c67e108be');
INSERT INTO `sys_role_resources` VALUES ('638ddad65bc0479581695b67d081d4ac', 'admin', '009288b298ef46e19674678184854643');
INSERT INTO `sys_role_resources` VALUES ('9df086b566c24fa5ae2e9ae389726989', 'admin', '0312043b388e4767b0a820754036b6ab');
INSERT INTO `sys_role_resources` VALUES ('d8f32323c7c74ba4af0f3e5142cd938d', 'admin', 'ad11b7f2cefe402890d38b7ea2d767da');

