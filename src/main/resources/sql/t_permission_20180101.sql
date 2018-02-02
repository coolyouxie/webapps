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

 Date: 02/02/2018 15:59:13 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `t_permission`
-- ----------------------------
DROP TABLE IF EXISTS `t_permission`;
CREATE TABLE `t_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(512) DEFAULT NULL COMMENT '权限名称',
  `code` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `parent_code` varchar(255) DEFAULT NULL,
  `type` varchar(16) DEFAULT NULL,
  `level` int(11) DEFAULT NULL COMMENT '层级，由小到大，0表示根节点，1表示子节点，以此类推',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `t_permission`
-- ----------------------------
BEGIN;
INSERT INTO `t_permission` VALUES ('13', '公司管理菜单权限', 'RETT_MU_COMPANY', null, '2', '2', '2017-12-24 20:31:42', '2017-12-27 14:16:50'), ('14', '查看', 'OP_VIEW', null, '3', '3', '2017-12-24 20:32:35', null), ('15', '修改', 'OP_UPDATE', null, '3', '3', '2017-12-24 20:33:21', null), ('16', '删除', 'OP_DELETE', null, '3', '3', '2017-12-24 20:33:51', null), ('17', '审核', 'OP_APPROVE', null, '3', '3', '2017-12-24 20:34:08', null), ('18', '用户管理菜单权限', 'RETT_MU_USER', null, '2', '2', '2017-12-24 21:19:46', '2017-12-31 23:24:51'), ('19', '数据导出', 'OP_EXPORT', null, '3', '3', '2017-12-24 21:25:04', null), ('20', '发布单菜单权限', 'RETT_MU_RECRUITMENT', null, '2', '2', '2017-12-27 14:13:15', '2017-12-27 14:16:55'), ('21', '添加', 'OP_ADD', null, '3', '3', '2017-12-27 14:14:02', null), ('22', '配置管理菜单权限', 'RETT_MU_CONFIG', null, '2', '2', '2017-12-27 14:29:11', null), ('23', 'Banner管理菜单权限', 'RETT_MU_BANNER', null, '2', '2', '2017-12-27 14:29:51', null), ('24', '消息管理菜单权限', 'RETT_MU_MESSAGE', null, '2', '2', '2017-12-27 14:30:24', null), ('25', '菜单权限管理', 'RETT_MU_MENU', null, '2', '2', '2017-12-27 14:31:53', null), ('26', '操作权限管理菜单权限', 'RETT_MU_OPERATE', null, '2', '2', '2017-12-27 14:32:31', null), ('27', '报名列表菜单权限', 'RETT_MU_ENROLLMENT', null, '2', '2', '2017-12-27 14:33:29', null), ('28', '团队用户列表权限', 'RETT_MU_GROUP', null, '2', '2', '2017-12-27 14:34:51', null), ('29', '推荐列表权限', 'RETT_MU_RECOMMEND', null, '2', '2', '2017-12-27 14:35:27', null), ('30', '入职审核列表权限', 'RETT_MU_ENTRYAPPROVE', null, '2', '2', '2017-12-27 14:36:12', null), ('31', '期满审核列表权限', 'RETT_MU_EXPIREAPPROVE', null, '2', '2', '2017-12-27 14:36:59', null), ('32', '提现审核列表权限', 'RETT_MU_EXPENDITURE', null, '2', '2', '2017-12-27 14:38:51', null), ('33', '权限管理', 'OP_PERMISSION', null, '3', '3', '2017-12-31 23:10:17', null), ('34', '招聘统计菜单权限', 'RETT_MU_RECRUIT_STATISTICS', null, '2', '2', '2018-01-21 10:54:54', '2018-01-21 10:56:30'), ('35', '进度管理菜单权限', 'RETT_MU_RECRUIT_PROCESS', null, '2', '2', '2018-01-21 23:45:05', null), ('36', '分享菜单权限', 'RETT_MU_SHARE', null, '2', '2', '2018-01-22 15:59:12', null);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
