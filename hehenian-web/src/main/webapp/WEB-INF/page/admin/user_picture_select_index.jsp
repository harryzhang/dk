<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<html>
	<head>
		
		<style>
		</style>
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
			param['paramMap.userId'] = ${userId}
			param['paramMap.materAuthTypeId'] =$('#materAuthTypeId').val();
	   		$.post("queryselectinofor.do",param,initCallBack);
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
	<body>
		<div id="right"
			style="background-image: url; background-position: top; background-repeat: repeat-x;">
			<div style="padding: 15px 10px 0px 10px;">
		
		
		
			<div>
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
						     <td width="100" height="28" align="center" 
								class="main_alll_h2">
								<a href="javascript:void(0);">可选资料认证</a>
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
				<div>
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
				<hr/>
						</tr>
					</table>
				</div>
		
		
		
		
		
				<div
					style="padding-right: 10px; padding-left: 10px; padding-bottom: 10px; width: auto; padding-top: 10px; background-color: #fff;">
					<table style="margin-bottom: 8px;" cellspacing="0" cellpadding="0"
						width="100%" border="0">
						<tbody>
							<tr>
								<td class="f66" align="left" width="50%" height="36px">
									 证件种类：
									 <s:if test=""></s:if>
									 <s:select list="#{6:'房产',7:'购车',8:'结婚',9:'学历',10:'技术',11:'手机',12:'微博',13:'现场',14:'抵押',15:'担保',16:'其他'
									 }" theme="simple"  name="selectvalue" id="materAuthTypeId"></s:select>
									<input id="search" type="button" value="查找" name="search" class="scbtn"/>
								</td>
							</tr>
						</tbody>
					</table>
					<span id="dataInfo"> </span>
				</div>
			</div>
		</div>
	</body>
</html>
