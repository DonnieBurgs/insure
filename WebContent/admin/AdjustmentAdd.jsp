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
<link href="/resources/js/jquery-ui/jquery-ui.min.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="/resources/js/jquery-ui/jquery-ui.min.js"></script>
<script type="text/javascript">var root = "";</script>
<script type="text/javascript" src="/resources/js/admin.js"></script>
<script type="text/javascript" src="/resources/js/common.js"></script><script type="text/javascript" src="/resources/js/My97DatePicker/config.js"></script>
<script type="text/javascript" src="/resources/js/My97DatePicker/lang/zh-cn.js"></script>
<link rel="stylesheet" type="text/css" href="/resources/js/My97DatePicker/skin/WdatePicker.css" />
<link rel="stylesheet" type="text/css" href="/resources/js/My97DatePicker/skin/default/datepicker.css" />
<script type="text/javascript" src="/resources/js/My97DatePicker/WdatePicker.js"></script>
<title>理算列表</title>
<style type="text/css">
#fff input{width: 120px;}
#fff span{color: red;}
</style>
</head>
<body id="right">
<div class="operation o-h bg-f8">
	<div class="operationLeft f-l"><font style="margin-left:10px;font-size:14px; font-weight:bold; text-indent:14px; letter-spacing:2px;">理算列表</font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<!--<c:if test="${auth44 eq '1'}"><a href="/emDetermine.do?method=blank&uf_parentid=${uf_parentid}&keyword=${keyword}&m=${m}&s=${s}" style="margin-left:100px;"><i class="iconfont">&#xe681;</i>新增理算</a></c:if>--></div>
	<div class="operationRight f-r">
	</div>
</div>

<table class="tlist">
	 <thead>
	 <tr class="title">
	 <th width=10%>案卷</th>
	  <th width=30% align=left style="background-color:#ffffff;text-align:left;">
	 	<input type="text" id="emClaimArchive" name="emClaimArchive" value="">
	 </th>
	 <th width=10%>案件</th>
	 <th width=30% align=left style="background-color:#ffffff;text-align:left;">
	 	<input type="text" id="emClaim" name="emClaim" value="">
	 </th>
	 	<th width="20%" class="operationBtn">
	 	</th>
	 </tr></thead>
</table>

<table width="100%" id="fff">
<tr>
<td width="48%">
<form id="commonForm" action="/emDetermine.do?method=add" method="post">
	 	<input type="hidden" id="claimarchiveid" name="claimarchiveid" value="${claimarchiveid}">
	 	<input type="hidden" id="claimid" name="claimid" value="${claimid}">
<br/>
<span>发票信息</span>
	<table class="tform">
		<tr>
		<td>发票号</td><td colspan="3">医院名称</td>
		</tr>
		<tr>
		<td><input type="text" id="receiptr_eceiptnumber" name="receiptr.receiptnumber" value=""></td>
		<td colspan="3"><input type="hidden" id="receiptr_hospitalid" name="receiptr.hospitalid" value="">
		<input type="text" id="emHospital" name="emHospital" value=""></td>
		</tr>
		<tr>
		<td>就诊日期</td><td>住院日期</td><td>出院日期</td>
		</tr>
		<tr>
		<td><input type="text" id="receiptr_visitdate" name="receiptr.visitdate" readonly="readonly" value="" onclick="WdatePicker({maxDate:'2018-12-31', dateFmt:'yyyy-MM-dd'})" style="width: 70px;"/></td>
		<td><input type="text" id="receiptr_hospitaldate" name="receiptr.hospitaldate" readonly="readonly" value="" onclick="WdatePicker({maxDate:'2018-12-31', dateFmt:'yyyy-MM-dd'})" style="width: 70px;"/></td>
		<td><input type="text" id="receiptr_dischargedate" name="receiptr.dischargedate" readonly="readonly" value="" onclick="WdatePicker({maxDate:'2018-12-31', dateFmt:'yyyy-MM-dd'})" style="width: 70px;"/></td>
		</tr>
		<tr>
		<td>统筹金额</td><td>个人缴费</td><td>合计</td>
		</tr>
		<tr>
		<td><input type="text" id="receiptr_fundpaid" name="receiptr.fundpaid" value=""></td>
		<td><input type="text" id="receiptr_cashpaid" name="receiptr.cashpaid" value=""></td>
		<td><input type="text" id="receiptr_total" name="receiptr.total" value=""></td>
		</tr> 
	</table>
	<br/>
<span>诊断信息</span>
	<table class="tform">
		<tr>
		<td>疾病</td><td>赔付类型</td><td>赔付状态</td>
		</tr>
		<tr>
		<td><input type="hidden" id="diseaseid" name="diseaseid" value=""><input type="text" id="emDisease" name="emDisease" value=""></td>
		<td>
		<select id="claimtype" name="claimtype">
		<option value="">请选择</option>
		<c:forEach var="it" items="${claimtypeItems }"><option value="${it.key }">${it.value}</option></c:forEach>
		</select>
		</td>
		<td><select id="claimstatus" name="claimstatus">
		<option value="">请选择</option>
		<c:forEach var="it" items="${claimstatusItems }"><option value="${it.key }">${it.value}</option></c:forEach>
		</select></td>
		</tr>
		<tr>
		<td>床位费</td><td>他方赔付金额</td><td>公共保额</td>
		</tr>
		<tr>
		<td><input type="text" id="cwfamount" name="cwfamount" value=""></td>
		<td><input type="text" id="tfpfamount" name="tfpfamount" value=""></td>
		<td><input type="text" id="sqamount" name="sqamount" value=""></td>
		</tr>
		<tr>
		<td colspan="6">扣除原因</td>
		</tr>
		<tr>
		<td colspan="6"><input type="text" id="dereason" name="dereason" value=""></td>
		</tr> 
	</table>
	<br/>
<span>医药费</span>
	<table class="tform">
		<tr>
		<td>医药费</td><td>精神病医药费</td><td>自费医药费</td>
		</tr>
		<tr>
		<td><input type="text" id="yyfamount" name="yyfamount" value=""></td>
		<td><input type="text" id="jsbyyfamount" name="jsbyyfamount" value=""></td>
		<td><input type="text" id="zfyyfamount" name="zfyyfamount" value=""></td>
		</tr>
		<tr>
		<td>自费医药费说明</td><td>医药费剔除金额</td><td>医药费剔除说明</td>
		</tr>
		<tr>
		<td><input type="text" id="fzyyfremark" name="fzyyfremark" value=""></td>
		<td><input type="text" id="yyftcamount" name="yyftcamount" value=""></td>
		<td><input type="text" id="yyfremark" name="yyfremark" value=""></td>
		</tr>
	</table>
	<br/>
<span>检查费</span>
	<table class="tform">
		<tr>
		<td>检查费</td><td>高科技检查费</td><td>高科技检查结果</td>
		</tr>
		<tr>
		<td><input type="text" id="jcfamount" name="jcfamount" value=""></td>
		<td><input type="text" id="gkjjcfamount" name="gkjjcfamount" value=""></td>
		<td>
		<select id="gkjjcjgpool" name="gkjjcjgpool">
		<option value="">请选择</option>
		<option value="0">正常</option>
		<option value="1">异常</option>
		</select></td>
		</tr>
		<tr>
		<td>自费检查费</td><td>自费检查费说明</td><td>检查费剔除金额</td>
		</tr>
		<tr>
		<td><input type="text" id="zfjcfamount" name="zfjcfamount" value=""></td>
		<td><input type="text" id="zfjcfremark" name="zfjcfremark" value=""></td>
		<td><input type="text" id="jcftcamount" name="jcftcamount" value=""></td>
		</tr>
		<tr>
		<td colspan="6">检查费剔除说明</td>
		</tr>
		<tr>
		<td colspan="6"><input type="text" id="jcftcremark" name="jcftcremark" value=""></td>
		</tr> 
	</table>
	<br/>
<span>特殊费用</span>
	<table class="tform">
		<tr>
		<td>精神类疾病</td><td>牙科自费项目</td><td>康复治疗及物理治疗</td>
		</tr>
		<tr>
		<td><input type="text" id="mentalillnessamount" name="mentalillnessamount" value=""></td>
		<td><input type="text" id="dentistryamount" name="dentistryamount" value=""></td>
		<td><input type="text" id="rehabilitationamount" name="rehabilitationamount" value=""></td>
		</tr>
	</table>
	
	<br/>
	<br/>
	<table>
		<tr>
			<td colspan="2">

				<input id="uf_parentid" name="uf_parentid" type="hidden" value="${uf_parentid}"/>
				<input id="keyword" name="keyword" type="hidden" value="${keyword}"/>
				<input id="m" name="m" type="hidden" value="${m}"/>
				<input id="s" name="s" type="hidden" value="${s}"/>
				<input type="button" class="btn1" value=" 提  交 " onclick="checkf()"/>
			</td>
		</tr>
	</table>
</td>
<td width="2%"></td>
<td width="48%" valign="top">
<form action="" id="op" name="op" method="get">
<table class="tlist">
	<thead>
		<tr class="title">
<th>案件ID</th>
<th>疾病ID</th>
<th>赔付类型</th>
<th>赔付状态</th>
<th>床位费</th>
		</tr>
	</thead>
	<tbody>
		
<c:choose>
<c:when test="${not empty resultRows}">
<c:forEach var="item" items="${resultRows}">
			<tr>
<td>${item.claimid}</td>
<td>${item.diseaseid}</td>
<td>${item.claimtype}</td>
<td>${item.claimstatus}</td>
<td>${item.cwfamount}</td>
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
	
			String u = "/emDetermine.do?method=list&keyword=" + keyword + "&uf_parentid=" + uf_parentid + "&m=" + pageSize;
			String u1 = "/emDetermine.do?method=list&keyword=" + keyword + "&uf_parentid=" + uf_parentid;
	
			out.write("<div class='bar'><span class=\"disabled\">共: " + totalCount + " 条记录</span> <span class=\"disabled\">每页显示: "
					+ "<a href=\"" + u1 + "&m=15\"" + (pageSize==15?" class=\"current\"":"") + ">15</a>"
					+ "<a href=\"" + u1 + "&m=40\"" + (pageSize==40?" class=\"current\"":"") + ">40</a>"
					+ "<a href=\"" + u1 + "&m=100\"" + (pageSize==100?" class=\"current\"":"") + ">100</a>") ;
			if (pageCount > 1) {
				
				buffer.append("<font style=\"margin-left:250px;\"></font>页码：");
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
	
</form></td></tr> </table>
<form action="/emDetermine.do?method=delete" id="deleteForm" name="deleteForm" method="post">
<input type="hidden" id="id" name="id" value="">
<input type="hidden" id="t" name="t" value="">
<input type="hidden" name="uf_parentid" value="${uf_parentid}">
<input type="hidden" name="key_isvalid" value="${key_isvalid }">
<input type="hidden" name="keyword" value="${keyword }">
<input type="hidden" name="m" value="${m }">
<input type="hidden" name="s" value="${s }">
</form>
</body>
<script type="text/javascript">
function submitform(){
	searchForm.submit();
}
function deleteDetermine(id){
	if(confirm("是否删除会员资料？")) {
		$("#id").val(id);
		$("#t").val(t);
		deleteForm.submit();
		
	}
}
function checkf() {
	
	
	var claimid = $("#claimid").val();
	if(claimid=="" || (claimid!="" && !is_int(claimid))) {alert("请正确输入案件ID！");return false;}
	
	if($("#receiptr_eceiptnumber").val() == "") {alert("请正确输入发票号！");return false;}
	if($("#receiptr_hospitalid").val() == "") {alert("请正确输入医院ID！");return false;}
	if($("#receiptr_visitdate").val() == "") {alert("请正确输入就诊日期！");return false;}
	if($("#receiptr_hospitaldate").val() == "") {alert("请正确输入住院日期！");return false;}
	if($("#receiptr_dischargedate").val() == "") {alert("请正确输入出院日期！");return false;}
	var fundpaid = $("#receiptr_fundpaid").val();
	if(fundpaid == "" || !(is_float(fundpaid) || is_int(fundpaid))) {alert("请正确输入统筹金额！");return false;}
	var cashpaid = $("#receiptr_cashpaid").val();
	if(cashpaid =="" || !(is_float(cashpaid) || is_int(cashpaid))) {alert("请正确输入个人缴费！");return false;}
	var total = $("#receiptr_total").val();
	if(total=="" || !(is_float(total) || is_int(total))) {alert("请正确输入合计！");return false;}

	
	if(commonForm.diseaseid.value=="" || commonForm.diseaseid.value!="" && !is_int(commonForm.diseaseid.value)) {alert("请正确输入疾病ID！");return false;}
	if(commonForm.claimtype.value=="") {alert("请正确输入赔付类型！");return false;}
	if(commonForm.claimstatus.value=="" || commonForm.claimstatus.value!="" && !is_int(commonForm.claimstatus.value)) {alert("请正确输入赔付状态！");return false;}


	
	
	document.getElementById("commonForm").submit() ;
  
}
$(function() {
	jQuery.curCSS = jQuery.css;
    $("#emClaimArchive").autocomplete({
    	source: "/emClaimArchive.do?method=autocomplete",
	    select: function( event, ui ) {
	    	$("#claimarchiveid").val(ui.item.id);
			var id = ui.item.id;
			$("#claimid").val("");
			$("#emClaim").val("");
	        $("#emClaim").autocomplete({
	        	source: "/emClaim.do?method=autocomplete&claimarchiveid=" + id,
	    	    select: function( event, ui ) {
	    	    	$("#claimid").val(ui.item.id);
	    	    	
	    	    }
	        });
	    }
    });
    $("#emDisease").autocomplete({
    	source: "/emDisease.do?method=autocomplete",
	    select: function( event, ui ) {
	    	$("#diseaseid").val(ui.item.id);
	    }
    });
    $("#emHospital").autocomplete({
    	source: "/emHospital.do?method=autocomplete",
	    select: function( event, ui ) {
	    	$("#receiptr_hospitalid").val(ui.item.id);
	    }
    });
    $("#dereason").autocomplete({
    	source: "/emDereason.do?method=autocomplete"
    });
 });
</script>
</html>