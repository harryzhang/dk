<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
<script language="javascript" type="text/javascript" src="../My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
			$(function(){
			  $("#tb_poundage").attr("value",${poundage});
			  $("#tb_realMoney").attr("value",${sum});
			  
			});
			function changeTxtStatus(){
			   if($("#manual").attr("checked")){//更改文本框的状态
   				 $("#tb_realMoney").removeAttr("readonly"); 
   				 $("#tb_poundage").removeAttr("readonly");  
   				 $("#tb_poundage").focus();
   			   }else{
   			     $("#tb_realMoney").attr("readonly","readonly"); 
   			     $("#tb_poundage").attr("readonly","readonly"); 
   			     $("#tb_realMoney").attr('value',${realMoney});
   			     $("#tb_poundage").attr('value',${poundage});
   			   }
			}
			
			function check(){//审核  
			 
			 if(isNaN($("#tb_realMoney").val()) || isNaN($("#tb_poundage").val())){
			   alert("请填写正确的数字");
			   return;
			 }
			 	
			  param['paramMap.sum'] = ${sum};
			  param['paramMap.poundage'] = $("#tb_poundage").val();
			  param['paramMap.money'] = $("#tb_realMoney").val();	 
			  param['paramMap.remark'] = $("#remark").val();
			  param['paramMap.wid'] = $("#wid").val();
			  param['paramMap.adminId'] = '${sessionScope.admin.id }';
			  param['paramMap.status'] = $("input[name='status']:checked").val();
			  param['paramMap.userId'] = $("#userId").val();
			  param['paramMap.check'] = 'true';
			  param['paramMap.trxId'] = '${request.trxId}';
			  param['paramMap.oldStatus'] = '1';//审核状态
			  $("#btnSave").attr("disabled", 'disabled');
			  $.shovePost("updateWithdrawCheck.do",param,function(data){
				  //alert(data);
					window.parent.location.reload();
			  });
			}
						
		</script>
<div style="padding: 10px;width:95%;">
	<div>
		<div style="padding: 10px; width: 100%;border-color: red">
			<table cellspacing="0" cellpadding="0" border="1" width="98%" style="color: #333333;border-collapse: collapse;border: none;">
				<tbody>
					<tr class="gvItem">
						<td class="f66" align="right" width="20%" height="28px">用户名： <s:hidden id="wid" name="#request.wid" /> <s:hidden id="userId" name="#request.userId" /></td>
						<td class="f66" width="30%" height="28px">${username }</td>
						<td class="f66" width="20%" align="right" height="28px">状态：</td>
						<td class="f66" width="30%" height="28px;"><s:if test="#request.status==1">初审中</s:if> <s:elseif
								test="#request.status==2">成功</s:elseif> <s:elseif
								test="#request.status==3">已取消</s:elseif> <s:elseif
								test="#request.status==4">待转账</s:elseif> <s:elseif
								test="#request.status==5">失败</s:elseif>
								<s:else>初始</s:else></td>
					</tr>
					<tr class="gvItem">
						<td class="f66" align="right" height="28px">2个月内提现总额：</td>
						<td class="f66" height="28px">${w_total }</td>
						<td class="f66" align="right" height="28px">2个月内回款总额：</td>
						<td class="f66" height="28px">${retSum }</td>
					</tr>
					<tr class="gvItem">
						<td class="f66" align="right" height="28px">充值成功总额：</td>
						<td class="f66" height="28px">${r_total }</td>
						<td class="f66" align="right" height="28px">投标成功总额：</td>
						<td class="f66" height="28px">${real_Amount }</td>
					</tr>
					<tr class="gvItem">
						<td class="f66" align="right" height="28px">可用余额：</td>
						<td class="f66" height="28px">${usableSum }</td>
						<td class="f66" align="right" height="28px">开户名：</td>
						<td class="f66" height="28px">${realName }</td>
					</tr>
					<tr class="gvItem">
						<td class="f66" align="right" height="28px">提现支行：</td>
						<td class="f66" height="28px">${branchBankName }</td>
						<td class="f66" align="right" height="28px">提现账号：</td>
						<td class="f66" height="28px">${cardNo }</td>
					</tr>
					<tr class="gvItem">
						<td class="f66" align="right" height="28px">提现总额：</td>
						<td class="f66" height="28px">${sum }</td>
						<td class="f66" align="right" height="28px">到账金额：</td>
						<td class="f66" height="28px">${sum }</td>
					</tr>
					<tr class="gvItem">
						<td class="f66" align="right" height="28px">手续费：</td>
						<td class="f66" height="28px">${poundage }</td>
						<td class="f66" align="right" height="28px">添加时间/IP：</td>
						<td class="f66" height="28px">${applyTime }/${ipAddress}</td>
					</tr>
				</tbody>
			</table>
		</div>
		<div style="padding-right: 10px; padding-left: 10px; padding-bottom: 10px; width: 98%; padding-top: 10px; background-color: #fff;">
			<a style="font-size: 14px;">审核</a>
			<table style="margin-bottom: 8px;" cellspacing="0" cellpadding="0" width="100%" border="0">
				<tr>
					<td class="f66" align="left" width="50%" height="28px">状态：&nbsp;&nbsp; <!-- <s:radio id="radio1" name="status" list="#{1:'等待审核',4:'审核通过',5:'审核不通过'}" value="1"></s:radio> --> <s:radio
							id="radio1" name="status" list="#{4:'审核通过',5:'审核不通过'}" value="5"></s:radio>
					</td>
				</tr>
				<tr>
					<td class="f66" align="left" width="80%" height="28px">到账金额： <input style="width:100px;" id="tb_realMoney" readonly="readonly" /> 手续费： <input style="width:100px;" id="tb_poundage"
						readonly="readonly" /> &nbsp;&nbsp;
				</tr>
				<tr>
					<td class="f66" align="left" width="50%" height="28px">备注：</td>
				</tr>
				<tr>
					<td class="f66" align="left" width="70%" height="28px"><s:textarea id="remark" cssStyle="margin-left:80px;" name="paramMap.remark" value="" cols="50" rows="2"></s:textarea>
					</td>
				</tr>
			</table>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<button id="btnSave" style="background-image: url('../images/admin/btn_queding.jpg');width: 70px;border: 0;height: 26px" onclick="check();"></button>
		</div>
	</div>
</div>
