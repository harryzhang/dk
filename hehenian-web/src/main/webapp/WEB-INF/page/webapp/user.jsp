<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<title>个人信息</title>
<jsp:include page="/include/wap-head.jsp"></jsp:include>
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

<jsp:include page="/include/wap-bottom.jsp"></jsp:include>

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
<jsp:include page="hftx.jsp"></jsp:include>
</body>
</html>
