<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<title>更新内容</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="This is my page" />
		<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
		
		<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
		<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
		<script type="text/javascript" src="../common/dialog/popup.js"></script>
		<script language="javascript" type="text/javascript" src="../My97DatePicker/WdatePicker.js"></script>
		
		<script>
			 function closeWin(){
			   window.parent.close();
			}
		
		</script>
		
	</head>
	<body>
			<div id="right"
				style="background-image: url(../images/admin/right_bg_top.jpg); background-position: top; background-repeat: repeat-x;">
				<div style="padding: 15px 10px 0px 10px;">
					<h4>${paramMap.title }</h4>
					<div style="width: auto; background-color: #FFF; padding: 10px;">
												<table width="100%" border="0" cellspacing="1" cellpadding="3">
							<tr>
								<td style="width: 100px; height: 25px;" align="right"
									class="blue12">
									标题：
								</td>
								<td align="left" class="f66">
									${paramMap.title }</td>
							</tr>
							
							<tr>
								<td style="width: 100px; height: 25px;" align="right"
									class="blue12">
									发布人：
								</td>
								<td align="left" class="f66">
								${paramMap.publisher }
									</td>
							</tr>
							<tr>
								<td style="width: 100px; height: 25px;" align="right"
									class="blue12">
									发布时间：
								</td>
								<td align="left" class="f66">
								  ${paramMap.publishTime}
								</td>
							</tr>
					        <tr>
							  <td style="width: 100px; height: 25px;" align="right"
									class="blue12">
									浏览次数：
								</td>
								<td align="left" class="f66">
								${paramMap.browseNum }
									</td>
							</tr>
							<tr>
								<td style="width: 100px; height: 25px;" align="right"
									class="blue12">
									图片：
								</td>
								<td align="left" class="f66">
									<img id="img" src="${basePath}/${paramMap.imgPath }" style="width: 100px;height: 100px;"/>
									</td>
							</tr>
							
							
							<tr>
								<td style="height: 25px;" align="right" class="blue12">
									内容：
								</td>
								<td align="left" class="f66">
									&nbsp;</td>
							</tr>
							
							<tr>
								<td style="height: 25px;" align="right" class="blue12">
									&nbsp;
								</td>
								<td align="left" class="f66">
								${paramMap.content }
									</td>
							</tr>
							
							
							<tr>
								<td height="36" align="right" class="blue12">
									&nbsp;
								</td>
								<td>
									<button id="btn_save" onclick="closeWin();"
										style="background-image: url('../images/admin/btn_queding.jpg'); width: 70px; border: 0; height: 26px"></button>
									&nbsp;
									</td>
							</tr>
						
						</table>
						<br />
					</div>
				</div>
			</div>
	</body>
</html>
