// JavaScript Document
var telReg = /^(0|86|17951)?(13[0-9]|15[012356789]|17[014678]|18[0-9]|14[57])[0-9]{8}$/;
var nameReg = /^[\u2E80-\uFE4F]+$/;
var emailReg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
var telCodeReg = /^\d{6}$/;// 验证码
var logintype = 0 ;
var testt = 100;

$('.loginPupopClose').click(function(){
	$('.loginBox').hide();
})

function hideLoginBox() {
	$('.loginBox').hide();
}

function toLogin() {
	$('#logintel').val("");
	$('#logintelCode').val("");
	$('#loginpassword').val("");
	$('#loginAlertStr').html("");
	$('.loginBox').show()
	$('.login').css('left', ($(window).width()-800)/2);
	var sHeight = screen.height;
	if(sHeight<=850) {
		$('.login').css('top',$('#key_top').val());
		
	} else {
		$('.login').css('top',$('#key_top').val());
		
	}

	var wHeight = $(window).height()-$('.login').height();
	var t = wHeight/2;


}
