<%@ page contentType="text/html; charset=utf-8"%>
<!doctype html>
<html>
	<head>
		<%@ include file="../common/head.jsp" %>
		<title>红包理财-手机号码验证</title>
	</head>
	
	<body class="bg-2">
	<header class="top-bar">
		<a href="${remoteAddr }">
		<span class="icon-back">
			</span>
		</a>
		<p>手机号码验证</p>
	</header>
	<div class="real-tips">
	</div>
	<section class="sign-area">
			<div class="sign-style br-2">
			<span class="sign-lable">手机号</span>
				<input type="text" id="mobilePhone" name="mobilePhone" value="${mobilePhone}" readonly="readonly">
			</div>
			<div class="sign-style br-4 bb-1">
				<input type="text" id="identifyCode" name="identifyCode" placeholder="请输入短信验证码" class="ml-15">
				<a id="ida" class="sign-get-ck" href="javascript:;">获取验证码</a>
			</div>
			<div class="sign-sub">
			<input type="hidden" id="remoteAddr" value="${remoteAddr}">
			<c:if test="${identifyCode==null || identifyCode=='' }">
				<input type="button" value="提交" id="authBtn">
			</c:if>
			</div>
	</section>
	<%@ include file="../common/tail.jsp" %>
</body>
<script type="text/javascript">
$(function(){
//获取手机验证码
$('#ida').bind("click",function(){
	if ($(this).attr("disable") == null) {
	   var param = {checkPhone:true};
	   sendPhoneVirifyCode(param);
	}
});


	$("#authBtn").bind("click",function() {
		var identifyCode = $("#identifyCode").val();
		if (identifyCode == "") {
			 popWindow("请输入验证码！");
			 return;
		}
			var param = {};
			param["identifyCode"] = identifyCode;
			$.post("http://m.hehenian.com/product/checkIdentifyCode.do", param, function(data) {
				if (data.result == "Y") {
					window.location.href="http://m.hehenian.com/account/realAuth.do";
				}else{
					popWindow(data.msg);
				}
			},"json");
	});
	
})

function sendPhoneVirifyCode(param) {
			/* var mobilePhone = $("#mobilePhone");
			if(mobilePhone!=null){
				if(mobilePhone.val()==null || mobilePhone.val().length==0){
					popWindow("手机号码出错");
					return;
				}
				param["mobilePhone"]=mobilePhone.val();
			} */
			
			$.post("http://m.hehenian.com/product/sendIdentifyCode.do", param, function(data) {
				if(data.ret==1){
					popWindow("发送验证码失败");
				}else if(data.ret==2){
					popWindow("手机号码不存在");
				}else if(data.ret==3){
					popWindow("手机号码已存在");
				}else{
					$("#ida").attr("disable", true).text("60s重获");
					telephoneTimer_();
					//popWindow("验证码："+data.ret);
				}
			},"json");
	}
//倒计时
function telephoneTimer_() {
	var count = 60;
	var timer=setInterval(function(){
		if(count <= 0){
			clearInterval(timer);
			$("#ida").removeAttr("disable").text("获取验证码");
		}else{
			count--;
			$("#ida").text(count+"s重获");
		}
	},1000);
}
</script>
</html>
