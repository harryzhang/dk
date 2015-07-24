<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
	<head>
		<%@ include file="../include/top.jsp"%>
		<title>订单详情</title>
	</head>
	<body>
		<c:if test="${loanPersonDo.loanDo.loanStatus=='NOPASS'}"> 
		    <span class="pa err_alt"></span>
		</c:if>
			<header>
			    <div class="p1 db_f enable_inf">
			    	<div class="bf1 p1 loan_person" style="padding-top:30px;">
			    	  <h3 class="loan_person_name">${loanPersonDo.realName}</h3>
			    	  <span class="loan_person_phone">${loanPersonDo.mobile}</span>
			    	</div>
			    	<div class="bf1 p1 loan_person" style="padding-top:30px;">
			    		<h4 class="t_ar"><fmt:formatNumber value="${loanPersonDo.loanDo.applyAmountString}" pattern="#,#00.00#"  /> </h4>
			    		<h4 class="t_ar">借款金额(元)</h4>
			    		 <h4 class="t_ar">${loanPersonDo.loanDo.loanPeriod }</h4>
			    		 <h4 class="t_ar">借款期数(月)</h4>
			    	</div>
			    </div>
			</header>
	<section>
<article>
<%-- 				<h3 class="tip">拒绝原因：</h3>
				<p class="p1" style="line-height:23px;min-height:40px;">
					${auditLog.reason }
					</p>
 --%>			</article>
			<div class="db_f btn_box">
				<p class="bf1 p1"><a href="<c:url value='/app/mhk/initPersonInfo.do?loanId=${loanPersonDo.loanDo.loanId}'/>" class="btn">查看审核资料</a></p>
				<p class="bf1 p1"><a href="<c:url value='/app/mhk/initTreatyData.do?loanId=${loanPersonDo.loanDo.loanId}'/>" class="btn" style="background:none; color:#45b0f7; background:#fff;">签约合同修改</a></p>
			</div>
		</section>
	</body>
</html>