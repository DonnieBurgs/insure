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
<title>新闻<c:if test="${auth62 eq '1'}">修改</c:if><c:if test="${auth62 ne '1'}">查看</c:if></title>
</head>
<body id="right">
<div class="operation o-h bg-f8">
	<div class="operationLeft f-l"><font style="margin-left:10px;font-size:14px; font-weight:bold; text-indent:14px; letter-spacing:2px;">新闻<c:if test="${auth62 eq '1'}">修改</c:if><c:if test="${auth62 ne '1'}">查看</c:if></font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<a href="javascript:history.back();" style="margin-left:100px;"><i class="iconfont">&#xe63d;</i>返回列表</a></div>
	<div class="operationRight f-r">
	</div>
</div>
<form id="commonForm" action="/emNews.do?method=update" method="post">
	<table class="tform">
<tr>
<td width="120" class="right">标题：</td>
<td><input type="text" id="title" name="title" value="${fn:replace(item.title,'"','&quot;')}" size=50></td>
</tr>
<tr>
<td width="120" class="right">内容：</td>
<td><input type="text" id="content" name="content" value="${fn:replace(item.content,'"','&quot;')}" size=50></td>
</tr>
<tr>
<td width="120" class="right">图片：</td>
<td><a class="mya" href="javascript:f_photo0.click();"><img id="imgphoto0" src="<c:if test="${empty item.photo}">/resources/images/folder2.png</c:if><c:if test="${not empty item.photo}">/images/${item.photo}</c:if>"></a></td>
</tr>
<tr>
<td width="120" class="right">发布日期：</td>
<td><input type="text" id="createdate" name="createdate" readonly="readonly" value="${item.createdate_}" onclick="WdatePicker({maxDate:'2018-12-31', dateFmt:'yyyy-MM-dd'})" style="width: 70px;"/></td>
</tr>
<tr>
<td width="120" class="right">有效日期：</td>
<td><input type="text" id="expdate" name="expdate" readonly="readonly" value="${item.expdate_}" onclick="WdatePicker({maxDate:'2018-12-31', dateFmt:'yyyy-MM-dd'})" style="width: 70px;"/></td>
</tr>
<tr>
<td width="120" class="right">是否显示：</td>
<td>
<select name="isshow">
<option value="1"<c:if test="${item.isshow eq '1'}"> selected</c:if>>显示
<option value="0"<c:if test="${item.isshow eq '0'}"> selected</c:if>>不显示
</select>
</td>
</tr>
<tr>
<td width="120" class="right">发布人：</td>
<td>${item.userid}</td>
</tr>

		<tr>
			<td colspan="2">
			<input id="newsid" name="newsid" type="hidden" value="${item.newsid}"/>
<input type="hidden" id="photo0" name="photo0"/>
				<input id="uf_parentid" name="uf_parentid" type="hidden" value="${uf_parentid}"/>
				<input id="keyword" name="keyword" type="hidden" value="${keyword}"/>
				<input id="m" name="m" type="hidden" value="${m}"/>
				<input id="s" name="s" type="hidden" value="${s}"/>
				<c:if test="${auth62 eq '1'}">
				<input type="button" class="btn1" value=" 提  交 " onclick="checkf()"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="button" class="btn1" value=" 删  除 " onclick="checkdelete()"/>
				</c:if>
				<c:if test="${auth62 ne '1'}">
				<input type="button" class="btn1" value=" 返  回 " onclick="history.back();"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				</c:if>
			</td>
		</tr>
	</table>
</form>
<input type="file" id="f_photo0" name="f_photo0" onchange='_compress(this,$("#photo0"),$("#imgphoto0"));' style="width:0px;"/>
</body>
<form id="deleteForm" action="/emNews.do?method=delete" method="post">
<input id="newsid" name="newsid" type="hidden" value="${item.newsid}"/>
<input id="uf_parentid" name="uf_parentid" type="hidden" value="${uf_parentid}"/>
<input id="keyword" name="keyword" type="hidden" value="${keyword}"/>
<input id="m" name="m" type="hidden" value="${m}"/>
<input id="s" name="s" type="hidden" value="${s}"/>
</form>
</html>
<script language=javascript>
function checkf() {
	if(commonForm.title.value=="") {alert("请正确输入标题！");return false;}
	if(commonForm.content.value=="") {alert("请正确输入内容！");return false;}
	if(commonForm.createdate.value=="") {alert("请正确输入发布日期！");return false;}
	if(commonForm.expdate.value=="") {alert("请正确输入有效日期！");return false;}

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