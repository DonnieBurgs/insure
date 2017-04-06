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
<link type="text/css" rel="stylesheet" href="/resources/css/main.css" />
<link type="text/css" rel="stylesheet" href="/admin/style/screen.css" />
<script type="text/javascript"
	src="/resources/jquery/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="/resources/js/jquery-ui.min.js"></script>
<script type="text/javascript"
	src="/resources/js/xheditor/xheditor-zh-cn.min.js"></script>
<script type="text/javascript">
	var root = "";
</script>
<script type="text/javascript" src="/resources/js/admin.js"></script>
<script type="text/javascript" src="/resources/js/common.js"></script>
<script type="text/javascript" src="/resources/js/photo.js"></script>
<script type="text/javascript"
	src="/resources/js/My97DatePicker/config.js"></script>
<script type="text/javascript"
	src="/resources/js/My97DatePicker/lang/zh-cn.js"></script>
<link rel="stylesheet" type="text/css"
	href="/resources/js/My97DatePicker/skin/WdatePicker.css" />
<link rel="stylesheet" type="text/css"
	href="/resources/js/My97DatePicker/skin/default/datepicker.css" />
<script type="text/javascript"
	src="/resources/js/My97DatePicker/WdatePicker.js"></script>
<title>救援订单<c:if test="${auth12 eq '1'}">处理</c:if><c:if
		test="${auth12 ne '1'}">查看</c:if></title>
</head>
<body id="right">
	<div class="operation o-h bg-f8">
		<div class="operationLeft f-l">
			<font
				style="margin-left: 10px; font-size: 14px; font-weight: bold; text-indent: 14px; letter-spacing: 2px;">救援订单<c:if
					test="${auth12 eq '1'}">处理</c:if>
				<c:if test="${auth12 ne '1'}">查看</c:if></font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<a href="javascript:history.back();" style="margin-left: 100px;"><i
				class="iconfont">&#xe63d;</i>返回列表</a>
		</div>
		<div class="operationRight f-r"></div>
	</div>
	<form id="commonForm" action="/emOrder.do?method=update" method="post">
		<table class="tform">
			<tr>
				<td width="120" class="right">会员姓名：</td>
				<td>${item.username}</td>
			</tr>
			<tr>
				<td width="120" class="right">订单类别：</td>
				<td><select name="otype">
						<c:if test="${not empty otList}">
							<c:forEach var="listItem" items="${otList}" varStatus="idx">
								<option value="${listItem.ordertypeid}"
									<c:if test="${item.otype eq listItem.ordertypeid}"> selected</c:if>>${listItem.ordertypename}</option>
							</c:forEach>
						</c:if>
				</select></td>
			</tr>
			<tr>
				<td width="120" class="right">出发时间：</td>
				<td><input type="text" id="departuredate" name="departuredate"
					readonly="readonly" value="${item.departuredate}"
					onclick="WdatePicker({maxDate:'2018-12-31', dateFmt:'yyyy-MM-dd'})"
					style="width: 70px;" /></td>
			</tr>
			<tr>
				<td width="120" class="right">出发地：</td>
				<td><input type="text" id="address1" name="address1"
					value="${fn:replace(item.address1,'" ','&quot;')}" size=50></td>
			</tr>
			<tr>
				<td width="120" class="right">出发地坐标：</td>
				<td><input type="text" id="lat1" name="lat1"
					value="${fn:replace(item.lat1,'"
					','&quot;')}" style="width: 100px;"><input type="text"
					id="lng1" name="lng1" value="${fn:replace(item.lng1,'"
					','&quot;')}" style="width: 100px;"></td>
			</tr>
			<tr>
				<td width="120" class="right">目的地：</td>
				<td><input type="text" id="address2" name="address2"
					value="${fn:replace(item.address2,'" ','&quot;')}" size=50></td>
			</tr>
			<tr>
				<td width="120" class="right">目的地坐标：</td>
				<td><input type="text" id="lat2" name="lat2"
					value="${fn:replace(item.lat2,'"
					','&quot;')}" style="width: 100px;"><input type="text"
					id="lng2" name="lng2" value="${fn:replace(item.lng2,'"
					','&quot;')}" style="width: 100px;"></td>
			</tr>
			<tr>
				<td width="120" class="right">估算路程：</td>
				<td><input type="text" id="distance" name="distance"
					value="${fn:replace(item.distance,'" ','&quot;')}" size=50></td>
			</tr>
			<tr>
				<td width="120" class="right">车型：</td>
				<td><select name="cartypeid">
						<c:if test="${not empty cartypeList}">
							<c:forEach var="listItem" items="${cartypeList}" varStatus="idx">
								<option value="${listItem.cartypeid}"
									<c:if test="${item.cartypeid eq listItem.cartypeid}"> selected</c:if>>${listItem.cartypename}</option>
							</c:forEach>
						</c:if>
				</select></td>
			</tr>
			<tr>
				<td width="120" class="right">套餐：</td>
				<td><select name="packtypeid">
						<c:if test="${not empty packtypeList}">
							<c:forEach var="listItem" items="${packtypeList}" varStatus="idx">
								<option value="${listItem.packtypeid}"
									<c:if test="${item.packtypeid eq listItem.packtypeid}"> selected</c:if>>${listItem.packtypename}</option>
							</c:forEach>
						</c:if>
				</select></td>
			</tr>
			<tr>
				<td width="120" class="right">服务选择：</td>
				<td><c:if test="${not empty serviceoptiontypeList}">
						<c:forEach var="listItem" items="${serviceoptiontypeList}"
							varStatus="idx">
							<input id="st${idx.index+1}" name="st${idx.index+1}"
								type="checkbox" value="${listItem.serviceoptiontypeid}"
								<c:if test="${fn:indexOf(item.servicetype, listItem.stid)>=0 }"> checked</c:if> />${listItem.serviceoptiontypename}&nbsp;&nbsp;
</c:forEach>
					</c:if>
			</tr>
			<tr>
				<td width="120" class="right">医生：</td>
				<td><input type="text" id="doctorid" name="doctorid"
					value="${fn:replace(item.doctorid,'" ','&quot;')}" size=50></td>
			</tr>
			<tr>
				<td width="120" class="right">订单评估价格：</td>
				<td><input type="text" id="price1" name="price1"
					value="${fn:replace(item.price1,'" ','&quot;')}" size=50></td>
			</tr>
			<tr>
				<td width="120" class="right">订金：</td>
				<td><input type="text" id="price2" name="price2"
					value="${fn:replace(item.price2,'" ','&quot;')}" size=50></td>
			</tr>
			<tr>
				<td width="120" class="right">订单报价：</td>
				<td><input type="text" id="price3" name="price3"
					value="${fn:replace(item.price3,'" ','&quot;')}" size=50></td>
			</tr>
			<tr>
				<td width="120" class="right">预付款：</td>
				<td><input type="text" id="price4" name="price4" value="${fn:replace(item.price4,'" ','&quot;')}" size=50>
				<c:if test="${auth12 eq '1'}">
					<input type="button" class="btn1" value="登记客户健康状况" onclick="toLogin();" style="left:50px;"/>
					<c:if test="${item.status eq '3'}"><input type="button" class="btn1" value="发送报价信息" onclick="price3MsgSend('3');" style="left:60px;margin-left:30px;"/></c:if>
					<c:if test="${item.status eq '5'}"><input type="button" class="btn1" value="发送出车信息" onclick="price3MsgSend('3a');" style="left:60px;margin-left:30px;"/></c:if>
					<c:if test="${item.status eq '6'}"><input type="button" class="btn1" value="发送尾款信息" onclick="price3MsgSend('3b');" style="left:60px;margin-left:30px;"/></c:if>
				</c:if></td>
			</tr>
			<tr>
				<td width="120" class="right">成本价格：</td>
				<td><input type="text" id="price5" name="price5"
					value="${fn:replace(item.price5,'" ','&quot;')}" size=50></td>
			</tr>
			<tr>
				<td width="120" class="right">状态：</td>
				<td><select name="status">
						<c:if test="${not empty orderstatusList}">
							<c:forEach var="listItem" items="${orderstatusList}"
								varStatus="idx">
								<option value="${listItem.orderstatusid}"
									<c:if test="${item.status eq listItem.orderstatusid}"> selected</c:if>>${listItem.orderstatusname}</option>
							</c:forEach>
						</c:if>
				</select></td>
			</tr>
			<tr>
				<td width="120" class="right">下单时间：</td>
				<td>${item.createdate}</td>
			</tr>
			<tr>
				<td width="120" class="right">其他要求：</td>
				<td><input type="text" id="note" name="note"
					value="${fn:replace(item.note,'" ','&quot;')}" size=50></td>
			</tr>
			<tr>
				<td width="120" class="right">客服备注：</td>
				<td><input type="text" id="servicenote" name="servicenote"
					value="${fn:replace(item.servicenote,'" ','&quot;')}" size=50>
					客户不可见</td>
			</tr>
			<tr>
				<td width="120" class="right">客户评分：</td>
				<td>总体评分：${item.score}&nbsp;&nbsp;服务质量：${item.score1}&nbsp;&nbsp;服务态度：${item.score2}&nbsp;&nbsp;处理时间：${item.score3}&nbsp;&nbsp;</td>
			</tr>
			<td width="120" class="right">客户评价：</td>
			<td><input type="text" id="scorenote" name="scorenote"
				value="${fn:replace(item.scorenote,'" ','&quot;')}" size=50></td>
			</tr>

			<tr>
				<td colspan="2"><input id="orderid" name="orderid"
					type="hidden" value="${item.orderid}" /> <input id="uf_parentid"
					name="uf_parentid" type="hidden" value="${uf_parentid}" /> <input
					id="keyword" name="keyword" type="hidden" value="${keyword}" /> <input
					id="key_otype" name="key_otype" type="hidden" value="${key_otype}" />
					<input id="key_status" name="keyword" type="hidden"
					value="${key_status}" /> <input id="m" name="m" type="hidden"
					value="${m}" /> <input id="s" name="s" type="hidden" value="${s}" />
					<c:if test="${auth12 eq '1'}">
						<input type="button" class="btn1" value=" 提  交 "
							onclick="checkf()" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				</c:if> <c:if test="${auth12 ne '1'}">
						<input type="button" class="btn1" value=" 返  回 "
							onclick="history.back();" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				</c:if></td>
			</tr>
		</table>
	</form>
	<div class="loginBox p-f d-n z-9 t-c">
		<div class="loginBg z-8"></div>
		<div class="login p-a z-9" id="lBox">
			<div class="wrap bg-f p-r">
				<img class=" p-a loginPupopClose fs-30 c-p" src="/admin/image/close.png" style="right:20px;top:25px;line-height:30px;width:20px;"/>
				
		<div class="operationLeft f-l t-c" style="width:100%;margin-top:30px;margin-bottom:10px;text-align:center;">
			<font
				style="text-align:center; font-size: 14px; font-weight: bold; text-indent: 14px; letter-spacing: 2px;">客户信息采集</font>
		</div>
	<form id="infForm" action="" method="post">
		<table class="tform">
<tr>
<td width="100" class="right">联系人：</td>
<td><input id="f_linkname" name="f_linkname" type="text" value="${inf.linkname}" style="width:120px;"/></td>
<td width="100" class="right">联系电话：</td>
<td><input id="f_linktel" name="f_linktel" type="text" value="${inf.linktel}" style="width:120px;"/></td>
<td width="100" class="right">与患者关系：</td>
<td><input id="f_linkrelation" name="f_linkrelation" type="text" value="${inf.linkrelation}" style="width:120px;"/></td>
</tr>
<tr>
<td class="right">患者姓名：</td>
<td><input id="f_username" name="f_username" type="text" value="${inf.username}" style="width:120px;"/></td>
<td class="right">性别：</td>
<td>
<select id="f_sex" name="f_sex">
<option value="男"<c:if test="${inf.sex eq '男'}"> selected</c:if>>男
<option value="女"<c:if test="${inf.sex eq '女'}"> selected</c:if>>女
</select>
</td>
<td class="right">年龄：</td>
<td><input id="f_age" name="f_age" type="text" value="${inf.age}" style="width:120px;"/></td>
</tr>
<tr>
<td class="right">转出医院：</td>
<td colspan="2"><input id="f_hospitalfrom" name="f_hospitalfrom" type="text" value="${inf.hospitalfrom}" style="width:230px;"/></td>
<td class="right">科室：</td>
<td colspan="2"><input id="f_subject" name="f_subject" type="text" value="${inf.subject}" style="width:230px;"/></td>
</tr>
<tr>
<td class="right">诊断：</td>
<td colspan="2"><input id="f_matter" name="f_matter" type="text" value="${inf.matter}" style="width:230px;"/></td>
<td class="right">起病时间：</td>
<td colspan="2"><input id="f_starttime" name="f_starttime" type="text" value="${inf.starttime}" style="width:230px;"/></td>
</tr>
<tr>
<td rowspan="6" class="right">目前病情：</td>
<td class="right">意识：</td>
<td>清醒<input id="f_sub1" name="f_sub1" type="checkbox" value="清醒"<c:if test="${inf.sub1 eq '清醒'}"> checked</c:if> onclick="checkClick('f_sub1');"/>&nbsp;&nbsp; 昏迷<input id="f_sub1a" name="f_sub1" type="checkbox" value="昏迷"<c:if test="${inf.sub1 eq '昏迷'}"> checked</c:if> onclick="checkClick('f_sub1a');"/>&nbsp;&nbsp;</td>
<td class="right">生命体征：</td>
<td colspan="2">稳定<input id="f_sub2" name="f_sub2" type="checkbox" value="稳定"<c:if test="${inf.sub2 eq '稳定'}"> checked</c:if> onclick="checkClick('f_sub2');"/>&nbsp;&nbsp; 不稳定<input id="f_sub2a" name="f_sub2" type="checkbox" value="不稳定"<c:if test="${inf.sub2 eq '不稳定'}"> checked</c:if> onclick="checkClick('f_sub2a');"/>&nbsp;&nbsp;</td>
</tr>
<tr>
<td class="right">呼吸：</td>
<td>自主呼吸<input id="f_sub3" name="f_sub3" type="checkbox" value="自主呼吸"<c:if test="${inf.sub3 eq '自主呼吸'}"> checked</c:if> onclick="checkClick('f_sub3');"/>&nbsp;&nbsp;<br> 呼吸机支持<input id="f_sub3a" name="f_sub3" type="checkbox" value="呼吸机支持"<c:if test="${inf.sub3 eq '呼吸机支持'}"> checked</c:if> onclick="checkClick('f_sub3a');"/>&nbsp;&nbsp;</td>
<td class="right">气管插管：</td>
<td colspan="2">插管<input id="f_sub4" name="f_sub4" type="checkbox" value="插管"<c:if test="${inf.sub4 eq '插管'}"> checked</c:if> onclick="checkClick('f_sub4');"/>&nbsp;&nbsp; 面罩<input id="f_sub4a" name="f_sub4" type="checkbox" value="面罩"<c:if test="${inf.sub4 eq '面罩'}"> checked</c:if> onclick="checkClick('f_sub4a');"/>&nbsp;&nbsp;</td>
</tr>
<tr>
<td class="right">有无气管切开：</td>
<td>有<input id="f_sub5" name="f_sub5" type="checkbox" value="有"<c:if test="${inf.sub5 eq '有'}"> checked</c:if> onclick="checkClick('f_sub5');"/>&nbsp;&nbsp; 无<input id="f_sub5a" name="f_sub5" type="checkbox" value="无"<c:if test="${inf.sub5 eq '无'}"> checked</c:if> onclick="checkClick('f_sub5a');"/>&nbsp;&nbsp;</td>
<td class="right">是否有传染病：</td>
<td colspan="2">有<input id="f_sub6" name="f_sub6" type="checkbox" value="有"<c:if test="${inf.sub6 eq '有'}"> checked</c:if> onclick="checkClick('f_sub6');"/>&nbsp;&nbsp; 无<input id="f_sub6a" name="f_sub6" type="checkbox" value="无"<c:if test="${inf.sub6 eq '无'}"> checked</c:if> onclick="checkClick('f_sub6a');"/>&nbsp;&nbsp;</td>
</tr>
<tr>
<td class="right">已预订床位：</td>
<td>是<input id="f_sub7" name="f_sub7" type="checkbox" value="是"<c:if test="${inf.sub7 eq '是'}"> checked</c:if> onclick="checkClick('f_sub7');"/>&nbsp;&nbsp; 否<input id="f_sub7a" name="f_sub7" type="checkbox" value="否"<c:if test="${inf.sub7 eq '否'}"> checked</c:if> onclick="checkClick('f_sub7a');"/>&nbsp;&nbsp;</td>
<td class="right">可以坐轮椅：</td>
<td colspan="2">是<input id="f_sub8" name="f_sub8" type="checkbox" value="是"<c:if test="${inf.sub8 eq '是'}"> checked</c:if> onclick="checkClick('f_sub8');"/>&nbsp;&nbsp; 否<input id="f_sub8a" name="f_sub8" type="checkbox" value="否"<c:if test="${inf.sub8 eq '否'}"> checked</c:if> onclick="checkClick('f_sub8a');"/>&nbsp;&nbsp;</td>
</tr>
<tr>
<td class="right">使用监护除颤仪：</td>
<td>是<input id="f_sub9" name="f_sub9" type="checkbox" value="是"<c:if test="${inf.sub9 eq '是'}"> checked</c:if> onclick="checkClick('f_sub9');"/>&nbsp;&nbsp; 否<input id="f_sub9a" name="f_sub9" type="checkbox" value="否"<c:if test="${inf.sub9 eq '否'}"> checked</c:if> onclick="checkClick('f_sub9a');"/>&nbsp;&nbsp;</td>
<td class="right">是否需要担架上楼：</td>
<td colspan="2">是<input id="f_sub10" name="f_sub10" type="checkbox" value="是"<c:if test="${inf.sub10 eq '是'}"> checked</c:if> onclick="checkClick('f_sub10');"/>&nbsp;&nbsp; 否<input id="f_sub10a" name="f_sub10" type="checkbox" value="否"<c:if test="${inf.sub10 eq '否'}"> checked</c:if> onclick="checkClick('f_sub10a');"/>&nbsp;&nbsp;</td>
</tr>
<tr>
<td colspan="3" class="right">家属是否可以协助过床、抬担架：</td>
<td colspan="2">是<input id="f_sub11" name="f_sub11" type="checkbox" value="是"<c:if test="${inf.sub11 eq '是'}"> checked</c:if> onclick="checkClick('f_sub11');"/>&nbsp;&nbsp; 否<input id="f_sub11a" name="f_sub11" type="checkbox" value="否"<c:if test="${inf.sub11 eq '否'}"> checked</c:if> onclick="checkClick('f_sub11a');"/>&nbsp;&nbsp;</td>
</tr>
<tr>
<td class="right">病情描述：</td>
<td colspan="2"><textarea id="f_note" name="f_note" style="height:46px;">${inf.note}</textarea></td>
<td class="right">主诊医生意见：</td>
<td colspan="5"><textarea id="f_doctornote" name="f_doctornote" style="height:46px;">${inf.doctornote}</textarea></td>
</tr>
<tr>
<td class="right">主诊医生：</td>
<td><input id="f_doctor" name="f_doctor" type="text" value="${inf.doctor}" style="width:120px;"/></td>
<td class="right">联系电话：</td>
<td><input id="f_doctortel" name="f_doctortel" type="text" value="${inf.doctortel}" style="width:120px;"/></td>
<td colspan="2" style="text-align:right;padding-right:20px;"><input name="f_submit" type="button" value="保 存" onclick="infSubmit()" style="width:80px;"/>
<input id="aid" name="aid" type="hidden" value="${adminid}" />
</td>
</tr>
		</table>
<input id="key_top" name="key_top" type="hidden" value="100">
				</form>
			</div>
		</div>
	</div>
	<script src="/admin/script/uInfoBox.js"></script>

</body>
<form id="deleteForm" action="/emOrder.do?method=delete" method="post">
	<input id="orderid" name="orderid" type="hidden"
		value="${item.orderid}" /> <input id="uf_parentid" name="uf_parentid"
		type="hidden" value="${uf_parentid}" /> <input id="keyword"
		name="keyword" type="hidden" value="${keyword}" /> <input
		id="key_otype" name="key_otype" type="hidden" value="${key_otype}" />
	<input id="key_status" name="keyword" type="hidden"
		value="${key_status}" /> <input id="m" name="m" type="hidden"
		value="${m}" /> <input id="s" name="s" type="hidden" value="${s}" />
</form>
</html>
<script language=javascript>
	function checkf() {
		if (commonForm.departuredate.value == "") {
			alert("请正确输入出发时间！");
			return false;
		}
		if (commonForm.address1.value == "") {
			alert("请正确输入出发地点！");
			return false;
		}
		if (commonForm.lat1.value == ""
				|| commonForm.lat1.value != ""
				&& !(is_float(commonForm.lat1.value) || is_int(commonForm.lat1.value))) {
			alert("请正确输入坐标！");
			return false;
		}
		if (commonForm.lng1.value == ""
				|| commonForm.lng1.value != ""
				&& !(is_float(commonForm.lng1.value) || is_int(commonForm.lng1.value))) {
			alert("请正确输入坐标！");
			return false;
		}
		if (commonForm.address2.value == "") {
			alert("请正确输入目的地！");
			return false;
		}
		if (commonForm.lat2.value == ""
				|| commonForm.lat2.value != ""
				&& !(is_float(commonForm.lat2.value) || is_int(commonForm.lat2.value))) {
			alert("请正确输入坐标！");
			return false;
		}
		if (commonForm.lng2.value == ""
				|| commonForm.lng2.value != ""
				&& !(is_float(commonForm.lng2.value) || is_int(commonForm.lng2.value))) {
			alert("请正确输入坐标！");
			return false;
		}
		if (commonForm.distance.value == ""
				|| commonForm.distance.value != ""
				&& !(is_float(commonForm.distance.value) || is_int(commonForm.distance.value))) {
			alert("请正确输入估算路程！");
			return false;
		}
		if (commonForm.cartypeid.value == ""
				|| commonForm.cartypeid.value != ""
				&& !is_int(commonForm.cartypeid.value)) {
			alert("请正确输入车型！");
			return false;
		}
		if (commonForm.packtypeid.value == ""
				|| commonForm.packtypeid.value != ""
				&& !is_int(commonForm.packtypeid.value)) {
			alert("请正确输入套餐！");
			return false;
		}
		if (commonForm.status.value == "" || commonForm.status.value != ""
				&& !is_int(commonForm.status.value)) {
			alert("请正确输入状态！");
			return false;
		}

		document.getElementById("commonForm").submit();

	}

	function checkdelete() {
		if (confirm("是否删除当前数据记录？") == false) {
			return;
		} else {
			document.getElementById("deleteForm").submit();
		}

	}
	
	function checkClick(ojid) {
		if($('#'+ojid).get(0).checked) {
			if(ojid.indexOf("a")>0) {
				ojid = ojid.replace("a","");
				$('#'+ojid).get(0).checked=false;
			} else {
				$('#'+ojid+'a').get(0).checked=false;
			}
			
		}
	}
	
	function infSubmit() {
		$.ajax({
			type : "post",
			url : "/emApp.do",
			dataType : "json",
			data : {
				"method" : "userInfSave",
				"userid" : "${item.userid}",
				"orderid" : "${item.orderid}",
				"linkname" : f_linkname.value,
				"linktel" : f_linktel.value,
				"linkrelation" : f_linkrelation.value,
				"username" : f_username.value,
				"sex" : f_sex.value,
				"age" : f_age.value,
				"hospitalfrom" : f_hospitalfrom.value,
				"subject" : f_subject.value,
				"matter" : f_matter.value,
				"starttime" : f_starttime.value,
				"note" : f_note.value,
				"doctor" : f_doctor.value,
				"doctortel" : f_doctortel.value,
				"doctornote" : f_doctornote.value,
				"aid" : aid.value,
				"sub1" : $('#f_sub1').get(0).checked?$('#f_sub1').val():($('#f_sub1a').get(0).checked?$('#f_sub1a').val():""),
				"sub2" : $('#f_sub2').get(0).checked?$('#f_sub2').val():($('#f_sub2a').get(0).checked?$('#f_sub2a').val():""),
				"sub3" : $('#f_sub3').get(0).checked?$('#f_sub3').val():($('#f_sub3a').get(0).checked?$('#f_sub3a').val():""),
				"sub4" : $('#f_sub4').get(0).checked?$('#f_sub4').val():($('#f_sub4a').get(0).checked?$('#f_sub4a').val():""),
				"sub5" : $('#f_sub5').get(0).checked?$('#f_sub5').val():($('#f_sub5a').get(0).checked?$('#f_sub5a').val():""),
				"sub6" : $('#f_sub6').get(0).checked?$('#f_sub6').val():($('#f_sub6a').get(0).checked?$('#f_sub6a').val():""),
				"sub7" : $('#f_sub7').get(0).checked?$('#f_sub7').val():($('#f_sub7a').get(0).checked?$('#f_sub7a').val():""),
				"sub8" : $('#f_sub8').get(0).checked?$('#f_sub8').val():($('#f_sub8a').get(0).checked?$('#f_sub8a').val():""),
				"sub9" : $('#f_sub9').get(0).checked?$('#f_sub9').val():($('#f_sub9a').get(0).checked?$('#f_sub9a').val():""),
				"sub10" : $('#f_sub10').get(0).checked?$('#f_sub10').val():($('#f_sub10a').get(0).checked?$('#f_sub10a').val():""),
				"sub11" : $('#f_sub11').get(0).checked?$('#f_sub11').val():($('#f_sub11a').get(0).checked?$('#f_sub11a').val():"")
			},
			success : function(data) {
				if (data.ret == 1) {
					hideLoginBox();
				} else {
					alert("保存失败。");
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				alert("网络不畅通。");
			}
		});

	}
	
	function price3MsgSend(mtype) {
		if(mtype=='3') {
			if(confirm("发送通知前请先登记\"客户健康状况表\"，填写报价、预付款，把订单状态设置为\"已报价\"，并点击\"提交\"保存到服务器。\r\n是否发送通知消息？")==false) return;
		} else if(mtype=='3a') {
			if(confirm("发送出车通知前请先确认收到预付款并完成派单，把订单状态设置为\"已派单\"，并点击\"提交\"保存到服务器。\r\n是否发送通知消息？")==false) return;
		} else if(mtype=='3b') {
			if(confirm("发送通知前请先确认已经出车完成所有服务，没有收取客户尾款现金，把订单状态设置为\"已完成服务\"，并点击\"提交\"保存到服务器。\r\n是否发送通知消息？")==false) return;
		}
		$.ajax({
			type : "post",
			url : "/emApp.do",
			dataType : "json",
			data : {
				"method" : "sendPrice3Msg",
				"userid" : "${item.userid}",
				"orderid" : "${item.orderid}",
				"linkid" : "${item.uuid}",
				"mtype" : mtype
			},
			success : function(data) {
				if (data.ret == 1) {
					alert("发送成功。");
				} else {
					alert("发送失败。");
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				alert("网络不畅通。");
			}
		});

	}
	
	function price3aMsgSend() {
		if(confirm("发送出车通知前请先确认收到预付款并完成派单，把订单状态设置为\"已派单\"，并点击\"提交\"保存到服务器。\r\n是否发送通知消息？")==false) return;
		$.ajax({
			type : "post",
			url : "/emApp.do",
			dataType : "json",
			data : {
				"method" : "sendPrice3aMsg",
				"userid" : "${item.userid}",
				"orderid" : "${item.orderid}",
				"linkid" : "${item.uuid}"
			},
			success : function(data) {
				if (data.ret == 1) {
					alert("发送成功。");
				} else {
					alert("发送失败。");
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				alert("网络不畅通。");
			}
		});

	}
</script>