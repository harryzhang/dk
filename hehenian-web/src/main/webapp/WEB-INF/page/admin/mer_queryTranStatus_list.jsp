<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<%@ include file="/include/includeJs.jsp"%>
<script type="text/javascript">
//审核
function queryTransStatus(ordId,time,queryTrade) {
	var param = {};
	param["paramMap.ordId"] = ordId;
	param["paramMap.startTime"] = time;
	param["paramMap.queryTrade"] = queryTrade;
	$.shovePost("queryTransStatus.do", param, function(data) {
		$("#status_" + ordId).html(data);
	});
}

</script>
<div>
	<table id="help" style="width: 100%; color: #333333;" cellspacing="1"
		cellpadding="3" bgcolor="#dee7ef" border="0">
		<tbody>
			<tr class=gvHeader>
				<th style="width: 100px;" scope="col">
					订单号
				</th>
				<th style="width: 100px;" scope="col">
					用户名
				</th>
				<th style="width: 100px;" scope="col">
					金额
				</th>
				<th style="width: 100px;" scope="col">
					时间
				</th>
				<th style="width: 100px;" scope="col">
					操作
				</th>
				<th style="width: 100px;" scope="col">
					结果状态
				</th>
			</tr>
			<s:if test="pageBean.page==null || pageBean.page.size==0">
				<tr align="center" class="gvItem">
					<td colspan="6">
						暂无数据
					</td>
				</tr>
			</s:if>
			<s:else>
				<s:set name="counts" value="#request.pageNum" />
				<s:iterator value="pageBean.page" var="bean" status="s">
					<tr class="gvItem">
						<td align="center">
							${bean.ordId}
						</td>
						<td align="center">
							${bean.userName}
						</td>
						<td align="center">
							${bean.money}
						</td>
						<td align="center">
						    <s:date name="time" format="yyyy-MM-dd"/>
						</td>
						<td align="center">
							<span onclick="queryTransStatus('${bean.ordId}','${time}' ,'${queryTrade}')"
								style="cursor: pointer; color: #3366CC;">查询</span>
						</td>
						<td align="center">
							<span style="cursor: pointer; color: #3366CC;" class="export" id="status_${bean.ordId}">未查询</span>
						</td>
					</tr>
				</s:iterator>
			</s:else>
		</tbody>
	</table>
</div>
<br>
<shove:page curPage="${pageBean.pageNum}" showPageCount="10"
	pageSize="${pageBean.pageSize }" theme="jsNumber"
	totalCount="${pageBean.totalNum}"></shove:page>

