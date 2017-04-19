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
<title>赔付信息添加</title>
</head>
<body id="right">
<div class="operation o-h bg-f8">
	<div class="operationLeft f-l"><font style="margin-left:10px;font-size:14px; font-weight:bold; text-indent:14px; letter-spacing:2px;">赔付信息添加</font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<a href="javascript:history.back();" style="margin-left:100px;"><i class="iconfont">&#xe681;</i>返回列表</a></div>
	<div class="operationRight f-r">
	</div>
</div>
<form id="commonForm" action="/emClaimSettlement.do?method=add" method="post">
	<table class="tform">
<tr>
<td width="120" class="right"><font color="red">*</font>发票ID：</td>
<td><input type="text" id="receiptid" name="receiptid" value="" size=50></td>
</tr>
<tr>
<td width="120" class="right"><font color="red">*</font>拒赔金额：</td>
<td><input type="text" id="jpamount" name="jpamount" value="" size=50></td>
</tr>
<tr>
<td width="120" class="right"><font color="red">*</font>赔付比例：</td>
<td><input type="text" id="pfrate" name="pfrate" value="" size=50></td>
</tr>
<tr>
<td width="120" class="right"><font color="red">*</font>医药费赔付金额：</td>
<td><input type="text" id="yyfpfje" name="yyfpfje" value="" size=50></td>
</tr>
<tr>
<td width="120" class="right"><font color="red">*</font>检查费赔付金额：</td>
<td><input type="text" id="jcfpfje" name="jcfpfje" value="" size=50></td>
</tr>
<tr>
<td width="120" class="right"><font color="red">*</font>床位费赔付金额：</td>
<td><input type="text" id="cwfpfje" name="cwfpfje" value="" size=50></td>
</tr>
<tr>
<td width="120" class="right"><font color="red">*</font>总赔付金额：</td>
<td><input type="text" id="pfamount" name="pfamount" value="" size=50></td>
</tr>
<tr>
<td width="120" class="right"><font color="red">*</font>备注：</td>
<td><input type="text" id="yyfremark" name="yyfremark" value="" size=50></td>
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
	if(commonForm.receiptid.value=="" || commonForm.receiptid.value!="" && !is_int(commonForm.receiptid.value)) {alert("请正确输入发票ID！");return false;}
	if(commonForm.jpamount.value=="" || commonForm.jpamount.value!="" && !(is_float(commonForm.jpamount.value) || is_int(commonForm.jpamount.value))) {alert("请正确输入拒赔金额！");return false;}
	if(commonForm.pfrate.value=="" || commonForm.pfrate.value!="" && !(is_float(commonForm.pfrate.value) || is_int(commonForm.pfrate.value))) {alert("请正确输入赔付比例！");return false;}
	if(commonForm.yyfpfje.value=="" || commonForm.yyfpfje.value!="" && !(is_float(commonForm.yyfpfje.value) || is_int(commonForm.yyfpfje.value))) {alert("请正确输入医药费赔付金额！");return false;}
	if(commonForm.jcfpfje.value=="") {alert("请正确输入检查费赔付金额！");return false;}
	if(commonForm.cwfpfje.value=="" || commonForm.cwfpfje.value!="" && !(is_float(commonForm.cwfpfje.value) || is_int(commonForm.cwfpfje.value))) {alert("请正确输入床位费赔付金额！");return false;}
	if(commonForm.pfamount.value=="" || commonForm.pfamount.value!="" && !(is_float(commonForm.pfamount.value) || is_int(commonForm.pfamount.value))) {alert("请正确输入总赔付金额！");return false;}
	if(commonForm.yyfremark.value=="") {alert("请正确输入备注！");return false;}

	document.getElementById("commonForm").submit() ;
  
}


</script>