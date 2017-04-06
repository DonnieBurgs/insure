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
<script type="text/javascript">var root = "";</script>
<script type="text/javascript" src="/resources/js/admin.js"></script>
<script type="text/javascript" src="/resources/js/common.js"></script>
<title>权限<c:if test="${auth84 eq '1'}">修改</c:if><c:if test="${auth84 ne '1'}">查看</c:if></title>
</head>
<body id="right">
<div class="operation o-h bg-f8">
	<div class="operationLeft f-l"><font style="margin-left:10px;font-size:14px; font-weight:bold; text-indent:14px; letter-spacing:2px;">权限<c:if test="${auth84 eq '1'}">修改</c:if><c:if test="${auth84 ne '1'}">查看</c:if></font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<a href="javascript:history.back();" style="margin-left:100px;"><i class="iconfont">&#xe63d;</i>返回列表</a></div>
	<div class="operationRight f-r">
	</div>
</div>
<form id="authorityForm" action="/emAuthority.do?method=update" method="post">
	<table class="tform">
<tr>
	<td width="120" class="right">名称：</td>
	<td><input id="authorityname" name="authorityname" type="text" value="${item.authorityname}"/></td>
</tr>
<tr>
<td width="120" class="right">权限类别：</td>
<td>
<select name="ctype">
<option value="2"<c:if test="${item.ctype eq 2}"> selected</c:if>>平台客服
<option value="3"<c:if test="${item.ctype eq 3}"> selected</c:if>>第三方客服
<option value="4"<c:if test="${item.ctype eq 4}"> selected</c:if>>销售
</select>
</td>
</tr>
<tr>
	<td width="120" class="right">权限：</td>
	<td>
<c:choose>
<c:when test="${not empty authorityStr}">
<c:forEach var="aus" items="${authorityStr}" varStatus="idx">
	<c:if test="${idx.index eq 0}">会员管理：<br>&nbsp;&nbsp;&nbsp;&nbsp;</c:if>
	<c:if test="${idx.index eq 10}"><br><br>业务管理：<br>&nbsp;&nbsp;&nbsp;&nbsp;</c:if>
	<c:if test="${idx.index eq 40}"><br><br>资料管理：<br>&nbsp;&nbsp;&nbsp;&nbsp;</c:if>
	<c:if test="${idx.index eq 50}"><br><br>营销管理：<br>&nbsp;&nbsp;&nbsp;&nbsp;</c:if>
	<c:if test="${idx.index eq 80}"><br><br>系统管理：<br>&nbsp;&nbsp;&nbsp;&nbsp;</c:if>
	<c:if test="${aus ne ''}">
		<input id="a${idx.index+1}" name="a${idx.index+1}" type="checkbox" value="1"<c:if test="${fn:substring(item.authorityno,idx.index,idx.index+1) eq '1'}"> checked</c:if>/>${aus}&nbsp;&nbsp;
	</c:if>
</c:forEach>
</c:when>
</c:choose>
	</td>
</tr>
<tr>
	<td width="120" class="right">备注：</td>
	<td><input id="note" name="note" type="text" value="${item.note}"/></td>
</tr>
		<tr>
			<td colspan="2">
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<input id="authorityid" name="authorityid" type="hidden" value="${item.authorityid}"/>
				<c:if test="${auth84 eq '1'}">
				<input type="button" class="btn1" value=" 提  交 " onclick="checkf()"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="button" class="btn1" value=" 删  除 " onclick="checkdelete()"/>
				</c:if>
				<c:if test="${auth84 ne '1'}">
				<input type="button" class="btn1" value=" 返  回 " onclick="history.back();"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				</c:if>
			</td>
		</tr>
	</table>
</form>
</body>
</html>
<script language=javascript>
function checkf() {
  if(authorityForm.authorityname.value=="") {alert("请正确输入名称！");authorityForm.authorityname.focus();return false;}
  document.getElementById("authorityForm").submit() ;
  
}
function checkdelete() {
	if(confirm("是否删除当前数据记录？")==false) {
		return ;
	} else {
    document.getElementById("deleteForm").submit() ;
	}
	
}
</script>
<form id="deleteForm" action="/emAuthority.do?method=delete" method="post">
				<input id="authorityid" name="authorityid" type="hidden" value="${item.authorityid}"/>
</form>