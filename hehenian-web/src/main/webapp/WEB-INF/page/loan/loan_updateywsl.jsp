<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>业务受理</title>
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
<script>

	$(function(){
		$("#btnSearch").click(function(){
			var realName = $("#realName").val();
			if(realName == ""){
				alert("请填写真实姓名");
			}else{
				$("#addForm").submit();
			}
		});
	});
	//发布
	function updateReleased() {
		var id = $("#id").val();
		param["paramMap.id"] = id;
		$.shovePost("updateReleased.do", param, function(data) {
			if (data == "1") {
				alert("发布成功")
				window.parent.closeMthod();
			}
			if (data == "2") {
				alert("发布失败");
				return;
			}
		});
	}

	//弹出窗口关闭
	function closeMthodes() {
		window.parent.closeMthod();
	}

	//认证查看
	function queryMsg() {
		var id = '${map.publisher}';
		window.location.href = 'queryPerUserCreditAction.do?userId=' + id;
	}
</script>
</head>
<body>
<form id="addForm" action="loan-add.do" method="post">
		<table style="margin-left: 40px; margin-right: 40px; margin-top: 20px; width: 90%" border="1">
			<tr style="height: 30px">
				<td colspan="4" style="text-align: left;">申请人基本信息</td>
			</tr>
			<input type="hidden" value="${loanPersonDo.loanPersonId}" name="loanPersonDo.loanPersonId" id="loanPersonId" />
        	<input type="hidden" value="${loanPersonDo.loanId}" name="loanPersonDo.loanId" id="loanId" />
				<tr>
				<td style="width: 100px; height: 30px;" align="right"
						class="blue12">
						真实姓名：
					</td>
					<td align="left">
					<input id="realName" name="loanPersonDo.realName" 
							   style="height: 20px;line-height: 20px;" type="text" />
					</td>
					<td style="width: 100px; height: 30px;" align="right"
						class="blue12">
						手机号码：
					</td>
					<td align="left">
					 <input id="mobile" name="loanPersonDo.mobile"
							 style="height: 20px;line-height: 20px;" type="text" />
					</td>
				</tr>
				<tr>
					<td style="width: 100px; height: 30px;" align="right"
						class="blue12" >
						身份证：
					</td>
					<td align="left">
						<input id="cardNo" name="loanPersonDo.cardNo"
							  style="height: 20px;line-height: 20px;" type="text"/>
					</td>
					<td style="width: 100px; height: 30px;" align="right"
						class="blue12" >
						小区名称：
					</td>
					<td align="left" class="f66">
						<input id="areaName" name="loanPersonDo.areaName" class="hhn"
							   style="height: 20px;line-height: 20px;"  type="text"/>
					</td>
				</tr>
				<tr>
					<td style="width: 100px; height: 30px;" align="right"
						class="blue12" >
						年龄：
					</td>
					<td align="left" class="f66">
						<input id="age" name="loanPersonDo.age" class="hhn"
							   style="height: 20px;line-height: 20px;"  type="text"/>
					</td>
					<td style="width: 100px; height: 30px;" align="right"
						class="blue12">
						性别：
					</td>
					<td align="left" class="f66">
						<select
							name="loanPersonDo.sex">
								<option value="">--请选择--</option>
								<option value="MALE"
									<c:if test="${loanPersonDo.sex == 'UNMARRIED'}">selected</c:if>>男</option>
								<option value="FEMALE"
									<c:if test="${loanPersonDo.sex == 'MARRIED'}">selected</c:if>>女</option>
						</select>
					</td>
				</tr>
				<tr>
					<td style="width: 100px; height: 30px;" align="right"
						class="blue12">
						婚姻状况：
					</td>
					<td align="left" class="f66">
						<select
							name="loanPersonDo.marriaged">
								<option value="">--请选择--</option>
								<option value="UNMARRIED"
									<c:if test="${loanPersonDo.marriaged == 'UNMARRIED'}">selected</c:if>>已婚</option>
								<option value="MARRIED"
									<c:if test="${loanPersonDo.marriaged == 'MARRIED'}">selected</c:if>>未婚</option>
								<option value="DIVORCE"
									<c:if test="${loanPersonDo.marriaged == 'DIVORCE'}">selected</c:if>>离异</option>
						</select>
					</td>
						<td style="width: 100px; height: 30px;" align="right"
						class="blue12">
						受教育程度：
					</td>
					<td align="left" class="f66">
						<select
							name="loanPersonDo.education">
								<option value="">--请选择--</option>
								<option value="HIGN_SCHOOL"
									<c:if test="${loanPersonDo.education == 'HIGN_SCHOOL'}">selected</c:if>>高中</option>
								<option value="JUNIOR_COLLEGE"
									<c:if test="${loanPersonDo.education == 'JUNIOR_COLLEGE'}">selected</c:if>>大专</option>
								<option value="BACHELOR"
									<c:if test="${loanPersonDo.education == 'BACHELOR'}">selected</c:if>>本科以上</option>
						</select>
					</td>
				</tr>
				<tr>
					<td style="width: 100px; height: 30px;" align="right"
						class="blue12">
						住宅电话：
					</td>
					<td align="left" class="f66">
						<input id="familyPhone" name="loanPersonDo.familyPhone"  class="hhn"
							  style="height: 20px;line-height: 20px;" type="text"/>
					</td>
					<td style="width: 100px; height: 30px;" align="right"
						class="blue12">
						邮箱：
					</td>
					<td align="left" >
						<input id="email" name="loanPersonDo.email" class="hhn"
							  style="height: 20px;line-height: 20px;" type="text"/>
					</td>
				</tr>
			  <tr>
			  	<td style="width: 100px; height: 30px;" align="right"
						class="blue12">
						借款用途：
					</td>
			  	<td colspan="3" style="text-align: left;">
				<textarea name="loanUsage" id="loanPersonDo.loanDo.loanUsage" cols="50"  rows="3"></textarea>			
		   </td>
			  </tr>
		</table>
		<table style="margin-left: 40px; margin-right: 40px; margin-top: 20px; width: 90%" border="1">
			<tr style="height: 30px">
				<td colspan="4" style="text-align: left;">&nbsp; 职业信息</td>
			</tr>
			<tr>
				<td style="width: 100px; height: 30px;" align="left"
					class="blue12" colspan="4">
					<input type="radio" name="loanPersonDo.jobDo.jobType" value="SELF_EMPLOYED"/>&nbsp;自雇人士&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="radio" name="loanPersonDo.jobDo.jobType" value="SALARYMAN"/>&nbsp;工薪族
				</td>
			</tr>
			<tr>
				<td style="width: 100px; height: 30px;" align="right"
					class="blue12">
					工作单位：
				</td>
				<td align="left" >
					<input id="companyName" name="loanPersonDo.jobDo.companyName" class="hhn"
						  style="height: 20px;line-height: 20px;" type="text"/>
				</td>
					<td style="width: 100px; height: 30px;" align="right"
					class="blue12">
					职位级别：
				</td>
				<td align="left" >
					<input id="position" name="loanPersonDo.jobDo.position" class="hhn"
						  style="height: 20px;line-height: 20px;" type="text"/>
				</td>
			</tr>
			<tr>
				<td style="width: 100px; height: 30px;" align="right"
					class="blue12">
					工作年限：
				</td>
				<td align="left" >
					<input id="jobYear" name="loanPersonDo.jobDo.jobYear" class="hhn"
						  style="height: 20px;line-height: 20px;" type="text"/>
				</td>
					<td style="width: 100px; height: 30px;" align="right"
					class="blue12">
					月收入：
				</td>
				<td align="left" class="f66">
					<input id="jobIncome" name="loanPersonDo.jobDo.jobIncome" class="hhn"
						  style="height: 20px;line-height: 20px;" type="text"/> 
				</td>
			</tr>
			<tr>
				<td style="width: 100px; height: 30px;" align="right"
					class="blue12">
					单位电话：
				</td>
				<td align="left" class="f66" colspan="3">
					<input id="companyPhone" name="loanPersonDo.jobDo.companyPhone" class="hhn"
						  style="height: 20px;line-height: 20px;" type="text"/>
				</td>
			</tr>
		</table>
		<table style="margin-left: 40px; margin-right: 40px; margin-top: 20px; width: 90%" border="1">
			<tr style="height: 30px">
				<td colspan="4" style="text-align: left;">&nbsp; 个人资产信息</td>
			</tr>
			<tr>
				<td style="width: 100px; height: 30px;" align="right"
					class="blue12">
					房产地址：
				</td>
				<td align="left" class="f66" colspan="3">
					<input id="houseAddress" name="loanPersonDo。propertyDo.houseAddress" class="hhn"
						  style="height: 20px;line-height: 20px;width: 80%;" type="text"/>
				</td>
			</tr>
			<tr>
				<td style="width: 100px; height: 30px;" align="right"
					class="blue12">
					购买时间：
				</td>
				<td align="left" class="f66">
					<fmt:formatDate value="${loanPersonDo.propertyDo.purchaseDate}" pattern="yyyy-MM-dd" var="formatDay"/>
					<input id="ordDate" name="loanPersonDo.propertyDo.purchaseDate" value="${formatDay}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})" class="hhn"/>
				</td>
					<td style="width: 100px; height: 30px;" align="right"
					class="blue12">
					购买方式：
				</td>
				<td align="left" class="f66">
					<c:set var="purchaseWay" value="${loanPersonDo。propertyDo.purchaseWay }"></c:set> 
					<select name="loanPersonDo。propertyDo.purchaseWay" style="width:90px">
						<option value="">--请选择--</option>
						<option value="NOMORTGAGE"
							<c:if test="${purchaseWay == 'NOMORTGAGE'}">selected</c:if>>一次性</option>
						<option value="MORTGAGE"
							<c:if test="${purchaseWay == 'MORTGAGE'}">selected</c:if>>按揭</option>
					</select>
				</td>
			</tr>
			<tr>
				<td style="width: 100px; height: 30px;" align="right"
					class="blue12">
					车牌号码：
				</td>
				<td align="left" class="f66">
					<input id="carNo" name="loanPersonDo。propertyDo.carNo" class="hhn"
						  style="height: 20px;line-height: 20px;" type="text"/>
				</td>
					<td style="width: 100px; height: 30px;" align="right"
					class="blue12">
					车辆品牌型号：
				</td>
				<td align="left" class="f66">
					<input id="carBrand" name="loanPersonDo。propertyDo.carBrand" class="hhn"
						  style="height: 20px;line-height: 20px;" type="text"/>
				</td>
			</tr>
			<tr>
			<td style="width: 100px; height: 30px;" align="right"
					class="blue12">
					房产信息校验：
				</td>
			<td colspan="3" style="text-align: left;">
				<input type="radio" name="loanPersonDo.jobDo.jobType" value="PASS"/>&nbsp;审核通过&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="radio" name="loanPersonDo.jobDo.jobType" value="NOPASS"/>&nbsp;审核失败
		   </td>
			</tr>
		</table>
		
		<!-- 联系人信息 -->
		<table style="margin-left: 40px; margin-right: 40px; margin-top: 20px; width: 90%" border="1">
			<tr style="height: 30px">
				<td colspan="4" style="text-align: left;">&nbsp; 联系人信息 </td>
			</tr>
			<tr>
				<td style="width: 100px; height: 30px;" align="left"
					class="blue12">
					姓名：<input id="ralationName" name="ralationName1" class="hhn"
						  style="height: 20px;line-height: 20px;width: 70%;" type="text"/>
				</td>
				<td style="width: 100px; height: 30px;" align="left"
					class="blue12">
					&nbsp;&nbsp;&nbsp;直系关系：<select
							name="relationship1" style="width:60px">
								<option value="">--请选择--</option>
								<option value="HIGN_SCHOOL"
									<c:if test="${loanPersonDo.education == 'HIGN_SCHOOL'}">selected</c:if>>父母</option>
								<option value="JUNIOR_COLLEGE"
									<c:if test="${loanPersonDo.education == 'JUNIOR_COLLEGE'}">selected</c:if>>兄弟</option>
								<option value="BACHELOR"
									<c:if test="${loanPersonDo.education == 'BACHELOR'}">selected</c:if>>姐妹</option>
								<option value="BACHELOR"
									<c:if test="${loanPersonDo.education == 'BACHELOR'}">selected</c:if>>子女</option>
						</select>
				</td>
					<td style="width: 100px; height: 30px;" align="left"
					class="blue12">
					手机号码：<input id="mobile" name="mobile1" class="hhn"
						  style="height: 20px;line-height: 20px;" type="text"/>
				</td>
			</tr>
			<tr>
				<td style="width: 100px; height: 30px;" align="left"
					class="blue12">
					姓名：<input id="ralationName" name="ralationName2" class="hhn"
						  style="height: 20px;line-height: 20px;width: 70%;" type="text"/>
				</td>
				<td style="width: 100px; height: 30px;" align="left"
					class="blue12">非直系关系：<select name="relationship2" style="width:60px">
								<option value="">--请选择--</option>
								<option value="HIGN_SCHOOL"
									<c:if test="${loanPersonDo.education == 'HIGN_SCHOOL'}">selected</c:if>>同事</option>
								<option value="JUNIOR_COLLEGE"
									<c:if test="${loanPersonDo.education == 'JUNIOR_COLLEGE'}">selected</c:if>>朋友</option>
						</select>
				</td>
					<td style="width: 100px; height: 30px;" align="left"
					class="blue12">
					手机号码：<input id="mobile2" name="mobile2" class="hhn"
						  style="height: 20px;line-height: 20px;" type="text"/>
				</td>
			</tr>
		</table>
		
		<table style="margin-left: 40px; margin-right: 40px; margin-top: 20px; width: 90%" border="1">
			<tr style="height: 30px">
				<td colspan="4" style="text-align: left;">&nbsp; 备注</td>
			</tr>
			<tr>
			<td colspan="4" style="text-align: left;">
				<textarea name="remark" id="loanPersonDo.remark" cols="60"  rows="4"></textarea>			
		   </td>
			</tr>
		</table>
</form>
</body>
</html>


