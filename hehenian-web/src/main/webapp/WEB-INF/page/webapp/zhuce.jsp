<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<title>设置安全密码</title>
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
  	 <div class="section-title" style=" padding-bottom:10px;">
        <h4>设置安全密码</h4>
      </div>
    <div class="content">
      <div class="one-half-responsive">
        <p style=" padding:20px 20px 0px 20px;">为了您的资金安全，请核对您的信息，以及设置<strong>安全密码</strong></p>
        <div class="container no-bottom">
          <div class="formFieldWrap"> 用户名：
            <s:property value='#request.userName'/>
            <input type="hidden" value="${request.userName}" id="userName"/>
          </div>
          <div class="formFieldWrap" style="line-height:54px;"> 手机号：
            <s:property value='#request.telephone'/>
            <input type="hidden" value="${request.telephone}" id="telephone"/>
          </div>
          <div class="formFieldWrap">
            <label class="field-title contactNameField" for="contactNameField">密码:</label>
            <input type="password" class="contactField requiredField"  name="paramMap.password" id="password" />
            <span style="color: red;margin-left: 32px;" id="s_password"
								class="formtips"></span> </div>
          <div class="formFieldWrap">
            <label class="field-title contactEmailField" for="contactEmailField">确认密码: </label>
            <input type="password" class="contactField requiredField requiredEmailField" name="paramMap.confirmPassword" id="confirmPassword" />
            <span style="color: red;margin-left: 32px;"
								id="s_confirmPassword" class="formtips"></span> </div>
          <div class="formSubmitButtonErrorsWrap">
            <input type="submit" class="buttonWrap button button-green contactSubmitButton" id="btn_register" value="设置" data-formId="contactForm" onclick="regg();"/>
          </div>
          </fieldset>
          </form>
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
			var falg = true;
			if (falg) {
				falg = false;
				var errornum = $("form .onError").length;
				

				
				 if ($("#password").val() == "") {
					$("#s_password").html("请设置您的密码");
					falg = false;
					return false;
				} else if ($("#password").val().length < 6||$("#password").val().length>20) {
					$("#s_password").html("密码长度为6-20个字符");
					falg = false;
					return false;
				} else if ($("#confirmPassword").val() == "") {
					$("#s_confirmPassword").html("请再次输入密码确认");
					falg = false;
					return false;
				} else if ($("#telephone").val() == "") {
					$("#s_telephone").html("请输入手机号码");
					falg = false;
					return false;
				}else if (!/^1[3|4|5|8][0-9]\d{4,8}$/.test($("#telephone").val())) {
					$("#s_telephone").attr("class", "formtips onError");
					$("#s_telephone").html("输入手机号的格式有误");
					falg = false;
					return false;
				}
				
				
				else if (errornum > 0) {
					alert("请正确填写注册信息");
					falg = false;
					return false;
				}
				$('#btn_register').attr('value', '设置中...');
				var param = {};
				
				param["paramMap.passwords"] = "${passwords}"; //彩之云
				param["paramMap.userid"] = "${userid}"; //彩之云

				param["paramMap.pageId"] = "userregister";
				param["paramMap.email"] = $("#email").val();
				param["paramMap.userName"] = $("#userName").val();
				param["paramMap.password"] = $("#password").val();
				param["paramMap.confirmPassword"] = $("#confirmPassword").val();
				param["paramMap.telephone"] = $("#telephone").val();
				param["paramMap.confirmTelephone"] = $("#confirmTelephone").val();
				//param["paramMap.refferee"] = $("#refferee").val();
				param["paramMap.code"] = $("#code").val();
				param["paramMap.param"] = $("#param").val();
				$.post("/register.do", param, function(data){
				if(data=="注册成功"){
					data="设置成功";
				}
					alert(data);
					if (data == "设置成功") {
						window.location.href="webapp-home.do";
					} else {
						$('#btn_register').attr("value", "设置");
					}
				});
			}
		}
	</script>
</html>
