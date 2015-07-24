<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<title>绑定合和年账号</title>
<jsp:include page="/include/wap-head.jsp"></jsp:include>
</head>
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
      <div class="section-title" style=" padding-bottom:10px;">
        <h4>绑定合和年账号</h4>
      </div>
      <div class="one-half-responsive">
        <p style=" padding:20px 20px 0px 20px;"><img src="/images/hhnimages/logo_zaixian.png" /></p>
        <p style=" padding:0px 20px 0px 20px;">您若在合和年在线注册过投资账户，请绑定您的合和年账户</p>
        <div class="container no-bottom">
          <div class="formFieldWrap">
            <label class="field-title contactNameField" for="contactNameField">合和年登陆用户名:</label>
            <input type="text"  class="contactField requiredField" name="paramMap.email" id="email" onfocus="this.select()"
									value="${request.hhnUname }" />
          </div>
          <div class="formFieldWrap">
            <label class="field-title contactEmailField" for="contactEmailField">合和年登陆密码: </label>
            <input type="password" onfocus="this.select()" class="contactField requiredField requiredEmailField"  name="paramMap.password" id="password" />
          </div>
          <div class="formTextareaWrap">
            <label class="field-title contactMessageTextarea" for="contactMessageTextarea">验证码: </label>
            <input onfocus="this.select()" type="text"  class="contactField requiredField"  name="paramMap.code" id="code" />
            <img src="/admin/imageCode.do?pageId=userlogin" title="点击更换验证码"
				style="cursor: pointer;float:left" id="codeNum" width="46" height="18" onclick="javascript:switchCode()" /><a href="javascript:void()" onclick="switchCode()"
				style="color: blue;">换一换?</a> </div>
          <div class="formSubmitButtonErrorsWrap" style=" margin-top:20px;">
            <input type="submit" class="buttonWrap button button-yellow contactSubmitButton" id="btn_login" value="绑定" data-formId="contactForm"/>
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
				$("#s_email").html("*请输用户名或邮箱地址");
			}
			if ($("#password").val() == "") {
				$("#s_password").attr("class", "formtips onError");
				$("#s_password").html("*请输入密码");
				// $("#retake_password").hide();
			}
			$('#btn_login').attr('value', '绑定中...');
			var param = {};
			param["paramMap.pageId"] = "userlogin";
			param["paramMap.email"] = $("#email").val();
			param["paramMap.password"] = $("#password").val();
			param["paramMap.code"] = $("#code").val();
			var addCookie = "0";
			if ($("#addCookie").attr("checked") == "checked")
				addCookie = "1";
			param["paramMap.addCookie"] = addCookie;

			$.post("webapp-colourlife-bind-hhnuser.do", param, function(datas) {
				var data=datas.msg;
				if (data == 1) {
					
					window.location.href = "webapp-index.do";
				} else if (data == 2) {
					$('#btn_login').attr('value', '绑定');
					$("#s_code").attr("class", "formtips onError");
					$("#s_code").html("*验证码错误！");
					switchCode();
					$("#btn_login").attr('disabled', false);
				} else if (data == 3) {
					$('#btn_login').attr('value', '绑定');
					$("#s_email").attr("class", "formtips onError");
					$("#s_email").html("*用户名或密码错误！");
					switchCode();
					$("#btn_login").attr('disabled', false);
				} else if (data == 4) {
					$('#btn_login').attr('value', '绑定');
					$("#s_email").attr("class", "formtips onError");
					switchCode();
					$("#s_email").html("*该用户已被禁用！");
					$("#btn_login").attr('disabled', false);
				} else if (data == 5) {
					$('#btn_login').attr('value', '绑定');
					$("#s_email").attr("class", "formtips onError");
					$("#s_email").html("*输入账号的手机号码和彩之云手机号码不一致！");
					switchCode();
					$("#btn_login").attr('disabled', false);
				} else if (data == 200) {
					$("#s_email").attr("class", "formtips onError");
					switchCode();
					alert("*该用户已被禁用！");
					$("#btn_login").attr('disabled', false);
				}
			});
		}

		
		$("#btn_login").click(function() {
			login();
		});
	</script>
</html>
