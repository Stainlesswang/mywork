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
<title>设备信息维护</title>
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 无纸化会议 <span class="c-gray en">&gt;</span> 设置管理
    <button name="" id="" class="btn btn-primary" type="button" style="float:right;margin-top: 8px;"><i class="Hui-iconfont">&#xe631;</i> 退出</button>
    <button name="" id="" class="btn btn-primary" type="button" style="float:right;margin-top: 8px;margin-right: 15px;"><i class="Hui-iconfont">&#xe6a6;</i> 删除清除设备绑定 </button>
    <button name="" id="" class="btn btn-primary" type="button" style="float:right;margin-top: 8px;margin-right: 15px;"><i class="Hui-iconfont">&#xe6a6;</i> 清除设备绑定</button>
    <button name="" id="" class="btn btn-primary" type="button" style="float:right;margin-top: 8px;margin-right: 15px;"><i class="Hui-iconfont">&#xe616;</i> 保存退出</button>
</nav>
<div class="page-container">
	<table class="table  table-bg" id="datatable">
		<tbody>
			<tr class="text-c">
				<td colspan="4"><h2 style="color: #095EA7;font-weight: bold;">设备信息维护单</h2></td>
			</tr>
			<tr class="text-l">
				<td class="text-r"><span class="c-red">*</span>设备使用人:</td>
				<td><input type="text" class="input-text" style="width:80%"  name="user_name" id="user_name"><button class="btn btn-primary" type="button">选择</button>
				<input id="mid" name="mid" type="hidden" value="${data.mid}"/>
				</td>
				<td class="text-r">账号:</td>
				<td><input type="text" readonly="readonly" class="input-text"  name="job_number" id="job_number"></td>
			</tr>
			<tr class="text-l">
				<td class="text-r">授权码:</td>
				<td>
					<span class="select-box" style="width:150px;">
						<select class="select" name="adminRole" size="1">
							<option value="0">shxx002</option>
							<option value="1">shxx003</option>
						</select>
					</span>
				</td>
				<td class="text-r">初始密码:</td>
				<td><input type="text" class="input-text"  name="passwd" id="passwd"></td>
			</tr>
			<tr class="text-l">
				<td class="text-r"><span class="c-red">*</span>设备编号:</td>
				<td colspan="3"><span id="ipad_uuid"></span></td>
			</tr>
			<tr class="text-l">
				<td class="text-r">登记人:</td>
				<td><span id="register"></span></td>
				<td class="text-r">登记时间:</td>
				<td><span id="create_time"></span></td>
			</tr>
			<tr class="text-l">
				<td class="text-r">备注信息:</td>
				<td colspan="3"><input type="text" class="input-text"  name="remark" id="remark"></td>
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
<!--<script type="text/javascript" src="<%=path %>/lib/datatables/1.10.0/jquery.dataTables.min.js"></script>--> 
<script type="text/javascript" src="<%=path %>/lib/laypage/1.2/laypage.js"></script>

<script type="text/javascript" src="<%=path %>/static/bootstrap/import/import.inc.js" ></script>
<script type="text/javascript" src="<%=path %>/js/util/util.js" ></script>
<script src="<%=path %>/js/custome/meeting/device-management-modify.js"></script>
<script type="text/javascript">

$(function(){
	/* $("#mergeBtn").click(
			function(){
				mergeFile("合并附件",getRootPath()+"/sub-pages/metting/meeting-materials-push-merge.jsp",800,500);
			}
			
			); */
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
/* function mergeFile(title,url,w,h){
	layer_show(title,url,w,h);
} */
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