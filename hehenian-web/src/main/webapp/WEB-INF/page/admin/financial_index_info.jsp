<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
	<div>
		<table id="gvNews" style="width: 98%; color: #333333;"
			cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="0">
			<tbody>
				<tr class=gvHeader>
					<th scope="col">
						序号
					</th>
					<th scope="col">
						团队长账号（姓名）
					</th>
					<th scope="col">
						经纪人账号（姓名）
					</th>
					<th scope="col">
						投资人账号（姓名）
					</th>
					<th scope="col">
						理财人
					</th>
					<th scope="col">
						真实姓名
					</th>
					<th scope="col">
						信用积分
					</th>
					<th scope="col">
						会员积分
					</th>
					<th scope="col">
						身份证
					</th>
					<th scope="col">
						注册时间
					</th>
					<th style="width: 100px;" scope="col">
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
							<s:if test="#bean.userName1!=null&&#bean.userName1!=''">
								${userName1}<br/>
								(${realName1})
							</s:if>
						</td>
						<td align="center">
						<s:if test="#bean.userName2!=null&&#bean.userName2!=''">
							${userName2}<br/>
							(${realName2})
						</s:if>
						</td>
						<td align="center">
						<s:if test="#bean.userName3!=null&&#bean.userName3!=''">
							${userName3}<br/>
							(${realName3})
						</s:if>
						</td>
						<td align="center">
							${userName4}
						</td>
						<td>
							${realName4}
						</td>
						<td>
							${creditrating }
						</td>
						<td>
							${rating }
						</td>
						<td>
							${idNo }
						</td>
						<td>
						${bean.addDate }
						</td>
							<td>
						<span style="cursor: pointer;" class="enable" rId="${bean.id }" enable="${bean.enable }" >
						${bean.enable==1?'解除关联':'重置关系' }
						</span>
					</td> </tr>
				</s:iterator>
				</s:else>
			</tbody>
		</table>
	</div>
	
	<shove:page curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>
