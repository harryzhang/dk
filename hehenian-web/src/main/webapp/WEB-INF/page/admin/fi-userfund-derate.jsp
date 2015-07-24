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
	//提交减免费用
	function submitDerateFee() {
		if (confirm("确认要减免这些费用?")) {
			if (checkParaRemark()) {
				alert("备注不可以为空");
				return false;
			}

			if (checkParaAmount()) {
				alert("金额不可以为空");
				return false;
			}

			var param = $('#derateForm').serialize();
			var url = "derateFee.do?shoveDate" + new Date().getTime();
			$.jBox
					.tip("<div class='data-submit'>提交数据中，请稍候...</div>",
							"loading");
			$.post(url, param, function(data) {
				$.jBox.closeTip();
				var callBack = data.msg;
				if (callBack == "1") {
					alert("减免费用保存成功!");
					window.location.reload();
				} else {
					alert(data.msg);
					return false;
				}
			});
		}
	}

	function checkParaRemark() {
		var retVal = false;
		var remarkArray = $("input[type='text'][name='remark']");
		$(remarkArray).each(function(index, element) {
			if (index != 0) {
				if ($(this).val() == "") {
					retVal = true;
					return false;
				}
			}
		});
		return retVal;
	}

	function checkParaAmount() {
		var retVal = false;
		var amountArray = $("input[type='text'][name='amount']");
		$(amountArray).each(function(index, element) {
			if (index != 0) {
				if ($(this).val() == "") {
					retVal = true;
					return false;
				}
			}
		});
		return retVal;
	}

	//addDerateFeeItem减免费用
	function addDerateFeeItem() {
		var trhtml = $('#emptyRow').prop('outerHTML');
		var newtr = $(trhtml);
		var table1 = $("#feetab");
		$(newtr).css('display', 'block');
		table1.append(newtr);
	}

	function removeDerateFeeItem() {
		var checkArray = $("input[type='checkbox'][name='counttr']");
		$(checkArray).each(function() {
			if ($(this).attr("checked") == "checked") {
				$(this).parent().parent().remove();
			}
		});
	}
</script>
<br/>
<div> <b>减免标的详情：</b></div>
<table id="help" style="width: 70%; color: #333333;" cellspacing="1"
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
				<td nowrap="nowrap"  colspan="6" style="align:left">投资人</td>
			</tr>
			<tr class="gvItem">

				<td nowrap="nowrap" align="right">当期剩余本金</td>
				<td align="left">
					<fmt:formatNumber value="${totalPrincipal}" pattern="##,###,###,###,##0.0#" minFractionDigits="2" />
				</td>
				<td nowrap="nowrap" align="right">当期利息</td>
				<td align="left">
				<fmt:formatNumber value="${totalInterest}" pattern="##,###,###,###,##0.0#" minFractionDigits="2" />
				</td>
				<td align="left"></td><td align="left"></td>

			</tr>
			<tr class="gvItem">
				<td nowrap="nowrap"  colspan="6" style="align:left">深圳合和年咨询账户</td>
			</tr>
			<tr class="gvItem">
				<td nowrap="nowrap" align="right">深圳合和年咨询费</td>
				<td align="left"><s:if test="#request.feeConsult != null">
							<fmt:formatNumber value="${feeConsult}" pattern="##,###,###,###,##0.0#" minFractionDigits="2" />
						</s:if></td>
				<td nowrap="nowrap" align="right">罚息</td>
				<td align="left">
						<s:if test="#request.feeFx != null">
							<fmt:formatNumber value="${feeFx}" pattern="##,###,###,###,##0.0#" minFractionDigits="2" />
						</s:if>
						<s:else>
							0.00
						</s:else>
				</td>
				<td nowrap="nowrap" align="right">放款手续费</td>
				<td align="left">
					<s:if test="#request.feeServiceCharge != null">
							<fmt:formatNumber value="${feeServiceCharge}" pattern="##,###,###,###,##0.0#" minFractionDigits="2" />
						</s:if>
						<s:else>
							0.00
						</s:else>
				</td>
			</tr>
			
			<tr class="gvItem">
				<td nowrap="nowrap" align="right">提前结清手续费</td>
				<td align="left">
						<s:if test="#request.feePre != null">
							<fmt:formatNumber value="${feePre}" pattern="##,###,###,###,##0.0#" minFractionDigits="2" />
						</s:if>
						<s:else>
							0.00
						</s:else>
				</td>
				<td nowrap="nowrap" align="right"></td>
				<td align="left"></td>
				<td nowrap="nowrap" align="right"></td>
				<td align="left"></td>
			</tr>
		</s:if>
	</tbody>
</table>
<br/>
<br/>
<div style="width: 100%;">
	<input type="button" value="新增减免费用" onclick="addDerateFeeItem()" /> <input
		type="button" value="删除减免费用" onclick="removeDerateFeeItem()" /> <input
		type="button" value="提交减免" onclick="submitDerateFee()" />
	<form action="derate" id="derateForm">
		
		<table id="feetab" color: #333333;" cellspacing="1" cellpadding="3"
			bgcolor="#dee7ef">
			<tbody>
				<tr class=gvHeader>
					<th></th>
					<th nowrap="nowrap">费用</th>
					<th nowrap="nowrap">金额</th>
					<th nowrap="nowrap">减免的理由</th>
				</tr>
				<tr id="emptyRow" style="display: none">
					<td><input type="checkbox" name="counttr" /></td>
					<td><select id="feecode" name="feecode">
							<option value="901">提前结清手续费</option>
							<option value="902">咨询费</option>
							<option value="903">放款手续费</option>
							<option value="603">罚息</option>
					</select></td>
					<td><input type="text" name="amount" id="amount" value="" /></td>
					<td>
						<select id="remark" name="remark">
							<option value="200">借款人骗贷</option>
							<option value="201">借款人跑路</option>
							<option value="202">借款人触犯法律无法履行还款</option>
							<option value="203">借款人经营不善</option>
							<option value="204">借款人收入有限</option>
							<option value="205">操作导致数据异常</option>
							<option value="207">错过还款日期</option>
							<option value="206">其他</option>
					</select>
					</td>
				</tr>
				<tr>
					<td>减免期数</td>
					<td colspan="2">
						<s:select name="repaymentId"  id="repaymentId" list="#request.periodMap" listKey="key" listValue="value"   value="#request.repaymentId" />
					</td>
				</tr>
			</tbody>
		</table>
	</form>
</div>