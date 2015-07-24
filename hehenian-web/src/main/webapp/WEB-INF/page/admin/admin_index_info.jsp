<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
	<div>
		<table id="gvNews" style="width: 100%; color: #333333;"
			cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="0">
			<tbody>
				<tr class=gvHeader>
					<th scope="col">
						选中
					</th>
					<th scope="col">
						管理员账号
					</th>
					<th scope="col">
						管理员汇付账号
					</th>
					<th scope="col">
						角色名称
					</th>
					<th scope="col">
						上次登录时间
					</th>
					<th scope="col">
						上次登录IP
					</th>
					<th scope="col">
						是否禁用
					</th>
					<th scope="col">
						操作
					</th>
				</tr>
				<s:if test="pageBean.page==null || pageBean.page.size==0">
					<tr align="center" class="gvItem">
						<td colspan="8">暂无数据</td>
					</tr>
				</s:if>
				<s:else>
				
				<s:iterator value="pageBean.page" var="bean" status="st">
					<tr class="gvItem">
						<td>
							<input id="gvNews_ctl02_cbID" class="adminId" type="checkbox"
								value="${bean.id }" name="gvNews$ctl02$cbID" />
						</td>
						<td align="center">
							${userName}
						</td>
						<td>
							${usrCustId}
						</td>
						<td>
							${roleName}
						</td>
						<td>
							<s:date name="#bean.lastTime" format="yyyy-MM-dd hh:mm:ss" />
						</td>
						<td>
							${bean.lastIP }
						</td>
						<td>
							${enable==1?'否':'是' }
						</td>
							<td>
						
							<s:if test="#bean.roleId == -1">
								<a href="updateAdminInit.do?id=${bean.id }" target="main">
						编辑</a>
							</s:if>
							<s:else>
							<s:if test="#bean.enable==1">
									<a id="gvNews_ctl02_lbDelete" style="cursor: pointer;"
										onclick="isNotenable(${bean.id })">禁用</a>
							</s:if>
							<s:else>
							<a id="gvNews_ctl02_lbDelete" style="cursor: pointer;"
										onclick="isenable(${bean.id })">启用</a>
							</s:else>
								<a href="updateAdminInit.do?id=${bean.id }" target="main">
							编辑</a>
							<a id="gvNews_ctl02_lbDelete" style="cursor: pointer;"
										onclick="deleteAdminById(${bean.id })">删除</a>
							</s:else>
						
					</td></tr>
				</s:iterator>
				</s:else>
			</tbody>
		</table>
	</div>
	<table style="border-collapse: collapse; border-color: #cccccc"
		cellspacing="1" cellpadding="3" width="100%" align="center" border="1">
		<tbody>
			<tr>
				<td class="blue12" style="padding-left: 8px" align="left">
					<input id="chkAll" onclick="checkAll(this); " type="checkbox"
						value="checkbox" name="chkAll" />
					全选 &nbsp;&nbsp;&nbsp;&nbsp;
					<input id="btnDel" onclick="deleteAdmins();" type="button"
						value="删除选中记录" name="btnDel" />
				</td>
			</tr>
		</tbody>
	</table>
	<shove:page curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>
