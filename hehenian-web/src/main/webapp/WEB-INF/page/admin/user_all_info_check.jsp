<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>用户信息审核</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<style type="text/css">
* {
	margin: 0;
	padding: 0
}

ul,li {
	list-style: none
}

.box {
	width: 660px;
	border: 1px solid #ccc;
	margin: 10px;
	font-size: 12px;
	font-family: Verdana, Arial, Helvetica, sans-serif
}

.tagMenu {
	height: 28px;
	line-height: 28px;
	background: #efefef;
	position: relative;
	border-bottom: 1px solid #999
}

.tagMenu h2 {
	font-size: 12px;
	padding-left: 10px;
}

.tagMenu ul {
	position: absolute;
	left: 100px;
	bottom: -1px;
	height: 26px;
}

ul.menu li {
	float: left;
	margin-bottom: 1px;
	line-height: 16px;
	height: 14px;
	margin: 5px 0 0 -1px;
	border-left: 1px solid #999;
	text-align: center;
	padding: 0 12px;
	cursor: pointer
}

ul.menu li.current {
	border: 1px solid #999;
	border-bottom: none;
	background: #fff;
	height: 25px;
	line-height: 26px;
	margin: 0
}

.content {
	padding: 10px
}

.layout div{ 
	height:auto;
}
</style>
<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
<script language="javascript" type="text/javascript">
	var param = {};

	//记录当前提交的是基础审核请求还是工作审核请求
	var currentCheck = 0;
	$(function() {
		initWindow();
		$("ul.menu li").click(function() {
			var c = $("ul.menu li");
			var index = c.index(this);
			var p = "No";
			show(c, index, p);
		});

		$("#cancel").click(function() {
			window.parent.closeJbox();
		});

		//审核
		$("#next").click(function() {
			if ($("#pass").attr("checked") == "checked")
				param["paramMap.flag"] = "1";
			if ($("#nopass").attr("checked") == "checked")
				param["paramMap.flag"] = "0";
			if (param["paramMap.flag"] == null) {
				var c = $("ul.menu li");
				var index = c.index($("ul.menu li.current")) + 1;
				show(c, index, "No");
				return;
			}
			param["paramMap.userId"] = $("#userId").val();
			param["paramMap.discription"] = $("#discription").val();
			if (currentCheck == 0)
				$.post("userBaseDataCheck.do", param, function(data) {
					if (data.msg == "保存成功")
						alert("审核成功");
					else if(data.msg == "保存失败")
						alert("审核失败");
					else{
						show($("ul.menu li"),1,"No");
						return;
					}
					window.location.reload();
				});
			else if (currentCheck == 1) {
				$.post("updateworkAdmin.do", param, function(data) {
					alert(data.msg);
					window.location.reload();
				});
			}
		});
	});

	function initWindow() {
		$("ul.menu li:first-child").addClass("current");
		$("div.content").find("div.layout:not(:first-child)").hide();
		$.get("queryAdminBasecMessage.do?uid=" + $("#userId").val(), function(data) {
			$("#No0").html(data);
		});
		$.get("queryPersonworkmsg.do?uid=" + $("#userId").val(), function(data) {
			$("#No1").html(data);
		});
		$.get("queryFinanceById.do?uid=" + $("#userId").val(), function(data) {
			$("#No2").html(data);
		});
		$.get("queryUserMustMsg.do?uid=" + $("#userId").val(), function(data) {
			$("#No3").html(data);
		});
	}

	function show(controlMenu, num, prefix) {
		currentCheck = num;
		var content = prefix + num;
		$('#' + content).siblings().hide();
		$('#' + content).show();
		controlMenu.eq(num).addClass("current").siblings().removeClass("current");
		//财力信息和必填信息不需要审核窗
		if (num < 2)
			$("#checkNow").show();
		else
			$("#checkNow").hide();
		if (num == 0) {
			var baseStatus = $("#baseInfoStatus").val();
			if (baseStatus == 2)
				$("#nopass").attr("checked", "checked");
			else
				$("#pass").attr("checked", "checked");
		} else if (num == 1) {
			var baseStatus = $("#workInfoStatus").val();
			if (baseStatus == 2)
				$("#nopass").attr("checked", "checked");
			else
				$("#pass").attr("checked", "checked");
		}
	}
</script>
</head>

<body>
	<div style="height:550px;">
		<input type="hidden" id="userId" value="${userId }" />
		<div class="box">
			<div class="tagMenu">
				<h2>贷前审核</h2>
				<ul class="menu">
					<li>个人资料</li>
					<li>工作信息</li>
					<li>财力信息</li>
					<li>必填认证</li>
				</ul>
			</div>
			<div class="content">
				<div class="layout" id="No0" style=" height:auto;"><img alt="载入中" src="../images/load.gif">载入中...</div>
				<div class="layout" id="No1" style=" height:auto;"><img alt="载入中" src="../images/load.gif">载入中...</div>
				<div class="layout" id="No2" style=" height:auto;"><img alt="载入中" src="../images/load.gif">载入中...</div>
				<div class="layout" id="No3" style=" height:auto;"><img alt="载入中" src="../images/load.gif">载入中...</div>
				 
			</div>
		</div>
		
		
		<!-- 审核div -->
		<div style="width: 660px; margin-left:10px; border:1px solid #ccc;" id="checkNow">
			<table style="width: 100%;" cellspacing="3" cellpadding="3">
				<tr height="30">
					<td style=" background: #EFEFEF;" colspan="2"><b style=" font-size:12px; margin-left:10px;">认证资料审核</b></td>
				</tr>
				<tr>
					<td style=" font-size:12px; padding:10px 0 10px 10px;" colspan="2">审核意见:<input type="radio"
						name="pcheck" id="pass" style=" margin-left:15px; margin-right:3px;" /><a style="color: green;">审核通过</a><input type="radio" name="pcheck"
						id="nopass" style=" margin-left:15px; margin-right:3px;"/><a style="color: red;">审核不通过</a> <br /> 
					</td>
				</tr>
				<tr>
					<td valign="top" style="font-size:12px; padding-left:10px;">审核说明: </td>
					<td><textarea rows="4" cols="66" id="discription" style=" margin-left:15px; padding:3px;"></textarea></td>
				</tr>
			</table>
			<div align="center" style=" padding:15px 0;">
				<input type="button" value="关闭" id="cancel" /> <input type="button"
					value="确定" id="next" />
			</div>
		</div>
	</div>
</body>
</html>
