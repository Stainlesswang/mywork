define(function(require, expors, module) {
    var $ = require('../../../lib/jquery/1.9.1/jquery.define.js');

    // 显示附件信息
    function showAttachInfo(file, meetStatus)
    {
    	var fileSize=file.attach_size;
    	fileSize=parseInt(fileSize);
    	if(fileSize<1024)
    	{
    		fileSize=fileSize+"B";
    	}
    	else if(fileSize<1024*1024)
    	{
    		fileSize=(fileSize/1024).toFixed(2)+"KB";
    	}
    	else
    	{
    		fileSize=(fileSize/1024/1024).toFixed(2)+"M";
    	}
    	var chakanUrl=getRootPath()+"/"+file.attach_path;
    	var downloadUrl=getRootPath()+"/fileUtil/downloadMeetFileByToken.action?token="+file.token+"&type="+file.attach_type+"&fileName="+encodeURIComponent(file.attach_name);
    	var file_icon=file.attach_type==1?"file_merge.png":"file_normal.png";
    	var attachFileHtml='<tr id="file_'+file.token+'" style="background-color:#E8E5E5;">'+
    	'<td><img src="'+getRootPath()+'/images/'+file_icon+'" style="width:18px;"/></td>'+
    	'<td><a href="'+chakanUrl+'" target="_blank">'+ file.attach_name+'&nbsp;&nbsp;&nbsp;('+fileSize+')</a></td>'+
    	'<td><a href="'+ downloadUrl +'">下载</a></td>'+
    	(meetStatus == 2 ? '' : '<td><a href="javascript:;" onclick="deleteAttach(\''+file.token+'\',\''+file.file_mid+'\')">删除</a></td>')+   //会议结束了就不能再删除附件了
    '</tr>';
    	if(file.attach_type==1)
    	{
    		$("#attachList").prepend(attachFileHtml);
    	}
    	else
    	{
    		$("#attachList").append(attachFileHtml);
    	}
    	
    }
    
    // 校验文件是否可以转换
    function checkFileCanConvert(fileType)
    {
    	if(fileType==""||fileType==null||fileType=="null")// 文件夹
    	{
    		return false;
    	}
    	fileType=fileType.toLowerCase();
    	var allowtype =  ["pdf","ppt","pptx","xls","xlsx","doc","docx"];
    	if(fileType.length>1 && fileType != ''){
    		if($.inArray(fileType,allowtype) == -1){
               return false;
           }else{
               return true;
           }
    	}
    	else
    	{
    		return false;
    	}
    }
    
	//require.async加载发生在模块执行期
    require.async('../../upload/upload.js', function(upload) {
        upload.init({
            url: getRootPath() + "/fileUtil/uploadAttactFile.action",// 请求地址
            multipart_params: {
                
            },
            //指定文件上传时文件域的名称，默认为file
            file_data_name: 'uploadFile',
            //按钮位置
            browse_button: 'addAttachBtn',
            //按钮容器
            container: 'upload-container',
            //文件最大M
            max_file_size: '50mb',
            //过滤文件格式
            filters: [{
                title: " Ms files",
                extensions: "doc,docx,xls,xlsx,ppt,pptx,pdf"
            }],
            //多选
            multi_selection: true,
            FilesAdded: function(uploader, files) {
            },
            //文件上传成功之后
            FileUploaded: function(up, file, result) {
            	var file = $.parseJSON(result.response).data;
            	file.attach_type=0;// 普通文件，没有合并
            	var rows = [{"attach_path":file.attach_path, "attach_name":file.attach_name, 
        			"attach_size":file.attach_size,"attach_realname":file.attach_realname,
        			"prefix":file.prefix,"suffix":file.suffix,attach_type:file.attach_type,"token":file.token
        		}];
            	//用于保存新增的附件。
            	//attachs,totalAttach变量已在meeting-materials-push-add-modify.js中定义过了，这种写法容易造成混淆，有时间再改 TODO
            	attachs.push(rows[0]);
            	totalAttach.push(rows[0]);
            	showAttachInfo(file);
            }
        })
    });
    expors.show = function(opt) {};
});