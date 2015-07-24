<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="/include/taglib.jsp"%>
<html>
<head>
<title>投资明细表</title>
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
	    var product = $("#product").val();
	    var buniess = $("#buniess").val();
	    
	    if(product == '') {
	    	alert("请选择产品！");
	    }
	    if(startTime == '' || endTime == '') {
	    	alert("请录入起止时间！");
	    }
         window.location.href=encodeURI(encodeURI("exportInvestDetail.do?startTime=+"+startTime+"&endTime="+endTime+"&flag="+product+"&buniess="+buniess));		
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
					<form action="investDetail.do" method="post">
						<table style="margin-bottom: 8px;" cellspacing="0" cellpadding="0" width="100%" border="0" class="bigBoss">
						<tbody>
							<tr>
								<td class="f66"  height="36px">&nbsp;&nbsp; 
									产品： <select
										name="flag" id="product">
											<option value="">--请选择--</option>
											<option value="CFRS"
												<c:if test="${flag == 'CFRS'}">selected</c:if>>彩富人生</option>
											<option value="SB"
												<c:if test="${flag == 'SB'}">selected</c:if>>散标</option>
											<option value="DQLC"
												<c:if test="${flag == 'DQLC'}">selected</c:if>>爱定宝</option>
											<option value="HBLC"
												<c:if test="${flag == 'HBLC'}">selected</c:if>>红包理财</option>
									</select> 
								</td>
								<td class="f66"  height="36px">&nbsp;&nbsp; 
									事业部： <select
										name="buniess" id="buniess">
											<option value="">--请选择--</option>
											<c:forEach items="${syb}" var="map">
												<option value="${map.bname }"
												<c:if test="${buniess eq map.bname }">selected</c:if>>${map.bname }</option>
											</c:forEach>
									</select> 
								</td>
								<td class="f66"  height="36px">&nbsp;&nbsp; 
								起止日期 ： <input id="startTime" name="startTime" value="${startTime}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})" class="hhn"/>&nbsp;&nbsp;----&nbsp;&nbsp;
								<input id="endTime" name="endTime" value="${endTime}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})" class="hhn"/>&nbsp;&nbsp;
									<input id="btSearch" type="submit" value="查找" style="width: 60px;"/>
									&nbsp;&nbsp; <input id="excel" type="button" 
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
										<th scope="col">序号</th>
										<th scope="col">日期</th>
										<th scope="col">订单号/交易号</th>
										<th scope="col">用户名</th>
										<th scope="col">姓名</th>		
										<th scope="col">手机号码</th>
										<th scope="col">推荐人</th>
										<th scope="col">推荐手机</th>
										<th scope="col">小区</th>
										<th scope="col">小区信息/社区</th>
										<th scope="col">事业部</th>
										<th scope="col">期数</th>
										<th scope="col">投资金额</th>
									</tr>
									<c:choose>
										<c:when test="${empty pageDo}">
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
														${count }
													</td>
													<td>
														${map.investTime}
													</td>
													<td>
														${map.bh}
													</td>
													<td>
														${map.username}  
													</td>
													<td>
														${map.realName}   
													</td>
													<td >
														${map.mobilePhone} 
													</td>
													<td >
														${map.refferee} 
													</td>
													<td >
														${map.tMobilePhone } 
													</td>
													<td>
														${map.cName}  
													</td>
													<td>
														${map.caddress }
													</td>
													<td >
														${map.bname } 
													</td>
													<td>
														${map.rate }
													</td>
													<td >
														<fmt:formatNumber value="${map.investAmount }" pattern="##.##" minFractionDigits="2" /> 
													</td>				
											</tr>
										</c:forEach>
										<tr class="gvItem">
											<td>合计：</td>
											<td>总单数：</td>
											<td colspan="5">${orderDs }</td>
											<td>总金额：</td>
											<td colspan="5">${totalMoney}</td>
										</tr>
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
