<%@page import="com.sun.org.apache.xml.internal.serialize.Printer"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="taglib.jsp"%>
<html xmlns:wb="http://open.weibo.com/wb">
<script src="http://tjs.sjs.sinajs.cn/open/api/js/wb.js" type="text/javascript" charset="utf-8"></script>
<link rel="shortcut icon" href="favicon.ico">
<script type="text/javascript">
	$(function() {
		$(".header_two_right_ul li").hover(function() {
			$(this).find("ul").show();
		}, function() {
			$(this).find("ul").hide();
		})

		$(function() {
			$(".header_two_right_ul_tan li").hover(function() {
				$(this).find("a").css("color", "#e94718");
			}, function() {
				$(this).find("a").css("color", "#333333");
			});

		});

	});

	//菜单栏选中样式
	function hhn(index) {
		$(".hhn").eq(index).attr("style", "background:#fff;border-bottom:2px solid #e94718;");
		$(".hhn").eq(index).find("a:first").css("color", "#e94718");
	}
</script>
<!--顶部状态栏 开始-->
<div class="header">
	<div class="header_one">
		<div class="header_one_center">
			 <strong> <span style="margin-top:4px;"><wb:follow-button uid="2403256623" type="red_1" width="67" height="24">
					</wb:follow-button> </span> &nbsp; <u>客服热线：0755-83025306</u><img src="images/hhnimages/top_qq.png" /><a href="callcenter.do?showkf=kfs">客服中心</a> </strong>
			<div class="cle"></div>
		</div>
	</div>
	<div class="header_two">
		<div class="header_two_center">
			<div class="header_two_left">
				<table width="370" border="0">
					<tr>
						<td width="225"><div class="header_two_left_logo">
								<a href="index.jsp"><img src="images/hhnimages/logo_zaixian.png" /> </a>
							</div></td>
						<td valign="bottom"><a href="index.jsp"><img src="images/hhnimages/logo_line.png" /> </a><span style="font-size: 12px;">花样年集团成员</span></td>
					</tr>
				</table>
			</div>
			<div class="header_two_right">
				<ul class="header_two_right_ul">
					<li class="hhn"><a href="index.jsp">首 页</a><span></span>
						<ul class="header_two_right_ul_tan" style=" font-size: 12px">
							<li><a href="getMessageByHhn.do?id=1" style=" font-size: 12px;color: #333333">关于我们</a></li>
							<li><a href="getMessageByHhn.do?id=3" style=" font-size: 12px;color: #333333">法律政策</a></li>
							<li><a href="callcenter.do?showkf=kfs" style=" font-size: 12px;color: #333333">联系我们</a></li>
						</ul>
					</li>
					<li class="hhn"><a href="finance.do">我要投资</a><span></span>
						<ul class="header_two_right_ul_tan" style=" font-size: 12px;color: #333333">
							<li><a href="queryFrontAllDebt.do" style=" font-size: 12px;color: #333333">债权转让</a></li>
							<li><a href="finance.do" style=" font-size: 12px;color: #333333">已发布借款</a></li>
							<li><a href="soonPublishList.do" style=" font-size: 12px;color: #333333">即将发布借款</a></li>
							<li><a href="queryPublishDebt.do" style=" font-size: 12px;color: #333333">正在转让债权</a></li>
						</ul>
					</li>
					<li class="hhn"><a href="borrow.do">我要借款</a><span></span>
						<ul class="header_two_right_ul_tan" style=" font-size: 12px;color: #333333">
							<li><a href="borrow.do" style=" font-size: 12px;color: #333333">申请借款</a></li>
							<li><a href="financetool.do" style=" font-size: 12px;color: #333333">利息计算</a></li>
						</ul>
					</li>
					<li class="hhn"><a href="home.do">会员中心</a></li>
					<li class="hhn"><a href="callcenter.do">帮助中心</a><span></span>
						<ul class="header_two_right_ul_tan">
							<li><a href="callcenter.do?showkf=kfs" style=" font-size: 12px;color: #333333">客服中心</a></li>
							<li><a href="capitalEnsure.do" style=" font-size: 12px;color: #333333">本金保障</a></li>
							<li><a href="financetool.do" style=" font-size: 12px;color: #333333">工具箱</a></li>
						</ul>
					</li>
				</ul>
			</div>
			<div class="cle"></div>
		</div>
	</div>
</div>


<!--顶部主导航 结束-->