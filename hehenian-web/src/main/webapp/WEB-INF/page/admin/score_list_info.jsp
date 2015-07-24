<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<%@ include file="/include/includeJs.jsp"%>
<style>
.content {
	display: none;
	position: fixed;
	top: 50%;
	margin-top: -250px;
	background: rgb(102, 198, 246);
	z-index: 3;
	left: 50%;
	margin-left: -200px;
	border: 1px red solid;
	font-size: 12px;
	color: #3e4959;
	width: auto;
	height: auto;
}
</style>
<script>
	function showScore(id) {
		$.jBox("iframe:queryScoreByID.do?id=" + id, {
			title : "查看问卷结果",
			top : "2%",
			width : 800,
			height : 550,
			buttons : {
				'关闭' : true
			}
		});
	}
</script>
<div>
	<table id="help" style="width: 100%; color: #333333;" cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="0">
		<tbody>
			<tr class=gvHeader>
				<th style="width: 35px;" scope="col">选中</th>
				<th style="width: 50px;" scope="col">编号</th>
				<th style="width: 100px;" scope="col">用户名</th>
				<th style="width: 50px;" scope="col">用户真实姓名</th>
				<th style="width: 150px;" scope="col">投资经验</th>
				<th style="width: 50px;" scope="col">投资目标</th>
				<th style="width: 50px;" scope="col">风险级别</th>
				<th style="width: 50px;" scope="col">风险偏好</th>
				<th style="width: 120px;" scope="col">操作</th>
			</tr>
			<s:if test="pageBean.page==null || pageBean.page.size==0">
				<tr align="center" class="gvItem">
					<td colspan="9">暂无数据</td>
				</tr>
			</s:if>
			<s:else>
				<s:set name="counts" value="#request.pageNum" />
				<s:iterator value="pageBean.page" var="bean" status="s">
					<tr class="gvItem">
						<td><input id="gvNews_ctl02_cbID" class="downloadId" type="checkbox" value="${bean.id }" name="cb_ids" /></td>
						<td><s:property value="#s.count+#counts" /></td>
						<td>${bean.username}</td>
						<td>${bean.realName}</td>
						<td>${bean.experience}</td>
						<td>${bean.aim}</td>
						<td>${bean.levels}级</td>
						<td>${bean.attitude}</td>
						<td><a href="javascript:showScore(${bean.id })">查看</a></td>
					</tr>
				</s:iterator>
			</s:else>
		</tbody>
	</table>
</div>
<div class="content" id="content"></div>
<table style="border-collapse: collapse; border-color: #cccccc" cellspacing="1" cellpadding="3" width="100%" align="center" border="0">
	<tbody>
		<tr>
			<td class="blue12" style="padding-left: 8px" align="left">
			<input id="chkAll" onclick="checkAll(this); " type="checkbox" value="checkbox" name="chkAll" style="vertical-align: middle;"/> 
			<input id="excel" type="button" value="导出所选数据" onclick="excel()"   style="vertical-align: middle;"/>
			</td>
		</tr>
	</tbody>
</table>
<shove:page curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>
