<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>添加问题内容</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Cache-Control" content="no-cache" />
<jsp:include page="../common.jsp"></jsp:include>
<style type="text/css">
	.modules{
		padding-bottom: 10px;
		padding-top: 10px;
	}
	.minMaxChoice{
		padding-bottom: 20px;
		padding-top: 10px;
	}
	.answerContent{width:90px;}
	.answerOrders{width:60px;}
	.btn-submit{padding:5px 15px;cursor: pointer;}
	#tab{font-size:14px;}
	#tab thead td{font-weight:700;}
	select.parentModules,select.childModules{width:150px;}
</style>
</head>

<body>
<form class="submitForm" action="/question/saveQuestion.html" method="post">
	<p style="color:red">注意：只有程度题、选择题、是否题在修改的时候能够直接同步前端，其他区间题、文字输入题需要js连调</p>
	<div>
	题目类型：<select class="questionsTypes" name="questionType">
				<option value="-1">请选择</option>
				<option value="1">选择题</option>
				<option value="2">文字输入题</option>
				<option value="3">程度题[符合-不符合]</option>
				<option value="4">程度题[重要-不重要]</option>
				<option value="5">是否题[是-否]</option>
				<option value="6">区间题</option>
			</select></br></br>
	</div>
	题目内容：<input type="text" value="" style="width:520px" name="content"/><br /><br />
	题目属性：<input type="radio" name="sexStatus" value="-1" checked="checked">男女通用&nbsp;&nbsp;<input type="radio" name="sexStatus" value="0">男性用户&nbsp;&nbsp;<input type="radio" name="sexStatus" value="1">女性用户
	手机端展示类型：<select name="mShowType">
		<option value="0">请选择</option>
		<option value="1">小型输入框</option>
		<option value="2">大型输入框</option>
		<option value="3">是否题</option>
		<option value="4">程度题-6</option>
		<option value="5">程度题-7</option>
		<option value="6">不以区间间隔</option>
		<option value="7">以区间间隔</option>
		<option value="8">页面展示类型(日期)</option>
		<option value="9">双列联动</option>
		<option value="10">双列不联动 以区间间隔</option>
		<option value="11">双列不联动 不以区间间隔</option>
		<option value="12">单选题 </option>
		<option value="13">多选题 </option>
		<option value="14">上传照片</option>
	</select>
	<div class="modules">问题选项：<a class="addAnswer" href="javascript:;">添加答案</a></div>
	<table id="tab">
	<thead>
		<tr>
			<td width="100">选项内容</td>
			<td width="30">顺序</td>
			<td width="200">选项属性</td>
			<td width="100">状态</td>
			<td >不回答的问题[例：1,3]</td>
		</tr>
	</thead>
	<tbody id="tableBody">
    </tbody>
    </table></br></br>
    <div class="modules">所属模块：<a class="addModules" href="javascript:;">添加模块</a></div>

    保密功能：<input type="radio" name="isHide" value="1" />启用&nbsp;&nbsp;
    <input type="radio" name="isHide" value="0"  checked="checked"/>关闭</br></br>
    
    启用状态：<input type="radio" name="inUse" value="1"  checked="checked"/>启用&nbsp;&nbsp;
    <input type="radio" name="inUse" value="0"/>关闭</br></br>
    
    投放端口：<input type="radio" name="platform" value="-1"  checked="checked"/>通用&nbsp;&nbsp;
    <input type="radio" name="platform" value="0"/>PC端
    <input type="radio" name="platform" value="1"/>手机端&nbsp;&nbsp;</br></br>
    <input type="button" id="button" class="btn-submit" value="保存数据">
</form>
 <script type="text/javascript" src="${basePath}/js/question/newAddQuestion.js"></script>
</body>
</html>
  