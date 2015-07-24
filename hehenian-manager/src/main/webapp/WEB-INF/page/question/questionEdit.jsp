<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>修改问卷题目</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Cache-Control" content="no-cache" />
<jsp:include page="../common.jsp"></jsp:include>
<meta http-equiv="Content-Type" content="text/html";charset="UTF-8">
</head>
<body>

  
<div class="center">
<form id="frm" action="/question/save.html" method="post" ENCTYPE="multipart/form-data" >

<table>
<tr>
<td>问题名称:</td>
<td><input name="content" type="text" id="rid" style="width:300px;"  value="${data.content }" /></td>
</tr>
<tr>
<td>题目类型:</td>
<td><select class="easyui-combobox" id="rid" name="multiChoice" style="width:300px;" editable="false">
	<option value="1" <c:if test="${data.multiChoice == 1 }"><c:out value="selected='selected'"/></c:if>>单选						</option>
    <option value="2" <c:if test="${data.multiChoice == 2 }"><c:out value="selected='selected'"/></c:if>>多选						</option>
    <option value="3" <c:if test="${data.multiChoice == 3 }"><c:out value="selected='selected'"/></c:if>>开放性问题					</option>
    <option value="4" <c:if test="${data.multiChoice == 4 }"><c:out value="selected='selected'"/></c:if>>程度题，是单选的一种，5种程度</option>
    <option value="5" <c:if test="${data.multiChoice == 5 }"><c:out value="selected='selected'"/></c:if>>程度题，7种程度				</option>
    <option value="6" <c:if test="${data.multiChoice == 6 }"><c:out value="selected='selected'"/></c:if>>程度题，6种程度				</option>
    <option value="7" <c:if test="${data.multiChoice == 7 }"><c:out value="selected='selected'"/></c:if>>是否题						</option>
    </select>
</td>
</tr>
<tr>
<td>最少选择几个:</td>
<td><input name="minChoice" type="text" id="rid" value="${data.minChoice }" /></td>
</tr>
<tr>
<td>最多选择几个:</td>
<td><input name="maxChoice" type="text" id="rid" value="${data.maxChoice }" /></td>
</tr>
<tr>
<td>是否启用:</td>
<td><input name="status" type="radio" id="rid" value="1" <c:if test="${data.status == 1 }"><c:out value="checked='checked'"/></c:if>	 name="status"/> 是
<input name="status" type="radio" id="rid" value="0" <c:if test="${data.status == 0 }"><c:out value="checked='checked'"/></c:if>	name="status"/>否 </td>
</tr>
<tr>
<td>语&nbsp;&nbsp;&nbsp;&nbsp;言:</td>
<td><input name="language" type="radio" id="rid" value="1" <c:if test="${data.language == 1 }"><c:out value="checked='checked'"/></c:if>	 name="language"/>繁体中文 
<input name="language" type="radio" id="rid" value="0" <c:if test="${data.language == 0 }"><c:out value="checked='checked'"/></c:if>	name="language"/>简体中文 </td>
</tr>
<tr>
<td>平&nbsp;&nbsp;&nbsp;&nbsp;台:</td>
<td><select class="easyui-combobox" id="rid" name="platform" style="width:150px;" editable="false">
	<option value="0" <c:if test="${data.platform == 1 }"><c:out value="selected='selected'"/></c:if>>PC</option>
    <option value="1" <c:if test="${data.platform == 2 }"><c:out value="selected='selected'"/></c:if>>Android</option>
    <option value="2" <c:if test="${data.platform == 3 }"><c:out value="selected='selected'"/></c:if>>iOS</option>
    <option value="3" <c:if test="${data.platform == 4 }"><c:out value="selected='selected'"/></c:if>>不限</option>
    </select></td>
</tr>
<tr>
<td>问题类型:</td>
<td><select class="easyui-combobox" id="rid" name="classify" style="width:150px;" editable="false">
	<option value="1" <c:if test="${data.classify == 1 }"><c:out value="selected='selected'"/></c:if>>注册</option>
    <option value="2" <c:if test="${data.classify == 2 }"><c:out value="selected='selected'"/></c:if>>QA</option>
    <option value="3" <c:if test="${data.classify == 3 }"><c:out value="selected='selected'"/></c:if>>初步沟通</option>
    <option value="4" <c:if test="${data.classify == 4 }"><c:out value="selected='selected'"/></c:if>>表明立场</option>
    <option value="5" <c:if test="${data.classify == 5 }"><c:out value="selected='selected'"/></c:if>>深入了解</option>
    <option value="6" <c:if test="${data.classify == 6 }"><c:out value="selected='selected'"/></c:if>>自由沟通</option>
    <option value="7" <c:if test="${data.classify == 7 }"><c:out value="selected='selected'"/></c:if>>唤回</option>
    </select></td>
</tr>
<tr>
<td>是否与其他题目有关联:</td>
<td><input name="isRelateOther" type="text" id="rid" value="${data.isRelateOther }" /></td>
</tr>
<tr>
<td>题目展示类型:</td>
<td><input name="mshowType" type="text" id="rid" value="${data.mshowType }" /></td>
</tr>
</table>

<input name="id" 				type="hidden" id="rid" value="${data.id }" 			/>
<input name="o_content" 		type="hidden" id="rid" value="${data.content } " 	/>
<input name="o_classify" 		type="hidden" id="rid" value="${data.classify } " 	/>
<input name="o_multiChoice" 	type="hidden" id="rid" value="${data.multichoice } " />
<input name="o_minChoice" 		type="hidden" id="rid" value="${data.minChoice } " 	/>
<input name="o_maxChoice" 		type="hidden" id="rid" value="${data.maxChoice } " 	/>
<input name="o_status" 			type="hidden" id="rid" value="${data.status } " 		/>
<input name="o_language" 		type="hidden" id="rid" value="${data.language } "	/>
<input name="o_platform" 		type="hidden" id="rid" value="${data.platform } "	/>
<input name="o_isRelateOther" 	type="hidden" id="rid" value="${data.isRelateOther } "	/>
<input name="o_mshowType" 		type="hidden" id="rid" value="${data.mshowType } "	/>
    <input type="button" value="提交" onclick="sub()"/>
</form>
<script type="text/javascript">
function sub(){
	$("#frm").submit();
//	parent.$.ligerDialog.close(); //关闭弹出窗; //关闭弹出窗
//	parent.$(".l-dialog,.l-window-mask").css("display","none"); //只隐藏遮罩层
//	 parent.window.QuestionListIndex();
}


 </script>

</div>
</body>
</html>
  
  