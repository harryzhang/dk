<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<%@ include file="/include/includeJs.jsp"%>
<div>
	<table id="help" style="color: #333333; width: 100%" cellspacing="1"
		cellpadding="3" bgcolor="#dee7ef" border="0">
		<tbody>
			<tr class=gvHeader>
				<th scope="col">用户名</th>
				<th scope="col">真实姓名</th>
				<th scope="col">可用余额</th>

				<th scope="col">提现账号</th>
				<th scope="col">提现银行</th>
				<th scope="col">支 行</th>
				<th scope="col">提现总额</th>
				<th scope="col">到账金额</th>
				<th scope="col">手续费</th>
				<th scope="col">提现时间</th>
				<th scope="col">状态</th>
				<th scope="col">操作</th>
				<th scope="col">审核人</th>
			</tr>
			<s:if test="pageBean.page==null || pageBean.page.size==0">
				<tr align="center" class="gvItem">
					<td colspan="14">暂无数据</td>
				</tr>
			</s:if>
			<s:else>
				<s:set name="counts" value="#request.pageNum" />
				<s:iterator value="pageBean.page" var="bean" status="s">
					<tr class="gvItem">
						<td>${bean.name}</td>
						<td>${bean.realName}</td>
						<td align="center">${bean.usableSum }</td>
						<td>${bean.acount}</td>
						<td>${bean.bankName}</td>
						<td>${bean.branchBankName}</td>
						<td>${bean.sum}</td>
						<td>${bean.sum}</td>
						<td>${bean.poundage }</td>
						<td><s:date name="#bean.applyTime"
								format="yyyy-MM-dd HH:mm:ss"></s:date></td>

						<td><s:if test="#bean.status==1">初审中</s:if> <s:elseif
								test="#bean.status==2">成功</s:elseif> <s:elseif
								test="#bean.status==3">已取消</s:elseif> <s:elseif                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              
								test="#bean.status==4">待转账</s:elseif> <s:elseif
								test="#bean.status==5">失败</s:elseif>
								<s:else>初始</s:else>
						</td>
						<td><s:if test="#bean.status==1">
								<a href="javascript:withdraw_check(${bean.id });">审核</a>
							</s:if> <s:elseif test="#bean.status==4">
								<a href="javascript:withdraw_trans(${bean.id });"> 转账</a>
							</s:elseif> <s:else>
								<a href="javascript:withdraw_show(${bean.id });">查看</a>
							</s:else></td>

						<td>${bean.checkname }</td>

					</tr>
				</s:iterator>
			</s:else>
			<tr class="gvItem">
				<td colspan="14" align="left"><font size="2">共有${totalNum
						}条记录</font></td>
			</tr>
		</tbody>
	</table>

	<shove:page curPage="${pageBean.pageNum}" showPageCount="10"
		pageSize="${pageBean.pageSize }" theme="jsNumber"
		totalCount="${pageBean.totalNum}"></shove:page>

</div>

<script type="text/javascript" src="../css/admin/popom.js"></script>
<script>
	function withdraw_check(wid) {
	$.jBox("iframe:queryWithdrawInfo.do?wid=" + wid, {
			title : "提现审核",
			width : 600,
			height : 520,
			buttons : {}
		});
	}

	function withdraw_trans(wid) {
	$.jBox("iframe:queryWithdrawTransInfo.do?wid=" + wid, {
			title : "转账审核",
			width : 600,
			height : 400,
			buttons : {}
		});
	}

	function withdraw_show(wid) {
	$.jBox("iframe:queryWithdrawShowInfo.do?wid=" + wid, {
			title : "提现查看",
			width : 600,
			height : 400,
			buttons : {'关闭':true}
		});
	}
</script>