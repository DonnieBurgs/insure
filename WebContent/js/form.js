// JavaScript Document
	var telReg = /^(0|86|17951)?(13[0-9]|15[012356789]|17[014678]|18[0-9]|14[57])[0-9]{8}$/;  
	var nameReg=/^[\u2E80-\uFE4F]+$/;
	var emailReg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
	var telCodeReg = /^\d{6}$/;//验证码
$('form').submit(function(e){//表单提交前校验
	$(this).find('.form-must').each(function(){//必填项
		var input = $(this).find('input').val();
		var inputName = $(this).find('input').attr('name');
		if(input===''){
			e.preventDefault();
			$(this).find('.form-error').text('此处必填').animate({top:'10px',opacity:'1'},500);
			$(this).find('input').addClass('red')
		}else{
			if(inputName ==='form-tel'){//手机格式校验
				if (input.length != 11){
					e.preventDefault();
					$(this).find('.form-error').text('请输入11位手机号').animate({top:'10px',opacity:'1'},500);
					$(this).find('input').addClass('red')
				}else if (!telReg.test(input)){
					e.preventDefault();
					$(this).find('.form-error').text('手机号不合法').animate({top:'10px',opacity:'1'},500);
					$(this).find('input').addClass('red')
				}
			}else if(inputName ==='form-name'){//中文校验
				if(input.length===0){
					e.preventDefault();
					$(this).find('.form-error').text('此处必填').animate({top:'10px',opacity:'1'},500);
					$(this).find('input').addClass('red')
				}else if(!nameReg.test(input)){
					e.preventDefault();
					$(this).find('.form-error').text('名字格式不正确').animate({top:'10px',opacity:'1'},500);
					$(this).find('input').addClass('red')
				}
			}else if(inputName ==='form-telCode'){//中文校验
				if(input.length===0){
					e.preventDefault();
					$(this).find('.form-error').text('此处必填').animate({top:'10px',opacity:'1'},500);
					$(this).find('input').addClass('red')
				}else if(input.length<4){
					e.preventDefault();
					$(this).find('.form-error').text('短信验证码错误').animate({top:'60px',opacity:'1'},500);
					$(this).find('input').addClass('red')
				}
			}else if(inputName ==='form-password'){//密码校验
				var strongRegex = new RegExp("^(?=.{8,})(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*\\W).*$", "g"); 
				var mediumRegex = new RegExp("^(?=.{7,})(((?=.*[A-Z])(?=.*[a-z]))|((?=.*[A-Z])(?=.*[0-9]))|((?=.*[a-z])(?=.*[0-9]))).*$", "g"); 
				var enoughRegex = new RegExp("(?=.{6,}).*", "g"); 
				if (false == enoughRegex.test($(this).val())) { 
					//e.preventDefault();
					//$(this).find('.form-error').text('密码强度不足').animate({top:'60px',opacity:'1'},500);
					//$(this).find('input').addClass('red')
				}
			}else if(inputName==='form-confirmPassword'){//两次密码校验
				var password = $(this).parents('form').find('input[name="form-password"]').val();
				if(input != password){
					e.preventDefault();
					$(this).find('.form-error').text('两次输入密码不一致').animate({top:'60px',opacity:'1'},500);
					$(this).find('input').addClass('red')
				}
			}else if(inputName==='form-email'){
				if(!emailReg.test(input)){
					e.preventDefault();
					$(this).find('.form-error').text('邮箱格式不正确').animate({top:'60px',opacity:'1'},500);
					$(this).find('input').addClass('red')
				}
			}
		}
    });
})
$('.form-must input').blur(function(){//必填项失去焦点校验
	var input = $(this).val();
	var inputName = $(this).attr('name');
	if(input===''){
		$(this).siblings('.form-error').text('此处必填').animate({top:'60px',opacity:'1'},500);
		$(this).addClass('red')
	}else{
		if(inputName ==='form-tel'){//手机格式校验
			if (input.length != 11){
				$(this).siblings('.form-error').text('请输入11位手机号').animate({top:'60px',opacity:'1'},500);
				$(this).find('input').addClass('red')
			}else if (!telReg.test(input)){
				$(this).siblings('.form-error').text('手机号不合法').animate({top:'60px',opacity:'1'},500);
				$(this).addClass('red')
			}
		}else if(inputName ==='form-name'){//中文校验
			if(input.length===0){
				$(this).siblings('.form-error').text('此处必填').animate({top:'60px',opacity:'1'},500);
				$(this).addClass('red')
			}else if(!nameReg.test(input)){
				$(this).siblings('.form-error').text('名字格式不正确').animate({top:'60px',opacity:'1'},500);
				$(this).addClass('red')
			}
		}else if(inputName ==='form-telCode'){//中文校验
			if(input.length===0){
				$(this).siblings('.form-error').text('此处必填').animate({top:'60px',opacity:'1'},500);
				$(this).addClass('red')
			}else if(input.length<4){
				$(this).siblings('.form-error').text('短信验证码错误').animate({top:'60px',opacity:'1'},500);
				$(this).addClass('red')
			}
		}else if(inputName ==='form-password'){//密码校验
			if (false == enoughRegex.test($(this).val())) { 
				$(this).siblings('.form-error').text('密码强度不足').animate({top:'60px',opacity:'1'},500);
				$(this).addClass('red')
			}
		}else if(inputName==='form-confirmPassword'){//两次密码校验
			var password = $(this).parents('form').find('input[name="form-password"]').val();
			if(input != password){
				$(this).siblings('.form-error').text('两次输入密码不一致').animate({top:'60px',opacity:'1'},500);
				$(this).addClass('red')
			}
		}else if(inputName==='form-email'){
			if(!emailReg.test(input)){
				$(this).siblings('.form-error').text('邮箱格式不正确').animate({top:'60px',opacity:'1'},500);
				$(this).addClass('red')
			}
		}
	}
});

$('input[name="form-password"]').keyup(function(){
		var strongRegex = new RegExp("^(?=.{8,})(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*\\W).*$", "g"); 
		var mediumRegex = new RegExp("^(?=.{7,})(((?=.*[A-Z])(?=.*[a-z]))|((?=.*[A-Z])(?=.*[0-9]))|((?=.*[a-z])(?=.*[0-9]))).*$", "g"); 
		var enoughRegex = new RegExp("(?=.{6,}).*", "g"); 
	if ($(this).val().length<6) { 
		$('.form-passwordLevel').removeClass('pw-weak'); 
		$('.form-passwordLevel').removeClass('pw-medium'); 
		$('.form-passwordLevel').removeClass('pw-strong'); 
		$('.form-passwordLevel').addClass('pw-defule'); 
		$('.form-passwordLevel-text').text('密码不足6位')
				
		 //密码小于六位的时候，密码强度图片都为灰色 
	}else if (strongRegex.test($(this).val())) { 
		$('.form-passwordLevel').removeClass('pw-weak'); 
		$('.form-passwordLevel').removeClass('pw-medium'); 
		$('.form-passwordLevel').removeClass('pw-strong'); 
		$('.form-passwordLevel').addClass(' pw-strong'); 
		$('.form-passwordLevel-text').text('强')
		 //密码为八位及以上并且字母数字特殊字符三项都包括,强度最强 
	}else if (mediumRegex.test($(this).val())) { 
		$('.form-passwordLevel').removeClass('pw-weak'); 
		$('.form-passwordLevel').removeClass('pw-medium'); 
		$('.form-passwordLevel').removeClass('pw-strong'); 
		$('.form-passwordLevel').addClass(' pw-medium'); 
		$('.form-passwordLevel-text').text('中')
		 //密码为七位及以上并且字母、数字、特殊字符三项中有两项，强度是中等 
	}else{ 
		$('.form-passwordLevel').removeClass('pw-weak'); 
		$('.form-passwordLevel').removeClass('pw-medium'); 
		$('.form-passwordLevel').removeClass('pw-strong'); 
		$('.form-passwordLevel').addClass('pw-weak'); 
		$('.form-passwordLevel-text').text('弱')
		 //如果密码为6为及以下，就算字母、数字、特殊字符三项都包括，强度也是弱的 
	} 
})
$('input[name="form-tel"]').on('focus input propertychange click',function(){//清除数字以外的字符
	tel = $(this).val()
	var bbb = tel.replace(/[^\d]/g,"")
	$(this).val(bbb)
})
$('input[name="form-name"]').blur(function(){//清除中文以外的字符
	var formNamereg=/^[\u2E80-\uFE4F]+$/;
	if(!formNamereg.test($(this).val())){
		$(this).siblings('.form-error').text('名字格式不正确').animate({top:'60px',opacity:'1'},500);
		$(this).addClass('red')
	}
})
$('form input').focus(function(){//获得焦点删除警告
	$(this).siblings('.form-error').text('').removeAttr('style')
	$(this).removeClass('red')
})

//发送手机验证码
$('.hairCode').click(function(){
	var ver_phone = /^(0|86|17951)?(13[0-9]|15[012356789]|17[014678]|18[0-9]|14[57])[0-9]{8}$/;// 手机验证
	var tel = $(this).siblings('input[name="form-tel"]').val();// 手机号
	if (tel == null || !ver_phone.test(tel)) {
		$(this).siblings('.form-error').text('手机号不合法').animate({top:'60px',opacity:'1'},500);
		$(this).siblings('input[name="form-tel"]').addClass('red')
		return;
	}else {
		$(this).unbind("click");
		hairCodeId = $(this).attr('id');
		lateSend();
	}
});

function lateSend() {
	$.ajax({ 
	    type: "post", 
	    url: "/emApp.do", 
	    dataType: "json",
	    data:{"method": "sendCode","mobile": formTel.value,"type":3},
	    success: function (data) {
	    	if(data.ret==1) {
				alert(data.msg);
				
	    	} else {
	    		alert(data.msg);
	    	}
	    }, 
	    error: function (XMLHttpRequest, textStatus, errorThrown) { 
	    	alert("操作失败，请稍后再试。");
	    } 
	});
	
	var time=60;
	timer=setInterval(function(){
		time--;
		$('#'+hairCodeId).html(time+"秒").css('background','#ddd');
		if (time<=0) {
			clearInterval(timer);
			$('#'+hairCodeId).html("获取验证码").attr('style','');
			$('#'+hairCodeId).bind("click",function(){
				var ver_phone = /^(0|86|17951)?(13[0-9]|15[012356789]|17[014678]|18[0-9]|14[57])[0-9]{8}$/;// 手机验证
				var tel = $(this).siblings('input[name="form-tel"]').val();// 手机号
				if (tel == null || !ver_phone.test(tel)) {
					$(this).siblings('.form-error').text('手机号不合法').animate({top:'60px',opacity:'1'},500);
					$(this).siblings('input[name="form-tel"]').addClass('red')
					return;
				}else {
					$(this).unbind("click");
					hairCodeId = $(this).attr('id');
					lateSend();
				}
			});

		}
	},1000);
}

/*var ver_phone = /^(0|86|17951)?(13[0-9]|15[012356789]|17[014678]|18[0-9]|14[57])[0-9]{8}$/;// 手机验证
var ver_code = /^\d{6}$/;//验证码
//发送手机验证码
send_sms_byreg = function() {
	var that = $(this).text()
	alert(that)
	var islanding = $('input[name="islanding"]').val();
	var phone = $('input[name="phone"]').val();// 手机号
	if (phone == undefined || !ver_phone.test(phone)) {
		newtips.errorinfo("请输入有效的手机号码！！");
		return;
	} else {
		$('#send_sms_btn').attr('onclick','');
		fn_ajax("/mobileRegistration/sendSMS", {
			phone : phone,
			islanding:islanding,
			versign : _sinx
		}, function(data) {
			if (data == "0") {
				newtips.succeedinfo("验证码已发送至您的手机，请查收");
				lateSend();
			} else if(data == "1"){
				newtips.errorinfo("请勿频繁刷新手机验证码！！");
				lateSend();
			} else if(data == "2"){
				newtips.errorinfo("手机验证码发送失败，请重试！！");
				$('#send_sms_btn').attr('onclick','send_sms_byreg()');
			} else if(data == "3"){
				newtips.errorinfo("该手机已注册！！");
				$('#send_sms_btn').attr('onclick','send_sms_byreg()');
				
			} else if(data == "4"){
				location.href = "mobileRegistration/to-regist";
			}else if(data == "5"){
				location.href = "mobileVisitor/landingpage";
			}else if(data == "6"){
				location.href = "mobileVisitor/event9_18_wx";
			}
		});
	}
}

function lateSend() {
		var time=60;
		timer=setInterval(function  () {
			time--;
			$('#send_sms_btn').html(time+"秒").css('background','#ddd');
			if (time<=0) {
				clearInterval(timer);
				$('#send_sms_btn').html("获取验证码").attr('style','');
				$('#send_sms_btn').attr("onclick","send_sms_byreg()");
			}
		},1000);
}*/
			


