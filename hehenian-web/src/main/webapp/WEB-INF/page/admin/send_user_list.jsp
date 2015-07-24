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
					<th style="width: 100px;" scope="col">
					    用户名
					</th>
					<th style="width: 100px;" scope="col">
					  姓名
					</th>	
					<th style="width: 100px;" scope="col">
					   总额
					</th>									
					<th style="width: 100px;" scope="col">
						可用金额
					</th>
					<th style="width: 100px;" scope="col">
						投标金额
					</th>
					 <th style="width: 35px;" scope="col">
						选中
					</th>
				</tr>
				<s:if test="pageBean.page==null || pageBean.page.size==0">
					<tr align="center" class="gvItem">
						<td colspan="8">暂无数据</td>
					</tr>
				</s:if>
				<s:else>
				<s:set name="counts" value="#request.pageNum"/>
					<s:iterator value="pageBean.page" var="bean" status="s">
					     <input type="hidden" name="allid" value="${bean.id}"/>
						<tr class="gvItem">					   
							<td>
								<s:property value="#s.count+#counts"/>
							</td>
							<td>
								${bean.username}
							</td>
							<td>
								${bean.realname}
							</td>
							<td>
								${bean.totalSum}
							</td>
							<td>
								${bean.usableSum}
							</td>
							<td>
								${bean.realAmount}
							</td>
							 <td>
								<input id="gvNews_ctl02_cbID" class="downloadId" type="checkbox"
									value="${bean.id }" name="cb_ids" />
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
			    <td width="94%"> &nbsp;</td>
				<td class="blue12" style="padding-right: 10px" align="right">
					<input id="chkAll" onclick="checkAll(this); " type="checkbox" value="checkbox" name="chkAll" />
					<br/>
					全选
					
				</td>
			</tr>
		</tbody>
	</table>
	<shove:page curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>
