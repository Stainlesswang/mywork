/**********************用户新增修改js***************************/
var selectOrgLayerIndex=null;
var state="add";

function getSelectNode()
{
	var treeObj = $.fn.zTree.getZTreeObj("selectOrgTree");
	var nodes=treeObj.getSelectedNodes(); 
//    console.log(JSON.stringify(nodes));
    return nodes;
}
function zTreeOnClick(event, treeId, treeNode) {
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
    	isCreate=true;
        var zNodes;
        $.ajax({
            url: getRootPath()+'/orginfo/background_showAllOrgTree.action', //url  action是方法的名称
            data: { },
            type: 'POST',
            dataType: "text", //可以是text，如果用text，返回的结果为字符串；如果需要json格式的，可是设置为json
            ContentType: "application/json; charset=utf-8",
            success: function(data) {
                zNodes = data;

                $.fn.zTree.init($("#selectOrgTree"), setting, eval('(' + zNodes + ')'));
                var treeObj = $.fn.zTree.getZTreeObj("selectOrgTree");
                treeObj.expandAll(true);
            },
            error: function(msg) {
                alert("失败");
            }
        });
    }

$(function(){
	

	
	var getUserDetail=function()
	{
		var user_id=$("#user_id").val();
		if($.trim(user_id)!='')
		{
			state="update";
			//获取用户信息
			var data={user_id:user_id};
			var succeed = function (result) {
	            if (0 == result.status) {
	            	var userInfo=result.data;
					for ( var x in userInfo) {
						if(x=='sex'||x=='admin_type')
						{
							$("input[name='_"+x+"'][value='"+userInfo[x]+"']").attr("checked",true);
						}
						else if (x=='is_temp') {
							$("input[name='is_temp'][value='"+userInfo[x]+"']").attr("checked", true);
						}
						else
						{
							if(x=='password')
							{
								$("#confirmPwd").val(userInfo[x]);
							}
							$("#" + x).val(userInfo[x]);
						}
//						else if(x!='password')
//						{
//							$("#" + x).val(userInfo[x]);
//						}
					}
					$("#_user_name").show();
					$("#_user_name").html(userInfo.user_name);
					$("#user_name").attr("readonly",true);
					$("#user_name").hide();
	            } else {
	                layer.alert(result.message);
	            }
	        };
	        $.post(getRootPath() + "/userinfo/background_getObjInstane.action",
	            data,
	            succeed, "json");
		}
		
		
	}
	
	getUserDetail();
	createTree ();
	//显示选择组织弹出框
	var showSelectOrgLayer=function()
	{
		selectOrgLayerIndex=layer.open({
			  type: 1,
			  title:'选择组织',
			  content:$("#selectOrgLayer"),
			  area: '400px',
			  maxmin: false,//放大缩小
			  move:true //是否允许拖拽
		});
		var currOrgId=$("#org_id").val();
//		$('#selectOrgTree').tree({
//	        url:getRootPath()+'/orginfo/background_showOrgTree.action?currOrgId='+currOrgId,
//	        checkbox:false,
//	        onBeforeExpand:function(node,param){
//	          var orgId=node.id;
//	      	  $('#selectOrgTree').tree('options').url = getRootPath()+"/orginfo/background_showOrgTree.action?orgId="+orgId+"&currOrgId="+currOrgId;// change the url                       
//	        },               
//	        onClick:function(node){
////		      	var orgId=node.id;
////		      	$("#org_id").val(orgId);
////		      	$("#org_name").val(node.text);
//	        },
//	        onLoadSuccess:function(node,data)
//	        {
//	        	
//	        }
//	     });
		
	}
	
	var showTips=function(message,tipsPos)
	{
		layer.tips(message, tipsPos, {
			tips: [2, '#AE81FF']
		});
	}
	
	//校验数据
	var checkValidate=function()
	{
		var real_name=$("#real_name").val();
		var user_name=$("#user_name").val();
		var password=$("#password").val();
		var confirmPwd=$("#confirmPwd").val();
		var org_id=$("#org_id").val();
		var org_name=$("#org_name").val();
		var email=$("#email").val();
		var mobile=$("#mobile").val();
		var tel=$("#tel").val();
		var flag=false;
//		if(state=='add')
//		{
//			flag=validate_notNull($("#real_name"),"#real_name","姓名不能为空")&&
//			validate_notNull($("#user_name"),"#user_name","登陆账号不能为空")&&
//			validate_notNull($("#password"),"#password","密码不能为空")&&
//			validate_notNull($("#confirmPwd"),"#confirmPwd","确认密码不能为空")&&
//			validate_notNull($("#org_name"),"#org_name","组织不能为空")&&
//			clientCheck_compare($("#password"),$("#confirmPwd"),"#confirmPwd","密码和确认密码相等");
//		}
//		else
//		{
//			flag=validate_notNull($("#real_name"),"#real_name","姓名不能为空")&&
//			validate_notNull($("#user_name"),"#user_name","登陆账号不能为空")&&
//			validate_notNull($("#org_name"),"#org_name","组织不能为空");
//			if($.trim(password)!=''&&flag)//如果密码不为空，就验证密码
//			{
//				flag=validate_notNull($("#confirmPwd"),"#confirmPwd","确认密码不能为空")&&
//				clientCheck_compare($("#password"),$("#confirmPwd"),"#confirmPwd","密码和确认密码相等");
//			}
//		}
		flag=validate_notNull($("#real_name"),"#real_name","姓名不能为空")&&
		validate_notNull($("#user_name"),"#user_name","登陆账号不能为空")&&
		validate_notNull($("#password"),"#password","密码不能为空")&&
		validate_notNull($("#confirmPwd"),"#confirmPwd","确认密码不能为空")&&
		validate_notNull($("#org_name"),"#org_name","组织不能为空")&&
		clientCheck_compare($("#password"),$("#confirmPwd"),"#confirmPwd","密码和确认密码相等");
		
		if(flag)
		{
			var unameReg=/^[a-zA-Z0-9_-]{3,16}$/;
			if(!unameReg.test(user_name))
			{
				showTips("3-16位，字母、数字和下划线。","#user_name");
				return false;
			}
			
			var emailReg=/^[a-zA-Z0-9.!#$%&'*+\/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$/;
			if($.trim(email)!=''&&!emailReg.test(email))
			{
				showTips("邮箱格式不正确","#email");
				return false;
			}
			var mobileReg=/^(((13[0-9]{1})|(15[0-35-9]{1})|(17[0-9]{1})|(18[0-9]{1}))+\d{8})$/;
			if($.trim(mobile)!=''&&!mobileReg.test(mobile))
			{
				showTips("手机号格式不正确","#mobile");
				return false;
			}
			
			var telReg=/^(\d{3,4}-?)?\d{7,9}$/g;
			if($.trim(tel)!=''&&!telReg.test(tel))
			{
				showTips("电话格式不正确","#tel");
				return false;
			}
			return true;
		}
		
		return false;
		
	}
	
	//保存用户信息
	var saveUserInfo=function()
	{
//		$("#form-user-add").submit();
		//原始的使用validate验证，提示的错误信息会偏移，故改为手动验证
		if(checkValidate())
		{
			//这边应该校验一下用户名是否存在
			var user_id=$("#user_id").val();
			var user_name=$("#user_name").val();
			var _data={user_id:user_id,user_name:user_name};
			var succeed = function (result) {
	            if (0 == result.status) {
	            	
	            	//保存数据 start
	            	var url=getRootPath()+"/userinfo/background_addUserInfo.action";
	    			if(state=='update')
	    			{
	    				url=getRootPath()+"/userinfo/background_modUserInfo.action";
	    			}
	    			//获取性别、管理员数据
	    			var sex= $("[name=_sex]:checked").val();
	    			var admin_type= $("[name=_admin_type]:checked").val();
	    			$("#form-user-add").ajaxSubmit({  
	    		        type: "post",//提交类型  
	    		        dataType: "json",//返回结果格式  
	    		        url: url,//请求地址  
	    		        data: {sex:sex,admin_type:admin_type},//请求数据  
	    		        success: function (data) {//请求成功后的函数  
	    		        	if(data.status==0){
	    		        		layer.msg('保存成功', {
	    		        			  time: 1000 //1秒关闭（如果不配置，默认是3秒）
	    		        			}, function(){
	    		        				var index = parent.layer.getFrameIndex(window.name);
	    		        				parent.$('#datatable').bootstrapTable("refresh");
			    						parent.layer.close(index);
	    		        			}); 
	    		    		}else{
	    		    			layer.alert(data.message);
	    		    		}
	    		        },  
	    		        error: function (data) { alert("操作失败"); },//请求失败的函数  
	    		        async: true  
	    			});
	            	//保存数据 end
	            	
					
	            } else {
//	                layer.alert(result.message);
	            	showTips(result.message,"#user_name");
	            }
	        };
	        $.post(getRootPath() + "/userinfo/background_isExistName.action",
	            _data,
	            succeed, "json");
			
			
			
			
		}
	}
	
	//绑定事件
	var bindEvent=function()
	{
		$("#closeSelectOrgLayerBtn").click(function(){//选择组织弹出框中取消按钮
			 layer.close(selectOrgLayerIndex);
		 });
		 $("#selectOrgBtn").click(showSelectOrgLayer);//选择组织按钮
		 $("#closeUserLayerBtn").click(function(){//关闭用户弹出窗口
			 var index = parent.layer.getFrameIndex(window.name);
			 //parent.$('.btn-refresh').click();
			 parent.layer.close(index);
		 });
		 
		 $("#saveUserBtn").click(saveUserInfo);//保存用户信息按钮
		 
		 $("#confirmOrgBtn").click(function(){//选择组织弹出框中确定按钮
			 var selected = getSelectNode();
			 if(!selected){
				layer.alert("请选择组织"); 
			 }
			 else{
				 $("#org_id").val(selected[0].id);
				 $("#org_name").val(selected[0].name);
				 layer.close(selectOrgLayerIndex);
			 }
		 });
	}
	
	bindEvent();
});































