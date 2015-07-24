<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html>
<head>
<%@ include file="../common/head.jsp" %>
		<title>${channel_name}</title>
</head>
<body class="bg-1">
	<header class="index-h">
		<div class="index-logo">
		</div>
		<p>您的随身理财管家</p>
	</header>
	<section class="log-area">
			<div>
				<input type="text" class="lis" id="userName" name="userName" placeholder="手机号码/用户名">
			</div>
			<div>
				<input type="password" class="lis" id="password" id="password" placeholder="登录密码">
			</div>
			<c:if test="${requestScope.loginStrategyInfo }">
				<div><input type="text" class="lis lis-check" id="code" name="code" placeholder="图片验证码">
					<span><img src="${loginServerUrl }/common/imageCode.do?pageId=userlogin" title="点击更换验证码" id="codeNum" /></span>
				</div>
			</c:if>
			<div>
		<a class="lis bg-s" id="login_btn" style="display: block;margin: auto;">立即登录</a>
			</div>
	</section>
	<section class="fnr">
	<p style="margin-top: .5rem;">
	<span><a href="${loginServerUrl }/account/resetPwdIndex.do">忘记密码？</a></span>
	|
	<span><a href="${loginServerUrl }/account/regIndex.do">注册理财账号</a></span>
	</p>
	</section>
	<script>

	$(function(){
		sbh();
		$("#login_btn").bind("click",function(){
			login();
		});
		//code 
		$("#codeNum").bind("click",function(){
			$(this).attr("src", "${loginServerUrl }/common/imageCode.do?pageId=userlogin&d=" + new Date().getTime());
		});

	});
	
	document.onkeydown = function(e) {
		if (!e)
			e = window.event;
		if ((e.keyCode || e.which) == 13) {
			login();
		}
	};
		
	function login(){
		var userName = $("#userName").val();
		var password = $("#password").val();
		if(userName.length==0 || password.length==0 ){
			popWindow("登录信息填写不完整");
			return;
		}
		var code = -1;
		if($("#code").val()) {
			code=$("#code").val();
			if($("#code").val().length==0) {
				popWindow("请输入验证码");
				return;
			}
		}
		var options = {type:"POST",url:"${loginServerUrl }/login/login.do",data:{userName:userName,password:password,code:code,fromUrl:'${fromUrl}'}};
		ajaxRequest(options,function(data){
		    if(data.result==1){
				popWindow("登录信息填写不完整");
			}else if(data.result==2){
				popWindow("验证码输入有误");
			}else if(data.result==3){
				if(data.loginStrategy == true) {
					loginDiv='<div><input type="text" class="lis lis-check" id="code" name="code" placeholder="图片验证码">';
					loginDiv+='<span><img src="${loginServerUrl }/common/imageCode.do?pageId=userlogin" title="点击更换验证码" id="codeNum" /></span></div>';
					//alert($("#code").val());
					if($("#code").length == 0 ) {
						$('.log-area').find("div").eq(1).after(loginDiv);
						$("#codeNum").bind("click",function(){
							$(this).attr("src", "${loginServerUrl }/common/imageCode.do?pageId=userlogin&d=" + new Date().getTime());
						});
					}
				}
				popWindow("用户名或密码错误");
			}else{
				window.location.href="${fromUrl}";
			}
		});
	}
	</script>
</body>
</html>