ALTER TABLE `recruitment`.`t_enrollment` ADD COLUMN `talker_id` int COMMENT '分配的客服ID' AFTER `is_history`, ADD COLUMN `entry_approver_id` int COMMENT '分配的入职审核人ID' AFTER `talker_id`, ADD COLUMN `expire_approver_id` int COMMENT '分配的期满审核人ID' AFTER `entry_approver_id`, ADD COLUMN `expenditure_approver_id` int COMMENT '分配的提现审核人ID' AFTER `expire_approver_id`;

ALTER TABLE `recruitment`.`t_enrollment` DROP COLUMN `expenditure_approver_id`, ADD COLUMN `is_entry_approved` int(255) COMMENT '是否已入职审核' AFTER `talker_id`, CHANGE COLUMN `entry_approver_id` `entry_approver_id` int(11) DEFAULT NULL COMMENT '分配的入职审核人ID' AFTER `is_entry_approved`, ADD COLUMN `is_expire_approved` int(255) COMMENT '是否已入职审核' AFTER `entry_approver_id`, CHANGE COLUMN `expire_approver_id` `expire_approver_id` int(11) DEFAULT NULL COMMENT '分配的期满审核人ID' AFTER `is_expire_approved`;

ALTER TABLE `recruitment`.`t_enrollment` CHANGE COLUMN `is_entry_approved` `is_entry_approved` int(255) DEFAULT 0 COMMENT '是否已入职审核,0未审核，1已审核', CHANGE COLUMN `is_expire_approved` `is_expire_approved` int(255) DEFAULT 0 COMMENT '是否已期满审核，0未审核，1已审核';


ALTER TABLE `recruitment`.`t_enrollment` ADD COLUMN `talker_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin COMMENT '负责沟通的客服专员名字' AFTER `expire_approver_id`, ADD COLUMN `entry_approver_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin COMMENT '负责入职审核专员名字' AFTER `talker_name`, ADD COLUMN `expire_approver_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin COMMENT '负责期满审核专员名字' AFTER `entry_approver_name`;












