<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
	<div>
		<table id="gvNews" style="width: 100%; color: #333333;"
			cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="0">
			<tbody>
				<tr class=gvHeader>
					<th style="width: 35px;" scope="col">
						选中
					</th>
				
					<th scope="col">用户名</th>
				    <th scope="col">真实姓名</th>
				    <th scope="col">帐号总金额</th>
				    <th scope="col">可用金额</th>
				    <th scope="col">身份证</th>
				    <th scope="col">个人信息</th>
				    <th scope="col">工作信息</th>
				    <!--  modify by houli 2013-05-02取消
				     <th scope="col">提现信息</th>
				     -->
				    <th scope="col">查看详情</th>
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
								value="${bean.id }" name="gvNews$ctl02$cbID" title="${username}" />
						</td>
						
						 <td align="center" >${username}</td>
					    <td align="center">${realName }</td>
						<td align="center">${allSum }</td>
					    <td align="center">${usableSum }</td>
					    <td align="center">${idNo }</td>
					    <td align="center">${personAuditStatus }</td>
					    <td align="center">${workAuditStatus }</td>
					        <!--<td align="center">
					        <s:if test="#bean.cashStatus==1">
					        	启用
					        </s:if>
					        <s:else>
					        	禁用
					        </s:else> 
					      </td> -->
							<td>
							
							<!-- <a id="gvNews_ctl02_lbDelete" style="cursor: pointer;" onclick="deleteById(${bean.id })"> -->
							<!-- modify by houli 2013-05-02 -->			
							<a id="gvNews_ctl02_lbDelete" style="cursor: pointer;"
							onclick="showDetail(${bean.id })">查看详情</a>
							
						
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
					<input id="btnDel" onclick="deleteAll();" type="button"
						value="添加选中记录" name="btnDel" />
				</td>
			</tr>
		</tbody>
	</table>
	<shove:page curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>
