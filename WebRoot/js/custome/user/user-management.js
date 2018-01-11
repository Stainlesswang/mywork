/****************************用户管理js******************************/
var orgLayerIndex=null;//当前组织弹出框index
var selectOrgLayerIndex=null;//当前选择组织弹出框index
var importLayerIndex=null;//导入excel弹出框index
var isSameLevel=false;//是否是同级

function bootstrapRefresh()
{
	$('#datatable').bootstrapTable('refresh').bootstrapTable('refreshOptions', {pageNumber: 1});
}

$(function(){
	var table = $('#datatable');
	table.bootstrapTable({
		method : 'post',//请求action。建议换成post
		url : getRootPath()+"/userinfo/background_showUserInfo.action",
		sidePagination : "server",
		showRefresh : false,
		search: false,
		pagination : true,
		pageSize : 10,
		pageList : [ 10, 50, 100 ],
		rowStyle : function(row,index){
			var classes = ['active', 'success', 'info', 'warning', 'danger'];
			if(row.admin_type==2||row.is_super==1)
			{
				return {
	                classes:'danger'
	            };
			}
			return {};
		},
//		toolbar : '#toolbar',
		queryParamsType : "limit",
		queryParams: function (params) {
            params["orgId"] = $("#choose_orgId").val();
            params["search"] = $("#keywords").val();
            var includeChild=0;//默认不选中子部门
        	if ($('#includeChild').is(':checked')) {
        		includeChild=1;
        	}
            params["includeChild"]=includeChild;
            return params;
        },
		columns : [
				{
					field : 'user_id',
					title : 'user_id',
					visible : false,
				},
				{
				    checkbox: true,
				    width :'25px',
				},
				{
					field : 'index',
					title : '序号',
					width :'50px',
					formatter : function(value, row, index) {
						return index+1;
					}
				},
				{
					field : 'user_name',
					title : '账号',
					visible : true,
					formatter : function(value, row, index) {
						if(row.admin_type==2||row.is_super==1)
						{
							return '<span style="font-weight: bolder;">'+value+'</span>';
						}
						return value;
					}
				},
				{
					field : 'real_name',
					title : '姓名',
					visible : true,
					formatter : function(value, row, index) {
						if(row.admin_type==2||row.is_super==1)
						{
							return '<span style="font-weight: bolder;">'+value+'</span>';
						}
						return value;
					}
				},
				{
					field : 'sex',
					title : '性别',
					visible : true,
					formatter : function(value, row, index) {
						var sex=value;
						if(value==0){sex= "男";}
						else if(value==1){sex= "女";}
						else {sex= "保密";}
						if(row.admin_type==2||row.is_super==1)
						{
							return '<span style="font-weight: bolder;">'+sex+'</span>';
						}
						return sex;
					}
//					sortable:true
				},
				{
					field : 'position',
					title : '职务',
					visible : true,
					formatter : function(value, row, index) {
						if(row.admin_type==2||row.is_super==1)
						{
							return '<span style="font-weight: bolder;">'+value+'</span>';
						}
						return value;
					}
				},
				{
					field : 'org_name',
					title : '所属部门',
					visible : true,
					formatter : function(value, row, index) {
						if(row.admin_type==2||row.is_super==1)
						{
							return '<span style="font-weight: bolder;">'+value+'</span>';
						}
						return value;
					}
				},
				{
					field : 'mobile',
					title : '手机',
					visible : true,
					formatter : function(value, row, index) {
						if(row.admin_type==2||row.is_super==1)
						{
							return '<span style="font-weight: bolder;">'+value+'</span>';
						}
						return value;
					}
				},
				{
					field : 'email',
					title : '邮箱',
					visible : true,
					formatter : function(value, row, index) {
						if(row.admin_type==2||row.is_super==1)
						{
							return '<span style="font-weight: bolder;">'+value+'</span>';
						}
						return value;
					}
				}
			]
	});
	
	var reloadWindow=function()
	{
		window.location.reload();
	}
	
	var createOrgTree=function()
	{
		$('#orgTree').tree({
	        url:getRootPath()+'/orginfo/background_showOrgTree.action',
	        checkbox:false,
	        onBeforeExpand:function(node,param){
	          var orgId=node.id;
//	          console.log("orgId:"+orgId);
	      	  $('#orgTree').tree('options').url = getRootPath()+"/orginfo/background_showOrgTree.action?orgId="+orgId;// change the url                       
	      	   //param.myattr = 'test';    // or change request parameter
	      	   
	        },               
	        onClick:function(node){
	          //alert(node.attributes);
		      	var orgId=node.id;
//		      	console.log("orgId2:"+orgId);
		      	$("#choose_orgId").val(orgId);
		      	bootstrapRefresh();
//		      	table.bootstrapTable("refresh");
//		      	var includeChild=0;//默认不选中子部门
//            	if ($('#includeChild').is(':checked')) {
//            		includeChild=1;
//            	}
//		      	table.bootstrapTable('refreshOptions', {pageNumber: 1,
//		      		orgId:$("#choose_orgId").val(),
//		      		search:$("#keywords").val(),
//		      		includeChild:includeChild
//		      	});
//		      	$('#datatable').bootstrapTable("refresh").bootstrapTable({
//		    		queryParams: function (params){
//		    			console.log(JSON.stringify(params));
//		    			params["orgId"] = $("#choose_orgId").val();
//		    			params["offset"] = 0;
//		                params["search"] = $("#keywords").val();
//		                var includeChild=0;//默认不选中子部门
//		            	if ($('#includeChild').is(':checked')) {
//		            		includeChild=1;
//		            	}
//		                params["includeChild"]=includeChild;
//		                return params;
//		    	  	}
//		    	});
	        },
	        onLoadSuccess:function(node,data)
	        {
//	        	console.log(JSON.stringify(data));
	        	var selected = $('#orgTree').tree('getSelected');
	        	if(!selected)
	        	{
	        		var root=$('#orgTree').tree("getRoot");
	            	var node2 = $('#orgTree').tree('find', root.id);
	            	$('#orgTree').tree('select', node2.target);
	            	$("#choose_orgId").val(root.id);
	            	table.bootstrapTable("refresh");
	        	}
	        	
	        }
//	        onContextMenu: function(e, node){  
//	                      e.preventDefault();  
//	                      $('#tree').tree('select', node.target);  
//	                      $('#mm').menu('show', {  
//	                          left: e.pageX,  
//	                          top: e.pageY  
//	                      });  
//	                  }  
	     });
	}
	
	createOrgTree();
	
	var treeReloadRoot=function()
	{
		var root = $('#orgTree').tree('getRoot');
		if(root)
		{
			$('#orgTree').tree("reload",root.target);
		}
		
	}
	
	var treeReloadCurr=function()
	{
		var selected = $('#orgTree').tree('getSelected');
		$('#orgTree').tree("reload",selected.target);
	}
	
	var treeReloadParent=function()
	{
		var selected = $('#orgTree').tree('getSelected');
		var parentNode=$('#orgTree').tree('getParent',selected.target);
		if(parentNode)
		{
			$('#orgTree').tree("reload",parentNode.target);
		}
		else
		{
			createOrgTree();
		}
	}
	var treeRemove=function()
	{
		var selected = $('#orgTree').tree('getSelected');
		$('#orgTree').tree("remove",selected.target);
	}
	
	var resetOrgForm=function()
	{
		
		$("#res").click(); 
		var validator=$("#form-add-modify-org").validate();
		validator.resetForm();
		//貌似隐藏域的值清不了，手动清理
		$("#form-add-modify-org").find("input,select,textarea").each(function(){
			$(this).val("");
		});
	}
	
	/**
	 * 弹出新增组织弹出窗口
	 */
	var showAddOrgLayer=function(type)
	{
		orgLayerIndex=layer.open({
			  type: 1,
			  title:'组织信息操作',
			  content:$("#addOrModifyOrgLayer"),
//			  content: getRootPath()+"/orginfo/background_orgAddOrModifyView.action",
			  area: ['700px', '380px'],
			  maxmin: false,//放大缩小
			  move:true //是否允许拖拽
		});
	}
	/**
	 * 同级组织
	 */
	var showAddSameLevelOrgLayer=function()
	{
		//$("input[name='res']").click(); 
		resetOrgForm();
		isSameLevel=true;
		var selected = $('#orgTree').tree('getSelected');
		if(!selected)
		{
			alert("请选中组织");
			return;
		}
		var pnode = $('#orgTree').tree('getParent',selected.target);
		if(!pnode)
		{
			alert("公司等级不能新增同级");
			return;
		}
		var parent_id=pnode.id;
		var parent_name=pnode.text;
//		console.log(JSON.stringify(pnode));
		$("#parent_id").val(parent_id);
		$("#parent_name").val(parent_name);
		$("#selectOrgBtn").hide();
		showAddOrgLayer(1);
	}
	/**
	 * 下级组织
	 */
	var showAddLowerLevelOrgLayer=function()
	{
		resetOrgForm();
		isSameLevel=false;
		var selected = $('#orgTree').tree('getSelected');
		if(!selected)
		{
			layer.alert("请选中组织");
			return;
		}
		var parent_id=selected.id;
		var parent_name=selected.text;
		$("#parent_id").val(parent_id);
		$("#parent_name").val(parent_name);
		$("#selectOrgBtn").hide();
		showAddOrgLayer(2);
	}
	
	
	/**
	 * 显示修改组织弹出窗口
	 */
	var showEditOrgLayer=function()
	{
		resetOrgForm();
		isSameLevel=true;
		var selected = $('#orgTree').tree('getSelected');
		if(!selected)
		{
			layer.alert("请选中要编辑的组织");
			return;
		}
		var org_id=selected.id;
		
		var data={org_id:org_id};
		var succeed = function (result) {
            if (0 == result.status) {
            	$("#org_id").val(org_id);
            	$("#org_name").val(result.data.org_name);
            	$("#parent_id").val(result.data.parent_id);
            	$("#oldPOrgId").val(result.data.parent_id);//20170512
    			$("#parent_name").val(result.data.parent_name);
    			$("#remark").val(result.data.remark);
    			var root=$('#orgTree').tree("getRoot");
    			if(root)
    			{
    				if(root.id!=org_id)
    				{
    					$("#selectOrgBtn").show();
    				}
    				else
    				{
    					$("#selectOrgBtn").hide();
    				}
                	showAddOrgLayer(0);
    			}
    			
    			
            } else {
                layer.alert(result.message);
            }
        };
        $.post(getRootPath() + "/orginfo/background_getObjInstane.action",
            data,
            succeed, "json");
		
//		$("#parent_id").rules("remove");
		
	}
	
	//删除组织，即注销
	var delOrgInfo=function()
	{
		//删除组织信息
		var root=$('#orgTree').tree("getRoot");
		var selected = $('#orgTree').tree('getSelected');
		if(!selected)
		{
			layer.alert("请选中要删除的组织");
			return;
		}
		if(root.id==selected.id)
		{
			layer.alert("公司等级不能删除");
			return;
		}
		var idsArr=new Array();
		idsArr.push(selected.id);
		layer.confirm('确定删除该组织以及下级组织？', {
			  btn: ['确定','取消'] //按钮
			}, function(){
				var data={ids:JSON.stringify(idsArr)};
				var succeed = function (result) {
		            if (0 == result.status) {
		            	layer.alert("删除成功");
//		            	treeRemove();
		            	treeReloadParent();
		            } else if(-30005 == result.status){
		                layer.alert("删除失败，该组织下可能有相关人员");
		            }
		            else{
		            	layer.alert(result.message);
		            }
		        };
		        $.post(getRootPath() + "/orginfo/background_delOrgs.action",
		            data,
		            succeed, "json");
				
			}, function(){
				//取消没有事件
			});
	}
	
	//保存组织信息
	var saveOrgInfo=function()
	{
		var validator=$("#form-add-modify-org").validate();
		if(validator.form()){
			var org_id=$("#org_id").val();
			var url="orginfo/background_addOrgInfo.action";
			if(org_id!='')
			{
				url="orginfo/background_modOrgInfo.action";
			}
			$("#form-add-modify-org").ajaxSubmit({  
		        type: "post",//提交类型  
		        dataType: "json",//返回结果格式  
		        url: getRootPath()+"/"+url,//请求地址  
		        data: {},//请求数据  
		        success: function (data) {//请求成功后的函数  
//		        	console.log(JSON.stringify(data));
		        	if(data.status==0){
//		        		$("#parent_name").rules("add",{required:true});
//		        		validator.resetForm();
		        		if(!isSameLevel)//如果不是新增同级
		        		{
		        			treeReloadCurr();
		        		}
		        		else
		        		{
		        			//如果是修改，且原始的上级id已经改变，需要重新加载树
		        			if(org_id!=''&&$("#oldPOrgId").val()!=$('#parent_id').val())
		        			{
		        				treeReloadRoot();//重新加载
		        			}
		        			else
		        			{
		        				treeReloadParent();
		        			}
		        			
		        		}
		        		
		        		
		        		layer.close(orgLayerIndex);
		        		layer.msg('保存成功',{
		        			  time: 2000 //2秒关闭（如果不配置，默认是3秒）
		        			});
		    		}else{
		    			layer.alert(data.message);
//		    			alert(data.message);
		    		}
		        },  
		        error: function (data) { alert("操作失败"); },//请求失败的函数  
		        async: true  
		   
			});
		}
	}
	
	//显示用户弹出框
	var showUserLayer=function(_title,_content)
	{
		var index=layer.open({
			  type: 2,
			  title:_title,
			  content: _content,
			  area: ['800px', '500px'],
			  maxmin: false,//放大缩小
			  move:true, //是否允许拖拽
		});
		layer.full(index);
	}
	var showAddUserLayer=function()
	{
		//获取选中的组织
		var selected = $('#orgTree').tree('getSelected');
		var selectOrgId="";
		var selectOrgName="";
		if(selected)
		{
			selectOrgId=selected.id;
			selectOrgName=selected.text;
		}
		var title="新增用户";
//		var url=getRootPath()+"/userinfo/background_userAddOrModifyView.action?selectOrgId="+selectOrgId+"&selectOrgName="+selectOrgName;
		var url=getRootPath()+"/userinfo/background_userAddOrModifyView.action?selectOrgId="+selectOrgId;
		showUserLayer(title,url);
	}
	//显示修改用户弹出框
	var showModifyUserLayer=function()
	{
		var selects = $('#datatable').bootstrapTable('getSelections'); 
		//alert(JSON.stringify(selects));
		if(selects!=null&&selects.length!=0)
		{
			if(selects.length==1)
			{
				var user_id=selects[0].user_id;
				showUserLayer('编辑用户',getRootPath()+"/userinfo/background_userAddOrModifyView.action?user_id="+user_id);
			}
			else
			{
				layer.alert("请选择一个用户信息进行编辑");
			}
		}
		else
		{
			layer.alert("请选择用户");
		}
		
	}
	
	//显示选择组织弹出框
	var showSelectOrgLayer=function()
	{
		selectOrgLayerIndex=layer.open({
			  type: 1,
			  title:'选择组织',
			  content:$("#selectOrgLayer"),
			  area: ['700px', '480px'],
			  maxmin: false,//放大缩小
			  move:true //是否允许拖拽
		});
		var currOrgId=$("#org_id").val();
		$('#selectOrgTree').tree({
	        url:getRootPath()+'/orginfo/background_showOrgTree.action?currOrgId='+currOrgId,
	        checkbox:false,
	        onBeforeExpand:function(node,param){
	          var orgId=node.id;
	      	  $('#selectOrgTree').tree('options').url = getRootPath()+"/orginfo/background_showOrgTree.action?orgId="+orgId+"&currOrgId="+currOrgId;// change the url                       
	        },               
	        onClick:function(node){
//		      	var orgId=node.id;
//		      	$("#parent_id").val(orgId);
//		      	$("#parent_name").val(node.text);
	        },
	        onLoadSuccess:function(node,data)
	        {
	        	
	        }
	     });
	}
	
	/**
	 *删除用户 
	 */
	var delUserInfo=function()
	{
		var selects = $('#datatable').bootstrapTable('getSelections'); 
		if(selects!=null&&selects.length!=0)
		{
			var idsArr=new Array();
			for(i=0;i<selects.length;i++)
			{
				if(selects[i].is_super==1)
				{
					layer.alert("含有超级管理员，不能被删除！");
					return;
				}
				idsArr.push(selects[i].user_id);
			}
			layer.confirm('确定删除所选用户？', {
				  btn: ['确定','取消'] //按钮
				}, function(){
					var data={ids:JSON.stringify(idsArr)};
					var succeed = function (result) {
			            if (0 == result.status) {
			            	layer.msg("删除成功",{time:2000});
			            	bootstrapRefresh();
			            } 
			            else{
			            	layer.alert(result.message);
			            }
			        };
			        $.post(getRootPath() + "/userinfo/background_delUsers.action",data,succeed, "json");
					
				}, function(){
					//取消没有事件
				});
			
		}
		else
		{
			layer.alert("请选择需要删除的用户");
		}
	}
	
	/**
	 * 导入Excel弹出窗口
	 */
	var showImportLayer=function()
	{
		var file = $("#uploadFile") 
		file.after(file.clone().val("")); 
		file.remove(); 
		var index=layer.open({
			  type: 2,
			  title:"导入Excel",
			  content:getRootPath()+"/userinfo/background_importUserView.action",
			  area: ['600px', '300px'],
			  maxmin: false,//放大缩小
			  move:true, //是否允许拖拽
		});
		layer.full(index);
	}
	
	
	//绑定事件
	var bindEvent=function()
	{
		 $("#addSameLevelBtn").click(showAddSameLevelOrgLayer);//新增同级按钮
		 $("#addLowerLevelBtn").click(showAddLowerLevelOrgLayer);//新增下级按钮
		 $("#orgEditBtn").click(showEditOrgLayer);//编辑组织按钮
		 $("#orgDelBtn").click(delOrgInfo);//删除组织按钮
		 $("#closeOrgLayerBtn").click(function(){//新增修改组织弹出框中取消按钮
			 layer.close(orgLayerIndex);
		 });
		 $("#closeSelectOrgLayerBtn").click(function(){//选择组织弹出框中取消按钮
			 layer.close(selectOrgLayerIndex);
		 });
		 $("#confirmOrgBtn").click(function(){//选择组织弹出框中确定按钮
			 var selected = $('#selectOrgTree').tree('getSelected');
			 if(!selected){
				layer.alert("请选择上级组织"); 
			 }
			 else{
				 $("#parent_id").val(selected.id);
			     $("#parent_name").val(selected.text);
				 layer.close(selectOrgLayerIndex);
			 }
		 });
		 $("#saveOrgBtn").click(saveOrgInfo);//保存组织按钮
		 
		 $("#addUserBtn").click(showAddUserLayer);//新增用户按钮
		 $("#modUserBtn").click(showModifyUserLayer);//编辑用户按钮
		 $("#delUserBtn").click(delUserInfo);//删除用户按钮
		 $("#selectOrgBtn").click(showSelectOrgLayer);//选择组织按钮
		 $("#dowloadBtn").click(function(){
			 window.location.href=getRootPath()+"/fileUtil/downloadUserTemplate.action?filePath=uploadFile/template/userTemplate.xls";
		 });//模板下载
		 $("#importExcelBtn").click(showImportLayer);//导入excel按钮
		 $("#includeChild").change(function() {
			 bootstrapRefresh();
		 });
		 $("#keywords").keydown(function(event) {    
             if (event.keyCode == 13) {    
            	 bootstrapRefresh();    
             }    
         })    
	}
	
	bindEvent();
	
	
	
});































