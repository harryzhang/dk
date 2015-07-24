<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<html>
	<head>
		<title>借款管理首页</title>
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
					<table border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="100" id="today"  class="xxk_all_a">
								<a href="forPaymentDueInInit.do">待还款列表</a>
							</td>
							<td width="2">
								&nbsp;
							</td>
							<td width="100" id="today" height="28"  class="xxk_all_a">
								<a href="forPaymentInit.do">应收款列表</a>
							</td>
							<td width="2">
								&nbsp;
							</td>
							<td width="100" id="tomorrow"  class="xxk_all_a">
								<a href="forPaymentTotalInit.do">待收款列表</a>
							</td>
							<td width="2">
								&nbsp;
							</td>
							<td width="100" id="today" height="28"  class="main_alll_h2">
								<a href="hasRepayInit.do">已还款列表</a>
							<td width="100" id="tomorrow" class="white12">
							</td>
							<td width="2">
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
									用户名&nbsp;&nbsp;:
									<input id="userName" value="" maxlength="20"/><!--&nbsp;&nbsp;
									真实姓名:
									<input id="realName" value="" maxlength="20" />&nbsp;&nbsp; 
									
									-->&nbsp;&nbsp;&nbsp;&nbsp;
							<select id="flag">
							<option value="1" selected="selected">还款时间</option>
							<option value="2">到期时间</option>
							</select>&nbsp;&nbsp;
							:&nbsp;<input id="timeStart" class="Wdate" value="${strStart }" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})"/>&nbsp;&nbsp;
									--
							<input id="timeEnd" class="Wdate" value="${endStart }" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})"/>&nbsp;&nbsp;
							</td>
							</tr>
							<!--<tr>
							<td class="f66" align="left" width="50%" height="36px">
							还款时间:
									<input id="timeStart" class="Wdate" value="${strStart }" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})"/>&nbsp;&nbsp;
									--
									<input id="timeEnd" class="Wdate" value="${endStart }" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})"/>&nbsp;&nbsp;
							&nbsp;&nbsp;
							到期时间:
									<input id="timeStart1" class="Wdate" value="" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})"/>&nbsp;&nbsp;
									--
									<input id="timeEnd1" class="Wdate" value="" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})"/>&nbsp;&nbsp;
						
							</td>
							</tr>
							--><tr>
								<td class="f66" align="left" width="50%" height="36px">
									借款期数：
									<s:select id="deadline" list="borrowDeadlineList" listKey="selectValue" listValue="selectName" headerKey="-1" headerValue="--请选择--"></s:select>
									&nbsp;&nbsp;
									借款类型：
									<s:select id="borrowWay" list="#{-1:'--请选择--',1:'薪金贷',2:'生意贷',3:'业主贷',4:'实地考察借款',5:'机构担保借款'}"></s:select>
								      <!--  modify by houli 2013-05-02 不需要这个查询
								          还款状态：
									<s:select id="status" list="#{-1:'--请选择--',1:'未偿还',2:'已偿还',3:'偿还中'}"></s:select>
									--> 
									<input id="search" type="button" value="搜索" name="search" />
									<input id="excel" type="button" value="导出Excel" name="excel" />
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
<script type="text/javascript" src="../css/admin/popom.js"></script>
<script language="javascript" type="text/javascript" src="../My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
			$(function(){
				initListInfo(param);
				$('#search').click(function(){
					param["pageBean.pageNum"] = 1;
				   initListInfo(param);
				});
				
				$("#excel").click(function(){
				 window.location.href=encodeURI(encodeURI("exporthasRepay.do?userName="+$("#userName").val()+"&&realName="+$("#realName").val()+"&&timeStart="+$("#timeStart").val()+"&&timeEnd="+$("#timeEnd").val()+"&&timeStart1="+$("#timeStart1").val()+"&&timeEnd1="+$("#timeEnd1").val()+"&&deadline="+$("#deadline").val()+"&&borrowWay="+$("#borrowWay").val()));
				});
			});			
			function initListInfo(praData){
				praData["paramMap.userName"] = $("#userName").val();
				praData["paramMap.realName"] = $("#realName").val();
				praData["paramMap.timeStart"] = $("#timeStart").val();
				praData["paramMap.timeEnd"] = $("#timeEnd").val();
				praData["paramMap.borrowWay"] = $("#borrowWay").val();
				praData["paramMap.deadline"] = $("#deadline").val();
				praData["paramMap.status"] = $("#status").val();
				
				//--------add by houli  添加一个到期时间的查询
				praData["paramMap.flag"]=$("#flag").val(); 
				//------------
		 		$.shovePost("hasRepayList.do",praData,initCallBack);
		 	}
		 	function initCallBack(data){
				$("#divList").html(data);
			}
		 	function viewDetail(id){
		 	    var url = "queryByrepayId.do?repayId="+id;
		 	    ShowIframe("还款详情",url,800,450);
		 	}
</script>
</html>
