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
<title>会议材料推送-新建</title>
</head>
<body>
<nav class="breadcrumb" style="position: fixed;width:100%;"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 无纸化会议 <span class="c-gray en">&gt;</span> 会议材料维护单
    <span class="select-box inline" style="float:right;border:0;font-size:14px;">
    <!-- 会议未结束 -->
    <c:if test="${data.status!=2}">
	    <button name="" id="saveMeetBtn" class="btn btn-primary navBtn" type="button" ><i class="Hui-iconfont">&#xe616;</i> 保存</button>
	    <button name="" id="pushToipad" class="btn btn-primary navBtn" type="button" ><i class="Hui-iconfont">&#xe644;</i> 推送</button>
	    <button name="" id="mergeBtn" class="btn btn-primary navBtn" type="button" ><i class="Hui-iconfont">&#xe604;</i> 合并附件</button>
     </c:if>
	    <c:if test="${data.mid!=null }">
	    	<c:if test="${data.status!=2}">
	    		<button name="" id="endBtn" class="btn btn-primary navBtn" type="button" ><i class="Hui-iconfont">&#xe6e4;</i> 结束会议</button>
	    	</c:if>
	    	<button name="" id="delBtn" class="btn btn-primary navBtn" type="button" onclick="delMeetInfo()"><i class="Hui-iconfont">&#xe609;</i> 删除</button>
	    </c:if>
	    
	    
	    <button name="" id="exitBtn" class="btn btn-danger navBtn" type="button" onclick="exitbtn()" ><i class="Hui-iconfont">&#xe631;</i> 退出</button>
	</span>
</nav>
<c:if test="${data.status ==2}">
</c:if>

<div class="page-container" style="position: absolute;top: 39px;width:100%;">
	
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
				<td class="text-r">会议类型:</td>
				<td colspan="3">
					<select class="input-text" id="meet_type" name="meet_type"></select>
				</td>
			</tr>
			<tr class="text-l">
				<td class="text-r"><span class="c-red">*</span>会议开始时间:</td>
				<td>
					<input type="text" onfocus="WdatePicker({ dateFmt:'yyyy-MM-dd HH:mm:ss' })" class="input-text Wdate"  name="meet_time" id="meet_time">
				</td>
				<td class="text-r"><span class="c-red">*</span>会议地点:</td>
				<td><input type="text" class="input-text"  name="meet_addr" id="meet_addr"></td>
			</tr>
			<tr class="text-l">
				
				<td class="text-r">会议结束时间:</td>
				<td><input type="text" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'meet_time\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})" class="input-text Wdate"  name="end_time" id="end_time"></td>
				<td class="text-r">主持人:</td>
				<td>
					<input type="text" class="input-text"  name="master" id="master">
				</td>
			</tr>
			<tr class="text-l">
				<td class="text-r"><span class="c-red">*</span>推送范围:</td>
				<td colspan="3">
					<input type="text" class="input-text mydisable" style="width:82%;float:left;" readonly="readonly"   name="meet_attend" id="meet_attend">
					<input type="hidden"name="meet_attend_ids" id="meet_attend_ids">
					<button class="btn btn-primary" type="button" id="selectUserBtn" style="width:17%;float:right;">选择</button>
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
				<td><span id="_push_time"></span></td>
			</tr>
			<tr class="text-l">
				<td class="text-r">资料下载情况:</td>
				<td colspan="3">
					<input type="text" class="input-text mydisable" readonly="readonly"   name="downloader_name" id="downloader_name">
				</td>
			</tr>
			<tr class="text-l">
				<td colspan="4">
					<!-- 附件:（附件拆离:请用鼠标右击点中要拆离的附件，用鼠标左键点"目标另存为(A)" 即可）</br> -->
					合并附件:支持合并的文件格式有&nbsp;pdf、doc、docx、xls、xlsx、ppt、pptx，其他格式暂不支持
				</td>
			</tr>
			<tr class="text-l" id="upload-container">
				<td colspan="4"><button class="btn btn-primary" type="button" id="addAttachBtn">批量添加附件</button></td>
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
			<tr class="text-l">
				<td colspan="4" id="pdfDraftTableLabel" style="display:none">pdf文件草稿版本列表：</td>
			</tr>
			<tr class="text-l">
				<td colspan="4">
					<table class="table table-border table-bordered table-bg" id="pdfDraftTable" style="display:none"></table>
				</td>
			</tr>
			
		</tbody>
	</table>
	<c:if test="${data.status ==2}">
		<div style="position:absolute;top: 10px;left: 81%;" ><i class="iconfont" style="font-size: 100px;">&#xe608;</i></div>
	</c:if>
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
<%--<script type="text/javascript" src="<%=path %>/lib/jquery.validation/1.14.0/validate-methods.js"></script> --%>
<script type="text/javascript" src="<%=path %>/lib/jquery.validation/1.14.0/messages_zh.js"></script>


<script type="text/javascript" src="<%=path %>/js/util/util.js" ></script>
<script type="text/javascript" src="<%=path %>/lib/sea.js"></script>
<script>
    seajs.use("<%=path %>/js/custome/meeting/upload.js");
</script>

<script type="text/javascript" src="<%=path %>/js/custome/meeting/meeting-materials-push-add-modify.js" ></script>

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

<!-- 选择参会领导弹出框 start -->
<div id="selectLeaderLayer" style="display:none;">
	<article class="page-container">
		<form class="form form-horizontal" id="form-select-org">
			<div class="row cl" style="min-height:300px;">
				<table class="table  table-bg">
					<tr>
						<td>
							<label class="" style="font-weight: normal;">
								<input type="checkbox" value="" name="includeChild" id="includeChild" checked="checked" style="margin: 0;"/>
								包含子部门</label>
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
				                <select multiple="multiple" id="dealerselect" name="dealerselect" size="12">
				                	
				                </select>
				            </div>
						</td>
					</tr>
				</table>
				
			</div>
			
			<div class="row cl">
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
		<label for="uploadFile" class="error"></label>
		<div class="row cl">
			<div class="col-xs-8 col-sm-9 col-xs-offset-3 col-sm-offset-2">
				<input type="file" class="requiredFile" id="uploadFile" name="uploadFile" accept="" style="width:100%;" />
			</div>
		</div>
		<div class="row cl">
			<div class="col-xs-6 col-sm-7 col-xs-offset-3 col-sm-offset-2">
				<label class="" style="font-weight: normal; font-size: 12px; color:red">
				上传文件大小不得超过50M!</label>
			</div>
		</div>
		<div class="row cl">
			<div class="col-xs-6 col-sm-7 col-xs-offset-5 col-sm-offset-4" style="">
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
		<table style="display:none;">
		<tr valign="middle" align="center" id="tabRowTemplate" class="template">
			<td class="text-r"></td>
			<td  class="text-r">
				<span class="select-box" style="width:78%;float:left;">
					<select class="select" name="mf" size="1" id="select0">
						<!-- <option value="0">测试正文.doc</option>
						<option value="1">测试附件.doc</option> -->
					</select>
				</span>
				<span>
					<button class="btn btn-primary" style="width:10%;;float:left;margin-left:3px;margin-right:2px;" type="button" onclick="addRow(this);">追加</button>
					<button class="btn btn-danger" type="button" style="width:10%;" onclick="rmRow(this);">删除</button>
				</span>
			</td>
			
		</tr>
	</table>
	
	<form class="form form-horizontal" id="form-materials-merge">
	<div class="row cl">
		<h2 class="text-c">多文件合并页</h2>
	</div>
	<div class="row cl" class="text-c">
		<!-- <div class="formControls col-xs-8 col-sm-9"> -->
		
			<table id="merge-tb" class="table  table-bg" style="width:70%;margin: 0 auto;margin-left:10%;">
				<tr>
					<td class="text-r" style="width:180px;"><span class="c-red">*</span>合并后的文件名称：</td>
					<td class="text-l">
						<input type="text" class="input-text" value="" placeholder="2-255位，数字字母汉字下划线(文件名不需要后缀，缺省为pdf格式)" id="fileName" name="fileName">
					</td>
				</tr>
				<tr >
					<td class="text-r"></td>
					<td  class="text-r">
						<span class="select-box" style="width:78%;float:left;">
							<select class="select" name="mf" size="1" id="select1">
							</select>
						</span>
						<span>
							<button class="btn btn-primary" style="width:10%;float:left;margin-left:3px;margin-right:2px;" type="button" id="btn0" onclick="addRow(this);">追加</button>
							<button class="btn btn-danger" type="button" style="width:10%;" onclick="rmRow(this);">删除</button>
						</span>
						
					</td>
				</tr>
			</table>
		<!-- </div> -->
	</div>
	
	
	<div class="row cl" style="text-align:center;">
		<button class="btn btn-primary" type="button" id="mergeConfirmBtn">确定</button>
		<button class="btn btn-default" type="button" id="cancelMergeFileBtn">取消</button>
		<!-- <div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-3">
			<button class="btn btn-primary" type="button" id="mergeConfirmBtn">确定</button>
			<button class="btn btn-default" type="button" id="cancelMergeFileBtn">取消</button>
		</div> -->
	</div>
	</form>
</article>
</div>
<!-- 合并附件弹出窗口 end -->


<style type="text/css">
	body, th, td, button, input, select, textarea
	{
		font-family: "\5B8B\4F53"
	}
</style>
</html>