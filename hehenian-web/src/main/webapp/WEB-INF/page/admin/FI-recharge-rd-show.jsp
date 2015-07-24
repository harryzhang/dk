<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<html>
	<head>
		<title>财务管理-用户银行卡管理</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="This is my page" />
		<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
		
	</head>
	<body>
		<div id="right"
			style="background-image: url; background-position: top; background-repeat: repeat-x;">
			<div style="padding: 10px 10px 0px 10px;">
				<div>
					<div
						style="padding-right: 10px; padding-left: 10px; padding-bottom: 10px; width: 100%; padding-top: 10px; background-color: #fff;">
						<table style="margin-bottom: 8px; font-size: 14px;" cellspacing="0" cellpadding="0"
							width="97%" border="0" >
							<tbody>
							
						         <tr>
									<td class="f66" align="right" width="50%" height="30px">
										用户名：&nbsp;&nbsp;
									</td>
									
									<td  align="left" >
									&nbsp;&nbsp;
										<s:property  value="paramMap.uname"></s:property>
									</td>
								 </tr>
								 <tr>
									<td class="f66" align="right" width="50%" height="30px">
										充值类型：&nbsp;&nbsp;
									</td>
									
									<td  align="left" >&nbsp;&nbsp;
										<s:if test="paramMap.type==1">
										   手动充值
										</s:if>
										<s:else>
										   手动扣费
										</s:else>
										<s:property  value="paramMap.rechargeType"></s:property>
									</td>
								</tr>
								
								<tr>
									<td class="f66" align="right" width="50%" height="30px">
										操作金额：&nbsp;&nbsp;
									</td>
									
									<td  align="left" >&nbsp;&nbsp;
										<s:property  value="paramMap.dealMoney"></s:property>
									</td>
								</tr>
								
								<tr>
									<td class="f66" align="right" width="50%" height="30px">
										备注：&nbsp;&nbsp;
										
									</td>
									<td  align="left" >
									 <s:textarea id="remark" cssStyle="margin:20px;"  class="textareash"
											name="paramMap.remark"  cols="30" rows="4" readonly="true"></s:textarea>
									</td>
								</tr>
								<tr>
									<td class="f66" align="right" width="50%" height="30px">
										操作人员：&nbsp;&nbsp;
									</td>
									
									<td  align="left" >&nbsp;&nbsp;
										<s:property  value="paramMap.userName"></s:property>
									</td>
								</tr>
								<tr>
									<td class="f66" align="right" width="50%" height="30px">
										操作时间：&nbsp;&nbsp;
									</td>
									
									<td  align="left" >&nbsp;&nbsp;
										<s:property  value="paramMap.checkTime"></s:property>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
			</div>
			</div>
	</body>
</html>
