/*
 Navicat MySQL Data Transfer

 Source Server         : 阿里云
 Source Server Type    : MySQL
 Source Server Version : 50637
 Source Host           : 116.62.196.88
 Source Database       : recruitment

 Target Server Type    : MySQL
 Target Server Version : 50637
 File Encoding         : utf-8

 Date: 02/02/2018 15:59:21 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `t_permission_relation`
-- ----------------------------
DROP TABLE IF EXISTS `t_permission_relation`;
CREATE TABLE `t_permission_relation` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `permission_id` int(11) DEFAULT NULL,
  `parent_permission_id` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `data_state` int(11) DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=126 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `t_permission_relation`
-- ----------------------------
BEGIN;
INSERT INTO `t_permission_relation` VALUES ('16', '13', '0', '2017-12-24 20:31:42', null, '1'), ('44', '18', '0', '2017-12-24 22:53:32', null, '1'), ('45', '15', '13', '2017-12-24 22:55:24', null, '1'), ('46', '16', '13', '2017-12-24 22:55:24', null, '1'), ('48', '15', '18', '2017-12-24 23:31:40', null, '1'), ('49', '16', '18', '2017-12-24 23:31:40', null, '1'), ('59', '14', '13', '2017-12-26 10:59:35', null, '1'), ('63', '20', '0', '2017-12-27 14:13:16', null, '1'), ('64', '14', '20', '2017-12-27 14:13:16', null, '1'), ('65', '15', '20', '2017-12-27 14:13:16', null, '1'), ('66', '21', '20', '2017-12-27 14:14:13', null, '1'), ('67', '14', '18', '2017-12-27 14:16:41', null, '1'), ('68', '21', '18', '2017-12-27 14:16:41', null, '1'), ('69', '21', '13', '2017-12-27 14:16:50', null, '1'), ('70', '22', '0', '2017-12-27 14:29:11', null, '1'), ('71', '14', '22', '2017-12-27 14:29:11', null, '1'), ('72', '15', '22', '2017-12-27 14:29:11', null, '1'), ('73', '16', '22', '2017-12-27 14:29:11', null, '1'), ('74', '21', '22', '2017-12-27 14:29:11', null, '1'), ('75', '23', '0', '2017-12-27 14:29:51', null, '1'), ('76', '14', '23', '2017-12-27 14:29:52', null, '1'), ('77', '15', '23', '2017-12-27 14:29:52', null, '1'), ('78', '16', '23', '2017-12-27 14:29:52', null, '1'), ('79', '21', '23', '2017-12-27 14:29:52', null, '1'), ('80', '24', '0', '2017-12-27 14:30:24', null, '1'), ('81', '14', '24', '2017-12-27 14:30:24', null, '1'), ('82', '15', '24', '2017-12-27 14:30:24', null, '1'), ('83', '16', '24', '2017-12-27 14:30:24', null, '1'), ('84', '21', '24', '2017-12-27 14:30:24', null, '1'), ('85', '25', '0', '2017-12-27 14:31:53', null, '1'), ('86', '14', '25', '2017-12-27 14:31:53', null, '1'), ('87', '15', '25', '2017-12-27 14:31:53', null, '1'), ('88', '16', '25', '2017-12-27 14:31:53', null, '1'), ('89', '21', '25', '2017-12-27 14:31:53', null, '1'), ('90', '26', '0', '2017-12-27 14:32:31', null, '1'), ('91', '14', '26', '2017-12-27 14:32:31', null, '1'), ('92', '15', '26', '2017-12-27 14:32:31', null, '1'), ('93', '16', '26', '2017-12-27 14:32:31', null, '1'), ('94', '21', '26', '2017-12-27 14:32:31', null, '1'), ('95', '27', '0', '2017-12-27 14:33:29', null, '1'), ('96', '14', '27', '2017-12-27 14:33:29', null, '1'), ('97', '15', '27', '2017-12-27 14:33:29', null, '1'), ('98', '16', '27', '2017-12-27 14:33:29', null, '1'), ('99', '21', '27', '2017-12-27 14:33:29', null, '1'), ('100', '28', '0', '2017-12-27 14:34:51', null, '1'), ('101', '14', '28', '2017-12-27 14:34:51', null, '1'), ('102', '15', '28', '2017-12-27 14:34:51', null, '1'), ('103', '16', '28', '2017-12-27 14:34:51', null, '1'), ('104', '21', '28', '2017-12-27 14:34:51', null, '1'), ('105', '29', '0', '2017-12-27 14:35:27', null, '1'), ('106', '14', '29', '2017-12-27 14:35:27', null, '1'), ('107', '15', '29', '2017-12-27 14:35:27', null, '1'), ('108', '16', '29', '2017-12-27 14:35:27', null, '1'), ('109', '21', '29', '2017-12-27 14:35:27', null, '1'), ('110', '30', '0', '2017-12-27 14:36:12', null, '1'), ('111', '14', '30', '2017-12-27 14:36:12', null, '1'), ('112', '15', '30', '2017-12-27 14:36:12', null, '1'), ('113', '17', '30', '2017-12-27 14:36:12', null, '1'), ('114', '31', '0', '2017-12-27 14:36:59', null, '1'), ('115', '14', '31', '2017-12-27 14:36:59', null, '1'), ('116', '15', '31', '2017-12-27 14:36:59', null, '1'), ('117', '17', '31', '2017-12-27 14:36:59', null, '1'), ('118', '32', '0', '2017-12-27 14:38:51', null, '1'), ('119', '14', '32', '2017-12-27 14:38:51', null, '1'), ('120', '17', '32', '2017-12-27 14:38:51', null, '1'), ('121', '21', '32', '2017-12-27 14:38:51', null, '1'), ('122', '33', '18', '2017-12-31 23:24:51', null, '1'), ('123', '34', '0', '2018-01-21 10:54:54', null, '1'), ('124', '35', '0', '2018-01-21 23:45:05', null, '1'), ('125', '36', '0', '2018-01-22 15:59:12', null, '1');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;