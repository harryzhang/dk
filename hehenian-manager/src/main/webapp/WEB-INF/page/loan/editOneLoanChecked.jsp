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
<div>
		<form id="editOneLoanChecked" method="post" style="padding:2px;"> 
		<div class="topbar_select" style="margin-top: 40px;">
		<div class="titlepart">一审结果： </div>
			<div class="bodypart">
			<input type="radio" name="checkResult" id="radio_check_1" value="1" ${loanCheckedDo.checkResult eq '1' ? 'checked="checked"' : ''} /><label for="radio_check_1">通过：</label>
			<input type="radio" name="checkResult" id="radio_check_2" value="2" ${loanCheckedDo.checkResult eq '2' ? 'checked="checked"' : ''} /><label for="radio_check_2">修改：</label>
			<input type="radio" name="checkResult" id="radio_check_0" value="0" ${loanCheckedDo.checkResult eq '0' ? 'checked="checked"' : ''} /><label for="radio_check_0">不通过：</label>
			</div>
		</div>
		<div class="topbar_select">
		<div class="titlepart" >审核意见：</div>
		<div class="bodypart">
		<input type="hidden" name="id" value="${loanCheckedDo.id }" />
	        	<input type="hidden" name="loanId" value="${loanCheckedDo.loanId }" />
	        	<input type="hidden" name="checkType" value="1" />
	        	<textarea rows="3" cols="30" name="opinion" id="opinion" style="width:300px; height:60px;">${loanCheckedDo.opinion}</textarea>
		</div>
		</div>
	    
	   </form>
	</div>
	<div class="topbar_select" >
	<div class="titlepart">&nbsp;</div>
	<div class="bodypart">
	   <c:if test="${!empty loanCheckTwo && loanCheckTwo eq '1'}">
	   		<a class="l-btn l_btn_size"  data-options="disabled:true" style="float:left;"
			plain="true" icon="icon-save" href="javascript:void(0)"  ><i class="fa fa-check"></i>&nbsp;确定</a>
	   </c:if>
	   <c:if test="${empty loanCheckTwo}">
	   		<a class="l-btn l_btn_size" style="float:left;"
			plain="true" icon="icon-save" href="javascript:void(0)" onclick="checkformData()"><i class="fa fa-check green"></i>&nbsp;确定</a>
	   </c:if>
	 
	<a id="loan_checked_cancel" class="l-btn l_btn_size"
		plain="true" icon="icon-cancel" href="javascript:closewin();"><i class="fa fa-close"></i>&nbsp;取消</a>
		</div>
</div>
<style type="text/css"	>
.topbar_select{
width:500px;
padding:10px 40px;
clear: both;
display: inline-block;
}
.titlepart{
width:100px;
min-height: 30px;
float: left;
text-align: right;
}
.btnmar{
margin-left: 50px;
}
 
.bodypart{
min-height:30px;
float:left;
}

.topbar_select input[type="radio"]{
width:auto !important;
text-align: left;
cursor: pointer;
margin-left: 20px;
}
.red{color:red;}
.green{color:#217B03;}
.yellow{color:yellow;}
.l_btn_size{
margin-left:10px !important; 
text-decoration: none !important; 
padding: 4px 14px !important;
}
</style>

<script type="text/javascript">
function closewin()
{
	$("#oneCheckedDiv").dialog("close");
	return false;
}
function checkformData()
{
	var checkrlt=$("input[name='checkResult']:checked").val();  
	if(checkrlt=="1")
		{
			var opt = $.messager.confirm("审核", '请确认要提交', function(r){
				if(r==true)
					{
					save_loan_checked('#oneCheckedDiv','#editOneLoanChecked');
					}
			});
			return false;
		}else{
			if($("#opinion").val()=="")
				{
				 	$.messager.alert("提示","请填写审核意见","error");
				 	$("#opinion").focus();
				 	return false;
				}else{
					var opt = $.messager.confirm("审核", '请确认要提交', function(r){
						if(r==true)
							{
								save_loan_checked('#oneCheckedDiv','#editOneLoanChecked');
							}
					});
				}
		}
}
</script>

</body>
</html>