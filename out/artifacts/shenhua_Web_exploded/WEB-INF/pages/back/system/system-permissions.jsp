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

<link rel="stylesheet" href="<%=path %>/lib/zTree/v3/css/zTreeStyle/zTreeStyle.css" type="text/css">
<link rel="stylesheet" href="<%=path %>/lib/multiselect2side/jquery.multiselect2side.css" type="text/css">
<style type="text/css">
	.navBtn{margin-top: 8px;margin-right: 10px;}
	.primary{width:35px;margin-top:8px;}
	.p-primary{width:32px;margin-top:8px;height: 31px;line-height: 31px;}
	.ztree li span.button.ico_docu{background-position: -110px -16px;}
	.mydisable{background-color: #ebebe4;} 
	.nav_content{position: fixed;width:100%;}
	.ctrl_content{text-align: left;width:100%;position: fixed;z-index: 99;height:51px;top:39px;margin-left:20px;background-color: #ffffff;padding-top:20px;}
	.page_content{position: absolute;top: 90px;width:100%;}
</style>
<title>系统权限管理</title>
</head>
<body>
<nav class="breadcrumb nav_content"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 系统权限管理 <span class="c-gray en">&gt;</span> 系统权限管理
    <span class="select-box inline" style="float:right;border:0;font-size:14px;">
	    <a class="btn btn-success" style="line-height:1.6em;" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a>
	    <button name="" id="savePerBtn" class="btn btn-primary" type="button"><i class="Hui-iconfont">&#xe616;</i> 保存</button>
	</span>
</nav>
<div class="page-container page_content">
<form class="form form-horizontal" id="form-per-add">
	<table class="table  table-bg" id="datatable" style="width:90%;">
		<tbody>
			<tr class="text-c">
				<td colspan="4"><h4>系统权限配置</h4></td>
			</tr>
			<tr class="text-l" style="display: none;">
				<td class="text-r">请您选择本模块的管理员:</td>
				<td colspan="3">
					<input type="text" class="input-text mydisable" readonly="readonly"  style="width:80%;"  name="adminNames" id="adminNames">
					<input id="adminIds" name="adminIds" type="hidden"/>
					<button class="btn btn-primary" type="button" id="selectAdminBtn">查找</button>
				</td> 
			</tr>
			<tr class="text-l">
				<td class="text-r">会议材料推送模块:</td>
				<td colspan="3">
					<input type="text" class="input-text mydisable" readonly="readonly"  style="width:80%;"  name="meetNames" id="meetNames">
					<input id="meetIds" name="meetIds" type="hidden"/>
					<button class="btn btn-primary" type="button" id="selectMeetBtn">查找</button>
				</td>
			</tr>
			<tr class="text-l">
				<td class="text-r">设备管理:</td>
				<td colspan="3">
					<input type="text" class="input-text mydisable" readonly="readonly" style="width:80%;"  name="ipadNames" id="ipadNames">
					<input id="ipadIds" name="ipadIds" type="hidden"/>
					<button class="btn btn-primary" type="button" id="selectIpadBtn">查找</button>
				</td>
			</tr>
			<tr class="text-l">
				<td class="text-r">用户管理:</td>
				<td colspan="3">
					<input type="text" class="input-text mydisable" readonly="readonly"  style="width:80%;"  name="usermanageNames" id="usermanageNames">
					<input id="usermanageIds" name="usermanageIds" type="hidden"/>
					<button class="btn btn-primary" type="button" id="selectUserManageBtn">查找</button>
				</td>
			</tr>
					
		</tbody>
	</table>
	<input type="hidden" id="back" name="back" value="${data.back}"/>
	</form>
</div>
<!--_footer 作为公共模版分离出去-->
<script type="text/javascript" src="<%=path %>/lib/jquery/1.9.1/jquery.min.js"></script> 
<script type="text/javascript" src="<%=path %>/static/bootstrap/import/import.inc.js" ></script> 
<script type="text/javascript" src="<%=path %>/lib/multiselect2side/jquery.multiselect2side.js"></script>
<script type="text/javascript" src="<%=path %>/lib/layer/2.4/layer.js"></script>
<script type="text/javascript" src="<%=path %>/static/h-ui/js/H-ui.min.js"></script> 
<script type="text/javascript" src="<%=path %>/static/h-ui.admin/js/H-ui.admin.js"></script>
<!--/_footer 作为公共模版分离出去-->

<!--请在下方写此页面业务相关的脚本-->
<script type="text/javascript" src="<%=path %>/lib/laypage/1.2/laypage.js"></script>
<script type="text/javascript" src="<%=path %>/lib/zTree/v3/js/jquery.ztree.all-3.5.min.js"></script>

<script type="text/javascript" src="<%=path %>/js/util/util.js" ></script>
<script type="text/javascript" src="<%=path %>/js/custome/system/system-permissions.js" ></script>

<script type="text/javascript">
$(function(){
	$("input").on('keypress',  //所有input标签回车无效，当然，可以根据需求自定义
		function(e){
			var key = window.event ? e.keyCode : e.which;
			if(key.toString() == "13"){
				return false;
			}
		}
	);
});

</script>

</body>
<!-- 各种弹出框 -->

<!-- 选择本模块管理员弹出框 start -->
<div id="selectAdminLayer" style="display:none;">
	<article class="page-container">
		<form class="form form-horizontal" id="form-select-org">
			<div class="row cl" style="min-height:300px;">
				<table class="table  table-bg">
					<tr>
						<td>
                            <input type="hidden" name="adminType" id="adminType" value="0">
                            <input type="checkbox" class="includeChild" id="includeChild" name="includeChild" checked="checked">
							<label for="includeChild">含子部门</label>
						</td>
						<td>
							<input type="text" name="keywords" id="keywords"/>
							<input type="button" value="查找" onclick="getUserList()">
							<input type="button" value="清除" onclick="clearSearch()">
						</td>
					</tr>
					<tr>
						<td style="vertical-align: top;">
							<ul id="treeDemo" class="ztree"></ul>
						</td>
						<td colspan="2">
							<div id="select">
				                <select multiple="multiple" id="adminselect" name="adminselect" size="12">
				                	
				                </select>
				            </div>
						</td>
					</tr>
				</table>
				
			</div>
			
			<div class="row cl">
				<!-- <div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-3"> -->
				<div style="text-align:center;">
					<button class="btn btn-primary confirmBtn" type="button" >确定</button>
					<button class="btn btn-default cancelBtn"  type="button">取消</button>
				</div>
			</div>
		</form>
	</article>
</div>
<!-- 选择本模块管理员弹出框 end -->


<!-- 选择设备管理弹出框 start -->
<div id="selectIpadLayer" style="display:none;">
	<article class="page-container">
		<form class="form form-horizontal" id="form-select-org2">
			<div class="row cl" style="min-height:300px;">
				<table class="table  table-bg" style="width:90%;">
					<tr>
						<td>
                            <input type="hidden" name="adminType" id="adminType2" value="0">
                            <input type="checkbox" class="includeChild" id="includeChild2" name="includeChild2" checked="checked">
							<label for="includeChild2">含子部门</label>
						</td>
						<td>
							<input type="text" name="keywords" id="keywords2"/>
							<input type="button" value="查找" onclick="getUserList()">
							<input type="button" value="清除" onclick="clearSearch()">
						</td>
					</tr>
					<tr>
						<td style="vertical-align: top;">
							<ul id="treeDemo2" class="ztree"></ul>
						</td>
						<td colspan="2">
							<div id="select2">
				                <select multiple="multiple" id="ipadselect" name="ipadselect" size="12">
				                	
				                </select>
				            </div>
						</td>
					</tr>
				</table>
				
			</div>
			
			<div class="row cl">
				<!-- <div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-3"> -->
				<div style="text-align:center;">
					<button class="btn btn-primary confirmBtn" type="button" >确定</button>
					<button class="btn btn-default cancelBtn"  type="button">取消</button>
				</div>
			</div>
		</form>
	</article>
</div>
<!-- 选择设备管理弹出框 end -->


<!-- 选择会议材料推送弹出框 start -->
<div id="selectMeetLayer" style="display:none;">
	<article class="page-container">
		<form class="form form-horizontal" id="form-select-org3">
			<div class="row cl" style="min-height:300px;">
				<table class="table  table-bg" style="width:90%;">
					<tr>
						<td>
                            <input type="hidden" name="adminType" id="adminType3" value="0">
                            <input type="checkbox" class="includeChild" id="includeChild3" name="includeChild3" checked="checked">
							<label for="includeChild3">含子部门</label>
						</td>
						<td>
							<input type="text" name="keywords" id="keywords3"/>
							<input type="button" value="查找" onclick="getUserList()">
							<input type="button" value="清除" onclick="clearSearch()">
						</td>
					</tr>
					<tr>
						<td style="vertical-align: top;">
							<ul id="treeDemo3" class="ztree"></ul>
						</td>
						<td colspan="2">
							<div id="select3">
				                <select multiple="multiple" id="meetselect" name="meetselect" size="12">
				                	
				                </select>
				            </div>
						</td>
					</tr>
				</table>
				
			</div>
			
			<div class="row cl">
				<!-- <div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-3"> -->
				<div style="text-align:center;">
					<button class="btn btn-primary confirmBtn" type="button">确定</button>
					<button class="btn btn-default cancelBtn" type="button">取消</button>
				</div>
			</div>
		</form>
	</article>
</div>
<!-- 选择会议材料推送弹出框 end -->


<!-- 选择用户管理人员弹出框 start -->
<div id="selectUserManageLayer" style="display:none;">
	<article class="page-container">
		<form class="form form-horizontal" id="form-select-org4">
			<div class="row cl" style="min-height:300px;">
				<table class="table  table-bg" style="width:90%;">
					<tr>
						<td>
                            <input type="hidden" name="adminType" id="adminType4" value="0">
							<input type="checkbox" class="includeChild" id="includeChild4" name="includeChild4" checked="checked">
							<label for="includeChild4">含子部门</label>
						</td>
						<td>
							<input type="text" name="keywords" id="keywords4"/>
							<input type="button" value="查找" onclick="getUserList()">
							<input type="button" value="清除" onclick="clearSearch()">
						</td>
					</tr>
					<tr>
						<td style="vertical-align: top;">
							<ul id="treeDemo4" class="ztree"></ul>
						</td>
						<td colspan="2">
							<div id="select3">
				                <select multiple="multiple" id="usermanageselect" name="usermanageselect" size="12">
				                	
				                </select>
				            </div>
						</td>
					</tr>
				</table>
				
			</div>
			
			<div class="row cl">
				<!-- <div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-3"> -->
				<div style="text-align:center;">
					<button class="btn btn-primary confirmBtn" type="button">确定</button>
					<button class="btn btn-default cancelBtn" type="button">取消</button>
				</div>
			</div>
		</form>
	</article>
</div>
<!-- 选择用户管理人员弹出框 end -->



<style type="text/css">
	body, th, td, button, input, select, textarea
	{
		font-family: "\5B8B\4F53"
	}
</style>


</html>