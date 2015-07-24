<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>花样年彩生活</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">
    <meta content="yes" name="apple-mobile-web-app-capable">
    <meta content="yes" name="apple-touch-fullscreen">
    <link type="text/css" rel="stylesheet" href="http://static.hehenian.com/m/css/main.min.css">
    <script src="http://static.hehenian.com/m/common/zepto.min.js"></script>
    <script src="http://static.hehenian.com/m/js/main.min.js"></script>
    <style>
		.qr-code{text-align: center;padding-top: 0.63rem;background: #fff;}
		.qr-code img{width: 7.05rem;}
		.qr-code p{color: #677280;font-size: 0.8rem;padding: .4rem 0 .83rem;}
    </style>
</head>
<body class="bg-2">
    <header class="top-bar">
        <a href="javascript:history.go(-1);">
            <span class="icon-back"></span>
        </a>
        <p>第三方合作 — 彩生活</p>
    </header>

	<div class="qr-code">
		<img src="http://static.hehenian.com/m/img/qecode1.png" alt="">
		<p>扫描下载彩生活</p>
	</div>

    <nav class="nav-tip" style="border-bottom: none;">
        <p>彩生活温馨提示：您若在彩生活投资了E理财、彩富人生，请到彩生活官方渠道查看相关记录及收益。</p>
    </nav>
    <%@ include file="../common/tail.jsp"%>
    <script>
    $(function() {
        sbh();
    })
    </script>
</body>

</html>