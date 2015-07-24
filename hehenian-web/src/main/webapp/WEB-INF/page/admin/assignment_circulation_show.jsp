<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<meta http-equiv="description" content="This is my page" />
		<link href="../css/admin/admin_css.css" rel="stylesheet"
			type="text/css" />
		<link href="../css/css.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
		<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
		<script type="text/javascript" src="../common/dialog/popup.js"></script>
		<script language="javascript" type="text/javascript"
			src="../script/jbox/jquery.jBox-2.3.min.js"></script>
		<script>
			
	//审核
	function updateAssginment() {
		var id = $("#id").val();
		var status = $("input[name='status']:checked").val();
		var dsc = $("#dsc").val();
		param["paramMap.status"] = status;
		param["paramMap.id"] = id;
		param["paramMap.dsc"] = dsc;
		var size = $("input[name='status']:checked").val();
		if (status == null) {
			alert("请选择审核按钮");
			return null;
		}
		$.shovePost("updateUserReview.do", param, function(data) {
			if (data == "1") {
				alert("提交成功")
				window.parent.closeMthod();
			}
			if (data == "2") {
				alert("提交失败");
				return;
			}
		});
	}
	
	var falg = false;
	function updateBorrow() {
   		 if(falg) return ;
   	   $('#btn_save').attr('style',"background-image: url('../images/admin/btn_chulz.jpg'); width: 70px; border: 0; height: 26px");
   	  $('#btn_save').attr('disabled',true);
	     param['paramMap.id'] = $('#id').val();
	     param['paramMap.status'] = $("input[name='paramMap.status']:checked").val();
	     param['paramMap.auditOpinion'] = $('#paramMap_auditOpinion').val();
	     $.shovePost('updateBorrowFullScale.do',param,function(data){
		   var callBack = data.msg;
		   if(callBack == undefined || callBack == ''){
			   falg = false;
		       $('#content').html(data);
		       $('#btn_save').attr('disabled',false);
		   }else{
		       if(callBack == 1){
		    	   falg = true;
		          alert("操作成功!");
		          window.location.href="borrowFullScale.do";
		          return false;
		       }
		       alert(callBack);
		       falg = false;
		       $('#btn_save').attr('style',"background-image: url('../images/admin/btn_queding.jpg'); width: 70px; border: 0; height: 26px");
		       $('#btn_save').attr('disabled',false);
		   }
		 });
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
				<td colspan="4" style="text-align: center;">
					满标复审
				</td>
			</tr>
			<tr>
				<td style="width: 150px;" align="right">
					用户名：
				</td>
				<td style="width: 200px;">
					${borrowMFullScaleDetail.username}
				</td>
				<td align="right" style="width: 150px;">
					真实姓名：
				</td>
				<td style="width: 200px;">
					${borrowMFullScaleDetail.realName}
				</td>
			</tr>
			<tr>
				<td align="right">
					标的标题：
				</td>
				<td>
					${borrowMFullScaleDetail.borrowTitle}
				</td>
				<td align="right">
					借款金额：
				</td>
				<td>
					${borrowMFullScaleDetail.borrowAmount}元
				</td>
			</tr>
			<tr>
				<td align="right">
					借款期限：
				</td>
				<td>
					${borrowMFullScaleDetail.deadline}
					<s:if test="%{borrowMFullScaleDetail.isDayThe ==1}">个月</s:if>
					<s:else>天</s:else>
				</td>
				<td align="right">
					年利率：
				</td>
				<td>
					${borrowMFullScaleDetail.annualRate}%
				</td>
			</tr>
			<tr>
				<td align="right">
					借款用途：
				</td>
				<td>
					${borrowMFullScaleDetail.purpose}
				</td>
				<td align="right">
					还款方式：
					<td>
						<s:if test="%{borrowMFullScaleDetail.paymentMode ==1}">
									   按月分期还款
									</s:if>
						<s:elseif test="%{borrowMFullScaleDetail.paymentMode ==2}">
									 按月付息,到期还本
									</s:elseif>
						<s:elseif test="%{borrowMFullScaleDetail.paymentMode ==3}">
									 秒还
									</s:elseif>
					</td>
			</tr>
			<tr>
				<td align="right">
					标的类型：
				</td>
				<td>
					<s:if test="%{borrowMFullScaleDetail.borrowWay==1}">
							薪金贷
							</s:if>
					<s:elseif test="%{borrowMFullScaleDetail.borrowWay==2}">
							生意贷
							</s:elseif>
					<s:elseif test="%{borrowMFullScaleDetail.borrowWay==3}">
							业主贷
							</s:elseif>
					<s:elseif test="%{borrowMFullScaleDetail.borrowWay==4}">
							实地考察借款
							</s:elseif>
					<s:elseif test="%{borrowMFullScaleDetail.borrowWay==5}">
							机构担保借款
							</s:elseif>
					<s:elseif test="%{borrowMFullScaleDetail.borrowWay==6}">
							流转标借款
							</s:elseif>
				</td>
				<td align="right">
					投标奖励：
				</td>
				<td>
					<s:if test="%{borrowMFullScaleDetail.excitationType ==2}">
									  按 固定金额,${borrowMFullScaleDetail.excitationSum}元
									</s:if>
					<s:elseif test="%{borrowMFullScaleDetail.excitationType ==3}">
									 按借款金额比例,${borrowMFullScaleDetail.excitationSum}%
									</s:elseif>
					<s:else>
									   无
									</s:else>
				</td>
			</tr>
			<tr>
				<td align="right">
					最低投标金额：
				</td>
				<td>
					${borrowMFullScaleDetail.smallestFlowUnit}元
				</td>
				<td align="right">
					最高投标金额：
				</td>
				<td>
					<s:if test="%{borrowMFullScaleDetail.maxTenderedSum==''}">
				                           没有限制
				        </s:if>
					<s:else>
				           ${borrowMFullScaleDetail.maxTenderedSum}元                
				        </s:else>
				</td>
			</tr>
			<tr>
				<td align="right">
					添加时间/IP：
				</td>
				<td>
					${borrowMFullScaleDetail.publishTime}
					<br>
						${borrowMFullScaleDetail.publishIP}
				</td>
				<td align="right">
					借款人认证查看：
				</td>
				<td>
					<a href="queryPerUserCreditAction.do?userId=${borrowMFullScaleDetail.userId}" target="_blank">申请人认证详情查看</a>
				</td>
			</tr>
		</table>
		<table
				style="margin-left: 40px; margin-right: 40px; margin-top: 20px; width: 90%"
				border="1">
				<tr style="height: 30px">
					<td colspan="4" style="text-align: left;">
						&nbsp; 审核
					</td>
				</tr>
				<tr>
					<td colspan="4">
						<table style="width: 100%" border="0">
							<tr>
								<td align="right">
									初审状态：
								</td>
								<td align="left">
									<s:if test="#request.borrowMFullScaleDetail.source==1">
										<input type="radio" id="statusYes" name="borrowStatus"
											value="9" disabled="disabled"
											style="vertical-align: text-bottom; margin-bottom: -2px;"
											checked="checked" />
									通过&nbsp;&nbsp;&nbsp;&nbsp;
									<input type="radio" id="statusNo" name="borrowStatus"
											value="-1" disabled="disabled"
											style="vertical-align: text-bottom; margin-bottom: -2px;"
											/>
									未通过&nbsp;&nbsp;&nbsp;&nbsp;初审人：${borrowMFullScaleDetail.firstTrial}
									</s:if>
									<s:if test="#request.borrowMFullScaleDetail.source==3">
										<input type="radio" id="statusYes" name="borrowStatus"
											value="9" disabled="disabled"
											style="vertical-align: text-bottom; margin-bottom: -2px;"
											checked="checked" />
									通过&nbsp;&nbsp;&nbsp;&nbsp;
									<input type="radio" id="statusNo" name="borrowStatus"
											value="-1" disabled="disabled"
											style="vertical-align: text-bottom; margin-bottom: -2px;"
											/>
									未通过&nbsp;&nbsp;&nbsp;&nbsp;初审人：${borrowMFullScaleDetail.firstTrial}
									</s:if>
								</td>
								<td align="left">
									<s:if test="#request.borrowMFullScaleDetail.source==0">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										<input type="radio" id="statusAgain" name="borrowStatus"
											value="10" disabled="disabled"
											style="vertical-align: text-bottom; margin-bottom: -2px;" />小贷公司导入用户，跳过初审</s:if>
									<s:if test="#request.map.source==2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										<input type="radio" id="statusAgain" name="borrowStatus"
											value="10" disabled="disabled"
											style="vertical-align: text-bottom; margin-bottom: -2px;" />彩生活用户，跳过初审</s:if>
								</td>
							</tr>
							<tr>
								<td align="right">
									初审说明：
								</td>
								<td align="left" colspan="2">
									<s:if test="#request.borrowMFullScaleDetail.source==1">
										<textarea name="firsttrialMailContent"
											id="firsttrialMailContent" cols="50" readonly="readonly"
											rows="3">用户：${borrowMFullScaleDetail.username}的借款[${borrowMFullScaleDetail.borrowTitle}]通过了初审.初审状态：初审通过
										</textarea>
									</s:if>
									<s:if test="#request.borrowMFullScaleDetail.source==3">
										<textarea name="firsttrialMailContent"
											id="firsttrialMailContent" cols="50" readonly="readonly"
											rows="3">用户：${borrowMFullScaleDetail.username}的借款[${borrowMFullScaleDetail.borrowTitle}]通过了初审.初审状态：初审通过
										</textarea>
									</s:if>
									<s:if test="#request.borrowMFullScaleDetail.source==0">
										<textarea name="firsttrialMailContent"
											id="firsttrialMailContent" cols="50" readonly="readonly"
											rows="3">无
										</textarea>
									</s:if>
									<s:if test="#request.borrowMFullScaleDetail.source==2">
										<textarea name="firsttrialMailContent"
											id="firsttrialMailContent" cols="50" readonly="readonly"
											rows="3">无
										</textarea>
									</s:if>
								</td>
							</tr>
							<tr>
								<td align="right">
									复审状态：
								</td>
								<td align="left">
										<input type="radio" id="statusYes" name="borrowStatusr"
											value="7" disabled="disabled"
											style="vertical-align: text-bottom; margin-bottom: -2px;"
											 checked="checked" />
									通过&nbsp;&nbsp;&nbsp;&nbsp;
								<input type="radio" id="statusNo" name="borrowStatusr"
											value="6" disabled="disabled"
											style="vertical-align: text-bottom; margin-bottom: -2px;"
											/>
									未通过&nbsp;&nbsp;&nbsp;&nbsp;复审人：${borrowMFullScaleDetail.recheck}
								</td>
							</tr>
							<tr>
								<td align="right">
									复审说明：
								</td>
								<td align="left" colspan="2">
									<textarea name="echeckMailContent" id="echeckMailContent"
										cols="50" readonly="readonly" rows="3">用户：${borrowMFullScaleDetail.username}的借款[${borrowMFullScaleDetail.borrowTitle}]通过了复审.可以发布</textarea>
								</td>
							</tr>
							<tr>
								<td colspan="3" align="center">
									<input type="button" style="background: #666666;" value="放款"
										onclick="updateBorrow();" />
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
