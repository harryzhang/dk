<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script src="script/jquery-1.7.1.min.js" type="text/javascript"></script>
<script>
$(function(){
	$('.tabmain').find('li').click(function(){
	$('.tabmain').find('li').removeClass('on');
	$(this).addClass('on');
	$('.lcmain_l').children('div').hide();
    $('.lcmain_l').children('div').eq($(this).index()).show();
	})
	})
</script>
<script>
  $(function(){
  
    $("#send_password").click(function(){
       var param = {};
       param["paramMap.email"] = $("#email").val();
       //alert(param["paramMap.email"]);
       $.post("forgetpasswordsenEml.do",param,function(data){
        if(data==0){
          $("#s_email").html("邮箱不能为空");
        }else if(data==2){
          $("#ok").html("邮件已将发送到你的邮箱，请登录你邮箱");
        }else{
          $("#s_email").html("该邮箱不存在");
        }
       });
    });
  
  
  });


</script>
   <jsp:include page="/include/head.jsp"></jsp:include>
</head>

<body>
<jsp:include page="/include/top.jsp"></jsp:include>	


<div class="nymain">
  <div class="bigbox">
  <div class="til">修改会员交易密码</div>
  <div class="sqdk" style="padding-top:55px; padding-bottom:80px; padding-left:300px; background:none;">
  <div id="s_tip" align="left" style="color: red;"></div>
    <div class="logintab" id="ok">
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
  
  
  
  <tr>
    <td align="right">新密码：</td>
    <td><input type="password" class="inp188"  id="newPassword"/>
      <span class="txt" style="color: red;">*输入您的新密码</span></td>
  </tr>
  <tr>
    <td align="right">确认新密码：</td>
    <td><input type="password" class="inp188" id="confirmpassword" />
      <span class="txt" style="color: red;">*请再次输入您的新密码</span></td>
  </tr>
  <tr>
    <td align="right">&nbsp;</td>
    <td style="padding-top:10px;"><input type="button" value="提交" class="bcbtn" id="bt_yes"/></td>
  </tr>
    </table>
    </div>
    
    <!--
    
      - -->
  </div>
  </div>
</div>

<!-- 引用底部公共部分 -->     
<jsp:include page="/include/footer.jsp"></jsp:include>
<script type="text/javascript" src="script/nav-zh.js"></script>
<script type="text/javascript" src="css/popom.js"></script>

<script>
$(function(){
    //样式选中
     $("#zh_hover").attr('class','nav_first');
	 $("#zh_hover div").removeClass('none');
});
</script>
<script>
$(function(){
   $("#newPassword").blur(function(){
     if($("#newPassword").val()==""){
       $("#s_tip").html("新密码不能为空");
     }else{
       $("#s_tip").html("");
     }
   });
   $("#confirmpassword").blur(function(){
      if($("#newPassword").val()!=$("#confirmpassword").val()){
        $("#s_tip").html("两次密码不相同");
      }else{
       $("#s_tip").html("");
      }
   });

  $("#bt_yes").click(function(){
    var param = {};
    param["paramMap.newPassword"] =  $("#newPassword").val();
    param["paramMap.userId"] = "${userId}";
    param["paramMap.confirmpassword"] = $("#confirmpassword").val();
    $.post("updateTrancePassword.do",param,function(data){
     if(data==1){
     alert("修改密码成功");
     window.location.href='login.do';
     }else if(data==3){
       $("#s_tip").html("新密码不能为空");
     }else if(data==4){
    	 alert("出错了");
     }else if(data==0){
     $("#s_tip").html("修改密码失败,请重新填写");
     }else if(data==5){
        $("#s_tip").html("两次输入密码不相同,请从新输入");
     }else if(data==6){
       $("#s_tip").html("输入密码长度错误，长度范围为6-20位");
     }
    })
  });

});

</script>


</body>
</html>
