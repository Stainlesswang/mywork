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

<link rel="stylesheet" type="text/css" href="<%=path %>/lib/jquery-easyui-1.5.1/themes/default/tree.css">
<link rel="stylesheet" type="text/css" href="<%=path %>/lib/jquery-easyui-1.5.1/themes/icon.css">
<!--[if IE 6]>
<script type="text/javascript" src="lib/DD_belatedPNG_0.0.8a-min.js" ></script>
<script>DD_belatedPNG.fix('*');</script>
<![endif]-->
<title>用户操作</title>
<style type="text/css">
	.td-width{}
	.td-width-text{width:40%;}
	.error1{position: relative;}
</style>
</head>
<body>
<article class="page-container">
<form class="form form-horizontal" id="form-user-add">
	<table class="table  table-bg" id="datatable">
		<tbody>
			<tr class="text-l">
				<td class="text-r td-width"><span class="c-red">*</span>姓名:</td>
				<td class="td-width-text"><input type="text"  class="input-text" id="real_name" name="real_name">
				<!-- <label for="real_name" class="error" style="position: relative;">22</label> -->
				</td>
				<td class="text-r td-width"><span class="c-red">*</span>登陆账号:</td>
				<td class="td-width-text"><input type="text"  class="input-text" id="user_name"  name="user_name">
					<!-- <label for="user_name" class="error" style="position: relative;"></label> -->
				</td>
			</tr>
			<tr class="text-l">
				<td class="text-r td-width"><span class="c-red">*</span>密码:</td>
				<td class="td-width-text">
					<input type="password" class="input-text" id="password"  name="password">
					<!-- <label for="password" class="error" style="position: relative;"></label> -->
				</td>
				<td class="text-r td-width"><span class="c-red">*</span>确认密码:</td>
				<td class="td-width-text">
					<input type="password" class="input-text" id="confirmPwd"  name="confirmPwd">
					<!-- <label for="confirmPwd" class="error" style="position: relative;"></label> -->
				</td>
			</tr>
			<tr class="text-l">
				<td class="text-r td-width"><span class="c-red">*</span>部门:</td>
				<td class="td-width-text">
				<input type="text" class="input-text" readonly="readonly"   style="width:80%;" id="org_name"  name="org_name">
					<input type="hidden" id="org_id" name="org_id"/>
					<button class="btn btn-primary" type="button" id="selectOrgBtn" style="width:18%;">选择</button>
					<!-- <label for="org_name" class="error" style="position: relative;"></label> -->
				</td>
				<td class="text-r td-width">电子邮箱:</td>
				<td class="td-width-text">
					<input type="text" class="input-text" id="email" name="email">
					<!-- <label for="email" class="error" style="position: relative;"></label> -->
				</td>
			</tr>
			<tr class="text-l">
				<td class="text-r td-width">移动电话:</td>
				<td class="td-width-text">
					<input type="text" class="input-text" id="mobile" name="mobile">
					<!-- <label for="mobile" class="error" style="position: relative;"></label> -->
				</td>
				<td class="text-r td-width">固定电话:</td>
				<td class="td-width-text">
					<input type="text" class="input-text" id="tel" name="tel">
					<!-- <label for="tel" class="error" style="position: relative;"></label> -->
				</td>
			</tr>
			<tr class="text-l">
				<td class="text-r td-width">性别:</td>
					<td>
						<div class="radio-box">
							<input type="radio" id="sex-1" name="_sex" value="0" checked>
							<label for="sex-1">男</label>
						</div>
						<div class="radio-box">
							<input type="radio" id="sex-2" name="_sex" value="1">
							<label for="sex-2">女</label>
						</div>
					</td>
					<td class="text-r td-width">职务:</td>
					<td class="td-width-text">
						<input type="text" class="input-text" id="position" name="position">
					</td>
			</tr>
			<tr class="text-l">
				<td class="text-r td-width">住址:</td>
				<td class="td-width-text"><textarea rows="3" cols="" id="addr" name="addr" class="textarea"></textarea></td>
				<td class="text-r td-width">用户说明:</td>
				<td class="td-width-text"><textarea rows="3" cols="" id="remark" name="remark" class="textarea"></textarea></td>
			</tr>
			
			<tr class="text-l">
				<td class="text-r td-width">是否为管理员:</td>
				<td colspan="3">
					<div class="radio-box">
						<input  type="radio" id="isAdminY" name="_admin_type" value="2">
						<label for="isAdminY">是</label>
					</div>
					<div class="radio-box">
						<input type="radio" id="isAdminN" name="_admin_type" checked value="0">
						<label for="isAdminN">否</label>
					</div>
				</td>
			</tr>
			<tr class="text-c">
				<td colspan="4">
					<input type="hidden" name="user_id" id="user_id" value="${data.user_id }"/>
					<button class="btn btn-primary" type="button" id="saveUserBtn">保存</button>
					<button class="btn btn-default cancelBtn" id="closeUserLayerBtn" type="button">关闭</button>
				</td>
			</tr>
		</tbody>
	</table>
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

<script type="text/javascript" src="<%=path %>/lib/jquery-easyui-1.5.1/jquery.easyui.min.js"></script>

<script type="text/javascript" src="<%=path %>/lib/jquery.validation/1.14.0/jquery.validate.js"></script> 
<script type="text/javascript" src="<%=path %>/lib/jquery.validation/1.14.0/validate-methods.js"></script> 
<script type="text/javascript" src="<%=path %>/lib/jquery.validation/1.14.0/messages_zh.js"></script>


<script type="text/javascript" src="<%=path %>/js/util/util.js" ></script>
<script type="text/javascript" src="<%=path %>/js/custome/user/user-add-modify.js"></script>

</body>

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
</html>