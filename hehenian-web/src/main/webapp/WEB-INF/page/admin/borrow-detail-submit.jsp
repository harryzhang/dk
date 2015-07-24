<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<table>
							<tr>
								<td colspan="2" class="blue12 left">
									审核状态：<span class="require-field">*</span>
									<s:radio name="paramMap.status" list="#{2:'审核通过',1:'审核不通过'}"></s:radio>
									<span class="require-field"><s:fielderror fieldName="paramMap['status']"></s:fielderror></span>
								</td>
							</tr>
							<tr>
								<td colspan="2" class="blue12 left">
									站内信通知：<span class="require-field">*&nbsp;<s:fielderror fieldName="paramMap['msg']"></s:fielderror></span>
									<br/><s:textarea cssStyle="margin-left:80px;" name="paramMap.msg" cols="30" rows="5"></s:textarea>
								</td>
								<td align="center" class="f66">
								</td>
							</tr>
							<tr>
							    <td colspan="2" class="blue12 left">
									审核意见：<span class="require-field">*&nbsp;<s:fielderror fieldName="paramMap['auditOpinion']"></s:fielderror></span>
									<br/><s:textarea cssStyle="margin-left:80px;" name="paramMap.auditOpinion" cols="30" rows="10"></s:textarea>
								</td>
							</tr>
							<tr>
								<td colspan="2" align="left" style="padding-left: 30px;">
									<button id="btn_save"
										style="background-image: url('../images/admin/btn_queding.jpg'); width: 70px; border: 0; height: 26px"></button>
								</td>
							</tr>
						</table>
<script>
	$(function(){
	  //提交表单
   	  $('#btn_save').click(function(){
	     param['paramMap.id'] = $('#id').val();
	     param['paramMap.status'] = $("input[name='paramMap.status']:checked").val();
	     param['paramMap.msg'] = $('#paramMap_msg').val();
	     param['paramMap.auditOpinion'] = $('#paramMap_auditOpinion').val();
	     $.shovePost('updateBorrowF.do',param,function(data){
		   if(data ==1){
		       alert('操作成功!');
		       window.location.href="borrowf.do";
		   }else{
		       $('#content').html(data);
		   }
		 });
	 });
	});
</script>						