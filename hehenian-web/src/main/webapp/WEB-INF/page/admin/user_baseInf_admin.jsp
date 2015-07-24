<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script language="javascript" type="text/javascript">
	$(function(){
		var baseStatus = $("#baseInfoStatus").val();
		if(baseStatus==2)
			$("#nopass").attr("checked","checked");
		else
			$("#pass").attr("checked","checked");
	});
</script>
<!-- 基础资料div -->
<div style="width: 100%;">
	<input type="hidden" value="${map.auditStatus }" id="baseInfoStatus" />
	<div style="float: left; width: 50% ;border-color: red;">
		<table border="1" cellspacing="0" cellpadding="0" width="100%" style=" font-size:12px;">
			<tr style="height: 30px;">
				<td width="40%" align="center">用户名</td>
				<td width="60%" align="center">${map.username}</td>
			</tr>
			<tr style="height: 30px;">
				<td align="center">真实姓名</td>
				<td align="center">${map.realName }</td>
			</tr>
			<tr style="height: 30px;">
				<td align="center">身份证号</td>
				<td align="center">${map.idNo }</td>
			</tr>
			<tr style="height: 30px;">
				<td align="center">手机</td>
				<td align="center">${map.cellPhone }</td>
			</tr>
			<tr style="height: 30px;">
				<td align="center">邮箱</td>
				<td align="center">${map.email }</td>
			</tr>
			<tr style="height: 30px;">
				<td align="center">银行卡号</td>
				<td align="center">${map.cardNo }</td>
			</tr>
		</table>
	</div>
	<div style="float: left; width: 50%;">
		<table border="1" cellspacing="0" cellpadding="0" width="100%" style=" font-size:12px;">
			<tr style="height: 30px;">
				<td align="center" width="40%">用户编号</td>
				<td width="60%" align="center">${map.userId }</td>
			</tr>
			<tr style="height: 30px;">
				<td align="center">推荐人</td>
				<td align="center">${map.refferee }</td>
			</tr>
			<tr style="height: 30px;">
				<td align="center">用户来源</td>
				<td align="center"><c:if test="${map.source==0}">小贷公司导入</c:if>
					<c:if test="${map.source==1}">网站注册</c:if> <c:if
						test="${map.source==2}">彩生活用户</c:if> <c:if test="${map.source==3}">净值用户</c:if>
				</td>
			</tr>
			<tr style="height: 30px;">
				<td align="center">居住地址</td>
				<td align="center">${map.address }</td>
			</tr>
			<tr style="height: 30px;">
				<td align="center">婚姻状况</td>
				<td align="center">${map.maritalStatus }</td>
			</tr>
			<tr style="height: 30px;">
				<td align="center">年龄</td>
				<td align="center">${map.age }</td>
			</tr>
		</table>
	</div>
</div>