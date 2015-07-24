<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<title>个人信息</title>
<jsp:include page="/include/app-jfq-head.jsp"></jsp:include>
</head>
<style>
.bg{display:none;position:fixed;width:100%;height:100%;background:#000;z-index:80;top:0;left:0;opacity:0.9;}
.tc_wenxts{display:none;width:100%;position:fixed;top:50%;margin-top:-150px;z-index:90;}
</style>
<body>
<div id="preloader">
  <div id="status">
    <p class="center-text"> 正在加载... </p>
  </div>
</div>
<!--顶部底部浮动层-->

<jsp:include page="/include/app-comm.jsp"></jsp:include>

<!-- main -->
<div id="main-wrap">
  <div id="container">
    <div class="content">
     <div class="section-title" style=" padding:10px 0px;; background-color:#50c2e9;border-top:#6fd0f0 1px solid;border-bottom:#32aadf 1px solid; color:#FFF; font-weight:bold; font-size:16px; text-align:center;">
        个人信息
      </div>
      <div style=" font-size:20px; line-height:32px; margin-bottom:20px;">
      </div>
      <div class="container no-bottom">
        <table cellspacing='0' class="table">
          <%--  <tr >
            <td class="table-sub-title">推广ID</td>
            <td colspan="2">${session.user.id}</td>
          </tr> --%>
          <tr class='even'>
            <td class="table-sub-title">用户名</td>
            <td colspan="2">${session.user.username}</td>
          </tr>
          <tr>
            <td class="table-sub-title">姓名</td>
            <td colspan="2">${map.realName}</td>
          </tr>
          <tr class='even'>
            <td class="table-sub-title"> 身份证号</td>
            <td colspan="2">${map.idNo}</td>
          </tr>
         <tr>
            <td class="table-sub-title">电子邮箱</td>
            <td colspan="2">${map.email}</td>
          </tr>
          <tr class='even'>
            <td class="table-sub-title">手机号码</td>
            <td colspan="2">${map.mobilePhone}</td>
          </tr>
          <tr>
            <td class="table-sub-title" > 汇付账号</td>
            <s:if test="#request.map.usrCustId>0">
              <td style=" border-right:0px;">${map.usrCustId }</td>
            </s:if>
            <s:else>
              <td style=" border-right:0px; ">您还不是汇付会员</td>
              <td><a href="javascript:;" class="button button-yellow" style=" float:right" onclick="regChinaPnr();">注册汇付</a></td>
            </s:else>
          </tr>
        </table>
        <input type="hidden" value="${map.idNo}" id="idNo"/>
        <input type="hidden" value="${map.usrCustId}" id="usrCustId"/>
    <%--  <div style=" text-align:center; padding:5px 0px 15px 0px;"><a href="#" onclick="zhmm();" class="button-big button-orange">找回汇付天下密码</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" onclick="zhmm();" class="button-big button-orange">登录汇付</a>
        <form action="http://test.chinapnr.com/muser/password/loginpwd/lpCheckLoginInfo" target="_blank" id="zhmm">
         <input type="hidden" name="usrCustId" value="${usrCustId}"/>
         <input type="hidden" name="merCustId" value="6000060000060703"/>
         <input type="hidden" name="loginId" value="cfbgs_${session.user.id}"/>
        </form> 
         </div> --%>
      </div>
    </div>
  </div>
</div>


<!---->
<div class="tc_wenxts" id="wenxts" style="display:none; ">
   	  <div style=" width:80%; margin:0px auto;text-align:center; color:#FFF">汇付天下托管账户系统，是汇付天下为P2P平台提供的资金账户体系，全面管理平台资金，帮助平台实现收款、付款、用户账户间资金划拨以及账户资金理财功能；同时，汇付天下保障用户账户中的资金独立存放，用户可通过P2P平台对本人汇付账户进行充值、提现，商户在未经用户的许可、授权或法律的规定的情况下，无权动用，从而保证投资人资金安全。</div>
	  <div style=" width:80%; margin:10px auto 40px auto; padding:10px 0px;" class="button-green" > <a style=" text-align:center; color:#FFF" href="/registerChinaPnr.do">注册汇付天下</a></div>
      <div style=" width:80%; margin:0px auto; text-align:center; color:#FFF"><a href="index.do" style=" color:#FF0">暂不注册，先逛逛</a></div>
</div>
<div class="bg"></div>

</body>
<script type="text/javascript">
var usrCustId="${map.usrCustId}";
if(usrCustId==''||(usrCustId-0)<=0){
	$("#wenxts").css("display", "block");
	$('.bg').fadeIn(200);
	$('.tc_wenxts').fadeIn(400);
}

</script>
</html>
