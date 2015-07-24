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
	<form id="editForm" method="post"> 
    <table width="360" border="0" align="center" cellpadding="3">
    
      <tr>
        <td width="120" align="right">
        	<input type="hidden" name="id" id="edit_prod_id" value="${prodDo.id }" />
        	<label for="name">名称：</label>
        </td>
        <td width="240"> <input type="text" name="name" value="${prodDo.name }" /> </td>
      </tr>
      
      <tr>
        <td align="right">
        	<label for="code">产品类型:</label>
        </td>
       	<td> <input type="text"  name="code" value="${prodDo.code }" /> </td>
      </tr>
      
      <tr>
        <td align="right"><label for="minLines">最低额度:</label></td>
        <td><input type="text" name="minLines" value="${prodDo.minLines }" /></td>
      </tr>
      
      <tr>
        <td align="right"><label for="maxLines">最高额度:</label></td>
        <td><input type="text" name="maxLines" value="${prodDo.maxLines }" /></td>
      </tr>
      
      <tr>
        <td align="right"><label for="loanIssue">贷款期限类型:</label></td>
        <td><input type="text" name="loanIssue" value="${prodDo.loanIssue }" /></td>
      </tr>
      
      <tr>
        <td align="right"><label for="publishCode">渠道编码:</label></td>
        <td><input type="text" name="publishCode" value="${prodDo.publishCode }" /></td>
      </tr>
      
      <tr>
        <td align="right"><label for="guarantee">是否需担保:</label></td>
        <td>
        	<select  class="easyui-combobox" name="guarantee" style="width:150px;" panelHeight="50">   
			    <option value="0" ${prodDo.guarantee eq '0' ? 'selected="selected"' : ''}>否</option>
				<option value="1" ${prodDo.guarantee eq '1' ? 'selected="selected"' : ''}>是</option>  
			</select>
        </td>
      </tr>
      
      <tr>
        <td align="right"><label for="mortgage">是否需抵押:</label></td>
        <td>
        	<select  class="easyui-combobox" name="mortgage" style="width:150px;" panelHeight="50">   
			    <option value="0" ${prodDo.mortgage eq '0' ? 'selected="selected"' : ''}>否</option>
				<option value="1" ${prodDo.mortgage eq '1' ? 'selected="selected"' : ''}>是</option>  
			</select>
        </td>
      </tr>
      
      <tr>
        <td align="right"><label for="retaLock">利率锁定:</label></td>
        <td>
        	<select  class="easyui-combobox" name="retaLock" style="width:150px;" panelHeight="50">   
			    <option value="0" ${prodDo.retaLock eq '0' ? 'selected="selected"' : ''}>否</option>
				<option value="1" ${prodDo.retaLock eq '1' ? 'selected="selected"' : ''}>是</option>  
			</select>
        </td>
      </tr>
      
      <tr>
        <td align="right"><label for="status">状态:</label></td>
        <td>
        	<select  class="easyui-combobox" name="status" style="width:150px;" panelHeight="50">   
			    <option value="T"  ${prodDo.status eq 'T' ? 'selected="selected"' : ''}>有效</option>
				<option value="F"  ${prodDo.status eq 'F' ? 'selected="selected"' : ''}>失效</option>  
			</select>
        </td>
      </tr>
      
      <tr>
        <td align="right"><label for="refeCode">推荐奖励:</label></td>
        <td><input type="text" name="refeCode" value="${prodDo.refeCode }" /></td>
      </tr>
      
      <tr>
        <td align="right"><label for="remark">备注:</label></td>
        <td>
        	<textarea rows="3" cols="22" name="remark">${prodDo.remark}</textarea>
        </td>
      </tr>
      
    </table>
    
   </form>
</div>

 <!-- 弹出框按钮 -->
<%-- <div class="windowButton">
     <!-- <a id="addUser_saveAndAdd" class="easyui-linkbutton my-dialog-button" plain="true" icon="icon-ok" href="javascript:void(0)" > 保存并新增</a>  -->
     <a id="edit_save" class="easyui-linkbutton my-dialog-button" plain="true" icon="icon-save" href="javascript:void(0)" > 保存</a> 
     <a id="edit_cancel" class="easyui-linkbutton my-dialog-button" plain="true" icon="icon-cancel" href="javascript:void(0)" >取消</a>
</div>
 --%>	
</body>
</html>