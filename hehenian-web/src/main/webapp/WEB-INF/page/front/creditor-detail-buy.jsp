<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<jsp:include page="/include/head.jsp"></jsp:include>
<link href="css/hhncss.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="script/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="script/jquery.shove-1.0.js"></script>
<script src="script/add_all.js" type="text/javascript"></script>
<script src="script/MSClass.js" type="text/javascript"></script>
<script type="text/javascript">
	$(function() {
		$(".nd_bdxq_jkxq_three_ul li:first").addClass("jdd")
		hhn(1);
		var param = {};
		param["id"] = '${paramMap.borrowId}';
		//$.post("queryDebtBorrowDetail.do", param, function(data) {
			//$("#borrow_detail").html(data);
		//});

		$(".header_two_right_ul li").hover(function() {
			$(this).find("ul").show();
		}, function() {
			$(this).find("ul").hide();
		})
		$(".nd_bdxq_jkxq_three_ul li").click(function() {
			var s = $(".nd_bdxq_jkxq_three_ul li").index(this);
			$(".nd_bdxq_jkxq_three_ul li").removeClass("jdd");//失去焦点
			$(this).addClass("jdd");//设为焦点
			$(".nd_bdxq_jkxq_three_a").hide();
			$(".nd_bdxq_jkxq_three_a").eq(s).show();
		})
		$(".jbcr_a").click(function() {
			$(".jb_taczq").show();
		})
		$(".jbcr_xxa").click(function() {
			$(".jb_taczq").hide();
		})
		$(".l_jkxq_ljtb").click(function() {
			if ($(".l_jkxq_ljtb_dh").is(":hidden")) {
				$(".l_jkxq_ljtb_dh").show();
			} else
				($(function() {
					$(".l_jkxq_ljtb_dh").hide();
				}))
		});
		
		$("#buyDebt").click(function(){
			param["paramMap.id"] = '${paramMap.debtId}'
				param["paramMap.auctionPrice"] = '${paramMap.auctionBasePrice}'
				param["paramMap.dealpass"]=$("#dealpass").val();
				param["paramMap.usrCustId"]=$("#usrCustId").val();
				param["paramMap.debtUsrCustId"]=$("#debtUsrCustId").val();
					if(confirm("是否确认购买？")){
						$("#buyDebt").attr("disabled",true);
						$.post("addAuctingDebt.do",param,function(data){
						  	if(data.length<20){
						  		alert(data);
						  	}else{
			<%--			  		alert(data);--%>
						  		$("#dataForm").html(data);
						  	}
						$("#buyDebt").attr("disabled",false);
						});
					}
		});
	})
	function buyDebt(){
	
	}
</script>
</head>
<body>
	<!-- 引用头部公共部分 -->
	<jsp:include page="/include/top.jsp"></jsp:include>


<div class="cle"></div>
	<div class="wytz_center">
    	<div class="qrzq_gm_all">
            <div class="qrzq_gm_top"><strong>债权购买</strong></div>
            <div class="qrzq_gm_left">
            	<table width="450" border="0">
                    <tr>
                        <td rowspan="4" width="180"><img src="${borrowMap.imgPath }" width="120" height="120" /></td>
                        <td height="30"><span>借款标题：</span><b>${borrowMap.borrowTitle }</b>
                        	<input id="usrCustId"  type="hidden" value="${userMap.usrCustId}"/>
                        </td>
                    </tr>
                    <tr>
                        <td height="30"><span>债权总额：</span><b>￥${paramMap.debtSum }</b></td>
                    </tr>
                    <tr>
                        <td height="30"><span>借款年利率：</span><b>${borrowMap.annualRate }% </b></td>
                    </tr>
                    <tr>
                        <td height="30"><span>借款期限：</span><b>${borrowMap.deadline }个月</b></td>
                    </tr>
                    <tr height="10"></tr>
                    <tr>
                        <td><span>用户名：</span><b>${publisherMap.username}</b></td>
                        <td><span>还款方式：</span><b> 
                        <s:if
							test="#request.borrowMap.paymentMode==1">按月等额本息还款</s:if> 
							<s:if
							test="#request.borrowMap.paymentMode==2">按先息后本还款</s:if> </b></td>
                    </tr>
                    <tr height="10"></tr>
                    <tr>
                        <td><span>籍贯：</span><b>${publisherMap.address }</b></td>
                        <td><span>交易类型：</span><b> <s:if
							test="#request.borrowMap.tradeType==1">线上交易</s:if> <s:if
							test="#request.borrowMap.tradeType==2">线下交易</s:if> </b></td>
                    </tr>
                    <tr height="10"></tr>
                    <tr>
                        <td><span>居住地点：</span><b> ${publisherMap.address}</b></td>
                        <td><span>部分借款：</span><b></b>
                        <input id="debtUsrCustId" value="${publisherMap.usrCustId}" type="hidden"/>
                        </td>
                    </tr>
                    <tr height="10"></tr>
                </table>
            </div>
            <div class="qrzq_gm_right">
            	<table width="450" border="0">
                	<tr height="10"></tr>
                    <tr>
                        <td><span>您的账户总额：</span><b> ${countMoney}元</b><a href="rechargeInit.do">我要充值</a></td>
                    </tr>
                    <tr height="10"></tr>
                    <tr>
                        <td><span>您的可用余额：</span><b> ${accmountStatisMap.usableAmount}元</b></td>
                    </tr>
                    <tr height="10"></tr>
                    <tr>
                        <td><span>请确认下面的购买金额：</span></td>
                    </tr>
                    <tr height="10"></tr>
                    <tr>
                        <td><span>所需金额：</span><b>￥${paramMap.auctionBasePrice} 元</b></td>
                    </tr> 
                    <tr height="10"></tr>
<%--                    <tr height="20">--%>
<%--                    	<td><span>交易密码：</span><input type="password" id="dealpass" size="15"/></td>--%>
<%--                    </tr>--%>
<%--                     <tr height="10"></tr>--%>
                    <tr>
                        <td><span></span><b>注意：点击按钮表示您将确认投标金额并同意付款</b></td>
                    </tr>
                    <tr height="10"></tr>
                    <tr>
                        <td><input type="button" id="buyDebt" value="确认购买" /></td>
                    </tr>
                </table>
            </div>
            <div class="cle"></div>
        </div>
   </div>
<div class="cle"></div>
<div style="display: none" id="dataForm"></div>

	<!-- 引用底部公共部分 -->
	<jsp:include page="/include/footer.jsp"></jsp:include>

</body>
</html>
