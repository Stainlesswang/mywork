/******************************设备新增、修改js************************************/
var selectUserLayerIndex=null;
var state="add";

function indexFormatter(value, row, index) {
    return index + 1;
}
//选择用户formatter
var selectHtmlFormatter=function(value,row,index)
{
	return "<a class='text-primary userHtml' style='cursor:pointer;color:#0D8BBB;'>选中</a>";
}
//查询用户表的参数
function queryParam_user(params){
	var nodes=getSelectNode();
	if(!nodes){return params;}
	var orgId=nodes[0].id;
	params["orgId"] = orgId;
	var search=$("#keywords").val();
	var includeChild=0;//默认不选中子部门
	if ($('#includeChild').is(':checked')) {
		includeChild=1;
	}
	params["search"] = search;
	params["includeChild"] = includeChild;
//	console.log(JSON.stringify(params));
	return params;
}

//选择用户
var selectUser = function(e, value, row, index){
//	console.log(JSON.stringify(row));
	var user_id=row.user_id;
	var user_name=row.user_name;
	var real_name=row.real_name;
	$("#user_id").val(user_id);
	$("#real_name").val(real_name);
	$("#job_number").val(user_name);
	$("#passwd").val(row.password);
	layer.close(selectUserLayerIndex);
}

var selectUserEvents = {"click .userHtml" : selectUser};

//用户表刷新
function userTableRefesh()
{
	$("#userTable").bootstrapTable("destroy").bootstrapTable({
		queryParams: function (params){
	//	    	params["colors"] = addColors;
			var nodes=getSelectNode();
			if(!nodes){return params;}
			var orgId=nodes[0].id;
			params["orgId"] = orgId;
			var search=$("#keywords").val();
			var includeChild=0;//默认不选中子部门
			if ($('#includeChild').is(':checked')) {
				includeChild=1;
			}
			params["search"] = search;
			params["includeChild"] = includeChild;
	    	return params;
	  	}
	});
//	$("#userTable").bootstrapTable("refresh");
}

//获取选中的节点
function getSelectNode()
{
	var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
	if(treeObj==null)
	{
		return null;
	}
	var nodes=treeObj.getSelectedNodes(); 
    return nodes;
}
//节点点击事件
function zTreeOnClick(event, treeId, treeNode) {
	userTableRefesh();
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
//创建树形组织     
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

                $.fn.zTree.init($("#treeDemo"), setting, eval('(' + zNodes + ')'));
                var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
                treeObj.expandAll(true);
                var node = treeObj.getNodesByFilter(function (node) { return node.level == 0 }, true);
                treeObj.selectNode(node);
                userTableRefesh();
            },
            error: function(msg) {
                alert("失败");
            }
        });
    }

//获取授权码列表
function getAuthCodeList()
{
	var data={};
	var succeed = function (result) {
        if (0 == result.status) {
        	//生成下拉菜单
        	 var codeList=result.rows;
        	 var html='';
        	 if(codeList.length>0)
        	 {
        		 for(i=0;i<codeList.length;i++)
    	  		 {
    	  			  html+='<option value="'+codeList[i].authcode+'">'+codeList[i].authcode+'</option>';
    	  		 } 
        	 }
        	 $("#authorationcode").append(html);
	  		 
        } else {
        	layer.alert(result.message);
        }
  };
  $.post(getRootPath()+"/authcode/background_showNoUseCode.action",data,succeed, "json");
	
}

//获取设备编码列表
function getDeviceCodeList()
{
	
	$.ajax({
		url: getRootPath() + "/device/getDeviceCodeList",
		data: {},
		type: 'POST',
		dataType: 'json',
		success: function (data) {
			if (data.status == 0) {
				var deviceCodeList = data.data;
				var html='';
				 for(i=0;i<deviceCodeList.length;i++)
    	  		 {
    	  			  html+='<option value="'+deviceCodeList[i].deviceCode+'">'+deviceCodeList[i].deviceName + ":" + deviceCodeList[i].deviceCode+'</option>';
    	  		 } 
				 $("#deviceCode").append(html);
			}
		}
	});
}

//获取设备详情
function getDeviceDetail()
{
	var mid=$("#mid").val();
	if($.trim(mid)!='')
	{
		state="update";
		//根据id获取会议详情
		var data={mid:mid};
		var succeed = function (result) {
            if (0 == result.status) {
            	var deivceInfo=result.data;
//            	console.log(JSON.stringify(deivceInfo));
				for ( var x in deivceInfo) {
					if(x=='create_time')
					{
						$("#_" + x).html(deivceInfo[x]);
						$("#" + x).val(deivceInfo[x]);
					}
					else if(x=='register')
					{
						$("#_" + x).html(deivceInfo[x]);
					}
					else if(x=='authorationcode'&&deivceInfo[x]!=null&&deivceInfo[x]!='')
					{
//						console.log(deivceInfo[x]);
//						$("#" + x).prepend('<option value="'+deivceInfo[x]+'" selected="selected">'+deivceInfo[x]+'</option');
						$("#" + x).append('<option value="'+deivceInfo[x]+'" selected="selected">'+deivceInfo[x]+'</option');
					}
					else if(x=='energy')
					{
						$("#" + x).html(deivceInfo[x] ? deivceInfo[x] + "%" : "");
					}
					else if (x == 'ipad_uuid')
					{
						$('#device').html('<span id="_ipad_uuid">' + (deivceInfo[x] || '') + '</span>');
					}
					else
					{
						$("#" + x).val(deivceInfo[x]);
					}
					
				}
				
				
				
            } else {
                layer.alert(result.message);
            }
        };
        $.post(getRootPath() + "/subAppUser/background_getObjInstane.action",data,succeed, "json");
		
	}
}

//清除设备绑定
function clearDeviceBindInfo()
{
	var mid=$("#mid").val();
	var idsArr=new Array();
	idsArr.push(mid);
	if($.trim(mid)!='')
	{
		layer.confirm('确定清除设备绑定？', {
		  btn: ['确定','取消'] //按钮
		}, function(){
			var data={ids:JSON.stringify(idsArr)};
			
			var succeed = function (result) {
	            if (0 == result.status) {
	            	layer.msg('清除成功', {
	        			  time: 1000 //1秒关闭（如果不配置，默认是3秒）
	        			}, function(){
	        				goBack();
	        			});
	            }
	            else
	            {
	            	layer.alert(result.message);
	            }
	        };
	        $.post(getRootPath() + "/subAppUser/background_clearSubAppUserByIds.action",data,succeed, "json");
		}, function(){
			//取消没有事件
		});
	}
	else
	{
		layer.alert("无效的请求");
	}
}

//删除
function delDeviceBindInfo()
{
	var mid=$("#mid").val();
	var idsArr=new Array();
	idsArr.push(mid);
	if($.trim(mid)!='')
	{
		var data={ids:JSON.stringify(idsArr)};
		layer.confirm('确定删除设备绑定？', {
			  btn: ['确定','取消'] //按钮
			}, function(){
				var data={ids:JSON.stringify(idsArr)};
				
				var succeed = function (result) {
		            if (0 == result.status) {
		            	layer.msg('删除成功', {
		        			  time: 1000 //1秒关闭（如果不配置，默认是3秒）
		        			}, function(){
		        				goBack();
		        			});
		            }
		            else
		            {
		            	layer.alert(result.message);
		            }
		        };
		        $.post(getRootPath() + "/subAppUser/background_delSubAppUserByIds.action",data,succeed, "json");
			}, function(){
				//取消没有事件
			});
	}else
	{
		layer.alert("无效的请求");
	}
}

//返回
function goBack()
{
	window.location.href=getRootPath()+"/backToUrl/toDo.action?url=deviceManage";
}
$(function(){
	createTree();
	getAuthCodeList();
	getDeviceCodeList();
	getDeviceDetail();
	//显示设备使用人员弹出框
    var showSelectUserLayer=function()
	{
		selectUserLayerIndex=layer.open({
			  type: 1,
			  title:'选择设备使用人员',
			  content:$("#selectUserLayer"),
			  area: ['800px', '580px'],
			  maxmin: false,//放大缩小
			  move:true //是否允许拖拽
		});
		if(!isCreate)
		{
			createTree();
		}
	}
    
    
    //校验
    var checkValidate=function()
    {
    	if(validate_notNull($("#real_name"),"#real_name","设备使用人不能为空"))
    		{
    			return true;
    		}
    		
    		return false;
    }
    
    //保存IPAD信息维护单
    var saveDeviceInfo=function()
    {
    	if(!checkValidate())
    	{
    		return;
    	}
    	var url=getRootPath()+"/subAppUser/background_addOrModifySubAppUser.action";
        var data={};
        $("#form-device-add").find("input,select,textarea").each(function(){
			data[$(this).attr("id")] = $(this).val();
		});
        if (data.deviceCode) {
        	data.ipad_uuid = data.deviceCode;
        }
		 var succeed = function (result) {
	            if (0 == result.status) {
	            	layer.msg('保存成功', {
	        			  time: 1000 //1秒关闭（如果不配置，默认是3秒）
	        			}, function(){
	        				goBack();
	        			});
	            } else {
	            	layer.alert(result.message);
	            }
	      };
	      $.post(url,data,succeed, "json");
    }
    
	
	var bindEvent=function()
	{
		$("#includeChild").change(function() {
			userTableRefesh();
	    });
		//选择使用人按钮
		$("#selectUserBtn").click(showSelectUserLayer);
		
		//关闭选择设备使用人员弹出框
		$("#closeSelectUserLayerBtn").click(
				function(){
					layer.close(selectUserLayerIndex);
				}
		);
		//退出按钮
		$("#exitBtn").click(
				function()
				{
					layer.confirm('确定退出编辑？', {
						  btn: ['确定','取消'] //按钮
						}, function(){
							goBack();
						}, function(){
							//取消没有事件
						});
				}
		);
		//保存并退出按钮
		$("#saveBtn").click(saveDeviceInfo);
		
	}
	
	bindEvent();
});


























