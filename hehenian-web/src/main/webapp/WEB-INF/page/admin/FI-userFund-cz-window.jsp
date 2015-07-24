<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<link rel="stylesheet" href="../css/jbox/Gray/jbox.css" type="text/css"></link>
<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
<div style="width: 96%; padding: 10px;" >
	<s:iterator value="pageBean.page" var="bean" status="s">
		<div>
			<div style="float: left; width: 60%;">
				<table id="help" style="width: 100%; color: #333333;"
					cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="0">
					<tbody>
						<tr class="gvItem">
							<td style="width: 27%;" scope="col" a align="right">
								用户名:&nbsp;
							</td>
							<td>
								${bean.username}
							</td>
						</tr>
						<tr class="gvItem">
							<td style="width: 27%;" scope="col" align="right">
								真实姓名:&nbsp;
							</td>
							<td>
								${bean.realName}
							</td>
						</tr>
						<tr class="gvItem">
							<td style="width: 27%;" scope="col" align="right">
								充值类型:&nbsp;
							</td>
							<td>
								<s:if test="#bean.rechargeType==1">支付宝支付</s:if>
								<s:if test="#bean.rechargeType==2">环迅支付</s:if>
								<s:if test="#bean.rechargeType==3">国付宝</s:if>
								<s:if test="#bean.rechargeType==4">手动充值</s:if>
								<s:if test="#bean.rechargeType==6">线下充值</s:if>
								<s:if test="#bean.rechargeType==51">手工充值</s:if>
								<s:if test="#bean.rechargeType==52">虚拟充值</s:if>
								<s:if test="#bean.rechargeType==53">奖励充值</s:if>
							</td>
						</tr>
						<tr class="gvItem">
							<td style="width: 27%;" scope="col" align="right">
								流水号:&nbsp;
							</td>
							<td>
								${bean.rechargeNumber}
							</td>
						</tr>
						<tr class="gvItem">
							<td style="width: 27%;" scope="col" align="right">
								添加时间/IP:&nbsp;
							</td>
							<td>
								<s:date name="#bean.rechargeTime" format="yyyy-MM-dd HH:mm:ss"></s:date>
								/${bean.ipAddress}
							</td>
						</tr>
					</tbody>
				</table>
			</div>
			<div style="float: left; width: 40%;">
				<table id="help" style="width: 100%; color: #333333;"
					cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="0">
					<tbody>
						<tr class="gvItem">
							<td style="width: 35%;" scope="col" align="right">
								充值金额:&nbsp;
							</td>
							<td>
								${bean.rechargeMoney}
							</td>
						</tr>
						<tr class="gvItem">
							<td style="width: 35%;" scope="col" align="right">
								手续费:&nbsp;
							</td>

							<td>
								${bean.cost}
							</td>
						</tr>
						<tr class="gvItem">
							<td style="width: 35%;" scope="col" align="right">
								实际到账:&nbsp;
							</td>
							<td>
								${bean.realMoney}
							</td>
						</tr>
						<tr class="gvItem">
							<td style="width:35%;" scope="col" align="right">
								状态:&nbsp;
							</td>
							<td>
								<s:if test="#bean.result==0">失败</s:if>
								<s:if test="#bean.result==1">成功</s:if>
								<s:if test="#bean.result==2">审核中</s:if>
							</td>
						</tr>
						<tr class="gvItem">
							<td style="width:35%;" scope="col" align="right">
								审核时间:&nbsp;
							</td>
							<td>
<%--								<s:date name="#bean.checkTime" format="yyyy-MM-dd HH:mm:ss"></s:date>--%>
									--
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</s:iterator>
</div>
