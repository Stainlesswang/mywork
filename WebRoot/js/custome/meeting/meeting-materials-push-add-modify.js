/** ****************************会议资料维护单********************************************* */
var selectLeaderLayerIndex=null;
var mergeFileLayerIndex=null;
var uploadLayerIndex=null;
var delAttachs = [];// 用于保存删除的附件。
var attachs = [];// 用于保存新增的附件。
var modAttachs = [];//用于保存修改过的附件
var totalAttach=[];// 所有附件，包括新增的以及原始上传的
var state="add";
var isCreate=false;
var meetingStatus=1;//定义1为初始状态,如果后边查询到会议状态结束  变成2(作为退按钮在结束状态下直接返回的标记)

/** *****************合并附件start*************************** */
var cnt = 0;
var rowCount=1;// 用来记录可以合并的文件数，追加时，超过数量就提示
var tableValue = [];
var tableValueLength = 0;
var newTrValues = {};
function addRow(cRow) {
    if(rowCount>=totalAttach.length)
    {
        layer.alert("没有可以继续合并的附件！");
        return;
    }
    cnt++;
    var temp = $("#tabRowTemplate");
    var newDom = temp.clone(true);
    newDom.attr("id", "test" + cnt);
    //已经存在的文件路径
    var optionValue = new Array();
    $("#form-materials-merge").find("option:selected").each(function(index2, elem2) {
        optionValue.push($(elem2).val());
    })
    newDom.find("option").each(function(index,elem) {
        if ($.inArray($(elem).val(), optionValue) == -1) {
            $(elem).attr("selected", true);
            //跳出循环
            return false;
        }

    });
    cnt++;
    rowCount++;
    $(cRow).parent().parent().parent().after(newDom);
}
function rmRow(cRow) {
    if (rowCount == 1) {
        layer.alert("最后一行不能删除");
        return;
    }
    if ($(cRow).prev().attr("id") == "btn0") {
        //降id赋给相邻的下一个button
        $($(cRow).parent().parent().parent().next().find("button").get(0)).attr("id","btn0")
    }
    rowCount--;
    $(cRow).parent().parent().parent().remove();
}


// 保存数据
// 合并附件操作
function mergeFileCtrl()
{
    tableValue = [];
    tableValueLength = 0;
    var newFileName=$.trim($("#fileName").val());
    if(newFileName=='')
    {
        layer.alert("请输入合并后的文件名称");
    }
    var flag=true;
    $("#form-materials-merge").find("select").each(function(){
        if(tableValue.length>0&&$.inArray($(this).val(),tableValue)>=0)
        {
// layer.alert("选择了重复的文件:"+$(this).text());
            var name= $(this).find("option:selected").text();
            layer.alert("选择了重复的文件:\n"+name);
            flag=false;
            return false;
        }
        else
        {
            tableValue.push($(this).val());
        }

    });

    if(tableValue.length==0)
    {
        layer.alert("请选择需要合并的文件");
        return;
    }
    if(flag)
    {
        var loadIndex=null;
        // 提交
        var data={fileName:newFileName,fileList:JSON.stringify(tableValue)};
        var succeed = function (result) {
// layer.closeAll('loading');
            layer.close(loadIndex);
            if (0 == result.status) {
// console.log(JSON.stringify(result));
                // 回写附件信息
                var file=result.data;
                file.attach_type=1;
                attachs.push(file);
                totalAttach.push(file);// 转换后的pdf设置可以再次转换20170527
                showAttachInfo(file);
                layer.close(mergeFileLayerIndex);
            }
            else{
                layer.alert(result.message);
            }
        };
        $.post(getRootPath() + "/fileUtil/background_mergeFile.action",data,succeed, "json");
// loadIndex = layer.load(2, {
// shade: [0.1,'#393D49'] //0.1透明度的白色背景
// });
        loadIndex =layer.load(2,{
            shade: [0.1,'#393D49'],// 0.1透明度的白色背景
            content:'<div style="margin-left: 50px;width: 150px;height: 37px;line-height: 37px">正在合并附件...</div>'

        });
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
/** *********************合并附件end*********************************** */

/*
 * 参数解释： title 标题 url 请求的url id 需要操作的数据id w 弹出层宽度（缺省调默认值） h 弹出层高度（缺省调默认值）
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
// 获取参会领导列表
function getUserList()
{
    var nodes=getSelectNode();// 获取选中的节点
// console.log(JSON.stringify(nodes));
    if(!nodes){return;}
    var orgId=nodes[0].id;
    var search=$("#keywords").val();
    var includeChild=0;// 默认不选中子部门
    if ($('#includeChild').is(':checked')) {
        includeChild=1;
    }
    var data={orgId:orgId,search:search,includeChild:includeChild}
    var succeed = function (result) {
        if (0 == result.status) {
// console.log(JSON.stringify(result));
            var rows=result.rows;
            var html="";
            $.each(rows, function (index, item) {
                // 循环获取数据 ,需要匹配选中的数据，看是否有重复的
                var flag=false;
                var userId = item.user_id;
                var realName = item.real_name;
                var userName = item.user_name;
                var orgName = item.org_name;
// var college_name = json[index].college_name;
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
// 删除会议信息
function delMeetInfo()
{
    var mid=$("#mid").val();
    var idsArr=new Array();
    idsArr.push(mid);
    if($.trim(mid)!='')
    {
        layer.confirm('确定删除该会议维护单？', {
            btn: ['确定','取消'] // 按钮
        }, function(){
            var data={ids:JSON.stringify(idsArr)};
            var succeed = function (result) {
                if (0 == result.status) {
                    layer.msg('删除成功', {
                        time: 1000 // 1秒关闭（如果不配置，默认是3秒）
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
            // 取消没有事件
        });
    }
    else
    {
        layer.alert("无效的请求");
    }
}

function getRoot() {
    var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
    // 返回一个根节点
    var node = treeObj.getNodesByFilter(function (node) { return node.level == 0 }, true);
// console.log(JSON.stringify(node.id));
}
function getSelectNode()
{
    var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
    var nodes=treeObj.getSelectedNodes();
// console.log(JSON.stringify(nodes));
    return nodes;
}
function setRootSelect()
{
    // 选中根节点
    var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
    // 返回一个根节点
    var node = treeObj.getNodesByFilter(function (node) { return node.level == 0 }, true);
    treeObj.selectNode(node);
}
function zTreeOnClick(event, treeId, treeNode) {
// getUserList(treeNode.id);
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
        url: getRootPath()+'/orginfo/background_showAllOrgTree.action', // url
                                                                        // action是方法的名称
        data: { },
        type: 'POST',
        dataType: "text", // 可以是text，如果用text，返回的结果为字符串；如果需要json格式的，可是设置为json
        ContentType: "application/json; charset=utf-8",
        success: function(data) {
            zNodes = data;

            $.fn.zTree.init($("#treeDemo"), setting, eval('(' + zNodes + ')'));
            var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
            treeObj.expandAll(true);
            var node = treeObj.getNodesByFilter(function (node) { return node.level == 0 }, true);
            treeObj.selectNode(node);
// getUserList(node.id);
            getUserList();
        },
        error: function(msg) {
            alert("失败");
        }
    });
}
// 显示附件信息
function showAttachInfo(file, meetStatus)
{
    var fileSize=file.attach_size;
    fileSize=parseInt(fileSize);
    if(fileSize<1024)
    {
        fileSize=fileSize+"B";

    }
    else if(fileSize<=1024*1024)
    {
        fileSize=(fileSize/1024).toFixed(2)+"KB";

    }
    else
    {
        fileSize=(fileSize/1024/1024).toFixed(2)+"M";


    }
    // startdownloadUserTemplate.action
// var
// downloadUrl=getRootPath()+"/fileUtil/downloadUserTemplate.action?filePath="+file.attach_path+"&fileName="+file.attach_name;
    var chakanUrl=getRootPath()+"/"+file.attach_path;
    var downloadUrl=getRootPath()+"/fileUtil/downloadMeetFileByToken.action?token="+file.token+"&type="+file.attach_type+"&fileName="+encodeURIComponent(file.attach_name);
// var
// downloadUrl=getRootPath()+"/fileUtil/downloadMeetFileByToken.action?token="+file.token+"&type="+file.attach_type+"&fileName="+file.attach_name;
// downloadUrl=decodeURIComponent(downloadUrl);//其实可以不需要的
    var file_icon=file.attach_type==1?"file_merge.png":"file_normal.png";
// var attachFileHtml='<tr id="file_'+file.token+'"
// style="background-color:#E8E5E5;">'+
// '<td><img src="'+getRootPath()+'/images/'+file_icon+'"
// style="width:18px;"/></td>'+
// '<td><a href="'+getRootPath()+'/'+file.attach_path+'"
// target="_blank">'+file.attach_name+'&nbsp;&nbsp;&nbsp;('+fileSize+')</a></td>'+
// '<td><a href="javascript:;"
// onclick="deleteAttach(\''+file.token+'\',\''+file.file_mid+'\')">删除</a></td>'+
// '</tr>';
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
// 删除附件
function deleteAttach(_token,attach_id){
    var token=_token;
    var deleteAttachToken = token;
    var attachTokens = $.map(attachs, function(attach){return attach.token});
    var totalAttachTokens=$.map(totalAttach, function(attach){return attach.token});// 20170510
    $("#file_" + token).remove();// 渲染附件，根据token找到html，删除。
    if($.inArray(deleteAttachToken, attachTokens) > -1){
        attachs.splice($.inArray(deleteAttachToken, attachTokens), 1);
        i = i - 1;
    }
    // 清除总列表中数据
    if($.inArray(deleteAttachToken, totalAttachTokens) > -1){
        totalAttach.splice($.inArray(deleteAttachToken, totalAttachTokens), 1);// 20170510
        i = i - 1;
    }
    if(attach_id!=null&&attach_id!=undefined&&attach_id!='undefined')
    {
        delAttachs = delAttachs.concat(attach_id);
    }
}

//删除回传文件
function deleteBackAttach(token, fileMid) {
    layer.confirm('确定删除草稿文件？', {btn: ['确定','取消']}, function(index) {
            layer.close(index);
            $.ajax({
                url: getRootPath() + "/subAppMeetingFile/deleteMeetingFile",
                data: {
                    fileMid:fileMid
                },
                dataType: 'json',
                success: function(result) {
                    if (result.status == 0) {
                        //刷新列表
                        $('#pdfDraftTable').bootstrapTable('refresh').bootstrapTable('refreshOptions', {pageNumber: 1});
                    }
                    else {
                        layer.alert(result.message);
                    }
                }
            });
        },
        function(){
            //取消没有事件
        });
}
// 获取pdf文件草稿版本列表
var getPdfDraft = function() {
    $("#pdfDraftTableLabel").show();
    $("#pdfDraftTable").show();
    var table = $('#pdfDraftTable');
    table.bootstrapTable({
        method : 'post',// 请求action。建议换成post
        url : getRootPath()+"/subAppMeetingFile/getMeetingFile",
        sidePagination : "server",
        showRefresh : false,
        search: false,
        pagination : true,
        pageSize : 4,
        pageList : [ 10, 50, 100 ],
        sortName : 'last_update_time',// 初始化的时候排序的字段
        sortOrder: "desc", // 排序方式
// toolbar : '#toolbar',
        queryParamsType : "limit",
        queryParams: function (params) {
            params["meeting_mid"] = $("#mid").val();
            params["attach_type"]=3;
            return params;
        },
        columns : [

            {
                field : 'index',
                title : '序号',
                width :'10px',
                formatter : function(value, row, index) {
                    return index+1;
                }
            },
            {
                field : 'attach_name',
                title : '附件名称',
                width : '30%',
                visible : true,
                sortable:true,
                formatter : function(value, row, index) {
                    var chakanUrl=getRootPath()+"/"+row.attach_path;
                    return '<a href="' + chakanUrl + '" target="_blank">' + value + '</a>';
                }
            },
            {
                field : 'uploader_name',
                title : '修改人',
                width : '20%',
                visible : true,
                sortable:true
            },
            {
                field : 'last_update_time',
                title : '修改时间',
                width : '20%',
                visible : true,
                sortable : true
            },
            {
                title: '操作',
                width: '20',
                formatter : function (value, row, index) {
                    var downloadUrl=getRootPath()+"/fileUtil/downloadMeetFileByToken.action?token="+row.token+"&type="+row.attach_type+"&fileName="+encodeURIComponent(row.attach_name);
                    return '<a href="'+ downloadUrl +'">下载</a>'+
                        '<a style="margin-left:10px" href="javascript:;" onclick="deleteBackAttach(\''+row.token+'\',\''+row.file_mid+'\')">删除</a>';
                }
            }
        ],
        responseHandler:function(res)
        {
            return res;

        }
    });
}

$(function(){
// createTree();//创建组织

    // multipselect2side 显示
    $("#dealerselect").multiselect2side({
        selectedPosition: 'right',
        moveOptions:true,
        labelsx: '待选人员',
        labeldx: '已选人员',
        search: "查找: "

    });

    //加载会议类型
    $.ajax({
        url: getRootPath() + "/meetingType/getMeetingType",
        data:{},
        type: 'POST',
        dataType:'json',
        async: false,
        success: function(result)
        {
            if (result.status == 0)
            {
                var rows=result.rows;
                var html="";
                $.each(rows, function (index, item)
                {
                    var typeId = item.id;
                    var typeName = item.name;
                    html+="<option value='"+typeId+"'>"+ typeName +  "</option>";
                });
                $("#meet_type").html(html);
            }
        }
    });

    // 获取会议详情
    var getMeetDetail=function()
    {
        var mid=$("#mid").val();
        if($.trim(mid)!='')
        {
            state="update";
            // 根据id获取会议详情
            var data={mid:mid};
            var succeed = function (result) {
                if (0 == result.status) {
                    var meetInfo=result.data;

                    for ( var x in meetInfo) {
                        if(x=='is_ipad')
                        {
                            $("#is_ipad").html(["未推送","已推送"][meetInfo.is_ipad]);
                        }
                        else if(x=='push_time'||x=='real_name'||x=='draft_time')
                        {
                            $("#" + x).val(meetInfo[x]);
                            $("#_" + x).html(meetInfo[x]);
                        }
                        else if(x!='meet_attend')
                        {
                            $("#" + x).val(meetInfo[x]);
                        }

                    }
                    if (meetInfo.status ==2 ) {
                        meetingStatus=2;
                        $("#meet_name").attr("disabled", true);
                        $("#meet_type").prop("disabled",true);
                        $("input").attr("disabled", true);
                        $("#selectUserBtn").attr("disabled", true);
                        $("#addAttachBtn").attr("disabled", true);

                    }
// $("[name='dealerselectms2side__dx[]']").html("<option
// value='2'>1212</option>");
                    // 显示参会人员信息
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
// var meet_attend=meetInfo.meet_attend;
// var meet_attend_ids=meetInfo.meet_attend_ids;
// if(meet_attend!=null&&meet_attend!=''&&meet_attend_ids!=null&&meet_attend_ids!='')
// {
// var html="";
// var attends=meet_attend.split(";");
// var ids=meet_attend_ids.split(";");
// for(i=0;i<attends.length-1;i++)
// {
// html+="<option value='"+ids[i]+"'>"+attends[i]+"</option>";
// }
// // alert(html);
// $("[name='dealerselectms2side__dx[]']").html(html);
// }
                    // 回写附件信息

                    var attachList=meetInfo.fileList;
                    if(attachList!=null&&attachList.length>0)
                    {
                        for(i=0;i<attachList.length;i++)
                        {
                            var file=attachList[i];
// if(file.attach_type==0&&checkFileCanConvert(file.suffix))//符合转换条件的文件
// {
// totalAttach.push(file);
// }
                            //文件不是回送的附件
                            if (file.attach_type != 3) {
                                if(checkFileCanConvert(file.suffix))// 符合转换条件的文件，合并后的附件也可以再次合并，20170527
                                {
                                    totalAttach.push(file);
                                }


                                showAttachInfo(file, meetInfo.status);
                            }
// var fileSize=file.attach_size;
// if(fileSize<1024)
// {
// fileSize=fileSize+"B";
// }
// if(1024<=fileSize<1024*1024)
// {
// fileSize=(fileSize/1024).toFixed(2)+"KB";
// }
// else
// {
// fileSize=(fileSize/1024/1024).toFixed(2)+"M";
// }
// //start
// var file_icon=file.attach_type==1?"file_merge.png":"file_normal.png";
// var attachFileHtml='<tr id="file_'+file.token+'"
// style="background-color:#E8E5E5;">'+
// '<td><img src="'+getRootPath()+'/images/'+file_icon+'"
// style="width:18px;"/></td>'+
// '<td><a
// href="'+getRootPath()+'/'+file.attach_path+'">'+file.attach_name+'&nbsp;&nbsp;&nbsp;('+fileSize+')</a></td>'+
// '<td><a href="javascript:;"
// onclick="deleteAttach(\''+file.token+'\',\''+file.file_mid+'\')">删除</a></td>'+
// '</tr>';
// $("#attachList").prepend(attachFileHtml);
                        }
                    }


                } else {
                    layer.alert(result.message);
                }
            };
            $.post(getRootPath() + "/subAppMeeting/background_getObjInstane.action",
                data,
                succeed, "json");

            // 单独获取会议资料下载信息
            $.ajax({
                url: getRootPath() + "/api/getMeetingFileDownload",
                data:{mid:mid},
                dataType:'json',
                success: function(result){
                    if (result.status == 0) {
                        var nameArray = new Array();
                        if (result.data) {
                            $.each(result.data,function(k,v){
                                if (v.real_name && $.inArray(v.real_name, nameArray) == -1) {
                                    nameArray.push(v.real_name);
                                }
                            });
                        }
                        // 显示资料下载人姓名
                        $("#downloader_name").val(nameArray.join(";"));
                    }

                }
            });

            // pdf文件草稿版本列表
            getPdfDraft();
        }
    }

    getMeetDetail();



    // 显示选择参会人员弹出框
    var showSelectLeaderLayer=function()
    {
        selectLeaderLayerIndex=layer.open({
            type: 1,
            title:'选择参会人员',
            content:$("#selectLeaderLayer"),
            area: '800px',
            maxmin: true,// 放大缩小
            move:true // 是否允许拖拽
        });
        if(!isCreate)
        {
            createTree();
        }
    }

    // 选择领导
    var selectLeader=function()
    {
        var leaderNames="";
        var leaderIds=";";
        $("[name='dealerselectms2side__dx[]'] option").each(function () { // 遍历全部option
            var val = $(this).val();
            var txt = $(this).text(); // 获取option的内容
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

    // 推送至ipad
    var pushToipad = function(){
        //没有附件不能推送、有一个附件不是pdf的不能推送
        if (totalAttach.length == 0 || (totalAttach.length == 1 && !totalAttach[0].attach_name.endsWith("pdf"))) {
            layer.alert("请合并附件后推送");
            return;
        }
        if (totalAttach.length > 1) {
            var hasMergeFile = false;
            for(var i = 0; i < totalAttach.length; i++){
                if (totalAttach[i].attach_type == 1) {
                    hasMergeFile = true;
                }
            }
            if (!hasMergeFile) {
                layer.alert("请合并附件后推送");
                return;
            }
        }
        // 未保存
        if (!$("#mid").val()) {
            // 保存会议信息
            return saveMeetInfo(1);
        }
        else {
//			push($("#mid").val());
            return saveMeetInfo(1);
        }
    }

    // 推送
    var push = function(mid)
    {
        var loading = layer.load(1);
        var data = {
            mid: mid
        };
        //Linux下部署不需要USB传送，将autoPush 修改为pushToIpad
        //并且将加载
        var url=getRootPath()+"/api/pushToIpad";
        // var url=getRootPath()+"/api/autoPush";

        var succeed = function (result) {
            if (0 == result.status) {
                layer.close(loading);
                layer.msg('推送成功', {
                    time: 1000 // 1秒关闭（如果不配置，默认是3秒）
                }, function(){

                    goBack();
                });
            } else {
                layer.close(loading);
                layer.alert(result.message);
            }
        };
        $.post(url,data,succeed, "json");
    }

    // 结束会议
    var endMeeting = function() {
        var mid=$("#mid").val();
        if(!mid) {
            layer.alert("会议不存在");
            return;
        }
        $.ajax({
            url: getRootPath() + "/api/endMeeting",
            data: {
                mid: mid
            },
            dataType: 'json',
            success: function (result){
                if (result.status == 0) {
                    layer.msg('结束会议成功', {
                        time: 1000 // 1秒关闭（如果不配置，默认是3秒）
                    }, function(){
                        window.location.href=getRootPath()+"/subAppMeeting/background_meetAddView.action?mid=" + mid + "&status=2";
                    });
                }
                else {
                    layer.alert(result.message);
                }
            },
            error: function(data){
                layer.alert("服务器异常！");
            }
        });
    }


    // 显示上传附件弹出框
    var showUploadAttachLayer=function()
    {
        var file = $("#uploadFile")
        file.after(file.clone().val(""));
        file.remove();
        uploadLayerIndex=layer.open({
            type: 1,
            title:'上传附件',
            content:$("#uploadAttachLayer"),
            area: ['500px', '250px'],
            maxmin: false,// 放大缩小
            move:true // 是否允许拖拽
        });
    }


    // 上传附件
    var fileUpload=function()
    {
        var validator=$("#form_file").validate();
        if(validator.form()){
            // 防止重复提交
            $("#uploadFileBtn").attr("disabled",true);
            $("#form_file").ajaxSubmit({
                type: "post",// 提交类型
// dataType: "json",//返回结果格式
                dataType: "text",
                url: getRootPath()+"/fileUtil/uploadAttactFile.action",// 请求地址
                data: {},// 请求数据
                success: function (data) {
                    // 请求成功后的函数
// console.log(data);
                    $("#uploadFileBtn").attr("disabled",false);
                    data = eval('('+data+')');  // 转为json对象
                    if(data.status==0){
// layer.alert("上传成功！");
                        layer.msg('上传成功', {
                            time: 1000 // 1秒关闭
                        }, function(){

                        });
                        // 回写附件信息
                        var file=data.data;
                        file.attach_type=0;// 普通文件，没有合并
                        var rows = [{"attach_path":file.attach_path, "attach_name":file.attach_name,
                            "attach_size":file.attach_size,"attach_realname":file.attach_realname,
                            "prefix":file.prefix,"suffix":file.suffix,attach_type:file.attach_type,"token":file.token
                        }];

                        // 回写附件信息
                        attachs.push(rows[0]);
// if(file.attach_type==0&&checkFileCanConvert(file.suffix))//符合转换条件的文件
// {
// totalAttach.push(rows[0]);
// }
                        if(checkFileCanConvert(file.suffix))// 符合转换条件的文件
                        {
                            totalAttach.push(rows[0]);
                        }

                        showAttachInfo(file);// 20170511
                        layer.close(uploadLayerIndex);
                    }else{
                        alert(data.message);
                    }
                },
                error: function (data) { $("#uploadFileBtn").attr("disabled",true); layer.alert("上传失败"); },// 请求失败的函数
                async: true

            });
        }

    }

    // 校验数据
    var checkValidate=function()
    {
        var meet_name=$("#meet_name").val();
        var meet_time=$("#meet_time").val();
        var meet_attend=$("#meet_attend").val();
        if (meet_name.length > 100){
            layer.tips("会议标题长度不能超过100个字符", "#meet_name", {
                tips: [2, '#FF5080']
            });
            return false;
        }
        if(validate_notNull($("#meet_name"),"#meet_name","会议标题不能为空")&&
            validate_notNull($("#meet_time"),"#meet_time","会议开始时间不能为空")&&
            validate_notNull($("#meet_attend"),"#meet_attend","参会领导不能为空"))
        {
            return true;
        }

        return false;
    }

    // 会议资料维护单保存 pushflag为1时执行推送操作
    var saveMeetInfo=function(pushflag)
    {

        var data = {};
        $("#form-meet-add").find("input,select,textarea").each(function(){
            data[$(this).attr("id")] = $(this).val();
        });
        // 校验数据
        // if(!checkValidate())
        // {
        // return;
        // }
        data["attachsList"]=JSON.stringify(attachs);// 添加的附件

        if (state == "update") {
            data["delatchsList"] = JSON.stringify(delAttachs);// 删除的附件
        }
// console.log(JSON.stringify(delAttachs));
// return;
        var url=getRootPath()+"/subAppMeeting/background_addOrModifySubAppMeeting.action";

        var succeed = function (result) {
            if (0 == result.status) {
// layer.alert("保存成功！");
// goBack();
                // 推送
                if (pushflag == 1) {

                    push(result.data);
                }
                else {
                    layer.msg('保存成功', {
                        time: 1000 // 1秒关闭（如果不配置，默认是3秒）
                    }, function(){
                        goBack();
                    });
                }
            } else {
                layer.alert(result.message);
            }
        };
        $.post(url,data,succeed, "json");

    }

    // 合并附件弹出框
    var showMergeFileLayer=function()
    {
        if(totalAttach==null||totalAttach.length==0)
        {
            layer.alert("没有可以合并的附件，请上传附件");
            return;
        }
        rowCount=1;
        mergeFileLayerIndex=layer.open({
            type: 1,
            title:'合并附件',
            content:$("#merge-file"),
            area: ['1000px','580px'],
            // maxmin: false,//放大缩小
            fix:false,
            move:false, // 是否允许拖拽,
            moveOut:true

        });
// layer.full(mergeFileLayerIndex);
// console.log(JSON.stringify(totalAttach));
        // 清除掉之前的下拉框数据
        $("#form-materials-merge .template").remove();
        $("#fileName").val($("#meet_name").val());
        if ($("#btn0").length == 0 ) {
            $('<tr >\
					<td class="text-r"></td>\
					<td  class="text-r">\
						<span class="select-box" style="width:78%;float:left;">\
							<select class="select" name="mf" size="1" id="select1">\
							</select>\
						</span>\
						<span>\
							<button class="btn btn-primary" style="width:10%;float:left;margin-left:3px;margin-right:2px;" type="button" id="btn0" onclick="addRow(this);">追加</button>\
							<button class="btn btn-danger" type="button" style="width:10%;" onclick="rmRow(this);">删除</button>\
						</span>\
					</td>\
				</tr>').appendTo($("#merge-tb"));
        }
        if(totalAttach.length!=0)
        {
            var html='';
            for(i=0;i<totalAttach.length;i++)
            {
                var file=totalAttach[i];
                html+='<option value="'+file.attach_path+'">'+file.attach_name+'</option>';
            }
            $("#select1").html(html);
            $("#select0").html(html);
            // 多文件合并缺省的把上传的附件都列出来
            for(i=0;i<totalAttach.length-1;i++)
            {
                addRow($("#btn0"));
            }
            $("#form-materials-merge").find("select").each(function(index,elem){
                // 设置选中
                $(elem).find("option").each(function(index2,elem2){
                    if(index==index2)
                    {
                        $(elem2).attr("selected",true);
                    }
                });
            });

        }

    }

    var bindEvents=function()
    {
        // 合并附件
        $("#mergeBtn").click(
// function(){
// mergeFile("合并附件",getRootPath()+"/backToUrl/toDo.action?url=meetingFileMerge",800,500);
// }
            showMergeFileLayer
        );
        // 选择领导按钮
        $("#selectUserBtn").click(showSelectLeaderLayer);

        $("#confirmSelectBtn").click(selectLeader);// 选择领导 确认按钮
        // 关闭选择领导弹出框
        $("#closeSelectLeaderLayerBtn").click(
            function(){
                layer.close(selectLeaderLayerIndex);
            }
        );
        // 上传附件按钮
        //$("#addAttachBtn").click(showUploadAttachLayer);
        // 上传附件确定按钮
        $("#uploadFileBtn").click(fileUpload);
        // 关闭上传文件弹出框
        $("#closeUploadLeaderLayerBtn").click(function(){
            layer.close(uploadLayerIndex);
        });
        // 退出按钮点击事件
        $("#exitBtn").click(function(){
            //判断当前会议状态,2 为结束状态,点击退出直接返回
            if (meetingStatus==1){
                //
                layer.confirm('确定退出编辑？', {
                    btn: ['确定','取消'] // 按钮
                }, function(){
                    goBack();
                }, function(){
                    // 取消没有事件
                });
            }else {
                //直接返回,不弹出交互框
                goBack();
            }

        });
        // 保存按钮
        $("#saveMeetBtn").click(saveMeetInfo);
        // 是否包含子部门
        $("#includeChild").change(function() {
            getUserList();
        });
        // 合并确定按钮
        $("#mergeConfirmBtn").click(mergeFileCtrl);
        // 取消合并附件按钮
        $("#cancelMergeFileBtn").click(function(){
            layer.close(mergeFileLayerIndex);
        });
        // 推送ipad
        $("#pushToipad").click(pushToipad);
        //结束会议
        $("#endBtn"	).click(endMeeting);


    }
    bindEvents();

});