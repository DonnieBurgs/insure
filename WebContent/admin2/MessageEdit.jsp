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
<title>消息<c:if test="${auth52 eq '1'}">修改</c:if><c:if test="${auth52 ne '1'}">查看</c:if></title>
</head>
<body id="right">
<div class="operation o-h bg-f8">
	<div class="operationLeft f-l"><font style="margin-left:10px;font-size:14px; font-weight:bold; text-indent:14px; letter-spacing:2px;">消息<c:if test="${auth52 eq '1'}">修改</c:if><c:if test="${auth52 ne '1'}">查看</c:if></font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<a href="javascript:history.back();" style="margin-left:100px;"><i class="iconfont">&#xe63d;</i>返回列表</a></div>
	<div class="operationRight f-r">
	</div>
</div>
<form id="commonForm" action="/emMessage.do?method=update" method="post">
	<table class="tform">
<tr>
<td width="120" class="right">消息会话ID：</td>
<td><input type="text" id="messagesessionid" name="messagesessionid" value="${fn:replace(item.messagesessionid,'"','&quot;')}" size=50></td>
</tr>
<tr>
<td width="120" class="right">群发ID：</td>
<td><input type="text" id="groupid" name="groupid" value="${fn:replace(item.groupid,'"','&quot;')}" size=50></td>
</tr>
<tr>
<td width="120" class="right">消息类型ID：</td>
<td><input type="text" id="messagetypeid" name="messagetypeid" value="${fn:replace(item.messagetypeid,'"','&quot;')}" size=50></td>
</tr>
<tr>
<td width="120" class="right">发送人ID：</td>
<td><input type="text" id="sender" name="sender" value="${fn:replace(item.sender,'"','&quot;')}" size=50></td>
</tr>
<tr>
<td width="120" class="right">接收人ID：</td>
<td><input type="text" id="receiver" name="receiver" value="${fn:replace(item.receiver,'"','&quot;')}" size=50></td>
</tr>
<tr>
<td width="120" class="right">消息内容：</td>
<td><input type="text" id="content" name="content" value="${fn:replace(item.content,'"','&quot;')}" size=50></td>
</tr>
<tr>
<td width="120" class="right">群发序列：</td>
<td><input type="text" id="groupseq" name="groupseq" value="${fn:replace(item.groupseq,'"','&quot;')}" size=50></td>
</tr>
<tr>
<td width="120" class="right">是否新消息：</td>
<td><input type="text" id="senderread" name="senderread" value="${fn:replace(item.senderread,'"','&quot;')}" size=50></td>
</tr>
<tr>
<td width="120" class="right">是否新消息：</td>
<td><input type="text" id="receiverread" name="receiverread" value="${fn:replace(item.receiverread,'"','&quot;')}" size=50></td>
</tr>
<tr>
<td width="120" class="right">是否删除：</td>
<td><input type="text" id="senderdelete" name="senderdelete" value="${fn:replace(item.senderdelete,'"','&quot;')}" size=50></td>
</tr>
<tr>
<td width="120" class="right">是否删除：</td>
<td><input type="text" id="receiverdelete" name="receiverdelete" value="${fn:replace(item.receiverdelete,'"','&quot;')}" size=50></td>
</tr>
<tr>
<td width="120" class="right">生成时间：</td>
<td><input type="text" id="createdate" name="createdate" readonly="readonly" value="${item.createdate_}" onclick="WdatePicker({maxDate:'2018-12-31', dateFmt:'yyyy-MM-dd'})" style="width: 70px;"/></td>
</tr>

		<tr>
			<td colspan="2">
			<input id="messageid" name="messageid" type="hidden" value="${item.messageid}"/>

				<input id="uf_parentid" name="uf_parentid" type="hidden" value="${uf_parentid}"/>
				<input id="keyword" name="keyword" type="hidden" value="${keyword}"/>
				<input id="m" name="m" type="hidden" value="${m}"/>
				<input id="s" name="s" type="hidden" value="${s}"/>
				<c:if test="${auth52 eq '1'}">
				<input type="button" class="btn1" value=" 提  交 " onclick="checkf()"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="button" class="btn1" value=" 删  除 " onclick="checkdelete()"/>
				</c:if>
				<c:if test="${auth52 ne '1'}">
				<input type="button" class="btn1" value=" 返  回 " onclick="history.back();"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				</c:if>
			</td>
		</tr>
	</table>
</form>

</body>
<form id="deleteForm" action="/emMessage.do?method=delete" method="post">
<input id="messageid" name="messageid" type="hidden" value="${item.messageid}"/>
<input id="uf_parentid" name="uf_parentid" type="hidden" value="${uf_parentid}"/>
<input id="keyword" name="keyword" type="hidden" value="${keyword}"/>
<input id="m" name="m" type="hidden" value="${m}"/>
<input id="s" name="s" type="hidden" value="${s}"/>
</form>
</html>
<script language=javascript>
function checkf() {
	if(commonForm.messagesessionid.value=="" || commonForm.messagesessionid.value!="" && !is_int(commonForm.messagesessionid.value)) {alert("请正确输入消息会话ID！");return false;}
	if(commonForm.groupid.value=="" || commonForm.groupid.value!="" && !is_int(commonForm.groupid.value)) {alert("请正确输入群发ID！");return false;}
	if(commonForm.messagetypeid.value=="" || commonForm.messagetypeid.value!="" && !is_int(commonForm.messagetypeid.value)) {alert("请正确输入消息类型ID！");return false;}
	if(commonForm.sender.value=="" || commonForm.sender.value!="" && !is_int(commonForm.sender.value)) {alert("请正确输入发送人ID！");return false;}
	if(commonForm.receiver.value=="" || commonForm.receiver.value!="" && !is_int(commonForm.receiver.value)) {alert("请正确输入接收人ID！");return false;}
	if(commonForm.content.value=="") {alert("请正确输入消息内容！");return false;}
	if(commonForm.groupseq.value=="" || commonForm.groupseq.value!="" && !is_int(commonForm.groupseq.value)) {alert("请正确输入群发序列！");return false;}
	if(commonForm.senderread.value=="" || commonForm.senderread.value!="" && !is_int(commonForm.senderread.value)) {alert("请正确输入是否新消息！");return false;}
	if(commonForm.receiverread.value=="" || commonForm.receiverread.value!="" && !is_int(commonForm.receiverread.value)) {alert("请正确输入是否新消息！");return false;}
	if(commonForm.senderdelete.value=="" || commonForm.senderdelete.value!="" && !is_int(commonForm.senderdelete.value)) {alert("请正确输入是否删除！");return false;}
	if(commonForm.receiverdelete.value=="" || commonForm.receiverdelete.value!="" && !is_int(commonForm.receiverdelete.value)) {alert("请正确输入是否删除！");return false;}
	if(commonForm.createdate.value=="") {alert("请正确输入生成时间！");return false;}

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