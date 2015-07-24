<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ include file="/include/taglib.jsp"%>
<html>
<head>
<title>结算方案管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<div id="right">
		<div style="padding: 15px 10px 0px 10px;">
			<div>
				<table id="help" style="width: 98%; color: #333333;">
					<tbody>
						<tr class=gvHeader>
							<th style="width: 5%;" scope="col">序号</th>
							<th style="width: 15%;" scope="col">费用名称</th>
							<th style="width: 15%;" scope="col">费用类型</th>
							<th style="width: 20%;" scope="col">收取方式</th>
							<th style="width: 10%;" scope="col">收取比率</th>
							<th style="width: 15%;" scope="col">收取金额</th>
							<th style="width: 20%;" scope="col">最后修改时间</th>
						</tr>
						<c:choose>
							<c:when test="${settSchemeDo.feeRuleDoList == null}">
								<tr align="center" class="gvItem">
									<td colspan="14">暂无数据</td>
								</tr>
							</c:when>
							<c:otherwise>
								<c:set var="count" value="0" />
								<c:forEach items="${settSchemeDo.feeRuleDoList}" var="feeRuleDo">
									<tr class="gvItem">
										<c:set var="count" value="${count+1}" />
										<td align="center" style="width: 100px;">${count}</td>
										<td>${feeRuleDo.ruleName}</td>
										<td><c:choose>
												<c:when test="${feeRuleDo.ruleType == 'CONSULT_FEE'}">咨询费</c:when>
												<c:when test="${feeRuleDo.ruleType == 'SERV_FEE'}">手续费</c:when>
												<c:when test="${feeRuleDo.ruleType == 'SETTLE_FEE'}">提现结清手续费</c:when>
												<c:when test="${feeRuleDo.ruleType == 'CREDIT_FEE'}">征信费</c:when>
												<c:when test="${feeRuleDo.ruleType == 'OVERDUE_FEE'}">逾期罚息</c:when>
												<c:when test="${feeRuleDo.ruleType == 'OTHER'}">其他</c:when>
												<c:otherwise>--</c:otherwise>
											</c:choose></td>
										<td>
											<c:choose>
												<c:when test="${feeRuleDo.gatherWay == 'ONCE_RATIO'}">一次性比例收取</c:when>
												<c:when test="${feeRuleDo.gatherWay == 'ONCE_FIXED'}">一次性固定收取</c:when>
												<c:when test="${feeRuleDo.gatherWay == 'EACH_RATIO'}">每期比例收取</c:when>
												<c:when test="${feeRuleDo.gatherWay == 'EACH_FIXED'}">每期固定收取</c:when>
												<c:when test="${feeRuleDo.gatherWay == 'ONCE_MONRATIO'}">一次性比例收取（月度比例））</c:when>
												<c:otherwise>--</c:otherwise>
											</c:choose>
										</td>
										<td>${feeRuleDo.gatherRate}%</td>
										<td>${feeRuleDo.feeAmount}</td>
										<td><fmt:formatDate value="${feeRuleDo.updateTime}"
												pattern="yyyy/MM/dd" /></td>
									</tr>
								</c:forEach>
								<tr align="center" class="gvItem">
									<td colspan="14"><input type="button" style="background: #666666;" value="关闭" onclick="closeMthodes();" /></td>
								</tr>
							</c:otherwise>
						</c:choose>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</body>
<script>
	//弹出窗口关闭
	function closeMthodes() {
		window.parent.closeMthod();
	}
</script>
</html>
