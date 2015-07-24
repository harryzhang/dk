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
				 <%--   <th style="width: 35px;" scope="col">
						选中
					</th>--%>
					<th style="width: 50px;" scope="col">
						序号
					</th>
					<th style="width: 200px;" scope="col">
						用户名
					</th>
					<th style="width: 150px;" scope="col">
						充值类型
					</th>
					
					<th style="width: 100px;" scope="col">
						充值金额
					</th>
					<th style="width: 80px;" scope="col">
						费率
					</th>
					<th style="width: 100px;" scope="col">
						到账金额
					</th>
					<th style="width: 120px;" scope="col">
						充值时间
					</th>
					
					<th style="width: 80px;" scope="col">
						状态
					</th>
					
					<th style="width: 80px;" scope="col">
						操作
					</th>
				</tr>
				<s:if test="pageBean.page==null || pageBean.page.size==0">
					<tr align="center" class="gvItem">
						<td colspan="10">暂无数据</td>
					</tr>
				</s:if>
				<s:else>
				<s:set name="counts" value="#request.pageNum"/>
					<s:iterator value="pageBean.page" var="bean" status="s">
						<tr class="gvItem">
						  <%--  <td>
								<input id="gvNews_ctl02_cbID" class="helpId" type="checkbox"
									value="${bean.id }" name="cb_ids" />
							</td>--%>
							<td align="center" style="width:100px;">
								<s:property value="#s.count+#counts"/>
							</td>
							<td>
								${bean.username}
							</td>
							<td>
							<s:if test="#bean.rechargeType==1">支付宝支付</s:if>
							<s:if test="#bean.rechargeType==2">环迅支付</s:if>
							<s:if test="#bean.rechargeType==3">国付宝</s:if>		
						     <s:if test="#bean.rechargeType==4">线下充值</s:if>
						     <s:if test="#bean.rechargeType==5">手工充值</s:if>
							<s:if test="#bean.rechargeType==6">虚拟充值</s:if>
							<s:if test="#bean.rechargeType==7">奖励充值</s:if>
							</td>
							<td>
								${bean.rechargeMoney}
							</td>
							<td>
								${bean.cost}
							</td>
							<td>
								${bean.realMoney}
							</td>
							<td>
								<s:date name="#bean.rechargeTime" format="yyyy-MM-dd HH:mm:ss" ></s:date>
							</td>
							<td>
							
							<s:if test="#bean.result==0">失败</s:if>
							<s:if test="#bean.result==1">成功</s:if>
							<s:if test="#bean.result==2">审核中</s:if>	
							<s:if test="#bean.result==3">审核中</s:if>	
							</td>
							<td>
								<a href="javascript:show(${bean.id });">查看</a>
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
					<font size="2">共有${totalNum }条记录</font>
				<%-- 	<input id="chkAll" class="helpId" onclick="checkAll(this); " type="checkbox" value="checkbox" name="chkAll" />
					全选 &nbsp;&nbsp;&nbsp;&nbsp;
					<input id="btnExport" onclick="" type="button" 
					 value="导出选中记录" name="btnExport" />	--%>
						&nbsp;&nbsp;&nbsp;&nbsp;
					<input id="excel" onclick="" type="button"
						value="导出EXCEL" name="btnExportExcel" />
					
				</td>
			</tr>
		</tbody>
	</table>
	<shove:page curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>
