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
<form class="submitForm" action="/question/editQuestion.html" method="post">
	<p style="color:red">注意：只有程度题、选择题、是否题在修改的时候能够直接同步前端，其他区间题、文字输入题需要js连调</p>
	<input type="hidden" name="questionId" value="${quesetionData.questionId}">
	<div>
	题目类型：<select class="questionsTypes" name="questionType">
				<option value="-1" ${quesetionData.questionType == -1 || quesetionData.questionType == null ? 'selected="selected"' : '' }>请选择</option>
				<option value="1" ${quesetionData.questionType == 1 ? 'selected="selected"' : '' }>选择题</option>
				<option value="2" ${quesetionData.questionType == 2 ? 'selected="selected"' : '' }>文字输入题</option>
				<option value="3" ${quesetionData.questionType == 3 ? 'selected="selected"' : '' }>程度题[符合-不符合]</option>
				<option value="4" ${quesetionData.questionType == 4 ? 'selected="selected"' : '' }>程度题[重要-不重要]</option>
				<option value="5" ${quesetionData.questionType == 5 ? 'selected="selected"' : '' }>是否题[是-否]</option>
				<option value="5" ${quesetionData.questionType == 6 ? 'selected="selected"' : '' }>区间题<option>
				
			</select></br></br>
	</div>
	<c:if test="${quesetionData.questionType != null && quesetionData.questionType == 1}">
		<div class="minMaxChoice">
		答案下限：&nbsp;<a class="minusMinChoice" style="cursor:hand">减小</a>&nbsp;&nbsp;<input type="text" value="${quesetionData.minChoice}" id="minChoice" name="minChoice"/><a class=""/>&nbsp;&nbsp;<a class=""/><a class="addMinChoice" style="cursor:hand">增加</a></br>
		答案上限：&nbsp;<a class="minusMaxChoice" style="cursor:hand">减小</a>&nbsp;&nbsp;<input type="text" value="${quesetionData.maxChoice}" id="maxChoice" name="maxChoice"/><a class=""/>&nbsp;&nbsp;<a class=""/><a class="addMaxChoice" style="cursor:hand">增加</a>
		</div>
	</c:if>
	题目内容：<input style="width:500px" type = "text" value = "${quesetionData.content }" name="content"/></br></br>
	题目属性：<input type="radio" name="sexStatus" value="-1" ${quesetionData.sexStatus == -1 ? 'checked="checked"' : ''}>男女通用&nbsp;&nbsp;<input type="radio" name="sexStatus" value="0" ${quesetionData.sexStatus == 0 ? 'checked="checked"' : ''}>男性用户&nbsp;&nbsp;<input type="radio" name="sexStatus" value="1" ${quesetionData.sexStatus == 1 ? 'checked="checked"' : ''}>女性用户</br></br>
	手机端展示类型：<select name="mShowType">
			<option value="0" ${quesetionData.mShowType == 0 ? 'selected=select' : '' }>请选择</option>
			<option value="1" ${quesetionData.mShowType == 1 ? 'selected=select' : '' }>小型输入框</option>
			<option value="2" ${quesetionData.mShowType == 2 ? 'selected=select' : '' }>大型输入框</option>
			<option value="3" ${quesetionData.mShowType == 3 ? 'selected=select' : '' }>是否题</option>
			<option value="4" ${quesetionData.mShowType == 4 ? 'selected=select' : '' }>程度题-6</option>
			<option value="5" ${quesetionData.mShowType == 5 ? 'selected=select' : '' }>程度题-7</option>
			<option value="6" ${quesetionData.mShowType == 6 ? 'selected=select' : '' }>不以区间间隔</option>
			<option value="7" ${quesetionData.mShowType == 7 ? 'selected=select' : '' }>以区间间隔</option>
			<option value="8" ${quesetionData.mShowType == 8 ? 'selected=select' : '' }>页面展示类型(日期)</option>
			<option value="9" ${quesetionData.mShowType == 9 ? 'selected=select' : '' }>双列联动</option>
			<option value="10" ${quesetionData.mShowType == 10 ? 'selected=select' : '' }>双列不联动 以区间间隔</option>
			<option value="11" ${quesetionData.mShowType == 11 ? 'selected=select' : '' }>双列不联动 不以区间间隔</option>
			<option value="12" ${quesetionData.mShowType == 12 ? 'selected=select' : '' }>单选题 </option>
			<option value="13" ${quesetionData.mShowType == 13 ? 'selected=select' : '' }>多选题 </option>
			<option value="14" ${quesetionData.mShowType == 14 ? 'selected=select' : '' }>上传照片</option>
		</select></br></br>
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
	<c:forEach var="var" items="${answersData}" varStatus="status">
		<tr class="row">
			<td><input class="answerContent" value="${var.content }" name="answers[${status.index}].content"/></td>
			<td><input class="answerOrders" value="${var.orders }" name="answers[${status.index}].orders"/></td>
			<td>
			<input type="radio" name="answers[${status.index}].sexStatus" value="-1" ${var.sexStatus == -1 ? 'checked="checked"' : ''}/>男女通用&nbsp;&nbsp;
			<input type="radio" name="answers[${status.index}].sexStatus" value="0"  ${var.sexStatus == 0 ? 'checked="checked"' : ''}/>男性用户&nbsp;&nbsp;
			<input type="radio" name="answers[${status.index}].sexStatus" value="1"  ${var.sexStatus == 1 ? 'checked="checked"' : ''} />女性用户
			</td>
			<td>
			<input type="radio" name="answers[${status.index}].isUse" value="0"  ${var.isUse == 0 ? 'checked="checked"' : ''}/>停用&nbsp;&nbsp;
			<input type="radio" name="answers[${status.index}].isUse" value="1"  ${var.isUse == 1 ? 'checked="checked"' : ''} />启用
			</td>
			<td>
				<c:choose>
				   <c:when test="${var.relationIds != null}">
				   	<input type="text" name="answers[${status.index}].relationIdsStr" value="<c:forEach var = "relate" items="${var.relationIds}">${relate},</c:forEach>"/>
                   </c:when>
                   <c:otherwise>
                     	<input type="text" name="answers[${status.index}].relationIdsStr" value=""/>
                    </c:otherwise>
				</c:choose>
			</td>
			<td>&nbsp;&nbsp;<a class="deleteAnswer" style="cursor: hand">删除该项</a></td>
	    </tr>
    </c:forEach>
    </tbody>
    </table>
    <div class="modules">所属模块：<a class="addModules" href="javascript:;">添加模块</a></div>
    <c:forEach var="var" items="${modulesData}" varStatus="status">
    	<!-- 模块的父级节点 -->
    	<div class="modules">
   	 	<select class="parentModules" name="modulesView[${status.index}].modules[0].questionnaireId">
    		<c:forEach var="parent" items="${parentModulesData.modules}">
    			<option value="${parent.questionnaireId}" ${var.questionnaireId != null && parent.questionnaireId != null && var.questionnaireId == parent.questionnaireId ? 'selected="select"' : '' }>${parent.content}</option>
    		</c:forEach>
    	</select>
    	<c:forEach var="varChild" items="${var.children}">
    		<!-- 模块的子级节点 -->
	    	<select class="childModules" name="modulesView[${status.index}].modules[1].questionnaireId">
	    		<c:forEach var="child" items="${varChild.children }">
	    			<option value="${child.questionnaireId}" ${child.questionnaireId != null && varChild.questionnaireId != null && child.questionnaireId == varChild.questionnaireId ? 'selected="select"' : '' }>${child.content}</option>
	    		</c:forEach>
	    	</select>
	    	<input type="hidden" value = "${varChild.questionnaireId}" class="defaultQuestionnaireId">
    	</c:forEach><a class="addModules" style="cursor:hand">添加模块</a>&nbsp;&nbsp;<a class="deleteModules" style="cursor:hand">删除模块</a>
    	</div>
    </c:forEach>
    保密功能：<input type="radio" name="isHide" value="1" ${quesetionData.isHide != null && quesetionData.isHide == 1 ? 'checked="check"' : '' }/>启用&nbsp;&nbsp;
    <input type="radio" name="isHide" value="0" ${quesetionData.isHide != null && quesetionData.isHide == 0 ? 'checked="check"' : '' }/>关闭</br></br>
    
    启用状态：<input type="radio" name="inUse" value="1" ${quesetionData.inUse != null && quesetionData.inUse == 1 ? 'checked="check"' : '' }/>启用&nbsp;&nbsp;
    <input type="radio" name="inUse" value="0" ${quesetionData.inUse != null && quesetionData.inUse == 0 ? 'checked="check"' : '' }/>关闭</br></br>
    
    投放端口：<input type="radio" name="platform" value="-1" ${quesetionData.platform == null || quesetionData.platform == -1 ? 'checked="check"' : '' }/>通用&nbsp;&nbsp;
    <input type="radio" name="platform" value="0" ${quesetionData.platform != null && quesetionData.platform == 0 ? 'checked="check"' : '' }/>PC端
    <input type="radio" name="platform" value="1" ${quesetionData.platform != null && quesetionData.platform == 1 ? 'checked="check"' : '' }/>手机端&nbsp;&nbsp;</br></br>
    <input type="button" id="button" class="btn-submit" value="提交修改">
    </form>
    <script type="text/javascript" src="${basePath}/js/question/newEditAnswer.js"></script>
</body>

</html>
  