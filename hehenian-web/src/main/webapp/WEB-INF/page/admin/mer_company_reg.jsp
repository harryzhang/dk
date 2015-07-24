<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
<style type="text/css">
b {
	color: red;
}

td {
	padding: 5px;
}
</style>
</head>
<body>
	<div id="right">
		<div style="padding: 15px;">
			<div>
				<table border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td width="100" height="28" class="main_alll_h2"><a href="companyRegInit.do">企业开户</a></td>
						<td width="2">&nbsp;</td>
					</tr>
				</table>
			</div>
			<div style="padding: 50px;  width: 1120px;  background-color: #fff;">
				<table width="50%" border="0" cellspacing="0" cellpadding="0" style=" font-size:12px;">
					<tr>
						<td align="right">企业全称：</td>
						<td><input type="text" id="usrname" /> <b>*</b></td>
					</tr>
					<tr>
						<td align="right">营业执照编号：</td>
						<td><input type="text" id="busiCode" /> <b>*</b></td>
					</tr>
					<tr>
						<td align="right">是否具有担保资格：</td>
						<td><input type="checkbox" name="guar" style="vertical-align: middle; " value="1" />是</td>
					</tr>
					<tr height="10"></tr>
					<tr>
						<td align="right"></td>
						<td><input value="确定" id="recharge" style="background: #666666;color: white;width: auto;" type="button" />
						</td>
					</tr>
					<tr height="60"></tr>
					<tr >
						<td colspan="2" style="font-size: 14px;font-weight: bold;"><span style="margin-left: -20px;">开户结果查询</span></td>
					</tr>
					<tr height="10"></tr>
					<tr>
						<td align="right">营业执照编号：</td>
						<td><input type="text" id="code" /> <input type="button" value="查询" onclick="query()" />
						</td>
					</tr>
					<tr >
						<td colspan="2" align="center"><b><span id="result" style="margin-left: -40px;"></span></b></td>
					</tr>
				</table>
			</div>
		</div>
	</div>
</body>
<div id="infos"></div>
<link rel="stylesheet" href="../css/jbox/Gray/jbox.css" type="text/css"></link>
<script type="text/javascript" src="../script/jbox/jquery.jBox-2.3.min.js"></script>
<script type="text/javascript" src="../script/jbox/jquery.jBox-zh-CN.js"></script>
<script type="text/javascript">
	$(function() {
		$("#recharge").click(function() {

			var busiCode = $("#busiCode").val();
			var usrname = $("#usrname").val();
			var guar = $("input[name=guar]:checked").val();
			if (busiCode == '') {
				alert("请填写营业执照编号");
				return;
			}
			if (usrname == '') {
				alert("请填写企业全称");
				return;
			}
			if (guar != 1) {
				if (!confirm("确定该企业无需担保资格?")) {
					return;
				}
			}
			param = {};
			param["busiCode"] = busiCode;
			param["usrname"] = usrname;
            if(!guar){
                guar="";
            }
			param["guar"] = guar;
			$.post("companyReg.do", $.param(param), function(data) {
				if (data.length < 20) {
					alert(data);
				} else {
					$("#infos").html(data);
				}
			});
		});
	});

	function query() {
		param = {};
		param["code"] = $("#code").val();
		$.post("queryCompany.do", $.param(param), function(data) {
			data = decodeURI(data);
			$("#result").html(data);
		});
	}
</script>
</html>
