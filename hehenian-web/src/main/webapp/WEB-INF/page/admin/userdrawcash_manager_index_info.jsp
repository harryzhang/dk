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
				     <th scope="col">提现信息</th>
				    <th scope="col">提现状态</th>
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
					    <td align="center">
					    	<s:if test="#bean.personAuditStatus == null">
					    		不完整
					    	</s:if>
					    	<s:else>
					    		基本信息完整
					    	</s:else>
					    </td>
					    <td align="center">
					    <s:if test="#bean.workAuditStatus == null">
					    		不完整
					    	</s:if>
					    	<s:else>
					    	工作信息完整
					    	</s:else>
					   </td>
					        <td align="center"><!--
					        <s:if test="#bean.cashStatus==1">
					        	启用
					        </s:if>
					        <s:else>
					        	禁用
					        </s:else>
					      --></td>
							<td>
							 <s:if test="#bean.cashStatus==1">
					        	<a id="gvNews_ctl02_lbDelete" style="cursor: pointer;"
										onclick="deleteById(${bean.id },2)">禁用</a>
					        </s:if>
					        <s:else>
					        	<a id="gvNews_ctl02_lbDelete" style="cursor: pointer;"
										onclick="deleteById(${bean.id },1)">开启</a>
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
					<input id="btnDel" onclick="deleteAll(2);" type="button"
						value="禁止选中记录" name="btnDel" />
					<input id="btnDel" onclick="deleteAll(1);" type="button"
						value="开启选中记录" name="btnDel" />
						
				</td>
			</tr>
		</tbody>
	</table>
	<shove:page curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>
