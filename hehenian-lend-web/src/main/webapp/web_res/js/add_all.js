 
    /*
    ****************************************************
    *BigContainer:最外层容器,
    *ListContainer:图片模块容器,
    *ImgContainerWidth:图片容器宽度,
    *SelectContainer：选择容器
    *SelectContainerNext：选择容器下级标签
    *SelectCss:选择标签选中样式
    *NoSelectCss：选择标签未选中样式
    *ListContainerNext:图片模块容器下级容器标签
    *BigListWidth:图片列表宽度
    *ScrollTime：滚动时间
    *WaitingTime:等待时间
    ****************************************************
    */
    function ImgScroll(BigContainer, ListContainer, ImgContainerWidth, SelectContainer, SelectContainerNext, SelectCss, NoSelectCss, ListContainerNext, BigListWidth, ScrollTime, WaitingTime) {
        var dsf;
        $(ListContainer).css("width", $(ListContainer + " > " + ListContainerNext).length * ImgContainerWidth);
        var zwidth = $(ListContainer + " > " + ListContainerNext).length * ImgContainerWidth;
        $(SelectContainer + " " + SelectContainerNext + ":first").attr("class", SelectCss);
        function scollatuo() {
            clearTimeout(dsf);
            if ($(BigContainer).scrollLeft() >= zwidth - BigListWidth - 100) {
                $(BigContainer).stop().animate({ scrollLeft: 0 }, ScrollTime);
                $(SelectContainer + " " + SelectContainerNext).attr("class", NoSelectCss);
                $(SelectContainer + " " + SelectContainerNext + ":first").attr("class", SelectCss);
 
            }
            else {
                $(BigContainer).stop().animate({ scrollLeft: $(BigContainer).scrollLeft() + BigListWidth }, ScrollTime);
                var s = parseInt($(BigContainer).scrollLeft() / BigListWidth);
                $(SelectContainer + " " + SelectContainerNext).attr("class", NoSelectCss);
                $(SelectContainer + " " + SelectContainerNext).eq(s + 1).attr("class", SelectCss);
            }
 
            dsf = setTimeout(scollatuo, WaitingTime);
        }
        dsf = setTimeout(scollatuo, WaitingTime);
        //点击小点
        $(SelectContainer + " " + SelectContainerNext).hover(function () {
            clearTimeout(dsf);
            var indexs = $(SelectContainer + " " + SelectContainerNext).index(this);
            $(BigContainer).stop().animate({ scrollLeft: BigListWidth * indexs }, ScrollTime);
            $(SelectContainer + " " + SelectContainerNext).attr("class", NoSelectCss);
            $(this).attr("class", SelectCss);
        },
function () { dsf = setTimeout(scollatuo, WaitingTime) })
        //baner停止
        $(BigContainer).hover(function () { clearTimeout(dsf); }, function () { dsf = setTimeout(scollatuo, WaitingTime) })
        //baner结束
 
 
    }
