<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<html>
	<head>
		<title>财务管理-扣费管理</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<link href="../css/admin/admin_css.css" rel="stylesheet"
			type="text/css" />
		<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
		<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
		<script language="javascript" type="text/javascript"
			src="../My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript">
			$(function(){
			   var adminId = '${sessionScope.admin.id }';
			   $("#adminId").attr("value",adminId);
			   $("#pageId").attr("value","withdraw");
			   $("#adminId").attr("name","paramMap.adminId");
			   $("#pageId").attr("name","paramMap.pageId");
			   $("#tb_code").attr("value","");
			});
			
			   //提交表单
				function tb_submit(){
					var param={};
					param["paramMap.dealMoney"] = $("#tb_money").val();
					param["paramMap.code"] = $("#tb_code").val();
					param["paramMap.pageId"] = $("#pageId").val();
					param["paramMap.userId"] = $("#tb_userId").val();
					param["paramMap.remark"] = $("#tb_remark").val();
					param["paramMap.userName"] = $("#tb_userName").val();
					$.shovePost("addBackWithdraw.do", param, function(data){
							if (data == "1") {
								alert("提交成功")
								window.parent.closeJbox();
							}else if (data == "0") 
								alert("提交失败");
							else if (data == "-99") 
								alert("用户余额不足");
							else{
								alert(data);
<%--								$("#right").html(data);--%>
								switchCode();
							}
					});
				}
			
			
			function cancel(){
			  $("#tb_remark").attr("value","");
			  $("#tb_money").attr("value","");
			  $("#remark_tip").html("");
			  $("#money_tip").html("");
			}
			
			function switchCode(){
				var timenow = new Date();
				$("#codeNum").attr("src","admin/imageCode.do?pageId=withdraw&d="+timenow);
			}
									
		</script>
	</head>
	<body>
		<form>
			<div id="right">
				<div style="padding: 15px 10px 0px 10px;">
					<div>
						<div
							style="padding-right: 10px; padding-left: 10px; padding-bottom: 10px; width: 100%; padding-top: 10px; background-color: #fff;">
							<table width="92%" border="0" cellspacing="1" cellpadding="3">
								<tr>
									<td style="width: 120px; height: 35px;" align="right"
										class="blue12">
										用户名：
									</td>
									<td align="left" class="f66">
										<s:textfield id="tb_userName" name="paramMap.userName"
											cssClass="in_text_2" cssStyle="width: 150px;height:20px;"
											theme="simple" readonly="true"></s:textfield>
										<s:textfield id="tb_userId" name="paramMap.userId"
											cssClass="in_text_2"
											cssStyle="width: 150px;height:20px;display:none"
											theme="simple" readonly="true"></s:textfield>
									</td>
								</tr>

								<tr>
									<td style="width: 120px; height: 35px;" align="right"
										class="blue12">
										扣除金额：
									</td>
									<td align="left" class="f66">
										<s:textfield id="tb_money" name="paramMap.dealMoney"
											cssClass="in_text_2" cssStyle="width: 150px;height:20px;"
											theme="simple"></s:textfield>
										<span id="money_tip" class="require-field">*<s:fielderror
												fieldName="paramMap['dealMoney']"></s:fielderror>
										</span>
									</td>
								</tr>
								<tr>
									<td style="width: 120px; height: 35px;" align="right"
										class="blue12">
										扣除备注：
									</td>
									<td>
										&nbsp;
									</td>

								</tr>
								<tr>
									<td colspan="2" class="f66">
										<s:textarea id="tb_remark" cssStyle="margin-left:80px;"
											name="paramMap.remark"  cols="30" rows="5"></s:textarea>
										<span id="remark_tip" class="require-field">*<s:fielderror
												fieldName="paramMap['remark']"></s:fielderror>
										</span>
									</td>
								</tr>
								<tr>
									<td style="width: 120px; height: 35px;" align="right"
										class="blue12">
										验证码：
									</td>
									<td colspan="2" class="f66">
										<s:textfield id="tb_code" name="paramMap.code"
											cssClass="in_text_2" cssStyle="width: 150px;height:20px;"
											theme="simple"></s:textfield>

										<img src="admin/imageCode.do?pageId=withdraw" title="点击更换验证码"
											style="cursor: pointer;" id="codeNum" width="46" height="18"
											onclick="javascript:switchCode()" />

										<span id="code_tip" class="require-field">*<s:fielderror
												fieldName="paramMap['code']"></s:fielderror>
										</span>

										<!-- <s:hidden id="adminId" name="paramMap.adminId"></s:hidden>
										<s:hidden id="pageId" name="paramMap.pageId"></s:hidden>-->

										<s:textfield id="adminId" name="paramMap.adminId"
											cssClass="in_text_2"
											cssStyle="width: 150px;height:20px;display:none"
											theme="simple"></s:textfield>
										<s:textfield id="pageId" name="paramMap.pageId"
											cssClass="in_text_2"
											cssStyle="width: 150px;height:20px;display:none"
											theme="simple"></s:textfield>
									</td>

								</tr>

								<tr>
									<td colspan="2" align="center">
											<input type="button"  onclick="tb_submit()" value="确定"/>
									</td>
								</tr>
							</table>
						</div>
					</div>
				</div>
			</div>
		</form>
	</body>
</html>
