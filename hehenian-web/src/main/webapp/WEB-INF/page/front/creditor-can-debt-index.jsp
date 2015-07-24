<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<jsp:include page="/include/head.jsp"></jsp:include>
<link href="css/hhncss.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="script/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="script/jquery.shove-1.0.js"></script>
<script src="script/add_all.js" type="text/javascript"></script>
<script src="script/MSClass.js" type="text/javascript"></script>
<style>
/*债权转让弹出框*/
.tcbox {
	background-color: #cccccc;
	padding: 8px;
	width: 496px;
	position: absolute;
	left: 425px;
	top: 172px;
	z-index: 999;
}

.tcbox .tcmain {
	background-color: #FFF;
}

.tcbox .tcmain {
	padding-top: 5px;
	padding-right: 18px;
	padding-bottom: 30px;
	padding-left: 18px;
}

.tcbox .tcmain h3 {
	font-size: 14px;
	line-height: 42px;
	font-weight: normal;
	color: #136dad;
}

.tcbox .tcmain .ysctab table {
	line-height: 28px;
}

.tcbox .tcmain .ysctab table tr td {
	border: 1px solid #ddd;
}

.tcbox .tcmain .ysctab table tr th {
	font-weight: normal;
	background-color: #f9f9f9;
	border: 1px solid #ddd;
}

.tcbox .tcmain .ysctab table tr .pic {
	padding-top: 5px;
	padding-bottom: 5px;
}

.tcbox .tcmain .xzzl {
	background-image: url(../images/neiye1_333.jpg);
	background-repeat: repeat-x;
	background-position: left top;
	padding-top: 10px;
}

.tcbox .tcmain .xzzl table {
	line-height: 30px;
}

.tcbox .tcmain .xzzl table tr th {
	font-size: 12px;
	font-weight: normal;
	color: #136dad;
}

input[type="button"] {
	width: 95px;
	height: 27px;
	border: none;
	text-align: center;
	color: #fff;
	background: url(images/pic_all.png) -1px -84px no-repeat;
	cursor: pointer;
}

input[type="button"]:hover {
	background: url(images/pic_all.png) -1px -112px no-repeat;
}
</style>
<script type="text/javascript">
	$(function() {

		$(".header_two_right_ul li").hover(function() {
			$(this).find("ul").show();
		}, function() {
			$(this).find("ul").hide();
		});

	});

	function addAssignmentDebt(borrowId, debtSum, debtLimit, investId) {
		$("#zrzq_div").attr("style", "");
		$("#debtSum").val(debtSum);
		$("#span_debtSum").html(debtSum);

		$("#borrowId").val(borrowId);
		$("#debtLimit").val(debtLimit);
		$("#span_debtLimit").html(debtLimit);
		$("#investId").val(investId);
	}
</script>
<script>
	$(function() {
//	hhn(2);
        hhnNew("topIndex-zqzr");
		$("#debt_cancel").click(function() {
			$("#zrzq_div").attr("style", "display:none");
			$("#editForm")[0].reset();
		});
		$("#debt_ok").click(function() {
			var para = $("#editForm").serialize();
			$.post("addAssignmentDebt.do", para, function(data) {
				if (data == 1) {
					alert("操作成功");
					$("#debt_cancel").click();
					$("#btn_search").click();
					window.location.reload();
				} else if (data == -1) {
					alert("操作失败");
					$("#debt_cancel").click();
				} else {
					$("#debt_add").html(data);
				}
			});
		});
	});
</script>
</head>
<body>
	<!-- 引用头部公共部分 -->
    <div style="position: absolute; height:54px; line-height:54px; background:#f3f3f3; width:100%; margin-top:151px !important; margin-top:177px;border-bottom:1px solid #e5e5e5;border-top:1px solid #e5e5e5;"></div>
	<jsp:include page="/include/top.jsp"></jsp:include>

	<div class="cle"></div>
	<div class="center">
		<div class="wytz_center">
			<div class="mjd_tzlc_all">
				<ul>
					<li onclick="javascript:window.location.href='queryFrontAllDebt.do'">已发布债权</li>
					<li onclick="javascript:window.location.href='queryFrontRedayDebt.do'">即将发布债权</li>
<%--					<li class="tzlc" onclick="javascript:window.location.href='queryFrontCanDebt.do'">我要转让债权</li>--%>
				</ul>
				<div class="cle"></div>
				<div class="mjd_tzlc_all_center" id="assignmentData">

					<!-- 我要转让债权div -->

					<div class="mjd_tzlc_lefth" style="display:block;">
						<s:if test="pageBean.page==null || pageBean.page.size==0">
							<table width="975" border="0">
								<tr height="30">
									<td align="center"><br />暂无记录</td>
								</tr>
							</table>
						</s:if>

						<s:else>
							<s:iterator value="pageBean.page" var="bean">
								<div class="mjd_tzlc_lefth_zqk">
									<a href="userMeg.do?id=${publisher}" target="_blank"> <shove:img src="${personalHead}" defaulImg="images/default-img.jpg" width="80" height="79"></shove:img> </a>
									<p>
										<strong><a href="financeDetail.do?id=${borrowId}" target="_blank">${borrowTitle }</a> </strong>
									</p>
									<table width="205" border="0">
										<tr height="30">
											<td width="70">借款金额：</td>
											<td width="135"><b>${bean.realAmount }</b></td>
										</tr>
										<tr>
											<td>年 利 率：</td>
											<td><b>${bean.annualRate }%</b></td>
										</tr>
										<tr height="30">
											<td>借款期限：</td>
											<td><u>${bean.deadline }个月</u></td>
										</tr>
										<tr>
											<td>债权期限：</td>
											<td><u>${bean.remainBorrowLimit }个月</u></td>
										</tr>
										<tr height="30">
											<td>投资金额：</td>
											<td><u>${realAmount }</u></td>
										</tr>
										<tr>
											<td>已收金额：</td>
											<td><u>${ hasPI}</u></td>
										</tr>
										<tr height="30">
											<td>待收金额：</td>
											<td><u>${recievedPI-hasPI}</u></td>
										</tr>
										<tr height="10"></tr>
										<tr>
											<td colspan="2" align="center"><s:if test="#bean.debtStatus==null||#bean.debtStatus ==4||#bean.debtStatus ==5||#bean.debtStatus ==6||#bean.debtStatus ==7">
													<input type="button" value="转让债权" onclick="javascript:addAssignmentDebt('${borrowId}','${recievedPI-hasPI}','${remainBorrowLimit}','${investId}')" />
												</s:if> <s:elseif test="#bean.debtStatus==1">
													<input type="button" value="撤回" onclick="window.location.href='cancelApplyDebt.do?debtId=${debtId}'" />
												</s:elseif></td>
										</tr>
										<tr height="10"></tr>
									</table>
									<div class="cle"></div>
								</div>
							</s:iterator>
							<div class="cle"></div>
							<div class="mjd_fy_all">
								<shove:page url="queryFrontCanDebt.do" curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="number" totalCount="${pageBean.totalNum}">
									<s:param name="debtSum">${paramMap.debtSum}</s:param>
									<s:param name="auctionBasePrice">${paramMap.auctionBasePrice}</s:param>
									<s:param name="auctionMode">${paramMap.auctionMode}</s:param>
									<s:param name="isLate">${paramMap.isLate}</s:param>
									<s:param name="publishDays">${paramMap.publishDays}</s:param>
								</shove:page>
							</div>
						</s:else>
					</div>

				</div>
			</div>
			<div class="cle"></div>
		</div>
	</div>
	<div class="cle"></div>

	<!-- 转让债权div -->
	<div>
		<div id="zrzq_div" class="tcbox" style="display: none;">
			<div class="tcmain">
				<p class="zqsm">简单的债权说明</p>
				<div id="debt_add" class="xzzl">

					<form id="editForm">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<th align="right">债权金额：</th>
								<td><span id="span_debtSum">${paramMap.debtSum }</span>元 <s:hidden id="debtSum" name="paramMap.debtSum"></s:hidden> <s:hidden id="investId" name="paramMap.investId"></s:hidden>
								</td>
							</tr>
							<tr>
								<th align="right">债权期限：</th>
								<td><span id="span_debtLimit">${paramMap.debtLimit}</span>个月 <input id="debtLimit" name="paramMap.debtLimit" value="${paramMap.debtLimit}" type="hidden" />
								</td>
							</tr>
							<tr>
								<th align="right">转让价格：<strong style="color: red;">*</strong>&nbsp;&nbsp;</th>
								<td><input type="text" class="inp90" name="paramMap.auctionBasePrice" value="${ paramMap.auctionBasePrice}" /> <strong style="color: red;"><s:fielderror
											fieldName="paramMap.auctionBasePrice" /> </strong> <input id="borrowId" name="paramMap.borrowId" value="${paramMap.borrowId }" type="hidden" />
								</td>
							</tr>
							<tr>
								<th align="right" valign="top">转让描述：：<strong style="color: red;">*</strong>&nbsp;&nbsp;</th>
								<td><textarea class="txt380" rows="6" cols="30" name="paramMap.details">${paramMap.details }</textarea><br /> <strong style="color: red;"> <s:fielderror
											fieldName="paramMap.details" /> </strong></td>
							</tr>
							<tr>
								<td style="padding-top:20px;" align="center" colspan="2">
									<%--<a href="javascript:void(0);" id="debt_ok" class="scbtn">确认</a> 
								<a href="javascript:void(0);"
									id="debt_cancel" class="scbtn" id="qxtck">取消</a>
								--%> <input type="button" value="确认" id="debt_ok" /> <input type="button" value="取消" id="debt_cancel" /></td>
							</tr>
						</table>
					</form>
					
				</div>
			</div>
		</div>
	</div>


	<!-- 引用底部公共部分 -->
	<jsp:include page="/include/footer.jsp"></jsp:include>
</body>
</html>
