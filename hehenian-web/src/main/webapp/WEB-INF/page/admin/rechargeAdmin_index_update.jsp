<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<html>
	<head>
		
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="This is my page" />
		<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
		<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
		<script type="text/javascript" language="javascript">
	    $(function(){
	        	//提交表单
				$("#succ").click(function(){
				   if(!window.confirm("此操作审核该项成功充值，确认吗?")){
				     return;
				   }
				    $("#ty").val("a");
					$("#addAdmin").submit();
					return false;
				});
				 //提交表单
				$("#fail").click(function(){
				   if(!window.confirm("此操作审核该项充值失败，确认吗?")){
				     return;
				   }
				    $("#ty").val("b");
					$("#addAdmin").submit();
					return false;
				});
				
				
	    });
		</script>

	</head>
	<body style="min-width: 1000px">
		<div id="right"
			style="background-image: url; background-position: top; background-repeat: repeat-x;">
			<div style="padding: 15px 10px 0px 10px;">
				<div>
				
				<table width="200px" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="100" height="28"  class="xxk_all_a">
								<a href="queryxxRechargeInit.do">线下充值管理</a>
							</td>
							<td width="2">
								&nbsp;
							</td>
								<td width="100" align="center" class="main_alll_h2">
									<a href="ajavascript:void(0);">线下充值审核</a>
								</td>
							<td>
								&nbsp;
							</td>
						</tr>
					</table>
				</div>
				<div
					style="padding-right: 10px; padding-left: 10px; padding-bottom: 10px; width: auto; padding-top: 10px; background-color: #fff;">
					<!--  <table style="margin-bottom: 8px;" cellspacing="0" cellpadding="0"
						width="100%" border="0">
						<tbody>
							<tr>
								<td class="f66" align="left" width="50%" height="36px">
									用户名:
									<input id="userName" name="paramMap.userName" />&nbsp;&nbsp; 
									真实姓名：
									<input id="realName" name="paramMap.realName" />&nbsp;&nbsp; 
									<input id="search" type="button" value="查找" name="search" />
								</td>
							</tr>
						</tbody>
					</table>-->
					<span id="dataInfo">
						<div>
    <form id="addAdmin" action="updateRechargeDetailStatusById.do" method="post">
		<table id="gvNews" style="width: 100%; color: #333333;"
			cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="0">
			<tbody>
			
				
				<s:hidden name="paramMap.id" />
				<s:hidden name="paramMap.type" value="" id="ty"/>
				<s:hidden name="paramMap.userId"/>
				<s:hidden name="paramMap.rechargeMoney"/>
					<tr class="gvItem">
						<td align="center">
						  用户账号:
							</td>
							<td>
							${paramMap.username}
					</td>
							<td>
							真实姓名：
					</td>
						<td>
							${paramMap.realName}
						</td>
					</tr>
					
								<tr class="gvItem">
						<td align="center">
								手机号码：
							</td>
							<td>
							${paramMap.mobilePhone}
					</td>
							<td>
								身份证：
					</td>
						<td>
						${paramMap.idNo}
						</td>
					</tr>
					
					
					
				<tr class="gvItem">
						<td align="center">
						  充值金额:
							</td>
							<td>
							${paramMap.rechargeMoney }
					</td>
							<td>
								交易编号:
					</td>
						<td>
							${paramMap.rechargeNumber}
						</td>
					</tr>
					
					
					
					
					
							<tr class="gvItem">
						<td align="center">
						线下充值备注:
							</td>
							<td>
							${paramMap.remark }
					</td>
							<td>
								提交时间:
					</td>
						<td>
							${paramMap.rechargeTime}
						</td>
					</tr>
					
					
					
									<tr class="gvItem">
						<td align="center" colspan="2">
						线下充值奖励:
							</td>
							<td colspan="2">
							<input type="text" name="paramMap.award" value="${paramMap.award }">% 奖：${awardmoney}
					</td>
					</tr>
					
					
				<tr class="gvItem">
						<td align="center" colspan="2">
						审核状态:
							</td>
							<td colspan="2">
							<a style="color: red;">
						<s:if test="paramMap.result==0">充值失败</s:if>
						<s:if test="paramMap.result==1">充值成功</s:if>
						<s:if test="paramMap.result==3">审核中</s:if>
						</a>
					</td>
					</tr>
					
							<tr class="gvItem">
						<td align="center" colspan="4">
						<s:if test="paramMap.result==3">
						<input type="button" value="审核通过" id="succ">
						<input type="button" value="审核失败" id="fail">
						</s:if>
						<s:fielderror fieldName="paramMap.allerror" />
							</td>
					</tr>
			
			</tbody>
		</table>
		</from>	
	</div>
					
					
					
					</span>
				</div>
			</div>
		</div>
	</body>
</html>
