<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
	<div>
		<table id="help" style="width: 100%; color: #333333;"
			cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="0">
			<tbody>
				<tr class=gvHeader>
				    <th style="width: 35px;" scope="col">
							选中	
					</th>
					<th style="width: 70px;" scope="col">
						用户名
					</th>
					<th style="width: 80px;" scope="col">
					    联系电话
					</th>
						<th style="width: 80px;" scope="col">
					   客服名称
					</th>
						<th style="width: 80px;" scope="col">
					  操作
					</th>
						
				</tr>
				<s:if test="pageBean.page==null || pageBean.page.size==0">
					<tr align="center" class="gvItem">
						<td colspan="5">暂无数据</td>
					</tr>
				</s:if>
				<s:else>
					<s:iterator value="pageBean.page" var="bean" status="st">
						<tr class="gvItem">
						    <td>
						    <input id="gvNews_ctl02_cbID" class="downloadId" type="checkbox"
									value="${bean.id }" name="cb_ids" 
									/>
							</td>
							<td>
								${bean.username }
							</td>
							<td>
								${bean.mobilePhone}
							</td>
							<td>
					   				 ${bean.kfname}
							</td>
							<td>
								<select id="gg_${bean.id}" >
							    <option value="">-请选择-</option>
							  <s:iterator value="#request.kefulist" var="kbena">
							    <option value="${kbena.id }">${kbena.name}</option>
							  </s:iterator>
							</select>
								<input type="button" value="确认" onclick="sure(${bean.id })">
							</td>
						</tr>
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
					<input id="chkAll" onclick="checkAll(this); " type="checkbox" value="checkbox" name="chkAll" />
					全选 &nbsp;&nbsp;&nbsp;&nbsp;
			
					<input id="btnDel" onclick="kefufors();" type="button"
						value="分配" name="btnDel" />
				
				</td>
			</tr>
		</tbody>
	</table>
	<shove:page curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>
