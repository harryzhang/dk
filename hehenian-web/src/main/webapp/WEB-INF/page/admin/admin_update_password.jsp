<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="This is my page" />
		<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
		
		<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
		<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
		<script type="text/javascript" language="javascript">
	    	$(function(){
				//提交表单
				$("#btn_save").click(function(){
					$("#updatePassword").submit();
				});
			});
		</script>

	</head>
	<body>
	<s:form id="updatePassword" action="updatePassword" method="post">
	<s:hidden name="paramMap.id" />
			<div id="right">
				<div style="padding: 15px 10px 0px 10px;">
					<div>
						<table border="0" cellspacing="0" cellpadding="0">
							<tr>
								<!--<td width="100" height="28"   class="xxk_all_a">
									<a href="queryAdminInit.do">管理员列表</a>
								</td>
								--><td width="2">
									&nbsp;
								</td>
								<td width="100"   class="main_alll_h2">
									 修改密码
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
								<td style="width: 100px; height: 25px;" align="right"
									class="blue12">
									帐号：
								</td>
								<td align="left" class="f66">
								<s:hidden name="paramMap.userName" />
									<s:property value="paramMap.userName" />
								</td>
							</tr>
							<tr>
								<td style="height: 25px;" align="right" class="blue12">
									旧密码：
								</td>
								<td align="left" class="f66">
									<s:password name="paramMap.oldPassword" theme="simple"
										cssClass="in_text_2" cssStyle="width: 150px" />
									<span style="color: red;">*<s:fielderror
											fieldName="paramMap.oldPassword" />
									</span>
								</td>
							</tr>
							<tr>
								<td style="height: 25px;" align="right" class="blue12">
									新密码：
								</td>
								<td align="left" class="f66">
									<s:password name="paramMap.password" theme="simple"
										cssClass="in_text_2" cssStyle="width: 150px" />
									<span style="color: red;">*<s:fielderror
											fieldName="paramMap.password" />
									</span>
								</td>
							</tr>
							<tr>
								<td style="height: 25px;" align="right" class="blue12">
									确认密码：
								</td>
								<td align="left" class="f66">
									<s:password name="paramMap.confirmPassword" theme="simple"
										cssClass="in_text_2" cssStyle="width: 150px" />
									<span style="color: red;">*<s:fielderror
											fieldName="paramMap.confirmPassword" />
									</span>
								</td>
							</tr>
							
							<tr>
								<td height="25">
								</td>
								<td align="left" class="f66" style="color: Red;">
									<s:fielderror fieldName="paramMap.allError" />
								</td>
							</tr>
							<tr>
								<td height="36" align="right" class="blue12">
									&nbsp;
								</td>
								<td>
									&nbsp;
								</td>
							</tr>
							<tr>
								<td height="36" align="right" class="blue12">
									&nbsp;
								</td>
								<td>
                                <button id="btn_save" style="background-image: url('../images/admin/btn_queding.jpg');width: 70px;border: 0;height: 26px"  ></button>
                                &nbsp;<button type="reset" style="background-image: url('../images/admin/btn_chongtian.jpg');width: 70px;height: 26px;border: 0px"></button>&nbsp;
                                &nbsp;
                                <span style="color: red;">
                             	  <s:fielderror fieldName="actionMsg" theme="simple"></s:fielderror>
                                </span>
                            </td>
							</tr>
						</table>
						<br />
					</div>
				</div>
			</div>
		</s:form>
	</body>
</html>
