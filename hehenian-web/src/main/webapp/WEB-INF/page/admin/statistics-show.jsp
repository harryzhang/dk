<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<html>
	<head>
		<title>认证资料统计查看</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="This is my page" />
		<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
		<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
		<script type="text/javascript" src="../css/admin/popom.js"></script>
	</head>
	<body>
	<div id="right">
			<div style="padding: 15px 10px 0px 10px;">
				<div>
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="120" height="28" class="main_alll_h2">
								<a href="javascript:void(0)">认证资料统计查看</a>
							</td>
							<td width="2">
								&nbsp;
							</td>
							<td width="2">
								&nbsp;
							</td>
							<td width="2">
								&nbsp;
							</td>
							<td>
								&nbsp;
							</td>
						</tr>
					</table>
					<div
						style="padding-right: 10px; padding-left: 10px; padding-bottom: 10px; width: 1120px; padding-top: 10px; background-color: #fff;">
						<table style="margin-bottom: 8px;" cellspacing="0" cellpadding="0"
							width="100%" border="0">
							<tbody>
					
							</tbody>
						</table>
						<span id="dataInfo">
						
							<div id="right"
			style="background-image: url; background-position: top; background-repeat: repeat-x;">
			<div style="padding: 15px 10px 0px 10px;">
				<div>
					
					<div
						style="padding-right: 10px; padding-left: 10px; padding-bottom: 10px; width: 1120px; padding-top: 10px; background-color: #fff;">
						<table style="margin-bottom: 8px;" cellspacing="0" cellpadding="0"
							width="100%" border="0">
							<tbody>
							
						         <tr>
									<td class="f66" align="left" width="25%" height="36px">
										用户名&nbsp;：&nbsp;&nbsp;${CountMsgMap.username }
									</td>
								 </tr>
								 <tr>
									<td class="f66" align="left" width="25%" height="36px">
										认证种类：&nbsp;&nbsp;${CountMsgMap.tyname }
									</td>
								</tr>
								<tr>
									<td class="f66" align="left" width="50%" height="36px">
										审核观点：&nbsp;&nbsp;${CountMsgMap.tmoption }
									</td>
								</tr>
								<tr>
									<td class="f66" align="left" width="25%" height="36px">
										证件图片：&nbsp;&nbsp;
										
									</td>
								</tr>
								<tr>
									<td class="f66" align="left" width="25%" height="36px">
									<table id="gvNews" style="width: 100%; color: #333333;"
			cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="0">
			<tbody>
			
				<s:if test="#request.PictureList==null || #request.PictureList.size==0 ">
					<tr align="center" class="gvItem">
						<td colspan="7">暂无数据</td>
					</tr>
				</s:if>
				<s:else>
				
				<s:iterator value="#request.PictureList" var="bean" status="sta">
				
					<tr class="gvItem">
						<td>
							${sta.count }
						</td>
						<td>
					  <img src="../${imagePath }" width="150px"	height="150px"/>
					</td>
						<td>
					  <a href="javascript:void(0)" onclick="show(${tmdid},${CountMsgMap.id })">查看</a>
					</td>
					</tr>
				</s:iterator>
				</s:else>
			</tbody>
		</table>
										
									</td>
								</tr>
								
								<tr>
								<td>&nbsp;</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
			</div>
			</div>
						
						 </span>
					</div>
				</div>
			</div>
			</div>

	</body>
	
	<script >
		function show(id,uid){
   			  var url = "queryoneindex.do?tmdid="+id+"&i="+uid;
              ShowIframe("查看单个图片详情",url,1000,1000);
   			}
   	</script>
</html>
