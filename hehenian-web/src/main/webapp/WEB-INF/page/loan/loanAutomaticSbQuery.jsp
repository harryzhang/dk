<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="/include/taglib.jsp"%>
<html>
<head>
<title>自动上标</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
<meta http-equiv="description" content="This is my page" />
<%@ include file="/include/includeJs.jsp"%>
<script language="javascript" type="text/javascript" src="../My97DatePicker/WdatePicker.js"></script>
<script>
/**
 * 校验手机
 * @param string 被校验的字符串
 */
function checkMobile(obj) {
     if(!(/^1[\d]{10}$/).test(obj.value)) {
    	 alert("请录入正确的手机号码！");
    	 obj.value="";
     }
}
</script>
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
						<td width="100" height="28" class="main_alll_h2"><a href="loanAutoQuery.do">自动上标</a></td>
						<td width="2">&nbsp;</td>
						<td>&nbsp;</td>
					</tr>
				</table>
				<div
					style="padding-right: 10px; padding-left: 10px; padding-bottom: 10px; width: 95%; padding-top: 10px; background-color: #fff;">
					<form action="loanAutoQuery.do" method="post">
						<table style="margin-bottom: 8px;" cellspacing="0" cellpadding="0" width="100%" border="0" class="bigBoss">
						<tbody>
							<tr>
								<td class="f66" align="left" width="80%" height="36px">&nbsp;&nbsp; 用户名 ： <input id="username"
									name="loanPersonDo.realName" value="${loanPersonDo.realName }" type="text" />&nbsp;&nbsp; 
									手机号码 ： <input id="mobile"
									name="loanPersonDo.mobile" value="${loanPersonDo.mobile}" type="text"  onchange="checkMobile(this)" />
									<input id="btSearch" type="submit" value="查找" style="width: 60px;" />
								</td>
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
										<th scope="col">借款编号</th>
										<th scope="col">客户姓名</th>
										<th scope="col">标的类型</th>
										<th scope="col">借款标题</th>
										<th scope="col">借款金额</th>
										<th scope="col">利率</th>
										<th scope="col">期限</th>
										<th scope="col">筹标期限</th>
										<th scope="col">操作</th>
									</tr>
									<c:choose>
										<c:when test="${pageDo.modelList == null}">
											<tr align="center" class="gvItem">
												<td colspan="14">暂无数据</td>
											</tr>
										</c:when>
										<c:otherwise>
											<c:set var="count" value="0" />
											<c:forEach items="${pageDo.modelList}" var="loanPersonDo">
													<tr class="gvItem">
														<c:set var="count" value="${count+1}" />
														<td align="center" style="width: 100px;">${count}<input type="hidden" class="loanPersonId" value="${loanPersonDo.loanPersonId}" /> </td>
														<td>
															${loanPersonDo.loanDo.loanId} 
														</td>
														<td>
															${loanPersonDo.realName} 
														</td>
														<td>
															集团员工贷5
														</td>
														<td >
															${loanPersonDo.loanDo.loanUsage}
														</td>
														<td>
															<fmt:formatNumber value="${loanPersonDo.loanDo.applyAmount}" pattern="##.##" minFractionDigits="2" />
														</td>
														<td>
															<c:if test="${loanPersonDo.loanDo.loanPeriod == '1' }">
																7.8%
															</c:if>
															<c:if test="${loanPersonDo.loanDo.loanPeriod == '3' }">
																8.4%
															</c:if>
															<c:if test="${loanPersonDo.loanDo.loanPeriod == '6' }">
																9%
															</c:if>
															<c:if test="${loanPersonDo.loanDo.loanPeriod == '12' }">
																10%
															</c:if>
														</td>
														<td>
															${loanPersonDo.loanDo.loanPeriod}
														</td>
														<td>
															5天
														</td>
														<td>
															
															<c:if test="${loanPersonDo.loanDo.loanStatus != 'SUBJECTED' }">
																<a href="javascript:queryRecheckById(${loanPersonDo.loanDo.loanId})">通过</a>
															</c:if>
															<a href="javascript:queryById(${loanPersonDo.loanDo.loanId})">查看</a>
														</td>
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
		window.location.href = "loanAutoQuery.do?loanPersonDo.realName=${loanPersonDo.realName}&loanPersonDo.mobile=${loanPersonDo.mobile}&curPage="
				+ pageNo + "&pageSize=10";
	}
	
	//受理
	function queryRecheckById(id) {
		$.getJSON("loanAutoSubject.do",{'loanPersonDo.loanId':id} ,function(data) {
			if (data.flag == 1) {
				alert("自动上标成功！");
				window.location.reload();
			} else {
				alert("自动上标失败！"+data.ret);
			}
		});
	}
	//查看
function queryById(id) {
		$.jBox("iframe:loanQuery.do?id=" + id, {
			title : "业务受理",
			width : 600,
			height : 580,
			top : 25,
			buttons : {

			}
		});
	}
</script>
</html>
