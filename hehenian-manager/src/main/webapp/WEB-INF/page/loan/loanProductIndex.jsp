<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>产品</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Cache-Control" content="no-cache" />
<jsp:include page="../common_easyui_cus.jsp"></jsp:include>
<script type="text/javascript" src="${basePath}/js/loan/loanProdIndex.js?v=${jsversion}"></script>

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
			          <td class="tdfont">产品名称:
			          		<input type="text"  size="14" id="search_name" ></td>
			          <td class="tdfont">&nbsp;产品编码:
							<input type="text" size="14" id="search_code" ></td>
			          <td class="tdfont">&nbsp;是否需担保: 
			          		<input class="easyui-combobox"  id="search_guarantee" size="7" data-options="panelHeight:'73',
							 valueField: 'value', textField: 'text', data: [{ text: '请选择...', value: '' },
							 { text: '是', value: '1' },{ text: '否', value: '0' }]" /></td>
			          <td class="tdfont">&nbsp;是否需抵押: 
			                <input class="easyui-combobox"  id="search_mortgage" size="7" data-options="panelHeight:'73',
							 valueField: 'value', textField: 'text', data: [{ text: '请选择...', value: '' },
							 { text: '是', value: '1' },{ text: '否', value: '0' }]"/></td>
			        </tr>
			        <tr>
			           <td class="tdfont">利率锁定: 
			                <input class="easyui-combobox"  id="search_retaLock" size="7" data-options="panelHeight:'73',
							 valueField: 'value', textField: 'text', data: [{ text: '请选择...', value: '' },
							 { text: '是', value: '1' },{ text: '否', value: '0' }]"/></td>
			          <td class="tdfont">&nbsp;产品状态: 
			                <input class="easyui-combobox"  id="search_status" size="7"  data-options="panelHeight:'73',
							 valueField: 'value', textField: 'text', data: [{ text: '请选择...', value: '' },
							 { text: '有效', value: 'T' },{ text: '失效', value: 'F' }]"/></td>
			          <td colspan="2">
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
  	<div id="tt" ></div>
   
</div>
		<!-- 用于产品新增修改 -->
   <div id="editDiv"></div>
   <!-- 用于新增修改产品前提 -->
   <div id="editProv"></div>
   <!-- 新增方案 -->
   <div id="editSett"></div>
   <!-- 产品明细 -->
   <div id="prodDetail"></div>

</body>
</html>