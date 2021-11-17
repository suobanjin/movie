$(function () {
    $(window).scroll(function () {
        if ($(window).scrollTop() > 100) {
            $("#toolbar").fadeIn(700);
        } else {
            $("#toolbar").fadeOut(700);
        }
    });
    $("#goTop").click(function () {
        $("body,html").animate({
            scrollTop: 0
        }, 700);
        return false;
    });
});
(function ($) {
    $.getUrlParam = function (name) {
        let reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");//构造一个含有目标参数的正则表达式对象
        let r = window.location.search.substr(1).match(reg);//匹配目标参数
        if (r != null) return unescape(r[2]);
        return null;//返回参数值
    };
    $.getUrlParamCN = function (name) {
        //获取url中的参数
        let reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
        let r = decodeURI(window.location.search).substr(1).match(reg); //匹配目标参数
        if (r != null) return unescape(r[2]);
        return null; //返回参数值
    }
})(jQuery);