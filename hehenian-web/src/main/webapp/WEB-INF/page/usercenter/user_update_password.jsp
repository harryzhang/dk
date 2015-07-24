<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<script src="script/jquery-1.7.1.min.js" type="text/javascript"></script>
		<script>
	$(function() {
		$('.tabmain').find('li').click(function() {
			$('.tabmain').find('li').removeClass('on');
			$(this).addClass('on');
			$('.lcmain_l').children('div').hide();
			$('.lcmain_l').children('div').eq($(this).index()).show();
		})
	})
</script>
<script>

	//修改密码
	function updateQusetionPass()
	{
		var password =  $("#password").val();
		var password1 =  $("#password1").val();
		param["paramMap.password"] = password;
		param["paramMap.password1"] = password1;
		param["paramMap.type"] = 1;
		param["paramMap.userName"] = $("#userName").val();
		if(password != password1)
		{
			$("#s_password1").show();
			$("#s_password1").html("<font style='color:red'>两次密码输入不一致</font>");
			return false;
		}else{
			$("#s_password1").hide();
			$.post("updateQusetionPass.do", param, function(data) {
				if (data == '1') {
					alert("修改成功");
					window.location.href="home.do"
				}else
				{
					alert("修改失败");
				}
			});
		}
	}
</script>
		<jsp:include page="/include/head.jsp"></jsp:include>
	</head>

	<body>
		<jsp:include page="/include/top.jsp"></jsp:include>


		<div class="nymain">
			<div class="bigbox">
				<input type="hidden" id="userName" value="${request.userName}" />
				<div class="cle"></div>
				<div class="center">
					<div class="wytz_center">
						<div class="login_center">
							<div class="login_center_top">
								找回密码
							</div>
							<table width="900" border="0" class="mm_zh_all">
								<tr height="50"></tr>
								<tr>
									<td width="300" align="right">
										密码：
									</td>
									<td width="200" align="center">
										<input type="password" id="password" maxlength="20" />
									</td>
									<td width="400" align="">
										<span id="s_userName"></span>
									</td>
								</tr>
								<tr height="25"></tr>
								<tr>
									<td width="300" align="right">
										确认密码：
									</td>
									<td width="200" align="center">
										<input type="password" id="password1" maxlength="20" />
									</td>
									<td width="400" align="">
										<span id="s_password1"></span>&nbsp;
									</td>
								</tr>
								<tr height="25"></tr>
								<tr>
									<td align="center"></td>
									<td align="">
										<input type="button" value="确定" onclick="updateQusetionPass();"/>
									</td>
									<td align="center"></td>
								</tr>
							</table>
							<div class="cle"></div>
						</div>
						<div class="cle"></div>
					</div>
				</div>

			</div>
		</div>

		<!-- 引用底部公共部分 -->
		<jsp:include page="/include/footer.jsp"></jsp:include>
		<script type="text/javascript" src="css/popom.js"></script>

		<script>
	$(function() {
		//样式选中
		$("#zh_hover").attr('class', 'nav_first');
		$("#zh_hover div").removeClass('none');
	});
</script>



	</body>
</html>
