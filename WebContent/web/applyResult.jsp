<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>进驻/接入申请</title>
<script src="http://libs.baidu.com/jquery/1.8.3/jquery.min.js"></script>
<link rel="stylesheet" type="text/css" href="/style/base.css" />
<link rel="stylesheet" type="text/css" href="/style/global.css" />
<script src="js/screen.js"></script>
</head>

<body class="state nav2">
<%@include file="/inc/header.jsp"%>
<div class="stateBanner"></div>
<div class="wrap state-form">
	<form action="/apply111.do" method="post">
    	<h2 class="ta-c c-3">进驻/接入申请</h2>
        
        <div class="applyFooter">
			<div class="wrap fs-24 c-3">
    			<c:if test="${applyresult eq '1'}">	资料提交成功，请等待系统审核。&nbsp;&nbsp;&nbsp;<a href="/index.do">返回主页</a></c:if>
    			<c:if test="${applyresult eq '2'}">	资料提交失败，请稍后再试。&nbsp;&nbsp;&nbsp;<a href="javascript:history.back();">返回</a>&nbsp;&nbsp;&nbsp;<a href="/index.do">返回主页</a></c:if>
    			<c:if test="${applyresult eq '3'}">	您的资料已经提交过，请不要重复提交。&nbsp;&nbsp;&nbsp;<a href="javascript:history.back();">返回</a>&nbsp;&nbsp;&nbsp;<a href="/index.do">返回主页</a></c:if>
    			<c:if test="${applyresult eq '4'}">	无效验证码。&nbsp;&nbsp;&nbsp;<a href="javascript:history.back();">返回</a>&nbsp;&nbsp;&nbsp;<a href="/index.do">返回主页</a></c:if>
    		</div>
		</div>
        
    </form>
</div>
<script src="/js/login.js"></script>
</body>
<script src="/js/formlogin.js"></script>
</html>
<script language=javascript>
</script>

