<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
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
<link rel="stylesheet" href="css/jbox/Gray/jbox.css" type="text/css"></link>
<link href="css/user.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="css/popom.js"></script>
<script type="text/javascript" src="script/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="script/jbox/jquery.jBox-2.3.min.js"></script>
<script language="javascript" type="text/javascript" src="My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
	$(function() {
		$(".header_two_right_ul li").hover(function() {
			$(this).find("ul").show();
		}, function() {
			$(this).find("ul").hide();
		})

		$(".wdzh_top_ul li").eq(1).addClass("wdzhxxk");
		$(".wdzh_top_ul li").click(function() {
			var ppain = $(".wdzh_top_ul li").index(this);
			$(".wdzh_top_ul li").removeClass("wdzhxxk");
			$(this).addClass("wdzhxxk");
		})
	})
</script>
<jsp:include page="/include/head.jsp"></jsp:include>
</head>
<body>

<jsp:include page="/include/top.jsp"></jsp:include>
<div class="user-all">
  <div class="user-center">
    <div style=" width:1170px; margin:0px auto; padding-bottom:24px;"><img src="/images/v1/detail_logo_bg.jpg" width="1170" height="6"  alt=""/></div>
    <div style=" overflow:hidden">
      <div class="u-left">
        <jsp:include page="/include/menu_userManage.jsp"></jsp:include>
      </div>
      <div class="u-right"><div class="user-right" style=" height:753px;">
          <h2>成功投资</h2>
    <div class="wdzh_next" style="display:block;">
    
    
    
				<div>
					<div class="tzjl_cgtz_box" style=" width:882px">
						<h2>
							<span>投资项目</span>
						</h2>
						<table width="100%" border="0" style="border:1px solid #e5e5e5;">
							<tr height="30">
								<td width="50%"><span>借出本金总额：</span><strong>￥<s:if test="#request.map.realAmount == ''">0.00</s:if> <s:else>${request.map.realAmount }</s:else> </strong></td>
							  <td><span>应收本息总额：</span><strong>￥<s:if test="#request.map.shouldGetAmount == ''">0.00</s:if> <s:else>${request.map.shouldGetAmount }</s:else> </strong></td>
							</tr>
							<tr height="30">
								<td><span>已收本息总额：</span><strong>￥<s:if test="#request.map.hasGetAmount == ''">0.00</s:if> <s:else>${request.map.hasGetAmount }</s:else> </strong></td>
								<td><span>未收本息总额：</span><strong>￥<s:if test="#request.map.hasGetAmount == ''">0.00</s:if> <s:else>${request.map.noGetAmount }</s:else>
							  </strong></td>
						  </tr>
						</table>
						
						<table width="100%" border="0" style="border:1px solid #e5e5e5;">
						<tr height="60">
								<td>
									<form action="investSuccessedRecord.do" id="searchForm" method="post">
										<input type="hidden" name="curPage" id="pageNum" /> 
										交易日期： <input type="text" id="publishTimeStart" name="publishTimeStart" value="${paramMap.publishTimeStart }"
											class="inp90 Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})" /> -<input type="text" id="publishTimeEnd" name="publishTimeEnd"
											value="${paramMap.publishTimeEnd }" class="inp90 Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})" /> 
											借款人：<input type="text" name="userName" id="userName" value="${paramMap.userName }" />
											<input type="button" value="搜索" id="search" />
									</form>
								</td>
							</tr>
					    </table>
					</div>
					<div class="tzjl_cgtz_box" style=" width:882px">
						<h2>
							<span>债权明细</span>
						</h2>
						<table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#e5e5e5">
							
							<tr align="center" height="30">
								<td bgcolor="#ffffff">债权编号</td>								
								<td bgcolor="#ffffff">交易日期</td>
								<td bgcolor="#ffffff">借款人</td>
								<td bgcolor="#ffffff">标题</td>
								<td bgcolor="#ffffff">年利率</td>
								<td bgcolor="#ffffff">债权期限</td>
								<td bgcolor="#ffffff">投资金额</td>
								<td bgcolor="#ffffff">已收金额</td>
								<td bgcolor="#ffffff">待收金额</td>
								<td bgcolor="#ffffff">回收阶段</td>
								<td bgcolor="#ffffff">借款协议</td>
								<td bgcolor="#ffffff">还款详情</td>
								<td bgcolor="#ffffff">债权转让</td>
							</tr>
							<s:if test="pageBean.page!=null && pageBean.page.size>0">
								<s:iterator value="pageBean.page" var="bean">
									<tr align="center" height="30">
										<td bgcolor="#ffffff">${bean.invest_number }</td>										
										<td bgcolor="#ffffff">${bean.investTime }</td>
										<td bgcolor="#ffffff">${bean.username }</td>
										<td bgcolor="#ffffff"><a href="financeDetail.do?id=${bean.borrowId }">${bean.borrowTitle }</a></td>
										<td bgcolor="#ffffff">${bean.annualRate }%</td>
										<td bgcolor="#ffffff">${bean.debtLimit }个月</td>
										<td bgcolor="#ffffff">${bean.realAmount }</td>
										<td bgcolor="#ffffff">${bean.hasPI }</td>
										<td bgcolor="#ffffff">${bean.notPI }</td>
										<td bgcolor="#ffffff">${bean.repayPeriod }</td>
										<td bgcolor="#ffffff"><a href="agreement.do?borrowId=${bean.borrowId }">查看</a></td>
										<td bgcolor="#ffffff"><s:if test="#bean.borrowStatus==2">
												<a style="color: #ccc;cursor: default;">招标中</a>
											</s:if> <s:elseif test="#bean.borrowStatus==3">
												<a style="color: #ccc;cursor: default;">满标审核中</a>
											</s:elseif> <s:elseif test="#bean.borrowStatus==4 || #bean.borrowStatus==5">
												<a href="javascript:payDeatil(${bean.investId })">还款详情</a>
											</s:elseif> <s:else>流标</s:else>
										</td>
										<td bgcolor="#ffffff">
										
										    <s:if test="#bean.borrowStatus!=4">--</s:if> 
										    
										    <s:elseif
												test="#bean.debtStatus==null||#bean.debtStatus ==4||#bean.debtStatus ==5||#bean.debtStatus ==6||#bean.debtStatus ==7 || (#bean.debtStatus==3 && #bean.currentUser == #bean.auctionerId)">
												<span style="padding-left: 0;cursor: pointer;color: #333;" data-xx="${bean.whbj }"
													onclick="zhuanrang('${bean.borrowId}','${bean.blanceP }','${bean.deadline-hasDeadline}','${bean.investId}','${bean.annualRate}','${bean.num }','${bean.whbj }','${bean.annualRate }','${bean.debtId}');">转让</span>
											</s:elseif>
											<s:elseif test="#bean.debtStatus==1">
												<span style="padding-left: 0;cursor: pointer;color: #333;" onclick="window.location.href='cancelApplyDebt.do?debtId=${debtId}'">撤回</span>
											</s:elseif> 
											<s:elseif test="#bean.debtStatus==2">
												<span style="padding-left: 0;color: #333;">转让中</span>
											</s:elseif> <s:elseif test="#bean.debtStatus==3 && #bean.currentUser == #bean.alienatorId">
												<span style="padding-left: 0;color: #333;">已转让</span>
											</s:elseif>
									   </td>
									</tr>
								</s:iterator>
							</s:if>
							<s:else>
								<tr>
									<td align="center" height="30" colspan="13">暂无数据</td>
								</tr>
							</s:else>
						</table>
						<div class="cle"></div>
						
						<div class="tzjl_fwxy1h" id="debt_add" style="top:-120px;">
							<form id="editForm">
								<table width="480" border="0">
									<%--									<tr>--%>
									<%--										<td colspan="2"><p>简单的债权说明：包括了债权过程的说明以及成功后收取的费用说明</p></td>--%>
									<%--									</tr>--%>
									<tr height="20">&nbsp;
									</tr>
									<tr><td align="right">温馨提示：</td>
									<td style="color: red;">投资满2个月之后才能进行债权转让</td>
									</tr>
									<%-- <tr>
										<td width="120" align="right">债权转让区间：</td>
										<td width="360"><span id="span_debtSum"></span>元 <s:hidden id="debtSum" name="paramMap.debtSum"></s:hidden> <s:hidden id="investId" name="paramMap.investId"></s:hidden>
										</td>
									</tr>
									<tr height="10"></tr> --%>
								<!-- 	<tr>
										<td width="120" align="right">债权期限：</td>
										<td width="360"><span id="span_debtLimit"></span>个月 <input id="debtLimit" name="paramMap.debtLimit" value="" type="hidden" />
										</td>
									</tr> -->
									<!-- <tr height="10"></tr>
									<tr>
										<td width="120" align="right">年利率：</td>
										<td width="360"><span id="annualRate"></span>%
										</td>
									</tr>
									<tr height="10"></tr> -->
									<tr>
										<td width="120" align="right">剩余本金：</td>
										<td width="360"><span id="whbj" style="padding-left:0px;"></span>元 
										<s:hidden id="investId" name="paramMap.investId"></s:hidden>
										<s:hidden id="debtSum" name="paramMap.debtSum"></s:hidden>
										<input id="debtLimit" name="paramMap.debtLimit" value="" type="hidden" />
										<s:hidden id="parentId" name="paramMap.parentId"></s:hidden>
										</td>
									</tr>
									<tr height="10"></tr>
									<tr>
										<td width="120" align="right">转让价格：</td>
										<td width="360"><input type="text" id="auctionBasePrice" class="inp90" name="paramMap.auctionBasePrice" value="" /> 
										<input id="borrowId" name="paramMap.borrowId" value="" type="hidden" />
										</td>
									</tr>
									 <tr>
										<td width="120" align="right"></td>
										<td width="360"> 
										<strong style="margin-left:0px;">
										不得超过剩余本金，不得少于剩余本金的90%
										</strong> 
										</td>
									</tr>
									 <%--
									<tr>
										<td width="120" align="right" valign="top">转让描述：</td>
										<td width="360"><textarea class="txt380" rows="6" cols="30" id="details" name="paramMap.details"></textarea><br /> <strong style="color: red;"> <s:fielderror
													fieldName="paramMap.details" id="detailsTip" /> </strong>
										</td>
										</td>
									</tr> --%>
									<tr height="20"></tr>
									<tr>
										<td>&nbsp;</td>
										<td width="360"><input type="button" value="确定" onclick="zhuanrangConfirm();" /><input type="button" value="取消" id="debt_cancel" onclick="operationClose();" /></td>
									</tr>
									<tr height="10"></tr>
								</table>
							</form>
						</div>
					</div>
					<div class="cle"></div>
					<%--					<p class="tzjl_quanx">--%>
					<%--						<input type="checkbox" onclick="checkAll(this)" /><input type="button" value="合并选中债权" onclick="combineInvest();" /><input type="button" value=" 拆分选中债权" onclick="splitInvest();" />--%>
					<%--					</p>--%>
					<div class="cle"></div>
					<div class="mjd_fy_all">
						<s:if test="pageBean.page!=null || pageBean.page.size>0">
							<div class="page" style=" padding-top:20px;">
								<p>
									<shove:page url="investSuccessedRecord.do" curPage="${pageBean.pageNum}" showPageCount="6" pageSize="${pageBean.pageSize }" theme="number"
										totalCount="${pageBean.totalNum}">
									</shove:page>
								</p>
							</div>
						</s:if>
					</div>
					<div class="cle"></div>
				</div>
			</div>
    </div></div>
    </div>
    <div style=" width:1170px; margin:0px auto">&nbsp;</div>
  </div>
</div>
<div class="cle"></div>






    
    
    
    


	<jsp:include page="/include/footer.jsp"></jsp:include>
	<script type="text/javascript" src="script/jquery.shove-1.0.js"></script>
	<script type="text/javascript">
	$(function(){

		$("#jumpPage").click(function() {
			var curPage = $("#curPageText").val();
			if (isNaN(curPage)) {
				alert("输入格式不正确!");
				return;
			}
			$("#pageNum").val(curPage);
			var chk_value = [];
			$('input[name="ck_mode"]:checked').each(function() {
				chk_value.push($(this).val());
			});
			if (chk_value.length != 0) {
				param['paramMap.m'] = '1';
				$("#type").val(chk_value);
			} else {
				$("#type").val("");
			}
			//$("#searchForm").submit();
            window.location.href="investSuccessedRecord.do?curPage="+curPage+"&publishTimeStart="+$("#publishTimeStart").val()+"&publishTimeEnd="+$("#publishTimeEnd").val();
		});
	});
		function openUrl(url) {
			window.open(url, '_blank');
		}

		//还款详情
		function payDeatil(investId) {
			var url = "homeBorrowForpayDetail.do?iid=" + investId;
			jBox("iframe:" + url, {
				title : "还款详情",
				top : '20%',
				width : 750,
				height : 400,
				buttons : {
					'确定' : true
				}
			});
		}

		//搜索条件验证
		$(function() {
			$("#search").click(function() {
				if ($("#publishTimeStart").val() != '' && $("#publishTimeEnd").val() != '' && $("#publishTimeStart").val() > $("#publishTimeEnd").val()) {
					alert("开始日期不能大于结束日期");
					return;
				}
				$("#pageNum").val(1);
				$("#searchForm").submit();
			});
		});
		//选择所有
		function checkAll(e) {
			if (e.checked) {
				$(".downloadId").attr("checked", "checked");
			} else {
				$(".downloadId").removeAttr("checked");
			}
		}
		//关闭窗口
		function protocolClose() {
			$(".tzjl_fwxyh").hide();
		}
		function operationClose() {
			$(".tzjl_fwxy1h").hide();
		}
		//转让
		var min;//转让最低金额
		var max;//转让最高金额
		function zhuanrang(borrowId, debtSum, debtLimit, investId, anna, num,whbj,annualRate,debtId) {//anna年利率       num 今天距离上次还款日的天数
			//anna = annas;
			debtSum = Number(debtSum);
			anna = Number(anna);
			num = Number(num);
			$(".tzjl_fwxy1h").show();
			$("#debtSum").val(debtSum);
			//$("#span_debtSum").html(debtSum);
			min = Number(debtSum * 0.9).toFixed(2);
			//------最高金额 =剩余本金 + 剩余本金*转让当天距离上次还款日的天数*月息/30
			//最高不超过剩余本金
			max = debtSum;//Number(debtSum + debtSum * num * anna / 36000).toFixed(2);
			var tips = min + " - " + max + " ";
			//$("#span_debtSum").html(tips);
			$("#whbj").html(whbj);
			$("#auctionBasePrice").val(whbj);
			//$("#annualRate").html(annualRate);

			$("#borrowId").val(borrowId);
			$("#debtLimit").val(debtLimit);
			$("#span_debtLimit").html(debtLimit);
			$("#investId").val(investId);
			$("#parentId").val(debtId);
		}

		//确定转让
		function zhuanrangConfirm() {
			var param = {};
			var price = $("#auctionBasePrice").val();
			if (isNaN(price)) {
				alert("价格非法");
				return;
			}
			price = Number(price);
			if ( max < price || min > price) {
				alert("价格非法");
				return;
			}

			param['investId'] = $("#investId").val();
			param['auctionBasePrice'] = $("#auctionBasePrice").val();
			param['debtSum'] = $("#debtSum").val();
			param['borrowId'] = $("#borrowId").val();
			param['debtLimit'] = $("#debtLimit").val();
			param['details'] = $("#details").val();
			param['auctionDays'] = $("#auctionDays").val();
			param['parentId'] = $("#parentId").val();
			var para = $("#editForm").serialize();
			$.post("addAssignmentDebt.do", para, function(data) {
				if (data == 1) {
					alert("操作成功");
					$("#debt_cancel").click();
					window.location.href = "investSuccessedRecord.do";
				} else if (data == -1) {
					alert("操作失败");
					$("#debt_cancel").click();
				} else {
					alert(data);
					$("#debt_cancel").click();
				}
			});
		}

		//合并选中债权
		function combineInvest() {
			var stIdArray = [];
			$("input[name='check']:checked").each(function(data) {
				stIdArray.push($(this).val());
			});

			if (stIdArray.length <= 1) {
				alert("至少选择两项需要合并的债权");
				return;
			}
			if (!window.confirm("是否要合并？")) {
				return;
			}

			var ids = stIdArray.join(",");
			var param = {};
			param["paramMap.id"] = ids;
			$.post("investsCombine.do", param, function(data) {
				if (data == -1) {
					alert("存在不同的标,不能合并");
				} else if (data == -2) {
					alert("存在不同的标,不能合并");
				} else if (data == -3) {
					alert("存在 转让的标，不能合并");
				} else if (data == -4) {
					alert("存在不同期数的标,不能合并");
				} else if (data == -5) {
					alert("存在不同已还期数的标,不能合并");
				} else if (data == -6) {
					alert("合并失败");
				} else {
					alert("合并成功");
					$(".downloadId").removeAttr("checked");
					window.location.reload();
				}
			});
		}
		//拆分选中债权
		function splitInvest() {
			var stIdArray = [];
			$("input[name='check']:checked").each(function() {
				stIdArray.push($(this).val());
			});

			if (stIdArray.length == 0) {
				alert("请选择需要拆分的债权");
				return;
			}

			var ids = stIdArray.join(",");
			var param = {};

			$.post("queryInvestById.do?ids=" + ids, param, function(data) {
				if (data == -2) {
					alert("存在不同的标,不能拆分");
				} else if (data == -3) {
					alert("存在 转让的标，不能拆分");
				} else if (data == -4) {
					alert("存在不同期数的标,不能拆分");
				} else if (data == -5) {
					alert("存在不同已还期数的标,不能拆分");
				} else if (data == -6) {
					alert("拆分失败");
				} else {
					jBox(data, {
						title : "债权拆分",
						width : 650,
						height : 500,
						top : 25,
						buttons : {}
					});
				}
			});
		}

		function closeMethod() {
			window.jBox.close();
			$(".downloadId").removeAttr("checked");
			window.location.reload();
		}
	</script>
</body>
</html>
