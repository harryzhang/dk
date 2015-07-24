<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
   <jsp:include page="/include/head.jsp"></jsp:include>
    <link rel="stylesheet" type="text/css" href="css/css.css"></link>
    <link id="skin" rel="stylesheet" href="css/jbox/Gray/jbox.css" />
</head>
<body>

<div class="nymain" style="margin:20px auto ;width:500px;height500px; border-left: none;border: 1px #ccc solid;">
		<div class="wdzh" style="width:500px;height500px;margin: 0px auto 0 auto;border: 0px;">
				<div class="box" >
					<div class="boxmain2" style="padding: 15px;">
        				<table width="100%" border="0" cellspacing="0" cellpadding="0" style="background-color: #f0f0f0;">
						  <tr>
						    <td width="11%">发件人：</td>
						    <td width="89%"><strong>${receiver}</strong></td>
						  </tr>
						  <tr>
						    <td>收件人：</td>
						    <td><input type="text" class="inp140" value="${sender }" id="receivers" />
						    <span style="color: red; float: none;" id="s_receiver" class="formtips"></span>
						    </td>
						  </tr>
						  <tr>
						    <td>标题：</td>
						    <td><input type="text" class="inp280" value="回复：${title }" id="titles" />
						    <span style="color: red; float: none;" id="s_title" class="formtips"></span>
						    </td>
						  </tr>
						  <tr>
						    <td valign="top">内容：</td>
						    <td><textarea class="txt420" id="contents"></textarea>
						    <span style="color: red; float: none;" id="s_content" class="formtips"></span>
						    </td>
						  </tr>
						  <tr>
						    <td>验证码：</td>
						    <td><input type="text" class="inp100x" id="codess" />
						      <img src="admin/imageCode.do?pageId=userlogin" title="点击更换验证码"
								style="cursor: pointer;" id="codeNum1" width="46" height="18"
								onclick="javascript:switchCode2()" />
								<span style="color: red; float: none;" id="s_code" class="formtips"></span>
						      </td>
						  </tr>
						  <tr>
						    <td>&nbsp;</td>
						    <td style="padding-top:20px;"><a href="javascript:void(0)"  id="btnsave"  class="bcbtn">提交发送</a></td>
						  </tr>
					 </table>
		        </div>
		        <hr />
		        
        <div class="biaoge2" style="padding: 15px;">
        <table width="100%" border="0" cellspacing="0" cellpadding="0" style="background-color: #f0f0f0;">
  <tr>
    <td width="11%">发件人：</td>
    <td width="89%">${sender}</td>
  </tr>
  <tr>
    <td>收件人：</td>
    <td>${receiver}</td>
  </tr>
  <tr>
    <td>标题：</td>
    <td>${title}</td>
  </tr>
  <tr>
    <td>日期：</td>
    <td>${date }</td>
  </tr>
  <tr>
    <td valign="top">内容：</td>
    <td>${content}</td>
     
  </tr>
  <tr align="center">
  <td style="padding-top:20px;" colspan="2"><input type="button" value="关闭" id= "close" class="scbtn" /></td>
  </tr>
        </table>

        </div>
    </div>
</div>

    </div>

 <script type="text/javascript" src="script/jquery-1.7.1.min.js"></script>
 <script type="text/javascript" src="script/jquery.shove-1.0.js"></script>
 <script type="text/javascript" src="script/jbox/jquery.jBox-2.3.min.js"></script>
<script type="text/javascript" src="script/jbox/jquery.jBox-zh-CN.js"></script>
 <script>
 
 $(function(){
 	$('#close').click(function(){
 		window.parent.jBox.close();
 	});
 	
 	$('#btnsave').click(function(){
 	   	param["paramMap.receiver"] = $("#receivers").val();
     	param["paramMap.title"] = $("#titles").val();
     	param["paramMap.content"] = $("#contents").val();
     	param["paramMap.code"] = $("#codess").val();
     	param["paramMap.pageId"] = "userlogin";
     	$.shovePost("addMail.do",param,function(data){
     	   if(data == 5){
     	     alert("验证码错误!");
     	     return;
     	   }else if(data == 1){
     	     alert("邮件发送失败，请重新发送");
     	     return;
     	   }else if(data == 8){
     	     alert("你是黑名单用户不能发生站内信");
     	     return;
     	   }else{
     	   	 alert("站内信回复成功");
     	   	 window.parent.initListInfo(param);//重新加载，将未读的邮件设置为已读
     	   	 window.parent.window.jBox.close() ;
     	   }
     	});
 	});
 	

   //收件人
       $("#receiver").blur(function(){
	     if($("#receiver").val()==""){
	       $("#s_receiver").html("*收件人不能为空");
	     }else{
	        checkRegister();
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
	  //验证码
       $("#code").blur(function(){
	     if($("#code").val()==""){
	       $("#s_code").html("*验证码不能为空");
	     }else{
	       $("#s_code").html("");
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
	  
 });
	function switchCode2(){
		var timenow = new Date();
		$("#codeNum1").attr("src","admin/imageCode.do?pageId=userlogin&d="+timenow);
	}
	
	
	//检查用户名是否有效
    function checkRegister(){
        param["paramMap.receiver"] = $("#receiver").val();
		$.post("judgeUserName.do",param,function(data){
              if(data == 1 ){
                 $("#s_receiver").html("*收件人不存在或者还不是您的好友！");
              }else{
                 $("#s_receiver").html("");
              }
		});
     }
      
        
     
</script>
</body>
</html>
