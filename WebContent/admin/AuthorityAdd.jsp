<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
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
<title>权限添加</title>
</head>
<body id="right">
<div class="operation o-h bg-f8">
	<div class="operationLeft f-l"><font style="margin-left:10px;font-size:14px; font-weight:bold; text-indent:14px; letter-spacing:2px;">权限添加</font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<a href="javascript:history.back();" style="margin-left:100px;"><i class="iconfont">&#xe681;</i>返回列表</a></div>
	<div class="operationRight f-r">
	</div>
</div>
<form id="authorityForm" action="/emAuthority.do?method=add" method="post">
	<table class="tform">
<tr>
	<td width="120" class="right">名称：</td>
	<td><input id="authorityname" name="authorityname" type="text" value=""/></td>
</tr>
<tr>
<td width="120" class="right">权限类别：</td>
<td>
<select name="ctype">
<option value="2">平台客服
<option value="3">第三方客服
<option value="4">销售
</select>
</td>
</tr>
<tr>
	<td width="120" class="right">权限：</td>
	<td>
<c:choose>
<c:when test="${not empty authorityStr}">
<c:forEach var="item" items="${authorityStr}" varStatus="idx">
	<c:if test="${idx.index eq 0}">会员管理：<br>&nbsp;&nbsp;&nbsp;&nbsp;</c:if>
	<c:if test="${idx.index eq 10}"><br><br>业务管理：<br>&nbsp;&nbsp;&nbsp;&nbsp;</c:if>
	<c:if test="${idx.index eq 40}"><br><br>资料管理：<br>&nbsp;&nbsp;&nbsp;&nbsp;</c:if>
	<c:if test="${idx.index eq 50}"><br><br>营销管理：<br>&nbsp;&nbsp;&nbsp;&nbsp;</c:if>
	<c:if test="${idx.index eq 80}"><br><br>系统管理：<br>&nbsp;&nbsp;&nbsp;&nbsp;</c:if>
	<c:if test="${item ne ''}">
		<input id="a${idx.index+1}" name="a${idx.index+1}" type="checkbox" value="1"/>${item}&nbsp;&nbsp;
	</c:if>
</c:forEach>
</c:when>
</c:choose>
	</td>
</tr>
<tr>
	<td width="120" class="right">备注：</td>
	<td><input id="note" name="note" type="text" value=""/></td>
</tr>
		<tr>
			<td colspan="2"><input type="button" class="btn1" value=" 提  交 " onclick="checkf()"/>
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
</script>