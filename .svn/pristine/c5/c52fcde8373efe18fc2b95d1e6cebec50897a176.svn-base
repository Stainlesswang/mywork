function bootstrapRefresh()
{
//	$('#datatable').bootstrapTable('refresh');
	$('#datatable').bootstrapTable('refresh').bootstrapTable('refreshOptions', {pageNumber: 1});
}

$(function() {
	$(".NumText").keyup(function(){ 
		$(this).val($(this).val().replace(/\D|^0/g,'')); 
	}).bind("paste",function(){
		$(this).val($(this).val().replace(/\D|^0/g,'')); 
	});
	var table = $('#datatable');
	table.bootstrapTable({
				method : 'post',//请求action。建议换成post
				url : getRootPath()+"/authcode/background_showauthcode.action",
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
		            params["status"] = $("#status").val();
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
							field : 'authcode',
							title : '授权码',
							visible : true,
						},
						{
							field : 'status',
							title : '是否使用',
							visible : true,
							formatter : function(value, row, index) {
								 if (value==null) {
									return null;
								}
								 if (value==0) {
									return "未使用";
								}else {
									return "已使用";
								}
							}
						} 
					]
			});
	
	
	
	var createAuthCode=function()
	{
		var prefix=$.trim($("#prefix").val());
		var num=$.trim($("#num").val());
		if(prefix=='')
		{
			layer.alert("请输入前缀");
			return;
		}
		if(num=='')
		{
			layer.alert("请输入数量");
			return;
		}
		num=parseInt(num);
		if(num>999)
		{
			layer.alert("数量不能超过999个");
			return;
		}
		if(!/^[a-zA-Z]{1,16}$/.test(prefix))
		{
			layer.alert("前缀必须是1-16位字母");
			return;
		}
		var data={prefix:prefix,num:num}
		var succeed = function (result) {
            if (0 == result.status) {
            	layer.msg('成功创建'+result.data+'条授权码', {
        			  time: 1000 //1秒关闭
        			}, function(){
        				bootstrapRefresh();
        			});
            }
            else{
            	layer.alert(result.message);
            }
        };
        $.post(getRootPath() + "/authcode/background_addAuthCode.action",
            data,succeed, "json");
	}
	
	//删除授权码
	var delAuthCode=function()
	{
		var selects = $('#datatable').bootstrapTable('getSelections'); 
		if(selects!=null&&selects.length!=0)
		{
			var idsArr=new Array();
			for(i=0;i<selects.length;i++)
			{
				idsArr.push(selects[i].authcode);
			}
			layer.confirm('如果授权码已被设备使用，设备将重新需要绑定，确定删除所选授权码？', {
				  btn: ['确定','取消'] //按钮
				}, function(){
					var data={codes:JSON.stringify(idsArr)};
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
			        $.post(getRootPath() + "/authcode/background_delAuthCode.action",
			            data,succeed, "json");
					
				}, function(){
					//取消没有事件
				});
		}
		else
		{
			layer.alert("请选择需要删除的授权码");
		}
	}
	
	var bindEvent=function()
	{
		//创建按钮
		$("#addAuthCodeBtn").click(createAuthCode);
		//退出
		$("#exitBtn").click(function(){
			window.location.href=getRootPath()+"/backToUrl/toDo.action?url=deviceManage";
		});
		//删除
		$("#delBtn").click(delAuthCode);
		//状态下拉框
		$("#status").change(function(){
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



















































