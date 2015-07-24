<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<%@include file="/include/huodong.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<jsp:include page="/include/cf-head.jsp"></jsp:include>
</head>
<body>
<jsp:include page="/include/cf-top.jsp"></jsp:include>
<div class="s_sur_ix main el_container" style=" overflow:hidden; margin-bottom:20px;">
    <!--左侧-->
    	<div class=" nr_left"> 
      <!-- 代码开始 -->
      <div id="page-wrap">
      <div><img src="/wap/images/webad.jpg" width="743" height="330"  alt=""/></div>
      <div style=" background:url(/wap/images/webbg.jpg) ; font-size:16px; line-height:24px;">
      <div style=" width:600px; margin:0px auto;">
        <p><strong>活动介绍          </strong></p>
        <p>为了庆祝e理财进入社区，为了感谢各会员的大力支持，现进行感恩大回馈，7月29日开始预约，7月30日进行投标，只要在30日当天进行投资的用户，都可以获得1%的现金奖！          </p><br />
<br />

        <p><strong>活动规则</strong></p>
        <p> 1. 7月29日开始预约，在网站端和e理财都可以进行预约；          </p>
        <p>2. 7月30日0点-22点进行投标，所有用户，只要当天有投资，都送本人投资额的1%作为奖励；          </p>
        <p>3. 活动奖励均在活动结束后5个工作日内发送到用户的投资账户，用户可自行提现或继续投资。 </p><br />
<br />

      </div>
     
<s:if test="#request.hasYuyue">
<s:if test="#request.nowDay!='20140730'">
 <div id="yyan" style=" width:426px; margin:0px auto ; padding-bottom:40px;"><img src="/wap/images/webaniu1.png" /></div>
</s:if>
<s:else>
 <div style=" width:426px; margin:0px auto ; padding-bottom:40px;"><a href="cf-finance.do"><img src="/wap/images/webaniu2.png" /></a></div>
</s:else>
</s:if>
<s:else>
 <div style=" width:426px; margin:0px auto ; padding-bottom:40px;"><a href="?cmd=yy#yyan"><img src="/wap/images/webaniu.png" /></a></div>
</s:else>   
      </div>
      
      </div>
      <!-- 代码结束 --> 
      
    </div>
    
    <!--右侧-->
    <jsp:include page="/include/cf-right.jsp"></jsp:include>
  </div>
  <jsp:include page="/include/cf-footer.jsp"></jsp:include> 
</body>

</html>