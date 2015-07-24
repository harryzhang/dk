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
	<form id="editFormLoan" method="post"> 
    <table width="360" border="0" align="center" cellpadding="3">

      <tr>
        <td width="150" align="right">
        	<input type="hidden" name="loanId" value="${loanDo.loanId }" />
        	<label for="applyAmount">申请金额:</label>
        </td>
        <td width="210"> <input type="text" class="easyui-numberbox" 
 						id="id_applyAmount_edit"  name="applyAmount" value="${loanDo.applyAmount}" /> </td>
      </tr>
      
      <tr>
        <td align="right">
        	<label for="annualRate">借款年利率(%):</label>
        </td>
       	<td> <input type="text"  name="annualRate" value="${loanDo.annualRate}" /> </td>
      </tr>
      
      <tr>
        <td align="right"><label for="loanPeriod">借款期限:</label></td>
        <td><input type="text" name="loanPeriod" value="${loanDo.loanPeriod}" /></td>
      </tr>
      
      <tr>
        <td align="right"><label for="loanUsage">贷款用途:</label></td>
        <td><input type="text" name="loanUsage" value="${loanDo.loanUsage}" /></td>
      </tr>
      
       <tr>
        <td align="right"><label for="loanTitle">借款标题:</label></td>
        <td><input type="text" name="loanTitle" value="${loanDo.loanTitle}" /></td>
      </tr>
      
      <tr>
        <td align="right"><label for="schemeId">还款方式:</label></td>
        <td>
			<input id="cc1" class="easyui-combobox"  name="schemeId" value="${loanDo.schemeId}"
				data-options="valueField: 'id',textField: 'text', url: '/loan/sett/all.html?id=${loanDo.schemeId}&productCode=${loanDo.productCode}' ,editable : false" />
        </td>
      </tr>
      
      <tr>
        <td align="right"><label for="channelId">渠道:</label></td>
        <td>
			<input id="channelId" class="easyui-combobox"  name="channelId" value="${loanDo.channelId}"
				data-options="valueField: 'id',textField: 'channel_name', url: '/loan/combox/channelType.html' ,editable : false" />
        </td>
      </tr>
      
      <tr>
        <td align="right"><label for="loanStatus">状态:</label></td>
        <td>
			<select  class="easyui-combobox" name="loanStatus" style="width:150px;">   
			    <option value="PENDING"  ${loanDo.loanStatus eq 'PENDING' ? 'selected="selected"' : ''}>待处理</option>
				<option value="INVALID"  ${loanDo.loanStatus eq 'INVALID' ? 'selected="selected"' : ''}>失效</option> 
				<option value="DRAFT"  ${loanDo.loanStatus eq 'DRAFT' ? 'selected="selected"' : ''}>草稿</option>
				<option value="NOPASS"  ${loanDo.loanStatus eq 'NOPASS' ? 'selected="selected"' : ''}>拒绝</option>
		
			</select>
			
        </td>
      </tr>
      
      <%--
      <tr>
        <td align="right"><label for="loanType">还款方式:</label></td>
        <td>
        	<select  class="easyui-combobox" name="loanType" style="width:150px;">   
			    <option value="1"  ${loanDo.loanType eq '1' ? 'selected="selected"' : ''}>信用贷</option>
				<option value="2"  ${loanDo.loanType eq '2' ? 'selected="selected"' : ''}>抵押贷</option> 
				<option value="3"  ${loanDo.loanType eq '3' ? 'selected="selected"' : ''}>担保贷</option> 
				<!-- 1:信用贷 2：抵押贷 3：担保贷 -->
			</select>
        </td>
      </tr>
       --%>
    </table>
    
   </form>
</div>
</body>

</html>