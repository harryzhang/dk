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
	<form id="editFormFee" method="post"> 
    <table width="360" border="0" align="center" cellpadding="3">

      <tr>
        <td width="150" align="right">
        	<input type="hidden" name="id" value="${feeDo.id }" />
        	<input type="hidden"  name="schemeId" type="text" size="30" value="${feeDo.schemeId}"  />
        	<label for="name">规则名称:</label>
        </td>
        <td width="210"> <input type="text" name="name" value="${feeDo.name}" /> </td>
      </tr>
      
      <tr>
        <td align="right">
        	<label for="type">规则类型:</label>
        </td>
       	<td> <input type="text"  name="type" value="${feeDo.type}" /> </td>
      </tr>
      
      <tr>
        <td align="right"><label for="gatherWay">收取方式:</label></td>
        <td><input type="text" name="gatherWay" value="${feeDo.gatherWay}" /></td>
      </tr>
      
      <tr>
        <td align="right"><label for="isInclude">是否包含在借款利率里:</label></td>
        <td>
        	<select  class="easyui-combobox" name="isInclude" style="width:150px;"  panelHeight="50">   
			    <option value="T"  ${feeDo.isInclude eq 'T' ? 'selected="selected"' : ''}>包含</option>
				<option value="F"  ${feeDo.isInclude eq 'F' ? 'selected="selected"' : ''}>不包含</option> 
				<!-- 方案状态(E:启用/D:禁用/P:发布) -->
			</select>
        </td>
      </tr>
      
      <tr>
        <td align="right"><label for="baseAmountType">乘数:</label></td>
        <td>
        	<select  class="easyui-combobox" name="baseAmountType" style="width:150px;" panelHeight="73">   
        		<option value=""  ${feeDo.baseAmountType eq '' ? 'selected="selected"' : ''}>请选择...</option>
			    <option value="1"  ${feeDo.baseAmountType eq '1' ? 'selected="selected"' : ''}>借款金额</option>
				<option value="2"  ${feeDo.baseAmountType eq '2' ? 'selected="selected"' : ''}>剩余本金</option> 
			</select>
        </td>
      </tr>
      
      <tr>
        <td align="right"><label for="gatherRate">收取比率:</label></td>
        <td><input type="text" name="gatherRate" value="${feeDo.gatherRate}" /></td>
      </tr>
      
      <tr>
        <td align="right"><label for="feeAmount">费用金额:</label></td>
        <td><input type="text" name="feeAmount" value="${feeDo.feeAmount}" /></td>
      </tr>
      
      <tr>
        <td align="right"><label for="isInitRepayPlanUse">是否在生成还款计划时用:</label></td>
        <td>
        	<select  class="easyui-combobox" name="isInitRepayPlanUse" style="width:150px;" panelHeight="50">   
			    <option value="T"  ${feeDo.isInitRepayPlanUse eq 'T' ? 'selected="selected"' : ''}>是</option>
				<option value="F"  ${feeDo.isInitRepayPlanUse eq 'F' ? 'selected="selected"' : ''}>否</option> 
			</select>
        </td>
      </tr>
      
    </table>
    
   </form>
</div>

</body>
</html>