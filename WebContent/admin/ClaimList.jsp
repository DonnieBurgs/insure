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
<link href="/resources/js/jquery-ui/themes/base/jquery-ui.css" rel="stylesheet" type="text/css" />
<link href="/resources/js/jquery-ui/themes/base/jquery.ui.autocomplete.css" rel="stylesheet" type="text/css" />
<script src="http://libs.baidu.com/jquery/1.8.3/jquery.min.js"></script>
<script type="text/javascript" src="/resources/js/jquery-ui.min.js"></script>
<script type="text/javascript">var root = "";</script>
<script type="text/javascript" src="/resources/js/admin.js"></script>
<script type="text/javascript" src="/resources/js/common.js"></script>
<script type="text/javascript" src="/resources/js/photo.js"></script>
<title>案件列表</title>
</head>
<body id="right">
<div class="operation o-h bg-f8">
	<div class="operationLeft f-l"><font style="margin-left:10px;font-size:14px; font-weight:bold; text-indent:14px; letter-spacing:2px;">案件列表</font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		</div>
	<div class="operationRight f-r">
	</div>
</div>
<table class="tlist">
	 <form action="/emClaim.do?method=list" id="searchForm" name="searchForm" method="post">
	 <thead>
	 <tr class="title">
	 <th width=10%>案卷</th>
	 <th width=80% align=left style="background-color:#ffffff;text-align:left;">
	 	<input type="hidden" id="keyword" name="keyword" value="${keyword}">
	 	<input type="text" id="emClaimArchive" name="emClaimArchive" value="<dict:itemdesc name="claimarchivenumber" value="${keyword}" table="em_claimarchive" path="id"/>">
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

<table width="100%">
<tr>
<td width="48%">
<c:if test="${auth44 eq '1'}">
<form id="commonForm" action="/emClaim.do?method=add" method="post">
<input type="hidden" id="claimarchiveid" name="claimarchiveid" value="${keyword}">
<input type="hidden" id="insuredid" name="insuredid">
	<table>
<tr>
<td>序号</td>
<td>被保人</td>
<td>报案人电话</td>
</tr>
<tr>
<td><input type="text" id="serialnumber" name="serialnumber" value="" style="width: 140px;"></td>
<td><input type="text" id="emInsured" name="emInsured" value="" style="width: 140px;"></td>
<td><input type="text" id="bardh" name="bardh" value="" style="width: 140px;"></td>
</tr>

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
</td><td></td>
<td width="48%">
<form action="" id="op" name="op" method="get">
<table class="tlist">
	<thead>
		<tr class="title">
<th>案卷</th>
<th>序号</th>
<th>被保人</th>
<th>报案人电话</th>
		</tr>
	</thead>
	<tbody>
		
<c:choose>
<c:when test="${not empty resultRows}">
<c:forEach var="item" items="${resultRows}">
			<tr>
<td><dict:itemdesc name="claimarchivenumber" value="${item.claimarchiveid}" table="em_claimarchive" path="id"/></td>
<td>${item.serialnumber}</td>
<td><dict:itemdesc name="insuredname" value="${item.insuredid}" table="em_insured" path="id"/></td>
<td>${item.bardh}</td>
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
	
			String u = "/emClaim.do?method=list&keyword=" + keyword + "&uf_parentid=" + uf_parentid + "&m=" + pageSize;
			String u1 = "/emClaim.do?method=list&keyword=" + keyword + "&uf_parentid=" + uf_parentid;
	
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
	
</form>
<form action="/emClaim.do?method=delete" id="deleteForm" name="deleteForm" method="post">
<input type="hidden" id="id" name="id" value="">
<input type="hidden" id="t" name="t" value="">
<input type="hidden" name="uf_parentid" value="${uf_parentid}">
<input type="hidden" name="key_isvalid" value="${key_isvalid }">
<input type="hidden" name="keyword" value="${keyword }">
<input type="hidden" name="m" value="${m }">
<input type="hidden" name="s" value="${s }">
</form>
</td>
</tr>
</table>
	 

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
	if(commonForm.insuredid.value=="" || commonForm.insuredid.value!="" && !is_int(commonForm.insuredid.value)) {alert("请正确输入被保人！");return false;}
	if(commonForm.bardh.value=="") {alert("请正确输入报案人电话！");return false;}

	document.getElementById("commonForm").submit() ;
  
}
$(function() {
	jQuery.curCSS = jQuery.css;
    $("#emClaimArchive").autocomplete({
    	source: "/emClaimArchive.do?method=autocomplete",
	    select: function( event, ui ) {
	    	$("#keyword").val(ui.item.id);
	    	$("#claimarchiveid").val(ui.item.id);
	    	
	    }
    });
    $("#emInsured").autocomplete({
    	source: "/emInsured.do?method=autocomplete",
	    select: function( event, ui ) {
	    	$("#insuredid").val(ui.item.id);
	    	
	    }
    });
 });
</script>
</html>