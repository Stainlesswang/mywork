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

<link rel="stylesheet" href="<%=path %>/lib/zTree/v3/css/zTreeStyle/zTreeStyle.css" type="text/css">

<link rel="stylesheet" href="<%=path %>/lib/multiselect2side/jquery.multiselect2side.css" type="text/css">

<link rel="stylesheet" href="<%=path %>/lib/iconfont/iconfont.css" type="text/css">

<style type="text/css">
	.navBtn{margin-top: 8px;margin-right: 10px;}
	.primary{width:35px;margin-top:8px;}
	.p-primary{width:32px;margin-top:8px;height: 31px;line-height: 31px;}
	.ztree li span.button.ico_docu{background-position: -110px -16px;}
	.mydisable{background-color: #ebebe4;} 
</style>
<!--[if IE 6]>
<script type="text/javascript" src="<%=path %>/lib/DD_belatedPNG_0.0.8a-min.js" ></script>
<script>DD_belatedPNG.fix('*');</script>
<![endif]-->
<title>会议类型管理-新建</title>
</head>
<body>
<nav class="breadcrumb" style="position: fixed;width:100%;"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 无纸化会议 <span class="c-gray en">&gt;</span> 会议类型管理
    <span class="select-box inline" style="float:right;border:0;font-size:14px;">
	    <button name="" id="saveBtn" class="btn btn-primary navBtn" type="button" ><i class="Hui-iconfont">&#xe616;</i> 保存</button>
	    <button name="" id="exitBtn" class="btn btn-danger navBtn" type="button" ><i class="Hui-iconfont">&#xe631;</i> 退出</button>
	</span>
</nav>

<div class="page-container" style="position: absolute;top: 39px;width:100%;">
	
	<form class="form form-horizontal" id="form-meet-add">
	<table class="table  table-bg" id="datatable" style="width:80%;">
		<tbody>
			<tr class="text-l">
				<td class="text-r"><span class="c-red">*</span>会议类型编号:</td>
				<td colspan="3">
					<input type="text" class="input-text"  name="identifier" id="identifier" value="${data.identifier}">
					<input type="hidden" id="originalIdentifier" value="${data.identifier}">
					<input type="hidden" name="id" id="id" value="${data.id}"/>
				</td>
			</tr>
			<tr class="text-l">
				<td class="text-r"><span class="c-red">*</span>会议类型名称:</td>
				<td colspan="3">
					<input type="text" class= "input-text" name="name" id="name" value="${data.name}">
				</td>
			</tr>
			<tr class="text-l">
				<td class="text-r">会议管理员:</td>
				<td colspan="3">
					<input type="text" class="input-text mydisable" style="width:82%;float:left;" readonly="readonly"   name="manager_name" id="manager_name" value="${data.manager_name}">
					<input type="hidden"name="manager_id" id="manager_id" value="${data.manager_id}">
					<button class="btn btn-primary" type="button" id="selectManagerBtn" style="width:17%;float:right;">选择</button>
				</td>
			</tr>
			<tr class="text-l">
				<td class="text-r">排序:</td>
				<td >
					<input type="text" class= "input-text" name="order" id="order" value="${data.order}">
				</td>
				<td>（选择类型时降序排序）</td>
			</tr>
			<tr class="text-l">
				<td class="text-r">简要说明:</td>
				<td colspan="3">
					<textarea class= "textarea" name="comment" id="comment">${data.comment}</textarea>
				</td>
			</tr>
		</tbody>
	</table>
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

<script type="text/javascript" src="<%=path %>/lib/My97DatePicker/4.8/WdatePicker.js"></script> 
<script type="text/javascript" src="<%=path %>/lib/laypage/1.2/laypage.js"></script>
<script type="text/javascript" src="<%=path %>/lib/zTree/v3/js/jquery.ztree.all-3.5.min.js"></script>

<script type="text/javascript" src="<%=path %>/lib/jquery.validation/1.14.0/jquery.validate.js"></script> 
<script type="text/javascript" src="<%=path %>/lib/jquery.validation/1.14.0/validate-methods.js"></script> 
<script type="text/javascript" src="<%=path %>/lib/jquery.validation/1.14.0/messages_zh.js"></script>


<script type="text/javascript" src="<%=path %>/js/util/util.js" ></script>
<script type="text/javascript" src="<%=path %>/js/lodash.js" ></script>

<script type="text/javascript">
	var rootPath = "<%=path%>";
</script>
<script type="text/javascript" src="<%=path %>/js/custome/meeting/meeting-type-add.js" ></script>

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
<!-- 选择会议管理员弹框 start -->
<div id="selectManagerLayer" style="display:none;">
	<article class="page-container">
		<form class="form form-horizontal">
			<div style="min-height:300px;">
				<table class="table  table-bg">
					<tr>
						<td>
							<label class="" style="font-weight: normal;margin-left: 10px">
								<input type="checkbox" value="" name="includeChild" id="includeChild" checked="checked" style="margin: 0;"/>
								包含子部门</label>
						</td>
						<td>
							<input type="text" name="keywords" id="keywords"/>
							<input type="button" id = "findBtn" value="查找" >
							<input type="button" id = "clearBtn" value="清除" >
						</td>
					</tr>
					<tr>
						<td style="vertical-align: top;">
							<ul id="orgTree" class="ztree"></ul>
						</td>
						<td colspan="2">
							<div>
				                <select id="dealerselect" name="dealerselect" size="12" style="width:220px">
				                	
				                </select>
				            </div>
						</td>
					</tr>
				</table>
				
			</div>
			
			<!-- <div class="row cl">
				<div style="text-align:center;">
					<button class="btn btn-primary" type="button" id="conChargeBtn">确定</button>
					<button class="btn btn-default cancelBtn" id="canChargeBtn" type="button">关闭</button>
				</div>
			</div> -->
		</form>
	</article>
</div>
<!-- 选择会议管理员弹框 end -->
<style type="text/css">
	body, th, td, button, input, select, textarea
	{
		font-family: "\5B8B\4F53"
	}
</style>
</html>