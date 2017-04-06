<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>申请</title>
<script src="http://libs.baidu.com/jquery/1.8.3/jquery.min.js"></script>
<link rel="stylesheet" type="text/css" href="/style/base.css" />
<link rel="stylesheet" type="text/css" href="/style/global.css" />
<script src="js/screen.js"></script>
</head>

<body class="apply nav2"  onload="setup();preselect('广东省');promptinfo();">
<%@include file="/inc/header.jsp"%>
<div class="apply-banner"><img class="w1" src="/image/apply-banner.png" /></div>
<div class="apply-form change-date">
    <form class="wrap" action="/applySubmit.do" method="post" enctype="multipart/form-data">
    	<h2 class="ta-c c-3">进驻/接入申请</h2>
        <div class="form-list p-r form-must">
            <span class="form-title d-ib">联系人姓名:</span>
            <input class="br-5" type="text" name="form-name" maxlength="20" />
            <div class="p-a form-error"></div>
        </div>
        <div class="form-list p-r form-must">
            <span class="form-title d-ib">公司名称:</span>
            <input class="br-5" type="text" name="form-nickname" maxlength="20" />
            <div class="p-a form-error"></div>
        </div>
        <div class="form-list p-r">
            <span class="form-title d-ib">公司所在地:</span>
            <select class="select br-5 p-r" name="province" id="linkage1"><i class="iconfont"></i>
            </select>
            <select class="select br-5 p-r" name="city" id="linkage2">
            </select>
            <!-- 
            <select class="select br-5 p-r" name="town" id="linkage3">
            </select>
            <select class="select d-n" name="hidden" id="linkage4">
            </select> -->
            <input id="address" name="address" type="hidden" value="" />
            <div class="p-a form-error"></div>
        </div>
        <div class="form-list p-r form-must">
            <span class="form-title d-ib">联系人手机:</span>
            <input class="br-5" type="tel" name="form-tel" id="formTel" maxlength="11" />
            <a class="hairCode br-5 ta-c d-ib fs-22 c-6" href="javascript:;" id="hairCode1">发送验证码</a>
            <div class="p-a form-error"></div>
        </div>
        <div class="form-list p-r form-must">
            <span class="form-title d-ib">短信验证码:</span>
            <input class="br-5" type="text" name="form-telCode" maxlength="6" />
            <div class="p-a form-error"></div>
        </div>
        <div class="form-list p-r form-must">
            <span class="form-title d-ib">设置密码:</span>
            <input class="br-5" type="password" name="form-password" maxlength="20" />
            <div class="p-a form-error"></div>
        </div>
        <div class="form-list p-r">
            <span class="form-title d-ib">密码强度:</span>
            <div class="form-passwordLevel d-ib br-50 o-h p-r"><i class="d-ib"></i></div>
            <span class="form-passwordLevel-text fs-22 c-6"></span>
        </div>
        <div class="form-list p-r form-must">
            <span class="form-title d-ib">验证密码:</span>
            <input class="br-5" type="password" name="form-confirmPassword" maxlength="20" />
            <div class="p-a form-error"></div>
        </div>
        <div class="p-r ta-c" id="test">
            <label class="p-r d-ib c-6 ta-c o-h c-p"><input class="p-a" type="radio" name="applyType" value="in" checked>进驻</label>
            <label class="p-r d-ib c-6 ta-c o-h c-p"><input class="p-a" type="radio" name="applyType" value="access">接入</label>
        </div>
        <div class="form-list p-r form-must">
            <span class="form-title d-ib">营业执照:</span>
            <a class="file-a d-ib c-6 br-5 o-h" href="javascript:;"><span class="file-route fs-20 f-l">请选择文件</span><span class="file-sub f-r fs-26">上传</span></a>
            <input class="br-5 p-a c-p" type="file" name="form-file" maxlength="50" />
            <div class="p-a form-error"></div>
        </div>
        <div class="form-list p-r form-must">
            <span class="form-title d-ib">申请人身份证:</span>
            <a class="file-a d-ib c-6 br-5 o-h" href="javascript:;"><span class="file-route fs-20 f-l">请选择文件</span><span class="file-sub f-r fs-26">上传</span></a>
            <input class="br-5 p-a c-p" type="file" name="form-file2" maxlength="50" />
            <div class="p-a form-error"></div>
        </div>
        <div class="ta-c"><p class="fs-26 c-6" style="" id="applyAlertStr"></p></div>
        <div class="submit ta-c"><button class="btn1 br-50" type="submit">提交</button></div>
    </form>
</div>
<div class="applyFooter">
	<div class="wrap fs-24 c-3">
    	已提交过申请：&nbsp;&nbsp;&nbsp;<a href="/apply.do?method=applystatus">查看审核状态</a>
    </div>
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
