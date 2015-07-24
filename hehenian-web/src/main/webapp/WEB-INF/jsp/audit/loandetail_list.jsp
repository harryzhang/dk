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
					<form action="queryLoanDetails.do" method="get">
						<table style="margin-bottom: 8px;" cellspacing="0" cellpadding="0"
							width="100%" border="0">
							<tbody>
								<tr>
									<td class="f66" align="left" width="50%" height="36px">
										身份证号：<input type="text" id="idNo" name="loanDetailDo.idNo"
										value="${loanDetailDo.idNo}" /> 手机号码：<input type="text"
										id="mobile" name="loanDetailDo.mobile"
										value="${loanDetailDo.mobile}" /> 状态： <select
										name="loanDetailDo.loanStatus">
											<option value="">--请选择--</option>
											<option value="PROCESSING"
												<c:if test="${loanDetailDo.loanStatus == 'PROCESSING'}">selected</c:if>>申请中</option>
											<option value="CHECKED"
												<c:if test="${loanDetailDo.loanStatus == 'CHECKED'}">selected</c:if>>校验通过</option>
											<option value="UNCHECKED"
												<c:if test="${loanDetailDo.loanStatus == 'UNCHECKED'}">selected</c:if>>校验不通过</option>
									</select> &nbsp;&nbsp; <input id="btSearch" type="submit" value="查找" />
										&nbsp;&nbsp; <input id="excel" type="button" value="导出excel" />
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
									<th style="width: 5%;" scope="col">序号</th>
									<th style="width: 10%;" scope="col">姓名</th>
									<th style="width: 10%;" scope="col">身份证号码</th>
									<th style="width: 8%;" scope="col">手机号码</th>
									<th style="width: 5%;" scope="col">部门</th>
									<th style="width: 10%;" scope="col">职位</th>
									<th style="width: 5%;" scope="col">月收入</th>
									<th style="width: 5%;" scope="col">卡号</th>
									<th style="width: 10%;" scope="col">所属银行</th>
									<th style="width: 10%;" scope="col">状态</th>
									<th style="width: 7%;" scope="col">借款金额</th>
									<th style="width: 5%;" scope="col">月收入</th>
									<th style="width: 10%;" scope="col">操作</th>
								</tr>
								<c:choose>
									<c:when test="${pageDo.modelList == null}">
										<tr align="center" class="gvItem">
											<td colspan="14">暂无数据</td>
										</tr>
									</c:when>
									<c:otherwise>
										<c:set var="count" value="0" />
										<c:forEach items="${pageDo.modelList}" var="loanDetailDo">
											<tr class="gvItem">
												<c:set var="count" value="${count+1}" />
												<td align="center" style="width: 100px;">${count}</td>
												<td>${loanDetailDo.realName}</td>
												<td>${loanDetailDo.idNo}</td>
												<td>${loanDetailDo.mobile}</td>
												<td>--</td>
												<td>--</td>
												<td><fmt:formatNumber value="${loanDetailDo.income}" pattern="##0.00"/></td>
												<td><c:choose><c:when test="${loanDetailDo.bankCardDo.cardNo != null}">${loanDetailDo.bankCardDo.cardNo}</c:when><c:otherwise>--</c:otherwise></c:choose></td>
												<td><c:choose><c:when test="${loanDetailDo.bankCardDo.bankName != null}">${loanDetailDo.bankCardDo.bankName}</c:when><c:otherwise>--</c:otherwise></c:choose></td>
												<td><c:choose>
														<c:when test="${loanDetailDo.loanStatus == 'PROCESSING'}">申请中</c:when>
														<c:when test="${loanDetailDo.loanStatus == 'CHECKED'}">校验通过</c:when>
														<c:when test="${loanDetailDo.loanStatus == 'UNCHECKED'}">校验不通过</c:when>
														<c:otherwise>未知</c:otherwise>
													</c:choose></td>
												<td><fmt:formatNumber value="${loanDetailDo.loanAmount}" pattern="##0.00"/></td>
												<td>${loanDetailDo.loanPeriod}期</td>
												<td><c:choose>
														<c:when test="${loanDetailDo.loanStatus == 'PROCESSING'}">
															<a href="javascript:changeLoanStatus('${loanDetailDo.loanId}','CHECKED')">通过</a>
															<a href="javascript:changeLoanStatus('${loanDetailDo.loanId}','UNCHECKED')">拒绝</a>
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

	function changeLoanStatus(loanId,loanStatus) {
		if (!confirm("您确认继续此操作吗?")) {
			return ;
		}
		$.get("changeLoanStatus.do", {
			loanId : loanId,
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
	
	$("#excel").bind("click",function(){
		window.location.href = "exportLoanDetails.do?loanDetailDo.idNo=${loanDetailDo.idNo}&loanDetailDo.mobile=${loanDetailDo.mobile}&loanDetailDo.loanStatus=${loanDetailDo.loanStatus}&curPage=${pageDo.currentPage}&pageSize=10";
	});

	function toPage(pageNo) {
		window.location.href = "queryLoanDetails.do?loanDetailDo.idNo=${loanDetailDo.idNo}&loanDetailDo.mobile=${loanDetailDo.mobile}&loanDetailDo.loanStatus=${loanDetailDo.loanStatus}&curPage="
				+ pageNo + "&pageSize=10";
	}
</script>
</html>
