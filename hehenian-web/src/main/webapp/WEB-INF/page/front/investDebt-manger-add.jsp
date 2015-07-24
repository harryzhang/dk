<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp"%>
<style>
input[type="button"] {
	width: 95px;
	height: 27px;
	border: none;
	text-align: center;
	color: #fff;
	background: url(images/pic_all.png) -1px -84px no-repeat;
	cursor: pointer;
}

input[type="button"]:hover {
	background: url(images/pic_all.png) -1px -112px no-repeat;
}
tr{
margin-top: 5px;
margin-left: 10px;
padding-left: 30px;
}

</style>
<form id="editForm" style="padding-left: 30px;">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td colspan="2"><p style="margin-left: -25px;">简单的债权说明：包括了债权过程的说明以及成功后收取的费用说明</p></td>
		</tr>
		<tr height="15"></tr>
		<tr height="15"></tr>
		<tr style="margin-top: 10px;">
			<td align="right">债权金额：</td>
			<td><span id="span_debtSum">${paramMap.debtSum }</span>元 <s:hidden id="debtSum" name="paramMap.debtSum"></s:hidden> <s:hidden id="investId" name="paramMap.investId"></s:hidden>
			</td>
		</tr>
		<tr height="15"></tr>
		<tr style="margin-top: 10px;">
			<td align="right">债权期限：</td>
			<td><span id="span_debtLimit">${paramMap.debtLimit}</span>个月 <input id="debtLimit" name="paramMap.debtLimit" value="${paramMap.debtLimit}" type="hidden" />
			</td>
		</tr>
		<tr height="15"></tr>
		<tr style="margin-top: 10px;">
			<td align="right">转让价格：</td>
			<td><input type="text" class="inp90" name="paramMap.auctionBasePrice" value="${ paramMap.auctionBasePrice}" /> <strong style="color: red;" class="errorrrr"><s:fielderror
						fieldName="paramMap.auctionBasePrice" id="priceTip" /> </strong> <input id="borrowId" name="paramMap.borrowId" value="${paramMap.borrowId }" type="hidden" />
			</td>
		</tr>
		<tr height="15"></tr>
		<tr style="margin-top: 10px;">
			<td align="right" valign="top">转让描述：</td>
			<td><textarea class="txt380" rows="6" cols="30" name="paramMap.details">${paramMap.details }</textarea><br /> <strong style="color: red;"  class="errorrrr"> <s:fielderror
						fieldName="paramMap.details" id="detailsTip" /> </strong></td>
		</tr>
		<tr height="20"></tr>
		<tr style="margin-top: 10px;">
			<td>&nbsp;</td>
			<td style="padding-top:10px;"><input type="button" value="确认" id="debt_ok" /> <input type="button" value="取消" id="debt_cancel" />
			</td>
		</tr>
		<tr height="15"></tr>
	</table>
</form>

<script>
	$(function() {

		$("#debt_cancel").click(function() {
			$("#detailsTip").html("");
			$("#priceTip").html("");
			$("#zrzq_div").attr("style", "display:none");
			$(".tzjl_fwxy1h").hide();
			$("#editForm")[0].reset();
			$(".errorrrr").html("");
		});
		$("#debt_ok").click(function() {
			var para = $("#editForm").serialize();
			$.post("addInvestDebt.do", para, function(data) {
				if (data == 1) {
					alert("操作成功");
					$("#debt_cancel").click();
					$("#btn_search").click();
					window.location.reload();
				} else if (data == -1) {
					alert("操作失败");
					$("#debt_cancel").click();
				} else {
					$("#debt_add").html(data);
				}
			});
		});
	});
</script>

