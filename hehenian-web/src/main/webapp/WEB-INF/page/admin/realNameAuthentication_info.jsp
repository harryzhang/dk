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
<%@ include file="/include/includeJs.jsp"%>
		
		<script>
		function closeMthod(){
			window.jBox.close();
			window.location.reload();
		}
		</script>
	<div>
		<table id="gvNews" style="width: 100%; color: #333333;"
			cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="0">
			<tbody>
				<tr class=gvHeader>
					<th scope="col" width="50px">
						序号
					</th>
					<th scope="col" width="80px">
						用户名
					</th>
					<th scope="col" width="75px">
						真实姓名
					</th>
					<th scope="col" width="75px">
						手机号码
					</th>
					<th scope="col" width="60px"> 
						身份证
					</th>
					<th scope="col" width="60px"> 
						用户来源
					</th>
					<th scope="col" width="60px"> 
						银行卡
					</th>
					<th scope="col" width="60px"> 
						邮箱
					</th>
					<th scope="col" width="100px"> 
						居住地址
					</th>
						<th scope="col" width="80px">
						操作
					</th>
				</tr>
				<s:if test="pageBean.page==null || pageBean.page.size==0">
					<tr align="center" class="gvItem">
						<td colspan="10">暂无数据</td>
					</tr>
				</s:if>
				<s:else>
				<s:set name="counts" value="#request.pageNum"/>
				<s:iterator value="pageBean.page" var="bean" status="s">
					<tr class="gvItem">
						<td>
						<s:property value="#s.count+#counts"/>
						</td>
						<td align="center">
							<span style="cursor: pointer;color:#3366CC;" onclick="userInfoAlert(${id },'${username }');">
								${username }
							</span>
						</td>
							<td>
						${realName }
					</td>
							<td>
						${cellPhone }
					</td>
						<td>
							${idNo}
						</td>
						<td>
							<s:if test="#request.source == 0">
								自动导入
							</s:if>
							<s:if test="#request.source == 1">
								网站注册
							</s:if>
						</td>
						<td>
							<s:if test="#request.bankCarStatus==0">未认证</s:if>
							<s:if test="#request.bankCarStatus==1">已认证</s:if>
						</td>
						<td>
							<s:if test="#request.email==''">未填写</s:if>
							<s:else>已填写</s:else>
					    </td>
						<td>
							<s:if test="#request.address==''">未填写</s:if>
							<s:else>已填写</s:else>
						</td>
						<td>
							<s:if test="#request.auditStatus == 1"><span style="cursor: pointer;" onclick="realNameAuthentication_info_alert(${id },'${username}');">审核</span></s:if>
							<s:if test="#request.auditStatus == 2"><span style="cursor: pointer;" >审核不通过</span></s:if>
							<s:if test="#request.auditStatus == 3"><span style="cursor: pointer;" onclick="realNameAuthentication_info_select(${id },'${username}');">查看</span></s:if>
					</td>
					</tr>
				</s:iterator>
				</s:else>
			</tbody>
		</table>
	</div>
<shove:page curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>