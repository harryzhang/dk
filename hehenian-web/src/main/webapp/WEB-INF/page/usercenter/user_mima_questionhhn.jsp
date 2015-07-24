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
var flag=false;
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

//跳转修改密码
function updatePwd()
{
	isExistUserName();
	if ($("#question").val() == "") {
		$("#s_question").show();
		$("#s_question").html("<font style='color:red'>请选择您的问题</font>");
		return false;
	} if($("#answer").val() == ""){
		$("#s_answer").show();
		$("#s_answer").html("<font style='color:red'>请输入您的答案</font>");
		return false;
	}else if(flag){
		param["paramMap.question"] = $("#question").val();
		param["paramMap.answer"] = $("#answer").val();
		$("#s_question").hide();
		$.post("doUpdateQusertionPwd.do", param, function(data) {
			if (data == '1') {
				$("#s_answer").show();
				$("#s_answer").html("<font style='color:red'>您的问题或者答案错误</font>");
				return false;
			}else
			{
				$("#s_answer").hide();
				window.location.href="doUpdatePwdes.do?userName="+$("#userName").val();
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
                <td width="200" align="center"><input type="text" id="userName" onblur="isExistUserName();"/></td>
                <td width="400" align=""><span id="s_userName"></span></td>
            </tr>
            <tr height="25"></tr>
            <tr>
                <td width="300" align="right">安全问题：</td>
                <td width="200" align="center"><select id="question">
												<option value="">请选择</option>
												<option value="您的生日">您的生日?</option>
												<option value="您父亲的姓名">您父亲的姓名?</option>
												<option value="您母亲的姓名">您母亲的姓名?</option>
												<option value="您高中班主任姓名">您高中班主任姓名?</option>
												<option value="您的出生地">您的出生地?</option>
												<option value="您初恋的姓名">您初恋的姓名?</option>
												<option value="您公司的名称">您公司的名称?</option>
												<option value="您的职业">您的职业?</option>
												<option value="您的爱好">您的爱好?</option>
												<option value="您的配偶">您的配偶?</option>
												<option value="您的小学校名">您的小学校名?</option>
												<option value="您的小学班主任姓名">您的小学班主任姓名?</option>
											</select></td>
                <td width="400" align=""><span id="s_question"></span></td>
            </tr>
            <tr height="25"></tr>
            <tr>
                <td width="300" align="right">问题答案：</td>
                <td width="200" align="center"><input type="text" id="answer"/></td>
                <td width="400" align=""><span id="s_answer"></span></td>
            </tr>
            <tr height="25"></tr>
            <tr>
                <td align="center"></td>
                <td align=""> <input type="button" value="确定" onclick="updatePwd();" /></td>
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
