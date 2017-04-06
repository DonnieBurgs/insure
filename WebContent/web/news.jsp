<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>易医通-新闻</title>
<script src="http://libs.baidu.com/jquery/1.8.3/jquery.min.js"></script>
<link rel="stylesheet" type="text/css" href="/style/base.css" />
<link rel="stylesheet" type="text/css" href="/style/global.css" />
<script src="js/screen.js"></script>
</head>

<body class="apply nav2">
<%@include file="/inc/header.jsp"%>
<div class="apply-banner"><img class="w1" src="/image/apply-banner.png" /></div>
<div class="apply-form change-date">
    <form class="wrap" action="" method="post" style="width:1120px;">
    	<h2 class="ta-c c-3" style="font-size:26px;">${item.title }</h2>
    	<div class="applyFooter" style="margin-top:0px;padding-top:0px;width:1120px;">
		<div style="width:100%;text-align:right;">
    	
        	<font class="c8" style="margin-right:15px;">${item.createdate_ }</font>
        	</div>
			<div class="wrap fs-14 c-3" style="margin-top:30px;margin-left:15px;margin-right:15px;">
    		${item.content }
			</div>
		</div>
		
    </form>
</div>
<script src="/js/login.js"></script>
</body>
<script src="/js/form.js"></script>
<script src="/js/formlogin.js"></script>
<script src="/js/geo.js"></script>
<script>
$(document).ready(function(){
//	setupL();
});
applyType();
$("input[name='applyType']").click(function(){
	applyType()
})
function applyType(){//接入/进驻选中状态
	$("input[name='applyType']:checked").parent().siblings().removeClass('active').find('i').remove();;
	$("input[name='applyType']:checked").parent().addClass('active').append('<i class="p-a iconfont">&#xe617;</i>');;
}
$('input[name="applyType"]').each(function(){
	var hf = window.location.href;
	var index = hf.substring(hf.lastIndexOf('#')+1,hf.length);
	var applyTypeVal =$(this).val()
	if(applyTypeVal === index){
		$(this).click()
		applyType()
	}
});
	
$(function(){//上传图片
	$('input[type="file"]').change(function(){
		var name = $(this).val();
		var fileText =name.substring(name.lastIndexOf("."),name.length);
		var fileName =fileText.toLowerCase();
		var error1 = '对不起，系统仅支持标准格式的照片，请您调整格式后重新上传，谢谢 ！'
		if ((fileName!='.jpg')&&(fileName!='.gif')&&(fileName!='.jpeg')&&(fileName!='.png')&&(fileName!='.bmp')){
			$(this).siblings('.form-error').text(error1).animate({top:'10px',opacity:'1'},500);
			$(this).siblings('.file-a').addClass('red')
			$(this).val('')
			$(this).siblings('.file-a').find('.file-route').text('请选择文件')
		}else{
			$(this).siblings('.file-a').find('.file-route').text(name)
		}
	})
})
$('input[type="file"]').each(function() {//刷新上传图片
	var name = $(this).val();
	if(name!=null){
		$(this).siblings('.file-a').find('.file-route').text(name)
	}
});
function promptinfo(){//地址联动
    var address = document.getElementById('address');
    var s1 = document.getElementById('linkage1');
    var s2 = document.getElementById('linkage2');
//    var s3 = document.getElementById('linkage3');
//    var s4 = document.getElementById('linkage4');
    address.value = s1.value + s2.value;
	
}
</script>
</html>
