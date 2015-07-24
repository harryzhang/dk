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
       $.post("updatetranpassword.do",param,function(data){
        if(data.mailAddress=='0'){
          $("#s_email").html("邮箱不能为空");
        }else if(data.mailAddress=='1'){
          $("#s_email").html("该邮箱不存在");
        }else if(data.mailAddress==3){
          $("#s_email").html("邮箱填写错误！");
        }else{
          $("#ok").html("邮件已经发送到你的邮箱,请<a href=http://"+data.mailAddress+">登录</a>到你的邮箱验证");
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
  <div class="til">找回交易密码</div>
  <div class="sqdk" style="padding-top:55px; padding-bottom:80px; padding-left:300px; background:none;">
   <!-- - 
   -->
   
    <div class="logintab" id="ok">
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
  <span style="color: red; float: none;" id="s_email"
														class="formtips">${msg }</span>
    <th align="right">请输入您注册时用的Email：</th>
    <td><input type="text" class="inp202" id="email"/>
    </td>
  </tr>
  <tr>
    <th align="right">&nbsp;</th>
    <td >
    <input type="button" value="发送" class="chaxun" id="send_password"/>
    </td>
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



</body>
</html>
