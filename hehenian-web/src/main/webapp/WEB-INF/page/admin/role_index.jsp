<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<html>
	<head>
		<title>管理组</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
		<script type="text/javascript">
			function deleteRole(id){
				if(!window.confirm("确定要删除吗?")){
		 			return;
		 		}
				window.location.href='deleteRole.do?id='+id;
			}
		</script>
	</head>
	<body>
			<div id="right">
				<div style="padding: 15px 10px 0px 10px;">
					<div>
						<table border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td height="28"  class="main_alll_h2">
									<a href="queryRoleList.do">管理组列表</a>
								</td>
								<td width="2">
									&nbsp;
								</td>
								<td width="100" class="xxk_all_a">
									<a href="addRoleInit.do">添加管理组</a>
								</td>
								<td width="2">
									&nbsp;
								</td>
								<td width="100" height="28"  class="xxk_all_a">
									<a href="queryAdminInit.do">管理员列表</a>
								</td>
								<td width="2">
									&nbsp;
								</td>
								<td width="100"  class="xxk_all_a">
									<a href="addAdminInit.do">添加管理员</a>
								</td>
								<td>
									&nbsp;
								</td>
							</tr>
						</table>
					</div>
					<div style="height: 20px;"></div>
					<div style="padding-right: 10px; padding-left: 10px;  padding-bottom: 10px; width: 90%; padding-top: 10px; background-color: #fff;">
						<div>
							<table id="gvNews" style="width: 100%; color: #333333;"
								cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="0">
									<tr class=gvHeader>
										<th scope="col" style="width: 70px;">
											序号
										</th>
										<th scope="col" style="width: 150px;">
											管理组名称
										</th>
										<th scope="col">
											描述
										</th>
										<th style="width: 70px;" scope="col">
											操作
										</th>
									</tr>
									<s:if test="pageBean.page==null || pageBean.page.size==0">
									<tr align="center" class="gvItem">
												<td colspan="8">暂无数据</td>
											</tr>
										</s:if>
										<s:else>
										<s:set name="counts" value="#request.pageNum"/>
										<s:iterator value="pageBean.page" var="bean" status="s">
												<tr class="gvItem">
													<td>
														<s:property value="#s.count+#counts"/>
													</td>
													<td>
														${name}
													</td>
													<td align="left">
														${description}
													</td>
													<td>
													<s:if test="#bean.id == -1">
													</s:if>
													
													<s:else>
														<a id="gvNews_ctl05_lbEdit"
															href="updateRoleInit.do?id=${id}">编辑</a>
														<s:if test="#bean.id>2">
														<a id="gvNews_ctl05_lbDelete" href="javascript:deleteRole(${bean.id })">删除</a>
														</s:if>
													</s:else>
													</td>
												</tr>
											</s:iterator>		
										</s:else>
							 </table>
						</div>
					</div>
				</div>
			</div>

	</body>
</html>
