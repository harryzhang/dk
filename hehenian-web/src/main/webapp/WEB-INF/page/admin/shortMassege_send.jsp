<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>

<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
<script type="text/javascript" src="../css/popom.js"></script>
<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
<script type="text/javascript" src="../css/admin/popom.js"></script>
<link rel="stylesheet" href="../css/jbox/Gray/jbox.css" type="text/css"></link>
<script language="javascript" type="text/javascript"
	src="../script/jbox/jquery.jBox-2.3.min.js"></script>
<script type="text/javascript">
		$(function(){
			$(".status").change(function(){
			if($('input:radio:checked').val()==3){
				$("#date").css("display","inline");
				}else{
				$("#date").css("display","none");
				}
			})
		});
		$(function(){
				//提交表单
			$("#btn_save").click(function(){
			$(this).hide();
			$("#addShortMessege").submit();
			return false;
			});
		});
		
	//初始化
	function switchCode() {
		var timenow = new Date();
		$("#codeNum")
				.attr("src", "imageCode.do?pageId=send&d=" + timenow);
	}
	//判断验证码是否过期
	function validatorCodeIsExpired() {
		var param = {};
		param["pageId"] = "send";
		$.post("codeIsExpired.do", param, function(data) {
			if (data == 1) {
				alert("验证码已过期");
				switchCode();
				return;
			}
			$("#loginForm").submit();
		});
	}
	$(function(){
	$("#users").change(function(){
			if($("#users").val()==3){
				$("#receiver_button").css("display","inline");
				}else{
				$("#receiver_button").css("display","none");
				}
			});
	$('#receiver_button').click(function(){
			$('.content').css({'display':'block'});
		});
		var str=$('#userIds').val(); 
		$('.btn_rec').click(function(){
			$('input[name="check"]:checked').each(function(){
				str=str+$(this).val()+":"
			});
			$('#userIds').val(str);
			$('.content').css({'display':'none'});
		});
	});
</script>
	<style>
.content{display:none;width:650px;height:377px;position:fixed;top:50%;margin-top:-200px;
background:#fff;z-index:3;left:50%;margin-left:-300px; border: 1px red solid;  
overflow-y:scroll;font-size: 12px;color: #3e4959;}
</style>
<div class="content">

<h4 style="color: #3e4959;">请选择收件人</h4>
<button class="btn_rec" style="background-image: url('../images/admin/btn_queding.jpg'); width: 70px; border: 0; height: 26px; display: block;float: right" ></button>
<hr style="clear: both;"/>
<s:iterator status="str" var="user" value="#request.users">
<span id = "sp_${user.id }" style="display:block;float: left;padding: 5px 0px 5px 0px;width: 125px">
&nbsp;&nbsp;<input type="checkbox" value="${user.id }" id="in_${user.id}" class="check" name="check"/>${user.username }</span>
<s:if test="#str.count%5==0"><br></s:if>
</s:iterator>
<hr style="clear: both;"/>
<button class="btn_rec" style="background-image: url('../images/admin/btn_queding.jpg'); width: 70px; border: 0; height: 26px; display: block;float: right" ></button>
</div>
<form id="addShortMessege" name="addShortMessege"
	action="addShortMassege.do" method="post">
	<div id="right">
		<div style="padding: 15px 10px 0px 10px;">
			<div>
			</div>

			<div style="width: auto; background-color: #FFF; padding: 10px;">
				<table width="100%" border="0" cellspacing="1" cellpadding="3">
					<tr>
						<td style="width: 100px; height: 25px;" align="right"
							class="blue12">
							收件人：
						</td>
						<td align="left" class="f66">
							<s:select headerKey="0" headerValue="--请选择--" name="paramMap.users"
								id="users" cssClass="login_text" theme="simple"
								cssStyle="width: 120px;height:25px;line-height:25px;"
								list="#{'1':'所有人','2':'管理员','3':'批量发送'}" />
							<span class="require-field">*<s:fielderror
									fieldName="paramMap.users" /> </span><input type="button" value="点击选择发送人" id="receiver_button" style="display: none"/>
						</td>

					</tr>

					<tr>
						<td style="height: 25px;" align="right" class="blue12">
							标题：
						</td>
						<td align="left" class="f66">
							<s:textfield id="tb_title" name="paramMap.title"
								cssClass="in_text_2" cssStyle="width: 200px;height: 25px;"
								theme="simple"></s:textfield>
							<span class="require-field">*<s:fielderror
									fieldName="paramMap.title" /> </span>
						</td>
					</tr>
					<tr>
						<td style="height: 25px;" align="right" class="blue12">
							内容：
						</td>
						<td align="left" class="f66">
							<s:textarea name="paramMap.content" cols="40" rows="10"
								id="content"></s:textarea>
							<span class="require-field">*<s:fielderror
									fieldName="paramMap.content" /> </span>
						</td>
					</tr>
					<tr>
						<td style="width: 100px; height: 25px;" align="right"
							class="blue12">
							状态：
						</td>
						<td align="left" class="f66">
							<s:radio list="#{'1':'自动发送','2':'保存草稿','3':'定时发送'}"
								name="paramMap.status" cssClass="status" value="2" />
							<s:textfield type="text" name="paramMap.date" cssStyle="display:none" id ="date" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:'readOnly'})"/>
						</td>
					</tr>
					<tr>
						<td style="width: 100px; height: 25px;" align="right"
							class="blue12">
							发送方式：
						</td>
						<td align="left" class="f66">
							<s:select headerKey="" headerValue="--请选择--" name="paramMap.style"
								cssClass="login_text" theme="simple" 
								cssStyle="width: 120px;height:25px;line-height:25px;"
								list="#{'1':'邮件','2':'站内信','3':'短信'}" />
							<span class="require-field">*<s:fielderror
									fieldName="paramMap.style" /> </span>
						</td>
					</tr>
					<tr>
						<td style="width: 100px; height: 25px;" align="right"
							class="blue12">
							验证码：
						</td>
						<td align="left" class="f66">
							<s:textfield name="paramMap.code" cssClass="login_text"
								theme="simple" cssStyle="width: 120px" />
							<img src="imageCode.do?pageId=send" title="点击更换验证码"
								style="cursor: pointer; vertical-align: -4px; * vertical-align: -1px; margin-left: 10px;"
								id="codeNum" width="58px" height="24px"
								onclick="javascript: switchCode();"></img>
							<span class="require-field">*<s:fielderror
									fieldName="paramMap.code" /> </span>
						</td>
					</tr>
					<tr>
						<td height="36" align="right" class="blue12">
							<s:hidden name="action"></s:hidden>
							<s:hidden id ="userIds" name="paramMap.receiverId"></s:hidden>
							<s:hidden value="send" name="pageId"></s:hidden>
							&nbsp;
						</td>
						<td>
							<button id="btn_save"
								style="background-image: url('../images/admin/btn_queding.jpg'); width: 70px; border: 0; height: 26px"></button>
							&nbsp;
							<button type="reset"
								style="background-image: url('../images/admin/btn_chongtian.jpg'); width: 70px; height: 26px; border: 0px"></button>
							&nbsp; &nbsp;
							<span class="require-field"><s:fielderror
									fieldName="actionMsg" theme="simple"></s:fielderror> </span>
						</td>
					</tr>
				</table>
				<br />
			</div>
		</div>
	</div>
</form>
