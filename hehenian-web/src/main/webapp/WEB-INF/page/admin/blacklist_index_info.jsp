<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>

	<div>
		<table id="help" style="width: 100%; color: #333333;"
			cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="0">
			<tbody>
				<tr class=gvHeader>
					<th style="width: 50px;" scope="col">
						序号
					</th>
					<th style="width: 80px;" scope="col">
					    用户名
					</th>
					<th style="width: 80px;" scope="col">
					   信用积分
					</th>	
					<th style="width: 80px;" scope="col">
					   会员积分积分
					</th>
					<th style="width: 180px;" scope="col">
					   身份证
					</th>									
					<th style="width: 100px;" scope="col">
						个人信息
					</th>
					<th style="width: 100px;" scope="col">
						工作信息
					</th>
					<th style="width: 100px;" scope="col">
						提现信息
					</th>
					<th style="width: 50px;" scope="col">
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
								${bean.username}
							</td>
							<td>
								${bean.creditrating}
							</td>
							<td>
								${bean.rating}
							</td>
							<td>
							  <s:if test="#bean.idNo==null">
							    未填写
							  </s:if>
							  <s:else>
							    ${bean.idNo}
							  </s:else>
								
							</td>
							<td>
								<s:if test="#bean.p_auditStatus==3">
								  基本信息完整
								</s:if>
								<s:else>
								  基本信息未完整
								</s:else>
							</td>
							<td>
								<s:if test="#bean.w_auditStatus==3">
								  工作信息完整
								</s:if>
								<s:else>
								  工作信息未完整
								</s:else>
							</td>
							<td>
								<s:if test="#bean.cardStatus==3">
								  提现信息完整
								</s:if>
								<s:else>
								  提现信息未完整
								</s:else>
							</td>
							<td>								
								<a href="javascript:del(${bean.id})" >
								    删除
								</a>
							</td>
						</tr>
					</s:iterator>
				</s:else>
			</tbody>
		</table>
	</div>
	<table style="border-collapse: collapse; border-color: #cccccc"
		cellspacing="0" cellpadding="0" width="100%" align="center" border="0">
		<tbody>
			<tr>
				<td class="blue12" width="850" style="padding-left: 8px" align="left">
					&nbsp;
				</td>
				<td>
				   <input id="btnblacklist" onclick="addblacklist();" type="button"
						value="添加黑名单" name="btnDblacklist" />
				</td>
			</tr>
		</tbody>
	</table>
	<shove:page curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>
