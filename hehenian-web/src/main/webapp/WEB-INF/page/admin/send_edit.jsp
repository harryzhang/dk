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
    <input id="idd" type="hidden" value="${paramMap.id}"/>
   <table width="100%" border="0" cellspacing="0" cellpadding="0">
   
   
     
     <tr>
       <td valign="top" height="100">发送内容：</td>
       <td>
          <textarea class="txt420" cols="35" rows="7" id="content">${session.content}</textarea>
          <span style="color: red; float: none;" id="s_content" class="formtips"></span>
       </td>
     </tr>
  
    <tr>
     <td>&nbsp;</td>
     <td style="padding-top:20px;"><input type="button" onclick="updateMail()" value="确定发送"/></td>
    </tr>
  
   </table>

    </div>
  </div>
</div>
<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="../css/popom.js"></script>
<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
<script type="text/javascript">
   function updateMail(){
     
      
     
      param["paramMap.content"]=$("#content").val();
   
      $.post("addsendSMSContent.do",param,function(data){
          if(data==1){
            alert("修改成功");          
            
            window.parent.close();
            
          }else{
             alert("修改失败");
          }
      });
      
   }
</script>
</body>
</html>