<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script src="script/jquery-1.7.1.min.js" type="text/javascript"></script>
<script>
	$(function() {
		$(".tabmain").find("li").click(function() {
			$(".tabmain").find("li").removeClass("on");
			$(this).addClass("on");
			$(".lcmain_l").children("div").hide();
			$(".lcmain_l").children("div").eq($(this).index()).show();
		});

		$("#send_password").click(function() {
			var param = {};
			param["paramMap.email"] = $("#email").val();
			param["paramMap.username"] = $("#uname").val();
			$.post("forgetpasswordsenEml.do", param, function(data) {
				if (data.mailAddress == "0") {
					$("#s_email").html("邮箱不能为空");
				} else if (data.mailAddress == "1") {
					$("#s_email").html("用户名或邮箱不存在");
				} else {
					var html = "<tr height='200' style='font-size: 14px;'><td>邮件已经发送到你的邮箱,请<a href='http://" + data.mailAddress+"' ";
					$("#ok").html(html + " target='_blank'>登录</a>到你的邮箱验证</td></tr>");
				}
			});
		});

	});
</script>
<jsp:include page="/include/head.jsp"></jsp:include>
</head>

<body>
	<jsp:include page="/include/top.jsp"></jsp:include>
	<div class="cle"></div>
	<div class="center">
		<div class="wytz_center">
			<div class="login_center">
				<div class="login_center_top">找回密码</div>
				<div align="center" style="text-align: center;">
					<table width="700" border="0" class="mm_zh_all" style="margin: 0 auto;" id="ok">
						<tr height="50"></tr>
						<tr>
							<td width="300" align="right">要找回的账号用户名：&nbsp;&nbsp;</td>
							<td width="400" align="left"><input type="text" id="uname" /></td>
						</tr>
						<tr height="25">
						</tr>
						<tr>
							<td width="300" align="right">输入您的安全邮箱：&nbsp;&nbsp;</td>
							<td width="400" align="left"><input type="text" id="email" /> <span id="s_email" style="color: red;font-size: 12px;"></span></td>
						</tr>
						<tr height="25"></tr>
						<%--					<tr>--%>
						<%--						<td  align="right">验证码：</td>--%>
						<%--						<td  align="center"><input type="text" />--%>
						<%--						<img src="images/login_yzm.jpg" /><a href="#">看不清，换一张</a>--%>
						<%--						</td>--%>
						<%--					</tr>--%>
						<tr>
							<td align="center" colspan="2"><input type="button" value="确定" id="send_password" class="scbtn" style="margin-left: -120px;" />
							</td>
						</tr>
						<tr height="25">
							<td align="center" colspan="2">
								<%--					<span id="ok" style="color: red;font-size: 12px;margin-left: -120px;">xxxxoooooo</span> --%>
							</td>
						</tr>
					</table>
				</div>
				<div class="cle"></div>
			</div>
			<div class="cle"></div>
		</div>
	</div>

	<!-- 引用底部公共部分 -->
	<jsp:include page="/include/footer.jsp"></jsp:include>
	<script type="text/javascript" src="css/popom.js"></script>
</body>
</html>
