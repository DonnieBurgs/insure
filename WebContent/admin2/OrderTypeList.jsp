<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
String keyword = (String)request.getAttribute("keyword");
String uf_parentid = (String)request.getAttribute("uf_parentid");
if(keyword==null) keyword = "";
if(uf_parentid==null) uf_parentid = "";
%>
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
<script type="text/javascript">var root = "";</script>
<script type="text/javascript" src="/resources/js/admin.js"></script>
<script type="text/javascript" src="/resources/js/common.js"></script>
<title>订单费用参数</title>
</head>
<body id="right">
<div class="operation o-h bg-f8">
	<div class="operationLeft f-l"><font style="margin-left:10px;font-size:14px; font-weight:bold; text-indent:14px; letter-spacing:2px;">订单费用参数</font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
	<div class="operationRight f-r">
	</div>
</div>
	 
<form id="commonForm" action="/emOrderType.do?method=update" method="post">
<table class="tlist">
	<thead>
		<tr class="title">
<th>订单类别</th>
<th>订金（固定金额）</th>
<th>订金（按百分比）</th>
<th>预付款（按百分比）</th>
		</tr>
	</thead>
	<tbody>
		
<c:choose>
<c:when test="${not empty resultRows}">
<c:forEach var="item" items="${resultRows}" varStatus="idx">
			<tr>
<td>${item.ordertypename}</td>
<td><input type="text" id="price2${idx.index}" name="price2${idx.index}" value="${item.price2}" style="width:50px;">元</td>
<td><input type="text" id="prate${idx.index}" name="prate${idx.index}" value="${item.price2rate}" style="width:50px;">%</td>
<td><input type="text" id="prrate${idx.index}" name="prrate${idx.index}" value="${item.price4rate}" style="width:50px;">%</td>

			</tr>
</c:forEach>
</c:when>
<c:otherwise>
			<tr><td colspan=5>
      <p><span>非常抱歉！</span></p><p>没有找到与您选择的搜索条件相符的资料。</p>
    	</td></tr>
</c:otherwise>
</c:choose>
		
		<tr>
			<td colspan="4" style="padding-top:20px;padding-bottom:40px;">
				<input type="button" class="btn1" value=" 提  交 " onclick="checkf()"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			</td>
		</tr>
	</tbody>
	<tfoot>
		<tr>
			<td colspan="7"></td>
		</tr>
	</tfoot>
</table>

</form>

</body>
<script type="text/javascript">
function checkf(){
	<c:if test="${not empty resultRows}">
	<c:forEach var="item" items="${resultRows}" varStatus="idx">
	if(commonForm.price2${idx.index}.value=="" || commonForm.price2${idx.index}.value!="" && !is_int(commonForm.price2${idx.index}.value)) {alert("请正确输入订金（固定金额）！");commonForm.price2${idx.index}.focus();return false;}
	if(commonForm.prate${idx.index}.value=="" || commonForm.prate${idx.index}.value!="" && !is_int(commonForm.prate${idx.index}.value)) {alert("请正确输入订金按百分比");commonForm.prate${idx.index}.focus();return false;}
	if(commonForm.prrate${idx.index}.value=="" || commonForm.prrate${idx.index}.value!="" && !is_int(commonForm.prrate${idx.index}.value)) {alert("请正确输入预付款（按百分比）！");commonForm.prrate${idx.index}.focus();return false;}
	</c:forEach>
	</c:if>
	commonForm.submit();
}
</script>
</html>