<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<link rel="stylesheet" type="text/css" href="css/hhncss.css" />
	<script src="script/jquery-1.2.6.pack.js" type="text/javascript"></script>
	<script type="text/javascript" src="script/jbox/jquery.jBox-2.3.min.js"></script>
	<script type="text/javascript" src="script/jbox/jquery.jBox-zh-CN.js"></script>
	<script src="script/add_all.js" type="text/javascript"></script>
	<script src="script/MSClass.js" type="text/javascript"></script>
	<jsp:include page="/include/head.jsp"></jsp:include>
	<script type="text/javascript">
		$(function() {
			$(".header_two_right_ul li").hover(function() {
				$(this).find("ul").show();
			}, function() {
				$(this).find("ul").hide();
			})

			$(".wdzh_top_ul li:last").addClass("wdzhxxk");
			$(".wdzh_top_ul li").click(function() {
				var ppain = $(".wdzh_top_ul li").index(this);
				$(".wdzh_top_ul li").removeClass("wdzhxxk");
				$(this).addClass("wdzhxxk");
				//$(".wdzh_next").hide();
				//$(".wdzh_next").eq(ppain).show();
			})
			$(".tzjl_fwxy").click(function() {
				$(".tzjl_fwxyh").show();
			})
			$(".tzjl_fwxy1").click(function() {
				$(".tzjl_fwxy1h").show();
			})
			$(".grxx_aqzx_ul li:first").addClass("aqzxxk");
			$(".grxx_aqzx_ul li").click(function() {
				var wd = $(".grxx_aqzx_ul li").index(this);
				$(".grxx_aqzx_ul li").removeClass("aqzxxk");
				$(this).addClass("aqzxxk");

				$(".grxx_aqzx").hide();
				$(".grxx_aqzx").eq(wd).show();
			})
		})
	</script>
</head>

<body>
	<jsp:include page="/include/top.jsp"></jsp:include>
	<div class="cle"></div>
	<div class="cle"></div>
	<div class="wytz_center">
		<div class="wdzh_top">
			<jsp:include page="/include/menu_userManage.jsp"></jsp:include>
			<div class="wdzh_next">
				<div class="cle"></div>
			</div>
			<div class="cle"></div>
			<div class="wdzh_next">
				<div class="cle"></div>
			</div>
			<div class="cle"></div>
			<div class="wdzh_next"></div>
			<div class="cle"></div>
			<div class="wdzh_next"></div>
			<div class="cle"></div>
			<div class="wdzh_next" style="display:block;">
				<div class="wdzh_next_left">
					<h3>更多</h3>
					<ul>
<%--						<li><a href="mailNoticeInit.do">站内信</a>--%>
						</li>
<%--						<li class="wdzh_next_left_ong"><a href="userrrjc.do" style=" color:#e94718;">网站积分</a>--%>
						</li>
						<li><a href="friendManagerInit.do">推广链接</a>
						</li>
						<li><a href="myReferee.do">我的推荐人</a>
						</li>
					</ul>
				</div>
				<div class="wdzh_next_right">
					<div class="wdtzr_tx">
						<h3>
							<span>网站积分说明</span>
						</h3>
						<div class="wdtzr_tx_a">
							<table width="750" border="0" cellpadding="0" cellspacing="1" bgcolor="#e5e5e5">
								<tr height="40">
									<td colspan="8" bgcolor="#faf9f9"><a href="javascript:void(0)" id="cr" style="margin-left: 10px;">查看信用积分</a><a href="javascript:void(0)" id="vi" style="margin-left: 15px;">网站积分兑换</a>
									</td>
								</tr>
							</table>
							<div class="boxmain2" id="info1s">
								<div class="biaoge" style="margin-top:0px;" id="info1"></div>
							</div>
							<div class="boxmain2" style="margin-top:0px; display:none" id="info2s">
								<div class="biaoge" id="info2"></div>
							</div>
						</div>
					</div>
					<div class="cle"></div>
				</div>
				<div class="cle"></div>
			</div>
			<div class="cle"></div>
		</div>
	</div>
	<div class="cle"></div>
	<jsp:include page="/include/footer.jsp"></jsp:include>
	<script type="text/javascript">
		$(function() {
			$.post("queryUserIntegral.do", null, function(data) {
				$("#info1").html("");
				$("#info1").html(data);
			});
		});
		//点击查看用户的vip信息
		$(function() {
			$("#vi").click(function() {
				var param = {};
				param["pageBean.pageNum"] = 1;
				$.post("queryUservip.do", param, function(data) {
					$("#info1s").css("display", "none");
					$("#info2s").css("display", "block");
					$("#info2").html("");
					$("#info2").html(data);
				});
			});
			//点击查看用户的信用积分信息
			$("#cr").click(function() {
				$.post("queryUserIntegral.do", null, function(data) {
					$("#info2s").css("display", "none");
					$("#info1s").css("display", "block");
					$("#info1").html("");
					$("#info1").html(data);
				});
			});
		});
	</script>
</body>
</html>

