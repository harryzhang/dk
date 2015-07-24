<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<jsp:include page="/include/app-jfq-head.jsp"></jsp:include>
</head>
<body>
<div id="preloader">
  <div id="status">
    <p class="center-text">正在加载...</p>
  </div>
</div>

<!--顶部底部浮动层-->
<jsp:include page="/include/app-comm.jsp"></jsp:include>

<!-- main -->
<div class="open">
  <ul>
    <li style=" text-align:right; font-size:40px; line-height:40px; float:right;">8%-12%</li>
    <li style=" font-size:18px; ">合和年在线</li>
    <li style=" float:left;">年化收益可达</li>
  </ul>
</div>
<div id="main-wrap">
<div id="container"  style=" padding-left:0px; padding-right:0px;" >
<div class="container no-bottom"  style="padding: 0px 10px;">
  <div class="section-title">
    <div class="facile-menu">合和年在线收益对比</div>
    <div  style=" margin:0px auto; width:300px;">
      <div class="sydb">
        <ul>
          <li style=" color:#999">█&nbsp;&nbsp;银行定期3%</li>
          <li style=" color:#007711">█&nbsp;&nbsp;银行理财4-5%</li>
          <li style=" color:#fec105">█&nbsp;&nbsp;宝宝军团4-6%</li>
          <li style=" color:#F60">█&nbsp;&nbsp;合和年在线8-12%</li>
        </ul>
      </div>
      <script src="../common/Chart.js"></script>
      <canvas id="canvas" width="300" height="300"></canvas>
      <script>
		var doughnutData = [
				{
					value: 30,
					color:"#d2d2d2"
				},
				{
					value : 65,
					color : "#fec105"
				},
				{
					value : 120,
					color : "#F60"
				},
				{
					value : 50,
					color : "#007711"
				},
			];
	var myDoughnut = new Chart(document.getElementById("canvas").getContext("2d")).Doughnut(doughnutData);
	</script> 
    </div>
    
    <!--      <div class="open">
      	<ul> 
        	<li style=" text-align:right; font-size:40px; line-height:40px; float:right;">8%-12%</li>
        	<li style=" font-size:18px; ">e理财</li>
            <li style=" float:left;">年化收益率</li>
        </ul>
    </div>-->
    <div class="facile-menu">合和年在线便捷通道</div>
    <div>
      <div class="facile">
        <div style=" border-right:1px dashed #d9d8d6; border-bottom:1px dashed #d9d8d6">
          <div class="bjan" style=" background:#e6507e"  ><a href="money.do"><img src="../../../images/appcomm/icon-1.png" width="100" height="100"  alt=""/></a></div>
          <div style=" margin-bottom:10px;">您的可用金额：<s:property default="0" value="#request.automaticBidMap.usableSum"/></div>
        </div>
      </div>
      <div class="facile">
        <div style=" border-bottom:1px dashed #d9d8d6">
          <div class="bjan" style=" background:#eb4b3f"  ><a href="recharge.do"><img src="../../../images/appcomm/icon-2.png" width="100" height="100"  alt=""/></a></div>
          <div style=" margin-bottom:10px;">充值</div>
        </div>
      </div>
      <div class="facile">
        <div style=" border-right:1px dashed #d9d8d6">
          <div class="bjan" style=" background:#f6722a" ><a href="automaticbid.do"><img src="../../../images/appcomm/icon-3.png" width="100" height="100"  alt=""/></a></div>
          <div style=" margin-bottom:10px;">自动投标状态：<font color="#66CC00">${automaticBidMap.bidStatus ==2?'开':'<font color="#FF0000">关</font>' }</font><!--<font color="#FF0000">关</font>--></div>
        </div>
      </div>
      <div class="facile">
        <div>
          <div class="bjan" style=" background:#24cdb8" ><a href="cash.do"><img src="../../../images/appcomm/icon-4.png" width="100" height="100"  alt=""/></a></div>
          <div style=" margin-bottom:10px;">提现</div>
        </div>
      </div>
    </div>
    <div class="facile-menu">合和年在线介绍</div>
    <div style=" padding:10px;">合和年在线是花样年集团推出的，目的为广大业主提供优质理财项目，旨在打造社区最专业的金融服务的一个平台。用户通过合和年在线进行投资，尊享10%的年化收益，平台不收取投资人任何费用；投资过程透明，投资信息公开，合和年在线采用汇付天下托管平台,资金流向全程可控；如果债务人发生违约，花样年集团承诺100%回购债权，投资零风险。</div>
  </div>
</div>
</div>
</div>
</body>
</html>
