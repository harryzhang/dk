<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<jsp:include page="/include/wap-head.jsp"></jsp:include>
<title>用户名注册</title>
<link href="/wap/styles/register.css" rel="stylesheet">
</head>
<body>
<jsp:include page="/include/wap-bottom.jsp"></jsp:include>

<div class="wrap" id="wrap" style="display:none;">
  <h2 class="t-a"><strong>用户名注册</strong></h2>
  <div class="registerFormBg">
      <form class="form registerForm">
          <p><input type="text" name="userName" placeholder = "请输入用户名" class="input-text" validate="true" rule="{type:'username',length:'2-15'}" tips="{username:'请输入有效的投资数额'}"></p>
          <p><input type="password" name="userName" placeholder = "请输入密码" class="input-text passW" validate="true" rule="{type:'password',length:'3-18'}" tips="{password:'请输入有效的投资数额'}"></p>
          <p><input type="submit" value="登录" class="btn btn-a"></p>
          <p><a href="#" class="reBack_pw">找回密码？</a></p>
      </form>
      <div class="border-top"></div>
       <div class="registerForm pT20">
          <p class="mB15">你还没有E理财账户？</p>
          <p><button type="button" class="btn btn-b">立即免费注册</button></p>
       </div>
  </div>
</div>
<jsp:include page="/include/wap-js.jsp"></jsp:include>
<script src="/wap/scripts/module/register.js" ></script>
</body>
</html>