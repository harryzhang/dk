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
					<th style="width: 35px;" scope="col">
						id
					</th>
					<th scope="col">
						用户账号
					</th>
					<th style="width: 150px;" scope="col">
						真实姓名
					</th>
					<th style="width: 200px;" scope="col">
						身份证认证
					</th>
					<th style="width: 150px;" scope="col">
						工作认证
					</th>
					<th style="width: 70px;" scope="col">
						信用报告验证
					</th>
					<th style="width: 170px;" scope="col">
						居住地验证
					</th>
						<th style="width: 170px;" scope="col">
						收入验证
					</th>
						<th style="width: 170px;" scope="col">
						跟踪审核
					</th>
						<th style="width: 170px;" scope="col">
						查看
					</th>
				</tr>
				<s:if test="pageBean.page==null || pageBean.page.size==0">
					<tr align="center" class="gvItem">
						<td colspan="7">暂无数据</td>
					</tr>
				</s:if>
				<s:else>
				
				<s:iterator value="pageBean.page" var="bean" status="sta">
					<tr class="gvItem">
						<td>
							${sta.count }
						</td>
						<td align="center">
							${username}
						</td>
						<td>
							${realName}
						</td>
						<td>
						  <s:if test="#bean.auditStatus==1">
							等待审核
							</s:if>
							<s:elseif test="#bean.status==3">
							审通过
							</s:elseif>
							<s:else>
							审核通过
							</s:else>
						
							<s:date name="#bean.addDate" format="yyyy-MM-dd HH:mm:ss" />
						</td>
						<td>
							
						</td>
						<td>
					
							
					</td>
						<td>
							${bean.number }
					</td>
						<td>
							${bean.number }
					</td>
						<td>
							${bean.number }
					</td>
						<td>
							${bean.number }
					</td>
					</tr>
				</s:iterator>
				</s:else>
			</tbody>
		</table>
	</div>
	<shove:page curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>
