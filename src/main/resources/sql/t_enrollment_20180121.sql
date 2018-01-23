ALTER TABLE `recruitment`.`t_enrollment` ADD COLUMN `old_talker_id` int COMMENT '上个招聘员ID' AFTER `is_latest`, ADD COLUMN `old_talker_name` varchar(255) COMMENT '上个招聘员姓名' AFTER `old_talker_id`;














