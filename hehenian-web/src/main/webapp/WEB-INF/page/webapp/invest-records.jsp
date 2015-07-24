<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<jsp:include page="/include/wap-head.jsp"></jsp:include>
</head>
<body>
<div id="preloader">
  <div id="status">
    <p class="center-text">正在加载...</p>
  </div>
</div>

<!--顶部底部浮动层-->

<jsp:include page="/include/wap-bottom.jsp"></jsp:include>

<!-- main -->
<div id="main-wrap">
  <div id="container"  style=" padding-left:0px; padding-right:0px;">
    <div class="content">
      <div class="section-title" style=" padding:10px 0px;; background-color:#50c2e9;border-top:#6fd0f0 1px solid;border-bottom:#32aadf 1px solid; color:#FFF; font-weight:bold; font-size:16px; text-align:center;"> 投资记录 </div>
      <div class="one-half-responsive last-column">
        <div class="container">
          <jsp:include page="more-invest-records.jsp"></jsp:include>
          
        </div>
      </div>
    </div>
  </div>
</div>
</body>
<script type="text/javascript">  
        var range = 50;             //距下边界长度/单位px  
        var elemt = 500;           //插入元素高度/单位px  
        var maxnum = 20;            //设置加载最多次数  
        var num = 1;  
        var totalheight = 0;   
        var main = $(".container");                     //主体元素  
        $(window).bind("scroll",function(){  
        	doLoadMore();
        });  
        var pageIndex=1;
        var canLoad=true;
        function doLoadMore(){
        	if(!canLoad){
        		return false;
        	}
        	var srollPos = $(window).scrollTop();    //滚动条距顶部距离(页面超出窗口的高度)  
            //console.log("滚动条到顶部的垂直高度: "+$(document).scrollTop());  
            //console.log("页面的文档高度 ："+$(document).height());  
            //console.log('浏览器的高度：'+$(window).height());  
              
            totalheight = parseFloat($(window).height()) + parseFloat(srollPos);  
            if(($(document).height()-range) <= totalheight  && num != maxnum) {  
            	 $(window).unbind("scroll"); 
            	$.post("webapp-more-investrecord.do",{pageIndex:pageIndex},function(data){
            		pageIndex++;
            		$(".container").append(data);
            		 $(window).bind("scroll",function(){  
            	        	doLoadMore();
            	        });  
            	});
               // main.append("<div style='border:1px solid tomato;margin-top:20px;color:#ac"+(num%20)+(num%20)+";height:"+elemt+"' >hello world"+srollPos+"---"+num+"</div>");  
               // num++;  
            }  
        }
    </script>
</html>
