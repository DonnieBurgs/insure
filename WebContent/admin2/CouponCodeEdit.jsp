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
<title>救援金券领取记录<c:if test="${auth70 eq '1'}">修改</c:if><c:if test="${auth70 ne '1'}">查看</c:if></title>
</head>
<body id="right">
<div class="operation o-h bg-f8">
	<div class="operationLeft f-l"><font style="margin-left:10px;font-size:14px; font-weight:bold; text-indent:14px; letter-spacing:2px;">救援金券领取记录<c:if test="${auth70 eq '1'}">修改</c:if><c:if test="${auth70 ne '1'}">查看</c:if></font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<a href="javascript:history.back();" style="margin-left:100px;"><i class="iconfont">&#xe63d;</i>返回列表</a></div>
	<div class="operationRight f-r">
	</div>
</div>
<form id="commonForm" action="/emCouponCode.do?method=update" method="post">
	<table class="tform">
<tr>
<td width="120" class="right">用户：</td>
<td><input type="text" id="userid" name="userid" value="${fn:replace(item.userid,'"','&quot;')}" size=50></td>
</tr>
<tr>
<td width="120" class="right">领取时间：</td>
<td>${item.createdate}</td>
</tr>
<tr>
<td width="120" class="right">是否使用：</td>
<td>
<select name="isused">
<option value="0"<c:if test="${item.usertype eq 0}"> selected</c:if>>未使用
<option value="1"<c:if test="${item.usertype eq 1}"> selected</c:if>>已使用
</select>
</td>
</tr>
<tr>
<td width="120" class="right">使用时间：</td>
<td>${item.useddate}</td>
</tr>
<tr>
<td width="120" class="right">关联订单号：</td>
<td><input type="text" id="orderid" name="orderid" value="${fn:replace(item.orderid,'"','&quot;')}" size=50></td>
</tr>
<tr>
<td width="120" class="right">备注：</td>
<td><input type="text" id="note" name="note" value="${fn:replace(item.note,'"','&quot;')}" size=50></td>
</tr>
<tr>
<td width="120" class="right">顺序号：</td>
<td><input type="text" id="seq" name="seq" value="${fn:replace(item.seq,'"','&quot;')}" size=50></td>
</tr>

		<tr>
			<td colspan="2">
			<input id="couponcodeid" name="couponcodeid" type="hidden" value="${item.couponcodeid}"/>

				<input id="uf_parentid" name="uf_parentid" type="hidden" value="${uf_parentid}"/>
				<input id="keyword" name="keyword" type="hidden" value="${keyword}"/>
				<input id="m" name="m" type="hidden" value="${m}"/>
				<input id="s" name="s" type="hidden" value="${s}"/>
				<c:if test="${auth70 eq '1'}">
				<input type="button" class="btn1" value=" 提  交 " onclick="checkf()"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="button" class="btn1" value=" 删  除 " onclick="checkdelete()"/>
				</c:if>
				<c:if test="${auth70 ne '1'}">
				<input type="button" class="btn1" value=" 返  回 " onclick="history.back();"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				</c:if>
			</td>
		</tr>
	</table>
</form>

</body>
<form id="deleteForm" action="/emCouponCode.do?method=delete" method="post">
<input id="couponcodeid" name="couponcodeid" type="hidden" value="${item.couponcodeid}"/>
<input id="uf_parentid" name="uf_parentid" type="hidden" value="${uf_parentid}"/>
<input id="keyword" name="keyword" type="hidden" value="${keyword}"/>
<input id="m" name="m" type="hidden" value="${m}"/>
<input id="s" name="s" type="hidden" value="${s}"/>
</form>
</html>
<script language=javascript>
function checkf() {
	if(commonForm.userid.value=="" || commonForm.userid.value!="" && !is_int(commonForm.userid.value)) {alert("请正确输入用户ID！");return false;}
	if(commonForm.orderid.value=="" || commonForm.orderid.value!="" && !is_int(commonForm.orderid.value)) {alert("请正确输入关联订单号！");return false;}
	if(commonForm.seq.value=="" || commonForm.seq.value!="" && !is_int(commonForm.seq.value)) {alert("请正确输入顺序号！");return false;}

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