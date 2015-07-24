<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="/include/taglib.jsp"%>
<html>
<head>
<title>各渠道进出账资金汇总表</title>
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
  			//获取id集合
 			var stIdArray = [];
			$("input[name='cb_ids']:checked").each(function(){
				stIdArray.push($(this).val());
			});
			
			if(stIdArray.length == 0){
				alert("请选择需要导出的信息");
				return ;
			}
		
			var ids = stIdArray.join(",");  
                window.location.href=encodeURI(encodeURI("exportAccountMsg.do?selectMethodId=com.hehenian.biz.dal.system.ICommonQueryDao.getCapitalAccount&ids="+ids));
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
					<form action="queryExample.do" method="post">
						<input  name="selectMethodId" value="com.hehenian.biz.dal.system.ICommonQueryDao.getCapitalAccount" type="hidden"/>
						<input  name="countMethod" value="com.hehenian.biz.dal.system.ICommonQueryDao.getTotalCountAccount" type="hidden"/>
						<table style="margin-bottom: 8px;" cellspacing="0" cellpadding="0" width="100%" border="0" class="bigBoss">
						<tbody>
							<tr>
								<td class="f66" align="left" width="80%" height="36px">&nbsp;&nbsp; 
								日期 ： <input id="createTime" name="createTime" value="${param.createTime}" onclick="WdatePicker({dateFmt:'yyyy-MM',readOnly:'readOnly'})" class="hhn"/>&nbsp;&nbsp;
									<input id="btSearch" type="submit" value="查找" style="width: 60px;"/>
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
										<th rowspan="2">选中</th>
										<th style="width: 5%;" scope="col" rowspan="2">日期</th>
										<th scope="col" colspan="5">彩生活进账</th>
										<th scope="col" colspan="3">物业国际</th>
										<th scope="col" colspan="3">平台进账</th>
										<th scope="col" rowspan="2">总进账金额</th>
										<th scope="col" rowspan="2">放款金额</th>
										<th scope="col" rowspan="2">取现金额</th>
										<th scope="col" colspan="3">可用余额汇总（站岗资金）</th>
									</tr>
									<tr class=gvHeader>
										<th scope="col">彩富人生</th>
										<th scope="col">散标</th>
										<th scope="col">爱定宝</th>
										<th scope="col">红包理财</th>
										<th scope="col">总额</th>
										<th scope="col">+薪宝</th>
										<th scope="col">+族宝</th>
										<th scope="col">总额</th>
										<th scope="col">散标</th>
										<th scope="col">爱定宝</th>
										<th scope="col">总额</th>
										<th scope="col">通联天下可用余额</th>
										<th scope="col">汇付账户可用余额</th>
										<th scope="col">总额</th>
									</tr>
									<c:choose>
										<c:when test="${pageDo.commonModeList == null}">
											<tr align="center" class="gvItem">
												<td colspan="15">暂无数据</td>
											</tr>
										</c:when>
										<c:otherwise>
											<c:set var="count" value="0" />
											<c:forEach items="${pageDo.commonModeList}" var="map">
												<tr class="gvItem">
													<td align="center"><input id="gvNews_ctl02_cbID" class="downloadId" type="checkbox" value="${map.accountId }" name="cb_ids" /></td>
													<c:set var="count" value="${count+1}" />
													<td>
														${map.createTime}
													</td>
													<td>
														<fmt:formatNumber value="${map.rechargeMoney}" pattern="##.##" minFractionDigits="2" />  
													</td>
													<td>
														<fmt:formatNumber value="${map.investAmount}" pattern="##.##" minFractionDigits="2" />   
													</td>
													<td >
														<fmt:formatNumber value="${map.tradeAmount}" pattern="##.##" minFractionDigits="2" /> 
													</td>
													<td >
														<fmt:formatNumber value="${map.hbBuyMoney}" pattern="##.##" minFractionDigits="2" /> 
													</td>
													<td >
														<fmt:formatNumber value="${map.cliftTotalMoney}" pattern="##.##" minFractionDigits="2" /> 
													</td>
													<td >
														<fmt:formatNumber value="${map.jxbBuyMoney}" pattern="##.##" minFractionDigits="2" /> 
													</td>
													<td >
														<fmt:formatNumber value="${map.jzbBuyMoney}" pattern="##.##" minFractionDigits="2" /> 
													</td>
													<td >
														<fmt:formatNumber value="${map.gjwyBuyMoney}" pattern="##.##" minFractionDigits="2" /> 
													</td>
													<td>
														<fmt:formatNumber value="${map.platformInvestAmount}" pattern="##.##" minFractionDigits="2" />  
													</td>
													<td>
														<fmt:formatNumber value="${map.platformTradeAmount }" pattern="##.##" minFractionDigits="2" />
													</td>
													<td >
														<fmt:formatNumber value="${map.platformTotalMoney}" pattern="##.##" minFractionDigits="2" /> 
													</td>
													<td >
														<fmt:formatNumber value="${map.totalMoney}" pattern="##.##" minFractionDigits="2" /> 
													</td>
													<td >
														<fmt:formatNumber value="${map.borrowAmount}" pattern="##.##" minFractionDigits="2" /> 
													</td>
													<td >
														<fmt:formatNumber value="${map.withdrawal}" pattern="##.##" minFractionDigits="2" /> 
													</td>
													<td >
														<fmt:formatNumber value="${map.tlAvailableMoney}" pattern="##.##" minFractionDigits="2" /> 
													</td>
													<td >
														<fmt:formatNumber value="${map.hfAvailableMoney}" pattern="##.##" minFractionDigits="2" /> 
													</td>
													<td >
														<fmt:formatNumber value="${map.totalAmount}" pattern="##.##" minFractionDigits="2" /> 
													</td>
											</tr>
										</c:forEach>
									</c:otherwise>
									</c:choose>
							</tbody>
						</table>
							<div>
								&nbsp;&nbsp; <input id="chkAll" onclick=checkAll(this); type="checkbox" value="checkbox" name="chkAll" /> &nbsp;全选&nbsp;&nbsp;&nbsp;<input id="excel" type="button" onclick="checkSend()"
									value="导出Excel" name="excel" />
							
							</div>
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
