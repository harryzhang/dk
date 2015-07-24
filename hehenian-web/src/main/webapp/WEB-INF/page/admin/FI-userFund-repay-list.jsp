<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
<script type="text/javascript" src="../common/dialog/popup.js"></script>
<script type="text/javascript" src="../script/jbox/jquery.jBox-2.3.min.js"></script>
<link href="../css/jbox/Gray/jbox.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
	$(function() {
		param["pageBean.pageNum"] = 1;
		$("#checkAll").click(function() {
			$("input[name='userCheckbox']").each(function() {
				this.checked = true;
			});
		});
		
		$("#reverse").click(function() {
			$("input[name='userCheckbox']").each(function(idx, item) {
				$(item).attr("checked", !$(item).attr("checked"));
			});
		});
		$("#repayForCheck").click(function() {
			if (confirm("确认为当前用户还款?")) {
				var repaymentId="";
				var hasDeadline="";
				var stillAmount="";
				var borrowId="";
				$("input[name='userCheckbox']").each(function(){
					if(this.checked== true ){
						repaymentId+=this.value+",";
						stillAmount+=$.trim($(this).parent().siblings(".still").html())+",";
						hasDeadline+=$(this).siblings(".has").val()+",";
						borrowId+=$(this).siblings(".borrowId").val()+",";
					}
				});
				repaymentId=repaymentId.substring(0,repaymentId.length-1);
				hasDeadline=hasDeadline.substring(0,hasDeadline.length-1);
				stillAmount=stillAmount.substring(0,stillAmount.length-1);
				borrowId=borrowId.substring(0,borrowId.length-1);
				repayForUser(repaymentId,hasDeadline,stillAmount,borrowId);
			}
		});
	});
	
	//重复请求
	function changeInvestRepaymentId(repaymentId){
		if (confirm("确认修复重复请求?")) {
			param["paramMap.repaymentId"] = repaymentId;
			$.jBox.tip("<div class='data-submit'>提交数据中，请稍候...</div>","loading");
	 		$.shovePost("changeInvestRepaymentId.do",param,function(data) {
	 			$.jBox.closeTip();
	 			var callBack = data.msg;
				if (callBack == "1") {
					alert("重复请求已经修复，请重新还款!");
					parent.location.reload();
				}else {
					alert(data.msg);
					return false;
				}
			});
		}
	}

	//还款
	function repay(repaymentId,hasDeadline,stillAmount,borrowId,usrCustId,hasFI,stillInterest,stillAmount,userId){
		if (confirm("确认为当前用户还款?")) {
			param["paramMap.repaymentId"] = repaymentId;
			param["paramMap.hasDeadline"] = hasDeadline;
			param["paramMap.stillAmount"] = stillAmount;
			param["paramMap.borrowId"] = borrowId;
			param["paramMap.usrCustId"] = usrCustId;
			param["paramMap.hasFI"] = hasFI;		//剩余本金
			param["paramMap.stillInterest"] = stillInterest;		//本期应换总额
			param["paramMap.stillAmount"] = stillAmount;		//本期应换总额
			param["paramMap.repayDate"] = $("#nextDate_" + borrowId).val();
			param["paramMap.userId"] = userId;
			param["paramMap.userName"] = $("#userName").val();
			param["paramMap.realName"] = $("#realName").val();
			param["paramMap.deadline"] = $("#deadline").val();
			$.jBox.tip("<div class='data-submit'>提交数据中，请稍候...</div>","loading");
	 		$.shovePost("updateRepayment.do",param,function(data) {
	 			var callBack = data.msg;
				if (callBack == "ok") {
					alert("还款成功!");
					window.location.reload();
				}else if(data == "1")
				{
					alert("当前用户已经还完所有期数借款,还款失败");
					$.jBox.closeTip();
					return false;
				}
				else {
					alert(data.msg);
					$.jBox.closeTip();
					return false;
				}
			});
		}
	}
	
	//提前还款
	function preRepay(borrowId,payId,userId,usrCustId){
			param["paramMap.borrowId"] = borrowId;
			param["paramMap.usrCustId"] = usrCustId;
			param["paramMap.userId"] = userId;
			param["paramMap.payId"] = payId;
			$.jBox.tip("<div class='data-submit'>提交数据中，请稍候...</div>","loading");
	 		$.shovePost("preRepaymentConfirm.do",param,function(data) {
	 			$.jBox.closeTip();
	 			$("#right").html(data);
			});
	}
	
	function initCallBack(data) {
		$("#dataInfo").html(data);
	}

</script>
<div>
	<div></div>
	<table id="help" style="width: 100%; color: #333333;" cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="0">
		<tbody>
			<tr class=gvHeader>
				<th scope="col">借款编号</th>
				<th scope="col">借款人</th>
				<th scope="col">借款标题</th>
				<th scope="col">借款总额</th>
				<th scope="col">期数</th>
				<th scope="col">利率</th>
				<th scope="col">起息时间</th>
				<th scope="col">到期时间</th>
				<th scope="col">下一还款日</th>
				<th scope="col">本期还款金额</th>
				<th scope="col" colspan="2">操作</th>
			</tr>
			<s:if test="pageBean.page==null || pageBean.page.size==0">
				<tr align="center" class="gvItem">
					<td colspan="14">暂无数据</td>
				</tr>
			</s:if>
			<s:else>
				<s:set name="counts" value="#request.pageNum" />
				<s:iterator value="pageBean.page" var="bean" status="s">
					<tr class="gvItem">
						<td align="center" style="width: 100px;" class="borrow"><input type="hidden" class="borrowId" value="${bean.borrowId}" /> ${bean.number }</td>
						<td>
							${bean.realName} <input id="userName" value="${bean.username}" type="hidden"> <input id="realName" value="${bean.realName}" type="hidden">
						</td>
						<td>${bean.borrowTitle}</td>
						<td>${bean.borrowAmount }</td>
						<td>
							<s:if test="#bean.borrowStatus==5">
								--
							</s:if><s:else>
								${bean.repayPeriod }
							</s:else>
						</td>
						<td>${bean.annualRate}%</td>
						<td>${bean.fisrtDate}</td>
						<td>${bean.lastDate}</td>
						<td><s:if test="#bean.nextDate==null">${bean.lastDate}</s:if> <s:else>${bean.nextDate}</s:else> <input id="nextDate_${bean.borrowId}" value="${bean.nextDate}"
							type="hidden">
						</td>
						<td>
							<s:if test="#bean.borrowStatus==5">
								--
							</s:if><s:else>
								${bean.stillAmount}
							</s:else>
						</td>
						<td nowrap="nowrap"><a href="queryRepaymentReportInit.do?borrowId=${bean.borrowId}">查看记录</a>&nbsp;</td>
						<td>
						<s:if test="#bean.borrowStatus==4">
								<span
									onclick="repay(${bean.repaymentId},${bean.hasDeadline},${bean.stillAmount},${bean.borrowId},${bean.usrCustId},${bean.hasFI},${bean.stillInterest},${bean.stillAmount},${bean.userId})"
									style="cursor: pointer">还款</span> &nbsp; 
								<span onclick="preRepay(${bean.borrowId},${bean.repaymentId},${bean.userId},${bean.usrCustId})" style="cursor: pointer">提前结清</span>
								<a href="derateFeeInit.do?repaymentId=${bean.repaymentId}&borrowId=${bean.borrowId}&userId=${bean.userId}">减免费用</a>&nbsp;
								<a href="toManualRepayent.do?repaymentId=${bean.repaymentId}&borrowId=${bean.borrowId}&userId=${bean.userId}">手工还款</a>&nbsp;
								<span onclick="changeInvestRepaymentId(${bean.repaymentId})" style="cursor: pointer">处理重复请求</span>
							</s:if>
							<s:else>已还完</s:else>
						</td>
					</tr>
				</s:iterator>
			</s:else>
		</tbody>
	</table>
	<br />
	<%--	<br />--%>
	<%--	<br />--%>
	<%--	<input type="button" value="全选" id="checkAll" />--%>
	<%--	&nbsp;&nbsp;--%>
	<%--	<input type="button" value="反选" id="reverse" />--%>
	<%--	&nbsp;&nbsp;--%>
	<%--	<input type="button" value="为选中项还款" id="repayForCheck" />--%>
	<%--	&nbsp;&nbsp;--%>
</div>
<shove:page curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>
