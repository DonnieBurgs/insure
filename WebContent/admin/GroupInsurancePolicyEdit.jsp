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
<title>团保方案<c:if test="${auth32 eq '1'}">修改</c:if><c:if test="${auth32 ne '1'}">查看</c:if></title>
</head>
<body id="right">
<div class="operation o-h bg-f8">
	<div class="operationLeft f-l"><font style="margin-left:10px;font-size:14px; font-weight:bold; text-indent:14px; letter-spacing:2px;">团保方案<c:if test="${auth32 eq '1'}">修改</c:if><c:if test="${auth32 ne '1'}">查看</c:if></font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<a href="javascript:history.back();" style="margin-left:100px;"><i class="iconfont">&#xe63d;</i>返回列表</a></div>
	<div class="operationRight f-r">
	</div>
</div>
<form id="commonForm" action="/emGroupInsurancePolicy.do?method=update" method="post">
	<table class="tform">
<tr>
<td width="120" class="right">投保公司：</td>
<td><input type="text" id="applicantcompanyid" name="applicantcompanyid" value="${fn:replace(item.applicantcompanyid,'"','&quot;')}" size=50></td>
</tr>
<tr>
<td width="120" class="right">承保公司：</td>
<td><input type="text" id="insurercompanyid" name="insurercompanyid" value="${fn:replace(item.insurercompanyid,'"','&quot;')}" size=50></td>
</tr>
<tr>
<td width="120" class="right">保单名称：</td>
<td><input type="text" id="groupinsurancepolicyname" name="groupinsurancepolicyname" value="${fn:replace(item.groupinsurancepolicyname,'"','&quot;')}" size=50></td>
</tr>
<tr>
<td width="120" class="right">保单编号：</td>
<td><input type="text" id="policynumber" name="policynumber" value="${fn:replace(item.policynumber,'"','&quot;')}" size=50></td>
</tr>
<tr>
<td width="120" class="right">保费合计：</td>
<td><input type="text" id="premium" name="premium" value="${fn:replace(item.premium,'"','&quot;')}" size=50></td>
</tr>
<tr>
<td width="120" class="right">保险期间（起）：</td>
<td><input type="text" id="periodbegin" name="periodbegin" readonly="readonly" value="${item.periodbegin_}" onclick="WdatePicker({maxDate:'2018-12-31', dateFmt:'yyyy-MM-dd'})" style="width: 70px;"/></td>
</tr>
<tr>
<td width="120" class="right">保险期间（止）：</td>
<td><input type="text" id="periodend" name="periodend" readonly="readonly" value="${item.periodend_}" onclick="WdatePicker({maxDate:'2018-12-31', dateFmt:'yyyy-MM-dd'})" style="width: 70px;"/></td>
</tr>
<tr>
<td width="120" class="right">（团保）方案名：</td>
<td><input type="text" id="policyid" name="policyid" value="${fn:replace(item.policyid,'"','&quot;')}" size=50></td>
</tr>
<tr>
<td width="120" class="right">意外险保单号：</td>
<td><input type="text" id="ywxpolicynumb" name="ywxpolicynumb" value="${fn:replace(item.ywxpolicynumb,'"','&quot;')}" size=50></td>
</tr>

		<tr>
			<td colspan="2">
			<input id="id" name="id" type="hidden" value="${item.id}"/>

				<input id="uf_parentid" name="uf_parentid" type="hidden" value="${uf_parentid}"/>
				<input id="keyword" name="keyword" type="hidden" value="${keyword}"/>
				<input id="m" name="m" type="hidden" value="${m}"/>
				<input id="s" name="s" type="hidden" value="${s}"/>
				<c:if test="${auth32 eq '1'}">
				<input type="button" class="btn1" value=" 提  交 " onclick="checkf()"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="button" class="btn1" value=" 删  除 " onclick="checkdelete()"/>
				</c:if>
				<c:if test="${auth32 ne '1'}">
				<input type="button" class="btn1" value=" 返  回 " onclick="history.back();"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				</c:if>
			</td>
		</tr>
	</table>
</form>

</body>
<form id="deleteForm" action="/emGroupInsurancePolicy.do?method=delete" method="post">
<input id="id" name="id" type="hidden" value="${item.id}"/>
<input id="uf_parentid" name="uf_parentid" type="hidden" value="${uf_parentid}"/>
<input id="keyword" name="keyword" type="hidden" value="${keyword}"/>
<input id="m" name="m" type="hidden" value="${m}"/>
<input id="s" name="s" type="hidden" value="${s}"/>
</form>
</html>
<script language=javascript>
function checkf() {
	if(commonForm.applicantcompanyid.value=="" || commonForm.applicantcompanyid.value!="" && !is_int(commonForm.applicantcompanyid.value)) {alert("请正确输入投保公司！");return false;}
	if(commonForm.insurercompanyid.value=="" || commonForm.insurercompanyid.value!="" && !is_int(commonForm.insurercompanyid.value)) {alert("请正确输入承保公司！");return false;}
	if(commonForm.groupinsurancepolicyname.value=="") {alert("请正确输入保单名称！");return false;}
	if(commonForm.policynumber.value=="") {alert("请正确输入保单编号！");return false;}
	if(commonForm.premium.value=="" || commonForm.premium.value!="" && !(is_float(commonForm.premium.value) || is_int(commonForm.premium.value))) {alert("请正确输入保费合计！");return false;}
	if(commonForm.periodbegin.value=="") {alert("请正确输入保险期间（起）！");return false;}
	if(commonForm.periodend.value=="") {alert("请正确输入保险期间（止）！");return false;}
	if(commonForm.policyid.value=="" || commonForm.policyid.value!="" && !is_int(commonForm.policyid.value)) {alert("请正确输入（团保）方案名！");return false;}
	if(commonForm.ywxpolicynumb.value=="") {alert("请正确输入意外险保单号！");return false;}

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