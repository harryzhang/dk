<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<link href="../css/admin/admin_css.css" rel="stylesheet"
			type="text/css" />
		<link href="../css/css.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
		<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
		<script type="text/javascript" src="../common/dialog/popup.js"></script>
		<script language="javascript" type="text/javascript"
			src="../script/jbox/jquery.jBox-2.3.min.js"></script>
		<script>	
	//转让
	function assignment() {
		var id = $("#id").val();
		var auctionDays = $("#auctionDays").val();
		var auctionBasePrice = $("#auctionBasePrice").val();
		var details = $("#details").val();
		var deadline = $("#deadline").val();
		param["paramMap.id"] = id;
		param["paramMap.auctionDays"] = auctionDays;
		param["paramMap.auctionBasePrice"] = auctionBasePrice;
		param["paramMap.details"] = details;
		param["paramMap.deadline"] = deadline;
		
		if (auctionDays == null || auctionDays == "") {
			alert("请选择转让期限");
			return null;
		}
		if (auctionBasePrice == null || auctionBasePrice == "") {
			alert("请填写转让底价");
			return null;
		}
		$.shovePost("assignment.do", param, function(data) {
			if (data == "1") {
				alert("提交成功")
				window.parent.closeMthod();
			}
			if (data == "2") {
				alert("提交失败");
				return;
			}
		});
	}
	
	
	
	//弹出窗口关闭
	function closeMthodes(){
			window.parent.closeMthodes();
		}
	</script>

	</head>
	<body>
	<input type="hidden" id="id" name="map.id" value="${map.id}"/>
	<input type="hidden" name="paramMap.deadline" id="deadline" value="${map.deadline}"/>
		<table
			style="margin-left: 40px; margin-right: 40px; margin-top: 80px; width: 90%"
			border="1">
			<tr style="height: 30px">
				<td colspan="2" style="text-align: center;">
					简单的债权说明：包括了债权过程的说明以及成功后收取的费用说明
				</td>
			</tr>
			<tr>
				<td style="width: 150px;" align="right">
					转让期限：<span class="require-field">*&nbsp;</span>
				</td>
				<td style="width: 200px;">
					<select id="auctionDays" name="auctionDays">
						<option value="" ${paramMap.auctionDays eq "" ? "selected=selected" : "" }>
							请选择
						</option>
						<option value="1" ${paramMap.auctionDays eq "1" ? "selected=selected" : "" }>
							1天
						</option>
						<option value="2" ${paramMap.auctionDays eq 2 ? "selected=selected" : ""}>
							2天
						</option>
						<option value="3" ${paramMap.auctionDays eq 3 ? "selected=selected" : ""}>
							3天
						</option>
						<option value="4" ${paramMap.auctionDays eq 4 ? "selected=selected" : ""}>
							4天
						</option>
						<option value="5" ${paramMap.auctionDays eq 5 ? "selected=selected" : ""}>
							5天
						</option>
						<option value="6" ${paramMap.auctionDays eq 6 ? "selected=selected" : ""}>
							6天
						</option>
						<option value="7" ${paramMap.auctionDays eq 7 ? "selected=selected" : ""}>
							7天
						</option>
					</select>
				</td>
			</tr>
			<tr>
				<td align="right" style="width: 150px;">
					债权金额：
				</td>
				<td style="width: 200px;">
					${map.realAmount}
				</td>
			</tr>
			<tr>
				<td align="right">
					债权期限：
				</td>
				<td>
				${map.deadlineTime}
				</td>
			</tr>
			<tr>
				<td align="right">
					转让底价：<span class="require-field">*&nbsp;</span>
				</td>
				<td>
					<input id="auctionBasePrice" name="paramMap.auctionBasePrice" type="text"
						maxlength="20" onkeyup="value=value.replace(/[^\d]/g,'')" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))" />
				</td>
			</tr>
			<tr>
				<td align="right">
					转让描述：
				</td>
				<td>
					<s:textarea id="details" name="paramMap.details" value="%{map.details}" cols="40" rows="2"></s:textarea>
				</td>
			</tr>
			<tr>
				<td align="center" colspan="2">
					<input type="button" style="background: #666666;" onclick="assignment();" value="确定" />
					<input type="button" style="background: #666666;" value="取消"
						onclick="closeMthodes();" />
				</td>
			</tr>
		</table>
	</body>
</html>
