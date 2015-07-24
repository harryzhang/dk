<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html><head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<%
		Object obj = request.getSession().getAttribute("user");
		if(obj == null) {
		    response.sendRedirect("http://mp.weixin.qq.com/s?__biz=MzA5ODA2NTQ3MQ==&mid=202006066&idx=1&sn=5a6e9e04e7dcf8c22101100f207bb1d3&scene=1&from=groupmessage&isappinstalled=0#rd");
		}
	%>
    <meta charset="utf-8">
    <title>留住财神爷</title>
    <meta name="viewport" content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="full-screen" content="true">
    <meta name="screen-orientation" content="portrait">
    <meta name="x5-fullscreen" content="true">
    <meta name="360-fullscreen" content="true">
    <style>
        body {
            text-align: center;
            background: #000000;
            padding: 0;
            border: 0;
            margin: 0;
            height: 100%;
        }

        html {
            -ms-touch-action: none; /* Direct all pointer events to JavaScript code. */
        }

        .sbgshow {
            display: block;
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            text-align: center;
            color: #fff;
            font-size: 30px;
            line-height: 1.7em;
            background: rgba(0,0,0,0.85);
        }

         .sbgshow .arron {
             position: absolute;
             top: 8px;
             right: 8px;
             width: 100px;
             height: 100px;
             background: url(http://file.seeyouyima.com/game/mao/resource/assets/arron.png) no-repeat;
             background-size: 100px 100px;
         }

            .sbgshow p {
                padding-top: 78px;
            }

        .sbg {
            display: none;
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            text-align: center;
            color: #fff;
            font-size: 26px;
            line-height: 1.7em;
            background: rgba(0,0,0,0.85);
        }

            .sbg .arron {
                position: absolute;
                top: 8px;
                right: 8px;
                width: 100px;
                height: 100px;
                background: url(http://file.seeyouyima.com/game/mao/resource/assets/arron.png) no-repeat;
                background-size: 100px 100px;
            }

            .sbg p {
                padding-top: 78px;
            }

        #download {
            display: block;
            position: fixed;
            top: 0;
            left: 0;
            right: 0;
            width: 100%;
            height: 45px;
            color: white;
            background: rgba(0,0,0,0.5);
            overflow: hidden;
            zoom: 1;
            z-index: 9999;
        }

		#RunTopic {
			text-align:center;
			display: block;
			width:480px;
			margin:0px auto;
		}
		
		#RunTopic div {
			margin:0px auto;
			display: block;
			font-size: 18px;
		}

        a{
            -webkit-touch-callout: none;
            -webkit-appearance: none;
            display: block;
            color: #6c6155;
            text-decoration: none;
        }

    </style>
</head>
<body style="padding: 0px; border: 0px; margin: 0px;">
    <a id="download">
		<div id="RunTopic">
			
		</div>
    </a>

    <div style="display: inline-block; width: 100%; height: 100%; margin: 0 auto; background: black; position: relative;" id="gameDiv">
        <canvas id="gameCanvas" width="480" height="800" style="width: 480px; height: 800px; background-color: rgb(0, 0, 0);"></canvas>
    </div>
    <div id="sbg" class="sbg">
        <div class="arron"></div>
        <p id="msg">
            请点击右上角<br>
            点击【分享到朋友圈】<br>
        </p>
    </div><div id="money" style="display:none"></div>

    <script>        var document_class = "GameApp";</script><!--这部分内容在编译时会被替换，要修改文档类，请到工程目录下的egretProperties.json内编辑。-->
	 <script language="javascript" type="text/javascript" src="static/js/resource_loader.js?ver=255"></script>
	<script src="static/js/egret_loader.js"></script>
	<script src="static/js/game-min-0722.js?223"></script>
	
<script>

var ScrollTime;
function ScrollAutoPlay(contID,scrolldir,showwidth,textwidth,steper){
	var PosInit,currPos;
	with($j('#'+contID)){
		currPos = parseInt(css('margin-left'));
		if(scrolldir=='left'){
			if(currPos<0 && Math.abs(currPos)>textwidth){
				css('margin-left',showwidth);
			}
			else{
				css('margin-left',currPos-steper);
			}
		}
		else{
			if(currPos>showwidth){
				css('margin-left',(0-textwidth));
			}
			else{
				css('margin-left',currPos-steper);
			}
		}
	}
}

//--------------------------------------------左右滚动效果----------------------------------------------
/*
AppendToObj：		显示位置（目标对象）
ShowHeight：		显示高度
ShowWidth：		显示宽度
ShowText：			显示信息
ScrollDirection：	滚动方向（值：left、right）
Steper：			每次移动的间距（单位：px；数值越小，滚动越流畅，建议设置为1px）
Interval:			每次执行运动的时间间隔（单位：毫秒；数值越小，运动越快）
*/
function ScrollText(AppendToObj,ShowHeight,ShowWidth,ShowText,ScrollDirection,Steper,Interval){
	var TextWidth,PosInit,PosSteper;
	with(AppendToObj){
		html('');
		css('overflow','hidden');
		css('height',ShowHeight+'px');
		css('line-height',ShowHeight+'px');
		css('width',ShowWidth);
	}
	if (ScrollDirection=='left'){
		PosInit = ShowWidth;
		PosSteper = Steper;
	}
	else{
		PosSteper = 0 - Steper;
	}
	if(Steper<1 || Steper>ShowWidth){Steper = 2}//每次移动间距超出限制(单位:px)
	if(Interval<1){Interval = 10}//每次移动的时间间隔（单位：毫秒）
	var Container = $j('<div></div>');
	var ContainerID = 'ContainerTemp';
	var i = 0;
	while($j('#'+ContainerID).length>0){
		ContainerID = ContainerID + '_' + i;
		i++;
	}
	with(Container){
	  attr('id',ContainerID);
	  css('float','left');
	  css('cursor','default');
	  appendTo(AppendToObj);
	  html(ShowText);
	  TextWidth = width();
	  if(isNaN(PosInit)){PosInit = 0 - TextWidth;}
	  css('margin-left',PosInit);
	  mouseover(function(){
		  clearInterval(ScrollTime);
	  });
	  mouseout(function(){
		  ScrollTime = setInterval("ScrollAutoPlay('"+ContainerID+"','"+ScrollDirection+"',"+ShowWidth+','+TextWidth+","+PosSteper+")",Interval);
	  });
	}
	ScrollTime = setInterval("ScrollAutoPlay('"+ContainerID+"','"+ScrollDirection+"',"+ShowWidth+','+TextWidth+","+PosSteper+")",Interval);
}

    var mebtnopenurl = '';
    window.shareData = {
        "imgUrl": "www.hehenian.com/sjm/static/images/btn_start.png",
        "timeLineLink": "http://mp.weixin.qq.com/s?__biz=MzA5ODA2NTQ3MQ==&mid=202006066&idx=1&sn=5a6e9e04e7dcf8c22101100f207bb1d3&scene=1&from=groupmessage&isappinstalled=0#rd",
        "tTitle": "留住财神爷！",
        "tContent": "在9×9范围内的格子中，留住财神爷。"
    };

    function showme() {
        //window.location.href = "http://mp.weixin.qq.com/s?__biz=MjM5NjExNzA0MQ==&mid=200076719&idx=1&sn=304ac9484d57a90945ba494884e2c2e1#rd";
    }
    function dp_share(n, m) { //通知好友会调用这个方法
        if (m == 0) {
            document.title = window.shareData.tContent = "留住财神爷"
        }
        
        if (m == 1) {
        	if($j("#money").text()){
        	    document.title = window.shareData.tContent = "我用了" + n + "步留住了财神爷！！！击败" + (100 - n) + "%的人,财神爷给我发了奖励"+ $j("#money").text()+"，你也来试试！";
        	}else {
        		document.title = window.shareData.tContent = "我用了" + n + "步留住了财神爷！！！击败" + (100 - n) + "%的人，你也来试试！";
        	}
        }
        if (m == 2) {
            document.title = window.shareData.tContent = "让财神爷逃跑啦，你来帮我留住他，留住有钱领哦！";
        }
        document.getElementById("sbg").className = "sbgshow";
        window.setTimeout(hiddenMe, 5000);
    }

    function hiddenMe() {
        document.getElementById("sbg").className = "sbg";
    }
    
    egret_h5.startGame();
    
    $j(document).ready(function () {
        if (/Android|Windows Phone|webOS|iPhone|iPod|BlackBerry/i.test(navigator.userAgent)) {
            //
        } else {
            $j(".aliForPc").show();
        }
    });
</script>


</body></html>