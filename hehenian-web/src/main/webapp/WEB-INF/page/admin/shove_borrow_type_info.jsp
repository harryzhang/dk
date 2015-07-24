<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<script type="text/javascript">
	function del(id){
 		if(!window.confirm("确定要删除吗?")){
 			return;
 		}
 		
		window.location.href = "deleteShoveType.do?id="+id;
 	}
</script>	
	<div>
		<table id="help" style="width: 100%; color: #333333;"
			cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="0">
			<tbody>
				<tr class=gvHeader>
					<th style="width: 50px;" scope="col">
						序号
					</th>
					<th style="width: 80px;" scope="col">
						标种类型
					</th>
					<th style="width: 150px;" scope="col">
						还款方式
					</th>
					<th style="width: 50px;" scope="col">
						限额
					</th>
					<th scope="col" align="center" style="width: 40px;" >
						状态
					</th>
					<th style="width: 50px;" scope="col">
						操作
					</th>
					<th style="width: 50px;" scope="col">
						操作
					</th>
				</tr>
				<s:if test="pageBean.page==null || pageBean.page.size==0">
					<tr align="center" class="gvItem">
						<td colspan="11">暂无数据</td>
					</tr>
				</s:if>
				<s:else>
				<s:set name="counts" value="#request.pageNum"/>
					<s:iterator value="pageBean.page" var="bean" status="s">
						<tr class="gvItem">
							<td>
								<s:property value="#s.count+#counts"/>
							</td>
							<td align="center">
						   		${name }
							</td>
							<td align="center">
									${titles }
							</td>
							<td align="center">
						   		${amount_end }
							</td>
							<td align="center">
							<s:if test="%{status == 1}">
								开启
							</s:if>
						    <s:else>
						    	关闭
						    </s:else>
							</td>	
							<td>
							<a href="updateShoveTypeInit.do?id=${id}">修改</a>
							</td>		
							<td>
							<a href="javascript:del(${id})">删除</a>
							</td>		
						</tr>
					</s:iterator>
				</s:else>
			</tbody>
		</table>
	</div>
	
<shove:page curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>
	