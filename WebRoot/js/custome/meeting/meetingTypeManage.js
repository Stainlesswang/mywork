;(function($, window, document, rootPath, undefined){
	function bootstrapRefresh()
	{
		$('#datatable').bootstrapTable('refresh').bootstrapTable('refreshOptions', {pageNumber: 1});
	}
	
	$(function() {
		var table = $('#datatable');
		table.bootstrapTable({
			method : 'post',
			url : rootPath + "/meetingType/getMeetingType",
			sidePagination : "server",
			showRefresh : false,
			search: false,
			pagination : true,
			pageSize : 10,
			pageList : [ 10, 50, 100 ],
			queryParamsType : "limit",
			queryParams: function (params) {
				params.search = $("#keywords").val();
	            return params;
	        },
			columns : [
					{
						field : 'id',
						title : 'id',
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
						field : 'identifier',
						title : '会议类型编号',
						visible : true,
						formatter : function(value, row, index) {
							return value;
						}
					},
					{
						field : 'name',
						title : '会议类型名称',
						visible : true,
						formatter : function(value, row, index) {
							return '<a href="' + rootPath + '/meetingType/meetTypeAddView?meetingTypeId=' + row.id + '">' + value + '</a>';
						}
					},
					/*{
						field: 'manager_account', 
						title: '会议类型管理员'
					},*/
					{
						field: 'comment',
						title: '说明'
					}
					
				]
		});
		
		$("#searchTypeBtn").click(function() {
			bootstrapRefresh();
		});
		
		$("#addMeetingTypeBtn").click(function() {
			window.location.href = rootPath + "/meetingType/meetTypeAddView"
		});
		
		//删除
		$("#delMeetingTypeBtn").click(function() {
			var selects = $('#datatable').bootstrapTable('getSelections'); 
			if(selects!=null&&selects.length!=0) {
				
				layer.confirm("确定要删除么？",
						{btn:['确定', '取消']},
						function() {
							var ids = [];
							$.each(selects, function(){
								ids.push(this.id);
							});
							$.ajax({
								url: rootPath + "/meetingType/delMeetingType",
								type: 'POST',
								dataType: 'json',
								data: {
									ids: ids
								},
								success: function(result) {
									if (result.status == 0) {
										layer.msg("删除成功", {time:1000});
										bootstrapRefresh();
									}
									else {
										layer.msg(result.message, {time:1000});
									}
								},
								error: function(jqXHR) {
									layer.alert("服务器异常: " + jqXHR.status);
								}
							});
				});
			}
			else {
				layer.alert("请选择要删除的会议类型");
			}
		});
		
	});
})(jQuery, window, document, rootPath);






























