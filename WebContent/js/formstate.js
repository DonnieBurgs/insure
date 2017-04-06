// JavaScript Document
var telReg = /^(0|86|17951)?(13[0-9]|15[012356789]|17[014678]|18[0-9]|14[57])[0-9]{8}$/;
var nameReg = /^[\u2E80-\uFE4F]+$/;
var emailReg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
var telCodeReg = /^\d{6}$/;// 验证码
function formcheck() {// 表单提交前校验
	var err = 0;
	$('form').find('.form-must').each(function() {// 必填项
		var input = $(this).find('input').val();
		var inputName = $(this).find('input').attr('name');
		if (input === '' && (inputName == 'status-tel' || inputName == 'status-telCode')) {
			$(this).find('.form-error').text('此处必填').animate({
				top : '60px',
				opacity : '1'
			}, 500);
			$(this).find('input').addClass('red');
			err = 1;
		} else {
			if (inputName === 'status-tel') {// 手机格式校验
				if (input.length != 11) {
					$(this).find('.form-error').text('请输入11位手机号').animate({
						top : '60px',
						opacity : '1'
					}, 500);
					$(this).find('input').addClass('red');
					err = 2;
				} else if (!telReg.test(input)) {
					$(this).find('.form-error').text('手机号不合法').animate({
						top : '60px',
						opacity : '1'
					}, 500);
					$(this).find('input').addClass('red');
					err = 3;
				}
			} else if (inputName === 'status-telCode') {// 中文校验
				if (input.length === 0) {
					$(this).find('.form-error').text('此处必填').animate({
						top : '60px',
						opacity : '1'
					}, 500);
					$(this).find('input').addClass('red');
					err = 4;
				} else if (input.length < 6) {
					$(this).find('.form-error').text('短信验证码错误').animate({
						top : '60px',
						opacity : '1'
					}, 500);
					$(this).find('input').addClass('red');
					err = 5;
				}
			}
		}
	});
	return err;
}

$('.form-must input').blur(
		function() {// 必填项失去焦点校验
			var input = $(this).val();
			var inputName = $(this).attr('name');
			if (input === '') {
				$(this).siblings('.form-error').text('此处必填').animate({
					top : '60px',
					opacity : '1'
				}, 500);
				$(this).addClass('red')
			} else {
				if (inputName === 'status-tel') {// 手机格式校验
					if (input.length != 11) {
						$(this).siblings('.form-error').text('请输入11位手机号')
								.animate({
									top : '60px',
									opacity : '1'
								}, 500);
						$(this).find('input').addClass('red')
					} else if (!telReg.test(input)) {
						$(this).siblings('.form-error').text('手机号不合法').animate(
								{
									top : '60px',
									opacity : '1'
								}, 500);
						$(this).addClass('red')
					}
				} else if (inputName === 'status-telCode') {// 中文校验
					if (input.length === 0) {
						$(this).siblings('.form-error').text('此处必填').animate({
							top : '60px',
							opacity : '1'
						}, 500);
						$(this).addClass('red')
					} else if (input.length < 6) {
						$(this).siblings('.form-error').text('短信验证码错误')
								.animate({
									top : '60px',
									opacity : '1'
								}, 500);
						$(this).addClass('red')
					}
				}
			}
		});

$('input[name="status-tel"]').on('focus input propertychange click', function() {// 清除数字以外的字符
	tel = $(this).val()
	var bbb = tel.replace(/[^\d]/g, "")
	$(this).val(bbb)
})
$('form input').focus(function() {// 获得焦点删除警告
	$(this).siblings('.form-error').text('').removeAttr('style')
	$(this).removeClass('red')
})


// 发送手机验证码
$('.stateCode')
		.click(
				function() {
					var ver_phone = /^(0|86|17951)?(13[0-9]|15[012356789]|17[014678]|18[0-9]|14[57])[0-9]{8}$/;// 手机验证
					var tel = $(this).siblings('input[name="status-tel"]').val();// 手机号
					if (tel == null || !ver_phone.test(tel)) {
						$(this).siblings('.form-error').text('手机号不合法').animate(
								{
									top : '60px',
									opacity : '1'
								}, 500);
						$(this).siblings('input[name="status-tel"]').addClass(
								'red')
						return;
					} else {
						$(this).unbind("click");
						hairCodeId = $(this).attr('id');
						stateSend();
					}
				});

function stateSend() {
	alert("验证码已发送至您的手机，请查收");
	var time = 60;
	timer = setInterval(
			function() {
				time--;
				$('#' + hairCodeId).html(time + "秒").css('background', '#ddd');
				if (time <= 0) {
					clearInterval(timer);
					$('#' + hairCodeId).html("获取验证码").attr('style', '');
					$('#' + hairCodeId)
							.bind(
									"click",
									function() {
										var ver_phone = /^(0|86|17951)?(13[0-9]|15[012356789]|17[014678]|18[0-9]|14[57])[0-9]{8}$/;// 手机验证
										var tel = $(this).siblings(
												'input[name="status-tel"]').val();// 手机号
										if (tel == null || !ver_phone.test(tel)) {
											$(this).siblings('.form-error')
													.text('手机号不合法').animate({
														top : '60px',
														opacity : '1'
													}, 500);
											$(this).siblings(
													'input[name="status-tel"]')
													.addClass('red')
											return;
										} else {
											$(this).unbind("click");
											hairCodeId = $(this).attr('id');
											lateSend();
										}
									});

				}
			}, 1000);
}
