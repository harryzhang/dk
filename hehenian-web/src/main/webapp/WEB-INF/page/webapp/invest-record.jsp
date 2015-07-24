<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp"%>
<tr class='even'>
            <td class="table-sub-title">债权编号</td>
            <td class="table-sub-title">交易金额</td>
            <td class="table-sub-title">交易日期</td>
          </tr>
		  <s:iterator value="pageBean.page" var="bean" status="status">
          <tr  class="${status.count%2==0?'even':''}">
            <td>${bean.invest_number }</td>
            <td>${bean.realAmount }元</td>
            <td>${bean.investTime }</td>
          </tr>
		  </s:iterator>
          
