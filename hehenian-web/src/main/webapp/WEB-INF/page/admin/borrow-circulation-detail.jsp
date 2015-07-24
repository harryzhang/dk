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
			<div id="right"
				style="background-image: url(../images/admin/right_bg_top.jpg); background-position: top; background-repeat: repeat-x;">
				<div style="padding: 15px 10px 0px 10px;">
					<div>
						<table border="0" cellspacing="0" cellpadding="0">
							<tr>
							    <td width="100" height="28" align="center"
									class="xxk_all_a">
									<a href="circulationBorrowInit.do">所有的流转标</a>
								</td>
								<td width="2">
								&nbsp;
							    </td>
								<td width="100" height="28" align="center"
									class="main_alll_h2">
									流转标详情
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
									用户名：${borrowCirculationDetail.username}
								</td>
								<td class="f66 leftp200">
									真实姓名：${borrowCirculationDetail.realName}
								</td>
							</tr>
							<tr>
								<td  class="blue12 left">
									奖励：
									<s:if test="borrowCirculationDetail.excitationType==3">${borrowCirculationDetail.excitationSum}%</s:if>
									<s:if test="borrowCirculationDetail.excitationType==2">${borrowCirculationDetail.excitationSum}元</s:if>
										<s:if test="borrowCirculationDetail.excitationType==1">无</s:if>
								</td>
								
									<td colspan="2" class="f66 leftp200">
									标的标题：${borrowCirculationDetail.borrowTitle}
								</td>
							</tr>
							<tr>
							    <td class="blue12 left">
									流转总金额：${borrowCirculationDetail.borrowAmount}&nbsp;元
								</td>
								<td class="f66 leftp200">
									年化利率：${borrowCirculationDetail.annualRate}%
								</td>
							</tr>
							<tr>
							    <td class="blue12 left">
									最小流转单位：${borrowCirculationDetail.smallestFlowUnit}&nbsp;元
								</td>
								<td class="f66 leftp200">
									回购期限：${borrowCirculationDetail.deadline}个月
								</td>
							</tr>
							<tr>
							    <td class="blue12 left">
									已流转份数：${borrowCirculationDetail.hasCirculationNumber}&nbsp;份
								</td>
								<td class="f66 leftp200">
									还有：${borrowCirculationDetail.remainCirculationNumber}份
								</td>
							</tr>
							<tr>
								<td colspan="2" class="blue12 left">
									添加时间：${borrowCirculationDetail.publishTime}/IP: ${borrowCirculationDetail.publishIP}
								</td>
							</tr>
							<tr>
								<td colspan="2" class="blue12 left">
									借款方商业概述：${borrowCirculationDetail.businessDetail}
								</td>
							</tr>
							<tr>
								<td colspan="2" class="blue12 left">
									借款方资产情况：${borrowCirculationDetail.assets}
								</td>
							</tr>
							<tr>
								<td colspan="2" class="blue12 left">
									借款方资金用途：${borrowCirculationDetail.moneyPurposes}
								</td>
							</tr>
							<tr>
								<td colspan="2" class="blue12 left">
									借款人认证审核查看：<a href="queryPerUserCreditAction.do?userId=${borrowCirculationDetail.userId}" target="_blank" style="color: blue;"><strong>申请人认证详情查看</strong></a>
								</td>
							</tr>
							</table>
							<div id="content">
							<table>
							<tr>
								<td colspan="2" class="blue12 left">
									审核状态：<span class="require-field">*</span>
									<s:radio name="paramMap.status" list="#{1:'审核通过',2:'审核不通过'}" value=""></s:radio>
								</td>
							</tr>
							<tr>
							    <td colspan="2" class="blue12 left">
									风险控制措施：<span class="require-field">*&nbsp;</span>
									<br/><s:textarea cssStyle="margin-left:80px;" name="paramMap.auditOpinion" value="%{borrowCirculationDetail.auditOpinion}" cols="30" rows="10"></s:textarea>
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
						<s:hidden name="id" value="%{borrowCirculationDetail.id}"></s:hidden>
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
	     param['paramMap.reciver'] = '${borrowCirculationDetail.userId}';
	     param['paramMap.status'] = $("input[name='paramMap.status']:checked").val();
	     param['paramMap.auditOpinion'] = $('#paramMap_auditOpinion').val();
	     $.shovePost('updateBorrowCirculation.do',param,function(data){
		   if(data.msg ==1){
		       alert('操作成功!');
		       window.location.href="borrowf.do";
		       return;
		   }
		   alert(data.msg);
		 });
	 });
	});
</script>
	</body>	
</html>