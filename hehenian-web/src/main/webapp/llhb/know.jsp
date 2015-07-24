<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta charset="utf-8" />
	<title>测试</title>
	<meta name="viewport" content="width=device-width initial-scale=1.0 maximum-scale=1.0 user-scalable=0" />
	<style>
		*{
			margin: 0;
			padding: 0;
			list-style: none;
			-webkit-box-sizing: border-box;
			-ms-box-sizing: border-box;
			-moz-box-sizing: border-box;
			box-sizing: border-box;
			font-style: normal;
		}
		body{
			font-family:Tahoma,Arial,Roboto,"Droid Sans","Helvetica Neue","Droid Sans Fallback","Heiti SC","Hiragino Sans GB",Simsun,sans-self;
			font-size: 16px;
			color: #494949;
		}
		img{
			width: 100%;
		}
		section{
			padding: 0 10px;
		}
		article{
			border-bottom: 1px dotted #ccc;
			padding-bottom: 20px;
		}
		article:last-child{
			border-bottom-width: 0;
		}
		.num{
			width: 60px;
			height:60px;
			-webkit-border-radius: 30px;
			-ms-border-radius: 30px;
			-moz-border-radius: 30px;
			border-radius: 30px;
			border:2px solid rgba(204,142,21,1);
			background: #fff;
			background: rgba(204,142,21,1);
			margin: 30px auto 20px;
		}
		.num span{
			width: 56px;
			height:56px;
			display: block;
			-webkit-border-radius: 30px;
			-ms-border-radius: 30px;
			-moz-border-radius: 30px;
			border-radius: 30px;
			border:5px solid #fff;
			line-height: 46px;
			text-align: center;
			font-size: 38px;
			color: #fff;
		}
		.title{
			text-align: center;
			height: 50px;
			line-height: 40px;
		}
		.disc{
			line-height: 22px;
			text-indent: 2em;
			padding-bottom: 20px;
		}
		.you,.cheng{
			width: 100%;
			display: table;
		}
		.you .item{
			width: 33.33%;
			display: table-cell;
			text-align: center;
		}
		.you img{
			width: 90px;
		}
		.you .title{
			height: 40px;
			font-size: 20px;
		}
		.cheng .item{
			width: 50%;
			line-height: 22px;
			display: table-cell;
			text-align: center;
		}
		.cheng img{
			width: 120px;
		}
		.cheng em{
			display: block;
			height: 30px;
			line-height: 34px;
		}
		.cheng h4{
			font-size: 22px;
		}
		.shi{
			font-size: 14px;
		}
		@media screen and (max-width: 320px) {
			body{
				font-size: 13px;
			}
			.num{
				width: 40px;
				height:40px;
				border:2px solid rgba(204,142,21,1);
				margin: 20px auto 10px;
			}
			.num span{
				width: 36px;
				height:36px;
				border:3px solid #fff;
				line-height: 30px;
				font-size: 24px;
			}
			.you img {
				width: 60px;
			}
		}
	</style>
</head>
<body>
	<header>
		<img src="${application.basePath}/llhb/image/pt1.png" alt="">
	</header>
	<section>
		<article>
			<div class="num">
				<span>1</span>
			</div>
			<h2 class="title">合和年在线是什么</h2>
			<p class="disc">合和年在线是花样年集团倾力打造的互联网金融P2P信贷平台，致力于为各位投资人提供安全、便捷、有担保、高收益的互联网理财服务。您可将富余的资金，通过我们平台出借给信用良好的借款人，并获得利息回报。</p>
			<img src="${application.basePath}/llhb/image/pt2.png" alt="">
		</article>
		<article>
			<div class="num">
				<span>2</span>
			</div>
			<h2 class="title">我们的优势</h2>
			<div class="you">
				<div class="item">
					<img src="${application.basePath}/llhb/image/y1.png" alt="">
					<h2 class="title">安全</h2>
					<p>100%本息保障<br/>多重审核<br/>IPC信贷技术</p>
				</div>
				<div class="item">
					<img src="${application.basePath}/llhb/image/y2.png" alt="">
					<h2 class="title">高收益</h2>
					<p>30倍银行存款利息<br/>0手续费</p>
				</div>
				<div class="item">
					<img src="${application.basePath}/llhb/image/y3.png" alt="">
					<h2 class="title">门槛低</h2>
					<p>1元起投</p>
				</div>
			</div>
		</article>
		<article>
			<div class="num">
				<span>3</span>
			</div>
			<h2 class="title"><br><br><br><br></h2>
			<p class="disc">爱定宝，年化收益最高14%，远超过银行定存利率。您可以选择爱定宝·3-M、爱定宝·6-M、爱定宝·12-M理财产品，100%本息保障，1元起投，到期返还本金收益。</p>
			<span class="shi">投资示例：</span>
			<img src="${application.basePath}/llhb/image/pt3.png" alt="">
		</article>
		<article>
			<div class="num">
				<span>4</span>
			</div>
			<h2 class="title">成就</h2>
			<p style="text-align: center; height:24px;">截止2015年3月</p>
			<div class="cheng">
				<div class="item">
					<img src="${application.basePath}/llhb/image/pt4.png" alt="">
					<em>总投资额为</em>
					<h4>19,600万</h4>
				</div>
				<div class="item">
					<img src="${application.basePath}/llhb/image/pt5.png" alt="">
					<em>总用户注册量为</em>
					<h4>200万</h4>
				</div>
			</div>
		</article>
	</section>
</body>
</html>
