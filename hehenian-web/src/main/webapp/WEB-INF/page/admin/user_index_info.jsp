<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
  <div><span style="color:#A9B5C3;font-size: 14px; ">待审核的用户数量为<b style="color: red">${usercount}</b> 个。总的待审核的认证数量为<b style="color: red">${byNamemap}</b>个</span></div>
	<div>
		<table id="gvNews" style="width: 100%; color: #333333;"
			cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="0">
			<tbody >
				<tr class=gvHeader>
					<th scope="col">
						序号
					</th>
					<th scope="col">
						用户账号
					</th>
					<th scope="col" >
						真实姓名
					</th>
					<th scope="col">
						身份证认证
					</th>
					<th scope="col">
						工作认证
					</th>
					<th scope="col">
						信用报告验证
					</th>
					<th scope="col">
						居住地验证
					</th>
						<th scope="col">
						收入验证
					</th>
						<th scope="col">
						跟踪审核
					</th>
						<th scope="col">
						查看
					</th>
				</tr>
				<s:if test="pageBean.page==null || pageBean.page.size==0">
					<tr align="center" class="gvItem">
						<td colspan="10">暂无数据</td>
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
							<a href="queryPerUserCreditAction.do?userId=${id }">${usrename}</a>
						</td>
						<td>
							${realName}
						</td>
						<td>
						<s:if test="#bean.tmIdentityauditStatus==1">
						 等待审核
						</s:if>
						<s:elseif test="#bean.tmIdentityauditStatus==2">
						审核失败
						</s:elseif>
						<s:elseif test="#bean.tmIdentityauditStatus==3">
						通过
						</s:elseif>
						<s:else>
					     未申请
						</s:else>
						
						<!-- 
						  <s:if test="#bean.auditStatus==1">
							等待审核
							</s:if>
							<s:elseif test="#bean.status==3">
							审通过
							</s:elseif>
							<s:else>
							审核通过
							</s:else>
						
							<s:date name="#bean.addDate" format="yyyy-MM-dd HH:mm:ss" />
							 -->
						</td>
						<td>
								
						<s:if test="#bean.tmworkauditStatus==1">
						 等待审核
						</s:if>
						<s:elseif test="#bean.tmworkauditStatus==2">
						审核失败
						</s:elseif>
						<s:elseif test="#bean.tmworkauditStatus==3">
						通过
						</s:elseif>
						<s:else>
					     未申请
						</s:else>
								
								
								
						</td>
						<td>
						
						<s:if test="#bean.tmresponseauditStatus==1">
						 等待审核
						</s:if>
						<s:elseif test="#bean.tmresponseauditStatus==2">
						审核失败
						</s:elseif>
						<s:elseif test="#bean.tmresponseauditStatus==3">
						通过
						</s:elseif>
						<s:else>
					     未申请
						</s:else>
							
		
						
							
					</td>
						<td>
						
						
						
											<s:if test="#bean.tmaddressauditStatus==1">
						 等待审核
						</s:if>
						<s:elseif test="#bean.tmaddressauditStatus==2">
						审核失败
						</s:elseif>
						<s:elseif test="#bean.tmaddressauditStatus==3">
						通过
						</s:elseif>
						<s:else>
					     未申请
						</s:else>
							
									
							
					</td>
						<td>
							
						<s:if test="#bean.tmincomeeauditStatus==1">
						 等待审核
						</s:if>
						<s:elseif test="#bean.tmincomeeauditStatus==2">
						审核失败
						</s:elseif>
						<s:elseif test="#bean.tmincomeeauditStatus==3">
						通过
						</s:elseif>
						<s:else>
					     未申请
						</s:else>
							
							
							
					</td>
						<td>
						<s:if test="#bean.serviceManName!=null">
						${serviceManName}
						</s:if>
						<s:else>
						未分配
						</s:else>
					</td>
						<td>
					<a href="queryPerUserPicturMsginitindex.do?userId=${id }">	查看</a>
					</td>
					</tr>
				</s:iterator>
				</s:else>
			</tbody>
		</table>
	</div>
	<shove:page curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}" ></shove:page>
