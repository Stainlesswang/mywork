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
<!--[if IE 6]>
<script type="text/javascript" src="<%=path %>/lib/DD_belatedPNG_0.0.8a-min.js" ></script>
<script>DD_belatedPNG.fix('*');</script>
<![endif]-->

<style type="text/css">
	.navBtn{margin-top: 8px;margin-right: 10px;}
	.ztree li span.button.ico_docu{background-position: -110px -16px;}
	.mydisable{background-color: #ebebe4;}    
</style>

<title>设备管理-新建</title>
</head>
<body>
<nav class="breadcrumb" style="position: fixed;width:100%;"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 无纸化会议 <span class="c-gray en">&gt;</span> 设备管理
	<span class="select-box inline" style="float:right;border:0;font-size:14px;">
	    <button name="" id="saveBtn" class="btn btn-primary navBtn" type="button"><i class="Hui-iconfont">&#xe616;</i> 保存退出</button>
	    <c:if test="${data.mid!=null }">
	    	<button name="" id="" onclick="clearDeviceBindInfo()" class="btn btn-danger navBtn" type="button"><i class="Hui-iconfont">&#xe6a6;</i> 清除设备绑定</button>
	    	<button name="" id="" onclick="delDeviceBindInfo()" class="btn btn-danger navBtn" type="button"><i class="Hui-iconfont">&#xe6a6;</i> 删除</button>
	    </c:if>
	    <button name="" id="exitBtn" class="btn btn-danger navBtn" type="button"><i class="Hui-iconfont">&#xe631;</i> 退出</button>
	</span>
</nav>
<div class="page-container" style="position: absolute;top: 39px;width:100%;">
	<form class="form form-horizontal" id="form-device-add">
		<table class="table  table-bg" id="datatable" style="width:80%;">
			<tbody>
				<tr class="text-c">
					<td colspan="4"><h2 style="color: #095EA7;font-weight: bold;">设备信息维护单</h2></td>
				</tr>
				<tr class="text-l">
					<td class="text-r"><span class="c-red">*</span>设备使用人:</td>
					<td>
						<input type="text" class="input-text mydisable" readonly="readonly"  style="width:80%"  name="real_name" id="real_name">
						<input type="hidden" id="mid" name="mid" value="${data.mid }"/>
						<input type="hidden" id="user_id" name="user_id" value=""/>
						<input type="hidden" id="create_time" name="create_time" value="${data.create_time}"/>
						<button class="btn btn-primary" type="button" id="selectUserBtn">选择</button>
					</td>
					<td class="text-r">账号:</td>
					<td>
						<input type="text"  class="input-text"  name="job_number" id="job_number" readonly="readonly" value="${data.user_name}">
					</td>
				</tr>
				<tr class="text-l">
					<td class="text-r">授权码:</td>
					<td>
						<span class="select-box" style="width:150px;">
							<select class="select" name="authorationcode" id="authorationcode">
								<option value="">选择授权码</option>
							</select>
						</span>
					</td>
					<td class="text-r">初始密码:</td>
					<td><input type="password" class="input-text"  name="passwd" id="passwd" readonly="readonly"></td>
				</tr>
				<tr class="text-l">
					<td class="text-r">设备编号:</td>
					<!-- <td ><span id="_ipad_uuid"></span></td> -->
					<td id="device">
						<span class="select-box" style="width:250px;">	
							<select class="select" name="deviceCode" id="deviceCode">
								<option value="">选择设备</option>
							</select>
						</span>
						
					</td>
					<td class="text-r">设备电量:</td>
					<td><span id="energy"></span></td>
				</tr>
				<tr class="text-l">
					<td class="text-r">登记人:</td>
					<td ><span id="_register">${sessionScope.loginUser.real_name}</span></td>
					<td class="text-r">登记时间:</td>
					<td><span id="_create_time">${data.create_time}</span></td>
				</tr>
				<tr class="text-l">
					<td class="text-r">备注信息:</td>
					<td colspan="3"><input type="text" class="input-text"  name="remark" id="remark"></td>
				</tr>
			</tbody>
		</table>
	</form>
</div>
<!--_footer 作为公共模版分离出去-->
<script type="text/javascript" src="<%=path %>/lib/jquery/1.9.1/jquery.min.js"></script> 
<script type="text/javascript" src="<%=path %>/static/bootstrap/import/import.inc.js" ></script>
<script type="text/javascript" src="<%=path %>/lib/layer/2.4/layer.js"></script>
<script type="text/javascript" src="<%=path %>/static/h-ui/js/H-ui.min.js"></script> 
<script type="text/javascript" src="<%=path %>/static/h-ui.admin/js/H-ui.admin.js"></script>
<!--/_footer 作为公共模版分离出去-->

<!--请在下方写此页面业务相关的脚本-->
<!--<script type="text/javascript" src="<%=path %>/lib/datatables/1.10.0/jquery.dataTables.min.js"></script>--> 
<script type="text/javascript" src="<%=path %>/lib/laypage/1.2/laypage.js"></script>
<script type="text/javascript" src="<%=path %>/lib/zTree/v3/js/jquery.ztree.all-3.5.min.js"></script>


<script type="text/javascript" src="<%=path %>/js/util/util.js" ></script>

<script type="text/javascript" src="<%=path %>/js/custome/meeting/device-management-add-modify.js" ></script>



</body>

<!-- 选择设备使用人弹出框 start -->
<div id="selectUserLayer" style="display:none;">
	<article class="page-container">
		<form class="form form-horizontal" id="form-select-org">
			<div class="row cl" style="min-height:300px;">
				<table class="table  table-bg">
					<tr>
						<td style="width:40%;vertical-align: top;">
							<div style="height:400px;">
								<label class="" style="font-weight: normal;">
								<input type="checkbox" value="" name="includeChild" id="includeChild" checked="checked" style="margin: 0;"/>
								包含子部门</label>
								<ul id="treeDemo" class="ztree"></ul>
							</div>
						</td>
						<td colspan="2">
							<div style="height:400px;">
								<table  id="userTable" data-toggle="table" 
								data-method="post"
								data-url="<%=basePath %>/userinfo/background_showUserInfo.action"
								data-striped="false"
								data-show-refresh="false"
								data-search="false"
								data-side-pagination="server"
								data-page-list="[5, 25, 50, 100, All]"
								data-pagination="true"
								data-page-size="5"
								data-query-params-type='limit'
								data-query-params="queryParam_user"
								data-show-pagination-switch="false">
								<thead>
		      	 					<tr>
		      	 						<th data-field="index" data-formatter="indexFormatter">序号</th> 
		            					<th data-field="user_id" data-visible="false">用户id</th>
		            					<th data-field="user_name">账号</th>
		            					<th data-field="real_name">姓名</th>
		            					<th data-field="org_name">所在部门</th>
		            					<th data-formatter="selectHtmlFormatter" data-events="selectUserEvents">操作</th>
		        					</tr>
		    					</thead>
		    					<tbody></tbody>
							</table>
							</div>
						</td>
					</tr>
				</table>
				
			</div>
			
			<div class="row cl">
				<div style="text-align:center;">
					<!-- <button class="btn btn-primary" type="button" id="confirmSelectBtn">确定</button> -->
					<button class="btn btn-default cancelBtn" id="closeSelectUserLayerBtn" type="button">取消</button>
				</div>
			</div>
		</form>
	</article>
</div>

<!-- 选择设备使用人 end -->

<style type="text/css">
	body, th, td, button, input, select, textarea
	{
		font-family: "\5B8B\4F53"
	}
</style>
</html>