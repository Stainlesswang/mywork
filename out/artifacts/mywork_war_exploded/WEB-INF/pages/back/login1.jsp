<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path;
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>系统登录</title>
<link rel="Shortcut Icon" href="<%=path%>/favicon/favicon.ico" />
<link rel="stylesheet" href="<%=path %>/login/css/login.css"/>
<script type="text/javascript" src="<%=path %>/login/js/jquery-1.8.2.min.js"></script>
<style type="text/css">
	body, th, td, button, input, select, textarea
	{
		font-family: "\5B8B\4F53"
	}
	
	

</style>
</head>

<body style="background-color:#d6dee0">
	<div id="loginBody">
		<div class="mtitle"><img src="<%=path %>/login/images/titlelogo.png" /></div>
		<div class="form-bg">
			<img src="<%=path %>/login/images/formbk1.png" />
		</div>
		<div class="login-box">
			<!--<h1>后台登录</h1>-->
			<form method="post">
				<div class="name">
					<label>管理员账号：</label>
					<input type="text" name="username" id="username" tabindex="1" autocomplete="off" value="" />
				</div>
				<div class="password">
					<label>密  码：</label>
					<input type="password" name="password" maxlength="16" id="password" tabindex="2" value="" />
				</div>
				<!--		<div class="code">
			<label>验证码：</label>
			<input type="text" name="code" maxlength="4" id="code" tabindex="3" value=""/>
			<div class="codeImg">
				<img src="<%=path %>/code/getCode.action" id="codeImg"/>
		</div>
		</div>-->
		<div class="remember">
			<input type="checkbox" id="remember" onclick="rememberMe();" tabindex="4" />
			<label for="remember">记住密码</label>
		</div>
		<div class="login">
			<button type="button" tabindex="5" onclick="login();">登录</button>
		</div>
		</form>
		</div>
		

		<!-- <div class="bottom" style="color:#000000;">北京大瓦科技有限公司 </div> -->
	</div>
	
	<%-- <div style="    width: 190px; height: 30px; margin: auto; position: relative; margin-top: -50px;">
			<img src="<%=path %>/login/images/logo1.png" alt="" style="width: 30px;
    height: 30PX;" />
			<img src="<%=path %>/login/images/logo_name.png" alt="" style="    width: 200px;
    float: right;
    margin-top: -50px;"/>
    <span style="    position: relative;
    margin: 0 auto;
    /* width: 200px; */
    float: right;
    margin-top: 10px;
    font-size: 12px;
    color: black;">北京南北恒达科技有限公司</span>
		</div> --%>

		<div class="screenbg">
			<ul>
				<%-- <li>
					<a href="javascript:;"><img src="img/0.jpg" /></a>
				</li>
				<li>
					<a href="javascript:;"><img src="img/1.jpg" /></a>
				</li>
				<li>
					<a href="javascript:;"><img src="<%=path %>/login/images/loginbk1.jpg" /></a>
				</li> --%>
			</ul>
		</div>
		
		
		<script type="text/javascript" src="<%=path %>/js/util/util.js"></script>
		<script type="text/javascript" src="<%=path %>/js/jQuery.md5.js"></script>
		<script type="text/javascript" src="<%=path%>/js/jquery.cookie.js"></script>
		<script type="text/javascript" src="<%=path %>/js/custome/backLogin.js"></script>

		<script type="text/javascript">
			$(function() {
				document.onkeydown = function(e) {
					var ev = document.all ? window.event : e;
					if(ev.keyCode == 13) {
						login();
					}
				}
			});
		</script>
	</body>

</html>