<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
</head>
<body>
	<form action="updateLoanInfo.do" id="updateAdmin" method="post">
		<div id="right">
			<div style="padding: 15px 10px 0px 10px;">
				<div style="width: auto; background-color: #FFF; padding: 10px;">
					<table width="100%" border="0" cellspacing="1" cellpadding="3">
						<tr>
							<td style="height: 25px;" align="right" class="blue12">用户名：</td>
							<td align="left" class="f66">
								<input type="text" name="loanInfoDo.realName" value="${loanInfoDo.realName}"/>
							</td>
						</tr>
						<tr>
							<td style="height: 25px;" align="right" class="blue12">身份证号码：</td>
							<td align="left" class="f66">
								<input type="text" name="loanInfoDo.idNo" value="${loanInfoDo.idNo}"/>
							</td>
						</tr>
						<tr>
							<td style="height: 25px;" align="right" class="blue12">借款金额：</td>
							<td align="left" class="f66">
								<input type="text" name="loanInfoDo.loanAmt" value="${loanInfoDo.loanAmt}"/>
							</td>
						</tr>
						<tr>
							<td style="height: 25px;" align="right" class="blue12">借款期限：</td>
							<td align="left" class="f66">
								<select name="loanInfoDo.loanPeriod">
									<option value="6" <c:if test="${loanInfoDo.loanPeriod== 6}">selected</c:if>>6</option>
									<option value="12" <c:if test="${loanInfoDo.loanPeriod== 12}">selected</c:if>>12</option>
									<option value="18" <c:if test="${loanInfoDo.loanPeriod== 18}">selected</c:if>>18</option>
									<option value="24" <c:if test="${loanInfoDo.loanPeriod== 24}">selected</c:if>>24</option>
									<option value="30" <c:if test="${loanInfoDo.loanPeriod== 30}">selected</c:if>>30</option>
									<option value="36" <c:if test="${loanInfoDo.loanPeriod== 36}">selected</c:if>>36</option>
								</select>
							</td>
						</tr>
						<tr>
							<td style="height: 25px;" align="right" class="blue12">产品类型：</td>
							<td align="left" class="f66">
								<select name="loanInfoDo.productType">
									<option value="">--请选择--</option>
									<option value="薪金贷" <c:if test="${loanInfoDo.productType== '薪金贷'}">selected</c:if>>薪金贷</option>
									<option value="生意贷" <c:if test="${loanInfoDo.productType == '生意贷'}">selected</c:if>>生意贷</option>
									<option value="业主贷" <c:if test="${loanInfoDo.productType == '业主贷'}">selected</c:if>>业主贷</option>
									<option value="精英贷" <c:if test="${loanInfoDo.productType == '精英贷'}">selected</c:if>>生意贷</option>
									<option value="合和贷" <c:if test="${loanInfoDo.productType == '合和贷'}">selected</c:if>>业主贷</option>
								</select> 
							</td>
						</tr>
						<tr>
							<td style="height: 25px;" align="right" class="blue12">还款方式：</td>
							<td align="left" class="f66">
								<select name="loanInfoDo.repayType">
									<option value="">--请选择--</option>
									<option value="FPIC" <c:if test="${loanInfoDo.repayType== 'FPIC'}">selected</c:if>>等本等息</option>
									<option value="LP" <c:if test="${loanInfoDo.repayType == 'LP'}">selected</c:if>>等额本金</option>
									<option value="IIFP" <c:if test="${loanInfoDo.repayType == 'IIFP'}">selected</c:if>>一次付息到期还款</option>
									<option value="MIFP" <c:if test="${loanInfoDo.repayType == 'MIFP'}">selected</c:if>>按月付息到期还本</option>
									<option value="PI" <c:if test="${loanInfoDo.repayType == 'PI'}">selected</c:if>>等额本息</option>
									<option value="HHD24" <c:if test="${loanInfoDo.repayType == 'HHD24'}">selected</c:if>>合和贷24期</option>
									<option value="HHD36" <c:if test="${loanInfoDo.repayType == 'HHD36'}">selected</c:if>>合和贷36期</option>
									<option value="EL" <c:if test="${loanInfoDo.repayType == 'EL'}">selected</c:if>>精英贷</option>
								</select> 
							</td>
						</tr>
						<tr>
							<td style="height: 25px;" align="right" class="blue12">投资利率%：</td>
							<td align="left" class="f66">
								<input type="text" name="loanInfoDo.annualRate" value="${loanInfoDo.annualRate}"/>
							</td>
						</tr>
						<tr>
							<td style="height: 25px;" align="right" class="blue12">筹标期限（天）：</td>
							<td align="left" class="f66">
								<input type="text" name="loanInfoDo.tenderDay" value="${loanInfoDo.tenderDay}"/>
							</td>
						</tr>
						<tr>
							<td style="height: 25px;" align="right" class="blue12">借款用途：</td>
							<td align="left" class="f66">
								<input type="text" name="loanInfoDo.loanUsage" value="${loanInfoDo.loanUsage}"/>
							</td>
						</tr>
						<tr>
							<td height="25"></td>
							<td align="left" class="f66" style="color: Red;">${message}</td>
						</tr>
						<tr>
							<td height="36" align="right" class="blue12">&nbsp;</td>
							<td>&nbsp;</td>
						</tr>
						<tr>
							<td height="36" align="right" class="blue12">&nbsp;</td>
							<td>
								<button id="btn_save" style="background-image: url('../images/admin/btn_queding.jpg');width: 70px;border: 0;height: 26px"></button> &nbsp;
								<button type="reset" style="background-image: url('../images/admin/btn_chongtian.jpg');width: 70px;height: 26px;border: 0px"></button>&nbsp; &nbsp;
							</td>
						</tr>
					</table>
					<br />
				</div>
			</div>
		</div>
	</form>
</body>
</html>
