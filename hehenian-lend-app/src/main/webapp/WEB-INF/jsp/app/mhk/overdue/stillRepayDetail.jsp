<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<%@ include file="../../include/top.jsp"%>
<title></title>
</head>
<header>
	<div class="p1 db_f enable_inf">
		<div class="bf1 p1 loan_person">
			<h3 class="loan_person_name">${loanPersonDo.realName}</h3>
			<span class="loan_person_phone">${loanPersonDo.mobile}</span>
		</div>
		<div class="loan_inf p1">
			<h4 class="t_ar">
				<fmt:formatNumber value="${loanPersonDo.loanDo.applyAmount}"
					pattern="#,#00.00#" />
			</h4>
			<span>借款金额(元)</span>
			<h4 class="t_ar">${loanPersonDo.loanDo.loanPeriod }</h4>
			<span>借款期数(月)</span>
		</div>
	</div>
</header>
<section>

	<article>
		<p class="rp_head p1">
			第<span class="dib fcl">${loanRepaymentDo.repayPeriod}/${loanPersonDo.loanDo.loanPeriod }</span>期 还款日期：<span class="dib fcl"><fmt:formatDate value="${loanRepaymentDo.repayDate}"
							pattern="yyyy-MM-dd" /></span>
		</p>
		<div class="db_f p1 repay_inf">
			<div class="bf1 db_f">
				<div class="bf1">
					<span><fmt:formatNumber
							value="${loanRepaymentDo.stillPrincipal }"
							pattern="#,#00.00#" /></span> <em>应还本金(元)</em>
				</div>
				<div class="bf1">
					<span><fmt:formatNumber
							value="${loanRepaymentDo.stillInterest }"
							pattern="#,#00.00#" /></span> <em>应还利息(元)</em>
				</div>
			</div>
			<div class="repayMoney">
				<span><fmt:formatNumber
							value="${loanRepaymentDo.stillPrincipal+loanRepaymentDo.stillInterest}"
							pattern="#,#00.00#" /></span> <em>应还总额(元)</em>
			</div>
		</div>
	</article>

</section>
	<article>
		<table class="table w10 p1">
			<thead>
			<tr>
				<th>期数</th>
				<th>还款日期</th>
				<th>应还总额</th>
				<th>还款状态</th>
			</tr>
			</thead>
			<tbody>
			<c:forEach items="${rmList}" var="loanRepaymentDo">
				<tr>
					<td>${loanRepaymentDo.repayPeriod }/${loanPersonDo.loanDo.loanPeriod }
					</td>
					<td><fmt:formatDate value="${loanRepaymentDo.repayDate}"
							pattern="yyyy-MM-dd" /></td>
					<td><fmt:formatNumber
							value="${loanRepaymentDo.stillPrincipal+loanRepaymentDo.stillInterest+loanRepaymentDo.lateFi }"
							pattern="#,#00.00#" /></td>
					<td><c:if test="${loanRepaymentDo.repayStatus==1 }">
                   		未还
                </c:if> <c:if test="${loanRepaymentDo.repayStatus==2 }">
                        	已还
                </c:if></td>
				</tr>
			</c:forEach>
			</tbody>

		</table>
	</article>
<%@ include file="../../include/foot.jsp"%>
<script>

		</script>
</body>
</html>