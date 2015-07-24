<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<jsp:include page="/include/head.jsp"></jsp:include>
<link id="skin" rel="stylesheet" href="css/jbox/Gray/jbox.css" />
<script language="javascript" type="text/javascript" src="My97DatePicker/WdatePicker.js"></script>
</head>
<body>
	<!-- 引用头部公共部分 -->
         <div style="position: absolute; height:54px; line-height:54px; background:#f3f3f3; width:100%; margin-top:151px !important; margin-top:177px;border-bottom:1px solid #e5e5e5;border-top:1px solid #e5e5e5;"></div>
	<jsp:include page="/include/top.jsp"></jsp:include>
	<div class="wytz_center">
    <div class="mjd_tzlc_all"><jsp:include page="/include/menu_userManage.jsp"></jsp:include></div>
		<div class="wdzh_top">
			
			<div class="wdzh_next" style="display: block;">
				<div class="wdzh_next_left">
					<jsp:include page="/include/menu_userManage_4_left.jsp"></jsp:include>
				</div>
			</div>
			<div class="wdzh_next_right">
				<h2 class="ggxxxx_box_h2" style="padding-left: 10px;"></h2>
				<div class="ggxxxx_box">
					<h3>
						<span> 开设汇付账户 </span>
					</h3>
					<table width="770" border="0" style="margin-bottom: 10px;">
						<tr>
							<td nowrap="nowrap" style="padding-left: 20px;">
							汇付会员编号: <s:if test="#request.usrCustId>0"> ${usrCustId } 
										<a class="bcbtn" style="margin-left: 10px;" href="loginHF.do" target="_blank">登陆汇付</a>
								</s:if> <s:else>
									<span style="color:#e94718;font-size: 12px;"> 您还不是汇付天下会员</span>
									
										<a class="bcbtn" style="margin-left: 10px;" href="registerChinaPnr.do" target="_blank">点击注册</a>
								</s:else>
								</td>
						</tr>
					</table>
				</div>
			</div>
			<div class="cle"></div>
			<div id="zhuce" style="display: none;"></div>
		</div>
	</div>
	<div class="cle"></div>
	<!-- 引用底部公共部分 -->
	<jsp:include page="/include/footer.jsp"></jsp:include>
	<script type="text/javascript" src="script/jquery-1.7.1.min.js"></script>
	<script type="text/javascript" src="script/jquery.shove-1.0.js"></script>
	<script type="text/javascript" src="common/date/calendar.js"></script>
	<script type="text/javascript" src="css/popom.js"></script>
	<script>
		$(function() {
			//样式选中
			$(".wdzh_next_left li").eq(2).addClass("wdzh_next_left_ong").find("a").attr("style","color: rgb(233, 71, 24);");
			//	dqzt(0);
			//	$('#li_1').addClass('on');
		});

		$(document).ready(function() {
			if ('${idNo}' == null || '${idNo}' == '') {
				alert("请先完善个人资料!");
				window.location.href = "owerInformationInit.do";
				return false;
			}
		});

	</script>
</body>
</html>
