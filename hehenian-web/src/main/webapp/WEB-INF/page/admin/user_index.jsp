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
		<script type="text/javascript" language="javascript">
	  	$(function(){
	    	param["pageBean.pageNum"]=1;
		    initListInfo(param);
		    $("#search").click(function(){
		    param["pageBean.pageNum"] = 1;
			initListInfo(param);
		    	});
		    }
	    );
	    
	     //加载留言信息
	   function initListInfo(praData) {
		    param["paramMap.userName"] = $("#userName").val();
			param["paramMap.auditStatus"] = $("#auditStatus").val();
			param["paramMap.adminName"] = $("#adminName").val();
			param["paramMap.realName"] = $("#realName").val();
			param["paramMap.certificateName"] = $("#certificateName").val();
			/*
			$.shovePost("tongji.do",praData,function(){
			    $("#dataInfo").html(data);    
			});
			*/
	   		$.shovePost("rechargeecordsInf.do",praData,initCallBack);
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
						  <td width="100" height="30">
								<a href="javascript:void(0);" class="main_alll_h2">基本资料认证</a>
							</td>
							<td width="2">&nbsp;
								
							</td>
							<td>&nbsp;
								
							</td>
						</tr>
					</table>
				</div>
				<div style="padding-right: 10px; padding-bottom: 10px; width: 1120px; padding-top: 10px; background-color: #fff;">
					<table style="margin-bottom: 8px;" cellspacing="0" cellpadding="0"
						width="100%" border="0">
						<tbody>
						<!-- - 
						   <tr><td id="tongji"></td></tr>-->
							<tr>
								<td class="f66" align="left" width="50%" height="36px">
									会员名称:
									<input id="userName" name="paramMap.userName" />&nbsp;&nbsp; 
									跟踪审核
									<input id="adminName" name="paramMap.adminName" />&nbsp;&nbsp; 
									真实姓名：
									<input id="realName" type="text" name="paramMap.realName"/>
									状态：
									<s:select id="auditStatus" list="#{-1:'-请选择-',1:'待审核',3:'成功',2:'失败'}" value="#request.types"></s:select>
									认证：
									<s:select id="certificateName" list="#{-1:'-请选择-',1:'身份认证',2:'工作认证',4:'信用报告认证',3:'居住地认证',5:'收入认证'}"></s:select>
									<input id="search" type="button" value="查找" name="search" />
								</td>
							</tr>
						</tbody>
					</table>
					<span id="dataInfo"><img src="../images/admin/load.gif" class="load" alt="加载中..." /> </span>
				</div>
			</div>
		</div>
	</body>
</html>
