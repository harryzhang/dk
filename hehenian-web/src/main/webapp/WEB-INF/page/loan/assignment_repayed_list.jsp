<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="../css/jbox/Gray/jbox.css" type="text/css"></link>
<div>
	 <table id="help" style="width: 100%; color: #333333;" cellspacing="1"
		cellpadding="3" bgcolor="#dee7ef" border="0">
		<tr class=gvItem>
			<th style="width: 100px;" scope="col">期数</th>
			<th style="width: 100px;" scope="col">还款日期</th>
			<th style="width: 100px;" scope="col">应还本金</th>
			<th style="width: 100px;" scope="col">剩余本金</th>
			<th style="width: 100px;" scope="col">应还本息</th>
			<th style="width: 100px;" scope="col">当前罚息</th>
			<th style="width: 100px;" scope="col">总还款额</th>
			<th style="width: 100px;" scope="col">还款状态</th>
			<th style="width: 100px;" scope="col">实际还款日</th>
		</tr>
		<c:choose>
			<c:when test="${pageDo.modelList == null}">
				<tr align="center" class="gvItem">
					<td colspan="14">暂无数据</td>
				</tr>
			</c:when>
			<c:otherwise>
			<c:set var="count" value="0" />
			<c:forEach items="${pageDo.modelList}" var="repaymentDo">
				<tr class="gvItem" style="height: 25px;">
					<td align="center">${repaymentDo.repayPeriod}</td>
					<td align="center">
						<fmt:formatDate value="${repaymentDo.repayDate}"  pattern="yyyy-MM-dd"/>
					</td>
					<td align="center">${repaymentDo.stillPrincipal}</td>
					<td align="center">${repaymentDo.principalBalance}</td>
					<td align="center">${repaymentDo.stillPrincipal+repaymentDo.stillInterest}</td>
					<td align="center">${repaymentDo.hasFi}</td>
					<td align="center">${repaymentDo.hasFi + repaymentDo.stillInterest + repaymentDo.stillPrincipal + repaymentDo.consultFee + repaymentDo.repayFee}</td>
					<td align="center">
					<c:if test="${repaymentDo.repayStatus == 1 }">未偿还 </c:if>
					<c:if test="${repaymentDo.repayStatus == 2 }">已偿还</c:if>
					</td>
					<td align="center">
						<fmt:formatDate value="${repaymentDo.realRepayDate}"  pattern="yyyy-MM-dd"/>
					</td>
				</tr>
			</c:forEach>
		</c:otherwise>
		</c:choose>
	</table>
</div>
<br>

