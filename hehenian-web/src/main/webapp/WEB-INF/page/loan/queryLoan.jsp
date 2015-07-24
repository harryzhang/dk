<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>业务受理</title>
<meta http-equiv="description" content="This is my page" />
<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
<link href="../css/css.css" rel="stylesheet" type="text/css" />
<style>
.hhn {
	fason: expression(this.readOnly = true)
}
</style>
<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
<script type="text/javascript" src="../common/dialog/popup.js"></script>
<script language="javascript" type="text/javascript"
	src="../script/jbox/jquery.jBox-2.3.min.js"></script>
<script language="javascript" type="text/javascript"
	src="../My97DatePicker/WdatePicker.js"></script>
<script>
	$(function() {
		$("#btnSearch").click(function() {
			var realName = $("#realName").val();
			if (realName == "") {
				alert("请填写真实姓名");
			} else {
				$("#addForm").submit();
			}
		});

		$("#onSend").click(function() {
			$.post("changeloanStatus.do", para, function(data) {
				if (data == 1) {
					alert("操作成功");
					$("#debt_cancel").click();
					window.location.href = "cf-userinfo.do";
				} else {
					alert(data);
					$("#debt_cancel").click();
				}
			});
		});
	});

	//弹出窗口关闭
	function closeMthodes() {
		window.parent.closeMthod();
	}

	//认证查看
	function queryMsg() {
		var id = '${map.publisher}';
		window.location.href = 'queryPerUserCreditAction.do?userId=' + id;
	}

	function changeSelectValue() {
		alert(this.value);
	}
</script>
</head>
<body>
	<form id="addForm" action="loanUpdate.do" method="post">
		<div align="right">
			<input type="button" style="background: #666666;" value="返回"
				onclick="closeMthodes();" />
		</div>
		<table
			style="margin-left: 40px; margin-right: 40px; margin-top: 20px; width: 90%"
			border="1">
			<tr style="height: 30px">
				<td colspan="4" style="text-align: left;">申请人基本信息</td>
			</tr>
			<tr>
				<td style="width: 100px; height: 30px;" align="right">
					真实姓名：</td>
				<td align="left"><input id="realName"
					name="loanPersonDo.realName" value="${loanPersonDo.realName }" 
					style="height: 20px; line-height: 20px;" type="text"  readonly="readonly" />
				</td>
				<td style="width: 100px; height: 30px;" align="right">
					手机号码：</td>
				<td align="left"><input id="mobile" name="loanPersonDo.mobile"
					value="${loanPersonDo.mobile }"
					style="height: 20px; line-height: 20px;" type="text" readonly="readonly" />
				</td>
			</tr>
			<tr>
				<td style="width: 100px; height: 30px;" align="right">
					身份证：</td>
				<td align="left"><input id="cardNo" name="loanPersonDo.idNo"
					value="${loanPersonDo.mobile }"
					style="height: 20px; line-height: 20px;" type="text" readonly="readonly" />
				</td>
				<td style="width: 100px; height: 30px;" align="right">
					小区名称：</td>
				<td align="left"><input id="cname" name="loanPersonDo.cname"
					readonly="readonly" value="${loanPersonDo.cname }"
					style="height: 20px; line-height: 20px;" type="text" /></td>
			</tr>
			<tr>
				<td style="width: 100px; height: 30px;" align="right">
					年龄：</td>
				<td align="left"><input id="age" name="loanPersonDo.age"
					readonly="readonly" value="${loanPersonDo.age }"
					style="height: 20px; line-height: 20px;" type="text" /></td>
				<td style="width: 100px; height: 30px;" align="right">
					性别：</td>
				<td align="left"><c:if test="${loanPersonDo.sex == 'MALE'}">男</c:if>
					<c:if test="${loanPersonDo.sex == 'FEMALE'}">女</c:if></td>
			</tr>
			<tr>
				<td style="width: 100px; height: 30px;" align="right">
					婚姻状况：</td>
				<td align="left"><c:if
						test="${loanPersonDo.marriaged == 'UNMARRIED'}">已婚</c:if> <c:if
						test="${loanPersonDo.marriaged == 'MARRIED'}">未婚</c:if> <c:if
						test="${loanPersonDo.marriaged == 'DIVORCE'}">离异</c:if></td>
				<td style="width: 100px; height: 30px;" align="right">
					受教育程度：</td>
				<td align="left"><c:if
						test="${loanPersonDo.education == 'GRADE_SCHOOL'}">初中及以下</c:if><c:if
						test="${loanPersonDo.education == 'HIGN_SCHOOL'}">高中</c:if> <c:if
						test="${loanPersonDo.education == 'JUNIOR_COLLEGE'}">大专</c:if> <c:if
						test="${loanPersonDo.education == 'BACHELOR'}">本科以上</c:if></td>
			</tr>
			<tr>
				<td style="width: 100px; height: 30px;" align="right">
					住宅电话：</td>
				<td align="left"><input id="familyPhone"
					name="loanPersonDo.familyPhone" readonly="readonly"
					value="${loanPersonDo.familyPhone }"
					style="height: 20px; line-height: 20px;" type="text" /></td>
				<td style="width: 100px; height: 30px;" align="right">
					邮箱：</td>
				<td align="left"><input id="email" name="loanPersonDo.email"
					readonly="readonly" value="${loanPersonDo.email }"
					style="height: 20px; line-height: 20px;" type="text" /></td>
			</tr>
			<tr>
				<td style="width: 100px; height: 30px;" align="right">
					借款用途：</td>
				<td colspan="3" style="text-align: left;">
					${loanPersonDo.loanDo.loanUsage }</td>
			</tr>
		</table>
		<table
			style="margin-left: 40px; margin-right: 40px; margin-top: 20px; width: 90%"
			border="1">
			<tr style="height: 30px">
				<td colspan="4" style="text-align: left;">&nbsp; 职业信息</td>
			</tr>
			<tr>
				<td style="width: 100px; height: 30px;" align="left" colspan="4">
					<input type="radio" name="loanPersonDo.jobDo.jobType" disabled="disabled"
					<c:if test="${loanPersonDo.jobDo.jobType == 'SELF_EMPLOYED'}">checked="checked"</c:if>
					value="SELF_EMPLOYED" />&nbsp;自雇人士&nbsp;&nbsp;&nbsp;&nbsp; <input
					type="radio" name="loanPersonDo.jobDo.jobType" disabled="disabled"
					<c:if test="${loanPersonDo.jobDo.jobType == 'SALARYMAN'}">checked="checked"</c:if>
					value="SALARYMAN" />&nbsp;工薪族
				</td>
			</tr>
			<tr>
				<td style="width: 100px; height: 30px;" align="right">
					工作单位：</td>
				<td align="left"><input id="companyName"
					name="loanPersonDo.jobDo.companyName" readonly="readonly"
					value="${loanPersonDo.jobDo.companyName}"
					style="height: 20px; line-height: 20px;" type="text" /></td>
				<td style="width: 100px; height: 30px;" align="right">
					职位级别：</td>
				<td align="left"><input id="position"
					name="loanPersonDo.jobDo.position" readonly="readonly"
					value="${loanPersonDo.jobDo.position}"
					style="height: 20px; line-height: 20px;" type="text" /></td>
			</tr>
			<tr>
				<td style="width: 100px; height: 30px;" align="right">
					工作年限：</td>
				<td align="left"><input id="jobYear"
					name="loanPersonDo.jobDo.jobYear" readonly="readonly"
					value="${loanPersonDo.jobDo.jobYear }"
					style="height: 20px; line-height: 20px;" type="text" /></td>
				<td style="width: 100px; height: 30px;" align="right">
					月收入：</td>
				<td align="left">
					<fmt:formatNumber value="${loanPersonDo.jobDo.jobIncome}" pattern="##.##" minFractionDigits="2" />
				</td>
			</tr>
			<tr>
				<td style="width: 100px; height: 30px;" align="right">
					单位电话：</td>
				<td align="left" colspan="3"><input id="companyPhone"
					name="loanPersonDo.jobDo.companyPhone" readonly="readonly"
					value="${loanPersonDo.jobDo.companyPhone}"
					style="height: 20px; line-height: 20px;" type="text" /></td>
			</tr>
		</table>
		<table
			style="margin-left: 40px; margin-right: 40px; margin-top: 20px; width: 90%"
			border="1">
			<tr style="height: 30px">
				<td colspan="4" style="text-align: left;">&nbsp; 个人资产信息</td>
			</tr>
			<tr>
				<td style="width: 100px; height: 30px;" align="right">
					房产地址：</td>
				<td align="left" colspan="3"><input id="houseAddress"
					name="loanPersonDo.propertyDo.houseAddress" readonly="readonly"
					style="height: 20px; line-height: 20px; width: 80%;" type="text"
					value="${loanPersonDo.propertyDo.houseAddress}" /></td>
			</tr>
			<tr>
				<td style="width: 100px; height: 30px;" align="right">
					购买时间：</td>
				<td align="left"><fmt:formatDate
						value="${loanPersonDo.propertyDo.purchaseDate}"
						pattern="yyyy-MM-dd" var="formatDay" /> <input id="ordDate"
					name="loanPersonDo.propertyDo.purchaseDate" value="${formatDay}"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})"
					readonly="readonly" /></td>
				<td style="width: 100px; height: 30px;" align="right">
					购买方式：</td>
				<td align="left"><c:if
						test="${loanPersonDo.propertyDo.purchaseWay == 'NOMORTGAGE'}">一次性</c:if>
					<c:if test="${loanPersonDo.propertyDo.purchaseWay == 'MORTGAGE'}">按揭</c:if>
				</td>
			</tr>
			<tr>
				<td style="width: 100px; height: 30px;" align="right">
					车牌号码：</td>
				<td align="left"><input id="carNo"
					name="loanPersonDo.propertyDo.carNo" readonly="readonly"
					value="${loanPersonDo.propertyDo.carNo}"
					style="height: 20px; line-height: 20px;" type="text" /></td>
				<td style="width: 100px; height: 30px;" align="right">
					车辆品牌型号：</td>
				<td align="left"><input id="carBrand"
					name="loanPersonDo.propertyDo.carBrand" readonly="readonly"
					value="${loanPersonDo.propertyDo.carBrand}"
					style="height: 20px; line-height: 20px;" type="text" /></td>
			</tr>
			<tr>
				<td style="width: 100px; height: 30px;" align="left" colspan="4">
					<input type="radio" name="loanPersonDo.propertyDo.houseStatus" disabled="disabled"
					<c:if test="${loanPersonDo.propertyDo.houseStatus == 'PASS'}">checked="checked"</c:if>
					value="PASS" />&nbsp;房产校验通过&nbsp;&nbsp;&nbsp;&nbsp; <input
					type="radio" name="loanPersonDo.propertyDo.houseStatus" disabled="disabled"
					<c:if test="${loanPersonDo.propertyDo.houseStatus == 'NOPASS'}">checked="checked"</c:if>
					value="NOPASS" />&nbsp;房产校验失败
				</td>
			</tr>
		</table>

		<!-- 联系人信息 -->
		<table
			style="margin-left: 40px; margin-right: 40px; margin-top: 20px; width: 90%"
			border="1">
			<tr style="height: 30px">
				<td colspan="4" style="text-align: left;">&nbsp; 联系人信息</td>
			</tr>
			<tr>
				<td style="width: 100px; height: 30px;" align="left">
					姓名：<input id="ralationName"
					name="loanPersonDo.loanRelationDoList[0].ralationName" readonly="readonly"
					value="${loanPersonDo.loanRelationDoList[0].ralationName}"
					style="height: 20px; line-height: 20px; width: 70%;" type="text" />
				</td>
				<td style="width: 100px; height: 30px;" align="left">
					&nbsp;&nbsp;&nbsp;直系关系： <c:if
						test="${loanPersonDo.loanRelationDoList[0].relationship == '父母'}">父母</c:if>
					<c:if
						test="${loanPersonDo.loanRelationDoList[0].relationship == '兄弟'}">兄弟</c:if>
					<c:if
						test="${loanPersonDo.loanRelationDoList[0].relationship == '姐妹'}">姐妹</c:if>
				</td>
				<td style="width: 100px; height: 30px;" align="left">
					手机号码：<input id="mobile"
					name="loanPersonDo.loanRelationDoList[0].mobile" readonly="readonly"
					value="${loanPersonDo.loanRelationDoList[0].mobile }"
					style="height: 20px; line-height: 20px;" type="text" />
				</td>
			</tr>
			<tr>
				<td style="width: 100px; height: 30px;" align="left">
					姓名：<input id="ralationName"
					name="loanPersonDo.loanRelationDoList[1].ralationName" readonly="readonly"
					value="${loanPersonDo.loanRelationDoList[1].ralationName }"
					style="height: 20px; line-height: 20px; width: 70%;" type="text" />
				</td>
				<td style="width: 100px; height: 30px;" align="left">
					非直系关系： <c:if
						test="${loanPersonDo.loanRelationDoList[1].relationship == '同事'}">同事</c:if>
					<c:if
						test="${loanPersonDo.loanRelationDoList[1].relationship == '朋友'}">朋友</c:if>
					<c:if
						test="${loanPersonDo.loanRelationDoList[1].relationship == '同学'}">同学</c:if>
				</td>
				<td style="width: 100px; height: 30px;" align="left">手机号码：<input
					id="mobile2" name="loanPersonDo.loanRelationDoList[1].mobile"
					readonly="readonly" value="${loanPersonDo.loanRelationDoList[1].mobile }"
					style="height: 20px; line-height: 20px;" type="text" />
				</td>
			</tr>
		</table>
		<table
			style="margin-left: 40px; margin-right: 40px; margin-top: 20px; width: 90%"
			border="1">
			<tr style="height: 30px">
				<td colspan="5" style="text-align: left;">&nbsp; 上传资料</td>
			</tr>
			<tr>
				<c:forEach items="${loanPersonDo.certificateDoList}"
					var="certificateDo">
					<c:set var="count" value="${count+1}" />
					<c:if test="${certificateDo.certificateType ne 'PROTOCOL' }">
						<td align="left">
						<a href="${fileAccessUrl}${certificateDo.filePath }" target="_blank"><img src="${fileAccessUrl}${certificateDo.filePath }" alt=""
							width="100px" /></a></td>
					</c:if>
				</c:forEach>
			</tr>
			</tr>
		</table>

		<table
			style="margin-left: 40px; margin-right: 40px; margin-top: 20px; width: 90%"
			border="1">
			<tr style="height: 30px">
				<td colspan="5" style="text-align: left;">&nbsp; 贷款合同</td>
			</tr>
			<tr>
				<c:forEach items="${loanPersonDo.certificateDoList}"
					var="certificateDo">
					<c:if test="${certificateDo.certificateType eq 'PROTOCOL' }">
						<td align="left" colspan="2">
						<a href="${fileAccessUrl}${certificateDo.filePath }" target="_blank"> 
							<img src="${fileAccessUrl}${certificateDo.filePath }" alt=""
								width="100px" />
						</a>
						</td>
					</c:if>
				</c:forEach>
			</tr>
			</tr>
		</table>
		<table
			style="margin-left: 40px; margin-right: 40px; margin-top: 20px; width: 90%"
			border="1">
			<tr style="height: 30px">
				<td colspan="4" style="text-align: left;">&nbsp; 备注</td>
			</tr>
			<tr>
				<td colspan="4" style="text-align: left;">
					${loanPersonDo.remark }</td>
			</tr>
		</table>
	</form>
</body>
</html>
