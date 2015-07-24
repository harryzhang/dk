<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<table>
							<tr>
							    <td colspan="2" class="blue12 left">
									风控意见：<span class="require-field">*&nbsp;<s:fielderror fieldName="paramMap['auditOpinion']"></s:fielderror></span>
									<br/><s:textarea cssStyle="margin-left:80px;" name="paramMap.auditOpinion" cols="30" rows="10"></s:textarea>
								</td>
							</tr>
							<tr>
								<td colspan="2" align="left" style="padding-left: 30px;">
									<button id="btn_update"
										style="background-image: url('../images/admin/btn_update.jpg'); width: 70px; border: 0; height: 26px"></button>
								   &nbsp;&nbsp;
								    <button id="btn_reback"
										style="background-image: url('../images/admin/btn_reback.jpg'); width: 70px; border: 0; height: 26px"></button>
								</td>
							</tr>
						</table>
<script>
var falg = false;
	$(function(){
	  //提交表单
   	  $('#btn_update').click(function(){
   	   	  if(falg) return ;
   		$('#btn_update').attr('style',"background-image: url('../images/admin/btn_chulz.jpg'); width: 70px; border: 0; height: 26px");
     	 $('#btn_update').attr('disabled',true);
	     param['paramMap.id'] = $('#id').val();
	     param['paramMap.reciver'] = $('#uid').val();
	     param['paramMap.auditOpinion'] = $('#paramMap_auditOpinion').val();
	     $.shovePost('updateBorrowTenderIn.do',param,function(data){
		   var callBack = data.msg;
		   if(callBack == undefined || callBack == ''){
			   falg = false;
		       $('#content').html(data);
		       $('#btn_update').attr('disabled',false);
		   }else{
		       if(callBack == 1){
		          alert("操作成功!");
		          falg = true;
		          window.location.href="borrowTenderIn.do";
		          return false;
		       }
		       alert(callBack);
		       falg = false;
		       $('#btn_update').attr('disabled',false);
		   }
		 });
	   });
	   $('#btn_reback').click(function(){
		if(falg) return ;
	     var r=confirm("撤消操作,是否继续?");
	     if(r == true){
	    	 $('#btn_reback').attr('style',"background-image: url('../images/admin/btn_chulz.jpg'); width: 70px; border: 0; height: 26px");
		     $('#btn_reback').attr('disabled',true);
	        param['paramMap.id'] = $('#id').val();
	        $.shovePost('reBackBorrowTenderIn.do',param,function(data){
		         var callBack = data.msg;
		         if(callBack == 1){
		            alert("操作成功!");
		            falg = true;
		            window.location.href="borrowTenderIn.do";
		            return false;
		         }
		         alert(callBack);
		         falg = false;
		         $('#btn_reback').attr('disabled',false);
		    });
	     }
	   });
	});
</script>						