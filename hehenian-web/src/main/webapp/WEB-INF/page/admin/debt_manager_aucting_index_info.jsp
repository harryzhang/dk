<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<div>
	<table id="gvNews" style="width: 100%; color: #333333;" cellspacing="1"
		cellpadding="3" bgcolor="#dee7ef" border="0">
		<tbody>
			<tr class=gvHeader>
				<th scope="col">
					序号
				</th>

				<th scope="col">
					借款人
				</th>

				<th scope="col">
					标题
				</th>
				<th scope="col">
					转让人
				</th>
				<th scope="col">
					期限
				</th>


				<th scope="col">
					年利率
				</th>
				<th scope="col">
					债权期限
				</th>
				<th scope="col">
					投资金额
				</th>
				<th scope="col">
					竞拍底价
				</th>
				<th scope="col">
					竞拍最高价
				</th>
				<th scope="col">
					竞拍者
				</th>
				<th scope="col">
					剩余时间
				</th>
				<th style="width: 90px;" scope="col">
					操作
				</th>
			</tr>
			<s:if test="pageBean.page==null || pageBean.page.size==0">
				<tr align="center" class="gvItem">
					<td colspan="13">
						暂无数据
					</td>
				</tr>
			</s:if>
			<s:else>
				<s:set name="counts" value="#request.pageNum" />
				<s:iterator value="pageBean.page" var="bean" status="s">
					<tr class="gvItem">

						<td align="center">
							<s:property value="#s.count+#counts" />
						</td>
						<td align="center">
							${borrowerName}
						</td>

						<td align="center">
							<a href="../financeDetail.do?id=${borrowId }" target="_blank">${borrowTitle}</a>
						</td>
						<td align="center">
							${alienatorName}
						</td>
						<td align="center">
							${deadline}
						</td>

						<td align="center">
							${annualRate}
						</td>
						<td align="center">
							${debtLimit}个月
						</td>
						<td align="center">
							${realAmount}
						</td>
						<td align="center">
							${auctionBasePrice}
						</td>
						<td align="center">
							${auctionHighPrice}
						</td>
						<td align="center">
							${auctionerName}
						</td>
						<td align="center">
							${remainDays }
						</td>
						<td>

							<a href="debtEndSuccess.do?id=${id}">成交</a>
							<a href="${basePath}/queryDebtDetail.do?id=${id}" target="_blank">
								查看</a>
							<a href="cancelManageDebt.do?id=${id}"> 撤回 </a>
						</td>
					</tr>
				</s:iterator>
			</s:else>
		</tbody>
	</table>
</div>
<shove:page curPage="${pageBean.pageNum}" showPageCount="10"
	pageSize="${pageBean.pageSize }" theme="jsNumber"
	totalCount="${pageBean.totalNum}"></shove:page>
