<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
    <jsp:include page="/include/cf-head.jsp"></jsp:include>
    </head>
    <body>
    <jsp:include page="/include/cf-top.jsp"></jsp:include>
    <div
			style="width: 550px; margin: 50px auto 10px auto; overflow: hidden; padding: 30px; border: 1px solid #CCC"">
      <div style="width: 100%; text-align: center; font-size:24px; line-height:50px; margin-bottom:20px;"><span style=" padding:20px 20px 0px 20px;"><img src="/images/hhnimages/logo_zaixian.png" /></span><br />
        您若在合和年在线注册过投资账户，请绑定。</div>
      <div style="float: left; text-align: right; padding-right: 10px; font-size: 14px; width: 140px;">
        <div style=" line-height:30px; margin-bottom:23px;" ><span class="field-title contactNameField">合和年登陆用户名</span>：</div>
        <div  style=" line-height:30px; margin-bottom:25px;" >合和年登陆密码<span style=" line-height:30px; margin-bottom:23px;">：</span></div>
        <div style=" line-height:30px; margin-bottom:23px;"  >验证码：</div>
      </div>
      <div style="float: left;">
        <div style=" margin-bottom:20px;">
          <input type="text"  class="contactField requiredField" name="paramMap.email" id="email" onfocus="this.select()"
									value="${request.hhnUname }" style=" width:280px; height:30px; padding-left:10px;"/>
          <br>
          <span style="color: red; margin-left: 32px;" id="s_userName" class="formtips"></span> </div>
        <div style=" margin-bottom:20px;">
          <input type="password" onfocus="this.select()" class="contactField requiredField requiredEmailField"  name="paramMap.password" id="password"  style=" width:280px; height:30px; padding-left:10px;" />
          <br>
          <span style="color: red; margin-left: 32px;" id="s_password" class="formtips"></span> </div>
        <div style=" margin-bottom:20px; overflow:hidden; line-height:32px;">
          <div style=" margin-bottom:20px; overflow:hidden; line-height:32px;">
            <input onfocus="this.select()" type="text"  class="contactField requiredField"  name="paramMap.code" id="code"  style=" width:100px; height:30px; padding-left:10px; float:left"/>
            <img src="/admin/imageCode.do?pageId=userlogin" title="点击更换验证码"
				style="cursor: pointer;float:left; margin-top:7px;width: 75px;height: 25px;" id="codeNum" width="46" height="18" onclick="javascript:switchCode()"/><a href="javascript:void()" onclick="switchCode()"
				style="color: blue;">换一换?</a> <br />
            <span style="color: red; margin-left: 32px;" id="s_confirmPassword" class="formtips"></span></div>
        </div>
      </div>
    </div>

      <div style="  text-align:center; margin-top:20px; margin-bottom:30px;">
        <input type="submit" class="buttonWrap button button-yellow contactSubmitButton" id="btn_login" value="绑定" data-formId="contactForm" 	style="width: 238px; height: 40px; border: 0px; background: #892E92; font-size: 16px; color: #FFF; margin-left: 0px;"/>
      </div>

    <jsp:include page="/include/cf-footer.jsp"></jsp:include>
</body>
    <script>
function switchCode() {
			var timenow = new Date();
			$("#codeNum").attr("src", "/admin/imageCode.do?pageId=userlogin&d=" + timenow);
		}
		function login() {
			$(this).attr('disabled', true);
			if ($("#email").val() == "") {
				//$("#s_email").attr("class", "formtips onError");
				//$("#s_email").html("*请输用户名或邮箱地址");
				alert("请输用户名或邮箱地址")
			}
			if ($("#password").val() == "") {
				$("#s_password").attr("class", "formtips onError");
				$("#s_password").html("*请输入密码");
				alert("请输入密码")
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

			$.post("cf-bind-hhnuser.do", param, function(datas) {
				var data=datas.msg;
				if (data == 1) {
					
					window.location.href = "cf-finance.do";
				} else if (data == 2) {
					$('#btn_login').attr('value', '绑定');
					$("#s_code").attr("class", "formtips onError");
					$("#s_code").html("*验证码错误！");
					alert("验证码错误")
					switchCode();
					$("#btn_login").attr('disabled', false);
				} else if (data == 3) {
					$('#btn_login').attr('value', '绑定');
					$("#s_email").attr("class", "formtips onError");
					$("#s_email").html("*用户名或密码错误！");
					alert("用户名或密码错误")
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
					alert("输入账号的手机号码和彩之云手机号码不一致")
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