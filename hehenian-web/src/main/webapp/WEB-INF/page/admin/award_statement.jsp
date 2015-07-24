<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<html>
	<head>
		<title>结算</title>
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
				initListInfo(param);				
			});
			
		function initListInfo(praData){			
			param["paramMap.economyId"] =$("#economyId").val();	 
	 		$.shovePost("awardSettlement.do",praData,initCallBack);
	 	}
	 	
	 	function initCallBack(data){
	 	  
			$("#dataInfo").html(data);
		}
   		function mingXi(){
   			$("#one").removeClass("xxk_all_a").addClass("main_alll_h2");
   			$("#two").removeClass("main_alll_h2").addClass("xxk_all_a");
   			$("#secend").css('display','block');
   			$("#first").css('display','none');
		}
		function jieSuan(){
			if($("#notuse").val()<=0){
				alert("该用户已经全部结算完成");
				return ;
			}
			$("#two").removeClass("xxk_all_a").addClass("main_alll_h2");
   			$("#one").removeClass("main_alll_h2").addClass("xxk_all_a");
   			$("#first").css('display','block');
   			$("#secend").css('display','none');
		}
		function submitData(){
		 	param["paramMap.userId"] =$("#id").val();	
		 	param["paramMap.handleSum"] =$("#notuse").val();	
		 	param["paramMap.remark"] =$("#remark").val();
		 	$.post("addEconomyAwardDetail.do",param,function(data){
		 		if(data==2){
		 			alert("结算失败")
		 		}else{
		 			alert("结算成功");
		 			param["pageBean.pageNum"] = 1;
		 			initListInfo(param);
		 		}
		 	});
		}
		
	</script>

	</head>
	<body>
		<div id="right" >
				<div style="padding: 15px 10px 0px 10px;">
					<div>
						<table border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="100" height="28" class="main_alll_h2" id="one">
									<a href="javascript:jieSuan()">结算</a>
								</td>
								<td width="2">
									&nbsp;
								</td>
								<td width="100" class="xxk_all_a" id="two">
										<a href="javascript:mingXi()">结算明细</a>
									</td>
									<td width="2">
									<input type="hidden" value="${paramMap.economyId}" id="economyId"/>
										&nbsp;
									</td>
						</tr>
					</table>
				</div>
				
						<span id="dataInfo"> </span>
	
	
			</div>
		</div>
	</body>
</html>
