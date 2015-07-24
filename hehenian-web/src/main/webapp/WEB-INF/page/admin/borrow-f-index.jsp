<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<html>
	<head>
		<title>借款管理首页</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="This is my page" />
		<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
	</head>
	<body>
		<div id="right">
			<div style="padding: 15px 10px 0px 10px;">
				<div>
					<table  border="0" cellspacing="0" cellpadding="0">
						<tr>
							  <td width="80" height="28" class="main_alll_h2">
								<a href="borrowf.do">待审核的借款</a>
							</td>
							<td width="2">
								&nbsp;
							</td>
							<td width="100" height="28" class="xxk_all_a">
								<a href="borrowTenderIn.do">招标中的借款</a>
							</td>
							<td width="2">
								&nbsp;
							</td>
							  <td width="100" height="28"  class="xxk_all_a">
								<a href="borrowFlowMark.do">流标的借款</a>
							</td>
							<td>
								&nbsp;
							</td>
						</tr>
					</table>
				</div>
				<div style="padding-right: 10px; padding-left: 10px; padding-bottom: 10px; width: 1120px; padding-top: 10px; background-color: #fff;">
					<table style="margin-bottom: 8px;" cellspacing="0" cellpadding="0"
						width="100%" border="0">
						<tbody>
							<tr>
								<td class="f66" align="left" width="50%" height="36px">
									用户名:
									<input id="userName" value="" />&nbsp;&nbsp; 
									借款类型：
									<s:select id="borrowWay" list="#{-1:'--请选择--',1:'薪金贷',2:'生意贷',3:'业主贷',4:'实地考察借款',5:'机构担保借款'}"></s:select>
									<input id="search" type="button" value="查找" name="search" />
								</td>
							</tr>
						</tbody>
					</table>
		             <span id="divList"><img src="../images/admin/load.gif" class="load" alt="加载中..." /></span>
					<div>
	</div>
</div>
			</div>
		</div>
	</body>
<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
<script type="text/javascript">
var falg = false;
			$(function(){
				initListInfo(param);
				$('#search').click(function(){
					param["pageBean.pageNum"] = 1;
				   initListInfo(param);
				});				
			});			
			function initListInfo(praData){
				praData["paramMap.userName"] = $("#userName").val();
				praData["paramMap.borrowWay"] = $("#borrowWay").val();
		 		$.shovePost("borrowflist.do",praData,initCallBack);
		 	}
		 	function initCallBack(data){
				$("#divList").html(data);
			}
			function rebackAction(obj){
			   var r=confirm("撤消操作,是否继续?");
	           if(r == true){
	        	  if (falg) return ;
	              param['paramMap.id'] = obj;
	              $.shovePost('reBackBorrowFistAudit.do',param,function(data){
		               var callBack = data.msg;
		               if(callBack == 1){
		                  alert("操作成功!");
		                  falg = true;
		                  window.location.href="borrowf.do";
		                  return false;
		               }
		               falg = false;
		               alert(callBack);
		          });
	            }
			}
</script>
</html>
