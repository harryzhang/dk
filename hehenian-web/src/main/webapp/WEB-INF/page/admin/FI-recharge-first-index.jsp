<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<html>
	<head>
		<title>财务管理-充值记录</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="This is my page" />
		<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
		<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
		<script language="javascript" type="text/javascript" src="../My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript" src="../css/admin/popom.js"></script>
		<script type="text/javascript">
			$(function(){
				param["pageBean.pageNum"] = 1;
				initListInfo(param);
				$("#bt_search").click(function(){
					param["pageBean.pageNum"] = 1;
					initListInfo(param);
				});
				
				
			});
			
			function initListInfo(param){
				param["paramMap.userName"] = $("#userName").val();
				param["paramMap.reStartTime"] = $("#reStartTime").val();
				param["paramMap.reEndTime"] = $("#reEndTime").val();
				param["paramMap.rechargeType"] = $("#rechargeType").val();
		 		$.shovePost("queryRechargeFirstList.do",param,initCallBack);
		 	}
		 	
		 	function initCallBack(data){
				$("#dataInfo").html(data);
			}
			
			function checkAll(e){
		   		if(e.checked){
		   			$(".helpId").attr("checked","checked");
		   		}else{
		   			$(".helpId").removeAttr("checked");
		   		}
   			}
   			
   			function show(id){
   			  var url = "queryOneFirstChargeDetails.do?rechargeId="+id;
              ShowIframe("充值详情显示",url,600,600);
   			}
						
		</script>
	</head>
	<body>
		<div id="right">
			<div style="padding: 15px 10px 0px 10px;">
				<div>
					<table  border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="120" height="28" class="xxk_all_a">
								<a href="queryRechargeRecordInit.do">充值记录</a>
							</td>
							<td width="2">
								&nbsp;
							</td>
							<td width="120" class="main_alll_h2">
								<a href="queryRechargeFirstInit.do">第一次充值查询</a>
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
					<div
						style="padding-right: 10px; padding-left: 10px; padding-bottom: 10px; width: 1120px; padding-top: 10px; background-color: #fff;">
						<table style="margin-bottom: 8px;" cellspacing="0" cellpadding="0"
							width="100%" border="0">
							<tbody>
								<tr>
									<td class="f66" align="left" width="50%" height="36px">
										用户名：&nbsp;&nbsp;
										<input id="userName" name="paramMap.username" type="text"/>
										&nbsp;&nbsp;
										充值时间：
										<input id="reStartTime" name="paramMap.rechargeTime" type="text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})"/>
										&nbsp;&nbsp;
										到
										<input id="reEndTime" name="paramMap.rechargeTime" type="text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})"/>
										&nbsp;&nbsp;
										充值类型：
										<s:select id="rechargeType" list="rechargeTypes" name="paramMap.rechargeType" listKey="typeId" listValue="typeValue" headerKey="-100" headerValue="--请选择--" />
										&nbsp;&nbsp;
										
										<input id="bt_search" type="button" value="查 找"  />
									</td>
								</tr>
							</tbody>
						</table>
						<span id="dataInfo"> </span>
					</div>
				</div>
			</div>
			</div>
	</body>
</html>
