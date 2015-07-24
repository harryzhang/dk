<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<jsp:include page="/include/head.jsp"></jsp:include>
<script type="text/javascript">
	$(function() {
        if("${msg}"!=""){
            alert("${msg}");
        }
		$(".header_two_right_ul li").hover(function() {
			$(this).find("ul").show();
		}, function() {
			$(this).find("ul").hide();
		})

		$(".fbjk_all_toux ul li:first").addClass(".inpp");
		$(".fbjk_all_toux ul li").click(function() {
			var ppin = $(".fbjk_all_toux ul li").index(this);
			$(".fbjk_all_toux ul li").removeClass(".inpp");
			$(this).addClass(".inpp");
			$(".fbjk_all_touxh").hide();
			$(".fbjk_all_touxh").eq(ppin).show();
		});
	});
	
</script>
    <style type="text/css">
        .sendBtn{font-family:Arial;line-height: 34px;color: #333;cursor: pointer;background: url(/images/sprit.png) no-repeat -111px -100px;text-align: center;height: 31px;width: 88px;border: none;display: inline-block;font-size: 13px;}
        .sendBtn1{font-family:Arial;line-height: 34px;color: #333;cursor: pointer;background: url(/images/sprit.png) no-repeat 0px 0px;text-align: center;height: 31px;width: 142px;border: none;display: inline-block;font-size: 13px;}
    </style>
</head>
<body>
<jsp:include page="/include/top.jsp"></jsp:include>
<div style=" margin-top:30px; margin-bottom:90px;">
  <div style=" width:890px; font-size:12px; text-align:right; margin:0px auto">我已注册，立即<a href="/account/login-index.do" style=" color:#f60">登录>></a></div>
  <div style=" width:890px; border:1px solid #e8e8e8; background:#FFF; margin:0px auto; overflow:hidden; height:400px;">
    <div style=" float:left; text-align:right; padding-right:10px; font-size:14px; width:140px;">
      <div class="login_cente_login_left">
        <div style="float:right">&nbsp;用户名：</div>
        <div style="float:right; margin-top:5px;"><strong style="color:#F00;">*</strong></div>
      </div>
      <div class="login_cente_login_left">
        <div style="float:right">&nbsp;密码：</div>
        <div style="float:right; margin-top:5px;"><strong style="color:#F00;">*</strong></div>
      </div>
      <div style=""></div>
      <div class="login_cente_login_left">
        <div style="float:right">&nbsp;确认密码：</div>
        <div style="float:right; margin-top:5px;"><strong style="color:#F00;">*</strong></div>
      </div>
      <div class="login_cente_login_left">
        <div style="float:right">&nbsp;手机号：</div>
        <div style="float:right; margin-top:5px;"><strong style="color:#F00;">*</strong></div>
      </div>
        <div class="login_cente_login_left">
            <div style="float:right">&nbsp;手机验证码：</div>
            <div style="float:right; margin-top:5px;"><strong style="color:#F00;">*</strong></div>
        </div>
      <!--<div class="login_cente_login_left"><div style="float:right">&nbsp;手机验证码：</div><div style="float:right; margin-top:5px;"><strong style="color:#F00;">*</strong></div></div>-->
      <div class="login_cente_login_left">推荐人（选填）：</div>
      <div>&nbsp;</div>
    </div>
    <div style="float:left;">
      <form action="/account/register.do" method="post" id="f1">
          <input name="paramMap.passwords" value="${passwords}" type="hidden"/>
          <input name="paramMap.userid" value="${userid}" type="hidden"/>
          <input name="paramMap.pageId" value="userregister" type="hidden"/>
          <input name="via" value="${param.via}" type="hidden"/>
          <input name="source" value="${param.source}" type="hidden"/>
          <input name="fromUrl" value="${param.fromUrl}" type="hidden"/>
      <div class="login_cente_login_right">
        <input type="text" class="login_tex1" name="userName" id="userName"  value="${param['userName']}" style=" background:url(/images/v1/id.png) no-repeat;background-position:270px 8px;"/>
          ${fieldErrors.userName[0]}
        <br>
        <span style="color:#f30;" id="s_userName"
								class="formtips"></span></div>
      <div class="login_cente_login_right">
        <input type="password" placeholder="6~16个字符，不能包含空格" onkeydown="if(event.keyCode==32||event.keyCode==188||event.keyCode==222){return false;}"  class="login_tex1" name="pwd" id="password" value="${param['pwd']}" style=" background:url(/images/v1/pass.png) no-repeat;background-position:270px 8px;"/>
          ${fieldErrors.pwd[0]}
        <br>
        <span style="color: red;" id="s_password" class="formtips"></span></div>
      <%--<div style=" font-size:12px; color:#999; margin-top:10px;">6~16个字符，不能包含空格,使用【字母+数字】的组合</div>--%>
      <div class="login_cente_login_right">
        <input type="password"  onkeydown="if(event.keyCode==32||event.keyCode==188||event.keyCode==222){return false;}"  class="login_tex1" name="confirmPassword" id="confirmPassword" value="${param['confirmPassword']}" style=" background:url(/images/v1/pass.png) no-repeat;background-position:270px 8px;"/>
          ${fieldErrors.confirmPassword[0]}
        <br>
        <span style="color: red;" id="s_confirmPassword" class="formtips"></span></div>
      <div class="login_cente_login_right">
        <input type="text" class="login_tex1" name="mobilePhone" id="telephone" value="${param['mobilePhone']}" style="width: 144px" />
          <a href="javascript:;" onclick="getTelephoneCode()" id="ida" disable="no" class="sendBtn">
              获取验证码
          </a>
          ${fieldErrors.mobilePhone[0]}
        <br />
        <span style="color: red;" id="s_telephone" class="formtips"></span>

      </div>
          <div class="login_cente_login_right">
              <input type="text" class="login_tex1" name="identifyCode" id="identifyCode" value="${param['identifyCode']}" style="width: 144px" />

              ${fieldErrors.identifyCode[0]}
              <br />
              <span style="color: red;" id="s_telephone" class="formtips"></span>

          </div>
      <!-- <div class="login_cente_login_right"><input type="text" class="login_tex2" name="paramMap.confirmTelephone" id="confirmTelephone" />
					                 <span style="margin-left:10px; float:left; margin-top:8px;">输入您收到的短信验证码</span>
					               <br><span style="color: red;margin-left: 32px;" id="s_confirmTelephone" class="formtips"></div>-->
      <div class="login_cente_login_right">
        <input type="text" class="login_tex1" name="refferee" id="refferee" value="${paramMap.userId==null?param['refferee']:paramMap.userId}" />
          ${fieldErrors.refferee[0]}
        <br>
        <span style="color: red;" id="s_refferee" class="formtips">
      </div>
      <div style=" font-size:12px; color:#999; margin-top:10px;">填写推荐人ID号或手机号码<br />
        您也可在后台查看自己ID号并推荐好友注册</div>
      </form>
    </div>
    <div style=" float:left; width:380px; height:300px; padding-left:40px; padding-top:100px;"><img src="/images/hhnimages/hhn/login_bgr.png" />
</div>
  </div>
  <div style=" text-align:center; line-height:40px; ">
    <input type="checkbox" id="agre" checked="checked" />
    我已经阅读并同意<a href="/zcxy.html" target="_blank"  style="color:#4aa3ff;">《注册协议》</a></div>
  <div style=" text-align:center; margin-bottom:70px;">
    <input type="button" id="btn_register" value="注册"  style=" width:238px; height:40px; border:0px; background:#f90; font-size:16px;color:#FFF; margin-left:0px;"/>
  </div>
</div>

</form>
<jsp:include page="/include/footer.jsp"></jsp:include>
<script type="text/javascript" src="css/popom.js"></script> 
<script type="text/javascript" src="script/jquery.shove-1.0.js"></script> 
<script type="text/javascript" src="script/jbox/jquery.jBox-2.3.min.js"></script> 
<script type="text/javascript" src="script/jbox/jquery.jBox-zh-CN.js"></script> 
<script type="text/javascript" src="/script/phone_verify.js"></script>
<script>
		function login() {
			window.location.href = "/account/login-index.do";
		}
		//验证码
		function switchCode() {
			var timenow = new Date();
			$("#codeNum").attr("src", "/admin/imageCode.do?pageId=userregister&d=" + timenow);
		}
		//回车登录
		document.onkeydown = function(e) {
			if (!e)
				e = window.event;
			if ((e.keyCode || e.which) == 13) {
				regg();
			}
		}

		//动态展示协议
		$(document).ready(function(){
			   $("#show").toggle(function(){
			    $("#all").fadeTo("slow",1);
			   },function(){
			    $("#all").fadeOut("slow");
			   });
		});
		
		//获取手机验证码
		function getTelephoneCode() {

			if ($("#ida").attr("disable") == "no") {
                var ret = verifyPhone_($("#telephone").val());
                if(ret!=""){
                    $("#s_telephone").attr("class", "formtips onError");
                    $("#s_telephone").html(ret);
                }else {
					$("#s_telephone").attr("class", "formtips");
					$("#s_telephone").html("");
                    $.post("/account/check-phone.do",{mobilePhone:$("#telephone").val()},function(data){
                        if(data=="0"){
                            getTelephoneCode_($("#telephone").val());
                        }else{
                            alert("手机号码已存在");
                        }
                    });

				}
			}
		}

	</script> 
<script>
		$(document).ready(function() {
			$("#btn_register").click(function() {
				regg();
			});
			$("#refferee").focus(function(){
				$("#sp_refferee").html("");
				$("#sp_refferee").attr("class", "formtips");
			});
			//失去焦点
			$("form :input").blur(function() {
				//userName
				if ($(this).attr("id") == "userName") {
					if (this.value == "") {
						$("#s_userName").attr("class", "formtips onError");
						$("#s_userName").html("请输入登录用户名");
					} else if (this.value.length < 5 || this.value.length > 25) {
						$("#s_userName").attr("class", "formtips onError");
						$("#s_userName").html("用户名长度为5-25个字符");
					} else if (!/^[A-Za-z0-9_]+$/.test(this.value)) {
						$("#s_userName").attr("class", "formtips onError");
						$("#s_userName").html("用户名由字母数字下划线组成");
					} else {
						$("#s_userName").html("");
						checkRegister('userName');
						$("#s_userName").attr("class", "formtips");
					}
				}
				//password
				if ($(this).attr("id") == "password") {
					pwd = this.value;
					if (this.value == "") {
						$("#s_password").attr("class", "formtips onError");
						$("#s_password").html("请设置您的密码");
					} else if (this.value.indexOf(" ")>=0) {
                        $("#s_password").attr("class", "formtips onError");
                        $("#s_password").html("密码不能包含空格");
                    }else if (this.value.length < 6) {
						$("#s_password").attr("class", "formtips onError");
						$("#s_password").html("密码长度为6-20个字符");
					} else {
						$("#s_password").html("");
						checkRegister('password');
						$("#s_password").attr("class", "formtips");
					}
				}
				//confirmPassword
				if ($(this).attr("id") == "confirmPassword") {
					if (this.value == "") {
						$("#s_confirmPassword").attr("class", "formtips onError");
						$("#s_confirmPassword").html("请再次输入密码确认");
					} else if (this.value != $("#password").val()) {
						$("#s_confirmPassword").attr("class", "formtips onError");
						$("#s_confirmPassword").html("两次输入的密码不一致");
					} else {
						$("#s_confirmPassword").attr("class", "formtips");
						$("#s_confirmPassword").html("");
					}
				}
				//telephone
				if ($(this).attr("id") == "telephone") {
					if (this.value == "") {
						$("#s_telephone").attr("class", "formtips onError");
						$("#s_telephone").html("请输入手机号");
					} else  {
                        var re =new RegExp("/^1[3|5|7|8|][0-9]{9}$/");
                        if (!/^0{0,1}(13[0-9]|15[0-9]|18[0-9]|14[0-9]|17[0-9])[0-9]{8}$/.test(this.value)){
                            $("#s_telephone").attr("class", "formtips onError");
                            $("#s_telephone").html("输入手机号的格式有误");
                        }else {
                            $("#s_telephone").attr("class", "formtips");
                            $("#s_telephone").html("");
                        }
					}
				}

			//----add by houli  推荐人 refferee
				if ($(this).attr("id") == "refferee") {
					if (this.value != "") {//如果推荐人不为null，则进行判断，判断经纪人是否有效
						if(isNaN(this.value)){
							$("#s_refferee").attr("class", "formtips onError");
							$("#s_refferee").html("推荐人不存在");
						}else{
							$.post("queryValidRecommer.do", {refferee : this.value}, function(data) {
								if (data == 1) {
									$("#s_refferee").attr("class", "formtips onError");
									$("#s_refferee").html("推荐人不存在");
								} else {
									$("#s_refferee").attr("class", "formtips");
									$("#s_refferee").html("");
								}
							});
						}
					} else {
						$("#s_refferee").attr("class", "formtips");
						$("#s_refferee").html("");
					}
				}
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

				$('#btn_register').attr('value', '注册中...');
				var param = {};
                $("#f_passwords").val("${passwords}"); //彩之云
                $("#f_userid").val( "${userid}"); //彩之云
                $("#f1").submit();

			}
		}
	</script>
<script>
    (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
        (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
            m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
    })(window,document,'script','//www.google-analytics.com/analytics.js','ga');

    ga('create', 'UA-53576224-1', 'auto');
    ga('send', 'pageview');

</script>
</body>
</html>
