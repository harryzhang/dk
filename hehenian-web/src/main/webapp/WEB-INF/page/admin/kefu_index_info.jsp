<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<%@ include file="/include/includeJs.jsp"%>
	<div>
		<table id="help" style="width: 100%; color: #333333;"
			cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="0">
			<tbody>
				<tr class=gvHeader>
				    <th style="width: 35px;" scope="col">
						选中
					</th>
					<th style="width: 70px;" scope="col">
						客服名称
					</th>
					<th style="width: 120px;" scope="col">
					    客服头像
					</th>	
					<th style="width: 80px;" scope="col">
					    QQ号
					</th>	
					<th style="width: 150px;" scope="col">
					    备注
					</th>								
					<th style="width: 80px;" scope="col">
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
								<input id="gvNews_ctl02_cbID" class="downloadId" type="checkbox"
									value="${bean.id }" name="cb_ids" />
							</td>
							<td>
								${bean.name }
							</td>
							<td >
							   <img width="70" height="30"  src="../${bean.kefuImage}" >
								
							</td>
							<td>
								${bean.QQ}
							</td>
							<td width="120" height="40" style="overflow: hidden;">
								${bean.remark}
							</td>
								
							<td>
								<a href="javascript:edit('updateKefuInit.do',${bean.id})" target="main">
								    编辑
								</a>
								&nbsp;&nbsp;
								<a href="javascript:del(${bean.id})" >
								
								删除
								</a>
									&nbsp;&nbsp;
								 <a href="queryKefuOfInvestorInit.do?id=${bean.id}" >
								查看
								</a>
										&nbsp;&nbsp;
											 <a href="queryKefuForInit.do?id=${bean.id}" >
								分配
								</a>
								
							</td>
						</tr>
					</s:iterator>
				</s:else>
			</tbody>
		</table>
	</div>
	<table style="border-collapse: collapse; border-color: #cccccc"
		cellspacing="1" cellpadding="3" width="100%" align="center" border="0">
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
