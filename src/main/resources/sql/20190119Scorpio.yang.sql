/**
*对应变更内容：
*邀请码功能完成（功能测试完成）
*1：增加用户发送邀请码功能
*2：用户注册填写邀请码功能
*3：被邀请用户注册后，生成注册红包发放到邀请人红包列表，目前仅支持一次方法
*4：被邀请用户入职、期满后，生成对应红包，发放到邀请人红包列表，目前仅支持一次方法
*5：增加用户红包列表查询（10条分页查询）
*6：增加用户红包领取功能
**/


ALTER TABLE `recruitment`.`t_user` ADD COLUMN `invite_code` varchar(10) AFTER `bank_card_num`;

ALTER TABLE `recruitment`.`t_recommend` ADD COLUMN `invite_code` varchar(10) AFTER `mobile`;

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `t_param_config`
-- ----------------------------
DROP TABLE IF EXISTS `t_param_config`;
CREATE TABLE `t_param_config` (
  `id` int(11) NOT NULL,
  `name` varchar(20) NOT NULL,
  `type` int(11) NOT NULL COMMENT '1，固定值。2，浮动值',
  `min_num` decimal(20,2) DEFAULT NULL,
  `max_num` decimal(20,2) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `t_param_config`
-- ----------------------------
BEGIN;
INSERT INTO `t_param_config` VALUES ('1', '邀请红包设置', '2', '0.10', '0.50', '2018-01-17 01:01:37', '2018-01-17 17:09:05'), ('2', '入职红包设置', '1', '0.00', '0.00', '2018-01-17 16:48:26', null), ('3', '期满红包设置', '1', '0.00', '0.00', '2018-01-17 17:28:55', null);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;


SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `t_user_award`
-- ----------------------------
DROP TABLE IF EXISTS `t_user_award`;
CREATE TABLE `t_user_award` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL COMMENT '红包接收人id',
  `invite_user_id` int(11) DEFAULT NULL COMMENT '被邀请人的id',
  `param_config_id` int(11) NOT NULL COMMENT '红包配置表的id',
  `price` decimal(20,2) NOT NULL COMMENT '实际红包金额',
  `stat` int(11) NOT NULL COMMENT '红包状态，1、待领取，2、已领取。',
  `create_time` datetime NOT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

SET FOREIGN_KEY_CHECKS = 1;

ALTER TABLE `recruitment`.`t_user` ADD COLUMN `award_flag` varchar(10) AFTER `invite_code`;

update t_user set award_flag = '000';

ALTER TABLE `recruitment`.`t_user` CHANGE COLUMN `award_flag` `award_flag` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '用户被邀请后，邀请人的红包生成状态，按位获取，分别为，注册，入职，期满，0为未获取，1为已获取或获取一次';
