<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="dict" uri="/WEB-INF/dict_tag.tld" %>
<%@ taglib prefix="util" uri="/WEB-INF/util.tld" %>
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
<title>发票列表</title>
<style type="text/css">
input[type="text"]{min-width:50px;width:70px;}
</style>
</head>
<body id="right">
<div class="operation o-h bg-f8">
	<div class="operationLeft f-l"><font style="margin-left:10px;font-size:14px; font-weight:bold; text-indent:14px; letter-spacing:2px;">发票列表</font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		</div>
	<div class="operationRight f-r">
	</div>
</div>
<table class="tlist">
	 <form action="/emReceipt.do?method=list" id="searchForm" name="searchForm" method="post">
	 <thead>
	 <tr class="title">
	 <th width=10%>关键字</th>
	 <th width=80% align=left style="background-color:#ffffff;text-align:left;">
	 	<input type="text" id="keyword" name="keyword" value="${keyword}">
	 </th>
	 	<th width="20%" class="operationBtn">
	 		<a href="javascript:submitform();"><i class="iconfont">&#xe608;</i>搜索</a>
	 	</th>
	 </tr></thead>
	 <tbody>

	 </tbody>
		<input id="uf_parentid" name="uf_parentid" type="hidden" value="${uf_parentid}"/>
	 </form>
</table>

	 
<form action="" id="op" name="op" method="get">
<table class="tlist">
	<thead>
		<tr class="title">
<th>案件ID</th>
<th>账单序号</th>
<th>被保险人序号</th>
<th>账单类型</th>
<th>发票号</th>

<th width="60">操作</th>
		</tr>
	</thead>
	<tbody>
		
<c:choose>
<c:when test="${not empty resultRows}">
<c:forEach var="item" items="${resultRows}">
			<tr>
<td>${item.claimid}</td>
<td>${item.receiptno}</td>
<td>${item.insuredno}</td>
<td>${item.receipttype}</td>
<td>${item.receiptnumber}</td>

				<td width="250" class="operationBtn">
				<c:if test="${auth44 eq '1'}"><a href="/emReceipt.do?method=list&claimid=${emClaim.id}&receiptid=${item.id}&keyword=${keyword}&uf_parentid=${uf_parentid}&m=${m}&s=${s}" style="margin-left:0px;"><i class="iconfont">&#xe6d6;</i>查看/编辑</a>
					<a href="javascript:deleteReceipt(${item.id}, 1)" style="margin-left:10px;"><i class="iconfont">&#xe636;</i>删除</a>
				</c:if>
				<c:if test="${auth44 ne '1'}"><a href="/emReceipt.do?method=list&claimid=${emClaim.id}&receiptid=${item.id}&keyword=${keyword}&uf_parentid=${uf_parentid}&m=${m}&s=${s}" style="margin-left:0px;"><i class="iconfont">&#xe6d6;</i>查看</a>
				</c:if>
				</td>
			</tr>
</c:forEach>
</c:when>
<c:otherwise>
			<tr><td colspan=5>
      <p><span>非常抱歉！</span></p><p>没有找到与您选择的搜索条件相符的资料。</p>
    	</td></tr>
</c:otherwise>
</c:choose>
		
	</tbody>
	<tfoot>
		<tr>
			<td colspan="7"></td>
		</tr>
	</tfoot>
</table>
<div>
<%@ include file="/inc/pagebar_s.jsp"%> <%
	startIndex = 1;
	pageSize = 15;
	pageIndexCount = 10;
	pageCount = 0;
	totalCount = 0;
	int[] indexs = new int[0];
	String parameters;


	String _page = (String)request.getAttribute("s");
	if (_page != null && !_page.equals("")) {
		startIndex = Integer.parseInt(_page);
	}

	String _maxRows = (String)request.getAttribute("m");
	if (_maxRows != null && !_maxRows.equals("")) {
		pageSize = Integer.parseInt(_maxRows);
	}
	
	if(request.getAttribute("totalRow") != null){
		Integer _totalCount = ((Long) request.getAttribute("totalRow")).intValue();
	
		if (_totalCount != null && _totalCount != 0) {
			totalCount = _totalCount;
			if (totalCount > 0) {
				pageCount = totalCount / pageSize + (totalCount % pageSize > 0 ? 1 : 0);
			} else {
				totalCount = 0;
			}
	
			if (startIndex >= pageCount) {
				startIndex = pageCount;
			} else if (startIndex <= 0) {
				startIndex = 1;
			}
			if (pageIndexCount > pageCount) {
				pageIndexCount = pageCount;
			}
	
			indexs = new int[pageIndexCount];
			int istart = startIndex - pageIndexCount / 2 + (pageIndexCount % 2 > 0 ? 0 : 1);
	
			int iend = startIndex + pageIndexCount / 2;
			if (istart <= 0) {
				istart = 1;
				iend = pageIndexCount;
			}
			if (iend > pageCount) {
				iend = pageCount;
				istart = pageCount - pageIndexCount + 1;
			}
			for (int i = 0; i < iend - istart + 1; i++) {
				indexs[i] = istart + i;
			}
	
			StringBuilder buffer = new StringBuilder();
	
			String u = "/emReceipt.do?method=list&keyword=" + keyword + "&uf_parentid=" + uf_parentid + "&m=" + pageSize;
			String u1 = "/emReceipt.do?method=list&keyword=" + keyword + "&uf_parentid=" + uf_parentid;
	
			out.write("<div class='bar'><span class=\"disabled\">共: " + totalCount + " 条记录</span> <span class=\"disabled\">每页显示: "
					+ "<a href=\"" + u1 + "&m=15\"" + (pageSize==15?" class=\"current\"":"") + ">15</a>"
					+ "<a href=\"" + u1 + "&m=40\"" + (pageSize==40?" class=\"current\"":"") + ">40</a>"
					+ "<a href=\"" + u1 + "&m=100\"" + (pageSize==100?" class=\"current\"":"") + ">100</a>") ;
			if (pageCount > 1) {
				
				buffer.append("<font style=\"margin-left:250px;\"></font>页码");
				if (startIndex > 1) {
					buffer.append("<a href=\"" + u + "&s=" + (startIndex-1) + "\">上一页</a>");
					if (startIndex > this.getFirstIndex() + 5 && indexs.length < pageCount) {
						buffer.append("<a href=\"" + u + "&s=" + getFirstIndex() + "\">" + this.getFirstIndex() + " ...</a>");
					}
				}
				for (int index : indexs) {
					buffer.append("<a href=\"" + u + "&s=" + index + "\"" + (startIndex == index?" class=\"current\"":"") + ">").append(index).append("</a>");
				}
	
				if (startIndex < pageCount) {
					if (startIndex < this.getLastIndex() - 5 && indexs.length < pageCount) {
						buffer.append("<a href=\"" + u + "&s=" + getLastIndex() + "\">... ").append(this.getLastIndex()).append("</a>");
					}
					buffer.append("<a href=\"" + u + "&s=" + getNextIndex() + "\">下一页</a>");
				}
	
				out.write(buffer.toString());
			}
			out.write("</span></div>");			
		}
	}
%>
	
</form>
<form action="/emReceipt.do?method=delete" id="deleteForm" name="deleteForm" method="post">
<input type="hidden" id="id" name="id" value="${emReceipt.id }">
<input type="hidden" id="t" name="t" value="">
<input type="hidden" name="uf_parentid" value="${emClaim.id}">
<input type="hidden" name="key_isvalid" value="${key_isvalid }">
<input type="hidden" name="keyword" value="${keyword }">
<input type="hidden" name="m" value="${m }">
<input type="hidden" name="s" value="${s }">
</form>
<br/>
<br/>
<span>发票信息</span>
<form id="commonForm" action="/emReceipt.do?method=add" method="post">
<input type="hidden" id="id" name="id" value="${emReceipt.id }">
<input type="hidden" id="claimid" name="claimid" value="${param.claimid }">
<input type="hidden" id="hospitalid" name="hospitalid" value="${emReceipt.hospitalid }">
	<table class="tform">
<tr>
<td><font color="red">*</font>案件</td>
<td colspan="9"><dict:itemdesc name="serialnumber" value="${param.claimid  }" table="em_claim" path="id"/></td>
</tr>
<tr>
<td class="right"><font color="red">*</font>账单序号</td>
<td><input type="text" id="receiptno" name="receiptno" value="${empty receiptno ? emReceipt.receiptno : receiptno}" style="min-width:50px;width:70px;"></td>
<td  class="right"><font color="red">*</font>被保险人序号</td>
<td><input type="text" id="insuredno" name="insuredno" value="${emClaim.serialnumber }" readonly="readonly"></td>
<td  class="right"><font color="red">*</font>账单类型</td>
<td><select id="receipttype" name="receipttype">
		<c:forEach var="it" items="${receipttypeMap }"><option value="${it.key }" <c:if test="${it.key eq emReceipt.receipttype }">selected="selected"</c:if>>${it.value}</option></c:forEach>
		</select></td>
<td   class="right"><font color="red">*</font>发票号</td>
<td><input type="text" id="receiptnumber" name="receiptnumber" value="${emReceipt.receiptnumber }"></td>
<td   class="right"><font color="red">*</font>费用类型</td>
<td><select id="feetype" name="feetype">
		<c:forEach var="it" items="${feetypeMap }"><option value="${it.key }" <c:if test="${it.key eq emReceipt.feetype }">selected="selected"</c:if>>${it.value}</option></c:forEach>
		</select></td>
</tr>
<tr>
<td class="right"><font color="red">*</font>医院</td>
<td colspan="3"><input type="text" id="emHospital" name="emHospital" value="<dict:itemdesc name="hospitalname" value="${emReceipt.hospitalid}" table="em_hospital" path="id"/>" style="width:280px;"></td>
<td class="right"><font color="red">*</font>就诊日期</td>
<td><input type="text" id="visitdate" name="visitdate" readonly="readonly" value="${emReceipt.visitdate_ }" onclick="WdatePicker({maxDate:'2018-12-31', dateFmt:'yyyy-MM-dd'})" style="width: 70px;"/></td>
<td class="right"><font color="red">*</font>住院日期</td>
<td><input type="text" id="hospitaldate" name="hospitaldate" readonly="readonly" value="${emReceipt.hospitaldate_ }" onclick="WdatePicker({maxDate:'2018-12-31', dateFmt:'yyyy-MM-dd'})" style="width: 70px;"/></td>
<td class="right"><font color="red">*</font>出院日期</td>
<td><input type="text" id="dischargedate" name="dischargedate" readonly="readonly" value="${emReceipt.dischargedate_ }" onclick="WdatePicker({maxDate:'2018-12-31', dateFmt:'yyyy-MM-dd'})" style="width: 70px;"/></td>
</tr>
<tr>
<td class="right">申报日期</td>
<td><input type="text" id="claimdate" name="claimdate" readonly="readonly" value="${emReceipt.claimdate_ }" onclick="WdatePicker({maxDate:'2018-12-31', dateFmt:'yyyy-MM-dd'})" style="width: 70px;"/></td>
<td class="right">就医类型</td>
<td><select id="medicaltype" name="medicaltype">
		<c:forEach var="it" items="${medicaltypeMap }"><option value="${it.key }" <c:if test="${it.key eq emReceipt.medicaltype }">selected="selected"</c:if>>${it.value}</option></c:forEach>
		</select></td>
<td class="right">统筹金额</td>
<td><input type="text" id="fundpaid" name="fundpaid" value="${emReceipt.fundpaid }"></td>
<td class="right">个人缴费</td>
<td><input type="text" id="cashpaid" name="cashpaid" value="${emReceipt.cashpaid }"></td>
<td class="right">合计</td>
<td><input type="text" id="total" name="total" value="${emReceipt.total }"></td>
</tr>
<tr>
<td class="right">就诊区域</td>
<td colspan="3"><select id="area" name="area">
		<c:forEach var="it" items="${areaMap }"><option value="${it.key }" <c:if test="${it.key eq emReceipt.area }">selected="selected"</c:if>>${it.value}</option></c:forEach>
		</select></td>
<td class="right">精神类疾病</td>
<td><input type="text" id="mentalillnessamount" name="mentalillnessamount" value="${emReceipt.mentalillnessamount }"></td>
<td class="right">牙科自费项目</td>
<td><input type="text" id="dentistryamount" name="dentistryamount" value="${emReceipt.dentistryamount }"></td>
<td class="right">康复治疗及物理治疗</td>
<td><input type="text" id="rehabilitationamount" name="rehabilitationamount" value="${emReceipt.rehabilitationamount }"></td>
</tr>
</table>

	<table class="tform" id="receiptinfoRows">

<tr>
<td><font color="red">*</font>费用项目</td>
<td><font color="red">*</font>费用金额</td>
<td><font color="red">*</font>自费金额</td>
<td><font color="red">*</font>自费描述</td>
<td><font color="red">*</font>医保支付金额</td>
<td><font color="red">*</font>医保支付金额原因</td>
</tr>
<c:choose>
<c:when test="${not empty receiptinfoRows }">
<c:forEach var="v" items="${receiptinfoRows }" varStatus="i">
<tr>
<td>
<select id="feeid_${i.index }" name="feeid">
		<c:forEach var="it" items="${feeRows }"><option value="${it.id }" <c:if test="${it.id eq v.feeid}">selected="selected"</c:if>>${it.feecode} ${it.feename }</option></c:forEach>
		</select>
</td>
<td><input type="text" id="fee_${i.index }" name="fee" value="${v.fee }"></td>
<td><input type="text" id="zfamount_${i.index }" name="zfamount" value="${v.zfamount }"></td>
<td><input type="text" id="zfmark_${i.index }" name="zfmark" value="${v.zfmark }" style="width: 150px;"></td>
<td><input type="text" id="ybzfamount_${i.index }" name="ybzfamount" value="${v.ybzfamount }"></td>
<td><input type="text" id="ybzfmark_${i.index }" name="ybzfmark" value="${v.ybzfmark }" style="width: 150px;"></td>
</tr>
</c:forEach>
</c:when>
<c:otherwise>
<c:if test="${emClaimInfo.claimtype eq 1 }">
<c:set var="feecodeIds" value="316"/>
</c:if>
<c:if test="${emClaimInfo.claimtype eq 2 }">
<c:set var="feecodeIds" value="101,104,118"/>
</c:if>
<c:if test="${emClaimInfo.claimtype eq 3 }">
<c:set var="feecodeIds" value="299"/>
</c:if>
<c:forTokens items="${feecodeIds }" delims="," varStatus="i" var="v">
<tr>
<td>
<select id="feeid_${i.index }" name="feeid">
		<c:forEach var="it" items="${feeRows }"><option value="${it.id }" <c:if test="${it.feecode eq v}">selected="selected"</c:if>>${it.feecode} ${it.feename }</option></c:forEach>
		</select>
</td>
<td><input type="text" id="fee_${i.index }" name="fee" value=""></td>
<td><input type="text" id="zfamount_${i.index }" name="zfamount" value=""></td>
<td><input type="text" id="zfmark_${i.index }" name="zfmark" value="" style="width: 150px;"></td>
<td><input type="text" id="ybzfamount_${i.index }" name="ybzfamount" value=""></td>
<td><input type="text" id="ybzfmark_${i.index }" name="ybzfmark" value="" style="width: 150px;"></td>
</tr>
</c:forTokens>
</c:otherwise>
</c:choose>
</table>
	
	
	<table>
	
		<tr>
			<td colspan="4">

				<input id="uf_parentid" name="uf_parentid" type="hidden" value="${uf_parentid}"/>
				<input id="keyword" name="keyword" type="hidden" value="${keyword}"/>
				<input id="m" name="m" type="hidden" value="${m}"/>
				<input id="s" name="s" type="hidden" value="${s}"/>
				<input type="button" class="btn1" value=" 提  交 " onclick="checkf()"/>
			</td>
		</tr>
	</table>
</form>

</body>
<script type="text/javascript">
function submitform(){
	searchForm.submit();
}
function deleteReceipt(id){
	if(confirm("是否删除会员资料？")) {
		$("#id").val(id);
		$("#t").val(t);
		deleteForm.submit();
		
	}
}
function checkf() {
	if(commonForm.claimid.value=="" || commonForm.claimid.value!="" && !is_int(commonForm.claimid.value)) {alert("请正确输入案件ID！");return false;}
	if(commonForm.receiptno.value=="" || commonForm.receiptno.value!="" && !is_int(commonForm.receiptno.value)) {alert("请正确输入账单序号！");return false;}
	if(commonForm.insuredno.value=="" || commonForm.insuredno.value!="" && !is_int(commonForm.insuredno.value)) {alert("请正确输入被保险人序号！");return false;}
	if(commonForm.receipttype.value=="" || commonForm.receipttype.value!="" && !is_int(commonForm.receipttype.value)) {alert("请正确输入账单类型！");return false;}
	if(commonForm.receiptnumber.value=="") {alert("请正确输入发票号！");return false;}
	if(commonForm.hospitalid.value=="" || commonForm.hospitalid.value!="" && !is_int(commonForm.hospitalid.value)) {alert("请正确输入医院ID！");return false;}
	if(commonForm.feetype.value=="" || commonForm.feetype.value!="" && !is_int(commonForm.feetype.value)) {alert("请正确输入费用类型！");return false;}
	if(commonForm.visitdate.value=="") {alert("请正确输入就诊日期！");return false;}
	if(commonForm.hospitaldate.value=="") {alert("请正确输入住院日期！");return false;}
	if(commonForm.dischargedate.value=="") {alert("请正确输入出院日期！");return false;}
	//if(commonForm.claimdate.value=="") {alert("请正确输入申报日期！");return false;}
	//if(commonForm.medicaltype.value=="" || commonForm.medicaltype.value!="" && !is_int(commonForm.medicaltype.value)) {alert("请正确输入就医类型！");return false;}
	if(commonForm.area.value=="") {alert("请正确输入就诊区域！");return false;}
	//if(commonForm.fundpaid.value=="" || commonForm.fundpaid.value!="" && !(is_float(commonForm.fundpaid.value) || is_int(commonForm.fundpaid.value))) {alert("请正确输入统筹金额！");return false;}
	//if(commonForm.cashpaid.value=="" || commonForm.cashpaid.value!="" && !(is_float(commonForm.cashpaid.value) || is_int(commonForm.cashpaid.value))) {alert("请正确输入个人缴费！");return false;}
	//if(commonForm.total.value=="" || commonForm.total.value!="" && !(is_float(commonForm.total.value) || is_int(commonForm.total.value))) {alert("请正确输入合计！");return false;}
	//if(commonForm.feeid.value=="" || commonForm.feeid.value!="" && !is_int(commonForm.feeid.value)) {alert("请正确输入费用项目ID！");return false;}
	//if(commonForm.fee.value=="" || commonForm.fee.value!="" && !(is_float(commonForm.fee.value) || is_int(commonForm.fee.value))) {alert("请正确输入床位费！");return false;}
	//if(commonForm.zfmark.value=="") {alert("请正确输入自费描述！");return false;}
	//if(commonForm.zfamount.value=="" || commonForm.zfamount.value!="" && !(is_float(commonForm.zfamount.value) || is_int(commonForm.zfamount.value))) {alert("请正确输入自费金额！");return false;}
	//if(commonForm.bfzfmark.value=="") {alert("请正确输入部分自费描述！");return false;}
	//if(commonForm.bfzfamount.value=="" || commonForm.bfzfamount.value!="" && !(is_float(commonForm.bfzfamount.value) || is_int(commonForm.bfzfamount.value))) {alert("请正确输入部分自费金额！");return false;}
	//if(commonForm.ybbzfmark.value=="") {alert("请正确输入医保不支付描述！");return false;}
	//if(commonForm.ybbzfamount.value=="" || commonForm.ybbzfamount.value!="" && !(is_float(commonForm.ybbzfamount.value) || is_int(commonForm.ybbzfamount.value))) {alert("请正确输入医保不支付金额！");return false;}
	//if(commonForm.ybzfmark.value=="") {alert("请正确输入医保支付金额原因！");return false;}
	//if(commonForm.ybzfamount.value=="" || commonForm.ybzfamount.value!="" && !(is_float(commonForm.ybzfamount.value) || is_int(commonForm.ybzfamount.value))) {alert("请正确输入医保支付金额！");return false;}
	//if(commonForm.mentalillnessamount.value=="" || commonForm.mentalillnessamount.value!="" && !(is_float(commonForm.mentalillnessamount.value) || is_int(commonForm.mentalillnessamount.value))) {alert("请正确输入精神类疾病！");return false;}
	//if(commonForm.dentistryamount.value=="" || commonForm.dentistryamount.value!="" && !(is_float(commonForm.dentistryamount.value) || is_int(commonForm.dentistryamount.value))) {alert("请正确输入牙科自费项目！");return false;}
	//if(commonForm.rehabilitationamount.value=="" || commonForm.rehabilitationamount.value!="" && !(is_float(commonForm.rehabilitationamount.value) || is_int(commonForm.rehabilitationamount.value))) {alert("请正确输入康复治疗及物理治疗！");return false;}

	var result;
	$("#receiptinfoRows tr").each(function(i, o){
		var label = $(o).find("select").find("option:selected").text();
		$(o).find("input").each(function(ii, oo){
			var val = $(oo).val();
			if(val == ''){
				var name = $(oo).attr("name");
				if(name == 'fee'){
					alert("请正确输入" + label + "费用金额！");
				}else if(name == 'zfamount'){
					alert("请正确输入" + label + "自费金额！");
				}else if(name == 'zfmark'){
					alert("请正确输入" + label + "自费描述！");
				}else if(name == 'ybzfamount'){
					alert("请正确输入" + label + "医保支付金额！");
				}else if(name == 'ybzfmark'){
					alert("请正确输入" + label + "医保支付金额原因！");
				}
				$(oo)[0].focus();
				result = true;
				return false;
			}
		});
		if(result)
			return false;
	});
	if(result)
		return false;
	
	document.getElementById("commonForm").submit() ;
  
}
$(function() {
	jQuery.curCSS = jQuery.css;
    
    $("#emFee").autocomplete({
    	source: "/emFee.do?method=autocomplete",
	    select: function( event, ui ) {
	    	$("#feeid").val(ui.item.id);
	    }
    });
    $("#emHospital").autocomplete({
    	source: "/emHospital.do?method=autocomplete",
	    select: function( event, ui ) {
	    	$("#hospitalid").val(ui.item.id);
	    }
    });
 });
</script>
</html>