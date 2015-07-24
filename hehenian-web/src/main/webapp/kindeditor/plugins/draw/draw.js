KindEditor.plugin('draw', function(K) {
        var editor = this, name = 'draw';
        // 点击图标时执行
        editor.clickToolbar(name, function() {
               dialog();
        });
});