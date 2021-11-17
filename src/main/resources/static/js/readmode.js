$('#read').click(function () {
    read($(this));
});
function read(thisObj){
    const innerText = thisObj.val();
    if (innerText === 'off') {
        $('#readMode').addClass('read');
        thisObj.val('on');
        codeStyleOn();
        localStorage.setItem('readMode','on');
    } else {
        $('#readMode').removeClass('read');
        thisObj.val('off');
        codeStyleOff();
        localStorage.removeItem('readMode');
    }
}
$(function () {
    if (!isMobile()) {
        let item = window.localStorage.getItem('readMode');
        if (item !== null && item !== undefined) {
            $('#readMode').addClass('read');
            let readBtn = $('#read');
            readBtn.val('on');
            codeStyleOn();
        }
    }
});

function codeStyleOn() {
    const code = $('#content').find('code');
    if (code !== null && code !== undefined && code.length > 0){
        $('code').addClass('code-read-mode')
    }
}

function codeStyleOff() {
    const code = $('#content').find('code');
    if (code !== null && code !== undefined && code.length > 0){
        $('code').removeClass('code-read-mode')
    }
}