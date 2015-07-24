/**
 * Created by huangqimf on 14-9-23.
 */
var data_1 = "{ root:[{name : '张三'，value : '1620',id : '01'},{name : '李四'，value : '1621',id : '02'},{name : '刘备'，value : '1621',id : '02'}，{name : '张三'，value : '1620',id : '01'}] }";
var data_2 = "{ root:[{name : '张三'，value : '1620',id : '01'},{name : '李四'，value : '1621',id : '02'},{name : '刘备'，value : '1621',id : '02'}，{name : '张三'，value : '1620',id : '01'}] }";

$(function(){
    var SHAKE_THRESHOLD = 800;
    var last_update = 0;
    var x = y = z = last_x = last_y = last_z = 0;
    var num = 0;
    var media = document.getElementById('media'),initMit = document.getElementById('initMit');
    var huge = document.getElementById('huge');
   // var result = document.getElementById('result').getElementsByTagName('span')[0];
    var startBtn = document.getElementById('startBtn');
    var DeviceMotionNum = document.getElementById('DeviceMotionNum'); //摇动了次数
    var hitNumRemnant = 2; //设置最多点击次数
    var secMind = 60; //(秒)摇手机时间限制
    var isFirstHit = true;
    var $body =$('body');

    //开始按钮事件绑定
    $body.on('tap', startBtn, init);

    function init(e) {
        secMind = isFirstHit ? secMind : 60;
        initMit.innerHTML = secMind;
        if (window.DeviceMotionEvent) {
            window.addEventListener('devicemotion', deviceMotionHandler, false);
        } else {
            alert('您的浏览器不支持HTML5摇手机事件，请升级到最新版本！');
        }
        var setTimeLim = setInterval(function(){
            if(secMind > 0){
                initMit.innerHTML = --secMind;
            }else {
                clearInterval(setTimeLim);
                huge.src = "images/huge_static.gif";
                setTimeout(gameOverAnimation,2000);
            }
        },1000);

        $(e.target).animate({
           'opacity' : 0,
           'translateY': -800+'%'
        },{
            duration : 300,
            easing : 'easy-in-out',
            complete: function(){
                hitNumRemnant--;
                var textVal;
                if( hitNumRemnant > 0 ){
                    textVal = '再摇一次('+hitNumRemnant+')';
                }else{
                    textVal ='Game Over';
                    $(this).removeClass('btn-warning').addClass('disabled');
                    $body.off('tap',startBtn);
                }
                $(this).text(textVal);
                isFirstHit = false;
            }
        });
        $(DeviceMotionNum.parentNode).animate({
            'opacity' : 1.0,
            'translateY':0
        },300,'easy-in');

        function gameOverAnimation(){
            $(DeviceMotionNum.parentNode).animate({
                'opacity' : 0,
                'translateY':800+'%'
            },300,'easy-in');

            $(e.target).animate({
                'opacity' : 1.0,
                'translateY':0
            },300,'easy-in-out');
        }
        //DeviceMotionNum.parentNode.style.display = 'block'; //显示摇的次数
        //e.target.style.display = 'none';//隐藏按钮
        e.stopPropagation();

    }

    function deviceMotionHandler(eventData) {  //设备摇动判断
        var acceleration = eventData.accelerationIncludingGravity;
        var curTime = new Date().getTime();
        if ((curTime - last_update) > 100 && secMind >0 ) {
            var diffTime = curTime - last_update;
            last_update = curTime;
            x = acceleration.x;
            y = acceleration.y;
            z = acceleration.z;
            var speed = Math.abs(x + y + z - last_x - last_y - last_z) / diffTime * 10000;
            if (speed > SHAKE_THRESHOLD) {
                huge.src = "images/huge.gif";
                DeviceMotionNum.innerHTML = ++num;  //摇的次数
                //media.setAttribute("src", "sound/splatter.mp3");
                //media.load();
                media.play();
            }
            last_x = x;
            last_y = y;
            last_z = z;

        }
    }
    function ajaxSend(data,url){
        $.ajax({
            type: "get",
            url: url,
            data: data,
           // beforeSend: beforeSendFun,
            dataType: "jsonp",
            jsonp: "callbackparam",//传递给请求处理程序或页面的，用以获得jsonp回调函数名的参数名(默认为:callback)
            jsonpCallback:"success_jsonpCallback",//自定义的jsonp回调函数名称，默认为jQuery自动生成的随机函数名
            success: function (data) {
                alert(data);
            },
            timeout:"3000",
            error: function () { alert('fail');}
        });
    }
   /* function contentImport(nodeId, scriptId, getData){    //ajax回调重置模型数据
        var contents = tmplate(scriptId, getData);
        $(nodeId).html(contents);
    }
    var htmls = template(rankingScript, getData);
    $(rankingsContLayer).html(htmls);

    $('#rankingsForWeek').html();*/


   /*----------------------活动规则------------------------------*/
    $body.on('tap', 'a.bk-js', function(e){
        var target = e.target;
        $(target).parent().next('ul.rule-details-list').toggle();
        $(target).toggleClass('dropDownIco');
        e.stopPropagation();
    });
    $('a.bk-js').trigger('tap');
});
