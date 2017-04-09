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
<title>发票<c:if test="${auth44 eq '1'}">修改</c:if><c:if test="${auth44 ne '1'}">查看</c:if></title>
</head>
<body id="right">
<div class="operation o-h bg-f8">
	<div class="operationLeft f-l"><font style="margin-left:10px;font-size:14px; font-weight:bold; text-indent:14px; letter-spacing:2px;">发票<c:if test="${auth44 eq '1'}">修改</c:if><c:if test="${auth44 ne '1'}">查看</c:if></font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<a href="javascript:history.back();" style="margin-left:100px;"><i class="iconfont">&#xe63d;</i>返回列表</a></div>
	<div class="operationRight f-r">
	</div>
</div>
<form id="commonForm" action="/emReceipt.do?method=update" method="post">
	<table class="tform">
<tr>
<td width="120" class="right">案件ID：</td>
<td><input type="text" id="claimid" name="claimid" value="${fn:replace(item.claimid,'"','&quot;')}" size=50></td>
</tr>
<tr>
<td width="120" class="right">发票号：</td>
<td><input type="text" id="receiptnumber" name="receiptnumber" value="${fn:replace(item.receiptnumber,'"','&quot;')}" size=50></td>
</tr>
<tr>
<td width="120" class="right">医院ID：</td>
<td><input type="text" id="hospitalid" name="hospitalid" value="${fn:replace(item.hospitalid,'"','&quot;')}" size=50></td>
</tr>
<tr>
<td width="120" class="right">就诊日期：</td>
<td><input type="text" id="visitdate" name="visitdate" readonly="readonly" value="${item.visitdate_}" onclick="WdatePicker({maxDate:'2018-12-31', dateFmt:'yyyy-MM-dd'})" style="width: 70px;"/></td>
</tr>
<tr>
<td width="120" class="right">住院日期：</td>
<td><input type="text" id="hospitaldate" name="hospitaldate" readonly="readonly" value="${item.hospitaldate_}" onclick="WdatePicker({maxDate:'2018-12-31', dateFmt:'yyyy-MM-dd'})" style="width: 70px;"/></td>
</tr>
<tr>
<td width="120" class="right">出院日期：</td>
<td><input type="text" id="dischargedate" name="dischargedate" readonly="readonly" value="${item.dischargedate_}" onclick="WdatePicker({maxDate:'2018-12-31', dateFmt:'yyyy-MM-dd'})" style="width: 70px;"/></td>
</tr>
<tr>
<td width="120" class="right">统筹金额：</td>
<td><input type="text" id="fundpaid" name="fundpaid" value="${fn:replace(item.fundpaid,'"','&quot;')}" size=50></td>
</tr>
<tr>
<td width="120" class="right">个人缴费：</td>
<td><input type="text" id="cashpaid" name="cashpaid" value="${fn:replace(item.cashpaid,'"','&quot;')}" size=50></td>
</tr>
<tr>
<td width="120" class="right">合计：</td>
<td><input type="text" id="total" name="total" value="${fn:replace(item.total,'"','&quot;')}" size=50></td>
</tr>

		<tr>
			<td colspan="2">
			<input id="id" name="id" type="hidden" value="${item.id}"/>

				<input id="uf_parentid" name="uf_parentid" type="hidden" value="${uf_parentid}"/>
				<input id="keyword" name="keyword" type="hidden" value="${keyword}"/>
				<input id="m" name="m" type="hidden" value="${m}"/>
				<input id="s" name="s" type="hidden" value="${s}"/>
				<c:if test="${auth44 eq '1'}">
				<input type="button" class="btn1" value=" 提  交 " onclick="checkf()"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="button" class="btn1" value=" 删  除 " onclick="checkdelete()"/>
				</c:if>
				<c:if test="${auth44 ne '1'}">
				<input type="button" class="btn1" value=" 返  回 " onclick="history.back();"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				</c:if>
			</td>
		</tr>
	</table>
</form>

</body>
<form id="deleteForm" action="/emReceipt.do?method=delete" method="post">
<input id="id" name="id" type="hidden" value="${item.id}"/>
<input id="uf_parentid" name="uf_parentid" type="hidden" value="${uf_parentid}"/>
<input id="keyword" name="keyword" type="hidden" value="${keyword}"/>
<input id="m" name="m" type="hidden" value="${m}"/>
<input id="s" name="s" type="hidden" value="${s}"/>
</form>
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

function checkdelete() {
	if(confirm("是否删除当前数据记录？")==false) {
		return ;
	} else {
		document.getElementById("deleteForm").submit() ;
	}
	
}

</script>