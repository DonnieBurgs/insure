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
<title>套餐类型添加</title>
</head>
<body id="right">
<div class="operation o-h bg-f8">
	<div class="operationLeft f-l"><font style="margin-left:10px;font-size:14px; font-weight:bold; text-indent:14px; letter-spacing:2px;">套餐类型添加</font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<a href="javascript:history.back();" style="margin-left:100px;"><i class="iconfont">&#xe681;</i>返回列表</a></div>
	<div class="operationRight f-r">
	</div>
</div>
<form id="commonForm" action="/emPackType.do?method=add" method="post">
	<table class="tform">
<tr>
<td width="120" class="right">套餐类型名称：</td>
<td><input type="text" id="packtypename" name="packtypename" value="" size=50></td>
</tr>
<tr>
<td width="120" class="right">图标：</td>
<td><a class="mya" href="javascript:f_photo0.click();"><img id="imgphoto0" src="/resources/images/folder2.png"></a></td>
</tr>
<c:if test="${not empty cartypeList}">
<c:forEach var="listItem" items="${cartypeList}" varStatus="idx">
	<tr>
		<td width="120" class="right">${listItem.cartypename}价格：</td>
		<td><input type="text" id="price${idx.index}" name="price${idx.index}" value="" size=50></td>
	</tr>
</c:forEach>
</c:if>
<tr>
<td width="120" class="right">排序：</td>
<td><input type="text" id="seq" name="seq" value="1000" size=50></td>
</tr>

		<tr>
			<td colspan="2">
<input type="hidden" id="photo0" name="photo0"/>
				<input id="uf_parentid" name="uf_parentid" type="hidden" value="${uf_parentid}"/>
				<input id="keyword" name="keyword" type="hidden" value="${keyword}"/>
				<input id="m" name="m" type="hidden" value="${m}"/>
				<input id="s" name="s" type="hidden" value="${s}"/>
				<input type="button" class="btn1" value=" 提  交 " onclick="checkf()"/>
			</td>
		</tr>
	</table>
</form>
<input type="file" id="f_photo0" name="f_photo0" onchange='_compress(this,$("#photo0"),$("#imgphoto0"));' style="width:0px;"/>
</body>
</html>
<script language=javascript>
function checkf() {
	if(commonForm.packtypename.value=="") {alert("请正确输入套餐类型名称！");return false;}
	<c:if test="${not empty cartypeList}">
	<c:forEach var="listItem" items="${cartypeList}" varStatus="idx">
	if(commonForm.price${idx.index}.value=="" || commonForm.price${idx.index}.value!="" && !(is_float(commonForm.price${idx.index}.value) || is_int(commonForm.price${idx.index}.value))) {alert("请正确输入${listItem.cartypename}价格！");return false;}
	</c:forEach>
	</c:if>
	if(commonForm.seq.value=="" || commonForm.seq.value!="" && !is_int(commonForm.seq.value)) {alert("请正确输入排序！");return false;}

	document.getElementById("commonForm").submit() ;
  
}


</script>