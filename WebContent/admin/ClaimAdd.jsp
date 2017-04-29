<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="dict" uri="/WEB-INF/dict_tag.tld" %>
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
<link href="/resources/js/jquery-ui/jquery-ui.min.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="/resources/js/jquery-ui/jquery-ui.min.js"></script>
<script type="text/javascript">var root = "";</script>
<script type="text/javascript" src="/resources/js/admin.js"></script>
<script type="text/javascript" src="/resources/js/common.js"></script>
<script type="text/javascript" src="/resources/js/photo.js"></script>
<script type="text/javascript" src="/resources/js/My97DatePicker/config.js"></script>
<script type="text/javascript" src="/resources/js/My97DatePicker/lang/zh-cn.js"></script>
<link rel="stylesheet" type="text/css" href="/resources/js/My97DatePicker/skin/WdatePicker.css" />
<link rel="stylesheet" type="text/css" href="/resources/js/My97DatePicker/skin/default/datepicker.css" />
<script type="text/javascript" src="/resources/js/My97DatePicker/WdatePicker.js"></script>
<title>案件列表</title>
<style type="text/css">
input[type="text"]{min-width:50px;width:70px;}
</style>
</head>
<body id="right">
<div class="operation o-h bg-f8">
	<div class="operationLeft f-l"><font style="margin-left:10px;font-size:14px; font-weight:bold; text-indent:14px; letter-spacing:2px;">案件</font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<a href="javascript:history.back();" style="margin-left:100px;"><i class="iconfont">&#xe681;</i>返回列表</a></div>
	<div class="operationRight f-r">
	</div>
</div>
<c:choose>
<c:when test="${empty item }">
<table class="tlist">
	 <thead>
	 <tr class="title">
	 <th width=10%>案卷</th>
	 <th width=80% align=left style="background-color:#ffffff;text-align:left;">
	 	<input type="hidden" id="keyword" name="keyword" value="${keyword}">
	 	<input type="text" id="emClaimArchive" name="emClaimArchive" value="<dict:itemdesc name="claimarchivenumber" value="${keyword}" table="em_claimarchive" path="id"/>">
	 </th>
	 	<th width="20%" class="operationBtn">
	 	</th>
	 </tr></thead>
	 <tbody>

	 </tbody>
</table>
</c:when>
<c:otherwise>
</c:otherwise>
</c:choose>

<c:if test="${auth44 eq '1'}">
<c:choose>
<c:when test="${empty item }">
<form id="commonForm" action="/emClaim.do?method=add" method="post">
<input type="hidden" id="claimarchiveid" name="claimarchiveid" value="${keyword}">
<input type="hidden" id="insuredid" name="insuredid">
</c:when>
<c:otherwise>
<form id="commonForm" action="/emClaim.do?method=update" method="post">
<input type="hidden" id="claimarchiveid" name="claimarchiveid" value="${fn:replace(item.claimarchiveid,'"','&quot;')}" size=50>
<input type="hidden" id="insuredid" name="insuredid" value="${item.insuredid }">
<input type="hidden" id="id" name="id" value="${item.id }">
<input type="hidden" id="claiminfoid" name="claiminfoid" value="${emClaimInfo.id }">
</c:otherwise>
</c:choose>

	<table class="tform">
<tr>
<td>序号</td>
<td>被保人</td>
<td>报案人电话</td>
</tr>
<tr>
<td><input type="text" id="serialnumber" name="serialnumber" value="${item.serialnumber }" style="width: 140px;" readonly="readonly"></td>
<td><input type="text" id="emInsured" name="emInsured" value="${insured.insuredname }" style="width: 140px;"></td>
<td><input type="text" id="bardh" name="bardh" value="${item.bardh }" style="width: 140px;"></td>
</tr>
</table>


<input type="hidden" id="insured_id" name="insured.id" value="${insured.id }">
<table class="tform">
<tr>
<td><font color="red">*</font>姓名：</td>
<td><input type="text" id="insured_insuredname" name="insured.insuredname" value="${insured.insuredname }" size=50></td>
<td><font color="red">*</font>性别：</td>
<td>
<select name="insured.gender" id="insured_gender" class="select100">
<option value="男" <c:if test="${insured.gender eq '男' }">selected="selected"</c:if>>男</option>
<option value="女" <c:if test="${insured.gender eq '女'}">selected="selected"</c:if>>女</option>
</select></td>
<td><font color="red">*</font>证件类型：</td>
<td><select name="insured.idtype" id="insured_idtype" class="select100">
<option value="身份证">身份证</option>
</select></td>
<td><font color="red">*</font>证件号码：</td>
<td><input type="text" id="insured_idnumber" name="insured.idnumber" value="${insured.idnumber}" size=50></td>
<td><font color="red">*</font>手机号：</td>
<td><input type="text" id="insured_mobile" name="insured.mobile" value="${insured.mobile }" size=50></td>
</tr>
<tr>
<td><font color="red">*</font>开户行省：</td><td><input type="text" id="insured_bankprovince" name="insured.bankprovince" value="${insured.bankprovince }" size=50></td>
<td><font color="red">*</font>开户行市：</td><td><input type="text" id="insured_bankcity" name="insured.bankcity" value="${insured.bankcity}" size=50></td>
<td><font color="red">*</font>开户行支行：</td><td><input type="text" id="insured_subbranch" name="insured.subbranch" value="${insured.subbranch }" size=50></td>
<td><font color="red">*</font>银行账号：</td><td><input type="text" id="insured_accountnumber" name="insured.accountnumber" value="${insured.accountnumber }" size=50></td>
<td><font color="red">*</font>账户名：</td><td><input type="text" id="insured_accountname" name="insured.accountname" value="${insured.accountname}" size=50></td>
</tr>
<tr>
<td><font color="red">*</font>开户行：</td><td><input type="text" id="insured_bankname" name="insured.bankname" value="${insured.bankname }" size=50></td>
<td>出生年月：</td><td><input type="text" id="insured_birthdate_" name="insured.birthdate" readonly="readonly" value="${insured.birthdate_}" onclick="WdatePicker({maxDate:'2018-12-31', dateFmt:'yyyy-MM-dd'})" style="width: 70px;"/></td>
<td>年龄：</td><td><input type="text" id="insured_age" name="insured.age" value="${insured.age }" size=50></td>
<td>人员类别：</td><td><input type="text" id="insured_insuredtype" name="insured.insuredtype" value="${insured.insuredtype}" size=50></td>
<td>电子邮件：</td><td><input type="text" id="insured_email" name="insured.email" value="${insured.email }" size=50></td>
</tr>
<tr>
<td>工作单位：</td><td><input type="text" id="insured_employer" name="insured.employer" value="${insured.employer}" size=50></td>
<td>工号：</td><td><input type="text" id="insured_jobnumber" name="insured.jobnumber" value="${insured.jobnumber }" size=50></td>
<td>工龄：</td><td><input type="text" id="insured_workage" name="insured.workage" value="${insured.workage}" size=50></td>
<td>科室：</td><td><input type="text" id="insured_title" name="insured.title" value="${insured.title }" size=50></td>
<td>机构：</td><td><input type="text" id="insured_department" name="insured.department" value="${insured.department }" size=50></td>
</tr>
</table>


<input type="hidden" id="info_diseaseid" name="info.diseaseid" value="${emClaimInfo.diseaseid }" size=50>
	<table class="tform">
<tr>
<td><font color="red">*</font>案件类型：</td><td><select id="info_claimtype" name="info.claimtype">
		<c:forEach var="it" items="${claimtypeMap }"><option value="${it.key }" <c:if test="${it.key eq emClaimInfo.claimtype }">selected="selected"</c:if>>${it.value}</option></c:forEach>
		</select></td>
<td><font color="red">*</font>出险原因：</td><td><select id="info_claimreason" name="info.claimreason">
		<c:forEach var="it" items="${claimreasonMap }"><option value="${it.key }" <c:if test="${it.key eq emClaimInfo.claimreason }">selected="selected"</c:if>>${it.value}</option></c:forEach>
		</select></td>
<td><font color="red">*</font>出险日期：</td>
<td><input type="text" id="info_claimdate" name="info.claimdate" readonly="readonly" value="${emClaimInfo.claimdate_ }" onclick="WdatePicker({maxDate:'2018-12-31', dateFmt:'yyyy-MM-dd'})" style="width: 70px;"/></td>
<td><font color="red">*</font>保险金支付方式：</td><td><select id="info_paytype" name="info.paytype">
		<c:forEach var="it" items="${paytypeMap }"><option value="${it.key }" <c:if test="${it.key eq emClaimInfo.paytype }">selected="selected"</c:if>>${it.value}</option></c:forEach>
		</select></td>
<td><font color="red">*</font>疾病：</td><td><input type="text" id="info_emDisease" name="info.emDisease" value="<dict:itemdesc name="diseasename" value="${emClaimInfo.diseaseid}" table="em_disease" path="id"/>" size=50>
</tr>
<tr>
<td><font color="red">*</font>出险类型1：</td><td>
<select id="info_insuretype1" name="info.insuretype1">
		<c:forEach var="it" items="${insuretypeMap }"><option value="${it.key }" <c:if test="${it.key eq emClaimInfo.insuretype1 }">selected="selected"</c:if>>${it.value}</option></c:forEach>
		</select></td>
<td>出险类型2：</td><td><select id="info_insuretype2" name="info.insuretype2">
		<c:forEach var="it" items="${insuretypeMap }"><option value="${it.key }" <c:if test="${it.key eq emClaimInfo.insuretype2 }">selected="selected"</c:if>>${it.value}</option></c:forEach>
		</select></td>
<td>出险类型3：</td><td><select id="info_insuretype3" name="info.insuretype3">
		<c:forEach var="it" items="${insuretypeMap }"><option value="${it.key }" <c:if test="${it.key eq emClaimInfo.insuretype3 }">selected="selected"</c:if>>${it.value}</option></c:forEach>
		</select></td>
<td>索赔金额：</td><td><input type="text" id="info_spamount" name="info.spamount" value="${emClaimInfo.spamount }" size=50></td>
<td>就诊次数：</td><td><input type="text" id="info_determinecount" name="info.determinecount" value="${emClaimInfo.determinecount }" size=50></td>

</tr>
<tr>
<td><font color="red">*</font>单张数：</td><td><input type="text" id="info_receiptcount" name="info.receiptcount" value="${emClaimInfo.receiptcount }" size=50></td>
<td>备注：</td><td colspan="7"><input type="text" id="info_mark" name="info.mark" value="${emClaimInfo.mark }" size=50></td>
</tr>
	</table>


<table>
		<tr>
			<td colspan="3" class="right">
<br/>
				<input id="uf_parentid" name="uf_parentid" type="hidden" value="${uf_parentid}"/>
				<input id="keyword" name="keyword" type="hidden" value="${keyword}"/>
				<input id="m" name="m" type="hidden" value="${m}"/>
				<input id="s" name="s" type="hidden" value="${s}"/>
				<input type="button" class="btn1" value=" 提  交 " onclick="checkf()"/>
			</td>
		</tr>
	</table>
</form>
</c:if>
	 

</body>
<script type="text/javascript">
function submitform(){
	searchForm.submit();
}
function deleteClaim(id){
	if(confirm("是否删除会员资料？")) {
		$("#id").val(id);
		$("#t").val(t);
		deleteForm.submit();
		
	}
}
function checkf() {
	if(commonForm.claimarchiveid.value=="" || commonForm.claimarchiveid.value!="" && !is_int(commonForm.claimarchiveid.value)) {alert("请正确输入案卷ID！");return false;}
	if(commonForm.serialnumber.value=="") {alert("请正确输入序号！");return false;}
	if(commonForm.emInsured.value=="") {alert("请正确输入被保人！");return false;}
	if(commonForm.bardh.value=="") {alert("请正确输入报案人电话！");return false;}

	if($("#insured_insuredname").val() =="") {alert("请正确输入名称！");return false;}
	if($("#insured_gender").val() =="") {alert("请正确输入性别！");return false;}
	if($("#insured_idtype").val() =="" ) {alert("请正确输入证件类型！");return false;}
	if($("#insured_idnumber").val() =="" ) {alert("请正确输入身份证！");return false;}
	if($("#insured_bankname").val() =="" ) {alert("请正确输入开户行！");return false;}
	if($("#insured_bankprovince").val() =="" ) {alert("请正确输入开户行省！");return false;}
	if($("#insured_bankcity").val() =="" ) {alert("请正确输入开户行市！");return false;}
	if($("#insured_subbranch").val() =="" ) {alert("请正确输入开户行支行！");return false;}
	if($("#insured_accountname").val() =="" ) {alert("请正确输入账户名！");return false;}
	if($("#insured_accountnumber").val() =="" ) {alert("请正确输入银行账号！");return false;}
	if($("#insured_mobile").val() =="" ) {alert("请正确输入手机号码！");return false;}
	
	if($("#info_claimdate").val() =="" ) {alert("请正确输入出险日期！");return false;}
	if($("#info_diseaseid").val() =="" ) {alert("请正确输入疾病！");return false;}
	var inforeceiptcount =  $("#info_receiptcount").val();
	if(inforeceiptcount ==""  || inforeceiptcount !="" && !is_int(inforeceiptcount)) {alert("请正确输入单张数！");return false;}
	
	document.getElementById("commonForm").submit() ;
  
}
$(function() {
	jQuery.curCSS = jQuery.css;
    $("#emClaimArchive").autocomplete({
    	source: "/emClaimArchive.do?method=autocomplete",
	    select: function( event, ui ) {
	    	$("#keyword").val(ui.item.id);
	    	$("#claimarchiveid").val(ui.item.id);
	    	
//	    	window.location.href = "/emClaim.do?method=blank&keyword=" + ui.item.id;
	   


	        $.getJSON("/emClaim.do?method=maxNo&claimarchiveid=" + ui.item.id, function(data){
	    		$("#serialnumber").val(data);
	    	});
	    }
    });
    $("#emInsured").autocomplete({
    	source: "/emInsured.do?method=autocomplete&inc=idnumber",
	    select: function( event, ui ) {
	    	$("#insuredid").val(ui.item.id);
	    	 $.getJSON("/emInsured.do?method=obj&id=" + ui.item.id, function(data){
	    		$.each(data, function(i, v){
	    			var key = "insured_" + i;
	    			$("#" + key).val(v);
	    		});
	    	});
	    }
    });
    $("#info_emDisease").autocomplete({
    	source: "/emDisease.do?method=autocomplete",
	    select: function( event, ui ) {
	    	$("#info_diseaseid").val(ui.item.id);
	    }
    });
 });
</script>
</html>