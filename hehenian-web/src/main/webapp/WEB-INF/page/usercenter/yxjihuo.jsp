<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%--   <link href="css/css.css" rel="stylesheet" type="text/css" />--%>
<script type="text/javascript" src="common/date/calendar.js"></script>
<script src="script/jquery-1.7.1.min.js" type="text/javascript"></script>
<style>
.nymain{ width: 1000px; margin:0 auto; overflow: hidden; height: auto;  color: #666; margin-top:15px }
.nymain .bigbox { border-right-width: 1px; border-bottom-width: 1px; border-left-width: 1px; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-right-color: #c6d0d6; border-bottom-color: #c6d0d6; border-left-color: #c6d0d6; overflow: hidden; height: auto; }
.nymain .bigbox .til { font-family: "微软雅黑"; font-size: 18px; line-height: 36px; color: #333; background-image: url(images/neiye5_03.jpg); background-repeat: repeat-x; text-align: center; height: 40px; font-weight: normal; }
.nymain .bigbox .sqdk { overflow: hidden; height: 100%; position: relative; }
.nymain .bigbox .sqdk .logintab { float: left; width: 408px; }
.nymain .bigbox .sqdk .logintab table { line-height: 42px; }
.nymain .bigbox .sqdk .logintab table tr th { font-family: "微软雅黑"; font-size: 14px; font-weight: normal; color: #333; }
.nymain .bigbox .sqdk .logintab table tr .zctd { color: #333; }
.nymain .bigbox .sqdk .logintab table tr .zctd a { color: #136dad; text-decoration: underline; }
.nymain .bigbox .sqdk .logintab table tr .zctd a:hover { text-decoration: none; color: #00315d; }
.nymain .bigbox .sqdk .logintab table tr .mima span a { color: #136dad; }
.nymain .bigbox .sqdk .logintab table tr .mima span a:hover { color: #00315d; text-decoration: underline; }
</style>
<script>
	$(function() {
		$('.tabmain').find('li').click(function() {
			$('.tabmain').find('li').removeClass('on');
			$(this).addClass('on');
			$('.lcmain_l').children('div').hide();
			$('.lcmain_l').children('div').eq($(this).index()).show();
		})
	})
</script>
<jsp:include page="/include/head.jsp"></jsp:include>
</head>
<jsp:include page="/include/top.jsp"></jsp:include>
<body>

	<div class="nymain">
		<div class="bigbox">
			<div class="til">邮箱激活</div>
			<div class="sqdk" style="padding-top:55px; padding-bottom:80px; padding-left:280px; background:none;">
				<div class="logintab">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<th align="center" class="jihuo">邮件已经发送到你的邮箱,请<a style="cursor: pointer; color: red;" onclick="window.open('http://${emaladdresss}');">登录</a>到你的邮箱验证</th>
						</tr>
					</table>
				</div>
			</div>
		</div>
	</div>
	<!-- 引用底部公共部分 -->
	<jsp:include page="/include/footer.jsp"></jsp:include>
	<script type="text/javascript" src="css/popom.js"></script>

	<script>
		$(function() {
			//样式选中
			$("#zh_hover").attr('class', 'nav_first');
			$("#zh_hover div").removeClass('none');
		});
	</script>
</body>
</html>
