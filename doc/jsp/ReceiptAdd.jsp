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
<title>发票添加</title>
</head>
<body id="right">
<div class="operation o-h bg-f8">
	<div class="operationLeft f-l"><font style="margin-left:10px;font-size:14px; font-weight:bold; text-indent:14px; letter-spacing:2px;">发票添加</font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<a href="javascript:history.back();" style="margin-left:100px;"><i class="iconfont">&#xe681;</i>返回列表</a></div>
	<div class="operationRight f-r">
	</div>
</div>
<form id="commonForm" action="/emReceipt.do?method=add" method="post">
	<table class="tform">
<tr>
<td width="120" class="right">案件ID：</td>
<td><input type="text" id="claimid" name="claimid" value="" size=50></td>
</tr>
<tr>
<td width="120" class="right">发票号：</td>
<td><input type="text" id="receiptnumber" name="receiptnumber" value="" size=50></td>
</tr>
<tr>
<td width="120" class="right">医院ID：</td>
<td><input type="text" id="hospitalid" name="hospitalid" value="" size=50></td>
</tr>
<tr>
<td width="120" class="right">就诊日期：</td>
<td><input type="text" id="visitdate" name="visitdate" readonly="readonly" value="" onclick="WdatePicker({maxDate:'2018-12-31', dateFmt:'yyyy-MM-dd'})" style="width: 70px;"/></td>
</tr>
<tr>
<td width="120" class="right">住院日期：</td>
<td><input type="text" id="hospitaldate" name="hospitaldate" readonly="readonly" value="" onclick="WdatePicker({maxDate:'2018-12-31', dateFmt:'yyyy-MM-dd'})" style="width: 70px;"/></td>
</tr>
<tr>
<td width="120" class="right">出院日期：</td>
<td><input type="text" id="dischargedate" name="dischargedate" readonly="readonly" value="" onclick="WdatePicker({maxDate:'2018-12-31', dateFmt:'yyyy-MM-dd'})" style="width: 70px;"/></td>
</tr>
<tr>
<td width="120" class="right">统筹金额：</td>
<td><input type="text" id="fundpaid" name="fundpaid" value="" size=50></td>
</tr>
<tr>
<td width="120" class="right">个人缴费：</td>
<td><input type="text" id="cashpaid" name="cashpaid" value="" size=50></td>
</tr>
<tr>
<td width="120" class="right">合计：</td>
<td><input type="text" id="total" name="total" value="" size=50></td>
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
	if(commonForm.claimid.value=="" || commonForm.claimid.value!="" && !is_int(commonForm.claimid.value)) {alert("请正确输入案件ID！");return false;}
	if(commonForm.receiptnumber.value=="") {alert("请正确输入发票号！");return false;}
	if(commonForm.hospitalid.value=="" || commonForm.hospitalid.value!="" && !is_int(commonForm.hospitalid.value)) {alert("请正确输入医院ID！");return false;}
	if(commonForm.visitdate.value=="") {alert("请正确输入就诊日期！");return false;}
	if(commonForm.hospitaldate.value=="") {alert("请正确输入住院日期！");return false;}
	if(commonForm.dischargedate.value=="") {alert("请正确输入出院日期！");return false;}
	if(commonForm.fundpaid.value=="" || commonForm.fundpaid.value!="" && !(is_float(commonForm.fundpaid.value) || is_int(commonForm.fundpaid.value))) {alert("请正确输入统筹金额！");return false;}
	if(commonForm.cashpaid.value=="" || commonForm.cashpaid.value!="" && !(is_float(commonForm.cashpaid.value) || is_int(commonForm.cashpaid.value))) {alert("请正确输入个人缴费！");return false;}
	if(commonForm.total.value=="" || commonForm.total.value!="" && !(is_float(commonForm.total.value) || is_int(commonForm.total.value))) {alert("请正确输入合计！");return false;}

	document.getElementById("commonForm").submit() ;
  
}


</script>