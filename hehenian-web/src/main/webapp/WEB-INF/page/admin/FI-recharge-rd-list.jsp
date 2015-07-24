<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<div>
	<table id="help" style="width: 100%; color: #333333;" cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="0">
		<tbody>
			<tr class=gvHeader>
				<th style="width: 50px;" scope="col">序号</th>
				<th style="width: 100px;" scope="col">用户名</th>
				<th style="width: 150px;" scope="col">真实姓名</th>
				<th style="width: 80px;" scope="col">类型</th>
				<th style="width: 80px;" scope="col">操作金额</th>
				<th style="width: 140px;" scope="col">备注</th>
				<th style="width: 80px;" scope="col">操作人员</th>
				<th style="width: 140px;" scope="col">操作时间</th>
				<th style="width: 50px;" scope="col">状态</th>
				<th style="width: 80px;" scope="col" colspan="2">操作</th>
			</tr>
			<s:if test="pageBean.page==null || pageBean.page.size==0">
				<tr align="center" class="gvItem">
					<td colspan="11">暂无数据</td>
				</tr>
			</s:if>
			<s:else>
				<s:set name="counts" value="#request.pageNum" />
				<s:iterator value="pageBean.page" var="bean" status="s">
					<tr class="gvItem">
						<td align="center"><s:property value="#s.count+#counts" /></td>
						<td>${bean.uname}</td>
						<td>${bean.realName}</td>
						<td><s:if test="#bean.type==1">
							    手动充值
							  </s:if> <s:elseif test="#bean.type==3">商户代扣</s:elseif> <s:else>
							   --
							  </s:else></td>
						<td>${bean.dealMoney}</td>
						<td>${bean.remark}</td>
						<td>${bean.userName}</td>
						<td><s:if test="#bean.checkTime==null">--</s:if>
						<s:else><s:date name="#bean.checkTime" format="yyyy-MM-dd HH:mm:ss" /></s:else>
						</td>
						<td><s:if test="#bean.result==1">成功</s:if>
						<s:elseif test="#bean.result==0">失败</s:elseif>
						</td>
						<td style="width: 40px;"><a href="javascript:show(${bean.id});">查看</a></td>
						<td style="width: 40px;"><a href="javascript:addUserRecharge(${bean.userId});" >充值</a></td>
					</tr>
				</s:iterator>
			</s:else>
		</tbody>
	</table>
</div>
<shove:page curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>
