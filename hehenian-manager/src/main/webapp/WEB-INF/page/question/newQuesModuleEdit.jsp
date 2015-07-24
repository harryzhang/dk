<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>修改问题模块</title>
    <link rel="stylesheet" type="text/css" href="/js/jquery-easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="/js/jquery-easyui/themes/icon.css">
	<script type="text/javascript" src="/js/jquery-easyui/jquery-1.8.0.min.js"></script>
	<script type="text/javascript" src="/js/jquery-easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="/js/jquery-easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
<form id="frm" class="submitEdit"  method="GET">
	<input name="questionnaireId" value="${result.questionnaireId}" type="hidden">
	<input name="parentId" value="${result.parentId}" type="hidden">
	<div class="tabelMod">
		<p style="color:red;font-size:14px">注意：由启用改停用，会删除该模块下的所有问题关联，但是不会删除问题数据</p>
		<label>问题模块:</label>
		<input name="parentContent" type="text" value="${parentContent}" onfocus=this.blur()>
	</div>
	<div class="tabelMod">
	<label>子模块:</label>
		<input name="content" type="text" value="${result.content}">
	</div>
	<div class="tabelMod">
		<label>平台:</label>
		<select name="platform" id="platform">
			<option value="-1" <c:if test="${result.platform == -1 }"><c:out value="selected='selected'"/></c:if>>全部</option>
  			<option value="0" <c:if test="${result.platform == 0 }"><c:out value="selected='selected'"/></c:if>>PC端</option>
  			<option value="1" <c:if test="${result.platform == 1 }"><c:out value="selected='selected'"/></c:if>>手机端</option>
   		</select>
	</div>
	<div class="tabelMod">
		<label>是否启用:</label>
		<select name="inUse" id="inUse">
			<option value="1" <c:if test="${result.inUse == 1 }"><c:out value="selected='selected'"/></c:if>>启用</option>
  			<option value="0" <c:if test="${result.inUse == 0 }"><c:out value="selected='selected'"/></c:if>>停用</option>
    	</select>
	</div>
	<br/>

	<input class="submit" type="button" value="提交保存"/>
	<span id="prompt" style="color:red;"></span>
</form>
</body>
<script type="text/javascript"> 
$(function(){
	$(".submit").live("click",function(){
		$.ajax({
			url : "/question/saveQuesModuleEdit.html?" + $('#frm').serialize(), 
			type : 'get',
			success : function(data){
				if(data && data=="1"){
					//关闭窗口
					$("#prompt").html("保存成功");
				} else if(data && data=="117"){
					$("#prompt").html("数据已存在");
				} else {
					$("#prompt").html("数据传输异常");
				}
			}
		});
	});
});
</script>
</html>