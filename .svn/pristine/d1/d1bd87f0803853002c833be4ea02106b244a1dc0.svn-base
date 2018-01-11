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
<title>用户管理</title>
<style type="text/css">
	.nav_content{position: fixed;width:100%;}
	.page_content{position: absolute;top:39px;width:100%;overflow:hidden;table-layout: fixed}
	.mydisable{background-color: #ebebe4;} 
	table td{overflow: hidden;}
</style>
</head>
<body>
<nav class="breadcrumb nav_content" style=""><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 用户管理  
<span class="select-box inline" style="float:right;border:0;font-size:14px;">
		<a class="btn btn-success" style="line-height:1.6em;" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a>
</span>
</nav>
<table class="table page_content" style="">
	<tr>
		<td width="310px" class="va-t">
			<span class="select-box inline" style="display:block;width:293px;float:left;border:0;font-size:14px;padding-left:0px;padding-right:0px;">
				<button name="" id="addSameLevelBtn" class="btn btn-primary" type="button">新增同级</button>
				<button name="" id="addLowerLevelBtn" class="btn btn-primary" type="button">新增下级</button>
				<button name="" id="orgEditBtn" class="btn btn-primary" type="button">编辑</button>
				<button name="" id="orgDelBtn" class="btn btn-danger" type="button">删除</button>
			</span>	
		</td>
		<td class="va-t" style="white-space:nowrap;">
			<span class="select-box inline" style="display:block;width:260px;float:left;border:0;font-size:14px;padding-left:0px;">
				<input type="text" name="" id="keywords" placeholder=" 快速查询" style="width:170px" class="input-text">
				<button name="" id="searchUserBtn" onclick="bootstrapRefresh();" class="btn btn-success" type="button"><i class="Hui-iconfont"></i> 查询</button>
			</span>
			<span class="select-box inline" style="display:block;width:396px;float:right;border:0;font-size:14px;">
				<button name="" id="addUserBtn" class="btn btn-primary" type="button">新增用户</button>
				<button name="" id="modUserBtn" class="btn btn-primary" type="button">编辑</button>
				<button name="" id="delUserBtn" class="btn btn-danger" type="button">删除</button>
				<button name="" id="importExcelBtn" class="btn btn-primary" type="button">导入Excel</button>
				<button name="" id="dowloadBtn" class="btn btn-primary" type="button">模板下载</button>
			</span>
		</td>
	</tr>
	
	<tr>
		<td width="310px" class="va-t">
			<label class="" style="font-weight: normal;">
				<input type="checkbox" value="" name="includeChild" id="includeChild" checked="checked" style="margin: 0;">
				包含子部门</label>
			<ul id="orgTree" class="easyui-tree"></ul>
		</td>
		<td class="va-t" style="white-space:nowrap;">
			<table class="table table-border table-bordered table-bg" id="datatable">
			</table>
		</td>
	</tr>
</table>
<input type="hidden" id="choose_orgId"/>








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
<script type="text/javascript" src="<%=path %>/js/custome/user/user-management.js"></script>
<%-- <script type="text/javascript" src="<%=path %>/lib/zTree/v3/js/jquery.ztree.all-3.5.min.js"></script>  --%>

</body>
<!-- 以下是各种事件的弹出框 -->

<!-- 新增或者修改组织信息弹出框start -->
<div id="addOrModifyOrgLayer" style="display:none;">
	<article class="page-container">
		<form class="form form-horizontal" id="form-add-modify-org">
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>组织名称：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<input type="text" class="input-text required" style="width:80%;"  value=""  id="org_name" name="org_name">
					<input type="hidden" id="org_id" name="org_id">
					<input type="hidden" id="oldPOrgId" name="oldPOrgId"><!-- 原始上级id -->
				</div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-3">父组织名称：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<input type="text" class="input-text mydisable" style="width:80%;" value=""  readonly="readonly" id="parent_name" name="parent_name">
					<button class="btn btn-primary"  type="button" id="selectOrgBtn">选择</button>
					<input type="hidden" id="parent_id" name="parent_id">
				</div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-3"><span class="c-red"></span>备注：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<textarea rows="3" class="textarea" style="width:80%;" id="remark" name="remark"></textarea>
				</div>
			</div>
			<div class="row cl">
				<div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-3">
					<button class="btn btn-primary" type="button" id="saveOrgBtn">确定</button>
					<input id="res" name="res" type="reset" style="display:none;" />
					<button class="btn btn-default cancelBtn" id="closeOrgLayerBtn" type="button">取消</button>
				</div>
			</div>
		</form>
	</article>
</div>
<!-- 新增或者修改组织信息弹出框 end -->

<!-- 选择组织弹出框 start -->
<div id="selectOrgLayer" style="display:none;">
	<article class="page-container">
		<form class="form form-horizontal" id="form-select-org">
			<div class="row cl" style="min-height:300px;">
				<ul id="selectOrgTree" class="easyui-tree"></ul>
			</div>
			
			<div class="row cl">
				<div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-3">
					<button class="btn btn-primary" type="button" id="confirmOrgBtn">确定</button>
					<button class="btn btn-default cancelBtn" id="closeSelectOrgLayerBtn" type="button">取消</button>
				</div>
			</div>
		</form>
	</article>
</div>
<!-- 选择组织弹出框 end -->
<style type="text/css">
	body, th, td, button, input, select, textarea
	{
		font-family: "\5B8B\4F53"
	}
</style>
</html>