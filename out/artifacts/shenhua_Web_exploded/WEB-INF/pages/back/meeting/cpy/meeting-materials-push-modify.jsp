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
<script type="text/javascript" src="lib/html5shiv.js"></script>
<script type="text/javascript" src="lib/respond.min.js"></script>
<![endif]-->
<link rel="stylesheet" type="text/css" href="<%=path %>/static/h-ui/css/H-ui.min.css" />
<link rel="stylesheet" type="text/css" href="<%=path %>/static/h-ui.admin/css/H-ui.admin.css" />
<link rel="stylesheet" type="text/css" href="<%=path %>/lib/Hui-iconfont/1.0.8/iconfont.css" />
<link rel="stylesheet" type="text/css" href="<%=path %>/static/h-ui.admin/skin/default/skin.css" id="skin" />
<link rel="stylesheet" type="text/css" href="<%=path %>/static/h-ui.admin/css/style.css" />
<!--[if IE 6]>
<script type="text/javascript" src="lib/DD_belatedPNG_0.0.8a-min.js" ></script>
<script>DD_belatedPNG.fix('*');</script>
<![endif]-->
<title>会议资料维护单</title>
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 无纸化会议 <span class="c-gray en">&gt;</span> 会议材料推送
    <button name="" id="" class="btn btn-primary" type="button" style="float:right;margin-top: 8px;"><i class="Hui-iconfont">&#xe631;</i> 退出</button>  
    <button name="" id="" class="btn btn-danger" type="button" style="float:right;margin-top: 8px;"><i class="Hui-iconfont">&#xe609;</i> 删除</button>
     &nbsp;&nbsp;  
    <button name="" id="mergeBtn" class="btn btn-primary" type="button" style="float:right;margin-top: 8px;margin-right: 15px;"><i class="Hui-iconfont">&#xe604;</i> 合并附件</button>
	<button name="" id="" class="btn btn-primary" type="button" style="float:right;margin-top: 8px;margin-right: 15px;"><i class="Hui-iconfont">&#xe616;</i> 推送</button>
	<button name="" id="" class="btn btn-primary" type="button" style="float:right;margin-top: 8px;margin-right: 15px;"><i class="Hui-iconfont">&#xe616;</i> 保存</button>
</nav>
<div class="page-container">
	<table class="table  table-bg" id="datatable">
		<tbody>
			<tr class="text-c">
				<td colspan="4"><h2 style="color: #095EA7;font-weight: bold;">会议资料维护单</h2></td>
			</tr>
			<tr class="text-l">
				<td class="text-r"><span class="c-red">*</span>会议标题:</td>
				<td colspan="3"><input type="text" class="input-text"   id="meet_name" name="meet_name"  >
				<input id="mid" name="mid" type="hidden" value="${data.mid}"/>
				</td>		
			</tr>
			<tr class="text-l">
				<td class="text-r"><span class="c-red">*</span>会议开始时间:</td>
				<td><input type="text" onfocus="WdatePicker({ dateFmt:'yyyy-MM-dd HH:mm:ss' })" class="input-text Wdate"   id="meet_time" name="meet_time" ></td>
				<td class="text-r">会议地点:</td>
				<td><input type="text" class="input-text"  id="meet_addr" name="meet_addr"  ></td>
			</tr>
			<tr class="text-l">
				<td class="text-r"><span class="c-red">*</span>参会领导:</td>
				<td colspan="3"><input type="text" class="input-text" style="width:80%;"  name=""><button class="btn btn-primary" type="button">选择</button></td>
			</tr>
			<tr class="text-l">
				<td class="text-r">拟稿人:</td>
				<td><span id="user_name" ></span></td>
				<td class="text-r">拟稿时间:</td>
				<td><span id="draft_time" ></span></td>
			</tr>
			<tr class="text-l">
				<td class="text-r">备注信息:</td>
				<td colspan="3"><input type="text" class="input-text"   id="remark" name="remark" ></td>
			</tr>
			<tr class="text-l">
				<td class="text-r">是否已推送设备:</td>
				<td><span id="is_ipad"></span></td>
				<td class="text-r">推送时间:</td>
				<td><input type="text" class="input-text"  id="push_time" name="push_time" ></td>
			</tr>
			<tr class="text-l">
				<td colspan="4">附件:（附件拆离:请用鼠标右击点中要拆离的附件，用鼠标左键点"目标另存为(A)" 即可）</td>
			</tr>
			<tr class="text-l">
				<td colspan="4"><button class="btn btn-primary" type="button">添加附件</button></td>
			</tr>
			<tr class="text-l">
				<td colspan="4">
					<table>
						<tr>
							<td>1</td>
							<td>图片</td>
							<td>测试.pdf(0.08M)</td>
							<td>删除</td>
						</tr>
						<tr>
							<td>2</td>
							<td>图片</td>
							<td>测试正文.doc(0.02M)</td>
							<td>删除</td>
						</tr>
						<tr>
							<td>3</td>
							<td>图片</td>
							<td>测试附件.doc(0.01M)</td>
							<td>删除</td>
						</tr>
					</table>
				</td>
			</tr>
		</tbody>
	</table>
</div>
<!--_footer 作为公共模版分离出去-->
<script type="text/javascript" src="<%=path %>/lib/jquery/1.9.1/jquery.min.js"></script> 
<script type="text/javascript" src="<%=path %>/lib/layer/2.4/layer.js"></script>
<script type="text/javascript" src="<%=path %>/static/h-ui/js/H-ui.min.js"></script> 
<script type="text/javascript" src="<%=path %>/static/h-ui.admin/js/H-ui.admin.js"></script>
<!--/_footer 作为公共模版分离出去-->

<!--请在下方写此页面业务相关的脚本-->
<script type="text/javascript" src="<%=path %>/lib/My97DatePicker/4.8/WdatePicker.js"></script> 
<script type="text/javascript" src="<%=path %>/lib/laypage/1.2/laypage.js"></script>

<script type="text/javascript" src="<%=path %>/static/bootstrap/import/import.inc.js" ></script>
<script type="text/javascript" src="<%=path %>/js/util/util.js" ></script>
<script src="<%=path %>/js/custome/meeting/meeting-materials-push-modify.js"></script>
<script type="text/javascript">
 
$(function(){
	$("#mergeBtn").click(
			function(){
				mergeFile("合并附件",getRootPath()+"/backToUrl/toDo.action?url=meetingFileMerge",800,500);
			}
			
			);
});
/*
	参数解释：
	title	标题
	url		请求的url
	id		需要操作的数据id
	w		弹出层宽度（缺省调默认值）
	h		弹出层高度（缺省调默认值）
*/
/*管理员-增加*/
function mergeFile(title,url,w,h){
	layer_show(title,url,w,h);
}
/*管理员-删除*/
/* function admin_del(obj,id){
	layer.confirm('确认要删除吗？',function(index){
		$.ajax({
			type: 'POST',
			url: '',
			dataType: 'json',
			success: function(data){
				$(obj).parents("tr").remove();
				layer.msg('已删除!',{icon:1,time:1000});
			},
			error:function(data) {
				console.log(data.msg);
			},
		});		
	});
} */

/*管理员-编辑*/
/* function admin_edit(title,url,id,w,h){
	layer_show(title,url,w,h);
} */
/*管理员-停用*/
/* function admin_stop(obj,id){
	layer.confirm('确认要停用吗？',function(index){
		//此处请求后台程序，下方是成功后的前台处理……
		
		$(obj).parents("tr").find(".td-manage").prepend('<a onClick="admin_start(this,id)" href="javascript:;" title="启用" style="text-decoration:none"><i class="Hui-iconfont">&#xe615;</i></a>');
		$(obj).parents("tr").find(".td-status").html('<span class="label label-default radius">已禁用</span>');
		$(obj).remove();
		layer.msg('已停用!',{icon: 5,time:1000});
	});
} */

/*管理员-启用*/
/* function admin_start(obj,id){
	layer.confirm('确认要启用吗？',function(index){
		//此处请求后台程序，下方是成功后的前台处理……
		
		
		$(obj).parents("tr").find(".td-manage").prepend('<a onClick="admin_stop(this,id)" href="javascript:;" title="停用" style="text-decoration:none"><i class="Hui-iconfont">&#xe631;</i></a>');
		$(obj).parents("tr").find(".td-status").html('<span class="label label-success radius">已启用</span>');
		$(obj).remove();
		layer.msg('已启用!', {icon: 6,time:1000});
	});
} */
</script>
</body>
</html>