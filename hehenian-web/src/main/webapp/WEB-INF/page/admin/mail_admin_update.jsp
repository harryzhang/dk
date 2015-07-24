<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
<jsp:include page="/include/head.jsp"></jsp:include>

</head>
<body>
	<div style="width: auto; background-color: #FFF; padding: 10px;">
						<table width="100%" border="0" cellspacing="1" cellpadding="3">
   
   	<table border="0" cellspacing="1" cellpadding="3">
     <tr>
        <td height="25" width="100px"  align="right" class="blue12">发件人：</td>
        <td align="left" width="300px" class="f66"><span >${paramMap.sender}</span>
        <input id="idd" type="hidden" value="${paramMap.id}"/>
        </td>
     </tr>
     <tr>
       <td height="25"  align="right" class="blue12">收件人：</td>
       <td align="left" class="f66"><span>${paramMap.reciver}<span></td>
     </tr>
     <tr>
        <td height="25"  align="right" class="blue12">标题：</td>
        <td align="left" class="f66">
          <span>${paramMap.mailTitle}</span>
         <span style="color: red; float: none;" id="s_title" class="formtips"></span>
       </td>
     </tr>
     <tr>
       <td height="25"  align="right" class="blue12">内容：</td>
       <td align="left" class="f66">
       <p>${paramMap.mailContent}</p>
         
          <span style="color: red; float: none;" id="s_content" class="formtips"></span>
       </td>
     </tr>
  
    <tr>
     <td>&nbsp;</td>
     <td style="padding-top:20px;"><input type="button" onclick="updateMail()" style="cursor: pointer;" value="确定"/></td>
    </tr>
  
   </table>

<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="../css/popom.js"></script>
<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
<script type="text/javascript">
   function updateMail(){
     
            window.parent.close();
            
       
     
      
   }
</script>
</body>
</html>