/**
 * Created by Administrator on 2017/3/1.
 */
var bssList;
var gridColumns = [
    {
        id: 'creator', title: '操作人', type: 'string', columnClass: 'text-center', width: '120px',
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
//                var userDetail = '<div class="' + _class + '">' +
//                    '<div class="contact-box">' +
//                    '<div class="col-sm-4">' +
//                    '<div class="text-center">' +
//                    '<img alt="image" class="img-circle m-t-xs img-responsive" src="' + avatar + '">' +
//                    '</div>' +
//                    '</div>' +
//                    '<div class="col-sm-8">' +
//                    '<h3><strong>' + name + '</strong></h3>' +
//                    '<address>' +
//                    'E-mail:<a href="mailto:' + email + '">' + email + '</a><br>' +
//                    '<abbr title="Phone">Tel:</abbr><a href="tel:' + phone + '">' + phone +
//                    '</a></address>' +
//                    '</div>' +
//                    '<div class="clearfix"></div>' +
//                    '</div>' +
//                    '</div>';
                //return '<span class="userName">' + userDetail + value.name + '</span>';
                return '<span class="userName">' + value.name + '</span>';
            }
            return '';
        }
    },    
    {id: 'params', title: '参数', type: 'string', columnClass: 'text-left'},
    {
        id: 'createTime',
        title: '操作时间',
        type: 'date',
        format: 'yyyy-MM-dd hh:mm:ss',
        otype: 'time_stamp_ms',
        columnClass: 'text-center',
        hideType: 'sm|xs',
        width: '110px'
    },
    {id: 'face', title: '接口', type: 'string', columnClass: 'text-center', width: '110px'}
];

var gridOption = {
    lang: 'zh-cn',
    ajaxLoad: true,
    loadAll: false,
    loadURL: '/log/search',
    exportFileName: '操作记录列表',
    columns: gridColumns,
    gridContainer: 'gridContainer',
    toolbarContainer: 'gridToolBar',
    tools: "",
    pageSize: 100,
    pageSizeLimit: [50, 100, 200]
};

function loadBssList() {
    $.get("/bss-list", function (data) {
        bssList = data.data;
        if (!bssList) {
            return;
        }
        var bsslist = $("#listbss");
        bsslist.append("<option value=''>请选择业务</option>");
        for(var i in bssList){
            bsslist.append("<option value="+bssList[i].value+">"+bssList[i].name+"</option>");
        }        
    });
}

var grid = $.fn.dlshouwen.grid.init(gridOption);

function bindAction() {
    $('#searchBtn').on('click', function () {
        grid.parameters = {};
        grid.parameters['passport'] = $('#passport').val();
        grid.parameters['startTime'] = $('#startTime').val();
        grid.parameters['endTime'] = $('#endTime').val();
        grid.parameters['content'] = $('#content').val();
        grid.parameters['listbss'] = $('#listbss').val();
        grid.refresh(true);
    });
    $('#resetBtn').on('click', function () {
        $('#passport').val("");
        $('#startTime').val("");
        $('#endTime').val("");
        $('#content').val("");
        $("#listbss").val("");
    })
}

$(function () {
    laydate({
        elem: '#startTime', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
        event: 'focus', //响应事件。如果没有传入event，则按照默认的click
        istime: true,
        format: 'YYYY-MM-DD hh:mm:ss', //日期格式
        festival: true //是否显示节日
    });

    laydate({
        elem: '#endTime', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
        event: 'focus', //响应事件。如果没有传入event，则按照默认的click
        istime: true,
        format: 'YYYY-MM-DD hh:mm:ss', //日期格式
        festival: true //是否显示节日
    });

    bindAction();
    grid.parameters = {};
    grid.load();
    loadBssList();
});
