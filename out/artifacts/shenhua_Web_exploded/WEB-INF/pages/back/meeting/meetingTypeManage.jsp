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
<!--[if lt IE 9]>
<script type="text/javascript" src="<%=path %>/lib/html5shiv.js"></script>
<script type="text/javascript" src="<%=path %>/lib/respond.min.js"></script>
<![endif]-->
<link rel="stylesheet" type="text/css" href="<%=path %>/static/h-ui/css/H-ui.min.css" />
<link rel="stylesheet" type="text/css" href="<%=path %>/static/h-ui.admin/css/H-ui.admin.css" />
<link rel="stylesheet" type="text/css" href="<%=path %>/lib/Hui-iconfont/1.0.8/iconfont.css" />
<link rel="stylesheet" type="text/css" href="<%=path %>/static/h-ui.admin/skin/default/skin.css" id="skin" />
<link rel="stylesheet" type="text/css" href="<%=path %>/static/h-ui.admin/css/style.css" />

<link rel="stylesheet" type="text/css" href="<%=path %>/lib/jquery-easyui-1.5.1/themes/default/tree.css">
<link rel="stylesheet" type="text/css" href="<%=path %>/lib/jquery-easyui-1.5.1/themes/icon.css">
<%-- <link rel="stylesheet" href="<%=path %>/lib/zTree/v3/css/zTreeStyle/zTreeStyle.css" type="text/css"> --%>
<!--[if IE 6]>
<script type="text/javascript" src="<%=path %>/lib/DD_belatedPNG_0.0.8a-min.js" ></script>
<script>DD_belatedPNG.fix('*');</script>
<![endif]-->
<title>会议类型管理</title>
<style type="text/css">
	.nav_content{position: fixed;width:100%;}
	.page_content{position: absolute;top:39px;width:100%;overflow:hidden;table-layout: fixed}
	.mydisable{background-color: #ebebe4;} 
	table td{overflow: hidden;}
</style>
</head>
<body>
<nav class="breadcrumb nav_content" style=""><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 无纸化会议  <span class="c-gray en">&gt;</span> 会议类型管理  
<span class="select-box inline" style="float:right;border:0;font-size:14px;">
		<a class="btn btn-success" style="line-height:1.6em;" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a>
</span>
</nav>
<table class="table page_content" style="">
	<tr>
		<td class="va-t" style="white-space:nowrap;">
			<span class="select-box inline" style="display:block;width:260px;float:left;border:0;font-size:14px;padding-left:0px;">
				<input type="text" name="" id="keywords" placeholder=" 快速查询" style="width:170px" class="input-text">
				<button name="" id="searchTypeBtn"  class="btn btn-success" type="button"><i class="Hui-iconfont"></i> 查询</button>
			</span>
			<span class="select-box inline" style="display:block;width:396px;border:0;font-size:14px;">
				<button name="" id="addMeetingTypeBtn" class="btn btn-primary" type="button">新建</button>
				<button name="" id="delMeetingTypeBtn" class="btn btn-danger" type="button">删除</button>
			</span>
		</td>
	</tr>
	
	<tr>
		<td class="va-t" style="white-space:nowrap;">
			<table class="table table-border table-bordered table-bg" id="datatable">
			</table>
		</td>
	</tr>
</table>








<!--_footer 作为公共模版分离出去-->
<script type="text/javascript" src="<%=path %>/lib/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript" src="<%=path %>/static/bootstrap/import/import.inc.js" ></script>
<script type="text/javascript" src="<%=path %>/lib/layer/2.4/layer.js"></script>
<script type="text/javascript" src="<%=path %>/static/h-ui/js/H-ui.min.js"></script>
<script type="text/javascript" src="<%=path %>/static/h-ui.admin/js/H-ui.admin.js"></script>
<!--/_footer 作为公共模版分离出去-->

<!--请在下方写此页面业务相关的脚本-->

<script type="text/javascript" src="<%=path %>/lib/jquery-easyui-1.5.1/jquery.easyui.min.js"></script>

<script type="text/javascript" src="<%=path %>/lib/jquery.validation/1.14.0/jquery.validate.js"></script> 
<script type="text/javascript" src="<%=path %>/lib/jquery.validation/1.14.0/validate-methods.js"></script> 
<script type="text/javascript" src="<%=path %>/lib/jquery.validation/1.14.0/messages_zh.js"></script>

<script type="text/javascript" src="<%=path %>/js/util/util.js" ></script>
<script type="text/javascript" src="<%=path %>/js/lodash.js" ></script>
<script type="text/javascript">
	var rootPath = "<%=path %>"
</script>
<script type="text/javascript" src="<%=path %>/js/custome/meeting/meetingTypeManage.js"></script>

</body>
<!-- 以下是各种事件的弹出框 -->

<!-- 新增角色弹框 -->
<div id="addRoleLayer" style="display:none;">
<article class="page-container">
	<form class="form form-horizontal" id="role-modify-add">
	<table class="table  table-bg" style="margin:auto;width:80%;">
		<tbody>
			<tr class="text-l">
				<td class="text-r td-width"><span class="c-red">*</span>角色名称:</td>
				<td class="td-width-text">
					<input type="text"  class="input-text" id="role_name" name="role_name">
					<input type="hidden" id="role_id" name="role_id"/>
				</td>
			</tr>
			<tr class="text-l">
				<td class="text-r td-width">角色描述:</td>
				<td class="td-width-text">
					<!-- <input type="text"  class="input-text" id="role_description" name="role_description"> -->
					<textarea rows="3" cols="" id="role_description" name="role_description" class="textarea"></textarea>
				</td>
			</tr>
			
			<tr class="text-c">
				<td colspan="4">
					<button class="btn btn-primary" type="button" id="saveRoleBtn">保存</button>
					<button class="btn btn-default cancelBtn" id="closeRoleLayerBtn" type="button">关闭</button>
				</td>
			</tr>
		</tbody>
	</table>
	</form>
	</article>
</div>

<!-- 角色权限弹出框 -->
<div id="rolePermissionLayer" style="display:none;">
<article class="page-container">
	<form class="form form-horizontal" id="form-role-permission" style="margin:auto;width:80%">
		<!-- <div class="checkbox">
			<label>
				<input type="checkbox" selectbox="" id="7" name="权限删除" checked="checked">
				权限删除
			</label>
		</div> -->
	</form>
	</article>
</div>

<style type="text/css">
	body, th, td, button, input, select, textarea
	{
		font-family: "\5B8B\4F53"
	}
</style>
</html>