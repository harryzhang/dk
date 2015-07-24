<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="/include/taglib.jsp"%>
<html>
<head>
<title>KPI完成情况</title>
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
	    window.location.href=encodeURI(encodeURI("exportKpi.do"));
      });
    
    $("#btSearch").click(function() {
		var applyTime = $("#applyTime").val();
		var endTime = $("#endTime").val();
		var data1 = Date.parse(applyTime.replace(/-/g, "/"));
		var data2 = Date.parse(endTime.replace(/-/g, "/"));
		var datadiff = data2 - data1;
		var time = 31 * 24 * 60 * 60 * 1000;
		if (applyTime.length > 0 && endTime.length > 0) {
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
				 <!-- 
				 <input id="excel" type="button" onclick="checkSend()"
									value="导出Excel" name="excel" />
									 --> 
					<div>
						<table id="help" style="color: #333333; width: 100%" cellspacing="1"
								cellpadding="3" bgcolor="#dee7ef" border="0">
								<tbody>
									<tr class=gvHeader>
										<th style="width: 5%;" scope="col" rowspan="2">KPI指标</th>
										<th scope="col" colspan="2">本周(日均)</th>
										<th scope="col" colspan="2">上周(日均)</th>
										<th scope="col" colspan="2">上月同周</th>
										<th scope="col" colspan="2">本月</th>
									</tr>
									<tr class=gvHeader>
										<th style="width: 5%;">KPI指标</th>
										<th scope="col">实际</th>
										<th scope="col">实际</th>
										<th scope="col">周增长率</th>
										<th scope="col">实际</th>
										<th scope="col">上月同比</th>
										<th scope="col" >本周为止</th>
										<th scope="col">时间进度</th>
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
														${map.name}
													</td>
													<td>${map.kpiVal}</td>
													<td >
														${map.weeksData} 
													</td>
													<td>
													${map.lastWeeksData} 
													</td>
													<td><fmt:formatNumber value="${map.growthRate}" pattern="##.##" minFractionDigits="2" />%</td>
													<td>
														${map.lastMonthData} 
													</td>
													<td>
														<fmt:formatNumber value="${map.lastTb }" pattern="##.##" minFractionDigits="2" />%
													</td>
													<td>
														${map.monthData}
													</td>
													<td>
														${map.timeP }
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
</html>
