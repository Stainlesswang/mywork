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
<link rel="stylesheet" type="text/css" href="<%=path %>/static/h-ui.admin/skin/default/skin.css" id="skin" />
<link rel="stylesheet" type="text/css" href="<%=path %>/static/h-ui.admin/css/style.css" />
<!--[if IE 6]>
<script type="text/javascript" src="<%=path %>/lib/DD_belatedPNG_0.0.8a-min.js" ></script>
<script>DD_belatedPNG.fix('*');</script>
<![endif]-->
<style type="text/css">
	.navBtn{margin-top: 8px;margin-right: 10px;}
	.nav_content{position: fixed;width:100%;}
	.ctrl_content{text-align: left;width:100%;position: fixed;z-index: 99;height:51px;top:39px;margin-left:20px;background-color: #ffffff;padding-top:20px;}
	.page_content{position: absolute;top: 90px;width:100%;}
</style>
<title>设备管理</title>
</head>
<body>
<nav class="breadcrumb nav_content"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 无纸化会议 <span class="c-gray en">&gt;</span> 设备管理
	<span class="select-box inline" style="float:right;border:0;font-size:14px;">
		<a class="btn btn-success" style="line-height:1.6em;" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a>
		<button name="" id="addDeviceBtn" class="btn btn-primary" type="button"><i class="Hui-iconfont">&#xe6df;</i> 新建</button>
		<button name="" id="delDeviceBtn" class="btn btn-danger" type="button"><i class="Hui-iconfont">&#xe609;</i> 删除</button>
	</span>
</nav>
<div class="text-c ctrl_content" style="">
	<div class="radio-box">
		<label >状态筛选</label>：
		<input type="radio" id="selectType-1" value="1" name="radio" checked>
		<label for="selectType-1" class="size-S">使用中设备</label>
		<input type="radio" id="selectType-2" value="-1" name="radio" >
		<label for="selectType-2" class="size-S">历史设备</label>
	</div>
		<%--<span class="select-box inline">--%>
			<%--<select class="select" id="selectType">--%>
				<%--<option value="1">使用中设备</option>--%>
				<%--<option value="-1">设备历史信息</option>--%>
				<%--&lt;%&ndash;<option value="3">授权码</option>&ndash;%&gt;--%>
			<%--</select>--%>
		<%--</span>--%>
		<input type="text" name="" id="keywords" placeholder=" 快速查询" style="width:250px" class="input-text">
		<button name="" id="" class="btn btn-success" onclick="bootstrapRefresh();" type="submit"><i class="Hui-iconfont"></i> 查询</button>
	</div>
	
<div class="page-container page_content">
	<table class="table table-border table-bordered table-bg" id="datatable">
		<!--<thead>
			<tr class="text-c">
				<th></th>
				<th width="25"><input type="checkbox" name="" value=""></th>
				<th>创建时间</th>
				<th>会议标题</th>
				<th>会议开始时间</th>
				<th>是否已推送设备</th>
			</tr>
		</thead>
		<tbody>
			<tr class="text-c">
				<td>1</td>
				<td><input type="checkbox" value="1" name=""></td>
				<td>2017-04-26</td>
				<td>测试</td>
				<td>2017-04-26</td>
				<td>已推送</td>
			</tr>
			<tr class="text-c">
				<td>2</td>
				<td><input type="checkbox" value="1" name=""></td>
				<td>2017-04-26</td>
				<td>测试</td>
				<td>2017-04-26</td>
				<td>已推送</td>
			</tr>
		</tbody>-->
	</table>
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
<script type="text/javascript" src="<%=path %>/js/custome/meeting/device-management.js" ></script>


</body>
<style type="text/css">
	body, th, td, button, input, select, textarea
	{
		font-family: "\5B8B\4F53"
	}
</style>
</html>