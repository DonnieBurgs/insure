// JavaScript Document
	var telReg = /^(((13[0-9]{1})|(14[0-9]{1})|(17[0]{1})|(15[0-3]{1})|(15[5-9]{1})|(18[0-9]{1}))+\d{8})$/;  
	var nameReg=/^[\u2E80-\uFE4F]+$/;
	var emailReg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
	var strongRegex = new RegExp("^(?=.{8,})(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*\\W).*$", "g"); 
	var mediumRegex = new RegExp("^(?=.{7,})(((?=.*[A-Z])(?=.*[a-z]))|((?=.*[A-Z])(?=.*[0-9]))|((?=.*[a-z])(?=.*[0-9]))).*$", "g"); 
	var enoughRegex = new RegExp("(?=.{6,}).*", "g"); 
$('form').submit(function(e){//表单提交前校验
	$('.form-must').each(function(){//必填项
		var input = $(this).find('input').val();
		var inputName = $(this).find('input').attr('name');
		if(input===''){
			e.preventDefault();
			$(this).find('.form-error').text('此处必填').animate({top:'45px',opacity:'1'},500);
			$(this).find('input').addClass('red')
		}else{
			if(inputName ==='form-tel'){//手机格式校验
				if (input.length != 11){
					e.preventDefault();
					$(this).find('.form-error').text('请输入11位手机号').animate({top:'45px',opacity:'1'},500);
					$(this).find('input').addClass('red')
				}else if (!telReg.test(input)){
					e.preventDefault();
					$(this).find('.form-error').text('手机号不合法').animate({top:'45px',opacity:'1'},500);
					$(this).find('input').addClass('red')
				}
			}else if(inputName ==='form-name'){//中文校验
				if(input.length===0){
					e.preventDefault();
					$(this).find('.form-error').text('此处必填').animate({top:'45px',opacity:'1'},500);
					$(this).find('input').addClass('red')
				}else if(!nameReg.test(input)){
					e.preventDefault();
					$(this).find('.form-error').text('名字格式不正确').animate({top:'45px',opacity:'1'},500);
					$(this).find('input').addClass('red')
				}
			}else if(inputName ==='form-password'){//密码校验
				if (false == enoughRegex.test($(this).val())) { 
					e.preventDefault();
					$(this).find('.form-error').text('密码强度不足').animate({top:'45px',opacity:'1'},500);
					$(this).find('input').addClass('red')
				}
			}else if(inputName==='form-confirmPassword'){//两次密码校验
				var password = $(this).parents('form').find('input[name="form-password"]').val();
				if(input != password){
					e.preventDefault();
					$(this).find('.form-error').text('两次输入密码不一致').animate({top:'45px',opacity:'1'},500);
					$(this).find('input').addClass('red')
				}
			}else if(inputName==='form-email'){
				if(!emailReg.test(input)){
					e.preventDefault();
					$(this).find('.form-error').text('邮箱格式不正确').animate({top:'45px',opacity:'1'},500);
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
		$(this).siblings('.form-error').text('此处必填').animate({top:'45px',opacity:'1'},500);
		$(this).addClass('red')
	}else{
		if(inputName ==='form-tel'){//手机格式校验
			if (input.length != 11){
				$(this).siblings('.form-error').text('请输入11位手机号').animate({top:'45px',opacity:'1'},500);
				$(this).find('input').addClass('red')
			}else if (!telReg.test(input)){
				$(this).siblings('.form-error').text('手机号不合法').animate({top:'45px',opacity:'1'},500);
				$(this).addClass('red')
			}
		}else if(inputName ==='form-name'){//中文校验
			if(input.length===0){
				$(this).siblings('.form-error').text('此处必填').animate({top:'45px',opacity:'1'},500);
				$(this).addClass('red')
			}else if(!nameReg.test(input)){
				$(this).siblings('.form-error').text('名字格式不正确').animate({top:'45px',opacity:'1'},500);
				$(this).addClass('red')
			}
		}else if(inputName ==='form-password'){//密码校验
			if (false == enoughRegex.test($(this).val())) { 
				$(this).siblings('.form-error').text('密码强度不足').animate({top:'45px',opacity:'1'},500);
				$(this).addClass('red')
			}
		}else if(inputName==='form-confirmPassword'){//两次密码校验
			var password = $(this).parents('form').find('input[name="form-password"]').val();
			if(input != password){
				$(this).siblings('.form-error').text('两次输入密码不一致').animate({top:'45px',opacity:'1'},500);
				$(this).addClass('red')
			}
		}else if(inputName==='form-email'){
			if(!emailReg.test(input)){
				$(this).siblings('.form-error').text('邮箱格式不正确').animate({top:'45px',opacity:'1'},500);
				$(this).addClass('red')
			}
		}
	}
});

$('input[name="form-password"]').keyup(function(){
	if (false == enoughRegex.test($(this).val())) { 
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
		$(this).siblings('.form-error').text('名字格式不正确').animate({top:'45px',opacity:'1'},500);
		$(this).addClass('red')
	}
})
$('form input').focus(function(){//获得焦点删除警告
	$(this).siblings('.form-error').text('').removeAttr('style')
	$(this).removeClass('red')
})




