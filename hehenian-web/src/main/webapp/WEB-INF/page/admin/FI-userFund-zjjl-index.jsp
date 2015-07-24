<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<html>
	<head>
		<title>财务管理-用户资金管理</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="This is my page" />
		<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
		<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
		<script language="javascript" type="text/javascript" src="../My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript" src="../css/popom.js"></script>
		<script type="text/javascript">
			$(function(){
				param["pageBean.pageNum"] = 1;
				initListInfo(param);
				$("#bt_search").click(function(){
					param["pageBean.pageNum"] = 1;
					initListInfo(param);
				});
				$("#excel").click(function(){
				      var userId=$("#userId").val();
				      var userName= $("#userName").val();
                     window.location.href=encodeURI(encodeURI("exportUserFundRecordList.do?userId="+userId+"&&userName="+userName));
                });
				
			});
			
			function initListInfo(param){
				param["paramMap.userId"] = $("#userId").val();
				param["paramMap.userName"] = $("#userName").val();
		 		$.shovePost("queryUserFundRecordList.do",param,initCallBack);
		 	}
		 	
		 	function initCallBack(data){
				$("#dataInfo").html(data);
			}
						
		</script>
	</head>
	<body>
	<s:hidden id="userId" name="paramMap.userId" ></s:hidden>
		<div id="right">
			<div style="padding: 15px 10px 0px 10px;">
				<div>
					
					<div
						style="padding-right: 10px; padding-left: 10px; padding-bottom: 10px; width: 1120px; padding-top: 10px; background-color: #fff;">
						<table style="margin-bottom: 8px;" cellspacing="0" cellpadding="0"
							width="100%" border="0">
							<tbody>
								<tr>
									<td class="f66" align="left" width="50%" height="36px">
										用户名：
										<input id="userName" name="paramMap.username" type="text" />
										

										<input id="bt_search" type="button" value="查 找" />
										&nbsp;&nbsp;
										<input id="excel" type="button" value="导出EXCEL" />
									</td>
								</tr>

							</tbody>
						</table>
						<span id="dataInfo"> </span>
					</div>
				</div>
			</div>
			</div>
	</body>
</html>
