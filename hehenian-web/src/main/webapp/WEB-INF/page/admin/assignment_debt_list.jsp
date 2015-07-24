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
	function queryAssignmentById(id) {
		$.jBox("iframe:queryAssignmentById.do?id=" + id, {
			title : "借款管理-债权详细信息",
			width : 700,
			height : 450,
			buttons : {
			}
		});
	}
	
	//债权转让
	function queryByAssignment(id) {
		$.jBox("iframe:queryByAssignment.do?id=" + id, {
			title : "债权转让",
			width : 600,
			height : 500,
			top:25,
			buttons : {
				
			}
		});
	}

	//债权拆分
	function queryAssignmentSplit(id) {
		$.jBox("iframe:queryAssignmentSplit.do?id=" + id, {
			title : "债权拆分",
			width : 650,
			height : 500,
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
		

	//返回到列表页
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
				<!-- 
				<th style="width: 100px;" scope="col">
					真实姓名
				</th>
				<th style="width: 100px;" scope="col">
					来源
				</th>
				 
				<th style="width: 100px;" scope="col">
					标的类型
				</th>
				-->
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
				<!-- 		<a href="queryUserFundRepayInit.do?id=${bean.invest_number}" style="cursor: pointer;color:#3366CC;">${bean.invest_number}</a>   -->
							<a href="queryUserFundRepayInit.do?numberId=${bean.number}" style="cursor: pointer;color:#3366CC;">${bean.invest_number}</a>
						</td>
						<td align="center">
							${bean.number}
						</td>
						<td align="center">
							${bean.investor}
						</td>
						<td align="center">
							${bean.tubId}
						</td>
						<!-- 
						<td align="center">
							<a href="javascript:queryAssignmentById.do?Id=${bean.id}"><span style="cursor: pointer;color:#3366CC;">${bean.username}</span></a>
							<a href="javascript:queryAssignmentById(${bean.id})"><span style="cursor: pointer;color:#3366CC;">${bean.username}</span></a>
						</td>
						 
						<td align="center">
							${bean.realName}
						</td>
						
						<td align="center">
							<s:if test="#bean.source==0">自动导入</s:if>
							<s:if test="#bean.source==1">网站注册</s:if>
							<s:if test="#bean.source==2">手动录入</s:if>
						</td>
						<td align="center">
								<s:if test="#bean.borrowWay==1">薪金贷</s:if>
							<s:if test="#bean.borrowWay==2">生意贷</s:if>
							<s:if test="#bean.borrowWay==3">业主借款</s:if>
						</td>
						-->
						<td align="center">
							${bean.borrowTitle}
						</td>
						<td align="center">
							${bean.realAmount}
						</td>
						<td align="center">
							${bean.monthRate} %
						</td>
						<td align="center">
							${bean.deadline}个月
						</td>
						<td align="center">
							${bean.auditTime}
						</td>
						<td align="center">
							${bean.endTime}
						</td>
						<td align="center">
							${bean.nextDate}
						</td>
						<!-- 
						<td align="center">
							<s:if test="#bean.isDebt==1 && #bean.distinguish_debt == 1">未转让</s:if>
							<s:if test="#bean.isDebt==1 && #bean.distinguish_debt == 0">未转让<font size="1">(前台申请)</font></s:if>
							<s:if test="#bean.isDebt==2">已转让</s:if>
							<s:if test="#bean.isDebt==3">转让中</s:if>
						</td>
						 -->
						<td>
						
							<s:if test="#bean.isDebt==1">
<%--								<a href="javascript:queryAssignmentSplit(${bean.id});" target="main">拆分--%>
<%--								</a>/--%>
							<a href="javascript:queryByAssignment(${bean.id});" target="main">转让</a>
							</s:if>
							<s:else>
<%--							拆分&nbsp;/&nbsp;转让--%>转让
						</s:else>
						 
							<a href="javascript:queryAssignmentById(${bean.id})">查看</a>
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

