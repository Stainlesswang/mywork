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
<script type="text/javascript" src="lib/html5shiv.js"></script>
<script type="text/javascript" src="lib/respond.min.js"></script>
<![endif]-->
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
</style>
<!--[if IE 6]>
<script type="text/javascript" src="lib/DD_belatedPNG_0.0.8a-min.js" ></script>
<script>DD_belatedPNG.fix('*');</script>
<![endif]-->
<title>会议材料推送-新建</title>
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 无纸化会议 <span class="c-gray en">&gt;</span> 会议材料推送
    <span class="select-box inline" style="float:right;border:0;font-size:14px;">
	    <button name="" id="saveMeetBtn" class="btn btn-primary navBtn" type="button" ><i class="Hui-iconfont">&#xe616;</i> 保存</button>
	    <button name="" id="" class="btn btn-primary navBtn" type="button" ><i class="Hui-iconfont">&#xe616;</i> 推送</button>
	    <button name="" id="mergeBtn" class="btn btn-primary navBtn" type="button" ><i class="Hui-iconfont">&#xe604;</i> 合并附件</button>
	    <c:if test="${data.mid!=null }">
	    	<button name="" id="delBtn" class="btn btn-primary navBtn" type="button" onclick="delMeetInfo()"><i class="Hui-iconfont">&#xe609;</i> 删除</button>
	    </c:if>
	    
	    
	    <button name="" id="exitBtn" class="btn btn-danger navBtn" type="button" ><i class="Hui-iconfont">&#xe631;</i> 退出</button>
	</span>
</nav>
<div class="page-container">
	<form class="form form-horizontal" id="form-meet-add">
	<table class="table  table-bg" id="datatable" style="width:80%;">
		<tbody>
			<tr class="text-c">
				<td colspan="4"><h2 style="color: #095EA7;font-weight: bold;">会议资料维护单</h2></td>
			</tr>
			<tr class="text-l">
				<td class="text-r"><span class="c-red">*</span>会议标题:</td>
				<td colspan="3">
					<input type="text" class="input-text"  name="meet_name" id="meet_name">
					<input type="hidden" name="mid" id="mid" value="${data.mid }"/>
					<input type="hidden" value="${data.draft_time}" id="draft_time" name="draft_time"/>
				</td>
			</tr>
			<tr class="text-l">
				<td class="text-r"><span class="c-red">*</span>会议开始时间:</td>
				<td>
					<input type="text" onfocus="WdatePicker({ dateFmt:'yyyy-MM-dd HH:mm:ss' })" class="input-text Wdate"  name="meet_time" id="meet_time">
				</td>
				<td class="text-r">会议地点:</td>
				<td><input type="text" class="input-text"  name="meet_addr" id="meet_addr"></td>
			</tr>
			<tr class="text-l">
				
				<td class="text-r"><span class="c-red">*</span>会议结束时间:</td>
				<td><input type="text" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'meet_time\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})" class="input-text Wdate"  name="end_time" id="end_time"></td>
				<td class="text-r">主持人:</td>
				<td>
					<input type="text" class="input-text"  name="master" id="master">
				</td>
			</tr>
			<tr class="text-l">
				<td class="text-r">参会领导:</td>
				<td colspan="3">
					<input type="text" class="input-text" style="width:80%;" readonly="readonly" disabled="disabled"  name="meet_attend" id="meet_attend">
					<input type="hidden"name="meet_attend_ids" id="meet_attend_ids">
					<button class="btn btn-primary" type="button" id="selectUserBtn">选择</button>
				</td>
			</tr>
			<tr class="text-l">
				<td class="text-r">拟稿人:</td>
				<td><span id="_real_name">${sessionScope.loginUser.real_name}</span></td>
				<td class="text-r">拟稿时间:</td>
				<td><span id="_draft_time">${data.draft_time}</span></td>
			</tr>
			<tr class="text-l">
				<td class="text-r">备注信息:</td>
				<td colspan="3"><input type="text" class="input-text"  name="remark" id="remark"></td>
			</tr>
			<tr class="text-l">
				<td class="text-r">是否已推送设备:</td>
				<td><span id="is_ipad">未推送</span></td>
				<td class="text-r">推送时间:</td>
				<td><span id="push_time"></span></td>
			</tr>
			<tr class="text-l">
				<td colspan="4">附件:（附件拆离:请用鼠标右击点中要拆离的附件，用鼠标左键点"目标另存为(A)" 即可）</td>
			</tr>
			<tr class="text-l">
				<td colspan="4"><button class="btn btn-primary" type="button" id="addAttachBtn">添加附件</button></td>
			</tr>
			<tr class="text-l">
				<td colspan="4">
					<table id="attachList" style="">
						<!-- <tr>
							<td>1</td>
							<td>图片</td>
							<td>测试.pdf(0.08M)</td>
							<td>删除</td>
						</tr> -->
						
					</table>
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

<script type="text/javascript" src="<%=path %>/js/custome/meeting/meeting-materials-push-add-modify.js" ></script>


</body>

<!-- 选择参会领导弹出框 start -->
<div id="selectLeaderLayer" style="display:none;">
	<article class="page-container">
		<form class="form form-horizontal" id="form-select-org">
			<div class="row cl" style="min-height:300px;">
				<table class="table  table-bg">
					<tr>
						<td>
							<input type="checkbox" id="includeChild" name="includeChild">
							<label for="includeChild">含子部门</label>
						</td>
						<td>
							<input type="text" name="keywords" id="keywords"/>
							<input type="button" value="查找" onclick="getUserList()">
							<input type="button" value="清除" onclick="clearSearch()">
						</td>
					</tr>
					<tr>
						<td>
							<ul id="treeDemo" class="ztree"></ul>
						</td>
						<td colspan="2">
							<div id="select">
				                <select multiple="multiple" id="dealerselect" name="dealerselect" size="12">
				                	
				                </select>
				            </div>
						</td>
					</tr>
				</table>
				
			</div>
			
			<div class="row cl">
				<!-- <div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-3"> -->
				<div style="text-align:center;">
					<button class="btn btn-primary" type="button" id="confirmSelectBtn">确定</button>
					<button class="btn btn-default cancelBtn" id="closeSelectLeaderLayerBtn" type="button">取消</button>
				</div>
			</div>
		</form>
	</article>
</div>
<!-- 选择参会领导弹出框 end -->

<!-- 上传附件弹出框 start -->
<div id="uploadAttachLayer" style="display:none;">
<article class="page-container">
	<form id="form_file" class="form form-horizontal" method="post" enctype="multipart/form-data" action="">
		<div class="row cl">
			<div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-3">
				<input type="file" class="required" id="uploadFile" name="uploadFile" accept="" style="width:100%;" />
			</div>
		</div>
		<div class="row cl">
			<div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-3" style="">
				<button class="btn btn-primary" type="button" id="uploadFileBtn">确定</button>
				<button class="btn btn-default cancelBtn" id="closeUploadLeaderLayerBtn" type="button">取消</button>
			</div>
		</div>
	</form>
	</article>
</div>
<!-- 上传附件弹出框 end -->

<!-- 合并附件弹出窗口 start -->
<div id="merge-file" style="display:none;">
	<article class="page-container">
	<form class="form form-horizontal" id="form-materials-merge">
	<div class="row cl">
		<h2 class="text-c">多文件合并页</h2>
	</div>
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>合并后的文件名称：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<input type="text" class="input-text" value="" placeholder="" id="fileName" name="fileName">
		</div>
	</div>
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3">1</label>
		<div class="formControls col-xs-8 col-sm-9">
			<span class="select-box" style="width:150px;">
				<select class="select" name="adminRole" size="1">
					<option value="0">测试正文.doc</option>
					<option value="1">测试附件.doc</option>
				</select>
			</span>
		</div>
	</div>
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3">2</label>
		<div class="formControls col-xs-8 col-sm-9">
			<span class="select-box" style="width:150px;">
				<select class="select" name="adminRole" size="1">
					<option value="0">测试正文.doc</option>
					<option value="1" selected="selected">测试附件.doc</option>
				</select>
			</span>
		</div>
	</div>
	<div class="row cl">
		<div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-3">
			<button class="btn btn-primary" type="button">确定</button>
			<button class="btn btn-default" type="button" id="cancelBtn">取消</button>
			<!-- <input class="btn btn-primary radius" type="submit" value="&nbsp;&nbsp;提交&nbsp;&nbsp;"> -->
		</div>
	</div>
	</form>
</article>
</div>
<!-- 合并附件弹出窗口 end -->

</html>