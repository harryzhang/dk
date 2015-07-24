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
					<th scope="col" style="width: 100px;" >
						投资人账号
					</th>
					<th scope="col" style="width: 100px;" >
						姓名
					</th>
					<th style="width: 150px;" scope="col">
						身份证号码
					</th>
					<th style="width: 100px;" scope="col">
						手机号码
					</th>
					<th style="width: 100px;" scope="col">
						添加时间
					</th>
					<th style="width: 60px;" scope="col">
						状态
					</th>
				</tr>
				<s:if test="pageBean.page==null || pageBean.page.size==0">
					<tr align="center" class="gvItem">
						<td colspan="9">暂无数据</td>
					</tr>
				</s:if>
				<s:else>
				
				<s:iterator value="pageBean.page" var="bean" status="st">
					<tr class="gvItem">
						<td>
							<input id="gvNews_ctl02_cbID" class="adminId" type="radio"
								value="${bean.id }" name="relation" />
						</td>
						<td align="center">
							${userName}
						</td>
						<td>
							${realName}
						</td>
						<td>
							${card }
						</td>
						<td>
							${telphone }
						</td>
						<td>
							<s:date name="#bean.addDate"/>
						</td>
						<td>
							${bean.enable==1?'启用':'禁用' }
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
					<input id="butTrue" value="确定" type="button" /><input id="butFalse" value="取消" type="button" />
				</td>
			</tr>
		</tbody>
	</table>
	<shove:page curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>
