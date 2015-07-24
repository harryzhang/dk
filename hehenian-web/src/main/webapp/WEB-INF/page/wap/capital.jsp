﻿<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<jsp:include page="/include/wap-head.jsp"></jsp:include>
</head>
<body>
<div id="preloader">
  <div id="status">
    <p class="center-text"> 正在加载... </p>
  </div>
</div>
<div class="all-elements">
  <div id="sidebar" class="page-sidebar">
    <div class="page-sidebar-scroll">
      <div class="sidebar-section">
        <p>目录</p>
        <a href="#" class="sidebar-close"></a> </div>
      <div class="navigation-items">
        <div class="nav-item"> <a href="index.html" class="home-nav">首页<em class="unselected-nav"></em></a> </div>
        <div class="nav-item"> <a href="#" class="features-nav submenu-deploy">个人中心<em class="dropdown-nav"></em></a>
          <div class="nav-item-submenu"> <a href="user.html">个人信息<em class="unselected-sub-nav"></em></a> <a href="investment.html">理财投资<em class="unselected-sub-nav"></em></a> <a href="capital.html">资金管理<em class="selected-sub-nav"></em></a> </div>
        </div>
        <div class="nav-item"> <a href="tp-list.html" class="contact-nav">投标列表<em class="unselected-nav"></em></a> </div>
        <div class="sidebar-decoration"></div>
      </div>
    </div>
  </div>
  <div id="content" class="page-content">
    <div class="page-header"> <a href="#" class="deploy-sidebar"></a>
      <p class="bread-crumb">资金管理</p>
      <a href="contact.html" class="deploy-contact"></a> </div>
    <div class="content">
      <div class="container" style=" margin-top:10px;">
        <div style="border-radius:100%;border:15px #f19149 solid;width:260px;height:260px; text-align:center; margin:0px auto; font-size:22px;">
          <ul style=" padding-top:80px; ">
            <li style="list-style-type:none;">我的累计收益</li>
            <li style="list-style-type:none; margin-top:10px; font-size:36px; height:40px; line-height:40px;">50000000.00</li>
          </ul>
        </div>
      </div>
       <div class="container" style=" text-align:center;">
                    <a href="#" class="button-big button-green">充值</a>
                    <a href="#" class="button-big button-orange">提现</a>
                </div>
      <div class="decoration"></div>
      <div class="container no-bottom">
         <div class="section-title" style=" margin-bottom:10px;">
          <h4>资产统计</h4>
        </div>
        <div class="one-half-responsive">
          <p><span class="text-highlight highlight-turqoise">资产总额：</span>872970.50 元</p>
          <p><span class="text-highlight highlight-green">冻结总额：</span>20300.00 元 </p>
          <p><span class="text-highlight highlight-blue">可用余额：</span>851289.00 元</p>
          <p><span class="text-highlight highlight-magenta">累计收益：</span>18.00 元</p>
          <p><span class="text-highlight highlight-dark">待收利息：</span>31.50 元</p>
          <p><span class="text-highlight highlight-red">待收本金：</span>1350.00 元</p>
        </div>
      </div>
      <div class="container no-bottom">
        <div class="section-title" style=" margin-bottom:10px;">
          <h4>收支明细</h4>
        </div>
        <table cellspacing='0' class="table">
          <tr class='even'>
            <td class="table-sub-title">类型</td>
            <td class="table-sub-title">收入</td>
            <td class="table-sub-title">支出</td>
            <td class="table-sub-title">时间</td>
          </tr>
          <tr >
            <td>投资收到还款</td>
            <td>102.00</td>
            <td>0.00</td>
            <td>2014-05-20</td>
          </tr>
          <tr class='even'>
            <td>投资收到还款</td>
            <td>51.00</td>
            <td>0.00</td>
            <td>2014-05-20</td>
          </tr>
          <tr>
            <td>投资收到还款</td>
            <td>151.00</td>
            <td>0.00</td>
            <td>2014-05-20</td>
          </tr>
          <tr class='even'>
            <td>投资收到还款</td>
            <td>256.00</td>
            <td>0.00</td>
            <td>2014-05-20</td>
          </tr>
          <tr>
            <td>投资收到还款</td>
            <td>0.00</td>
            <td>91.00</td>
            <td>2014-05-20</td>
          </tr>
        </table>
      </div>
      <div class="decoration" style="margin-top:15px;"></div>
      <div class="container no-bottom">
        <div class="clear"></div>
      </div>
      <div class="content-footer" style=" text-align:center;margin-bottom:10px; font-size:10px;"> 爱生活、爱理财、就上合和年<br>
        花样年集团成员 （香港联交所上市企业HK1777） </div>
    </div>
  </div>
</div>
</body>
</html>
