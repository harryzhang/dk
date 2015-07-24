<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<html>
	<head>
		<title>用户注册管理列表</title>
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
		<script type="text/javascript">
			$(function(){
				
				param["pageBean.pageNum"] = 1;
				initListInfo(param);
				$("#bt_search").click(function(){
				var param = {};
			    param["pageBean.pageNum"] = 1;
			    param["paramMap.userName"] = $("#userName").val();
			    param["paramMap.phone"] = $("#phone").val();
			    param["paramMap.startTime"] = $("#startTime").val();
			    param["paramMap.endTime"] = $("#endTime").val();
			    param["paramMap.source"] = $("#source").val();
					initListInfo(param);
				});
				
					
			});
			
			function initListInfo(praData){
		 		$.post("userRegisterManageList.do",praData,initCallBack);
		 	}
		 	
		 	function initCallBack(data){
		 	  
				$("#dataInfo").html(data);
			}
			
		 	function checkAll(e){
		   		if(e.checked){
		   			$(".downloadId").attr("checked","checked");
		   		}else{
		   			$(".downloadId").removeAttr("checked");
		   		}
   			}
		 	
		</script>
	</head style="min-width: 1000px">
	<body>
		<div id="right">
			<div style="padding: 15px 10px 0px 10px;">
				<div>
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="120" height="28" class="main_alll_h2">
								<a href="userRegisterManageList.do">用户注册管理列表</a>
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
								<tr>
									<td class="f66" align="left" width="50%" height="36px">
										用户名：
										<input id="userName" name="paramMap.userName" type="text" />
										手机号码：
										<input id="phone" name="paramMap.phone" type="text" />
										注册时间：
										<input id="startTime" name="paramMap.startTime" type="text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:'readOnly'})"/>
										--
										<input id="endTime" name="paramMap.endTime" type="text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:'readOnly'})"/>
										用户来源：
										
										<select id="source" name="source">
                                <option value="" ${paramMap.source
                                eq "" ? "selected=selected" : "" }>
                                    请选择
                                </option>
                                <option value="0" ${paramMap.source
                                eq 0 ? "selected=selected" : ""}>
                                    自动导入
                                </option>
                                <option value="1" ${paramMap.source
                                eq 1 ? "selected=selected" : ""}>
                                    网站注册
                                </option>
										<input id="bt_search" type="button" value="搜索"  />
									</td>
								</tr>
							</tbody>
						</table>
						<span id="dataInfo"> </span>
					</div>
				</div>
			</div>
	</body>
</html>
