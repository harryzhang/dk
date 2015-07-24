<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="../css/jbox/Gray/jbox.css" type="text/css"></link>
<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
<script type="text/javascript" src="../css/popom.js"></script>
<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
<script language="javascript" type="text/javascript"
	src="../script/jbox/jquery.jBox-2.3.min.js"></script>
<script type="text/javascript">
	// 跳转详情页面
	function queryAssignmentById(id) {
		$.jBox("iframe:queryRepaymentAssignmentById.do?invest_id=" + id, {
			title : "待还款详情",
			width : 679,
			height : 500,
			buttons : {
				'确定' : true
			}
		});
	}

	//弹出窗口关闭
	function close() {
		ClosePop();
	}
</script>
<div>
	<table id="help" style="width: 100%; color: #333333;" cellspacing="1"
		cellpadding="3" bgcolor="#dee7ef" border="0">
		<tbody>
		<!-- 
			<tr class=gvHeader>
				<th style="width: 60px;" scope="col">
					债权编号
				</th>
				<th style="width: 100px;" scope="col">
					借款人
				</th>
				<th style="width: 100px;" scope="col">
					姓名
				</th>
				<th style="width: 100px;" scope="col">
					借款时间
				</th>
				<th style="width: 100px;" scope="col">
					标的类型
				</th>
				<th style="width: 100px;" scope="col">
					借款标题
				</th>
				<th style="width: 100px;" scope="col">
					期数/总期数
				</th>
				<th style="width: 100px;" scope="col">
					应还时间
				</th>
				<th style="width: 100px;" scope="col">
					应还金额
				</th>
				<th style="width: 100px;" scope="col">
					详情
				</th>
			</tr>
			 -->
			<tr class=gvHeader>
				<th style="width: 60px;" scope="col">
					债权编号
				</th>
				<th style="width: 60px;" scope="col">
					借款编号
				</th>
				<th style="width: 100px;" scope="col">
					债权人
				</th>
				<th style="width: 100px;" scope="col">
					会员号
				</th>
				<th style="width: 100px;" scope="col">
					借款标题
				</th>
				<th style="width: 100px;" scope="col">
					金额
				</th>
				<th style="width: 100px;" scope="col">
					利率
				</th>
				<th style="width: 100px;" scope="col">
					期限
				</th>
				
				<th style="width: 100px;" scope="col">
					起息时间
				</th>
				<th style="width: 100px;" scope="col">
					到期时间
				</th>
				<th style="width: 100px;" scope="col">
					下一还款日
				</th>
				<!-- 
				<th style="width: 100px;" scope="col">
					状态
				</th>
				 -->
				<th style="width: 100px;" scope="col">
					操作
				</th>
			</tr>
			
			<s:if test="pageBean.page==null || pageBean.page.size==0">
				<tr align="center" class="gvItem">
					<td colspan="12">
						暂无数据
					</td>
				</tr>
			</s:if>
			<s:else>
				<s:set name="counts" value="#request.pageNum" />
				<s:iterator value="pageBean.page" var="bean" status="s">
					<tr class="gvItem">
						<td align="center">
							${bean.invest_number}
						</td>
						<td align="center">
							${bean.number}
						</td>
						<td align="center">
							${bean.investor}
						</td>
						<td align="center">
							${bean.id}
						</td>
						<td align="center">
							${bean.borrowTitle}
						</td>
						<td align="center">
							${bean.investAmount }
						</td>
						<td align="center">
							${bean.monthRate} %
						</td>

						<td align="center">
							${bean.deadline}个月
						</td>
						<td align="center">
							${bean.passTime}
						</td>
						<td align="center">
							${bean.endTime}
						</td>
						<td align="center">
							${bean.nextDate}
						</td>
						<td>
							<a href="javascript:queryAssignmentById(${bean.invest_id})">查看详情</a>
						</td>
					</tr>
				</s:iterator>
			</s:else>
		</tbody>
	</table>
</div>
<br>

<shove:page curPage="${pageBean.pageNum}" showPageCount="10"
	pageSize="${pageBean.pageSize }" theme="jsNumber"
	totalCount="${pageBean.totalNum}"></shove:page>

