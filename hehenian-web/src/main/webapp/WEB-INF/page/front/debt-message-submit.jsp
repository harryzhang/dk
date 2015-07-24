<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<h4>咨询或评论：(字数在1-120之间)</h4><s:actionmessage/><span class="fred"><s:fielderror fieldName="paramMap['msg']"></s:fielderror></span>
<textarea id="msgContent" name="paramMap.msg" class="txt918">${paramMap.msg}</textarea>
<span class="fred"><s:fielderror fieldName="paramMap['code']"></s:fielderror></span>
<p>验证码：
      <input type="text" class="inp100x" name="paramMap.code" id="code"/>
		 <img src="" title="点击更换验证码" style="cursor: pointer;"
		 	  id="codeNum" width="46" height="18" onclick="javascript:switchCode()"/>
       <a id="savetbn" href="javascript:void(0);" class="chaxun">提交</a></p>
<script type="text/javascript">
$(function(){
     $('#savetbn').click(function(){
	     param['paramMap.id']=$('#id').val();
	     param['paramMap.code']=$('#code').val();
	     param['paramMap.msg']=$('#msgContent').val();
         $.shovePost('addDebtMSG.do',param,function(data){
		     if(data.msg == 1){
		       initListInfo(param);
		     }else{
		       $('#msgadd').html(data);
		     }
         });
     });
     switchCode();
});
function switchCode(){
	    var timenow = new Date();
	    $('#codeNum').attr('src','admin/imageCode.do?pageId=msg&d='+timenow);
};	
</script>