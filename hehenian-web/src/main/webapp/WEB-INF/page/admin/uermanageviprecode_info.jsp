<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<script type="text/javascript" src="../css/popom.js"></script>
<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
	<div>
		<table id="help" style="width: 100%; color: #333333;"
			cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="0">
			<tbody>
				<tr class=gvHeader>
					<th style="width: 50px;" scope="col">
						序号
					</th>
						<th style="width: 100px;" scope="col">
						用户名
					</th>
						<th style="width: 100px;" scope="col">
						真实姓名
					</th>
						<th style="width: 100px;" scope="col">
						申请vip时间
					</th>
						<th style="width: 100px;" scope="col">
						到期vip时间
					</th>
						<th style="width: 100px;" scope="col">
						付费状态
					</th>
				</tr>
				<s:if test="pageBean.page==null || pageBean.page.size==0">
					<tr align="center" class="gvItem">
						<td colspan="6">暂无数据</td>
					</tr>
				</s:if>
				<s:else>
				<s:set name="counts" value="#request.pageNum"/>
					<s:iterator value="pageBean.page" var="bean" status="s">
						<tr class="gvItem">
							<td>
								<s:property value="#s.count+#counts"/>
							</td>
							<td>
							${username }
							</td>
								<td>
							${realName }
							</td>
								<td>
							${vipCreateTime }
							</td>
								<td>
							${vip }
							</td>
								<td>
								<s:if test="#bean.feeStatus==1">未扣除</s:if>
								<s:if test="#bean.feeStatus==2">已付费</s:if>
							</td>
						</tr>
					</s:iterator>
				</s:else>
			</tbody>
		</table>
	</div>
<script>

</script>

	<shove:page curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>