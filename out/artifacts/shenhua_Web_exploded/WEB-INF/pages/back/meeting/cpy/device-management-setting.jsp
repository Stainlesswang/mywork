<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
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
<link rel="stylesheet" type="text/css" href="<%=path %>/static/h-ui/css/H-ui.min.css" />
<link rel="stylesheet" type="text/css" href="<%=path %>/static/h-ui.admin/css/H-ui.admin.css" />
<link rel="stylesheet" type="text/css" href="<%=path %>/lib/Hui-iconfont/1.0.8/iconfont.css" />
<link rel="stylesheet" type="text/css" href="<%=path %>/static/h-ui.admin/skin/default/skin.css" id="skin" />
<link rel="stylesheet" type="text/css" href="<%=path %>/static/h-ui.admin/css/style.css" />
<title>设备管理-查找配置</title>
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 无纸化会议 <span class="c-gray en">&gt;</span> 设置管理
    <button name="" id="" class="btn btn-primary" type="button" style="float:right;margin-top: 8px;"><i class="Hui-iconfont">&#xe609;</i> 退出</button>
	<button name="" id="" class="btn btn-primary" type="button" style="float:right;margin-top: 8px;margin-right: 15px;"><i class="Hui-iconfont">&#xe609;</i> 保存退出</button>
</nav>
<div class="page-container">
	<table class="table  table-bg" id="datatable">
		<tbody>
			<tr class="text-c">
				<td colspan="4" ><h2><font color="red">点击查看配置，进入设置管理配置界面,可配置各人员权限。 </font></h2></td>
			</tr>
			<tr class="text-c">
				<td colspan="4"><h4>会议材料推送配置</h4></td>
			</tr>
			<tr class="text-l">
				<td class="text-r">请您选择本模块的管理员:</td>
				<td colspan="3"><input type="text" class="input-text"  style="width:80%;"  name=""><button class="btn btn-primary" type="button">查找</button></td> 
			</tr>
			<tr class="text-l">
				<td class="text-r">请您选择可维护设备信息人员:</td>
				<td colspan="3"><input type="text" class="input-text" style="width:80%;"  name=""><button class="btn btn-primary" type="button">查找</button></td>
			</tr>
			<tr class="text-l">
				<td class="text-r">请您选择可新建会议材料人员:</td>
				<td colspan="3"><input type="text" class="input-text"  style="width:80%;"  name=""><button class="btn btn-primary" type="button">查找</button></td>
			</tr>		
		</tbody>
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

<script type="text/javascript">
</script>
</body>
</html>