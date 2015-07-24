<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML>
<html>
<head>
<jsp:include page="/include/mobile/head.jsp"></jsp:include>
<style type="text/css">
.items1 { width:100%; }
.items1 li { height:60px; border-bottom:#d1d1d1 solid 1px; width:100% }
.items1 li strong,.items li em { display:block; width:60%; float:left; height:60px; line-height:60px; font-weight:normal; }
.items1 li strong { width:40%; }
#remainTimeOne{width:170px}
.items1 li strong i { padding-left:20px;}
</style>
</head>
<body class="index">
<jsp:include page="/include/mobile/loading.jsp"></jsp:include>
<div class="wrap animate-waves" id="wrap" style="display:none;">
<span id="remainSeconds" style="display: none;">${times}</span>
  <ul class="items1 user">
    <li><strong><i>剩余本金</i></strong><strong style="font-size:14px; color:#e94718; font-weight:normal;">￥${paramMap.balance }</strong></li>
    <li><strong><i>应收利息</i></strong><strong style="font-size:14px; color:#e94718; font-weight:normal;">￥${paramMap.interest }</strong></li>
    <li><strong><i>年化收益率</i></strong><strong style="font-size:14px; color:#e94718; font-weight:normal;">${borrowMap.annualRate }%</strong></li>
    <li><strong><i>剩余期限</i></strong><strong id="days"style="font-size:12px; color:#e94718; font-weight:normal;">${paramMap.dayss } 天</strong></li>
    <li><strong><i>下一还款日</i></strong><strong style="font-size:14px; color:#e94718; font-weight:normal;">${request.nextDay }</strong></li>
    <li><strong><i>剩余交易时间</i></strong><strong id="remainTimeOne" style="font-size:12px; color:#e94718; font-weight:normal;">&nbsp;</strong>
    </li>
   	<div style="margin:0px auto 0px auto;" align="center">
		<br/><strong style=" color: #E94718;font-size: 14px;font-weight: normal;">转让价格： ￥${paramMap.auctionBasePrice}</strong><br/><br/>
		<s:if test="#request.paramMap.debtStatus==1">
			<input type="button" value="申请中" class="jbcr_a" style="cursor: default;background: #ccc;" disabled="disabled" />
		</s:if>
		<s:elseif test="#request.paramMap.debtStatus==3">
			<input type="button" value="已转让" class="jbcr_a" style="cursor: default;background: #ccc;" disabled="disabled" />
		</s:elseif>
		<s:elseif test="#request.times<=0">
			<input type="button" value="已过期" class="jbcr_a" style="cursor: default;background: #ccc;" disabled="disabled" />
		</s:elseif>
		<s:elseif test="#request.paramMap.debtStatus==2">
			<input type="button" id="buyDebt" onclick="buys()" value="立即购买" class="jbcr_a" />
		</s:elseif>
	</div>
  </ul>
</div>
<div id="dataForm" style="display: none;"></div>
<jsp:include page="/include/mobile/bottom.jsp"></jsp:include>
<jsp:include page="/include/mobile/common-js.jsp"></jsp:include>
<script>
var SysSecond;
var InterValObj;
$(function() {
	SysSecond = Number($("#remainSeconds").html()); //这里获取倒计时的起始时间 
	InterValObj = window.setInterval(SetRemainTime, 1000); //间隔函数，1秒执行 

	
});

//将时间减去1秒，计算天、时、分、秒 
function SetRemainTime() {
	if (SysSecond > 0) {
		SysSecond = SysSecond - 1;
		var second = Math.floor(SysSecond % 60); // 计算秒     
		var minite = Math.floor((SysSecond / 60) % 60); //计算分 
		var hour = Math.floor((SysSecond / 3600) % 24); //计算小时 
		var day = Math.floor((SysSecond / 3600) / 24); //计算天 
		var times = day + "天" + hour + "小时" + minite + "分" + second + "秒";
		$("#remainTimeOne").html(times);
	} else {
		window.clearInterval(InterValObj);
		$("#remainTimeOne").html("过期");
	}
}

function buys(){
	var param = {};
	param["paramMap.id"] = '${paramMap.debtId}'
	param["paramMap.auctionPrice"] = '${paramMap.auctionBasePrice}'
	param["paramMap.dealpass"] = $("#dealpass").val();
	param["paramMap.usrCustId"] = $("#usrCustId").val();
	param["paramMap.debtUsrCustId"] = $("#debtUsrCustId").val();
	if (confirm("是否确认购买？")) {
		$("#buyDebt").attr("disabled", true);
		$.post("/../addAuctingDebt.do", param, function(data) {
			if (data.length < 30) {
				HHN.popup({type:"alert",content:data,element:$("#buyDebt")});
			} else {
				$("#dataForm").html(data);
			}
		});
	}
}
</script>
</body>
</html>