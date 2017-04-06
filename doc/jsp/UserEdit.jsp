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
<title>会员<c:if test="${auth82 eq '1'}">修改</c:if><c:if test="${auth82 ne '1'}">查看</c:if></title>
</head>
<body id="right">
<div class="operation o-h bg-f8">
	<div class="operationLeft f-l"><font style="margin-left:10px;font-size:14px; font-weight:bold; text-indent:14px; letter-spacing:2px;">会员<c:if test="${auth82 eq '1'}">修改</c:if><c:if test="${auth82 ne '1'}">查看</c:if></font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<a href="javascript:history.back();" style="margin-left:100px;"><i class="iconfont">&#xe63d;</i>返回列表</a></div>
	<div class="operationRight f-r">
	</div>
</div>
<form id="commonForm" action="/emUser.do?method=update" method="post">
	<table class="tform">
<tr>
<td width="120" class="right">账号：</td>
<td><input type="text" id="account" name="account" value="${fn:replace(item.account,'"','&quot;')}" size=50></td>
</tr>
<tr>
<td width="120" class="right">密码：</td>
<td><input type="text" id="password" name="password" value="${fn:replace(item.password,'"','&quot;')}" size=50></td>
</tr>
<tr>
<td width="120" class="right">姓名：</td>
<td><input type="text" id="username" name="username" value="${fn:replace(item.username,'"','&quot;')}" size=50></td>
</tr>
<tr>
<td width="120" class="right">昵称：</td>
<td><input type="text" id="nickname" name="nickname" value="${fn:replace(item.nickname,'"','&quot;')}" size=50></td>
</tr>
<tr>
<td width="120" class="right">工号：</td>
<td><input type="text" id="userno" name="userno" value="${fn:replace(item.userno,'"','&quot;')}" size=50></td>
</tr>
<tr>
<td width="120" class="right">公司ID：</td>
<td><input type="text" id="companyid" name="companyid" value="${fn:replace(item.companyid,'"','&quot;')}" size=50></td>
</tr>
<tr>
<td width="120" class="right">会员类别：</td>
<td><input type="text" id="usertype" name="usertype" value="${fn:replace(item.usertype,'"','&quot;')}" size=50></td>
</tr>
<tr>
<td width="120" class="right">推荐人ID：</td>
<td><input type="text" id="parentid" name="parentid" value="${fn:replace(item.parentid,'"','&quot;')}" size=50></td>
</tr>
<tr>
<td width="120" class="right">性别：</td>
<td><input type="text" id="sex" name="sex" value="${fn:replace(item.sex,'"','&quot;')}" size=50></td>
</tr>
<tr>
<td width="120" class="right">年龄：</td>
<td><input type="text" id="age" name="age" value="${fn:replace(item.age,'"','&quot;')}" size=50></td>
</tr>
<tr>
<td width="120" class="right">身份证：</td>
<td><input type="text" id="identity" name="identity" value="${fn:replace(item.identity,'"','&quot;')}" size=50></td>
</tr>
<tr>
<td width="120" class="right">手机：</td>
<td><input type="text" id="mobile" name="mobile" value="${fn:replace(item.mobile,'"','&quot;')}" size=50></td>
</tr>
<tr>
<td width="120" class="right">是否有效：</td>
<td><input type="text" id="isvalid" name="isvalid" value="${fn:replace(item.isvalid,'"','&quot;')}" size=50></td>
</tr>
<tr>
<td width="120" class="right">照片：</td>
<td><a class="mya" href="javascript:f_photo0.click();"><img id="imgphoto0" src="<c:if test="${empty item.photo}">/resources/images/folder2.png</c:if><c:if test="${not empty item.photo}">/upload/images/${item.photo}</c:if>"></a></td>
</tr>
<tr>
<td width="120" class="right">是否关联微信：</td>
<td><input type="text" id="isregist" name="isregist" value="${fn:replace(item.isregist,'"','&quot;')}" size=50></td>
</tr>
<tr>
<td width="120" class="right">微信Openid：</td>
<td><input type="text" id="openid" name="openid" value="${fn:replace(item.openid,'"','&quot;')}" size=50></td>
</tr>
<tr>
<td width="120" class="right">二维码：</td>
<td><input type="text" id="qrurl" name="qrurl" value="${fn:replace(item.qrurl,'"','&quot;')}" size=50></td>
</tr>
<tr>
<td width="120" class="right">开通时间：</td>
<td><input type="text" id="createdate" name="createdate" readonly="readonly" value="${item.createdate_}" onclick="WdatePicker({maxDate:'2018-12-31', dateFmt:'yyyy-MM-dd'})" style="width: 70px;"/></td>
</tr>
<tr>
<td width="120" class="right">家庭电话：</td>
<td><input type="text" id="tel" name="tel" value="${fn:replace(item.tel,'"','&quot;')}" size=50></td>
</tr>
<tr>
<td width="120" class="right">地址：</td>
<td><input type="text" id="addr" name="addr" value="${fn:replace(item.addr,'"','&quot;')}" size=50></td>
</tr>
<tr>
<td width="120" class="right">备注：</td>
<td><input type="text" id="note" name="note" value="${fn:replace(item.note,'"','&quot;')}" size=50></td>
</tr>
<tr>
<td width="120" class="right">余额：</td>
<td><input type="text" id="balance" name="balance" value="${fn:replace(item.balance,'"','&quot;')}" size=50></td>
</tr>
<tr>
<td width="120" class="right">推送token：</td>
<td><input type="text" id="devicetoken" name="devicetoken" value="${fn:replace(item.devicetoken,'"','&quot;')}" size=50></td>
</tr>
<tr>
<td width="120" class="right">token刷新时间：</td>
<td><input type="text" id="devicetokendate" name="devicetokendate" readonly="readonly" value="${item.devicetokendate_}" onclick="WdatePicker({maxDate:'2018-12-31', dateFmt:'yyyy-MM-dd'})" style="width: 70px;"/></td>
</tr>
<tr>
<td width="120" class="right">坐标：</td>
<td><input type="text" id="lng" name="lng" value="${fn:replace(item.lng,'"','&quot;')}" size=50></td>
</tr>
<tr>
<td width="120" class="right">坐标：</td>
<td><input type="text" id="lat" name="lat" value="${fn:replace(item.lat,'"','&quot;')}" size=50></td>
</tr>
<tr>
<td width="120" class="right">坐标更新时间：</td>
<td><input type="text" id="latlngdate" name="latlngdate" readonly="readonly" value="${item.latlngdate_}" onclick="WdatePicker({maxDate:'2018-12-31', dateFmt:'yyyy-MM-dd'})" style="width: 70px;"/></td>
</tr>
<tr>
<td width="120" class="right">设备ID：</td>
<td><input type="text" id="deviceid" name="deviceid" value="${fn:replace(item.deviceid,'"','&quot;')}" size=50></td>
</tr>
<tr>
<td width="120" class="right">顺序号：</td>
<td><input type="text" id="seq" name="seq" value="${fn:replace(item.seq,'"','&quot;')}" size=50></td>
</tr>
<tr>
<td width="120" class="right">权限ID：</td>
<td><input type="text" id="authorityid" name="authorityid" value="${fn:replace(item.authorityid,'"','&quot;')}" size=50></td>
</tr>
<tr>
<td width="120" class="right">是否删除：</td>
<td><input type="text" id="isdelete" name="isdelete" value="${fn:replace(item.isdelete,'"','&quot;')}" size=50></td>
</tr>

		<tr>
			<td colspan="2">
			<input id="userid" name="userid" type="hidden" value="${item.userid}"/>
<input type="hidden" id="photo0" name="photo0"/>
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
<input type="file" id="f_photo0" name="f_photo0" onchange='_compress(this,$("#photo0"),$("#imgphoto0"));' style="width:0px;"/>
</body>
<form id="deleteForm" action="/emUser.do?method=delete" method="post">
<input id="userid" name="userid" type="hidden" value="${item.userid}"/>
<input id="uf_parentid" name="uf_parentid" type="hidden" value="${uf_parentid}"/>
<input id="keyword" name="keyword" type="hidden" value="${keyword}"/>
<input id="m" name="m" type="hidden" value="${m}"/>
<input id="s" name="s" type="hidden" value="${s}"/>
</form>
</html>
<script language=javascript>
function checkf() {
	if(commonForm.account.value=="") {alert("请正确输入账号！");return false;}
	if(commonForm.password.value=="") {alert("请正确输入密码！");return false;}
	if(commonForm.username.value=="") {alert("请正确输入姓名！");return false;}
	if(commonForm.nickname.value=="") {alert("请正确输入昵称！");return false;}
	if(commonForm.userno.value=="") {alert("请正确输入工号！");return false;}
	if(commonForm.companyid.value=="" || commonForm.companyid.value!="" && !is_int(commonForm.companyid.value)) {alert("请正确输入公司ID！");return false;}
	if(commonForm.usertype.value=="" || commonForm.usertype.value!="" && !is_int(commonForm.usertype.value)) {alert("请正确输入会员类别！");return false;}
	if(commonForm.parentid.value=="" || commonForm.parentid.value!="" && !is_int(commonForm.parentid.value)) {alert("请正确输入推荐人ID！");return false;}
	if(commonForm.sex.value=="") {alert("请正确输入性别！");return false;}
	if(commonForm.age.value=="") {alert("请正确输入年龄！");return false;}
	if(commonForm.identity.value=="") {alert("请正确输入身份证！");return false;}
	if(commonForm.mobile.value=="") {alert("请正确输入手机！");return false;}
	if(commonForm.isvalid.value=="" || commonForm.isvalid.value!="" && !is_int(commonForm.isvalid.value)) {alert("请正确输入是否有效！");return false;}
	if(commonForm.isregist.value=="" || commonForm.isregist.value!="" && !is_int(commonForm.isregist.value)) {alert("请正确输入是否关联微信！");return false;}
	if(commonForm.openid.value=="") {alert("请正确输入微信Openid！");return false;}
	if(commonForm.qrurl.value=="") {alert("请正确输入二维码！");return false;}
	if(commonForm.createdate.value=="") {alert("请正确输入开通时间！");return false;}
	if(commonForm.tel.value=="") {alert("请正确输入家庭电话！");return false;}
	if(commonForm.addr.value=="") {alert("请正确输入地址！");return false;}
	if(commonForm.note.value=="") {alert("请正确输入备注！");return false;}
	if(commonForm.balance.value=="" || commonForm.balance.value!="" && !(is_float(commonForm.balance.value) || is_int(commonForm.balance.value))) {alert("请正确输入余额！");return false;}
	if(commonForm.devicetoken.value=="") {alert("请正确输入推送token！");return false;}
	if(commonForm.devicetokendate.value=="") {alert("请正确输入token刷新时间！");return false;}
	if(commonForm.lng.value=="" || commonForm.lng.value!="" && !(is_float(commonForm.lng.value) || is_int(commonForm.lng.value))) {alert("请正确输入坐标！");return false;}
	if(commonForm.lat.value=="" || commonForm.lat.value!="" && !(is_float(commonForm.lat.value) || is_int(commonForm.lat.value))) {alert("请正确输入坐标！");return false;}
	if(commonForm.latlngdate.value=="") {alert("请正确输入坐标更新时间！");return false;}
	if(commonForm.deviceid.value=="") {alert("请正确输入设备ID！");return false;}
	if(commonForm.seq.value=="" || commonForm.seq.value!="" && !is_int(commonForm.seq.value)) {alert("请正确输入顺序号！");return false;}
	if(commonForm.authorityid.value=="" || commonForm.authorityid.value!="" && !is_int(commonForm.authorityid.value)) {alert("请正确输入权限ID！");return false;}
	if(commonForm.isdelete.value=="" || commonForm.isdelete.value!="" && !is_int(commonForm.isdelete.value)) {alert("请正确输入是否删除！");return false;}

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