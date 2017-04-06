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
<title>管理员<c:if test="${auth82 eq '1'}">修改</c:if><c:if test="${auth82 ne '1'}">查看</c:if></title>
</head>
<body id="right">
<div class="operation o-h bg-f8">
	<div class="operationLeft f-l"><font style="margin-left:10px;font-size:14px; font-weight:bold; text-indent:14px; letter-spacing:2px;">管理员<c:if test="${auth82 eq '1'}">修改</c:if><c:if test="${auth82 ne '1'}">查看</c:if></font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<a href="javascript:history.back();" style="margin-left:100px;"><i class="iconfont">&#xe63d;</i>返回列表</a></div>
	<div class="operationRight f-r">
	</div>
</div>
<form id="commonForm" action="/emAdmin.do?method=update" method="post">
<table class="tform">
<tr>
	<td width="120" class="right">姓名：</td>
	<td><input id="username" name="username" type="text" value="${item.username}"/></td>
</tr>
<tr>
<td width="120" class="right">账号：</td>
<td><input id=""account"" name="account" type="text" value="${item.account}"/></td>
</tr>
<tr>
<td width="120" class="right">手机：</td>
<td><input id="mobile" name="mobile" type="text" value="${item.mobile}"/></td>
</tr>
<tr>
	<td width="120" class="right">工号：</td>
	<td><input id="userno" name="userno" type="text" value="${item.userno}"/></td>
</tr>
<tr>
	<td width="120" class="right">密码：</td>
	<td><input id="password" name="password" type="text" value=""/> 留空表示不修改密码</td>
</tr>
<tr>
<td width="120" class="right">所属公司：</td>
<td><select name="companyid">
	<option value="">请选择</option>
<c:if test="${not empty cList}">
<c:forEach var="company" items="${cList}" varStatus="idx">
	<option value="${company.companyid}"<c:if test="${item.companyid eq company.companyid}"> selected</c:if>>${company.companyname}</option>
</c:forEach>
</c:if>
</select></td>
</tr>
<tr>
<td width="120" class="right">会员类型：</td>
<td>
<select name="usertype">
<option value="2"<c:if test="${item.usertype eq 2}"> selected</c:if>>平台客服
<option value="3"<c:if test="${item.usertype eq 3}"> selected</c:if>>第三方客服
<option value="4"<c:if test="${item.usertype eq 4}"> selected</c:if>>销售
</select>
</td>
</tr>
<tr>
	<td width="120" class="right">权限：</td>
	<td>
		<select name="authorityid">
      <option value="">--请选择--
<c:if test="${not empty aList}">
<c:forEach var="auth" items="${aList}">
      <option value="${auth.authorityid}"<c:if test="${item.authorityid eq auth.authorityid}"> selected</c:if>>${auth.authorityname}</option>
</c:forEach>
</c:if>
		</select>
	</td>
</tr>
<tr>
<td width="120" class="right">性别：</td>
<td>
<select name="sex">
<option value="男"<c:if test="${item.sex eq '男'}"> selected</c:if>>男
<option value="女"<c:if test="${item.sex eq '女'}"> selected</c:if>>女
</select>
</td>
</tr>
<tr>
<td width="120" class="right">年龄：</td>
<td><input id="age" name="age" type="text" value="${item.age}"/></td>
</tr>
<tr>
<td width="120" class="right">身份证：</td>
<td><input id="identity" name="identity" type="text" value="${item.identity}"/></td>
</tr>
<tr>
<td width="120" class="right">家庭电话：</td>
<td><input id="tel" name="tel" type="text" value="${item.tel}"/></td>
</tr>
<tr>
<td width="120" class="right">地址：</td>
<td><input id="addr" name="addr" type="text" value="${item.addr}"/></td>
</tr>
<tr>
<td width="120" class="right">是否有效：</td>
<td>
<select name="isvalid">
<option value="1"<c:if test="${item.isvalid eq 1}"> selected</c:if>>有效
<option value="0"<c:if test="${item.isvalid eq 0}"> selected</c:if>>无效
</select>
</td>
</tr>
<!--
<tr>
<td width="120" class="right">关联微信：</td>
<td><c:if test="${item.isregist eq 0}">未关联</c:if>
	<c:if test="${item.isregist eq 1}">已关联 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;微信Openid：${item.openid}</c:if> </td>
</tr>
-->
<tr>
<td width="120" class="right">显示顺序：</td>
<td><input id="seq" name="seq" type="text" value="${item.seq}"/></td>
</tr>
<tr>
<td width="120" class="right">照片：</td>
<td><a class="mya" href="javascript:f_photo.click();"><img id="imgphoto" src="<c:if test="${item.photo eq ''}">/resources/images/folder2.png</c:if><c:if test="${item.photo ne ''}">/images/${item.photo}</c:if>"></a></td>
</tr>
<tr>
<td width="120" class="right">备注：</td>
<td><textarea id="note" name="note" cols=80 rows=3>${item.note}</textarea></td>
</tr>
		<tr>
			<td colspan="2">
				<input id="userid" name="userid" type="hidden" value="${item.userid}"/>
				<input id="uf_parentid" name="uf_parentid" type="hidden" value="${uf_parentid}"/>
				<input id="key_usertype" name="key_usertype" type="hidden" value="${key_usertype}"/>
				<input id="keyword" name="keyword" type="hidden" value="${keyword}"/>
				<input id="m" name="m" type="hidden" value="${m}"/>
				<input id="s" name="s" type="hidden" value="${s}"/>
				<input type="hidden" id="photo" name="photo"/>
				<c:if test="${auth82 eq '1'}">
				<input type="button" class="btn1" value=" 提  交 " onclick="checkf()"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="button" class="btn1" value=" 删  除 " onclick="checkdelete()"/>
				</c:if>
				<c:if test="${auth82 ne '1'}">
				<input type="button" class="btn1" value=" 返  回 " onclick="history.back();"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				</c:if>
			</td>
		</tr>
	</table>
</form>
<input type="file" id="f_photo" name="f_photo" onchange='_compress(this,$("#photo"),$("#imgphoto"));' style="width:0px;"/>
</body>
</html>
<script language=javascript>
function checkf() {
	if(commonForm.username.value=="") {alert("请正确输入姓名！");commonForm.username.focus();return false;}
	if(commonForm.account.value=="") {alert("请正确输入账号！");commonForm.account.focus();return false;}
  	if(commonForm.mobile.value=="" || commonForm.mobile.value!="" && !checkmobile(commonForm.mobile.value)) {alert("请正确输入手机号码！");commonForm.mobile.focus();return false;}
    if(commonForm.companyid.value=="1" && commonForm.companyid.value=="") {alert("请正确选择公司！");return false;}
	if(commonForm.seq.value!="" && !isnum(commonForm.seq.value)) {alert("请正确输入显示顺序！");commonForm.seq.focus();return false;}
	document.getElementById("commonForm").submit() ;
  
}

function tclick(t){
	if(t==1) {
		$("#cdiv").show();
	} else {
		$("#cdiv").hide();
	}
}

	$(document).ready(function(){
	
	} );


</script>