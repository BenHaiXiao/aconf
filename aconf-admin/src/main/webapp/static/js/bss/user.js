/**
 * @author xiaobenhai
 * Date: 2017/2/10
 * Time: 11:15
 * 用户配置相关
 */

$(function () {
    var $image = $(".image-crop > img");
    $($image).cropper({
        aspectRatio: 1,
        preview: ".img-preview",
        done: function (data) {
            // 输出结果
        }
    });

    var $avatarUploader = $("#avatar");
    if (window.FileReader) {
        $avatarUploader.change(function () {
            var fileReader = new FileReader(),
                files = this.files,
                file;

            if (!files.length) {
                return;
            }

            file = files[0];

            if (/^image\/\w+$/.test(file.type)) {
                fileReader.readAsDataURL(file);
                fileReader.onload = function () {
                    $avatarUploader.val("");
                    $image.cropper("reset", true).cropper("replace", this.result);
                };
            } else {
                layer.alert("请选择图片文件");
            }
        });
    } else {
        $avatarUploader.addClass("hide");
    }

    $('#userEditForm').submit(function (e) {
        e.preventDefault();
        var id = $('#userId').val();
        var phone = $('#phone').val();
        var email = $('#email').val();
        // {width: 64, height: 64}
        var avatar = $image.cropper('getCroppedCanvas', {width: 64, height: 64}).toDataURL('image/jpeg');
        if (!phone || !email) {
            layer.alert("请填写联系电话和Email");
        }
        var data = {
            avatar: avatar,
            telephone: phone,
            email: email
        };
        $.ajax({
            url: '/user/update',
            method: "post",
            dataType: "json",
            contentType: "application/json",
            data: JSON.stringify(data),
            success: function (data) {
                if (data && data.code == 200) {
                    window.location.href = "/index";
                } else {
                    layer.alert("服务器错误，请稍后重试");
                }
            },
            error: function () {
                layer.alert("服务器错误，请稍后重试");
            }
        })
    });
});
