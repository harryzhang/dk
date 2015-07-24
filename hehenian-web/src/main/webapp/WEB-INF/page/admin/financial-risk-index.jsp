	<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<html>
	<head>
		<title>投标记录管理首页</title>
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
							<td width="100" id="today" class="main_alll_h2">
								风险保障金记录
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
				<div style="padding-right: 10px; padding-left: 10px; padding-bottom: 10px; width: 1120px; padding-top: 10px; background-color: #fff;">
					<table style="margin-bottom: 8px;" cellspacing="0" cellpadding="0"
						width="100%" border="0">
						<tbody>
							<tr>
								<td class="f66" align="left" width="50%" height="36px">
									详细来源:
									<input id="resource" value="" />&nbsp;&nbsp; 
									时间:
									<input id="timeStart" class="Wdate" value="" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})"/>&nbsp;&nbsp;
									--
									<input id="timeEnd" class="Wdate" value="" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})"/>&nbsp;&nbsp;
									类型：
									<select id="riskType">
									   <option value="">--请选择--</option>
									   <option value="收入">收入</option>
									   <option value="支出">支出</option>
									</select>
									<input id="search" type="button" value="查找" name="search" />
								</td>
							</tr>
							<tr>
								<td class="f66" align="left" width="50%" height="36px">
									
									<input type="button" value="手动添加" onclick="addRisk();"/>
									<input type="button" value="手动扣除" onclick="deductedRisk();"/>
									<input id="excel" type="button" value="导出Excel"/>
								</td>
							</tr>
						</tbody>
					</table>
		             <span id="divList"><img src="../images/admin/load.gif" class="load" alt="加载中..." /></span>
					<div>
	</div>
</div>
			</div>
		</div>
	</body>
<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
<script type="text/javascript" src="../My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="../css/admin/popom.js"></script>
<script type="text/javascript">
			$(function(){
				initListInfo(param);
				$('#search').click(function(){
				   param["pageBean.pageNum"]=1;
				   initListInfo(param);
				});		
				
				$("#excel").click(function(){      
                     window.location.href=encodeURI(encodeURI("exportRiskList.do?resource="+$("#resource").val()+"&&timeStart="+$("#timeStart").val()+"&&timeEnd="+$("#timeEnd").val()+"&&riskType="+$("#riskType").val()));
                });		
			});			
			function initListInfo(praData){
				praData["paramMap.resource"] = $("#resource").val();
				praData["paramMap.timeStart"] = $("#timeStart").val();
				praData["paramMap.timeEnd"] = $("#timeEnd").val();
				praData["paramMap.riskType"] = $("#riskType").val();
		 		$.shovePost("queryRiskList.do",praData,initCallBack);
		 	}
		 	function initCallBack(data){
				$("#divList").html(data);
			}
			function addRisk(){
   			   var url="addRiskInit.do";
   			   ShowIframe("手动添加风险保障金",url,500,300);
   			}
   			function deductedRisk(){
   			   var url="deductedRiskInit.do";
   			   ShowIframe("手动扣除风险保障金",url,500,300);
   			}
   			function closeReresh(){
   			  window.location.href="queryRiskInit.do";
   			  ClosePop();
   			}
   			function close(){
   			  ClosePop();
   			}
</script>
</html>
