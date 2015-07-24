<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<title>借款管理-初审</title>
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
			<div id="right">
				<div style="padding: 15px 10px 0px 10px;">
					<div>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="100" height="28" class="main_alll_h2">
									借款审核初审
								</td>
								<td width="2">
									&nbsp;
								</td>
								<td width="100" align="center"  class="white12">
								</td>
								<td>
									&nbsp;
								</td>
							</tr>
						</table>
					</div>
					 
					<div class="tab">
						<table cellspacing="1" cellpadding="3">
							<tr>
								<td class="blue12 left">
									用户名：${borrowMAllDetail.username}
								</td>
								<td class="f66 leftp200">
									真实姓名：${borrowMAllDetail.realName}
								</td>
							</tr>
							<tr>
								<td colspan="2" class="blue12 left">
									标的标题：${borrowMAllDetail.borrowTitle}
								</td>
							</tr>
							<tr>
							    <td class="blue12 left">
									借款金额：${borrowMAllDetail.borrowAmount}&nbsp;元
								</td>
								<td class="f66 leftp200">
									年利率：${borrowMAllDetail.annualRate}%
								</td>
							</tr>
							<tr>
								<td class="blue12 left">
									借款期限：${borrowMAllDetail.deadline}
									<s:if test="%{borrowMAllDetail.isDayThe ==1}">个月</s:if><s:else>天</s:else>
								</td>
								<td class="f66 leftp200">
								           借款用途：${borrowMAllDetail.purpose}
								</td>
							</tr>
							<tr>
								<td class="blue12 left">
									还款方式：
									<s:if test="%{borrowMAllDetail.paymentMode ==1}">
									   按月分期还款
									</s:if>
									<s:elseif test="%{borrowMAllDetail.paymentMode ==2}">
									 按月付息,到期还本
									</s:elseif>
									<s:elseif test="%{borrowMAllDetail.paymentMode ==3}">
									 秒还
									</s:elseif>　
								</td>
								<td class="f66 leftp200">
								标的类型：
							<s:if test="%{borrowMAllDetail.borrowWay==1}">
							薪金贷
							</s:if>
							<s:elseif test="%{borrowMAllDetail.borrowWay==2}">
							生意贷
							</s:elseif>
							<s:elseif test="%{borrowMAllDetail.borrowWay==3}">
							业主贷
							</s:elseif>
							<s:elseif test="%{borrowMAllDetail.borrowWay==4}">
							实地考察借款
							</s:elseif>
							<s:elseif test="%{borrowMAllDetail.borrowWay==5}">
							机构担保借款
							</s:elseif>
								</td>
							</tr>
							<tr>
								<td class="blue12 left">
									最低投标金额：${borrowMAllDetail.minTenderedSum}元
								</td>
								<td class="f66 leftp200">
                                                                                                最高投标金额：${borrowMAllDetail.maxTenderedSum}
                                    <s:if test="%{borrowMAllDetail.maxTenderedSum==''}">
							                           没有限制
							        </s:if>
							        <s:else>
							           ${borrowMAllDetail.maxTenderedSum}元                
							        </s:else>                                                            
								</td>
							</tr>
							<tr>
								<td class="blue12 left">
									投标奖励:  
									<s:if test="%{borrowMAllDetail.excitationType ==2}">
									  按 固定金额,${borrowMAllDetail.excitationSum}元
									</s:if>
									<s:elseif test="%{borrowMAllDetail.excitationType ==3}">
									 按借款金额比例,${borrowMAllDetail.excitationSum}%
									</s:elseif>
									<s:else>
									   无
									</s:else>
								</td>
								<td align="left" class="f66">
								</td>
							</tr>
							<tr>
								<td class="blue12 left">
									投标密码：
									<s:if test="%{borrowMAllDetail.hasPWD ==1}">
									   有
									</s:if>
									<s:else>
									   无
									</s:else>
								</td>
								<td class="f66 leftp200">
								</td>
							</tr>
							<tr>
								<td colspan="2" class="blue12 left">
									添加时间：${borrowMAllDetail.publishTime}/IP: ${borrowMAllDetail.publishIP}
								</td>
							</tr>
							<tr>
								<td colspan="2" class="blue12 left">
									借款详情：${borrowMAllDetail.detail}
								</td>
							</tr>
							<tr>
								<td colspan="2" class="blue12 left">
									借款人认证审核查看：<a href="queryPerUserCreditAction.do?userId=${borrowMAllDetail.userId}" target="_blank">申请人认证详情查看</a>
								</td>
							</tr>
							</table>
							<div id="content">
							<table>
							<tr>
								<td colspan="2" class="blue12 left">
									审核状态：<span class="require-field">*</span>
									<s:radio name="paramMap.status" list="#{2:'审核通过',1:'审核不通过'}" value="%{borrowMAllDetail.borrowStatus}"></s:radio>
								</td>
							</tr>
							<tr>
								<td colspan="2" class="blue12 left">
									站内信通知：<span class="require-field">*&nbsp;</span>
									<br/><s:textarea cssStyle="margin-left:80px;" name="paramMap.msg" cols="30" rows="5"></s:textarea>
								</td>
								<td align="center" class="f66">
								</td>
							</tr>
							<tr>
							    <td colspan="2" class="blue12 left">
									审核意见：<span class="require-field">*&nbsp;</span>
									<br/><s:textarea cssStyle="margin-left:80px;" name="paramMap.auditOpinion" value="%{borrowMAllDetail.auditOpinion}" cols="30" rows="10"></s:textarea>
								</td>
							</tr>
							<tr>
								<td colspan="2" align="left" style="padding-left: 30px;">
									<button id="btn_save"
										style="background-image: url('../images/admin/btn_queding.jpg'); width: 70px; border: 0; height: 26px"></button>
								</td>
							</tr>
						</table>
							</div>
						<s:hidden name="id" value="%{borrowMAllDetail.id}"></s:hidden>
						<br />
					</div>
				</div>
			</div>
<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
<script>
	$(function(){
	  //提交表单
   	  $('#btn_save').click(function(){
	     param['paramMap.id'] = $('#id').val();
	     param['paramMap.status'] = $("input[name='paramMap.status']:checked").val();
	     param['paramMap.msg'] = $('#paramMap_msg').val();
	     param['paramMap.auditOpinion'] = $('#paramMap_auditOpinion').val();
	     $.shovePost('updateBorrowF.do',param,function(data){
		   if(data ==1){
		       alert('操作成功!');
		       window.location.href="borrowf.do";
		   }else{
		       $('#content').html(data);
		   }
		 });
	 });
	});
</script>
	</body>	
</html>