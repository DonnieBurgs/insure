<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="dict" uri="/WEB-INF/dict_tag.tld" %>
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
<title>保全导入</title>
</head>
<body id="right">
<div class="operation o-h bg-f8">
	<div class="operationLeft f-l"><font style="margin-left:10px;font-size:14px; font-weight:bold; text-indent:14px; letter-spacing:2px;">保全导入</font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		</div>
	<div class="operationRight f-r">
	</div>
</div>
<form id="commonForm" action="/emDataExport.do?method=add"  method="post">
	<table class="tform">
<tr>
<td>案卷号</td><td><input type="text" name="claimarchivenumber"></td>
</tr>
<tr>
<td>收单日期</td><td><input type="text" id="acceptdate" name="acceptdate" readonly="readonly" value="" onclick="WdatePicker({maxDate:'2018-12-31', dateFmt:'yyyy-MM-dd'})" style="width: 70px;"/></td>
</tr>
<tr>
<td>案件编号</td><td><input type="text" name="serialnumber"></td>
</tr>
<tr>
<td>被保人姓名</td><td><input type="text" name="insuredname"></td>
</tr>
<tr>
<td>案件类型</td><td><select id="claimtype" name="claimtype">
		<option value="">请选择</option>
		<c:forEach var="it" items="${claimtypeMap }"><option value="${it.key }">${it.value}</option></c:forEach>
		</select></td>
</tr>
		<tr>
			<td colspan="2">
<input type="hidden" id="photo0" name="photo0"/>
				<input id="uf_parentid" name="uf_parentid" type="hidden" value="${uf_parentid}"/>
				<input id="keyword" name="keyword" type="hidden" value="${keyword}"/>
				<input id="m" name="m" type="hidden" value="${m}"/>
				<input id="s" name="s" type="hidden" value="${s}"/>
				<input type="button" class="btn1" value=" 导出数据 " onclick="checkf()"/>
			</td>
		</tr>
	</table>
</form>
<input type="file" id="f_photo0" name="f_photo0" onchange='_compress(this,$("#photo0"),$("#imgphoto0"));' style="width:0px;"/>
</body>
</html>
<script language=javascript>
function checkf() {

	document.getElementById("commonForm").submit() ;
  
}


</script>