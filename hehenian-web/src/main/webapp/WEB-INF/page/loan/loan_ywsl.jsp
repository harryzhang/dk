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
	width: 80%;
}
</style>
<meta http-equiv="description" content="This is my page" />
<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
<link href="../css/css.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
<script type="text/javascript" src="../script/uploadPreview.min.js"></script>
<script type="text/javascript" src="../common/dialog/popup.js"></script>
<script language="javascript" type="text/javascript" src="../script/jbox/jquery.jBox-2.3.min.js"></script>
<script language="javascript" type="text/javascript" src="../My97DatePicker/WdatePicker.js"></script>
<script>
	$(function(){
		$("#btnSearch").click(function(){
			var jobYear = $("#jobYear").val();
			var purchaseWay = $("#purchaseWay").val();
			var jobType = $("input[name='loanPersonDo.jobDo.jobType']:checked").val();
			var houseStatus = $("input[name='loanPersonDo.propertyDo.houseStatus']:checked").val();
			if($("#age").val() == '') {
				alert("请录入年龄");
				return;
			}
			if($('#sex').val() == '') {
				alert("请录入性别");
				return;
			}
			if($('#marriaged').val() == '') {
				alert("请录入婚姻状况");
				return;
			}
			if($('#education').val() == '') {
				alert("请录入受教育程度");
				return;
			}
			if($('#familyPhone').val() == '') {
				alert("请录入住宅电话");
				return;
			}
			if($("#email").val() == '') {
				alert("请录入邮箱");
				return;
			}
			if($("#loanUsage").val()== '') {
				alert("请录入借款用途");
				return;
			}if(jobType === undefined) {
				alert("请录入工作类型");
				return;
			}
			if($("#companyName").val()==''){
				alert("请录入工作单位");
				return;
			}if($("#position").val()=='') {
				alert("请录入职位信息");
				return;
			}
			if(jobYear == "") {
				alert("请录入工作年限");
				return;
			}
			if($("#jobIncome").val()== '') {
				alert("请录入月收入");
				return;
			}
			if($("#companyPhone").val()=='') {
				alert("请录入单位电话");
				return;
			}
			if($("#houseAddress").val()=='') {
				alert("请录入房产地址");
				return;
			}if(purchaseWay == '') {
				alert("请录入购买方式");
				return;
			}if($("#purchaseDate").val()=='') {
				alert("请录入购买时间");
				return;
			}if(houseStatus === undefined) {
				alert("请选择房产校验");
				return;
			}if($("#ralationName1").val()=='') {
				alert("请录入联系人姓名");
				return;
			}if($("#relationship1").val()=='') {
				alert("请录入联系人直系关系");
				return;
			}if($("#mobile1").val()=='') {
				alert("请录入联系人手机号码");
				return;
			}if($("#ralationName2").val()=='') {
				alert("请录入联系人姓名");
				return;
			}if($("#relationship2").val()=='') {
				alert("请录入联系人直系关系");
				return;
			}if($("#mobile2").val()=='') {
				alert("请录入联系人手机号码");
				return;
			}if($("#file1").val() == '') {
				alert("请录入身份证正面");
				return;
			}if($("#file2").val() == '') {
				alert("请录入身份证反面");
				return;
			}if($("#file3").val() == '') {
				alert("请录入房产证明");
				return;
			}if($("#file4").val() == '') {
				alert("请录入工作流水");
				return;
			}if($("#file5").val() == '') {
				alert("请录入工作流水");
				return;
			}
			else{
				$("#btnSearch").attr("disable",true);
				window.parent.$.jBox.tip("<div class='data-submit'>提交数据中，请稍候...</div>","loading");
				$("#addForm").submit();
			}
		});
		
		$("#onSend").click(function(){
			var jobYear = $("#jobYear").val();
			var purchaseWay = $("#purchaseWay").val();
			var jobType = $("input[name='loanPersonDo.jobDo.jobType']:checked").val();
			var houseStatus = $("input[name='loanPersonDo.propertyDo.houseStatus']:checked").val();
			if($("#age").val() == '') {
				alert("请录入年龄");
				return;
			}
			if($('#sex').val() == '') {
				alert("请录入性别");
				return;
			}
			if($('#marriaged').val() == '') {
				alert("请录入婚姻状况");
				return;
			}
			if($('#education').val() == '') {
				alert("请录入受教育程度");
				return;
			}
			if($('#familyPhone').val() == '') {
				alert("请录入住宅电话");
				return;
			}
			if($("#email").val() == '') {
				alert("请录入邮箱");
				return;
			}
			if($("#loanUsage").val()== '') {
				alert("请录入借款用途");
				return;
			}if(jobType === undefined) {
				alert("请录入工作类型");
				return;
			}
			if($("#companyName").val()==''){
				alert("请录入工作单位");
				return;
			}if($("#position").val()=='') {
				alert("请录入职位信息");
				return;
			}
			if(jobYear == "") {
				alert("请录入工作年限");
				return;
			}
			if($("#jobIncome").val()== '') {
				alert("请录入月收入");
				return;
			}
			if($("#companyPhone").val()=='') {
				alert("请录入单位电话");
				return;
			}
			if($("#houseAddress").val()=='') {
				alert("请录入房产地址");
				return;
			}if(purchaseWay == '') {
				alert("请录入购买方式");
				return;
			}if($("#purchaseDate").val()=='') {
				alert("请录入购买时间");
				return;
			}if(houseStatus === undefined) {
				alert("请选择房产校验");
				return;
			}if($("#ralationName1").val()=='') {
				alert("请录入联系人姓名");
				return;
			}if($("#relationship1").val()=='') {
				alert("请录入联系人直系关系");
				return;
			}if($("#mobile1").val()=='') {
				alert("请录入联系人手机号码");
				return;
			}if($("#ralationName2").val()=='') {
				alert("请录入联系人姓名");
				return;
			}if($("#relationship2").val()=='') {
				alert("请录入联系人直系关系");
				return;
			}if($("#mobile2").val()=='') {
				alert("请录入联系人手机号码");
				return;
			}if($("#file1").val() == '') {
				alert("请录入身份证正面");
				return;
			}if($("#file2").val() == '') {
				alert("请录入身份证反面");
				return;
			}if($("#file3").val() == '') {
				alert("请录入房产证明");
				return;
			}if($("#file4").val() == '') {
				alert("请录入工作流水");
				return;
			}if($("#file5").val() == '') {
				alert("请录入工作流水");
				return;
			}else {
				$("#btnSearch").attr("disable",true);
				window.parent.$.jBox.tip("<div class='data-submit'>提交数据中，请稍候...</div>","loading");
				$("#addForm").get(0).action = "loanUpdate.do?loanPersonDo.loanDo.loanStatus=PROCESSING";
				$("#addForm").submit();
			}
		});
		
		if($("input[name='loanPersonDo.jobDo.jobType']:checked").val() =="SELF_EMPLOYED") {
			$("#mon").text('月营业额：');
			$("#sj").text('营业时间：');
		}
		if($("input[name='loanPersonDo.jobDo.jobType']:checked").val()=="SALARYMAN") {
			$("#mon").text('月收入：');
			$("#sj").text('工作年限：');
		}
		for(var i=0; i < 5; i++) {
			var flag = 'file'+(i+1);
			if($('#file'+(i+1)).val().length > 0) {
				flag = 'fil'+(i+1);
			}
			new uploadPreview({ UpBtn: flag, DivShow: 'imgdiv'+(i+1), ImgShow: 'img'+(i+1) });
		}
		
		$("input[name='files']").bind("change", function() { 
			var id =this.id;
			id =id.substring(3);
			$("#filePath"+id).val('');
		}); 
	});

	function changStatus() {
		if($("input[name='loanPersonDo.jobDo.jobType']:checked").val() =="SALARYMAN") {
			$("#mon").text('月收入：');
			$("#sj").text('工作年限：');
		}else {
			$("#mon").text('月营业额：');
			$("#sj").text('营业时间：');
		}
	}
	
	//弹出窗口关闭
	function closeMthodes() {
		window.parent.closeMthod();
	}
	var c_window = '${c_window}';
	if(c_window == 'yes') {
		alert('${message}');
		closeMthodes();
	}
	
	//认证查看
	function queryMsg() {
		var id = '${map.publisher}';
		window.location.href = 'queryPerUserCreditAction.do?userId=' + id;
	}
	
	function changeSelectValue() {
		alert(this.value);
	}
	function changeValue(obj,id) {
		$("#"+id).val('');
	}
	function validatorData(obj,id) {
		var reg = new RegExp("^[0-9]*$");  
		if(!reg.test(obj.value)){  
			alert("请输入数字!");
			obj.value = ""; 
		}
		var val = $("#"+id);
		if(val.get(0) != null){
			if(val.val().length > 0) {
				if(val.val() < 23 || val.val() > 60) {
					alert("年龄必须在23-60");
					$("#"+id).val('');
				}
			}
		}
	}
	/**
	 * 校验email
	 * @param string 被校验的字符串
	 */
	function checkEmail(obj) {
	    if(!(/^\w+([-.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/).test(obj.value)){
	    	alert("请输入正确邮箱地址！");
	    	obj.value="";
	    }
	}
	/**
	 * 校验手机
	 * @param string 被校验的字符串
	 */
	function checkMobile(obj) {
	     if(!(/^1[\d]{10}$/).test(obj.value)) {
	    	 alert("请录入正确的手机号码！");
	    	 obj.value="";
	     }
	}
</script>
</head>
<body>
<form id="addForm" action="loanUpdate.do" method="post" enctype="multipart/form-data">
		<div align="right">
			<input type="button" style="background: #666666;" value="保存" id="btnSearch" />
			<input type="button" style="background: #666666;" title="提交后将不能修改，请确认无误！" value="提交" id="onSend" /> 
			<input type="button" style="background: #666666;"
								value="返回" onclick="closeMthodes();" />
		</div>
		<div align="center">${message}</div>
		<table style="margin-left: 40px; margin-right: 40px; margin-top: 20px; width: 90%" border="1">
			<tr style="height: 30px">
				<td colspan="4" style="text-align: left;">申请人基本信息</td>
			</tr>
			<input type="hidden" value="${loanPersonDo.loanPersonId}" name="loanPersonDo.loanPersonId"  />
        	<input type="hidden" value="${loanPersonDo.loanDo.loanId}" name="loanPersonDo.loanId"/>
        	<input type="hidden" value="${loanPersonDo.loanDo.loanId}" name="loanPersonDo.loanDo.loanId" />
        	<input type="hidden" id="jobId" value="${loanPersonDo.jobDo.jobId}" name="loanPersonDo.jobDo.jobId" />
        	<input type="hidden" value="${loanPersonDo.propertyDo.propertyId}" name="loanPersonDo.propertyDo.propertyId" />
       		<input type="hidden" value="${loanPersonDo.loanRelationDoList[0].ralationId}" name="loanPersonDo.loanRelationDoList[0].ralationId" />
        	<input type="hidden" value="${loanPersonDo.loanRelationDoList[1].ralationId}" name="loanPersonDo.loanRelationDoList[1].ralationId" />
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
						<input id="idNo" name="loanPersonDo.idNo" value="${loanPersonDo.idNo }"
							  style="height: 20px;line-height: 20px;" type="text" readonly="readonly"/>
					</td>
					<td style="width: 100px; height: 30px;" align="right"
						class="blue12" >
						小区名称：
					</td>
					<td align="left" class="f66">
						<input id="cname" name="loanPersonDo.cname" value="${loanPersonDo.cname }"  readonly="readonly"
							   style="height: 20px;line-height: 20px;"  type="text"/>
					</td>
				</tr>
				<tr>
					<td style="width: 100px; height: 30px;" align="right"
						class="blue12" >
						年龄：
					</td>
					<td align="left" class="f66">
						<input id="age" name="loanPersonDo.age" class="hhn" value="${loanPersonDo.age }"
							   style="height: 20px;line-height: 20px;"  type="text" onchange="validatorData(this,'age')"/><b>*</b>
					</td>
					<td style="width: 100px; height: 30px;" align="right"
						class="blue12">
						性别：
					</td>
					<td align="left" class="f66">
						<select id="sex"
							name="loanPersonDo.sex">
								<option value="">--请选择--</option>
								<option value="MALE"
									<c:if test="${loanPersonDo.sex == 'MALE'}">selected</c:if>>男</option>
								<option value="FEMALE"
									<c:if test="${loanPersonDo.sex == 'FEMALE'}">selected</c:if>>女</option>
						</select><b>*</b>
					</td>
				</tr>
				<tr>
					<td style="width: 100px; height: 30px;" align="right"
						class="blue12">
						婚姻状况：
					</td>
					<td align="left" class="f66">
						<select id="marriaged"
							name="loanPersonDo.marriaged">
								<option value="">--请选择--</option>
								<option value="UNMARRIED"
									<c:if test="${loanPersonDo.marriaged == 'UNMARRIED'}">selected</c:if>>已婚</option>
								<option value="MARRIED"
									<c:if test="${loanPersonDo.marriaged == 'MARRIED'}">selected</c:if>>未婚</option>
								<option value="DIVORCE"
									<c:if test="${loanPersonDo.marriaged == 'DIVORCE'}">selected</c:if>>离异</option>
						</select><b>*</b>
					</td>
						<td style="width: 100px; height: 30px;" align="right"
						class="blue12">
						受教育程度：
					</td>
					<td align="left" class="f66">
						<select id="education"
							name="loanPersonDo.education">
								<option value="">--请选择--</option>
								<option value="GRADE_SCHOOL"
									<c:if test="${loanPersonDo.education == 'GRADE_SCHOOL'}">selected</c:if>>初中及以下</option>
								<option value="HIGN_SCHOOL"
									<c:if test="${loanPersonDo.education == 'HIGN_SCHOOL'}">selected</c:if>>高中</option>
								<option value="JUNIOR_COLLEGE"
									<c:if test="${loanPersonDo.education == 'JUNIOR_COLLEGE'}">selected</c:if>>大专</option>
								<option value="BACHELOR"
									<c:if test="${loanPersonDo.education == 'BACHELOR'}">selected</c:if>>本科以上</option>
						</select><b>*</b>
					</td>
				</tr>
				<tr>
					<td style="width: 100px; height: 30px;" align="right"
						class="blue12">
						住宅电话：
					</td>
					<td align="left" class="f66">
						<input id="familyPhone" name="loanPersonDo.familyPhone"  class="hhn" value="${loanPersonDo.familyPhone }"
							  style="height: 20px;line-height: 20px;" type="text" onchange="validatorData(this)"/><b>*</b>
					</td>
					<td style="width: 100px; height: 30px;" align="right"
						class="blue12">
						邮箱：
					</td>
					<td align="left" >
						<input id="email" name="loanPersonDo.email" class="hhn" value="${loanPersonDo.email }"
							  style="height: 20px;line-height: 20px;" type="text" onchange="checkEmail(this)"/><b>*</b>
					</td>
				</tr>
			  <tr>
			  	<td style="width: 100px; height: 30px;" align="right"
						class="blue12">
						借款用途：
					</td>
			  	<td colspan="3" style="text-align: left;">
				<textarea name="loanPersonDo.loanDo.loanUsage" id="loanUsage" cols="50"  rows="3">${loanPersonDo.loanDo.loanUsage }</textarea>&nbsp;&nbsp;<b>*</b>	
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
					<input type="radio" name="loanPersonDo.jobDo.jobType" onchange="changStatus()" <c:if test="${loanPersonDo.jobDo.jobType == 'SELF_EMPLOYED'}">checked="checked"</c:if> value="SELF_EMPLOYED"/>&nbsp;自雇人士&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="radio" name="loanPersonDo.jobDo.jobType" onchange="changStatus()" <c:if test="${loanPersonDo.jobDo.jobType == 'SALARYMAN'}">checked="checked"</c:if> value="SALARYMAN"/>&nbsp;受薪人士&nbsp;<b>*</b>
				</td>
			</tr>
			<tr>
				<td style="width: 100px; height: 30px;" align="right"
					class="blue12">
					工作单位：
				</td>
				<td align="left" >
					<input id="companyName" name="loanPersonDo.jobDo.companyName" class="hhn" value="${loanPersonDo.jobDo.companyName}"
						  style="height: 20px;line-height: 20px;" type="text"/><b>*</b>
				</td>
					<td style="width: 100px; height: 30px;" align="right"
					class="blue12">
					职位级别：
				</td>
				<td align="left">
					<input id="position" name="loanPersonDo.jobDo.position" class="hhn" value="${loanPersonDo.jobDo.position}"
						  style="height: 20px;line-height: 20px;" type="text"/><b>*</b>
				</td>
			</tr>
			<tr>
				<td style="width: 100px; height: 30px;" align="right"
					class="blue12" id="sj">
					营业时间：
				</td>
				<td align="left" >
					<input id="jobYear" name="loanPersonDo.jobDo.jobYear" class="hhn" value="${loanPersonDo.jobDo.jobYear }"
						  style="height: 20px;line-height: 20px;" type="text" onchange="validatorData(this)"/><b>*</b>
				</td>
					<td style="width: 100px; height: 30px;" align="right"
					class="blue12" id="mon">
					月营业额：
				</td>
				<td align="left" >
					<input id="jobIncome" name="loanPersonDo.jobDo.jobIncome" class="hhn" value="${loanPersonDo.jobDo.jobIncome}"
						  style="height: 20px;line-height: 20px;" type="text" onchange="validatorData(this)"/><b>*</b>
				</td>
			</tr>
			<tr>
				<td style="width: 100px; height: 30px;" align="right"
					class="blue12">
					单位电话：
				</td>
				<td align="left"  colspan="3">
					<input id="companyPhone" name="loanPersonDo.jobDo.companyPhone" class="hhn" value="${loanPersonDo.jobDo.companyPhone}"
						  style="height: 20px;line-height: 20px;" type="text" onchange="validatorData(this)"/><b>*</b>
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
					<input id="houseAddress" name="loanPersonDo.propertyDo.houseAddress" class="hhn" placeholder="示例：碧水龙庭1栋508房"
						  style="height: 20px;line-height: 20px;width: 80%;" type="text" value="${loanPersonDo.propertyDo.houseAddress}"/>&nbsp;&nbsp;<b>*</b>	
				</td>
			</tr>
			<tr>
				<td style="width: 100px; height: 30px;" align="right"
					class="blue12">
					购买时间：
				</td>
				<td align="left" >
					<fmt:formatDate value="${loanPersonDo.propertyDo.purchaseDate}" pattern="yyyy-MM-dd" var="formatDay"/>
					<input id="purchaseDate" name="loanPersonDo.propertyDo.purchaseDate" value="${formatDay}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})" class="hhn"/>&nbsp;&nbsp;<b>*</b>	
				</td>
					<td style="width: 100px; height: 30px;" align="right"
					class="blue12">
					购买方式：
				</td>
				<td align="left" class="f66">
					<select name="loanPersonDo.propertyDo.purchaseWay" id="purchaseWay" style="width:90px">
						<option value="">--请选择--</option>
						<option <c:if test="${loanPersonDo.propertyDo.purchaseWay == 'NOMORTGAGE'}">selected</c:if> value="NOMORTGAGE">一次性</option>
						<option <c:if test="${loanPersonDo.propertyDo.purchaseWay == 'MORTGAGE'}">selected</c:if> value="MORTGAGE">按揭</option>
					</select><b>*</b>
				</td>
			</tr>
			<tr>
				<td style="width: 100px; height: 30px;" align="right"
					class="blue12">
					车牌号码：
				</td>
				<td align="left" class="f66">
					<input id="carNo" name="loanPersonDo.propertyDo.carNo" class="hhn"  value="${loanPersonDo.propertyDo.carNo}"
						  style="height: 20px;line-height: 20px;" type="text"/>
				</td>
					<td style="width: 100px; height: 30px;" align="right"
					class="blue12">
					车辆品牌型号：
				</td>
				<td align="left" class="f66">
					<input id="carBrand" name="loanPersonDo.propertyDo.carBrand" class="hhn" value="${loanPersonDo.propertyDo.carBrand}"
						  style="height: 20px;line-height: 20px;" type="text"/>
				</td>
			</tr>
			<tr>
				<td style="width: 100px; height: 30px;" align="left"
					class="blue12" colspan="4">
					<input type="radio" name="loanPersonDo.propertyDo.houseStatus" <c:if test="${loanPersonDo.propertyDo.houseStatus == 'PASS'}">checked="checked"</c:if> value="PASS"/>&nbsp;房产校验通过&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="radio" name="loanPersonDo.propertyDo.houseStatus" <c:if test="${loanPersonDo.propertyDo.houseStatus == 'NOPASS'}">checked="checked"</c:if> value="NOPASS"/>&nbsp;房产校验失败<b>*</b>
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
					姓名：<input id="ralationName1" name="loanPersonDo.loanRelationDoList[0].ralationName" value="${loanPersonDo.loanRelationDoList[0].ralationName}"
						  style="height: 20px;line-height: 20px;width: 65%;border: 1px #ccc solid;" type="text"/><b>*</b>
				</td>
				<td style="width: 110px; height: 30px;" align="left"
					class="blue12">
					&nbsp;&nbsp;&nbsp;直系关系：<select id="relationship1"
							name="loanPersonDo.loanRelationDoList[0].relationship" style="width:60px">
								<option value="">--请选择--</option>
								<option value="父母"
									<c:if test="${loanPersonDo.loanRelationDoList[0].relationship == '父母'}">selected</c:if>>父母</option>
								<option value="兄弟"
									<c:if test="${loanPersonDo.loanRelationDoList[0].relationship == '兄弟'}">selected</c:if>>兄弟</option>
								<option value="姐妹"
									<c:if test="${loanPersonDo.loanRelationDoList[0].relationship == '姐妹'}">selected</c:if>>姐妹</option>
						</select><b>*</b>
				</td>
					<td style="width: 120px; height: 30px;" align="left"
					class="blue12">
					手机号码：<input id="mobile" name="loanPersonDo.loanRelationDoList[0].mobile"  value="${loanPersonDo.loanRelationDoList[0].mobile }"
						  style="height: 20px;line-height: 20px;border: 1px #ccc solid;width: 60%;" type="text" onchange="checkMobile(this)"/><b>*</b>
				</td>
			</tr>
			<tr>
				<td style="width: 100px; height: 30px;" align="left"
					class="blue12">
					姓名：<input id="ralationName2" name="loanPersonDo.loanRelationDoList[1].ralationName"  value="${loanPersonDo.loanRelationDoList[1].ralationName }"
						  style="height: 20px;line-height: 20px;width: 65%;border: 1px #ccc solid;" type="text"/><b>*</b>
				</td>
				<td style="width: 110px; height: 30px;" align="left"
					class="blue12">
					非直系关系：<select id="relationship2" name="loanPersonDo.loanRelationDoList[1].relationship" style="width:60px">
								<option value="">--请选择--</option>
								<option value="朋友"
									<c:if test="${loanPersonDo.loanRelationDoList[1].relationship == '朋友'}">selected</c:if>>朋友</option>
								<option value="同事"
									<c:if test="${loanPersonDo.loanRelationDoList[1].relationship == '同事'}">selected</c:if>>同事</option>
								<option value="同学"
									<c:if test="${loanPersonDo.loanRelationDoList[1].relationship == '同学'}">selected</c:if>>同学</option>
						</select><b>*</b>
				</td>
					<td style="width: 120px;height: 30px;" align="left"
					class="blue12">
					手机号码：<input id="mobile2" name="loanPersonDo.loanRelationDoList[1].mobile" value="${loanPersonDo.loanRelationDoList[1].mobile }"
						  style="height: 20px;line-height: 20px;border: 1px #ccc solid;width: 60%;" type="text" onchange="checkMobile(this)"/><b>*</b>
				</td>
			</tr>
		</table> 
		<table style="margin-left: 40px; margin-right: 40px; margin-top: 20px; width: 90%" border="1">
			<tr style="height: 30px">
				<td colspan="5" style="text-align: left;">&nbsp; 上传资料</td>
			</tr>
				<c:choose>
					<c:when test="${empty loanPersonDo.certificateDoList}">
						<tr align="center">
							<td style="width: 100px; height: 30px;"
								class="blue12">
								<div id="imgdiv1">
									<input type="file" id="file1" name="files" style="display: none;" value="IDCARD" onchange="" />
									<img src="../images/loan/ywsl.png" id="img1" onclick="file1.click()" style="width:80px;height:80px"/></br>
									身份证正面
								</div>
							</td>
							<td align="center" class="f66">
								<div id="imgdiv2">
									<input type="file" name="files" id="file2" style="display: none;" value="IDCARD" />
									<img src="../images/loan/ywsl.png" id="img2" onclick="file2.click()" style="width:80px;height:80px"/>
									</br>
									身份证反面
								</div>
							</td>
								<td style="width: 100px; height: 30px;" 
								class="blue12">
								<div id="imgdiv3">
									<input type="file" name="files" id="file3" style="display: none;" value="HOUSE" />
									<img src="../images/loan/ywsl.png" id="img3" onclick="file3.click()" style="width:80px;height:80px"/></br>
									房产证明
								</div>
							</td>
							<td align="center" class="f66">
								<div id="imgdiv4">
									<input type="file" name="files" id="file4" style="display: none;" value="JOB" />
									<img src="../images/loan/ywsl.png" id="img4" onclick="file4.click()" style="width:80px;height:80px"/></br>
									工作流水
								</div>
							</td>
							<td td align="center" class="f66">
							  <div id="imgdiv5">
								<input type="file" name="files" id="file5" style="display: none;" value="INCOME"  />
								<img src="../images/loan/ywsl.png" alt="" id="img5" onclick="file5.click()" style="width:80px;height:80px"/></br>
								收入流水
							  </div>
							</td>
						</tr>
					</c:when>
					<c:otherwise>
						<tr >
							<c:forEach items="${loanPersonDo.certificateDoList}" var="certificateDo">
								<c:set var="count" value="${count+1}" />
								<td>
									<div id="imgdiv${count }">
										<img src="${fileAccessUrl}${certificateDo.filePath }" style="width:100px;height:80px" id="img${count }" />
										<input type="hidden" value="${certificateDo.certificateId}" name="loanPersonDo.certificateDoList[${count-1 }].certificateId" />
										<input type="hidden" value="${certificateDo.loanId}" name="loanPersonDo.certificateDoList[${count-1 }].loanId" />
										<input type="hidden" value="${certificateDo.loanPersonId}" name="loanPersonDo.certificateDoList[${count-1 }].loanPersonId" />
										<input type="hidden" value="${certificateDo.certificateType}" name="loanPersonDo.certificateDoList[${count-1 }].certificateType" />
										<input type="hidden" value="${certificateDo.certificateName}" name="loanPersonDo.certificateDoList[${count-1 }].certificateName" />
										<input type="hidden" id="file${count}"  value="${certificateDo.certificateType}"/>
										<input type="hidden" value="${certificateDo.fileType }" name="loanPersonDo.certificateDoList[${count-1 }].fileType"/>
										<input id="filePath${count }" name="loanPersonDo.certificateDoList[${count-1 }].filePath" value="${certificateDo.filePath}" type="hidden"/>
										<input type="file" name="files" id="fil${count}" style="display: none;"/>
										<input type="button"  onclick="fil${count}.click()"  value="重新上传"/></br>
									</div>
								</td>
							</c:forEach>
						</tr>
				</c:otherwise>
				</c:choose>
			</tr>
		</table>
		<table style="margin-left: 40px; margin-right: 40px; margin-top: 20px; width: 90%" border="1">
			<tr style="height: 30px">
				<td colspan="4" style="text-align: left;">&nbsp; 备注</td>
			</tr>
			<tr>
			<td colspan="4" style="text-align: left;">
				<textarea name="loanPersonDo.remark" id="remark" cols="60" placeholder="请详细说明亲见客户的情况" rows="4">${loanPersonDo.remark }</textarea>			
		   </td>
			</tr>
		</table>
</form>
</body>
</html>
