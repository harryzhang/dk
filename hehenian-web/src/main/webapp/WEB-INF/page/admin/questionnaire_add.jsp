<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<title>更新内容</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="This is my page" />
		<link href="../css/admin/admin_css.css" rel="stylesheet"
			type="text/css" />
		<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
		<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
		<script language="javascript" type="text/javascript"
			src="../script/jbox/jquery.jBox-2.3.min.js"></script>
		<script>
		function submitForm(){
			var optionArray = [];
			var scoreArray=[];
			$('input[name="option"]').each(function(){
				optionArray.push($(this).val());
			});
			var maxScore=0;
			$('input[name="score"]').each(function(){
				if(maxScore<Number($(this).val())){
					maxScore=Number($(this).val());
				}
				scoreArray.push($(this).val());
			});
				var options = optionArray.join(",");
				var sorces = scoreArray.join(",");
				var param = {};
		param["paramMap.question"]=$("#question").val();
		param["paramMap.questionType"]=$('input[name="paramMap.questionType"]:checked').val();
		param["paramMap.type"]=$("#type").val();
		param["paramMap.sorces"]=sorces;
		param["paramMap.options"]=options;
		param["paramMap.status"]=$('input[name="paramMap.status"]:checked').val();
		param["paramMap.maxScore"]=maxScore;
			$.shovePost("addQuestionnaire.do", param, function(data) {
			if (data == "1") {
				alert("添加成功")
				window.parent.closeMthod();
			}
			if (data == "2") {
				alert("添加失败");
				return;
			}
			
		});
		}
	    $(function(){
			$("#add_option").click(function(){
				var count = Number($("#count").val())+1;
				var html ="<span id='option_"+count+"'><input name='option' id='input_"+count;
				html+="' style='width:300px;margin-top: 5px'/>&nbsp;&nbsp;&nbsp;";
				html+="<input name='score' style='text-align:center ;width:25px' />分&nbsp;&nbsp;";
				html+="<input type='button' value='删除选项' onclick='del_option("+count+")' /> <br /></span> ";
				$("#options_list_lb").append(html)
				$("#count").val(count);
			});
	  });
	  function del_option(id){
		  var html=$("#option_"+id).html();
		  $("#option_"+id).remove();
		  if($("#options_list_lb").html().trim() ==null||$("#options_list_lb").html().trim()==""){
			  $("#options_list_lb").append(html);
			  alert("删除失败，至少保留一个选项");
		  }
	  }
	  function checkDate(){
	  var result = 0;
	  $('input[name="option"]').each(function(){
			if($(this).val()==""){
				result =1;
			}
		});
		$('input[name="score"]').each(function(){
			if($(this).val()==""){
				result =1;
			}
		});
		if($("#question").val().trim==""){
			result =1;
		}
		if(result == 0){
			submitForm();
			return true;
		}
		alert("请填写完整信息");
		return false;
	  }
		</script>

	</head>
	<body>
		<form id="addQuestion" name="addQuestion" action="addQuestionnaire.do"
			method="post">
			<div id="right">
				<div style="padding: 15px 10px 0px 10px;">


					<div style="width: auto; background-color: #FFF; padding: 10px;">
						<table width="100%" border="0" cellspacing="1" cellpadding="3">
							<tr>
								<td style="width: 100px; height: 25px;" align="right"
									class="blue12">
								</td>
								<td align="left" class="f66">

								</td>
							</tr>

							<tr>
								<td style="height: 25px;" align="right" class="blue12">
									问卷题目：
								</td>
								<td align="left" class="f66">
									<s:textarea name="paramMap.question" rows="3"
										cssStyle="width: 400px; padding:5px;" id = "question"></s:textarea>

									<span class="require-field">*<s:fielderror
											fieldName="paramMap.question" /> </span>
								</td>
							</tr>
							<tr>
								<td style="height: 25px;" align="right" class="blue12">
									问卷状态：
								</td>
								<td align="left" class="f66">
									<s:radio list="#{'1':'显示','2':'隐藏'}"
								name="paramMap.status" cssClass="status" value="2"/>
								</td>
							</tr>
							<tr>
								<td style="height: 25px;" align="right" class="blue12">
									题型：
								</td>
								<td align="left" class="f66">
									<s:radio list="#{'1':'单选','2':'多选'}"
								name="paramMap.questionType" cssClass="status" value="2" id="questionType"/>
								</td>
							</tr>
							<tr>
								<td style="height: 25px;" align="right" class="blue12">
									题目类型：
								</td>
								<td align="left" class="f66">
								<s:select list="#{'1':'个人投资情况调查','2':'风险偏好调查','3':'认知程度调查','4':'互联网金融工具使用情况调查','5':'其他'}"
								name="paramMap.type" headerKey="0" headerValue="--请选择--" id="type"
								></s:select>
									
								</td>
							</tr>
							<tr>
								<td style="width: 100px; height: 25px;" align="right"
									class="blue12">
									选项：
								</td>
								<td align="left" class="f66" id="options_list">
									<span id="options_list_lb"> <span id="option_1">
											<input name="option" id="input_1"
												style="width: 300px; margin-top: 5px" />&nbsp;&nbsp;&nbsp;<input
												name="score" style="text-align: center; width: 25px" />分&nbsp;&nbsp;<input
												type="button" value="删除选项" id="delete"
												onclick="del_option('1')" /> <br /> </span> </span>
									<span class="require-field"><s:fielderror
											fieldName="option" /> </span>
									<span class="require-field"><s:fielderror
											fieldName="sorce" /> </span>
									<input type="hidden" id="count" value="1" />
								</td>
							</tr>
							
							<tr>
							
								<td height="36" align="right" class="blue12">
									<s:hidden name="paramMap.options" id="options"></s:hidden>
									<s:hidden name="paramMap.sorces" id="sorces"></s:hidden>
									<s:hidden name="paramMap.maxScore" id="maxScore"></s:hidden>
									<s:hidden name="action"></s:hidden>
									&nbsp;
								</td>
								<td>
									<input id="btn_save" type="button" value="确定"
										onclick="return checkDate()" />
									&nbsp;
									<input type="button" value="新增选项" id="add_option" />
									<span id="messageInfo" class="blue12"></span> &nbsp; &nbsp;
									<span class="require-field"><s:fielderror
											fieldName="actionMsg" theme="simple"></s:fielderror> </span>
								</td>
							</tr>
							<tr>
								<td colspan="2">
									<img id="img" src="../images/NoImg.GIF"
										style="display: none; width: 100px; height: 100px;" />
								</td>
							</tr>
						</table>
						<br />
					</div>
				</div>
			</div>
		</form>
	</body>
</html>
