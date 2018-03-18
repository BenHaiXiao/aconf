/**
 * Created by Administrator on 2017/2/6.
 */
function savePush() {
    var title = $('#pushAddTitle').val();
    var channel = $('#pushAddSids').val();
    var sys = $('#pushAddSys').val();
    var effectiveTime = Date.parse($('#pushAddTime').val());
    var send = {
        title: title,
        sids: channel,
        effectiveTime: effectiveTime,
        sys: sys
    };
    $.ajax({
        url: '/bss/' + $('#bssId').val() + '/push',
        method: "post",
        contentType: "application/json",
        data: JSON.stringify(send),
        dataType: "json",
        success: function (data) {
            if (data && data.code == 200) {
                layer.closeAll();
                toastr.success("广播添加成功，将在" + $('#pushAddTime').val() + "发送。", "添加成功");
                grid.refresh(true);
            }
        }
    })
}

function updatePush(id) {
    var title = $('#pushAddTitle').val();
    var channel = $('#pushAddSids').val();
    var sys = $('#pushAddSys').val();
    var effectiveTime = Date.parse($('#pushAddTime').val());
    var send = {
        title: title,
        sids: channel,
        effectiveTime: effectiveTime,
        sys: sys
    };
    $.ajax({
        url: '/push/' + id,
        method: "PUT",
        contentType: "application/json",
        data: JSON.stringify(send),
        dataType: "json",
        success: function (data) {
            if (data && data.code == 200) {
                layer.closeAll();
                toastr.success("广播更新成功，将在" + $('#pushAddTime').val() + "发送。", "操作成功");
                grid.refresh(true);
            }
        }
    })
}

$(function () {
    //外部js调用
    laydate({
        elem: '#pushAddTime', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
        event: 'focus', //响应事件。如果没有传入event，则按照默认的click
        istime: true,
        format: 'YYYY-MM-DD hh:mm:ss', //日期格式
        festival: true //是否显示节日
    });
    $('#addPushForm').validate({
        rules: {
            title: {
                required: true,
                minlength: 1,
                maxlength: 48
            },
            sids: {
                required: true,
                minlength: 1
            },
            sys: {
                required: true,
                minlength: 1
            },
            time: {
                required: true,
                minlength: 1
            }
        },
        submitHandler: function (form) {
            var pushId = $('#pushId').val();
            if (pushId) {
                updatePush(pushId);
            } else {
                savePush();
            }
        }
    });
});