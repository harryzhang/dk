<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="This is my page" />
		<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
		<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
</head>
	<body>
	<form id="addAdmin" action="addAdmin.do" method="post">
			<div id="right">
				<div style="padding: 15px 10px 0px 10px;">
					<div>
						<table border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="100" height="28" class="xxk_all_a">
									<a href="queryRiskInit.do">风险保障金记录</a>
								</td>
								<td width="2">
									&nbsp;
								</td>
								<td width="100" class="main_alll_h2">
									风险保障金详情
								</td>
								<td>
									&nbsp;
								</td>
							</tr>
						</table>
					</div>
					 
					<div style="width: auto; background-color: #FFF; padding: 10px;">
						<table width="100%" border="0" cellspacing="1" cellpadding="3">
							<tr>
								<td style="height: 25px;" align="right" class="blue12">
									操作金额：
								</td>
								<td align="left" class="f66">
									${riskDetailMap.amount}&nbsp;元
								</td>

							</tr>
							<tr>
								<td style="width: 100px; height: 25px;" align="right"
									class="blue12">
									保障金余额：
								</td>
								<td align="left" class="f66">
									${riskDetailMap.riskBalance}&nbsp;元
								</td>
							</tr>
							<tr>
								<td style="height: 25px;" align="right" class="blue12">
									类型：
								</td>
								<td align="left" class="f66">
								    ${riskDetailMap.riskType}
								</td>
							</tr>
							<tr>
								<td style="height: 25px;" align="right" class="blue12">
									交易对方：
								</td>
								<td align="left" class="f66">
								   ${riskDetailMap.trader}
								</td>
							</tr>
							<tr>
								<td style="width: 100px; height: 25px;" align="right"
									class="blue12">
									详细来源：
								</td>
								<td align="left" class="f66">
								    ${riskDetailMap.resource}
								</td>
							</tr>
							<tr>
								<td style="width: 100px; height: 25px;" align="right"
									class="blue12">
									借款标题：
								</td>
								<td align="left" class="f66">
								    <a href="http://financeDetail.do?id=${riskDetailMap.borrowId}" target="_blank">${riskDetailMap.borrowTitle}</a>
								</td>
							</tr>
							<tr>
								<td style="width: 100px; height: 25px;" align="right"
									class="blue12">
									操作时间：
								</td>
								<td align="left" class="f66">
								    ${riskDetailMap.riskDate}
								</td>
							</tr>
							<tr>
								<td style="width: 100px; height: 25px;" align="right"
									class="blue12">
									操作人员：
								</td>
								<td align="left" class="f66">
								    ${riskDetailMap.operator}
								</td>
							</tr>
							<tr>
								<td style="width: 100px; height: 25px;" align="right"
									class="blue12">
									备注：
								</td>
								<td align="left" class="f66">
								    <textarea rows="10" cols="30">${riskDetailMap.remark}</textarea>
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
