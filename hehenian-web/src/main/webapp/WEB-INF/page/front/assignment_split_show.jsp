<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<link href="css/admin/admin_css.css" rel="stylesheet" type="text/css" />
		<link href="css/css.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="script/jquery-1.7.1.min.js"></script>
		<script type="text/javascript" src="script/jquery.shove-1.0.js"></script>
		<script type="text/javascript" src="common/dialog/popup.js"></script>
		<script type="text/javascript"
			src="script/jbox/jquery.jBox-2.3.min.js"></script>
		<script type="text/javascript" src="script/jbox/jquery.jBox-zh-CN.js"></script>
		<script>
			
	//拆分
	function assignmentSplit() {
		var split = $("#split").val();
		var realAmount = $("#realAmount").val();
		var details = $("#details").val();
		var number = $("#number").val();
		param["paramMap.split"] = split;
		param["paramMap.realAmount"] = realAmount;
		param["paramMap.details"] = details;
		param["paramMap.number"] = number;
		param["paramMap.ids"] =$("#ids").val();;
		
		if (split == null || split == "") {
			alert("请选择拆分方式");
			return null;
		}
		if(split == "1")
		{
			if(number <= 1 || number > 49)
			{
				alert("请输入正确是份数");
				$("#number").attr("value","");
				return null;
			}
		}
		if(split == "2")
		{
			param["paramMap.number"] = 1;
		}
		if (realAmount == null || realAmount == "") {
			alert("请填写拆分金额");
			return null;
		}
		$.shovePost("assignmentSplit.do", param, function(data) {
			if (data == "1") {
				alert("拆分成功")
				var para1 = {};
				window.parent.closeMethod();
			}
			if (data == "2") {
				alert("拆分失败");
				return;
			}
			
		});
	}
	
	//拆分验证
	function split()
	{
		var split = $("#split").val();
		if(split == 1){
			$("#realAmount").attr("readonly", "readonly");
			$("#number").removeAttr("disabled");
			$("#realAmount").attr("value", "");
		}else if(split == 2){
			$("#number").attr("disabled", "disabled");
			$("#realAmount").removeAttr("readonly");
			$("#number").attr("value", "");
			$("#realAmount").attr("value", "");
		}
		else{
			$("#number").removeAttr("disabled");
			$("#realAmount").removeAttr("readonly");
			$("#realAmount").attr("value", "");
		}
	}
	
	//平均拆分计算
	function count()
	{
		var number = $("#number").val();
		var split = $("#split").val();
		if (split == null || split == "") {
			alert("请选择拆分方式");
			$("#number").attr("value","");
			return null;
		}
		if(number <= 1 || number > 49)
		{
			alert("请输入正确是份数");
			$("#number").attr("value","");
			return null;
		}
		
		if($.trim($("#number").val()).length != 0)
			{
				$.post("queryDebtSumById.do?number="+number, function(data) {
				
					$("#realAmount").attr("value",data);
				});
			}
	}
	
	//部分拆分计算
	function queryRealAmount(){
		var split = $("#split").val();
		if (split == null || split == "") {
			alert("请选择拆分方式");
			$("#number").attr("value","");
			return null;
		}
		var realAmount = $("#realAmount").val();
		if (realAmount == null || realAmount == "" && split == "2") {
			alert("请填写拆分金额");
			return null;
		}
		$.post("queryRealAmount.do?realAmount="+realAmount, function(data) {
			if(data=="2"){
				$("#realAmount").attr("value","");
				$("#realAmountSpan").html("<span style='color:red;'>您输入的金额超过了拆分总额，请重新输入!</span>");
				return;
			}else{
				$("#realAmountSpan").html("");
			}
		});
	}
	
	//弹出窗口关闭
	function closeMthodes(){
		window.parent.closeMethod();
	}
	</script>

	</head>
	<body>
		<input type="hidden" id="ids" name="" value="${request.ids }"/>
		<table
			style="margin-left: 40px; margin-right: 40px; margin-top: 80px; width: 90%"
			border="1">
			<tr style="height: 30px">
				<td colspan="2" style="text-align: center;">
					债权拆分说明：包括了债权拆分的具体方式，及拆分后的情况。
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
					借款人：
				</td>
				<td>
					${map.username}
				</td>
			</tr>
			<tr>
				<td style="width: 150px;" align="right">
					拆分方式：
					<span class="require-field">*&nbsp;</span>
				</td>
				<td style="width: 200px;">
					<select id="split" name="split" onchange="split();">
						<option value="" ${paramMap.split eq "" ? "selected=selected" : "" }>
							请选择
						</option>
						<option value="1" ${paramMap.split eq "1" ? "selected=selected" : "" }>
							平均拆分
						</option>
						<option value="2" ${paramMap.split eq 2 ? "selected=selected" : ""}>
							部分拆分
						</option>
					</select>
				</td>
			</tr>
			<tr>
				<td align="right">
					拆分份数：
					<span class="require-field">*&nbsp;</span>
				</td>
				<td>
					<input id="number" name="paramMap.number" onblur="count();"
						type="text" onkeyup="value=value.replace(/[^\d]/g,'')"
						onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))"></input>
					<span class="#000000">*&nbsp;轻输入2-49的正整数</span>
				</td>
			</tr>
			<tr>
				<td align="right">
					拆分金额：
					<span class="require-field">*&nbsp;</span>
				</td>
				<td>
					<input id="realAmount" name="paramMap.realAmount" type="text"
						onblur="queryRealAmount();" maxlength="20"
						onkeyup="value=value.replace(/[^\d]/g,'')"
						onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))" />
					<span id="realAmountSpan" name="realAmountSpan"></span>
				</td>
			</tr>
			<tr>
				<td align="right">
					拆分描述：
				</td>
				<td>
					<s:textarea id="details" name="paramMap.details"
						value="%{map.details}" cols="40" rows="2"></s:textarea>
				</td>
			</tr>
			<tr>
				<td align="center" colspan="2">
					<input type="button" style="background: #666666;"
						onclick="assignmentSplit();" value="确定" />
					<input type="button" style="background: #666666;" value="取消"
						onclick="closeMthodes();" />
				</td>
			</tr>
		</table>
	</body>
</html>
