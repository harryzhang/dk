<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp"%>
 <div class="container no-bottom">
        <div class="static-notification-yellow" style="margin-bottom:0px; line-height:24px; font-size:14px; text-align:center">
         投资概览
        </div>
        <table cellspacing='0' class="table">
          <tr class='even'>
            <td class="table-sub-title">投资中</td>
            <td>￥${map.investingTotal }</td>
          </tr>
          <tr >
            <td class="table-sub-title">回款中</td>
            <td>￥${map.repayingTotal+map.payforTotal }</td>
          </tr>
          <tr >
            <td class="table-sub-title">总回款额</td>
            <td>￥${map.hasPaySum }</td>
          </tr>
          <tr class='even'>
            <td class="table-sub-title">总回收本金</td>
            <td>￥${map.hasPayPrincipal }</td>
          </tr>
          <tr>
            <td class="table-sub-title">总回收利息</td>
            <td>￥${map.hasPayInterest }</td>
          </tr>
        </table>
      </div> 
     
      <div class="container no-bottom">
        <div class="static-notification-blue" style="margin-bottom:0px; line-height:24px; font-size:14px; text-align:center">
       投资记录
        </div>
        <table cellspacing='0' class="table" id="invest-records">
        </table>
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
      </div>
  
 <script>
		$("#invest-records").load("webapp-investrecord.do");
</script> 
