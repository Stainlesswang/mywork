function bootstrapRefresh()
{
//	$('#datatable').bootstrapTable('refresh');
	$('#datatable').bootstrapTable('refresh').bootstrapTable('refreshOptions', {pageNumber: 1});
}
$(function() {
	var table = $('#datatable');
	table.bootstrapTable({
				method : 'post',//请求action。建议换成post
				url : getRootPath()+"/subAppMeeting/background_showMeeting.action",
				sidePagination : "server",
				showRefresh : false,
				search: false,
				pagination : true,
				pageSize : 10,
				pageList : [ 10, 50, 100 ],
				sortName : 'create_time',//初始化的时候排序的字段
				sortOrder: "desc", //排序方式
//				toolbar : '#toolbar',
				queryParamsType : "limit",
				queryParams: function (params) {
		            params["search"] = $("#keywords").val();
		            params["is_paid"]=$("#is_paid").val();
		            return params;
		        },
				columns : [
						{
							field : 'mid',
							title : 'mid',
							visible : false,
						},
						{
						    field: '',
						    checkbox: true,
						    width :'25px',
						},
						{
							field : 'index',
							title : '行号',
							width :'50px',
							formatter : function(value, row, index) {
								return index+1;
							}
						},
						{
							field : 'create_time',
							title : '创建时间',
							visible : true,
							sortable:true
						},
						{
							field : 'meet_name',
							title : '会议标题',
							visible : true,
							sortable:true,
							formatter: function (value, row, index) {
								return "<a href='" + getRootPath() + "/subAppMeeting/background_meetAddView.action?mid=" + row.mid + "&status=" + row.status + "'>" +  value + "</a>"
								}
						},
						{
							field : 'meet_time',
							title : '会议开始时间',
							visible : true,
							sortable:true
						},
						{
							field : 'is_ipad',
							title : '是否已推送设备',
							visible : true,
							formatter : function(value, row, index) {
								if (value==null) {
									return null;
								}
								if(value==0){
									return "未推送";
								}
								if (value==1) {
									return "已推送";
								}else {
									return "未推送";
								}
							}
						},
						{
							field: 'status',
							title: '是否结束',
							formatter: function (value, row, index) {
								if (value==null) {
									return null;
								}
								if (value==2) {
									return "已结束";
								}
							}
						}
					],
					responseHandler:function(res)
					{
//						console.log(JSON.stringify(res));
//						return { rows: res.rows,total:res.total };
						return res;
						
					}
			});
	
	$("#addPushBtn").click(function(){
		window.location.href=getRootPath()+"/subAppMeeting/background_meetAddView.action";
	});
	$("#is_paid").change(function(){
		var isPaid=$("#is_paid").val();
		if(isPaid==5){//查看配置
			//
			window.location.href=getRootPath()+"/backToUrl/toDo.action?url=meetingSetting&back=meet";
		}
		else if(isPaid==0||isPaid==1||isPaid==-1)
		{
			bootstrapRefresh();
		}
	});
	
	$("#keywords").keydown(function(event) {    
        if (event.keyCode == 13) {    
       	 bootstrapRefresh();    
        }    
    }) 
	
	//删除
	$("#delMeetBtn").click(function(){
		var selects = $('#datatable').bootstrapTable('getSelections'); 
		if(selects!=null&&selects.length!=0)
		{
			var idsArr=new Array();
			for(i=0;i<selects.length;i++)
			{
				idsArr.push(selects[i].mid);
			}
			layer.confirm('确定删除所选会议？', {
				  btn: ['确定','取消'] //按钮
				}, function(){
					var data={ids:JSON.stringify(idsArr)};
					var succeed = function (result) {
			            if (0 == result.status) {
			            	layer.msg('删除成功', {
			        			  time: 1000 //1秒关闭
			        			}, function(){
			        				bootstrapRefresh();
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
			layer.alert("请选择需要删除的会议");
		}
		
	});
	
	
	//无网回送
	$("#noWifiBackBtn").click(function() {
		debugger;
		var loading = layer.load(1);
		$.ajax({
			url: getRootPath() + "/api/noWifiBackFile",
			type: 'POST',
			dataType: 'json',
			success: function (result){
				layer.close(loading);
				if(0 == result.status) {
	            	layer.msg('回送成功', {
	        			  time: 1000 // 1秒关闭（如果不配置，默认是3秒）
	        			}, function(){
	        				
	        			});
				}
				else {
					layer.alert(result.message);
				}
			},
			error: function(result) {
				layer.alert("服务器异常");
			}
		});
	});

});



















































