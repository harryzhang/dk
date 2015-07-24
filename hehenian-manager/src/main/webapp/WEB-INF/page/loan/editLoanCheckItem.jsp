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
	<form id="editFormItem" method="post"> 
    <table width="360" border="0" align="center" cellpadding="3">
      <tr>
        <td width="120" align="right">
        	<input type="hidden" name="checkId" value="${loanCheckItemDo.checkId }" />
        	<label for="checkItemName">评分项名称:</label>
        </td>
        <td width="240"> <input type="text" class="easyui-validatebox" 
        data-options="required:true,validType:'length[1,30]'" name="checkItemName" value="${loanCheckItemDo.checkItemName }" /> </td>
      </tr>
      
      <tr>
        <td align="right"> <label for="checkItemCode">评分项编码:</label> </td>
       	<td> <input type="text"  class="easyui-validatebox"  data-options="validType:'length[0,30]'"  name="checkItemCode" value="${loanCheckItemDo.checkItemCode }" /> </td>
      </tr>
      
      <tr>
        <td align="right"><label for="checkType">评分类别:</label></td>
        <td><input type="text"  class="easyui-validatebox"  data-options="validType:'length[0,15]'" name="checkType" value="${loanCheckItemDo.checkType }" /></td>
      </tr>

      <tr>
        <td align="right"><label for="status">状态:</label></td>
        <td>
        	<select  class="easyui-combobox" name="status" style="width:150px;"  panelHeight="50">   
			    <option value="T"  ${loanCheckItemDo.status eq 'T' ? 'selected="selected"' : ''}>有效</option>
				<option value="F"  ${loanCheckItemDo.status eq 'F' ? 'selected="selected"' : ''}>失效</option>  
			</select>
        </td>
      </tr>
      
      <tr>
        <td align="right"><label for="version">版本号:</label></td>
        <td><input type="text" class="easyui-numberbox" name="version" value="${loanCheckItemDo.version }" /></td>
      </tr>
      
      <tr>
        <td align="right"><label for="remark">备注:</label></td>
        <td>
        	<textarea rows="4" cols="22" class="easyui-validatebox"  data-options="validType:'length[0,50]'" name="remark">${loanCheckItemDo.remark}</textarea>
        </td>
      </tr>
    
    </table>
    
   </form>
</div>
<%-- <script type="text/javascript" src="${basePath }/js/jquery-easyui/plugins/jquery.validatebox.js"></script>
 --%>

</body>
</html>