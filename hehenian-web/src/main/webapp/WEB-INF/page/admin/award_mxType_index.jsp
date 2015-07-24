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
	    );
	  //加载留言信息
	   function initListInfo(praData) {
		   	praData["startDate"] = $("#startDate").val();
   			praData["endDate"] = $("#endDate").val();
   			praData["level2userId"] = '${level2userId}';
   			praData["userId"] = '${userId}';
   			praData["level"] = '${level}';
   			praData["mxType"] = '${mxType}';
   			praData["month"] = $("#month").val();
	   		$.shovePost("queryAwardLevel2mxTypeInfo.do",praData,initCallBack);
   		}  <%--
	   //加载留言信息
	   function initListInfo(praData) {
   			praData["level2userId"] = '${level2userId}';
   			praData["userId"] = '${userId}';
	   		$.shovePost("queryIoRInfo.do",praData,initCallBack);
   		}
   		--%>
   		function initCallBack(data){
		 	$("#dataInfo").html(data);
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
				<div>
					<table border="0" cellspacing="0" cellpadding="0">
						<tr>
								<td width="160" height="28" class="xxk_all_a2">
									<a href="queryAwardLevel2${level}Init.do">经纪人提成奖励统计(${level==3?'投资人':'理财人' })</a>
								</td>
								<td width="2">
									&nbsp;
								</td>
								<td width="120" align="center"  class="${mxType==1?'main_alll_h3':'xxk_all_a3' }">
									<a href="queryAwardLevel2${level}mxType1Init.do?level2userId=${level2userId }&userId=${userId}">第一部分提成奖励</a>
								</td>
								<td width="2">
									&nbsp;
								</td>
								<td width="120" align="center" class="${mxType==1?'xxk_all_a3':'main_alll_h3' }">
									<a href="queryAwardLevel2${level}mxType2Init.do?level2userId=${level2userId }&userId=${userId}">第二部分提成奖励</a>
								</td>
								<td width="2">
									&nbsp;
								</td>
								<td width="150"  class="main_alll_h3">
									<a href="queryIoRInit.do?level2userId=${level2userId }&userId=${userId}&level=${level}">产生提成奖金的投资记录</a>
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
										贡献时间：
									<s:if test="#request.mxType==1">
										<input id="startDate" type="text" onclick="startDate();"/>&nbsp;&nbsp;
										—&nbsp;&nbsp;
										<input id="endDate" type="text" onclick="endDate();"/>&nbsp;&nbsp; 
									</s:if>
									<s:else>
										<s:select list="monthList" id="month" listKey="id" listValue="name"></s:select>
									</s:else>
									<input id="search" type="button" value="搜索" name="search" />
								</td>
							</tr>
							<tr>
								<td class="f66" align="left" width="50%" height="36px">
									提成奖励：${paramMap.moneys }
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
