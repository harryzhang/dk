<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
	<div>
		<table id="help" style="width: 100%; color: #333333;"
			cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="0">
			<tbody>
				<tr class=gvHeader>
					<th scope="col">
						序号
					</th>
					<th scope="col">
						用户名
					</th>
					<th scope="col">
						真实姓名
					</th>
					<th scope="col">
						手机号码
					</th>
							<th scope="col">
						申请手机
					</th>									
					<th scope="col">
						投标总额
					</th>
						<th scope="col">
						申请时间
					</th>
					<th scope="col">
              		                  状态
					</th>
					<th scope="col">
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
								${username}
							</td>
							<td>
							${realName}
							</td>	
							<td>
							${cellPhone}
							</td>	
										<td>
							${mobilePhone}
							</td>	
							<td>
								${amountall}
							</td>						
							<td>
							${requsetTime}
							</td>
								<td>
								<s:if test="#bean.tpStatus==1">成功</s:if>
								<s:if test="#bean.tpStatus==2">审核中</s:if>
								<s:if test="#bean.tpStatus==3">取消</s:if>
								<s:if test="#bean.tpStatus==4">失败</s:if>
							</td>
								<td>
							<a href="updateUserTel.do?id=${id}&tpdiid=${tpbiid}">查看</a>
							</td>
						
							
						</tr>
					</s:iterator>
				</s:else>
			</tbody>
		</table>
	</div>
	<shove:page curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>
