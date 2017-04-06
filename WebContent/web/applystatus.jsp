<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>审核状态查询</title>
<script src="http://libs.baidu.com/jquery/1.8.3/jquery.min.js"></script>
<link rel="stylesheet" type="text/css" href="/style/base.css" />
<link rel="stylesheet" type="text/css" href="/style/global.css" />
<script src="js/screen.js"></script>
</head>

<body class="state nav2">
<%@include file="/inc/header.jsp"%>
<div class="stateBanner"></div>
<div class="wrap state-form">
	<form action="/applySubmit.do" method="post">
    	<h2 class="ta-c c-3">审核状态查询</h2>
        <div class="form-list p-r form-must">
            <span class="form-title d-ib">手机号码:</span>
            <input class="br-5" type="tel" name="status-tel" id="formtel" maxlength="11" />
            <a class="stateCode br-5 ta-c d-ib fs-22 c-6" href="javascript:;" id="hairCode1">发送验证码</a>
            <div class="p-a form-error"></div>
        </div>
        <div class="form-list p-r form-must">
            <span class="form-title d-ib">验证码:</span>
            <input class="br-5" type="text" name="status-telCode" maxlength="6" />
            <div class="p-a form-error"></div>
        </div>
        <div class="submit ta-c"><button class="btn1 br-50" type="button" style="margin-top:30px;" onclick="formsubmit();">提交</button></div>
        <p class="fs-26 c-6" id="alertStr"></p>
    </form>
</div>
<script src="/js/login.js"></script>
</body>
<script src="/js/formstate.js"></script>
<script src="/js/formlogin.js"></script>
</html>
<script language=javascript>
function formsubmit() {
//	if(commonForm.check_phone.value=="" || checkmobile(commonForm.check_phone.value)=="") {wapalert('请正确填写手机号码。');return false;}
	if(formcheck()==0) {
		$.ajax({ 
		    type: "post", 
		    url: "apply.do?method=asSubmit&mobile="+formtel.value, 
		    dataType: "json", 
		    success: function (data) {
				$('#alertStr').html("查询结果："+data.msg);

		    }, 
		    error: function (XMLHttpRequest, textStatus, errorThrown) { 
		    	$('#alertStr').html("操作失败，请稍后再试。");
		    } 
		});
	
	}

}
</script>

