<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp"%>
<!DOCTYPE HTML>
<html>

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no" />
    <meta name="format-detection" content="telephone=no">
    <meta name="apple-mobile-web-app-capable" content="yes" />
    <meta name="apple-mobile-web-app-status-bar-style" content="black" />
    <meta name="keywords" content="" />
    <meta name="description" content="" />
    <title>活动详情</title>
    <style type="text/css">
    * {
        padding: 0;
        margin: 0;
    }
    article {
        padding-left: 10px;
    }
    body {
        background: #FEFFE0;
        font-size: 14px;
    }
    h2 {
        font-size: 14px;
        color: #fff;
        font-weight: normal;
        background: url(http://static.hehenian.com/m/img/mather_day/title.png) no-repeat;
        -webkit-background-size: 109.5px 24px;
        background-size: 109.5px 24px;
        height: 24px;
        padding: 2px 0 0 10px;
        margin: 10px 0;
        position: relative;
        margin-left: -10px;
    }
    p {
        padding: 0;
        margin: 0;
    }
    ul>li {
        counter-increment: section;
        padding-bottom: 5px;
        font-size: 14px;
        padding-left: 23px;
        text-indent: -23px;
    }
    ul>li:last-child {
        margin-bottom: 0;
    }
    ul>li:before {
        content: counter(section);
        counter-increment: 1;
        width: 20px;
        height: 20px;
        background: #FE3653;
        color: #fff;
        line-height: 20px;
        text-align: center;
        display: inline-block;
        border-radius: 10px;
        margin-right: 3px;
        text-indent: 0;
        padding-left: 0;
    }
    .list img{margin: 10px 0;}
    .number{color: #FE3653;padding-left: 15px;}

    .tips{
    	color: #996805;
    	font-size: 12px;
    	text-align: center;padding-top: 30px;padding-bottom: 8px;
    }
	.footer{
		height: 40px;
	}
    .fixed-nav{
    	position: fixed;
    	background: #FC3552;
    	left: 0;
    	bottom: 0;
    	height: 40px;
    	line-height: 40px;
    	width: 100%;
    	text-align: center;
    }
    .fixed-nav a{
    	color: #fff;
    	font-size: 14px;
    	text-decoration: none;
    }
    .fixed-nav a span{font-size: 16px;}
    .fixed-nav i{
    	display: block;
    	width: 8px;
    	height: 8px;
    	border-top: 2px solid #fff;
    	border-right: 2px solid #fff;
    	position: absolute;
    	top: 15px;
    	right: 20px;
    	-webkit-transform: rotate(45deg);
    	-ms-transform: rotate(45deg);
    	-o-transform: rotate(45deg);
    	transform: rotate(45deg);
    }
    </style>
</head>

<body>
    <article>
        <h2>活动日期</h2>
        <p>2015年5月7日-2015年5月17日</p>
        <h2>参与方式</h2>
        <ul>
            <li>关注“合和年在线”微信账号</li>
            <li>活动期间，在合和年在线进行注册并投资（不包含债权转让）的用户均可参与</li>
        </ul>

        <h2>获奖规则</h2>
        <ul>
            <li>奖品将从活动期间在合和年在线进行投资的所有用户中进行抽奖</li>
            <li>抽奖结果将会在合和年在线官网和微信账号上公布</li>
            <li>奖品将会在活动结束后10个工作日内发送</li>
        </ul>

        <div class="list">
            <h2>奖品设置</h2>
            <p>一等奖：iPad mini3 <span class="number">1名</span></p>
            <img src="http://static.hehenian.com/m/img/mather_day/j1.png" height="50" alt="">
            <p>二等奖：小米移动电源<span class="number">2名</span></p>
            <img src="http://static.hehenian.com/m/img/mather_day/j2.png" height="50" alt="">
            <p>三等奖：迷你按摩器<span class="number">6名</span></p>
            <img src="http://static.hehenian.com/m/img/mather_day/j3.png" height="50" alt="">
            <p>阳光奖：合和年杯子<span class="number">10名</span></p>
            <img src="http://static.hehenian.com/m/img/mather_day/j4.png" height="50" alt="">
        </div>
    </article>
    <div class="tips">本活动最终解释权归合和年在线所有</div>
    <div class="footer">
    	<div class="fixed-nav">
    		<a href="/webapp/webapp-login.do"><span>立即登录</span>  开启财富之门</a><i></i>
    	</div>
    </div>
</body>

</html>
