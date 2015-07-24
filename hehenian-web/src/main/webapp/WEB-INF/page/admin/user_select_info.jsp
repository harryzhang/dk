<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<div><span class="f66">待审核的用户数量为 ${usercount } 个。总的待审核的认证数量为${byNamemap }个</span></div>
	<div>
		<table id="gvNews" style="width: 100%; color: #333333;"
			cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="0">
			<tbody>
				<tr class=gvHeader>
					<th style="width: 100px;" scope="col">
						序号
					</th>
					<th scope="col">
						用户账号
					</th>
					<th style="width: 100px;" scope="col">
						真实姓名
					</th>
					<th style="width: 100px;" scope="col">
						待审核认证
					</th>
					<th style="width: 100px;" scope="col">
					成功认证
					</th>
					<th style="width: 100px;" scope="col">
						失败认证
					</th>
					<th style="width: 100px;" scope="col">
						未申请认证	
					</th>
					<!-- 
						<th style="width: 170px;" scope="col">
						过期认证	
					</th>
					 -->
						<th style="width: 100px;" scope="col">
						跟踪审核
					</th>
						<th style="width: 100px;" scope="col">
						操作
					</th>
				</tr>
				<s:if test="pageBean.page==null || pageBean.page.size==0">
					<tr align="center" class="gvItem">
						<td colspan="9">暂无数据</td>
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
						<a href="queryPerUserCreditAction.do?userId=${id}">
						${username}
						</a>	
						</td>
						<td>
						${realName}
						</td>
						<td>
					<!-- 待审核验证 -->			
                     
                    <!--    <s:if test="#bean.wait==0">0</s:if>
                      <s:else>${wait}</s:else> -->
                      ${wait}
						</td>
						<td>
			<!--   <s:if test="#bean.wait==0">0</s:if>
                      <s:else>${success}</s:else>-->
							${success}
					</td>
						<td>
							
			<!-- 	<s:if test="#bean.wait==0">0</s:if>
                      <s:else>${fail}</s:else> -->
							${fail}
							
					</td>
					<td>
					${nosh}
					</td>
					<!-- 
						<td>
					${pass}
					</td>
					 -->
						<td>
				 ${tausername }
					</td>
						<td>
					  <a href="queryselect.do?userId=${id}">查看</a>
					</td>
					</tr>
				</s:iterator>
				</s:else>
			</tbody>
		</table>
	</div>
	<shove:page curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>
