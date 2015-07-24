<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<link href="css/css.css" rel="stylesheet" type="text/css" />
<style>
.mjd_fy_all{ width:560px; margin:20px auto 10px auto;}
.mjd_fy_all a{ padding:3px 10px; float:left; margin-right:5px; border:1px solid #e0e0e0; color:#333;}
.mjd_fy_all a:hover{ color:#fff; background:#f07a05;}
.mjd_fy_all span{ float:left; margin-right:5px; color:#333; font-size:12px;}
.mjd_fy_all input{ float:left; margin-right:5px; width:57px; padding-left:3px; line-height:23px; height:23px; border:1px solid #e0e0e0;}
.curPageColor {
   background-color: #f07a05;
}
</style>
 <table width="700" border="0" cellpadding="0" cellspacing="1" bgcolor="#e5e5e5">
							<tr height="30">
								<th bgcolor="#f5f5f5" scope="col">推荐好友</th>
								<th bgcolor="#f5f5f5" scope="col">注册时间</th>
								<%--<th bgcolor="#f5f5f5" scope="col">成功投资时间</th>--%>
								<%--<th bgcolor="#f5f5f5" scope="col">奖励</th>--%>
							</tr>
							<s:if test="pageBean.page==null || pageBean.page.size==0">
								<tr align="center" class="gvItem" height="30">
									<td colspan="4">暂无数据</td>
								</tr>
							</s:if>
							<s:else>
								<s:iterator value="pageBean.page" var="bean">
									<tr>
										<td align="center">${bean.username}</td>
										<td align="center">${bean.createTime }</td>
										<%--<td align="center">${bean.vipCreateTime}</td>--%>
										<%--<td colspan="6" align="center">${bean.money}</td>--%>
									</tr>
								</s:iterator>
							</s:else>
						</table> 
						<div class="cle"></div>
						<div class="mjd_fy_all">
							<div class="fenyemain">
								<shove:page curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>
							</div>
							<div class="cle"></div>
						</div>  
						<div class="cle"></div>