<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>评分项配置</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Cache-Control" content="no-cache" />
<jsp:include page="../common_easyui_cus.jsp"></jsp:include>
<script type="text/javascript" src="${basePath}/js/common/js/jquery-easyui-1.4.1/extension/jquery-easyui-datagridview/datagrid-detailview.js"></script>
<script type="text/javascript" src="${basePath}/js/loan/loanCheckItemIndex.js"></script>

<style type="text/css">
.tdfont{
	font-size: 12px;
}
</style>
</head>
<body class="easyui-layout">

<div id="body" region="center" > 
  	<!-- 查询条件区域 -->
	<div id="search_area"  class="easyui-panel"  >
		<div id="conditon" >
			<form id="searchForm" style="margin-top:7px;margin-left:5px;">
			      <table border="0">
			        <tr>
			          <td class="tdfont">名称: 
			          		<input type="text"  size="13" id="search_checkItemName" /></td>
			          <td class="tdfont">&nbsp;状态: 
			          		<input class="easyui-combobox"  id="search_checkItemStatus" size="7"  data-options="panelHeight:'73',
							 valueField: 'value', textField: 'text', data: [{ text: '请选择...', value: '' },
							 { text: '有效', value: 'T' },{ text: '失效', value: 'F' }]"/></td>
			          <td>
			              <a  href="javascript:void(0)" id="searchButton" class="easyui-linkbutton" iconCls="icon-search" plain="true">查询</a> 
			              <a  href="javascript:void(0)" id="resetButton" class="easyui-linkbutton" iconCls="icon-reset" plain="true" >重置</a>
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
	
	<div id="addCheckDetail"></div>
</body>
</html>