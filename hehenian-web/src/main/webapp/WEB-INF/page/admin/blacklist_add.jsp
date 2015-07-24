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
        <td width="30%" height="60">添加黑名单用户：</td>
        <td height="30">
         <input type="text" id="username" />
          <span style="color: red; float: none;" id="s_content" class="formtips"></span>
        </td>
     </tr>
    
     <tr>
     <td>&nbsp;</td>
     <td style="padding-top:20px;"><input type="button" onclick="addBlacklist()" value="确定添加"/></td>
    </tr>
   </table>

    </div>
  </div>
</div>
<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="../css/popom.js"></script>
<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
<script type="text/javascript">

   $(function(){
      //内容框
       $("#username").blur(function(){
	     if($("#username").val()==""){
	       $("#s_content").html("用户名不能为空");
	     }else{
	       $("#s_content").html("");
	     }
	  });
   });
   
   function addBlacklist(){
     param["paramMap.username"]=$("#username").val();
     if($("#username").val()==""){
	       $("#s_content").html("*用户名不能为空");
	       return;
	 }
	 $.post("addBlackList.do",param,function(data){
	     if(data==1){
	        alert("用户不存在");
	        return;
	     }else{
	        alert("黑名单用户添加成功");
	        window.parent.close();
	     }
	 });
   }
</script>
</body>
</html>