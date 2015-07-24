<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>借款管理-满标复审</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
<meta http-equiv="description" content="This is my page" />
<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
<link href="../css/admin/admin_custom_css.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<div id="right" style="background-image: url(../images/admin/right_bg_top.jpg); background-position: top; background-repeat: repeat-x;">
		<div style="padding: 15px 10px 0px 10px;">
			<div>
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td width="100" height="28" bgcolor="#CC0000" align="center" bgcolor="#8594A9" class="main_alll_h2">借款审核满标复审</td>
						<td width="2">&nbsp;</td>
						<td width="100" align="center" class="white12"></td>
						<td>&nbsp;</td>
					</tr>
				</table>
			</div>

			<div class="tab">
				<table cellspacing="1" cellpadding="3">
					<tr>
						<td class="blue12 left">用户名：${borrowMFullScaleDetail.username}</td>
						<td class="f66 leftp200">真实姓名：${borrowMFullScaleDetail.realName}</td>
					</tr>
					<tr>
						<td colspan="2" class="blue12 left">标的标题：${borrowMFullScaleDetail.borrowTitle}</td>
					</tr>
					<tr>
						<td class="blue12 left">借款金额：${borrowMFullScaleDetail.borrowAmount}&nbsp;元</td>
						<td class="f66 leftp200">年利率：${borrowMFullScaleDetail.annualRate}%</td>
					</tr>
					<tr>
						<td class="blue12 left">借款期限：${borrowMFullScaleDetail.deadline} <s:if test="%{borrowMFullScaleDetail.isDayThe ==1}">个月</s:if>
							<s:else>天</s:else></td>
						<td class="f66 leftp200">借款用途：${borrowMFullScaleDetail.purpose}</td>
					</tr>
					<tr>
						<td class="blue12 left">还款方式： 按月分期还款</td>
						<td class="f66 leftp200">标的类型： <s:if test="%{borrowMFullScaleDetail.borrowWay==1}">
							薪金贷
							</s:if> <s:elseif test="%{borrowMFullScaleDetail.borrowWay==2}">
							生意贷
							</s:elseif> <s:elseif test="%{borrowMFullScaleDetail.borrowWay==3}">
							业主贷
							</s:elseif></td>
					</tr>
					<s:if test="#request.subscribes==1">
						<tr>
							<td class="blue12 left">最小认购金额：${borrowMFullScaleDetail.smallestFlowUnit}元</td>
							<td class="f66 leftp200">认购总份数：${borrowMFullScaleDetail.circulationNumber}份</td>
						</tr>
					</s:if>
					<s:elseif test="%{borrowMFullScaleDetail.borrowShow==2}">
						<tr>
							<td class="blue12 left">最小认购金额：${borrowMFullScaleDetail.smallestFlowUnit}元</td>
							<td class="f66 leftp200">认购总份数：${borrowMFullScaleDetail.circulationNumber}份</td>
						</tr>
					</s:elseif>
					<s:else>
						<tr>
							<td class="blue12 left">最低投标金额：${borrowMFullScaleDetail.minTenderedSum}元</td>
							<td class="f66 leftp200">最高投标金额： <s:if test="%{borrowMFullScaleDetail.maxTenderedSum==''}">
								                           没有限制
								        </s:if> <s:else>
								           ${borrowMFullScaleDetail.maxTenderedSum}元                
								        </s:else></td>
						</tr>
					</s:else>
					<tr>
						<td class="blue12 left">投标奖励: <s:if test="%{borrowMFullScaleDetail.excitationType ==2}">
									  按 固定金额,${borrowMFullScaleDetail.excitationSum}元
									</s:if> <s:elseif test="%{borrowMFullScaleDetail.excitationType ==3}">
									 按借款金额比例,${borrowMFullScaleDetail.excitationSum}%
									</s:elseif> <s:else>
									   无
									</s:else></td>
						<td align="left" class="f66"></td>
					</tr>
					<tr>
						<td class="blue12 left">投标密码： <s:if test="%{borrowMFullScaleDetail.hasPWD ==1}">
									   有
									</s:if> <s:else>
									   无
									</s:else></td>
						<td class="f66 leftp200"></td>
					</tr>
					<s:if test="%{borrowMFullScaleDetail.borrowShow==1}">
						<tr>
							<td colspan="2" class="blue12 left">借款详情：${borrowMFullScaleDetail.detail}</td>
						</tr>
					</s:if>
					<s:else>
						<tr>
							<td colspan="2" class="blue12 left">借款方商业概述：${borrowMFullScaleDetail.businessDetail}</td>
						</tr>
						<tr>
							<td colspan="2" class="blue12 left">借款方资产情况：${borrowMFullScaleDetail.assets}</td>
						</tr>
						<tr>
							<td colspan="2" class="blue12 left">借款方资金用途：${borrowMFullScaleDetail.moneyPurposes}</td>
						</tr>
					</s:else>
					<tr>
						<td colspan="2" class="blue12 left">添加时间：${borrowMFullScaleDetail.publishTime}/IP: ${borrowMFullScaleDetail.publishIP}</td>
					</tr>
					<tr>
						<td colspan="2" class="blue12 left">借款人认证审核查看：<a href="queryPerUserCreditAction.do?userId=${borrowMFullScaleDetail.userId}" target="_blank">申请人认证详情查看</a></td>
					</tr>
					<tr>
						<td colspan="2" class="blue12 left">审核状态：<span class="require-field">*</span> <s:radio name="" list="#{2:'审核通过',1:'审核不通过'}" disabled="true" value="2"></s:radio></td>
					</tr>
					<tr>
						<td colspan="2" class="blue12 left">站内信通知： <br />
						<s:textarea cssStyle="margin-left:80px;" disabled="true" cols="30" rows="5" value="%{borrowMFullScaleDetail.mailContent}"></s:textarea></td>
						<td align="center" class="f66"></td>
					</tr>
				</table>
				<div id="content">
					<table>
						<tr>
							<td colspan="2" class="blue12 left">复审状态：<span class="require-field">*</span> <s:radio name="paramMap.status" list="#{4:'复审通过',6:'复审不通过'}" value=""></s:radio></td>
						</tr>
						<tr>
							<td colspan="2" class="blue12 left">风控意见：<span class="require-field">*&nbsp;</span> <br />
							<s:textarea cssStyle="margin-left:80px;" name="paramMap.auditOpinion" value="%{borrowMFullScaleDetail.auditOpinion}" cols="30" rows="10"></s:textarea></td>
						</tr>
						<tr>
							<td colspan="2" align="left" style="padding-left: 30px;">
								<button id="btn_save" style=" background-image: url('../images/admin/btn_queding.jpg'); width: 70px; border: 0; height: 26px"></button>&nbsp;&nbsp;
								<button id="btn_reback" style="background-image: url('../images/admin/btn_reback.jpg'); width: 70px; border: 0; height: 26px"></button></td>
						</tr>
					</table>
				</div>
				<s:hidden name="id" value="%{borrowMFullScaleDetail.id}"></s:hidden>
				<br />
			</div>
		</div>
	</div>
	<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
	<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
	<script>
		var falg = false;
		$(function() {
			//提交表单
			$('#btn_save').click(
					function() {
						if (falg)
							return;
						$('#btn_save').attr('style', "background-image: url('../images/admin/btn_chulz.jpg'); width: 70px; border: 0; height: 26px");
						$('#btn_save').attr('disabled', true);
						param['paramMap.id'] = $('#id').val();
						param['paramMap.status'] = $("input[name='paramMap.status']:checked").val();
						param['paramMap.auditOpinion'] = $('#paramMap_auditOpinion').val();
						$.shovePost('updateBorrowFullScale.do', param, function(data) {
							var callBack = data.msg;
							if (callBack == undefined || callBack == '') {
								falg = false;
								$('#content').html(data);
								$('#btn_save').attr('disabled', false);
							} else {
								if (callBack == 1) {
									falg = true;
									alert("操作成功!");
									window.location.href = "borrowFullScale.do";
									return false;
								}
								alert(callBack);
								falg = false;
								$('#btn_save').attr('style',
										"background-image: url('../images/admin/btn_queding.jpg'); width: 70px; border: 0; height: 26px");
								$('#btn_save').attr('disabled', false);
							}
						});
					});
			$('#btn_reback').click(
					function() {
						if (falg)
							return;
						var r = confirm("撤消操作,是否继续?");
						if (r == true) {
							$('#btn_reback').attr('style',
									"background-image: url('../images/admin/btn_chulz.jpg'); width: 70px; border: 0; height: 26px");
							$('#btn_reback').attr('disabled', true);
							param['paramMap.id'] = $('#id').val();
							$.shovePost('reBackBorrowTenderIn.do', param, function(data) {
								var callBack = data.msg;
								if (callBack == 1) {
									falg = true;
									alert("操作成功!");
									window.location.href = "borrowFullScale.do";
									return false;
								}
								falg = false;
								alert(callBack);
								$('#btn_reback').attr('style',
										"background-image: url('../images/admin/btn_reback.jpg'); width: 70px; border: 0; height: 26px");
								$('#btn_reback').attr('disabled', false);
							});
						}
					});
		});
	</script>
</body>
</html>