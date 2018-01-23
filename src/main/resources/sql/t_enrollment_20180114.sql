ALTER TABLE `recruitment`.`t_enrollment` ADD COLUMN `interviwe_intention` int DEFAULT 0 COMMENT '默认值0，1同意面试，2不同意面试。' AFTER `expire_approver_name`;

ALTER TABLE `recruitment`.`t_enrollment` CHANGE COLUMN `interviwe_intention` `interview_intention` int(11) DEFAULT 0 COMMENT '默认值0，1同意面试，2不同意面试。', ADD COLUMN `intention_city_id` int AFTER `interview_intention`, ADD COLUMN `interview_time` datetime COMMENT '预约的面试时间' AFTER `intention_city_id`;











