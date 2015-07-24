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
    	<p class="center-text">
			正在加载...
        </p>
    </div>
</div>


<div class="all-elements">
    <div id="sidebar" class="page-sidebar">
        <div class="page-sidebar-scroll">
        	<div class="sidebar-section">
            	<p>目录</p>
                <a href="#" class="sidebar-close"></a>
            </div>
            
            <div class="navigation-items">
                <div class="nav-item">
                    <a href="index.html" class="home-nav">首页<em class="selected-nav"></em></a>
                </div> 
                <div class="nav-item">
                    <a href="#" class="features-nav submenu-deploy">个人中心<em class="dropdown-nav"></em></a>
                    <div class="nav-item-submenu">
                    	<a href="user.html">个人信息<em class="unselected-sub-nav"></em></a>
                        <a href="investment.html">理财投资<em class="unselected-sub-nav"></em></a>
                        <a href="capital.html">资金管理<em class="unselected-sub-nav"></em></a>
                    </div>
                </div> 

                <div class="nav-item">
                    <a href="tp-list.html" class="contact-nav">投标列表<em class="unselected-nav"></em></a>
                </div> 
                <div class="sidebar-decoration"></div>
                <div><img src="images/indexlogo.jpg" alt="" width="130"/></div>
                <div><img src="images/indexlogo1.jpg" alt="" width="130"/></div>
            </div>
            

   
                
        </div>
    </div>


    <div id="content" class="page-content">
    	<div class="page-header">
        	<a href="#" class="deploy-sidebar"></a>
            <p class="bread-crumb">中国首家P2P社区金融平台</p>
            <a href="contact.html" class="deploy-contact"></a>
        </div>
        
        <div class="content">

            
            <div class="container"  style="margin-bottom:0px;">
                <div class="slider-controls" data-snap-ignore="true">                
                    <div>
                        <img src="images/general-nature/2.jpg" class="responsive-image" alt="img">
                        
                    </div>
                
                    <div>
                        <img src="images/general-nature/3.jpg" class="responsive-image" alt="img">
                  
                    </div>

                    <div>
                        <img src="images/general-nature/1.jpg" class="responsive-image" alt="img">
                       
                    </div>
                </div>
                <a href="#" class="next-slider"></a>
                <a href="#" class="prev-slider"></a>
            </div>
                         
            <div class="container no-bottom">
            	<div class="section-title">
                	<h4>累计接收投资金额</h4>
                    <em>30,212,542.00</em>
                  
                </div>
            </div>
            
            <div class="decoration"></div>

            <div class="container no-bottom">
            	<div class="section-title" style="padding-top:0px;">
                	<h4>为投资者带来收益</h4>
                    <em>4,521,212.00</em>
                    
                </div>
            </div>
     
            <div class="decoration"></div>
               <div class="container">
                 <div style=" width:50%; float:left; text-align:center">
                    <a href="tp-list.html" class="button green-bubble" style=" width:80%;margin-left:0px">我要投资</a></div>
                     <div style=" width:50%; float:left; text-align:center;">
                    <a href="user.html" class="button button-yellow" style=" width:80%;margin-left:0px">我的账户</a></div>
                </div> 
            <div class="content-footer" style=" text-align:center;margin-bottom:10px; font-size:10px;">
        爱生活、爱理财、就上合和年<br>
花样年集团成员 （香港联交所上市企业HK1777）
       
            </div>
            
              
        </div>                
    </div>  
</div>

</body>
</html>
