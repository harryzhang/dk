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
		<script type="text/javascript" src="../common/dialog/popup.js"></script>
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
		    param["paramMap.userName"] = $("#userName2").val();
			param["paramMap.startDate"] = $("#startDate").val();
			param["paramMap.endDate"] = $("#endDate").val();
	   		$.shovePost("queryEconomyList.do",praData,initCallBack);
   		}
   		
   		function initCallBack(data){
		 	$("#dataInfo").html(data);
		 	$(".enable").click(function(){
		 	   
		 		var even = $(this);
		 		var rId = even.attr("rId");
		 		var enable = even.attr("enable");
		 		if(enable==1){
		 		if(!window.confirm("确定要解除关系吗?")){
		 			return;
		 		}
			 		$.shovePost("updateRelation.do",{id:rId},function(data){
			 			if(data.msg==1){
			 				alert("解除关系成功！");
			 				even.html("重置关系");
			 				even.attr("enable",2);
			 				history.go(0);
			 				return;
			 			}
			 			alert(data.msg);
			 		});
			 		
		 		}else{
		 			ShowIframe("关联团队长","relationLevelInit.do?id="+rId,1000,800);
		 			
		 		}
		 	});
   		}
   		
   		//分页
   		function pageInfo(pageId){
   			param["pageBean.pageNum"] = pageId;
   			initListInfo(param);
   		}
   		
   		//判断是否有选中项
   		function checked(str){
   			var c = 0;
   			$(".adminId").each( function(i, n){
				if(n.checked){
					c = 1;
				}
			});
			if(c==0){
				alert("请先选中您要"+str+"的项！");
				return false;
			}
			return true;
   		}
   		
   	
   		//全选
   		function checkAll(e){
	   		if(e.checked){
	   			$(".adminId").attr("checked","checked");
	   		}else{
	   			$(".adminId").removeAttr("checked");
	   		}
   		}
   		function startDate(){
			return showCalendar('startDate', '%Y-%m-%d', '24', true, 'startDate');
		}
		
		function endDate(){
			return showCalendar('endDate', '%Y-%m-%d', '24', true, 'endDate');
		}
		
		function popCell(data){
			if(data.msg==1){
 				alert("重置成功！");
 				initListInfo(param);
 				ClosePop();
 				return;
 			}
 			alert(data.msg);
		}

		//管理客户
		function relationColumn(level2Id){//level2Id经济人id
			ShowIframe("关联客户","relationLeveladdInit.do?level2Id="+level2Id,1000,800);
			};
	</script>

	</head>
	<body>
		<div id="right">
				<div style="padding: 15px 10px 0px 10px;">
					<div>
						<table border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="100" height="28" class="main_alll_h2">
									<a href="queryEconomyInit.do">经纪人列表</a>
								</td>
								<td width="2">
									&nbsp;
								</td>
								<td width="100" class="xxk_all_a">
										<a href="queryLeve2SumInit.do">经纪人提成</a>
									</td>
									<td width="2">
										&nbsp;
									</td>
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
									经纪人账号：
									<input id="userName2" type="text" />&nbsp;&nbsp; 
								 	添加时间:
									<input id="startDate" size="20" maxlength="20" type="text" onclick="startDate();" value="${startDate }" /> —
									<input id="endDate" size="20" maxlength="20" type="text" onclick="endDate();" value="${endDate }" />
									<input id="search" type="button" value="搜索" name="search" />
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
