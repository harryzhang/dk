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
	<form id="editFormProv" method="post"> 
    <table width="360" border="0" align="center" cellpadding="3">
    
      <tr>
        <td width="150" align="right">
        	<input type="hidden" name="id" value="${provDo.id }" />
        	<input type="hidden"  name="prodId" type="text" size="30" value="${provDo.prodId}"  />
        	<label for="minAge">最小年龄:</label>
        </td>
        <td width="210"> <input type="text" name="minAge" value="${provDo.minAge}" /> </td>
      </tr>
      
      <tr>
        <td align="right">
        	<label for="maxAge">最大年龄:</label>
        </td>
       	<td> <input type="text"  name="maxAge" value="${provDo.maxAge}" /> </td>
      </tr>
      
      <tr>
        <td align="right"><label for="minIncome">最低收入:</label></td>
        <td><input type="text" name="minIncome" value="${provDo.minIncome}" /></td>
      </tr>
      
      <tr>
        <td align="right"><label for="job">工作岗位:</label></td>
        <td><input type="text" name="job" value="${provDo.job}" /></td>
      </tr>
      
      <tr>
        <td align="right"><label for="minYearWork">最低工作年限:</label></td>
        <td><input type="text" name="minYearWork" value="${provDo.minYearWork}" /></td>
      </tr>
      
      <tr>
        <td align="right"><label for="workAddr">工作地址:</label></td>
        <td><input type="text" name="workAddr" value="${provDo.workAddr}" /></td>
      </tr>
      
       <tr>
        <td align="right"><label for="livAddr">居住地址:</label></td>
        <td><input type="text" name="livAddr" value="${provDo.livAddr}" /></td>
      </tr>
      
      <tr>
        <td align="right"><label for="remark">备注:</label></td>
        <td><input type="text" name="remark" value="${provDo.remark }" /></td>
      </tr>
      
      <tr>
        <td align="right"><label for="ifpunch">是否需抵押:</label></td>
        <td>
        	<select  class="easyui-combobox" name="ifpunch" style="width:150px;" panelHeight="50">   
			    <option value="0" ${provDo.ifpunch eq '0' ? 'selected="selected"' : ''}>否</option>
				<option value="1" ${provDo.ifpunch eq '1' ? 'selected="selected"' : ''}>是</option>  
			</select>
        </td>
      </tr>
      
      <tr>
        <td align="right"><label for="status">状态:</label></td>
        <td>
        	<select  class="easyui-combobox" name="status" style="width:150px;" panelHeight="50">   
			    <option value="T"  ${provDo.status eq 'T' ? 'selected="selected"' : ''}>有效</option>
				<option value="F"  ${provDo.status eq 'F' ? 'selected="selected"' : ''}>失效</option>  
			</select>
        </td>
      </tr>
      
    </table>
    
   </form>
</div>

</body>
</html>