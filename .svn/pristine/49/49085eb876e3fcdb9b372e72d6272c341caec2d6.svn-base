/******************************************会议资料维护***********************************************************/
$(function() {
	function getMeetDetail() 
	{
	var mid=$('#mid').val();
	if (mid!=null&&mid!='')
	{
	$.ajax({
		type:"POST",
		url:getRootPath()+'/subAppMeeting/background_getObjInstane.action',
		data:{
			mid:mid
		},
		dataType : 'json',
		cache : false,
		success : function(result) {
//		    console.log(JSON.stringify(result)); 
			if (result.status === 0) {
				var meet=result.data;
				for ( var x in meet) {
					$("#" + x).val(meet[x]);
					
				}
				$("#user_name").html([meet["user_name"]]);
				$("#draft_time").html([meet["draft_time"]]);
				$("#is_ipad").html(['未推送', '已推送'][meet["is_ipad"]]);//<span>用html,<input>用val
    			
    			
			} else {
				alert(result.info);
			}
		}
	});	
	}	
	}
	getMeetDetail();
});