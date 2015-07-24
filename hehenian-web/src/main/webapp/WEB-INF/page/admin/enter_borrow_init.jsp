<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<%@ include file="/include/includeJs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="../jquery-ui/css/ui-lightness/jquery-ui-1.8.17.custom.css"></link>
<style>
input {
	height: 25px;
}

b {
	color: red;
}

li {
	font-size: 14px;
	font-family: "宋体"
}
</style>
</head>
<body>
	<div id="right" style="padding: 15px 10px 0px 10px;">
		<div>
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td id="admins" width="100px" height="28" class="main_alll_h2"><a href="importUserInfoInit.do">录入借款资料</a>
					</td>
					<td width="2">&nbsp;</td>
					<td width="900">&nbsp;</td>
				</tr>
			</table>
		</div>
		<div style="padding: 25px 10px 20px 280px;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0" style=" font-size:12px;">
				<tr>
					<td width="90" align="right">借款人：</td>
					<td><input type="text" id="uname" /><b>*</b><span id="utip" style="color: red;"></span>
					</td>
				</tr>
				<tr height="10"></tr>
				<tr>
					<td align="right" width="90"><span>借款用途：</span>
					</td>
					<td><input class="sel_140" id="purpose" type="text" /> <b>*</b></td>
				</tr>
				<tr height="10"></tr>
				<tr>
					<td align="right" width="90"><span>借款类型：</span>
					</td>
					<td><select style="color: black;" id="borrowway">
							<option value="">--请选择--</option>
							<option value="1">薪金贷</option>
							<option value="2">生意贷</option>
							<option value="3">业主贷</option>
					</select><b>*</b>
					</td>
				</tr>
				<tr height="10"></tr>
				<tr>
					<td align="right" width="90"><span>借款期限： </span>
					</td>
					<td><select id="deadMonth" style="display: block;float: left;margin-top: 5px;">
							<option value="">--请选择--</option>
							<option value="1">1个月</option>
							<option value="2">2个月</option>
							<option value="3">3个月</option>
							<option value="4">4个月</option>
							<option value="5">5个月</option>
							<option value="6">6个月</option>
							<option value="7">7个月</option>
							<option value="8">8个月</option>
							<option value="9">9个月</option>
							<option value="10">10个月</option>
							<option value="11">11个月</option>
							<option value="12">12个月</option>
							<option value="13">13个月</option>
							<option value="14">14个月</option>
							<option value="15">15个月</option>
							<option value="16">16个月</option>
							<option value="17">17个月</option>
							<option value="18">18个月</option>
							<option value="19">19个月</option>
							<option value="20">20个月</option>
							<option value="21">21个月</option>
							<option value="22">22个月</option>
							<option value="23">23个月</option>
							<option value="24">24个月</option>
					</select> <b>*</b>
					</td>
				</tr>
				<tr height="10"></tr>
				<tr>
					<td align="right" width="90"><span>借款金额： </span>
					</td>
					<td><input type="text" id="amount" /> 元<b>*</b></td>
				</tr>
				<tr height="10"></tr>
				<tr>
					<td align="right" width="90"><span>年利率： </span>
					</td>
					<td><input type="text" maxlength="5" id="anna" /> %<b>*</b>
					</td>
				</tr>
				<tr height="10"></tr>
				<tr>
					<td align="right" width="90"><span>最多投标人数： </span>
					</td>
					<td><input type="text" id="maxInvest" /> 人 <span id="maxInvestip" style="color:#888;margin-left: 10px;">选填,2~49人</span>
					</td>
				</tr>
				<tr height="10"></tr>
				<tr>
					<td align="right" width="90"><span>筹标期限： </span>
					</td>
					<td><select id="raiseTerm">
							<option value="">--请选择--</option>
							<option value="1">1天</option>
							<option value="2">2天</option>
							<option value="3">3天</option>
							<option value="4">4天</option>
							<option value="5">5天</option>
							<option value="6">6天</option>
							<option value="7">7天</option>
					</select><b>*</b></td>
				</tr>
				<tr height="10"></tr>
				<tr>
					<td align="right" width="90"><span>借款咨询方： </span>
					</td>
					<td><input type="text"  id="zxf" /><b> *</b></td>
				</tr>
				<tr height="10"></tr>
				<tr>
					<td align="right" width="90"><span>咨询方分行： </span>
					</td>
					<td><input type="text" id="zxfh" /><b> *</b></td>
				</tr>
				<tr height="10"></tr>
				<tr>
					<td align="right"><span>借款详情： </span>
					</td>
					<td><textarea cols="30" rows="4" id="info"></textarea>
					</td>
				</tr>
				<tr height="10"></tr>
				<tr>
					<td align="right"></td>
					<td><input value="发布" id="addborrow" style="background: #666666;color: white;width: auto;margin-left: 100px;" type="button" />
					</td>
				</tr>
			</table>
		</div>
	</div>
</body>
<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
<script type="text/javascript" language="javascript">
	$(function() {
		$("#addborrow").click(function() {
<%--var title = $("#title").val();--%>
	var borrowway = $("#borrowway").val();
			var purpose = $("#purpose").val();
			var deadLine = $("#deadMonth").val();
			var amount = $("#amount").val();
			var anna = $("#anna").val();
			var maxInvest = $("#maxInvest").val();
			var raiseTerm = $("#raiseTerm").val();
			var info = $("#info").val();
			var zxf = $("#zxf").val();
			var zxfh = $("#zxfh").val();
			
			if ($("#uname").val() == "") {
				alert("请输入借款人");
				$("#uname").val("");
				$("#uname").focus();
				return;
			}
			if (purpose == "") {
				alert("请输入借款用途");
				return;
			}
			if (borrowway == "") {
				alert("请选择借款类型");
				return;
			}
			if (deadLine == "") {
				alert("请选择借款期限");
				return;
			}
			if (amount == "") {
				alert("请填写借款金额");
				return;
			}
			if (isNaN(amount) || Number(amount) % 100 != 0 || amount < 100) {
				alert("请输入正确的金额，如：100,200");
				return false;
			}
			if (anna == "") {
				alert("请填写借款利率");
				return;
			}
			if (raiseTerm == "") {
				alert("请选择筹标期限");
				return;
			}
			if (isNaN($("#maxInvest").val())) {
				alert("请输入正确的人数数值");
				$("#maxInvest").focus();
				return false;
			}
			if (maxInvest != "" && (maxInvest<2||maxInvest>49)) {
				alert("最多投标人数在2~49之间");
				return;
			}
			if(zxf == ""){
				alert("请填写借款咨询方");
				$("#zxf").focus();
				return;
			}
			if(zxfh == ""){
				alert("请填写咨询方分行");
				$("#zxfh").focus();
				return;
			}
			$("#addborrow").attr("disabled", true);
			param["paramMap.title"] = purpose;
			param["paramMap.borrowway"] = borrowway;
			param["paramMap.purpose"] = purpose;
			param["paramMap.deadLine"] = deadLine;
			param["paramMap.amount"] = amount;

			param["paramMap.username"] = $("#uname").val();
			param["paramMap.anna"] = anna;
			param["paramMap.maxInvest"] = maxInvest;
			param["paramMap.raiseTerm"] = raiseTerm;
			param["paramMap.info"] = info;
			param["paramMap.zxf"] = zxf;
			param["paramMap.zxfh"] = zxfh;
			$.post("addEnterBorrow.do", param, function(data) {
				alert(data.msg);
				if (data.msg == "录入成功") {
					$("input[type=text]").val("");
					$("select").val("");
					$("#info").val("");
					param = {};
				} else {
					$("#addborrow").attr("disabled", false);
				}
			});
		});
	});
</script>
<script type="text/javascript" src="../jquery-ui/js/jquery-ui-1.8.17.custom.min.js"></script>
<script type="text/javascript">
	$(function() {
		$("#uname").autocomplete({
			width : 300,
			max : 10,
			delay : 100,
			minLength : 1,
			autoFocus : true,
			cacheLength : 1,
			highlight : false,
			source : function(request, response) {
				var param = {}
				param['paramMap.keyword'] = $("#uname").val();
				$.ajax({
					url : "queryBorrowerListHHN.do",
					dataType : "json",
					data : param,
					success : function(data) {
						response($.map(eval(data), function(item) {
							return {
								value : item.username,
								pranchiser_name : item.username,
								id : item.id
							};
						}));
					}
				});

			},
			select : function(event, ui) {
			}

		});
	});
</script>
</html>
