/**
 * Created by Administrator on 2017/3/10.
 */
function upload() {
    var fileArray = $('#resourceFile').prop("files");
    if (fileArray.length != 1) {
        return false;
    }
    var fileVal = fileArray[0];
    if (fileVal.size > 10240 * 1024) {
        layer.alert("文件大小请限制在10M以内");
        return false;
    }
    var desc = $('#resourceDesc').val();
    var reader = new FileReader();
    var file = reader.readAsDataURL(fileVal);
    var configId = $('#configAddId').val();
    var data = new FormData();
    data.append('file', fileVal);
    data.append('description', desc);
    data.append('configId', configId);

    $.ajax({
        url: '/resource',
        method: 'post',
        data: data,
        cache: false,
        processData: false,
        contentType: false,
        success: function (data) {
            if (data && data.code == 200) {
                resGrid.refresh(true);
            }
        },
        error: function () {
            layer.alert("网络错误，上传失败");
        }
    });
}

$(function () {
    $('#addResourceForm').validate({
        rules: {
            file: {
                required: true
            },
            description: {
                required: true
            }
        },
        submitHandler: function (form) {
            upload();
        }
    });
});