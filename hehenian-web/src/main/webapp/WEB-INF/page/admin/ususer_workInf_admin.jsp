<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<link href="../css/css.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="../css/admin/admin_css.css" />
<!-- 工作资料div -->
<div style="width: 100%;">
	<input type="hidden" value="${map.auditStatus }" id="workInfoStatus" />
	<div style="float: left; width: 50% ;border-color: red">
		<table border="1" cellspacing="0" cellpadding="0" width="100%" style=" font-size:12px;">
			<tr style="height: 35px;">
				<td width="40%" align="center">单位名称</td>
				<td width="60%" align="center">${map.orgName}</td>
			</tr>
			<tr style="height: 35px;">
				<td align="center">职业</td>
				<td align="center">${map.occStatus }</td>
			</tr>
			<tr style="height: 35px;">
				<td align="center">公司类别</td>
				<td align="center">${map.companyType }</td>
			</tr>
			<tr style="height: 35px;">
				<td align="center">公司规模</td>
				<td align="center">${map.companyScale }</td>
			</tr>
			<tr style="height: 35px;">
				<td align="center">职位</td>
				<td align="center">${map.job }</td>
			</tr>
		</table>
	</div>
	<div style="float: left; width: 50%;">
		<table border="1" cellspacing="0" cellpadding="0" width="100%" style=" font-size:12px;">
			<tr style="height: 35px;">
				<td align="center" width="40%">月收入</td>
				<td width="60%" align="center">${map.monthlyIncome }</td>
			</tr>
			<tr style="height: 35px;">
				<td align="center">现单位工作年限</td>
				<td align="center">${map.workYear }</td>
			</tr>
			<tr style="height: 35px;">
				<td align="center">单位电话</td>
				<td align="center">${map.companyTel }</td>
			</tr>
			<tr style="height: 35px;">
				<td align="center">单位邮箱</td>
				<td align="center">${map.workEmail }</td>
			</tr>
			<tr style="height: 35px;">
				<td align="center">单位地址</td>
				<td align="center">${map.companyAddress }</td>
			</tr>
		</table>
	</div>
</div>