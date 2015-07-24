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
	<form id="editFormCommunity" method="post"> 
    <table width="360" border="0" align="center" cellpadding="3">

      <tr>
        <td width="150" align="right">
        	<input type="hidden" name="id" value="${loanCmobile.id }" />
        	<label for="cname">事业部:</label>
        </td>
        <td width="210">
        	<input id="cname" class="easyui-combobox"   name="cname" value="${loanCmobile.cname}"
				data-options="valueField: 'cname',textField: 'cname', url: '/community/allCommuntiy.html' ,editable : false" />
 		</td>
      </tr>
      
      <tr>
        <td align="right">
        	<label for="cuserId">彩之云员工ID:</label>
        </td>
       	<td> <input type="text"  name="cuserId" value="${loanCmobile.cuserId}" /> </td>
      </tr>
      
      <tr>
        <td align="right"><label for="cusername">姓名:</label></td>
        <td><input type="text" name="cusername" value="${loanCmobile.cusername}" /></td>
      </tr>
      
      <tr>
        <td align="right"><label for="cmobile">手机号码:</label></td>
        <td><input type="text" name="cmobile" value="${loanCmobile.cmobile}" /></td>
      </tr>
      
       
      
      <%--
      <tr>
        <td align="right"><label for="loanType">还款方式:</label></td>
        <td>
        	<select  class="easyui-combobox" name="loanType" style="width:150px;">   
			    <option value="1"  ${loanCmobile.loanType eq '1' ? 'selected="selected"' : ''}>信用贷</option>
				<option value="2"  ${loanCmobile.loanType eq '2' ? 'selected="selected"' : ''}>抵押贷</option> 
				<option value="3"  ${loanCmobile.loanType eq '3' ? 'selected="selected"' : ''}>担保贷</option> 
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