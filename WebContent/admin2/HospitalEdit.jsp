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
<title>医院<c:if test="${auth42 eq '1'}">修改</c:if><c:if test="${auth42 ne '1'}">查看</c:if></title>
</head>
<body id="right">
<div class="operation o-h bg-f8">
	<div class="operationLeft f-l"><font style="margin-left:10px;font-size:14px; font-weight:bold; text-indent:14px; letter-spacing:2px;">医院<c:if test="${auth42 eq '1'}">修改</c:if><c:if test="${auth42 ne '1'}">查看</c:if></font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<a href="javascript:history.back();" style="margin-left:100px;"><i class="iconfont">&#xe63d;</i>返回列表</a></div>
	<div class="operationRight f-r">
	</div>
</div>
<form id="commonForm" action="/emHospital.do?method=update" method="post">
	<table class="tform">
<tr>
<td width="120" class="right">医院名称：</td>
<td><input type="text" id="hospitalname" name="hospitalname" value="${fn:replace(item.hospitalname,'"','&quot;')}" size=50></td>
</tr>
<tr>
<td width="120" class="right">等级：</td>
<td><input type="text" id="grade" name="grade" value="${fn:replace(item.grade,'"','&quot;')}" size=50></td>
</tr>
<tr>
<td width="120" class="right">联系电话：</td>
<td><input type="text" id="mobile" name="mobile" value="${fn:replace(item.mobile,'"','&quot;')}" size=50></td>
</tr>
<tr>
<td width="120" class="right">区域：</td>
<td>
			<select name="area1" onchange="loadArea(this, areaid)" style="width:110px;">
	 		<option value="">－请选择－</option>
	 		 <c:if test="${not empty areaList1}">
	 		 <c:forEach var="area" items="${areaList1}" varStatus="idx">
	 		 		<option value="${area.areaid}"<c:if test="${areaid1 eq area.areaid}"> selected</c:if>>${area.areaname}</option>
	 		 </c:forEach>
	 		 </c:if>
	 		</select>
			<select name="areaid" style="width:110px;">
	 		<option value="">－请选择－</option>
	 		 <c:if test="${not empty areaList2}">
	 		 <c:forEach var="area" items="${areaList2}" varStatus="idx">
	 		 		<option value="${area.areaid}"<c:if test="${areaid eq area.areaid}"> selected</c:if>>${area.areaname}</option>
	 		 </c:forEach>
	 		 </c:if>
	 		</select>
</td>
</tr>
<tr>
<td width="120" class="right">地址：</td>
<td><input type="text" id="addr" name="addr" value="${fn:replace(item.addr,'"','&quot;')}" size=50></td>
</tr>
<tr>
<td width="120" class="right">网站：</td>
<td><input type="text" id="url" name="url" value="${fn:replace(item.url,'"','&quot;')}" size=50></td>
</tr>
<tr>
<td width="120" class="right">坐标：</td>
<td>纬度<input type="text" id="lat" name="lat" value="${fn:replace(item.lat,'"','&quot;')}" style="width:100px;">&nbsp;经度<input type="text" id="lng" name="lng" value="${fn:replace(item.lng,'"','&quot;')}" style="width:100px;"></td>
</tr>
<tr>
<td width="120" class="right">简介：</td>
<td><input type="text" id="note" name="note" value="${fn:replace(item.note,'"','&quot;')}" size=50></td>
</tr>
<tr>
<td width="120" class="right">照片：</td>
<td><a class="mya" href="javascript:f_photo0.click();"><img id="imgphoto0" src="<c:if test="${empty item.photo}">/resources/images/folder2.png</c:if><c:if test="${not empty item.photo}">/upload/images/${item.photo}</c:if>"></a></td>
</tr>

		<tr>
			<td colspan="2">
			<input id="hospitalid" name="hospitalid" type="hidden" value="${item.hospitalid}"/>
<input type="hidden" id="photo0" name="photo0"/>
				<input id="uf_parentid" name="uf_parentid" type="hidden" value="${uf_parentid}"/>
				<input id="keyword" name="keyword" type="hidden" value="${keyword}"/>
				<input id="key_areaid1" name="key_areaid1" type="hidden" value="${key_areaid1}"/>
				<input id="key_areaid" name="key_areaid" type="hidden" value="${key_areaid}"/>
				<input id="m" name="m" type="hidden" value="${m}"/>
				<input id="s" name="s" type="hidden" value="${s}"/>
				<c:if test="${auth42 eq '1'}">
				<input type="button" class="btn1" value=" 提  交 " onclick="checkf()"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="button" class="btn1" value=" 删  除 " onclick="checkdelete()"/>
				</c:if>
				<c:if test="${auth42 ne '1'}">
				<input type="button" class="btn1" value=" 返  回 " onclick="history.back();"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				</c:if>
			</td>
		</tr>
	</table>
</form>
<input type="file" id="f_photo0" name="f_photo0" onchange='_compress(this,$("#photo0"),$("#imgphoto0"));' style="width:0px;"/>
</body>
<form id="deleteForm" action="/emHospital.do?method=delete" method="post">
<input id="hospitalid" name="hospitalid" type="hidden" value="${item.hospitalid}"/>
<input id="uf_parentid" name="uf_parentid" type="hidden" value="${uf_parentid}"/>
<input id="keyword" name="keyword" type="hidden" value="${keyword}"/>
<input id="key_areaid1" name="key_areaid1" type="hidden" value="${key_areaid1}"/>
<input id="key_areaid" name="key_areaid" type="hidden" value="${key_areaid}"/>
<input id="m" name="m" type="hidden" value="${m}"/>
<input id="s" name="s" type="hidden" value="${s}"/>
</form>
</html>
<script language=javascript>
function checkf() {
	if(commonForm.hospitalname.value=="") {alert("请正确输入医院名称！");return false;}
	if(commonForm.mobile.value=="") {alert("请正确输入联系电话！");return false;}
	if(commonForm.areaid.value=="" || commonForm.areaid.value!="" && !is_int(commonForm.areaid.value)) {alert("请正确输入区域！");return false;}
	if(commonForm.lat.value=="" || commonForm.lat.value!="" && !(is_float(commonForm.lat.value) || is_int(commonForm.lat.value))) {alert("请正确输入坐标！");return false;}
	if(commonForm.lng.value=="" || commonForm.lng.value!="" && !(is_float(commonForm.lng.value) || is_int(commonForm.lng.value))) {alert("请正确输入坐标！");return false;}

	document.getElementById("commonForm").submit() ;
  
}

function clearSelect(current, nodename){
	var anode, tnode;
	while(current.hasChildNodes()) {
		anode = current.firstChild;
		current.removeChild(anode);
	}
	anode = document.createElement("option");
	anode.setAttribute("value","");
	anode.setAttribute("selected", "1");
 	current.appendChild(anode);
	tnode = document.createTextNode(nodename);
	anode.appendChild(tnode);
	current.disabled = true;
	current.style.backgroundColor = '#CCCCCC';			
}

function loadArea(current, child) {
	clearSelect(child,"－请选择－");
	if(child!="") clearSelect(child,"－请选择－");
	if(current.value=="") return ;
	
	$.ajax({ 
	    type: "get", 
	    url: "/emApp.do?method=areaList&areaid=" + current.value, 
	    dataType: "json", 
	    success: function (data) {
			$.each(data, function(i, n){
				anode = document.createElement("option");
				anode.setAttribute("value",n.areaid);
				child.appendChild(anode);
				tnode = document.createTextNode(n.areaname);
				anode.appendChild(tnode);
			});
			child.disabled = false;
			child.style.backgroundColor = '#FFFFFF';

	    }, 
	    error: function (XMLHttpRequest, textStatus, errorThrown) { 
	            alert(errorThrown); 
	    } 
	});

}

function checkdelete() {
	if(confirm("是否删除当前数据记录？")==false) {
		return ;
	} else {
		document.getElementById("deleteForm").submit() ;
	}
	
}

</script>