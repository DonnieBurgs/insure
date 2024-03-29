﻿<%@ page language="java" contentType="text/html; charset=UTF-8"
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
<title>消息会话添加</title>
</head>
<body id="right">
<div class="operation o-h bg-f8">
	<div class="operationLeft f-l"><font style="margin-left:10px;font-size:14px; font-weight:bold; text-indent:14px; letter-spacing:2px;">消息会话添加</font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<a href="javascript:history.back();" style="margin-left:100px;"><i class="iconfont">&#xe681;</i>返回列表</a></div>
	<div class="operationRight f-r">
	</div>
</div>
<form id="commonForm" action="/emMessageSession.do?method=add" method="post">
	<table class="tform">
<tr>
<td width="120" class="right">会员ID：</td>
<td><input type="text" id="userid1" name="userid1" value="" size=50></td>
</tr>
<tr>
<td width="120" class="right">会员ID：</td>
<td><input type="text" id="userid2" name="userid2" value="" size=50></td>
</tr>
<tr>
<td width="120" class="right">消息数量：</td>
<td><input type="text" id="messagecount" name="messagecount" value="" size=50></td>
</tr>
<tr>
<td width="120" class="right">新消息数量：</td>
<td><input type="text" id="isnew1" name="isnew1" value="" size=50></td>
</tr>
<tr>
<td width="120" class="right">新消息数量：</td>
<td><input type="text" id="isnew2" name="isnew2" value="" size=50></td>
</tr>
<tr>
<td width="120" class="right">最新消息ID：</td>
<td><input type="text" id="lastmessageid" name="lastmessageid" value="" size=50></td>
</tr>
<tr>
<td width="120" class="right">公司ID：</td>
<td><input type="text" id="companyid" name="companyid" value="" size=50></td>
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
	if(commonForm.userid1.value=="" || commonForm.userid1.value!="" && !is_int(commonForm.userid1.value)) {alert("请正确输入会员ID！");return false;}
	if(commonForm.userid2.value=="" || commonForm.userid2.value!="" && !is_int(commonForm.userid2.value)) {alert("请正确输入会员ID！");return false;}
	if(commonForm.messagecount.value=="" || commonForm.messagecount.value!="" && !is_int(commonForm.messagecount.value)) {alert("请正确输入消息数量！");return false;}
	if(commonForm.isnew1.value=="" || commonForm.isnew1.value!="" && !is_int(commonForm.isnew1.value)) {alert("请正确输入新消息数量！");return false;}
	if(commonForm.isnew2.value=="" || commonForm.isnew2.value!="" && !is_int(commonForm.isnew2.value)) {alert("请正确输入新消息数量！");return false;}
	if(commonForm.lastmessageid.value=="" || commonForm.lastmessageid.value!="" && !is_int(commonForm.lastmessageid.value)) {alert("请正确输入最新消息ID！");return false;}
	if(commonForm.companyid.value=="" || commonForm.companyid.value!="" && !is_int(commonForm.companyid.value)) {alert("请正确输入公司ID！");return false;}

	document.getElementById("commonForm").submit() ;
  
}


</script>