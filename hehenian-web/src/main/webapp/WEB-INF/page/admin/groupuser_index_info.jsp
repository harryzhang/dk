<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
	<div>
		<table id="gvNews" style="width: 100%; color: #333333;"
			cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="0">
			<tbody>
				<tr class=gvHeader>
					<!--<th style="width: 35px;" scope="col">
						选中
					</th>
					--><th scope="col">序号</th>
					<th scope="col">用户组</th>
				    <th scope="col">成员数</th>
				    <th scope="col">备注</th>
				    <th scope="col">添加人</th>
				    <th scope="col">提现状态</th>
				    <th scope="col">操作</th>
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
						<!--<td>
							<input id="gvNews_ctl02_cbID" class="adminId" type="checkbox"
								value="${bean.id }" name="gvNews$ctl02$cbID" />
						</td>
						 --><td align="center"><s:property value="#s.count+#counts"/></td>
						 <td align="center"><a href="queryGroupMemberInit.do?paramMap.groupId=${id}" target="main">${groupName }</a></td>
					    <td align="center">${groupCount }</td>
					
					    <td align="center">${groupRemark }</td>
					    <td align="center">${adminName }</td>
					        <td align="center">
					        <s:if test="#bean.cashStatus==1">
					        	启用
					        </s:if>
					        <s:else>
					        	禁用
					        </s:else>
					      </td>
							<td>
							
							<a  href="modifyGroupInit.do?groupId=${id }" target="main">编辑</a>
							&nbsp;
							<a  href="javascript:sendMsg(${id})" target="main">短信</a>
							&nbsp;
							<a  href="javascript:sendEmail(${id})" target="main">邮件</a>
							&nbsp;
							<a id="gvNews_ctl02_lbDelete" style="cursor: pointer;"
										onclick="deleteById(${bean.id })">删除</a>
						
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
				<td class="blue12" style="padding-left: 8px" align="right">
					<!--<input id="chkAll" onclick="checkAll(this); " type="checkbox"
						value="checkbox" name="chkAll" />
					全选 &nbsp;&nbsp;&nbsp;&nbsp;
					<input id="btnDel" onclick="deleteAll();" type="button"
						value="删除选中记录" name="btnDel" />
				-->
				<input id="add" onclick="addGroupInit();" type="button"
						value="添加用户组" name="add" />
				</td>
			</tr>
		</tbody>
	</table>
	<shove:page curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>
