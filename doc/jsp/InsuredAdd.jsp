<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<link type="text/css" rel="stylesheet" href="/admin/style/base.css" />
<link type="text/css" rel="stylesheet" href="/admin/style/global.css" />
<link href="/resources/css/main.css" rel="stylesheet" type="text/css" />
<script src="http://libs.baidu.com/jquery/1.8.3/jquery.min.js"></script>
<script type="text/javascript" src="/resources/js/jquery-ui.min.js"></script>
<script type="text/javascript" src="/resources/js/xheditor/xheditor-zh-cn.min.js"></script>
<script type="text/javascript">var root = "";</script>
<script type="text/javascript" src="/resources/js/admin.js"></script>
<script type="text/javascript" src="/resources/js/common.js"></script>
<script type="text/javascript" src="/resources/js/photo.js"></script>
<script type="text/javascript" src="/resources/js/My97DatePicker/config.js"></script>
<script type="text/javascript" src="/resources/js/My97DatePicker/lang/zh-cn.js"></script>
<link rel="stylesheet" type="text/css" href="/resources/js/My97DatePicker/skin/WdatePicker.css" />
<link rel="stylesheet" type="text/css" href="/resources/js/My97DatePicker/skin/default/datepicker.css" />
<script type="text/javascript" src="/resources/js/My97DatePicker/WdatePicker.js"></script>
<title>被保险人添加</title>
</head>
<body id="right">
<div class="operation o-h bg-f8">
	<div class="operationLeft f-l"><font style="margin-left:10px;font-size:14px; font-weight:bold; text-indent:14px; letter-spacing:2px;">被保险人添加</font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<a href="javascript:history.back();" style="margin-left:100px;"><i class="iconfont">&#xe681;</i>返回列表</a></div>
	<div class="operationRight f-r">
	</div>
</div>
<form id="commonForm" action="/emInsured.do?method=add" method="post">
	<table class="tform">
<tr>
<td width="120" class="right">名称：</td>
<td><input type="text" id="insuredname" name="insuredname" value="" size=50></td>
</tr>
<tr>
<td width="120" class="right">性别：</td>
<td><input type="text" id="gender" name="gender" value="" size=50></td>
</tr>
<tr>
<td width="120" class="right">身份证：</td>
<td><input type="text" id="idnumber" name="idnumber" value="" size=50></td>
</tr>
<tr>
<td width="120" class="right">护照：</td>
<td><input type="text" id="passport" name="passport" value="" size=50></td>
</tr>
<tr>
<td width="120" class="right">出生年月：</td>
<td><input type="text" id="birthdate" name="birthdate" readonly="readonly" value="" onclick="WdatePicker({maxDate:'2018-12-31', dateFmt:'yyyy-MM-dd'})" style="width: 70px;"/></td>
</tr>
<tr>
<td width="120" class="right">工作单位：</td>
<td><input type="text" id="employer" name="employer" value="" size=50></td>
</tr>
<tr>
<td width="120" class="right">工号：</td>
<td><input type="text" id="jobnumber" name="jobnumber" value="" size=50></td>
</tr>
<tr>
<td width="120" class="right">开户行：</td>
<td><input type="text" id="bankname" name="bankname" value="" size=50></td>
</tr>
<tr>
<td width="120" class="right">账户名：</td>
<td><input type="text" id="accountname" name="accountname" value="" size=50></td>
</tr>
<tr>
<td width="120" class="right">银行账号：</td>
<td><input type="text" id="accountnumber" name="accountnumber" value="" size=50></td>
</tr>
<tr>
<td width="120" class="right">电子邮件：</td>
<td><input type="text" id="email" name="email" value="" size=50></td>
</tr>
<tr>
<td width="120" class="right">机构：</td>
<td><input type="text" id="department" name="department" value="" size=50></td>
</tr>

		<tr>
			<td colspan="2">

				<input id="uf_parentid" name="uf_parentid" type="hidden" value="${uf_parentid}"/>
				<input id="keyword" name="keyword" type="hidden" value="${keyword}"/>
				<input id="m" name="m" type="hidden" value="${m}"/>
				<input id="s" name="s" type="hidden" value="${s}"/>
				<input type="button" class="btn1" value=" 提  交 " onclick="checkf()"/>
			</td>
		</tr>
	</table>
</form>

</body>
</html>
<script language=javascript>
function checkf() {
	if(commonForm.insuredname.value=="") {alert("请正确输入名称！");return false;}
	if(commonForm.gender.value=="") {alert("请正确输入性别！");return false;}
	if(commonForm.idnumber.value=="") {alert("请正确输入身份证！");return false;}
	if(commonForm.passport.value=="") {alert("请正确输入护照！");return false;}
	if(commonForm.birthdate.value=="") {alert("请正确输入出生年月！");return false;}
	if(commonForm.employer.value=="") {alert("请正确输入工作单位！");return false;}
	if(commonForm.jobnumber.value=="") {alert("请正确输入工号！");return false;}
	if(commonForm.bankname.value=="") {alert("请正确输入开户行！");return false;}
	if(commonForm.accountname.value=="") {alert("请正确输入账户名！");return false;}
	if(commonForm.accountnumber.value=="") {alert("请正确输入银行账号！");return false;}
	if(commonForm.email.value=="") {alert("请正确输入电子邮件！");return false;}
	if(commonForm.department.value=="") {alert("请正确输入机构！");return false;}

	document.getElementById("commonForm").submit() ;
  
}


</script>