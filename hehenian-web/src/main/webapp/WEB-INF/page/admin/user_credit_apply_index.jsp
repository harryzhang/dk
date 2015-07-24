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
		<link rel="stylesheet" type="text/css" href="../common/date/calendar.css"/>
		<script type="text/javascript" src="../common/date/calendar.js"></script>
		<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
		<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
		<script language="javascript" type="text/javascript" src="../My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript" language="javascript">
	  	$(function(){
	    	param["pageBean.pageNum"]=1;
	    	
		    initListInfo(param);
		    
		    $("#search").click(function(){
		    param["pageBean.pageNum"] = 1;
			initListInfo(param);
		    	});
		    }
	    )
	    
	     //加载留言信息
	   function initListInfo(praData) {
	     /*
		    param["paramMap.userName"] = $("#userName").val();
			param["paramMap.auditStatus"] = $("#auditStatus").val();
			param["paramMap.serviceManName"] = $("#serviceManName").val();
			param["paramMap.realName"] = $("#realName").val();
			param['paramMap.userId'] = ${userId}
			*/
			param["paramMap.userName"] = $("#userName").val();
			param["paramMap.starttime"] = $("#starttime").val();
			param["paramMap.endtime"] = $("#endtime").val();
			param["paramMap.austatus"] = $("#materAuthTypeId").val();
			
	   		$.post("querycreditinofor.do",param,initCallBack);
   		}
   		
   		function initCallBack(data){
		 	$("#dataInfo").html(data);
   		}
   		
   		function selectStartDate(){
			return showCalendar('startDate', '%Y-%m-%d', '24', true, 'startDate');
		}
		
		function selectEndDate(){
			return showCalendar('endDate', '%Y-%m-%d', '24', true, 'endDate');
		}
		</script>

	</head>
	<body style="min-width: 1000px">
		<div id="right">
			<div style="padding: 15px 10px 0px 10px;">
				<div>
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
						   <td width="100" height="28" class="main_alll_h2">
								<a href="javascript:void(0);">额度申请管理</a>
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
				<div
					style="padding-right: 10px; padding-left: 10px; padding-bottom: 10px; width: 1120px; padding-top: 10px; background-color: #fff;">
					<table style="margin-bottom: 8px;" cellspacing="0" cellpadding="0"
						width="100%" border="0">
						<tbody>
							<tr>
							<td class="f66" align="left" width="50%" height="36px">
							      用户名：
							     <input id="userName"  name="paramMap.userName" />&nbsp;&nbsp; &nbsp;&nbsp; 
							     申请时间
							    <input id="starttime"  class="Wdate" name="paramMap.starttime" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})"/>
							   --
							    <input id="endtime"  class="Wdate" name="paramMap.endtime" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})"/>&nbsp;&nbsp; 
								
									 状态：<s:select list="#{-1:'全部',3:'成功',2:'失败',1:'审核中'}" theme="simple" value="#request.types" name="paramMap.materAuthTypeId" id="materAuthTypeId" ></s:select>
									<input id="search" type="button" value="查找" name="search" class="scbtn"/>
								</td>
							</tr>
						</tbody>
					</table>
					<span id="dataInfo"> <img src="../images/admin/load.gif" class="load" alt="加载中..." /></span>
				</div>
			</div>
		</div>
	</body>
</html>
