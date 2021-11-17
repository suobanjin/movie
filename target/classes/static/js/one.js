
$(function () {
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
    $('.menu.toggle').click(function () {
        $('.m-item').toggleClass('m-mobile-hide');
    });
    var qrcode = new QRCode("qrcode", {
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


    var waypoint = new Waypoint({
        element: document.getElementById('waypoint'),
        handler: function (direction) {
            if (direction === 'down') {
                $('#toolbar').show(100);
            } else {
                $('#toolbar').hide(500);
            }
        }
    });
    const $list = $('#list');
    const default_error_html = '<div class="item center aligned">' +
        '<div class="ui negative message">\n' +
        '  <div class="header">\n' +
        '    初始化失败\n' +
        '  </div>\n' +
        '    <p>可能是网络连接出现了问题</p>\n' +
        '  </div>';
    $.ajax({
        type: 'GET',
        url: 'http://v3.wufazhuce.com:8000/api/reading/index/?version=3.5.0&platform=android',
        beforeSend:function(){
            layer.msg('内容加载中', {
                icon: 16
                , shade: 0.01
            });
        },
        success: function (data) {
            let json;
            if ((typeof data === 'string') && data.constructor === String) {
                json = JSON.parse(data);
            } else {
                json = data;
            }
            if (json.res === 0) {
                let data = json.data;
                let essay = data.essay;
                let list = '';
                for (let i = 0; i < essay.length; i++) {
                    let $object = essay[i];
                    let author = $object.author;
                    let date = $object.hp_makettime;
                    date = dateFormate(date);
                    if (i === 0){
                        list += '<div class="item"> <div class="right floated content ">' +
                            '<a href="javascript:void(0);" onclick="getContent(' + $object.content_id + ')" class="ui button mini teal">阅读原文</a>' + '</div>' +
                            ' <div class="content">' + '<span class="ui orange label right pointing">' + author[0].user_name + '</span>' +
                            '<span class="footer_link" style="margin-left: 5px!important;"  data-tooltip="' + date + '"> ' + $object.hp_title + '&nbsp;<div class="ui label mini red left pointing m-mobile-hide">New</div> </span>' +
                            '</div>' +
                            '</div>';
                    }else {
                        list += '<div class="item"> <div class="right floated content ">' +
                            '<a href="javascript:void(0);" onclick="getContent(' + $object.content_id + ')" class="ui button mini teal">阅读原文</a>' + '</div>' +
                            ' <div class="content">' + '<span class="ui orange label right pointing">' + author[0].user_name + '</span>' +
                            '<span class="footer_link" style="margin-left: 5px!important;" data-position="right center" data-tooltip="' + date + '"> ' + $object.hp_title + '</span>' +
                            '</div>' +
                            '</div>';
                    }
                }
                $list.append(list);
                let $first = essay[0];
                let id = $first.content_id;
                layer.closeAll();
                getContent(id);
                list = '';
                const $list02 = $('#list02');
                const $serial = data.serial;
                let map = new Map();
                for (let i = 0; i < $serial.length; i++) {
                    const $object = $serial[i];
                    const author = $object.author;
                    const name = author.user_name;
                    let title = $object.title;
                    title = stringRegx(title);
                    title = title.trim();
                    let value = map.get(title + name);
                    if (value === null || value === undefined) {
                        map.set(title + name, $object);
                        const date = $object.maketime;
                        const lastestId = $object.id;
                        const desc = $object.excerpt;
                        const arraysId = $object.serial_list;
                        list += '<div class="item">' + '<div class="content">' + '<span class="ui label right pointing teal">' + name + '</span>' +
                            '<span class="footer_link" style="margin-left: 5px!important;" data-position="right center" data-tooltip="' + desc + '">' + title + '</span>' +
                            '</div>' + '</div>' + '<div class="item">';
                        for (let j = 0; j < arraysId.length; j++) {
                            const thisId = arraysId[j];
                            if ((j + 1) === 20 || (j + 1) === 25 || (j + 1) === 26) {
                                list += '<a href="javascript:void(0);" onclick="getLongContent(' + thisId + ')" class="ui button mini" style="margin-top: 3px!important;">' + (j + 1) + '</a>';
                            } else {
                                list += '<a href="javascript:void(0);" onclick="getLongContent(' + thisId + ',this);" class="ui button mini" style="margin-top: 3px!important;">' + (j + 1) + '</a>';
                            }
                        }
                        list += '</div>';
                    }
                }
                $list02.append(list);
                list = '';
                const $comments = $('#list03');
                let arrayQuestion = data.question;
                for (let i = 0; i < arrayQuestion.length; i++) {
                    let $object = arrayQuestion[i];
                    const arrayAsker = $object.asker_list;
                    const asker = arrayAsker[0];
                    const id = $object.question_id;
                    const title = $object.question_title;
                    const answer = $object.answer_content;
                    if (i === 0){
                        list += '<div class="item"> <div class="right floated content ">' +
                            '<a href="javascript:void(0);" onclick="getQuestionContent(' + id + ')" class="ui button mini teal">前往回答</a>' + '</div>' +
                            ' <div class="content">' + '<span class="ui orange label right pointing">' + asker.user_name + '</span>' +
                            '<span class="footer_link" style="margin-left: 5px!important;"  data-tooltip="' + answer + '"> ' + title + '&nbsp;<div class="ui label mini red left pointing m-mobile-hide">New</div> </span>' +
                            '</div>' +
                            '</div>';
                    }else {
                        list += '<div class="item"> <div class="right floated content ">' +
                            '<a href="javascript:void(0);" onclick="getQuestionContent(' + id + ')" class="ui button mini teal">前往回答</a>' + '</div>' +
                            ' <div class="content">' + '<span class="ui orange label right pointing">' + asker.user_name + '</span>' +
                            '<span class="footer_link" style="margin-left: 5px!important;" data-position="right center" data-tooltip="' + answer + '"> ' + title + '</span>' +
                            '</div>' +
                            '</div>';
                    }
                }
                $comments.append(list);
            } else {
                $list.append(default_error_html);
            }
        },
        error: function () {
            $list.append(default_error_html);
        }
    });
});
const $topImage = $('#top_image');
const $topTitle = $('#top_title');
const $author = $('#author');
const $content = $('#content');
const $date = $('#date');

function getQuestionContent(id) {
    const url = 'http://v3.wufazhuce.com:8000/api/question/' + id;
    $.ajax({
        type: 'GET',
        url: url,
        beforeSend: function () {
            layer.msg('回答加载中', {
                icon: 16
                , shade: 0.01
            });
        },
        success: function (data) {
            let json;
            if ((typeof data === 'string') && data.constructor === String) {
                json = JSON.parse(data);
            } else {
                json = data;
            }
            if (json.res === 0) {
                let $data = json.data;
                let title = $data.question_title;
                let author = $data.answerer;
                let date = $data.last_update_date;
                date = dateFormate(date);
                let content = $data.answer_content;
                $($topTitle)[0].innerHTML = title;
                $($author)[0].innerHTML = author.user_name;
                $($date)[0].innerHTML = date;
                $($content)[0].innerHTML = content;
                let audio = $data.audio;
                $('#audio').attr('src', audio);
                let audioAuthor = $data.anchor;
                if (audioAuthor !== null && audioAuthor.length > 0) {
                    $('#audioAuthor')[0].innerHTML = '本文提供朗读音频，' + audioAuthor;
                } else {
                    $('#audioAuthor')[0].innerHTML = '本文未提供朗读音频';
                }
                $.scrollTo('#position', 300);
            }else{
                layer.msg('回答加载失败，请检查后再说',{
                    icon:5
                });
            }
        },
        complete: function () {
            layer.closeAll();
        },
        error:function () {
            layer.msg('回答加载失败，请先检查网络',{
                icon:5
            });
        }
    });
}

function getLongContent(id, thisObject) {
    const url = 'http://v3.wufazhuce.com:8000/api/serialcontent/' + id;
    $.ajax({
        type: 'GET',
        url: url,
        beforeSend: function () {
            layer.msg('文章加载中', {
                icon: 16
                , shade: 0.01
            });
        },
        success: function (data) {
            let json;
            if ((typeof data === 'string') && data.constructor === String) {
                json = JSON.parse(data);
            } else {
                json = data;
            }
            if (json.res === 0) {
                let $data = json.data;
                let title = $data.title;
                let author = $data.author;
                let date = $data.last_update_date;
                date = dateFormate(date);
                let content = $data.content;
                let image = $data.top_media_image;
                $($topTitle)[0].innerHTML = title;
                if (image !== null && image.length !== 0) {
                    $($topImage).attr('src', image);
                }
                $($author)[0].innerHTML = author.user_name;
                $($date)[0].innerHTML = date;
                $($content)[0].innerHTML = content;
                let audio = $data.audio;
                $('#audio').attr('src', audio);
                let audioAuthor = $data.anchor;
                if (audioAuthor !== null && audioAuthor.length > 0) {
                    $('#audioAuthor')[0].innerHTML = '本文提供朗读音频，' + audioAuthor;
                } else {
                    $('#audioAuthor')[0].innerHTML = '本文未提供朗读音频';
                }
                $(thisObject).addClass('teal');
                $.scrollTo('#position', 300);
            }else{
                layer.msg('连载文章加载失败，请先检查网络',{
                    icon:5
                });
            }
        },
        complete: function () {
            layer.closeAll();
        },
        error:function () {
            layer.msg('连载文章加载失败，请先检查网络',{
                icon:5
            })
        }
    });
}

function getContent(id) {
    const url = 'http://v3.wufazhuce.com:8000/api/essay/' + id + '?version=3.5.0&platform=android';
    $.ajax({
        type: 'GET',
        url: url,
        beforeSend: function () {
            layer.msg('文章加载中', {
                icon: 16
                , shade: 0.01
            });
        },
        success: function (data) {
            let json;
            if ((typeof data === 'string') && data.constructor === String) {
                json = JSON.parse(data);
            } else {
                json = data;
            }
            if (json.res === 0) {
                let $data = json.data;
                let title = $data.hp_title;
                let author = $data.hp_author;
                let date = $data.hp_makettime;
                date = dateFormate(date);
                let content = $data.hp_content;
                let image = $data.top_media_image;
                $($topTitle)[0].innerHTML = title;
                if (image !== null && image.length !== 0) {
                    $($topImage).attr('src', image);
                }else{
                    let imageUrl = getTopImage();
                    console.log(imageUrl)
                    if (imageUrl !== null) {
                        $($topImage).attr('src', imageUrl);
                    }
                    console.log(imageUrl);
                }
                $($author)[0].innerHTML = author;
                $($date)[0].innerHTML = date;
                $($content)[0].innerHTML = content;
                let audio = $data.audio;
                $('#audio').attr('src', audio);
                let audioAuthor = $data.anchor;
                if (audioAuthor !== null && audioAuthor.length > 0) {
                    $('#audioAuthor')[0].innerHTML = '本文提供朗读音频，' + audioAuthor;
                } else {
                    $('#audioAuthor')[0].innerHTML = '本文未提供朗读音频';
                }
                $.scrollTo('#position',300);
            }else{
                layer.msg('文章加载失败，请刷新后重试',{
                    icon: 5
                });
            }
        },
        complete: function () {
            layer.closeAll();
        },
        error: function () {
            layer.msg('文章加载失败，请先检查网络');
        }
    });
}
function randomContent() {
    $.ajax({
        type: 'GET',
        url: 'https://interface.meiriyiwen.com/article/random?dev=1',
        beforeSend: function () {
            layer.msg('文章加载中', {
                icon: 16
                , shade: 0.01
            });
        },
        success: function (data) {
            let json;
            if ((typeof data === 'string') && data.constructor === String) {
                json = JSON.parse(data);
            } else {
                json = data;
            }
            let $data = json.data;
            let title = $data.title;
            let author = $data.author;
            let content = $data.content;
            if (content === null || content === undefined || content.length === 0){
                layer.msg('随机文章加载失败，请刷新后重试',{
                    icon: 5
                });
                return;
            }
            $($topTitle)[0].innerHTML = title;
            $($author)[0].innerHTML = author;
            $($content)[0].innerHTML = content;
            $($date)[0].innerHTML = '未知';
            $('#audio').attr('src', '');
            $('#audioAuthor')[0].innerHTML = '本文未提供朗读音频';
            $.scrollTo('#position', 300);
        },
        complete: function () {
            layer.closeAll();
        },
        error: function () {
            layer.msg('随机文章加载失败，请先检查网络',{
                icon:5
            });
        }
    });
}
function stringRegx(str) {
    let indexOf = str.indexOf("·");
    return str.substring(0, indexOf);
}
function dateFormate(str) {
    const number = str.indexOf(" ");
    return str.substring(0, number);
}
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

function getTopImage() {
    let imageUrl = null;
    $.ajax({
        type: 'GET',
        url: 'https://www.mxnzp.com/api/image/girl/list/random?app_id=enl0qoimivvdyjor&app_secret=RHdqU3BzU2Q4aUltL2hBV0gxQTQvdz09',
        success: function (data) {
            let json;
            if ((typeof data === 'string') && data.constructor === String){
                json = JSON.parse(data);
            }else{
                json = data;
            }
            if (json.code === 1){
                imageUrl = json.data[0].imageUrl;
            }else{
                imageUrl = null;
            }
        },
        error:function () {
            imageUrl = null;
        },
        async: false
    });
    return imageUrl;
}