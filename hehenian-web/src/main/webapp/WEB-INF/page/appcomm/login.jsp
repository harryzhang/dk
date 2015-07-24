<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<title>登录</title>
<jsp:include page="/include/app-jfq-head.jsp"></jsp:include>
</head>
<body>
<div id="preloader">
  <div id="status">
    <p class="center-text"> 正在加载... </p>
  </div>
</div>
<style>
#s_email img,#s_password img,#s_code img{display: inline;}
</style>
<!-- main -->
<div id="main-wrap">
  <div id="container">
  	 <div class="section-title" style=" padding: 10px;">
        <h4 style="display: inline;">登录</h4>
        <h4 style="display: inline;float: right;">没有账号？/<a href="app-reg.do">马上注册</a></h4>
      </div>
    <div class="content">
      <div class="one-half-responsive" style=" padding: 10px;">
        <div class="container no-bottom">
          <div class="formFieldWrap"> 用户名：
            <input type="text"  id="email" class="contactField requiredField"/>
             <span id="s_email" style="font-size: 12px;display: none; "></span>
          </div>
          <div class="formFieldWrap">
            <label class="field-title contactNameField" for="contactNameField">密码:</label>
            <input type="password" class="contactField requiredField"  name="paramMap.password" id="password" />
            <span id="s_password" style="font-size: 12px;display: none; "></span>
            </div>
            <div class="formFieldWrap">
            <label class="field-title contactNameField" for="contactNameField">验证码:</label>
            <input type="text" class="contactField requiredField" id="code"  />
            <span id="s_code" style="font-size: 12px;display: none; "></span>
            <img  src="/admin/imageCode.do?pageId=userlogin" onclick="switchCode();" id="codeNum">
            </div>
          <div class="formSubmitButtonErrorsWrap">
            <input type="submit" class="buttonWrap button button-green contactSubmitButton" id="btn_login" value="登录" data-formId="contactForm" onclick="login();"/>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>
</body>
<script>
function switchCode() {
			var timenow = new Date();
			$("#codeNum").attr("src", "/admin/imageCode.do?pageId=userlogin&d=" + timenow);
		}
		
		function login() {
			$(this).attr('disabled', true);
			if ($("#email").val() == "") {
				$("#s_email").attr("class", "formtips onError");
				$("#s_email").html("<img src='/images/v1/login.png'>请输用户名");
				$("#s_email").show();
				return false;
			}else{
				$("#s_email").hide();
			}
			if ($("#password").val() == "") {
				$("#s_password").attr("class", "formtips onError");
				$("#s_password").html("<img src='/images/v1/login.png'>请输入密码");
				$("#s_password").show();
				return false;
				// $("#retake_password").hide();
			}else{
				$("#s_password").hide();
			}
			if ($("#code").val() == "") {
				$("#s_code").attr("class", "formtips onError");
				$("#s_code").html("<img src='/images/v1/login.png'>请输入密码");
				$("#s_code").show();
				return false;
				// $("#retake_password").hide();
			}else{
				$("#s_code").hide();
			}
			$('#btn_login').attr('value', '登录中...');
			var param = {};
			param["paramMap.pageId"] = "userlogin";
			param["paramMap.email"] = $("#email").val();
			param["paramMap.password"] = $("#password").val();
			param["paramMap.code"] = $("#code").val();
			var addCookie = "0";
			if ($("#addCookie").attr("checked") == "checked")
				addCookie = "1";
			param["paramMap.addCookie"] = addCookie;

			$.post("/logining.do", param, function(datas) {
				var data=datas.msg;
				if (data == 1) {
				//	var fromUrl = datas.fromUrl;
					window.location.href = "index.do";
				} else if (data == 2) {
					$('#btn_login').attr('value', '登录');
					$("#s_code").attr("class", "formtips onError");
					$("#s_code").html("<img src='/images/v1/login.png'>验证码错误！");
					$("#s_code").show();
					switchCode();
					$("#btn_login").attr('disabled', false);
				} else if (data == 3) {
					$('#btn_login').attr('value', '登录');
					$("#s_email").attr("class", "formtips onError");
					$("#s_email").html("<img src='/images/v1/login.png'>用户名或密码错误！");
					$("#s_email").show();
					switchCode();
					$("#btn_login").attr('disabled', false);
				} else if (data == 4) {
					$('#btn_login').attr('value', '登录');
					$("#s_email").attr("class", "formtips onError");
					switchCode();
					$("#s_email").html("<img src='/images/v1/login.png'>该用户已被禁用！");
					$("#btn_login").attr('disabled', false);
				} else if (data == 200) {
					$("#s_email").attr("class", "formtips onError");
					switchCode();
					alert("该用户已被禁用！");
					$("#btn_login").attr('disabled', false);
				}
			});
		}
	</script>
</html>
