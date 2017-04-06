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
<title>联盟链接<c:if test="${auth72 eq '1'}">修改</c:if><c:if test="${auth72 ne '1'}">查看</c:if></title>
</head>
<body id="right">
<div class="operation o-h bg-f8">
	<div class="operationLeft f-l"><font style="margin-left:10px;font-size:14px; font-weight:bold; text-indent:14px; letter-spacing:2px;">联盟链接<c:if test="${auth72 eq '1'}">修改</c:if><c:if test="${auth72 ne '1'}">查看</c:if></font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<a href="javascript:history.back();" style="margin-left:100px;"><i class="iconfont">&#xe63d;</i>返回列表</a></div>
	<div class="operationRight f-r">
	</div>
</div>
<form id="commonForm" action="/emUnionLink.do?method=update" method="post">
	<table class="tform">
<tr>
<td width="120" class="right">图片：</td>
<td><a class="mya" href="javascript:f_photo0.click();"><img id="imgphoto0" src="<c:if test="${empty item.photo}">/resources/images/folder2.png</c:if><c:if test="${not empty item.photo}">/upload/images/${item.photo}</c:if>" style="max-width:500px;"></a></td>
</tr>
<tr>
<td width="120" class="right">链接：</td>
<td><input type="text" id="url" name="url" value="${fn:replace(item.url,'"','&quot;')}" size=50></td>
</tr>
<tr>
<td width="120" class="right">代码：</td>
<td><textarea id="code" name="code" style="width:500px;">${fn:replace(item.code,'"','&quot;')}</textarea></td>
</tr>
<tr>
<td width="120" class="right">备注：</td>
<td><input type="text" id="note" name="note" value="${fn:replace(item.note,'"','&quot;')}" size=50></td>
</tr>
<tr>
<td width="120" class="right">顺序号：</td>
<td><input type="text" id="seq" name="seq" value="${fn:replace(item.seq,'"','&quot;')}" size=50></td>
</tr>

		<tr>
			<td colspan="2">
			<input id="unionlinkid" name="unionlinkid" type="hidden" value="${item.unionlinkid}"/>
<input type="hidden" id="photo0" name="photo0"/>
				<input id="uf_parentid" name="uf_parentid" type="hidden" value="${uf_parentid}"/>
				<input id="keyword" name="keyword" type="hidden" value="${keyword}"/>
				<input id="m" name="m" type="hidden" value="${m}"/>
				<input id="s" name="s" type="hidden" value="${s}"/>
				<c:if test="${auth72 eq '1'}">
				<input type="button" class="btn1" value=" 提  交 " onclick="checkf()"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="button" class="btn1" value=" 删  除 " onclick="checkdelete()"/>
				</c:if>
				<c:if test="${auth72 ne '1'}">
				<input type="button" class="btn1" value=" 返  回 " onclick="history.back();"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				</c:if>
			</td>
		</tr>
	</table>
</form>
<input type="file" id="f_photo0" name="f_photo0" onchange='_compress(this,$("#photo0"),$("#imgphoto0"));' style="width:0px;"/>
</body>
<form id="deleteForm" action="/emUnionLink.do?method=delete" method="post">
<input id="unionlinkid" name="unionlinkid" type="hidden" value="${item.unionlinkid}"/>
<input id="uf_parentid" name="uf_parentid" type="hidden" value="${uf_parentid}"/>
<input id="keyword" name="keyword" type="hidden" value="${keyword}"/>
<input id="m" name="m" type="hidden" value="${m}"/>
<input id="s" name="s" type="hidden" value="${s}"/>
</form>
</html>
<script language=javascript>
function checkf() {
	if(commonForm.url.value=="") {alert("请正确输入链接！");return false;}
	if(commonForm.code.value=="") {alert("请正确输入代码！");return false;}
	if(commonForm.seq.value=="" || commonForm.seq.value!="" && !is_int(commonForm.seq.value)) {alert("请正确输入顺序号！");return false;}

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