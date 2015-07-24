<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<title>注册</title>
<jsp:include page="/include/app-jfq-head.jsp"></jsp:include>
</head>
<body>
<div id="preloader">
  <div id="status">
    <p class="center-text"> 正在加载... </p>
  </div>
</div>

<!-- main -->
<div id="main-wrap">
  <div id="container">
  	 <div class="section-title" style=" padding:10px;">
        <h4 style="display: inline;">注册</h4>
        <h4 style="display: inline;float: right;">已有账号？/<a href="app-login-index.do?via=${session.appvia}">登录</a></h4>
      </div>
    <div class="content">
      <div class="one-half-responsive" style=" padding: 10px;">
        <div class="container no-bottom">
          <div class="formFieldWrap"> 
          <label class="field-title contactNameField" for="contactNameField">用户名：</label>
            <input type="text" class="contactField requiredField" id="userName"/>
            <span style="color: red;" id="s_userName" class="formtips"></span>
          </div>
          <div class="formFieldWrap" style="line-height:54px;"> 
          <label class="field-title contactNameField" for="contactNameField">手机号：</label>
            <input type="text" class="contactField requiredField" id="telephone"/>
            <span style="color: red;" id="s_telephone" class="formtips"></span>
          </div>
          <div class="formFieldWrap">
            <label class="field-title contactNameField" for="contactNameField">密码:</label>
            <input type="password" class="contactField requiredField"  name="paramMap.password" id="password" />
            <span style="color: red;" id="s_password" class="formtips"></span> </div>
          <div class="formFieldWrap">
            <label class="field-title contactEmailField" for="contactEmailField">确认密码: </label>
            <input type="password" class="contactField requiredField requiredEmailField" name="paramMap.confirmPassword" id="confirmPassword" />
            <span style="color: red;"
								id="s_confirmPassword" class="formtips"></span> </div>
            <div style=" padding-bottom: 5px;color: red;"><span id="tipSpan"></span></div>
          <div class="formSubmitButtonErrorsWrap">
            <input type="submit" class="buttonWrap button button-green contactSubmitButton" id="btn_register" value="注册" data-formId="contactForm" onclick="regg();"/>
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
		function regg() {
			$(".formtips").html("");
			var falg = true;
			if (falg) {
				falg = false;
				var errornum = $("form .onError").length;
				if ($.trim($("#userName").val()) == "") {
					$("#s_userName").html("请输入用户名");
					falg = false;
					return false;
				}else if ($.trim($("#telephone").val()) == "") {
					$("#s_telephone").html("请输入手机号码");
					falg = false;
					return false;
				}else if (!/^1[3|4|5|8][0-9]\d{4,8}$/.test($.trim($("#telephone").val()))) {
					$("#s_telephone").attr("class", "formtips onError");
					$("#s_telephone").html("输入手机号的格式有误");
					falg = false;
					return false;
				} 
				
				else 
				 if ($.trim($("#password").val()) == "") {
					$("#s_password").html("请设置您的密码");
					falg = false;
					return false;
				} else if ($.trim($("#password").val()).length < 6||$.trim($("#password").val()).length>20) {
					$("#s_password").html("密码长度为6-20个字符");
					falg = false;
					return false;
				} else if ($.trim($("#confirmPassword").val() )== "") {
					$("#s_confirmPassword").html("请再次输入密码确认");
					falg = false;
					return false;
				} 
				
				
				else if (errornum > 0) {
					alert("请正确填写注册信息");
					falg = false;
					return false;
				}
				$('#btn_register').attr('value', '注册中...');
				var param = {};
				
				param["paramMap.passwords"] = "${passwords}"; //彩之云
				param["paramMap.userid"] = "${userid}"; //彩之云

				param["paramMap.pageId"] = "userregister";
				param["paramMap.email"] = $.trim($("#email").val());
				param["paramMap.userName"] = $.trim($("#userName").val());
				param["paramMap.password"] = $.trim($("#password").val());
				param["paramMap.confirmPassword"] = $.trim($("#confirmPassword").val());
				param["paramMap.telephone"] = $.trim($("#telephone").val());
				param["paramMap.confirmTelephone"] = $.trim($("#confirmTelephone").val());
				//param["paramMap.refferee"] = $("#refferee").val();
				param["paramMap.code"] = $("#code").val();
				param["paramMap.param"] = $("#param").val();
				param["paramMap.registerType"] = "${session.appvia}";
				$.post("/register.do", param, function(data){
                    $("#tipSpan").text("");
					//alert(data);
					if (data == "注册成功") {
						window.location.href="index.do";
					} else {
                        $("#tipSpan").text(data);
						$('#btn_register').attr("value", "注册");
					}
				});
			}
		}
	</script>
</html>
