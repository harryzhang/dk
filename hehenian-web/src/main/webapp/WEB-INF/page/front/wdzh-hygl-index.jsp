<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="css/hhncss.css"></link>
<jsp:include page="/include/head.jsp"></jsp:include>

<script type="text/javascript" src="script/jbox/jquery.jBox-2.3.min.js"></script>
<link rel="stylesheet" href="css/jbox/Gray/jbox.css" type="text/css"></link>
<style>
.bcbtn9 {
	font-size: 14px;
	line-height: 34px;
	color: #333;
	cursor: pointer;
	background: url(images/sprit.png) no-repeat -111px -100px;
	text-align: center;
	height: 31px;
	width: 88px;
	border: none;
	display: inline-block;
	font-size: 12px
}
</style>
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
					<div class="wdzh_cztx_one">
						<span>温馨提示：</span>请不要推荐给不熟悉的人士，避免给别人带来不必要的困扰。请把下边的链接地址发给您的好友，这样您就成了他的推荐人。你推荐的好友注册投资后，你将获得包括一次性的推荐奖和持续3年的投资提成。奖励细则请查看帮助中心推荐好友规则。
					</div>
					<div style="height: 20px" class="cle"><font color="#000000" size="3">&nbsp;&nbsp;&nbsp;&nbsp;您的用户推广ID为：<s:property value="#request.userId1"/></font></div>
					<div class="cle"></div><br/>
					<div class="cle"></div>
					<div class="wdzh_hygl_lj">
						<input type="text" value="${url}reg.do?param=${userId}" readonly="readonly" id="link" style="width: 200px;height: 22px; margin-left: 20px;" /> <input type="button" id="copy_button"
							value="复制链接" class="bcbtn9" style="margin-left: 10px;" />
					</div>
					<div class="cle"></div>
					<div class="cle"></div>
					<div class="wdzh_hygl_one" style="margin-left:20px; margin-top:20px;" id="dataInfo">
						<span id="dataInfo"> <img src="../images/admin/load.gif" class="load" alt="加载中..." /></span>
					</div>
					<div class="cle"></div>
				</div>
				<div class="cle"></div>
			</div>
		</div>
		<div class="cle"></div>
	</div>
	<jsp:include page="/include/footer.jsp"></jsp:include>
	<script type="text/javascript" src="script/ZeroClipboard.js"></script>
	<script type="text/javascript">
		var clip = null;
		$(function() {
			$(".wdzh_next_left li").eq(5).addClass("wdzh_next_left_ong").find("a").attr("style","color: rgb(233, 71, 24);");
			clip = new ZeroClipboard.Client();
			clip.setHandCursor(true);
			clip.addEventListener("mouseOver", my_mouse_over);
			clip.addEventListener("complete", my_complete);
			clip.glue("copy_button");
		});

		function my_mouse_over(client) {
			clip.setText($("#link").val());
		}
		function my_complete(client, text) {
			jBox.tip("已复制到剪贴板", "success", {
				top : "31%",
				timeout: 900
			});
		}
	</script>
	<script type="text/javascript">
			$(function(){
				param["pageBean.pageNum"] = 1;
				initListInfo(param);
				$("#bt_search").click(function(){
					param["pageBean.pageNum"] = 1;
					initListInfo(param);
				});
			});
			
			function initListInfo(praData){
		 		$.post("friendManagerList.do",param,initCallBack);
		 	}
		 	
		 	function initCallBack(data){
				$("#dataInfo").html(data);
			}
			
		</script>
</body>
</html>