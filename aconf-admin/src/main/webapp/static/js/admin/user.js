//@author xiaobenhai on 2017/2/17.

var codeTable = ["超级管理员", "管理员", "普通用户"];
var bssList;
var gridColumns = [
    {id: 'name', title: '姓名', type: 'string', columnClass: 'text-center'},
    {id: 'passport', title: '通行证', type: 'string', columnClass: 'text-center'},
    {id: 'jobCode', title: '工号', type: 'string', columnClass: 'text-center', hideType: 'lg|md|sm|xs'},
    // {id: 'tester', title: 'YY号', type: 'string', columnClass: 'text-center', hideType: 'lg|md|sm|xs'},
    {id: 'telephone', title: '手机号', type: 'string', columnClass: 'text-center', hideType: 'lg|md|sm|xs'},
    {id: 'email', title: 'E-mail', type: 'string', columnClass: 'text-center'},
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
        hideType: 'sm|xs'
    },
    {
        id: 'role',
        title: '身份',
        type: 'string',
        columnClass: 'text-center',
        resolution: function (value, record, column, grid, dataNo, columnNo) {
            var content = '';

            if (value >= 0) {
                content += '<label class="label label-success">' + codeTable[value] + '</label>';
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
        content += '<button class="btn btn-xs btn-info" onclick="editUser(' + record.id + ')"><i class="fa fa-edit"></i> 编辑</button>';
        content += '  ';
        if (record.state == 1) {
            content += '<button class="btn btn-xs btn-danger" onclick="delUser(' + record.id + ')"><i class="fa fa-trash-o"></i> 禁用</button>';
        } else {
            content += '<button class="btn btn-xs btn-primary" onclick="enableUser(' + record.id + ')"><i class="fa fa-trash-o"></i> 启用</button>';
        }
        return content;
    }
    }
];

function editUser(userId) {
    $.ajax({
        url: '/user/' + userId,
        method: "GET",
        dataType: "json",
        success: function (data) {
            if (data && data.data) {
                var result = data.data;
                //填入原值
                $('#userId').val(userId);
                $('#addPassport').val(result.passport);
                $('#addPassport').attr("disabled", "disabled");
                $('#addRole').val(result.role);
                $('#addPhone').val(result.telephone);
                //设置bss权限           
                var roleSplit = result.bssRole.split(",");
                $bssRoleCheckboxes = $('[name="bssrole"]');
                $bssRoleCheckboxes.each(function(){
            		var $check = $(this);
            		$check.prop('checked', false);
            		$.each(roleSplit, function(k, v){
            			if($check.val() == v){
            				$check.prop('checked', true);
            			}
            		});
            	});
            	
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

function delUser(userId) {
    $.ajax({
        url: '/user/' + userId,
        method: 'delete',
        success: function (data) {
            if (data && data.code == 200) {
                toastr.success("禁用用户成功", "操作成功");
                grid.refresh(true);
            }
        }
    })
}

function loadBssList() {
    $.get("/bss-list", function (data) {
        bssList = data.data;
        if (!bssList) {
            return;
        }
        var bsslist1 = $("#bssRoleId");       
        var bsslist2 = $("#listbss");
        var count = 0;
        bsslist2.append("<option value=''>选择指定业务权限</option>");
        for(var i in bssList){
        	count++;
            bsslist1.append('<input name="bssrole" type="checkbox" value="'+bssList[i].value+'">'+bssList[i].name+'&nbsp;&nbsp;&nbsp;');
            if(count%3==0){
            	bsslist1.append('<br/>');
            }
            bsslist2.append("<option value="+bssList[i].value+">"+bssList[i].name+"</option>");
        }
    });
}

function enableUser(userId) {
    $.ajax({
        url: '/user/' + userId + '/enable',
        method: 'put',
        success: function (data) {
            if (data && data.code == 200) {
                toastr.success("启用用户成功", "操作成功");
                grid.refresh(true);
            }
        }
    })
}

function bssCheckboxAll() {
	if($('[name="allbssrole"]').prop('checked') == true){
		$('[name="bssrole"]').each(function(){
			$(this).prop('checked', true);
	    });		
	}else{
		$('[name="bssrole"]').each(function(){
			$(this).prop('checked', false);
		});
	}
}

var gridOption = {
    lang: 'zh-cn',
    ajaxLoad: true,
    loadAll: false,
    loadURL: '/user/search',
    exportFileName: '自定义关键词词库列表',
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

function bindAction() {
    $('#userAddBtn').on('click', function () {
        $('#addDialogWrapper').removeClass("hide");
        //reset
        $('#addPassport').val("");
        editDialog = layer.open({
            type: 1,
            title: '编辑用户信息',
            shadeClose: false,
            shade: 0.8,
            scrollbar: false,
            area: ['40%', '60%'],
            content: $('#addDialogWrapper')
        });
    });
    $('#userSearchBtn').on('click', function () {
        grid.parameters = {};
        grid.parameters['passport'] = $('#userPassport').val();
        grid.parameters['name'] = $('#userName').val();
        grid.parameters['state'] = $('#userState').val();
        grid.parameters['bss'] = $('#listbss').val();
        grid.refresh(true);
    });
    $('#oldUserBtn').on('click', function () {
        window.location.href = "/old-user";
    })
}

$(function () {
    init();
    bindAction();
    grid.parameters = {};
    grid.load();
    loadBssList();
});
