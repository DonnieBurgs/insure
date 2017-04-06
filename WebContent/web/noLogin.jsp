<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>登录超时</title>
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
    	<h2 class="ta-c c-3">操作超时</h2>
        
        <div class="applyFooter">
			<div class="wrap fs-24 c-3">
    			请重新登录。&nbsp;&nbsp;&nbsp;<a href="/index.do">返回主页</a>&nbsp;&nbsp;&nbsp;<a href="javascript:toLogin();">重新登录</a>
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

