<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<link rel="stylesheet" type="text/css" href="css/hhncss.css"></link>
<script src="script/jquery-1.2.6.pack.js" type="text/javascript"></script>
<script type="text/javascript" src="script/jbox/jquery.jBox-2.3.min.js"></script>
<script type="text/javascript" src="script/jbox/jquery.jBox-zh-CN.js"></script>
<script src="script/add_all.js" type="text/javascript"></script>
<script src="script/MSClass.js" type="text/javascript"></script>
<jsp:include page="/include/head.jsp"></jsp:include>
<script type="text/javascript">
	$(function() {
		$(".wdzh_next_left li").eq(6).addClass("wdzh_next_left_ong").find("a").attr("style","color: rgb(233, 71, 24);");
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
<%--			$(".wdzh_next").hide();--%>
<%--			$(".wdzh_next").eq(ppain).show();--%>
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
	
	function confirm() {
			var param = {};
			param["refferee"] = $("#refferee").val();
			if($("#userId").val()==$("#refferee").val())
			{
				alert("您不能推荐自己!");
				return false;
			}
			if($("#refferee").val()=='' || $("#refferee").val() == null)
			{
				alert("推荐人不能为空!");
				return false;
			}
			$.post("setMyReferee.do", $.param(param), function(data) {
				if (data == "-1") {
					alert("推荐人不能为空");
					return;
				}
				if (data == "-2") {
					alert("推荐人不存在");
					return;
				}
				if (data > 0) {
					alert("设置成功");
					window.location.reload();
				} else {
					alert("设置失败");
				}
			});
		}

	$(document).ready(function(){
		　　if('${map.refferee}' == -1)
			{
				$("#refferee").attr("");
			}else
			{
				$("#refferee").val('${map.refferee}');;
			}
		}); 
</script>
</head>

<body>
<div style="position: absolute; height:54px; line-height:54px; background:#f3f3f3; width:100%; margin-top:151px !important; margin-top:177px;border-bottom:1px solid #e5e5e5;border-top:1px solid #e5e5e5;"></div>
	<jsp:include page="/include/top.jsp"></jsp:include>
	<div class="cle"></div>
	<div class="wytz_center">
    <div class="mjd_tzlc_all"><jsp:include page="/include/menu_userManage.jsp"></jsp:include></div>
		<div class="wdzh_top">
		
			<div class="cle"></div>
			<div class="wdzh_next" style="display:block;">
				<div class="wdzh_next_left">
					   
         <jsp:include page="/include/menu_userManage_4_left.jsp"></jsp:include>
				</div>
				<div class="wdzh_next_right">
					<div class="wdtzr_tx">
						<h3>
							<span>填写我的推荐人<input id="userId" value="${request.userId}" type="hidden"/></span>
						</h3>
						<div class="wdtzr_tx_a" >
							<p style="margin-left: 20px;">
								填写我的推荐人：<input type="text" id="refferee" <s:if test="#request.map.refferee !='' && #request.map.refferee != -1">disabled="disabled"</s:if> />
								<s:if test="#request.map.refferee == '' || #request.map.refferee == -1"><font style="color:red">提示：请填写推荐人的会员号，推荐人必须是网站会员。</font></s:if>
							</p>
							<p style="margin-left: 100px;margin-top: 20px;margin-bottom: 20px;">
								<input type="button" value="提交" onclick="confirm();" class="bcbtn" <s:if test="#request.map.refferee !='' && #request.map.refferee != -1">disabled="disabled" style="background: #ccc"</s:if> />
							</p>
							<p style="margin-left: 20px;">
								我的推荐人:
								<s:if test="#request.map.refferee=='' || #request.map.refferee == -1">暂无</s:if>
								<s:else><a style="color: red">${map.refferee}</a></s:else>
							</p>
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
</body>
</html>
