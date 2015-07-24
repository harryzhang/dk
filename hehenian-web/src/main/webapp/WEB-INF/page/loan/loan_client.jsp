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
.hhn {
	border: 1px #ccc solid;
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
			var applyAmount = $("#applyAmount").val();
			var loanStatus = $("input[type='radio']:checked").val();
			if(loanStatus != "NOPASS"){
				if(loanAmount == '' || loanAmount === undefined) {
					alert("请录入完整信息，在提交！");
					return;
				}
			}
			if(loanStatus == ''|| loanStatus === undefined) {
				alert("请录入完整信息，在提交！");
				return;
			}
			if(Number(applyAmount) < Number(loanAmount)) {
				alert("放款金额大于借款金额！");
				return;
			}
			$.post("updateLoanShInfo.do?loanPersonDo.loanDo.loanId=${loanPersonDo.loanDo.loanId}&loanPersonDo.loanDo.loanAmount="+loanAmount+"&loanPersonDo.loanDo.loanStatus="+loanStatus+"", function(data) {
				if (data == 1) {
					alert("提交成功");
					closeMthodes();
				} else {
					alert("提交失败");
				}
			});
		});
	});

	function queryById(id) {
		window.location.href = "loanQuery.do?id="+id
	}
	
	function validatorData(e) {
		 var re = /^\d+(?=\.{0,1}\d+$|$)/ 
	    if (e.value != "") { 
	        if (!re.test(e.value)) { 
	            alert("请输入数字"); 
	            e.value = ""; 
	            e.focus(); 
	        }
	        if(!(e.value < 100) && !(e.value%100==0)) {
				alert("请输入100的倍数");
				this.value='';
				e.focus(); 
			}
	    } 
	}
	
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
</script>
</head>
<body>
<form id="addForm" action="updateLoanShInfo.do" method="post">
		<div align="right">
			<input type="button" style="background: #666666;" onclick="queryById(${loanPersonDo.loanDo.loanId})" value="客户信息" /> 
			<input type="button" style="background: #666666;" title="提交后将不能修改，请确认无误！" value="提交" id="btnSearch" /> 
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
						<input id="applyAmount" name="loanPersonDo.loanDo.applyAmount"   value="${loanPersonDo.loanDo.applyAmount }"
							   style="height: 20px;line-height: 20px;"  type="hidden"/>
						<fmt:formatNumber value="${loanPersonDo.loanDo.applyAmount}" pattern="##.##" minFractionDigits="2" />
					</td>
					<td style="width: 100px; height: 30px;" align="right"
						class="blue12" >
						放款金额：
					</td>
					<td align="left" class="f66">
						<input id="loanAmount" name="loanPersonDo.loanDo.loanAmount" placeholder="请输入100的倍数" class="hhn" value="${loanPersonDo.loanDo.loanAmount}" title="请输入100的倍数"
							   style="height: 20px;line-height: 20px;"  type="text" onchange="validatorData(this)"/>
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
					<input type="radio" name="loanPersonDo.loanDo.loanStatus" <c:if test="${loanPersonDo.loanDo.loanStatus == 'AUDITED'}">checked="checked"</c:if> value="AUDITED"/>&nbsp;审批通过&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="radio" name="loanPersonDo.loanDo.loanStatus" <c:if test="${loanPersonDo.loanDo.loanStatus == 'NOPASS'}">checked="checked"</c:if> value="NOPASS"/>&nbsp;审批失败
				</td>
			</tr>
		</table>
</form>
</body>
</html>
