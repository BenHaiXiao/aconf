/**
 * @author xiaobenhai
 * Date: 2017/1/16
 * Time: 16:31
 */
var branchCount = 0;
var editDialog;
var jsonEditDialog;
var resEditor;

var deletedFilters = [];
var deletedConditions = [];

// $.fn.steps.reset = function () {
//     var wizard = this,
//         options = getOptions(this),
//         state = getState(this);
//     if(state.currentIndex !== 0) {
//         goToStep(wizard, options, state, 0);
//     }
//
//     for (i = 1; i < state.stepCount; i++) {
//         var stepAnchor = getStepAnchor(wizard, i);
//         stepAnchor.parent().removeClass("done")._enableAria(false);
//     }
// };

var gridColumns = [
    {id: 'id', title: 'id', type: 'number', columnClass: 'text-center', width: '10px', hideType: 'lg|md|sm|xs'},
    {id: 'key', title: '键', type: 'string', columnClass: 'text-center', width: '100px'},
    {
        id: 'value', title: '默认值', type: 'string', columnClass: 'text-center', width: '10%',
        resolution: function (value, record, column, grid, dataNo, columnNo) {
            var v = value;
            if (record.valueType == 1) {
                v = "${" + v + "}";
            }
            if (value.length > 50) {
                v = value.slice(0, 49) + "...";
            }
            return v;
        }
    },
    {
        id: 'sendDefault', title: '下发默认值', type: 'string', columnClass: 'text-center',
        resolution: function (value, record, column, grid, dataNo, columnNo) {
            var content = '';
            if (!record.conditions) {
                return content;
            }
            var valid = parseInt(value);
            if (valid == 1) {
                content = "<span class='label label-warning'>下发</span>"
            } else if (valid == 0) {
                content = "<span class='label label-default'>不下发</span>"
            }
            return content;
        }
    },
    {
        id: 'conditions', title: '条件分支', type: 'string', columnClass: 'text-center',
        extra: false,
        resolution: function (value, record, column, grid, dataNo, columnNo) {
            var content = '';
            for (var i = 0; i < value.length; i++) {
                var v = value[i].value;
                if (v.length > 20) {
                    v = v.slice(0, 19) + "...";
                }
                content += '<div style="margin: 10px 0;"><span style="margin: 0.8em 0;display:inline-block;" class="label label-success">' + value[i].name + '</span> --&gt; <span class="label label-default">' + v + '</span></div>';
            }
            return content;
        }
    },
    {
        id: 'description', title: '描述', type: 'string', columnClass: 'text-center', hideType: 'lg|md|sm|xs',
        resolution: function (value, record, column, grid, dataNo, columnNo) {
            if (value) {
                return '<pre>' + value + '</pre>';
            }
            return '';
        }
    },
    {
        id: 'creator', title: '上次操作人', type: 'string', columnClass: 'text-center', hideType: 'md|sm|xs',
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
    {
        id: 'updateTime',
        title: '更新时间',
        type: 'date',
        format: 'yyyy-MM-dd hh:mm:ss',
        otype: 'time_stamp_ms',
        columnClass: 'text-center',
        hideType: 'md|sm|xs'
    },
    {id: 'version', title: '版本号', type: 'number', columnClass: 'text-center', hideType: 'md|sm|xs'},
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
        content += '<button class="btn btn-xs btn-primary" onclick="openDetail(' + record.id + ')"><i class="fa fa-book"></i> 详情</button>';
        content += '  ';
        if ($('#canbeEdit').val() == '1') {
        	content += '<button class="btn btn-xs btn-info" onclick="editConfig(' + record.id + ')"><i class="fa fa-edit"></i> 编辑</button>';
        	content += '  ';
        	content += '<button class="btn btn-xs btn-danger" onclick="delBss(' + record.id + ')"><i class="fa fa-trash-o"></i> 删除</button>';
        }
        return content;
    }
    }
];

function openDetail(id) {
    var bssid = $('#bssid').val();
    var appid = $('#appid').val();
    var token = $('#token').val();
    window.location.href = '/config/' + id + '/view?bssid=' + bssid + '&appid=' + appid + '&token=' + token;
}

function delBss(id) {
    var dialog = layer.confirm('您确认要删除吗？', {
        btn: ['确定', '取消']
    }, function () {
        var bssId = $('#bssid').val();
        $.ajax({
            url: "/bss/" + bssId + "/config/" + id + "/v2",
            method: "delete",
            dataType: "json",
            success: function (data) {
                if (data && data.code == 200) {
                    grid.refresh(true);
                    layer.close(dialog);
                }
            }
        })
    }, function () {

    });
}

function editConfig(id) {
    $('#branchContainer').empty();
    $.ajax({
        url: "/config/" + id,
        method: "get",
        dataType: "json",
        success: function (data) {
            if (data && data.code === 200) {
                var result = data.data;
                branchCount = result.conditions.length;
                result.basisList = basisList;
                var html = template('configAddTemplate', result);
                $('#branchContainer').append(html);

                $('#configAddName').val(result.key);
                $('#configSendDefault').val(result.sendDefault);
                $('#configDefaultValue').val(result.value);
                $('#configAddDesc').val(result.description);
                $('#configAddId').val(result.id);

                deletedFilters = [];
                deletedConditions = [];

                $('#add-dialog-wrapper').removeClass("hide");
                editDialog = layer.open({
                    type: 1,
                    title: '编辑配置项',
                    shadeClose: false,
                    shade: 0.8,
                    scrollbar: false,
                    area: ['75%', '80%'],
                    content: $('#add-dialog-wrapper')
                });
                $('[data-toggle="tooltip"]').tooltip();
                $('a[data-tools="editor"]').toolbar({
                    content: '#editor-toolbar',
                    position: 'top',
                    style: 'dark',
                    zIndex: 19891025,
                    adjustment: 20
                });
            }
        }
    });
}

var gridOption = {
    lang: 'zh-cn',
    ajaxLoad: true,
    loadAll: false,
    loadURL: '/config/search',
    exportFileName: '自定义关键词词库列表',
    columns: gridColumns,
    gridContainer: 'gridContainer',
    toolbarContainer: 'gridToolBar',
    tools: "",
    pageSize: 50,
    pageSizeLimit: [20, 50, 100]
};

var grid = $.fn.dlshouwen.grid.init(gridOption);

function bindAction() {
    $('#configAddBtn').on('click', function () {
        $('#add-dialog-wrapper').removeClass("hide");
        $('#branchContainer').empty();
        branchCount = 0;
        //reset
        $('#configAddName').val("");
        $('#configSendDefault').val(1);
        $('#configDefaultValue').val("");
        $('#configAddDesc').val("");
        $('#configAddId').val("");
        deletedFilters = [];
        deletedConditions = [];
        editDialog = layer.open({
            type: 1,
            title: '编辑配置项',
            shadeClose: false,
            shade: 0.8,
            scrollbar: false,
            area: ['75%', '80%'],
            content: $('#add-dialog-wrapper')
        });
        $('[data-toggle="tooltip"]').tooltip();
        $('a[data-tools="editor"]').toolbar({
            content: '#editor-toolbar',
            position: 'top',
            style: 'dark',
            zIndex: 19891025,   //为了覆盖在layer之上
            adjustment: 20
        });
    });
    $('#configSearchBtn').on('click', function () {
        grid.parameters = {};
        grid.parameters['bssid'] = $('#bssid').val();
        grid.parameters['config_id'] = $('#configId').val();
        grid.parameters['name'] = $('#configName').val();
        grid.parameters['state'] = $('#configState').val();
        grid.refresh(true);
    });
    $('#configResetBtn').on('click', function () {
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
    grid.parameters['bssid'] = $('#bssid').val();
    grid.load();
});