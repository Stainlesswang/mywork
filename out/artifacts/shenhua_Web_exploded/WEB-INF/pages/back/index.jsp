<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path;
%>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<link rel="Bookmark" href="/favicon.ico" >
<link rel="Shortcut Icon" href="/favicon.ico" />
<!--[if lt IE 9]>
<script type="text/javascript" src="<%=path %>/lib/html5shiv.js"></script>
<script type="text/javascript" src="<%=path %>/lib/respond.min.js"></script>
<![endif]-->
<link rel="stylesheet" type="text/css" href="<%=path %>/static/h-ui/css/H-ui.min.css" />
<link rel="stylesheet" type="text/css" href="<%=path %>/static/h-ui.admin/css/H-ui.admin.css" />
<link rel="stylesheet" type="text/css" href="<%=path %>/lib/Hui-iconfont/1.0.8/iconfont.css" />
<link rel="stylesheet" type="text/css" href="<%=path %>/static/h-ui.admin/skin/blue/skin.css" id="skin" />
<link rel="stylesheet" type="text/css" href="<%=path %>/static/h-ui.admin/css/style.css" />
<!--[if IE 6]>
<script type="text/javascript" src="<%=path %>/lib/DD_belatedPNG_0.0.8a-min.js" ></script>
<script>DD_belatedPNG.fix('*');</script>
<![endif]-->
<title>会议材料推送</title>
<meta name="keywords" content="">
<meta name="description" content="">
<style type="text/css">
	#mainContent iframe{min-width:1000px;}
	body{font-family: "\5B8B\4F53"}
</style>
</head>
<body>
<header class="navbar-wrapper">
	<div class="navbar navbar-fixed-top">

		<div class="container-fluid cl"> <a class="logo navbar-logo f-l mr-10 hidden-xs" href="" onresize="32dp">
			<i class="Hui-iconfont">&#xe64f;</i>
            无纸化会议系统</a>
			<a aria-hidden="false" class="nav-toggle Hui-iconfont visible-xs" href="javascript:;">&#xe667;</a>

			<nav id="Hui-userbar" class="nav navbar-nav navbar-userbar hidden-xs">
				<ul class="cl">
					<!-- <li>超级管理员</li> -->
					<c:if test="${sessionScope.loginUser!=null}">
						<li class="dropDown dropDown_hover">
							<i class="Hui-iconfont">&#xe60d;</i>
							<a href="#" class="dropDown_A">${sessionScope.loginUser.real_name } <i class="Hui-iconfont">&#xe6d5;</i></a>
							<ul class="dropDown-menu menu radius box-shadow">
								<!-- <li><a href="javascript:;" onClick="myselfinfo()">个人信息</a></li> -->
								<li id="switchAccount"><a href="javascript:;">切换账户</a></li>
								<li id="editAccount"><a href="javascript:;">编辑用户</a></li>
								<li id="logout"><a href="javascript:;">退出</a></li>
							</ul>
						</li>
									
					</c:if>
					
					<!-- <li id="Hui-msg"> <a href="#" title="消息"><span class="badge badge-danger">1</span><i class="Hui-iconfont" style="font-size:18px">&#xe68a;</i></a> </li> -->
					<li id="Hui-skin" class="dropDown right dropDown_hover"> <a href="javascript:;" class="dropDown_A" title="换肤"><i class="Hui-iconfont" style="font-size:18px">&#xe62a;</i></a>
						<ul class="dropDown-menu menu radius box-shadow">
							<li><a href="javascript:;" data-val="blue" title="默认（蓝色）">默认（蓝色）</a></li>
							<li><a href="javascript:;" data-val="default" title="黑色">黑色</a></li>
							<li><a href="javascript:;" data-val="green" title="绿色">绿色</a></li>
							<li><a href="javascript:;" data-val="red" title="红色">红色</a></li>
							<li><a href="javascript:;" data-val="yellow" title="黄色">黄色</a></li>
							<li><a href="javascript:;" data-val="orange" title="橙色">橙色</a></li>
					</ul>
				</li>
			</ul>
		</nav>
	</div>
</div>
</header>
<aside class="Hui-aside">
	<div class="menu_dropdown bk_2">
	<c:if test='${sessionScope.loginUser.is_super==1||
							sessionScope.loginUser.admin_type==2||
							sessionScope.functionMap["1-1"]=="1"||

							sessionScope.functionMap["1-3"]=="1"}'>
			<dl id="menu-article" >
				<%--<dt><img src="<%=path %>/images/icon-2.png"/> 无纸化会议<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>--%>
					<dt><i class="Hui-iconfont">&#xe72d;</i> 无纸化会议<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
					<dd>
					<ul id="Huifold1" class="Huifold" >
						<c:if test='${sessionScope.loginUser.is_super==1||
						    sessionScope.loginUser.admin_type==2||
							sessionScope.functionMap["1-1"]=="1"||
							sessionScope.functionMap["1-3"]=="1"}'>
							<li><a data-href="<%=path %>/backToUrl/toDo.action?url=meetingManage" data-title="会议材料管理" href="javascript:void(0)"><i class="Hui-iconfont">&#xe63e;</i>&nbsp;会议材料管理</a></li>
							<li><a data-href="<%=path %>/backToUrl/toDo.action?url=meetingTypeManage" data-title="会议类型管理" href="javascript:void(0)"><i class="Hui-iconfont">&#xe6f5;</i>&nbsp;会议类型管理</a></li>
						</c:if>

					</ul>
				</dd>
			</dl>
		</c:if>
		<c:if test='${sessionScope.loginUser.is_super==1||
							sessionScope.loginUser.admin_type==2||
							sessionScope.functionMap["1-1"]=="1"||
							sessionScope.functionMap["1-2"]=="1"}'>

			<dl id="menu-user">
				<dt><i class="Hui-iconfont">&#xe64f;</i> 设备管理<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
				<dd>
					<ul>
						<li><a data-href="<%=path %>/backToUrl/toDo.action?url=deviceManage" data-title="设备管理" href="javascript:void(0)"><i class="Hui-iconfont">&#xe708;</i>&nbsp;设备管理</a></li>
						<li><a data-href="<%=path %>/authcode/background_AuthDetailView.action	" data-title="认证码管理" href="javascript:void(0)"><i class="Hui-iconfont">&#xe72c;</i>&nbsp;认证码管理</a></li>

					</ul>
				</dd>
			</dl>
			<%--<li><a data-href="<%=path %>/backToUrl/toDo.action?url=deviceManage" data-title="设备管理" href="javascript:void(0)"><i class="Hui-iconfont">&#xe708;</i>&nbsp;设备管理</a></li>--%>
		</c:if>
		<c:if test='${sessionScope.loginUser.is_super==1||
						sessionScope.loginUser.admin_type==2||
						sessionScope.functionMap["1-4"]=="1"}'>
			<dl id="menu-user">
				<dt><i class="Hui-iconfont">&#xe61d;</i> 系统管理<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
				<dd>
					<ul>

							<li><a data-href="<%=path %>/backToUrl/toDo.action?url=userManage" data-title="用户管理" href="javascript:void(0)"><i class="Hui-iconfont">&#xe62b;</i>&nbsp;用户管理</a></li>

						<c:if test='${sessionScope.loginUser.is_super==1}'>
						<li><a data-href="<%=path %>/backToUrl/toDo.action?url=systemPermissions" data-title="系统权限管理" href="javascript:void(0)"><i class="Hui-iconfont">&#xe653;</i>&nbsp;系统权限管理</a></li>
					    </c:if>

					</ul>

				</dd>
			</dl>
		</c:if>
</div>
</aside>
<div class="dislpayArrow hidden-xs"><a class="pngfix" href="javascript:void(0);" onClick="displaynavbar(this)"></a></div>
<section class="Hui-article-box" id="mainContent">
	<div id="Hui-tabNav" class="Hui-tabNav hidden-xs">
		<div class="Hui-tabNav-wp">
			<ul id="min_title_list" class="acrossTab cl" >
				<!-- <li class="active">
					<span title="我的桌面" data-href="welcome.html">我的桌面</span>
					<em></em></li> -->
					<c:choose>
						<c:when test='${sessionScope.loginUser.is_super==1||
							sessionScope.loginUser.admin_type==2||
							sessionScope.functionMap["1-3"]=="1"}'>
							<li class="active">
								<span title="会议材料推送" data-href="<%=path %>/backToUrl/toDo.action?url=meetingManage">会议材料推送</span>
								<em></em>
							</li>
						</c:when>
						<c:when test='${sessionScope.functionMap["1-2"]=="1"}'>
							<li class="active">
								<span title="设备管理" data-href="<%=path %>/backToUrl/toDo.action?url=deviceManage">设备管理</span>
								<em></em>
							</li>
						</c:when>
						<c:when test='${sessionScope.functionMap["1-4"]=="1"}'>
							<li class="active">
								<span title="用户管理" data-href="<%=path %>/backToUrl/toDo.action?url=userManage">用户管理</span>
								<em></em>
							</li>
						</c:when>
						<c:otherwise>
							<li class="active">
								<span title="我的桌面" data-href="<%=path %>/welcome.html">我的桌面</span>
								<em></em>
							</li>
						</c:otherwise>
					</c:choose>
					
		</ul>
	</div>
		<div class="Hui-tabNav-more btn-group"><a id="js-tabNav-prev" class="btn radius btn-default size-S" href="javascript:;"><i class="Hui-iconfont">&#xe6d4;</i></a><a id="js-tabNav-next" class="btn radius btn-default size-S" href="javascript:;"><i class="Hui-iconfont">&#xe6d7;</i></a></div>
</div>
	<div id="iframe_box" class="Hui-article" style="overflow-y:hidden;">
		<div class="show_iframe">
			<div style="display:none" class="loading"></div>
			<%-- <iframe scrolling="yes" frameborder="0" src="<%=path %>/welcome.html"></iframe> --%>
			
			
					<c:choose>
						<c:when test='${sessionScope.loginUser.is_super==1||
							sessionScope.loginUser.admin_type==2||
							sessionScope.functionMap["1-1"]=="1"||
							sessionScope.functionMap["1-3"]=="1"}'>
							<iframe scrolling="yes" frameborder="0" src="<%=path %>/backToUrl/toDo.action?url=meetingManage"></iframe>
						</c:when>
						<c:when test='${sessionScope.functionMap["1-2"]=="1"}'>
							<iframe scrolling="yes" frameborder="0" src="<%=path %>/backToUrl/toDo.action?url=deviceManage"></iframe>
						</c:when>
						<c:when test='${sessionScope.functionMap["1-4"]=="1"}'>
							<iframe scrolling="yes" frameborder="0" src="<%=path %>/backToUrl/toDo.action?url=userManage"></iframe>
						</c:when>
						<c:otherwise>
							<iframe scrolling="yes" frameborder="0" src="<%=path %>/welcome.html"></iframe>
						</c:otherwise>
					</c:choose>
			
			
			
	</div>
</div>
</section>

<div class="contextMenu" id="Huiadminmenu">
	<ul>
		<li id="closethis">关闭当前 </li>
		<li id="closeall">关闭全部 </li>
</ul>
</div>
<!--_footer 作为公共模版分离出去-->
<script type="text/javascript" src="<%=path %>/lib/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript" src="<%=path %>/lib/layer/2.4/layer.js"></script>
<script type="text/javascript" src="<%=path %>/static/h-ui/js/H-ui.min.js"></script>
<script type="text/javascript" src="<%=path %>/static/h-ui.admin/js/H-ui.admin.js"></script>
<!--/_footer 作为公共模版分离出去-->

<!--请在下方写此页面业务相关的脚本-->
<script type="text/javascript" src="<%=path %>/lib/jquery.contextmenu/jquery.contextmenu.r2.js"></script>
<script type="text/javascript" src="<%=path %>/js/util/util.js"></script>
<script type="text/javascript" src="<%=path %>/js/browser.js"></script>
<script type="text/javascript">
$(function(){
	var webBrowser= BrowserDetect.browser;
	var webVersion=BrowserDetect.version;
	if(webBrowser=='Explorer')//如果是IE浏览器，因为不兼容IE8
	{
		if(webVersion<9)
		{
			alert("请选择IE9及以上浏览器");
		}
	}
	$('#logout').click(function(event) {
		event.preventDefault();
		layer.confirm('确定退出？', {
			  btn: ['确定','取消'] //按钮
			}, function(){
				$.ajax({
					url:getRootPath()+'/login/backLoginOut.action',
					type:'post',
					success:function(data) {
						window.location = getRootPath()+'/login/toLogin.action';
					},
					error: function() {
						window.location = getRootPath()+'/login/toLogin.action';
					}
				});
			}, function(){
				//取消没有事件
			});
	});
	
	$('#switchAccount').click(function(event) {
		event.preventDefault();
		$.ajax({
			url:getRootPath()+'/login/backLoginOut.action',
			type:'post',
			success:function(data) {
				window.location = getRootPath()+'/login/toLogin.action';
			},
			error: function() {
				window.location = getRootPath()+'/login/toLogin.action';
			}
		});
	});
	//编辑用户
	$("#editAccount").click(function(){
		layer.open({
			  type: 2,
			  title:"编辑用户",
			  content:getRootPath()+"/userinfo/background_editAccountView.action",
			  area: ['850px', '550px'],
			  // maxmin: false,//放大缩小
			  move:true, //是否允许拖拽
		});
	});
});
/*个人信息*/
function myselfinfo(){
	layer.open({
		type: 1,
		area: ['300px','200px'],
		fix: false, //不固定
		maxmin: true,
		shade:0.4,
		title: '查看信息',
		content: '<div>管理员信息</div>'
	});
}



</script> 


</body>
</html>