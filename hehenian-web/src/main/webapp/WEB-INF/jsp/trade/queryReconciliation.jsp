<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="/include/taglib.jsp"%>
<html>
<head>
<title>对账信息</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
<meta http-equiv="description" content="This is my page" />
<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
<script type="text/javascript" src="../css/popom.js"></script>
<script language="javascript" type="text/javascript" src="../My97DatePicker/WdatePicker.js"></script>
		<style type="text/css">
			.bigBoss  tbody td input{width: 140px;}
		</style>
</head>
<body>
	<div id="right">
		<div style="padding: 15px 10px 0px 10px;">
			<div>
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td width="100" height="28" class="main_alll_h2"><a href="queryReconciliation.do">对账信息</a></td>
						<td width="2">&nbsp;</td>
						<td>&nbsp;</td>
					</tr>
				</table>
				<div
					style="padding-right: 10px; padding-left: 10px; padding-bottom: 10px; width: 95%; padding-top: 10px; background-color: #fff;">
					<form action="queryReconciliation.do" method="get">
						<table style="margin-bottom: 8px;" cellspacing="0" cellpadding="0" width="100%" border="0" class="bigBoss">
						<tbody>
							<tr>
								<td class="f66" align="left" width="80%" height="36px">&nbsp;&nbsp; 用户名 ： <input id="username"
									name="reconciliationDo.userDo.username" value="${reconciliationDo.userDo.username }" type="text" />&nbsp;&nbsp; 对账类型 ： <select
										name="reconciliationDo.reconciliationType">
											<option value="">--请选择--</option>
											<option value="RECHARGE"
												<c:if test="${reconciliationDo.reconciliationType == 'RECHARGE'}">selected</c:if>>充值</option>
											<option value="CASH"
												<c:if test="${reconciliationDo.reconciliationType == 'CASH'}">selected</c:if>>提现</option>
											<option value="DEBT"
												<c:if test="${reconciliationDo.reconciliationType == 'DEBT'}">selected</c:if>>债权</option>
											<option value="LOANS"
												<c:if test="${reconciliationDo.reconciliationType == 'LOANS'}">selected</c:if>>放款</option>
											<option value="REPAYMENT"
												<c:if test="${reconciliationDo.reconciliationType == 'REPAYMENT'}">selected</c:if>>还款</option>
											<option value="TRANSFER"
												<c:if test="${reconciliationDo.reconciliationType == 'TRANSFER'}">selected</c:if>>商户扣款</option>
									</select> &nbsp;&nbsp; 
									对账状态 ： <select
										name="reconciliationDo.reconciliationStatus">
											<option value="">--请选择--</option>
											<option value="UNRECONCILIATION"
												<c:if test="${reconciliationDo.reconciliationStatus == 'UNRECONCILIATION'}">selected</c:if>>未对账</option>
											<option value="SUCCESS"
												<c:if test="${reconciliationDo.reconciliationStatus == 'SUCCESS'}">selected</c:if>>对账成功</option>
											<option value="FAILURE"
												<c:if test="${reconciliationDo.reconciliationStatus == 'FAILURE'}">selected</c:if>>对账失败</option>
									</select> &nbsp;&nbsp; 订单号： <input id="ordId" name="reconciliationDo.ordId" value="${reconciliationDo.ordId }" type="text" />&nbsp;&nbsp;
									<fmt:formatDate value="${reconciliationDo.ordDate}" pattern="yyyy-MM-dd" var="formatDay"/>
									订单日期：<input id="ordDate" class="reconciliationDo。ordDate" name="reconciliationDo.ordDate" value="${formatDay}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})"/> 
								<input id="btSearch" type="submit" value="查找" style="width: 60px;" /></td>
							</tr>
						</tbody>
					</table>
					</form>
					<div>
						<table id="help" style="color: #333333; width: 100%" cellspacing="1"
								cellpadding="3" bgcolor="#dee7ef" border="0">
								<tbody>
									<tr class=gvHeader>
										<th style="width: 5%;" scope="col">序号</th>
										<th scope="col">订单编号</th>
										<th scope="col">用户名</th>
										<th scope="col">对账类型</th>
										<th scope="col">对账状态</th>
										<th scope="col">对账失败描叙</th>
										<th scope="col">交易金额</th>
										<th scope="col">汇付交易状态</th>
										<th scope="col">订单日期</th>
										<th scope="col">取现手续费金额</th>
										<th scope="col">扣款方账号</th>
										<th scope="col">转让金额</th>
										<th scope="col">承接金额</th>
										<th scope="col">转让 手续费</th>
									</tr>
									<c:choose>
										<c:when test="${pageDo.modelList == null}">
											<tr align="center" class="gvItem">
												<td colspan="14">暂无数据</td>
											</tr>
										</c:when>
										<c:otherwise>
											<c:set var="count" value="0" />
											<c:forEach items="${pageDo.modelList}" var="reconciliationDo">
												<tr class="gvItem">
													<c:set var="count" value="${count+1}" />
													<td align="center" style="width: 100px;">${count}</td>
													<td align="center" style="width: 100px;" class="borrow"><input type="hidden" class="ordId" value="${reconciliationDo.ordId}" /> ${reconciliationDo.ordId} </td>
													<td>
														${reconciliationDo.userDo.username} <input id="username" value="${reconciliationDo.userDo.username}" type="hidden"> 
													</td>
													<td>
														<c:if test="${reconciliationDo.reconciliationType == 'RECHARGE'}">充值</c:if>
														<c:if test="${reconciliationDo.reconciliationType == 'CASH'}">提现</c:if>
														<c:if test="${reconciliationDo.reconciliationType == 'DEBT'}">债权</c:if>
														<c:if test="${reconciliationDo.reconciliationType == 'LOANS'}">放款</c:if>
														<c:if test="${reconciliationDo.reconciliationType == 'REPAYMENT'}">还款</c:if>
														<c:if test="${reconciliationDo.reconciliationType == 'TRANSFER'}">商户扣款</c:if>
													</td>
													<td>
														<c:if test="${reconciliationDo.reconciliationStatus == 'UNRECONCILIATION'}">未对账</c:if>
														<c:if test="${reconciliationDo.reconciliationStatus == 'SUCCESS'}">对账成功</c:if>
														<c:if test="${reconciliationDo.reconciliationStatus == 'FAILURE'}">对账失败</c:if>
													</td>
													<td title="${reconciliationDo.reconciliationDesc}">
														${reconciliationDo.reconciliationDesc}
													</td>
													<td>${reconciliationDo.transAmt}</td>
													<td>
														<c:if test="${reconciliationDo.transStat == 'S'}">成功</c:if>
														<c:if test="${reconciliationDo.transStat == 'F'}">失败</c:if>
														<c:if test="${reconciliationDo.transStat == 'I'}">初始</c:if>
														<c:if test="${reconciliationDo.transStat == 'P'}">部分成功</c:if>
														<c:if test="${reconciliationDo.transStat == 'H'}">经办</c:if>
														<c:if test="${reconciliationDo.transStat == 'R'}">拒绝</c:if>
													</td>
													<td>
														<fmt:formatDate value="${reconciliationDo.ordDate}"  pattern="yyyy-MM-dd HH:mm:ss"/>
													<td>
														${reconciliationDo.feeAmt }
													</td>
													<td>${reconciliationDo.feeCustId ne null ? reconciliationDo.feeCustId : ((reconciliationDo.feeAcctId ne null) ? reconciliationDo.feeAcctId : '')}</td>
													<td>${reconciliationDo.creditAmt}</td>
													<td>${reconciliationDo.creditDealAmt}</td>
													<td>${reconciliationDo.creditAmt - reconciliationDo.creditDealAmt}</td>
											</tr>
										</c:forEach>
									</c:otherwise>
									</c:choose>
							</tbody>
						</table>
						<shove:page curPage="${pageDo.currentPage}" showPageCount="10"
							pageSize="${pageDo.pageSize}" theme="jsText"
							totalCount="${pageDo.totalCount}" funMethod="toPage">
						</shove:page>

					</div>
				</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">

	function toPage(pageNo) {
		window.location.href = "queryReconciliation.do?reconciliationDo.userDo.username=${reconciliationDo.userDo.username}&reconciliationDo.ordId=${reconciliationDo.ordId}&reconciliationDo.ordDate=${formatDay}&curPage="
				+ pageNo + "&pageSize=10";
	}
</script>
</html>
