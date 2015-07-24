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
<script type="text/javascript">
	$(function() {
//		hhn(2);
        hhnNew("topIndex-finance");
		$(".header_two_right_ul li").hover(function() {
			$(this).find("ul").show();
		}, function() {
			$(this).find("ul").hide();
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
					<li class="tzlc" onclick="javascript:window.location.href='queryFrontRedayDebt.do'">即将发布债权</li>
					<%--<li onclick="javascript:window.location.href='queryFrontCanDebt.do'">我要转让债权</li>--%>
				</ul>
				<div class="cle"></div>
				<div class="mjd_tzlc_all_center" id="assignmentData">

					<!-- 即将发布债权div -->
					<div class="mjd_tzlc_lefth" style="display:block;">
						<h1>
							<table width="975" border="0">
								<tr height="40">
									<td width="115" align="center"><b>转让人</b></td>
									<td width="140" align="center"><b>借款人</b></td>
									<td width="150" align="center"><b>借款标题</b></td>
									<td width="110" align="center"><b>是否有逾期</b></td>
									<td width="140" align="center"><b>年利率</b></td>
									<td width="140" align="center"><b>债权总额</b></td>
									<td width="160" align="center"><b>转让价格</b></td>
								</tr>
							</table>
						</h1>
						<s:if test="pageBean.page==null || pageBean.page.size<=0">
							<table width="975" border="0">
								<tr>
									<td align="center"><br />暂无记录<br />
									</td>
								</tr>
							</table>
						</s:if>
						<s:else>
							<s:iterator value="pageBean.page" var="bean">
								<div class="mjd_tzlc_left_oneh">
									<table width="975" border="0">
										<tr>
											<td width="115" align="center">
												<p>
													<b>${alienatorName}</b>
													<%--<a href="userMeg.do?id=${alienatorId}" target="_blank"><b>${alienatorName}</b> </a>--%>
												</p></td>
											<td width="140" align="center">
<%--												<div class="mjd_tzlc_left_one_img">--%>
													<a href="userMeg.do?id=${publisher}" target="_blank"> 
<%--													<shove:img src="${personalHead}" defaulImg="images/default-img.jpg" width="80" height="79"></shove:img> --%>
													</a>
<%--												</div>--%>
												<p>
													<b>${borrowerName}</b>
												</p></td>
											<td width="150" align="center"><a href="queryDebtDetailHHN.do?id=${id }" target="_blank">${number }</a></td>
											<td width="110" align="center"><s:if test="#bean.isLate==2">有</s:if> <s:else>无</s:else></td>
											<td width="140" align="center" nowrap="nowrap">${annualRate}%</td>
											<td width="140" align="center">${debtSum }元</td>
											<td width="160" align="center">${auctionBasePrice}元</td>
										</tr>
									</table>
								</div>
							</s:iterator>
							<div class="mjd_fy_all">
								<shove:page url="queryFrontRedayDebt.do" curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="number" totalCount="${pageBean.totalNum}">
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

	<!-- 引用底部公共部分 -->
	<jsp:include page="/include/footer.jsp"></jsp:include>
</body>
</html>
