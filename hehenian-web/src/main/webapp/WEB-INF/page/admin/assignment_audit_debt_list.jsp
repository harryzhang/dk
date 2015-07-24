<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="../css/jbox/Gray/jbox.css" type="text/css"></link>
<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
<script type="text/javascript" src="../css/popom.js"></script>
<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
<script language="javascript" type="text/javascript" src="../script/jbox/jquery.jBox-2.3.min.js"></script>
<script type="text/javascript">
	// 跳转详情页面
	//债权转让
	function queryByAssignment(id) {
		$.jBox("iframe:showAuditDebt.do?id=" + id, {
			title : "债权转让审核",
			width : 600,
			height : 540,
			top:25,
			buttons : {
				
			}
		});
	}
	//弹出窗口关闭
	function closeMthod(){
			window.jBox.close();
			window.location.reload();
		}
		
		//取消--关闭弹窗
	function closeMthodes(){
			window.jBox.close();
		}

	function reloadList() {
		window.location.href = "findUserReviewIndex.do";
	}

</script>
<div>
	<table id="help" style="width: 100%; color: #333333;" cellspacing="1"
		cellpadding="3" bgcolor="#dee7ef" border="0">
		<tbody>
			<tr class=gvHeader>
				<th style="width: 60px;" scope="col">
					借款者
				</th>
				<th style="width: 100px;" scope="col">
					标题
				</th>
				<th style="width: 100px;" scope="col">
					年利率
				</th>
				<th style="width: 100px;" scope="col">
					债权期限
				</th>
				<th style="width: 100px;" scope="col">
					转让人
				</th>
				<td style="width: 100px;" scope="col">
					投资金额
				</td>
				<th style="width: 100px;" scope="col">
					转让金额
				</th>
				<th style="width: 100px;" scope="col">
					申请时间
				</th>
				<th style="width: 100px;" scope="col">
					操作
				</th>
			</tr>
			<s:if test="pageBean.page==null || pageBean.page.size==0">
				<tr align="center" class="gvItem">
					<td colspan="11">
						暂无数据
					</td>
				</tr>
			</s:if>
			<s:else>
				<s:set name="counts" value="#request.pageNum" />
				<s:iterator value="pageBean.page" var="bean" status="s">
					<tr class="gvItem">
						<td align="center">
							${ bean.publisher}
						</td>
						<td align="center">
							${bean.borrowTitle}
						</td>
						<td align="center">
						${bean.annualRate }
						</td>
						<td align="center">
						${bean.debtLimit }
						</td>
						<td align="center">
						${ bean.username}
						</td>
						<td align="center">
							${bean.debtSum}
						</td>
						<td align="center">
							${bean.auctionBasePrice}
						</td>
						<td align="center">
						<s:date name="#bean.applyTime" format="yyyy-MM-dd HH:mm:ss"/>
						</td>
						<td>
							<a href="javascript:queryByAssignment(${bean.id})">查看</a>
						</td>
					</tr>
				</s:iterator>
			</s:else>
		</tbody>
	</table>
</div>
<br>
<script>
	
</script>

<shove:page curPage="${pageBean.pageNum}" showPageCount="10"
	pageSize="${pageBean.pageSize }" theme="jsNumber"
	totalCount="${pageBean.totalNum}"></shove:page>

