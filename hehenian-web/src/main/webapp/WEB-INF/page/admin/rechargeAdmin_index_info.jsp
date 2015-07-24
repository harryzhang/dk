<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<html>
	<head>
		
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="This is my page" />
		<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" type="text/css" href="../common/date/calendar.css"/>
		<script type="text/javascript" src="../common/date/calendar.js"></script>
		<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
		<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
	<div>
		<table id="gvNews" style="width: 100%; color: #333333;"
			cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="0">
			<tbody>
				<tr class=gvHeader>
				 
					<th scope="col" >
						用户账号
					</th>
					<th  scope="col">
						真实姓名
					</th>
						<th  scope="col">
						手机号码
					</th>
					<th  scope="col">
						身份证
					</th>
					<th  scope="col">
						充值金额
					</th>
						<th  scope="col">
						交易编号
					</th>
					<th  scope="col">
						充值银行
					</th>
						<th  scope="col" width="200">
						线下充值备注
					</th>
						<th scope="col">
						提交时间
					</th>
						<th  scope="col">
						审核状态
					</th>
						<th  scope="col">
						操作
					</th>
					
				</tr>
				<s:if test="pageBean.page==null || pageBean.page.size==0">
					<tr align="center" class="gvItem">
						<td colspan="10">暂无数据</td>
					</tr>
				</s:if>
				<s:else>
				
				<s:iterator value="pageBean.page" var="bean" status="sta">
					<tr class="gvItem">
						<td align="center">
							<a>${username}</a>
						</td>
							<td>
						${realName }
					</td>
					<td>
							${mobilePhone}
						</td>
						<td>
							${idNo}
						</td>
						<td>
								${rechargeMoney}
						</td>
						<td style="width:200px;white-space:normal;word-spacing:normal;word-wrap:break-word;word-break:break-all">
						${rechargeNumber}
						</td>
						<td>
								${bankName}
						</td>
						<td style="width:200px;white-space:normal;word-spacing:normal;word-wrap:break-word;word-break:break-all">
					${remark}
					</td>
						<td>
					${rechargeTime}
					</td>
						<td>
						<s:if test="#bean.result==0">充值失败</s:if>
						<s:if test="#bean.result==1">充值成功</s:if>
						<s:if test="#bean.result==3">审核中</s:if>
					</td>
						<td>
						<a href="queryrechargeAdminInit.do?id=${id}">${result==3?'审核':'查看'}</a>
					</td>
					</tr>
				</s:iterator>
				</s:else>
			</tbody>
		</table>
	</div>
<shove:page curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>