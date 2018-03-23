function keyLogin(){
    if (event.keyCode==13)  //回车键的键值为13
        document.getElementById("denglu").click(); //调用登录按钮的登录事件
};
function login()
{
	var username=$("#username").val();
	var password=$("#password").val();
	var code=$("#code").val();
	if(username=='')
	{
		alert("用户名不能为空");
		return;
	}
	if(password=='')
	{
		alert("密码不能为空");
		return;
	}
//	if(code=='')
//	{
//		alert("验证码不能为空");
//		return;
//	}
	//password=$.md5("parkfiaqdik093kf" + password + "park9fjiajfi3949452");
	//password=password.toLowerCase();
	//登陆操作
	$.ajax({
	      type: "POST",
	       url: getRootPath()+"/login/backLogin.action",
	       data: {username:username,password:password,code:code},
	       dataType: 'json',
	       cache: false,
	       success: function (result) {
//	            alert(JSON.stringify(result));
	            if(result.status==0)
	            {
	            	saveCookie();
	            	rememberMe();
	            	window.location.href=getRootPath()+"/index/index.action";
	            }
	            else
	            {
	            	alert(result.message);
	            }
	      }
	   });
	
	
	
}


function rememberMe()
{
	if (!$('#remember').is(':checked')) {
        $.cookie('loginname', '', {
            expires: -1
        });
        $.cookie('password', '', {
            expires: -1
        });
        $("#username").val('');
        $("#password").val('');
    }
}


function saveCookie() {
    if ($('#remember').is(':checked')) {
        $.cookie('username', $("#username").val(), {
            expires: 7
        });
        $.cookie('password', $("#password").val(), {
            expires: 7
        });
    }
}

//更改验证码
function changeCode() {
    $("#codeImg").attr("src", getRootPath()+"/code/getCode.action?t=" + new Date().getTime());
}

$(function(){
	var username = $.cookie('username');
    var password = $.cookie('password');
    if (typeof(username) != "undefined"
        && typeof(password) != "undefined") {
        $("#username").val(username);
        $("#password").val(password);
        $("#remember").attr("checked", true);
        $("#code").focus();
    }
    
    $("#codeImg").bind("click", changeCode);
});








