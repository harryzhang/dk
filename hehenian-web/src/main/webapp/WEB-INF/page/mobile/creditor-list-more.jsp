﻿<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<s:iterator value="pageBean.page" var="bean" status="s" >
	        <dd>
		        <span>
		        	<div style="text-align:left;"><font style="color:#0cf">${borrowTitle}</font></div>
	   				<div style="text-align:left;">编号：</div>
	   				<i><font style="#999" size="-2">${number }</font></i>
				</span>
				<span>
					 <div style="text-align:left;">债权总额：</div><i style="font-style:normal;color:#0d79c1;">${debtSum }</i> 元</br>
          			 <div style="text-align:left;">转让价格：</div><i style="font-style:normal;color:#0d79c1;">${auctionBasePrice}</i> 元</br>
          			<div style="text-align:left;">年利率：</div><i style="font-style:normal;color:#0d79c1;">${annualRate}%</i></br>
				</span>
				<span><div style="text-align:left;">剩余时间：</div><i>${remainDays } </i></br>无逾期</span>
		        <span >
		        	<i><c:choose>
	              		<c:when test="${bean.debtStatus == 1 && bean.remainTimes > 0}">
	              			<span style="width:100%;" class="btn fr doTender btn-dark animate-drop">申请中</span>
	              		</c:when>
	              		<c:when test="${bean.debtStatus == 2 && bean.remainTimes > 0}">
	              			 <span  style="background:#F60;width:100%; color:#FFF;" class="btn fr doTender animate-drop" onclick="window.parent.location.href='queryDebtDetailHHN.do?id=${id }'">转让中</span>
	              		</c:when>
	              		<c:when test="${bean.debtStatus == 3}">
	              			<span style="width:100%;" class="btn fr doTender btn-dark animate-drop">已转让</span>
	              		</c:when>
	              		<c:otherwise>
						<span style="width:100%;" class="btn fr doTender btn-dark animate-drop" onclick="window.parent.location.href='queryDebtDetailHHN.do?id=${id }'">已过期</span>
						</c:otherwise>
		              </c:choose>
	              </i>
		        </span>
			</dd>
	</s:iterator>