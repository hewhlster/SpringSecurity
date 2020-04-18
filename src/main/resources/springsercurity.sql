/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 5.7.17-log : Database - springsecurity
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`springsecurity` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `springsecurity`;

/*Table structure for table `tbl_permission` */

DROP TABLE IF EXISTS `tbl_permission`;

CREATE TABLE `tbl_permission` (
  `id` varchar(32) NOT NULL,
  `pname` varchar(32) DEFAULT NULL,
  `purl` varchar(100) DEFAULT NULL,
  `pmemo` varbinary(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tbl_permission` */

insert  into `tbl_permission`(`id`,`pname`,`purl`,`pmemo`) values ('p1000',NULL,'/product/add',NULL),('p1001',NULL,'/product/delete',NULL),('p1002',NULL,'/product/update',NULL),('p1003',NULL,'/product/select',NULL);

/*Table structure for table `tbl_role` */

DROP TABLE IF EXISTS `tbl_role`;

CREATE TABLE `tbl_role` (
  `id` varchar(32) NOT NULL,
  `rname` varchar(32) DEFAULT NULL,
  `rtag` varbinary(32) DEFAULT NULL,
  `rmemo` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tbl_role` */

insert  into `tbl_role`(`id`,`rname`,`rtag`,`rmemo`) values ('r1000','超级管理员','ROLE_ADMIN','可以操作所有'),('r1001','管理员','ROLE_MANAGER',NULL);

/*Table structure for table `tbl_role_permission` */

DROP TABLE IF EXISTS `tbl_role_permission`;

CREATE TABLE `tbl_role_permission` (
  `rid` varchar(32) NOT NULL,
  `pid` varchar(32) NOT NULL,
  PRIMARY KEY (`rid`,`pid`),
  KEY `pid` (`pid`),
  CONSTRAINT `tbl_role_permission_ibfk_1` FOREIGN KEY (`rid`) REFERENCES `tbl_role` (`id`),
  CONSTRAINT `tbl_role_permission_ibfk_2` FOREIGN KEY (`pid`) REFERENCES `tbl_permission` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tbl_role_permission` */

insert  into `tbl_role_permission`(`rid`,`pid`) values ('r1000','p1000'),('r1001','p1000'),('r1000','p1001'),('r1001','p1001'),('r1000','p1002'),('r1000','p1003');

/*Table structure for table `tbl_user` */

DROP TABLE IF EXISTS `tbl_user`;

CREATE TABLE `tbl_user` (
  `id` varbinary(32) NOT NULL,
  `ucode` varchar(32) DEFAULT NULL,
  `username` varbinary(32) DEFAULT NULL,
  `password` varbinary(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tbl_user` */

insert  into `tbl_user`(`id`,`ucode`,`username`,`password`) values ('u1000','1000','jack','rose'),('u1001','2000','rose','jack');

/*Table structure for table `tbl_user_role` */

DROP TABLE IF EXISTS `tbl_user_role`;

CREATE TABLE `tbl_user_role` (
  `uid` varchar(32) NOT NULL,
  `rid` varchar(32) NOT NULL,
  PRIMARY KEY (`uid`,`rid`),
  KEY `rid` (`rid`),
  CONSTRAINT `tbl_user_role_ibfk_1` FOREIGN KEY (`rid`) REFERENCES `tbl_role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tbl_user_role` */

insert  into `tbl_user_role`(`uid`,`rid`) values ('u1000','r1000'),('u1001','r1001');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
