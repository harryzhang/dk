<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>情人节的约定_中国首家社区互联网金融服务平台</title>
<jsp:include page="/include/head.jsp"></jsp:include>
<style>
	em{
		font-style: normal;
	}
	.q1{
		width:100%;
		background:#d0171c;
	}
	.q1 img{
		display:block;
		margin: auto;
	}
	.qq{
		width:100%;
		background:#ffdfe0;
	}
	.q2{
		width:1170px;
		height:900px;
		margin: auto;
		background:url(/img/q2.png) no-repeat;
	}
	.q3{
		width:1170px;
		height:900px;
		margin: -2px auto 0; 
		background:url(/img/q3.png) no-repeat;
	}
	h3{
		font-size: 35px;
		font-family: "微软雅黑";
	}
	.heart{
		position: absolute;
		margin: 460px 0 0 260px;
	}
	.heart label{
		font-size: 47px;
		font-weight: 900;
	}
	.heart label.green{
		color: #009c4d;
		font-size: 52px;
	}
	.heart em{
		font-size: 26px;
	}
	.heart2{
		margin: 746px 0 0 545px;
	}
	.heart3{
		margin: 150px 0 0 160px;
	}
	.heart4{
		margin: 480px 0 0 550px;
	}
	.btn{
width: 120px;
height: 30px;
line-height: 30px;
color: #fff;
font-size: 23px;
padding: 5px 5px;
position: absolute;
text-align: center;
	}
	.btn1{
		background: #f3b174;
		margin: 40px 0 0 60px;
	}
	.btn1:hover,.btn1:active{
		background: #ee8840;
	}
	.btn2{
		background: #e693ad;
		margin: 49px 0 0 160px;
	}
	.btn2:hover,.btn2:active{
		background: #ac5f73;
	}
	.btn3{
		background: #9fbe6e;
		margin: 40px 0 0 100px;
	}
	.btn3:hover,.btn3:active{
		background: #8dae57;
	}
	.btn4{
		background: #61c3d0;
		margin: 40px 0 0 100px;
	}
	.btn4:hover,.btn4:active{
		background: #2ab5d2;
	}
	.cun{padding-left: 100px;}
	.tip{
		display: block;
		text-align: right;
	}
</style>
</head>
<body>
<jsp:include page="/include/top.jsp"></jsp:include>
<div class="cle"></div>
<div class="q1">
	<img src="/img/q1.png" />
</div>
<div class="qq">
	<div class="q2">
		<div class="heart">
			<h3 class="cun">存入<label>8563</label><em>元</em></h3>
			<h3>明年收获<label class="green">9420</label><em>元</em><em>(就是爱你)</em></h3>
			<a class="btn btn1" href="/hhn_web/termFinance.do">立即存单</a>
		</div>
		<div class="heart heart2">
			<h3 style="padding-left: 80px;">存入<label>11951</label><em>元</em></h3>
			<h3>明年收获<label class="green">13145.20</label><em>元</em><br/></h3>
			<h3 class="tip"><em>(一生一世我爱你)</em></h3>
			<a class="btn btn2" href="/hhn_web/termFinance.do">立即存单</a>
		</div>
	</div>
	<div class="q3">
		<div class="heart heart3">
			<h3 class="cun">存入<label>47285</label><em>元</em></h3>
			<h3>明年收获<label class="green">52013.14</label><em>元</em></h3>
			<h3 class="tip"><em>(我爱你一生一世)</em></h3>
			<a class="btn btn3" href="/hhn_web/termFinance.do">立即存单</a>
		</div>
		<div class="heart heart4">
			<h3 style="padding-left: 40px;">存入<label>473636</label><em>元</em></h3>
			<h3>明年收获<label class="green">520999</label><em>元</em></h3>
			<h3 class="tip"><em>(我爱你天长地久)</em></h3>
			<a class="btn btn4" href="/hhn_web/termFinance.do">立即存单</a>
		</div>
	</div>
</div>
<jsp:include page="/include/footer.jsp"></jsp:include>
</body>
</html>