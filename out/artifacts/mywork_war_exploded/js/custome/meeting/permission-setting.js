/************************************权限设置js-暂未使用*********************************/
var selectAdminLayerIndex=null;
var currentPerSubType=1;//默认选择本模块管理员
var idNameArr=["","admin","ipad","meet"];
var treeIdNameArr=["","treeDemo","treeDemo2","treeDemo3"];
var checkBoxIdArr=["","includeChild","includeChild2","includeChild3"];
var keywordsIdArr=["","keywords","keywords2","keywords3"];
var treeId="treeDemo";
var checkBoxId="includeChild";
var keywordsId="keywords";
var isCreate=false;
var isCreate2=false;
var isCreate3=false;

//返回
function goBack()
{
	var back=$("#back").val();
	if(back==''||back=='meet')//返回会议
	{
		window.location.href=getRootPath()+"/backToUrl/toDo.action?url=meetingManage";
	}
	else//返回设备管理
	{
		window.location.href=getRootPath()+"/backToUrl/toDo.action?url=deviceManage";
	}
	
}
function clearSearch(){
	$("#"+keywordsId).val("");
	getUserList();
}
//获取管理员领导列表
function getUserList()
{
	var nodes=getSelectNode();//获取选中的节点
	var idName=idNameArr[currentPerSubType];
	if(!nodes){return;}
	var orgId=nodes[0].id;
	var search=$("#"+keywordsId).val();
	var includeChild=0;//默认不选中子部门
	if ($('#'+checkBoxId).is(':checked')) {
		includeChild=1;
	}
	var data={orgId:orgId,search:search,includeChild:includeChild}
	var succeed = function (result) {
        if (0 == result.status) {
//        	console.log(JSON.stringify(result));
        	var rows=result.rows;
        	var html="";
        	 $.each(rows, function (index, item) {    
                 //循环获取数据     ,需要匹配选中的数据，看是否有重复的
        		 var flag=false;
        		 var userId = item.user_id;   
                 var realName = item.real_name; 
                 var userName = item.user_name;
                 var orgName = item.org_name;
//                 var college_name = json[index].college_name; 
                 $("[name='"+idName+"selectms2side__dx[]'] option").each(function(){  
                    if(userId==$(this).val())
                    {
                    	flag=true;
                    }
                 });

                if(!flag)
                {
                	html+="<option value='"+userId+"'>"+ realName + "</option>"; 
                }
                  
                       
             });
        	 $("#"+idName+"selectms2side__sx").html(html);
        } 
        else{
        	layer.alert(result.message);
        }
    };
    $.post(getRootPath() + "/userinfo/background_showUserInfo.action",data,succeed, "json");
}

function getSelectNode()
{
	
	var treeObj = $.fn.zTree.getZTreeObj(treeId);
	var nodes=treeObj.getSelectedNodes(); 
//    console.log(JSON.stringify(nodes));
    return nodes;
}
function zTreeOnClick(event, treeId, treeNode) {
//  getUserList(treeNode.id);
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
   
function createTree () {
	if(currentPerSubType==1){isCreate=true;}
	else if(currentPerSubType==2){isCreate2=true;}
	else if(currentPerSubType==3){isCreate3=true;}
      var zNodes;
      $.ajax({
          url: getRootPath()+'/orginfo/background_showAllOrgTree.action', //url  action是方法的名称
          data: { },
          type: 'POST',
          dataType: "text", //可以是text，如果用text，返回的结果为字符串；如果需要json格式的，可是设置为json
          ContentType: "application/json; charset=utf-8",
          success: function(data) {
              zNodes = data;

              $.fn.zTree.init($("#"+treeId), setting, eval('(' + zNodes + ')'));
              var treeObj = $.fn.zTree.getZTreeObj(treeId);
              treeObj.expandAll(true);
              var node = treeObj.getNodesByFilter(function (node) { return node.level == 0 }, true);
              treeObj.selectNode(node);
//              getUserList(node.id);
              getUserList();
          },
          error: function(msg) {
              alert("失败");
          }
      });
  }

//获取权限消息
function getPermission()
{
	var data={};
	var succeed = function (result) {
        if (0 == result.status) {
//        	console.log(JSON.stringify(result));
        	var adminList=result.data.adminlist;
        	var ipadList=result.data.ipadlist;
        	var meetList=result.data.meetlist;
        	var html="";
        	var names="";
        	var ids="";
        	//模块管理员
        	$.each(adminList, function (index, item) {    
        		html+='<option value="'+item.user_id+'">'+item.real_name+'</option>'; 
        		names+=item.real_name+";"; 
        		ids+=item.user_id+";";
            });
        	$("[name='adminselectms2side__dx[]']").html(html);
        	$("#adminNames").val(names);
        	$("#adminIds").val(ids);
        	//ipad信息维护员
        	html="";
        	names="";
        	ids="";
        	$.each(ipadList, function (index, item) {    
        		html+='<option value="'+item.user_id+'">'+item.real_name+'</option>'; 
        		names+=item.real_name+";";
        		ids+=item.user_id+";";
            });
        	$("[name='ipadselectms2side__dx[]']").html(html);
        	$("#ipadNames").val(names);
        	$("#ipadIds").val(ids);
        	//会议创建员
        	html="";
        	names="";
        	ids="";
        	$.each(meetList, function (index, item) {    
        		html+='<option value="'+item.user_id+'">'+item.real_name+'</option>'; 
        		names+=item.real_name+";";
        		ids+=item.user_id+";";
            });
        	$("[name='meetselectms2side__dx[]']").html(html);
        	$("#meetNames").val(names);
        	$("#meetIds").val(ids);
        	
        } 
        else{
        	layer.alert(result.message);
        }
    };
    $.post(getRootPath() + "/permission/background_showPermission.action",data,succeed, "json");
}

$(function(){
	
	$("#adminselect").multiselect2side({  
        selectedPosition: 'right',  
        moveOptions:true,  
        labelsx: '待选人员',  
        labeldx: '已选人员',
        search: "查找: ", 
        
    });
	$("#ipadselect").multiselect2side({  
        selectedPosition: 'right',  
        moveOptions:true,  
        labelsx: '待选人员',  
        labeldx: '已选人员',
        search: "查找: ", 
        
    });
	$("#meetselect").multiselect2side({  
        selectedPosition: 'right',  
        moveOptions:true,  
        labelsx: '待选人员',  
        labeldx: '已选人员',
        search: "查找: ", 
        
    });
	//获取权限信息
	getPermission();
	var showSelectPermissionLayer=function(title,id)
	{
		treeId=treeIdNameArr[currentPerSubType];
		checkBoxId=checkBoxIdArr[currentPerSubType];
		keywordsId=keywordsIdArr[currentPerSubType];
		selectAdminLayerIndex=layer.open({
			  type: 1,
			  title:title,
			  content:$("#"+id),
			  area: ['800px', '580px'],
			  maxmin: false,//放大缩小
			  move:true //是否允许拖拽
		});
		layer.full(selectAdminLayerIndex);
		if(currentPerSubType==1)
		{
			if(!isCreate)
			{
				createTree();
			}
		}
		else if(currentPerSubType==2){
			if(!isCreate2)
			{
				createTree();
			}
		}
		else if(currentPerSubType==3){
			if(!isCreate3)
			{
				createTree();
			}
		}
		
	}
	
	var showSelectAdminLayer=function()
	{
		currentPerSubType=1;
		
		showSelectPermissionLayer("选择本模块管理员","selectAdminLayer");
	}
	var showSelectIpadLayer=function()
	{
		currentPerSubType=2;
		showSelectPermissionLayer("选择设备信息维护员","selectIpadLayer");
	}
	var showSelectMeetLayer=function()
	{
		currentPerSubType=3;
		showSelectPermissionLayer("选择新建会议材料人员","selectMeetLayer");
	}
	
	
	var selectAdmin=function()
	{
		var idName=idNameArr[currentPerSubType];
		var leaderNames="";
		var leaderIds="";
		
		$("[name='"+idName+"selectms2side__dx[]'] option").each(function () { //遍历全部option
	        var val = $(this).val();
	        var txt = $(this).text(); //获取option的内容
	        leaderNames+=txt+";";
	        leaderIds+=val+";";
		});
		$("#"+idName+"Ids").val(leaderIds);
		$("#"+idName+"Names").val(leaderNames);
		layer.close(selectAdminLayerIndex);
	}
	
	//保存权限信息
	var savePermissionInfo=function()
	{
		var data = {};
		$("#form-per-add").find("input,select,textarea").each(function(){
			data[$(this).attr("id")] = $(this).val();
		});
		data["per_type"]="1";
        var url=getRootPath()+"/permission/background_setPermission.action";
        
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
	        getUserList();
	    });
		$("#includeChild2").change(function() {
	        getUserList();
	    });
		$("#includeChild3").change(function() {
	        getUserList();
	    });
		
		//选择本模块管理员
		$("#selectAdminBtn").click(showSelectAdminLayer);
		$("#selectIpadBtn").click(showSelectIpadLayer);
		$("#selectMeetBtn").click(showSelectMeetLayer);
		
		$(".confirmBtn").click(selectAdmin);//选择本模块管理员 确认按钮
		//关闭选择本模块管理员弹出框
		$(".cancelBtn").click(
				function(){
					layer.close(selectAdminLayerIndex);
				}
		);
		
		$("#exitBtn").click(function(){
			layer.confirm('确定退出编辑？', {
				  btn: ['确定','取消'] //按钮
				}, function(){
					goBack();
				}, function(){
					//取消没有事件
				});
		});
		
		$("#savePerBtn").click(savePermissionInfo);
	}
	bindEvent();
});



























