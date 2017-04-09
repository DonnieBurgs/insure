if(navigator.userAgent.match(/(iPhone|iPod|Android|ios)/i)){
	$('html,body').width('1100px')
};
// JavaScript Document
var telReg = /^(0|86|17951)?(13[0-9]|15[012356789]|17[014678]|18[0-9]|14[57])[0-9]{8}$/;
var nameReg = /^[\u2E80-\uFE4F]+$/;
var emailReg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
var telCodeReg = /^\d{6}$/;// 验证码
var logintype = 0 ;
function loginformcheck() {// 表单提交前校验
	var err = 0;
	$('form').find('.login-must').each(function() {// 必填项
		var input = $(this).find('input').val();
		var inputName = $(this).find('input').attr('name');
		if (inputName == 'login-tel' || inputName == 'login-telCode' || inputName == 'login-password') {
			if (input === '') {
				$(this).find('.form-error').text('此处必填').animate({
					top : '45px',
					opacity : '1'
				}, 500);
				$(this).find('input').addClass('red');
				err = 1;
			} else {
				if (inputName === 'login-tel') {// 手机格式校验
					if (input.length != 11) {
						$(this).find('.form-error').text('请输入11位手机号').animate({
							top : '45px',
							opacity : '1'
						}, 500);
						$(this).find('input').addClass('red');
						err = 2;
					} else if (!telReg.test(input)) {
						$(this).find('.form-error').text('手机号不合法').animate({
							top : '45px',
							opacity : '1'
						}, 500);
						$(this).find('input').addClass('red');
						err = 3;
					}
				} else if (inputName === 'login-telCode') {// 中文校验
					if (input.length === 0) {
						$(this).find('.form-error').text('此处必填').animate({
							top : '45px',
							opacity : '1'
						}, 500);
						$(this).find('input').addClass('red');
						err = 4;
					} else if (input.length < 4) {
						$(this).find('.form-error').text('短信验证码错误').animate({
							top : '45px',
							opacity : '1'
						}, 500);
						$(this).find('input').addClass('red');
						err = 5;
					}
				} else if (inputName === 'login-password') {// 中文校验
					if (input.length === 0) {
						$(this).find('.form-error').text('此处必填').animate({
							top : '45px',
							opacity : '1'
						}, 500);
						$(this).find('input').addClass('red');
						err = 4;
					} else if (input.length < 6) {
						$(this).find('.form-error').text('密码至少6位字符').animate({
							top : '45px',
							opacity : '1'
						}, 500);
						$(this).find('input').addClass('red');
						err = 5;
					}
				}		
			}
			

		}
	});
	return err;
}

$('.login-must input').blur(
		function() {// 必填项失去焦点校验
			var input = $(this).val();
			var inputName = $(this).attr('name');
			if (inputName == 'login-tel' || inputName == 'login-telCode' ||  inputName == 'login-password') {
				if (input === '') {
					$(this).siblings('.form-error').text('此处必填').animate({
						top : '45px',
						opacity : '1'
					}, 500);
					$(this).addClass('red')
				} else {
					if (inputName === 'login-tel') {// 手机格式校验
						if (input.length != 11) {
							$(this).siblings('.form-error').text('请输入11位手机号')
									.animate({
										top : '45px',
										opacity : '1'
									}, 500);
							$(this).find('input').addClass('red')
						} else if (!telReg.test(input)) {
							$(this).siblings('.form-error').text('手机号不合法').animate(
									{
										top : '45px',
										opacity : '1'
									}, 500);
							$(this).addClass('red')
						}
					} else if (inputName === 'login-telCode') {// 中文校验
						if (input.length === 0) {
							$(this).siblings('.form-error').text('此处必填').animate({
								top : '45px',
								opacity : '1'
							}, 500);
							$(this).addClass('red')
						} else if (input.length < 4) {
							$(this).siblings('.form-error').text('短信验证码错误')
									.animate({
										top : '45px',
										opacity : '1'
									}, 500);
							$(this).addClass('red')
						}
					} else if (inputName === 'login-password') {// 中文校验
						if (input.length === 0) {
							$(this).siblings('.form-error').text('此处必填').animate({
								top : '45px',
								opacity : '1'
							}, 500);
							$(this).addClass('red')
						} else if (input.length < 6) {
							$(this).siblings('.form-error').text('密码至少6位字符')
									.animate({
										top : '45px',
										opacity : '1'
									}, 500);
							$(this).addClass('red')
						}
					}
				}
			}

		});

$('input[name="login-tel"]').on('focus input propertychange click', function() {// 清除数字以外的字符
	tel = $(this).val()
	var bbb = tel.replace(/[^\d]/g, "")
	$(this).val(bbb)
})
$('form input').focus(function() {// 获得焦点删除警告
	$(this).siblings('.form-error').text('').removeAttr('style')
	$(this).removeClass('red')
})


// 发送手机验证码
$('.loginCode')
		.click(
				function() {
					var ver_phone = /^(0|86|17951)?(13[0-9]|15[012356789]|17[014678]|18[0-9]|14[57])[0-9]{8}$/;// 手机验证
					var tel = $(this).siblings('input[name="login-tel"]').val();// 手机号
					if (tel == null || !ver_phone.test(tel)) {
						$(this).siblings('.form-error').text('手机号不合法').animate(
								{
									top : '45px',
									opacity : '1'
								}, 500);
						$(this).siblings('input[name="login-tel"]').addClass(
								'red')
						return;
					} else {
						$(this).unbind("click");
						hairCodeId = $(this).attr('id');
						loginSend();
					}
				});

function loginSend() {
	//alert("验证码已发送至您的手机，请查收");
	$('#loginAlertStr').html("");
	$.ajax({ 
	    type: "post", 
	    url: "/emApp.do", 
	    dataType: "json",
	    data:{"method": "sendCode","mobile": logintel.value},
	    success: function (data) {
	    	if(data.ret==1) {
				$('#loginAlertStr').html(data.msg);
				
	    	} else {
	    		$('#loginAlertStr').html(data.msg);
	    	}
	    }, 
	    error: function (XMLHttpRequest, textStatus, errorThrown) { 
	    	$('#loginAlertStr').html("操作失败，请稍后再试。");
	    } 
	});
	
	var time = 60;
	timer = setInterval(
			function() {
				time--;
				$('#' + hairCodeId).html(time + "秒").css('background', '#ddd');
				if (time <= 0) {
					clearInterval(timer);
					$('#' + hairCodeId).html("获取验证码").attr('style', '');
					$('#' + hairCodeId).click(
							
									function() {
										var ver_phone = /^(0|86|17951)?(13[0-9]|15[012356789]|17[014678]|18[0-9]|14[57])[0-9]{8}$/;// 手机验证
										var tel = $(this).siblings(
												'input[name="login-tel"]').val();// 手机号
										if (tel == null || !ver_phone.test(tel)) {
											$(this).siblings('.form-error')
													.text('手机号不合法').animate({
														top : '45px',
														opacity : '1'
													}, 500);
											$(this).siblings(
													'input[name="login-tel"]')
													.addClass('red')
											return;
										} else {
											$(this).unbind("click");
											hairCodeId = $(this).attr('id');
											loginSend();
										}
									});

				}
			}, 1000);
}

$('.loginPupopClose').click(function(){
	$('.loginBox').hide();
})

function hideLoginBox() {
	$('.loginBox').hide();
}

function toLogin() {
	$('#logintel').val("13700000005");
	$('#logintelCode').val("111111");
	$('#loginpassword').val("111111");
	$('#loginAlertStr').html("");
	$('.loginBox').show()

	var winHeight = $(window).height()-$('.login').height();
	var top = winHeight/2;
	$('.login').css('top',top)


}

function toLogout() {
	$.ajax({ 
	    type: "post", 
	    url: "/emApp.do", 
	    dataType: "json",
	    data:{"method": "userLogout"},
	    success: function (data) {
			setupLogout();
	    }, 
	    error: function (XMLHttpRequest, textStatus, errorThrown) { 
	    	$('#loginAlertStr').html("操作失败，请稍后再试。");
	    } 
	});


}

function toMan() {
	location.href="/admin.do?method=index";
}

function setupL() {
//	if(window.localStorage.getItem("uu")) {
//		$('#btnLogin').click(function(){
			//$('.loginBox').show()
//		})
		$('#btnLogin').html("控制中心");
		$('#btnLogin').attr("href", "javascript:toMan();");
		$('#btnLogin2').html("退出");
		$('#btnLogin2').attr("href", "javascript:toLogout();");
		$('#btnLogin2').show();
//	} else {
		
//	}
	
	
}

function setupLogout() {
		$('#btnLogin').html("登录");
		$('#btnLogin').attr("href", "javascript:toLogin();");
		$('#btnLogin2').hide();
	
}

function loginformsubmit() {
	$('#loginAlertStr').html("");

	if(loginformcheck()==0) {
		$.ajax({ 
		    type: "post", 
		    url: "/emApp.do", 
		    dataType: "json",
		    data:{"method": "userLogin","mobile": logintel.value,"code": logintelCode.value,"ps": loginpassword.value},
		    success: function (data) {
		    	if(data.ret==1) {
					$('#loginAlertStr').html(data.msg);
					window.localStorage.setItem("uu", "" + data.data.userid);
					setupL();
					setTimeout('hideLoginBox()', 2300);
		    		
		    	} else {
		    		$('#loginAlertStr').html(data.msg);
		    	}
		    }, 
		    error: function (XMLHttpRequest, textStatus, errorThrown) { 
		    	$('#loginAlertStr').html("操作失败，请稍后再试。");
		    } 
		});
	
	}

}
