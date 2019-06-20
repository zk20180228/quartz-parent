/*
Navicat MySQL Data Transfer

Source Server         : taotao
Source Server Version : 50540
Source Host           : localhost:3306
Source Database       : quartz

Target Server Type    : MYSQL
Target Server Version : 50540
File Encoding         : 65001

Date: 2019-06-19 17:43:24
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `t_task`
-- ----------------------------
DROP TABLE IF EXISTS `t_task`;
CREATE TABLE `t_task` (
  `id` varchar(40) NOT NULL COMMENT 'id',
  `job_CN_name` varchar(50) NOT NULL COMMENT '中文名',
  `job_group` varchar(50) NOT NULL COMMENT '任务组',
  `job_class_name` varchar(50) NOT NULL COMMENT 'IOC中bean的名字',
  `job_class_method` varchar(50) NOT NULL COMMENT '对应的方法',
  `job_params` varchar(100) DEFAULT NULL COMMENT '方法参数',
  `job_cron_expression` varchar(30) NOT NULL COMMENT 'corn表达式',
  `job_misfire_policy` int(2) DEFAULT NULL COMMENT '错误执行的策略  0默认(只会执行一次misfire，下次正点执行)1所有misfire的任务会马上执行,下次正点执行 2只会执行一次misfire，下次正点执行 3错过不执行,错过不执行,下次正点执行',
  `job_description` varchar(300) DEFAULT NULL COMMENT '任务描述',
  `job_status` int(2) NOT NULL COMMENT '任务状态 0暂停 1运行中',
  `job_start_time` datetime DEFAULT NULL COMMENT '任务开始时间',
  `job_end_time` datetime DEFAULT NULL COMMENT '任务结束时间',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_task
-- ----------------------------
INSERT INTO `t_task` VALUES ('251eb2cf4705453ab2dd1450e8af17bf', '测试任务', 'test', 'testJob', 'test01', '', '0/5 * * * * ?', '0', '', '0', '2019-06-19 17:14:32', null, '2019-06-19 17:14:37', '2019-06-19 17:17:41');
INSERT INTO `t_task` VALUES ('c0ec3b9d236441d2a2e42188e577f26e', '测试任务02', 'test02', 'testJob', 'test01', '', '0/6 * * * * ?', '0', 'xxx', '0', null, null, '2019-06-19 17:17:31', '2019-06-19 17:20:33');
