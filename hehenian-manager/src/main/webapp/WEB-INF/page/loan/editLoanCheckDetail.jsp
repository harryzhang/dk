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
	<form id="editFormDetail" method="post"> 
    <table width="360" border="0" align="center" cellpadding="3">
      <tr>
        <td width="120" align="right">
        	<input type="hidden" name="itemCheckId" value="${loanCheckDetailDo.itemCheckId }" />
        	<input type="hidden" name="checkId" value="${loanCheckDetailDo.checkId }" />
        	<label for="valType">取值方式:</label>
        </td>
        <td width="240"> 
			<select  class="easyui-combobox" name="valType" style="width:150px;" panelHeight="50">   
			    <option value="1"  ${loanCheckDetailDo.valType eq '1' ? 'selected="selected"' : ''}>固定值</option>
				<option value="2"  ${loanCheckDetailDo.valType eq '2' ? 'selected="selected"' : ''}>范围值</option>  
			</select>
		 </td>
      </tr>
      
      <tr>
        <td align="right"> <label for="checkVal">分值:</label> </td>
       	<td> <input type="text" class="easyui-numberbox" max="99999999999" name="checkVal" value="${loanCheckDetailDo.checkVal }" /> </td>
      </tr>
      
      <tr>
        <td align="right"><label for="startItemVal">条目起始值:</label></td>
        <td><input type="text" class="easyui-numberbox" precision="2" name="startItemVal" value="${loanCheckDetailDo.startItemVal }" /></td>
      </tr>
      
      <tr>
        <td align="right"><label for="endItemVal">条目截止值:</label></td>
        <td><input type="text" class="easyui-numberbox" precision="2"  name="endItemVal" value="${loanCheckDetailDo.endItemVal }" /></td>
      </tr>
     
      <tr>
        <td align="right"><label for="fixItemVal">固定值:</label></td>
        <td><input type="text" class="easyui-numberbox" precision="2" name="fixItemVal" value="${loanCheckDetailDo.fixItemVal }" /></td>
      </tr>
      
      <tr>
        <td align="right"><label for="coefficient">系数:</label></td>
        <td><input type="text" class="easyui-numberbox" precision="2" name="coefficient" value="${loanCheckDetailDo.coefficient }" /></td>
      </tr>
      
      <tr>
        <td align="right"><label for="remark">备注:</label></td>
        <td>
        	<textarea rows="4" cols="22" class="easyui-validatebox"  data-options="validType:'length[0,50]'" name="remark">${loanCheckDetailDo.remark}</textarea>
        </td>
      </tr>
    
    </table>
    
   </form>
</div>

</body>
</html>