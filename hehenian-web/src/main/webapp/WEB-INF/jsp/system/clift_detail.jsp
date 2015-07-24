<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="/include/taglib.jsp"%>
<html>
<head>
<title>E理财每日数据表</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
<meta http-equiv="description" content="This is my page" />
<%@ include file="/include/includeJs.jsp"%>
<script language="javascript" type="text/javascript" src="../My97DatePicker/WdatePicker.js"></script>
<script>
function checkAll(e){
	if(e.checked){
		$(".downloadId").attr("checked","checked");
	}else{
		$(".downloadId").removeAttr("checked");
	}
}
$(function(){
    $("#excel").click(function(){   
    	var startTime = $("#startTime").val();
	    var endTime = $("#endTime").val();
	    if(startTime == '' || endTime == '') {
	    	alert("请录入起止时间！");
	    }
	    window.location.href=encodeURI(encodeURI("exportEMsg.do?startTime=+"+startTime+"&endTime="+endTime));
      });
    
    $("#btSearch").click(function() {
		var startTime = $("#startTime").val();
		var endTime = $("#endTime").val();
		var data1 = Date.parse(startTime.replace(/-/g, "/"));
		var data2 = Date.parse(endTime.replace(/-/g, "/"));
		var datadiff = data2 - data1;
		var time = 31 * 24 * 60 * 60 * 1000;
		if (startTime.length > 0 && endTime.length > 0) {
			if (datadiff<0||datadiff>time) {
				alert("开始时间应小于结束时间并且间隔小于31天，请检查!");
				return false;
			}
		}
		$("#form1").submit();
	});
});
</script>
		<style type="text/css">
			.bigBoss  tbody td input{width: 140px;}
		</style> 
</head>
<body>
	<div id="right">
		<div style="padding: 15px 10px 0px 10px;">
			<div>
				<div
					style="padding-right: 10px; padding-left: 10px; padding-bottom: 10px; width: 95%; padding-top: 10px; background-color: #fff;">
					<form action="queryClift.do" method="post" name="form1">
						<table style="margin-bottom: 8px;" cellspacing="0" cellpadding="0" width="100%" border="0" class="bigBoss">
						<tbody>
							<tr>
								<td class="f66" align="left" width="80%" height="36px">&nbsp;&nbsp; 
								起止日期 ： <input id="startTime" name="startTime" value="${startTime}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly',maxDate:'%y-%M-%d'})" class="hhn"/>&nbsp;&nbsp;----&nbsp;&nbsp;
								<input id="endTime" name="endTime" value="${endTime}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly',maxDate:'%y-%M-%d'})" class="hhn"/>&nbsp;&nbsp;
									<input id="btSearch" type="submit" value="查找" style="width: 60px;"/>
									&nbsp;&nbsp; <input id="excel" type="button" onclick="checkSend()"
									value="导出Excel" name="excel" />
								</td>
							</tr>
						</tbody>
					</table>
					</form>
					<div>
						<table id="help" style="color: #333333; width: 100%" cellspacing="1"
								cellpadding="3" bgcolor="#dee7ef" border="0">
								<tbody>
									<tr class=gvHeader>
										<th style="width: 5%;" scope="col" rowspan="2">日期</th>
										<th scope="col" colspan="4">用户</th>
										<th scope="col" colspan="4">散标</th>
										<th scope="col" colspan="4">彩富人生</th>
										<th scope="col" colspan="4">爱定宝</th>
										<th scope="col" colspan="4">红包理财</th>
										<th scope="col" rowspan="2">今日进账总额</th>
									</tr>
									<tr class=gvHeader>
										<th scope="col">今日注册人数</th>
										<th scope="col">累计注册人数</th>
										<th scope="col">今日登陆人数</th>
										<th scope="col">今日投资人数</th>		
										<th scope="col">今日投资金额<br/>(不含彩富人生)</th>
										<th scope="col">今日交易笔数</th>
										<th scope="col">累计交易笔数</th>
										<th scope="col">总投资金额<br/>(不含彩富人生)</th>
										<th scope="col">今日完成订单数</th>
										<th scope="col">累计订单完成数</th>
										<th scope="col">今日完成订单金额</th>
										<th scope="col">累计完成订单金额</th>
										<th scope="col">今日购买金额</th>
										<th scope="col">今日交易笔数</th>
										<th scope="col">累计交易笔数</th>
										<th scope="col">累计完成订单金额</th>
										<th scope="col">今日交易笔数</th>
										<th scope="col">累计交易笔数</th>
										<th scope="col">今日购买金额</th>
										<th scope="col">累计购买金额</th>
									</tr>
									<c:choose>
										<c:when test="${pageDo == null}">
											<tr align="center" class="gvItem">
												<td colspan="15">暂无数据</td>
											</tr>
										</c:when>
										<c:otherwise>
											<c:set var="count" value="0" />
											<c:forEach items="${pageDo}" var="map">
												<tr class="gvItem">
													<c:set var="count" value="${count+1}" />
													<td>
														${map.createTime}
													</td>
													<td>
														${map.registerNumber}  
													</td>
													<td>
														${map.totalRegisterNumber}   
													</td>
													<td >
														${map.loginNumber} 
													</td>
													<td >
														${map.investor} 
													</td>
													<td >
														<fmt:formatNumber value="${map.investAmount}" pattern="##.##" minFractionDigits="2" /> 
													</td>
													<td>
														${map.sbInvestNumber}  
													</td>
													<td >
														${map.totalSbInvestNumber } 
													</td>
													<td>
														<fmt:formatNumber value="${map.totalInvestAmount }" pattern="##.##" minFractionDigits="2" />
													</td>
													<td >
														${map.ordNumber } 
													</td>
													<td >
														${map.tatolOrdNumber } 
													</td>
													<td >
														<fmt:formatNumber value="${map.rechargeMoney}" pattern="##.##" minFractionDigits="2" /> 
													</td>
													<td >
														<fmt:formatNumber value="${map.totalRechargeMoney}" pattern="##.##" minFractionDigits="2" /> 
													</td>
													<td >
														<fmt:formatNumber value="${map.tradeAmount}" pattern="##.##" minFractionDigits="2" /> 
													</td>
													<td >
														${map.tradeNumber } 
													</td>
													<td >
														${map.totalTradeNumber } 
													</td>
													<td >
														<fmt:formatNumber value="${map.totalTradeAmount}" pattern="##.##" minFractionDigits="2" /> 
													</td>
													<td >
														${map.hbCount } 
													</td>
													<td >
														${map.totalHbCount } 
													</td>
													<td >
														<fmt:formatNumber value="${map.hbBuyMoney}" pattern="##.##" minFractionDigits="2" /> 
													</td>
													<td >
														<fmt:formatNumber value="${map.totalHbBuyMoney}" pattern="##.##" minFractionDigits="2" /> 
													</td>
													<td>
														<fmt:formatNumber value="${map.sumAmount }" pattern="##.##" minFractionDigits="2" />
													</td>
											</tr>
										</c:forEach>
									</c:otherwise>
									</c:choose>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
	function toPage(pageNo) {
		window.location.href = "queryExample.do?selectMethodId=query.getTotalCount&countMethod=query.getMap&realName=${param.realName}&mobile=${param.mobile}&curPage="
				+ pageNo + "&pageSize=10";
	}
</script>
</html>
