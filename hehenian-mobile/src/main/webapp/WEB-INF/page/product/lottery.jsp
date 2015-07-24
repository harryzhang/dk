<%@ page contentType="text/html; charset=utf-8"%>
<!doctype html>
<html>
<head>
	<%@ include file="../common/head.jsp" %>
	<% request.setAttribute("menuIndex", 0); %>
	<link rel="stylesheet" href="http://static.hehenian.com/m/activity/css/lottery.css">
    <link rel="stylesheet" href="http://static.hehenian.com/m/js/widget/modal/modalmin.css">
    <title>${channel_name}-全民来中奖</title>
</head>

<body>
    <h1 class="banner">
		<img src="http://static.hehenian.com/m/activity/img/rotate/banner.png" width="100%" alt="">
	</h1>

    <div class="rotate-wrapper">
        <div class="content animate-rotate" id="rotateLottery">
        </div>
        <div class="pointer" id="pointer">
            <i></i>
        </div>
    </div>
    
   <div class="text-wrapper" style="max-height:150px;overflow:hidden;position:relative;<c:if test='${empty topList }'>display:none;</c:if>">
        <ul class="scrollText">
        <c:forEach var="item" items="${topList }">
            <li>${item.userName }，抽中了${item.prizeName }元</li>
        </c:forEach>
        </ul>
    </div>
    

    <div class="text-wrapper" style="margin-top:14px;">
        <dl>
            <dt>一、活动对象</dt>
            <dd>通过国际物业注册的用户</dd>
            <dt>二、活动日期</dt>
            <dd>2015年5月15日-2015年5月30日</dd>
            <dt>三、活动细则：</dt>
            <dd>1.投资任何项目（投资期限6个月以上）满1000元即可获得一次抽奖机会，可以叠加。
            </dd>
            <dd>2.中奖奖金直接发到合和年在线账户里</dd>
        </dl>
        <p>本活动最终解释权为合和年在线所有</p>
    </div>

    <%@ include file="../common/tail.jsp" %>
    <script src="http://static.hehenian.com/m/js/widget/modal/modalmin.js"></script>
    <script>
    // $(function(){
    var pointer = $('#pointer'), //按钮
        lottery = $('#rotateLottery'), //转盘
        step = 8, //8个奖项
        stepRotate = 360 / 8; //每个奖项的角度
	
    function getRotate(step) {
        lottery.removeClass('animate-rotate').addClass('rotatefn');
        var fill = Math.random()*stepRotate;
        if(fill>stepRotate-3){
        	fill = stepRotate-3;
        }else if(fill < 3){
        	fill = 3;
        }
        setTimeout(function() {
            lottery.css({
                webkitTransform: 'rotate(' + (8 * 360 + (step - 1) * stepRotate + fill) + 'deg)',
                transform: 'rotate(' + (8 * 360 + (step - 1) * stepRotate + fill) + 'deg)'
            });
        }, 20);
    };

    pointer.on('click', function() {
        var self = $(this);
        if (self.data('ing')) return;
        $(this).data('ing', 1);
            lottery.css({
                webkitTransform: 'rotate(0deg)',
                transform: 'rotate(0deg)'
            });
            lottery.addClass('animate-rotate');
            var options = {type:"POST",url:"http://m.hehenian.com/product/ajaxLuckyDraw.do",data:{}};
			ajaxRequest(options,function(data){
			    if(data.status==0){
			    	 new Modalmin({
		                content:data.message,
		                ok: function() {}
		            });
		            self.data('ing',0);
		            return;
			    }
	            setTimeout(function() {
	                getRotate(data.id);
	            }, 5);
	            setTimeout(function() {
	                self.data('ing', 0);
	                lottery.removeClass('rotatefn');
	                new Modalmin({
		                content: getMessage(data),
		                ok: function() {}
		            });
		            $('.scrollText').prepend('<li>'+data.userName+'，抽中了'+data.amount+'元</li>');
	            	$('.scrollText').parent().css({display:'block'});
	            }, 6620);
             });
    });
    
(function(){
    var text = $('.scrollText').html();
    var li = $('.scrollText li');
    if(li.size()>5){
    	$('.scrollText').html(text + text);
    	$('.scrollText').addClass('scrollTextAn');
    }
})();
    
    function getMessage(data){
    	var msg = "恭喜您获得"+data.amount+"元奖励，距离旁边的<br/>万元君只差一点点了，快把它拿走吧~";
    	if(data.id==2 || data.id==3 || data.id==5){
    		msg = "恭喜您获得"+data.amount+"元奖金，万元君还在<br/>等着您，继续抽奖吧~您还有"+data.done+"次抽奖机会";
    	}else if(data.id==4){
    		msg = "恭喜您获得百元奖金，离万元君只有两个鸡蛋<br/>的距离，还在等什么，快来抱走万元大奖吧~";
    	}else if(data.id==6){
    		msg = "恭喜您获得"+data.amount+"元奖金，再接再厉就可获得<br/>万元大奖哦，快来抽奖吧！您还有"+data.done+"次抽奖机会";
    	}else if(data.id==7){
    		msg = "恭喜您获得千元奖金，千元君已经<br/>被您抱走了，万元君唾手可得，快继续抽奖吧~";
    	}else if(data.id==8){
    		msg = "运气值max~恭喜您获得万元大奖，快继续投资<br/>换取更多抽奖机会吧，祝您好运连连~";
    	}
    	return msg;
    }
    
     //异步请求
	function ajaxRequest(options,callback){
		$.ajax({ 
			type:options.type,
			dataType: "json",			
			url:options.url, 
			data:options.data,
			success:callback,
			error:function(data){
				new Modalmin({
	                content: '网络不给力，稍后重试',
	                ok: function() {}
	            });
			}
		});
	}
    // });
    </script>
</body>
</html>
