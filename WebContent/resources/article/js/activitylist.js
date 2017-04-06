function showmask(t) {
    t = t || 0,
    $("#mask").css({
        visibility: "visible"
    }),
    $("#mask").css({
        display: "block"
    });
    var i = document.body.scrollHeight > document.body.clientHeight ? document.body.scrollHeight: document.body.clientHeight,
    a = document.body.scrollWidth > document.body.clientWidth ? document.body.scrollWidth: document.body.clientWidth;
    document.getElementById("mask").style.width = a + "px",
    document.getElementById("mask").style.height = i - t + "px",
    document.getElementById("mask").style.marginTop = t + "px",
    document.body.style.overflowY = "hidden"
}
function hidemask() {
    $("#mask").css({
        display: "none"
    }),
    document.getElementById("mask").style.width = "0px",
    document.getElementById("mask").style.height = "0px",
    document.body.style.overflowY = "auto"
}
function refreshCaptcha() {
//    $(".tCenter img.imgCode").attr("src", This.base_url + "get_captcha?rnd=" + (new Date).getTime())
}
function getYZM() {
    $(".unlogin .validate").on("click",
    function() {
        var t = $(this),
        i = $(".unlogin .inputBoxB .iText").val();
        if (!/^[1-9]\d{10}$/.test(i)) return $(".unlogin  .errorTip.ph").html("请输入正确的手机号"),
        !1;
        $(".unlogin .errorTip.ph").html(""),
        $.ajax({
            type: "post",
            dataType: "json",
            url: "docWeixinSh.do?method=userCode",
            data: {
                mobile: i,
                key: "",
                value: ""
            },
            success: function(t) {
            	 - 1 == t.status ? ($(".unlogin .errorTip.yz").html(t.message), clearInterval(s)) : $(".unlogin  .errorTip.yz").html("")
            },
            error: function(t) {
                alert(t)
            }
        });
        var a = 60;
        $(this).off("click"),
        $(this).val(a + "秒后重新获取");
        var s = setInterval(function() {
            return 1 == a ? (clearInterval(s), a = 60, t.val("获取验证码"), getYZM(), !1) : (a--, void t.val(a + "秒后重新获取"))
        },
        1e3)
    })
}
function loginAndOpt() {
    $(".login .btnSubmit").on("click",
    function() {
        var t = $(".unlogin .inputBoxB .iText").val();
        if (!/^[1-9]\d{10}$/.test(t)) return $(".unlogin  .errorTip.ph").html("请输入正确的手机号"),
        !1;
        $(".unlogin .errorTip.ph").html("");
        var i = $(".unlogin .inputBoxS .iText").val();
        if (!/^\d{4}$/.test(i)) return $(".unlogin .errorTip.yz").html("请输入正确的验证码"),
        !1;
        $(".unlogin  .errorTip.yz").html("");
        $(this);
        return $.ajax({
            type: "post",
            dataType: "json",
            url: "docWeixinSh.do?method=getCoupon",
            data: {
                phone: t,
                verifyCode: i,
                couponNumber: $("#couponid").val(),
                parentid: $("#parentid").val(),
                op: $("#op").val(),
                userid: $("#userid").val(),
                type: "login"
            },
            success: function(t) {
            	if(0 != t.status) {
            		$("#mmboxTxt").html(t.message);
            		$(".unlogin  .errorTip.ph").html("");
            		$(".unlogin  .errorTip.yz").html(""); 
            		refreshCaptcha();
            		$(".login").addClass("hidden");
    				$(".mmbox").removeClass("hidden");
//            		hidemask();
            	} else {
            		$(".login  .errorTip").html(t.message);
            		$(".unlogin  .errorTip.ph").html("");
            		$(".unlogin  .errorTip.yz").html(""); 
            		$(".login").addClass("hidden");
            		hidemask();
            	}
            },
            error: function() {
                alert("网络不太好，请重试!")
            }
        }),
        !1
    })
}
function getglab() {
    $(".tCenter a.changeCode").on("click",
    function() {
        refreshCaptcha()
    }),
    $(".login .btnV").on("click",
    function() {
        var t = $(".login  .iTextV").val();
        if (!/^\d.+$/.test(t)) return $(".login   .errorTip").html("请输入正确的验证码"),
        !1;
        $(".login  .errorTip").html("");
        var i = cookie.get("PLAZA_ID");
        $.ajax({
            type: "post",
            dataType: "json",
            url: "",
            data: {
                verify_id: i,
                verifyCode: t,
                couponNumber: "",
                activityId: "",
                type: "captcha"
            },
            success: function(t) {
//                504 != t.status ? (200 == t.status && (window.location.href = This.grabsucc_url + "&productNo=" + This.couponNumber), 403 == t.status && ($(".tCenter img.imgCode").attr("src", This.base_url + "get_captcha?rnd=" + (new Date).getTime()), $(".login   .errorTip").html("验证码输入错误！")), (401 == t.status || 500 == t.status || 407 == t.status || 400 == t.status || 402 == t.status || 404 == t.status || 405 == t.status || 406 == t.status) && (window.location.href = This.grabfail_url + "&status=" + t.status)) : (alert(t.msg), This.hidemask())
            },
            error: function() {
                alert("网络不太好，请重试！")
            }
        })
    })
}
function getmmbox() {
    $(".login .btnCl").on("click",
    function() {
    	$(".login  .errorTip").html("");
    	$(".unlogin  .errorTip.ph").html("");
    	$(".unlogin  .errorTip.yz").html("");
    	$(".mmbox").addClass("hidden");
    	hidemask();
    	
    })
}
var $ = Zepto,
This = {};
$("#mask").click(function() {
    $(".login ").addClass("hidden"),
    hidemask()
}),
$(".closeBtn").click(function(t) {
    t.preventDefault(),
    $(".login  .errorTip").html(""),
    $(".unlogin  .errorTip.ph").html(""),
    $(".unlogin  .errorTip.yz").html(""),
    $(".login").addClass("hidden"),
    hidemask()
});
$(".ticketList").on("click", "a.qBtn",
function(t) {
    t.preventDefault(),
    t.stopPropagation(),
    showmask(),
    $(".unlogin").removeClass("hidden"),
    $.ajax({
        url: "",
        data: {
            activityId: 0,
            couponNumber: 0,
            captcha: ""
        },
        dataType: "json",
        type: "post",
        success: function(t) {

        }
    })
}),
getYZM(),
loginAndOpt(),
getglab(),
getmmbox(),
$(".unlogin, .glab").on("click",
function(t) {
    t.preventDefault(),
    t.stopPropagation()
});