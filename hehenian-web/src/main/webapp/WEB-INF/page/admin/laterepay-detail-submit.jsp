<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<table class="gvItem" style="width:100%;">
   <tr>
	   <td colspan="2" class="blue12 left">
			沟通内容：<span class="require-field">*&nbsp;<s:fielderror fieldName="paramMap['content']"></s:fielderror></span>
		    <br/><s:textarea cssStyle="margin-left:80px;" name="paramMap.content" cols="30" rows="10"></s:textarea>
	   </td>
   </tr>
   <tr>
	    <td colspan="2" align="left" style="padding-left: 30px;">
		  <button id="btn_save"
		    style="background-image: url('../images/admin/btn_queding.jpg'); width: 70px; border: 0; height: 26px"></button>
		</td>
	</tr>
</table>
<script type="text/javascript">
			$(function(){
				$('#btn_save').click(function(){
				   var id = $('#id').val();
				   param['paramMap.id'] = id;
				   param['paramMap.content'] = $('#paramMap_content').val();
				   $.shovePost('addRepayMentNotice.do',param,function(data){
		              var callBack = data.msg;
		              if(callBack == undefined || callBack == ''){
		                 $('#contentDiv').html(data);
		              }else{
		                if(callBack == 1){
		                    alert("操作成功!");
		                    window.location.href="repaymentNoticeList.do?id="+id;
		                    return false;
		                }
		                alert(callBack);
		              }
		            });
				});
			});
</script>