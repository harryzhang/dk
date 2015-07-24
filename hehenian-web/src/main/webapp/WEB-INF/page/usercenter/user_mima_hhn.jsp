<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script src="script/jquery-1.7.1.min.js" type="text/javascript"></script>
<style>
login_center_top {
	width: 100%;
	height: 40px;
	background: url(images/login_bg_1.jpg) repeat-x;
	line-height: 40px;
	text-align: center;
	font-size: 18px;
	color: #666;
}

.login_center {
	width: 100%;
	border: 1px solid #e5e5e5;
	margin-top: 15px;
	border-top: none;
	background: #fff;
	margin-left: 15px;
	margin-bottom: 20px;
	min-height: 450px;
	text-align: center;
	
}

.mm_zh_all {
	margin: auto;
}
</style>
<script>
	$(function() {
		$('.tabmain').find('li').click(function() {
			$('.tabmain').find('li').removeClass('on');
			$(this).addClass('on');
			$('.lcmain_l').children('div').hide();
			$('.lcmain_l').children('div').eq($(this).index()).show();
		});

		$("#send_password").click(function() {
			var param = {};
			param["paramMap.email"] = $("#email").val();
			//alert(param["paramMap.email"]);
			$.post("forgetpasswordsenEml.do", param, function(data) {
				if (data.mailAddress == '0') {
					$("#s_email").html("邮箱不能为空");
				} else if (data.mailAddress == '1') {
					$("#s_email").html("该邮箱不存在");
				} else {
					$("#ok").html("邮件已经发送到你的邮箱,请<a href=http://"+data.mailAddress+">登录</a>到你的邮箱验证");
				}
			});
		});

	});

	//邮箱找回
	function emailhhn() {
		window.location.href = "emailhhn.do";
	}
	//手机找回
	//function phonehhn() {
//		window.location.href = "phonehhn.do";
//	}
	//安全码找回
	function questionhhn() {
		window.location.href = "questionhhn.do";
	}
</script>
<jsp:include page="/include/head.jsp"></jsp:include>
</head>

<body>
<div style="position: absolute; height:54px; line-height:54px; background:#f3f3f3; width:100%; margin-top:144px;border-bottom:1px solid #e5e5e5;border-top:1px solid #e5e5e5;"></div>
	<jsp:include page="/include/top.jsp"></jsp:include>

	<div class="nymain">
		<div class="bigbox">
			<div class="center">
                		<div style=" text-align:center; margin-top:10px;"  class="login_cente_login" >
			
                    为节约您的时间，请通过下面的两种方式找回密码，若这两种方式都无法找回，请通过人工审核方式找回密码！

          
			
                </div>
                
                <div style="width:1000px;margin:50px auto; overflow:hidden">	
                <span>通过邮箱找回密码</span><input type="button" value="立即找回" onclick="emailhhn();" class="scbtn"/>
                
                
                <span>通过安全问题找回密码</span><input type="button" value="立即找回" onclick="questionhhn();" class="scbtn"/>
                
                </div>
				
					
						
					<!--	<table width="667" border="0" class="mm_zh_all">-->	

<%--							<tr height="35"></tr>--%>
<%--							<tr>--%>
<%--								<td width="300" align="right"><span>通过手机找回密码</span></td>--%>
<%--								<td width="67"></td>--%>
<%--								<td width="300" align="left"><input type="button" value="立即找回" onclick="phonehhn();" class="scbtn" /></td>--%>
<%--							</tr>--%>

<%--							<tr height="35"></tr>--%>
<%--							<tr>--%>
					<!--	</table>-->
						
					
				
				
		
		</div>
	</div>

	<!-- 引用底部公共部分 -->
	<jsp:include page="/include/footer.jsp"></jsp:include>
	<script type="text/javascript" src="css/popom.js"></script>
</body>
</html>
