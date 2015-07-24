<%--
  Created by IntelliJ IDEA.
  User: liuwtmf
  Date: 2014/10/29
  Time: 15:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/include/taglib.jsp"%>
<html>
<head>
    <title></title>
    <script type="text/javascript" src="/script/jquery-1.7.1.min.js"></script>
</head>
<body>
1.注册汇付-<s:if test="#request.userinfo.usrCustId>0">完成</s:if><s:else>未完成</s:else><a href="/registerChinaPnr.do" target="_blank">xxxxx</a><br/>
2.充值-<s:if test="#request.hasRecharge">完成</s:if><s:else>未完成</s:else><a onclick="recharge();" href="#">xxxx</a><br/>
3.授权-<s:if test="#request.hasAuth">完成</s:if><s:else>未完成</s:else><a href="#"  onclick="sq();">xxxxx</a><br/>
4.自动投标-<s:if test="#request.bidStatus==2">完成</s:if><s:else>未完成</s:else><a onclick="zxtb();" href="#">xxxxx</a><br/>
<form action="/addRecharge1.do" method="post" id="f_addRechargeInfo" target="_blank">
    <input type="hidden" id="int_money" name="money" value="${wyf_recharge_money}"/>
</form>
<form action="/transferAuth.do" method="post" id="f_transferAuth" target="_blank">
    <input type="hidden" name="toUserId" value="1000011"/>
    <input type="hidden" name="authAmt" value="${authAmount}"/>
</form>
activityOrderDo:${activityOrderDo}
<div id="autoDiv" style="display: none;"></div>
<script>
    function recharge(){
        $("#f_addRechargeInfo").submit();
    }
    function zxtb(){
        $.post("/automaticBidModify.do", {}, function(data) {
            if (data.length < 20) {
                alert(data);
                return false;
            }
            $("#autoDiv").html(data);
        });
    }
    function sq(){
        $("#f_transferAuth").submit();
    }
</script>
</body>
</html>
