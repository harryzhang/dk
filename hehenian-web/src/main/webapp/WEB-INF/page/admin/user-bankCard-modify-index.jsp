<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<%@ include file="/include/includeJs.jsp"%>
<html>
	<head>
		<title>财务管理-用户银行卡管理</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="This is my page" />
		<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="../My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript" src="../css/admin/popom.js"></script>
		
		<script type="text/javascript">
			$(function(){
				param["pageBean.pageNum"] = 1;
				initListInfo(param);
				$("#bt_search").click(function(){
					param["pageBean.pageNum"] = 1;
					initListInfo(param);
				});
			});
			
			function initListInfo(param){
				param["paramMap.userName"] = $("#userName").val();
				param["paramMap.checkUser"] = $("#checkUser").val();
				param["paramMap.realName"] = $("#realName").val();
				param["paramMap.commitTimeStart"]= $("#commitTimeStart").val();
				param["paramMap.commitTimeEnd"]= $("#commitTimeEnd").val();
				param["paramMap.cardStatus"]= $("#cardStatus").val();
				
		 		$.shovePost("queryUserModifiyBankList.do",param,initCallBack);
		 	}
		
		
		 	function initCallBack(data){
				$("#dataInfo").html(data);
			}
		function show(id) {
		$.jBox("iframe:queryOneBankCardEditShowInfo.do?bankId=" + id, {
		    title: "银行卡详情显示",
		    width: 600,
		    height: 420,
		    buttons: {'确定':true}
		});
	}		
		</script>
	</head>
	<body>
		<div id="right">
			<div style="padding: 15px 10px 0px 10px;">
				<div>
					<table border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="120" height="28" class="xxk_all_a">
								<a href="queryUserBankInit.do">银行卡管理</a>
							</td>
							<td width="2">
								&nbsp;
							</td>
							<td width="120" class="main_alll_h2">
								<a href="javascript:void(0)">银行卡变更</a>
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
								<tr>
									<td class="f66" align="left" width="50%" height="36px" >
										用户名：
										<input id="userName" name="paramMap.username" type="text"  class="inp80"/>
										&nbsp;&nbsp;
										审核人：
										<s:select id="checkUser"  list="checkers" name="paramMap.checkUser" listKey="id" listValue="userName" headerKey="-100" headerValue="--请选择--" />
										&nbsp;&nbsp;
										真实姓名：
										<input id="realName" name="paramMap.realName" type="text"  class="inp80"/>
										&nbsp;&nbsp;
										申请时间：
										<input id="commitTimeStart"  class="inp80"  name="paramMap.commitTime" type="text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})"/>
										&nbsp;-&nbsp;
										<input id="commitTimeEnd"   class="inp80" name="paramMap.commitTime" type="text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})"/>
									   &nbsp;&nbsp;
									    状  &nbsp;态：
									<s:select id="cardStatus" list="#{-1:'-请选择-',2:'审核中',3:'未通过'}" value="#request.types" ></s:select>
										<input id="bt_search" type="button" value="搜索"  />
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
