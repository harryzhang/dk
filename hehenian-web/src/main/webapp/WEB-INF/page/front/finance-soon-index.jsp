<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<jsp:include page="/include/head.jsp"></jsp:include>

</head>
<body>
	<!-- 引用头部公共部分 -->
        <div style="position: absolute; height:54px; line-height:54px; background:#f3f3f3; width:100%; margin-top:151px !important; margin-top:177px;border-bottom:1px solid #e5e5e5;border-top:1px solid #e5e5e5;"></div>
	<jsp:include page="/include/top.jsp"></jsp:include>

	<div class="center">
		<div class="wytz_center">
	
			<div class="mjd_tzlc_all">
				<ul>
					<li id="tab_1">已发布的借款</li>
					<li id="tab_2" class="tzlc">即将发布的借款</li>
<%--					<li id="tab_3">正在转让的债权</li>--%>
				</ul>
				<div class="cle"></div>
				<div class="mjd_tzlc_all_center">
					<div class="mjd_tzlc_left" style="display:block; padding-top:30px;">
						<s:if test="pageBean.page!=null || pageBean.page.size>0">
							<s:iterator value="pageBean.page" var="finance">
								
                                <div style=" width:905px; background:url(images/bbbj.png) left center no-repeat; height:137px; border-right:1px solid #e6e6e6;"> 
  <!--图-->
        <div style=" width:78px; height:137px; float:left;"><img src="images/hot1.png"/></div>
  <!--内容-->
  <div style=" width:550px;float:left;height:110px; margin-top:2px; padding-top:25px;">
    <div style="width:550px;  font-style:16px; line-height:24px;">
      <div style=" width:300px; float:left;font-weight:bold; "><a href="financeDetail.do?id=${finance.id}" target="_blank">${finance.borrowTitle}</a> &nbsp;
													<%--<s:if test="#finance.borrowWay ==1">
														<img src="images/neiye1_53.jpg" width="15" height="16" title="薪金贷" />
													</s:if>
													<s:if test="#finance.borrowWay ==2">
														<img src="images/neiye1_54.jpg" width="15" height="16" title="生意贷" />
													</s:if>
													<s:if test="#finance.borrowWay ==3">
														<img src="images/neiye1_55.jpg" width="15" height="16" title="业主贷" />
													</s:if>
													&nbsp; --%><br />
													<s:if test="#finance.hasPWD ==1">
														<img src="images/lock.png" width="15" height="16" title="投标时需要填写密码" />
													</s:if>
													<s:if test="#finance.isDayThe ==2">
														<img src="images/neiye1_67.jpg" width="15" height="16" title="天标" />
													</s:if>
													<s:if test="#finance.auditStatus ==3">
														<img src="images/neiye1_62.jpg" width="15" height="16" title="该用户通过抵押认证" />
													</s:if></div>
      <div  style=" width:200px; float:left">编号：<shove:sub size="15" value="#finance.number" suffix="..." /></div>
    </div>
    <div style=" float:left; width:300px; height:70px;">￥<font style="  font-size:38px; color:#0983ff "><s:property value="#finance.borrowAmount" default="0" /></font>元</div>
    <div style=" float:left; width:200px; line-height:28px;">借款期限：<s:property value="#finance.deadline" default="0" /> <s:if test="%{#finance.isDayThe ==1}">个月</s:if> <s:else>天</s:else></div>
  
    
    <div style=" float:left; width:200px; line-height:30px;">年利率：<s:property value="#finance.annualRate" default="0" />%&nbsp;<s:if test="#finance.annualRate >= 12"></s:if></div>

  </div>
  
  <!--投标按钮-->
  <div style=" width:200px;float:left;  height:83px;margin-top:52px; font-size:24px; text-align:right; padding-right:40px"  > <s:if test="#finance.borrowStatus==7">复审中</s:if>
											<s:else>
												<s:if test="#finance.isTimes == 0">${finance.publishTime}</s:if>
												<s:else>待发布</s:else>
											</s:else> </div>
</div>
                                
                                
<div style=" border-right:1px solid #e6e6e6; width:905px;"><img src="images/bbj.png" /></div>
						
								<div class="cle"></div>
							</s:iterator>
						</s:if>
						<s:else>
							<li style="text-align: center;padding-top: 20px;padding-bottom: 20px;">没有数据</li>
						</s:else>
						<div class="cle"></div>
						<s:if test="pageBean.page!=null || pageBean.page.size>0">
							<div class="mjd_fy_all">
								<shove:page url="soonPublishList.do" curPage="${pageBean.pageNum}" showPageCount="7" pageSize="${pageBean.pageSize }" theme="number" totalCount="${pageBean.totalNum}">
									<s:param name="m">${paramMap.m}</s:param>
									<s:param name="title">${paramMap.title}</s:param>
									<s:param name="paymentMode">${paramMap.paymentMode}</s:param>
									<s:param name="purpose">${paramMap.purpose}</s:param>
									<s:param name="raiseTerm">${paramMap.raiseTerm}</s:param>
									<s:param name="reward">${paramMap.reward}</s:param>
									<s:param name="arStart">${paramMap.arStart}</s:param>
									<s:param name="arEnd">${paramMap.arEnd}</s:param>
									<s:param name="type">${paramMap.type}</s:param>
									<s:param name="province">${paramMap.province}</s:param>
									<s:param name="regCity">${paramMap.regCity}</s:param>
								</shove:page>
								<div class="cle"></div>
							</div>
						</s:if>
					</div>

					<div class="mjd_tzlc_right">
						<div class="mjd_tzlc_right_two">
							<div class="mjd_tzlc_right_two_a">
								<strong>收益计算器</strong>
							</div>
							<table width="230" border="0">
								<tr height="50">
									<td width="80" align="right"><span>投标金额：</span></td>
									<td><input type="text" id="borrowSum" />&nbsp;元</td>
								</tr>
								<tr>
									<td width="80" align="right"><span>年利率：</span></td>
									<td><input type="text" id="yearRate" />&nbsp;%</td>
								</tr>
								<tr height="50">
									<td width="80" align="right"><span>投资期限：</span></td>
									<td><input type="text" id="borrowTime" />&nbsp;月</td>
								</tr>
								<tr>
									<td width="80" align="right"><span>还款方式：</span></td>
									<td><select id="repayWay"><option>按月等额本金</option>
									</select></td>
								</tr>
								<tr height="5"></tr>
								<tr height="50">
									<td width="80" align="right">&nbsp;</td>
									<td><input type="button" value="计算" onclick="rateCalculate()" /></td>
								</tr>
								<tr>
									<td colspan="2"><strong><span style="color: red; float: none;" class="formtips" id="show_error"></span> </strong></td>
								</tr>
							</table>
							<span id="showIncome"></span>
						</div>
					
					</div>
				</div>
			</div>
			<div class="cle"></div>
		</div>
	</div>
	<div class="cle"></div>
	<!-- 引用底部公共部分 -->
	<jsp:include page="/include/footer.jsp"></jsp:include>
	<script type="text/javascript" src="script/jquery.shove-1.0.js"></script>
	<script type="text/javascript" src="script/jbox/jquery.jBox-2.3.min.js"></script>
	<script type="text/javascript" src="script/jbox/jquery.jBox-zh-CN.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			var ck_type = "${paramMap.type}";
			var no = ck_type.split(',');
			if (no != '') {
				for ( var i = 1; i <= no.length; i++) {
					$('#ck_mode_' + i).attr('checked', 'checked');
				}
			}
			$("#province").change(function() {
				var provinceId = $("#province").val();
				registedPlaceCity(provinceId, 2);
			});
		});

		function registedPlaceCity(parentId, regionType) {
			var _array = [];
			_array.push("<option value='-1'>--请选择--</option>");
			var param = {};
			param["regionType"] = regionType;
			param["parentId"] = parentId;
			$.post("ajaxqueryRegions.do", param, function(data) {
				for ( var i = 0; i < data.length; i++) {
					_array.push("<option value='"+data[i].regionId+"'>" + data[i].regionName + "</option>");
				}
				$("#registedPlaceCity").html(_array.join(""));
			});
		}
		function checkTou(id, type) {
			var param = {};
			param["id"] = id;
			$.shovePost('financeInvestInit.do', param, function(data) {
				var callBack = data.msg;
				if (callBack != undefined) {
					alert(callBack);
				} else {
					if (type == 2) {
						var url = "subscribeinit.do?borrowid=" + id;
						$.jBox("iframe:" + url, {
							title : "我要购买",
							width : 450,
							height : 450,
							buttons : {}
						});
					} else {
						window.location.href = 'financeInvestInit.do?id=' + id;
					}
				}
			});
		}
		function closeMthod() {
			window.jBox.close();
			window.location.reload();
		}

		function clearComponentVal() {
			param = {};
			$('#titles').val('');
			$('#paymentMode').get(0).selectedIndex = 0;
			$('#purpose').get(0).selectedIndex = 0;
			$('#deadline').get(0).selectedIndex = 0;
			$('#reward').get(0).selectedIndex = 0;
			$('#arStart').get(0).selectedIndex = 0;
		}

		function rateNumJudge() {//验证利息计算输入数字是否正确
			if ($("#borrowSum").val() == "") {
				$("#show_error").html("&nbsp;&nbsp;投资金额不能为空");
				$("#showIncome").html("");
			} else if (!/^(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-9]*))$/.test($("#borrowSum").val())) {
				$("#show_error").html("&nbsp;&nbsp;请输入正确的投资金额进行计算");
				$("#showIncome").html("");
			} else if ($("#yearRate").val() == "") {
				$("#show_error").html("&nbsp;&nbsp;年利率不能为空");
				$("#showIncome").html("");
			} else if (!/^(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-9]*))$/.test($("#yearRate").val())) {
				$("#show_error").html("&nbsp;&nbsp;请输入正确的年利率");
				$("#showIncome").html("");
			} else if ($("#borrowTime").val() == "") {
				$("#show_error").html("&nbsp;&nbsp;投资期限不能为空");
				$("#showIncome").html("");
			} else if (!/^[0-9]*[1-9][0-9]*$/.test($("#borrowTime").val())) {
				$("#show_error").html("&nbsp;&nbsp;请输入正确投资期限");
				$("#showIncome").html("");
			} else {
				//if(!/^[0-9].*$/.test($("#bidReward").val())){
				//      $("#show_error").html("&nbsp;&nbsp;请输入正确的投资奖励");
				// 	      $("#showIncome").html("");
				//   }else 
				//	if(!/^[0-9].*$/.test($("#bidRewardMoney").val())){
				//      $("#show_error").html("&nbsp;&nbsp;请输入正确的现金奖励 ");
				//      $("#showIncome").html("");
				// }else{
				$("#show_error").html("");
			}
		}

		function rateCalculate() {//利息计算
			var str = rateNumJudge();
			param["paramMap.borrowSum"] = $("#borrowSum").val();
			param["paramMap.yearRate"] = $("#yearRate").val();
			param["paramMap.borrowTime"] = $("#borrowTime").val();
			param["paramMap.repayWay"] = $("#repayWay").get(0).selectedIndex;
			param["paramMap.bidReward"] = $("#bidReward").val();
			param["paramMap.bidRewardMoney"] = $("#bidRewardMoney").val();

			var _array = [];

			if ($("#show_error").text() != "") {
				return;
			}
			$.shovePost("incomeCalculate.do", param, function(data) {
				//只有一条记录
				if (data == 1) {
					$("#show_error").html("请填写完整信息进行计算");
					return;
				}
				_array.push("<table>");
				for ( var i = 0; i < data.length; i++) {
					data[i].income2year = data[i].income2year < 1 ? "0" + data[i].income2year : data[i].income2year;
					data[i].rateSum = data[i].rateSum < 1 ? "0" + data[i].rateSum : data[i].rateSum;
					_array.push("<tr><td style='padding-left:20px'><span>投标奖励：</span><span>" + data[i].reward + "元</span><br/>"
							 + "<span>总计利息：</span><span>" + data[i].rateSum
							+ "元</span><br/>" + "<span>每月还款：</span><span>" + data[i].monPay + "元</span><br/>" + "<span>总共收益：</span><span>"
							+ data[i].allSum + "元</span></td></tr>"); /*<br/>" + "<span>总计收益：</span><span>" + data[i].netIncome + "元(扣除10%管理费)</span>
					+ "<span>年化收益：</span><span>" + data[i].income2year + "%</span><br/>" _array.push("<p>投标奖励："+data[i].reward+"元</p><br /><br />"
					+"<p>年化收益："+data[i].income2year+"元</p><br /><br />"
					+"<p>总收益："+data[i].allSum+"元</p><br /><br />"
					+"<p>每月还款："+data[i].monPay+"元</p><br /><br />"
					+"<p>总计收益(扣除10%管理费)："+data[i].netIncome+"元</p>");*/
				}
				//_array.push("</table>");
				$("#showIncome").html(_array.join(""));
			});
		}
	</script>
	<script type="text/javascript">
	function reggg(){
		if(${session.user!=null}){
			alert("你已是登录用户");
		}else{
			window.location.href="/account/reg.do";
		}
	}
		$(function() {
//			hhn(1);
            hhnNew("topIndex-finance");
			$("span#tit").each(function() {
				if ($(this).text().length > 6) {
					$(this).text($(this).text().substring(0, 8) + "..");
				}
			});

			var m = '${paramMap.m}';
			if (m == '') {
				m = 1;
			}
			$("#tab_1").click(function() {
				window.location.href = "finance.do"
			});
			$("#tab_2").click(function() {
				window.location.href = "soonPublishList.do"
			});
			$("#tab_3").click(function() {
				window.location.href = "queryPublishDebt.do"
			});

			$("#searchLink").click(function() {
				window.location.href = "finance.do?title=" + $("#titles").val();
			});
			$("#search").click(function() {
				$("#searchForm").submit();
			});
			$("#arEnd").change(function() {
				var arStart = $('#arStart').val();
				arStart = parseInt(arStart);
				var arEnd = $(this).val();
				arEnd = parseInt(arEnd);
				if (arEnd < arStart) {
					alert('金额范围不能小于开始!');
				}
			});
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
				$("#searchForm").submit();
			});
		});
	</script>
</body>
</html>
