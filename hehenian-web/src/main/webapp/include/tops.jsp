<%@page import="com.sun.org.apache.xml.internal.serialize.Printer"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="taglib.jsp" %>
<link rel="shortcut icon" href="favicon.ico">
<!--顶部状态栏 开始-->
<div class="l_top_top">
	<div class="l_top_top_center">
		<div class="l_top_top_center_left">
			<!-- <a href="update.jsp"><img src="images/mobile_01.png" />手机版</a>
			<span>|</span> -->
			<a href="javascript:addCookie();">设为首页</a>
			<span>|</span>
			<a href="javascript:setHomepage();">加入收藏</a>
			<span>|</span>
			<a href="callcenter.do">帮助中心</a>
		</div>
		<div class="l_top_top_center_right">
			<a href="/account/login-index.do">登录</a><span>|</span><a href="/account/reg.do">注册</a>&nbsp;
			<span> | </span><a href="/callcenter.do?showkf=kfs" class="kf">客服</a>
			<!-- <img src="images/weibo.png" /><a href="javascript:void(0);">新浪共享</a>
			<img src="images/qq_gx_img.png" /><a href="javascript:void(0);">QQ共享</a> -->
		</div>
	</div>
</div>
<div class="top">
<script type="text/javascript" src="script/gg.js"></script>
  <div class="logo"> <a href=""><img src="images/index9_03.jpg" /></a></div>

  <div class="top_pho"><img src="images/top_dh_bg.png" /></div>
</div>
<div class="nav">
  <div class="nav_left"> </div>
  <div class="nav_main">
    <ul>
      <li id="sy_hover"><a href="index.jsp">首页</a>
        <div class="h_menu none" id="sy_menu">
          <ul>
            <li class="first"><a href="getMessageBytypeId.do?typeId=2">平台原理</a></li>
            <li class="s-line2"></li>
            <li><a href="frontTeam.do">团队介绍</a></li>
            <li class="s-line2"></li>            
            <li><a href="getMessageBytypeId.do?typeId=3">法律政策</a></li>
            <li class="s-line2"></li>
            <li><a href="getMessageBytypeId.do?typeId=1">关于我们</a></li>
            <li class="s-line2"></li>
            <li><a href="download.do">下载专区</a></li>
            <li class="s-line2"></li>          
          </ul>
        </div>
      </li>
      <li id="licai_hover"><a href="finance.do">我要理财</a>
      <div class="h_menu none" id="licai_menu">
          <ul>
            <li class="first"><a href="getMessageBytypeId.do?typeId=8">如何理财</a></li>
            <li class="s-line2"></li>
            <li class="two" ><a href="financetool.do">工具箱</a></li>
            <li class="s-line2"></li>
            <!--  <li class="three" ><a href="become2FinanceInit.do">成为理财人</a></li>-->
          </ul>
        </div>
      </li>
      <li id="jk_hover"><a href="borrow.do">我要借款</a>
      <div class="h_menu none" id="jk_menu">
          <ul>
            <li class="first"><a href="borrow.do">申请贷款</a></li>
            <li class="s-line2"></li>
            <li></li>
          </ul>
        </div>
      </li>
      <li id="zq_hover"><a href="creditor.do">债权转让</a></li>
      <li id="zh_hover"><a href="home.do">我的账户</a></li>
      <li id="bj_hover"><a href="capitalEnsure.do">本金保障</a></li>
      <li id="kf_hover"><a href="callcenter.do">客服中心</a></li>
      <li id="lt_hover" style="background:none;"><a target="_blank"  href="loginBBS.do">论坛</a></li>
    </ul>
  </div>
  <div class="nav_right"> </div>
</div>
<div class="nav_bottom">
  <div class="nav_b_left"></div>
  <div class="nav_b_right"></div>
</div>
        <!--顶部主导航 结束-->