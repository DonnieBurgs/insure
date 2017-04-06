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

-- 导出  表 emt.em_authority 结构
CREATE TABLE IF NOT EXISTS `em_authority` (
  `authorityid` int(10) NOT NULL AUTO_INCREMENT,
  `authorityname` varchar(100) DEFAULT NULL,
  `authorityno` varchar(100) DEFAULT NULL,
  `ctype` int(10) NOT NULL DEFAULT '0',
  `note` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`authorityid`),
  UNIQUE KEY `authorityid` (`authorityid`)
) ENGINE=MyISAM AUTO_INCREMENT=10005 DEFAULT CHARSET=utf8;

-- 正在导出表  emt.em_authority 的数据：4 rows
/*!40000 ALTER TABLE `em_authority` DISABLE KEYS */;
INSERT INTO `em_authority` (`authorityid`, `authorityname`, `authorityno`, `ctype`, `note`) VALUES
	(10001, '系统管理员', '110011001111000011111100000000110000000011001111001100110011110010111111000000001111111100000000000', 2, ''),
	(10002, '业务员', '100010000010000000001000000000100000000011000000001000000000000000000000000000000000000000000000000', 2, ''),
	(10003, '资料管理员', '100000000000000000000000000000000000000011001111000000000000000000000000000000000000000000000000000', 2, ''),
	(10004, '新闻管理员', '000000000000000000000000000000000000000000000000000000000011110000000000000000000000000000000000000', 2, '');
/*!40000 ALTER TABLE `em_authority` ENABLE KEYS */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
