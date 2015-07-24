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
		<link href="../common/date/calendar.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
		<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
		<script type="text/javascript" src="../common/date/calendar.js"></script>
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
		    param["userName"] = $("#userName").val();
		    param["realName"] = $("#realName").val();
			param["startDate"] = $("#startDate").val();
			param["endDate"] = $("#endDate").val();
	   		$.shovePost("queryrelationinvestorsInfo.do",praData,initCallBack);
   		}
   		
   		function initCallBack(data){
		 	$("#dataInfo").html(data);
		 	$("#butTrue").click(function(){
		 		var parentId = $("input[name='relation']:checked").val();
		 		var id = '${id}';
		 		$.shovePost("updateInvestorParentId.do",{id:id,parentId:parentId},function(data){
		 			window.parent.popCell(data);
		 		});
		 	});
   		}
   		
   		//分页
   		function pageInfo(pageId){
   			param["pageBean.pageNum"] = pageId;
   			initListInfo(param);
   		}
   		
   		function startDate(){
			return showCalendar('startDate', '%Y-%m-%d', '24', true, 'startDate');
		}
		
		function endDate(){
			return showCalendar('endDate', '%Y-%m-%d', '24', true, 'endDate');
		}
	</script>

	</head>
	<body>
		<div id="right"
			style="background-image: url; background-position: top; background-repeat: repeat-x;">
			<div style="padding: 15px 10px 0px 10px;">
				<div
					style="padding-right: 10px; padding-left: 10px; padding-bottom: 10px; width: auto; padding-top: 10px; background-color: #fff;">
					<table style="margin-bottom: 8px;" cellspacing="0" cellpadding="0"
						width="100%" border="0">
						<tbody>
							<tr>
								<td class="f66" align="left" width="50%" height="36px">
									投资人账号：
									<input id="userName" type="text" />&nbsp;&nbsp; 
									姓名:
									<input id="realName" type="text" />&nbsp;&nbsp;
									 添加时间:
									<input id="startDate" size="20" maxlength="20" type="text" onclick="startDate();" value="${startDate }" /> —
									<input id="endDate" size="20" maxlength="20" type="text" onclick="endDate();" value="${endDate }" />
									<input id="search" type="button" value="确定" name="search" />
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
