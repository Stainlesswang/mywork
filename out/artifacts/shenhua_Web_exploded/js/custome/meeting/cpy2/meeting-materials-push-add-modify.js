/******************************会议资料维护单**********************************************/
var selectLeaderLayerIndex=null;
var uploadLayerIndex=null;
var delAttachs = [];//用于保存删除的附件。
var attachs = [];//用于保存新增的附件。
var totalAttach=[];//所有附件，包括新增的以及原始上传的
var state="add";
var isCreate=false;
/*
	参数解释：
	title	标题
	url		请求的url
	id		需要操作的数据id
	w		弹出层宽度（缺省调默认值）
	h		弹出层高度（缺省调默认值）
*/
function mergeFile(title,url,w,h){
	layer_show(title,url,w,h);
}

function goBack()
{
	window.location.href=getRootPath()+"/backToUrl/toDo.action?url=meetingManage";
}
function clearSearch(){
	$("#keywords").val("");
	getUserList();
}
//获取参会领导列表
function getUserList()
{
	var nodes=getSelectNode();//获取选中的节点
//	console.log(JSON.stringify(nodes));
	if(!nodes){return;}
	var orgId=nodes[0].id;
	var search=$("#keywords").val();
	var includeChild=0;//默认不选中子部门
	if ($('#includeChild').is(':checked')) {
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
                 $("[name='dealerselectms2side__dx[]'] option").each(function(){  
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
        	 $("#dealerselectms2side__sx").html(html);
        } 
        else{
        	layer.alert(result.message);
        }
    };
    $.post(getRootPath() + "/userinfo/background_showUserInfo.action",data,succeed, "json");
}
//删除会议信息
function delMeetInfo()
{
	var mid=$("#mid").val();
	var idsArr=new Array();
	idsArr.push(mid);
	if($.trim(mid)!='')
	{
		layer.confirm('确定删除该会议维护单？', {
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
		            else{
		            	layer.alert(result.message);
		            }
		        };
		        $.post(getRootPath() + "/subAppMeeting/background_delSubAppMeetingByIds.action",
		            data,succeed, "json");
				
			}, function(){
				//取消没有事件
			});
	}
	else
	{
		layer.alert("无效的请求");
	}
}

function getRoot() {  
    var treeObj = $.fn.zTree.getZTreeObj("treeDemo");  
    //返回一个根节点  
   var node = treeObj.getNodesByFilter(function (node) { return node.level == 0 }, true); 
   console.log(JSON.stringify(node.id));
} 
function getSelectNode()
{
	var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
	var nodes=treeObj.getSelectedNodes(); 
//    console.log(JSON.stringify(nodes));
    return nodes;
}
function setRootSelect()
{
	 //选中根节点
    var treeObj = $.fn.zTree.getZTreeObj("treeDemo");  
    //返回一个根节点  
   var node = treeObj.getNodesByFilter(function (node) { return node.level == 0 }, true);
   treeObj.selectNode(node);
}
function zTreeOnClick(event, treeId, treeNode) {
//    getUserList(treeNode.id);
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
//                getUserList(node.id);
                getUserList();
            },
            error: function(msg) {
                alert("失败");
            }
        });
    }
    
  //删除附件
var deleteAttach = function(_token,attach_id){
    	var token=_token;
    	var deleteAttachToken = token;
    	var attachTokens = $.map(attachs, function(attach){return attach.token});
    	var totalAttachTokens=$.map(totalAttach, function(attach){return attach.token});//20170510
    	$("#file_" + token).remove();//渲染附件，根据token找到html，删除。
    	if($.inArray(deleteAttachToken, attachTokens) > -1){
    		attachs.splice($.inArray(deleteAttachToken, attachTokens), 1);
    		totalAttach.splice($.inArray(deleteAttachToken, totalAttachTokens), 1);//20170510
    		i = i - 1;
    	}
    	if(attach_id!=null&&attach_id!=undefined&&attach_id!='undefined')
    	{
    		delAttachs = delAttachs.concat(attach_id);
    	}
    }

$(function(){
//	createTree();//创建组织
	
	// multipselect2side 显示  
    $("#dealerselect").multiselect2side({  
        selectedPosition: 'right',  
        moveOptions:true,  
        labelsx: '待选领导',  
        labeldx: '已选领导',
        search: "查找: ", 
        
    }); 
    
    //获取会议详情
    var getMeetDetail=function()
    {
    	var mid=$("#mid").val();
    	if($.trim(mid)!='')
    	{
    		state="update";
    		//根据id获取会议详情
    		var data={mid:mid};
			var succeed = function (result) {
	            if (0 == result.status) {
	            	var meetInfo=result.data;
//	            	console.log(JSON.stringify(meetInfo));
					for ( var x in meetInfo) {
						if(x=='is_paid')
						{
							$("#is_paid").html(["未推送","已推送"][meetInfo.is_paid]);
						}
						else if(x=='push_time'||x=='real_name'||x=='draft_time')
						{
							$("#" + x).val(meetInfo[x]);
							$("#_" + x).html(meetInfo[x]);
						}
						else
						{
							$("#" + x).val(meetInfo[x]);
						}
					}
//				    $("[name='dealerselectms2side__dx[]']").html("<option value='2'>1212</option>");
					//显示参会人员信息
					var attendList=meetInfo.attendUserList;
					if(attendList!=null&&attendList.length>0)
					{
						var html="";
						var attendsNames="";
						for(i=0;i<attendList.length;i++)
						{
							html+="<option value='"+attendList[i].user_id+"'>"+attendList[i].real_name+"</option>";
							attendsNames+=attendList[i].real_name+";";
						}
						$("#meet_attend").val(attendsNames);
						$("[name='dealerselectms2side__dx[]']").html(html);
					}
//					var meet_attend=meetInfo.meet_attend;
//					var meet_attend_ids=meetInfo.meet_attend_ids;
//					if(meet_attend!=null&&meet_attend!=''&&meet_attend_ids!=null&&meet_attend_ids!='')
//					{
//						var html="";
//						var attends=meet_attend.split(";");
//						var ids=meet_attend_ids.split(";");
//						for(i=0;i<attends.length-1;i++)
//						{
//							html+="<option value='"+ids[i]+"'>"+attends[i]+"</option>";
//						}
////						alert(html);
//						$("[name='dealerselectms2side__dx[]']").html(html);
//					}
					//回写附件信息
					
					var attachList=meetInfo.fileList;
					if(attachList!=null&&attachList.length>0)
					{
						for(i=0;i<attachList.length;i++)
						{
							var file=attachList[i];
			        		
			        		var fileSize=file.attach_size;
			        		if(fileSize<1024)
							{
			        			fileSize=fileSize+"B";
							}
							if(1024<=fileSize<1024*1024)
							{
								fileSize=(fileSize/1024).toFixed(2)+"KB";
							}
							else
							{
								fileSize=(fileSize/1024/1024).toFixed(2)+"M";
							}
			        		//start
			        		var file_icon=file.attach_type==1?"file_merge.png":"file_normal.png";
			        		var attachFileHtml='<tr id="file_'+file.token+'" style="background-color:#E8E5E5;">'+
								'<td><img src="'+getRootPath()+'/images/'+file_icon+'" style="width:18px;"/></td>'+
								'<td><a href="'+getRootPath()+'/'+file.attach_path+'">'+file.attach_name+'&nbsp;&nbsp;&nbsp;('+fileSize+')</a></td>'+
								'<td><a href="javascript:;" onclick="deleteAttach(\''+file.token+'\',\''+file.file_mid+'\')">删除</a></td>'+
							'</tr>';
			        		$("#attachList").prepend(attachFileHtml);
						}
					}
					
					
	            } else {
	                layer.alert(result.message);
	            }
	        };
	        $.post(getRootPath() + "/subAppMeeting/background_getObjInstane.action",
	            data,
	            succeed, "json");
    		
    	}
    }
    
    getMeetDetail();

	//显示选择参会人员弹出框
    var showSelectLeaderLayer=function()
	{
		selectLeaderLayerIndex=layer.open({
			  type: 1,
			  title:'选择参会领导',
			  content:$("#selectLeaderLayer"),
			  area: ['800px', '580px'],
			  maxmin: false,//放大缩小
			  move:true //是否允许拖拽
		});
		if(!isCreate)
		{
			createTree();
		}
	}
	
	//选择领导
	var selectLeader=function()
	{
		var leaderNames="";
		var leaderIds=";";
		$("[name='dealerselectms2side__dx[]'] option").each(function () { //遍历全部option
	        var val = $(this).val();
	        var txt = $(this).text(); //获取option的内容
	        leaderNames+=txt+";";
	        leaderIds+=val+";";
		});
		if(leaderNames.length==0)
		{
			layer.alert("请选择参会领导");
		}
		else
		{
			$("#meet_attend").val(leaderNames);
			$("#meet_attend_ids").val(leaderIds);
			layer.close(selectLeaderLayerIndex);
		}
	}
	
	//显示上传附件弹出框
	var showUploadAttachLayer=function()
	{
		uploadLayerIndex=layer.open({
			  type: 1,
			  title:'上传附件',
			  content:$("#uploadAttachLayer"),
			  area: ['500px', '250px'],
			  maxmin: false,//放大缩小
			  move:true //是否允许拖拽
		});
	}
	
	
	//上传附件
	var fileUpload=function(topDOM)
	{
		var validator=$("#form_file").validate();
		if(validator.form()){
			$("#form_file").ajaxSubmit({  
		        type: "post",//提交类型  
		        dataType: "json",//返回结果格式  
		        url: getRootPath()+"/fileUtil/uploadAttactFile.action",//请求地址  
		        data: {},//请求数据  
		        success: function (data) {//请求成功后的函数  
//		        	console.log(JSON.stringify(data));
		        	if(data.status==0){
		    			layer.alert("上传成功！");
		    			//回写附件信息
		        		var file=data.data;
		        		file.attach_type=0;//普通文件，没有合并
		        		var rows = [{"attach_path":file.attach_path, "attach_name":file.attach_name, 
		        			"attach_size":file.attach_size,"attach_realname":file.attach_realname,
		        			"prefix":file.prefix,"suffix":file.suffix,attach_type:file.attach_type,"token":file.token
		        		}];
		        		
		        		//回写附件信息
		        		attachs.push(rows[0]);
		        		var fileSize=file.attach_size;
		        		if(fileSize<1024)
						{
		        			fileSize=fileSize+"B";
						}
						if(1024<=fileSize<1024*1024)
						{
							fileSize=(fileSize/1024).toFixed(2)+"KB";
						}
						else
						{
							fileSize=(fileSize/1024/1024).toFixed(2)+"M";
						}
		        		//start
		        		var file_icon=file.attach_type==1?"file_merge.png":"file_normal.png";
		        		var attachFileHtml='<tr id="file_'+file.token+'" style="background-color:#E8E5E5;">'+
							'<td><img src="'+getRootPath()+'/images/'+file_icon+'" style="width:18px;"/></td>'+
							'<td><a href="'+getRootPath()+'/'+file.attach_path+'">'+file.attach_name+'&nbsp;&nbsp;&nbsp;('+fileSize+')</a></td>'+
							'<td><a href="javascript:;" onclick="deleteAttach(\''+file.token+'\',\''+file.file_mid+'\')">删除</a></td>'+
						'</tr>';
		        		$("#attachList").prepend(attachFileHtml);
		        		layer.close(uploadLayerIndex);
		    		}else{
		    			alert(data.message);
		    		}
		        },  
		        error: function (data) { layer.alert("上传失败"); },//请求失败的函数  
		        async: true  
		   
			});
		}
		
	}
	
	//校验数据
	var checkValidate=function()
	{
		var meet_name=$("#meet_name").val();
		var meet_time=$("#meet_time").val();
		var meet_attend=$("#meet_attend").val();
		if(validate_notNull($("#meet_name"),"#meet_name","会议标题不能为空")&&
			validate_notNull($("#meet_time"),"#meet_time","会议开始时间不能为空")&&
			validate_notNull($("#meet_attend"),"#meet_attend","参会领导不能为空"))
		{
			return true;
		}
		
		return false;
	}
	
	//会议资料维护单保存
	var saveMeetInfo=function()
	{
		
		var data = {};
		$("#form-meet-add").find("input,select,textarea").each(function(){
			data[$(this).attr("id")] = $(this).val();
		});
		//校验数据
        if(!checkValidate())
        {
        	return;
        }
        data["attachsList"]=JSON.stringify(attachs);//添加的附件
        
        if (state == "update") {
            data["delatchsList"] = JSON.stringify(delAttachs);//删除的附件
        }
//        console.log(JSON.stringify(delAttachs));
//        return;
        var url=getRootPath()+"/subAppMeeting/background_addOrModifySubAppMeeting.action";
        
		 var succeed = function (result) {
	            if (0 == result.status) {
//	                layer.alert("保存成功！");
//	                goBack();
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
	
	//合并附件
	var mergeFile=function()
	{
		
	}
	
	var bindEvents=function()
	{
		//合并附件
		$("#mergeBtn").click(
//				function(){
//					mergeFile("合并附件",getRootPath()+"/backToUrl/toDo.action?url=meetingFileMerge",800,500);
//				}
				mergeFile
		);
		//选择领导按钮
		$("#selectUserBtn").click(showSelectLeaderLayer);
		
		$("#confirmSelectBtn").click(selectLeader);//选择领导 确认按钮
		//关闭选择领导弹出框
		$("#closeSelectLeaderLayerBtn").click(
				function(){
					layer.close(selectLeaderLayerIndex);
				}
		);
		//上传附件按钮
		$("#addAttachBtn").click(showUploadAttachLayer);
		//上传附件确定按钮
		$("#uploadFileBtn").click(fileUpload);
		//关闭上传文件弹出框
		$("#closeUploadLeaderLayerBtn").click(function(){
			layer.close(uploadLayerIndex);
		});
		//退出按钮点击事件
		$("#exitBtn").click(function(){
			layer.confirm('确定退出编辑？', {
				  btn: ['确定','取消'] //按钮
				}, function(){
					goBack();
				}, function(){
					//取消没有事件
				});
		});
		//保存按钮
		$("#saveMeetBtn").click(saveMeetInfo);
		//是否包含子部门
		$("#includeChild").change(function() {
	        getUserList();
	    });
		
	}
	bindEvents();
	
});
























