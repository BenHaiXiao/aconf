/**
 * 展示联系人信息
 * @param who
 * @param when
 * @param userInfo
 */
function showContacts(who, when, userInfo) {
    var content = '<div class="contact-box"><a href="profile.html">';
    content += '<div class="col-sm-4"><div class="text-center">';
    content += '<img alt="image" class="img-circle m-t-xs img-responsive" src="' + userInfo.avatar + '">';
    content += '<div class="m-t-xs font-bold">' + userInfo.name + '</div></div></div>';
    content += '<div class="col-sm-8"><h3><strong>' + userInfo.name + '</strong></h3>';
    content += '<p><i class="fa fa-map-marker"></i>爱在哪在哪</p>';
    content += '<address><strong>Baidu, Inc.</strong><br>E-mail:' + userInfo.email + '<br>';
    content += '<abbr title="Phone">Tel:</abbr> ' + userInfo.phone;
    content += '</address></div><div class="clearfix"></div></a></div>';

    $(who).on(when, function () {

    })
}