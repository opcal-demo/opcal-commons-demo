CREATE TABLE `sd_user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_date` datetime DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  `first_name` varchar(250) DEFAULT NULL,
  `last_name` varchar(250) DEFAULT NULL,
  `gender` varchar(6) DEFAULT NULL,
  `age` int DEFAULT '0',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `IDX_full_name` (`first_name`,`last_name`),
  KEY `IDX_age` (`age`),
  KEY `IDX_deleted` (`is_deleted`)
);