<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link href="css/css.css" rel="stylesheet" type="text/css" />
    <style type="text/css">
      ul,li{margin:0;padding:0}
      #scrollDiv{height:182px;overflow:hidden}
    </style>
</head>
<body>
<!-- 引用头部公共部分 -->
<jsp:include page="/include/top.jsp"></jsp:include>	

<div class="nymain">
  <div class="bigbox">
  <div class="til">会员忘记密码</div>
  <div class="sqdk" style="padding-top:55px; padding-bottom:80px; padding-left:60px;">
    <div class="logintab">
      <form action="" method="post">
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
   <tr>
    <td colspan="2" align="center"> <span style="color: red" >合和年信贷给您的手机发送了一条短信校验码，请立即查收!</span>
    </td>
  <tr>
    <tr>
    <th align="right">手机号：<span class="fred">*</span></th>
    <td>${cellphone}<input type="hidden" value="${sign}" id="cellphone"/>
    </td>
  <tr>
    <th align="right">短信校验码：<span class="fred">*</span></th>
    <td><input type="text" class="inp100"  name="paramMap.cellcode" id="cellcode"/>
    </td>
  </tr>
    <tr>
    <th align="right"></th>
    <td> <span style="color: red" id="s_cellcode" class="formtips"></span>
    </td>
  </tr>
  <tr>
    <th align="right">类型：<span class="fred">*</span></th>
    <td >
    
    <input type="radio" name="updatetype" checked="checked"/>登录密码 &nbsp;&nbsp;
    
    </td>
  </tr>
      <tr>
    <th align="right"></th>
    <td> <span  style="color: red" id="s_userName" class="formtips"></span>
    </td>
  </tr>
  <tr>
    <th align="right">新密码：<span class="fred">*</span></th>
    <td><input  type="password" class="inp202" name="paramMap.password" id="password"/>
    </td>
  </tr>
       <tr>
    <th align="right"></th>
    <td> <span  style="color: red" id="s_password" class="formtips"></span>
    </td>
  </tr>
  <tr>
    <th align="right">确认新密码：<span class="fred">*</span></th>
    <td ><input type="password" class="inp202" name="paramMap.confirmPassword" id="confirmPassword"/>
    </td>
  </tr>
       <tr>
    <th align="right"></th>
    <td><span  style="color: red" id="s_confirmPassword" class="formtips"></span>
    </td>
  </tr>
      <tr>
    <th align="right"></th>
    <td><span  style="color: red" id="s_code" class="formtips"></span>
    </td>
  </tr>
  <tr>
    <th align="right">&nbsp;</th>
   <td><input type="button" id="btn_cellregs" value="保存" class="zcbtn" style="cursor: pointer;"/></td>
  </tr>
    </table>
  </form> 
    </div>
    <div class="tip">
    <ul><li>帮助他人 快乐自己 收获利息</li>
<li>助您创业 资金周转 分期偿还</li>
<li>收益稳定可靠回报高</li>
<li>交易安全快捷有保障</li></ul>
    <div class="loginbtn">
    <a href="login.do" class="dlbtn">马上登录</a>
    </div>
    </div>
    <div class="renpic" style="top:50px;">
    
    </div>
  </div>
  </div>
</div>
<!-- 引用底部公共部分 -->     
<jsp:include page="/include/footer.jsp"></jsp:include>
<script>
//回车登录
document.onkeydown=function(e){
  if(!e)e=window.event;
  if((e.keyCode||e.which)==13){
    regg();
  }
}
</script>

<script>
$(function(){


    //样式选中
    $("#zh_hover").attr('class','nav_first');
	$('.tabmain').find('li').click(function(){
	$('.tabmain').find('li').removeClass('on');
	$(this).addClass('on');
	$('.lcmain_l').children('div').hide();
    $('.lcmain_l').children('div').eq($(this).index()).show();
	})
	})
	
		 $(document).ready(function(){
		 //失去焦点
		 $("form :input").blur(function(){ 
		   //短信码
		     if($(this).attr("id")=="cellcode"){
		       if(this.value==""){
		        $("#s_cellcode").attr("class", "formtips onError");
		        $("#s_cellcode").html("请填写短信校验码");
		       }else{
		          $("#s_cellcode").attr("class", "formtips");
		        $("#s_cellcode").html("");
		       }
		     }
		   //password
		   	 if($(this).attr("id")=="password"){
		   	    pwd=this.value; 
		     if(this.value==""){
		      	$("#s_password").attr("class", "formtips onError");
		       //alert("请设置您的密码");
		        $("#s_password").html("请设置您的密码");  
		     }else if(this.value.length<6){ 
		      	$("#s_password").attr("class", "formtips onError");
		      //alert("密码长度为6-20个字符");
		      $("#s_password").html("密码长度为6-20个字符"); 
		     }else{
		     $("#s_password").html(""); 
		      	$("#s_password").attr("class", "formtips");
		     }
		   }
		   //confirmPassword
		   
		     if($(this).attr("id")=="confirmPassword"){
		     if(this.value==""){
		     $("#s_confirmPassword").attr("class", "formtips onError");
		       //alert("请再次输入密码确认");
		       $("#s_confirmPassword").html("请再次输入密码确认"); 
		     }else if(this.value!=pwd){ 
		     $("#s_confirmPassword").attr("class", "formtips onError");
		      $("#s_confirmPassword").html("两次输入的密码不一致"); 
		     }else{
		      $("#s_confirmPassword").attr("class", "formtips");
		      $("#s_confirmPassword").html(""); 
		     }
		   }
		  });
		 
		     });
		     
</script>
<script>
   $("#btn_cellregs").click(function(){
		       regg();
		     });

</script>
<script>
var falg = true;
function regg(){
              if(falg){
              falg = false;
             var errornum=$("form .onError").length;
		         if($("#cellcode").val()==""){
		            $("#s_cellcode").html("短信校验码错误");
		            falg = true;
		            return false;
		         }else if($("#password").val()==""){
		         $("#s_password").html("请设置您的密码"); 
		         falg = true;
		         return false;
		         }else if($("#confirmPassword").val()==""){
		           $("#s_confirmPassword").html("请再次输入密码确认"); 
		           falg = true;
		           return false;
		         }else if(errornum > 0){   
		        	alert('信息填写错误');
		        	falg = true;
		            return false;
		            }else{
		           }
		         if($("#cellphone").val()==""){
		            alert("手机号码错误");
		            falg = true;
		            return false;
		         } 
		         $('#btn_cellregs').attr('value','提交中...');
		         var param = {};
		         param["paramMap.cellphone"]= $("#cellphone").val();
		         param["paramMap.cellcode"] = $("#cellcode").val();
		         param["paramMap.password"] = $("#password").val();
		         param["paramMap.confirmPassword"] = $("#confirmPassword").val();
		         $.post("cellphoneforgetinfo.do",param,function(data){
		           if(data.mailAddress=='请输入正确的验证码'){
		              $("#s_cellcode").html("请输入正确的验证码");
		              $('#btn_cellregs').attr('value','提交');
		              falg = true;
		           }else if(data.mailAddress=='与获取验证码手机号不一致'){
		                    $('#btn_cellregs').attr('value','提交');
		            $("#s_cellcode").html("与获取验证码手机号不一致");
		            falg = true;
		           }else if(data.mailAddress=='请填写验证码'){
		                      $('#btn_cellregs').attr('value','提交');
		           $("#s_cellcode").html("请填写验证码");
		            falg = true;
		           }else if(data.mailAddress=='请输入正确的验证码'){
		                     $('#btn_cellregs').attr('value','提交');
		             $("#s_cellcode").html("请输入正确的验证码");
		            falg = true;
		           }else if(data.mailAddress=='1'){
		                     $('#btn_cellregs').attr('value','提交');
		                 $("#s_password").html("请设置您的密码"); 
		             falg = true;
		           }else if(data.mailAddress=='2'){
		                   $('#btn_cellregs').attr('value','提交');
		               $("#s_password").html("密码长度不对，密码长度为6到20位"); 
		            falg = true;
		           }else if(data.mailAddress=='3'){
		                  $('#btn_cellregs').attr('value','提交');
		            $("#s_password").html("两次密码输入不同"); 
		            falg = true;
		           }
		           else if(data.mailAddress=='4'){
		             $('#btn_cellregs').attr('value','提交');
		            falg = true;
		            alert("提交失败");
		           }
		           else if(data.mailAddress=='5'){
		                alert("恭喜你!修改密码成功");
		            $.post("removecellCode.do","",function(data){
		              window.location.href="/account/login-index.do";
		            });//删除手机验证码
		           }else if(data.mailAddress=='6'){
		            $('#btn_cellregs').attr('value','提交');
		            falg = true;
		            alert("该用户不存在");
		           }
		         });
		       }

}


</script>
</body>
</html>
