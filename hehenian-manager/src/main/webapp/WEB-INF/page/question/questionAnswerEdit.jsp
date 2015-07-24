<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>选择内容</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Cache-Control" content="no-cache" />
<jsp:include page="../common.jsp"></jsp:include>
</head>
<body>

	<input type="button" value="新建" id="new" onclick="funtion:create();"/>
	<form id="frm" action="/question/saveAnswerEdit.html" method="post">
	<input type="hidden" name ="id" value ="${id}" >
	<table id="tab">
	<thead><tr><td>ID</td><td>选项内容</td><td>选项顺序</td><td>操作</td></tr></thead>
	<tbody>
	<c:forEach var="var" items="${ data}">
	<tr class="row" id="row_${var.id }">
		<td>${var.id }</td>
		<td><input name="${var.id }_options" type="text" id="${var.id }_options" class="options" value="${var.options }" onchange="funtion:changeData();"/></td>
		<td><input name="${var.id }_orders" type="text" id="${var.id }_orders" class="orders" value="${var.orders }" onchange="funtion:changeData();"/></td>
		<td><a href="#" onclick="funtion:delData(${var.id });">删除</a></td>
    </tr>
    </c:forEach>
    </tbody>
    </table>
    <input type="button" value="提交" onclick="funtion:subSave();">
	</form>
</body>
<script type="text/javascript">
 var id = 0;
 var changeNum = 0;
 	function create(){
 		id = id+1;
 		var ro = '<tr class="row" id = '+id+'>'+
		'<td></td>'+
		'<td><input name="new_options_'+id+'" type="text" class="options added" id="rid"  /></td>'+
		'<td><input name="new_orders_'+id+'" type="text" class="orders added" id="rid" /></td>'+
		'<td><a href="#" onclick="funtion:delRow('+id+');">删除</a></td>'+
    '</tr>';
    $("#tab").append(ro);
    
// 		$("tr:eq(1)").clone().appendTo("#tab");   //在表格后面添加一行        
 	}
 	function delRow(btn){
 		$("#"+btn).remove();
 	}
 	
 	function subSave(){
 		var optionList = $('.options');
 		var orderList = $('.orders');
 		var dataArray = [];
 		if(changeNum == 0 && $('.added').length == 0){
 			return;
 		}
 		for(var i =0 ; i<optionList.length ;i++){
 			if(optionList[i].value.trim() == ''){
 				alert('选项内容 不能为空');
 				return;
 			}
 			dataArray.push(optionList[i].value.trim())
 		}
 		$.unique(dataArray);
 		if(optionList.length != dataArray.length){
 			alert("选项内容 数据重复");
 			return;
 		}
 		dataArray=[];
 		for(var i =0 ; i<orderList.length ;i++){
 			if(orderList[i].value.trim() == ''){
 				alert('选项内容 不能为空');
 				return;
 			}
 			dataArray.push(orderList[i].value.trim())
 		}
 		$.unique(dataArray);
 		if(orderList.length != dataArray.length){
 			alert("选项顺序 数据重复");
 			return;
 		}
 		$('#frm').submit();
 		
 	}
 	
 	function changeData(){
 		changeNum = changeNum+1;
 	}
 	
 	function delData(id){
 		$.ajax({
			type: "post",
			url: "/question/delQuestionAnswer.html?id="+id,
			success: function(data, textStatus){
 				$("#row_"+id).remove();
			},
			complete: function(XMLHttpRequest, textStatus){
			//HideLoading();
			},
			error: function(){
			//请求出错处理
			}
			});
 	}
 	
//$(function(){
//	$('input').	
//});
 	
 </script>
</html>
  