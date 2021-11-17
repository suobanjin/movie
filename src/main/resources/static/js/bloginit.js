$(function () {
    let $code = $('code[class=""], pre code:not([class])');
    let parent = $code.parent('pre');
    parent.addClass('codeStyle-new');
    //判断是否是手机
    const mobile_flag = isMobile();
    if(mobile_flag){//mobile
        $('#readMode').removeClass('m-opacity-very-mini').addClass('m-not-opacity');
    }
    tocbot.init({
        tocSelector: '.js-toc',
        contentSelector: '.js-toc-content',
        headingSelector: 'h1, h2, h3',
    });

    $('.toc.button').popup({
        popup: $('.toc-container.popup'),
        on: 'click',
        position: 'left center'
    });

    $('.wechat').popup({
        popup: $('.wechat-qr'),
        position: 'left center'
    });

    let qrcode = new QRCode("qrcode", {
        text: window.location.href,
        width: 110,
        height: 110,
        colorDark: "#000000",
        colorLight: "#ffffff",
        correctLevel: QRCode.CorrectLevel.H
    });

    $('#toTop-button').click(function () {
        $(window).scrollTo(0, 500);
    });
    const $type_id = $('#type').val();
    const $list = $('#list');
    let $list_html = '';
    const $date = $('#blogId').val();
    const default_error_html = '<div class="item center aligned">' +
        '<div class="ui negative message">\n' +
        '  <div class="header">\n' +
        '    初始化失败\n' +
        '  </div>\n' +
        '    <p>可能是网络连接出现了问题</p>\n' +
        '  </div>';
    if ($type_id === null || $type_id.length <= 0) {
        $list_html = default_error_html;
        $list.append($list_html);
    } else {
        let tags = $('#tag a');
        let tagArrays = [];
        if (tags.length > 0) {
            for (let i = 0; i < tags.length; i++) {
                tagArrays[i] = $(tags[i]).attr('data-value');
            }
        }
        $.ajax({
            type: 'GET',
            url: '/blog/detail/list',
            data:
                {
                    'typeId': $type_id,
                    'id': $date,
                    'tags': tagArrays
                },
            success: function (data) {
                let json;
                let color = ['red', 'orange', 'teal', '', '', ''];
                if ((typeof data === 'string') && data.constructor === String) {
                    json = JSON.parse(data);
                } else {
                    json = data;
                }
                if (json.code === 1) {
                    const text_list = json.data;
                    for (let i = 0; i < text_list.length; i++) {
                        let textListKey = text_list[i];
                        let colorClass = color[i];
                        $list_html += '<div class="item"> <div class="right floated content ">' +
                            '<a href="/blog/detail/' + textListKey.id + '" class="ui button mini teal">阅读原文</a>' + '</div>' +
                            ' <div class="content">' + '<span class="ui m-mobile-hide ' + colorClass + ' label right pointing">' + textListKey.views + '阅/' + textListKey.like + '赞</span>' +
                            '<span class="footer_link" style="margin-left: 5px!important;" data-position="right center" data-tooltip="' + '[' + textListKey.flag + ']' + textListKey.author + '"> ' + textListKey.title + '</span>' +
                            '</div>' +
                            '</div>';

                    }
                    $list.append($list_html);
                } else if (json.code === 2) {
                    $('#aboutContent').attr('style', 'display: none!important;');
                    $('#aboutContentSegment').attr('style', 'display: none!important;');
                } else {
                    $list.append(default_error_html);
                }
            },
            error: function () {
                $list.append(default_error_html);
            }
        });
    }
    let html = '';
    const $tuiJian = $('#tuiJian');
    $.ajax({
        type: 'GET',
        url: '/blog/detail/favoriteList',
        data: {},
        success: function (data) {
            let json;
            if ((typeof data === 'string') && data.constructor === String) {
                json = JSON.parse(data);
            } else {
                json = data;
            }
            if (json.code === 1) {
                let blogArray = json.data;
                for (let i = 0; i < blogArray.length; i++) {
                    let blog = blogArray[i];
                    html += '<div class="column four wide">' + '<a href="/blog/detail/' + blog.id + '" class="ui medium image">' +
                        '<img class="ui rounded image" src="' + blog.headerImageUrl + '" alt="' + blog.title + '" style="width: 214px!important;height: 151px!important;">' + '</a>' +
                        '<div class="ui  center aligned segment mini m-padded-mini" style="border: none!important;box-shadow: none!important;">' +
                        '<a href="/blog/detail/' + blog.id + '" class="centered" >' + blog.title + '</a>' + '</div>' + '</div>';
                }
                $tuiJian.append(html);
            } else {
                html = default_error_html;
                $tuiJian.append(html);
            }
        },
        error: function () {
            html = default_error_html;
            $tuiJian.append(html);
        }
    });
    let randomHtml = '';
    const $list03 = $('#list03');
    $.ajax({
       type: 'GET',
       url: '/blog/detail/random/'+$date,
       success: function (data) {
           let json;
           let color = ['red', 'orange', 'teal', '', '', ''];
           if ((typeof data === 'string') && data.constructor === String) {
               json = JSON.parse(data);
           } else {
               json = data;
           }
           if (json.code === 1) {
               const text_list = json.data;
               for (let i = 0; i < text_list.length; i++) {
                   let textListKey = text_list[i];
                   let colorClass = color[i];
                   randomHtml += '<div class="item"> <div class="right floated content ">' +
                       '<a href="/blog/detail/' + textListKey.id + '" class="ui button mini teal">阅读原文</a>' + '</div>' +
                       ' <div class="content">' + '<span class="ui m-mobile-hide ' + colorClass + ' label right pointing">' + textListKey.views + '阅/' + textListKey.like + '赞</span>' +
                       '<span class="footer_link" style="margin-left: 5px!important;" data-position="right center" data-tooltip="' + '[' + textListKey.flag + ']' + textListKey.author + '"> ' + textListKey.title + '</span>' +
                       '</div>' +
                       '</div>';

               }
               $list03.append(randomHtml);
           }
       }
    });
    const $left = $('#left');
    const $right = $('#right');
    if ($date !== null) {
        $.ajax({
            type: 'GET',
            url: '/blog/detail/more/' + $date,
            success: function (data) {
                let json;
                if ((typeof data === 'string') && data.constructor === String) {
                    json = JSON.parse(data);
                } else {
                    json = data;
                }
                if (json.code === 2) {
                    $left.addClass('disabled');
                    $right.addClass('disabled');
                } else if (json.code === 1) {
                    let array = json.data;
                    if (array[0] !== null && array[0] !== undefined) {
                        $left.attr('href', '/blog/detail/' + array[0].id);
                        $left.attr('data-tooltip', '[' + array[0].flag + '] ' + array[0].title);
                    }else{
                        $left.addClass('disabled');
                    }
                    if (array[1] !== null && array[1] !== undefined ){
                        $right.attr('href', '/blog/detail/' + array[1].id);
                        $right.attr('data-tooltip', '[' + array[1].flag + '] ' + array[1].title);
                    }else{
                        $right.addClass('disabled');
                    }
                }
            },
        });
    } else {
        $left.addClass('disabled');
        $right.addClass('disabled');
    }
});
let waypoint = new Waypoint({
    element: document.getElementById('waypoint'),
    handler: function (direction) {
        if (direction === 'down') {
            $('#toolbar').show(100);
        } else {
            $('#toolbar').hide(500);
        }
    }
});
$('.menu.toggle').click(function () {
    $('.m-item').toggleClass('m-mobile-hide');
});
$('pre').addClass("line-numbers").css("white-space", "pre-wrap");
$('#content img').addClass('ui').addClass('centered').addClass('image');
let error = $('#error');
let value = error.val().trim();
if (value !== null && value.length !== 0) {
    layer.msg(value, {
        icon: 5
    });
}
$('#payButton').popup({
    popup: $('.payQR.popup'),
    on: 'click',
    position: 'top center',
}).click(function () {
    $('#left').attr('style', 'margin-top: -18px!important;');
    $('#right').attr('style', 'margin-top: -18px!important;');
});

function isMobile() {
    const userAgentInfo = navigator.userAgent;
    const mobileAgents = ["Android", "iPhone", "SymbianOS", "Windows Phone", "iPad", "iPod"];
    let mobile_flag = false;
    if ('ontouchend' in document.body || /mobile/i.test(navigator.userAgent)) return true;
    if (navigator.userAgent.match(/mobile/i)) return true;
    //根据userAgent判断是否是手机
    for (let v = 0; v < mobileAgents.length; v++) {
        if (userAgentInfo.indexOf(mobileAgents[v]) > 0) {
            mobile_flag = true;
            break;
        }
    }
    const screen_width = window.screen.width;
    const screen_height = window.screen.height;
    //根据屏幕分辨率判断是否是手机
    if(screen_width < 500 && screen_height < 800){
        mobile_flag = true;
    }
    return mobile_flag;
}