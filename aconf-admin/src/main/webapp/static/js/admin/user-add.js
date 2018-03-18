// @author xiaobenhai on 2017/2/22.

$(function () {
    var suggest = $("#addPassport").bsSuggest({
        allowNoKeyword: false, //是否允许无关键字时请求数据
        multiWord: false, //以分隔符号分割的多关键字支持
        url: "/user/suggest?keyword=",
        showBtn: false,
        keyField: 'passport',
        getDataMethod: "url", //获取数据的方式，总是从 URL 获取
        effectiveFieldsAlias: {
            passport: "通行证",
            realName: "姓名"
        },
        // showHeader: true,
        processData: function (json) { // url 获取数据时，对数据的处理，作为 getData 的回调函数
            if (!json || !json.data || json.data.length === 0) {
                return false;
            }
            return {
                value: json.data
            };
        }
    }).on('onDataRequestSuccess', function (e, result) {
        console.log('onDataRequestSuccess: ', result);
    }).on('onSetSelectValue', function (e, keyword) {
        console.log('onSetSelectValue: ', keyword);
    }).on('onUnsetSelectValue', function (e) {
        console.log("onUnsetSelectValue");
    });


    $('#addUserForm').validate({
        rules: {
            passport: {
                required: true
            },
            role: {
                required: true
            }
        },
        submitHandler: function (form) {
            var userId = $('#userId').val();
            if (userId) {
                updateUser(userId);
            } else {
                saveUser();
            }
        }
    });
});

/**
 * 更新用户信息
 * @param userId 用户id
 */
function updateUser(userId) {
    var role = $('#addRole').val();
    var telephone = $("#addPhone").val();
    var authbss = "0";
    $bssRoleCheckboxes = $('[name="bssrole"]');
    $bssRoleCheckboxes.each(function(){
        if ($(this).prop('checked') == true) {
                authbss = authbss+","+$(this).val();
        }
    });    
    var data = {
        role: role,
        bssRole: authbss,  
        telephone: telephone
    };
    $.ajax({
        url: '/user/' + userId,
        method: 'put',
        dataType: 'json',
        contentType: 'application/json',
        data: JSON.stringify(data),
        success: function (data) {
            if (data && data.code == 200) {
                toastr.success("用户信息更新成功", "操作成功");
                window.layer.closeAll();
                grid.refresh(true);
            }
        },
        error: function () {
            layer.alert("网络错误，请稍后重试");
        }
    });
}

/**
 * 激活新用户
 */
function saveUser() {
    var passport = $('#addPassport').val();
    var role = $('#addRole').val();
    var telephone = $("#addPhone").val();
    var authbss = "0";
    $bssRoleCheckboxes = $('[name="bssrole"]');
    $bssRoleCheckboxes.each(function(){
        if ($(this).prop('checked') == true) {
                authbss = authbss+","+$(this).val();
        }
    });        
    var data = {
        passport: passport,
        role: role,
        bssRole: authbss,  
        telephone: telephone
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
                window.layer.closeAll();
                grid.refresh(true);
            }
        },
        error: function () {
            layer.alert("网络错误，请稍后重试");
        }
    });
}