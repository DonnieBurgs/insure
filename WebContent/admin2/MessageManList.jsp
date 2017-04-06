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
<title>消息会话列表</title>
</head>
<body id="right">
<table id="topTable" class="tform" height="100%">
<tr>
<td id="tableLeft" width="40%" class="left" valign=top>

<div class="operation o-h bg-f8">
	<div class="operationLeft f-l"><font style="margin-left:10px;font-size:14px; font-weight:bold; text-indent:14px; letter-spacing:2px;">消息会话列表</font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		</div>
	<div class="operationRight f-r">
	<c:if test="${auth52 eq '1'}"><a href="/emMessageSession.do?method=blank&uf_parentid=${uf_parentid}&keyword=${keyword}&m=${m}&s=${s}" style="margin-top:10px;"><i class="iconfont">&#xe681;</i>新增消息会话</a></c:if>
	</div>
</div>
<c:if test="${not empty adminList}">
<table class="tlist">
	 <thead>
	 <tr class="title">
	 <th width=10%>客服</th>
<c:forEach var="itemList" items="${adminList}" varStatus="idx">
	 <th align="left" style="background-color:#ffffff;text-align:left;">
	 	<a href="javascript:manClick(${itemList.userid},'${itemList.account}');"><i class="iconfont">&#xe632;</i>${itemList.username}</a>
	 </th>
	 <c:if test="${idx.index eq 3 or idx.index eq 8 or idx.index eq 13 or idx.index eq 18 or idx.index eq 23 or idx.index eq 28 or idx.index eq 33}"></tr><tr class="title"></c:if>
</c:forEach>
	 </tr></thead>
	 <tbody>

	 </tbody>
</table>
</c:if>
<table class="tlist">
	 <form action="/emMessageMan.do?method=list" id="searchForm" name="searchForm" method="post">
	 <thead>
	 <tr class="title">
	 <th width=10%>关键字</th>
	 <th align="left" style="background-color:#ffffff;text-align:left;">
	 	<input type="text" id="keyword" name="keyword" value="${keyword}">
	 </th>
	 	<th width="100" class="operationBtn">
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
<th>会员姓名</th>
<th>手机</th>
<th>消息数量</th>
<th width="60">操作</th>
		</tr>
	</thead>
	<tbody>
		
<c:choose>
<c:when test="${not empty resultRows}">
<c:forEach var="item" items="${resultRows}">
			<tr>
<td>${item.username}</td>
<td>${item.mobile}</td>
<td>${item.messagecount}</td>

				<td width="250" class="operationBtn">
				<c:if test="${auth52 eq '1'}"><a href="javascript:userClick(${item.userid});" style="margin-left:0px;"><i class="iconfont">&#xe6d6;</i>发消息</a>
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
	
			String u = "/emMessageSession.do?method=list&keyword=" + keyword + "&uf_parentid=" + uf_parentid + "&m=" + pageSize;
			String u1 = "/emMessageSession.do?method=list&keyword=" + keyword + "&uf_parentid=" + uf_parentid;
	
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
<form action="/emMessageSession.do?method=delete" id="deleteForm" name="deleteForm" method="post">
<input type="hidden" id="messagesessionid" name="messagesessionid" value="">
<input type="hidden" id="t" name="t" value="">
<input type="hidden" name="uf_parentid" value="${uf_parentid}">
<input type="hidden" name="key_isvalid" value="${key_isvalid }">
<input type="hidden" name="keyword" value="${keyword }">
<input type="hidden" name="m" value="${m }">
<input type="hidden" name="s" value="${s }">
</form>
<script type="text/javascript">
var bodyHeight = document.documentElement.clientHeight;//获取窗口可视宽度
var curMan = ${user.userid};
var curAccount = "${user.account}";
$(document).ready(function(){
	var toptable = document.getElementById('topTable');
	var tableleft = document.getElementById('tableLeft');
	var tableright = document.getElementById('tableRight');
	toptable.style.height=(bodyHeight-100)+'px';
	tableleft.style.height=(bodyHeight-100)+'px';
	tableright.style.height=(bodyHeight-100)+'px';
	var mFrame = document.getElementById('msgFrame');
	mFrame.src="http://120.77.245.252/emt/v-v1-zh_CN-/emt/index.w?device=m#!messageSession//{\"account\":\"" + curAccount + "\",\"r\":\"" + Math.random() + "\"}";
//	alert(bodyHeight);
});
function submitform(){
	//alert("ss");
	searchForm.submit();
}
function manClick(manid, account){
	curMan = manid;
	curAccount = account;
	var mFrame = document.getElementById('msgFrame');
	mFrame.src="";
	setTimeout("manClick1(" + account + ")",1000);
	//mFrame.src="http://120.77.245.252/emt/v-v1-zh_CN-/emt/index.w?device=m#!messageSession//{\"account\":\"" + account + "\",\"r\":\"" + Math.random() + "\"}";
	//http://120.77.245.252/emt/v-nYFrQ3-zh_CN-/emt/index.w?language=zh_CN&skin=#!messageSession//{"uuid":"C76D47806E300001936112B819A91862","from":"orderlist"}
	//http://localhost/x5/UI2/v_/emt/index.w?device=m#!messageSession//{"account":"13652273783"}
	//http://localhost/x5/UI2/v_/emt/index.w?device=m#!message//{"account":"13711553301","userid":"10009"}
}
function manClick1(account){
	var mFrame = document.getElementById('msgFrame');
	mFrame.src="http://120.77.245.252/emt/v-v1-zh_CN-/emt/index.w?device=m#!messageSession//{\"account\":\"" + account + "\",\"r\":\"" + Math.random() + "\"}";

}
function userClick(userid){
	var mFrame = document.getElementById('msgFrame');
	mFrame.src="";
	setTimeout("userClick1(" + userid + ")",1000);
}
function userClick1(userid){
	var mFrame = document.getElementById('msgFrame');
	mFrame.src="http://120.77.245.252/emt/v-v1-zh_CN-/emt/index.w?device=m#!message//{\"account\":\"" + curAccount + "\",\"userid\":\"" + userid + "\",\"r\":\"" + Math.random() + "\"}";
}
</script>

</td>
<td width="50" height="700" style="padding:0px;">
</td>
<td id="tableRight" width="600" height="700" style="padding:0px;">
<iframe name="msgFrame" id="msgFrame" src="" width="500" height=100% frameborder=0/>
</td>
</tr>
</table>
gggg
</body>

</html>