/*
SQLyog Community Edition- MySQL GUI v8.14 
MySQL - 5.7.35-log : Database - shop
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`shop` /*!40100 DEFAULT CHARACTER SET gbk */;

USE `shop`;

/*Table structure for table `cars` */

DROP TABLE IF EXISTS `cars`;

CREATE TABLE `cars` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `gid` int(11) NOT NULL,
  `uid` varchar(20) NOT NULL,
  `number` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1895282153 DEFAULT CHARSET=gbk;

/*Data for the table `cars` */

/*Table structure for table `goods` */

DROP TABLE IF EXISTS `goods`;

CREATE TABLE `goods` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(100) NOT NULL COMMENT '标题',
  `unit_price` double NOT NULL COMMENT '单价',
  `stock` int(11) NOT NULL COMMENT '库存',
  `photo` varchar(100) NOT NULL COMMENT '预览图',
  `introduction` text COMMENT '简介',
  `classification` varchar(20) DEFAULT '其他' COMMENT '分类',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(20) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `update_by` varchar(20) DEFAULT NULL COMMENT '修改人',
  `status` int(11) DEFAULT '1' COMMENT '状态',
  `sales` int(11) DEFAULT '0' COMMENT '销量',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=gbk;

/*Data for the table `goods` */

insert  into `goods`(`id`,`title`,`unit_price`,`stock`,`photo`,`introduction`,`classification`,`create_time`,`create_by`,`update_time`,`update_by`,`status`,`sales`) values (1,'iphone 13pro max 1TB 远峰蓝色',12999,7,'https://2f.zol-img.com.cn/product/215_320x240/933/ceOMvXTRF9M.jpg','Apple iPhone 13 Pro (A2639) 1TB 远峰蓝色 支持移动联通电信5G 双卡双待手机','手机','2021-11-10 11:36:02','admin','2021-12-21 16:34:31','admin',1,11),(2,'Redmi Note 11 Pro+',2099,10,'https://cdn.cnbj0.fds.api.mi-img.com/b2c-shopapi-pms/pms_1635332995.28485388.jpg','Redmi Note 11 Pro+ 5G智能手机120W充电1亿像素天玑920液冷游戏芯小米红米','手机','2021-11-10 11:36:02','admin','2021-12-20 15:40:37','admin',1,8),(3,'Redmi Note 9 Pro 5G',1399,8,'https://img11.360buyimg.com/n7/jfs/t1/215393/39/4597/128233/619201b1Eb0df80bd/5cee5951d6bd1612.jpg','Redmi Note 9 Pro 5G 一亿像素 骁龙750G 33W快充 120Hz刷新率 湖光秋色 8GB+128GB 智能','手机','2021-11-19 09:34:01','admin','2021-12-21 15:38:00','admin',1,14),(4,'Redmi K40',2499,6,'https://img14.360buyimg.com/n7/jfs/t1/143568/34/22949/201614/618e3896E3c535697/051d80d9251775f9.jpg','Redmi K40 骁龙870 三星AMOLED 120Hz高刷直屏 4800万高清三摄 12GB+256GB 幻境 游戏电竞5G','手机','2021-11-19 09:42:17','admin','2021-12-24 10:51:21','admin',1,8),(5,'长虹75D5P PRO',5199,5,'https://img14.360buyimg.com/n7/jfs/t1/199736/38/18248/126330/619cc3d6Ef1a8c5ce/11c12bbb38e3e846.jpg','长虹75D5P PRO 75英寸4K超高清 云游戏电视 免遥控语音 安防摄像头 无线投屏平板LED液晶电视机','电视','2021-11-24 11:42:41','admin','2021-12-20 17:11:53','admin',1,9);

/*Table structure for table `merchant` */

DROP TABLE IF EXISTS `merchant`;

CREATE TABLE `merchant` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `password` varchar(20) NOT NULL,
  `account` varchar(50) NOT NULL,
  `app_name` varchar(20) DEFAULT NULL,
  `app_icon` varchar(200) DEFAULT NULL,
  `secret` text,
  `pub_key` text,
  `balance` mediumtext,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=gbk;

/*Data for the table `merchant` */

insert  into `merchant`(`id`,`password`,`account`,`app_name`,`app_icon`,`secret`,`pub_key`,`balance`) values (1,'123123','admin','admin','https://tse3-mm.cn.bing.net/th/id/OIP-C.VERhWdC7vWC7_OM0CtMF0AAAAA?pid=ImgDet&rs=1','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','639700');

/*Table structure for table `messages` */

DROP TABLE IF EXISTS `messages`;

CREATE TABLE `messages` (
  `id` int(11) NOT NULL,
  `title` varchar(50) DEFAULT NULL,
  `content` varchar(100) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

/*Data for the table `messages` */

/*Table structure for table `orders` */

DROP TABLE IF EXISTS `orders`;

CREATE TABLE `orders` (
  `id` varchar(50) NOT NULL,
  `gid` int(11) NOT NULL,
  `uid` varchar(20) NOT NULL,
  `title` varchar(100) NOT NULL,
  `price` int(11) NOT NULL COMMENT '单价',
  `total` int(11) NOT NULL COMMENT '总价',
  `status` int(11) DEFAULT '1' COMMENT '状态',
  `addressee` varchar(20) NOT NULL COMMENT '收件人',
  `address` varchar(100) NOT NULL COMMENT '收件人地址',
  `phone` varchar(20) NOT NULL COMMENT '联系电话',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(20) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `update_by` varchar(20) DEFAULT NULL COMMENT '修改人',
  `number` int(11) DEFAULT NULL COMMENT '数量',
  `pay_time` datetime DEFAULT NULL COMMENT '支付时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

/*Data for the table `orders` */

insert  into `orders`(`id`,`gid`,`uid`,`title`,`price`,`total`,`status`,`addressee`,`address`,`phone`,`create_time`,`create_by`,`update_time`,`update_by`,`number`,`pay_time`) values ('dW5kZWZpbmVk1639720625561',2,'admin','Redmi Note 11 Pro+',2099,6297,2,'士大夫士大夫但是','萨芬大苏打v 啊发射点十分收到','1996564520','2021-12-17 14:16:54','admin','2021-12-20 17:28:49',NULL,3,'2021-12-20 17:28:10'),('dW5kZWZpbmVk1639984817512',2,'admin','Redmi Note 11 Pro+',2099,10495,1,'ooo','asjkdkjshadhs ','1121223121','2021-12-20 15:20:45','admin','2021-12-20 17:28:49',NULL,5,'2021-12-20 17:28:12'),('dW5kZWZpbmVk1639989973489',5,'admin','长虹75D5P PRO',5199,5199,1,'梵蒂冈反对','犯得上发射点','6454353454','2021-12-20 16:46:24','admin','2021-12-20 17:28:48',NULL,1,'2021-12-20 17:28:14'),('dW5kZWZpbmVk1639993209947',3,'admin','Redmi Note 9 Pro 5G',1399,1399,2,'fdsfsdfsd','dfsdfsdfds','sdfsdfsdfsd','2021-12-20 17:40:17','admin','2021-12-20 17:40:33',NULL,1,NULL),('dW5kZWZpbmVk1640314005377',4,'admin','(秒杀)Redmi K40',2299,2299,1,'而温热微软','23423423','4324234234','2021-12-24 10:48:02','admin','2021-12-24 10:55:19',NULL,1,'2021-12-24 10:51:21'),('YWRtaW4=1639703411835',5,'admin','长虹75D5P PRO',5199,5199,1,'张涛','四川省成都市金牛区九里堤','19960821523','2021-12-17 09:13:57','admin','2021-12-20 17:28:48',NULL,1,'2021-12-20 17:28:14'),('YWRtaW4=1639986737236',4,'admin','Redmi K40',2499,4998,1,'张桃','四川省成都市金牛区','19960821523','2021-12-20 15:53:12','admin','2021-12-20 17:28:49',NULL,2,'2021-12-20 17:28:15'),('YWRtaW4=1639986880870',4,'admin','Redmi K40',2499,2499,1,'001','实打实打算','199653355','2021-12-20 15:54:57','admin','2021-12-20 17:28:48',NULL,1,'2021-12-20 17:28:18'),('YWRtaW4=1639987001145',5,'admin','长虹75D5P PRO',5199,10398,1,'女兵g','恢复鬼画符更换','1112121212','2021-12-20 15:56:55','admin','2021-12-20 17:28:49',NULL,2,'2021-12-20 17:28:19'),('YWRtaW4=1639990602878',5,'admin','长虹75D5P PRO',5199,5199,1,'而温热微软','额威威认为','3532543545','2021-12-20 16:56:57','admin','2021-12-20 17:28:48',NULL,1,'2021-12-20 17:28:20'),('YWRtaW4=1639991005653',3,'admin','Redmi Note 9 Pro 5G',1399,1399,1,'fdf','fdfdfdf','342342444','2021-12-20 17:03:52','admin','2021-12-20 17:28:49',NULL,1,'2021-12-20 17:28:21'),('YWRtaW4=1639991268225',1,'admin','iphone 13pro max 1TB 远峰蓝色',12999,12999,1,'sdsadsad','34234324324','34324234234','2021-12-20 17:07:59','admin','2021-12-20 17:28:48',NULL,1,'2021-12-20 17:28:21'),('YWRtaW4=1639991471958',5,'admin','长虹75D5P PRO',5199,5199,1,'fdsfsd','fsfsdfsdf','4554352435','2021-12-20 17:11:27','admin','2021-12-20 17:28:48',NULL,1,'2021-12-20 17:28:22'),('YWRtaW4=1639991741030',1,'admin','iphone 13pro max 1TB 远峰蓝色',12999,12999,1,'dfdsf','sdfdsfsdfsd','66453544545','2021-12-20 17:15:55','admin','2021-12-20 17:27:15',NULL,1,'2021-12-20 17:25:14'),('YWRtaW4=1639993055767',3,'admin','Redmi Note 9 Pro 5G',1399,1399,2,'dfdsfds','fdsfsdf','e34543543','2021-12-20 17:37:43','admin','2021-12-20 17:40:31',NULL,1,NULL),('YWRtaW4=1639993097285',3,'admin','Redmi Note 9 Pro 5G',1399,1399,2,'fsdfdsf','sdfsdfsdfsd','sdfdsfsdf','2021-12-20 17:38:24','admin','2021-12-20 17:40:36',NULL,1,NULL),('YWRtaW4=1640000834321',3,'admin','Redmi Note 9 Pro 5G',1399,1399,1,'犯得上士大夫但是','士大夫士大夫士大夫但是','撒旦发射345345','2021-12-20 19:47:31','admin','2021-12-21 15:38:00',NULL,1,'2021-12-21 15:38:00'),('YWRtaW4=1640074266430',4,'admin','Redmi K40',2499,2499,1,'fdsfsd','sdfsdfsfds','fdsfsdfsdf','2021-12-21 16:11:21','admin','2021-12-21 16:26:40',NULL,1,'2021-12-21 16:26:40'),('YWRtaW4=1640075651096',1,'admin','iphone 13pro max 1TB 远峰蓝色',12999,12999,0,'你说的房价多少',' 说服对方的身份收到  ','打发士大夫大师傅似的','2021-12-21 16:34:30','admin',NULL,NULL,1,NULL);

/*Table structure for table `pay_record` */

DROP TABLE IF EXISTS `pay_record`;

CREATE TABLE `pay_record` (
  `no` varchar(50) NOT NULL COMMENT '支付流水号',
  `mch_id` int(11) DEFAULT NULL,
  `uid` int(11) DEFAULT NULL,
  `title` varchar(100) DEFAULT NULL,
  `detail` varchar(50) DEFAULT NULL,
  `out_trade_no` varchar(50) DEFAULT NULL,
  `attach` varchar(50) DEFAULT NULL,
  `total_fee` mediumtext,
  `time_start` datetime DEFAULT NULL,
  `time_expire` datetime DEFAULT NULL,
  `notify_url` varchar(50) DEFAULT NULL,
  `pay_time` datetime DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`no`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

/*Data for the table `pay_record` */

insert  into `pay_record`(`no`,`mch_id`,`uid`,`title`,`detail`,`out_trade_no`,`attach`,`total_fee`,`time_start`,`time_expire`,`notify_url`,`pay_time`,`create_time`,`update_time`,`status`,`remark`) values ('20211221153551226',1,1,'Redmi Note 9 Pro 5G',NULL,'YWRtaW4=1640000834321',NULL,'139900','2021-12-21 15:35:51',NULL,'http://localhost:30082/pay/pay-success','2021-12-21 15:37:57',NULL,NULL,3,NULL),('20211221161131416',1,1,'Redmi K40',NULL,'YWRtaW4=1640074266430',NULL,'249900','2021-12-21 16:11:31',NULL,'http://localhost:30082/pay/pay-success','2021-12-21 16:26:38',NULL,NULL,3,NULL),('20211221163557740',1,NULL,'iphone 13pro max 1TB 远峰蓝色',NULL,'YWRtaW4=1640075651096',NULL,'1299900','2021-12-21 16:35:57',NULL,'http://localhost:30082/pay/pay-success',NULL,NULL,NULL,1,NULL),('20211224105053543',1,1,'(秒杀)Redmi K40',NULL,'dW5kZWZpbmVk1640314005377',NULL,'249900','2021-12-24 10:50:48',NULL,'http://localhost:30082/pay/pay-success','2021-12-24 10:51:17',NULL,NULL,3,NULL);

/*Table structure for table `sec_kill_goods` */

DROP TABLE IF EXISTS `sec_kill_goods`;

CREATE TABLE `sec_kill_goods` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(100) NOT NULL COMMENT '标题',
  `unit_price` int(11) NOT NULL COMMENT '原价',
  `sec_kill_price` int(11) NOT NULL COMMENT '秒杀价',
  `stock` int(11) NOT NULL COMMENT '库存',
  `photo` varchar(100) NOT NULL COMMENT '预览图',
  `introduction` text COMMENT '简介',
  `classification` varchar(20) DEFAULT '其他' COMMENT '分类',
  `status` int(11) DEFAULT '1' COMMENT '状态',
  `sales` int(11) DEFAULT '0' COMMENT '销量',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=gbk;

/*Data for the table `sec_kill_goods` */

insert  into `sec_kill_goods`(`id`,`title`,`unit_price`,`sec_kill_price`,`stock`,`photo`,`introduction`,`classification`,`status`,`sales`) values (1,'(秒杀)iphone 13pro max 1TB 远峰蓝色',12999,10999,10,'https://2f.zol-img.com.cn/product/215_320x240/933/ceOMvXTRF9M.jpg','Apple iPhone 13 Pro (A2639) 1TB 远峰蓝色 支持移动联通电信5G 双卡双待手机','手机',1,0),(2,'(秒杀)Redmi Note 11 Pro+',2099,1999,10,'https://cdn.cnbj0.fds.api.mi-img.com/b2c-shopapi-pms/pms_1635332995.28485388.jpg','Redmi Note 11 Pro+ 5G智能手机120W充电1亿像素天玑920液冷游戏芯小米红米','手机',1,0),(3,'(秒杀)Redmi Note 9 Pro 5G',1399,1299,10,'https://img11.360buyimg.com/n7/jfs/t1/215393/39/4597/128233/619201b1Eb0df80bd/5cee5951d6bd1612.jpg','Redmi Note 9 Pro 5G 一亿像素 骁龙750G 33W快充 120Hz刷新率 湖光秋色 8GB+128GB 智能','手机',1,0),(4,'(秒杀)Redmi K40',2499,2299,9,'https://img14.360buyimg.com/n7/jfs/t1/143568/34/22949/201614/618e3896E3c535697/051d80d9251775f9.jpg','Redmi K40 骁龙870 三星AMOLED 120Hz高刷直屏 4800万高清三摄 12GB+256GB 幻境 游戏电竞5G','手机',1,0),(5,'(秒杀)长虹75D5P PRO',5199,4999,10,'https://img14.360buyimg.com/n7/jfs/t1/199736/38/18248/126330/619cc3d6Ef1a8c5ce/11c12bbb38e3e846.jpg','长虹75D5P PRO 75英寸4K超高清 云游戏电视 免遥控语音 安防摄像头 无线投屏平板LED液晶电视机','电视',1,0);

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `account` varchar(20) NOT NULL,
  `password` varchar(20) NOT NULL,
  `nick` varchar(20) NOT NULL,
  `sex` varchar(10) DEFAULT NULL,
  `email` varchar(20) NOT NULL,
  `avatar` varchar(100) DEFAULT NULL COMMENT '头像',
  `level` int(11) DEFAULT '0' COMMENT '级别',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `status` int(11) DEFAULT '1',
  PRIMARY KEY (`account`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

/*Data for the table `user` */

insert  into `user`(`account`,`password`,`nick`,`sex`,`email`,`avatar`,`level`,`create_time`,`status`) values ('3553851742','123456','开心宝贝','男','2412601628@qq.com',NULL,0,'2021-11-16 19:02:19',1),('admin','123123','管理员','男','111@qq.com','https://i.udongman.cn/image/2021/0112/20210112045854781.jpg',1,'2021-11-10 11:36:01',1);

/*Table structure for table `userinfo` */

DROP TABLE IF EXISTS `userinfo`;

CREATE TABLE `userinfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(30) DEFAULT NULL,
  `email` varchar(20) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `balance` mediumtext,
  `password` varchar(30) DEFAULT NULL,
  `pay_pwd` varchar(20) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=gbk;

/*Data for the table `userinfo` */

insert  into `userinfo`(`id`,`username`,`email`,`phone`,`balance`,`password`,`pay_pwd`,`create_time`,`update_time`,`status`) values (1,'blackSoul','admin','19960821523','490299','123123','982047','2021-12-22 02:37:03','2021-12-22 10:37:13',1);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
