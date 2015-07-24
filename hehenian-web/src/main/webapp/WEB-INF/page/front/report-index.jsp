<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <jsp:include page="/include/head.jsp"></jsp:include>
</head>
<body>
<input type="hidden" id="id" value="${id}"/><input type="hidden" id="userName" value="${userName}"/>
<div class="tck">
<div class="tishi">
网站真诚提醒您：请客观地反映您所遇到的真实情况,<br/>
以共同维护一个诚信和公平的借贷环境。</div>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tbody><tr>
    <th colspan="2" align="left">举报的用户：<span id="user"></span></th>
  </tr>
  <tr>
    <th >标题：</th>
    <td><input name="paramMap.title" id="titles" type="text" class="inp188" maxlength="200" value="${paramMap.title}"/><br/>
    <span class="fred"><s:fielderror fieldName="paramMap['title']"></s:fielderror></span>
    </td>
  </tr>
  <tr>
    <th valign="top" style="line-height:24px;">内容：</th>
    <td style="padding-top:6px;"><label for="textarea"></label>
      <textarea name="paramMap.content" cols="45" rows="5" class="txt380" id="textarea">${paramMap.content}</textarea><br/>
      <span class="fred"><s:fielderror fieldName="paramMap['content']"></s:fielderror></span>
      </td>
  </tr>
  <tr>
    <th>验证码：</th>
    <td><input name="input" type="text" name="paramMap.code" id="cod" class="inp90" value="${paramMap.code}"/>
       <img src="admin/imageCode.do?pageId=code" title="点击更换验证码" style="cursor: pointer;"
		 	  id="codeNum2" width="46" height="18" onclick="javascript:switchCode2()"/><br/>
	   <span class="fred"><s:fielderror fieldName="paramMap['code']"></s:fielderror></span>	 	  
    </td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td><a id="savetbn2" href="javascript:void(0);" class="scbtn">提交发送</a></td>
  </tr>
</tbody></table>
</div>
<script type="text/javascript" src="script/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="script/jquery.shove-1.0.js"></script>
<script type="text/javascript" src="script/jbox/jquery.jBox-2.3.min.js"></script>
<script type="text/javascript" src="script/jbox/jquery.jBox-zh-CN.js"></script>
<script type="text/javascript">
$(function(){
     $('#savetbn2').click(function(){
        	
         param['paramMap.id']=$('#id').val();
	     param['paramMap.title']=$('#titles').val();
	     param['paramMap.content']=$('#textarea').val();
	     param['paramMap.code']=$('#cod').val();
         $.shovePost('reportAdd.do',param,function(data){
		     if(data.msg == 1){
		       alert('举报成功');
		       window.parent.window.jBox.close() ;
		     }else{
		       $('.tck').html(data);
		     }
         });
     });
     switchCode2();
});
function switchCode2(){
        $('#user').text($('#userName').val());
	    var timenow = new Date();
	    $('#codeNum2').attr('src','admin/imageCode.do?pageId=code&d='+timenow);
};	
</script>
</body>
</html>