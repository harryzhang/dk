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
					<th style="width: 50px;" scope="col">
						序号
					</th>
					<th style="width: 100px;" scope="col">
					    标题
					</th>
					<th style="width: 50px;" scope="col">
					   发送人
					</th>	
					<th style="width: 50px;" scope="col">
					   接收人
					</th>									
					<th style="width: 150px;" scope="col">
						内容
					</th>
					<th style="width: 80px;" scope="col">
						发送时间
					</th>
					<th style="width: 120px;" scope="col">
						操作
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
						<tr class="gvItem">
						    <td>
								<input id="gvNews_ctl02_cbID" class="downloadId" type="checkbox"
									value="${bean.id }" name="cb_ids" />
							</td>
							<td>
								<s:property value="#s.count+#counts"/>
							</td>
							<td>
								${bean.mailTitle}
							</td>
							<td>
								${bean.sender}
							</td>
							<td>
								${bean.reciver}
							</td>
							<td>
								${bean.mailContent}
							</td>
							<td>
								${bean.sendTime}
							</td>
							<td>
								<a href="javascript:updates(${bean.id})" >
								    查看
								</a>
								&nbsp;&nbsp;
								<a href="javascript:del(${bean.id})" >
								    删除
								</a>
								&nbsp;&nbsp;
								<a href="javascript:black(${bean.id})" >
								    加入黑名单
								</a>
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
					<input id="btnDel" onclick="dels();" type="button"
						value="删除选中记录" name="btnDel" />
				</td>
			</tr>
		</tbody>
	</table>
	<shove:page curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>
