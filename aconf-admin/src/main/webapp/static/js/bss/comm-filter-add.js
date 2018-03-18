/**
 * Created by Administrator on 2017/2/6.
 */
function saveFilter() {
    var basis = $('#filterBasis').val();
    var operator = $('#filterOperator').val();
    var boundary = $('#filterBoundary').val();
    var send = {
        basis: basis,
        operator: operator,
        boundary: boundary
    };
    $.ajax({
        url: '/bss/' + $('#bssId').val() + '/filter',
        method: "post",
        contentType: "application/json",
        data: JSON.stringify(send),
        dataType: "json",
        success: function (data) {
            if (data && data.code == 200) {
                layer.closeAll();
                toastr.success("公共拦截器 " + basis + " " + operator + " " + boundary + " 添加成功。", "添加成功");
                grid.refresh(true);
            }
        }
    })
}

function updateFilter(id) {
    var basis = $('#filterBasis').val();
    var operator = $('#filterOperator').val();
    var boundary = $('#filterBoundary').val();
    var send = {
        basis: basis,
        operator: operator,
        boundary: boundary
    };
    $.ajax({
        url: '/filter/' + id,
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
    $('#addFilterForm').validate({
        rules: {
            basis: {
                required: true
            },
            operator: {
                required: true
            },
            boundary: {
                required: true
            }
        },
        submitHandler: function (form) {
            var filterId = $('#filterAddId').val();
            if (filterId) {
                updateFilter(filterId);
            } else {
                saveFilter();
            }
        }
    });
});