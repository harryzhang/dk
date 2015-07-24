<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
<style>
	tr{font-size: 12px;
	color: #3e4959;
	font-family: "tahoma";
	background-color: #fff;
	height: 28px;
	text-align: center; }
</style>
<div>
	<table style="width: 100%; color: #333333;" cellspacing="1"
		cellpadding="3" bgcolor="#dee7ef" border="0">
		<tr>
		<td  style="width: 150px; height: 25px;" align="right">用户名</td>
		<td>${paramMap.username}</td>
		<td  style="width: 150px; height: 25px;" align="right">用户编号</td>
		<td>${paramMap.id}</td>
		</tr>
		
		<tr>
		<td  style="width: 150px; height: 25px;" align="right">真实姓名</td>
		<td>${paramMap.realName }</td>
		<td  style="width: 150px; height: 25px;" align="right">推荐渠道</td>
		<td>${paramMap.refferee}</td>
		</tr>
		
		<tr>
		<td  style="width: 150px; height: 25px;" align="right">年龄</td>
		<td>${paramMap.age }</td>
		<td style="width: 150px; height: 25px;" align="right">用户来源</td>
		<td>
		${paramMap.source}
		</td>
		</tr>
		<tr>
		<td  style="width: 150px; height: 25px;" align="right">年收入</td>
		<td>
		${paramMap.income}
		</td>
		<td  style="width: 150px; height: 25px;" align="right">年可用投资区间</td>
		<td>
			${paramMap.investment}
		</td>
		</tr>
		<tr>
		<td  style="width: 150px; height: 25px;" align="right">投资经历</td>
		<td >
			${paramMap.experience}
		</td>
		<td  style="width: 150px; height: 25px;" align="right">投资目标</td>
		<td>${paramMap.aim}</td>
		</tr>
		
		<tr>
		<td  style="width: 150px; height: 25px;" align="right">个人投资情况得分/模块总分</td>
		<td>${paramMap.total }/<span style="color: blue">${paramMap.questionScore}</span></td>
		<td  style="width: 150px; height: 25px;" align="right">互联网金融工具使用情况得分/模块总分</td>
		<td>${paramMap.internet}/<span style="color: blue">${paramMap.intnetScore}</span></td>
		</tr>
		
		<tr>
		<td  style="width: 150px; height: 25px;" align="right">p2p网贷平台的认知程度得分/模块总分</td>
		<td>${paramMap.degree }/<span style="color: blue">${paramMap.degreeScore}</span></td>
		<td  style="width: 150px; height: 25px;" align="right">风险偏好得分/模块总分</td>
		<td>${paramMap.predilection}/<span style="color: blue">${paramMap.prediScore}</span></td>
		</tr>
		<tr>
		<td  style="width: 150px; height: 25px;" align="right">总得分/问卷总分</td>
		<td>${paramMap.total+paramMap.internet+ paramMap.degree +paramMap.predilection}
		/<span style="color: blue">
		${paramMap.prediScore+paramMap.intnetScore+paramMap.questionScore+paramMap.degreeScore}</span>
		
		</td>
		<td  style="width: 150px; height: 25px;" align="right">填写问卷时间</td>
		<td>${paramMap.writetime}</td>
		</tr>
		<tr>
		<td  style="width: 150px; height: 25px;" align="right">认证情况</td>
		<td colspan="3">
		</td>
		</tr>
		<tr>
		<td  style="width: 150px; height: 25px;" align="right">风险承受态度</td>
		<td>
			${paramMap.attitude}
		</td>
		<td  style="width: 150px; height: 25px;" align="right">风险等级</td>
		<td>${paramMap.levels}级</td>
		</tr>
	</table>
</div>
