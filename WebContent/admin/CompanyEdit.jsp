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
<title>公司<c:if test="${auth82 eq '1'}">修改</c:if><c:if test="${auth82 ne '1'}">查看</c:if></title>
</head>
<body id="right">
<div class="operation o-h bg-f8">
	<div class="operationLeft f-l"><font style="margin-left:10px;font-size:14px; font-weight:bold; text-indent:14px; letter-spacing:2px;">公司<c:if test="${auth82 eq '1'}">修改</c:if><c:if test="${auth82 ne '1'}">查看</c:if></font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<a href="javascript:history.back();" style="margin-left:100px;"><i class="iconfont">&#xe63d;</i>返回列表</a></div>
	<div class="operationRight f-r">
	</div>
</div>
<form id="commonForm" action="/emCompany.do?method=update" method="post">
	<table class="tform">
<tr>
<td width="120" class="right">公司名称：</td>
<td><input type="text" id="companyname" name="companyname" value="${fn:replace(item.companyname,'"','&quot;')}" size=50></td>
</tr>
<tr>
<td width="120" class="right">联系人：</td>
<td><input type="text" id="contact" name="contact" value="${fn:replace(item.contact,'"','&quot;')}" size=50></td>
</tr>
<tr>
<td width="120" class="right">联系电话：</td>
<td><input type="text" id="mobile" name="mobile" value="${fn:replace(item.mobile,'"','&quot;')}" size=50></td>
</tr>
<tr>
<td width="120" class="right">区域：</td>
<td><input type="text" id="areaid" name="areaid" value="${fn:replace(item.areaid,'"','&quot;')}" size=50></td>
</tr>
<tr>
<td width="120" class="right">地址：</td>
<td><input type="text" id="addr" name="addr" value="${fn:replace(item.addr,'"','&quot;')}" size=50></td>
</tr>
<tr>
<td width="120" class="right">是否有效：</td>
<td><input type="text" id="isvalid" name="isvalid" value="${fn:replace(item.isvalid,'"','&quot;')}" size=50></td>
</tr>
<tr>
<td width="120" class="right">公司类型：</td>
<td><input type="text" id="companytypeid" name="companytypeid" value="${fn:replace(item.companytypeid,'"','&quot;')}" size=50></td>
</tr>
<tr>
<td width="120" class="right">有效期（起）：</td>
<td><input type="text" id="startdate" name="startdate" readonly="readonly" value="${item.startdate_}" onclick="WdatePicker({maxDate:'2018-12-31', dateFmt:'yyyy-MM-dd'})" style="width: 70px;"/></td>
</tr>
<tr>
<td width="120" class="right">有效期（止）：</td>
<td><input type="text" id="expdate" name="expdate" readonly="readonly" value="${item.expdate_}" onclick="WdatePicker({maxDate:'2018-12-31', dateFmt:'yyyy-MM-dd'})" style="width: 70px;"/></td>
</tr>
<tr>
<td width="120" class="right">申请时间：</td>
<td><input type="text" id="createdate" name="createdate" readonly="readonly" value="${item.createdate_}" onclick="WdatePicker({maxDate:'2018-12-31', dateFmt:'yyyy-MM-dd'})" style="width: 70px;"/></td>
</tr>
<tr>
<td width="120" class="right">余额：</td>
<td><input type="text" id="balance" name="balance" value="${fn:replace(item.balance,'"','&quot;')}" size=50></td>
</tr>
<tr>
<td width="120" class="right">公司简介：</td>
<td><input type="text" id="note" name="note" value="${fn:replace(item.note,'"','&quot;')}" size=50></td>
</tr>
<tr>
<td width="120" class="right">审核意见：</td>
<td><input type="text" id="auditnote" name="auditnote" value="${fn:replace(item.auditnote,'"','&quot;')}" size=50></td>
</tr>
<tr>
<td width="120" class="right">营业执照：</td>
<td><a class="mya" href="javascript:f_photo0.click();"><img id="imgphoto0" src="<c:if test="${empty item.photo1}">/resources/images/folder2.png</c:if><c:if test="${not empty item.photo1}">/upload/images/${item.photo1}</c:if>"></a></td>
</tr>
<tr>
<td width="120" class="right">身份证：</td>
<td><a class="mya" href="javascript:f_photo1.click();"><img id="imgphoto1" src="<c:if test="${empty item.photo2}">/resources/images/folder2.png</c:if><c:if test="${not empty item.photo2}">/upload/images/${item.photo2}</c:if>"></a></td>
</tr>

		<tr>
			<td colspan="2">
			<input id="id" name="id" type="hidden" value="${item.id}"/>
<input type="hidden" id="photo0" name="photo0"/><input type="hidden" id="photo1" name="photo1"/>
				<input id="uf_parentid" name="uf_parentid" type="hidden" value="${uf_parentid}"/>
				<input id="keyword" name="keyword" type="hidden" value="${keyword}"/>
				<input id="m" name="m" type="hidden" value="${m}"/>
				<input id="s" name="s" type="hidden" value="${s}"/>
				<c:if test="${auth82 eq '1'}">
				<input type="button" class="btn1" value=" 提  交 " onclick="checkf()"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="button" class="btn1" value=" 删  除 " onclick="checkdelete()"/>
				</c:if>
				<c:if test="${auth82 ne '1'}">
				<input type="button" class="btn1" value=" 返  回 " onclick="history.back();"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				</c:if>
			</td>
		</tr>
	</table>
</form>
<input type="file" id="f_photo0" name="f_photo0" onchange='_compress(this,$("#photo0"),$("#imgphoto0"));' style="width:0px;"/><input type="file" id="f_photo1" name="f_photo1" onchange='_compress(this,$("#photo1"),$("#imgphoto1"));' style="width:0px;"/>
</body>
<form id="deleteForm" action="/emCompany.do?method=delete" method="post">
<input id="id" name="id" type="hidden" value="${item.id}"/>
<input id="uf_parentid" name="uf_parentid" type="hidden" value="${uf_parentid}"/>
<input id="keyword" name="keyword" type="hidden" value="${keyword}"/>
<input id="m" name="m" type="hidden" value="${m}"/>
<input id="s" name="s" type="hidden" value="${s}"/>
</form>
</html>
<script language=javascript>
function checkf() {
	if(commonForm.companyname.value=="") {alert("请正确输入公司名称！");return false;}
	if(commonForm.contact.value=="") {alert("请正确输入联系人！");return false;}
	if(commonForm.mobile.value=="") {alert("请正确输入联系电话！");return false;}
	if(commonForm.areaid.value=="" || commonForm.areaid.value!="" && !is_int(commonForm.areaid.value)) {alert("请正确输入区域！");return false;}
	if(commonForm.addr.value=="") {alert("请正确输入地址！");return false;}
	if(commonForm.isvalid.value=="" || commonForm.isvalid.value!="" && !is_int(commonForm.isvalid.value)) {alert("请正确输入是否有效！");return false;}
	if(commonForm.companytypeid.value=="" || commonForm.companytypeid.value!="" && !is_int(commonForm.companytypeid.value)) {alert("请正确输入公司类型！");return false;}
	if(commonForm.startdate.value=="") {alert("请正确输入有效期（起）！");return false;}
	if(commonForm.expdate.value=="") {alert("请正确输入有效期（止）！");return false;}
	if(commonForm.createdate.value=="") {alert("请正确输入申请时间！");return false;}
	if(commonForm.balance.value=="" || commonForm.balance.value!="" && !(is_float(commonForm.balance.value) || is_int(commonForm.balance.value))) {alert("请正确输入余额！");return false;}
	if(commonForm.note.value=="") {alert("请正确输入公司简介！");return false;}
	if(commonForm.auditnote.value=="") {alert("请正确输入审核意见！");return false;}

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