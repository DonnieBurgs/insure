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
<title>系统参数添加</title>
</head>
<body id="right">
<div class="operation o-h bg-f8">
	<div class="operationLeft f-l"><font style="margin-left:10px;font-size:14px; font-weight:bold; text-indent:14px; letter-spacing:2px;">系统参数添加</font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<a href="javascript:history.back();" style="margin-left:100px;"><i class="iconfont">&#xe681;</i>返回列表</a></div>
	<div class="operationRight f-r">
	</div>
</div>
<form id="commonForm" action="/emSysParam.do?method=add" method="post">
	<table class="tform">
<tr>
<td width="120" class="right">公司ID：</td>
<td><input type="text" id="companyid" name="companyid" value="" size=50></td>
</tr>
<tr>
<td width="120" class="right">默认打卡提醒次数：</td>
<td><input type="text" id="alarmnumcheckin" name="alarmnumcheckin" value="" size=50></td>
</tr>
<tr>
<td width="120" class="right">默认打卡提醒持续时间（分钟）：</td>
<td><input type="text" id="alarmtimecheckin" name="alarmtimecheckin" value="" size=50></td>
</tr>
<tr>
<td width="120" class="right">默认签到提醒次数：</td>
<td><input type="text" id="alarmnumsign" name="alarmnumsign" value="" size=50></td>
</tr>
<tr>
<td width="120" class="right">默认签到提醒持续时间（分钟）：</td>
<td><input type="text" id="alarmtimesign" name="alarmtimesign" value="" size=50></td>
</tr>
<tr>
<td width="120" class="right">默认脱岗提醒次数：</td>
<td><input type="text" id="alarmnumleave" name="alarmnumleave" value="" size=50></td>
</tr>
<tr>
<td width="120" class="right">默认脱岗提醒持续时间（分钟）：</td>
<td><input type="text" id="alarmtimeleave" name="alarmtimeleave" value="" size=50></td>
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
	if(commonForm.companyid.value=="" || commonForm.companyid.value!="" && !is_int(commonForm.companyid.value)) {alert("请正确输入公司ID！");return false;}
	if(commonForm.alarmnumcheckin.value=="" || commonForm.alarmnumcheckin.value!="" && !is_int(commonForm.alarmnumcheckin.value)) {alert("请正确输入默认打卡提醒次数！");return false;}
	if(commonForm.alarmtimecheckin.value=="" || commonForm.alarmtimecheckin.value!="" && !is_int(commonForm.alarmtimecheckin.value)) {alert("请正确输入默认打卡提醒持续时间（分钟）！");return false;}
	if(commonForm.alarmnumsign.value=="" || commonForm.alarmnumsign.value!="" && !is_int(commonForm.alarmnumsign.value)) {alert("请正确输入默认签到提醒次数！");return false;}
	if(commonForm.alarmtimesign.value=="" || commonForm.alarmtimesign.value!="" && !is_int(commonForm.alarmtimesign.value)) {alert("请正确输入默认签到提醒持续时间（分钟）！");return false;}
	if(commonForm.alarmnumleave.value=="" || commonForm.alarmnumleave.value!="" && !is_int(commonForm.alarmnumleave.value)) {alert("请正确输入默认脱岗提醒次数！");return false;}
	if(commonForm.alarmtimeleave.value=="" || commonForm.alarmtimeleave.value!="" && !is_int(commonForm.alarmtimeleave.value)) {alert("请正确输入默认脱岗提醒持续时间（分钟）！");return false;}

	document.getElementById("commonForm").submit() ;
  
}


</script>