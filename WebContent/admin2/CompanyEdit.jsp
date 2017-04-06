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
<title>公司 <c:if test="${auth52 eq '1'}">修改</c:if><c:if test="${auth52 ne '1'}">查看</c:if></title>
</head>
<body id="right">
<div class="operation o-h bg-f8">
	<div class="operationLeft f-l"><font style="margin-left:10px;font-size:14px; font-weight:bold; text-indent:14px; letter-spacing:2px;">公司 <c:if test="${auth52 eq '1'}">修改</c:if><c:if test="${auth52 ne '1'}">查看</c:if></font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<a href="javascript:history.back();" style="margin-left:100px;"><i class="iconfont">&#xe63d;</i>返回列表</a></div>
	<div class="operationRight f-r">
	</div>
</div>
<form id="commonForm" action="/emCompany.do?method=update" method="post">
	<table class="tform">
<tr>
<td width="120" class="right">公司名称：</td>
<td><input type="text" id="companyname" name="companyname" value="${fn:replace(item.companyname,'"','&quot;')}" size=50></td>
</tr>
<tr>
<td width="120" class="right">联系人：</td>
<td><input type="text" id="contact" name="contact" value="${fn:replace(item.contact,'"','&quot;')}" size=50></td>
</tr>
<tr>
<td width="120" class="right">联系电话：</td>
<td><input type="text" id="mobile" name="mobile" value="${fn:replace(item.mobile,'"','&quot;')}" size=50></td>
</tr>
<tr>
<td width="120" class="right">公司类别：</td>
<td>
<select name="companytypeid">
<option value="0"<c:if test="${item.companytypeid eq '0'}"> selected</c:if>>企业
<option value="1"<c:if test="${item.companytypeid eq '1'}"> selected</c:if>>服务供应商
<option value="2"<c:if test="${item.companytypeid eq '2'}"> selected</c:if>>小型机构
<option value="3"<c:if test="${item.companytypeid eq '3'}"> selected</c:if>>销售商
</select>
</td>
</tr>
<tr>
<td width="120" class="right">服务类别：</td>
<td>
<select name="servicetypeid">
<option value="0"<c:if test="${item.servicetypeid eq '0'}"> selected</c:if>>医院
<option value="1"<c:if test="${item.servicetypeid eq '1'}"> selected</c:if>>民航
<option value="2"<c:if test="${item.servicetypeid eq '2'}"> selected</c:if>>高铁
</select>
</td>
</tr>
<tr>
<td width="120" class="right">区域：</td>
<td>
			<select name="area1" onchange="loadArea(this, area2, areaid)" style="width:110px;">
	 		<option value="">－请选择－</option>
	 		 <c:if test="${not empty areaList1}">
	 		 <c:forEach var="area" items="${areaList1}" varStatus="idx">
	 		 		<option value="${area.areaid}"<c:if test="${areaid1 eq area.areaid}"> selected</c:if>>${area.areaname}</option>
	 		 </c:forEach>
	 		 </c:if>
	 		</select>
			<select name="area2" onchange="loadArea(this, areaid, '')" style="width:110px;">
	 		<option value="">－请选择－</option>
	 		 <c:if test="${not empty areaList2}">
	 		 <c:forEach var="area" items="${areaList2}" varStatus="idx">
	 		 		<option value="${area.areaid}"<c:if test="${areaid2 eq area.areaid}"> selected</c:if>>${area.areaname}</option>
	 		 </c:forEach>
	 		 </c:if>
	 		</select>
			<select name="areaid" style="width:110px;">
	 		<option value="">－请选择－</option>
	 		 <c:if test="${not empty areaList3}">
	 		 <c:forEach var="area" items="${areaList3}" varStatus="idx">
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
<td width="120" class="right">申请审核：</td>
<td><select name="isaudit">
<option value="0"<c:if test="${item.isaudit eq '0'}"> selected</c:if>>未审核
<option value="1"<c:if test="${item.isaudit eq '1'}"> selected</c:if>>审核通过
<option value="2"<c:if test="${item.isaudit eq '2'}"> selected</c:if>>审核未通过
</select>
</td>
</tr>
<tr>
<td width="120" class="right">是否有效：</td>
<td><select name="isvalid">
<option value="1"<c:if test="${item.isvalid eq '1'}"> selected</c:if>>有效
<option value="0"<c:if test="${item.isvalid eq '0'}"> selected</c:if>>无效
</select>
</td>
</tr>
<tr>
<td width="120" class="right">有效期（起）：</td>
<td><input type="text" id="startdate" name="startdate" readonly="readonly" value="${item.startdate_}" onclick="WdatePicker({maxDate:'2018-12-31', dateFmt:'yyyy-MM-dd'})" style="width: 70px;"/></td>
</tr>
<tr>
<td width="120" class="right">有效期（止）：</td>
<td><input type="text" id="expdate" name="expdate" readonly="readonly" value="${item.expdate_}" onclick="WdatePicker({maxDate:'2018-12-31', dateFmt:'yyyy-MM-dd'})" style="width: 70px;"/></td>
</tr>
<tr>
<td width="120" class="right">管理员：</td>
<td>账号(手机号码)：${user.account} &nbsp;&nbsp;密码：${user.password}</td>
</tr>

		<tr>
			<td colspan="2">
			<input id="companyid" name="companyid" type="hidden" value="${item.companyid}"/>

				<input id="uf_parentid" name="uf_parentid" type="hidden" value="${uf_parentid}"/>
				<input id="keyword" name="keyword" type="hidden" value="${keyword}"/>
				<input id="key_companytypeid" name="key_companytypeid" type="hidden" value="${key_companytypeid}"/>
				<input id="key_isaudit" name="key_isaudit" type="hidden" value="${key_isaudit}"/>
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
<form id="deleteForm" action="/emCompany.do?method=delete" method="post">
<input id="companyid" name="companyid" type="hidden" value="${item.companyid}"/>
<input id="uf_parentid" name="uf_parentid" type="hidden" value="${uf_parentid}"/>
<input id="keyword" name="keyword" type="hidden" value="${keyword}"/>
<input id="key_companytypeid" name="key_companytypeid" type="hidden" value="${key_companytypeid}"/>
<input id="key_isaudit" name="key_isaudit" type="hidden" value="${key_isaudit}"/>
<input id="m" name="m" type="hidden" value="${m}"/>
<input id="s" name="s" type="hidden" value="${s}"/>
</form>
</html>
<script language=javascript>
function checkf() {
	if(commonForm.companyname.value=="") {alert("请正确输入公司名称！");return false;}
	if(commonForm.contact.value=="") {alert("请正确输入联系人！");return false;}
	if(commonForm.mobile.value=="") {alert("请正确输入联系电话！");return false;}
	if(commonForm.addr.value=="") {alert("请正确输入地址！");return false;}
	if(commonForm.startdate.value=="") {alert("请正确输入有效期（起）！");return false;}
	if(commonForm.expdate.value=="") {alert("请正确输入有效期（止）！");return false;}

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

	function loadArea(current, child, child1) {
		clearSelect(child,"－请选择－");
		if(child1!="") clearSelect(child1,"－请选择－");
		if(current.value=="") return ;
		
		$.ajax({ 
		    type: "get", 
		    url: "/emApi.do?method=areaList&areaid=" + current.value, 
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