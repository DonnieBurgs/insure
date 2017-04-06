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
<script type="text/javascript" src="/resources/jquery/jquery-1.9.1.min.js"></script>
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
<title>被保险人<c:if test="${auth26 eq '1'}">修改</c:if><c:if test="${auth26 ne '1'}">查看</c:if></title>
</head>
<body id="right">
<div class="operation o-h bg-f8">
	<div class="operationLeft f-l"><font style="margin-left:10px;font-size:14px; font-weight:bold; text-indent:14px; letter-spacing:2px;">被保险人<c:if test="${auth26 eq '1'}">修改</c:if><c:if test="${auth26 ne '1'}">查看</c:if></font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<a href="javascript:history.back();" style="margin-left:100px;"><i class="iconfont">&#xe63d;</i>返回列表</a></div>
	<div class="operationRight f-r">
	</div>
</div>
<form id="commonForm" action="/emInsured.do?method=update" method="post">
	<table class="tform">
<tr>
<td width="120" class="right">名称：</td>
<td><input type="text" id="insuredname" name="insuredname" value="${fn:replace(item.insuredname,'"','&quot;')}" size=50></td>
</tr>
<tr>
<td width="120" class="right">性别：</td>
<td><input type="text" id="gender" name="gender" value="${fn:replace(item.gender,'"','&quot;')}" size=50></td>
</tr>
<tr>
<td width="120" class="right">身份证：</td>
<td><input type="text" id="idnumber" name="idnumber" value="${fn:replace(item.idnumber,'"','&quot;')}" size=50></td>
</tr>
<tr>
<td width="120" class="right">护照：</td>
<td><input type="text" id="passport" name="passport" value="${fn:replace(item.passport,'"','&quot;')}" size=50></td>
</tr>
<tr>
<td width="120" class="right">出生年月：</td>
<td><input type="text" id="birthdate" name="birthdate" readonly="readonly" value="${item.birthdate_}" onclick="WdatePicker({maxDate:'2018-12-31', dateFmt:'yyyy-MM-dd'})" style="width: 70px;"/></td>
</tr>
<tr>
<td width="120" class="right">工作单位：</td>
<td><input type="text" id="employer" name="employer" value="${fn:replace(item.employer,'"','&quot;')}" size=50></td>
</tr>
<tr>
<td width="120" class="right">工号：</td>
<td><input type="text" id="jobnumber" name="jobnumber" value="${fn:replace(item.jobnumber,'"','&quot;')}" size=50></td>
</tr>
<tr>
<td width="120" class="right">开户行：</td>
<td><input type="text" id="bankname" name="bankname" value="${fn:replace(item.bankname,'"','&quot;')}" size=50></td>
</tr>
<tr>
<td width="120" class="right">账户名：</td>
<td><input type="text" id="accountname" name="accountname" value="${fn:replace(item.accountname,'"','&quot;')}" size=50></td>
</tr>
<tr>
<td width="120" class="right">银行账号：</td>
<td><input type="text" id="accountnumber" name="accountnumber" value="${fn:replace(item.accountnumber,'"','&quot;')}" size=50></td>
</tr>
<tr>
<td width="120" class="right">电子邮件：</td>
<td><input type="text" id="email" name="email" value="${fn:replace(item.email,'"','&quot;')}" size=50></td>
</tr>
<tr>
<td width="120" class="right">机构：</td>
<td><input type="text" id="department" name="department" value="${fn:replace(item.department,'"','&quot;')}" size=50></td>
</tr>

		<tr>
			<td colspan="2">
			<input id="insuredid" name="insuredid" type="hidden" value="${item.insuredid}"/>

				<input id="uf_parentid" name="uf_parentid" type="hidden" value="${uf_parentid}"/>
				<input id="keyword" name="keyword" type="hidden" value="${keyword}"/>
				<input id="m" name="m" type="hidden" value="${m}"/>
				<input id="s" name="s" type="hidden" value="${s}"/>
				<c:if test="${auth26 eq '1'}">
				<input type="button" class="btn1" value=" 提  交 " onclick="checkf()"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="button" class="btn1" value=" 删  除 " onclick="checkdelete()"/>
				</c:if>
				<c:if test="${auth26 ne '1'}">
				<input type="button" class="btn1" value=" 返  回 " onclick="history.back();"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				</c:if>
			</td>
		</tr>
	</table>
</form>

</body>
<form id="deleteForm" action="/emInsured.do?method=delete" method="post">
<input id="insuredid" name="insuredid" type="hidden" value="${item.insuredid}"/>
<input id="uf_parentid" name="uf_parentid" type="hidden" value="${uf_parentid}"/>
<input id="keyword" name="keyword" type="hidden" value="${keyword}"/>
<input id="m" name="m" type="hidden" value="${m}"/>
<input id="s" name="s" type="hidden" value="${s}"/>
</form>
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

function checkdelete() {
	if(confirm("是否删除当前数据记录？")==false) {
		return ;
	} else {
		document.getElementById("deleteForm").submit() ;
	}
	
}

</script>