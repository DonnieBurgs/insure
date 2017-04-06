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
<title>理算<c:if test="${auth44 eq '1'}">修改</c:if><c:if test="${auth44 ne '1'}">查看</c:if></title>
</head>
<body id="right">
<div class="operation o-h bg-f8">
	<div class="operationLeft f-l"><font style="margin-left:10px;font-size:14px; font-weight:bold; text-indent:14px; letter-spacing:2px;">理算<c:if test="${auth44 eq '1'}">修改</c:if><c:if test="${auth44 ne '1'}">查看</c:if></font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<a href="javascript:history.back();" style="margin-left:100px;"><i class="iconfont">&#xe63d;</i>返回列表</a></div>
	<div class="operationRight f-r">
	</div>
</div>
<form id="commonForm" action="/emDetermine.do?method=update" method="post">
	<table class="tform">
<tr>
<td width="120" class="right">案件ID：</td>
<td><input type="text" id="claimid" name="claimid" value="${fn:replace(item.claimid,'"','&quot;')}" size=50></td>
</tr>
<tr>
<td width="120" class="right">疾病ID：</td>
<td><input type="text" id="diseaseid" name="diseaseid" value="${fn:replace(item.diseaseid,'"','&quot;')}" size=50></td>
</tr>
<tr>
<td width="120" class="right">赔付类型：</td>
<td><input type="text" id="claimtype" name="claimtype" value="${fn:replace(item.claimtype,'"','&quot;')}" size=50></td>
</tr>
<tr>
<td width="120" class="right">赔付状态：</td>
<td><input type="text" id="claimstatus" name="claimstatus" value="${fn:replace(item.claimstatus,'"','&quot;')}" size=50></td>
</tr>
<tr>
<td width="120" class="right">床位费：</td>
<td><input type="text" id="cwfamount" name="cwfamount" value="${fn:replace(item.cwfamount,'"','&quot;')}" size=50></td>
</tr>
<tr>
<td width="120" class="right">他方赔付金额：</td>
<td><input type="text" id="tfpfamount" name="tfpfamount" value="${fn:replace(item.tfpfamount,'"','&quot;')}" size=50></td>
</tr>
<tr>
<td width="120" class="right">公共保额：</td>
<td><input type="text" id="sqamount" name="sqamount" value="${fn:replace(item.sqamount,'"','&quot;')}" size=50></td>
</tr>
<tr>
<td width="120" class="right">扣除原因：</td>
<td><input type="text" id="dereason" name="dereason" value="${fn:replace(item.dereason,'"','&quot;')}" size=50></td>
</tr>
<tr>
<td width="120" class="right">医药费：</td>
<td><input type="text" id="yyfamount" name="yyfamount" value="${fn:replace(item.yyfamount,'"','&quot;')}" size=50></td>
</tr>
<tr>
<td width="120" class="right">精神病医药费：</td>
<td><input type="text" id="jsbyyfamount" name="jsbyyfamount" value="${fn:replace(item.jsbyyfamount,'"','&quot;')}" size=50></td>
</tr>
<tr>
<td width="120" class="right">自费医药费：</td>
<td><input type="text" id="zfyyfamount" name="zfyyfamount" value="${fn:replace(item.zfyyfamount,'"','&quot;')}" size=50></td>
</tr>
<tr>
<td width="120" class="right">自费医药费说明：</td>
<td><input type="text" id="fzyyfremark" name="fzyyfremark" value="${fn:replace(item.fzyyfremark,'"','&quot;')}" size=50></td>
</tr>
<tr>
<td width="120" class="right">医药费剔除金额：</td>
<td><input type="text" id="yyftcamount" name="yyftcamount" value="${fn:replace(item.yyftcamount,'"','&quot;')}" size=50></td>
</tr>
<tr>
<td width="120" class="right">医药费剔除说明：</td>
<td><input type="text" id="yyfremark" name="yyfremark" value="${fn:replace(item.yyfremark,'"','&quot;')}" size=50></td>
</tr>
<tr>
<td width="120" class="right">检查费：</td>
<td><input type="text" id="jcfamount" name="jcfamount" value="${fn:replace(item.jcfamount,'"','&quot;')}" size=50></td>
</tr>
<tr>
<td width="120" class="right">高科技检查费：</td>
<td><input type="text" id="gkjjcfamount" name="gkjjcfamount" value="${fn:replace(item.gkjjcfamount,'"','&quot;')}" size=50></td>
</tr>
<tr>
<td width="120" class="right">高科技检查结果：</td>
<td><input type="text" id="gkjjcjgpool" name="gkjjcjgpool" value="${fn:replace(item.gkjjcjgpool,'"','&quot;')}" size=50></td>
</tr>
<tr>
<td width="120" class="right">自费检查费：</td>
<td><input type="text" id="zfjcfamount" name="zfjcfamount" value="${fn:replace(item.zfjcfamount,'"','&quot;')}" size=50></td>
</tr>
<tr>
<td width="120" class="right">自费检查费说明：</td>
<td><input type="text" id="zfjcfremark" name="zfjcfremark" value="${fn:replace(item.zfjcfremark,'"','&quot;')}" size=50></td>
</tr>
<tr>
<td width="120" class="right">检查费剔除金额：</td>
<td><input type="text" id="jcftcamount" name="jcftcamount" value="${fn:replace(item.jcftcamount,'"','&quot;')}" size=50></td>
</tr>
<tr>
<td width="120" class="right">检查费剔除说明：</td>
<td><input type="text" id="jcftcremark" name="jcftcremark" value="${fn:replace(item.jcftcremark,'"','&quot;')}" size=50></td>
</tr>

		<tr>
			<td colspan="2">
			<input id="determineid" name="determineid" type="hidden" value="${item.determineid}"/>

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
<form id="deleteForm" action="/emDetermine.do?method=delete" method="post">
<input id="determineid" name="determineid" type="hidden" value="${item.determineid}"/>
<input id="uf_parentid" name="uf_parentid" type="hidden" value="${uf_parentid}"/>
<input id="keyword" name="keyword" type="hidden" value="${keyword}"/>
<input id="m" name="m" type="hidden" value="${m}"/>
<input id="s" name="s" type="hidden" value="${s}"/>
</form>
</html>
<script language=javascript>
function checkf() {
	if(commonForm.claimid.value=="" || commonForm.claimid.value!="" && !is_int(commonForm.claimid.value)) {alert("请正确输入案件ID！");return false;}
	if(commonForm.diseaseid.value=="" || commonForm.diseaseid.value!="" && !is_int(commonForm.diseaseid.value)) {alert("请正确输入疾病ID！");return false;}
	if(commonForm.claimtype.value=="") {alert("请正确输入赔付类型！");return false;}
	if(commonForm.claimstatus.value=="" || commonForm.claimstatus.value!="" && !is_int(commonForm.claimstatus.value)) {alert("请正确输入赔付状态！");return false;}
	if(commonForm.cwfamount.value=="" || commonForm.cwfamount.value!="" && !(is_float(commonForm.cwfamount.value) || is_int(commonForm.cwfamount.value))) {alert("请正确输入床位费！");return false;}
	if(commonForm.tfpfamount.value=="" || commonForm.tfpfamount.value!="" && !(is_float(commonForm.tfpfamount.value) || is_int(commonForm.tfpfamount.value))) {alert("请正确输入他方赔付金额！");return false;}
	if(commonForm.sqamount.value=="" || commonForm.sqamount.value!="" && !(is_float(commonForm.sqamount.value) || is_int(commonForm.sqamount.value))) {alert("请正确输入公共保额！");return false;}
	if(commonForm.dereason.value=="") {alert("请正确输入扣除原因！");return false;}
	if(commonForm.yyfamount.value=="" || commonForm.yyfamount.value!="" && !(is_float(commonForm.yyfamount.value) || is_int(commonForm.yyfamount.value))) {alert("请正确输入医药费！");return false;}
	if(commonForm.jsbyyfamount.value=="" || commonForm.jsbyyfamount.value!="" && !(is_float(commonForm.jsbyyfamount.value) || is_int(commonForm.jsbyyfamount.value))) {alert("请正确输入精神病医药费！");return false;}
	if(commonForm.zfyyfamount.value=="" || commonForm.zfyyfamount.value!="" && !(is_float(commonForm.zfyyfamount.value) || is_int(commonForm.zfyyfamount.value))) {alert("请正确输入自费医药费！");return false;}
	if(commonForm.fzyyfremark.value=="") {alert("请正确输入自费医药费说明！");return false;}
	if(commonForm.yyftcamount.value=="" || commonForm.yyftcamount.value!="" && !(is_float(commonForm.yyftcamount.value) || is_int(commonForm.yyftcamount.value))) {alert("请正确输入医药费剔除金额！");return false;}
	if(commonForm.yyfremark.value=="") {alert("请正确输入医药费剔除说明！");return false;}
	if(commonForm.jcfamount.value=="" || commonForm.jcfamount.value!="" && !(is_float(commonForm.jcfamount.value) || is_int(commonForm.jcfamount.value))) {alert("请正确输入检查费！");return false;}
	if(commonForm.gkjjcfamount.value=="" || commonForm.gkjjcfamount.value!="" && !(is_float(commonForm.gkjjcfamount.value) || is_int(commonForm.gkjjcfamount.value))) {alert("请正确输入高科技检查费！");return false;}
	if(commonForm.gkjjcjgpool.value=="" || commonForm.gkjjcjgpool.value!="" && !is_int(commonForm.gkjjcjgpool.value)) {alert("请正确输入高科技检查结果！");return false;}
	if(commonForm.zfjcfamount.value=="" || commonForm.zfjcfamount.value!="" && !(is_float(commonForm.zfjcfamount.value) || is_int(commonForm.zfjcfamount.value))) {alert("请正确输入自费检查费！");return false;}
	if(commonForm.zfjcfremark.value=="") {alert("请正确输入自费检查费说明！");return false;}
	if(commonForm.jcftcamount.value=="" || commonForm.jcftcamount.value!="" && !(is_float(commonForm.jcftcamount.value) || is_int(commonForm.jcftcamount.value))) {alert("请正确输入检查费剔除金额！");return false;}
	if(commonForm.jcftcremark.value=="") {alert("请正确输入检查费剔除说明！");return false;}

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