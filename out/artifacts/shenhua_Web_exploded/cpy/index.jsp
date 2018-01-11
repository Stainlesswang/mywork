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
<script type="text/javascript" src="lib/html5shiv.js"></script>
<script type="text/javascript" src="lib/respond.min.js"></script>
<![endif]-->
<link rel="stylesheet" type="text/css" href="<%=path %>/static/h-ui/css/H-ui.min.css" />
<link rel="stylesheet" type="text/css" href="<%=path %>/static/h-ui.admin/css/H-ui.admin.css" />
<link rel="stylesheet" type="text/css" href="<%=path %>/lib/Hui-iconfont/1.0.8/iconfont.css" />
<link rel="stylesheet" type="text/css" href="<%=path %>/static/h-ui.admin/skin/default/skin.css" id="skin" />
<link rel="stylesheet" type="text/css" href="<%=path %>/static/h-ui.admin/css/style.css" />

<link rel="stylesheet" href="<%=path %>/lib/zTree/v3/css/zTreeStyle/zTreeStyle.css" type="text/css">
<!--[if IE 6]>
<script type="text/javascript" src="lib/DD_belatedPNG_0.0.8a-min.js" ></script>
<script>DD_belatedPNG.fix('*');</script>
<![endif]-->
<title>用户管理</title>
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 用户管理  
<span class="select-box inline" style="float:right;border:0;font-size:14px;">
	<select class="select" style="border:1px solid #cccccc;">
		<option value="0">已注册用户(按部门)</option>
	</select>
	<a href="" style="color:#0893F7;">部门管理</a>&nbsp;|&nbsp;
	<a href="" style="color:#0893F7;">群组管理</a>&nbsp;|&nbsp;
	<a href="" style="color:#0893F7;">系统角色</a>
	<select class="select" style="border:1px solid #cccccc;">
		<option value="0">--配置管理--</option>
	</select>
</span>
		
</nav>
<table class="table">
	<tr>
		<td width="350" class="va-t">
			<button name="" id="addSameLevelBtn" class="btn btn-primary" type="button">新增同级</button>
			<button name="" id="addLowerLevelBtn" class="btn btn-primary" type="button">新增下级</button>
			<button name="" id="orgEditBtn" class="btn btn-primary" type="button">编辑</button>
			<button name="" id="orgDelBtn" class="btn btn-danger" type="button">删除</button>
		</td>
		<td class="va-t">
			<span class="select-box inline" style="float:right;border:0;font-size:14px;">
				<button name="" id="addUserBtn" class="btn btn-primary" type="button">新增用户</button>
				<button name="" id="modUserBtn" class="btn btn-primary" type="button">编辑用户</button>
				<button name="" id="delUserBtn" class="btn btn-danger" type="button">删除用户</button>
				<button name="" id="importExcelBtn" class="btn btn-primary" type="button">导入Excel</button>
				<button name="" id="dowloadBtn" class="btn btn-primary" type="button">模板下载</button>
			</span>
		</td>
	</tr>

	<tr>
		<td width="350" class="va-t"><!-- <ul id="treeDemo" class="ztree"></ul> -->
			<%-- <ul id="tt" class="easyui-tree" data-options="url:'<%=path %>/js/custome/user/user-management.json',method:'get',animate:true,checkbox:false"></ul> --%>
			<ul id="treeDemo" class="ztree"></ul>
		</td>
		<td class="va-t">
			<table class="table table-border table-bordered table-bg" id="datatable">
				<!-- <thead style="background-color: #B5B5B5;">
					<tr>
						<td>序号</td>
						<td>全称</td>
						<td>用户ID</td>
						<td>职务</td>
						<td>所属部门</td>
					</tr>
				</thead> -->
			</table>
		</td>
	</tr>
</table>
<input type="hidden" id="choose_orgId"/>








<!--_footer 作为公共模版分离出去-->
<script type="text/javascript" src="<%=path %>/lib/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript" src="<%=path %>/lib/layer/2.4/layer.js"></script>
<script type="text/javascript" src="<%=path %>/static/h-ui/js/H-ui.min.js"></script>
<script type="text/javascript" src="<%=path %>/static/h-ui.admin/js/H-ui.admin.js"></script>
<!--/_footer 作为公共模版分离出去-->

<!--请在下方写此页面业务相关的脚本-->
<script type="text/javascript" src="<%=path %>/static/bootstrap/import/import.inc.js" ></script>

<script type="text/javascript" src="<%=path %>/lib/jquery.validation/1.14.0/jquery.validate.js"></script> 
<script type="text/javascript" src="<%=path %>/lib/jquery.validation/1.14.0/validate-methods.js"></script> 
<script type="text/javascript" src="<%=path %>/lib/jquery.validation/1.14.0/messages_zh.js"></script>

<script type="text/javascript" src="<%=path %>/js/util/util.js" ></script>

<script type="text/javascript" src="<%=path %>/lib/zTree/v3/js/jquery.ztree.all-3.5.min.js"></script>

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
				</div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-3">父组织名称：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<input type="text" class="input-text" style="width:80%;" value="" disabled="disabled" readonly="readonly" id="parent_name" name="parent_name">
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
<script type="text/javascript">
function getRoot() {  
    var treeObj = $.fn.zTree.getZTreeObj("treeDemo");  
    //返回一个根节点  
   var node = treeObj.getNodesByFilter(function (node) { return node.level == 0 }, true); 
   console.log(JSON.stringify(node.id));
} 
function getSelectNode()
{
	var nodes=treeObj.getSelectedNodes(); 
    console.log(JSON.stringify(nodes));
}
function setRootSelect()
{
	 //选中根节点
    var treeObj = $.fn.zTree.getZTreeObj("treeDemo");  
    //返回一个根节点  
   var node = treeObj.getNodesByFilter(function (node) { return node.level == 0 }, true);
}
function zTreeOnClick(event, treeId, treeNode) {
	//console.log(JSON.stringify(treeNode));
    //alert(treeNode.id + ", " + treeNode.name);
    var treeObj=$.fn.zTree.getZTreeObj("treeDemo");
    getRoot();
};
var setting = {
        data: {
            simpleData: {
                enable: true
            }
        },
        callback: {
    		onClick: zTreeOnClick
    	}
    };
     
    function createTree () {
        var zNodes;
        $.ajax({
            url: getRootPath()+'/orginfo/background_showAllOrgTree.action', //url  action是方法的名称
            data: { id: "11" },
            type: 'POST',
            dataType: "text", //可以是text，如果用text，返回的结果为字符串；如果需要json格式的，可是设置为json
            ContentType: "application/json; charset=utf-8",
            success: function(data) {
                zNodes = data;

                $.fn.zTree.init($("#treeDemo"), setting, eval('(' + zNodes + ')'));
               
               treeObj.selectNode(node);
            },
            error: function(msg) {
                alert("失败");
            }
        });
    }

    $(document).ready(function() {
        createTree();
    });
</script>
</html>