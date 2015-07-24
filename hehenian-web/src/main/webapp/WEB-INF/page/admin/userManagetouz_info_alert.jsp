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
			table tr td{border:1px solid #666666; padding:8px 25px;}
			#table_3 tr td{width:100px;line-height:1.8;}
			.userManage_left{padding-left:30px;}
			.userManage_touz_table1 tr td{width:}
			.userManage_touz_table1_td_1{width:70px;}
			.userManage_touz_table1_td_2{padding:8px 15px;border-top: 0px; border-bottom: 0px;}
		</style>
	</head>
	<body >
		<div id="id_a">
			<ul style="margin-bottom:10px;">
				<li><a href="queryUserInfoinner.do?i=${id}">会员资料</a></li>
				<li><a href="queryUserMoneyInfoinner.do?i=${id}">账户资金</a></li>
				<li>投资借款</li>
<%--				<li onclick="show(1);">会员资料</li>--%>
<%--				<li onclick="show(2);">账户资金</li>--%>
<%--				<li onclick="show(3);">投资借款</li>--%>
			</ul>
		</div>
		</br>

	<div id="table_2">
		<table class="userManage_touz_table1" style="margin-left:40px;margin-top:20px;width:600px;">
			<tr>
				<td class="userManage_touz_table1_td_3">发布借款标的：</td><td class="userManage_touz_table1_td_1"><s:if test="#request.borrowRecordMap_touz.publishBorrow !=''">${borrowRecordMap_touz.publishBorrow}</s:if><s:else>0</s:else></td>
				<td class="userManage_touz_table1_td_2"></td><td>成功借款笔数：</td><td class="userManage_touz_table1_td_1"><s:if test="#request.borrowRecordMap_touz.successBorrow !=''">${borrowRecordMap_touz.successBorrow}</s:if><s:else>0</s:else></td>
			</tr>
			<tr>
				<td class="userManage_touz_table1_td_3">还清笔数：</td><td class="userManage_touz_table1_td_1"><s:if test="#request.borrowRecordMap_touz.repayBorrow !=''">${borrowRecordMap_touz.repayBorrow}</s:if><s:else>0</s:else></td>
				<td class="userManage_touz_table1_td_2"></td><td>逾期次数：</td><td class="userManage_touz_table1_td_1"><s:if test="#request.borrowRecordMap_touz.hasFICount !=''">${borrowRecordMap_touz.hasFICount}</s:if><s:else>0</s:else></td>
			</tr>
			<tr>
				<td class="userManage_touz_table1_td_3">严重逾期次数：</td><td class="userManage_touz_table1_td_1"><s:if test="#request.borrowRecordMap_touz.badFICount !=''">${borrowRecordMap_touz.badFICount}</s:if><s:else>0</s:else></td>
				<td class="userManage_touz_table1_td_2"></td><td>共借入：</td><td class="userManage_touz_table1_td_1"><s:if test="#request.borrowRecordMap_touz.borrowAmount !=''">${borrowRecordMap_touz.borrowAmount}</s:if><s:else>0</s:else></td>
			</tr>
			<tr>
				<td class="userManage_touz_table1_td_3">待还金额：</td><td class="userManage_touz_table1_td_1"><s:if test="#request.borrowRecordMap_touz.forRepayPI !=''">${borrowRecordMap_touz.forRepayPI}</s:if><s:else>0</s:else></td>
				<td class="userManage_touz_table1_td_2"></td><td>逾期金额：</td><td class="userManage_touz_table1_td_1"><s:if test="#request.borrowRecordMap_touz.hasI !=''">${borrowRecordMap_touz.hasI}</s:if><s:else>0</s:else></td>
			</tr>
			<tr>
				<td class="userManage_touz_table1_td_3">共借出：</td><td class="userManage_touz_table1_td_1"><s:if test="#request.borrowRecordMap_touz.investAmount !=''">${borrowRecordMap_touz.investAmount}</s:if><s:else>0</s:else></td>
				<td class="userManage_touz_table1_td_2"></td><td>待收金额：</td><td class="userManage_touz_table1_td_1"><s:if test="#request.borrowRecordMap_touz.forPI !=''">${borrowRecordMap_touz.forPI}</s:if><s:else>0</s:else></td>
			</tr>
		
<%--			<tr>--%>
<%--			    <td>发布借款标的：<s:if test="#request.borrowRecordMap_touz.publishBorrow !=''">${borrowRecordMap_touz.publishBorrow}</s:if><s:else>0</s:else></td>--%>
<%--			    <td>成功借款笔数：<s:if test="#request.borrowRecordMap_touz.successBorrow !=''">${borrowRecordMap_touz.successBorrow}</s:if><s:else>0</s:else></td>--%>
<%--			    <td>还清笔数：<s:if test="#request.borrowRecordMap_touz.repayBorrow !=''">${borrowRecordMap_touz.repayBorrow}</s:if><s:else>0</s:else></td>--%>
<%--			    <td>逾期次数：<s:if test="#request.borrowRecordMap_touz.hasFICount !=''">${borrowRecordMap_touz.hasFICount}</s:if><s:else>0</s:else></td>--%>
<%--			    <td>严重逾期次数：<s:if test="#request.borrowRecordMap_touz.badFICount !=''">${borrowRecordMap_touz.badFICount}</s:if><s:else>0</s:else></td>--%>
<%--			 </tr>	--%>
<%--			 <tr>--%>
<%--			    <td>共借入：<s:if test="#request.borrowRecordMap_touz.borrowAmount !=''">${borrowRecordMap_touz.borrowAmount}</s:if><s:else>0</s:else> </td>--%>
<%--			    <td>待还金额：<s:if test="#request.borrowRecordMap_touz.forRepayPI !=''">${borrowRecordMap_touz.forRepayPI}</s:if><s:else>0</s:else></td>--%>
<%--			    <td>逾期金额：<s:if test="#request.borrowRecordMap_touz.hasI !=''">${borrowRecordMap_touz.hasI}</s:if><s:else>0</s:else></td>--%>
<%--			    <td>共借出：<s:if test="#request.borrowRecordMap_touz.investAmount !=''">${borrowRecordMap_touz.investAmount}</s:if><s:else>0</s:else></td>--%>
<%--			    <td>待收金额：<s:if test="#request.borrowRecordMap_touz.forPI !=''">${borrowRecordMap_touz.forPI}</s:if><s:else>0</s:else></td>--%>
<%--			 </tr>--%>
		</table>
	</div>
	
	</body>
</html>
