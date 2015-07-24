<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<title>投标详情</title>
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
          <div class="nav-item-submenu"> <a href="user.html">个人信息<em class="unselected-sub-nav"></em></a> <a href="investment.html">理财投资<em class="unselected-sub-nav"></em></a> <a href="capital.html">资金管理<em class="unselected-sub-nav"></em></a> </div>
        </div>
        <div class="nav-item"> <a href="tp-list.html" class="contact-nav">投标列表<em class="selected-nav"></em></a> </div>
        <div class="sidebar-decoration"></div>
      </div>
    </div>
  </div>
  <div id="content" class="page-content">
    <div class="page-header"> <a href="#" class="deploy-sidebar"></a>
      <p class="bread-crumb">投标详情</p>
      <a href="contact.html" class="deploy-contact"></a> </div>
    <div class="content">
      <div class="container no-bottom" style="background:#edf0f3">
        <div class="section-title">
          <h4>厂房扩大生产短期周转</h4>
          <em1>编号：HHN6849165</em1> </div>
      </div>
      <div class="decoration"></div>
      <div class="container no-bottom container-b">
        <ul>
          <li>借款金额：50,000.00</li>
          <li>已投金额：45,000.00</li>
          <li>剩余可投金额：5,000.00</li>
          <li>年利率：12%</li>
          <li>借款期限：12个月</li>
          <li>还款方式：按月分期还</li>
          <li>已投人数：7人</li>
          <div class="chart"> <strong>投标进度</strong> <em>90%</em>
            <div class="chart-background">
              <div class="green-chart p90"></div>
            </div>
          </div>
          <div class="formFieldWrap">
            <label class="field-title contactNameField" for="contactNameField">请输入投标金额:</label>
            <input type="text" name="contactNameField" value="" class="contactField requiredField" id="contactNameField"/>
          </div>
          <div class="formFieldWrap">
            <label class="field-title contactEmailField" for="contactEmailField">请输入验证码: </label>
            <input type="text" name="contactEmailField" value="" class="contactField requiredField requiredEmailField" id="contactEmailField"/>
          </div>
          <div class="chart_buttom"><a href="tp-intro.html" class="button-big button-green">立即投标</a></div>
        </ul>
      </div>
      <div class="decoration"></div>
      <div class="container no-bottom">
        <div class="one-half-responsive">
          <h4>相关信息</h4>
          <ul>
            <li>用户:zh***</li>
            <li>性别：男 </li>
            <li>年龄：35 </li>
            <li>学历：高中/中专 </li>
            <li>收入：12000/月 </li>
            <li>职位：总经理</li>
            <li>公司名称： 广州鸣轩电器限公司</li>
            <li>公司行业： 电子、电工、电器</li>
            <li>现单位工作时间：6年</li>
          </ul>
        </div>
        <div class="decoration hide-if-responsive"></div>
        <div class="one-half last-column">
          <h4>审核信息</h4>
          <ul class="icon-list">
            <li class="tick-list">身份认证</li>
            <li class="tick-list">工作认证</li>
            <li class="tick-list">居住地认证</li>
            <li class="tick-list">信用报告</li>
            <li class="tick-list">收入认证</li>
            <li class="tick-list">房产证</li>
            <li class="tick-list">房屋租赁合同</li>
            <li class="tick-list">水电单据</li>
            <li class="tick-list">工作证明</li>
            <li class="tick-list">银行流水</li>
            <li class="tick-list">信用卡账单</li>
            <li class="tick-list">车产证</li>
            <li class="tick-list">社保</li>
            <li class="tick-list">营业执照</li>
            <li class="tick-list">租赁合同</li>
            <li class="tick-list">其它资产证明</li>
            <li class="tick-list">征信报告</li>
          </ul>
        
        </div>
        
        
        <div class="clear"></div>
      </div>
      <div class="decoration"></div>
      <div class="content-footer" style=" text-align:center;margin-bottom:10px; font-size:10px;">
        爱生活、爱理财、就上合和年<br>
花样年集团成员 （香港联交所上市企业HK1777）
       
            </div>
    </div>
  </div>
</div>
</body>
</html>