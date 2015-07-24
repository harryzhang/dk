<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>满标借款详情</title>
<meta http-equiv="description" content="This is my page" />
<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
<link href="../css/css.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
<script type="text/javascript" src="../common/dialog/popup.js"></script>
<script language="javascript" type="text/javascript" src="../script/jbox/jquery.jBox-2.3.min.js"></script>
<style>
.hhna{margin-left: 40px; margin-right: 40px; margin-top: 20px; width: 90%;}
.hhna td{padding: 3px ;}
</style>
<script>
	var falg = false;
	$(function() {
		//提交表单
		$('#btn_save').click(function() {
			if (falg)
				return;
			falg = true;
			$('#btn_save').attr('disabled', true);
			param['paramMap.id'] = $('#id').val();
			param['paramMap.start'] = $('#startIndex').val();
			param['paramMap.status'] = '4';
			param['paramMap.auditOpinion'] = $('#paramMap_auditOpinion').val();
			param['paramMap.isDefault'] = $("input[name='isDefault']:checked").val();
			window.parent.$.jBox.tip("<div class='data-submit'>提交数据中，请稍候...</div>","loading");
			$.shovePost("updateBorrowFull.do", param, function(data) {
				var callBack = data.msg;
				if (callBack == 1) {
					alert("操作成功!");
					window.parent.closeMthod();
				} else {
					$.jBox.closeTip();
					alert(callBack);
					falg = false;
					$('#btn_save').attr('disabled', false);
				}
			});
		});
	});

	//弹出窗口关闭
	function closeMthodes() {
		window.parent.closeMthod();
	}
</script>
</head>
<body>
	<input type="hidden" name="id" id="id" value="${fullScaleDetail.id}" />
	<input type="hidden" name="borrowTitle" id="borrowTitle" value="${fullScaleDetail.borrowTitle}"></input>
	<input type="hidden" name="reciver" id="reciver" value="${fullScaleDetail.publisher}"></input>
	<table class="hhna" border="1">
		<tr style="height: 30px">
			<td colspan="4" style="text-align: center;">满标借款</td>
		</tr>
		<tr>
			<td align="right">用户名：</td>
			<td>${fullScaleDetail.username}</td>
			<td align="right" style="width: 150px;">真实姓名：</td>
			<td>${fullScaleDetail.realName}</td>
		</tr>
		<tr>
			<td align="right">标的标题：</td>
			<td>${fullScaleDetail.borrowTitle}</td>
			<td align="right">借款金额：</td>
			<td>${fullScaleDetail.borrowAmount}&nbsp;元</td>
		</tr>
		<tr>
			<td align="right">借款期限：</td>
			<td>${fullScaleDetail.deadline} 个月</td>
			<td align="right">年利率：</td>
			<td>${fullScaleDetail.annualRate}&nbsp;%</td>
		</tr>
		<tr>
			<td align="right">借款用途：</td>
			<td>${fullScaleDetail.moneyPurposes}</td>
			<td align="right">还款方式：</td>
			<td><s:if test="#request.fullScaleDetail.paymentMode ==1">
									   按月分期还款
									</s:if> <s:elseif test="#request.fullScaleDetail.paymentMode ==2">
									 按月付息,到期还本
									</s:elseif> <s:elseif test="#request.fullScaleDetail.paymentMode ==3">
									 秒还
									</s:elseif></td>
		</tr>
		<tr>
			<td align="right">标的类型：</td>
			<td><s:if test="#request.fullScaleDetail.borrowWay==1">
							薪金贷
							</s:if> <s:elseif test="#request.fullScaleDetail.borrowWay==2">
							生意贷
							</s:elseif> <s:elseif test="#request.fullScaleDetail.borrowWay==3">
							业主贷</s:elseif>
			</td>
			<td align="right">投标奖励：</td>
			<td><s:if test="#request.fullScaleDetail.excitationType ==2">
									  按 固定金额,${fullScaleDetail.excitationSum}&nbsp;元
									</s:if> <s:elseif test="#request.fullScaleDetail.excitationType ==3">
									 按借款金额比例,${fullScaleDetail.excitationSum}&nbsp;%
									</s:elseif> <s:else>
									   无
									</s:else></td>
		</tr>
		<tr>
			<td align="right">最低投标金额：</td>
			<td>${fullScaleDetail.minTenderedSum}&nbsp;元</td>
			<td align="right">最高投标金额：</td>
			<td><s:if test="#request.fullScaleDetail.maxTenderedSum=='' || #request.fullScaleDetail.maxTenderedSum==-1 ">
								没有限制
							</s:if> <s:else>
								 ${fullScaleDetail.maxTenderedSum}&nbsp;元                
							</s:else></td>
		</tr>
<%--		<tr>--%>
<%--			<td align="right">成功解冻/放款次数：</td>--%>
<%--			<td>${fullScaleDetail.unfreeOk}/${fullScaleDetail.loansOk}</td>--%>
<%--			<td align="right">投资总次数：</td>--%>
<%--			<td>${fullScaleDetail.investNum}</td>--%>
<%--		</tr>--%>
		<tr>
			<td align="right">添加时间/IP：</td>
			<td>${fullScaleDetail.publishTime}<br /> ${fullScaleDetail.publishIP}</td>
			<td align="right" nowrap="nowrap">借款人认证审核查看：</td>
			<td><a href="queryPerUserCreditAction.do?userId=${fullScaleDetail.userId}">认证详情查看</a></td>
		</tr>
	</table>
	<table style="margin-left: 40px; margin-right: 40px; margin-top: 20px; width: 90%;" border="1">
		<tr style="height: 30px">
			<td colspan="4" style="text-align:center;">&nbsp; 审核信息</td>
		</tr>
		<tr>
			<td colspan="4">
				<table style="width: 100%;" border="0">
					<tr>
						<td align="right">初审状态：</td>
						<td align="left"><s:if test="#request.fullScaleDetail.source==1 || #request.fullScaleDetail.source==3">
								<input type="radio" id="statusYes" name="borrowStatus" value="9" disabled="disabled" style="verti cal-align: text-bottom; margin-bottom: -2px;" checked="checked" />
									通过&nbsp;&nbsp;&nbsp;&nbsp;
									<input type="radio" id="statusNo" name="borrowStatus" value="-1" disabled="disabled" style="vertical-align: text-bottom; margin-bottom: -2px;" />
									未通过&nbsp;&nbsp;&nbsp;&nbsp;初审人：${fullScaleDetail.firstTrial}
						</s:if> <s:if test="#request.fullScaleDetail.source==0">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										<input type="radio" id="statusAgain" name="borrowStatus" value="10" checked="checked" disabled="disabled" style="vertical-align: text-bottom; margin-bottom: -2px;" />小贷公司导入用户，跳过初审</s:if>
							<s:if test="#request.fullScaleDetail.source==2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										<input type="radio" id="statusAgain" name="borrowStatus" value="10" checked="checked" disabled="disabled" style="vertical-align: text-bottom; margin-bottom: -2px;" />彩生活用户，跳过初审</s:if></td>
					</tr>
					<tr>
						<td align="right">初审说明：</td>
						<td align="left" colspan="2"><s:if test="#request.fullScaleDetail.source==1 || #request.fullScaleDetail.source==3 ">
								<textarea name="firsttrialMailContent" id="firsttrialMailContent" cols="50" readonly="readonly" rows="3" style="font-size: 12px;">用户：${fullScaleDetail.username}的借款[${fullScaleDetail.borrowTitle}]通过了初审.初审状态：初审通过
										</textarea>
							</s:if> <s:if test="#request.fullScaleDetail.source==0  || #request.fullScaleDetail.source==2">
								<textarea name="firsttrialMailContent" id="firsttrialMailContent" cols="50" readonly="readonly" rows="3" style="font-size: 12px;">无
										</textarea>
							</s:if></td>
					</tr>
					<tr>
						<td align="right">复审状态：</td>
						<td align="left"><input type="radio" id="statusYes" name="borrowStatusr" value="7" disabled="disabled" style="vertical-align: text-bottom; margin-bottom: -2px;" checked="checked" />
							通过&nbsp;&nbsp;&nbsp;&nbsp; <input type="radio" id="statusNo" name="borrowStatusr" value="6" disabled="disabled" style="vertical-align: text-bottom; margin-bottom: -2px;" />
							未通过&nbsp;&nbsp;&nbsp;&nbsp;复审人：${fullScaleDetail.recheck}
							</td>
					</tr>
					<tr>
						<td align="right">复审说明：</td>
						<td align="left" colspan="2"><textarea name="echeckMailContent" id="echeckMailContent" cols="50" readonly="readonly" rows="3" style="font-size: 12px;">用户：${fullScaleDetail.username}的借款[${fullScaleDetail.borrowTitle}]通过了复审.可以发布</textarea>
						</td>
					</tr>
					<%--					<tr>--%>
					<%--						<td colspan="3" align="left">--%>
					<%--						<input type="checkbox" name="isDefault" checked="checked" style="margin:5px 10px 5px 25px;;" />自动取现--%>
					<%--						</td>--%>
					<%--					</tr>--%>
					<tr>
						<td colspan="3" align="center">
							<s:if test="#request.fullScaleDetail.borrowStatus == 3">
<%--							<p><span style="margin-left: 7px;color: red;">跳过放款人个数:<input id="startIndex" type="text" style="border: 1px #888 solid;width: 50px;height: 17px;" /></span></p>--%>
								<input type="button" style="background: #666666;" value="放款" id="btn_save" />
								<input type="button" style="background: #666666;" value="取消" onclick="closeMthodes();" />
							</s:if> <s:if test="#request.fullScaleDetail.borrowStatus == 4">
								<input type="button" style="background: #666666;" value="关闭" onclick="closeMthodes();" />
							</s:if></td>
					</tr>
				</table></td>
		</tr>
	</table>
</body>
</html>
