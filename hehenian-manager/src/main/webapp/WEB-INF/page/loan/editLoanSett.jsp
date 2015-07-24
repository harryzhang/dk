<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>编辑</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Cache-Control" content="no-cache" />
<jsp:include page="../common_easyui_cus.jsp"></jsp:include>

</head>
<body>

<!-- 内容 -->
<div >
	<form id="editFormSett" method="post"> 
    <table width="360" border="0" align="center" cellpadding="3">
    
      <tr>
        <td width="150" align="right">
        	<input type="hidden" name="id" value="${settDo.id }" />
        	<input type="hidden"  name="prodId" type="text" size="30" value="${settDo.prodId}"  />
        	<label for="name">方案名称:</label>
        </td>
        <td width="210"> <input type="text" name="name" value="${settDo.name}" /> </td>
      </tr>
      
      <tr>
        <td align="right">
        	<label for="code">方案编码:</label>
        </td>
       	<td> <input type="text"  name="code" value="${settDo.code}" /> </td>
      </tr>
      
      <tr>
        <td align="right"><label for="repayWay">还款方式:</label></td>
        <td><input type="text" name="repayWay" value="${settDo.repayWay}" /></td>
      </tr>
      
      <tr>
        <td align="right"><label for="defaultAnnualRate">默认借款年利率:</label></td>
        <td><input type="text" name="defaultAnnualRate" value="${settDo.defaultAnnualRate}" /></td>
      </tr>
      
      <tr>
        <td align="right"><label for="receiptWay">回款方式:</label></td>
        <td><input type="text" name="receiptWay" value="${settDo.receiptWay}" /></td>
      </tr>
      
      <tr>
        <td align="right"><label for="aheadSettlePeriod">提前结清顺延期限:</label></td>
        <td><input type="text" name="aheadSettlePeriod" value="${settDo.aheadSettlePeriod}" /></td>
      </tr>
      
      <tr>
        <td align="right"><label for="status">状态:</label></td>
        <td>
        	<select  class="easyui-combobox" name="status" style="width:150px;" panelHeight="73">   
			    <option value="E"  ${settDo.status eq 'E' ? 'selected="selected"' : ''}>启用</option>
				<option value="D"  ${settDo.status eq 'D' ? 'selected="selected"' : ''}>禁用</option> 
				<option value="P"  ${settDo.status eq 'P' ? 'selected="selected"' : ''}>发布</option> 
				<!-- 方案状态(E:启用/D:禁用/P:发布) -->
			</select>
        </td>
      </tr>
      
    </table>
    
   </form>
</div>

</body>
</html>