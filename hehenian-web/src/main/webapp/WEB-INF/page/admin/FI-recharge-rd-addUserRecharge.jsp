<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
<script type="text/javascript">
	$(function() {
		var adminId = '${sessionScope.admin.id }';
		$("#adminId").attr("value", adminId);
		$("#pageId").attr("value", "recharge");
		$("#adminId").attr("name", "paramMap.adminId");
		$("#pageId").attr("name", "paramMap.pageId");
		$("#tb_code").attr("value", "");

		$("input").blur(function() {
			if ($(this).attr("id") == "tb_money") {
				if ($(this).val() == "") {
					$(this).next().html("充值金额不能为空");
					return;
				} else {
					$(this).next().html("");
				}
				if (isNaN($(this).val())) {
					$(this).next().html("充值金额为数字");
					return;
				} else {
					$(this).next().html("");
				}
				if ($(this).val() <= 0) {
					$(this).next().html("充值金额为正数");
					return;
				} else {
					$(this).next().html("");
				}
			}
			if ($(this).attr("id") == "tb_code") {
				if ($(this).val() == "") {
					$("#code_tip").html("请输入验证码");
				} else {
					$("#code_tip").html("");
				}
			}
		});

		$("#tb_remark").blur(function() {
			if ($("#tb_remark").val() == '' || $("#tb_remark").val() == undefined) {
				$("#remark_tip2").html("备注不能为空");
			} else {
				$("#remark_tip2").html("");
			}
		});
	});

	//提交表单
	function tb_submit() {
		if ($("#tb_money").val() == "") {
			alert("充值金额不能为空");
			return;
		}
		if (isNaN($("#tb_money").val())) {
			alert("充值金额为数字");
			return;
		}
		if ($("#tb_money").val() <= 0) {
			alert("充值金额为正数");
			return;
		}
		if ($("#tb_code").val() == "") {
			alert("请输入验证码");
			return;
		}
		if ($("#tb_remark").val() == "") {
			alert("备注不能为空");
			return;
		}
		$("#gogo").attr("disabled", true);
		var param = {};
		param["paramMap.dealMoney"] = $("#tb_money").val();
		param["paramMap.code"] = $("#tb_code").val();
		param["paramMap.pageId"] = $("#pageId").val();
		param["paramMap.userId"] = $("#tb_userId").val();
		param["paramMap.remark"] = $("#tb_remark").val();
		param["paramMap.username"] = '${request.map.username}';
		param["paramMap.usrCustId"] = '${request.map.usrCustId}';
		param["paramMap.openAcctId"] = '${request.map.cardNo}';
		$.shovePost("addBackUserRecharge.do", param, function(data) {
			alert(data);
			if (data == "商户代扣充值成功") {
				window.parent.location.reload();
				return;
			}
			$("#gogo").attr("disabled", false);
			switchCode();
		});
	}

	function switchCode() {
		var timenow = new Date();
		$("#codeNum").attr("src", "admin/imageCode.do?pageId=recharge&d=" + timenow);
	}
</script>
</head>
<body>
	<div style="padding: 15px 0px 0px 0px;width: 92%;margin: 0 auto;">
		<div style="padding-right: 10px; padding-left: 10px; padding-bottom: 10px; width: 100%; padding-top: 10px; background-color: #fff;">
			<table width="92%" border="0" cellspacing="1" cellpadding="3">
				<tr>
					<td style="width: 120px; height: 35px;" align="right" class="blue12">用户名：</td>
					<td align="left" class="f66"><input style="width: 150px;height:22px;" type="text" value="${request.map.username }" id="tb_userName" readonly="readonly"> <input
						style="display: none;" type="hidden" value="${request.map.userId }" id="tb_userId">
				</tr>
				<tr>
					<td style="width: 120px; height: 35px;" align="right" class="blue12">充值金额：</td>
					<td align="left" class="f66"><input id="tb_money" name="paramMap.dealMoney" style="width: 150px;height:22px;" /> <span id="money_tip" class="require-field"
						style="font-size: 11px;">*</span>
					</td>
				</tr>
				<tr>
					<td style="width: 120px; height: 35px;" align="right" class="blue12">备注：</td>
					<td align="left" class="f66"><s:textarea id="tb_remark" cssStyle="font-size:12px;width: 180px;height:60px;" name="paramMap.remark" ></s:textarea> 
					<span id="remark_tip2" class="require-field" style="font-size: 11px;">*</span></td>
				</tr>
				<tr>
					<td style="width: 120px; height: 35px;" align="right" class="blue12">验证码：</td>
					<td colspan="2" class="f66"><input id="tb_code" style="width: 150px;height:22px;" /> <img src="admin/imageCode.do?pageId=recharge" title="点击更换验证码" style="cursor: pointer;"
						id="codeNum" width="46" height="18" onclick="javascript:switchCode()" /> <span id="code_tip" class="require-field" style="font-size: 11px;">* </span>
					</td>
					<s:hidden id="pageId" name="paramMap.pageId"></s:hidden>
					<input id="pageId" name="paramMap.pageId" class="in_text_2" style="width: 150px;height:20px;display:none" type="hidden" />
				</tr>
				<tr>
					<td colspan="2" align="center"><input type="button" onclick="tb_submit()" value="提交" id="gogo" />
					</td>
				</tr>
			</table>
		</div>
	</div>
</body>
</html>
