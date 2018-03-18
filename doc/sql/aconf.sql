/*
SQLyog Ultimate v11.24 (32 bit)
MySQL - 5.5.44-0ubuntu0.12.04.1 : Database - publess
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`publess` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `publess`;

/*Table structure for table `app` */

DROP TABLE IF EXISTS `app`;

CREATE TABLE `app` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '应用id',
  `name` varchar(48) DEFAULT NULL COMMENT '应用名称',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  `creator` varchar(255) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `valid` tinyint(2) DEFAULT NULL COMMENT '0--已停用,1--正常',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

/*Table structure for table `app_register` */

DROP TABLE IF EXISTS `app_register`;

CREATE TABLE `app_register` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'app_id',
  `name` varchar(48) NOT NULL COMMENT '应用名称',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  `rnum` int(11) NOT NULL COMMENT 'r_num',
  `valid` tinyint(4) DEFAULT NULL COMMENT '有效位',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1002 DEFAULT CHARSET=utf8;

/*Table structure for table `bss` */

DROP TABLE IF EXISTS `bss`;

CREATE TABLE `bss` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '业务id',
  `code` varchar(48) NOT NULL COMMENT 'code标识，全局唯一',
  `name` varchar(48) DEFAULT NULL COMMENT '业务名称',
  `creator` varchar(255) DEFAULT NULL COMMENT '创建者',
  `developer` varchar(255) DEFAULT NULL COMMENT '开发者',
  `tester` varchar(255) DEFAULT NULL COMMENT '测试人员',
  `operator` varchar(255) DEFAULT NULL COMMENT '运营人员',
  `effective_type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '有效期类型，0--长期有效，1--区间内有效',
  `effective_time` datetime DEFAULT NULL COMMENT '生效时间',
  `failure_time` datetime DEFAULT NULL COMMENT '失效时间',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  `app_id` bigint(20) DEFAULT NULL COMMENT '对应AppId',
  `valid` tinyint(2) NOT NULL DEFAULT '1' COMMENT '0--已删除,1--正常,2--还没生效,3--结束',
  `version` bigint(20) DEFAULT '0' COMMENT '版本号',
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=1028 DEFAULT CHARSET=utf8;

/*Table structure for table `bss_archived` */

DROP TABLE IF EXISTS `bss_archived`;

CREATE TABLE `bss_archived` (
  `id` bigint(20) NOT NULL COMMENT '业务id',
  `code` varchar(48) NOT NULL COMMENT 'code标识，全局唯一',
  `name` varchar(48) DEFAULT NULL COMMENT '业务名称',
  `creator` varchar(255) DEFAULT NULL COMMENT '创建者',
  `developer` varchar(255) DEFAULT NULL COMMENT '开发者',
  `tester` varchar(255) DEFAULT NULL COMMENT '测试人员',
  `operator` varchar(255) DEFAULT NULL COMMENT '运营人员',
  `effective_type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '有效期类型，0--长期有效，1--区间内有效',
  `effective_time` datetime DEFAULT NULL COMMENT '生效时间',
  `failure_time` datetime DEFAULT NULL COMMENT '失效时间',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  `app_id` bigint(20) DEFAULT NULL COMMENT '对应AppId',
  `valid` tinyint(2) NOT NULL DEFAULT '1' COMMENT '0--已停用,1--正常',
  `version` bigint(20) DEFAULT '0' COMMENT '版本号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `conditions` */

DROP TABLE IF EXISTS `conditions`;

CREATE TABLE `conditions` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '条件ID',
  `config_id` bigint(20) NOT NULL COMMENT '所属配置项ID',
  `name` varchar(48) DEFAULT NULL COMMENT '条件名称',
  `value` varchar(4096) DEFAULT NULL COMMENT '下发值',
  `creator` varchar(48) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `seq` int(11) DEFAULT NULL COMMENT '排列次序',
  `state` int(11) DEFAULT NULL COMMENT '100--已删除,101--生效，102--停用',
  `value_type` tinyint(4) DEFAULT '0' COMMENT '值类型,0--普通,1--表达式',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=57 DEFAULT CHARSET=utf8;

/*Table structure for table `config` */

DROP TABLE IF EXISTS `config`;

CREATE TABLE `config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '配置项id',
  `name` varchar(48) NOT NULL COMMENT '键',
  `value` varchar(4096) DEFAULT NULL COMMENT '值',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  `bss_id` bigint(20) DEFAULT NULL COMMENT '业务id',
  `creator` varchar(255) DEFAULT NULL COMMENT '操作员',
  `create_time` datetime DEFAULT NULL COMMENT '更新时间',
  `version` bigint(20) DEFAULT NULL COMMENT '版本号',
  `valid` tinyint(2) DEFAULT NULL COMMENT '状态，0--停用，1--正常',
  `send_default` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否下发默认值，默认不下发.0--不下发,1--下发',
  `value_type` tinyint(4) DEFAULT '0' COMMENT '值类型，0--普通，1--表达式',
  PRIMARY KEY (`id`),
  UNIQUE KEY `key` (`name`,`bss_id`)
) ENGINE=InnoDB AUTO_INCREMENT=80 DEFAULT CHARSET=utf8;

/*Table structure for table `config_history` */

DROP TABLE IF EXISTS `config_history`;

CREATE TABLE `config_history` (
  `serial_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '序列id',
  `id` bigint(20) NOT NULL COMMENT '配置项id',
  `name` varchar(48) NOT NULL COMMENT '键',
  `value` varchar(255) DEFAULT NULL COMMENT '默认值',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  `bss_id` bigint(20) DEFAULT NULL COMMENT '业务id',
  `creator` varchar(255) DEFAULT NULL COMMENT '操作员',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `version` bigint(20) DEFAULT NULL COMMENT '版本号',
  `valid` tinyint(2) DEFAULT NULL COMMENT '0--停用，1--正常',
  `send_default` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否下发默认值,0--不发送,1--发送',
  `value_type` tinyint(4) DEFAULT '0' COMMENT '值类型，0--普通，1--表达式',
  PRIMARY KEY (`serial_id`),
  UNIQUE KEY `name` (`name`,`bss_id`,`version`)
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8;

/*Table structure for table `filter` */

DROP TABLE IF EXISTS `filter`;

CREATE TABLE `filter` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '拦截规则id',
  `belong_id` bigint(20) NOT NULL COMMENT '所属业务或配置项ID或条件id',
  `basis` varchar(48) DEFAULT NULL COMMENT '依据(uid,sid...)',
  `operator` varchar(48) DEFAULT NULL COMMENT '操作',
  `boundary` varchar(255) DEFAULT NULL COMMENT '比较值',
  `creator` varchar(48) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `valid` tinyint(4) DEFAULT NULL COMMENT '状态,0--停用,1--正常',
  `belong_type` tinyint(2) DEFAULT NULL COMMENT '类型,0--业务拦截器,1--配置项拦截器,2--条件项拦截器',
  `basis_type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '依据类型，0--普通，1--扩展',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=103 DEFAULT CHARSET=utf8;

/*Table structure for table `operation_log` */

DROP TABLE IF EXISTS `operation_log`;

CREATE TABLE `operation_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `passport` varchar(48) DEFAULT NULL COMMENT '操作人',
  `address` varchar(16) DEFAULT NULL COMMENT 'ip',
  `type` varchar(255) NOT NULL COMMENT '操作类型',
  `params` varchar(1024) NOT NULL COMMENT '操作参数',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '操作时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1122 DEFAULT CHARSET=utf8;

/*Table structure for table `push` */

DROP TABLE IF EXISTS `push`;

CREATE TABLE `push` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'push id',
  `bss_id` bigint(20) NOT NULL COMMENT '所属业务的id',
  `title` varchar(48) DEFAULT NULL COMMENT 'push标题',
  `description` varchar(255) DEFAULT NULL COMMENT '备注',
  `creator` varchar(48) DEFAULT NULL COMMENT '创建者',
  `effective_time` datetime DEFAULT NULL COMMENT '生效时间点,该时间要在bss有效时间内',
  `sids` varchar(255) DEFAULT NULL COMMENT '推送频道列表，逗号分隔',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `state` int(4) DEFAULT NULL COMMENT '状态标记,100--已删除,101--生效,102--推送中,103--已推送,104--失效',
  `sys` tinyint(2) DEFAULT NULL COMMENT '下发平台.0--both,1--pc，2--mobile',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

/*Table structure for table `resource` */

DROP TABLE IF EXISTS `resource`;

CREATE TABLE `resource` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `url` varchar(500) NOT NULL COMMENT 'url地址',
  `description` varchar(128) DEFAULT NULL COMMENT '简述',
  `config_id` bigint(20) DEFAULT NULL COMMENT '所属配置项',
  `version` bigint(20) DEFAULT NULL COMMENT '版本号',
  `upload_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '上传时间',
  `creator` varchar(48) DEFAULT NULL COMMENT '上传人(dw_)',
  `state` tinyint(4) DEFAULT NULL COMMENT '0--已删,1--normal',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

/*Table structure for table `user_auth` */

DROP TABLE IF EXISTS `user_auth`;

CREATE TABLE `user_auth` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `passport` varchar(255) NOT NULL COMMENT '海度通行证',
  `avatar` varchar(255) DEFAULT NULL COMMENT '头像',
  `job_code` varchar(40) DEFAULT NULL COMMENT '工号',
  `telephone` varchar(15) DEFAULT NULL COMMENT '电话',
  `name` varchar(48) DEFAULT NULL COMMENT '姓名',
  `email` varchar(60) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL COMMENT '角色',
  `state` tinyint(4) DEFAULT NULL COMMENT '0--禁用,1--正常',
  `bss_role` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
