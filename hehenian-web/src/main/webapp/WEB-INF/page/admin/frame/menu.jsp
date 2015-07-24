<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		 <title>合和年在线后台管理</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="This is my page" />
		<link href="../css/admin/admin_css.css" rel="stylesheet"
			type="text/css" />
		<script type="text/javascript" src="../css/admin/jquery-1.2.6.pack.js"></script>

		<script type="text/javascript">

	function loginOut() {
		if (confirm("是否确定要退出？")) {
			window.parent.location = "adminLoginOut.do";
		}
	}
	
<%--		if(${session.index}==-1){--%>
			window.top.main.location.href="main.do";
			<%--}
		if(${session.index}==-2){
			window.top.main.location.href="borrowAll.do";
		}
		if(${session.index}==-3){
			window.top.main.location.href="userFundInit.do";
		}
		if(${session.index}==-4){
			window.top.main.location.href="queryPersonInfolistindex.do";
		}
		if(${session.index}==-5){
			window.top.main.location.href="queryUserManageBaseInfoindex.do";
		}
		if(${session.index}==-6){
			window.top.main.location.href="queryNewsListInit.do";
		}
		if(${session.index}==-7){
			window.top.main.location.href="downloadlistinit.do";
		}
		if(${session.index}==-8){
			window.top.main.location.href="queryGroupCaptainInit.do";
		}
		if(${session.index}==-9){
			window.top.main.location.href="loginStatisInit.do";
		}
		if(${session.index}==-10){
			window.top.main.location.href="queryUserListInit.do";
		}--%>
		$(function(){
			$(".munes li").click(function(){
			$(".munes li").attr("class","");
			$(this).attr("class","cur");
			})
		})
		
	</script>
	</head>
	<body
		style="width: 100%; margin-top: 20px; padding: 0px; background: #fff url(../images/admin/main-left.gif) 147px top repeat-y; * background: #fff url(../images/admin/main-left.gif) 146px top repeat-y; min-height: 300px;">
		<form id="form1" style="padding: 0; margin: 0">
			<div id="left">
				<table border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td>
							<s:iterator
								value="#session.adminRoleMenuList.{?#this.rightsId==#session.index}"
								var="bean" status="sta">
								<table border="0" cellspacing="0" cellpadding="0">
									<tr style="cursor: pointer;"
										onclick="showsubmenu(${sta.index})">
										<td height="33" class="menu_top_a">
											<s:if test="#session.index <0">
												${bean.summary }
											</s:if>
											<s:else>
												${session.searchCode }
											</s:else>
										</td>
										<td align="left">
										</td>
									</tr>
								</table>
							</s:iterator>
							<div id="submenu${sta.index}" class="munes">
								<ul>
									<s:if test="#session.index <0">
										<s:iterator
											value="#session.adminRoleMenuList.{?#this.parentId==#session.index}"
											var="sBean">
											<li>
												<a href="${sBean.action }" target="main"><span
													class="point">&raquo;</span>${sBean.summary }</a>
											</li>
	
										</s:iterator>
									</s:if>
									<s:else>
										<s:if test="#session.issearchCount==0">
											<div style="text-align:center;">无搜索记录！</div>
										</s:if>
										<s:else>
											<s:iterator
											value="#session.adminRoleMenuLists"
											var="sBean">
											<li>
												<a href="${sBean.action }" target="main"><span
													class="point">&raquo;</span>${sBean.summary }</a>
											</li>
	
										</s:iterator>
										</s:else>
										
									</s:else>
								</ul>
							</div>
						</td>
					</tr>
				</table>
			</div>
			<!-- <div id="bottom"
				style="float: left; font-size: 12px; color: #333333; margin: 30px 0 0 10px;">
				<div style="margin-left: 5px;">
					欢迎您：${sessionScope.admin.userName }&nbsp;
					<span style="color: #999;"></span>&nbsp;
				</div>
				<div>
					<span>[<a target="main" href="updatePasswordInit.do">修改密码</a>]</span><span>[<a
						id="lbtnLogout" href="javascript:loginOut();" class="top_aa">安全退出</a>]</span>
				</div>
			</div>
			 -->
		</form>
	</body>
</html>
