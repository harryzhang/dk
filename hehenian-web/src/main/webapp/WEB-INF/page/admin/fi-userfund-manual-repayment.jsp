<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
<script type="text/javascript" src="../common/dialog/popup.js"></script>
<script type="text/javascript"
	src="../script/jbox/jquery.jBox-2.3.min.js"></script>
<link href="../css/jbox/Gray/jbox.css" rel="stylesheet" type="text/css" />
<script language="javascript" type="text/javascript">

	function checkCompFee(ordId){
		var fee603 = $("#fee603_" + ordId).val();
		
		var fee902 = $("#fee902_" + ordId).val();
		var fee901 = $("#fee901_" + ordId).val();
		var fee903 = $("#fee903_" + ordId).val();
		
		if(fee603 != "" || fee902 != "" || fee901 != "" || fee903 != ""   ){
			alert("代偿的时候不允许输入费用")
		
			$("#fee603_" + ordId).val("");
			$("#fee902_" + ordId).val("");
			$("#fee901_" + ordId).val("");
			$("#fee903_" + ordId).val("");
			return false;
		}
		return true;
	}

	//提交
	function manualRepayment(repaymentId, ordId, operateType) {
		var msg = "确认要还款?";
		if (operateType == 'COMP_REPAY') {
			msg = "确认要代偿?";
			if(!checkCompFee(ordId)){
				return false;
			}
		}
		
		
		if (confirm(msg)) {
			param["paramMap.repaymentId"] = repaymentId;
			param["paramMap.ordId"] = ordId;
			param["paramMap.fee603"] = $("#fee603_" + ordId).val();
			param["paramMap.fee902"] = $("#fee902_" + ordId).val();
			param["paramMap.fee901"] = $("#fee901_" + ordId).val();
			param["paramMap.fee903"] = $("#fee903_" + ordId).val();
			param["paramMap.realPrincipal"] = $("#realPrincipal_" + ordId)
					.val();
			param["paramMap.realInterest"] = $("#realInterest_" + ordId).val();
			param["paramMap.operateType"] = operateType;

			var url = "manualRepayment.do?shoveDate" + new Date().getTime();
			$.jBox
					.tip("<div class='data-submit'>提交数据中，请稍候...</div>",
							"loading");
			$.post(url, param, function(data) {
				$.jBox.closeTip();
				var callBack = data.msg;
				if (callBack == "1") {
					alert("还款成功!");
					$("#btn_repay_" + ordId).attr('disabled', "disabled");
					$("#btn_comp_" + ordId).attr('disabled', "disabled");
				} else {
					alert(data.msg);
					return false;
				}
			});
		}
	}
</script>
<br/>
<br/>
<div align="center"><span><b>标的详情</b></span></div>
<table id="help" style="width: 80%; color: #333333;" cellspacing="1"
	cellpadding="3" bgcolor="#dee7ef" border="0">
	<tbody>
		<s:if test="#request.repaymentContext != null">
			<tr class="gvItem">
				<td nowrap="nowrap" align="right">借款人用户名</td>
				<td align="left">${repaymentContext.userDo.person.realName}</td>
				<td nowrap="nowrap" align="right">身份证号码</td>
				<td align="left">${repaymentContext.userDo.person.idNo}</td>
				<td nowrap="nowrap" align="right">借款编号</td>
				<td align="left">${repaymentContext.borrow.number}</td>
			</tr>
			<tr class="gvItem">
				<td nowrap="nowrap" align="right">放款金额</td>
				<td align="left">
				    <fmt:formatNumber value="${repaymentContext.borrow.borrowAmount}" pattern="##,###,###,###,##0.0#" minFractionDigits="2" />
				</td>
				<td nowrap="nowrap" align="right">放款期限</td>
				<td align="left">${repaymentContext.borrow.deadline}</td>
				<td nowrap="nowrap" align="right">借款利率</td>
				<td align="left">
					<fmt:formatNumber value="${repaymentContext.borrow.annualRate}" pattern="##,###,###,###,##0.0#" minFractionDigits="2" />
				</td>
			</tr>
			<tr class="gvItem">
				<td nowrap="nowrap" align="right">已还期数</td>
				<td align="left">${repaymentContext.borrow.hasDeadline}</td>
				<td nowrap="nowrap" align="right">当期期数</td>
				<td align="left">${repaymentContext.repaymentDo.repayPeriod}</td>
				<td nowrap="nowrap" align="right">剩余本金</td>
				<td align="left">
					<fmt:formatNumber value="${repaymentContext.repaymentDo.principalBalance}" pattern="##,###,###,###,##0.0#" minFractionDigits="2" />
				</td>
			</tr>
			<tr class="gvItem">
				<td nowrap="nowrap" colspan="6" style="align: left">投资人</td>
			</tr>
			<tr class="gvItem">
				<td nowrap="nowrap" align="right">当期应收本金</td>
				<td align="left">
					<fmt:formatNumber value="${totalPrincipal}" pattern="##,###,###,###,##0.0#" minFractionDigits="2" />
				</td>
				<td nowrap="nowrap" align="right">当期应收利息</td>
				<td align="left">
					<fmt:formatNumber value="${totalInterest}" pattern="##,###,###,###,##0.0#" minFractionDigits="2" />
				</td>
				<td align="left"></td>
				<td align="left"></td>
			</tr>
			<tr class="gvItem">
				<td nowrap="nowrap" colspan="6" style="align: left">深圳合和年咨询账户</td>
			</tr>
			<tr class="gvItem">
				<td nowrap="nowrap" align="right">深圳合和年咨询费</td>
				<td align="left"><s:if test="#request.feeConsult != null">
							<fmt:formatNumber value="${feeConsult}" pattern="##,###,###,###,##0.0#" minFractionDigits="2" />
						</s:if>
				</td>
				<td nowrap="nowrap" align="right">罚息</td>
				<td align="left">
						<s:if test="#request.feeFx != null">
							<fmt:formatNumber value="${feeFx}" pattern="##,###,###,###,##0.0#" minFractionDigits="2" />
						</s:if>
						<s:else>
						  0.00
						</s:else>
						</td>
				<td></td>
				<td></td>
			</tr>
		</s:if>
	</tbody>
</table>
<br>
<br>
<div align="center"><span><b>投资明细表</b></span></div>
<table id="manual-repayment" style="width: 80%; color: #333333;"
	cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="0">
	<tbody>
		<tr class=gvHeader>
			<th nowrap="nowrap">操作</th>
			<th nowrap="nowrap">还款子订单</th>
			<th nowrap="nowrap">还款订单号</th>
			<th nowrap="nowrap">应收本金</th>
			<th nowrap="nowrap">应收利息</th>
			<th nowrap="nowrap">实收本金</th>
			<th nowrap="nowrap">实收利息</th>
			<th nowrap="nowrap">收款人</th>
			<th nowrap="nowrap">是否代偿</th>
			<th nowrap="nowrap">还款期数</th>
			<th></th>
		</tr>
		<s:if test="#request.repaymentContext != null">
			<s:if test="#request.repaymentContext.investList != null">
				<s:iterator value="#request.repaymentContext.investList" var="bean"
					status="s">
					<tr class="gvItem">
						<td><s:if test="#bean.investRepaymentDO.repayStatus==1">
								<input type="button" value="还款"
									id="btn_repay_${bean.investRepaymentDO.repayId}"
									onclick="manualRepayment('${bean.investRepaymentDO.repayId}','${bean.investRepaymentDO.id}', 'NORMAL_REPAY')" />
								<input type="button" value="代偿"
									id="btn_comp_${bean.investRepaymentDO.repayId}"
									onclick="manualRepayment('${bean.investRepaymentDO.repayId}','${bean.investRepaymentDO.id}', 'COMP_REPAY')" />
							</s:if></td>
						<td>${bean.investRepaymentDO.investDo.subOrdId}</td>
						<td>${bean.investRepaymentDO.id}</td>
						<td>
							<fmt:formatNumber value="${bean.investRepaymentDO.recivedPrincipal}" pattern="##,###,###,###,##0.0#" minFractionDigits="2" />
						</td>
						<td>
							<fmt:formatNumber value="${bean.investRepaymentDO.recivedInterest}" pattern="##,###,###,###,##0.0#" minFractionDigits="2" />
						</td>
						<td><input name="realPrincipal_${bean.investRepaymentDO.id}"
							id="realPrincipal_${bean.investRepaymentDO.id}" value="" /></td>
						<td><input name="realInterest_${bean.investRepaymentDO.id}"
							id="realInterest_${bean.investRepaymentDO.id}" value="" /></td>
						<td>${bean.investRepaymentDO.ownerUser.username}</td>
						<td><s:if test="#bean.investRepaymentDO.isWebRepay ==1 ">
						   		未代偿
						   </s:if> <s:else>
						                   已 代偿
						   </s:else></td>
						<td>${bean.investRepaymentDO.repayPeriod}</td>
						<td>罚息:<input name="fee603_${bean.investRepaymentDO.id}"
							id="fee603_${bean.investRepaymentDO.id}" value="" /> <br /> 咨询费:<input
							name="fee902_${bean.investRepaymentDO.id}"
							id="fee902_${bean.investRepaymentDO.id}" value="" /> <br />
							提前结清手续费：<input name="fee901_${bean.investRepaymentDO.id}"
							id="fee901_${bean.investRepaymentDO.id}" value="" /> <br />
							放款手续费：<input name="fee903_${bean.investRepaymentDO.id}"
							id="fee903_${bean.investRepaymentDO.id}" value="" />
						</td>
					</tr>
				</s:iterator>
			</s:if>
		</s:if>
	</tbody>
</table>

