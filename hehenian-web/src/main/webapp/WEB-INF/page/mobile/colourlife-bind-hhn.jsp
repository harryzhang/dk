<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<jsp:include page="/include/mobile/head.jsp"></jsp:include>
    <link rel="stylesheet" type="text/css" href="<c:url value='/wap/mobile/styles/hhn.common.css?t=1'/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/wap/mobile/styles/hhn.layout.css?t=1'/>" />
    <script type="text/javascript">var appRefer =<%if(session.getAttribute("appstyle")==null){%>null<%}else{%>"<%=session.getAttribute("appstyle") %>"<%}%>;</script>
    <script type="text/javascript" src="<c:url value="/wap/mobile/scripts/common/zepto.min.js"/>"></script>
<title>绑定合和年帐号</title>
</head>
<body>
<jsp:include page="/include/mobile/loading.jsp"></jsp:include>
<h1 class="t title bind-hhn-title"><span>绑定合和年帐号</span></h1>
<div class="wrap" id="wrap" style="display:none;">
	<div class="tips-content p bind-tips hr"><img src="/images/hhnimages/logo_zaixian.png" /><p>您若在合和年在线注册过投资账户，请绑定您的合和年账户</p></div>
    <div class="pd bind-content">
    <form action="webapp-colourlife-bind-hhnuser.do" id="form">
      <input type="hidden" value="userlogin" name="paramMap.pageId" />
          <div class="input-item login-line"><input type="text" name="paramMap.email" class="username" placeholder = "请输入合和年登录用户名" validate="true" rule="{type:'username',empty:false,length:'2-20',disabled:''}" tips="{username:'请输入正确的合和年登录用户名',empty:'请输入合和年登录用户名'}"></div>
          <div class="input-item login-line"><input type="password" name="paramMap.password" class="password" placeholder = "请输入合和年登录密码" validate="true" rule="{empty:false,length:'6-20'}" tips="{length:'合和年登录密码长度为6-20个字符',empty:'请输入合和年登录密码'}"></div>
          <div class="login-code">
              <div class="half fl">
              	<div class="input-item"><input onfocus="this.select()" type="tel" placeholder = "请输入验证码"  name="paramMap.code" id="code" validate="true" rule="{empty:false,length:'4-4',disabled:''}" tips="{empty:'请输入验证码'}" /></div>
              </div>
              <img src="/admin/imageCode.do?pageId=userlogin" title="点击更换验证码" id="codeNum" />
          </div>
          <a href="#" class="btn-a" id="btnLogin">绑定</a>
    </form>
    </div>
</div>
<%--<jsp:include page="/include/mobile/bottom.jsp"></jsp:include>--%>
<jsp:include page="/include/mobile/common-js.jsp"></jsp:include>
<script src="/wap/mobile/scripts/module/bindhhn.js" ></script>
</body>
</html>
