<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<%@ include file="/include/includeJs.jsp"%>
<script>
function statement(id){
	$.jBox("iframe:awardSettlementInit.do?economyId="+id, {
	    title: "编辑",
	    top:"2%",
	    width: 800,
	    height: 550,
	    buttons: { '关闭':true}
	});
}
</script>
	<div>
		<table id="gvNews" style="width: 100%; color: #333333;"
			cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="0">
			<tbody>
				<tr class=gvHeader>
					<th scope="col">
						序号
					</th>
					<th scope="col">
						经纪人
					</th>
					<th scope="col">
						旗下用户
					</th>
					<th scope="col">
						旗下用户姓名
					</th>
					<th scope="col">
						提成
					</th>
					<th scope="col">
						添加时间
					</th>
					<th scope="col">
						是否结算
					</th>
				</tr>
				<s:if test="pageBean.page==null || pageBean.page.size==0">
					<tr align="center" class="gvItem">
						<td colspan="7">暂无数据</td>
					</tr>
				</s:if>
				<s:else>
				
				<s:iterator value="pageBean.page" var="bean" status="st">
					<tr class="gvItem">
						<td>
						${st.count }
						</td>
						<td>
						${economy}
						</td>
						<td>
						${investor }
						</td>
						<td>${realName }</td>
						<td>
						${money }
						</td>
						<td>
						${addDate}
						</td>
						<td>
						${status==1?"已结算":"未结算"}
						</td>
						</tr>
				</s:iterator>
				</s:else>
			</tbody>
		</table>
	</div>
	<table style="border-color: #fff; margin-top: 20px;" cellspacing="1"
		cellpadding="3" width="100%" border="1">
		<tbody>
			<tr>
				<td class="blue12" style="padding-left: 0px" align="right" >
					未结算金额：<input id="chkAll"  type="text" disabled="disabled"
						value="${paramMap.notuse}" style="vertical-a lign: -6px;" />
					&nbsp;&nbsp;&nbsp;&nbsp;
					<input id="btnDel" onclick="statement(${paramMap.economyId});" type="button" value="去结算"
						name="btnDel" />
				</td>
			</tr>
		</tbody>
</table>
	<shove:page curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>
	