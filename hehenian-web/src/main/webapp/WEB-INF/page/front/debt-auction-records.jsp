<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<script>
$(function(){
   	var maxAuctionPrice = $("#maxAuctionPrice").val();
   	if(maxAuctionPrice==""){
   		$("#span_maxAuctionPrice").html("无");
   	}else{
   	 $("#span_maxAuctionPrice").html("￥" + maxAuctionPrice);
   	}
   	 if($("#debtStatus").val() == 2){
     	$("#remainTimeTwo").html($("#remainDays").val());
     }
});		     
</script>

<div class="ifbox2">
    <div class="til">
    <ul><li class="on">竞拍记录</li>
    </ul>
    <div class="til_bot">
    </div>
    </div>
    <div class="boxmain">
    <div class="tzjl">
    <ul>
      <li>目前最高竞拍金额：<strong><span id="span_maxAuctionPrice"></span></strong></li><li>剩余竞拍时间：<strong><span id="remainTimeTwo"></span></strong></li></ul>
    </div>
    <div class="tytab">
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <th align="center">竞拍人</th>
    <th align="center">竞拍金额</th>
    <th align="center">竞拍时间 </th>
    </tr>
  <s:if test="%{#request.list !=null && #request.list.size()>0}">
      <s:iterator value="#request.list" var="bean">
      <tr>
    <td align="center" class="name">
    <a href="userMeg.do?id=${userId}" target="_blank">
    ${username }
   
    </a></td>
    <td align="center" class="fred">￥${auctionPrice }</td>
    <td align="center">${ auctionTime}</td>
    </tr>
  </s:iterator>        
  </s:if>
  <s:else>
      <td colspan="3" align="center">没有数据</td>
  </s:else>
    </table>
    </div>
    </div>
    </div>