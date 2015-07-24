<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
    <jsp:include page="/include/cf-head.jsp"></jsp:include>
    </head>
    <body>
    <jsp:include page="/include/cf-top.jsp"></jsp:include>
    <div class="s_sur_ix main el_container"
		style="overflow: hidden; margin-bottom: 20px;"> 
      <!--  <div style=" margin:10px 0px;"><img src="/color/images/ad.jpg" width="974" height="80"  alt=""/></div> --> 
      <!--左侧-->
      <div
			style="width: 550px; margin: 50px auto 10px auto; overflow: hidden; padding: 30px 30px 50px 30px; border: 1px solid #CCC"">
        <div style="width: 100%; text-align: center; font-size:26px; line-height:50px; margin-bottom:20px;">为了您的资金安全，请设置您的安全密码</div>
        <div style="float: left; text-align: right; padding-right: 10px; font-size: 14px; width: 140px;">
          <div style=" line-height:30px; margin-bottom:23px;" >&nbsp;用户名：</div>
          <div  style=" line-height:30px; margin-bottom:25px;" >&nbsp;密码：</div>
          <div style=" line-height:30px; margin-bottom:23px;"  >&nbsp;确认密码：</div>
          <div  style=" line-height:30px; " >&nbsp;手机号：</div>
        </div>
        <div style="float: left;">
          <div style=" margin-bottom:20px;">
            <input  type="text" style=" width:280px; height:30px; padding-left:10px;" name="paramMap.userName" id="userName" value="<s:property value='#request.userName'/>" />
            <br>
            <span style="color: red; margin-left: 32px;" id="s_userName" class="formtips"></span> </div>
          <div class="login_cente_login_right">
            <input type="password" class="login_tex1" name="paramMap.password" id="password" style=" width:280px; height:30px; padding-left:10px;" />
            <br>
            <span style="color: red; margin-left: 32px;" id="s_password" class="formtips"></span> </div>
          <div style="font-size: 12px; color: #999; margin-bottom: 10px;">6~16个字符，不能包含空格,使用【字母+数字】的组合</div>
          <div style=" margin-bottom:20px;">
            <input type="password" class="login_tex1" name="paramMap.confirmPassword" id="confirmPassword" style=" width:280px; height:30px; padding-left:10px;"/>
            <br>
            <span style="color: red; margin-left: 32px;" id="s_confirmPassword" class="formtips"></span> </div>
          <div class="login_cente_login_right">
            <input disabled="disabled" type="text" class="login_tex1" name="paramMap.telephone"
						id="telephone" value="<s:property value='#request.telephone'/>" style=" width:280px; height:30px; padding-left:10px;"/>
            <br />
            <span style="color: red; margin-left: 32px;"
						id="s_telephone" class="formtips"></span> </div>
        </div>
      </div>
      <div style="text-align: center; line-height: 40px;">
        <input type="checkbox" id="agre" checked="checked" />
        我已经阅读并同意<a
				href="/zcxy.html" target="_blank" style="color: #4aa3ff;">《投资协议》</a> </div>
      <div style="text-align: center; margin-bottom: 70px;">
        <input type="button" id="btn_register" value="设置"
				style="width: 238px; height: 40px; border: 0px; background: #892E92; font-size: 16px; color: #FFF; margin-left: 0px;" />
      </div>
      
      <!--右侧-->
      <%-- <jsp:include page="/include/cf-right.jsp"></jsp:include> --%>
    </div>
    <jsp:include page="/include/cf-footer.jsp"></jsp:include>
</body>
    <script>
		$(document).ready(function() {
			$("#btn_register").click(function() {
				regg();
			});
		});

		//提交
		//验证数据
		function checkRegister(str) {
			var param = {};
			if (str == "userName") {
				param["paramMap.email"] = "";
				param["paramMap.userName"] = $("#userName").val();
			} else {
				param["paramMap.email"] = $("#email").val();
				param["paramMap.userName"] = "";
			}
			$.post("ajaxCheckRegister.do", param, function(data) {
				if (data == 3 || data == 4) {
					if (str == "userName") {
						$("#s_userName").html("该用户已存在");
					}
				}
			});

		}

		function regg() {
			var falg = true;
			if (falg) {
				falg = false;
				var errornum = $("form .onError").length;
				if(errornum>0){
					alert("请填写完整的信息");
					falg = false;
					return false;
				}
			
				if ($("#userName").val() == "") {
					$("#s_userName").html("请输入登录用户名");
					falg = false;
					return false;
				}else if ($("#userName").val().length < 5 || $("#userName").val().length > 25) {
					$("#s_userName").html("用户名长度为5-25个字符");
					falg = false;
					return false;
				} else if (!/^[A-Za-z0-9_]+$/.test($("#userName").val())) {
					$("#s_userName").html("用户名只能有数字字母下划线组成");
					falg = false;
					return false;
				} else if ($("#password").val() == "") {
					$("#s_password").html("请设置您的密码");
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
				} else {
					if (!$("#agre").attr("checked")) {
						alert("请勾选我已阅读并同意《投资协议》");
						falg = false;
						return false;
					}
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
				param["paramMap.refferee"] = $("#refferee").val();
				param["paramMap.code"] = $("#code").val();
				param["paramMap.param"] = $("#param").val();
				$.post("/register.do", param, function(data){
					if (data == "注册成功") {
						alert("设置成功");
						window.location.href="cf-finance.do";
					} else {
						alert(data);
						$('#btn_register').attr("value", "设置");
					}
				});
			}
		}
	</script>
</html>