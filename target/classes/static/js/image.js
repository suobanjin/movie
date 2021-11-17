$(function () {
    if ('ontouchend' in document.body || /mobile/i.test(navigator.userAgent)) {

    }else{
        const item = localStorage.getItem('ImageUrl');
        if (item !== null && item !== undefined) {
            setImage(item);
        }
    }
});
$('#changeImage').click(function () {
    changeImage();
});
function changeImage() {
    let item = localStorage.getItem('imageIndex');
    if (item === null || item === undefined){
        item = 0;
        localStorage.setItem('imageIndex','0');
    }else{
        item = parseInt(item) + 1;
        if (item > 16){
            item = 0;
            localStorage.setItem('imageIndex','0');
        }else{
            localStorage.setItem('imageIndex',item+'');
        }
    }
    $.ajax({
            url: 'https://jsonp.afeld.me/?callback=&url=https%3A%2F%2Fcn.bing.com%2FHPImageArchive.aspx%3Fformat%3Djs%26idx%3D' + item + '%26n%3D1%26mkt%3Dzh-CN',
            type: 'GET',
            success: function (data) {
                let json;
                if ((typeof data === 'string') && data.constructor === String) {
                    json = JSON.parse(data);
                } else {
                    json = data;
                }
                const myUrl = 'https://cn.bing.com' + (json.images)[0].url;
                localStorage.setItem('ImageUrl',myUrl);
                setImage(myUrl);

            }
        }
    );
}
function setImage(myUrl) {
    let img = new Image();
    img.src = myUrl;
    if (img.complete) {
        const body = $('body');
        body.attr('style', 'background-image: url(' + img.src + ')');
    } else {
        img.onload = function () {
            const body = $('body');
            body.attr('style', 'background-image: url(' + img.src + ')');
            img.onload = null;
        };
    }
}