﻿<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		 <title>合和年在线后台管理</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="This is my page" />
		<link href="css/admin_css.css" rel="stylesheet" type="text/css" />
		 <script type="text/javascript" src="script/jquery-1.7.1.min.js"></script>
		<script>
			var URL ="";    
			var time =5; //倒计时的秒数   
			$(function(){
				var obj=$("#msg-link li").first();
				URL=$(obj).find("a").attr("href");
				Load();
			});
			function Load(){    
					for(var i=time;i>=0;i--){    
						window.setTimeout("doUpdate("+ i +")", (time-i) * 1000);    
					}    
				}    
				
			function jump(){		
				  	window.location=URL;
				}
			function doUpdate(num){    
				$("#times").html(num)
				if(num == 0){
				   jump();
				}    
			} 
			
		</script>
	</head>
	<body>
		<div id="right"
			style="background-image: url; background-position: top; background-repeat: repeat-x;">
			<div style="padding: 15px 10px 0px 10px;">
				<div style="width: auto; padding: 10px; text-align: center;">
					<table align="center" width="400">
						<tr>
							<td width="50" valign="top">
								<s:if test="#session.helpMessage==null">
									<img src="images/admin/warning.gif" width="32"
										height="32" border="0" alt="warning" />
								</s:if>
								<s:else>
									<s:if test="#session.helpMessage.msg_type==0">
										<img src="images/admin/information.gif" width="32"
											height="32" border="0" alt="information" />
									</s:if>
									<s:elseif test="#session.helpMessage.msg_type==1">
										<img src="images/admin/warning.gif" width="32"
											height="32" border="0" alt="warning" />
									</s:elseif>
									<s:else>
										<img src="images/admin/confirm.gif" width="32"
											height="32" border="0" alt="confirm" />
									</s:else>
								</s:else>
							</td>
							<td style="font-size: 14px; font-weight: bold">
								<s:if test="#session.helpMessage==null">
								页面已过期
							</s:if>
								<s:else>
									<s:property value="#session.helpMessage.title" />
								</s:else>
							</td>
						</tr>
						<tr>
							<td></td>
							<td id="redirectionMsg">

								如果您不做出选择，将在
								<span id="times"></span>秒后为你跳转到第一个连接
							</td>
						</tr>
						<tr>
							<td></td>
							<td>
								<ul style="margin: 0; padding: 0 10px" class="msg-link"
									id="msg-link">
									<s:if test="#session.helpMessage==null">
										<li>
											<a href="default.do" >返回主页</a>
										</li>
									</s:if>
									<s:else>
										<s:iterator value="#session.helpMessage.msg" var="c" 
										    status="sta">
											<li>
												<a href='<s:property escape="false" value="#session.helpMessage.url[#sta.index]"/>'>${c}</a>
											</li>
										</s:iterator>
									</s:else>
								</ul>
							</td>
						</tr>
					</table>
					<br />
				</div>
			</div>
		</div>
	</body>
</html>
