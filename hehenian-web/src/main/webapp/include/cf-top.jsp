<%@page import="com.sun.org.apache.xml.internal.serialize.Printer"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/include/taglib.jsp"%>

<!-- 工具栏 start -->
<div class="topBanner_box">
      <div class="toolbar el_container"> 
    
    <!-- left part start -->
    <div class="fn_left"> <a class="favorite el_btn" title="添加收藏"> <i class="icon"></i> <span class="txt">添加收藏</span> </a> <a class="phoneToCL el_btn" title="手机彩之云" href="http://mapp.colourlife.com/" target="_blank"> <i class="icon"></i> <span class="txt">手机彩之云</span> </a> <a class="ttWeibo shareBox el_btn" href="http://e.t.qq.com/caishenghuoshequ?preview" target="_blank"> <i class="icon"></i> </a> <a class="sinaWeibo shareBox el_btn" href="http://e.weibo.com/3170349347/profile" target="_blank"> <i class="icon"></i> </a> <a class="weixin shareBox el_btn"> <i class="icon"></i> <span class="shareBox_inner"> <strong class="shareBox_box"> <img src="http://cc.colourlife.com/common/images/pic_blank.gif" class="codeImg"> <span class="txt">关注微信公众账号，获取更多优惠。</span> </strong> </span> </a> </div>
    <!-- left part end --> 
    
    <!-- right part start -->
   <div class="fn_right">
                            <a href="http://cyz.colourlife.com/" class="optLink el_btn">个人主页</a>
                <a href="http://cyz.colourlife.com/order" class="optLink el_btn" title="我的订单">我的订单</a>
                <a href="http://cyz.colourlife.com/site/logout" class="optLink el_btn" title="退出">退出</a>
                        <span href="#" class="optMore el_btn">
                    <span class="optMore_item">
                        <span class="txt" title="帮助">帮助</span>
                        <i class="icon"></i>
                    </span>
                    <span class="optMore_box">
                        <div class="optMore_box_inner">
                                                                                                <a href="http://www.colourlife.com/help?category_id=40" class="optMore_link optMore_link_first">   常见问题</a>
                                                                                                                                <a href="http://www.colourlife.com/help?category_id=27" class="optMore_link">售后服务</a>
                                                                                                                                <a href="http://www.colourlife.com/help?category_id=28" class="optMore_link optMore_link_last">客服中心</a>
                                                                                    </div>
                    </span>
                </span>
        </div>
    <!-- right part end -->
    
    <div class="fn_clear"></div>
  </div>
    </div>
<!-- 工具栏 end --> 

<!-- 头部广告栏 start -->
<div class="topBanner el_container"> <a href="http://www.colourlife.com/luckyAppWeb" class="imgLink" target="_blank"> <img src="http://cc.colourlife.com/common/images/ad/12new.jpg" class="demoPic"> </a> </div>
<!-- 头部广告栏 end --> 

<!-- 搜索栏 start -->
<div class="searchBar el_container"> 
      
      <!-- logo start -->
      <div class="logoBox fn_left"> <a href="http://bslt.c.colourlife.com/" class="logoLink" title="彩生活"> <img src="http://cc.colourlife.com/common/images/pic_blank.gif" class="logoImg"> </a> </div>
      <!-- logo end --> 
      
      <!-- search start -->
      <form id="search-form" action="/site/search" method="GET">
    <div class="searchBox fn_left">
          <div class="searchBox_tabs"> <a class="searchBox_tab searchBox_tab_cur"
               val="1">商家</a> <a class="searchBox_tab "
               val="2">商品</a> <a class="searchBox_tab "
               val="3">信息</a>
        <input class="searchBox_tab_val" name='searchType' type="hidden"
                   value="1">
      </div>
          <div class="searchBox_container">
        <input type="text" name='searchText' class="keyword_by_el_inText el_inText"
                   value="" defaultTxt="搜索">
        <button class="search_by_el_btn el_btn"> <i class="icon"></i> </button>
      </div>
        </div>
  </form>
      <!-- search end --> 
      
      <!-- telephone start -->
      <div class="phoneBox fn_right"> <span class="phoneBox_item"> <i class="icon"></i> <span class="txt">客服热线:4008 893 893</span> </span> </div>
      <!-- telephone end -->
      
      <div class="fn_clear"></div>
    </div>
<!-- 搜索栏 end --> 

<!-- 主导航栏 start -->
<div class="mainNav">
      <div class="mainNav_box el_container"> 
    <!-- address start --> 
    <span class="addressBox"> <span class="el_selectBox"> <span class="el_selectBox_valBox el_selectBox_valBox_bg">
    <input type="text" class="el_selectBox_val" title="碧水龙庭" 
                                   value="碧水龙庭" disabled="disabled">
    <i class="el_selectBox_icon"></i> </span> </span> </span> 
    <!-- address end --> 
    
    <a href="http://bslt.c.colourlife.com/" class="mainNav_item" title="小区首页"> 
    <span class="txt">小区首页</span> </a> 
    <a href="http://bslt.c.colourlife.com/property" class="mainNav_item" title="物业服务"> 
    <span class="txt">物业服务</span> </a> <a href="http://bslt.c.colourlife.com/facility" class="mainNav_item" title="周边优惠"> 
    <span class="txt">周边优惠</span> </a> 
    
    <a href="http://bslt.c.colourlife.com/goods" class="mainNav_item" title="生活超市"> 
    <span class="txt">生活超市</span> </a> <a href="http://bslt.c.colourlife.com/cheap" class="mainNav_item" title="天天特价"> 
    <span class="txt">天天团</span> </a> <a href="http://bslt.c.colourlife.com/yellowPage" class="mainNav_item" title="黄页信息"> 
    <span class="txt">黄页信息</span> </a> <a href="http://bslt.c.colourlife.com/surrounding" class="mainNav_item" title="便民服务"> 
    <span class="txt">便民服务</span> </a> <a href="http://mapp.colourlife.com/" class="mainNav_item" title="APP下载"> 
    <span class="txt">投资理财</span> </a> 
   
    
  </div>
    </div>
<!-- 主导航栏 end -->

 <script>
  $(function(){
	  setInterval(function(){
$.post("/message.do");
},120000);
  });
  </script>
  
  <%@ page import="java.text.*" %>
<%
DateFormat dateFormat=new SimpleDateFormat("yyyyMMdd");
request.setAttribute("nowDay",dateFormat.format(new Date()));
%>
