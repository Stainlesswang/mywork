function bootstrapRefresh()
{
//	$('#datatable').bootstrapTable('refresh');
	$('#datatable').bootstrapTable('refresh').bootstrapTable('refreshOptions', {pageNumber: 1});
}



$(function() {
	var table = $('#datatable');
	table.bootstrapTable({
				method : 'post',//请求action。建议换成post
				url : getRootPath()+"/subAppUser/background_showSubAppUser.action",
				sidePagination : "server",
				showRefresh : false,
				search: false,
				pagination : true,
				pageSize : 10,
				pageList : [ 10, 50, 100 ],
//				toolbar : '#toolbar',
				queryParamsType : "limit",
				queryParams: function (params) {
		            params["search"] = $("#keywords").val();
		            params["is_binding"] =$("input[type='radio']:checked").val();
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
                            align: 'center',
							width :'50px',
							formatter : function(value, row, index) {
								return index+1;
							}
						},
						{
							field : 'authorationcode',
							title : '授权码',
							visible : true,
							sortable:true,
                            align: 'center',
							formatter: function (value, row, index) {
								if(value==null||value=='')
								{
									value='--';
								}
								return "<a href='" + getRootPath() + "/subAppUser/background_addOrModifySubAppUserView.action?mid=" + row.mid + "'>" +  value + "</a>"
								}
						},
						{
							field : 'real_name',
							title : '领导姓名',
							visible : true,
                            align: 'center',
							sortable:true
						},
						{
							field : 'job_number',
							title : '账号',
                            align: 'center',
							visible : true,
							sortable:true
						},
						{
							field : 'is_binding',
							title : '设备是否绑定',
							visible : true,
                            align: 'center',
							formatter : function(value, row, index) {
								 if (value==null) {
									return null;
								}
								 if (value==0) {
									return "未绑定";
								}else {
									return "已绑定";
								}
							}
						},
						{
							field : 'energy',
							title : '电量',
							formatter : function(value, row, index) {
								return value ? value + "%" : "";
							}
						},
                    {
                        field : 'storage',
                        title : '设备存储',
                        visible : true
                    },
						{
							field : 'create_time',
							title : '登记日期',
							visible : true,
							sortable:true
						}
					]
			});
	
	
	
	
	$("#addDeviceBtn").click(function(){
		//检查已使用的授权码是否超过了授权人数
		$.ajax({
			url: getRootPath() + "/authcode/checkUsedAuthcodeNum",
			dataType: 'json',
			success:function(result) {
				if (result.status == 0){
					window.location.href=getRootPath()+"/subAppUser/background_addOrModifySubAppUserView.action";
				}
				else{
					layer.msg(result.message, {time:1000});
				}
			},
			error: function(result) {
				layer.meesage("服务异常",{time:1000});
			}
		});
	});
	
	//根据选中的设备信息状态来显示是否绑定信息
    $("input[type='radio']").change(function(){
		var selectType=$("input[type='radio']:checked").val();
		if(selectType==0)
		{
			table.bootstrapTable('showColumn', 'state');
		}
		else
		{
			if(selectType==2){
				window.location.href=getRootPath()+"/backToUrl/toDo.action?url=meetingSetting&back=device";
			}
            // else if(selectType==3){
            //     window.location.href=getRootPath()+"/authcode/background_AuthDetailView.action";
            // }
			else
			{
				bootstrapRefresh();
				if(selectType==1)
				{
					table.bootstrapTable('showColumn', 'is_binding');
				}
				else
				{
					table.bootstrapTable('hideColumn', 'is_binding');
				}
				
			}
			
		}
	});
	
	$("#keywords").keydown(function(event) {    
        if (event.keyCode == 13) {    
       	 bootstrapRefresh();    
        }    
    }) 
	
	//删除
	$("#delDeviceBtn").click(function(){
		var selects = $('#datatable').bootstrapTable('getSelections'); 
		if(selects!=null&&selects.length!=0)
		{
			var idsArr=new Array();
			for(i=0;i<selects.length;i++)
			{
				idsArr.push(selects[i].mid);
			}
			layer.confirm('确定删除所选设备？', {
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
			        $.post(getRootPath() + "/subAppUser/background_delSubAppUserByIds.action",
			            data,succeed, "json");
					
				}, function(){
					//取消没有事件
				});
		}
		else
		{
			layer.alert("请选择需要删除的设备");
		}
		
	});

});