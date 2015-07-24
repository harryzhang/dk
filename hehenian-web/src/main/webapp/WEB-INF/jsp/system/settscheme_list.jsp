<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="/include/taglib.jsp"%>
<html>
<head>
<title>结算方案管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
<script type="text/javascript" src="../css/popom.js"></script>
<%@ include file="/include/includeJs.jsp"%>
<script type="text/javascript">
	//查看
	function toSettSchemeDetail(schemeId) {
		$.jBox("iframe:toSettSchemeDetail.do?schemeId=" + schemeId, {
			title : "费用规则",
			width : 630,
			height : 600,
			top : 25,
			buttons : {

			}
		});
	}

	//弹出窗口关闭
	function closeMthod() {
		window.jBox.close();
		window.location.reload();
	}

	//取消--关闭弹窗
	function closeMthodes() {
		window.jBox.close();
	}
</script>
</head>
<body>
	<div id="right">
		<div style="padding: 15px 10px 0px 10px;">
			<div>
				<table border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td width="120" height="28" class="main_alll_h2"><a
							href="javascript:void(0)">结算方案管理</a></td>
						<td width="2">&nbsp;</td>
						<td width="2">&nbsp;</td>
						<td width="2">&nbsp;</td>
						<td>&nbsp;</td>
					</tr>
				</table>
				<div
					style="padding-right: 10px; padding-left: 10px; padding-bottom: 10px; width: 95%; padding-top: 10px; background-color: #fff;">
					<form action="querySettSchemes.do" method="get">
						<table style="margin-bottom: 8px;" cellspacing="0" cellpadding="0"
							width="100%" border="0">
							<tbody>
								<tr>
									<td class="f66" align="left" width="50%" height="36px">
										方案代码：<input type="text" id="schemeCode" name="settSchemeDo.schemeCode" value="${settSchemeDo.schemeCode}" /> 
										还款方式：
										<select name="settSchemeDo.repayWay">
											<option value="">--请选择--</option>
											<option value="FPIC" <c:if test="${settSchemeDo.repayWay == 'FPIC'}">selected</c:if>>等本等息（平息）</option>
											<option value="LP" <c:if test="${settSchemeDo.repayWay == 'LP'}">selected</c:if>>等额本金</option>
											<option value="IIFP" <c:if test="${settSchemeDo.repayWay == 'IIFP'}">selected</c:if>>一次付息到期还款</option>
											<option value="MIFP" <c:if test="${settSchemeDo.repayWay == 'MIFP'}">selected</c:if>>按月付息到期还本</option>
											<option value="PI" <c:if test="${settSchemeDo.repayWay == 'PI'}">selected</c:if>>等额本息</option>
										</select> &nbsp;&nbsp; 
										回款方式：
										<select name="settSchemeDo.receiptWay">
											<option value="">--请选择--</option>
											<option value="FPIC" <c:if test="${settSchemeDo.receiptWay == 'FPIC'}">selected</c:if>>等本等息（平息）</option>
											<option value="LP" <c:if test="${settSchemeDo.receiptWay == 'LP'}">selected</c:if>>等额本金</option>
											<option value="IIFP" <c:if test="${settSchemeDo.receiptWay == 'IIFP'}">selected</c:if>>一次付息到期还款</option>
											<option value="MIFP" <c:if test="${settSchemeDo.receiptWay == 'MIFP'}">selected</c:if>>按月付息到期还本</option>
											<option value="PI" <c:if test="${settSchemeDo.receiptWay == 'PI'}">selected</c:if>>等额本息</option>
										</select> &nbsp;&nbsp; 
										状态： 
										<select name="settSchemeDo.schemeStatus">
											<option value="">--请选择--</option>
											<option value="ENABLED" <c:if test="${settSchemeDo.schemeStatus == 'ENABLED'}">selected</c:if>>启用</option>
											<option value="DISABLED" <c:if test="${settSchemeDo.schemeStatus == 'DISABLED'}">selected</c:if>>禁用</option>
											<option value="PUBLISHED" <c:if test="${settSchemeDo.schemeStatus == 'PUBLISHED'}">selected</c:if>>已发布</option>
										</select> &nbsp;&nbsp; 
										<input id="btSearch" type="submit" value="查找" />&nbsp;&nbsp; 
									</td>
								</tr>
							</tbody>
						</table>
					</form>
					<div>
						<table id="help" style="width: 98%; color: #333333;">
							<tbody>
								<tr class=gvHeader>
									<th style="width: 5%;" scope="col">序号</th>
									<th style="width: 10%;" scope="col">方案代码</th>
									<th style="width: 10%;" scope="col">方案名称</th>
									<th style="width: 10%;" scope="col">还款方式</th>
									<th style="width: 10%;" scope="col">借款年利率</th>
									<th style="width: 10%;" scope="col">回款方式</th>
									<th style="width: 10%;" scope="col">结清顺延期数</th>
									<th style="width: 5%;" scope="col">方案状态</th>
									<th style="width: 10%;" scope="col">创建时间</th>
									<th style="width: 10%;" scope="col">修改时间</th>
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
										<c:forEach items="${pageDo.modelList}" var="settSchemeDo">
											<tr class="gvItem">
												<c:set var="count" value="${count+1}" />
												<td align="center" style="width: 100px;">${count}</td>
												<td>${settSchemeDo.schemeCode}</td>
												<td>${settSchemeDo.schemeName}</td>
												<td>
													<c:choose>
														<c:when test="${settSchemeDo.repayWay == 'FPIC'}">等本等息（平息）</c:when>
														<c:when test="${settSchemeDo.repayWay == 'LP'}">等额本金</c:when>
														<c:when test="${settSchemeDo.repayWay == 'IIFP'}">一次付息到期还款</c:when>
														<c:when test="${settSchemeDo.repayWay == 'MIFP'}">按月付息到期还本</c:when>
														<c:when test="${settSchemeDo.repayWay == 'PI'}">等额本息</c:when>
														<c:otherwise>--</c:otherwise>
													</c:choose>
												</td>
												<td>${settSchemeDo.defaultAnnualRate}%</td>
												<td>
													<c:choose>
														<c:when test="${settSchemeDo.receiptWay == 'FPIC'}">等本等息（平息）</c:when>
														<c:when test="${settSchemeDo.receiptWay == 'LP'}">等额本金</c:when>
														<c:when test="${settSchemeDo.receiptWay == 'IIFP'}">一次付息到期还款</c:when>
														<c:when test="${settSchemeDo.receiptWay == 'MIFP'}">按月付息到期还本</c:when>
														<c:when test="${settSchemeDo.receiptWay == 'PI'}">等额本息</c:when>
														<c:otherwise>--</c:otherwise>
													</c:choose>
												</td>
												<td>${settSchemeDo.aheadSettlePeriod}</td>
												<td>
													<c:choose>
														<c:when test="${settSchemeDo.schemeStatus == 'ENABLED'}">启用</c:when>
														<c:when test="${settSchemeDo.schemeStatus == 'DISABLED'}">禁用</c:when>
														<c:when test="${settSchemeDo.schemeStatus == 'PUBLISHED'}">已发布</c:when>
														<c:otherwise>--</c:otherwise>
													</c:choose>
												</td>
												<td><fmt:formatDate value="${settSchemeDo.createTime}" pattern="yyyy/MM/dd"/></td>
												<td><fmt:formatDate value="${settSchemeDo.updateTime}" pattern="yyyy/MM/dd"/></td>
												<td><a href="javascript:toSettSchemeDetail(${settSchemeDo.schemeId})">费用规则</a></td>
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
		window.location.href = "querySettSchemes.do?settSchemeDo.schemeCode=${settSchemeDo.schemeCode}&settSchemeDo.repayWay=${settSchemeDo.repayWay}&settSchemeDo.receiptWay=${settSchemeDo.receiptWay}&settSchemeDo.schemeStatus=${settSchemeDo.schemeStatus}&curPage="
				+ pageNo + "&pageSize=10";
	}
</script>
</html>
