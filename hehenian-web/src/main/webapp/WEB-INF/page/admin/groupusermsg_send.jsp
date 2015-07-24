<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<title>资料下载-添加</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="This is my page" />
		<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
		
		<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
		<link rel="stylesheet" href="../kindeditor/themes/default/default.css" />
	
		<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
		
		
		<script>
		
			
		  $(function(){
		      
		      //提交表单
				$("#btn_save").click(function(){
					
					var paramm = $("#editForm").serialize();
					$.shovePost("sendGroupMessage.do",paramm,function(data){
						if(data == 1){
							alert("发送成功");
							window.close();
						}else if(data == -2){
							$("#span_content").html("内容不能为空");
							$("#btn_save").show();
						}else{
							alert("发送失败");
							$("#btn_save").show();
						}
					});
					return false;	
				});
		  });
		   
	    
		
			
		</script>
		
	</head>
	<body>
		<form id="editForm" name="addTeam" action="sendGroupMessage.do" method="post">
			<div id="right">
				<div style="padding: 15px 10px 0px 10px;">
					<div>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="100" height="28"  class="main_alll_h2">
									发送短信
								</td>
								
								<td>
									&nbsp;
								</td>
							</tr>
						</table>
					</div>
					 
					<div style="width: auto; background-color: #FFF; padding: 10px;">
						<table width="100%" border="0" cellspacing="1" cellpadding="3">
												
							<tr>
								<td style="height: 25px;" align="right" class="blue12">
									短信内容：
								</td>
								<td align="left" class="f66">
									<s:textarea id="tr_content" name="paramMap.content" rows="5" cols="30" theme="simple"></s:textarea>
									<s:hidden name="paramMap.groupId"></s:hidden>
									<span class="require-field">*<span id="span_content"></span></span>
								</td>
							</tr>
							
							
							
							
							
							<tr>
								<td height="36" align="right" class="blue12">
									<s:hidden name="action"></s:hidden>
									&nbsp;
								</td>
								<td>
									<button id="btn_save"
										style="background-image: url('../images/admin/btn_queding.jpg'); width: 70px; border: 0; height: 26px"></button>
									&nbsp;
									
                                    <span id="messageInfo" class="blue12"></span>
									&nbsp; &nbsp;
									<span class="require-field"><s:fielderror fieldName="actionMsg" theme="simple"></s:fielderror></span>
								</td>
							</tr>
							<tr>
								<td colspan="2">
									<img id="img" src="../images/NoImg.GIF"
										style="display: none; width: 100px; height: 100px;" />
								</td>
							</tr>
						</table>
						<br />
					</div>
				</div>
			</div>
		</form>
	</body>
</html>
