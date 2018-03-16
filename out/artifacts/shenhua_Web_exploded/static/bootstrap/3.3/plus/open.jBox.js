/**
 * 模块化开窗，需要插件jQuery，jBox
 * 针对jquery实例对象扩展了三个方法：openTip，openModal，showNotice
 * 开窗打开时触发body对象的“box.open”事件,开窗关闭时触发body对象的“box.close”事件
 * Created by Chenny on 2016/9/19.
 */
$(function () {
    /*为职务和部门输入框加载开窗控件*/
    var container = $("<div><table></table></div>");
    var nameList=[
        'duty',
        'department',
        'room',
        'template',
        'employee',
        'user',
        'period',
        'number'
    ];
    var createTable= function (name,input,table) {
        if (name == 'duty') {
            table.find("table").bootstrapTable({
                showRefresh: true,
                search: true,
                showColumns: true,
                url: getRootPath() + '/duty/queryDuties.do',
                method: 'post',
                pagination: true,
                sidePagination: "server",
                columns: [{
                    field: '_id',
                    title: '职务代码',
                    formatter: function (value, row, index) {
                        if (value == null) {
                            return null;
                        }
                        return value["$oid"];
                    },
                    visible: false,
                    align: 'center'
                }, {
                    field: 'duty_name',
                    title: '职务名称',
                    sortable: true,
                    align: 'center'
                }, {
                    field: 'duty_flag',
                    title: '标志',
                    visible: false,
                    align: 'center'
                }, {
                    field: 'department_name',
                    title: '所在部门',
                    sortable: true,
                    align: 'center'
                }, {
                    field: 'department_code',
                    title: '部门代码',
                    visible: false,
                    align: 'center'
                }],
                onClickRow: function (row) {
                    input.val(row.duty_name)
                }
            })
        } else if (name == "department") {
            table.find("table").bootstrapTable({
                method: 'post',
                url: getRootPath() + '/department/queryPagination.do',
                sidePagination: "server",
                showRefresh: true,
                search: true,
                showColumns: true,
                pagination: true,
                columns: [{
                    field: '_id',
                    title: '部门代码',
                    formatter: function (value, row, index) {
                        if (value == null) {
                            return null;
                        }
                        return value["$oid"];
                    },
                    visible: false,
                    align: 'center'
                }, {
                    field: 'department_abbr_name',
                    title: '部门简称',
                    visible: false,
                    align: 'center'
                }, {
                    field: 'department_full_name',
                    title: '部门全称',
                    align: 'center'
                }, {
                    field: 'department_parent_full_name',
                    title: '上级部门'
                }, {
                    field: 'department_tell',
                    title: '部门电话',
                    visible: false,
                    align: 'center'
                }, {
                    field: 'department_fax',
                    title: '部门传真',
                    visible: false,
                    align: 'center'
                }],
                onClickRow: function (row) {
                    input.val(row.department_abbr_name)
                }
            })
        } else if (name == "room") {
            table.find("table").bootstrapTable({
                showRefresh: true,
                search: true,
                showColumns: true,
                url: getRootPath() + '/meetingRoom/queryPagination.do',
                method: 'post',
                pagination: true,
                sidePagination: "server",
                columns: [{
                    field: '_id',
                    title: '会议室代码',
                    formatter: function (value, row, index) {
                        if (value == null) {
                            return null;
                        }
                        return value["$oid"];
                    },
                    visible: false,
                    align: "center"
                }, {
                    field: 'room_name',
                    title: '会议室名称',
                    visible: true,
                    align: "center"
                }, {
                    field: 'capacity',
                    title: '可容纳人数',
                    sortable: true,
                    visible: true,
                    align: "center"
                }, {
                    field: 'position',
                    title: '会议室位置',
                    sortable: true,
                    visible: true,
                    align: "center"
                }, {
                    field: 'arrange',
                    title: '布置要求',
                    sortable: true,
                    visible: false,
                    align: "center"
                }, {
                    field: 'equipment',
                    title: '设备要求',
                    sortable: true,
                    visible: false,
                    align: "center"
                }],
                onClickRow: function (row) {
                    input.val(row.room_name)
                }
            })
        } else if(name == "template"){
            table.find("table").bootstrapTable({
                method: 'post',
                url: getRootPath() + '/WorkFlowManager/queryAllTemplate.do',
                sidePagination: "server",
                showRefresh: true,
                search: true,
                showColumns: true,
                pagination: true,
                columns: [{
                    field: '_id',
                    title: '模板代码',
                    formatter: function (value, row, index) {
                        if (value == null) {
                            return null;
                        }
                        return value["$oid"];
                    },
                    visible: false,
                    align:'center',
                    sortable: true
                }, {
                    field: 'title',
                    title: '模板标题',
                    align:'center',
                    sortable: true
                }],
                onClickRow: function (row) {
                    input.val(row.title)
                }
            })
        }else if(name=='employee'){
            table.find("table").bootstrapTable({
                showRefresh: true,
                search: true,
                showColumns: true,
                url: getRootPath() + '/employees/queryPagination.do',
                method: 'post',
                pagination: true,
                sidePagination: "server",
                columns: [{
                    field: '_id',
                    title: '员工代码',
                    formatter: function (value, row, index) {
                        if (value == null) {
                            return null;
                        }
                        return value["$oid"];
                    },
                    visible: false,
                    sortable: true,
                    align: 'center'
                }, {
                    field: 'epy_login',
                    title: '用户名',
                    visible: false,
                    sortable: true,
                    align: 'center'
                }, {
                    field: 'username',
                    title: '中文名',
                    visible: true,
                    sortable: true,
                    align: 'center'
                }, {
                    field: 'company',
                    title: '所属公司',
                    sortable: true,
                    visible: false,
                    align: 'center'
                },{
                    field: 'duty_name',
                    title: '职务',
                    sortable: true,
                    visible: true,
                    align: 'center'
                }, {
                    field: 'department_name',
                    title: '所在部门',
                    sortable: true,
                    visible: true,
                    align: 'center'
                }, {
                    field: 'epy_gender',
                    title: '性别',
                    visible: false,
                    sortable: true,
                    align: 'center'
                }, {
                    field: 'epy_birthday',
                    title: '出生日期',
                    visible: false,
                    sortable: true,
                    align: 'center'
                }, {
                    field: 'epy_cadr',
                    title: '通讯地址',
                    visible: false,
                    sortable: true,
                    align: 'center'
                }, {
                    field: 'epy_zcode',
                    title: '邮编',
                    visible: false,
                    sortable: true,
                    align: 'center'
                }, {
                    field: 'epy_tel',
                    title: '电话',
                    visible: false,
                    sortable: true,
                    align: 'center'
                }, {
                    field: 'epy_mob',
                    title: '手机',
                    visible: false,
                    sortable: true,
                    align: 'center'
                }, {
                    field: 'epy_ema',
                    title: '电子邮箱',
                    visible: false,
                    sortable: true,
                    align: 'center'
                }, {
                    field: 'epy_hmob',
                    title: '家庭电话',
                    visible: false,
                    sortable: true,
                    align: 'center'
                }, {
                    field: 'epy_hadr',
                    title: '家庭地址',
                    visible: false,
                    sortable: true,
                    align: 'center'
                }, {
                    field: 'epy_epstat',
                    title: '在职状态',
                    visible: false,
                    sortable: true,
                    align: 'center'
                }, {
                    field: 'epy_desc',
                    title: '备注',
                    visible: false,
                    sortable: true,
                    align: 'center'
                }],
                onClickRow: function (row) {
                    input.val(row.username)
                }
            })
        } else if(name=="user"){
            table.find("table").bootstrapTable({
                showRefresh: true,
                search: true,
                showColumns: true,
                url: getRootPath() + '/employees/queryEmployees.do',
                method: 'post',
                pagination: true,
                sidePagination: "server",
                columns: [{
                    field: 'uuid',
                    title: '编号',
                    visible:false
                }, {
                    field: 'epid',
                    title: '用户名'
                }, {
                    field: 'username',
                    title: '中文名'
                } ],
                onClickRow: function (row) {
                    input.val(row.epid)
                }
            });
        }
        else if(name=="period"){
            table.find("table").bootstrapTable({
                showRefresh: true,
                search: true,
                showColumns: true,
                url: getRootPath() + '/project/queryPagination.do?title=合约阶段',
                method: 'post',
                pagination: true,
                sidePagination: "server",
                columns: [{
                    field: 'phase_number',
                    title: '合约阶段'
                }, {
                    field: 'phase_type',
                    title: '阶段类型'
                }, {
                    field: 'phase_name',
                    title: '阶段名称'
                } ],
                onClickRow: function (row) {
                    input.val(row.phase_number)
                }
            });
        }
        else if(name=="number"){
            table.find("table").bootstrapTable({
                showRefresh: true,
                search: true,
                showColumns: true,
                url: getRootPath() + '/WorkFlowManager/queryDocuments.do?title=供应商合约',
                method: 'post',
                pagination: true,
                sidePagination: "server",
                columns: [{
                    field: 'sheet_number',
                    title: '合约编号'
                }, {
                    field: 'contract_category',
                    title: '合约类别'
                }, {
                    field: 'contract_name',
                    title: '合约名称'
                } , {
                    field: 'contract_amount',
                    title: '合约金额'
                }, {
                    field: 'payee',
                    title: '收款单位'
                } , {
                    field: 'amount_paid',
                    title: '付款金额'
                } ],
                onClickRow: function (row) {
                    input.val(row.sheet_number)
                }
            });
        }
    };
    $.fn.extend({
        /**
         * 使用 jBox toolTip 的方式开窗
         * 调用示例：$("#duty").openTip("duty")
         * 调用示例：$("#duty").openTip("duty",$("#duty_name"))
         * 调用示例：$("#duty").openTip("duty",null,function(row){})
         * @param name 开窗表名
         * @param input 待录入的输入框（jquery对象）
         * @param callBack 开窗中单击事件发生后的回调方法，带一个参数，可根据回调方法向表单中的其他对象赋值
         */
        openTip: function (name, input, callBack) {
            input = input || $(this);
            var table = container.clone(), toolTip;
            createTable(name,input,table);
            table.on("click-row.bs.table", function (a,row) {
                toolTip.close();
                if (callBack != null) {
                    callBack(row)
                }
            });
            toolTip = $(this).jBox('Tooltip', {
                content: table,
                target: input,
                closeOnEsc: true,
                trigger: 'click',
                maxWidth: 500,
                maxHeight: 400,
                position: {
                    y: 'bottom'
                },
                onOpen: function () {
                    $("body").trigger("box.open",[table])
                },
                onClose: function () {
                    $("body").trigger("box.close",[table])
                }
            });
            return toolTip
        },
        /**
         * 使用 jBox modal 的方式开窗
         * @param name 开窗表名
         * @param input 待待录入的输入框（jquery对象）
         * @param callBack 开窗中单击事件发生后的回调方法，带一个参数，可根据回调方法向表单中的其他对象赋值
         */
        openModal: function (name, input, callBack) {
            input = input || $(this);
            var table = container.clone(),modal;
            createTable(name,input,table);
            table.on("click-row.bs.table", function (a,row) {
                modal.close();
                if (callBack != null) {
                    callBack(row)
                }
            });
            modal=new jBox("Modal", {
                closeOnEsc: true,
                closeButton: "box",
                content:table,
                maxWidth: 600,
                maxHeight: 400,
                blockScroll:false,
                overlay: false,
                onOpen: function () {
                    $("body").trigger("box.open",[table])
                },
                onClose: function () {
                    $("body").trigger("box.close",[table])
                }
            });
            $(this).click(function () {
                modal.open()
            });
            return modal
        }
    });
    $.extend({
        /**
         * 用于快速创建通知消息，调用示例：$.showNotice("保存成功","green")
         * @param message 通知中的消息
         * @param color 通知颜色
         */
        showNotice: function (message, color) {
            $("body").jBox("Notice",{
                content: message,
                color: color,
                stack: true,
                autoClose: 2000,
                fade: 300,
                onOpen: function () {
                    $("body").trigger("notice.open")
                },
                onClose: function () {
                    $("body").trigger("notice.close")
                }
            })
        }
    })
});