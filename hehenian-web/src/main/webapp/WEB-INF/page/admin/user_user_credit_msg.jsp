<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="../css/css.css" />
<script src="../script/jquery-1.7.1.min.js" type="text/javascript"></script>
<script>
	
</script>
</head>
<body>
	<div class="nymain" style="width: 90%;margin: 15px auto;">
		<div class="wdzh" style="width: 98%;">
			<div class="r_main" style="width: 95%;margin-left: 12px;">
				<div class="box" style="width: 100%;text-align: center;" align="center">
					<h2 style="font-size: 14px;margin: 10px auto;padding: 0;text-align: center;">合和年网贷认证</h2>
					<div class="boxmain2" style="padding: 10px 35px 10px 35px;">
						<div class="biaoge" style="margin-top: -8px;">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<s:if test="pageBean.page==null || pageBean.page.size==0">
									<tr align="center" class="gvItem">
										<td colspan="2">暂无数据</td>
									</tr>
								</s:if>
								<s:else>
									<s:iterator value="pageBean.page" var="bean" status="s">
										<tr class="gvItem">
											<td style="width: 200px;">${bean.name }</td>
											<td style="width: 200px;">
												<s:if test="#bean.auditStatus==3"><a style="color:blue;">通过</a><img style="margin-left: 20px;" alt="ok" src="../images/neiye2_44.jpg"> </s:if> 
												<s:elseif test="#bean.auditStatus==2"><a style="color:red;">不通过</a></s:elseif> 
												<s:elseif test="#bean.auditStatus==1"><a style="color:gray;">待审核</a></s:elseif>
												<s:else><a style="color:green;">待上传</a></s:else>
											</td>
										</tr>
									</s:iterator>
								</s:else>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
