<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<div>
	<table id="help" style="width: 100%; color: #333333;" cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="0">
		<tbody>
			<tr class=gvHeader>
				<th style="width: 50px;" scope="col">序号</th>
				<th style="width: 120px;" scope="col">用户名</th>
				<th style="width: 120px;" scope="col">汇付会员号</th>
				<th style="width: 100px;" scope="col">真实姓名</th>
				<th style="width: 100px;" scope="col">操作金额</th>
				<th style="width: 80px;" scope="col">手续费</th>
				<th style="width: 100px;" scope="col">操作人员</th>
				<th style="width: 80px;" scope="col">状态</th>
				<th style="width: 140px;" scope="col">操作时间</th>
				<th style="width: 120px;" scope="col">操作</th>
			</tr>
			<s:if test="pageBean.page==null || pageBean.page.size==0">
				<tr align="center" class="gvItem">
					<td colspan="10">暂无数据</td>
				</tr>
			</s:if>
			<s:else>
				<s:set name="counts" value="#request.pageNum" />
				<s:iterator value="pageBean.page" var="bean" status="s">
					<tr class="gvItem">
						<td align="center"><s:property value="#s.count+#counts" /></td>
						<td>${bean.uname}</td>
						<td>${bean.usrCustId}</td>
						<td>${bean.realName}</td>
						<td>${bean.dealMoney}</td>
						<td>${bean.cost}</td>
						<td>${bean.checkUser}</td>
						<td><s:if test="#bean.result==1">
							    成功
							  </s:if> <s:elseif test="#bean.result==0">失败</s:elseif> <s:else>
							    --
							  </s:else></td>
						<td><s:if test="#bean.checkTime==null">--</s:if> <s:else>
								<s:date name="#bean.checkTime" format="yyyy-MM-dd HH:mm:ss" />
							</s:else></td>
						<td onclick="addRecharge('${bean.userId}','${bean.usrCustId }')" style="cursor: pointer;">会员奖励</td>
					</tr>
				</s:iterator>
			</s:else>
			<tr class="gvItem">
				<td colspan="10" align="left"><font size="2">共有${totalNum }条记录</font>
				</td>
			</tr>
		</tbody>
	</table>
</div>
<link rel="stylesheet" href="../css/jbox/Gray/jbox.css" type="text/css"></link>
<script type="text/javascript" src="../script/jbox/jquery.jBox-2.3.min.js"></script>
<script type="text/javascript" src="../script/jbox/jquery.jBox-zh-CN.js"></script>
<script type="text/javascript">
	function addRecharge(userId,usrCustId) {
		if(usrCustId==''||usrCustId==undefined){
			alert("该用户还不是汇付天下会员");
			return;
		}
		$.jBox("iframe:addBackRechargeInit.do?userId=" + userId, {
			title : "会员奖励页面",
			width : 500,
			height : 400,
			buttons : {}
		});
	}
</script>
<shove:page curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>
