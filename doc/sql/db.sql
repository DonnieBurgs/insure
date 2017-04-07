CREATE TABLE `em_insurercompany` (
	`id`    INT(10) NOT NULL AUTO_INCREMENT,
	`insurercompanyname`    VARCHAR(100) NULL,
	`insurercompanycode`    VARCHAR(100) NULL,
   primary key (`id`),
   UNIQUE INDEX `id` (`id`)
) engine=myisam AUTO_INCREMENT=10001;

CREATE TABLE `em_applicantcompany` (
	`id`    INT(10) NOT NULL AUTO_INCREMENT,
	`applicantcompanyname`    VARCHAR(100) NULL,
	`applicantcompanycode`    VARCHAR(100) NULL,
   primary key (`id`),
   UNIQUE INDEX `id` (`id`)
) engine=myisam AUTO_INCREMENT=10001;

CREATE TABLE `em_insurerpolicy` (
	`id`    INT(10) NOT NULL AUTO_INCREMENT,
	`groupinsurancepolicyid`    INT(10) NOT NULL DEFAULT 0,
	`insuredid`    INT(10) NOT NULL DEFAULT 0,
	`attachedtoid`    INT(10) NOT NULL DEFAULT 0,
	`periodbegin`    DATETIME NULL,
	`periodend`    DATETIME NULL,
	`policystate`    VARCHAR(100) NULL,
	`shistate`    VARCHAR(100) NULL,
	`shiarea`    VARCHAR(100) NULL,
	`joblocal`    VARCHAR(100) NULL,
	`relation`    VARCHAR(100) NULL,
	`jobnumber`    VARCHAR(100) NULL,
	`bankname`    VARCHAR(100) NULL,
	`accountname`    VARCHAR(100) NULL,
	`accountnumber`    VARCHAR(100) NULL,
	`clientid`    VARCHAR(100) NULL,
   primary key (`id`),
   UNIQUE INDEX `id` (`id`)
) engine=myisam AUTO_INCREMENT=10001;

CREATE TABLE `em_policy` (
	`id`    INT(10) NOT NULL AUTO_INCREMENT,
	`policyname`    VARCHAR(100) NULL,
	`policynumber`    VARCHAR(100) NULL,
   primary key (`id`),
   UNIQUE INDEX `id` (`id`)
) engine=myisam AUTO_INCREMENT=10001;

CREATE TABLE `em_insured` (
	`id`    INT(10) NOT NULL AUTO_INCREMENT,
	`insuredname`    VARCHAR(100) NULL,
	`gender`    VARCHAR(100) NULL,
	`idnumber`    VARCHAR(100) NULL,
	`passport`    VARCHAR(100) NULL,
	`birthdate`    DATETIME NULL,
	`employer`    VARCHAR(100) NULL,
	`jobnumber`    VARCHAR(100) NULL,
	`bankname`    VARCHAR(100) NULL,
	`accountname`    VARCHAR(100) NULL,
	`accountnumber`    VARCHAR(100) NULL,
	`email`    VARCHAR(100) NULL,
	`department`    VARCHAR(100) NULL,
   primary key (`id`),
   UNIQUE INDEX `id` (`id`)
) engine=myisam AUTO_INCREMENT=10001;

CREATE TABLE `em_insurancegroup` (
	`id`    INT(10) NOT NULL AUTO_INCREMENT,
	`policyid`    INT(10) NOT NULL DEFAULT 0,
	`insurancegroupname`    VARCHAR(100) NULL,
	`insurancegroupcode`    VARCHAR(100) NULL,
   primary key (`id`),
   UNIQUE INDEX `id` (`id`)
) engine=myisam AUTO_INCREMENT=10001;

CREATE TABLE `em_insurance` (
	`id`    INT(10) NOT NULL AUTO_INCREMENT,
	`policyid`    INT(10) NOT NULL DEFAULT 0,
	`insurancename`    VARCHAR(100) NULL,
	`insurancecode`    VARCHAR(100) NULL,
	`insurancetype`    VARCHAR(100) NULL,
   primary key (`id`),
   UNIQUE INDEX `id` (`id`)
) engine=myisam AUTO_INCREMENT=10001;

CREATE TABLE `em_groupinsurancepolicy` (
	`id`    INT(10) NOT NULL AUTO_INCREMENT,
	`applicantcompanyid`    INT(10) NOT NULL DEFAULT 0,
	`insurercompanyid`    INT(10) NOT NULL DEFAULT 0,
	`groupinsurancepolicyname`    VARCHAR(100) NULL,
	`policynumber`    VARCHAR(100) NULL,
	`premium`    FLOAT(10,2) NOT NULL DEFAULT 0,
	`periodbegin`    DATETIME NULL,
	`periodend`    DATETIME NULL,
	`policyid`    INT(10) NOT NULL DEFAULT 0,
	`ywxpolicynumb`    VARCHAR(100) NULL,
   primary key (`id`),
   UNIQUE INDEX `id` (`id`)
) engine=myisam AUTO_INCREMENT=10001;

CREATE TABLE `em_attachedto` (
	`id`    INT(10) NOT NULL AUTO_INCREMENT,
	`attachedtoname`    VARCHAR(100) NULL,
   primary key (`id`),
   UNIQUE INDEX `id` (`id`)
) engine=myisam AUTO_INCREMENT=10001;

CREATE TABLE `em_claimarchive` (
	`id`    INT(10) NOT NULL AUTO_INCREMENT,
	`groupinsurancepolicyid`    INT(10) NOT NULL DEFAULT 0,
	`acceptdate`    DATETIME NULL,
	`claimarchivenumber`    VARCHAR(100) NULL,
   primary key (`id`),
   UNIQUE INDEX `id` (`id`)
) engine=myisam AUTO_INCREMENT=10001;

CREATE TABLE `em_claim` (
	`id`    INT(10) NOT NULL AUTO_INCREMENT,
	`claimarchiveid`    INT(10) NOT NULL DEFAULT 0,
	`serialnumber`    VARCHAR(100) NULL,
	`insuredid`    INT(10) NOT NULL DEFAULT 0,
	`bardh`    VARCHAR(100) NULL,
   primary key (`id`),
   UNIQUE INDEX `id` (`id`)
) engine=myisam AUTO_INCREMENT=10001;

CREATE TABLE `em_determine` (
	`id`    INT(10) NOT NULL AUTO_INCREMENT,
	`claimid`    INT(10) NOT NULL DEFAULT 0,
	`diseaseid`    INT(10) NOT NULL DEFAULT 0,
	`claimtype`    VARCHAR(100) NULL,
	`claimstatus`    INT(10) NOT NULL DEFAULT 0,
	`cwfamount`    FLOAT(10,2) NOT NULL DEFAULT 0,
	`tfpfamount`    FLOAT(10,2) NOT NULL DEFAULT 0,
	`sqamount`    FLOAT(10,2) NOT NULL DEFAULT 0,
	`dereason`    VARCHAR(100) NULL,
	`yyfamount`    FLOAT(10,2) NOT NULL DEFAULT 0,
	`jsbyyfamount`    FLOAT(10,2) NOT NULL DEFAULT 0,
	`zfyyfamount`    FLOAT(10,2) NOT NULL DEFAULT 0,
	`fzyyfremark`    VARCHAR(100) NULL,
	`yyftcamount`    FLOAT(10,2) NOT NULL DEFAULT 0,
	`yyfremark`    VARCHAR(100) NULL,
	`jcfamount`    FLOAT(10,2) NOT NULL DEFAULT 0,
	`gkjjcfamount`    FLOAT(10,2) NOT NULL DEFAULT 0,
	`gkjjcjgpool`    INT(10) NOT NULL DEFAULT 0,
	`zfjcfamount`    FLOAT(10,2) NOT NULL DEFAULT 0,
	`zfjcfremark`    VARCHAR(100) NULL,
	`jcftcamount`    FLOAT(10,2) NOT NULL DEFAULT 0,
	`jcftcremark`    VARCHAR(100) NULL,
   primary key (`id`),
   UNIQUE INDEX `id` (`id`)
) engine=myisam AUTO_INCREMENT=10001;

CREATE TABLE `em_disease` (
	`id`    INT(10) NOT NULL AUTO_INCREMENT,
	`diseasename`    INT(10) NOT NULL DEFAULT 0,
	`diseasecode`    VARCHAR(100) NULL,
	`pinyin`    VARCHAR(100) NULL,
	`critical`    INT(10) NOT NULL DEFAULT 0,
	`domain`    VARCHAR(100) NULL,
   primary key (`id`),
   UNIQUE INDEX `id` (`id`)
) engine=myisam AUTO_INCREMENT=10001;

CREATE TABLE `em_receipt` (
	`id`    INT(10) NOT NULL AUTO_INCREMENT,
	`claimid`    INT(10) NOT NULL DEFAULT 0,
	`receiptnumber`    VARCHAR(100) NULL,
	`hospitalid`    INT(10) NOT NULL DEFAULT 0,
	`visitdate`    DATETIME NULL,
	`hospitaldate`    DATETIME NULL,
	`dischargedate`    DATETIME NULL,
	`fundpaid`    FLOAT(10,2) NOT NULL DEFAULT 0,
	`cashpaid`    FLOAT(10,2) NOT NULL DEFAULT 0,
	`total`    FLOAT(10,2) NOT NULL DEFAULT 0,
   primary key (`id`),
   UNIQUE INDEX `id` (`id`)
) engine=myisam AUTO_INCREMENT=10001;

CREATE TABLE `em_dereason` (
	`id`    INT(10) NOT NULL AUTO_INCREMENT,
	`dereasonname`    VARCHAR(100) NULL,
	`dereasoncode`    VARCHAR(100) NULL,
   primary key (`id`),
   UNIQUE INDEX `id` (`id`)
) engine=myisam AUTO_INCREMENT=10001;

CREATE TABLE `em_claimimage` (
	`id`    INT(10) NOT NULL AUTO_INCREMENT,
	`claimid`    INT(10) NOT NULL DEFAULT 0,
	`photourl`    VARCHAR(100) NULL,
   primary key (`id`),
   UNIQUE INDEX `id` (`id`)
) engine=myisam AUTO_INCREMENT=10001;

CREATE TABLE `em_hospital` (
	`id`    INT(10) NOT NULL AUTO_INCREMENT,
	`hospitalname`    VARCHAR(100) NULL,
	`grade`    VARCHAR(100) NULL,
	`hospitalcode`    VARCHAR(100) NULL,
	`pinyin`    VARCHAR(100) NULL,
	`domain`    VARCHAR(100) NULL,
	`province`    VARCHAR(100) NULL,
	`region`    VARCHAR(100) NULL,
	`county`    VARCHAR(100) NULL,
   primary key (`id`),
   UNIQUE INDEX `id` (`id`)
) engine=myisam AUTO_INCREMENT=10001;

CREATE TABLE `em_company` (
	`companyid`    INT(10) NOT NULL AUTO_INCREMENT,
	`companyname`    VARCHAR(100) NULL,
	`contact`    VARCHAR(100) NULL,
	`mobile`    VARCHAR(100) NULL,
	`areaid`    INT(10) NOT NULL DEFAULT 0,
	`addr`    VARCHAR(100) NULL,
	`isvalid`    INT(10) NOT NULL DEFAULT 0,
	`companytypeid`    INT(10) NOT NULL DEFAULT 0,
	`startdate`    DATETIME NULL,
	`expdate`    DATETIME NULL,
	`createdate`    DATETIME NULL,
	`balance`    FLOAT(10,2) NOT NULL DEFAULT 0,
	`note`    VARCHAR(1000) NULL,
	`auditnote`    VARCHAR(1000) NULL,
	`photo1`    VARCHAR(100) NULL,
	`photo2`    VARCHAR(100) NULL,
   primary key (`companyid`),
   UNIQUE INDEX `companyid` (`companyid`)
) engine=myisam AUTO_INCREMENT=10001;

CREATE TABLE `em_user` (
	`userid`    INT(10) NOT NULL AUTO_INCREMENT,
	`account`    VARCHAR(100) NULL,
	`password`    VARCHAR(100) NULL,
	`username`    VARCHAR(100) NULL,
	`nickname`    VARCHAR(100) NULL,
	`userno`    VARCHAR(100) NULL,
	`companyid`    INT(10) NOT NULL DEFAULT 0,
	`usertype`    INT(10) NOT NULL DEFAULT 0,
	`parentid`    INT(10) NOT NULL DEFAULT 0,
	`sex`    VARCHAR(100) NULL,
	`age`    VARCHAR(100) NULL,
	`identity`    VARCHAR(100) NULL,
	`mobile`    VARCHAR(100) NULL,
	`isvalid`    INT(10) NOT NULL DEFAULT 0,
	`photo`    VARCHAR(200) NULL,
	`isregist`    INT(10) NOT NULL DEFAULT 0,
	`openid`    VARCHAR(100) NULL,
	`qrurl`    VARCHAR(200) NULL,
	`createdate`    DATETIME NULL,
	`tel`    VARCHAR(100) NULL,
	`addr`    VARCHAR(100) NULL,
	`note`    VARCHAR(3000) NULL,
	`balance`    FLOAT(10,2) NOT NULL DEFAULT 0,
	`devicetoken`    VARCHAR(200) NULL,
	`devicetokendate`    DATETIME NULL,
	`lng`    FLOAT(12,8) NOT NULL DEFAULT 0,
	`lat`    FLOAT(12,8) NOT NULL DEFAULT 0,
	`latlngdate`    DATETIME NULL,
	`deviceid`    VARCHAR(100) NULL,
	`seq`    INT(10) NOT NULL DEFAULT 0,
	`authorityid`    INT(10) NOT NULL DEFAULT 0,
	`isdelete`    INT(10) NOT NULL DEFAULT 0,
   primary key (`userid`),
   UNIQUE INDEX `userid` (`userid`)
) engine=myisam AUTO_INCREMENT=10001;

CREATE TABLE `em_loginlog` (
	`loginlogid`    INT(10) NOT NULL AUTO_INCREMENT,
	`adminid`    INT(10) NOT NULL DEFAULT 0,
	`createdate`    DATETIME NULL,
	`note`    VARCHAR(200) NULL,
   primary key (`loginlogid`),
   UNIQUE INDEX `loginlogid` (`loginlogid`)
) engine=myisam AUTO_INCREMENT=10001;

CREATE TABLE `em_authority` (
	`authorityid`    INT(10) NOT NULL AUTO_INCREMENT,
	`authorityname`    VARCHAR(100) NULL,
	`authorityno`    VARCHAR(100) NULL,
	`ctype`    INT(10) NOT NULL DEFAULT 0,
	`note`    VARCHAR(500) NULL,
   primary key (`authorityid`),
   UNIQUE INDEX `authorityid` (`authorityid`)
) engine=myisam AUTO_INCREMENT=10001;

CREATE TABLE `em_area` (
	`areaid`    INT(10) NOT NULL AUTO_INCREMENT,
	`areaname`    VARCHAR(100) NULL,
	`fullname`    VARCHAR(100) NULL,
	`parentid`    INT(10) NOT NULL DEFAULT 0,
	`path`    VARCHAR(100) NULL,
	`seq`    INT(10) NOT NULL DEFAULT 0,
	`isdelete`    INT(10) NOT NULL DEFAULT 0,
   primary key (`areaid`),
   UNIQUE INDEX `areaid` (`areaid`)
) engine=myisam AUTO_INCREMENT=10001;

CREATE TABLE `em_syslog` (
	`syslogid`    INT(10) NOT NULL AUTO_INCREMENT,
	`type`    INT(10) NOT NULL DEFAULT 0,
	`linkid`    INT(10) NOT NULL DEFAULT 0,
	`createdate`    DATETIME NULL,
	`note`    VARCHAR(500) NULL,
   primary key (`syslogid`),
   UNIQUE INDEX `syslogid` (`syslogid`)
) engine=myisam AUTO_INCREMENT=10001;

CREATE TABLE `em_syslogtype` (
	`syslogtypeid`    INT(10) NOT NULL AUTO_INCREMENT,
	`typename`    VARCHAR(100) NULL,
	`seq`    INT(10) NOT NULL DEFAULT 0,
   primary key (`syslogtypeid`),
   UNIQUE INDEX `syslogtypeid` (`syslogtypeid`)
) engine=myisam AUTO_INCREMENT=10001;

CREATE TABLE `em_sysparam` (
	`sysparamid`    INT(10) NOT NULL AUTO_INCREMENT,
	`companyid`    INT(10) NOT NULL DEFAULT 0,
	`alarmnumcheckin`    INT(10) NOT NULL DEFAULT 0,
	`alarmtimecheckin`    INT(10) NOT NULL DEFAULT 0,
	`alarmnumsign`    INT(10) NOT NULL DEFAULT 0,
	`alarmtimesign`    INT(10) NOT NULL DEFAULT 0,
	`alarmnumleave`    INT(10) NOT NULL DEFAULT 0,
	`alarmtimeleave`    INT(10) NOT NULL DEFAULT 0,
   primary key (`sysparamid`),
   UNIQUE INDEX `sysparamid` (`sysparamid`)
) engine=myisam AUTO_INCREMENT=10001;

