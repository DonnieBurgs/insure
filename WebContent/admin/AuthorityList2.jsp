<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>权限管理</title>
<link type="text/css" rel="stylesheet" href="/admin/style/base.css" />
<link type="text/css" rel="stylesheet" href="/admin/style/global.css" />
<link href="/resources/css/main.css" rel="stylesheet" type="text/css" />
<script src="http://libs.baidu.com/jquery/1.8.3/jquery.min.js"></script>

</head>

<body id="right">
<div class="operation o-h bg-f8">
	<div class="operationLeft f-l"><font style="margin-left:10px;font-size:14px; font-weight:bold; text-indent:14px; letter-spacing:2px;">权限列表</font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<c:if test="${auth46 eq '1'}"><a href="/emAuthority.do?method=blank" style="margin-left:100px;"><i class="iconfont">&#xe681;</i>新增权限</a></c:if></div>
	<div class="operationRight f-r">
	</div>
</div>
	<div class="screen w1">
    	<form>
        	<div class="d-ib screen-list">
            	<span class="d-ib ta-r">开始时间：</span>
                <input name="startTime" class="laydate-icon br-5" id="start" placeholder="开始时间">
            </div>
        	<div class="d-ib screen-list">
            	<span class="d-ib ta-r">结束时间：</span>
                <input name="endTime" class="laydate-icon br-5" id="end" placeholder="结束时间">
            </div>
        	<div class="d-ib screen-list">
            	<span class="d-ib ta-r">关&nbsp;&nbsp;键&nbsp;&nbsp;词：</span>
                <input name="crux" class="laydate-icon br-5" type="text" placeholder="关键词" class="" />
            </div>
            <div class="d-ib screen-list">
            	<span class="d-ib ta-r">类&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;型：</span>
                <select name="screenClass" class="br-5">
                	<option>选项一</option>
                	<option>选项二</option>
                	<option>选项三</option>
                </select>
            </div>
            <div class="d-ib screen-list">
            	<span class="d-ib ta-r">等&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;级：</span>
                <select name="screenClass" class="br-5">
                	<option>选项一</option>
                	<option>选项二</option>
                	<option>选项三</option>
                </select>
            </div>
            <div class="d-ib screen-list">
            	<span class="d-ib ta-r">&nbsp;</span>
                <button class="screenSubmit br-5 c-f" type="submit">搜索</button>
            </div>
        </form>
    </div>
    <div class="tableBox">
    	<table function-type="nothing" class="w1 ta-c fs-12 c-6 bc-c">
            <tr>
                <th>名称</th>
                <th>备注</th>
                <th>操作</th>
            </tr>
<c:choose>
<c:when test="${fn:length(resultRows) > 0}">
<c:forEach var="item" items="${resultRows}">
			<tr>
				<td>${item.authorityname}</td>
				<td><c:if test="${item.ctype eq 0}"> 系统管理</c:if><c:if test="${item.ctype eq 1}"> 保安公司</c:if></td>
				<td>${item.note}</td>
				<td><a href="/scAuthority.do?method=fill&id=${item.authorityid}">编辑</a></td>
			</tr>
</c:forEach>
</c:when>
<c:otherwise>
			<tr><td colspan=4>
      <p><span>非常抱歉！</span></p><p>没有找到与您选择的搜索条件相符的资料。</p>
    	</td></tr>
</c:otherwise>
</c:choose>
        </table>
	</div>
<%@ include file="/admin/inc/pagebar_s.jsp"%> 
<%
	startIndex = 1;
	pageSize = 15;
	pageIndexCount = 10;
	pageCount = 0;
	totalCount = 0;
	int[] indexs = new int[0];
	String parameters;
	String _parentNo = (String)request.getAttribute("parentNo");
	if(_parentNo==null) _parentNo = "" ;


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
	
			if (pageCount > 1) {
				
				String u = "/scAuthority.do?method=list&parentNo=" + _parentNo;
				
				buffer.append("<div class='bar'><span class=\"disabled\">共: " + totalCount + "</span> <span class=\"disabled\">每页显示: "+pageSize+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;页码：</span>");
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
				buffer.append("</div>");
	
				out.write(buffer.toString());
			}
		}
	}
%>
	    
</div>
</body>
<script src="../../script/laydate.js"></script>
<script>
$(window.parent.document).find("#right").load(function(){
	var main = $(window.parent.document).find("#right");
	var mainheight = $(document).height()+30;
	main.height(mainheight);
});
$('tr.c-p').each(function(){
	$(this).click(function(){
		var	thishref = $(this).attr('data-href')
		location.href=thishref 
	})
})
$('td span.trChange').each(function(){
	$(this).click(function(){
		var  functionState=$(this).attr('function-state')
		if(functionState==='no'){
			$(this).attr('function-state','yes').text('保存')
			$(this).parents('tr').find('input').removeAttr('disabled').css('background','#fff')
		}else if(functionState==='yes'){
			$(this).attr('function-state','no').text('修改')
			$(this).parents('tr').find('input').attr('disabled','disabled').removeAttr('style')
		}
	})
})
$('td span.trDelete').each(function(){
	$(this).click(function(){
		$(this).parents('tr').remove();
	})
})

</script>
</html>





