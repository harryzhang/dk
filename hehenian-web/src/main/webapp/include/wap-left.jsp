<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp"%>
<div id="sidebar" class="page-sidebar">
    <div class="page-sidebar-scroll">
      <div class="sidebar-section">
        <p>目录</p>
        <a href="#" class="sidebar-close"></a> </div>
      <div class="navigation-items">
        <div class="nav-item"> <a href="webapp-index.do" class="home-nav">首页<em class="unselected-nav"></em></a> </div>
        <div class="nav-item"> <a href="javascript:;" class="features-nav submenu-deploy">个人中心<em class="dropdown-nav"></em></a>
          <div class="nav-item-submenu"> 
		  <a href="webapp-userinfo.do">个人信息<em class="unselected-sub-nav"></em></a> 
		  <a href="webapp-investDetailCount.do">理财投资<em class="unselected-sub-nav"></em></a> 
		  <a href="webapp-money.do">资金管理<em class="unselected-sub-nav"></em></a> </div>
        </div>
        <div class="nav-item"> <a href="webapp-finance.do" class="contact-nav">投资列表<em class="unselected-nav"></em></a> </div>
        <div class="sidebar-decoration"></div>
      </div>
    </div>
  </div>
  <script>
  $(function(){
	  setInterval(function(){
$.post("/cf/heartbeat.do");
},120000);
  });
  </script>