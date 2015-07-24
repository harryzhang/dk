<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<%@ include file="/include/includeJs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script type="text/javascript">
	//选择所有
	function checkAll(e) {
		if (e.checked) {
			$(".downloadId").attr("checked", "checked");
		} else {
			$(".downloadId").removeAttr("checked");
		}
	}

	//查看
	function queryBringById(id) {
		$.jBox("iframe:queryBringById.do?id=" + id, {
			title : "撮合的借款",
			width : 600,
			height : 520,
			top : 25,
			buttons : {

			}
		});
	}
	//查看
	function queryInvestPrecent(id) {
		$.jBox("iframe:queryInvestPrecentById.do?id=" + id, {
			title : "投标进度",
			width : 600,
			height : 380,
			buttons : {
				'关闭':true
			}
		});
	}

	//弹出窗口关闭
	function closeMthod() {
		window.jBox.close();
		window.location.reload();
	}

	//取消--关闭弹窗
	function closeMthodes() {
		window.jBox.close();
	}
	
	//返回列表
	function reload()
	{
		window.location.href="queryBringListIndex.do";
	}
</script>
<div id="hhnhhn">
	<table id="help" style="width: 100%; color: #333333;" cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="0">
		<tbody>
			<tr class=gvHeader>
				<th style="width: 35px;" scope="col">选中</th>
				<th style="width: 80px;" scope="col">借款编号</th>
	<!-- 		<th style="width: 180px;" scope="col">用户名</th>  -->
				<th style="width: 120px;" scope="col">真实姓名</th>
	<!-- 		<th style="width: 120px;" scope="col">借款来源</th>   -->
				<th style="width: 120px;" scope="col">标的类型</th>
				<th style="width: 100px;" scope="col">借款标题</th>
				<th style="width: 100px;" scope="col">借款金额</th>
				<th style="width: 100px;" scope="col">利率</th>
				<th style="width: 100px;" scope="col">期限</th>
				<th style="width: 100px;" scope="col">筹标期限</th>
				<th style="width: 100px;" scope="col">投标进度</th>
				<th style="width: 140px;" scope="col">复审人</th>
				<th style="width: 150px;" scope="col">操作</th>
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
						<td align="center"><input id="gvNews_ctl02_cbID" class="downloadId" type="checkbox" value="${bean.id }" name="cb_ids" col="${bean.collagen}" /></td>
						<td align="center">${bean.number}</td>
			<!-- 		<td align="center"><span onclick="queryBringById(${bean.id});" style="cursor: pointer; color: #3366CC;">${bean.username}</span></td>  -->
						<td align="center">${bean.realName}</td>
		<!-- 			<td align="center"><s:if test="#bean.source==0">小贷公司导入</s:if> <s:if test="#bean.source==1">网站注册</s:if> <s:if test="#bean.source==2">彩生活用户</s:if> <s:if test="#bean.source==3">净值用户</s:if>  -->
						</td>
						<td align="center"><s:if test="#bean.borrowWay==1">薪金贷</s:if> <s:if test="#bean.borrowWay==2">生意贷</s:if> <s:if test="#bean.borrowWay==3">业主贷</s:if></td>
						<td align="center">${bean.borrowTitle}</td>
						<td align="center">${bean.borrowAmount}</td>
						<td align="center">${bean.annualRate}&nbsp;%</td>
						<td align="center">${bean.deadline}个月</td>
						<td align="center">${bean.raiseTerm}天</td>
						<td align="center" >
						<a href="javascript:queryInvestPrecent(${bean.id})" style="color: #3366CC;"><fmt:formatNumber type="percent" value="${bean.collagen}"></fmt:formatNumber></a>
						</td>
						<td align="center">${bean.recheck}</td>
						<td><a href="javascript:queryBringById(${bean.id})">查看</a></td>
					</tr>
				</s:iterator>
			</s:else>
		</tbody>
	</table>
</div>
<br>
<shove:page curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>
