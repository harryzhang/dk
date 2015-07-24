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
		    praData["level2userName"] = $("#level2userName").val();
			praData["userName"] = $("#userName").val();
			praData["realName"] = $("#realName").val();
			praData["level"] = '${level}';
			param["level2Id"] = '${level2userId}';
	   		$.shovePost("queryAwardLevel2Info.do",praData,initCallBack);
   		}
   		
   		function initCallBack(data){
		 	$("#dataInfo").html(data);
   		}
   		
   		//分页
   		function pageInfo(pageId){
   			param["pageBean.pageNum"] = pageId;
   			initListInfo(param);
   		}
	</script>

	</head>
	<body>
		<div id="right">
			<div style="padding: 15px;">
				<div>
					<table border="0" cellspacing="0" cellpadding="0">
						<tr>
						<td width="150" height="28" align="center"  
								class="xxk_all_a3">
								<a href="queryLeve2SumInit.do">经济人提成奖励管理</a>
							</td>
							<td width="2">
								&nbsp;
							</td>
							<td width="150" height="28" class="${level==3?'main_alll_h3':'xxk_all_a3' }">
								<a href="queryAwardLevel23Init.do?level2userId=${level2userId}">经纪人提成列表(投资人)</a>
							</td>
							<td width="2">&nbsp;
								
							</td>
							<td width="150" height="28" class="${level==3?'xxk_all_a3':'main_alll_h3' }">
								<a href="queryAwardLevel24Init.do?level2userId=${level2userId}">经纪人提成列表(理财人)</a>
							</td>
							<td>&nbsp;
								
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
									经纪人账号：
									<input id="level2userName" type="text"/>&nbsp;&nbsp; 
									${level==3?'投资人':'理财人' }账号：
									<input id="userName" type="text"/>&nbsp;&nbsp; 
									${level==3?'投资人':'理财人' }姓名：
									<input id="realName" type="text"/>&nbsp;&nbsp; 
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
