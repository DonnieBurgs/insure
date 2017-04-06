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
</head>
<body id="right">
<div class="mtitle my-header">
<h1><c:if test="${parentid eq ''}">会员</c:if><c:if test="${parentid ne ''}">（${parent.username}）家庭成员</c:if> 查看</h1>
<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[ <a href="/emUser.do?method=list&parentid=${parentid}">返回列表</a> ] </span></div>
<table class="tform">
<c:if test="${parentid eq ''}">
<tr>
	<td width="120" class="right">会员类型：</td>
	<td>卡主</td>
</tr>
</c:if>
<c:if test="${parentid ne ''}">
<tr>
	<td width="120" class="right">与卡主关系：</td>
	<td>${item.relate}</td>
</tr>
</c:if>
<tr>
	<td width="120" class="right">会员名称：</td>
	<td>${item.username}</td>
</tr>
<tr>
<td width="120" class="right">会员号：</td>
<td>${item.userno}</td>
</tr>
<tr>
<td width="120" class="right">性别：</td>
<td>${item.sex}
</td>
</tr>
<tr>
<td width="120" class="right">年龄：</td>
<td>${item.age}</td>
</tr>
<tr>
<td width="120" class="right">身份证：</td>
<td>${item.identity}</td>
</tr>
<tr>
<td width="120" class="right">手机：</td>
<td>${item.mobile}</td>
</tr>
<tr>
<td width="120" class="right">家庭电话：</td>
<td>${item.tel}</td>
</tr>
<tr>
<td width="120" class="right">地址：</td>
<td>${item.addr}</td>
</tr>
<tr>
<td width="120" class="right">是否有效：</td>
<td><c:if test="${item.isvalid eq 1}">有效</c:if>
<c:if test="${item.isvalid eq 0}">无效</c:if>
</td>
</tr>
<tr>
<td width="120" class="right">有效时间：</td>
<td>${item.startdate_} ～ ${item.expdate_}</td>
</tr>
<tr>
<td width="120" class="right">关联微信：</td>
<td><c:if test="${item.isregist eq 0}">未关联</c:if>
	<c:if test="${item.isregist eq 1}">已关联 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;微信Openid：${item.openid}</c:if> </td>
</tr>
<tr>
<td width="120" class="right">显示顺序：</td>
<td>${item.seq}</td>
</tr>
<tr>
<td width="120" height="1" bgcolor="#eeeeee"></td>
<td bgcolor="#eeeeee"></td>
</tr>
<tr>
<td width="120" height="30" class="right">产品：</td>
<td>
<c:if test="${not empty cList}">
<c:forEach var="card" items="${cList}">
<c:if test="${item.cardid eq card.cardid}">${card.cardname}</c:if>
</c:forEach>
</c:if>
</td>
</tr>
<tr>
<td width="120" class="right">卡号：</td>
<td>${item.cardno}</td>
</tr>
<tr>
<td width="120" class="right">余额：</td>
<td>${item.balance}</td>
</tr>
<tr>
<td width="120" class="right">可预约次数：</td>
<td>${item.registertimes}</td>
</tr>
<tr>
<td width="120" height="40" class="right">增选服务：</td>
<td><input type="checkbox" id="addin1" name="addin1" value="1"<c:if test="${item.addin1 eq '1'}"> checked</c:if>>TM15肿瘤筛查&nbsp;&nbsp;&nbsp;&nbsp;
<input type="checkbox" id="addin2" name="addin2" value="1"<c:if test="${item.addin2 eq '1'}"> checked</c:if>>基因筛查&nbsp;&nbsp;&nbsp;&nbsp;
<input type="checkbox" id="addin3" name="addin3" value="1"<c:if test="${item.addin3 eq '1'}"> checked</c:if>>附属会籍&nbsp;&nbsp;&nbsp;&nbsp;
<input type="checkbox" id="addin4" name="addin4" value="1"<c:if test="${item.addin4 eq '1'}"> checked</c:if>>其他&nbsp;
<input id="addin4cont" name="addin4cont" type="text" value="${item.addin4cont}"/></td>
</tr>
<tr>
<td width="120" height="1" bgcolor="#eeeeee"></td>
<td bgcolor="#eeeeee"></td>
</tr>
<tr>
<td width="120" class="right" rowspan=3>标签：</td>
<td>年纪：<c:if test="${not empty t1List}">
<c:forEach var="tagitem" items="${t1List}" varStatus="idx">
<c:if test="${item.tag1 eq tagitem.tagvalue}">${tagitem.tagname}</c:if>
</c:forEach>
</c:if>
</td>
</tr>
<tr>
<td>
病类别：
<c:if test="${not empty t2List}">
<c:forEach var="tagitem" items="${t2List}" varStatus="idx">
<c:if test="${idx.index eq 10}"><br></c:if>
<c:if test="${fn:indexOf(tagIds,tagitem.usertagid) ge 0}">${tagitem.tagname}&nbsp;&nbsp;</c:if>
</c:forEach>
</c:if>
</td>
</tr>
<tr>
<td>
中医体质：
<c:if test="${not empty t3List}">
<c:forEach var="tagitem" items="${t3List}" varStatus="idx">
<c:if test="${idx.index eq 10}"><br></c:if>
<c:if test="${fn:indexOf(tagIds,tagitem.usertagid) ge 0}">${tagitem.tagname}&nbsp;&nbsp;</c:if>
</c:forEach>
</c:if>
</td>
</tr>
<tr>
<td width="120" height="40" class="right">健康计划：</td>
<td>计划1:
<c:if test="${not empty tList}">
<c:forEach var="tem" items="${tList}">
<c:if test="${item.templateid1 eq tem.templateid}">${tem.templatename}</c:if>
</c:forEach>
</c:if>

&nbsp;&nbsp;&nbsp;&nbsp;
计划2:
<c:if test="${not empty tList}">
<c:forEach var="tem" items="${tList}">
<c:if test="${item.templateid2 eq tem.templateid}">${tem.templatename}</c:if>
</c:forEach>
</c:if>
</td>
</tr>
<tr>
<td width="120" class="right">照片：</td>
<td><c:if test="${item.photo ne ''}"><img id="imgphoto" src="/images/${item.photo}"></c:if></td>
</tr>
<tr>
<td width="120" class="right">备注：</td>
<td>${item.info}</td>
</tr>
		<tr>
			<td colspan="2">
				<input type="button" class="btn1" value=" 返 回 " onclick="history.back()"/>
			</td>
		</tr>
	</table>
</form>
</body>
</html>
