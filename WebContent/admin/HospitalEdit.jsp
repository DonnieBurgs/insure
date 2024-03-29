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
<title>医院<c:if test="${auth52 eq '1'}">修改</c:if><c:if test="${auth52 ne '1'}">查看</c:if></title>
</head>
<body id="right">
<div class="operation o-h bg-f8">
	<div class="operationLeft f-l"><font style="margin-left:10px;font-size:14px; font-weight:bold; text-indent:14px; letter-spacing:2px;">医院<c:if test="${auth52 eq '1'}">修改</c:if><c:if test="${auth52 ne '1'}">查看</c:if></font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<a href="javascript:history.back();" style="margin-left:100px;"><i class="iconfont">&#xe63d;</i>返回列表</a></div>
	<div class="operationRight f-r">
	</div>
</div>
<form id="commonForm" action="/emHospital.do?method=update" method="post">
	<table class="tform">
<tr>
<td width="120" class="right">医院名称：</td>
<td><input type="text" id="hospitalname" name="hospitalname" value="${fn:replace(item.hospitalname,'"','&quot;')}" size=50></td>
</tr>
<tr>
<td width="120" class="right">医院等级：</td>
<td><input type="text" id="grade" name="grade" value="${fn:replace(item.grade,'"','&quot;')}" size=50></td>
</tr>
<tr>
<td width="120" class="right">代码：</td>
<td><input type="text" id="hospitalcode" name="hospitalcode" value="${fn:replace(item.hospitalcode,'"','&quot;')}" size=50></td>
</tr>
<tr>
<td width="120" class="right">拼音缩写：</td>
<td><input type="text" id="pinyin" name="pinyin" value="${fn:replace(item.pinyin,'"','&quot;')}" size=50></td>
</tr>
<tr>
<td width="120" class="right">数据范围：</td>
<td><input type="text" id="domain" name="domain" value="${fn:replace(item.domain,'"','&quot;')}" size=50></td>
</tr>
<tr>
<td width="120" class="right">省级：</td>
<td><input type="text" id="province" name="province" value="${fn:replace(item.province,'"','&quot;')}" size=50></td>
</tr>
<tr>
<td width="120" class="right">地级：</td>
<td><input type="text" id="region" name="region" value="${fn:replace(item.region,'"','&quot;')}" size=50></td>
</tr>
<tr>
<td width="120" class="right">县级：</td>
<td><input type="text" id="county" name="county" value="${fn:replace(item.county,'"','&quot;')}" size=50></td>
</tr>

		<tr>
			<td colspan="2">
			<input id="id" name="id" type="hidden" value="${item.id}"/>

				<input id="uf_parentid" name="uf_parentid" type="hidden" value="${uf_parentid}"/>
				<input id="keyword" name="keyword" type="hidden" value="${keyword}"/>
				<input id="m" name="m" type="hidden" value="${m}"/>
				<input id="s" name="s" type="hidden" value="${s}"/>
				<c:if test="${auth52 eq '1'}">
				<input type="button" class="btn1" value=" 提  交 " onclick="checkf()"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="button" class="btn1" value=" 删  除 " onclick="checkdelete()"/>
				</c:if>
				<c:if test="${auth52 ne '1'}">
				<input type="button" class="btn1" value=" 返  回 " onclick="history.back();"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				</c:if>
			</td>
		</tr>
	</table>
</form>

</body>
<form id="deleteForm" action="/emHospital.do?method=delete" method="post">
<input id="id" name="id" type="hidden" value="${item.id}"/>
<input id="uf_parentid" name="uf_parentid" type="hidden" value="${uf_parentid}"/>
<input id="keyword" name="keyword" type="hidden" value="${keyword}"/>
<input id="m" name="m" type="hidden" value="${m}"/>
<input id="s" name="s" type="hidden" value="${s}"/>
</form>
</html>
<script language=javascript>
function checkf() {
	if(commonForm.hospitalname.value=="") {alert("请正确输入医院名称！");return false;}
	if(commonForm.grade.value=="") {alert("请正确输入医院等级！");return false;}
	if(commonForm.hospitalcode.value=="") {alert("请正确输入代码！");return false;}
	if(commonForm.pinyin.value=="") {alert("请正确输入拼音缩写！");return false;}
	if(commonForm.domain.value=="") {alert("请正确输入数据范围！");return false;}
	if(commonForm.province.value=="") {alert("请正确输入省级！");return false;}
	if(commonForm.region.value=="") {alert("请正确输入地级！");return false;}
	if(commonForm.county.value=="") {alert("请正确输入县级！");return false;}

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