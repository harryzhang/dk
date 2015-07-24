<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
	<meta http-equiv="pragma" content="no-cache"/>
	<meta http-equiv="cache-control" content="no-cache"/>
	<meta http-equiv="expires" content="0"/>    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3"/>
	<meta http-equiv="description" content="This is my page"/>
	<link href="css/css.css" rel="stylesheet" type="text/css" />
    <link id="skin" rel="stylesheet" href="css/jbox/Gray/jbox.css" />
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
      <form action="register.do" method="post">
      <s:hidden name="paramMap.param" id="param"  />
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
    <th align="right"><span class="fred">*&nbsp;</span>手机号：</th>
    <td><input type="text" class="inp202"  name="paramMap.cellphone" id="cellphone"/>
    </td>
    <td width="100px"> <span style="color: red" id="s_cellphone" class="formtips"></span>
    </td>
  </tr>
  <tr>
    <th align="right"><span class="fred">*&nbsp;</span>验证码：</th>
    
        <td><input type="text" class="inp100"  name="paramMap.code" id="code"/>
    <img src="admin/imageCode.do?pageId=cellphone" title="点击更换验证码" style="cursor: pointer;"
									id="codeNum" width="46" height="18" onclick="javascript:switchCode()" />
									 <a href="javascript:void()" onclick="switchCode()" style="color: blue;">换一换?</a>
									</td>
     <td width="80px"> <span style="color: red" id="s_code" class="formtips"></span>
    </td>
  </tr>
  	<s:if test="#session.DEMO==1">
  	<tr>
  	<td>&nbsp;</td>
	  	<td ><span style="color:red;font-size: 12px">* 演示站点不发送短信</span></td>
		</tr>
	</s:if>
  <tr>
    <th align="right">&nbsp;</th>
    <td class="tyzc" ><input type="checkbox" id="agre" checked="checked"/>我已经阅读并同意<a style="cursor: pointer;" onclick="fff()">使用条款</a>和<a style="cursor: pointer;" onclick="ffftip()">隐私条款</a></td>
  </tr>
  <tr>
    <th align="right">&nbsp;</th>
   <!--  <td><a href="yxjihuo.html" class="zcbtn">免费注册</a></td> -->
   <td><input type="button" id="btn_cellreg" value="提交" class="zcbtn" style="cursor: pointer;"/></td>
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
    <a href="/account/login-index.do" class="dlbtn">马上登录</a>
    </div>
    </div>
    <div class="renpic" style="top:50px;">
    
    </div>
  </div>
  </div>
</div>
<!-- 引用底部公共部分 -->     
<jsp:include page="/include/footer.jsp"></jsp:include>
  <script type="text/javascript" src="css/popom.js"></script>
  <script src="script/jquery-1.7.1.min.js" type="text/javascript"></script>
  <script type="text/javascript" src="script/jbox/jquery.jBox-2.3.min.js"></script>
<script type="text/javascript" src="script/jbox/jquery.jBox-zh-CN.js"></script>
  <script>
  //弹出隐私条款和使用条款
 function fff(){
		    	  jQuery.jBox.open("post:querytips.do", "使用条款", 600,400,{ buttons: { } });
		    }
		    function ffff(){
		      ClosePop();
		    }
</script>
<script>
 function ffftip(){
	 jQuery.jBox.open("post:querytip.do", "隐私条款", 600,400,{ buttons: { } });
		    }
</script>
  <script>
//回车登录
document.onkeydown=function(e){
  if(!e)e=window.event;
  if((e.keyCode||e.which)==13){
   cellreg();
  }
}
</script>
  <script>
  $(function(){
 $("form :input").blur(function(){ 
 //手机号码
 if($(this).attr("id")=="cellphone"){
   if((!/^1[3,5,8]\d{9}$/.test($("#cellphone").val()))){
         $("#s_cellphone").attr("class", "formtips onError");
		 $("#s_cellphone").html("手机号码格式错误"); 
   }else{
         $("#s_cellphone").attr("class", "formtips");
		 $("#s_cellphone").html(""); 
   }
 }
 //验证码
 if($(this).attr("id")=="code"){
   if($(this).val()==""){
     	 $("#s_code").attr("class", "formtips onError");
		 $("#s_code").html("验证码不正确"); 
   }else{
        $("#s_code").attr("class", "formtips");
		 $("#s_code").html(""); 
   }
 }
 });  
  });
  </script>
  <script>
  		//初始化验证码
		function switchCode(){
			var timenow = new Date();
			$("#codeNum").attr("src","admin/imageCode.do?pageId=cellphone&d="+timenow);
		}
  </script>
  <script>
   var falg = true;
  function cellreg(){
   var errornum=$("form .onError").length;
              if(falg){
              falg = false;
               if($("#cellphone").val()==""){
                 $("#s_cellphone").html("手机号码格式错误");
                 falg = true;
                 switchCode();
                 return false;
               }
               if($("#code").val()==""){
                 $("#s_code").html("验证码不正确");
                 falg = true;
                 switchCode();
                 return false;
               }
               
               if(!$("#agre").attr("checked")){
		            alert("请勾选我已阅读并同意《使用条款》和《隐私条款》");
		            falg = true;
		           return false;
		           }
               
               var param = {};
               param["paramMap.cellphone"]= $("#cellphone").val();
               param["paramMap.pageId"] = "cellphone";
               param["paramMap.code"]= $("#code").val();
               $.post("cellPhoneregsinit.do",param,function(da){
               if(da==1){
                 var cellph = $("#cellphone").val();
                 if('${DEMO}'!=1){
	                 /**发送手机验证码**/
	                 $.post("sendSMS.do","phone="+cellph,function(data){
	                   if(data=='1'){
	                      window.location.href='cellPhonereginit.do?cp='+cellph;
	                   }else{
	                     alert("短信发送失败!");
	                   }
	                 });
                   }else{
                	   window.location.href='cellPhonereginit.do?cp='+cellph;
                   }
               }else if(da==2){
               $("#s_code").html("验证码不正确");
                 falg = true;
                 switchCode();
               }else if(da==3){
               $("#s_cellphone").html("手机号码格式错误");
                 falg = true;
                 switchCode();
               }else if(da==5){
            	   $("#s_cellphone").html("手机号已存在!");
                   falg = true;
                   switchCode();
               }
               });
               
              }
  
  }
  
  </script>
  <script>
  //提交
  $(function(){
     $("#btn_cellreg").click(function(){
     cellreg();
     });
  });
  </script>
</body>
</html>
