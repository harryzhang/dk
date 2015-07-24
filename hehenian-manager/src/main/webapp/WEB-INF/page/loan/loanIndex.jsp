<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>订单</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Cache-Control" content="no-cache" />
<jsp:include page="../common_easyui_cus.jsp"></jsp:include>
<link rel="stylesheet" type="text/css" href="${basePath }/css/icons.css" />
<script type="text/javascript" src="${basePath}/js/common/js/jquery-easyui-1.4.1/extension/jquery-easyui-datagridview/datagrid-detailview.js"></script>
<script type="text/javascript" src="${basePath}/js/loan/loanIndex.js?v=${jsversion}"></script>

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
			          <td class="tdfont">查询条件:
			          	<input type="text" size="14" id="searchStr" name="searchStr" placeholder="订单号/借款人/手机号码" ></td>
			          <td class="tdfont">&nbsp;产品类型: 
			          	<input class="easyui-combobox" name="productCode" size="13" id="search_productCode" /></td>
			          <td class="tdfont">&nbsp;贷款类型: 
			         	 <input class="easyui-combobox"  id="search_loanType" size="7"  data-options="panelHeight:'90',
							 valueField: 'value', textField: 'text', data: [{ text: '请选择...', value: '' },
							 { text: '信用贷款', value: '1' },{ text: '抵押贷款', value: '2' },{ text: '担保贷款', value: '3' }]" /></td>
			          <td class="tdfont">&nbsp;订单状态:
						<input class="easyui-combobox" name="loanStatus" size="7"  id="search_loanStatus" /></td>
			        </tr>
			        <tr>
				          <td class="tdfont" colspan="2">申请日期:
							<input type="text" id="search_startDate"  name="startDate" class="easyui-datebox"
									size="14"
									 data-options="editable : false" />-<input type="text" id="search_endDate" 
									 name="endDate" class="easyui-datebox" size="14" data-options="editable : false" />
						  </td>
	 		  				<td class="tdfont">&nbsp;下一节点: 
				         	 <input class="easyui-combobox"  id="search_processNextStep" size="16"  data-options="panelHeight:'90',
								 valueField: 'value', textField: 'text', data: [{ text: '请选择...', value: '' },
								  { text: '验证业主', value: 'CALL_COLOR_HOUSE_CHECK' },
								 { text: '彩管家审核', value: 'PROXY_CHECK' },
								 { text: '征信补入', value: 'INPUT_CREDIT_REPORT' },
								 { text: '小贷审批', value: 'XIAODAI_AUDIT' },
								 { text: '待上标', value: 'TO_SUBJECT' },
								 { text: '用户提交', value: 'TO_EDIT' },
								 { text: '一审', value: 'MENDSTEP1' },
								 { text: '二审', value: 'MENDSTEP2' }
								 ]" /></td>
				          <td >
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
   
  <div id="detailLoanDiv"></div> 
  
  <div id="oneCheckedDiv"></div>
  
  <div id="twoCheckedDiv"></div>
  
  <div id="sysLoanData"></div>
</body>

</html>