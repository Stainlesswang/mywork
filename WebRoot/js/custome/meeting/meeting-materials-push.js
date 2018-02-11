function bootstrapRefresh() {
//	$('#datatable').bootstrapTable('refresh');
    $('#datatable').bootstrapTable('refresh').bootstrapTable('refreshOptions', {pageNumber: 1});
}

$(function () {
    var table = $('#datatable');
    table.bootstrapTable({
        method: 'post',//请求action。建议换成post
        url: getRootPath() + "/subAppMeeting/background_showMeeting.action",
        sidePagination: "server",
        showRefresh: false,
        search: false,
        pagination: true,
        pageSize: 10,
        pageList: [10, 50, 100],
        sortName: 'create_time',//初始化的时候排序的字段
        sortOrder: "desc", //排序方式
//				toolbar : '#toolbar',
        queryParamsType: "limit",
        queryParams: function (params) {
            params["search"] = $("#keywords").val();
            params["is_paid"] = $("input[type='radio']:checked").val();
            return params;
        },
        columns: [

            {
                field: 'mid',
                title: 'mid',
                visible: false
            },
            {
                field: '',
                checkbox: true,
                width: '25px'
            },
            {
                field: 'index',
                title: '行号',
                width: '50px',
                align: 'center',
                formatter: function (value, row, index) {
                    return index + 1;
                }
            },
            {
                field: 'create_time',
                title: '创建时间',
                align: 'center',
                visible: true,
                sortable: true
            },
            {
                field: 'meet_name',
                title: '会议标题',
                visible: true,
                sortable: true,
                align: 'center',
                formatter: function (value, row, index) {
                    return "<a href='" + getRootPath() + "/subAppMeeting/background_meetAddView.action?mid=" + row.mid + "&status=" + row.status + "'>" + value + "</a>"
                }
            },
            {
                field: 'meet_time',
                title: '会议开始时间',
                align: 'center',
                visible: true,
                sortable: true
            },
            {
                field: 'meet_type_name',
                title: '会议类型',
                align: 'center',
                visible: true,
                sortable: false
            },
            {
                field: 'real_name',
                title: '拟稿人',
                align: 'center',
                visible: true,
                sortable: false
            },
            {
                field: 'is_ipad',
                title: '是否已推送设备',
                width: '55px',
                visible: true,
                align: 'center',
                <!--增加显示样式2018/1/24 王建强-->
                // formatter: function(value,row,index) {
                //     //通过判断单元格的值，来格式化单元格，返回的值即为格式化后包含的元素
                //     var a = "";
                //     if(value == "已完成") {
                //         var a = '<span style="color:#00ff00">'+value+'</span>';
                //     }else if(value == "已分派"){
                //         var a = '<span style="color:#0000ff">'+value+'</span>';
                //     }else if(value == "待办") {
                //         var a = '<span style="color:#FF0000">'+value+'</span>';
                //     }else{
                //         var a = '<span>'+value+'</span>';
                //     }
                //     return a;
                // }

                formatter: function (value, row, index) {
                    var a="";
                    if (value == 0) {

                        a='<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>';

                    }
                    if (value == 1) {
                        a= '<span class="glyphicon glyphicon-ok" aria-hidden="true"></span>';
                    }
                    else {
                      a= '<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>';
                    }
                    return a;
                }

            },
            {
                field: 'status',
                title: '是否结束',
                align: 'center',
                formatter: function (value, row, index) {

                    if (value == 2) {
                        return  '<span style="color:#B82C29" fontsize="20dp">'+'结束'+'</span>';
                    }
                    else {
                        return  '<span style="color:#008B00" fontsize="20dp">'+'正常'+'</span>';
                    }
                }
            }
        ],
        responseHandler: function (res) {
//						console.log(JSON.stringify(res));
//						return { rows: res.rows,total:res.total };
            return res;

        }
//         rowStyle: function (row, index) {
//
// //这里['active', 'success', 'info', 'warning', 'danger']代表5中颜色
//
//             var str = "";
//
//             if (row.AduitStatus == "已推送") {
//
//                 str = 'success'; // 显示绿色
//
//             } else  {
//
//                 str = 'warning'; // 显示黄色
//
//             }
// 			}
    });

    $("#addPushBtn").click(function () {
        window.location.href = getRootPath() + "/subAppMeeting/background_meetAddView.action";
    });
    $("input[type='radio']").change(function () {
        var isPaid=$("input[type='radio']:checked").val();

        if (isPaid == 5) {//查看配置
            //
            window.location.href = getRootPath() + "/backToUrl/toDo.action?url=meetingSetting&back=meet";
        }
        else if (isPaid == 0 || isPaid == 1 || isPaid == -1) {
            bootstrapRefresh();
        }
    });
    // $("#is_paid").change(function () {
    //     // var isPaid = $("#is_paid").val();
    //     var isPaid=$("input[type='radio']:checked").val();
    //     if (isPaid == 5) {//查看配置
    //         //
    //         window.location.href = getRootPath() + "/backToUrl/toDo.action?url=meetingSetting&back=meet";
    //     }
    //     else if (isPaid == 0 || isPaid == 1 || isPaid == -1) {
    //         bootstrapRefresh();
    //     }
    // });

    $("#keywords").keydown(function (event) {
        if (event.keyCode == 13) {
            bootstrapRefresh();
        }
    })

    //删除
    $("#delMeetBtn").click(function () {
        var selects = $('#datatable').bootstrapTable('getSelections');
        if (selects != null && selects.length != 0) {
            var idsArr = new Array();
            for (i = 0; i < selects.length; i++) {
                idsArr.push(selects[i].mid);
            }
            layer.confirm('确定删除所选会议？', {
                btn: ['确定', '取消'] //按钮
            }, function () {
                var data = {ids: JSON.stringify(idsArr)};
                var succeed = function (result) {
                    if (0 == result.status) {
                        layer.msg('删除成功', {
                            time: 1000 //1秒关闭
                        }, function () {
                            bootstrapRefresh();
                        });
                    }
                    else {
                        layer.alert(result.message);
                    }
                };
                $.post(getRootPath() + "/subAppMeeting/background_delSubAppMeetingByIds.action",
                    data, succeed, "json");

            }, function () {
                //取消没有事件
            });
        }
        else {
            layer.alert("请选择需要删除的会议");
        }

    });


    //无网回送
    $("#noWifiBackBtn").click(function () {
        debugger;
        var loading = layer.load(1);
        $.ajax({
            url: getRootPath() + "/api/noWifiBackFile",
            type: 'POST',
            dataType: 'json',
            success: function (result) {
                layer.close(loading);
                if (0 == result.status) {
                    layer.msg('回送成功', {
                        time: 1000 // 1秒关闭（如果不配置，默认是3秒）
                    }, function () {

                    });
                }
                else {
                    layer.alert(result.message);
                }
            },
            error: function (result) {
                layer.alert("服务器异常");
            }
        });
    });

});



















































