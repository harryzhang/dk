<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<%@ include file="/include/includeJs.jsp"%>
<div>
	<table id="help" style="color: #333333; width: 100%" cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="0">
		<tbody>
			<tr class=gvHeader>
				<th scope="col">用户名</th>
				<th scope="col">真实姓名</th>
				<th scope="col">可用余额</th>
				<th scope="col">汇付会员号</th>
				<th scope="col">提现账号</th>
				<th scope="col">提现银行</th>
				<th scope="col">支 行</th>
				<th scope="col">提现总额</th>
				<th scope="col">到账金额</th>
				<th scope="col">手续费</th>
				<th scope="col">提现时间</th>
				<th scope="col">状态</th>
				<th scope="col">操作员</th>
				<th scope="col">操作</th>
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
						<td>${bean.username}</td>
						<td>${bean.realName}</td>
						<td align="center">${bean.usableSum }</td>
						<td>${bean.usrCustId}</td>
						<td>${bean.cardNo}</td>
						<td>${bean.bankName}</td>
						<td>${bean.branchBankName}</td>
						<td>${bean.sum}</td>
						<td>${bean.realSum}</td>
						<td>${bean.poundage }</td>
						<td>${applyTime }</td>
						<td><s:if test="#bean.status==1">成功</s:if> <s:elseif test="#bean.status==0">失败</s:elseif> <s:else>--</s:else></td>
						<td>${admin }</td>
						<td onclick="merCash('${bean.username}','${bean.usrCustId}','${bean.userId }','${bean.cardNo }')" style="cursor: pointer;">取现</td>
					</tr>
				</s:iterator>
			</s:else>
			<tr class="gvItem">
				<td colspan="14" align="left"><font size="2">共有${totalNum }条记录</font>
				</td>
			</tr>
		</tbody>
	</table>
	<shove:page curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>
</div>
<script>
	function merCash(username, usrCustId, userId, cardNo) {
		if (usrCustId == "" || usrCustId == undefined) {
			alert("该用户还不是汇付天下会员");
			return;
		}
		/*if (cardNo == "--" || cardNo == "" || cardNo == undefined) {
			alert("该用户还没有绑定银行卡");
			return;
		}*/
		var param = {};
		param["username"] = username;
		param["usrCustId"] = usrCustId;
		param["userId"] = userId;
		param["cardNo"] = cardNo;
		var url = "iframe:merCashInit.do?userId=" + userId + "&username=" + username + "&usrCustId=" + usrCustId + "&cardNo=" + cardNo;
		$.jBox(url, {
			title : "平台取现申请",
			width : 450,
			height : 260,
			buttons : {}
		});
	}
</script>