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
  <div class="til">手机会员注册</div>
  <div class="sqdk" style="padding-top:55px; padding-bottom:80px; padding-left:60px;">
    <div class="logintab">
      <form action="" method="post">
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
   <tr>
    <td colspan="2" align="center" width="400px;"> <span style="color: red;width: 100%;" >
    <s:if test="#session.DEMO==1">*　演示站点不发送短信，效验码随意填写</s:if><s:else>
   			 合和年在线向您的手机发送了一条短信校验码，请立即查收!
   			 </s:else>
    </span>
    </td>
  </tr>
    <tr>
    <th align="right"><span class="fred">*</span>手机号：</th>
    <td>${cellphone}<input type="hidden" value="${cellphone}" id="cellphone"/>
    </td>
  </tr>
  <tr>
    <th align="right"><span class="fred">*</span>短信校验码：</th>
    <td><input type="text" class="inp100"  name="paramMap.cellcode" id="cellcode"/>
    </td>
  </tr>
    <tr>
    <th align="right"></th>
    <td> <span style="color: red" id="s_cellcode" class="formtips"></span>
    </td>
  </tr>
  <tr>
    <th align="right"><span class="fred">*</span>用户名：</th>
    <td ><input type="text" class="inp202" name="paramMap.userName"  id="userName"/>
    </td>
  </tr>
      <tr>
    <th align="right"></th>
    <td> <span  style="color: red" id="s_userName" class="formtips"></span>
    </td>
  </tr>
  <tr>
    <th align="right"><span class="fred">*</span>密码：</th>
    <td><input  type="password" class="inp202" name="paramMap.password" id="password"/>
    </td>
  </tr>
       <tr>
    <th align="right"></th>
    <td> <span  style="color: red" id="s_password" class="formtips"></span>
    </td>
  </tr>
  <tr>
    <th align="right"><span class="fred">*</span>确认密码：</th>
    <td ><input type="password" class="inp202" name="paramMap.confirmPassword" id="confirmPassword"/>
    </td>
  </tr>
       <tr>
    <th align="right"></th>
    <td><span  style="color: red" id="s_confirmPassword" class="formtips"></span>
    </td>
  </tr>
  <tr>
    <th align="right">推荐人：</th>
    <td>
      <input type="text" class="inp202" name="paramMap.refferee" id="refferee" value="${paramMap.refferee }" />
    </td>
  </tr>
    <tr>
    <th align="right"></th>
    <td><span id="s_refferee" class="formtips"></span>
    </td>
  </tr>
      <tr>
    <th align="right"></th>
    <td><span  style="color: red" id="s_code" class="formtips"></span>
    </td>
  </tr>
  <tr>
    <th align="right">&nbsp;</th>
   <td><input type="button" id="btn_cellregs" value="免费注册" class="zcbtn" style="cursor: pointer;"/></td>
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
<script type="text/javascript" src="script/nav-zh.js"></script>
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
		   if('${DEMO}'!=1){
			     if($(this).attr("id")=="cellcode"){
			       if(this.value==""){
			        $("#s_cellcode").attr("class", "formtips onError");
			        $("#s_cellcode").html("请填写短信校验码");
			       }else{
			          $("#s_cellcode").attr("class", "formtips");
			        $("#s_cellcode").html("");
			       }
			     }
		   }
		   //userName
		   if($(this).attr("id")=="userName"){
		     if(this.value==""){
		     $("#s_userName").attr("class", "formtips onError");
		       //alert("请输入登录用户名");
		        $("#s_userName").html("请输入登录用户名");
		     }else if(this.value.length<2){ 
		     $("#s_userName").attr("class", "formtips onError");
		      //alert("用户名长度为5-20个字符");
		      $("#s_userName").html("用户名长度为2-20个字符"); 
		    // }else if(!/^[^@\/\'\\\"#$%&\^\*]+$/.test(this.value)){
		       }else if(!/^[\u4E00-\u9FA5A-Za-z0-9_]+$/.test(this.value)){
		        $("#s_userName").attr("class", "formtips onError");
		        $("#s_userName").html("用户名不能含有特殊字符"); 
		     } else{
		     $("#s_userName").html(""); 
		      checkRegister('userName');
		      $("#s_userName").attr("class", "formtips");
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
		      checkRegister('password');
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
		 
		 //提交
		     });
		     //验证数据
		     	function checkRegister(str){
		     	  var param = {};
		     	  if(str == "userName"){
                    param["paramMap.email"] = "";
				    param["paramMap.userName"] = $("#userName").val();    		     	  
		     	  }else{
		     	     param["paramMap.email"] = $("#email").val();
				     param["paramMap.userName"] = "";
		     	  }
		     	  $.post("ajaxCheckRegister.do",param,function(data){
		     	  if(data == 3 || data == 4){
		     	     if(str=="userName"){
		     	     $("#s_userName").html("该用户已存在");
		     	     }else{
		     	      $("#s_email").html("该邮箱已注册");
		     	     }
		     	  }
		     	  });
		     	  
		     	}
		     //========
		     
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
			         if('${DEMO}'!=1){
			            $("#s_cellcode").html("短信校验码错误");
			            falg = true;
			            return false;
			         }
		         }else if($("#userName").val()==""){
		           $("#s_userName").html("请输入登录用户名");
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
		        	alert('请正确填写注册信息');
		        	falg = true;
		            return false;
		            }else{
		           }
		         if($("#cellphone").val()==""){
		            alert("手机号码错误");
		            falg = true;
		            return false;
		         } 
		         $('#btn_cellregs').attr('value','注册中...');
		         var param = {};
		         param["paramMap.cellphone"]= $("#cellphone").val();
		         param["paramMap.cellcode"] = $("#cellcode").val();
		         param["paramMap.userName"] = $("#userName").val();
		         param["paramMap.password"] = $("#password").val();
		         param["paramMap.confirmPassword"] = $("#confirmPassword").val();
		         param["paramMap.refferee"] = $("#refferee").val();
		         param["paramMap.code"] = $("#code").val();
		         param["paramMap.param"] = $("#param").val();
		         $.post("cellreginfo.do",param,function(data){
		           if(data.mailAddress=='0'){
		             $("#s_code").html("验证码输入有误，请重新输入");
		              $('#btn_cellregs').attr('value','免费注册');
		              falg = true;
		           }else if(data.mailAddress=='1'){
		            $('#btn_cellregs').attr('value','免费注册');
		            $("#s_confirmPassword").html("两次输入的密码不一致"); 
		            falg = true;
		           }else if(data.mailAddress=='2'){
		            $('#btn_cellregs').attr('value','免费注册');
		            $("#s_userName").html("该用户已存在");
		            falg = true;
		           }else if(data.mailAddress=='3'){
		            $('#btn_cellregs').attr('value','免费注册');
		            $("#s_email").html("该邮箱已注册");
		            falg = true;
		           }else if(data.mailAddress=='4'){
		            alert("注册失败！");
		             $('#btn_cellregs').attr('value','免费注册');
		             falg = true;
		           }else if(data.mailAddress=='5'){
		            $('#btn_cellregs').attr('value','免费注册');
		            falg = true;
		           	alert("推荐人填写错误！");
		           }else if(data.mailAddress=='12'){
		            $('#btn_cellregs').attr('value','免费注册');
		            falg = true;
		             $("#s_email").html("请输入您的邮箱地址");
		           }
		           else if(data.mailAddress=='13'){
		            $('#btn_cellregs').attr('value','免费注册');
		            falg = true;
		              $("#s_userName").html("请输入登录用户名");
		           }
		           else if(data.mailAddress=='14'){
		            $('#btn_cellregs').attr('value','免费注册');
		              $("#s_password").html("请设置您的密码"); 
		              falg = true;
		           }
		             else if(data.mailAddress=='15'){
		              $('#btn_cellregs').attr('value','免费注册');
		             $("#s_confirmPassword").html("请再次输入密码确认"); 
		             falg = true;
		           }else if(data.mailAddress=='16'){
		            $('#btn_cellregs').attr('value','免费注册');
		            falg = true;
		            alert("注册失败");
		           }
		           else if(data.mailAddress=='18'){
		            $('#btn_cellregs').attr('value','免费注册');
		            $("#s_userName").html("用户名长度为2-20个字符"); 
		            falg = true;
		           }
		           else if(data.mailAddress=='20'){
		           $('#btn_cellregs').attr('value','免费注册');
		           $("#s_userName").html("用户名不能含有特殊字符"); 
		            falg = true;
		           }
		            else if(data.mailAddress=='21'){
		            $('#btn_cellregs').attr('value','免费注册');
		            $("#s_userName").html("用户名第一个字符不能是下划线"); 
		            falg = true;
		           }else if(data.mailAddress=='手机已存在'){
                     alert("该手机号码已经注册了");	
                     window.location.href='cellPhoneinit.do';
                     //将手机短信验证码移除
                     $.post("removecellCode.do","",function(data){});//删除手机验证码
		           }
		           else if(data.mailAddress=='请输入正确的验证码'){
		           	 $('#btn_cellregs').attr('value','免费注册');
		             $("#s_cellcode").html("短信校验码错误");
		              falg = true;
		           }
		           else if(data.mailAddress=='注册成功'){
		            alert("恭喜你!注册成功");
		            $.post("removecellCode.do","",function(data){
		              window.location.href="/account/login-index.do";
		            });//删除手机验证码
		           }
		         });
		       }

}


</script>
<script>
 var timers=60;
        $(function(){
         timers=120;
	     tipId=window.setInterval(timer,1000);
        });      
	   //定时
	
		   function timer(){
		       if(timers>=0){
		         timers--;
		       }else{
		          window.clearInterval(tipId);
		          $.post("removecellCode.do","",function(data){});
		       }
		   }
</script>



</body>
</html>
