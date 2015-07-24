<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="/include/taglib.jsp"%>
<html>
<head>
<title>审核管理-借款申请管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
<script type="javascript" type="text/javascript"
	src="../My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="../css/popom.js"></script>
</head>
<body>
	<div id="right">
		<div style="padding: 15px 10px 0px 10px;">
			<div>
				<table border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td width="120" height="28" class="main_alll_h2"><a
							href="javascript:void(0)">借款申请管理</a></td>
						<td width="2">&nbsp;</td>
						<td width="2">&nbsp;</td>
						<td width="2">&nbsp;</td>
						<td>&nbsp;</td>
					</tr>
				</table>
				<div
					style="padding-right: 10px; padding-left: 10px; padding-bottom: 10px; width: 95%; padding-top: 10px; background-color: #fff;">
					<form action="queryLoanInfos.do" method="post">
						<table style="margin-bottom: 8px;" cellspacing="0" cellpadding="0"
							width="100%" border="0">
							<tbody>
								<tr>
									<td class="f66" align="left" width="50%" height="36px">
										用户姓名：<input type="text" id="idNo" name="loanInfoDo.realName" value="${loanInfoDo.realName}" /> 
										产品类型：<select name="loanInfoDo.productType">
												<option value="">--请选择--</option>
												<option value="薪金贷 " <c:if test="${loanInfoDo.productType== '薪金贷'}">selected</c:if>>薪金贷</option>
												<option value="生意贷" <c:if test="${loanInfoDo.productType == '生意贷'}">selected</c:if>>生意贷</option>
												<option value="业主贷" <c:if test="${loanInfoDo.productType == '业主贷'}">selected</c:if>>业主贷</option>
												<option value="精英贷 " <c:if test="${loanInfoDo.productType == '精英贷'}">selected</c:if>>精英贷</option>
												<option value="合和贷 " <c:if test="${loanInfoDo.productType == '合和贷'}">selected</c:if>>合和贷</option>
											</select> 
										状态： <select name="loanInfoDo.loanStatus">
												<option value="">--请选择--</option>
												<option value="CANCEL" <c:if test="${loanInfoDo.loanStatus == 'CANCEL'}">selected</c:if>>已作废</option>
												<option value="UNRELEASE" <c:if test="${loanInfoDo.loanStatus == 'UNRELEASE'}">selected</c:if>>未发布</option>
												<option value="TOCHINAPNR" <c:if test="${loanInfoDo.loanStatus == 'TOCHINAPNR'}">selected</c:if>>已发布汇付</option>
												<option value="TODEPOSIT" <c:if test="${loanInfoDo.loanStatus == 'TODEPOSIT'}">selected</c:if>>已发布定存</option>
											</select> 
											&nbsp;&nbsp; 
										<input id="btSearch" type="submit" value="查找" />
									</td>
								</tr>
							</tbody>
						</table>
					</form>
					<div>
						<table id="help" style="width: 98%; color: #333333;"
							cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="0">
							<tbody>
								<tr class=gvHeader>
									<th scope="col">真实姓名</th>
									<th scope="col">身份证号码</th>
									<th scope="col">产品类型</th>
									<th scope="col">借款标题	</th>
									<th scope="col">借款金额</th>
									<th scope="col">还款方式</th>
									<th scope="col">借款年利率</th>
									<th scope="col">投资年利率</th>
									<th scope="col">借款期限</th>
									<th scope="col">筹标期限</th>
									<th scope="col">状态</th>
									<th scope="col">操作</th>
								</tr>
								<c:choose>
									<c:when test="${pageDo.modelList == null}">
										<tr align="center" class="gvItem">
											<td colspan="14">暂无数据</td>
										</tr>
									</c:when>
									<c:otherwise>
										<c:forEach items="${pageDo.modelList}" var="loanInfoDo">
											<tr class="gvItem">
												<td>${loanInfoDo.realName}</td>
												<td>${loanInfoDo.idNo}</td>
												<td>${loanInfoDo.productType}</td>
												<td>${loanInfoDo.loanUsage}</td>
												<td><fmt:formatNumber value="${loanInfoDo.loanAmt}" pattern="##0.00"/></td>
												<td>
													<c:choose>
														<c:when test="${loanInfoDo.repayWay == 'FPIC'}">平息</c:when>
														<c:when test="${loanInfoDo.repayWay == 'LP'}">等额本金</c:when>
														<c:when test="${loanInfoDo.repayWay == 'IIFP'}">一次付息到期还款</c:when>
														<c:when test="${loanInfoDo.repayWay == 'MIFP'}">按月付息到期还本</c:when>
														<c:when test="${loanInfoDo.repayWay == 'PI'}">等额本息</c:when>
														<c:when test="${loanInfoDo.repayWay == 'HHD24'}">合和贷24期</c:when>
														<c:when test="${loanInfoDo.repayWay == 'HHD36'}">合和贷36期</c:when>
														<c:when test="${loanInfoDo.repayWay == 'EL'}">精英贷</c:when>
														<c:otherwise>--</c:otherwise>
													</c:choose>
												</td>
												<td><c:choose><c:when test="${loanInfoDo.loanAnnualRate != null}"><fmt:formatNumber value="${loanInfoDo.loanAnnualRate}" pattern="##0.00"/>%</c:when><c:otherwise>--</c:otherwise></c:choose></td>
												<td><fmt:formatNumber value="${loanInfoDo.annualRate}" pattern="##0.00"/>%</td>
												<td>${loanInfoDo.loanPeriod}期</td>
												<td>${loanInfoDo.tenderDay}天</td>
												<td><c:choose>
														<c:when test="${loanInfoDo.loanStatus == 'CANCEL'}">已作废</c:when>
														<c:when test="${loanInfoDo.loanStatus == 'UNRELEASE'}">未发布</c:when>
														<c:when test="${loanInfoDo.loanStatus == 'TOCHINAPNR'}">已发布汇付</c:when>
														<c:when test="${loanInfoDo.loanStatus == 'TODEPOSIT'}">已发布定存</c:when>
														<c:otherwise>--</c:otherwise>
													</c:choose>
												</td>
												<td>
													<c:choose>
														<c:when test="${loanInfoDo.loanStatus == 'UNRELEASE'}">
															<a href="javascript:releaseLoanInfo('${loanInfoDo.loanInfoId}','TOCHINAPNR')">发布到汇付</a>
															<a href="javascript:releaseLoanInfo('${loanInfoDo.loanInfoId}','TODEPOSIT')">发布到定存</a>
														</c:when>
														<c:otherwise>
															--
														</c:otherwise>
													</c:choose></td>
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
	function releaseLoanInfo(loanInfoId,loanStatus) {
		if (!confirm("您确认继续此操作吗?")) {
			return ;
		}
		$.get("releaseLoanInfo.do", {
			loanInfoId : loanInfoId,
			loanStatus : loanStatus
		}, function(data) {
			if (data.success) {
				alert(data.message);
				window.location.reload();
			} else {
				alert(data.message);
			}
		});
	}

	function toPage(pageNo) {
		window.location.href = "queryLoanInfos.do?loanDetailDo.idNo=${loanDetailDo.idNo}&loanDetailDo.mobile=${loanDetailDo.mobile}&loanDetailDo.loanStatus=${loanDetailDo.loanStatus}&curPage="
				+ pageNo + "&pageSize=10";
	}
</script>
</html>
