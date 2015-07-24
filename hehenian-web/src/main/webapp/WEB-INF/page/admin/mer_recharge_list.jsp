<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../css/admin/popom.js"></script>
<script type="text/javascript" src="../My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
<script>
function initListInfo(param){
	var num = param["pageBean.pageNum"];
	$("#num").val(num);
	document.forms['editForm'].submit();
}
</script>
<div style="padding: 20px 20px 0px 20px;   background-color: #fff;">
	<form action="queryMerRechargeList.do" method="post" id="editForm" name="editForm">
		<table cellspacing=" 0" cellpadding="0" width="100%" border="0">
			<tbody>
				<tr>
					<td class="f66" height="36px">提交时间： 
					<input name="start" type="text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})" value="${start }" /> &nbsp; 到 &nbsp; 
					<input name="end" type="text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})"  value="${end }"/> <input type="submit" value="查 找" /></td>
					<input type="hidden" id="num" name="num" value="1"/>
				</tr>
			</tbody>
		</table>
	</form>
</div>
<div style="padding: 0px 20px 0px 20px;">
	<table id="gvNews" style="width: 100%; color: #333333;" cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="0">
		<tbody>
			<tr class=gvHeader>
				<th scope="col">订单号</th>
				<th scope="col">充值金额</th>
				<th scope="col">手续费</th>
				<th scope="col">充值时间</th>
				<th scope="col">子账户号</th>
				<th scope="col">充值结果</th>
				<th scope="col">账户余额</th>
			</tr>
			<s:if test="pageBean.page==null || pageBean.page.size==0">
				<tr align="center" class="gvItem">
					<td colspan="7">暂无数据</td>
				</tr>
			</s:if>
			<s:else>
				<s:iterator value="pageBean.page" var="bean" status="s">
					<tr class="gvItem">
						<td align="center">${bean.trxId}</td>
						<td align="center">${bean.money}</td>
						<td align="center">${bean.fee}</td>
						<td align="center"><s:date name="#bean.rechargeTime" format="yyyy-MM-dd HH:mm:ss" />
						</td>
						<td align="center">${bean.subAcct}</td>
						<td align="center">
						<s:if test="#bean.result==1">成功</s:if>
						<s:elseif test="#bean.result==0">失败</s:elseif>
						 <s:else>充值中</s:else>
						</td>
						<td align="center">${bean.usableSum}</td>
					</tr>
				</s:iterator>
			</s:else>
		</tbody>
	</table>
	<p />
	<shove:page curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>
</div>
