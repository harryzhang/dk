<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<script type="text/javascript">
 $(function(){
   $("#excel").click(function(){  
   window.location.href=encodeURI(encodeURI("exportRechargeRecord.do?userName="+$("#userName").val()+"&&rechargeTime="+$("#rechargeTime").val()+"&&rechargeType="+$("#rechargeType").val()+"&&statss="+$("#status").val()));
   });
 });
</script>
	<div>
		<table id="help" style="width: 100%; color: #333333;"
			cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="0">
			<tbody>
				<tr class=gvHeader>
				   <th style="width: 35px;" scope="col">
						选中
					</th>
					<th style="width: 100px;" scope="col">
						用户名
					</th>
					<th style="width: 150px;" scope="col">
						姓名
					</th>
					
					<th style="width: 100px;" scope="col">
						身份证号码
					</th>
					<th style="width: 80px;" scope="col">
					手机号码
					</th>
					<th style="width: 100px;" scope="col">
							添加时间
					</th>
				</tr>
				<s:if test="pageBean.page==null || pageBean.page.size==0">
					<tr align="center" class="gvItem">
						<td colspan="10">暂无数据</td>
					</tr>
				</s:if>
				<s:else>
					<s:iterator value="pageBean.page" var="bean" status="st">
						<tr class="gvItem">
						    <td>
								<input id="gvNews_ctl02_cbID" class="helpId" type="checkbox"
									value="${bean.id }" name="cb_ids" />
							</td>
							<td align="center" style="width:100px;">
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
					<input id="chkAll" class="helpId" onclick="checkAll(this); " type="checkbox" value="checkbox" name="chkAll" />
					全选 &nbsp;&nbsp;&nbsp;&nbsp;
					<input id="btnExport" onclick="addRelationkh()" type="button" 
					 value="确认添加关联" name="btnExport" />	
						&nbsp;&nbsp;&nbsp;&nbsp;
				</td>
			</tr>
		</tbody>
	</table>
	<shove:page curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>
