﻿<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML>
<html>
<head>
<style>
.items1 { width:100%; }
.items1 li { height:60px; border-bottom:#d1d1d1 solid 1px; width:100% }
.items1 li strong,.items1 li em { display:block; width:70%; float:left; height:60px; line-height:60px; font-weight:normal; }
.items1 li strong { width:30%; }
.items1 li strong i { padding-left:8px;}
</style>
<jsp:include page="/include/mobile/head.jsp"></jsp:include>
<title>个人信息</title>
</head>
<body>
<jsp:include page="/include/mobile/loading.jsp"></jsp:include>
<h1 class="t title"><span>债权转让信息</span></h1>
<div class="wrap" id="wrap">
<form id="editForm">
  <ul class="items1 user">
     <li style="color: red;"><strong><i>温馨提示：</i></strong><em> 投资满2个月之后才能进行债权转让  </em></li>
    <li><strong><i>剩余本金：</i></strong><em> ${whbj}元  </em></li>
    <li><strong><i>转让价格：  </i></strong><em><input type="text" id="auctionBasePrice" class="inp90" name="paramMap.auctionBasePrice" value="${whbj}" /> </em></li>
	<input id="investId"  name="paramMap.investId" value="${ investId}" type="hidden" />
	<input id="debtSum"  name="paramMap.debtSum"  value="${ whbj}" type="hidden" />
	<input id="debtLimit" name="paramMap.debtLimit" value="${ debtLimit}" type="hidden" />
	<input id="parentId" name="paramMap.parentId" value="${ parentId}" type="hidden" />
	<input id="borrowId" name="paramMap.borrowId" value="${ borrowId}" type="hidden" />
  </ul>
   <ul class="account-btn hr pd">
	    <li><a href="javascript:void(0)" class="btn-c" id="draw">确认</a></li>
	    <li>
    		<a href="webapp/webapp-invest-zr.do" class="btn-c" id="recharge">取消</a>
	    </li>
  	</ul>
  </form>
</div>
<jsp:include page="/include/mobile/bottom.jsp"></jsp:include>
<jsp:include page="/include/mobile/common-js.jsp"></jsp:include>
</body>
<script>
Zepto(function(){
	//loading
	HHN.loadPage();
	var min;//转让最低金额
	var max;//转让最高金额
	
	$("#draw").bind('click', function() { 
		debtSum = Number($("#debtSum").val());
		//$("#span_debtSum").html(debtSum);
		min = Number(debtSum * 0.9).toFixed(2);
		//------最高金额 =剩余本金 + 剩余本金*转让当天距离上次还款日的天数*月息/30
		//最高不超过剩余本金
		max = debtSum;//Number(debtSum + debtSum * num * anna / 36000).toFixed(2);
		var param = {};
		var price = $("#auctionBasePrice").val();
		if (isNaN(price)) {
			HHN.popup({type:"alert",content:'价格非法',element:$("#auctionBasePrice")});
			return;
		}
		price = Number(price);
		if ( max < price || min > price) {
			HHN.popup({type:"alert",content:'价格非法',element:$("#auctionBasePrice")});
			return;
		}
		param['investId'] = $("#investId").val();
		param['auctionBasePrice'] = $("#auctionBasePrice").val();
		param['debtSum'] = $("#debtSum").val();
		param['borrowId'] = $("#borrowId").val();
		param['debtLimit'] = $("#debtLimit").val();
		param['parentId'] = $("#parentId").val();
		var para = $("#editForm").serialize();
		$.post("/../addAssignmentDebt.do", para, function(data) {
			if (data == 1) {
				HHN.popLoading("/webapp/webapp-invest-zr.do","操作成功，跳转中...")
			} else if (data == -1) {
				HHN.popup({type:"alert",content:'操作失败',element:$("#draw")});
			} else {
				HHN.popup({type:"alert",content:data,element:$("#draw")});
				//alert(data);
			}
		});
	}); 
});

</script>
</html>