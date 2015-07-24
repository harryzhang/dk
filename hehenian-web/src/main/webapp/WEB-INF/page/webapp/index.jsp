<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<%
DateFormat dateFormat=new SimpleDateFormat("yyyyMMdd");
Date date1=dateFormat.parse("20140901");
Date date2=dateFormat.parse("20140905");
Date date3=dateFormat.parse(dateFormat.format(new Date()));
if(!(date3.before(date1)||date3.after(date2))){
	response.sendRedirect("huodong.do?activityId=1");
}

%>
<jsp:include page="/include/wap-head.jsp"></jsp:include>
</head>
<body>
<div id="preloader">
  <div id="status">
    <p class="center-text">正在加载...</p>
  </div>
</div>
<!--顶部底部浮动层-->
<jsp:include page="/include/wap-bottom.jsp"></jsp:include>

<!-- main -->
  <div class="container">
    <div class="slider-controls" data-snap-ignore="true">
        <div><a href="/webapp/huodong.do"><img src="/images/song30app.jpg" class="responsive-image" alt="img"> </a> </div>
        <div><a href="#"><img src="/images/llsjapp.jpg" class="responsive-image" alt="img"> </a> </div>
     <div><a href="/webapp/webapp-money.do"><img src="/images/zdtbb-webapp.jpg" class="responsive-image" alt="img"> </a> </div>
      
      <div> <a href="/webapp/qa.do"><img src="/wap/images/general-nature/3.jpg" class="responsive-image" alt="img"></a></div>
 
    </div>
    <%--  <div style=" padding:10px 10px 0px 10px; color:#F00"><a href="#">100元奖励在活动结束后5个工作日内发放</a></div>--%>

  </div>

<div id="main-wrap">
<div id="container"  style=" padding-left:0px; padding-right:0px;" >
<div class="container no-bottom"  style="padding: 0px 10px;">
  <div class="section-title">
    <div class="facile-menu"  style=" background-color:#50c2e9;border-top:#6fd0f0 1px solid;border-bottom:#32aadf 1px solid; color:#FFF">e理财收益对比</div>
    <div  style=" margin:0px auto; width:100%;">
      <div class="sydb" style=" right:0px; margin:0px auto;top:8px;">
        <ul style="  margin-left:0px;margin-top:10px;">
          <li style=" color:#999">█&nbsp;&nbsp;银行定期3%</li>
          <li style=" color:#007711">█&nbsp;&nbsp;银行理财4-5%</li>
          <li style=" color:#fec105">█&nbsp;&nbsp;宝宝军团4-6%</li>
          <li style=" color:#F60">█&nbsp;&nbsp;彩生活e理财8-10%</li>
        </ul>
      </div>
      <script src="../common/Chart.js"></script>
      <canvas id="canvas" width="120" height="120"></canvas>
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
    <div class="facile-menu"  style=" background-color:#50c2e9;border-top:#6fd0f0 1px solid;border-bottom:#32aadf 1px solid; color:#FFF">e理财便捷通道</div>
    <div>
      <div class="facile">
        <div style=" border-right:1px dashed #d9d8d6; border-bottom:1px dashed #d9d8d6">
          <div class="bjan" style=" background:#e6507e"  ><a href="webapp-money.do"><img src="../../../images/appcomm/icon-1.png" width="100" height="100"  alt=""/></a></div>
          <div style=" margin-bottom:10px;">可用金额：<s:property default="0" value="#request.automaticBidMap.usableSum"/></div>
        </div>
      </div>
      <div class="facile">
        <div style=" border-bottom:1px dashed #d9d8d6">
          <div class="bjan" style=" background:#eb4b3f"  ><a href="webapp-recharge.do"><img src="../../../images/appcomm/icon-2.png" width="100" height="100"  alt=""/></a></div>
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
          <div class="bjan" style=" background:#24cdb8" ><a href="webapp-cash.do"><img src="../../../images/appcomm/icon-4.png" width="100" height="100"  alt=""/></a></div>
          <div style=" margin-bottom:10px;">提现</div>
        </div>
      </div>
    </div>
    <div class="facile-menu"  style=" background-color:#50c2e9;border-top:#6fd0f0 1px solid;border-bottom:#32aadf 1px solid; color:#FFF; margin-top:15px;">e理财介绍</div>
    <div style=" padding:10px;">彩生活e理财是彩生活集团联合花样年集团共同推出的，目的为广大业主提供优质理财项目，旨在打造社区最专业的金融服务的一个平台。业主通过彩生活e理财进行投资，尊享10%的年化收益，平台不收取投资人任何费用；投资过程透明，投资信息公开，e理财采用汇付天下托管平台,资金流向全程可控；如果债务人发生违约，花样年集团承诺100%回购债权，投资零风险。</div>
     <div class="facile-menu"  style=" background-color:#50c2e9;border-top:#6fd0f0 1px solid;border-bottom:#32aadf 1px solid; color:#FFF; margin-top:15px;">平台原理 </div>
    <div style=" padding:10px;">e理财网络借贷平台原理就是有资金并且有理财投资想法的个人及中小企业，通过中介机构的审核和服务，使用信用贷款的方式将资金贷给其他有借款需求的人。<br>
 
 专业的金融服务网站（P2P网站）充当中介机构，负责对借款方的经济效益、经营管理水平、发展前景等情况进行详细的考察并进行信用评级，并收取手续费收入；中介机构通过引入第三方支付机构管理资金的托管、结算以保证各方的资金安全；中介机构通过引入借款回购机构以将投资人的风险降到最低；中介机构通过网络为借贷双方提供公开、透明、安全、高效的交易场所，降低借贷过程的交易费用。</div>
  <div class="facile-menu"  style=" background-color:#50c2e9;border-top:#6fd0f0 1px solid;border-bottom:#32aadf 1px solid; color:#FFF; margin-top:15px;">法律政策 </div>
    <div style=" padding:10px;">会员在e理财合法投资理财产品的收益受法律保护<br>
 

e理财设立专门的法律合规部门，法律合规部门从服务合规性、政策走向等多方面提供法律意见，保障融资方式、平台和项目的合法性。 <br>

• 一切理财产品的设计最终都需要经过法律合规部门的审核，保证理财产品的合法性；<br> 
• 一切合同和规章制度都由法律合规部门和专业的金融机构一同起草，投资人在平台上的所有操作都合法。<br>
 

 

关于e理财提供居间撮合服务的合法性：<br>


明确合法 根据《合同法》第23章关于"居间合同"的规定，特别是第424条规定的"居间合同是居间人向委托人报告订立合同的机会或者提供订立合同的媒介服务，委托人支付报酬的合同"，e理财为民间借贷提供撮合借贷双方形成借贷关系的居间服务有着明确的法律基础。 

 

关于电子合同的合法性及可执行性：

现行法律明确确定了合同效力 根据《电子签名法》的规定，民事活动中的合同或者其他文件、单证等文书，当事人可以约定使用电子签名、数据电文，不能因为合同采用电子签名、数据电文就否定其法律效力。同时，《电子签名法》中还规定，可靠的电子签名与手写签名或者盖章具有同等的法律效力。明确肯定了符合条件的电子签名与手写签名或盖章具有同等的效力。 关于投资人在e理财获得的出借理财收益的合法性:为合法收益,受到法律保护 根据最高人民法院《关于人民法院审理借贷案件的若干意见》第6条："民间借贷的利率可以适当高于银行的利率，各地人民法院可以根据本地区的实际情况具体掌握，但最高不得超过银行同类贷款利率的四倍，（包含利率本款）。超出此限度的，超出部分的利息不予保护。"当期人民银行一年期基准贷款利率为6.00%，则当期基准贷款利率的四倍为24.00%，e理财的出借理财收益低于24.00%，为合法利息收益，受到法律保护。
</div>
 
  </div>
</div>
</div>
</div>
<script>
     $.get("/updateUserUsableSum.do?_="+new Date().getTime());
</script>
</body>
</html>
