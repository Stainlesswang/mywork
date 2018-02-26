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
	<meta charset="utf-8">
	<meta name="renderer" content="webkit|ie-comp|ie-stand">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
	<meta http-equiv="Cache-Control" content="no-siteapp" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>系统登录</title>
<link rel="Shortcut Icon" href="<%=path%>/favicon/favicon.ico" />



	<link rel="stylesheet" type="text/css" href="<%=path %>/static/h-ui.admin/css/H-ui.login.css" />
	<link rel="stylesheet" type="text/css" href="<%=path %>/static/h-ui/css/H-ui.min.css" />
	<link rel="stylesheet" type="text/css" href="<%=path %>/lib/Hui-iconfont/1.0.8/iconfont.css" />
	<link rel="stylesheet" type="text/css" href="<%=path %>/static/h-ui.admin/css/style.css" />
</head>

<body >
	<div class="header"></div>
	<div class="loginWraper">
		<div id="loginform" class="loginBox">
				<!--<h1>后台登录</h1>-->
			<form class="form form-horizontal" method="post" action="backLogin.action">
				<div class="row cl">
					<label class="form-label col-xs-3"><i class="Hui-iconfont">&#xe60d;</i></label>
					<div class="formControls col-xs-8">
					<input type="text" name="username" id="username"  placeholder="账户" value="" class="input-text size-L"/>
					</div>
				</div>
<div class="col-10">&nbsp;&nbsp;</div>
				<div class="row cl">
					<label class="form-label col-xs-3"><i class="Hui-iconfont">&#xe60e;</i></label>
					<div class="formControls col-xs-8">
					<input type="password" name="password"  id="password" placeholder="密码"   class="input-text size-L"/>
				     </div>
					</div>
					<div class="col-10">&nbsp;&nbsp;&nbsp;</div>
				<div class="row cl">
					<div class="formControls col-xs-8 col-xs-offset-3">
						<label for="remember">
							<input type="checkbox" name="online" id="remember" value="" onclick="rememberMe()" >
							记住密码</label>
					</div>
				</div>
					<div class="col-10">&nbsp;&nbsp;</div>
				<div class="row cl">
					<div class="formControls col-xs-8 col-xs-offset-3">
						<input name="" type="button" onclick="login();" class="btn btn-success radius size-L" value="&nbsp;登&nbsp;&nbsp;&nbsp;&nbsp;录&nbsp;">
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input name="" type="reset" class="btn btn-default radius size-L" value="&nbsp;取&nbsp;&nbsp;&nbsp;&nbsp;消&nbsp;">
					</div>
				</div>
				</div>
			</form>
		</div>
	</div>
	<div class="footer">Copyright Meeting.v2.0</div>
	<script type="text/javascript" src="<%=path %>/lib/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript" src="<%=path %>/js/util/util.js"></script>

	<script type="text/javascript" src="<%=path %>/js/custome/backLogin.js"></script>
	<script type="text/javascript" src="<%=path %>/static/h-ui/js/H-ui.js"></script>
	<%--<script type="text/javascript">--%>
			<%--$(function() {--%>
				<%--document.onkeydown = function(e) {--%>
					<%--var ev = document.all ? window.event : e;--%>
					<%--if(ev.keyCode == 13) {--%>
						<%--login();--%>
					<%--}--%>
				<%--}--%>
			<%--});--%>
		<%--</script>--%>
	</body>

</html>