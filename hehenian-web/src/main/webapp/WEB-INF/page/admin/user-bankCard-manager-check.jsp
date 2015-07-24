<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>帮助中心-内容维护-修改</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
<style>
.blue12 {
	color: #3E4959;
	font-family: "tahoma";
	font-size: 12px;
	line-height: 30px;
}

.f66 {
	color: #3E4959;
	font-family: "tahoma";
	font-size: 12px;
	line-height: 30px;
	padding-left: 30px;
}
</style>
<script type="text/javascript">
	param = {};
	$(function() {
		$("#remark").focus(function() {
			$("#hhhhn").html("");
		});
		//提交表单
		$("#btn_save").click(function() {
			if ($("#remark").val() == '') {
				$("#hhhhn").html("审核意见不能为空");
				return;
			}

			//$(this).hide();
			param["paramMap.bankId"] = $("#tb_id").val();
			param["paramMap.status"] = $("input[name='paramMap.status']:checked").val();
			param["paramMap.remark"] = $("#remark").val();
			param["paramMap.usrCustId"] = $("#tb_usrCustId").val();
			param["paramMap.openBankId"] = $("#tb_openBankId").val();
			param["paramMap.openBranchName"] = $("#tb_modifiedBranchBankName").val();
			param["paramMap.provinceId"] = $("#tb_province").val();
			param["paramMap.cityId"] = $("#tb_city").val();
			param["paramMap.openAcctId"] = $("#tb_modifiedCardNo").val();
			$("#btn_save").attr("disabled",true);
			$.shovePost("updateUserBankInfo.do", param, function(data) {
				if (data == "操作成功") {
					alert("操作成功");
					window.location.href = "queryUserBankInit.do";
					return;
				}  
				if (data == "请求参数非法") {
					alert(data + ":请检查身份证号码和银行卡号码");
				} else {
					alert(data);
				}
				$("#btn_save").attr("disabled",false);
			});
		});
	});
</script>

</head>
<body>
	<div id="right">
		<div style="width: auto; background-color: #FFF; padding: 10px;">
			<table border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="120" height="28" class="xxk_all_a"><a href="queryUserBankInit.do">银行卡管理</a>
					</td>
					<td></td>
					<td width="120" height="28" class="xxk_all_a"><a href="queryModifyBankInit.do">银行卡变更</a> <s:hidden id="tb_usrCustId" name="paramMap.usrCustId" />
					</td>
				</tr>
			</table>
			<div style="margin: 20px; ">&nbsp;</div>
			<table width="700px;" cellspacing="1" cellpadding="3" style="border-collapse:collapse;border:none;margin-left: 300px;" border="0">
				<tr>
					<td style="width: 120px;" align="right" class="blue12">用户名：</td>
					<td align="left" class="f66"><s:textfield id="tb_username" name="paramMap.username" cssClass="in_text_2" cssStyle="width: 150px" theme="simple" readonly="true"></s:textfield></td>
				</tr>
				<tr>
					<td align="right" class="blue12">真实姓名：</td>
					<td align="left" class="f66"><s:textfield id="tb_realName" name="paramMap.realName" cssClass="in_text_2" cssStyle="width: 150px" theme="simple" readonly="true"></s:textfield></td>
				</tr>
				<tr>
					<td align="right" class="blue12">身份证：<s:hidden name="paramMap.usrCustId" id="tb_usrCustId"></s:hidden></td>
					<td align="left" class="f66"><s:textfield id="tb_idNo" name="paramMap.idNo" cssClass="in_text_2" cssStyle="width: 150px" theme="simple" readonly="true"></s:textfield></td>
				</tr>

				<tr>
					<td align="right" class="blue12">开户行：</td>
					<td align="left" class="f66">
						<!-- <s:property  value="paramMap.modifiedBankName"></s:property>--> <s:textfield id="tb_modifiedBankName" name="paramMap.bankName" cssClass="in_text_2" cssStyle="width: 150px"
							theme="simple" readonly="true"></s:textfield> <s:hidden id="tb_openBankId" name="paramMap.openBankId" />
					</td>
				</tr>
				<tr>
					<td align="right" class="blue12">开户支行：</td>
					<td align="left" class="f66">
						<!-- <s:property  value="paramMap.modifiedBranchBankName"></s:property>--> <s:textfield id="tb_modifiedBranchBankName" name="paramMap.branchBankName" cssClass="in_text_2"
							cssStyle="width: 150px" theme="simple" readonly="true"></s:textfield></td>
				</tr>
				<tr>
					<td align="right" class="blue12">开户地区：</td>
					<td align="left" class="f66">
						<!-- <s:property  value="paramMap.modifiedBranchBankName"></s:property>--> <input id="tb_provinces" name="tb_provinces" value="${paramMap.province}--${paramMap.city}" class="in_text_2"
						style="width: 150px" theme="simple" readonly="true" /> <input id="tb_province" name="tb_province" value="${paramMap.provinceId}" type="hidden" /> <input id="tb_city" name="tb_city"
						value="${paramMap.cityId}" type="hidden" />
					</td>
				</tr>
				<tr>
					<td align="right" class="blue12">银行卡号：</td>
					<td align="left" class="f66">
						<!-- <s:property  value="paramMap.modifiedCardNo"></s:property></td>--> <s:textfield id="tb_modifiedCardNo" name="paramMap.cardNo" cssClass="in_text_2" cssStyle="width: 150px"
							theme="simple" readonly="true"></s:textfield> <s:hidden id="tb_modifiedTime" name="paramMap.modifiedTime" cssClass="in_text_2" cssStyle="width: 150px" theme="simple"></s:hidden> <s:hidden
							id="tb_id" name="paramMap.bankId" cssClass="in_text_2" cssStyle="width: 150px" theme="simple"></s:hidden></td>
				</tr>
				<tr>
					<td align="right" class="blue12">审核状态：</td>
					<td align="left" class="f66"><s:radio id="radio1" name="paramMap.status" list="#{1:'审核通过',3:'审核不通过'}" value="3"></s:radio> <span class="require-field">*<s:fielderror
								fieldName="paramMap['status']"></s:fielderror> </span></td>
				</tr>
				<tr>
					<td align="right" class="blue12">审核意见：</td>
					<td align="left" class="f66"><span style="font-size:12px;margin-top: 5px;"><s:textarea id="remark" value="" cols="20" rows="3"></s:textarea> </span><span class="require-field">*<span
							id="hhhhn" style="font-size: 12px;margin-left: 5px;"></span> </span></td>
				</tr>
				<tr>
					<td height="36" align="right" class="blue12"><s:hidden name="action"></s:hidden> &nbsp;</td>
					<td>
						<button id="btn_save" style="background-image: url('../images/admin/btn_queding.jpg'); width: 70px; border: 0; height: 26px"></button> &nbsp;</td>
				</tr>
			</table>
			<br />
		</div>
	</div>
</body>
</html>
