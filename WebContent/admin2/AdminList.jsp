<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<link type="text/css" rel="stylesheet" href="/admin/style/base.css" />
<link type="text/css" rel="stylesheet" href="/admin/style/global.css" />
<link href="/resources/css/main.css" rel="stylesheet" type="text/css" />
<script src="http://libs.baidu.com/jquery/1.8.3/jquery.min.js"></script>
<script type="text/javascript" src="/resources/lhgdialog/lhgcore.min.js"></script>
<script type="text/javascript" src="/resources/lhgdialog/lhgdialog.js?s=areo_blue?s=areo_blue"></script>
<script type="text/javascript" src="/resources/js/xheditor/xheditor-zh-cn.min.js"></script>
<script type="text/javascript">var root = "";</script>
<script type="text/javascript" src="/resources/js/admin.js"></script>
<script type="text/javascript" src="/resources/js/My97DatePicker/config.js"></script>
<script type="text/javascript" src="/resources/js/My97DatePicker/lang/zh-cn.js"></script>
<link rel="stylesheet" type="text/css" href="/resources/js/My97DatePicker/skin/WdatePicker.css" />
<link rel="stylesheet" type="text/css" href="/resources/js/My97DatePicker/skin/default/datepicker.css" />
<script type="text/javascript" src="/resources/js/My97DatePicker/WdatePicker.js"></script>
<title>管理员 列表</title>
</head>
<%
String uf_parentid = (String)request.getAttribute("uf_parentid");
String key_usertype = (String)request.getAttribute("key_usertype");
Integer key_isvalid = (Integer)request.getAttribute("key_isvalid");
String keyword = (String)request.getAttribute("keyword");
String parentid = (String)request.getAttribute("parentid");
if(uf_parentid==null) uf_parentid = "";
if(key_usertype==null) key_usertype = "";
if(key_isvalid==null) key_isvalid = 0;
if(keyword==null) keyword = "";
%>
<body id="right">
<div class="operation o-h bg-f8">
	<div class="operationLeft f-l"><font style="margin-left:10px;font-size:14px; font-weight:bold; text-indent:14px; letter-spacing:2px;">管理员列表</font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<c:if test="${auth82 eq '1'}"><a href="/emAdmin.do?method=blank&uf_parentid=${uf_parentid}&key_usertype=${key_usertype}&key_isvalid=${key_isvalid}&keyword=${keyword}&m=${m}&s=${s}" style="margin-left:100px;"><i class="iconfont">&#xe681;</i>新增管理员</a></c:if></div>
	<div class="operationRight f-r">
	</div>
</div>
<table class="tlist">
	 <form action="/emAdmin.do?method=list" id="searchForm" name="searchForm" method="post">
	 <thead>
	 <tr class="title">
	 	<th width=10%>关键字</th>
	 	<th width=10% align=left style="background-color:#ffffff;text-align:left;">
	 		<input type="text" id="keyword" name="keyword" value="${keyword}">
	 	</th>
	 	<th width=10%>类别</th>
	 	<th align=left style="background-color:#ffffff;text-align:left;">
<select name="key_usertype">
<option value="">－全部－
<option value="2"<c:if test="${key_usertype eq 2}"> selected</c:if>>平台客服
<option value="3"<c:if test="${key_usertype eq 3}"> selected</c:if>>第三方客服
<option value="4"<c:if test="${key_usertype eq 4}"> selected</c:if>>销售
</select>
	 	</th>
	 	<th width=10%>有效</th>
	 	<th align=left style="background-color:#ffffff;text-align:left;">
<select name="key_isvalid">
<option value="99">－全部－
<option value="0"<c:if test="${key_isvalid eq 0}"> selected</c:if>>无效
<option value="1"<c:if test="${key_isvalid eq 1}"> selected</c:if>>有效
</select>
	 	</th>
	 	<th width="20%" class="operationBtn">
	 		<input type="hidden" id="report" name="report" value="${report}">
	 		<a href="javascript:submitform(0);"><i class="iconfont">&#xe608;</i>搜索</a>
	 	</th>
	 </tr>
	 </thead>
	 <tbody>

	 </tbody>
	 </form>
</table>
<form action="" id="op" name="op" method="get">
<table class="tlist">
	<thead>
		<tr class="title">
<th onclick="sort('name', 'Desc');">
姓名
</th>
<th>账号</th>
<th>手机</th>
<th>工号</th>
<th>管理员类别</th>
<th>性别</th>
<th>是否删除</th>
<th width="250">操作</th>
		</tr>
	</thead>
	<tbody>
		
<c:choose>
<c:when test="${not empty resultRows}">
<c:forEach var="item" items="${resultRows}">
			<tr>
				<td height=30>${item.username}</td>
				<td>${item.account}</td>
				<td>${item.mobile}</td>
				<td>${item.userno}</td>
				<td><c:if test="${item.usertype eq 2}">平台客服</c:if><c:if test="${item.usertype eq 3}">第三方客服</c:if><c:if test="${item.usertype eq 4}">销售</c:if></td>
				<td>${item.sex}</td>
				<td><c:if test="${item.isdelete eq 1}">已删除</c:if><c:if test="${item.isdelete ne 1}">有效</c:if></td>
				<td width="250px;" class="operationBtn">
				<c:if test="${auth82 eq '1'}"><a href="/emAdmin.do?method=fill&userid=${item.userid}&uf_parentid=${uf_parentid}&key_usertype=${key_usertype}&key_isvalid=${key_isvalid}&keyword=${keyword}&m=${m}&s=${s}" style="margin-left:0px;"><i class="iconfont">&#xe6d6;</i>查看/编辑</a>
					<c:if test="${item.isdelete eq 0 }"><a href="javascript:deleteUser(${item.userid}, 1)" style="margin-left:10px;"><i class="iconfont">&#xe636;</i>删除</a></c:if>
				</c:if>
				<c:if test="${auth82 ne '1'}"><a href="/emAdmin.do?method=fill&userid=${item.userid}&uf_parentid=${uf_parentid}&key_usertype=${key_usertype}&key_isvalid=${key_isvalid}&keyword=${keyword}&m=${m}&s=${s}" style="margin-left:0px;"><i class="iconfont">&#xe6d6;</i>查看</a>
				</c:if>
				</td>
			</tr>
</c:forEach>
</c:when>
<c:otherwise>
			<tr><td colspan=10>
      <p><span>非常抱歉！</span></p><p>没有找到与您选择的搜索条件相符的资料。</p>
    	</td></tr>
</c:otherwise>
</c:choose>
		
	</tbody>
	<tfoot>
		<tr>
			<td colspan="10"></td>
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
			
			String u = "/emAdmin.do?method=list&uf_parentid=" + uf_parentid + "&key_usertype=" + key_usertype + "&key_isvalid=" + key_isvalid + "&keyword=" + keyword + "&m=" + pageSize;
			String u1 = "/emAdmin.do?method=list&uf_parentid=" + uf_parentid + "&key_usertype=" + key_usertype + "&key_isvalid=" + key_isvalid + "&keyword=" + keyword;
	
			out.write("<div class='bar'><span class=\"disabled\">共: " + totalCount + "</span> <span class=\"disabled\">每页显示: "
					+ (pageSize==15?"<span class=\"current\">15</span>":"<a href=\"" + u1 + "&m=15\">15</a>") + "<span class=\"disabled\">&nbsp;|&nbsp;</span>"
					+ (pageSize==30?"<span class=\"current\">30</span>":"<a href=\"" + u1 + "&m=30\">30</a>") + "<span class=\"disabled\">&nbsp;|&nbsp;</span>"
					+ (pageSize==100?"<span class=\"current\">100</span>":"<a href=\"" + u1 + "&m=100\">100</a>")) ;
			if (pageCount > 1) {
				
				buffer.append("<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;页码：</span>");
				if (startIndex > 1) {
					buffer.append("<a href=\"" + u + "&s=" + (startIndex-1) + "\">上一页</a>");
					if (startIndex > this.getFirstIndex() + 5 && indexs.length < pageCount) {
						buffer.append("<a href=\"" + u + "&s=" + getFirstIndex() + "\">" + this.getFirstIndex() + " ...</a>");
					}
				}
				for (int index : indexs) {
					if (startIndex == index) {
						buffer.append("<span class=\"current\">").append(index).append("</span>");
					} else {
						buffer.append("<a href=\"" + u + "&s=" + index + "\">").append(index).append("</a>");
					}
				}
	
				if (startIndex < pageCount) {
					if (startIndex < this.getLastIndex() - 5 && indexs.length < pageCount) {
						buffer.append("<a href=\"" + u + "&s=" + getLastIndex() + "\">... ").append(this.getLastIndex()).append("</a>");
					}
					buffer.append("<a href=\"" + u + "&s=" + getNextIndex() + "\">下一页</a>");
				}
	
				out.write(buffer.toString());
			}
			out.write("</div>");			
		}
	}
%>
	
</form>
<form action="/emAdmin.do?method=delete" id="deleteForm" name="deleteForm" method="post">
<input type="hidden" id="userid" name="userid" value="">
<input type="hidden" id="t" name="t" value="">
<input type="hidden" name="uf_parentid" value="${uf_parentid}">
<input type="hidden" name="key_usertype" value="${key_usertype }">
<input type="hidden" name="key_isvalid" value="${key_isvalid }">
<input type="hidden" name="keyword" value="${keyword }">
<input type="hidden" name="m" value="${m }">
<input type="hidden" name="s" value="${s }">
</body>
<script type="text/javascript">
function submitform(r){
	$("#report").val(r)
	searchForm.submit();
}

	
	function deleteUser(userid, t){
		if(t==1) {
			if(confirm("是否删除管理员资料？")) {
				$("#userid").val(userid);
				$("#t").val(t);
				deleteForm.submit();
				
			}
		} else {
			if(confirm("是否恢复管理员资料？")) {
				$("#userid").val(userid);
				$("#t").val(t);
				deleteForm.submit();
				
			}
		}
	}

</script>
</html>