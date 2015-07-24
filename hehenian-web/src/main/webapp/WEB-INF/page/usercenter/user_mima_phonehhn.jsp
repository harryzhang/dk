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
	var flag = false;
	var flag1 = false;
	//验证用户名是否存在
	function isExistUserName() {
		if ($("#userName").val() == null || $("#userName").val() == "") {
			$("#s_userName").show();
			$("#s_userName").html("<font style='color:red'>用户名不能为空</font>");
			return flag = false;
		}
		param["paramMap.userName"] = $("#userName").val();
		$.post("isExistUserName.do", param, function(data) {
			if (data == '1') {
				$("#s_userName").show();
				$("#s_userName").html("<font style='color:red'>用户名不存在</font>");
				return flag = false;
			} else {
				$("#s_userName").hide();
				return flag = true;
			}
		});

	}

	//获取手机验证码
	function getTelephoneCode() {
		param["paramMap.userName"] = $("#userName").val();
		param["paramMap.telephone"] = $("#telephone").val();
		if ($("#ida").attr("disable") == "no") {
			if ($("#telephone").val() == "") {
				$("#s_telephone").show();
				$("#s_telephone").html("<font style='color:red'>请输入手机号</font>");
				return flag1 = false;
			} else if (!/^1[3|4|5|8][0-9]\d{4,8}$/.test($("#telephone").val())) {
				$("#s_telephone").show();
				$("#s_telephone").html("<font style='color:red'>输入手机号的格式有误</font>");
				return flag1 = false;
			} else {
				//根据用户名验证手机号码是否存在
				$.post("isExistPhone.do", param, function(data) {
					if (data == '1') {
						$("#s_telephone").show();
						$("#s_telephone").html("<font style='color:red'>手机号码不存在</font>");
						return flag1 = false;
					} else {
						$("#s_telephone").hide();
						$.post("telephoneCode.do", param, function(data) {
							if (data == "-1") {
								$("#s_telephone").attr("class", "formtips onError");
								$("#s_telephone").html("验证码获取失败");
							}
							if (data == "5") {
								$("#s_telephone").attr("class", "formtips onError");
								$("#s_telephone").html("该手机号码已被使用");
							} else {
								$("#s_telephone").attr("class", "formtips");
								$("#s_telephone").html("");
								telephoneTimer();
							}
						});
					}
				});
			}
		}
	}
	//倒计时
	var count = 60;
	var timer;
	function telephoneTimer() {
		$("#ida").attr("disable", "yes");
		if (count == 1 || count == 2) {
			$("#message").text("获取成功..." + count + "s");
		} else {
			$("#message").text("获取中..." + count + "s");
		}
		timer = window.setTimeout("telephoneTimer()", 1000);
		count = count - 1;
		if (count == -1) {
			window.clearTimeout(timer);
			count = 10;
			$("#message").text("获取验证码");
			$("#ida").attr("disable", "no");
		}
	}

	//跳转修改密码
	function updatePwd() {
		param["paramMap.code"] = $("#code").val();
		param["paramMap.telephone"] = $("#telephone").val();
		isExistUserName();
		if ($("#telephone").val() == "") {
			$("#s_telephone").show();
			$("#s_telephone").html("<font style='color:red'>请输入手机号</font>");
			return false;
		} else if (!/^1[3|4|5|8][0-9]\d{4,8}$/.test($("#telephone").val())) {
			$("#s_telephone").show();
			$("#s_telephone").html("<font style='color:red'>输入手机号的格式有误</font>");
			return false;
		} else if ($("#code").val() == "") {
			$("#s_code").show();
			$("#s_code").html("<font style='color:red'>请输入您收到的验证码</font>");
			return false;
		} else if (flag) {
			$("#s_telephone").hide();
			$.post("doUpdatePwd.do", param, function(data) {
				if (data == '1') {
					$("#s_code").show();
					$("#s_code").html("<font style='color:red'>验证码错误</font>");
					return false;
				} else {
					$("#s_code").hide();
					window.location.href = "doUpdatePwdes.do?userName=" + $("#userName").val();
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
			<div class="cle"></div>
			<div class="center">
				<div class="wytz_center">
					<div class="login_center">
						<div class="login_center_top">找回密码</div>
						<table width="900" border="0" class="mm_zh_all">
							<tr height="50"></tr>
							<tr>
								<td width="300" align="right">用户名：</td>
								<td width="200" align="center"><input type="text" id="userName" onblur="isExistUserName();" maxlength="30" /></td>
								<td width="400" align=""><span id="s_userName"></span></td>
							</tr>
							<tr height="25"></tr>
							<tr>
								<td width="300" align="right">已绑定的手机号码：</td>
								<td width="200" align="center"><input type="text" id="telephone" maxlength="20" /></td>
								<td width="400" align=""><span id="s_telephone"></span>&nbsp; <a href="javascript:void()" onclick="getTelephoneCode();" id="ida" disable="no"><strong id="message">获取验证码</strong>
								</a></td>
							</tr>
							<tr height="25"></tr>
							<tr>
								<td width="300" align="right">验证码：</td>
								<td width="200" align="center"><input type="text" id="code" /></td>
								<td width="400" align=""><span id="s_code"></span>
								</td>
							</tr>
							<tr height="25"></tr>
							<tr>
								<td align="center"></td>
								<td align=""><input type="button" value="确定" onclick="updatePwd();" /></td>
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
