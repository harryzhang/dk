<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="../css/jbox/Gray/jbox.css" type="text/css"></link>
<div>
<!-- 
	<table id="help" style="width: 100%; color: #333333;" cellspacing="1"
		cellpadding="3" bgcolor="#dee7ef" border="0">
		<tbody>
			<tr class=gvHeader>
				<th style="width: 60px;" scope="col">
					还款人
				</th>
				<th style="width: 100px;" scope="col">
					期数
				</th>
				<th style="width: 100px;" scope="col">
					还款时间
				</th>
				<th style="width: 100px;" scope="col">
					应还本金
				</th>
				<th style="width: 100px;" scope="col">
					应还利息
				</th>
				<th style="width: 100px;" scope="col">
					是否逾期
				</th>
				<th style="width: 100px;" scope="col">
					逾期天数
				</th>
				<th style="width: 100px;" scope="col">
					应罚利息
				</th>
			</tr>
			<s:if test="pageBean.page==null || pageBean.page.size==0">
				<tr align="center" class="gvItem">
					<td colspan="8">
						暂无数据
					</td>
				</tr>
			</s:if>
			<s:else>
				<s:set name="counts" value="#request.pageNum" />
				<s:iterator value="pageBean.page" var="bean" status="s">
					<tr class="gvItem">
						<td align="center">
							${bean.repayname}
						</td>
						<td align="center">
							${bean.repayPeriod}
						</td>
						<td align="center">
							${bean.realRepayDate}
						</td>
						<td align="center">
							￥${bean.stillPrincipal}
						</td>
						<td align="center">
							￥${bean.stillInterest }
						</td>
						<td align="center">
							<s:if test="#bean.isLate == 1">否</s:if>
							<s:else>是</s:else>
						</td>
						<td align="center">
							${bean.lateDay}
						</td>
						<td align="center">
							￥${bean.lateFI}
						</td>
					</tr>
				</s:iterator>
			</s:else>
		</tbody>
	</table>
	
	 -->
	 <table id="help" style="width: 100%; color: #333333;" cellspacing="1"
		cellpadding="3" bgcolor="#dee7ef" border="0">
		<tr class=gvItem>
			<th style="width: 100px;" scope="col">期数</th>
			<th style="width: 100px;" scope="col">还款日期</th>
			<th style="width: 100px;" scope="col">应还本金</th>
			<th style="width: 100px;" scope="col">应还利息</th>
			<th style="width: 100px;" scope="col">应还本息</th>
			<th style="width: 100px;" scope="col">还款状态</th>
		</tr>
		<s:if test="pageBean.page==null || pageBean.page.size==0">
			<tr class="gvItem" style="height: 25px;">
				<td colspan="6">暂无数据</td>
			</tr>
		</s:if>
		<s:else>
			<s:iterator value="pageBean.page" var="bean" status="s">
				<tr class="gvItem" style="height: 25px;">
					<td align="center">${bean.repayPeriod}</td>
					<td align="center">${bean.repayDate}</td>
					<td align="center">${bean.recivedPrincipal}</td>
					<td align="center">${bean.recivedInterest}</td>
					<td align="center">${bean.recivedPrincipal+bean.recivedInterest}</td>
					<td align="center"><s:if test="#bean.repayStatus == 1">未偿还 </s:if><s:if test="#bean.repayStatus == 2">已偿还</s:if></td>
				</tr>
			</s:iterator>
		</s:else>
	</table>
</div>
<br>

