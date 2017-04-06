-- --------------------------------------------------------
-- 主机:                           120.77.245.252
-- 服务器版本:                        5.1.73 - Source distribution
-- 服务器操作系统:                      redhat-linux-gnu
-- HeidiSQL 版本:                  9.3.0.4984
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- 导出  表 emt.em_user 结构
CREATE TABLE IF NOT EXISTS `em_user` (
  `userid` int(10) NOT NULL AUTO_INCREMENT,
  `account` varchar(100) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `username` varchar(100) DEFAULT NULL,
  `userno` varchar(100) DEFAULT NULL,
  `companyid` int(10) NOT NULL DEFAULT '0',
  `usertype` int(10) NOT NULL DEFAULT '0',
  `parentid` int(10) NOT NULL DEFAULT '0',
  `sex` varchar(100) DEFAULT NULL,
  `age` varchar(100) DEFAULT NULL,
  `identity` varchar(100) DEFAULT NULL,
  `mobile` varchar(100) DEFAULT NULL,
  `isvalid` int(10) NOT NULL DEFAULT '0',
  `photo` varchar(200) DEFAULT NULL,
  `isregist` int(10) NOT NULL DEFAULT '0',
  `openid` varchar(100) DEFAULT NULL,
  `qrurl` varchar(200) DEFAULT NULL,
  `createdate` datetime DEFAULT NULL,
  `tel` varchar(100) DEFAULT NULL,
  `addr` varchar(100) DEFAULT NULL,
  `note` varchar(3000) DEFAULT NULL,
  `balance` float(10,2) NOT NULL DEFAULT '0.00',
  `devicetoken` varchar(200) DEFAULT NULL,
  `devicetokendate` datetime DEFAULT NULL,
  `lng` float(12,8) NOT NULL DEFAULT '0.00000000',
  `lat` float(12,8) NOT NULL DEFAULT '0.00000000',
  `latlngdate` datetime DEFAULT NULL,
  `deviceid` varchar(100) DEFAULT NULL,
  `seq` int(10) NOT NULL DEFAULT '0',
  `authorityid` int(10) NOT NULL DEFAULT '0',
  `isdelete` int(10) NOT NULL DEFAULT '0',
  `nickname` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`userid`),
  UNIQUE KEY `userid` (`userid`)
) ENGINE=MyISAM AUTO_INCREMENT=10016 DEFAULT CHARSET=utf8;

-- 正在导出表  emt.em_user 的数据：14 rows
/*!40000 ALTER TABLE `em_user` DISABLE KEYS */;
INSERT INTO `em_user` (`userid`, `account`, `password`, `username`, `userno`, `companyid`, `usertype`, `parentid`, `sex`, `age`, `identity`, `mobile`, `isvalid`, `photo`, `isregist`, `openid`, `qrurl`, `createdate`, `tel`, `addr`, `note`, `balance`, `devicetoken`, `devicetokendate`, `lng`, `lat`, `latlngdate`, `deviceid`, `seq`, `authorityid`, `isdelete`, `nickname`) VALUES
	(10001, '13711553301', '111111', '13711553301', NULL, 0, 1, 10003, '男', '40', '', '13711553301', 1, 'http://wx.qlogo.cn/mmopen/iatMxM8veDQmRJlRlywUFKiamp4YYKLviaOicHQTLUiczI7aS4dbCtgm2OlnEGb1dIeXs1sMfIKJEKL4bhju1Dn94EfItAejcptVQ/0', 0, 'o5voTxNAIt4KdpemYmeqWgZB-5vU', 'http://weixin.qq.com/q/02ZVimYoCWcj410000g03o', '2017-02-21 22:07:52', '13711553301', '', '', 0.00, '', '2017-02-21 22:07:52', 0.00000000, 0.00000000, '2017-02-21 22:07:52', '', 0, 0, 0, 'samGGG'),
	(10002, '13652273783', '111111', '13652273783', 'E10002', 10019, 2, 0, '男', '51', '', '13652273783', 1, '', 0, '', NULL, '2017-02-21 23:59:31', '13700000000', '', '', 0.00, '', '2017-02-21 23:59:31', 0.00000000, 0.00000000, '2017-02-21 23:59:31', '', 0, 10001, 0, NULL),
	(10003, '13316138111', '138111', '13316138111', 'E10003', 10019, 2, 0, '男', '0', '', '13316138111', 1, 'http://wx.qlogo.cn/mmopen/iatMxM8veDQmad4Kib5LTcKXpaWpTe8mLJEx6PPSr7jmdPcIcib5NicurWibsP5RC11TmBTYsCIWLbbplYZEaD13De76obpfYbmZ5/0', 0, 'o5voTxH5Hee6OuMlPjbc1Z1cfbYY', NULL, '2017-02-27 17:13:24', '13316138111', '', '', 0.00, '', '2017-02-27 17:13:24', 0.00000000, 0.00000000, '2017-02-27 17:13:24', '', 0, 10001, 0, '海阔天空'),
	(10004, '13902241488', '123456789', '13902241488', 'E11003', 0, 2, 0, '男', '0', '', '13902241488', 1, 'http://wx.qlogo.cn/mmopen/Q3auHgzwzM67R1Mbs1BrnEEGdS1fW8OnscV2p2cOhd32Ync7Wz4G9HKFWl9Q21xMqVItBQHQumKR1FauzZ5gmyKm7ZYwYe50hhpDpcIku8M/0', 0, 'o5voTxAhDhxgKC5uO2OdcKIcDlJc', NULL, '2017-02-28 11:06:15', '13902241488', '', '', 0.00, '', '2017-02-28 11:06:15', 0.00000000, 0.00000000, '2017-02-28 11:06:15', '', 0, 0, 0, '陽洋'),
	(10005, '18675838371', 'wdzcx\'19861216', '18675838371', NULL, 0, 1, 0, '男', '0', '', '18675838371', 1, '', 0, '', NULL, '2017-03-01 17:13:43', '18675838371', '', '', 0.00, '', '2017-03-01 17:13:43', 0.00000000, 0.00000000, '2017-03-01 17:13:43', '', 0, 0, 0, NULL),
	(10006, '13318160000', '111111', '13318160000', NULL, 10019, 1, 0, '男', '0', '', '13318160000', 1, '', 0, '', NULL, '2017-03-01 18:03:15', '13318160000', '', '', 0.00, '', '2017-03-01 18:03:15', 0.00000000, 0.00000000, '2017-03-01 18:03:15', '', 0, 10002, 0, NULL),
	(10007, '13318160000', '111111', '13318160000', NULL, 10019, 1, 0, '男', '0', '', '13318160000', 1, '', 0, '', NULL, '2017-03-01 18:03:15', '13318160000', '', '', 0.00, '', '2017-03-01 18:03:15', 0.00000000, 0.00000000, '2017-03-01 18:03:15', '', 0, 10002, 0, NULL),
	(10008, 'baidu', '111111', '江浩', '', 10019, 2, 0, '男', '11', '11111', '13700000003', 1, '', 0, '', NULL, '2017-03-02 00:41:54', '', '', '', 0.00, '', '2017-03-02 00:41:54', 0.00000000, 0.00000000, NULL, NULL, 1000, 0, 0, NULL),
	(10009, '13700000004', '111111', '订单处理中心', 'E10009', 10019, 1, 0, '男', '33', '5566', '13700000004', 1, '', 0, '', NULL, '2017-03-02 00:47:53', '', '', '', 0.00, '', '2017-03-02 00:47:53', 0.00000000, 0.00000000, NULL, NULL, 1000, 0, 1, '订单处理中心'),
	(10010, '13700000005', '111111', '13700000005', NULL, 10020, 2, 0, '男', '0', '', '13700000005', 1, '', 0, '', NULL, '2017-03-03 16:18:16', '13700000005', '', '', 0.00, '', '2017-03-03 16:18:16', 0.00000000, 0.00000000, '2017-03-03 16:18:16', '', 0, 10001, 0, NULL),
	(10011, '13700000007', '111111', '13700000007', NULL, 10020, 1, 0, '男', '0', '', '13700000005', 1, '', 0, '', NULL, '2017-03-03 16:18:16', '13700000005', '', '', 0.00, '', '2017-03-03 16:18:16', 0.00000000, 0.00000000, '2017-03-03 16:18:16', '', 0, 10002, 0, NULL),
	(10012, '13700000006', '111111', '江浩敏', 'M86354546', 10019, 2, 0, '男', '23', '', '13700000006', 1, '', 0, '', NULL, '2017-03-03 22:13:24', '', '', '', 0.00, '', '2017-03-03 22:13:24', 0.00000000, 0.00000000, NULL, NULL, 1000, 10002, 0, NULL),
	(10014, '18513465808', '881113', '高瞻远瞩', NULL, 0, 1, 0, '男', '0', '', '18513465808', 1, 'http://wx.qlogo.cn/mmopen/cgYeAnx7esqucmjibic9N7RLowniamlMnZ8rjAyOnoKtYgkWnA6CIjs6rutwSFPGAN67LDrgH1DNsQhblfHVCjlyo3HwheVsZEI/0', 0, 'o5voTxHfS1-k5L6tC-GyX67hDkbI', NULL, '2017-03-17 11:37:19', '18513465808', '', '', 0.00, '', '2017-03-17 11:37:19', 0.00000000, 0.00000000, '2017-03-17 11:37:19', '', 0, 0, 0, '高瞻远瞩'),
	(10015, '13925171896', '171896', '毛粮', '', 0, 1, 0, '男', '0', '', '13925171896', 1, 'http://wx.qlogo.cn/mmopen/PiajxSqBRaELCfUJia0WeHCdmScQhZEGk57V8hKMhdGtic6YxvEmickRSzJEY6nkufSic5H5Il2lIJLXjJFQofCmySA/0', 0, 'o5voTxENlko9JSGnAzA1KiweDLkI', NULL, '2017-03-30 19:48:20', '13925171896', '', '新闻发布', 0.00, '', '2017-03-30 19:48:20', 0.00000000, 0.00000000, '2017-03-30 19:48:20', '', 0, 10004, 0, '毛粮');
/*!40000 ALTER TABLE `em_user` ENABLE KEYS */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
