<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<title>借款管理-流标</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="This is my page" />
		<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
		<link href="../css/admin/admin_custom_css.css" rel="stylesheet" type="text/css" />
	</head>
	<body>
			<div id="right">
				<div style="padding: 15px 10px 0px 10px;">
					<div>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="100" height="28" class="main_alll_h2">
									借款审核流标
								</td>
								<td width="2">
									&nbsp;
								</td>
								<td width="100" align="center"  class="white12">
								</td>
								<td>
									&nbsp;
								</td>
							</tr>
						</table>
					</div>
					 
					<div class="tab">
						<table cellspacing="1" cellpadding="3">
							<tr>
								<td class="blue12 left">
									用户名：${borrowMFlowMarkDetail.username}
								</td>
								<td class="f66 leftp200">
									真实姓名：${borrowMFlowMarkDetail.realName}
								</td>
							</tr>
							<tr>
								<td colspan="2" class="blue12 left">
									标的标题：${borrowMFlowMarkDetail.borrowTitle}
								</td>
							</tr>
							<tr>
							    <td class="blue12 left">
									借款金额：${borrowMFlowMarkDetail.borrowAmount}&nbsp;元
								</td>
								<td class="f66 leftp200">
									年利率：${borrowMFlowMarkDetail.annualRate}%
								</td>
							</tr>
							<tr>
								<td class="blue12 left">
									借款期限：${borrowMFlowMarkDetail.deadline}
									<s:if test="%{borrowMFlowMarkDetail.isDayThe ==1}">个月</s:if><s:else>天</s:else>
								</td>
								<td class="f66 leftp200">
								           借款用途：${borrowMFlowMarkDetail.purpose}
								</td>
							</tr>
							<tr>
								<td class="blue12 left">
									还款方式：
									<s:if test="%{borrowMFlowMarkDetail.paymentMode ==1}">
									   按月分期还款
									</s:if>
									<s:elseif test="%{borrowMFlowMarkDetail.paymentMode ==2}">
									 按月付息,到期还本
									</s:elseif>
									<s:elseif test="%{borrowMFlowMarkDetail.paymentMode ==3}">
									 秒还
									</s:elseif>　　
									<s:elseif test="%{borrowMFlowMarkDetail.paymentMode ==4}">
									 一次性还款
									</s:elseif>　
								</td>
								<td class="f66 leftp200">
								标的类型：
							<s:if test="%{borrowMFlowMarkDetail.borrowWay==1}">
							薪金贷
							</s:if>
							<s:elseif test="%{borrowMFlowMarkDetail.borrowWay==2}">
							生意贷
							</s:elseif>
							<s:elseif test="%{borrowMFlowMarkDetail.borrowWay==3}">
							业主贷
							</s:elseif>
							<s:elseif test="%{borrowMFlowMarkDetail.borrowWay==4}">
							实地考察借款
							</s:elseif>
							<s:elseif test="%{borrowMFlowMarkDetail.borrowWay==5}">
							机构担保借款
							</s:elseif>
							<s:elseif test="%{borrowMFlowMarkDetail.borrowWay==6}">
							流转标借款
							</s:elseif>
								</td>
							</tr>
							<s:if test="#request.subscribes==1">
									<tr>
									<td class="blue12 left">
									最小认购金额：${borrowMFlowMarkDetail.smallestFlowUnit}元
								</td>
								<td class="f66 leftp200">
                                                                                                认购总份数：${borrowMFlowMarkDetail.circulationNumber}份
								</td>
								</tr>
							</s:if>
							<s:elseif test="%{borrowMFlowMarkDetail.borrowShow==2}">
								<tr>
									<td class="blue12 left">
									最小认购金额：${borrowMFlowMarkDetail.smallestFlowUnit}元
								</td>
								<td class="f66 leftp200">
                                                                                                认购总份数：${borrowMFlowMarkDetail.circulationNumber}份
								</td>
								</tr>
							</s:elseif>
							<s:else>
									<tr>
									<td class="blue12 left">
										最低投标金额：${borrowMFlowMarkDetail.minTenderedSum}元
									</td>
									<td class="f66 leftp200">
	                                                                                                最高投标金额：
	                                    <s:if test="%{borrowMFlowMarkDetail.maxTenderedSum==''}">
								                           没有限制
								        </s:if>
								        <s:else>
								           ${borrowMFlowMarkDetail.maxTenderedSum}元                
								        </s:else>                                                            
									</td>
								</tr>
							</s:else>
							<tr>
								<td class="blue12 left">
									投标奖励:  
									<s:if test="%{borrowMFlowMarkDetail.excitationType ==2}">
									  按 固定金额,${borrowMFlowMarkDetail.excitationSum}元
									</s:if>
									<s:elseif test="%{borrowMFlowMarkDetail.excitationType ==3}">
									 按借款金额比例,${borrowMFlowMarkDetail.excitationSum}%
									</s:elseif>
									<s:else>
									   无
									</s:else>
								</td>
								<td align="left" class="f66">
								</td>
							</tr>
							<tr>
								<td class="blue12 left">
									投标密码：
									<s:if test="%{borrowMFlowMarkDetail.hasPWD ==1}">
									   有
									</s:if>
									<s:else>
									   无
									</s:else>
								</td>
								<td class="f66 leftp200">
								</td>
							</tr>
								<s:if test="%{borrowMFlowMarkDetail.borrowShow==1}">
								<tr>
								<td colspan="2" class="blue12 left">
									借款详情：${borrowMFlowMarkDetail.detail}
								</td>
								</tr>
							</s:if>
							<s:else>
									<tr>
								<td colspan="2" class="blue12 left">
									借款方商业概述：${borrowMFlowMarkDetail.businessDetail}
								</td>
							</tr>
							<tr>
								<td colspan="2" class="blue12 left">
									借款方资产情况：${borrowMFlowMarkDetail.assets}
								</td>
							</tr>
							<tr>
								<td colspan="2" class="blue12 left">
									借款方资金用途：${borrowMFlowMarkDetail.moneyPurposes}
								</td>
							</tr>
							</s:else>
							<tr>
								<td colspan="2" class="blue12 left">
									添加时间：${borrowMFlowMarkDetail.publishTime}/IP: ${borrowMFlowMarkDetail.publishIP}
								</td>
							</tr>
							<tr>
								<td colspan="2" class="blue12 left">
									借款人认证审核查看：<a href="queryPerUserCreditAction.do?userId=${borrowMFlowMarkDetail.userId}" target="_blank">申请人认证详情查看</a>
								</td>
							</tr>
							</table>
							<div id="content">
							<table>
							<tr>
							    <td colspan="2" class="blue12 left">
									风控意见：
									<br/><s:textarea cssStyle="margin-left:80px;" name="paramMap.auditOpinion" value="%{borrowMFlowMarkDetail.auditOpinion}" cols="30" rows="10"></s:textarea>
								</td>
							</tr>
						</table>
							</div>
						<br />
					</div>
				</div>
			</div>
<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
	</body>	
</html>