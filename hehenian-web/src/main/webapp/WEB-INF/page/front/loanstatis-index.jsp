<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <jsp:include page="/include/head.jsp"></jsp:include>
    <link href="css/inside.css"  rel="stylesheet" type="text/css" />
</head>
<body>
<!-- 引用头部公共部分 -->
<jsp:include page="/include/top.jsp"></jsp:include>	
<div class="nymain">
  <div class="wdzh">
    <div class="l_nav">
      <div class="box">
         <!-- 引用我的帐号主页左边栏 -->
         <%@include file="/include/left.jsp" %>
      </div>
    </div>
    <div class="r_main">
      <div class="box">
        <div class="tabtil">
       <ul><li class="on">贷款统计</li></ul>
        </div>

      <div class="boxmain2">
      <div class="biaoge" style="margin-top:0px;">
          <table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <th colspan="9">还款统计</th>
    </tr>
  <tr>
    <td align="center">总借款额</td>
    <td align="center">发布借款数</td>
    <td align="center">已还本息</td>
    <td align="center">成功借款数</td>
    <td align="center">借款总笔数统计</td>
    <td align="center">待还本息</td>
    <td align="center">正常还清笔数</td>
    <td align="center">提前还清笔数</td>
    <td align="center">未还清笔数</td>
    </tr>
  <tr>
    <td align="center">￥<s:if test="#request.loanStatisMap.borrowAmount !=''">${loanStatisMap.borrowAmount}</s:if><s:else>0</s:else></td>
    <td align="center"><s:if test="#request.loanStatisMap.publishBorrow !=''">${loanStatisMap.publishBorrow}</s:if><s:else>0</s:else></td>
    <td align="center">￥<s:if test="#request.loanStatisMap.hasPI !=''">${loanStatisMap.hasPI}</s:if><s:else>0</s:else></td>
    <td align="center"><s:if test="#request.loanStatisMap.successBorrow !=''">${loanStatisMap.successBorrow}</s:if><s:else>0</s:else></td>
     <td align="center"><s:if test="#request.loanStatisMap.borrowAmount !=''">${loanStatisMap.allPublishRepay}</s:if><s:else>0</s:else></td>
    <td align="center">￥<s:if test="#request.loanStatisMap.forRepayPI !=''">${loanStatisMap.forRepayPI}</s:if><s:else>0</s:else></td>
    <td align="center"><s:if test="#request.loanStatisMap.repayBorrow !=''">${loanStatisMap.repayBorrow}</s:if><s:else>0</s:else></td>
    <td align="center"><s:if test="#request.loanStatisMap.beforeRepayBorrow !=''">${loanStatisMap.beforeRepayBorrow}</s:if><s:else>0</s:else></td>
    <td align="center"><s:if test="#request.loanStatisMap.forRepayBorrow !=''">${loanStatisMap.forRepayBorrow}</s:if><s:else>0</s:else></td>
    </tr>
          </table>

          </div>
          <div class="biaoge">
          <table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <th colspan="4">逾期统计</th>
    </tr>
  <tr>
    <td align="center">逾期本息</td>
    <td align="center">逾期次数</td>
    <td align="center">逾期罚款</td>
    <td align="center">严重逾期次数</td>
    </tr>
  <tr>
    <td align="center">￥<s:if test="#request.loanStatisMap.hasFI !=''">${loanStatisMap.hasFI}</s:if><s:else>0</s:else></td>
    <td align="center"><s:if test="#request.loanStatisMap.hasFICount !=''">${loanStatisMap.hasFICount}</s:if><s:else>0</s:else></td>
    <td align="center">￥<s:if test="#request.loanStatisMap.hasI !=''">${loanStatisMap.hasI}</s:if><s:else>0</s:else></td>
    <td align="center"><s:if test="#request.loanStatisMap.badFICount !=''">${loanStatisMap.badFICount}</s:if><s:else>0</s:else></td>
    </tr>
          </table>

          </div>
       </div>
</div>
    </div>
  </div>
</div>

<!-- 引用底部公共部分 -->     
<jsp:include page="/include/footer.jsp"></jsp:include>
<script type="text/javascript" src="script/nav-zh.js"></script>
<script type="text/javascript" src="script/jquery.shove-1.0.js"></script>
<script>
$(function(){
    //样式选中
     $("#zh_hover").attr('class','nav_first');
	 $("#zh_hover div").removeClass('none');
	 $('#li_9').addClass('on');
});		     
</script>
</body>
</html>
