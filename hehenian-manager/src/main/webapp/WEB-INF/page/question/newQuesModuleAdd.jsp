<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>新增问题模块</title>
    <link rel="stylesheet" type="text/css" href="/js/jquery-easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="/js/jquery-easyui/themes/icon.css">
	<script type="text/javascript" src="/js/jquery-easyui/jquery-1.8.0.min.js"></script>
	<script type="text/javascript" src="/js/jquery-easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="/js/jquery-easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
<form id="frm" class="submitAdd"  method="get">
	<p style="color:red">注意：新增一个模块需要有需求评审，否则请不要随意新增子模块</p>
	<div class="tabelMod">
		<label>问题模块:</label>
		<select name="parentId" id="quesModuleBox">
			<c:forEach items="${modulesView.modules}" var="modules">
				<option value="${modules.questionnaireId }">${modules.content}</option>
			</c:forEach>	
		</select>
	</div>
	
	<div class="tabelMod">
		<label>子模块:</label>
		<input type="text" name="content"/>
	</div>
	<div class="tabelMod">
		<label>平台:</label>
		<select name="platform" id="platformBox">
			 <option value="-1">全部</option>
			 <option value="0">	PC端</option>
			 <option value="1">手机端</option>
		</select>
	</div>
	<div class="tabelMod">
		<label>是否启用:</label>
		<input type="radio" value="1" name="inUse" checked="checked">是
		<input type="radio" value="0" name="inUse">否 
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
			url : "/question/saveQuesModuleAdd.html?" + $('#frm').serialize(), 
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