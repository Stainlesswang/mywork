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

<!--[if lt IE 9]>
<script type="text/javascript" src="<%=path %>/lib/html5shiv.js"></script>
<script type="text/javascript" src="<%=path %>/lib/respond.min.js"></script>
<![endif]-->
<link rel="stylesheet" type="text/css" href="<%=path %>/static/h-ui/css/H-ui.min.css" />
<link rel="stylesheet" type="text/css" href="<%=path %>/static/h-ui.admin/css/H-ui.admin.css" />
<link rel="stylesheet" type="text/css" href="<%=path %>/lib/Hui-iconfont/1.0.8/iconfont.css" />
<link rel="stylesheet" type="text/css" href="<%=path %>/static/h-ui.admin/skin/default/skin.css" id="skin" />
<link rel="stylesheet" type="text/css" href="<%=path %>/static/h-ui.admin/css/style.css" />

<%-- <link rel="stylesheet" type="text/css" href="<%=path %>/lib/jquery-easyui-1.5.1/themes/default/tree.css">
<link rel="stylesheet" type="text/css" href="<%=path %>/lib/jquery-easyui-1.5.1/themes/icon.css"> --%>
<link rel="stylesheet" href="<%=path %>/lib/zTree/v3/css/zTreeStyle/zTreeStyle.css" type="text/css">
<!--[if IE 6]>
<script type="text/javascript" src="<%=path %>/lib/DD_belatedPNG_0.0.8a-min.js" ></script>
<script>DD_belatedPNG.fix('*');</script>
<![endif]-->
<title>导入excel</title>
<style type="text/css">
	.td-width{}
	.td-width-text{width:40%;}
	.error1{position: relative;}
	.ztree li span.button.ico_docu{background-position: -110px -16px;}
	.mydisable{background-color: #ebebe4;} 
</style>
</head>
<body>
<article class="page-container">
<form class="form form-horizontal" id="form-import" method="post" enctype="multipart/form-data">
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3">组织:</label>
		<div class="formControls col-xs-6 col-sm-7">
			<input type="text" class="input-text  mydisable" readonly="readonly"    style="width:68%;" id="org_name"  name="org_name">
					<input type="hidden" id="org_id" name="org_id"/>
					<button class="btn btn-default" type="button" id="clearOrgBtn" style="width:12%;float:right;">清除</button>
					<button class="btn btn-primary" type="button" id="selectOrgBtn" style="width:18%;float:right;">选择</button>
					
		</div>
	</div>
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>Excel文件:</label>
		<div class="formControls col-xs-6 col-sm-7">
			<input type="file" class="input-text requiredFile" style="height:auto;"" name="uploadFile" id="uploadFile">
			
		</div>
	</div>
	<div class="row cl">
		<div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-3">
			<button class="btn btn-primary" type="button" id="submitBtn">提交</button>
			<button class="btn btn-default cancelBtn" id="closeUserLayerBtn" type="button">关闭</button>
		</div>
	</div>
	<div class="row cl">
		<div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-3" style="color:red;">
			备注:<br>
			导入用户的两种方式:<br>
			1.按选中的组织导入，即默认导入属于该组织的用户（即不按照excel中的组织信息导入）<br>
			2.按excel中用户所属组织导入。当不选中组织，系统将按照excel中所属组织信息进行导入，若组织不存在，系统自动创建(excel中公司级别组织必须存在)。<br>
			excel中所属组织一列中，上下级组织必须使用"-"隔开。
		</div>
	</div>
	</form>
</article>
<!--_footer 作为公共模版分离出去-->
<script type="text/javascript" src="<%=path %>/lib/jquery/1.9.1/jquery.min.js"></script> 
<script type="text/javascript" src="<%=path %>/static/bootstrap/import/import.inc.js" ></script>
<script type="text/javascript" src="<%=path %>/lib/layer/2.4/layer.js"></script>
<script type="text/javascript" src="<%=path %>/static/h-ui/js/H-ui.min.js"></script> 
<script type="text/javascript" src="<%=path %>/static/h-ui.admin/js/H-ui.admin.js"></script>
<!--/_footer 作为公共模版分离出去-->

<!--请在下方写此页面业务相关的脚本-->
<script type="text/javascript" src="<%=path %>/lib/laypage/1.2/laypage.js"></script>

<%-- <script type="text/javascript" src="<%=path %>/lib/jquery-easyui-1.5.1/jquery.easyui.min.js"></script> --%>
<script type="text/javascript" src="<%=path %>/lib/zTree/v3/js/jquery.ztree.all-3.5.min.js"></script>

<script type="text/javascript" src="<%=path %>/lib/jquery.validation/1.14.0/jquery.validate.js"></script> 
<script type="text/javascript" src="<%=path %>/lib/jquery.validation/1.14.0/validate-methods.js"></script> 
<script type="text/javascript" src="<%=path %>/lib/jquery.validation/1.14.0/messages_zh.js"></script>


<script type="text/javascript" src="<%=path %>/js/util/util.js" ></script>
<script type="text/javascript" src="<%=path %>/js/custome/user/user-import.js"></script>

</body>

<!-- 选择组织弹出框 start -->
<div id="selectOrgLayer" style="display:none;">
	<article class="page-container">
		<form class="form form-horizontal" id="form-select-org">
			<div class="row cl" style="min-height:300px;">
				<ul id="selectOrgTree" class="ztree"></ul>
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