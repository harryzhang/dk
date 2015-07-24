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
	    
	    function updatestatus(data){
   		      if(!window.confirm("确认结算?")){
   		         return ;
   		      }
   		      param["paramMap.id"] = data
               $.post("updateMoneyInfo.do",param,function(data){
                  if(data.msg=="1"){
                    alert("结算成功");
                    window.location.reload();
                    return;
                  }
                  alert(data.msg);
               });   		
   		
   		}
	    //加载留言信息
	   function initListInfo(praData) {
			praData["paramMap.level1userName"] = $("#level1userName").val();
			praData["paramMap.realName"] = $("#realName").val();
			praData["paramMap.startTime"] = $("#startTime").val();
			praData["paramMap.endTime"] = $("#endTime").val();
	   		$.shovePost("quereyGroupCloseMoneyInfo.do",praData,initCallBack);
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
		<div id="right"
			style="background-image: url; background-position: top; background-repeat: repeat-x;">
			<div style="padding: 15px 10px 0px 10px;">
				<div>
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="150" height="28" align="center"  
								class="main_alll_h2">
								<a href="quereyGroupCloseMoneyInit.do">团队长月结算管理</a>
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
					style="padding-right: 10px; padding-left: 10px; padding-bottom: 10px; width: auto; padding-top: 10px; background-color: #fff;">
					<table style="margin-bottom: 8px;" cellspacing="0" cellpadding="0"
						width="100%" border="0">
						<tbody>
							<tr>
								<td class="f66" align="left" width="50%" height="36px">
									团队长账号：
									<input id="level1userName" />&nbsp;&nbsp; 
									真实姓名:
									<input id="realName" />&nbsp;&nbsp; 
									开始时间：
									<input id="startTime" class="Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})"/>&nbsp;&nbsp; 
									结算时间：
									<input id="endTime" class="Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})"/>&nbsp;&nbsp; 
									<input id="search" type="button" value="搜索" name="search" />
								</td>
							</tr>
							<tr>
							</tr>
						</tbody>
					</table>
					<span id="dataInfo"> </span>
				</div>
			</div>
		</div>
	</body>
</html>
