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
	<form id="editHousePriceForm" method="post"> 
    <table width="360" border="0" align="center" cellpadding="3">

      <tr>
        <td width="150" align="right">
        	<input type="hidden" name="id" value="${areaObj.id }" />
        	<label for="cid">小区ID:</label>
        </td>
        <td width="210"> 
        	<input type="text" id="cid" name="cid" value="${areaObj.cid}"  onkeypress="return IsNum(event)" <c:if test="${areaObj.id>0}">readonly="readonly"</c:if>  />
        </td>
      </tr>
      
      <tr>
        <td align="right">
        	<label for="cname">小区名称:</label>
        </td>
       	<td> 
       		<input type="text"  name="cname" value="${areaObj.cname}" <c:if test="${areaObj.id>0}">readonly="readonly"</c:if>  /> 
       	</td>
      </tr>
      
      <tr>
        <td align="right"><label for="housePrice">房价:</label></td>
        <td><input type="text" name="housePrice" value="${areaObj.housePrice}"   onkeypress="return IsNum(event)"/></td>
      </tr>
    </table>
   </form>
</div>
</body>
</html>