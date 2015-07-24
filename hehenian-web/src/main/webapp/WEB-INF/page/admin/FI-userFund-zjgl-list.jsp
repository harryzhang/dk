<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<script type="text/javascript" src="../css/popom.js"></script>
<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
<script type="text/javascript" src="../css/admin/popom.js"></script>
<link rel="stylesheet" href="../css/jbox/Gray/jbox.css" type="text/css"></link>
<script language="javascript" type="text/javascript" src="../script/jbox/jquery.jBox-2.3.min.js"></script>
<script type="text/javascript">

	function select_IncomeInfo(id){
		$.jBox("iframe:queryUserIncomeInfo.do?userId="+id, {
		    title: "用户信息详情",
		    width: 679,
		    height: 500,
		    buttons: { '关闭': true }
		});
	}
		

	function initCallBack(data){
 		$("#dataInfo").html(data);
	}
		//弹出窗口关闭
   		function close(){
   			 ClosePop();  			  
   		}
</script>
<div style="overflow-x: auto; overflow-y: auto; width: 100%;">
	<table id="help" style="width: 1020px; color: #333333;" cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="0">
		<tbody>
			<tr class=gvHeader>
				<th style="width: 50px;" scope="col">编号</th>
				<th style="width: 160px;" scope="col">用户名</th>
				<th style="width: 180px;" scope="col">真实姓名</th>
				<th style="width: 180px;" scope="col">身份证号</th>
				<th style="width: 120px;" scope="col">投资收益曲线</th>
				<th style="width: 100px;" scope="col">借款详情</th>
				<th style="width: 100px;" scope="col">投资详情</th>
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
						<td>${bean.username}</td>
						<td>${bean.realName}</td>
						<td>${bean.idNo}</td>
						<td onclick="select_IncomeInfo(${bean.userId});" style="cursor: pointer;">查看</td>
						<td><a href="queryUserFundBorrowInit.do?userId=${bean.userId}" target="main">查看</a></td>
						<td><a href="queryUserFundInvestInit.do?userId=${bean.userId}" target="main">查看</a></td>
					</tr>
				</s:iterator>
			</s:else>
		</tbody>
	</table>
	<shove:page curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>
</div>