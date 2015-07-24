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
</head>
	<body>
	<form id="addAdmin" action="circulationRepayAdd.do" method="post">
			<div id="right"
				style="background-image: url; background-position: top; background-repeat: repeat-x;">
				<div style="padding: 15px 10px 0px 10px;">
					<div>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="100" height="28" align="center" 
									class="xxk_all_a">
									<a href="circulationRepayRecordInit.do">流转标还款记录</a>
								</td>
								<td width="2">
									&nbsp;
								</td>
								<td width="150" align="center" class="main_alll_h2">
									添加流转标还款记录
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
									借款标题：
								</td>
								<td align="left" class="f66">
								    ${borrowTitle}
								</td>

							</tr>
							<tr>
								<td style="width: 100px; height: 25px;" align="right"
									class="blue12">
									还款金额：
								</td>
								<td align="left" class="f66">
									<s:textfield name="paramMap.amount" theme="simple"
										cssClass="in_text_2" cssStyle="width: 150px" ></s:textfield>
									<span style="color: red;">*<s:fielderror
											fieldName="paramMap['amount']" />
									</span>
								</td>
							</tr>
							<tr>
								<td style="width: 100px; height: 25px;" align="right"
									class="blue12">
									备注：
								</td>
								<td align="left" class="f66">
								    <s:textarea name="paramMap.remark" theme="simple"
										cols="30" rows="10"> </s:textarea>
								    <span style="color: red;"><s:fielderror
											fieldName="paramMap['remark']" />
									</span>
								</td>
							</tr>
							<tr>
								<td height="25">
								<span style="color: red;"><s:fielderror
											fieldName="paramMap['action']" />
									</span>
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
                                <button id="btn_save"  type="submit" style="background-image: url('../images/admin/btn_queding.jpg');width: 70px;border: 0;height: 26px"  ></button>
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
		</form>
	</body>
</html>
