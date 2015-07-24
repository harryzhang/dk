<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<html>
<head>

<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="../css/jbox/Gray/jbox.css" type="text/css"></link>
<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
<script type="text/javascript" src="../common/dialog/popup.js"></script>
<script type="text/javascript" src="../css/popom.js"></script>
<script language="javascript" type="text/javascript" src="../script/jbox/jquery.jBox-2.3.min.js"></script>
<script type="text/javascript">
	// 跳转详情页面
	function check_UserInfo(id) {
		$.jBox("iframe:adminBase.do?uid=" + id, {
			title : "审核认证信息",
			width : 700,
			height : 600,
			top : 5,
			buttons : {}
		});
	}

	//弹出窗口关闭
	function closeJbox() {
		window.jBox.close();
		window.location.reload();
	}
</script>
</head>
<body>
	<div>

		<table id="gvNews" style="width: 100%; color: #333333;" cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="0">
			<tbody>
				<tr class=gvHeader>
					<th scope="col" width="50px">序号</th>
					<th scope="col" width="80px">用户账号</th>
					<th scope="col" width="75px">真实姓名</th>
					<th scope="col" width="75px">手机号码</th>
					<th scope="col" width="60px">身份证</th>
					<th scope="col" width="80px">用户来源</th>
					<th scope="col" width="100px">个人信息</th>
					<th scope="col" width="100px">工作信息</th>
					<th scope="col" width="100px">财力信息</th>
					<th scope="col" width="80px">必填认证</th>
					<th scope="col" width="80px">操作</th>
				</tr>
				<s:if test="pageBean.page==null || pageBean.page.size==0">
					<tr align="center" class="gvItem">
						<td colspan="11">暂无数据</td>
					</tr>
				</s:if>
				<s:else>
					<s:set name="counts" value="#request.pageNum" />
					<s:iterator value="pageBean.page" var="bean" status="s">
						<tr class="gvItem">
							<td><s:property value="#s.count+#counts" />
							</td>
							<td align="center"><a>${bean.username}</a>
							</td>
							<td>${bean.realName }</td>
							<td>${bean.mobilePhone }</td>
							<td>${bean.idNo}</td>
							<td><s:if test="#bean.source==0">小贷公司导入</s:if> <s:if test="#bean.source==1">网站注册</s:if> <s:if test="#bean.source==2">彩生活用户</s:if> <s:if test="#bean.source==3">净值用户</s:if></td>

							<!-- 个人信息状态 -->
							<td><s:if test="#bean.personauditStatus==1">基本信息完整<a style="color: gray;">(待审核)</a>
								</s:if> <s:elseif test="#bean.personauditStatus==2">基本信息完整<a style="color: red;">(失败)</a>
								</s:elseif> <s:elseif test="#bean.personauditStatus==3">基本信息完整<a style="color: blue;">(成功)</a>
								</s:elseif> <s:else>未填写</s:else></td>
							<!--工作信息状态 -->
							<td><s:if test="#bean.workauditStatus==1">工作信息完整
									<a style="color: gray;">(待审核)</a>
								</s:if> <s:elseif test="#bean.workauditStatus==2">工作信息完整<a style="color: red;">(失败)</a>
								</s:elseif> <s:elseif test="#bean.workauditStatus==3">工作信息完整<a style="color: blue;">(成功)</a>
								</s:elseif> <s:else>未填写</s:else>
							</td>
							<!--财力信息状态 -->
							<td><s:if test="#bean.redayCount>0">
									<a style="color: gray;">待审核</a>
								</s:if> 
								<s:elseif test="passCount==0">
									<a style="color:red ;">未认证</a>
								</s:elseif> <s:else>
									<a style="color: blue;">已认证</a>
								</s:else>
							</td>
							<!--必填认证信息状态 -->
							<td><s:if test="#bean.mustStatus==0"><a style="color: gray;">未填写 </a></s:if> <s:elseif test="#bean.mustStatus==15">
									<a style="color: blue;">已认证</a>
								</s:elseif> <s:else>
									<a style="color: red;">待审核</a>
								</s:else></td>
							<td><a href="javascript:check_UserInfo(${bean.userId})"> <s:if test="#bean.mustStatus==15#bean.personauditStatus==3&&#bean.workauditStatus==3">查看 </s:if> <s:else>审核</s:else>
							</a></td>
						</tr>
					</s:iterator>
				</s:else>
			</tbody>
		</table>
	</div>
	<shove:page curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>
</body>
</html>