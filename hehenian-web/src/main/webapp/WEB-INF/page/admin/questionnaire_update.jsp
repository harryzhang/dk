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
			
		  function updateOption(id){
		  		$("#update_" +id).css('display','none');
		  		$("#sure_" +id).css('display','inline');
		  		$("#score_" +id).removeAttr("disabled");
		  		$("#option_" +id).removeAttr("disabled");
		  	
		  }
		  function updateSubmit(id){
		 		param["paramMap.id"] = id;
		 		param["paramMap.questionId"] = $("#id").val();
				param["paramMap.score"] = $("#score_" +id).val();
				param["paramMap.option"] = $("#option_" +id).val();
		  		$.post("updateOption.do",param,callBack);
		  }
		  function  deleteOption(id){
		  	param["paramMap.id"] = id;
		  	param["paramMap.questionId"] = $("#id").val();
		  	$.post("deleteOption.do",param,callBack);
		  }
		   function addOption(){
			var html ="&nbsp;&nbsp;&nbsp;&nbsp;<span id='option_add'><input name='option' id='inputadd";
			html+="' style='width:200px;margin-top: 5px'/>&nbsp;&nbsp;&nbsp;";
			html+="<input name='score' style='text-align:center ;width:25px' id='inputaddscore' />分&nbsp;&nbsp;&nbsp;&nbsp;";
			html+="<input type='button' value='确认添加' onclick='addSubmit()' /> <br /></span> ";
			$("#options_list_lb").append(html)
		  }
		  function addSubmit(){
		 		param["paramMap.questionId"] = $("#id").val();
				param["paramMap.score"] = $("#inputaddscore" ).val();
				param["paramMap.option"] = $("#inputadd").val();
				$.post("addOption.do",param,callBack);
		  }
	 	function callBack(data){
		 	var strJosn = eval(data)
		 	if(strJosn==1){
		 		alert("操作失败");
		 		return false;
		 	}
		 	var html=""
		 	$("#options_list_lb").get(0).innerHTML="";
		 	for(var i=0; i<strJosn.length; i++){
		 	 html="<span >"+(i+1)+"</span>:";
		 	 html+="<input name=\"option\" id=\"option_"+strJosn[i].id+"\" value=\""+strJosn[i].option+"\" style=\"width:200px;margin-top: 5px\" disabled=\"disabled\"/>";
		 	 html+="&nbsp;&nbsp;&nbsp;<input name=\"score\" style=\"width:25px\" id=\"score_"+strJosn[i].id+"\"  value=\""+strJosn[i].score+" \"disabled=\"disabled\"/>分";
		 	 html+=" &nbsp;<input type=\"button\" id=\"update_"+strJosn[i].id+"\" value=\"修改选项\"  onclick=\"updateOption(\'"+strJosn[i].id+"\')\" />";
		 	 html+="<input type=\"button\" id=\"sure_"+strJosn[i].id+"\"  value=\"确认修改\" onclick=\"updateSubmit(\'"+strJosn[i].id+"\')\"  style=\"display:none\"/>";
			 html+=	"<input  type=\"button\" onclick=\"deleteOption(\'"+strJosn[i].id+"\')\"  value=\"删除选项\" /> <br/>";
			 $("#options_list_lb").append(html);
			}
		}
			function smaxScore(){
			var maxScore=0;
			$('input[name="score"]').each(function(){
				if(maxScore<Number($(this).val())){
					maxScore=Number($(this).val());
				}
			});
			$("#maxScore").val(maxScore);
			submitData();
			return true;
		
		}
		function submitData(){
			
			param["paramMap.id"]=$("#id").val();
			param["paramMap.question"]=$("#question").val();
			param["paramMap.status"]=$('input[name="paramMap.status"]:checked').val();
			param["paramMap.maxScore"]=$("#maxScore").val();
			param["paramMap.questionType"]=$('input[name="paramMap.questionType"]:checked').val();
			param["paramMap.type"]=$("#type").val();
			$.shovePost("updateQuestionnaire.do", param, function(data) {
			if (data == "1") {
				alert("修改成功")
				window.parent.closeMthod();
			}
			if (data == "2") {
				alert("修改失败");
				return;
			}
			
		});
		}
		</script>

	</head>
	<body>
		<s:hidden id="sort_" name="paramMap.sort"></s:hidden>

			<s:hidden id="id" name="paramMap.id" />
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
										cssStyle="width: 400px; padding:5px;" id="question"></s:textarea>
								</td>
							</tr>
							<tr>
								<td style="height: 25px;" align="right" class="blue12">
									问卷状态：
								</td>
								<td align="left" class="f66">
									<s:radio list="#{'1':'显示','2':'隐藏'}"
								name="paramMap.status" cssClass="status" />
								</td>
							</tr>
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
								<span id="options_list_lb" >
									<s:iterator value="options" var="bean" status="s">
										<span >${s.count}</span>:	
										<input name="option" id="option_${bean.id}" value="${bean.option }" style="width:200px;margin-top: 5px"  disabled="disabled"/>&nbsp;&nbsp;&nbsp;
										<input name="score" style="width:25px" id="score_${bean.id}"  value="${bean.score}" disabled="disabled"/>分
								    	&nbsp;
								    	<input type="button" id="update_${bean.id }" value="修改选项"  onclick="updateOption('${bean.id}')" />
								    	<input type="button" id="sure_${bean.id }"  value="确认修改" onclick="updateSubmit('${bean.id}')"  style="display:none"/>
								    	<input  type="button" onclick="deleteOption(${bean.id})"  value="删除选项" />
										<br />
									</s:iterator>
									</span>
								</td>
							</tr>
							<tr>
								<td height="36" align="right" class="blue12">
									<s:hidden name="action"></s:hidden>
									<s:hidden name="paramMap.maxScore" id = "maxScore"></s:hidden>
									&nbsp;
								</td>
								<td>
									<input id="btn_save" type="submit" value="确定"  onclick="return smaxScore()"/>
									&nbsp;
									<input type="button" value="新增选项" id="add_option"  onclick="addOption()"/>
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
	</body>
</html>
