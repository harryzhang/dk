<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.sp2p.util.DateUtil"%>
<%@ include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<title>借款产品参数</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="This is my page" />
		<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
		<link href="../css/css.css" rel="stylesheet" type="text/css" />
		
		<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
		<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
		<script type="text/javascript" src="../common/dialog/popup.js"></script>
		
		<script>

    		   $(function(){
					show(1);
			    	if($("#img").val() == ''){
			    		$("#img").attr("src","../images/NoImg.GIF");
				    }	        
		       }); 
		       $("#li_work").click(function() {
		      		alert("3435435");
					window.location.href = 'queryUserMangework.do?uid=${map.userId}';
				});
				function closeJbox(){
					window.parent.closeJbox();
				}
				function show(id){

					
					
					//for(var i =1;i<=3;i++){
					//	if(id == i){
					//		$("#table_"+i).css("display","block");
					//	}else{	
					//		$("#table_"+i).css("display","none");
					//	}
					//}
				}
		</script>

		<style type="text/css">
			#id_a{font-size:16px;}
			#id_a ul li{float:left; width:100px;margin-left:50px;cursor: pointer;}
			table tr td{width:80px;border:1px solid #666666; padding:8px 18px;}
			#table_3 tr td{width:100px;line-height:1.8;}
			.userManage_left{padding-left:30px;}
			.userManageMoney_table_02 td{width:60px;border:1px solid #666666;}
			.userManage_info_k{width:1px;border-top: 0px;border-bottom: 0px; padding: 8px 10px;}
		</style>
	</head>
	<body >
		<div id="id_a">
			<ul style="margin-bottom:10px;">
				<li><a href="queryUserInfoinner.do?i=${id}">会员资料</a></li>
				<li>账户资金</li>
				<li><a href="queryUserTouzInfoinner.do?i=${id}">投资借款</a></li>
<%--				<li onclick="show(1);">会员资料</li>--%>
<%--				<li onclick="show(2);">账户资金</li>--%>
<%--				<li onclick="show(3);">投资借款</li>--%>
			</ul>
		</div>
		</br>

	<div id="table_2">
	<table style="margin-left:40px;margin-top:20px;">
			<tr>
				<td>总资产：</td>
				<td>${ map_zij.freezeSum+ map_zij.usableSum+ map_zij.dueinSum}</td>
				<td class="userManage_info_k"></td>
				<td style="width:120px;">净收益：</td>
				<td>-</td>
			</tr>
			<tr>
				<td>可用余额：</td>
				<td>${ map_zij.usableSum}</td>
				<td class="userManage_info_k"></td>
				<td>加权收益率(总充值)：</td>
				<td>-</td>
			</tr>
			<tr>
				<td>冻结总额：</td>
				<td>${ map_zij.freezeSum}</td>
				<td class="userManage_info_k"></td>
				<td>待收金额：</td>
				<td>${ map_zij.dueinSum }</td>
			</tr>
			<tr>
				<td>充值总额：</td>
				<td>-</td>
				<td class="userManage_info_k"></td>
				<td>可用信用额度：</td>
				<td>${ map_zij.creditLimit }</td>
			</tr>
			<tr>
				<td>提现总额：</td>
				<td>-</td>
			</tr>
			
		</table>
		<table class="userManageMoney_table_02" style="margin-left:40px;margin-top:20px;">
			<tr>
				<td colspan="6">最新资金变动：</td>
			</tr>
			<tr>
				<td>时间</td>
				<td>影响金额</td>
				<td>可用余额</td>
				<td>交易方</td>
				<td>交易类型</td>
				<td>备注</td>
			</tr>
			<s:iterator value="#request.map_money_change" id="a" status="st">
			<tr>
				<td><s:property value="#a.recordTime" /></td>
				<td><s:property value="#a.handleSum" /></td>
				<td><s:property value="#a.usableSum" /></td>
				<td><s:property value="#a.traderName" /></td>
				<td><s:property value="#a.fundMode" /></td>
				<td><s:property value="#a.remarks" /></td>
			</tr>
			</s:iterator>
			
			
			
		</table>
	</div>
	
	</div>
	</body>
</html>
