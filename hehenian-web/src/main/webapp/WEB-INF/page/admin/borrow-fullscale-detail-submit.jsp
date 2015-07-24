<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<table>
	<tr>
		<td colspan="2" class="blue12 left">复审状态：<span class="require-field">*</span> <s:radio name="paramMap.status" list="#{4:'复审通过',6:'复审不通过'}" value="%{paramMap.status}"></s:radio> <span
			class="require-field"><s:fielderror fieldName="paramMap['status']"></s:fielderror>&nbsp;</span></td>
	</tr>
	<tr>
		<td colspan="2" class="blue12 left">风控意见：<span class="require-field">*<s:fielderror fieldName="paramMap['auditOpinion']"></s:fielderror>&nbsp;</span> <br />
		<s:textarea cssStyle="margin-left:80px;" name="paramMap.auditOpinion" value="%{paramMap.auditOpinion}" cols="30" rows="10"></s:textarea></td>
	</tr>
	<tr>
		<td colspan="2" align="left" style="padding-left: 30px;">
			<button id="btn_save" style="background-image: url('../images/admin/btn_queding.jpg'); width: 70px; border: 0; height: 26px"></button> &nbsp;&nbsp;
			<button id="btn_reback" style="background-image: url('../images/admin/btn_reback.jpg'); width: 70px; border: 0; height: 26px"></button></td>
	</tr>
</table>
<script>
	var falg = false;
	$(function() {
		//提交表单
		$('#btn_save').click(function() {
			if (falg)
				return;
			$('#btn_save').attr('style', "background-image: url('../images/admin/btn_chulz.jpg'); width: 70px; border: 0; height: 26px");
			$('#btn_save').attr('disabled', true);
			param['paramMap.id'] = $('#id').val();
			param['paramMap.status'] = $("input[name='paramMap.status']:checked").val();
			param['paramMap.auditOpinion'] = $('#paramMap_auditOpinion').val();
			$.shovePost('updateBorrowFullScale.do', param, function(data) {
				var callBack = data.msg;
				if (callBack == undefined || callBack == '') {
					falg = false;
					$('#content').html(data);
					$('#btn_save').attr('disabled', false);
				} else {
					if (callBack == 1) {
						falg = true;
						alert("操作成功!");
						window.location.href = "borrowFullScale.do";
						return false;
					}
					falg = false;
					alert(callBack);
					$('#btn_save').attr('disabled', false);
				}
			});
		});
		$('#btn_reback').click(function() {
			if (falg)
				return;
			var r = confirm("撤消操作,是否继续?");
			if (r == true) {
				param['paramMap.id'] = $('#id').val();
				$.shovePost('reBackBorrowTenderIn.do', param, function(data) {
					var callBack = data.msg;
					if (callBack == 1) {
						falg = true;
						alert("操作成功!");
						window.location.href = "borrowFullScale.do";
						return false;
					}
					falg = false;
					alert(callBack);
				});
			}
		});
	});
</script>
