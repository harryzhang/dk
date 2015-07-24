<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<jsp:include page="/include/mobile/head.jsp"></jsp:include>
<title>登录</title>
</head>
<body>
<jsp:include page="/include/mobile/loading.jsp"></jsp:include>
<h1 class="t-a title login-title"><strong>登录</strong></h1>
<div class="wrap" id="wrap" style="display:none;">
  <div class="pd login hr clearfix">
      <form class="form" id="form" action="/webapp/webapp-logining.do">
      	  <input type="hidden" value="userlogin" name="paramMap.pageId" />
          <div class="input-item login-line"><input type="text" name="userName" class="username" placeholder = "请输入用户名" validate="true" rule="{type:'username',empty:false,length:'1-20',disabled:''}" tips="{username:'请输入正确的用户名',empty:'请输入用户名'}"></div>
          <div class="input-item login-line"><input type="password" name="pwd" class="password" placeholder = "请输入密码" validate="true" rule="{empty:false,length:'6-20'}" tips="{length:'密码长度为6-20个字符',empty:'请输入密码'}"></div>
          <div class="login-code">
              <div class="half fl">
              	<div class="input-item"><input type="tel" name="paramMap.code" id="code" validate="true" rule="{empty:false,length:'4-4',disabled:''}" tips="{empty:'请输入验证码'}" /></div>
              </div>
              <img src="/admin/imageCode.do?pageId=userlogin" title="点击更换验证码" id="codeNum" />
          </div>
          <a href="#" class="btn-c" id="btnLogin">登录</a>
      </form>
  </div>
  <div class="pd">
       <div class="login-info">你还没有账户？</div>
       <a href="/webapp/webapp-register.do" class="btn-c ">立即免费注册</a>
   </div>
</div>
<input type="hidden" id="fromUrl" value="${param.fromUrl}"/>
<jsp:include page="/include/mobile/bottom.jsp"></jsp:include>
<jsp:include page="/include/mobile/common-js.jsp"></jsp:include>
<script src="/wap/mobile/scripts/module/login.js?_=1" ></script>
</body>
</html>