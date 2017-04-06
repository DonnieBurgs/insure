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
<title>个人保单添加</title>
</head>
<body id="right">
<div class="operation o-h bg-f8">
	<div class="operationLeft f-l"><font style="margin-left:10px;font-size:14px; font-weight:bold; text-indent:14px; letter-spacing:2px;">个人保单添加</font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<a href="javascript:history.back();" style="margin-left:100px;"><i class="iconfont">&#xe681;</i>返回列表</a></div>
	<div class="operationRight f-r">
	</div>
</div>
<form id="commonForm" action="/emInsurerPolicy.do?method=add" method="post">
	<table class="tform">
<tr>
<td width="120" class="right">方案：</td>
<td><input type="text" id="policyid" name="policyid" value="" size=50></td>
</tr>
<tr>
<td width="120" class="right">被保险人：</td>
<td><input type="text" id="insuredid" name="insuredid" value="" size=50></td>
</tr>
<tr>
<td width="120" class="right">主被保险人：</td>
<td><input type="text" id="attachedtoid" name="attachedtoid" value="" size=50></td>
</tr>
<tr>
<td width="120" class="right">保险期间（起）：</td>
<td><input type="text" id="periodbegin" name="periodbegin" readonly="readonly" value="" onclick="WdatePicker({maxDate:'2018-12-31', dateFmt:'yyyy-MM-dd'})" style="width: 70px;"/></td>
</tr>
<tr>
<td width="120" class="right">保险期间（止）：</td>
<td><input type="text" id="periodend" name="periodend" readonly="readonly" value="" onclick="WdatePicker({maxDate:'2018-12-31', dateFmt:'yyyy-MM-dd'})" style="width: 70px;"/></td>
</tr>
<tr>
<td width="120" class="right">保险状态：</td>
<td><input type="text" id="policystate" name="policystate" value="" size=50></td>
</tr>
<tr>
<td width="120" class="right">医保状态：</td>
<td><input type="text" id="shistate" name="shistate" value="" size=50></td>
</tr>
<tr>
<td width="120" class="right">医保地点：</td>
<td><input type="text" id="shiarea" name="shiarea" value="" size=50></td>
</tr>
<tr>
<td width="120" class="right">工作地：</td>
<td><input type="text" id="joblocal" name="joblocal" value="" size=50></td>
</tr>
<tr>
<td width="120" class="right">关系：</td>
<td><input type="text" id="relation" name="relation" value="" size=50></td>
</tr>
<tr>
<td width="120" class="right">保险编号：</td>
<td><input type="text" id="clientid" name="clientid" value="" size=50></td>
</tr>
<tr>
<td width="120" class="right">雇员编号：</td>
<td><input type="text" id="jobnumber" name="jobnumber" value="" size=50></td>
</tr>
<tr>
<td width="120" class="right">开户行：</td>
<td><input type="text" id="bankname" name="bankname" value="" size=50></td>
</tr>
<tr>
<td width="120" class="right">账户名：</td>
<td><input type="text" id="accountname" name="accountname" value="" size=50></td>
</tr>
<tr>
<td width="120" class="right">银行账号：</td>
<td><input type="text" id="accountnumber" name="accountnumber" value="" size=50></td>
</tr>
<tr>
<td width="120" class="right">保险编号：</td>
<td><input type="text" id="clientid" name="clientid" value="" size=50></td>
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
	if(commonForm.policyid.value=="" || commonForm.policyid.value!="" && !is_int(commonForm.policyid.value)) {alert("请正确输入方案！");return false;}
	if(commonForm.insuredid.value=="" || commonForm.insuredid.value!="" && !is_int(commonForm.insuredid.value)) {alert("请正确输入被保险人！");return false;}
	if(commonForm.attachedtoid.value=="" || commonForm.attachedtoid.value!="" && !is_int(commonForm.attachedtoid.value)) {alert("请正确输入主被保险人！");return false;}
	if(commonForm.periodbegin.value=="") {alert("请正确输入保险期间（起）！");return false;}
	if(commonForm.periodend.value=="") {alert("请正确输入保险期间（止）！");return false;}
	if(commonForm.policystate.value=="") {alert("请正确输入保险状态！");return false;}
	if(commonForm.shistate.value=="") {alert("请正确输入医保状态！");return false;}
	if(commonForm.shiarea.value=="") {alert("请正确输入医保地点！");return false;}
	if(commonForm.joblocal.value=="") {alert("请正确输入工作地！");return false;}
	if(commonForm.relation.value=="") {alert("请正确输入关系！");return false;}
	if(commonForm.clientid.value=="") {alert("请正确输入保险编号！");return false;}
	if(commonForm.jobnumber.value=="") {alert("请正确输入雇员编号！");return false;}
	if(commonForm.bankname.value=="") {alert("请正确输入开户行！");return false;}
	if(commonForm.accountname.value=="") {alert("请正确输入账户名！");return false;}
	if(commonForm.accountnumber.value=="") {alert("请正确输入银行账号！");return false;}
	if(commonForm.clientid.value=="") {alert("请正确输入保险编号！");return false;}

	document.getElementById("commonForm").submit() ;
  
}


</script>