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
<script type="text/javascript" src="<%=path %>/lib/PIE_IE678.js"></script>
<![endif]-->
<link rel="stylesheet" type="text/css" href="<%=path %>/static/h-ui/css/H-ui.min.css" />
<link rel="stylesheet" type="text/css" href="<%=path %>/static/h-ui.admin/css/H-ui.admin.css" />
<link rel="stylesheet" type="text/css" href="<%=path %>/lib/Hui-iconfont/1.0.8/iconfont.css" />
	<link rel="stylesheet" type="text/css" href="<%=path %>/lib/icheck/icheck.css" />
<link rel="stylesheet" type="text/css" href="<%=path %>/static/h-ui.admin/skin/default/skin.css" id="skin" />
<link rel="stylesheet" type="text/css" href="<%=path %>/static/h-ui.admin/css/style.css" />
<!--[if IE 6]>
<script type="text/javascript" src="<%=path %>/lib/DD_belatedPNG_0.0.8a-min.js" ></script>
<script>DD_belatedPNG.fix('*');</script>
<![endif]-->
<title>会议材料推送</title>
<style type="text/css">
	.nav_content{position: fixed;width:100%;}
	.ctrl_content{text-align: left;width:100%;position: fixed;z-index: 99;height:51px;top:39px;margin-left:20px;background-color: #ffffff;padding-top:20px;}
	.page_content{position: absolute;top: 90px;width:100%;}
</style>
</head>
<body>
<nav class="breadcrumb " style=""><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 无纸化会议 <span class="c-gray en">&gt;</span> 会议材料推送
	<!-- button name="" id="" class="btn btn-danger" type="button" style="float:right;margin-top: 8px;"><i class="Hui-iconfont">&#xe609;</i> 删除</button> -->
	<span class="select-box inline" style="float:right;border:0;font-size:14px;">
		<a class="btn btn-success" style="line-height:1.6em;" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a>
		<button name="" id="addPushBtn" class="btn btn-primary" type="button"><i class="Hui-iconfont">&#xe6df;</i> 新建</button>
		<button name="" id="noWifiBackBtn" class="btn btn-primary" type="button"><i class="Hui-iconfont">&#xe616;</i> 无网回送</button>
		<button name="" id="delMeetBtn" class="btn btn-danger" type="button"><i class="Hui-iconfont">&#xe609;</i> 删除</button>
	</span>
	
</nav>

<div class="page-container " style="">
	<div id="zuo">
		<div class="radio-box" >
				<label >状态筛选</label>：
				<input type="radio" id="radio-1" value="-1" name="radio" checked>
				<label for="radio-1" class="size-S">全部文件</label>
				<input type="radio" id="radio-2" value="1" name="radio" >
				<label for="radio-2" class="size-S">已推送文件</label>
				<input type="radio" id="radio-3" value="0" name="radio" >
				<label for="radio-3" class="size-S">未推送文件</label>
			</div>
		<%--<span class="select-box inline">--%>
			<%--<select class="select" id="is_paid" >--%>
				<%--<option value="0" >未推送文件</option>--%>
				<%--<option value="1">已推送文件</option>--%>
				<%--<option value="-1" selected="selected">全部文件</option>--%>
			<%--</select>--%>
		<%--</span>--%>
		<input  type="text" name="" id="keywords" placeholder="输入会议名称/参会者姓名" style="width:250px" class="input-text">
		<button name="" id="" onclick="bootstrapRefresh();" class="btn btn-success" type="submit"><i class="Hui-iconfont"></i>查询</button>
	</div>
	<div class="mt-20">
	<table class="table table-border table-bordered table-bg" id="datatable" >
	</table>
    </div>
</div>
<!--_footer 作为公共模版分离出去-->
<script type="text/javascript" src="<%=path %>/lib/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript" src="<%=path %>/static/bootstrap/import/import.inc.js" ></script> 
<script type="text/javascript" src="<%=path %>/lib/layer/2.4/layer.js"></script>
<script type="text/javascript" src="<%=path %>/static/h-ui/js/H-ui.min.js"></script> 
<script type="text/javascript" src="<%=path %>/static/h-ui.admin/js/H-ui.admin.js"></script>
<!--/_footer 作为公共模版分离出去-->

<!--请在下方写此页面业务相关的脚本-->
<script type="text/javascript" src="<%=path %>/lib/laypage/1.2/laypage.js"></script>


<script type="text/javascript" src="<%=path %>/js/util/util.js" ></script>
<script type="text/javascript" src="<%=path %>/js/custome/meeting/meeting-materials-push.js" ></script>


</body>
<style type="text/css">
	body, th, td, button, input, select, textarea
	{
		font-family: "\5B8B\4F53"
	}
	#zuo{width:900px;height:50px ;float:left}
</style>
</html>