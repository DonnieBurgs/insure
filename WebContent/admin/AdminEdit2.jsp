<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<link type="text/css" rel="stylesheet" href="/admin/style/base.css" />
<link type="text/css" rel="stylesheet" href="/admin/style/global.css" />
<link href="/resources/css/main.css" rel="stylesheet" type="text/css" />
<script src="http://libs.baidu.com/jquery/1.8.3/jquery.min.js"></script>
<script type="text/javascript" src="/resources/js/xheditor/xheditor-zh-cn.min.js"></script>
<script type="text/javascript">var root = "";</script>
<script type="text/javascript" src="/resources/js/admin.js"></script>
<script type="text/javascript" src="/resources/js/common.js"></script>
<title>管理员修改</title>
</head>
<body id="right">
<div class="operation o-h bg-f8">
	<div class="operationLeft f-l"><font style="margin-left:10px;font-size:14px; font-weight:bold; text-indent:14px; letter-spacing:2px;">管理员修改</font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<a href="javascript:history.back();" style="margin-left:100px;"><i class="iconfont">&#xe63d;</i>返回列表</a></div>
	<div class="operationRight f-r">
	</div>
</div>
<form id="userForm" action="/emAdmin2.do?method=update" method="post">
	<table class="tform">
<tr>
	<td width="120" class="right">账号：</td>
	<td>${item.account}</td>
</tr>
<tr>
	<td width="120" class="right">姓名：</td>
	<td><input id="username" name="username" type="text" value="${item.username}"/></td>
</tr>
<tr>
	<td width="120" class="right">权限：</td>
	<td>
<c:if test="${not empty aList}">
<c:forEach var="auth" items="${aList}">
      <c:if test="${item.authorityid eq auth.authorityid}">${auth.authorityname}</c:if>
</c:forEach>
</c:if>
	</td>
</tr>
<tr>
	<td width="120" class="right">备注：</td>
	<td>${item.note}</td>
</tr>
<tr>
	<td width="120" class="right">有效：</td>
	<td><c:if test="${item.isvalid eq '1'}"> 账户有效</c:if>&nbsp;&nbsp;
  </td>
</tr>
<tr>
<td colspan="2">${msg}
	
</td>
</tr>
		<tr>
			<td colspan="2"><input type="button" class="btn1" value=" 提  交 " onclick="checkf()"/>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<input id="f" name="f" type="hidden" value="1"/>
				
			</td>
		</tr>
	</table>
</form>
</body>
</html>
<script language=javascript>
function checkf() {
  if(userForm.username.value=="" || userForm.username.value.length>10) {alert("请正确输入姓名！长度不超过10个汉字。");userForm.username.focus();return false;}

  document.getElementById("userForm").submit() ;
  
}

</script>
