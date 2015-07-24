<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <jsp:include page="/include/head.jsp"></jsp:include>
    <link href="../css/css.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div class="nymain" style="width:770px;" >
  <div class="wdzh"  style="width:770px;">
    <div class="r_main"  style="width:770px;">
<div class="box" style="border:none;">
        <div class="boxmain2">
         <div class="biaoge">
   <table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <th style="width: 65px;">还款人</th>
    <th style="width: 40px;">期数</th>
    <th style="width: 85px;">还款时间</th>
    <th style="width: 100px;">应还本金</th>
    <th style="width: 100px;">应还利息</th>
    <th style="width: 70px;">是否逾期</th>
    <th style="width: 70px;">逾期天数</th>
    <th style="width: 70px;">应还罚息</th><%--
    <th style="width: 100px;">是否网站垫付</th>
    --%><th style="width: 80px;">投资人</th>
    </tr>
  	<s:if test="pageBean.page==null || pageBean.page.size==0">
	   <tr align="center">
		  <td colspan="11">暂无数据</td>
	   </tr>
	</s:if>
	<s:else>
	<s:iterator value="pageBean.page" var="bean">
	 <tr>
	   <td align="center"><s:if test="%{#bean.isWebRepay == 2}">网站垫付</s:if><s:else>${bean.borrowName}</s:else></td>
	   <td align="center">${bean.repayPeriod}</td>
	   <td align="center">${bean.repayDate}</td>
	   <td align="center">￥${bean.recivedPrincipal }</td>
	   <td align="center">￥${bean.recivedInterest}</td>
	   <td align="center"><s:if test="%{#bean.isLate == 1}">否</s:if><s:else>是</s:else></td>
	   <td align="center">${bean.lateDay}</td>
	   <td align="center">￥${bean.recivedFI}</td><%--
	   <td align="center"><s:if test="%{#bean.isWebRepay == 1}">否</s:if><s:else>是</s:else></td>
	   --%><td align="center">${bean.username}</td>
       </tr>
     </s:iterator>
	</s:else>
</table>
          </div>
    </div>
</div>
    </div>
  </div>
</div>
</body>
</html>