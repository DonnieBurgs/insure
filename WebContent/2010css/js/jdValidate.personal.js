$.extend(validateFunction,{
	FORM_validate:function(){
		$("#username").jdValidate(validatePrompt.username,validateFunction.username,true);
		$("#pwd").jdValidate(validatePrompt.pwd,validateFunction.pwd,true);
		$("#pwd2").jdValidate(validatePrompt.pwd2,validateFunction.pwd2,true);
		$("#mobile").jdValidate(validatePrompt.mobile,validateFunction.mobile,true);
		$("#mail").jdValidate(validatePrompt.mail,validateFunction.mail,true);
		return validateFunction.FORM_submit(["#username","#pwd","#pwd2","#mobile", "#mail"])
	}		 
});

$("#username").jdValidate(validatePrompt.username,validateFunction.username);
$("#pwd").bind("keyup",function(){
	validateFunction.pwdstrength();
}).jdValidate(validatePrompt.pwd,validateFunction.pwd)
$("#pwd2").jdValidate(validatePrompt.pwd2,validateFunction.pwd2);
$("#mail").jdValidate(validatePrompt.mail,validateFunction.mail);
$("#mobile").jdValidate(validatePrompt.mobile,validateFunction.mobile);
$("#registsubmit").click(function() {
	if($("#referrer").val() == '可不填')
		$("#referrer").val('');
	
    var flag = validateFunction.FORM_validate();
    if (flag) {
        $(this).attr({"disabled":"disabled"}).attr({"value":"提交中,请稍等"});
        $.ajax({
            type: "post", cache: false,
            url: "/userServlet.do?method=reg",
            data: $("#formpersonal").serialize(), dataType: "json",
            success: function(result) {
                if (result) {
                    if (result.info) {
                        $("#registsubmit").removeAttr("disabled");
                        $("#registsubmit").attr({"value":"同意以下协议，提交"});
                        alert(obj.info);
                    }
                    if (result.success == true) {
                        window.location.href = result.location;
                    }
                }
            }
        });
    }
});