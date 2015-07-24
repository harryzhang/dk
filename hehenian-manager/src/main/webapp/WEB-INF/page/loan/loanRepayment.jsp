<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>订单</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Cache-Control" content="no-cache" />
<jsp:include page="../common_easyui_cus.jsp"></jsp:include>
<link rel="stylesheet" type="text/css" href="${basePath }/css/icons.css" />
<script type="text/javascript" src="${basePath}/js/common/js/jquery-easyui-1.4.1/extension/jquery-easyui-datagridview/datagrid-detailview.js?v=${jsversion}"></script>
<script type="text/javascript" src="${basePath}/js/loan/loanRepayment.js?v=${jsversion}"></script>

<style type="text/css">
.tdfont{
	font-size: 12px;
}
</style>
</head>
<body class="easyui-layout">

<div id="body" region="center" > 
  <!-- 查询条件区域 -->
	<div id="search_area"  class="easyui-panel" >
		<div id="conditon" >
			<form id="searchForm" style="margin-top:7px;margin-left:5px;" >
			      <table border="0">
			        <tr>
						<td >
							<input type="button" id="loanAll" value="全部订单">   
						</td>
						<td >
							<input type="button" id="loanToday" value="今日应还">   
						</td> 
						<td >
							<input type="button" id="loanToday3" value="未来3天应还">   
						</td> 
						<td >
							<input type="button" id="loanAdd3" value="逾期3天内">   
						</td> 
						<td >
							<input type="button" id="loanAdd" value="逾期3天以上">   
						</td> 
			        </tr>
			      </table>
		     </form>
	     </div>
    	<span id="openOrClose"></span> 
	</div>
  
  	<!-- 数据表格区域 -->
  <div id="tt"></div>
  
</div>

  <div id="editDiv"></div>
   
  <div id="detailLoanDiv"></div> 
  
  <div id="oneCheckedDiv"></div>
  
  <div id="twoCheckedDiv"></div>
  
  <div id="sysLoanData"></div>
</body>

</html>