<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>贷款审核</title>
<style>
b {
color: red;
}
</style>
<meta http-equiv="description" content="This is my page" />
<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
<link href="../css/css.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
<script type="text/javascript" src="../common/dialog/popup.js"></script>
<script language="javascript" type="text/javascript" src="../script/jbox/jquery.jBox-2.3.min.js"></script>
<script language="javascript" type="text/javascript" src="../My97DatePicker/WdatePicker.js"></script>
<script>
	$(function(){
		$("#btnSearch").click(function(){
			var loanAmount = $("#loanAmount").val();
			var loanStatus = $("input[type='radio']:checked").val();
			if(loanStatus != "NOPASS"){
				if(loanAmount == '') {
					alert("请录入完整信息，在提交！");
					return;
				}
			}
			if(loanStatus == '') {
				alert("请录入完整信息，在提交！");
				return;
			}
			$.post("updateLoanShInfo.do?loanPersonDo.loanDo.loanId=${loanPersonDo.loanDo.loanId}&loanPersonDo.loanDo.applyAmount="+applyAmount+"&loanPersonDo.loanDo.loanStatus="+loanStatus+"", function(data) {
				if (data == 1) {
					alert("提交成功");
					closeMthodes();
				} else {
					alert("提交失败");
				}
			});
		});
		$("#loanAmount").blur(function(){
			var bs = this.value;
			if(bs < 100 && !(bs%100==0)) {
				alert("请输入100的倍数");
				this.value='';
			}
		});
	});

	//弹出窗口关闭
	function closeMthodes() {
		window.parent.closeMthod();
	}

	//认证查看
	function queryMsg() {
		var id = '${map.publisher}';
		window.location.href = 'loanQuery.do?userId=' + id;
	}
	function changeSelectValue() {
		alert(this.value);
	}
	function queryById(id) {
		window.location.href = "loanQuery.do?id="+id
	}
</script>
</head>
<body>
<form id="addForm" action="updateLoanShInfo.do" method="post">
		<div align="right">
			<input type="button" style="background: #666666;" onclick="queryById(${loanPersonDo.loanDo.loanId})" value="客户信息" />
			<input type="button" style="background: #666666;"
								value="返回" onclick="closeMthodes();" />
		</div>
		<table style="margin-left: 40px; margin-right: 40px; margin-top: 20px; width: 90%" border="1">
			<tr style="height: 30px">
				<td colspan="4" style="text-align: left;">申请人基本信息</td>
			</tr>
			<input type="hidden" value="${loanPersonDo.loanPersonId}" name="loanPersonDo.loanPersonId"  />
        	<input type="hidden" value="${loanPersonDo.loanDo.loanId}" name="loanPersonDo.loanDo.loanId" />
				<tr>
				<td style="width: 100px; height: 30px;" align="right"
						class="blue12">
						真实姓名：
					</td>
					<td align="left">
					<input id="realName" name="loanPersonDo.realName" value="${loanPersonDo.realName }"
							   style="height: 20px;line-height: 20px;" type="text" readonly="readonly"/>
					</td>
					<td style="width: 100px; height: 30px;" align="right"
						class="blue12">
						手机号码：
					</td>
					<td align="left">
					 <input id="mobile" name="loanPersonDo.mobile" value="${loanPersonDo.mobile }"
							 style="height: 20px;line-height: 20px;" type="text" readonly="readonly" />
					</td>
				</tr>
				<tr>
					<td style="width: 100px; height: 30px;" align="right"
						class="blue12" >
						身份证：
					</td>
					<td align="left">
						<input id="cardNo" name="loanPersonDo.idNo" value="${loanPersonDo.idNo }"
							  style="height: 20px;line-height: 20px;" type="text" readonly="readonly"/>
					</td>
					<td style="width: 100px; height: 30px;" align="right"
						class="blue12" >
						借款期限：
					</td>
					<td align="left" class="f66">
						<input id="age" name="loanPersonDo.loanDo.loanPeriod" readonly="readonly" value="${loanPersonDo.loanDo.loanPeriod }" 
							   style="height: 20px;line-height: 20px;"  type="text"/>
					</td>
				</tr>
				<tr>
					<td style="width: 100px; height: 30px;" align="right"
						class="blue12" >
						借款金额：
					</td>
					<td align="left" class="f66">
						<input id="applyAmount" name="loanPersonDo.loanDo.applyAmount" readonly="readonly"  value="${loanPersonDo.loanDo.applyAmount }"
							   style="height: 20px;line-height: 20px;"  type="hidden"/>
						<fmt:formatNumber value="${loanPersonDo.loanDo.applyAmount}" pattern="##.##" minFractionDigits="2" />
					</td>
					<td style="width: 100px; height: 30px;" align="right"
						class="blue12" >
						放款金额：
					</td>
					<td align="left" class="f66">
						<input id="loanAmount" name="loanPersonDo.loanDo.loanAmount" class="hhn" value="${loanPersonDo.loanDo.loanAmount }" title="输入100的倍数"
							   style="height: 20px;line-height: 20px;"  readonly="readonly" type="text"/>
					</td>
				</tr>
				<tr>
					<td style="width: 100px; height: 30px;" align="right"
						class="blue12">
						借款用途：
					</td>
					<input id="loanUsage" type="hidden" name="loanPersonDo.loanDo.loanUsage" value="${loanPersonDo.loanDo.loanUsage }" />
					<td style="text-align: left;" colspan="3">${loanPersonDo.loanDo.loanUsage }	</td>
				</tr>
		</table>
		<table style="margin-left: 40px; margin-right: 40px; margin-top: 20px; width: 90%" border="1">
			<tr style="height: 30px">
				<td colspan="4" style="text-align: left;">&nbsp;审批信息</td>
			</tr>
			<tr>
				<td style="width: 100px; height: 30px;" align="left"
					class="blue12" colspan="4">
					<input type="radio" name="loanPersonDo.loanDo.loanStatus" disabled="disabled" <c:if test="${loanPersonDo.loanDo.loanStatus ne 'NOPASS'}">checked="checked"</c:if> value="AUDITED"/>&nbsp;审批通过&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="radio" name="loanPersonDo.loanDo.loanStatus" disabled="disabled" <c:if test="${loanPersonDo.loanDo.loanStatus == 'NOPASS'}">checked="checked"</c:if> value="NOPASS"/>&nbsp;审批失败
				</td>
			</tr>
		</table>
</form>
</body>
</html>
