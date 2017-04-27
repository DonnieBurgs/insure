<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="dict" uri="/WEB-INF/dict_tag.tld" %>
<%@ taglib prefix="util" uri="/WEB-INF/util.tld" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<link type="text/css" rel="stylesheet" href="/admin/style/base.css" />
<link type="text/css" rel="stylesheet" href="/admin/style/global.css" />
<link href="/resources/css/main.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="/resources/jquery/jquery-1.9.1.min.js"></script>
<link href="/resources/js/jquery-ui/jquery-ui.min.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="/resources/js/jquery-ui/jquery-ui.min.js"></script>
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
<title>案件<c:if test="${auth44 eq '1'}">修改</c:if><c:if test="${auth44 ne '1'}">查看</c:if></title>
<style type="text/css">
input[type="text"]{min-width:50px;width:70px;}
</style>
</head>
<body id="right">
<div class="operation o-h bg-f8">
	<div class="operationLeft f-l"><font style="margin-left:10px;font-size:14px; font-weight:bold; text-indent:14px; letter-spacing:2px;">案件<c:if test="${auth44 eq '1'}">修改</c:if><c:if test="${auth44 ne '1'}">查看</c:if></font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<a href="javascript:history.back();" style="margin-left:100px;"><i class="iconfont">&#xe63d;</i>返回列表</a></div>
	<div class="operationRight f-r">
	</div>
</div>
<form id="commonForm" action="/emClaim.do?method=update" method="post">
<input type="hidden" id="claimarchiveid" name="claimarchiveid" value="${fn:replace(item.claimarchiveid,'"','&quot;')}" size=50>
<input type="hidden" id="insuredid" name="insuredid" value="${item.insuredid }">
<input type="hidden" id="id" name="id" value="${item.id }">
<input type="hidden" id="claiminfoid" name="claiminfoid" value="${emClaimInfo.id }">
	<table class="tform">
<tr>
<td>序号</td>
<td>被保人</td>
<td>报案人电话</td>
</tr>
<tr>
<td><input type="text" id="serialnumber" name="serialnumber" style="width: 140px;" readonly="readonly" value="${item.serialnumber }"></td>
<td><input type="text" id="emInsured" name="emInsured"  style="width: 140px;" readonly="readonly" value="${insured.insuredname }"></td>
<td><input type="text" id="bardh" name="bardh" style="width: 140px;" value="${item.bardh }"></td>
</tr>
</table>


<input type="hidden" id="insured_id" name="insured.id" value="${insured.id }">
<table class="tform">
<tr>
<td>姓名：</td>
<td><input type="text" id="insured_insuredname" name="insured.insuredname" value="${insured.insuredname }" size=50></td>
<td>性别：</td>
<td>
<select name="insured.gender" id="insured_gender" class="select100">
<option value="男" <c:if test="${insured.gender eq '男' }">selected="selected"</c:if>>男</option>
<option value="女" <c:if test="${insured.gender eq '女'}">selected="selected"</c:if>>女</option>
</select></td>
<td>证件类型：</td>
<td><select name="insured.idtype" id="insured_idtype" class="select100">
<option value="身份证">身份证</option>
</select></td>
<td>身份证：</td>
<td><input type="text" id="insured_idnumber" name="insured.idnumber" value="${insured.idnumber}" size=50></td>
<td>护照：</td>
<td><input type="text" id="insured_passport" name="insured.passport" value="${insured.passport }" size=50></td>
</tr>
<tr>
<td>出生年月：</td><td><input type="text" id="insured_birthdate_" name="insured.birthdate" readonly="readonly" value="${insured.birthdate_}" onclick="WdatePicker({maxDate:'2018-12-31', dateFmt:'yyyy-MM-dd'})" style="width: 70px;"/></td>
<td>工作单位：</td><td><input type="text" id="insured_employer" name="insured.employer" value="${insured.employer}" size=50></td>
<td>工号：</td><td><input type="text" id="insured_jobnumber" name="insured.jobnumber" value="${insured.jobnumber }" size=50></td>
<td>工龄：</td><td><input type="text" id="insured_workage" name="insured.workage" value="${insured.workage}" size=50></td>
<td>科室：</td><td><input type="text" id="insured_title" name="insured.title" value="${insured.title }" size=50></td>
</tr>
<tr>
<td>人员类别：</td><td><input type="text" id="insured_insuredtype" name="insured.insuredtype" value="${insured.insuredtype}" size=50></td>
<td>年龄：</td><td><input type="text" id="insured_age" name="insured.age" value="${insured.age }" size=50></td>
<td>开户行省：</td><td><input type="text" id="insured_bankprovince" name="insured.bankprovince" value="${insured.bankprovince }" size=50></td>
<td>开户行市：</td><td><input type="text" id="insured_bankcity" name="insured.bankcity" value="${insured.bankcity}" size=50></td>
<td>开户行支行：</td><td><input type="text" id="insured_subbranch" name="insured.subbranch" value="${insured.subbranch }" size=50></td>
</tr>
<tr>
<td>银行账号：</td><td><input type="text" id="insured_accountnumber" name="insured.accountnumber" value="${insured.accountnumber }" size=50></td>
<td>开户行：</td><td><input type="text" id="insured_bankname" name="insured.bankname" value="${insured.bankname }" size=50></td>
<td>账户名：</td><td><input type="text" id="insured_accountname" name="insured.accountname" value="${insured.accountname}" size=50></td>
<td>电子邮件：</td><td><input type="text" id="insured_email" name="insured.email" value="${insured.email }" size=50></td>
<td>机构：</td><td><input type="text" id="insured_department" name="insured.department" value="${insured.department }" size=50></td>
</tr>
<tr>
<td>手机号：</td>
<td colspan="9"><input type="text" id="insured_mobile" name="insured.mobile" value="${insured.mobile }" size=50></td>
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
<td><font color="red">*</font>出险日期：</td><td><input type="text" id="info_claimdate" name="info.claimdate" readonly="readonly" value="${emClaimInfo.claimdate_ }" onclick="WdatePicker({maxDate:'2018-12-31', dateFmt:'yyyy-MM-dd'})" style="width: 70px;"/></td>
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
<td><font color="red">*</font>出险类型2：</td><td><select id="info_insuretype2" name="info.insuretype2">
		<c:forEach var="it" items="${insuretypeMap }"><option value="${it.key }" <c:if test="${it.key eq emClaimInfo.insuretype2 }">selected="selected"</c:if>>${it.value}</option></c:forEach>
		</select></td>
<td><font color="red">*</font>出险类型3：</td><td><select id="info_insuretype3" name="info.insuretype3">
		<c:forEach var="it" items="${insuretypeMap }"><option value="${it.key }" <c:if test="${it.key eq emClaimInfo.insuretype3 }">selected="selected"</c:if>>${it.value}</option></c:forEach>
		</select></td>
<td><font color="red">*</font>索赔金额：</td><td><input type="text" id="info_spamount" name="info.spamount" value="${emClaimInfo.spamount }" size=50></td>
<td><font color="red">*</font>就诊次数：</td><td><input type="text" id="info_determinecount" name="info.determinecount" value="${emClaimInfo.determinecount }" size=50></td>

</tr>
<tr>
<td><font color="red">*</font>单张数：</td><td><input type="text" id="info_receiptcount" name="info.receiptcount" value="${emClaimInfo.receiptcount }" size=50></td>
<td><font color="red">*</font>备注：</td><td colspan="7"><input type="text" id="info_mark" name="info.mark" value="${emClaimInfo.mark }" size=50></td>
</tr>
	</table>


<table>

		<tr>
			<td colspan="2">
			<input id="id" name="id" type="hidden" value="${item.id}"/>

				<input id="uf_parentid" name="uf_parentid" type="hidden" value="${uf_parentid}"/>
				<input id="keyword" name="keyword" type="hidden" value="${keyword}"/>
				<input id="m" name="m" type="hidden" value="${m}"/>
				<input id="s" name="s" type="hidden" value="${s}"/>
				<c:if test="${auth44 eq '1'}">
				<input type="button" class="btn1" value=" 提  交 " onclick="checkf()"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="button" class="btn1" value=" 删  除 " onclick="checkdelete()"/>
				</c:if>
				<c:if test="${auth44 ne '1'}">
				<input type="button" class="btn1" value=" 返  回 " onclick="history.back();"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				</c:if>
			</td>
		</tr>
	</table>
</form>

</body>
<form id="deleteForm" action="/emClaim.do?method=delete" method="post">
<input id="id" name="id" type="hidden" value="${item.id}"/>
<input id="uf_parentid" name="uf_parentid" type="hidden" value="${uf_parentid}"/>
<input id="keyword" name="keyword" type="hidden" value="${keyword}"/>
<input id="m" name="m" type="hidden" value="${m}"/>
<input id="s" name="s" type="hidden" value="${s}"/>
</form>
</html>
<script language=javascript>
function checkf() {
	if(commonForm.claimarchiveid.value=="" || commonForm.claimarchiveid.value!="" && !is_int(commonForm.claimarchiveid.value)) {alert("请正确输入案卷ID！");return false;}
	if(commonForm.serialnumber.value=="") {alert("请正确输入序号！");return false;}
	if(commonForm.insuredid.value=="" || commonForm.insuredid.value!="" && !is_int(commonForm.insuredid.value)) {alert("请正确输入被保人！");return false;}
	if(commonForm.bardh.value=="") {alert("请正确输入报案人电话！");return false;}

	document.getElementById("commonForm").submit() ;
  
}

function checkdelete() {
	if(confirm("是否删除当前数据记录？")==false) {
		return ;
	} else {
		document.getElementById("deleteForm").submit() ;
	}
	
}
$(function() {
	jQuery.curCSS = jQuery.css;
    
    $("#info_emDisease").autocomplete({
    	source: "/emDisease.do?method=autocomplete",
	    select: function( event, ui ) {
	    	$("#info_diseaseid").val(ui.item.id);
	    }
    });
 });
</script>