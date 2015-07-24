<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
<script type="text/javascript" src="../common/dialog/popup.js"></script>
<script type="text/javascript" src="../script/jbox/jquery.jBox-2.3.min.js"></script>
<link href="../css/jbox/Gray/jbox.css" rel="stylesheet" type="text/css" />
<script language="javascript"  type="text/javascript">
	//提交减免费用
	function  updaterepaymentStatus(borrowId, repayPeriod){
		if (confirm("确认要更改这期成已还状态?")) {
			param["borrowId"] = borrowId;
			param["repayPeriod"] = repayPeriod;
			$.jBox.tip("<div class='data-submit'>提交数据中，请稍候...</div>","loading");
	 		$.shovePost("updateRepaymentStatus.do",param,function(data) {
	 			$.jBox.closeTip();
	 			var callBack = data.msg;
				if (callBack == "1") {
					alert("更改状态成功!");
					window.location.reload();
				}else {
					alert(data.msg);
					return false;
				}
			});
	 		
		}
	}
	
	
</script>
<div style="width: 1120px;">
	<table id="help" style="width: 100%; color: #333333;" cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="0">
		<tbody>
			<tr class=gvHeader>
				<th style="width: 80px;" scope="col">期数</th>
				<th style="width: 80px;" scope="col">还款日期</th>
				<th style="width: 80px;" scope="col">应还本金</th>
				<th style="width: 80px;" scope="col">剩余本金</th>
				<th style="width: 80px;" scope="col">应还利息</th>
				<th style="width: 80px;" scope="col">应还本息</th>
				<th style="width: 80px;" scope="col">咨询费</th>
				<th style="width: 80px;" scope="col">手续费</th>
				<th style="width: 80px;" scope="col">应还罚息</th>
				<th style="width: 80px;" scope="col">总还款额</th>
				<th style="width: 80px;" scope="col">逾期状态</th>
				<th style="width: 80px;" scope="col">还款状态</th>
				<th style="width: 80px;" scope="col">代偿状态</th>
				<th style="width: 80px;" scope="col">实际还款日</th>
				<th style="width: 80px;" scope="col">查看投资人</th>
			</tr>
			<s:if test="pageBean.page==null || pageBean.page.size==0">
				<tr align="center" class="gvItem">
					<td colspan="13">暂无数据</td>
				</tr>
			</s:if>
			<s:else>
				<s:iterator value="pageBean.page" var="bean" status="s">
					<tr class="gvItem">
						<td>${bean.repayPeriod}</td>
						<td><s:date name="#bean.repayDate" format="yyyy-MM-dd" /></td>
						<td>${bean.stillPrincipal}</td>
						<td>${bean.principalBalance}</td>
						<td>${bean.stillInterest}</td>
						<td>${bean.stillPI}</td>
						<td>${bean.consultFee}</td>
						<td>${bean.repayFee}</td>
						<td>${bean.lateFI}</td>
						<td>${bean.stillSum}</td>
						<td>
							<s:if test="#bean.isLate == 2">逾期</s:if> 
							<s:else></s:else>
						</td>
						<td>
							<s:if test="#bean.repayStatus == 1">未还</s:if> 
							<s:else>已还</s:else>
						</td>
						<td>
							<s:if test="#bean.isWebRepay == 2">代偿</s:if> 
							<s:else></s:else>
						</td>
						<td><s:date name="#bean.realRepayDate" format="yyyy-MM-dd" /></td>
						<td nowrap="nowrap">
							<a href="queryRepaymentInvestIndex.do?investId=${bean.borrowId}">查看投资人</a>&nbsp;
							<s:if test="#bean.repayStatus == 1">
								<a href="#" onclick="updaterepaymentStatus('${bean.borrowId}','${bean.repayPeriod}')">更改成已还</a>&nbsp;
							</s:if> 
						</td>
						
					</tr>
				</s:iterator>
			</s:else>
		</tbody>
	</table>
</div>
<shove:page curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>
