<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
		<div> 
		<table id="help" style=" color: #333333;width:100%"
			cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="0">
			<tbody>
				<tr class=gvHeader>
				<th style="width: 35px;" scope="col">
						选中
					</th>
					<th style="width: 30px;" scope="col">
						序号
					</th>
					<th style="width: 120px;" scope="col">
						用户名
					</th>
					<th style="width: 120px;" scope="col">
						真实姓名
					</th>
					
					<th style="width: 120px;" scope="col">
						提现账号
					</th>
					<th style="width: 120px;" scope="col">
						提现银行
					</th>
					<th style="width: 160px;" scope="col">
						支  行
					</th>
					<th style="width: 80px;" scope="col">
						提现总额
					</th>
					<th style="width: 80px;" scope="col">
						到账金额
					</th>
					<th style="width: 80px;" scope="col">
						手续费
					</th>
					<th style="width: 120px;" scope="col">
						提现时间
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
						<td colspan="13">暂无数据</td>
					</tr>
				</s:if>
				<s:else>
				<s:set name="counts" value="#request.pageNum"/>
					<s:iterator value="pageBean.page" var="bean" status="s">
						<tr class="gvItem">
						   <td>
								<input id="gvNews_ctl02_cbID" class="helpId" type="checkbox"
									value="${bean.id };${bean.sum};${bean.poundage };${bean.userId }" name="cb_ids" />
							</td>
							<td align="center" style="width:100px;">
								<s:property value="#s.count+#counts"/>
							</td>
							<td>
								${bean.name}
							</td>
							<td>
								${bean.realName}
							</td>
							<td>
							  ${bean.acount}
							</td>
							<td>
								${bean.bankName}
							</td>
							<td>
								${bean.branchBankName}
							</td>
							<td>
								${bean.sum}
							</td>
							<td>
								${bean.realMoney}
							</td>
							<td>
								${bean.poundage }
							</td>
							<td>
							  <s:date name="#bean.applyTime" format="yyyy-MM-dd HH:mm:ss" ></s:date>
							</td>
							<td>
							<s:if test="#bean.status==1">审核中</s:if>
							  <s:elseif test="#bean.status==2">已提现</s:elseif>
							  <s:elseif test="#bean.status==3">取消</s:elseif>
							  <s:elseif test="#bean.status==4">转账中</s:elseif>
							  <s:elseif test="#bean.status==5">失败</s:elseif>
							</td>
							<td>
							 <a href="javascript:withdraw_trans(${bean.id });"> 转账 </a>
							</td>
						</tr>
					</s:iterator>
				</s:else>
			</tbody>
		</table>
		<table style="border-collapse: collapse; border-color: #cccccc"
		cellspacing="1" cellpadding="3" width="100%" align="center" border="1">
		<tbody>
			<tr>
				<td class="blue12" style="padding-left: 8px" align="left">
					<input id="chkAll" onclick="checkAll(this); " type="checkbox" value="checkbox" name="chkAll" />
					全选 &nbsp;&nbsp;&nbsp;&nbsp;
					<input id="btnTrans" onclick="updates();" type="button"
						value="成功转账选中记录" name="btnDel" />
				</td>
			</tr>
		</tbody>
	</table>
	
	
	<shove:page curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>
</div>

	<script type="text/javascript" src="../css/admin/popom.js"></script>
	<script>
		function withdraw_trans(wid){
		    var url = "queryWithdrawTransInfo.do?wid="+wid;
              ShowIframe("转账审核",url,600,600);
		}
	</script>