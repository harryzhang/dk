<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<jsp:include page="/include/head.jsp"></jsp:include>
<link rel="stylesheet" type="text/css" href="css/css.css"></link>
</head>
<body>
	<div class="nymain" style="margin:20px auto ;width:700px;border-left: none;border: 1px #ccc solid;">
		<div class="wdzh" style="width:700px;margin: 0px auto 0 auto;border: 0px;">
			<div class="r_main" style="width: 96%;margin: 10px 13px;;">
				<div class="box">
					<div class="boxmain2" style="padding: 15px;">
						<div class="biaoge" style="margin-top: -10px;">
							<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin: 10px auto;">
								<tr>
									<th style="width: 40px;">期数</th>
									<th style="width: 80px;">还款时间</th>
									<th style="width: 80px;">本期应收本金</th>
									<th style="width: 80px;">本期应收利息</th>
									<th style="width: 65px;">剩余本金</th>
									<th style="width: 90px;">本期应收本息</th>
<%--									<th style="width: 70px;">还款人</th>--%>
									<th style="width: 70px;">还款状态</th>
								</tr>
								<s:if test="request.listMap==null || request.listMap.size==0">
									<tr align="center">
										<td colspan="11">暂无数据</td>
									</tr>
								</s:if>
								<s:else>
									<s:iterator value="#request.listMap" var="bean">
										<tr>
											<td align="center">${bean.repayPeriod}</td>
											<td align="center">${bean.repayDate}</td>
											<td align="center">${bean.forpayPrincipal}</td>
											<td align="center">${bean.forpayInterest}</td>
											<td align="center">${bean.principalBalance}</td>
											<td align="center">${bean.forpaySum}</td>
<%--											<td align="center"><s:if test="%{#bean.isWebRepay == 2}">网站垫付</s:if> <s:else>${bean.username}</s:else>--%>
<%--											</td>--%>
											<td align="center">
											<s:if test="#bean.isLate == 1 && #bean.repayStatus == 1">未还</s:if> 
											<s:elseif test="#bean.isLate == 1 && #bean.repayStatus == 2">已还</s:elseif>
											<s:elseif test="#bean.isLate == 2 && #bean.isWebRepay == 1">逾期</s:elseif>
											<s:elseif test="#bean.isLate == 2 && #bean.isWebRepay == 2">代偿</s:elseif>
											<s:else>已还</s:else></td>
										</tr>
									</s:iterator>
								</s:else>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>