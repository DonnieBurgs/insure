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
<title>救援订单添加</title>
</head>
<body id="right">
<div class="operation o-h bg-f8">
	<div class="operationLeft f-l"><font style="margin-left:10px;font-size:14px; font-weight:bold; text-indent:14px; letter-spacing:2px;">救援订单添加</font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<a href="javascript:history.back();" style="margin-left:100px;"><i class="iconfont">&#xe681;</i>返回列表</a></div>
	<div class="operationRight f-r">
	</div>
</div>
<form id="commonForm" action="/emOrder.do?method=add" method="post">
	<table class="tform">
<tr>
<td width="120" class="right">会员ID：</td>
<td><input type="text" id="userid" name="userid" value="" size=50></td>
</tr>
<tr>
<td width="120" class="right">订单类别：</td>
<td><select name="otype">
<c:if test="${not empty otList}">
<c:forEach var="listItem" items="${otList}" varStatus="idx">
	<option value="${listItem.ordertypeid}">${listItem.ordertypename}</option>
</c:forEach>
</c:if>
</select></td>
</tr>
<tr>
<td width="120" class="right">出发时间：</td>
<td><input type="text" id="departuredate" name="departuredate" readonly="readonly" value="${nowdate}" onclick="WdatePicker({maxDate:'2018-12-31', dateFmt:'yyyy-MM-dd'})" style="width: 70px;"/></td>
</tr>
<tr>
<td width="120" class="right">出发地：</td>
<td><input type="text" id="address1" name="address1" value="" size=50></td>
</tr>
<tr>
<td width="120" class="right">出发地坐标：</td>
<td><input type="text" id="lat1" name="lat1" value="23.14690971" style="width: 100px;"><input type="text" id="lng1" name="lng1" value="113.33590698" style="width: 100px;"></td>
</tr>
<tr>
<td width="120" class="right">目的地：</td>
<td><input type="text" id="address2" name="address2" value="" size=50></td>
</tr>
<tr>
<td width="120" class="right">目的地坐标：</td>
<td><input type="text" id="lat2" name="lat2" value="23.12439919" style="width: 100px;"><input type="text" id="lng2" name="lng2" value="113.31762695" style="width: 100px;"></td>
</tr>
<tr>
<td width="120" class="right">估算路程：</td>
<td><input type="text" id="distance" name="distance" size=50></td>
</tr>
<tr>
<td width="120" class="right">车型：</td>
<td><select name="cartypeid">
<c:if test="${not empty cartypeList}">
<c:forEach var="listItem" items="${cartypeList}" varStatus="idx">
	<option value="${listItem.cartypeid}">${listItem.cartypename}</option>
</c:forEach>
</c:if>
</select></td>
</tr>
<tr>
<td width="120" class="right">套餐：</td>
<td><select name="packtypeid">
<c:if test="${not empty packtypeList}">
<c:forEach var="listItem" items="${packtypeList}" varStatus="idx">
	<option value="${listItem.packtypeid}">${listItem.packtypename}</option>
</c:forEach>
</c:if>
</select></td>
</tr>
<tr>
<td width="120" class="right">服务选择：</td>
<td>
<c:if test="${not empty serviceoptiontypeList}">
<c:forEach var="listItem" items="${serviceoptiontypeList}" varStatus="idx">
	<input id="st${idx.index+1}" name="st${idx.index+1}" type="checkbox" value="${listItem.serviceoptiontypeid}"/>${listItem.serviceoptiontypename}&nbsp;&nbsp;
</c:forEach>
</c:if>
</tr>
<tr>
<td width="120" class="right">医生：</td>
<td><input type="text" id="doctorid" name="doctorid" size=50></td>
</tr>
<tr>
<td width="120" class="right">订单评估价格：</td>
<td><input type="text" id="price1" name="price1" size=50></td>
</tr>
<tr>
<td width="120" class="right">订金：</td>
<td><input type="text" id="price2" name="price2" size=50></td>
</tr>
<tr>
<td width="120" class="right">订单报价：</td>
<td><input type="text" id="price3" name="price3" size=50></td>
</tr>
<tr>
<td width="120" class="right">预付款：</td>
<td><input type="text" id="price4" name="price4" size=50></td>
</tr>
<tr>
<td width="120" class="right">成本价格：</td>
<td><input type="text" id="price5" name="price5" size=50></td>
</tr>
<tr>
<td width="120" class="right">其他要求：</td>
<td><input type="text" id="note" name="note" value="" size=50></td>
</tr>
<tr>
<td width="120" class="right">客服备注：</td>
<td><input type="text" id="servicenote" name="servicenote" value="" size=50> 客户不可见</td>
</tr>

		<tr>
			<td colspan="2">

				<input id="uf_parentid" name="uf_parentid" type="hidden" value="${uf_parentid}"/>
				<input id="keyword" name="keyword" type="hidden" value="${keyword}"/>
				<input id="key_otype" name="key_otype" type="hidden" value="${key_otype}"/>
				<input id="key_status" name="key_status" type="hidden" value="${key_status}"/>
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
	if(commonForm.userid.value=="" || commonForm.userid.value!="" && !is_int(commonForm.userid.value)) {alert("请正确输入会员ID！");return false;}
	if(commonForm.otype.value=="" || commonForm.otype.value!="" && !is_int(commonForm.otype.value)) {alert("请正确输入订单类别！");return false;}
	if(commonForm.departuredate.value=="") {alert("请正确输入出发时间！");return false;}
	if(commonForm.address1.value=="") {alert("请正确输入出发地点！");return false;}
//	if(commonForm.province1.value=="") {alert("请正确输入省！");return false;}
//	if(commonForm.city1.value=="") {alert("请正确输入市！");return false;}
//	if(commonForm.district1.value=="") {alert("请正确输入区！");return false;}
	if(commonForm.lat1.value=="" || commonForm.lat1.value!="" && !(is_float(commonForm.lat1.value) || is_int(commonForm.lat1.value))) {alert("请正确输入坐标！");return false;}
	if(commonForm.lng1.value=="" || commonForm.lng1.value!="" && !(is_float(commonForm.lng1.value) || is_int(commonForm.lng1.value))) {alert("请正确输入坐标！");return false;}
	if(commonForm.address2.value=="") {alert("请正确输入目的地！");return false;}
//	if(commonForm.province2.value=="") {alert("请正确输入省！");return false;}
//	if(commonForm.city2.value=="") {alert("请正确输入市！");return false;}
//	if(commonForm.district2.value=="") {alert("请正确输入区！");return false;}
	if(commonForm.lat2.value=="" || commonForm.lat2.value!="" && !(is_float(commonForm.lat2.value) || is_int(commonForm.lat2.value))) {alert("请正确输入坐标！");return false;}
	if(commonForm.lng2.value=="" || commonForm.lng2.value!="" && !(is_float(commonForm.lng2.value) || is_int(commonForm.lng2.value))) {alert("请正确输入坐标！");return false;}
	if(commonForm.distance.value=="" || commonForm.distance.value!="" && !(is_float(commonForm.distance.value) || is_int(commonForm.distance.value))) {alert("请正确输入估算路程！");return false;}
	if(commonForm.cartypeid.value=="" || commonForm.cartypeid.value!="" && !is_int(commonForm.cartypeid.value)) {alert("请正确输入车型！");return false;}
	if(commonForm.packtypeid.value=="" || commonForm.packtypeid.value!="" && !is_int(commonForm.packtypeid.value)) {alert("请正确输入套餐！");return false;}
//	if(commonForm.servicetype.value=="") {alert("请正确输入服务选择！");return false;}
//	if(commonForm.doctorid.value=="" || commonForm.doctorid.value!="" && !is_int(commonForm.doctorid.value)) {alert("请正确输入医生ID！");return false;}
	if(commonForm.price1.value=="" || commonForm.price1.value!="" && !(is_float(commonForm.price1.value) || is_int(commonForm.price1.value))) {alert("请正确输入订单评估价格！");return false;}
	if(commonForm.price2.value=="" || commonForm.price2.value!="" && !(is_float(commonForm.price2.value) || is_int(commonForm.price2.value))) {alert("请正确输入订金！");return false;}
	if(commonForm.price3.value!="" && !(is_float(commonForm.price3.value) || is_int(commonForm.price3.value))) {alert("请正确输入订单报价！");return false;}
	if(commonForm.price4.value!="" && !(is_float(commonForm.price4.value) || is_int(commonForm.price4.value))) {alert("请正确输入预付款！");return false;}
	if(commonForm.price5.value!="" && !(is_float(commonForm.price5.value) || is_int(commonForm.price5.value))) {alert("请正确输入成本价格！");return false;}
//	if(commonForm.status.value=="" || commonForm.status.value!="" && !is_int(commonForm.status.value)) {alert("请正确输入状态！");return false;}
//	if(commonForm.note.value=="") {alert("请正确输入其他要求！");return false;}
//	if(commonForm.servicenote.value=="") {alert("请正确输入客服备注！");return false;}
//	if(commonForm.score.value=="" || commonForm.score.value!="" && !(is_float(commonForm.score.value) || is_int(commonForm.score.value))) {alert("请正确输入评分！");return false;}
//	if(commonForm.score1.value=="" || commonForm.score1.value!="" && !is_int(commonForm.score1.value)) {alert("请正确输入评分！");return false;}
//	if(commonForm.score2.value=="" || commonForm.score2.value!="" && !is_int(commonForm.score2.value)) {alert("请正确输入评分！");return false;}
//	if(commonForm.score3.value=="" || commonForm.score3.value!="" && !is_int(commonForm.score3.value)) {alert("请正确输入评分！");return false;}
//	if(commonForm.scorenote.value=="") {alert("请正确输入评论！");return false;}

	document.getElementById("commonForm").submit() ;
  
}


</script>