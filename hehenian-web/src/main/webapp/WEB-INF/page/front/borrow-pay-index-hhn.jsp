<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<link href="css/css.css" rel="stylesheet" type="text/css" />
<jsp:include page="/include/head.jsp"></jsp:include>
</head>
<body>
	<input type="hidden" id="payId" value="${payMap.id}" />
	<input type="hidden" id="borrowId" value="${payMap.borrowId}" />
	<div class="nymain" style="width:90%;margin-top:-50px;">
		<div class="wdzh" style="border:none;">
			<div class="r_main" style="border:none;">
				<div class="box" style="border:none;">
					<div class="boxmain2" style="border:none;">
						<div class="biaoge2" style="border:none;">
							<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top: 20px;margin-left: 50px;">
								<tr>
									<td width="21%" align="right">账户余额：&nbsp;&nbsp;&nbsp;&nbsp;</td>
									<td width="79%"><strong>${payMap.totalSum}元 </strong>
									</td>
								</tr>
								<tr>
									<td align="right">可用余额：&nbsp;&nbsp;&nbsp;&nbsp;</td>
									<td><strong>${payMap.usableSum }元 </strong>
									</td>
								</tr>
								<tr>
									<td align="right">还款日期：&nbsp;&nbsp;&nbsp;&nbsp;</td>
									<td><strong> ${payMap.repayDate } </strong> <input id="repayDate" type="hidden" value="${payMap.repayDate }" />
									</td>
								</tr>
								<tr>
									<td align="right">待还本息：&nbsp;&nbsp;&nbsp;&nbsp;</td>
									<td><strong>${payMap.forPI }元</strong>
									</td>
								</tr>
								<tr>
									<td align="right">逾期本息：&nbsp;&nbsp;&nbsp;&nbsp;</td>
									<td><strong>0.00元</strong>
									</td>
								</tr>
								<tr>
									<td align="right">需还总额：&nbsp;&nbsp;&nbsp;&nbsp;</td>
									<td><strong>${payMap.needSum }元</strong> <input id="needSum" type="hidden" value="${payMap.needSum }" />
									</td>
								</tr>
								<tr>
									<td align="right">交易密码：&nbsp;&nbsp;&nbsp;&nbsp;</td>
									<td><input type="password" class="inp140" id="pwd" maxlength="20" /> <span style="color: red; float: none;" id="pwd_tip" class="formtips"></span></td>
								</tr>
								<tr>
									<td align="right">验证码：&nbsp;&nbsp;&nbsp;&nbsp;</td>
									<td><input type="text" class="inp100x" name="paramMap.code" id="code" /> <img src="admin/imageCode.do?pageId=vip" title="点击更换验证码" style="cursor: pointer;" id="codeNum"
										width="46" height="18" onclick="javascript:switchCode()" />
									</td>
								</tr>
								<tr>
									<td align="right">&nbsp;</td>
									<td style="padding-top:20px;"><input style="cursor:pointer;" id="btnsave" value="确认还款" class="bcbtn" />
									</td>
								</tr>
							</table>

						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<script type="text/javascript" src="script/jquery-1.7.1.min.js"></script>
	<script type="text/javascript" src="script/jquery.shove-1.0.js"></script>
	<script type="text/javascript" src="script/jbox/jquery.jBox-2.3.min.js"></script>
	<script type="text/javascript" src="script/jbox/jquery.jBox-zh-CN.js"></script>
	<script>
		$(function() {
			$('#btnsave').click(function() {
				param["paramMap.id"] = $("#payId").val();
				param["paramMap.code"] = $("#code").val();
				param["paramMap.transAmt"] = "${payMap.transAmt}";
				param["paramMap.needSum"] = $("#needSum").val();
				param["paramMap.repayFee"] = "${payMap.repayFee}";
				param["paramMap.flag"] = "${request.flag}";
				param["paramMap.pwd"] = $("#pwd").val();
				param["paramMap.borrowId"] = $("#borrowId").val();
				param["paramMap.repayDate"] = $("#repayDate").val();
				$("#btnsave").attr("disabled", true);
				$("#btnsave").text("受理中...");
				$.shovePost("submitPay.do", param, function(data) {
					var callBack = data.msg;
					if (callBack == "ok") {
						alert("还款成功!");
						parent.location.reload();
						return false;
					} else if (callBack == "请求参数非法") {
						alert("请求参数非法,请确认您已经绑定了银行卡");
						$("#btnsave").attr("disabled", false);
						return false;
					}
					alert(callBack);
					switchCode();
					$("#btnsave").attr("disabled", false);
					$("#btnsave").text("确认还款");
				});
			});
			switchCode();
		});
		function switchCode() {
			var timenow = new Date();
			$("#codeNum").attr("src", "admin/imageCode.do?pageId=invest&d=" + timenow);
		};
	</script>
</body>
</html>
