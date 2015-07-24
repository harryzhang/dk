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
</style>
<form id="editForm">
	<table width="480" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td colspan="2"><p>简单的债权说明：包括了债权过程的说明以及成功后收取的费用说明</p></td>
		</tr>
		<tr height="10"></tr>
		<tr style="margin-top: 10px;">
			<th align="right">债权金额：</th>
			<td width="360"><span id="span_debtSum">${paramMap.debtSum }</span>元 <s:hidden id="debtSum" name="paramMap.debtSum"></s:hidden> <s:hidden id="investId" name="paramMap.investId"></s:hidden>
			债权转让金额在 ${paramMap.debtSum }*0.9 ~  ${paramMap.debtSum }*1.1 之间 
			</td>
		</tr>
		<tr height="10"></tr>
		<tr >
			<th width="120" align="right">债权期限：</th>
			<td width="360"><span id="span_debtLimit">${paramMap.debtLimit}</span>个月 <input id="debtLimit" name="paramMap.debtLimit" value="${paramMap.debtLimit}" type="hidden" />
			</td>
		</tr>
		<tr height="10"></tr>
		<tr >
			<th width="120" align="right">转让价格：</th>
			<td width="360"><input type="text" class="inp90" id="auctionBasePrice" name="paramMap.auctionBasePrice" value="${ paramMap.auctionBasePrice}" /> <strong style="color: red;" class="errorrrr"><s:fielderror
						fieldName="paramMap.auctionBasePrice" id="priceTip" /> </strong> <input id="borrowId" name="paramMap.borrowId" value="${paramMap.borrowId }" type="hidden" />
			</td>
		</tr>
		<tr height="10"></tr>
		<tr >
			<th width="120" align="right" >转让描述：</th>
			<td width="360"><textarea class="txt380" rows="6" cols="30" name="paramMap.details">${paramMap.details }</textarea><br /> <strong style="color: red;"  class="errorrrr"> <s:fielderror
						fieldName="paramMap.details" id="detailsTip" /> </strong></td>
		</tr>
		<tr height="20"></tr>
		<tr >
			<td>&nbsp;</td>
			<td width="360"><input type="button" value="确认" id="debt_ok" /> <input type="button" value="取消" id="debt_cancel" />
			</td>
		</tr>
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
<%--			param["paramMap.auctionBasePrice"] = $("#auctionBasePrice").val();--%>
			$.post("addAssignmentDebt.do", para, function(data) {
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

