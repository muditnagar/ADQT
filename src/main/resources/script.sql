DROP SCHEMA IF EXISTS `let_us_see`;

CREATE SCHEMA `let_us_see`;

USE SCHEMA `let_us_see`;


DROP TABLE IF EXISTS `user_details`;

CREATE TABLE IF NOT EXISTS `user_details` (
	`id` int(10) NOT NULL AUTO_INCREMENT,
	`name` varchar(250) NOT NULL,
	`email` varchar(250) NOT NULL,
	`company_name` varchar(250) NOT NULL,
	`password` varchar(500) NOT NULL,
	PRIMARY KEY (`id`),
	UNIQUE KEY `email` (`email`)
);

DROP TABLE IF EXISTS `column_info`;

CREATE TABLE IF NOT  EXISTS `column_info`(
	`id` int(10) PRIMARY KEY AUTO_INCREMENT,
	`table_id` int(10) NOT NULL,
	`column_name` varchar(250) NOT NULL,
	`data_type`  varchar(250) NOT NULL,
    `column_index` int
);

DROP TABLE IF EXISTS `table_info`;

CREATE TABLE IF NOT EXISTS `table_info`(
	`id` int(10) PRIMARY KEY AUTO_INCREMENT,
	`table_name` varchar(250) NOT NULL
);

DROP TABLE IF EXISTS `rule_value`;

CREATE TABLE IF NOT EXISTS `rule_value`(
	`id` int(10) PRIMARY KEY AUTO_INCREMENT,
	`table_name` varchar(250) ,
	`column_name` varchar(250) ,
	`data_type`  varchar(250),
    `column_index` int ,
	`rule_type` varchar(250),
	`rule_key` varchar(250) ,
	`rule_value` varchar(250)
);
	
