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
	<form id="editFormSyn" method="post"> 
    <table width="360" border="0" align="center" cellpadding="3">
      <tr>
        <td width="120" align="right">
        	<label for="selectDate">同步日期:</label>
        </td>
        <td width="240"> 
			<input type="text" id="id_synDate_edit"  name="selectDate" class="easyui-datebox easyui-validatebox"
					size="14"  data-options="editable:false,required:true" />
						  
		 </td>
      </tr>
    
    </table>
    
   </form>
</div>

</body>
</html>