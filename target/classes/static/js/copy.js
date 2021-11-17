function copyContent(content) {
    document.addEventListener('copy', save); // 监听浏览器copy事件
    document.execCommand('copy'); // 执行copy事件，这时监听函数会执行save函数。
    document.removeEventListener('copy', save); // 移除copy事件
    // 保存方法
    function save(e) {
        e.clipboardData.setData('text/plain', content); // 剪贴板内容设置
        e.preventDefault()
    }
}
$('pre').hover(function () {
    const thisObj = $(this);
    const btn = $(this).find('.ui.button.right.floated');
    if (btn === null || btn === undefined || btn.length === 0) {
         $(this).append('<div class="animated fadeIn mini m-opacity-mini"  style="position: absolute;top: 0.8em;right: 1em"><button class="ui icon button right floated mini next m-opacity-mini"><i class="copy outline icon"></i></button></div>');
        $('.ui.button.right.floated.mini.next').click(function () {
            let content = thisObj[0].innerText;
            const lastIndex = content.lastIndexOf('复制');
            if (lastIndex !== null && lastIndex !== undefined && lastIndex > 2) {
                content = content.substring(0, lastIndex);
            }
            copyContent(content);
            const width = window.innerWidth;
            const  widthStr =width - 220 + 'px';
            layer.msg('复制成功',{
                offset: ['70px', widthStr],
                icon: 6,
            });
        });
    }
},function () {
    const btn = $(this).find('.ui.button.right.floated.mini.next');
    if (btn !== null && btn !== undefined && btn.length > 0){
        $('.ui.button.right.floated.mini.next').remove();
    }
});
