<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>未找到您要找的页面</title>
		<link rel="shortcuticon" href="images/favicon.ico" type="image/x-icon" />
		
		<script>
			function jump(){
				window.parent.location="/account/login-index.do";
			}
			function close(){
				window.parent.close();	
			}
			
		</script>
		<style type="text/css">
			<!--
			#errorbody {
				PADDING: 0px;
				MARGIN: 0px;
				FONT: 14px Arial, Helvetica, sans-serif;
				color: #000000;
				background-color:#F0F0F0;
				line-height:200%;
			}
			#errorbody h3 { font-size:16px; font-weight:bold;}
			#errorbody A {	COLOR: #007ab7; TEXT-DECORATION: none}
			#errorbody A:hover {COLOR: #007ab7; TEXT-DECORATION: none}
			#errorbody table { margin:0px auto; text-align:left; width:980px; border:1px solid #cccccc; height:360px;}
			#errorbody .tu {  padding-left:100px; padding-top:45px; width:110px;}
			#errorbody .wen {  padding-left:40px; padding-top:40px;}
			-->
		</style>
	</head>
	<body id="errorbody">
		<table cellpadding="0" cellspacing="0" style="margin-top: 100px;">
		  <tr>
		    <td valign="top" class="tu"><img src="../images/errorimg.jpg" width="106" height="118" style="padding-top:15px;" /></td>
		    <td valign="top" class="wen"><h3>抱歉，你的登录已经过期……</h3>
		      <p><strong>点击以下链接继续浏览网站的其它内容</strong><br />
		        <a title="返回登录页面" href="javascript:jump();">返回登录页面</a><br />
		    <a title="关闭页面" href="javascript:close();">关闭页面</a></p></td>
		  </tr>
		</table>
	</body>
</html>