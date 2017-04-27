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
<title>案件信息<c:if test="${auth44 eq '1'}">修改</c:if><c:if test="${auth44 ne '1'}">查看</c:if></title>
</head>
<body id="right">
<div class="operation o-h bg-f8">
	<div class="operationLeft f-l"><font style="margin-left:10px;font-size:14px; font-weight:bold; text-indent:14px; letter-spacing:2px;">案件信息<c:if test="${auth44 eq '1'}">修改</c:if><c:if test="${auth44 ne '1'}">查看</c:if></font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<a href="javascript:history.back();" style="margin-left:100px;"><i class="iconfont">&#xe63d;</i>返回列表</a></div>
	<div class="operationRight f-r">
	</div>
</div>
<form id="commonForm" action="/emClaimInfo.do?method=update" method="post">
<input type="hidden" id="info_diseaseid" name="info.diseaseid" value="" size=50>
<input type="hidden" id="claimid" name="claimid" value="" size=50>
	<table class="tform">
<tr>
<td><font color="red">*</font>案件类型：</td>
<td><font color="red">*</font>出险原因：</td>
<td><font color="red">*</font>出险日期：</td>
<td><font color="red">*</font>保险金支付方式：</td>
<td><font color="red">*</font>疾病：</td>
</tr>
<tr><td><select id="info_claimtype" name="claimtype">
		<c:forEach var="it" items="${claimtypeMap }"><option value="${it.key }">${it.value}</option></c:forEach>
		</select></td>
<td><select id="info_claimreason" name="claimreason">
		<c:forEach var="it" items="${claimreasonMap }"><option value="${it.key }">${it.value}</option></c:forEach>
		</select></td>
<td><input type="text" id="info_claimdate" name="claimdate" readonly="readonly" value="" onclick="WdatePicker({maxDate:'2018-12-31', dateFmt:'yyyy-MM-dd'})" style="width: 70px;"/></td>
<td><select id="info_paytype" name="paytype">
		<c:forEach var="it" items="${paytypeMap }"><option value="${it.key }">${it.value}</option></c:forEach>
		</select></td>

<td><input type="text" id="info_emDisease" name="emDisease" value="" size=50>
</tr>
<tr>
<td><font color="red">*</font>出险类型1：</td>
<td><font color="red">*</font>出险类型2：</td>
<td><font color="red">*</font>出险类型3：</td>
<td><font color="red">*</font>索赔金额：</td>
<td><font color="red">*</font>就诊次数：</td>

</tr>
<tr>
<td>
<select id="info_insuretype1" name="insuretype1">
		<c:forEach var="it" items="${insuretypeMap }"><option value="${it.key }">${it.value}</option></c:forEach>
		</select></td>
<td><select id="info_insuretype2" name="insuretype2">
		<c:forEach var="it" items="${insuretypeMap }"><option value="${it.key }">${it.value}</option></c:forEach>
		</select></td>
<td><select id="info_insuretype3" name="insuretype3">
		<c:forEach var="it" items="${insuretypeMap }"><option value="${it.key }">${it.value}</option></c:forEach>
		</select></td>

<td><input type="text" id="info_spamount" name="spamount" value="" size=50></td>

<td><input type="text" id="info_determinecount" name="determinecount" value="" size=50></td>
</tr>
<tr>
<td><font color="red">*</font>单张数：</td>
<td colspan="4"><font color="red">*</font>备注：</td>
</tr>
<tr>
<td><input type="text" id="info_receiptcount" name="receiptcount" value="" size=50></td>
<td colspan="4"><input type="text" id="info_mark" name="mark" value="" size=50></td>
</tr>
	</table>
<table class="tform">
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
<form id="deleteForm" action="/emClaimInfo.do?method=delete" method="post">
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
	if(commonForm.claimtype.value=="" || commonForm.claimtype.value!="" && !is_int(commonForm.claimtype.value)) {alert("请正确输入案件类型！");return false;}
	if(commonForm.claimreason.value=="" || commonForm.claimreason.value!="" && !is_int(commonForm.claimreason.value)) {alert("请正确输入出险原因！");return false;}
	if(commonForm.claimdate.value=="") {alert("请正确输入出险日期！");return false;}
	if(commonForm.paytype.value=="" || commonForm.paytype.value!="" && !is_int(commonForm.paytype.value)) {alert("请正确输入保险金支付方式！");return false;}
	if(commonForm.insuretype1.value=="") {alert("请正确输入出险类型1！");return false;}
	if(commonForm.insuretype2.value=="") {alert("请正确输入出险类型2！");return false;}
	if(commonForm.insuretype3.value=="") {alert("请正确输入出险类型3！");return false;}
	if(commonForm.spamount.value=="" || commonForm.spamount.value!="" && !(is_float(commonForm.spamount.value) || is_int(commonForm.spamount.value))) {alert("请正确输入索赔金额！");return false;}
	if(commonForm.diseaseid.value=="" || commonForm.diseaseid.value!="" && !is_int(commonForm.diseaseid.value)) {alert("请正确输入疾病ID！");return false;}
	if(commonForm.determinecount.value=="" || commonForm.determinecount.value!="" && !is_int(commonForm.determinecount.value)) {alert("请正确输入就诊次数！");return false;}
	if(commonForm.receiptcount.value=="" || commonForm.receiptcount.value!="" && !is_int(commonForm.receiptcount.value)) {alert("请正确输入单张数！");return false;}
	if(commonForm.mark.value=="") {alert("请正确输入备注！");return false;}

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