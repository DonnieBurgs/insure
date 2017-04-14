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
<link href="/resources/js/jquery-ui/themes/base/jquery-ui.css" rel="stylesheet" type="text/css" />
<link href="/resources/js/jquery-ui/themes/base/jquery.ui.autocomplete.css" rel="stylesheet" type="text/css" />
<script src="http://libs.baidu.com/jquery/1.8.3/jquery.min.js"></script>
<script type="text/javascript" src="/resources/js/jquery-ui.min.js"></script>
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
<title>案卷添加</title>
</head>
<body id="right">
<div class="operation o-h bg-f8">
	<div class="operationLeft f-l"><font style="margin-left:10px;font-size:14px; font-weight:bold; text-indent:14px; letter-spacing:2px;">案卷添加</font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<a href="javascript:history.back();" style="margin-left:100px;"><i class="iconfont">&#xe681;</i>返回列表</a></div>
	<div class="operationRight f-r">
	</div>
</div>
<form id="commonForm" action="/emClaimArchive.do?method=add" method="post">
	<table class="tform">
<tr>
<td width="120" class="right">团保方案：</td>
<td>
<input type="hidden" id="groupinsurancepolicyid" name="groupinsurancepolicyid" value="" size=50>
<input type="text" id="groupinsurancepolicy" name="groupinsurancepolicy" value="" size=50>
</td>
</tr>
<tr>
<td width="120" class="right">收单日期：</td>
<td><input type="text" id="acceptdate" name="acceptdate" readonly="readonly" value="" onclick="WdatePicker({maxDate:'2018-12-31', dateFmt:'yyyy-MM-dd'})" style="width: 70px;"/></td>
</tr>
<tr>
<td width="120" class="right">案卷号：</td>
<td><input type="text" id="claimarchivenumber" name="claimarchivenumber" value="" size=50></td>
</tr>

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
</form>
</body>
</html>
<script language=javascript>
function checkf() {
	if(commonForm.groupinsurancepolicyid.value=="" || commonForm.groupinsurancepolicyid.value!="" && !is_int(commonForm.groupinsurancepolicyid.value)) {alert("请正确输入团保方案ID！");return false;}
	if(commonForm.acceptdate.value=="") {alert("请正确输入收单日期！");return false;}
	if(commonForm.claimarchivenumber.value=="") {alert("请正确输入案卷号！");return false;}

	document.getElementById("commonForm").submit() ;
  
}
$(function() {
	jQuery.curCSS = jQuery.css;
    var availableTags = ${util:queryTable4Array("em_groupinsurancepolicy","groupinsurancepolicyname","asc")};
    $("#groupinsurancepolicy").autocomplete({
    	source: function(request,response){  
            response($.map(availableTags,function(item){  
	                return {
	                    label:item.groupinsurancepolicyname,
	                    value:item.groupinsurancepolicyname,
	                    id:item.id
	                }
            	}
            ));  
		},
	    select: function( event, ui ) {
	    	$("#groupinsurancepolicyid").val(ui.item.id);
	    }
    });
 });

</script>