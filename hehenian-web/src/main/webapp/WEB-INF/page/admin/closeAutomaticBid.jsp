<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<title>关闭用户自动投标</title>
		<link href="../css/admin/admin_css.css" rel="stylesheet"
			type="text/css" />
		<link href="../css/css.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
		<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
		<script type="text/javascript" src="../common/dialog/popup.js"></script>
		<script language="javascript" type="text/javascript" src="../script/jbox/jquery.jBox-2.3.min.js"></script>
		<script>
		function closeBid() {
		    param['paramMap.id'] = ${paramMap.id};
		    $.shovePost('closeAutomaticBid.do',param,function(data){
				if (data == "1") {
					alert("关闭成功！")
					window.parent.closeMthod();
				}
				if (data == "2") {
					alert("关闭失败！");
					return;
				}
			});
		} 
		</script>
		
	</head>
	<body>
		<div id="right">
			<div style="padding: 15px 10px 0px 10px;">
				<div style="width: auto; background-color: #FFF; padding: 20px;">
					<table width="80%" border="1" cellspacing="1" cellpadding="3"  align="center" >
						<tr>
							<td style="width: 50%; height: 30px;" align="right"
								class="blue12">
								用户名：
							</td>
							
							<td align="center" class="f66">
								${paramMap.username}
							</td>
							
						</tr>
						<tr>
							<td style="width: 50%; height: 30px;" align="right"
								class="blue12">
								单笔投标金额：
							</td>
							<td align="center" class="f66">
								${paramMap.bidAmount}
							</td>
						</tr>
							<tr>
							<td style="width: 50%; height: 30px;" align="right"
								class="blue12">
								账户保留金额：
							</td>
							<td align="center" class="f66">
								${paramMap.remandAmount}
							</td>
						</tr>
						<tr>
							<td style="width: 50%; height: 30px;" align="right"
								class="blue12">
								自动投标设置时间：
							</td>
							<td align="center" class="f66">
								${paramMap.bidSetTime}
							</td>
						</tr>
						<tr>
							<td style="width: 50%; height: 30px;" align="right"
								class="blue12">
								自动投标状态：
							</td>
							<td align="center" class="f66">
								<s:if test="paramMap.bidStatus==2">开</s:if>
								<s:elseif test="paramMap.bidStatus==1">关</s:elseif>
							</td>
						</tr>
					</table>
				</div>
				<div style="width: auto; background-color: #FFF; padding: 20px;">
					<table style="border-collapse: collapse; border-color: #cccccc"
						cellspacing="0" cellpadding="0" width="100%" align="center" border="0">
						<tbody>
							<tr>
								<td height="36" align="center" class="blue12">
									<input type="button" value="取消" name="cancle" id="cancle" onclick="window.parent.closeMthodes()" />
									<input type="button" value="关闭用户自动投标" name="closeBid" id="closeBid" onclick="closeBid()"/>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</body>
</html>
