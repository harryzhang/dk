<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<jsp:include page="/include/head.jsp"></jsp:include>

</head>
<body>
<div class="box" >
 <div class="boxmain2">
  <div class="biaoge2">
   
   <table width="100%" border="0" cellspacing="0" cellpadding="0">
   
     <tr><td>&nbsp;</td><td>&nbsp;</td></tr>
     <tr>
        <td width="14%" height="30">发件人：</td>
        <td height="30">
          ${request.sender.userName}
        <span style="color: red; float: none;"  class="formtips"></span>
        </td>
     </tr>
     <tr>
       <td height="30">收件人：</td>
       <td height="30">
        <input type="text" id="receiver"  />
        <span style="color: red; float: none;" id="s_receiver" class="formtips"></span>
       </td>
     </tr>
     <tr>
        <td height="30">标题：</td>
        <td height="30">
         
          <input type="text" id="title"  name="paramMap.mailTitle" />
         <span style="color: red; float: none;" id="s_title" class="formtips"></span>
       </td>
     </tr>
     <tr>
       <td valign="top" height="100">内容：</td>
       <td>
          
          <textarea name="paramMap.mailContent" id="content" rows="5" cols="10"></textarea>
          <span style="color: red; float: none;" id="s_content" class="formtips"></span>
       </td>
     </tr>
      <tr>
    <td>验证码：</td>
    <td><input type="text" class="inp100x" id="code"/>
    <img src="admin/imageCode.do?pageId=userlogin" title="点击更换验证码"
		style="cursor: pointer;" id="codeNum" width="46" height="18"
		onclick="javascript:switchCode()" />
		<span style="color: red; float: none;" id="s_code" class="formtips"></span>
      </td>
  </tr>
  
    <tr>
     <td>&nbsp;</td>
     <td style="padding-top:20px;"><input type="button" onclick="addMail()" value="确定发送"/></td>
    </tr>
  
   </table>

    </div>
  </div>
</div>
<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="../css/admin/popom.js"></script>
<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
<script>
 $(function(){
     //收件人
       $("#receiver").blur(function(){
	     if($("#receiver").val()==""){
	       $("#s_receiver").html("*收件人不能为空");
	     }else{
	       $("#s_receiver").html("");
	     }
	  });
	  
	   //标题
       $("#title").blur(function(){
	     if($("#title").val()==""){
	       $("#s_title").html("*标题不能为空");
	     }else{
	       $("#s_title").html("");
	     }
	  });
	  
	  //内容框
       $("#content").blur(function(){
	     if($("#content").val()==""){
	       $("#s_content").html("*内容不能为空");
	     }else{
	       $("#s_content").html("");
	     }
	  });
	  
	  //验证码
       $("#code").blur(function(){
	     if($("#code").val()==""){
	       $("#s_code").html("*验证码不能为空");
	     }else{
	       $("#s_code").html("");
	     }
	  });
 });
 
    //检查用户名是否有效param["paramMap.receiver"] = $("#receiver").val();
    function checkRegister(){
        //param["paramMap.receiver"] = $("#receiver").val();
        //alert($("#receiver").val());
        window.location.href="judgeUserName.do";
		
     }
  
     //改变验证码
     function switchCode(){
		var timenow = new Date();
		$("#codeNum").attr("src","admin/imageCode.do?pageId=userlogin&d="+timenow);
	}
</script>
<script type="text/javascript">


       function addMail(){
     	param["paramMap.receiver"] = $("#receiver").val();
     	param["paramMap.title"] = $("#title").val();
     	param["paramMap.content"] = $("#content").val();
     	param["paramMap.code"] = $("#code").val();
     	param["paramMap.pageId"] = "userlogin";
     	if($("#receiver").val()==""){
	       $("#s_receiver").html("*收件人不能为空");
	       return;
	     }
	     if($("#title_s").val()==""){
	       $("#s_title").html("*标题不能为空");
	       return;
	     }
	     if($("#content").val()==""){
	       $("#s_content").html("*内容不能为空");
	       return;
	     }
	     if($("#code").val()==""){
	       $("#s_code").html("*验证码不能为空");
	       return;
	     }
	     //有错误提示的时候不提交
	     if($("#s_receiver").text()!="" || $("#s_title").text()!="" ||$("#s_content").text()!=""
	       ||$("#s_code").text()!=""){
	          return;
	       }
     	$.shovePost("AdminAddMail.do",param,function(data){
     	   if(data == 5){
     	     $("#s_code").html("验证码错误");
     	     return;
     	   }else if(data == 1){
     	     alert("邮件发送失败，请重新发送");
     	     return;
     	   }else if(data==8){
     	      alert("收件人不存在");
     	     return;
     	   }else if(data == 2){
     	      alert("站内信发送成功");
     	       window.parent.close();
     	     return;
     	   }
     	});
     }
</script>
</body>
</html>