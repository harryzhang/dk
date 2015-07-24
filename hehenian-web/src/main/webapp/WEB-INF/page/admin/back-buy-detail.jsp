<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<div style="padding: 15px 10px 0px 10px;font-size: 12px;font-family: tahoma;">
	<table style="width: 95%; color: #333333;border-collapse: collapse;margin: auto;" border="1">
		<tbody>
			<tr>
				<th scope="col">用户名</th>
				<th scope="col">剩余本金</th>
				<th scope="col">当期利息</th>
				<th scope="col">已产生利息天数</th>
				<th scope="col">回购金额</th>
				<th scope="col">操作</th>
			</tr>
			<s:if test="#request.list==null || #request.list.size==0">
				<tr align="center">
					<td colspan="6">暂无数据</td>
				</tr>
			</s:if>
			<s:else>
				<s:set name="counts" value="#request.pageNum" />
				<s:iterator value="#request.list" var="bean" status="s">
					<tr>
						<td align="center">${bean.username}</td>
						<td align="center" class="bal">${bean.balance}</td>
						<td align="center">${bean.interest}</td>
						<td align="center">${bean.day}</td>
						<td align="center">${day*interest/30+balance}</td>
						<td align="center" onclick="backBuy(${bean.investId},this,${bean.status });" <s:if test="#bean.status != -1">disabled="disabled"</s:if> style="cursor: pointer;">
						<s:if test="#bean.status == -1">回购</s:if>
						<s:else>已回购</s:else>
						</td>
					</tr>
				</s:iterator>
			</s:else>
		</tbody>
	</table>
	<p />
	<div style="display: none;" id="htmlDiv"></div>
</div>
<script type="text/javascript" src="../script/jquery-1.7.1.min.js">
</script>
<script type="text/javascript">
	function backBuy(investId,node,status) {
		if(status != -1)
			return;
		$(node).attr("disabled",true);
		$(node).attr("style","color:#ccc;");
		var url = "backBuyByborrowId.do?investId="+investId;
		param = {};
		param["investId"] = investId;
		param["dealPrice"] = $(node).prev().html();
		param["price"] = $(node).parent().children(".bal").html();
		$.post(url,$.param(param), function(data){
			if(data.length<30){
				alert(data);
				return false;
			}
			$("#htmlDiv").html(data);
		});
	}
</script>