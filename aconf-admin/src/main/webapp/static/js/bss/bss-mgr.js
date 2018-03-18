/**
 * @author xiaobenhai
 * Date: 2017/1/11
 * Time: 15:42
 */
var editDialog;

var gridColumns = [
    {id: 'name', title: '名称', type: 'string', columnClass: 'text-center'},
    {id: 'code', title: '业务代号', type: 'string', columnClass: 'text-center'},
    {id: 'developer', title: '开发人员', type: 'string', columnClass: 'text-center', hideType: 'lg|md|sm|xs'},
    {id: 'tester', title: '测试人员', type: 'string', columnClass: 'text-center', hideType: 'lg|md|sm|xs'},
    {id: 'operator', title: '运营人员', type: 'string', columnClass: 'text-center', hideType: 'lg|md|sm|xs'},
    {
        id: 'description',
        title: '描述',
        type: 'string',
        columnClass: 'text-center',
        hideType: 'lg|md|sm|xs'
    },
    {
        id: 'createTime',
        title: '创建时间',
        type: 'date',
        format: 'yyyy-MM-dd hh:mm:ss',
        otype: 'time_stamp_ms',
        columnClass: 'text-center',
        hideType: 'lg|md|sm|xs'
    },
    {
        id: 'effectiveType',
        title: '类型',
        type: 'number',
        columnClass: 'text-center',
        resolution: function (value, record, column, grid, dataNo, columnNo) {
            var content = '';
            var valid = parseInt(value);
            if (valid === 0) {
                content = "<span class='label label-success'>永久有效</span>"
            }
            return content;
        }
    },
    {
        id: 'effectiveTime',
        title: '生效时间',
        type: 'date',
        format: 'yyyy-MM-dd hh:mm:ss',
        otype: 'string',
        oformat: 'yyyy-MM-dd hh:mm:ss',
        columnClass: 'text-center',
        hideType: 'lg|md|sm|xs'
    },
    {
        id: 'failureTime',
        title: '失效时间',
        type: 'date',
        format: 'yyyy-MM-dd hh:mm:ss',
        otype: 'string',
        oformat: 'yyyy-MM-dd hh:mm:ss',
        columnClass: 'text-center',
        hideType: 'lg|md|sm|xs'
    },
    {
        id: 'updateTime',
        title: '更新时间',
        type: 'date',
        format: 'yyyy-MM-dd hh:mm:ss',
        otype: 'time_stamp_ms',
        columnClass: 'text-center',
        hideType: 'lg|md|sm|xs'
    },
    {id: 'version', title: '业务版本号', type: 'number', columnClass: 'text-center', hideType: 'md|sm|xs'},
    {
        id: 'valid',
        title: '状态',
        type: 'string',
        columnClass: 'text-center',
        resolution: function (value, record, column, grid, dataNo, columnNo) {
            var content = '';
            var valid = parseInt(value);
            if (valid == 1) {
                content = "<span class='label label-warning'>使用中</span>"
            } else if (valid == 2) {
                content = "<span class='label label-default'>未生效</span>"
            } else if (valid == 3) {
                content = "<span class='label label-default'>已过期</span>"
            } else if (valid == 0) {
                content = "<span class='label label-default'>已删除</span>"
            }
            return content;
        }
    },
    {
        id: 'operation',
        title: '操作',
        type: 'string',
        columnClass: 'text-center', resolution: function (value, record, column, grid, dataNo, columnNo) {
        var content = '';
        content += '<button class="btn btn-xs btn-primary" onclick="openBss(' + record.id + ');"><i class="fa fa-book"></i> 打开业务</button>';
        content += '  ';
        if ($('#roleType').val() == '1') {
            content += '<button class="btn btn-xs btn-info" onclick="editBss(' + record.id + ')"><i class="fa fa-edit"></i> 编辑</button>';
            content += '  ';
            content += '<button class="btn btn-xs btn-danger" onclick="delBss(' + record.id + ',' + record.valid + ')"><i class="fa fa-trash-o"></i> 删除</button>';
        }
        return content;
    }
    }
];

var gridOption = {
    lang: 'zh-cn',
    ajaxLoad: true,
    loadAll: false,
    loadURL: '/bss/search',
    exportFileName: '自定义关键词词库列表',
    columns: gridColumns,
    gridContainer: 'gridContainer',
    toolbarContainer: 'gridToolBar',
    tools: "",
    pageSize: 100,
    pageSizeLimit: [20, 50, 100]
};

var grid = $.fn.dlshouwen.grid.init(gridOption);

function openBss(bssid) {
    var token = $('#token').val();
    var appId = $('#appId').val();
    window.location.href = "/config-mgr?bssid=" + bssid + "&token=" + token + "&appid=" + appId;
}

function editBss(bssid) {
    $.ajax({
        url: '/bss/' + bssid,
        method: 'get',
        dataType: 'json',
        success: function (data) {
            if (data && data.code === 200) {
                var result = data.data;
                //填入原值
                $('#bssAddId').val(bssid);
                $('#bssAddName').val(result.name);
                $('#bssAddCode').val(result.code);
                $('#bssAddCode').attr("disabled", "disabled");
                $('#bssAddDesc').val(result.description);
                if (result.effectiveType === 0) {
                    $('#longTime').iCheck('check');
                    $('#effectiveTime').val('');
                    $('#failureTime').val('');
                } else {
                    $('#shortTime').iCheck('check');
                    $('#effectiveTime').val(result.effectiveTime);
                    $('#failureTime').val(result.failureTime);
                }
                //打开模态框
                $('#addDialogWrapper').removeClass("hide");
                editDialog = layer.open({
                    type: 1,
                    title: '编辑业务',
                    shadeClose: false,
                    shade: 0.8,
                    scrollbar: false,
                    area: ['50%', '50%'],
                    content: $('#addDialogWrapper')
                });
            }
        }
    })
}

function delBss(id, valid) {
    if (valid == 1) {
        parent.layer.alert('业务正在进行中，不能删除。');
        return;
    }
    if (valid == 0) {
        parent.layer.alert('业务已经删除，请勿重复操作。');
        return;
    }
    $.ajax({
        url: '/bss/' + id,
        method: 'DELETE',
        dataType: 'json',
        success: function (data) {
            if (!data) {
                toastr.error("可能是网络原因，请稍后重试，若重试无效，请联系系统维护人。", "操作失败");
                return;
            }
            if (data.code == 106) {
                toastr.error("id无效", "操作失败");
                return;
            }
            if (data.code == 108) {
                toastr.error("查无此业务", "操作失败");
                return;
            }
            if (data.code == 200) {
                toastr.success("业务已删除", "操作成功");
                grid.reload(true);
                return;
            }
            toastr.error("其他错误，请联系管理员", "操作失败");
        },
        error: function () {
            toastr.error("可能是网络原因，请稍后重试，若重试无效，请联系系统维护人。", "操作失败");
        }
    });
}

function bindAction() {
    $('#bssAddBtn').on('click', function () {
        $('#addDialogWrapper').removeClass("hide");
        //reset
        $('#bssAddId').val('');
        $('#bssAddName').val('');
        $('#bssAddCode').val('');
        $('#bssAddCode').removeAttrs("disabled");
        $('#bssAddDesc').val('');
        $('#longTime').iCheck('check');
        $('#effectiveTime').val('');
        $('#failureTime').val('');
        editDialog = layer.open({
            type: 1,
            title: '编辑业务',
            shadeClose: false,
            shade: 0.8,
            scrollbar: false,
            area: ['50%', '50%'],
            content: $('#addDialogWrapper')
        });
    });
    $('#bssSearchBtn').on('click', function () {
        grid.parameters = {};
        grid.parameters['appid'] = $('#appId').val();
        grid.parameters['code'] = $('#bssCode').val();
        grid.parameters['name'] = $('#bssName').val();
        grid.parameters['state'] = $('#bssState').val();
        grid.refresh(true);
    });
    $('#bssResetBtn').on('click', function () {
        $('#bssCode').val("");
        $('#bssName').val("");
        $('#bssState').val(8);
    })
}


function init() {
    toastr.options = {
        "closeButton": true,
        "debug": false,
        "progressBar": true,
        "positionClass": "toast-top-right",
        "onclick": null,
        "showDuration": "400",
        "hideDuration": "1000",
        "timeOut": "3000",
        "extendedTimeOut": "1000",
        "showEasing": "swing",
        "hideEasing": "linear",
        "showMethod": "fadeIn",
        "hideMethod": "fadeOut"
    };
}

$(function () {
    init();
    bindAction();
    grid.parameters = {};
    grid.parameters['appid'] = $('#appId').val();
    grid.load();
});