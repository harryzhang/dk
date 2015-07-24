<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>借款管理-招标中</title>
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
						<td width="100" id="today" height="28" class="xxk_all_a"><a href="lateRepayInit.do">逾期的还款</a></td>
						<td width="2">&nbsp;</td>
<%--						<td width="100" id="today" height="28" class="xxk_all_a"><a href="overduePaymentInit.do">逾期代偿</a></td>--%>
<%--						<td width="2">&nbsp;</td>--%>
<%--						<td width="100" height="28" class="xxk_all_a"><a href="overduePaymentInit.do">待回购</a></td>--%>
<%--						<td width="2">&nbsp;</td>--%>
						<td width="100" height="28" class="main_alll_h2">还款详情</td>
						<td width="2">&nbsp;</td>
					</tr>
				</table>
			</div>

			<div class="tab">
				<table cellspacing="1" cellpadding="3">
					<tr>
						<td class="blue12 left">用户名：${map.borrower} <input type="hidden" id="repayId" value="${map.repayId}" /></td>
						<td class="blue12 left">欠款总额：${map.forSum}元</td>
					</tr>
					<tr>
						<td colspan="2" class="blue12 left">借款标题： <a href="${frontUrl}financeDetail.do?id=${map.borrowId}" target="_blank">${map.borrowTitle}</a></td>
					</tr>
					<tr>
						<td colspan="2" class="blue12 left">本期还款逾期情况：<br />
							<table id="gvNews" style="width: 100%; color: #333333;" cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="0">
								<tbody>
									<tr class=gvHeader>
										<th scope="col">逾期天数</th>
										<th scope="col">应还金额</th>
										<th scope="col">逾期罚金</th>
										<th scope="col">总还款</th>
									</tr>
									<tr align="center" class="gvItem">
										<td>${map.lateDay}</td>
										<td>${map.stillPI}</td>
										<td>${map.lateFI}</td>
										<td>${map.totalSum}</td>
									</tr>
								</tbody>
							</table></td>
						<td align="center" class="f66"></td>
					</tr>
				</table>
				<div id="content">
					<table>
						<tr>
							<td colspan="2" class="blue12 left">催收记录</td>
						</tr>
						<tr>
							<td colspan="2" class="blue12 left">催收结果： <br />
							<textarea style="margin-left:80px;" id="colResult" cols="30" rows="6"></textarea></td>
						</tr>
						<tr>
							<td colspan="2" class="blue12 left">署名备注：<span style="margin-left:15px;"><input id="remark" type="text" />
							</span></td>
						</tr>
						<tr>
							<td colspan="2" align="left" style="padding-left: 30px;">
								<button id="btn_save" style="background-image: url('../images/admin/btn_queding.jpg'); width: 70px; border: 0; height: 26px"></button></td>
						</tr>
					</table>
				</div>
				<br />
			</div>
		</div>
	</div>
	<div style="padding: 15px 10px 0px 10px;">
		客服通知记录
		<p />
		<table id="gvNews" style="width: 100%; color: #333333;" cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="0">
			<tbody>
				<tr class=gvHeader>
					<th style="width: 35px;" scope="col">序号</th>
					<th style="width: 150px;" scope="col">沟通时间</th>
					<th style="width: 150px;" scope="col">沟通记录</th>
				</tr>
				<s:if test="#request.serviceList==null || #request.serviceList.size==0">
					<tr align="center" class="gvItem">
						<td colspan="3">暂无数据</td>
					</tr>
				</s:if>
				<s:else>
					<s:iterator value="#request.serviceList" var="service">
						<tr class="gvItem">
							<td align="center">${service.id}</td>
							<td align="center">${service.serviceTime}</td>
							<td align="center">${service.serviceContent}</td>
						</tr>
					</s:iterator>
				</s:else>
			</tbody>
		</table>
	</div>
	<div style="padding: 15px 10px 0px 10px;" id="collection">
		催款进度记录
		<p />
		<table id="gvNews" style="width: 100%; color: #333333;" cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="0">
			<tbody>
				<tr class=gvHeader>
					<th style="width: 35px;" scope="col">序号</th>
					<th style="width: 150px;" scope="col">署名备注</th>
					<th style="width: 150px;" scope="col">催收时间</th>
					<th style="width: 150px;" scope="col">催收结果</th>
					<th style="width: 150px;" scope="col">操作</th>
				</tr>
				<s:if test="#request.collectionList==null || #request.collectionList.size==0">
					<tr align="center" class="gvItem">
						<td colspan="5">暂无数据</td>
					</tr>
				</s:if>
				<s:else>
					<s:iterator value="#request.collectionList" var="collection">
						<tr class="gvItem">
							<td align="center">${collection.id}</td>
							<td align="center">${collection.remark}</td>
							<td align="center">${collection.collectionDate}</td>
							<td align="center">${collection.colResult}</td>
							<td><a href="javascript:delCollection(${collection.id});">删除</a></td>
						</tr>
					</s:iterator>
				</s:else>
			</tbody>
		</table>
	</div>
	<p></p>
	<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
	<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
	<script>
	$(function(){
	  //提交表单
   	  $('#btn_save').click(function(){
   	     param['paramMap.id'] = $('#repayId').val();
	     param['paramMap.colResult'] = $('#colResult').val();
	     param['paramMap.remark'] = $('#remark').val();
	     $.shovePost('addCollection.do',param,function(data){
		   var callBack = data.msg;
		   alert(callBack);
		   
		   //----add by houli 添加催收记录后刷新页面，显示数据
		   var rId = $("#repayId").val();
		   window.location.href = 'repaymentDetail.do?id='+rId;
		   //----
		 });
	   });
	});
	function delCollection(id){
	    param['paramMap.id'] = id;
	    $.shovePost('delCollection.do',param,function(data){
		   var callBack = data.msg;
		   alert(callBack);
		   
		    //----add by houli 添加催收记录后刷新页面，显示数据
		   var rId = $("#repayId").val();
		   window.location.href = 'repaymentDetail.do?id='+rId;
		   //----
		});
	}
</script>
</body>
</html>