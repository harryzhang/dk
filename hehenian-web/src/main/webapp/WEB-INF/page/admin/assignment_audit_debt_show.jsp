<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<meta http-equiv="description" content="This is my page" />
		<link href="../css/admin/admin_css.css" rel="stylesheet"
			type="text/css" />
		<link href="../css/css.css" rel="stylesheet" type="text/css" />
        <link href="../css/jbox/Gray/jbox.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
		<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
		<script type="text/javascript" src="../common/dialog/popup.js"></script>
		<script language="javascript" type="text/javascript"
			src="../script/jbox/jquery.jBox-2.3.min.js"></script>
			<script language="javascript" type="text/javascript" src="../My97DatePicker/WdatePicker.js"></script>
		<script>
			
	//审核
	function updateAssginment() {
		var id = $("#ids").val();
		var status = $("input[name='borrowStatusr']:checked").val();
		param["paramMap.status"] = status;
		param["paramMap.id"] = id;
		param["paramMap.investId"] = $("#investId").val();
		param["paramMap.remark"] = $("#echeckMailContent").val();
		param["paramMap.time"] = $("#time").val();
		$.jBox.tip("<div class='data-submit'>提交数据中，请稍候...</div>","loading");
		$.shovePost("auditDebt.do", param, function(data) {
			if (data == "1") {
				alert("提交成功")
				window.parent.closeMthod();
			}
			if (data == "2") {
				$.jBox.closeTip();
				alert("提交失败");
				return;
			}
		});
	}
	function audit(status){
		if(status=="0"){
		$("#echeckMailContent").html("${paramMap.username}的债权审核通过");
		}
		if(status=="1"){
		$("#echeckMailContent").html("${paramMap.username}的债权审核不通过");
		}
	}
	//弹出窗口关闭
	function closeMthodes(){
			window.parent.closeMthod();
		}
	</script>

	</head>
	<body>
		<table
			style="margin-left: 40px; margin-right: 40px; margin-top: 20px; width: 90%"
			border="1">
			<tr style="height: 30px">
				<td colspan="4" style="text-align: left; color: rgb(62, 73, 89);background: url('../images/admin/title_bg_blue.jpg') repeat-x;">
					债权转让审核
				</td>
			</tr>
			<tr>
				<td style="width: 150px;" align="right">
					转让人：
				</td>
				<td style="width: 200px;">
					${paramMap.username}
				</td>
				<td align="right" style="width: 150px;">
					转让期限：
				</td>
				<td style="width: 200px;">
					${paramMap.debtLimit}
				</td>
			</tr>
			<tr>
				<td align="right">
					剩余本金：
				</td>
				<td>
					${paramMap.balance}
				</td>
				<td align="right">
					年利率：
				</td>
				<td>
					${paramMap.annualRate}%
				</td>
			</tr>
			<tr>
				<td align="right">
					债权金额：
				</td>
				<td>
					${paramMap.debtSum}
				</td>
				<td align="right">
					转让价格：
				</td>
				<td>
					${paramMap.auctionBasePrice}
				</td>
			</tr>
			<tr>
				<td align="right">
					借款人：
				</td>
				<td>
					${paramMap.publisher}
				</td>
				<td align="right">
					借款标题：
					</td>
					<td>
						${paramMap.borrowTitle}
					</td>
			</tr>
		</table>
		<table style="margin-left: 40px; margin-right: 40px; margin-top: 20px; width: 90%"
				border="1">
			<tr style="height: 30px">
				<td colspan="7" style="text-align: left; color: rgb(62, 73, 89);background: url('../images/admin/title_bg_blue.jpg') repeat-x;">
					&nbsp; 债权还款时间表
				</td>
			</tr>
			<tr style="height: 30px">
			<th  style="text-align: center;">
				序号
				</th>
				<th  style="text-align: center;">
				还款日期
				</th>
				<th  style="text-align: center;">
				已还本息
				</th>
				<th  style="text-align: center;">
				待还本息
				</th>
				<th  style="text-align: center;">
				是否逾期
				</th>
				<th  style="text-align: center;">
				待付罚息
				</th>
				<th  style="text-align: center;">
				状态
				</th>
			</tr>
			<s:iterator value="pageBean.page" var="bean" status="s">
			<tr style="height: 30px">
				<td  style="text-align: center;">
				${s.count }
				</td>
				<td  style="text-align: center;">
				${bean.repayDate }
				</td>
				<td  style="text-align: center;">
				${bean.hasPrincipal+bean.hasInterest }
				</td>
				<td  style="text-align: center;">
				${bean.recivedPrincipal+bean.recivedInterest }
				</td>
				<td  style="text-align: center;">
				<s:if test="%{#bean.isLate ==1}">未逾期</s:if>
				<s:if test="%{#bean.isLate ==2}">逾期</s:if>
				</td>
				<td  style="text-align: center;">
				${bean.recivedFI }
				</td>
				<td  style="text-align: center;">
				<s:if test="%{#bean.realRepayDate ==null||#bean.realRepayDate ==''}">
				未偿还
				</s:if>
				<s:else>
				已偿还
				</s:else>
				</td>
			</tr>
			</s:iterator>
		</table>
		<table
				style="margin-left: 40px; margin-right: 40px; margin-top: 20px; width: 90%"
				border="1">
				<tr style="height: 30px">
					<td colspan="4" style="text-align: left; color: rgb(62, 73, 89);background: url('../images/admin/title_bg_blue.jpg') repeat-x;">
						&nbsp; 审核
					</td>
				</tr>
				<tr>
				
					<td colspan="4">
						<table style="width: 100%" border="0">
							<tr>
								<td align="right">
									审核状态：
								</td>
								<td align="left">
								<input type="hidden" value="${ paramMap.investId}"  id = "investId"/>
									<input type="hidden" value="${paramMap.id}" id="ids" />
										<input type="radio" id="statusYes" name="borrowStatusr"
											value="2" 
											style="vertical-align: text-bottom; margin-bottom: -2px;"
											 onclick="audit(0)"/>
									通过&nbsp;&nbsp;&nbsp;&nbsp;
								<input type="radio" id="statusNo" name="borrowStatusr"
											value="6" 
											style="vertical-align: text-bottom; margin-bottom: -2px;"checked="checked" 
											onclick="audit(1)"/>
									未通过&nbsp;&nbsp;&nbsp;&nbsp;
								</td>
							</tr>
							<tr>
								<td align="right">
									添加备注：
								</td>
								<td align="left" colspan="2">
									<textarea name="echeckMailContent" id="echeckMailContent"
										cols="50" readonly="readonly" rows="3"></textarea>
								</td>
							</tr>
							<tr>
								<td align="right">
									发布时间：
								</td>
								<td align="left" colspan="2">
									<input id="time" type="text"
										onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'%y-%M-%d %H:{%m+5}:%s',readOnly:'readOnly'});"
									style="border: 1px solid rgb(62, 73, 89);"/>
								</td>
							</tr>
							<tr>
								<td colspan="3" align="center">
									<input type="button" style="background: #666666;" value="确定"
										onclick="updateAssginment();" />
									<input type="button" style="background: #666666;" value="取消"
										onclick="closeMthodes();" />
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
	</body>
</html>
