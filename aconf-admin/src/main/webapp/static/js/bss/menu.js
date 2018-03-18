/**
 * Created by Administrator on 2017/2/7.
 */
$(function () {
    var bssMgrItem = $('#bssMgrItem');
    var bssListItem = $('#bssListItem');
    var bssId = getQueryString("bssid");
    var token = getQueryString("token");
    var appId = getQueryString("appid");
    if (bssId) {
        bssMgrItem.removeClass("hide");
        var link = bssMgrItem.find('ul a');
        link.on("click", function (e) {
            window.location.href = $(this).attr("href") + "?bssid=" + bssId + "&token=" + token + "&appid=" + appId;
            return false;
        });
    } else {
        bssMgrItem.addClass("hide");
    }
    if (appId) {
        bssListItem.removeClass("hide");
        var bssLink = bssListItem.find('a');
        bssLink.on("click", function (e) {
            window.location.href = $(this).attr("href") + "?appid=" + appId + "&token=" + token;
            return false;
        })
    } else {
        bssListItem.addClass("hide");
    }
});

/**
 * 从url里取属性
 * @param name
 * @returns {null}
 */
function getQueryString(name) {
    var reg = new RegExp('(^|&)' + name + '=([^&]*)(&|$)', 'i');
    var r = window.location.search.substr(1).match(reg);
    if (r != null) {
        return unescape(r[2]);
    }
    return null;
}