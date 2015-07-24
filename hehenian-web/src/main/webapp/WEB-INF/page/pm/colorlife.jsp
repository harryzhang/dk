<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>彩生活投资理财统计</title>
</head>
<body>


<link rel='stylesheet prefetch' href='/newcss/colourlife/font-awesome.min.css'>

<link rel="stylesheet" href="/newcss/colourlife/style.css" media="screen" type="text/css" />

</head>

<body>



<div class="wrapper">
  <div style="font-family:Microsoft YaHei; font-size:26px; padding:20px 0px; font-weight:bold">彩生活投资理财统计</div>
    <div class="counter col_fourth">
      <h2 class="timer count-title" id="count-number" data-to="${zc}" data-speed="1500"></h2>
       <p class="count-text ">总注册数</p>
    </div>

    <div class="counter col_fourth">
      <h2 class="timer count-title" id="count-number" data-to="${tz}" data-speed="1500"></h2>
      <p class="count-text ">总投资额</p>
    </div>

    <div class="counter col_fourth">
      <h2 class="timer count-title" id="count-number" data-to="${fk}" data-speed="1500"></h2>
      <p class="count-text ">总放款额</p>
    </div>


</div>
<div class="wrapper">
<div style=" font-family:Microsoft YaHei; font-size:26px; padding:20px 0px; font-weight:bold">昨天</div>
    <div class="counter col_fourth">
      <h2 class="timer count-title" id="count-number" data-to="${resultMap.yestzc==null?0:resultMap.yestzc}" data-speed="1500"></h2>
       <p class="count-text ">注册数</p>
    </div>

    <div class="counter col_fourth">
      <h2 class="timer count-title" id="count-number" data-to="${resultMap.yesttz==null?0:resultMap.yesttz}" data-speed="1500"></h2>
      <p class="count-text ">投资额</p>
    </div>

    <div class="counter col_fourth">
      <h2 class="timer count-title" id="count-number" data-to="${resultMap.yestfk==null?0:resultMap.yestfk}" data-speed="1500"></h2>
      <p class="count-text ">放款额</p>
    </div>


</div>


<div class="wrapper">
<div style=" font-family:Microsoft YaHei; font-size:26px; padding:20px 0px; font-weight:bold">今天</div>
    <div class="counter col_fourth">
      <h2 class="timer count-title" id="count-number" data-to="${resultMap.nowzc==null?0:resultMap.nowzc}" data-speed="1500"></h2>
       <p class="count-text ">注册数</p>
    </div>

    <div class="counter col_fourth">
      <h2 class="timer count-title" id="count-number" data-to="${resultMap.nowtz==null?0:resultMap.nowtz}" data-speed="1500"></h2>
      <p class="count-text ">投资额</p>
    </div>

    <div class="counter col_fourth">
      <h2 class="timer count-title" id="count-number" data-to="${resultMap.nowfk==null?0:resultMap.nowfk}" data-speed="1500"></h2>
      <p class="count-text ">放款额</p>
    </div>


</div>

<script src='/newcss/colourlife/jquery.js'></script>

<script src="/newcss/colourlife/index.js"></script>
</body>
</html>