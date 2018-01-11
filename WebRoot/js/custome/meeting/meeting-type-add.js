;(function(rootPath, $, window, document, undefined){
	//选择会议管理员弹出框
	var showManagerLayer = function() {
		layer.open({
			type: 1,
			title: '选择会议管理员',
			content: $("#selectManagerLayer"),
			btn:['确定','取消'],
			area: ['550px', '450px'],
			maxmin: false, 
			move: true,
			success:function(){
				//树没生成
				if ($("#orgTree").children().length == 0) {
					createTree();
				}
			},
			yes:function(index) {
				if($('#dealerselect option:selected').index() < 0)
				{
					layer.alert("请选择会议管理员");
				}
				else
				{
					$("#manager_name").val($('#dealerselect option:selected').text());
					$("#manager_id").val($('#dealerselect option:selected').val());
					layer.close(index);
				}
			}
		});
		
	}
	
	function zTreeOnClick(event, treeId, treeNode) {
		getUserList();
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
	
	//创建组织架构树
	function createTree() {
	   var zNodes;
	   $.ajax({
	       url: rootPath + '/orginfo/background_showAllOrgTree.action', 
	       data: {
	       	
	       },
	       type: 'POST',
	       dataType: "text", // 可以是text，如果用text，返回的结果为字符串；如果需要json格式的，可是设置为json
	       ContentType: "application/json; charset=utf-8",
	       success: function(data) {
	           zNodes = data;

	           $.fn.zTree.init($("#orgTree"), setting, eval('(' + zNodes + ')'));
	           var treeObj = $.fn.zTree.getZTreeObj("orgTree");
	           treeObj.expandAll(true);
	           var node = treeObj.getNodesByFilter(function (node) { return node.level == 0 }, true);
	           treeObj.selectNode(node);
	           getUserList();
	       },
	       error: function(msg) {
	           layer.alert("获取组织架构失败");
	       }
	   });
	}
	
	function getUserList()
	{
		var nodes=getSelectNode();// 获取选中的节点
		if(!nodes){return;}
		var orgId=nodes[0].id;
		var search=$("#keywords").val();
		var includeChild=0;// 默认不选中子部门
		if ($('#includeChild').is(':checked')) {
			includeChild=1;
		}
		var data={orgId:orgId,search:search,includeChild:includeChild}
		var succeed = function (result) {
	        if (0 == result.status) {
	        	var rows=result.rows;
	        	var html="";
	        	 $.each(rows, function (index, item) {    
	                 // 循环获取数据 ,需要匹配选中的数据，看是否有重复的
	        		 var flag=false;
	        		 var userId = item.user_id;   
	                 var realName = item.real_name; 
	                 var userName = item.user_name;
	                 var orgName = item.org_name;
	                 html+="<option value='"+userId+"'>"+ realName +  "</option>"; 
	             });
	        	 $("#dealerselect").html(html);
	        } 
	        else{
	        	layer.alert(result.message);
	        }
	    };
	    $.post(getRootPath() + "/userinfo/background_showUserInfo.action",data,succeed, "json");
	}
	
	//获取被选择的节点
	function getSelectNode()
	{
		var treeObj = $.fn.zTree.getZTreeObj("orgTree");
		var nodes=treeObj.getSelectedNodes(); 
	    return nodes;
	}
	
	//清除查找
	 function clearSearch(){
			$("#keywords").val("");
			getUserList();
		}
	 
	 var showTips=function(message,tipsPos)
		{
			layer.tips(message, tipsPos, {
				tips: [2, '#AE81FF']
			});
		}
	 
	 //检查会议类型编号是否唯一
	 var checkIdentifierValidate = function(identifier, fun) {
		 var originalIdentifier = $("#originalIdentifier").val();
		 if ((!originalIdentifier && identifier) ||(identifier && originalIdentifier && identifier != originalIdentifier)) {
			 $.ajax({
				 url: rootPath + "/meetingType/getMeetingType",
				 type: 'POST',
				 dataType: 'json', 
				 data: {
					 identifier: identifier
				 },
				 async: false,
				 success: function(data) {
					 if (data.status == 0 && data.total > 0) {
						 showTips("会议类型编号已存在", "#identifier");
						 if (fun) {
							 fun(false);
						 }
					 }
				 }
			 });
		 }
	 }
	
	$(function() {
		$("#selectManagerBtn").click(function() {
			showManagerLayer();
		});
		$("#includeChild,#findBtn").click(function() {
			getUserList();
		});
		$("#clearBtn").click(function() {
			clearSearch();
		});
		//失去焦点
		$("#identifier").blur(function() {
			checkIdentifierValidate($(this).val());
		});
		$("#saveBtn").click(function() {
			if (!validate_notNull( $("#identifier"),"#identifier","会议类型编号不能为空")) {
				return false;
			}
			if (!validate_notNull( $("#name"),"#name","会议类型名称不能为空")) {
				return false;
			}
			var validate = true;
			checkIdentifierValidate($("#identifier").val(), function(result) {
				validate = result;
			});
			if (!validate) {
				return false;
			}
			var order = $("#order").val();
			var orderReg= /^\+?[1-9][0-9]*$/g;　　//判断是否为正整数 
			if($.trim(order)!=''&&!orderReg.test(order))
			{
				showTips("排序只能输入正整数","#order");
				return false;
			}
			$.ajax({
				url: rootPath + "/meetingType/addorModifyMeetingType",
				dataType:'json',
				type:'POST',
				data:{
					id: $("#id").val(),
					name: $("#name").val(),
					identifier: $("#identifier").val(),
					manager_id: $("#manager_id").val(),
					order: $("#order").val(),
					comment: $("#comment").val()
				},
				success: function(result) {
					if (result.status == 0) {
						layer.msg("保存成功", {time:1000});
						goBack();
					}
					else {
						layer.msg(result.message, {time:1000});
					}
				}
			});
		});
		
		// 退出按钮点击事件
		$("#exitBtn").click(function(){
			layer.confirm('确定退出编辑？', {
				  btn: ['确定','取消'] // 按钮
				}, function(){
					goBack();
				}, function(){
					// 取消没有事件
				});
		});
		
	});
	
	function goBack()
	{
		window.location.href=rootPath + "/backToUrl/toDo.action?url=meetingTypeManage";
	}
	
})(rootPath, jQuery, window, document);




















