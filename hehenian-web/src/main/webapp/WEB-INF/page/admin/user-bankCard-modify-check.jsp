<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<title>帮助中心-内容维护-修改</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="This is my page" />
		<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
		
		<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
		<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
		<script type="text/javascript">
			
<%--			$(function(){--%>
<%--				//提交表单--%>
<%--				$("#btn_save").click(function(){--%>
<%--					$(this).hide();--%>
<%--					$("#updateUserModifyBank").submit();--%>
<%--					return false;--%>
<%--				});--%>
<%--			});--%>


			param = {};
			$(function() {
				$("#remark").focus(function() {
					$("#hhhhn").html("");
				});
				//提交表单
				$("#btn_save").click(function() {
					if ($("#remark").val() == '') {
						$("#hhhhn").html("审核意见不能为空");
						return;
					}

					//$(this).hide();
					param["paramMap.bankId"] = $("#tb_id").val();
					param["paramMap.userName"] = $("#checkUser").val();
					param["paramMap.status"] = $("input[name='paramMap.status']:checked").val();
					param["paramMap.remark"] = $("#remark").val();
					param["paramMap.usrCustId"] = $("#tb_usrCustId").val();
					param["paramMap.openBankId"] = $("#tb_openBankId").val();
					param["paramMap.openBranchName"] = $("#tb_modifiedBranchBankName").val();
					param["paramMap.provinceId"] = $("#tb_provinceId").val();
					param["paramMap.cityId"] = $("#tb_cityId").val();
					param["paramMap.openAcctId"] = $("#tb_modifiedCardNo").val();
					param["paramMap.modifiedBankName"] = $("#tb_modifiedBankName").val();
					
					$.shovePost("updateUserModifyBank.do", param, function(data) {
						if (data == "操作成功")
							alert("操作成功");
						else  {
							alert(data);
						}
							window.location.href = "queryUserBankInit.do";
					});
				});
			});
			
		</script>
		
	</head>
	<body>
		<!--  <form id="updateHelp" name="updateHelp" action="updateHelp.do" method="post"> -->
<%--		<form id="updateUserModifyBank" name="updateUserModifyBank" action="updateUserModifyBank.do" method="post">--%>
<%--<form>--%>
			<div id="right" >
				<div style="padding: 15px 10px 0px 10px;">
					<div>
						<table border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="120" height="28" class="xxk_all_a">
								<a href="queryUserBankInit.do">银行卡管理</a>
								</td>
								<td width="2">
									&nbsp;
								</td>
								<td width="120" class="main_alll_h2">
									<a href="queryModifyBankInit.do">银行卡变更</a>
								</td>
								<td width="2">
									&nbsp;
								</td>
								<td width="2">
									&nbsp;
								</td>
								<td>
									&nbsp;
								</td>
							 </tr>
						</table>
					</div>
					 
					<div style="width: auto; background-color: #FFF; padding: 10px;" align="center">
						<table width="700" border="0" cellspacing="1" cellpadding="3">
							<tr>
								<td style="width: 120px; height: 25px;" align="right"
									class="blue12">
									用户名：
								</td>
								<td align="left" class="f66">
									<s:textfield id="tb_username" name="paramMap.username"
										cssClass="in_text_2" cssStyle="width: 150px" theme="simple" readonly="true"></s:textfield>
									<s:hidden id="provinceId" name="paramMap.provinceId"></s:hidden>
									<s:hidden id="cityId" name="paramMap.cityId"></s:hidden>
									</td>
							</tr>
							<tr>
								<td style="width: 120px; height: 25px;" align="right" class="blue12">
									真实姓名：
								</td>
								<td align="left" class="f66">
								<s:textfield id="tb_realName" name="paramMap.realName"
										cssClass="in_text_2" cssStyle="width: 150px" theme="simple" readonly="true"></s:textfield>
									<s:hidden name="paramMap.usrCustId" id="tb_usrCustId"></s:hidden>
									</td>
							</tr>
							<tr>
								 <td style="width: 120px; height: 25px;" align="right" class="blue12">
								    审核人：
										</td>
								 <td align="left" class="f66">
								 <s:select id="checkUser" list="checkers" name="paramMap.userName" listKey="id" listValue="userName"  />
										<span class="require-field">*<s:fielderror fieldName="paramMap['userName']"></s:fielderror></span>
								 
								 </td>
							</tr>
							<tr>
								<td style="width: 120px;  height: 25px;" align="right"
									class="blue12">
									身份证：
								</td>
								<td align="left" class="f66">
									<s:textfield id="tb_idNo" name="paramMap.idNo"
										cssClass="in_text_2" cssStyle="width: 150px" theme="simple" readonly="true"></s:textfield>
							</tr>
							<tr>
								<td style="width: 120px; height: 25px;" align="right" class="blue12">
									原始银行卡号：
								</td>
								<td align="left" class="f66">
									<s:textfield id="tb_cardNo" name="paramMap.cardNo"
										cssClass="in_text_2" cssStyle="width: 150px" theme="simple" readonly="true"></s:textfield></td>
							</tr>
							<tr>
								<td style="width: 120px;  height: 25px;" align="right"
									class="blue12">
									 新的银行卡号：
								</td>
								<td align="left" class="f66">
									<!-- <s:property  value="paramMap.modifiedCardNo"></s:property></td>-->
									<s:textfield id="tb_modifiedCardNo" name="paramMap.modifiedCardNo"
										cssClass="in_text_2" cssStyle="width: 150px" theme="simple" readonly="true"></s:textfield>
								  <s:hidden  id="tb_modifiedTime" name="paramMap.modifiedTime"
										cssClass="in_text_2" cssStyle="width: 150px" theme="simple"></s:hidden>
										<s:hidden  id="tb_id" name="paramMap.bankId"
										cssClass="in_text_2" cssStyle="width: 150px" theme="simple"></s:hidden>
										<s:hidden  id="tb_openBankId" name="paramMap.openBankId"
										cssClass="in_text_2" cssStyle="width: 150px" theme="simple"></s:hidden>
										<s:hidden  id="tb_cityId" name="paramMap.cityId"
										cssClass="in_text_2" cssStyle="width: 150px" theme="simple"></s:hidden>
										<s:hidden  id="tb_provinceId" name="paramMap.provinceId"
										cssClass="in_text_2" cssStyle="width: 150px" theme="simple"></s:hidden>
										</td>
										
							</tr>
							<tr>
								<td style="width: 120px;  height: 25px;" align="right"
									class="blue12">
									 新的开户行：
								</td>
								<td align="left" class="f66">
									<!-- <s:property  value="paramMap.modifiedBankName"></s:property>-->
									<s:textfield id="tb_modifiedBankName" name="paramMap.modifiedBankName"
										cssClass="in_text_2" cssStyle="width: 150px" theme="simple" readonly="true"></s:textfield>
									</td>
							</tr>
							<tr>
								<td style="width: 120px;  height: 25px;" align="right"
									class="blue12">
									 新的开户支行：
								</td>
								<td align="left" class="f66">
									<!-- <s:property  value="paramMap.modifiedBranchBankName"></s:property>-->
									<s:textfield id="tb_modifiedBranchBankName" name="paramMap.modifiedBranchBankName"
										cssClass="in_text_2" cssStyle="width: 150px" theme="simple" readonly="true"></s:textfield>
									</td>
							</tr>
							<tr>
								 <td style="width: 120px; height: 25px;" align="right" class="blue12">
								审核状态：
								</td>
								<td align="left" class="f66">
							       <s:radio id="radio1" name="paramMap.status" list="#{1:'审核通过',3:'审核不通过'}" value="3"></s:radio>
								 <span class="require-field">*<s:fielderror fieldName="paramMap['status']"></s:fielderror></span>
								 
								 </td>
								</tr>
								<tr>
								<td style="width: 120px; height: 25px;" align="right" class="blue12">
								  审核意见：
							       
								 </td>
								<td align="left" class="f66">
								<s:textarea id="remark" cssStyle="margin-left:5px;" name="paramMap.remark" value="" cols="30" rows="5"></s:textarea>
								<span class="require-field">*<s:fielderror fieldName="paramMap['remark']"></s:fielderror><span id="hhhhn" style="font-size: 12px;margin-left: 5px;"></span> </span></span>
								 
								</td>
								</tr>
							<tr>
								<td height="36" align="right" class="blue12">
									<s:hidden name="action"></s:hidden>
									&nbsp;
								</td>
								<td>
									<button id="btn_save"
										style="background-image: url('../images/admin/btn_queding.jpg'); width: 70px; border: 0; height: 26px"></button>
									&nbsp;
									
								</td>
							</tr>
						</table>
						<br />
					</div>
				</div>
			</div>
<%--		</form>--%>
	</body>
</html>
