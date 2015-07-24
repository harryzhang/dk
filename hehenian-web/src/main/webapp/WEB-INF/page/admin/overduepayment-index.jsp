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
<link href="../css/admin/admin_custom_css.css" rel="stylesheet" type="text/css" />
<link href="../css/jbox/Gray/jbox.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<div id="right">
		<div style="padding: 15px 10px 0px 10px;">
			<div>
				<table border="0" cellspacing="0" cellpadding="0">
					<tr>
<%--						<td width="100" id="today" height="28" class="xxk_all_a"><a href="lateRepayInit.do">逾期的还款</a></td>--%>
						<td width="2">&nbsp;</td>
						<td width="100" id="today" height="28" class="main_alll_h2"><a href="overduePaymentInit.do">代偿借款</a></td>
						<td width="2">&nbsp;</td>
<%--						<td width="100" height="28" class="xxk_all_a"><a href="#">已代偿借款</a></td>--%>
						<td width="100" id="tomorrow" class="white12"></td>
					</tr>
				</table>
			</div>
			<div style="padding-right: 10px; padding-left: 10px; padding-bottom: 10px; width: 1120px; padding-top: 10px; background-color: #fff;">
				<table style="margin-bottom: 8px;" cellspacing="0" cellpadding="0" width="100%" border="0">
					<tbody>
						<tr>
							<td class="f66" align="left" width="50%" height="36px">用户名: <input id="userName" value="" maxlength="20" />&nbsp;&nbsp; 借款类型： <s:select id="borrowWay"
									list="#{-1:'--请选择--',1:'薪金贷',2:'生意贷',3:'业主贷'}"></s:select> &nbsp;&nbsp; 还款状态： <s:select id="status" list="#{-1:'--请选择--',1:'未偿还',2:'已偿还',3:'偿还中'}"></s:select>
								<input id="search" type="button" value="搜索" name="search" /> <input id="excel" type="button" value="导出Excel" name="excel" /></td>
						</tr>
					</tbody>
				</table>
				<span id="divList"><img src="../images/admin/load.gif" class="load" alt="加载中..." />
				</span>
				<div></div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
<script type="text/javascript" src="../css/admin/popom.js"></script>
<script type="text/javascript" src="../script/jbox/jquery.jBox-2.3.min.js"></script>
<script type="text/javascript">
			$(function(){
				initListInfo(param);
				$("#search").click(function(){
				   param["pageBean.pageNum"] = 1;
				   initListInfo(param);
				});
				
				$("#excel").click(function(){
				  window.location.href=encodeURI(encodeURI("exportlateRepay.do?status=1"+"&&userName="+$("#userName").val()+"&&status="+$("#status").val()+"&&borrowWay="+$("#borrowWay").val()));
				});
			});			
			function initListInfo(praData){
				praData["paramMap.userName"] = $("#userName").val();
				praData["paramMap.borrowWay"] = $("#borrowWay").val();
				praData["paramMap.status"] = $("#status").val();
		 		$.shovePost("overduePaymentList.do",praData,initCallBack);
		 	}
		 	function initCallBack(data){
				$("#divList").html(data);
			}
            var flag=true;
			function repaySubmit(payId,borrowId,elem){
                if(flag){
                    if(confirm("是否确定代偿?")){
                        flag =false;
                        param["paramMap.id"] = payId;
                        param["paramMap.borrowId"] = borrowId;
						$.jBox.tip("<div class='data-submit'>提交数据中，请稍候...</div>","loading");
                        $.shovePost("overduePaymentRepaySubmit.do",param,function(data){
                            if(data == "操作成功"){
                                alert("操作成功!");
                                window.location.href="overduePaymentInit.do";
                                return false;
                            }
                            alert(data);
							$.jBox.closeTip();
                            flag =true;
                        });
                    }
                }else{
                    alert("请勿重复点击");
                }

			}
</script>
</html>