/******************************************IPAD信息维护***********************************************************/
$(function() {
	function getUserDetail() 
	{
	var mid=$('#mid').val();
	if (mid!=null&&mid!='')
	{
	$.ajax({
		type:"POST",
		url:getRootPath()+'/subAppUser/background_getObjInstane.action',
		data:{
			mid:mid
		},
		dataType : 'json',
		cache : false,
		success : function(result) {
//		    console.log(JSON.stringify(result)); 
			if (result.status === 0) {
				var user=result.data;
				for ( var x in user) {
					$("#" + x).val(user[x]);
					
				}
				$("#ipad_uuid").html([user["ipad_uuid"]]);
				$("#register").html([user["register"]]);
				$("#create_time").html([user["create_time"]]);
				 
    			
    			
			} else {
				alert(result.info);
			}
		}
	});	
	}	
	}
	getUserDetail();
});