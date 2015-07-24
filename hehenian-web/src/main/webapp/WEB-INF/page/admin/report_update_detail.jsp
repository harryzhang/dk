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
   
     <tr><td>&nbsp;</td><td>&nbsp;</td></tr>
     <tr>
        <td width="14%" height="30">举报人：</td>
        <td height="30"><span >${userMap.username}</span></td>
     </tr>
     <tr>
       <td height="30">被举报人：</td>
       <td height="30"><span>${map.username}<span></td>
     </tr>
     <tr>
        <td height="30">举报类型：</td>
        <td height="30">
          <s:if test="#paramMap.reportType==1">
           违约
          </s:if>
          <s:else>
            其他
          </s:else>
       </td>
     </tr>
    <tr>
       <td height="30">内容：</td>
       <td ><span>${paramMap.reportContent}<span></td>
     </tr>
     <tr>
       <td valign="top" height="100">备注：</td>
       <td>
          <textarea class="txt420" cols="35" rows="7" id="content">${paramMap.remark}</textarea>
          <span style="color: red; float: none;" id="s_content" class="formtips"></span>
       </td>
     </tr>
     <tr>
       <td height="30">处理：</td>
       <td >
        <input type="radio" id="status1" name="status" value="2"/>&nbsp;举报成功
        <input type="radio" id="status2" name="status" value="3"/>&nbsp;举报失败  &nbsp; &nbsp;
         <span style="color: red; float: none;" id="s_status" class="formtips"></span>
       </td>
     </tr>
  
    <tr>
     <td>&nbsp;</td>
     <td style="padding-top:20px;"><input type="button" onclick="updateMail()" value="确定"/></td>
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
      if(($("input[name='status']:checked").val())==undefined||($("input[name='status']:checked").val())==""){
	    
	       $("#s_status").html("请填写处理状态");
	      return false;
	   }
	   if($("#content").val()==""){
	       $("#s_content").html("请填写备注");
	       return false;
	   }
	   //add by liuwei
	     param["paramMap.user"]='${paramMap.user}'; //举报人
	   //.................
      param["paramMap.status"]=$("input[name='status']:checked").val();
      
      param["paramMap.remark"]=$("#content").val();
       param["paramMap.id"]=$("#idd").val();
      $.post("updateReport.do",param,function(data){
          if(data==1){
            alert("处理成功");          
            
            window.parent.close();
            
          }else{
             alert("处理失败");
          }
      });
      
   }
</script>
</body>
</html>