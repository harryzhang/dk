<%@page import="com.sun.org.apache.xml.internal.serialize.Printer"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html xmlns:wb="http://open.weibo.com/wb">
<jsp:include page="head.jsp"></jsp:include>
<script src="http://tjs.sjs.sinajs.cn/open/api/js/wb.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript">
	$(function() {
		$(".header_two_right_ul li").hover(function() {
			$(this).find("ul").show();
		}, function() {
			$(this).find("ul").hide();
		})

		//ImgScroll(".mjd_banner", "#mjd_banner_ul", 1000, ".contrl", "a", "sydp-a5", "sydp-a6", "li", 1000, 900, 4000);

		//头部页面hover效果
		$(function() {
			$(".header_two_right_ul_tan li").hover(function() {
				$(this).find("a").css("color", "#e94718");
			}, function() {
				$(this).find("a").css("color", "#333333");
			});

		});

	});
	//菜单栏选中样式
	function hhn(index) {
		$(".hhn").eq(index).attr("style", "background:#fff;border-bottom:2px solid #e94718;");
		$(".hhn").eq(index).find("a:first").css("color", "#e94718");
	}
    function hhnNew(indexId) {
        $("#"+indexId).attr("style", "background:#fff;border-bottom:2px solid #e94718;");
        $("#"+indexId).find("a:first").css("color", "#e94718");
    }
	function setCookie(name,value)
	{
	    var Days = 30;
	    var exp = new Date();
	    exp.setTime(exp.getTime() + Days*24*60*60*1000);
	    document.cookie = name + "="+ escape (value) + ";expires=" + exp.toGMTString();

	}

	//读取cookies
	function getCookie(name)
	{
	    var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
	 
	    if(arr=document.cookie.match(reg))
	 
	        return (arr[2]);
	    else
	        return null;
	}
</script>
<!--顶部状态栏 开始-->
<div class="header">
  <div class="header_one">
    <div class="header_one_center">
    
      <div style="font-size: 13px; width:50% !important; width:49%; float:left; font-size:80%">
     
      <div style="float:left; margin-top:0px;" > <a href="${loanServerUrl}"><img src="<c:url value='/web_res/img/logo_zaixian.png'/>" style=" border:0px;" alt="合和年在线"/> </a></div>
       <div style="float:left; margin-top:0px;" > &nbsp;&nbsp;&nbsp;&nbsp;客服热线：<font style="font-size:17px;font-family: 'Merriweather', serif;">4008-303-737</font>&nbsp;&nbsp;&nbsp;&nbsp;服务时间：8:30-20:00&nbsp;&nbsp;&nbsp;&nbsp;</div>
      <p style="float:left; margin-top:15px;"><a class="ui-top-img-link weibo" href="http://weibo.com/u/3893072293" target="_blank">合和年新浪微博</a></p>
      <p style="float:left; margin-top:15px;"><a class="ui-top-img-link we-chat" href="<c:url value='/web_res/img/er_wema.jpg'/>"  rel="lytebox" title="请扫描二维码，添加微信账号，获取海量投资资讯及最新优惠信息">合和年微信</a></p>
      </div>
      <div style="font-size: 13px; width:50%; float:right; text-align:right ; ">
        <ul >
          <li style=" border-left:1px solid #FFF; padding:0px 0px 0px 10px; float:right; width:80px;"><span><a id='reg'>免费注册</a></span></li>
          <li style="border-right:1px solid #ccc ; padding:0px 20px; float:right; overflow:hidden; height:48px;"><span>
            <c:if test="${user !=null}"><a id='home' >[${user.username}]</a> <span></span> <a id='logout' href="<c:url value='/'/>">退出</a> </c:if>
            <c:if test="${user ==null}"><a id='login' >登录</a></c:if>
            </span></li>
        </ul>
      </div>
    </div>
  </div>
<script type="text/javascript">
	var urlTemp = window.location.href;
	document.getElementById("reg").href="${hhnServerUrl}/account/reg.do"+"?fromUrl="+urlTemp+"&source=100";
	if(${user !=null}){
		document.getElementById("home").href="${hhnServerUrl}/home.do"+"?fromUrl="+urlTemp+"&source=100";
		document.getElementById("logout").href="${hhnServerUrl}/logout.do"+"?fromUrl="+urlTemp+"&source=100";
	}else{
		document.getElementById("login").href="${hhnServerUrl}/account/login-index.do"+"?fromUrl="+urlTemp+"&source=100";
	}
</script>

<!--顶部主导航 结束-->