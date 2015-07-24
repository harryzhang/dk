<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<%@ include file="/include/includeJs.jsp"%>
<script type="text/javascript">
	//查看
	function borrowFullScaleDetail(id) {
		$.jBox("iframe:borrowFullScaleDetail.do?id=" + id, {
			title : "满标的借款",
			width : 630,
			height : 600,
			top : 25,
			buttons : {

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
</script>
<div style="padding: 15px 10px 0px 10px;">
		<table id="gvNews" style="width: 100%; color: #333333;"
			cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="0">
			<tbody>
				<tr class=gvHeader>
					<th style="width: 150px;" scope="col">
						借款编号
					</th>
			<!-- 		<th style="width: 150px;" scope="col">

	<table id="gvNews" style="width: 100%; color: #333333;" cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="0">
		<tbody>
			<tr class=gvHeader>
				<th style="width: 80px;" scope="col">借款编号</th>
				<!-- 		<th style="width: 150px;" scope="col">
						用户名
					</th>
			 -->
				<th style="width: 150px;" scope="col">真实姓名</th>
				<!-- 		
					<th style="width: 200px;" scope="col">
						通过认证数量
					</th>
			 -->
				<th style="width: 80px;" scope="col">标的类型</th>
				<th style="width: 80px;" scope="col">借款标题</th>
				<th style="width: 80px;" scope="col">借款金额</th>
				<th style="width: 80px;" scope="col">利率</th>
				<th style="width: 80px;" scope="col">期限</th>
				<th style="width: 80px;" scope="col">筹标期限</th>
				<th style="width: 80px;" scope="col">状态</th>
				<th style="width: 80px;" scope="col">操作</th>
			</tr>
			<s:if test="pageBean.page==null || pageBean.page.size==0">
				<tr align="center" class="gvItem">
					<td colspan="12">暂无数据</td>
				</tr>
			</s:if>
			<s:else>
				<s:set name="acounts" value="#request.pageNum" />
				<s:iterator value="pageBean.page" var="bean" status="s">
					<tr class="gvItem">
						<td>${number}</td>
						<!-- 
						<td>
							<s:property value="#s.count+#acounts"/>
						</td>
						
						<td align="center">
							${username}
						</td>
					 -->
						<td>${realName}</td>
						<!-- 
						<td>
						   <s:if test="#bean.counts==null">
							<span class="require-field">0</span>个
							</s:if>
							<s:else>
							 ${counts}个
							</s:else>
						</td>
						 -->
						<td><s:if test="#bean.borrowWay==1">
							薪金贷
							</s:if> <s:elseif test="#bean.borrowWay==2">
							生意贷
							</s:elseif> <s:elseif test="#bean.borrowWay==3">
							业主贷
							</s:elseif></td>
						<td><a href="../financeDetail.do?id=${id }" target="_blank">${borrowTitle}</a></td>
						<td>${borrowAmount}</td>
						<td>${annualRate}%</td>
						<td>${deadline } <s:if test="%{#bean.isDayThe ==1}">个月</s:if>
							<s:else>天</s:else></td>
						<td>${raiseTerm }天</td>
						<td>
						<s:if test="#request.borrowStatus == 3">未放款</s:if>
							<s:if test="#request.borrowStatus == 4 || #request.borrowStatus == 5 ">已放款</s:if>
							</td>
						<td>
						<s:if test="#request.borrowStatus == 3">
								<a href="javascript:borrowFullScaleDetail(${id})">放款</a>
							</s:if> <s:if test="#request.borrowStatus == 4 || #request.borrowStatus == 5 ">
								<a href="javascript:borrowFullScaleDetail(${id})">查看</a>
							</s:if></td>
					</tr>
				</s:iterator>
			</s:else>
		</tbody>
	</table>
</div>
<shove:page curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>
