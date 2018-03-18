/**
 * Created by Administrator on 2017/2/6.
 */
var gridColumns = [
    {id: 'id', title: 'id', type: 'number', columnClass: 'text-center', hideType: 'xs'},
    {id: 'title', title: '标题', type: 'string', columnClass: 'text-center'},
    {id: 'description', title: '描述', type: 'string', columnClass: 'text-center', hideType: 'lg|md|sm|xs'},
    {
        id: 'creator', title: '操作人', type: 'string', columnClass: 'text-center', hideType: 'md|sm|xs',
        extra: false,
        resolution: function (value, record, column, grid, dataNo, columnNo) {
            if (value && value.name) {
                var _class = 'user-detail';
                if (dataNo == 0 || dataNo == 1) {
                    _class = 'pull-down';
                }
                var name = value.name ? value.name : "无名氏";
                var email = value.email ? value.email : "未填写";
                var avatar = value.avatar ? value.avatar : "/static/img/profile.jpg";
                var phone = value.phone ? value.phone : '未填写';
                var userDetail = '<div class="' + _class + '">' +
                    '<div class="contact-box">' +
                    '<div class="col-sm-4">' +
                    '<div class="text-center">' +
                    '<img alt="image" class="img-circle m-t-xs img-responsive" src="' + avatar + '">' +
                    '</div>' +
                    '</div>' +
                    '<div class="col-sm-8">' +
                    '<h3><strong>' + name + '</strong></h3>' +
                    '<address>' +
                    'E-mail:<a href="mailto:' + email + '">' + email + '</a><br>' +
                    '<abbr title="Phone">Tel:</abbr><a href="tel:' + phone + '">' + phone +
                    '</a></address>' +
                    '</div>' +
                    '<div class="clearfix"></div>' +
                    '</div>' +
                    '</div>';
                return '<span class="userName">' + userDetail + value.name + '</span>';
            }
            return '';
        }
    },
    {id: 'sids', title: '频道', type: 'string', columnClass: 'text-center'},
    {
        id: 'sys',
        title: '平台',
        type: 'string',
        columnClass: 'text-center',
        resolution: function (value, record, column, grid, dataNo, columnNo) {
            var content = '';
            var valid = parseInt(value);
            if (valid == 0) {
                content = "全平台"
            } else if (valid == 1) {
                content = "PC端"
            } else if (valid == 2) {
                content = "移动端"
            }
            return content;
        }
    },
    {
        id: 'effectiveTime',
        title: '触发时间',
        type: 'date',
        format: 'yyyy-MM-dd hh:mm:ss',
        otype: 'time_stamp_ms',
        columnClass: 'text-center'
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
        id: 'updateTime',
        title: '更新时间',
        type: 'date',
        format: 'yyyy-MM-dd hh:mm:ss',
        otype: 'time_stamp_ms',
        columnClass: 'text-center',
        hideType: 'md|sm|xs'
    },
    {
        id: 'state',
        title: '状态',
        type: 'string',
        columnClass: 'text-center',
        hideType: 'xs',
        resolution: function (value, record, column, grid, dataNo, columnNo) {
            var content = '';
            var valid = parseInt(value);
            if (valid == 101) {
                content = "<span class='label label-warning'>待推送</span>"
            } else if (valid == 103) {
                content = "<span class='label label-default'>已推送</span>"
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
        // content += '<button class="btn btn-xs btn-primary" onclick=""><i class="fa fa-book"></i> 详情</button>';
        // content += '  ';
        content += '<button class="btn btn-xs btn-info" onclick="editPush(' + record.id + ')"><i class="fa fa-edit"></i> 编辑</button>';
        content += '  ';
        content += '<button class="btn btn-xs btn-danger" onclick="delPush(' + record.id + ')"><i class="fa fa-trash-o"></i> 删除</button>';
        return content;
    }
    }
];

var gridOption = {
    lang: 'zh-cn',
    ajaxLoad: true,
    loadAll: false,
    loadURL: '/push/search',
    exportFileName: '广播列表',
    columns: gridColumns,
    gridContainer: 'gridContainer',
    toolbarContainer: 'gridToolBar',
    tools: "",
    pageSize: 10,
    pageSizeLimit: [10, 20, 50]
};

var grid = $.fn.dlshouwen.grid.init(gridOption);

function delPush(id) {
    if (!id) {
        return false;
    }
    parent.layer.confirm("你确定要删除该广播吗？", function () {
        $.ajax({
            url: '/push/' + id,
            method: "DELETE",
            dataType: "json",
            success: function (data) {
                if (data && data.code == 200) {
                    parent.layer.closeAll();
                    toastr.success("广播已成功删除", "操作成功");
                    grid.refresh(true);
                }
            }
        })
    }, function () {

    });
}

/**
 * 编辑广播条目。在新弹出的模态框中进行编辑（与新建共用同一套模板）
 * @param id pushID
 */
function editPush(id) {
    $.ajax({
        url: '/push/' + id,
        method: "GET",
        dataType: "json",
        success: function (data) {
            if (data && data.data) {
                var result = data.data;
                //填入原值
                $('#pushId').val(id);
                $('#pushAddTitle').val(result.title);
                $('#pushAddSids').val(result.sids);
                $('#pushAddSys').val(result.sys);
                var effectiveTime = new Date(result.effectiveTime);
                $('#pushAddTime').val(effectiveTime.Format("yyyy-MM-dd hh:mm:ss"));
                //打开模态框
                $('#addDialogWrapper').removeClass("hide");
                editDialog = layer.open({
                    type: 1,
                    title: '编辑广播',
                    shadeClose: false,
                    shade: 0.8,
                    scrollbar: false,
                    area: ['50%', '50%'],
                    content: $('#addDialogWrapper')
                });
            }
        },
        error: function () {
            toastr.error("网络问题，请稍后重试", "操作失败");
        }
    })
}

function bindAction() {
    $('#pushAddBtn').on('click', function () {
        $('#addDialogWrapper').removeClass("hide");
        //reset
        $('#pushAddTitle').val("");
        $('#pushAddSids').val("");
        $('#pushAddSys').val("");
        $('#pushAddEffectiveTime').val("");
        editDialog = layer.open({
            type: 1,
            title: '编辑广播',
            shadeClose: false,
            shade: 0.8,
            scrollbar: false,
            area: ['50%', '50%'],
            content: $('#addDialogWrapper')
        });
    });
    $('#pushSearchBtn').on('click', function () {
        grid.parameters = {};
        grid.parameters['bssid'] = $('#bssId').val();
        grid.parameters['title'] = $('#pushTitle').val();
        grid.parameters['channel'] = $('#pushChannel').val();
        grid.parameters['state'] = $('#pushState').val();
        grid.refresh(true);
    });
    $('#pushResetBtn').on('click', function () {
        $('#configCode').val("");
        $('#configName').val("");
        $('#configState').val(1);
    });
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
    grid.parameters['bssid'] = $('#bssId').val();
    grid.load();
});