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
<title>救援金券领取记录添加</title>
</head>
<body id="right">
<div class="operation o-h bg-f8">
	<div class="operationLeft f-l"><font style="margin-left:10px;font-size:14px; font-weight:bold; text-indent:14px; letter-spacing:2px;">救援金券领取记录添加</font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<a href="javascript:history.back();" style="margin-left:100px;"><i class="iconfont">&#xe681;</i>返回列表</a></div>
	<div class="operationRight f-r">
	</div>
</div>
<form id="commonForm" action="/emCouponCode.do?method=add" method="post">
	<table class="tform">
<tr>
<td width="120" class="right">救援金券ID：</td>
<td><input type="text" id="couponid" name="couponid" value="" size=50></td>
</tr>
<tr>
<td width="120" class="right">用户ID：</td>
<td><input type="text" id="userid" name="userid" value="" size=50></td>
</tr>
<tr>
<td width="120" class="right">领取时间：</td>
<td><input type="text" id="createdate" name="createdate" readonly="readonly" value="" onclick="WdatePicker({maxDate:'2018-12-31', dateFmt:'yyyy-MM-dd'})" style="width: 70px;"/></td>
</tr>
<tr>
<td width="120" class="right">是否使用：</td>
<td>
<select name="isused">
<option value="0">未使用
<option value="1">已使用
</select>
</td>
</tr>
<tr>
<td width="120" class="right">关联订单号：</td>
<td><input type="text" id="orderid" name="orderid" value="" size=50></td>
</tr>
<tr>
<td width="120" class="right">备注：</td>
<td><input type="text" id="note" name="note" value="" size=50></td>
</tr>
<tr>
<td width="120" class="right">顺序号：</td>
<td><input type="text" id="seq" name="seq" value="1000" size=50></td>
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
	if(commonForm.couponid.value=="" || commonForm.couponid.value!="" && !is_int(commonForm.couponid.value)) {alert("请正确输入救援金券ID！");return false;}
	if(commonForm.userid.value=="" || commonForm.userid.value!="" && !is_int(commonForm.userid.value)) {alert("请正确输入用户ID！");return false;}
	if(commonForm.createdate.value=="") {alert("请正确输入领取时间！");return false;}
	if(commonForm.isused.value=="" || commonForm.isused.value!="" && !is_int(commonForm.isused.value)) {alert("请正确输入是否使用！");return false;}
	if(commonForm.seq.value=="" || commonForm.seq.value!="" && !is_int(commonForm.seq.value)) {alert("请正确输入顺序号！");return false;}

	document.getElementById("commonForm").submit() ;
  
}


</script>