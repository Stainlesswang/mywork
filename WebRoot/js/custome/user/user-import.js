/**********************导入exceljs***************************/
var selectOrgLayerIndex=null;

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
		/*$('#selectOrgTree').tree({
	        url:getRootPath()+'/orginfo/background_showOrgTree.action?currOrgId='+currOrgId,
	        checkbox:false,
	        onBeforeExpand:function(node,param){
	          var orgId=node.id;
	      	  $('#selectOrgTree').tree('options').url = getRootPath()+"/orginfo/background_showOrgTree.action?orgId="+orgId+"&currOrgId="+currOrgId;// change the url                       
	        },               
	        onClick:function(node){
	        },
	        onLoadSuccess:function(node,data)
	        {
	        	
	        }
	     });*/
		createTree();
	}
	

	var importExcel=function()
	{
		var validator=$("#form-import").validate();
		if(validator.form()){
			$("#form-import").ajaxSubmit({  
		        type: "post",//提交类型  
//		        dataType: "json",//返回结果格式  
		        dataType: "text",//返回结果格式  
		        url: getRootPath()+"/userinfo/background_importUserExcel.action",//请求地址  
		        data: {},//请求数据  
		        success: function (data) {//请求成功后的函数  
		        	data = eval('('+data+')');  //转为json对象 
		        	if(data.status==0){
		        		layer.msg('成功导入'+(data.data.addRow)+'条数据,修改'+(data.data.modRow)+'条数据', {
		        			  time: 1000 //1秒关闭（如果不配置，默认是3秒）
		        			}, function(){
		        				var index = parent.layer.getFrameIndex(window.name);
		        				//刷新tree
		        				var root = parent.$('#orgTree').tree('getRoot');
		        				if(root)
		        				{
		        					parent.$('#orgTree').tree("reload",root.target);
		        				}
		        				parent.$('#datatable').bootstrapTable("refresh");
	    						parent.layer.close(index);
		        			}); 
		    		}else{
		    			if(-30001==data.status)
		    			{
		    				layer.alert("导入失败，数据库或Excel中存在相同的账号！");
		    			}
		    			else
		    			{
		    				layer.alert(data.message);
		    			}
		    			
		    		}
		        },  
		        error: function (data) { alert("操作失败"); },//请求失败的函数  
		        async: true  
			});
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
		 
		 $("#submitBtn").click(importExcel);//提交按钮
		 
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
		 //清除选中的组织部门信息
		 $("#clearOrgBtn").click(function(){
			 $("#org_id").val("");
			 $("#org_name").val("");
		 });
	}
	
	bindEvent();
});































