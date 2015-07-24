<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<link href="../css/jbox/Gray/jbox.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
	//提前还款
	function doPreRepay(borrowId,payId,userId,usrCustId){
		if (confirm("确认提前还款?")) {
			param["paramMap.borrowId"] = borrowId;
			param["paramMap.usrCustId"] = usrCustId;
			param["paramMap.userId"] = userId;
			param["paramMap.payId"] = payId;
			$.jBox.tip("<div class='data-submit'>提交数据中，请稍候...</div>","loading");
	 		$.shovePost("updatePreRepayment.do",param,function(data) {
	 			var callBack = data.msg;
				if (callBack == "1") {
					alert("还款成功!");
					window.location.reload();
				}else {
					alert(data.msg);
					$.jBox.closeTip();
					return false;
				}
			});
		}
	}
</script>
<div style="width: 70%;">
    
	<table id="help" style="width: 100%; color: #333333;" cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="0">
		<tbody>
			
			<s:if test="#request.repaymentContext != null">
			<tr class="gvItem">
					<td align="right">借款人用户名</td><td  align="left">${repaymentContext.userDo.person.realName}</td>
					<td nowrap="nowrap"     align="right">身份证号码</td><td  align="left">${repaymentContext.userDo.person.idNo}</td>
					<td nowrap="nowrap"     align="right">借款编号</td><td  align="left">${repaymentContext.borrow.number}</td>
			</tr>					
			<tr class="gvItem">
					<td nowrap="nowrap"     align="right">放款金额</td>
					<td  align="left">
						<fmt:formatNumber value="${repaymentContext.borrow.borrowAmount}" pattern="##,###,###,###,##0.0#" minFractionDigits="2" />
					</td>
					<td nowrap="nowrap"     align="right">放款期限</td><td  align="left">${repaymentContext.borrow.deadline}</td>
					<td nowrap="nowrap"     align="right">放款时间</td>
					<td  align="left">
						<fmt:formatDate value="${repaymentContext.borrow.auditTime}" type="date"/>
					     
					</td>
			</tr>					
			<tr class="gvItem">
					<td nowrap="nowrap"     align="right">借款利率</td>
					<td  align="left">
					   <fmt:formatNumber value="${repaymentContext.borrow.annualRate}" pattern="##,###,###,###,##0.0#" minFractionDigits="2" />
					</td>
					<td nowrap="nowrap"     align="right">提前结清日期</td><td  align="left">${request.preDate}</td>
					<td nowrap="nowrap"     align="right">已还期数</td><td  align="left">${repaymentContext.borrow.hasDeadline}</td>
			
			</tr>					
			<tr class="gvItem">
					<td nowrap="nowrap"     align="right">当期期数</td>
					<td  align="left">
						${repaymentContext.repaymentDo.repayPeriod}
					</td>
					<td nowrap="nowrap"     align="right">当期剩余本金</td>
					<td  align="left">
						<fmt:formatNumber value="${repaymentContext.repaymentDo.principalBalance}" pattern="##,###,###,###,##0.0#" minFractionDigits="2" />
					</td>
					<td></td><td></td>
			</tr>					
			<tr class="gvItem">
					<td nowrap="nowrap"     colspan="6" >投资人</td>
			</tr>					
			<tr class="gvItem">
					<td nowrap="nowrap"     align="right">总计应收剩余本金</td>
					<td  align="left">
						<fmt:formatNumber value="${totalPrincipal}" pattern="##,###,###,###,##0.0#" minFractionDigits="2" />
					</td>
					<td nowrap="nowrap"     align="right">总计应收利息</td>
					<td  align="left">
						<fmt:formatNumber value="${totalInterest}" pattern="##,###,###,###,##0.0#" minFractionDigits="2" />
					</td>
					<td></td><td></td>
			</tr>					
			<tr class="gvItem">
					<td nowrap="nowrap"      colspan="6" style="align:left;">深圳合和年咨询账户</td>
			</tr>					
			<tr class="gvItem">
					<td nowrap="nowrap"     align="right">提前结清服务费</td>
					<td  align="left">
					   <s:if test="#request.feePre != null">
							<fmt:formatNumber value="${feePre}" pattern="##,###,###,###,##0.0#" minFractionDigits="2" />
						</s:if>
					</td>
					<td nowrap="nowrap"     align="right">深圳合和年咨询费</td>
					<td  align="left">
						<s:if test="#request.feeConsult != null">
							<fmt:formatNumber value="${feeConsult}" pattern="##,###,###,###,##0.0#" minFractionDigits="2" />
						</s:if>
                    </td>
                    <td nowrap="nowrap"     align="right">罚息</td>
					<td  align="left">
						<s:if test="#request.feeFx != null">
							<fmt:formatNumber value="${feeFx}" pattern="##,###,###,###,##0.0#" minFractionDigits="2" />
						</s:if>
						<s:else>
							0.00
						</s:else>
					</td>
			</tr>
			<tr class="gvItem">
				<td nowrap="nowrap" align="right">放款手续费</td>
				<td align="left">
					<s:if test="#request.feeServiceCharge != null">
							<fmt:formatNumber value="${feeServiceCharge}" pattern="##,###,###,###,##0.0#" minFractionDigits="2" />
						</s:if>
						<s:else>
							0.00
						</s:else>
				</td>
				<td></td><td></td>
				<td></td><td></td>
			</tr>					
			<tr class="gvItem">		
					<td nowrap="nowrap"     colspan="6" style="align:left;">深圳合和年代偿账户</td>
			</tr>					
			<tr class="gvItem">			
					<td nowrap="nowrap"     align="right" >上期代偿费用</td>
					<td colspan="5" align="left">
						<fmt:formatNumber value="${request.compAmount}" pattern="##,###,###,###,##0.0#" minFractionDigits="2" />
					</td>
			</tr>					
			<tr class="gvItem">	
					<td nowrap="nowrap"     align="right" >合计</td>
					<td colspan="5" align="left">
						<fmt:formatNumber value="${request.totalAmount}" pattern="##,###,###,###,##0.0#" minFractionDigits="2" />
					</td>
		    </tr>
		    
		    
		    </s:if>
		    <tr class="gvItem">
		    	<td td nowrap="nowrap" colspan="6">
		    		<input type="button" value="提前结清" onclick="doPreRepay(${paramMap.borrowId},${paramMap.payId},${paramMap.userId},${paramMap.usrCustId})">
		    	</td>
		    </tr>
		</tbody>
	</table>
</div>
