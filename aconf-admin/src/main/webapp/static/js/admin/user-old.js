/**
 * Created by Administrator on 2017/3/14.
 */

var gridColumns = [
    {id: 'realName', title: '姓名', type: 'string', columnClass: 'text-center'},
    {id: 'passport', title: '通行证', type: 'string', columnClass: 'text-center'},
    {id: 'jobCode', title: '工号', type: 'string', columnClass: 'text-center', hideType: 'lg|md|sm|xs'},
    {id: 'deptName', title: '小组', type: 'string', columnClass: 'text-center', hideType: 'md|sm|xs'},
    {id: 'email', title: 'E-mail', type: 'string', columnClass: 'text-center'},
    {
        id: 'operation',
        title: '操作',
        type: 'string',
        columnClass: 'text-center', resolution: function (value, record, column, grid, dataNo, columnNo) {
        var content = '';
        content += '<button class="btn btn-xs btn-info" onclick="activeUser(\'' + record.passport + '\')"><i class="fa fa-edit"></i> 激活</button>';
        return content;
    }
    }
];

/**
 * 激活用户(调用了新用户接口）
 * @param passport 用户通行证
 */
function activeUser(passport) {
    var data = {
        passport: passport,
        role: 2
    };
    $.ajax({
        url: '/user',
        method: 'post',
        dataType: 'json',
        contentType: 'application/json',
        data: JSON.stringify(data),
        success: function (data) {
            if (data && data.code == 200) {
                toastr.success("新增用户成功", "操作成功");
                grid.refresh(true);
            }
        },
        error: function () {
            layer.alert("网络错误，请稍后重试");
        }
    });
}

var gridOption = {
    lang: 'zh-cn',
    ajaxLoad: true,
    loadAll: false,
    loadURL: '/user-old/search',
    exportFileName: '旧系统用户清单',
    columns: gridColumns,
    gridContainer: 'gridContainer',
    toolbarContainer: 'gridToolBar',
    tools: "",
    pageSize: 100,
    pageSizeLimit: [20, 50, 100]
};

var grid = $.fn.dlshouwen.grid.init(gridOption);

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
    grid.parameters = {};
    grid.load();
});
