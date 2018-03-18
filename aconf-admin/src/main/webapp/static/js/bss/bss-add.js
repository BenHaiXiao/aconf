/**
 * Created by Administrator on 2017/2/27.
 */
function saveBss() {
    var bssName = $('#bssAddName').val();
    var bssCode = $('#bssAddCode').val();
    var bssDesc = $('#bssAddDesc').val();
    var effectiveType = $('#timeArea').hasClass("hide") ? 0 : 1;
    var effectiveTime = $('#effectiveTime').val();
    var failureTime = $('#failureTime').val();
    var appId = $('#appId').val();
    var send = {
        name: bssName,
        code: bssCode,
        description: bssDesc,
        appId: appId,
        effectiveType: effectiveType,
        effectiveTime: effectiveTime,
        failureTime: failureTime
    };
    $.ajax({
        url: '/bss',
        method: "post",
        contentType: "application/json",
        data: JSON.stringify(send),
        dataType: "json",
        success: function (data) {
            if (data && data.code == 200) {
                layer.closeAll();
                toastr.success("业务添加成功。", "操作成功");
                grid.refresh(true);
            }
        }
    })
}

function updateBss(id) {
    var bssName = $('#bssAddName').val();
    var bssCode = $('#bssAddCode').val();
    var bssDesc = $('#bssAddDesc').val();
    var effectiveType = $('#timeArea').hasClass("hide") ? 0 : 1;
    var effectiveTime = $('#effectiveTime').val();
    var failureTime = $('#failureTime').val();
    var send = {
        name: bssName,
        code: bssCode,
        description: bssDesc,
        effectiveType: effectiveType,
        effectiveTime: effectiveTime,
        failureTime: failureTime
    };
    $.ajax({
        url: '/bss/' + id,
        method: "PUT",
        contentType: "application/json",
        data: JSON.stringify(send),
        dataType: "json",
        success: function (data) {
            if (data && data.code == 200) {
                layer.closeAll();
                toastr.success("更新成功。", "操作成功");
                grid.refresh(true);
            }
        }
    })
}


$(function () {
    $('body').on('ifChecked', 'input[name="effectiveType"]', function () {
        var value = $(this).val();
        if (value == 1) {
            $('#timeArea').removeClass("hide");
        } else {
            $('#timeArea').addClass("hide");
        }
    });
    laydate({
        elem: '#effectiveTime', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
        event: 'focus', //响应事件。如果没有传入event，则按照默认的click
        istime: true,
        format: 'YYYY-MM-DD hh:mm:ss', //日期格式
        festival: true //是否显示节日
    });
    laydate({
        elem: '#failureTime', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
        event: 'focus', //响应事件。如果没有传入event，则按照默认的click
        istime: true,
        format: 'YYYY-MM-DD hh:mm:ss', //日期格式
        festival: true //是否显示节日
    });
    $('#addBssForm').validate({
        rules: {
            bssName: {
                required: true
            },
            bssCode: {
                required: true
            },
            bssDesc: {
                required: true
            }
        },
        submitHandler: function (form) {
            var bssId = $('#bssAddId').val();
            if (bssId) {
                updateBss(bssId);
            } else {
                saveBss();
            }
        }
    });
});