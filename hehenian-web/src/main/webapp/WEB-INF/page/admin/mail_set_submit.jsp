<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>

						<table width="50%" border="0" cellspacing="1" cellpadding="3" align="center">
							<tr style="height: 20px;"></tr>
							<tr>
								<td style="width: 100px; height: 25px;" align="right"
									class="blue12">
									SMTP服务器：
								</td>
								<td align="left" class="f66">
									<s:textfield id="tb_host" name="paramMap.host"
										 theme="simple" value="%{paramMap.host}"></s:textfield>
									<span class="require-field" id="e_host">*</span>
								</td>
							</tr>	
								<tr>
								<td style="width: 100px; height: 25px;" align="right"
									class="blue12">
									端口号:
								</td>
								<td align="left" class="f66">
									<s:textfield id="tb_port" name="paramMap.port"
										 theme="simple" value="%{paramMap.port}"></s:textfield>
									<span class="require-field" id="e_port">*</span>
								</td>
							</tr>	
							<tr>
								<td style="width: 100px; height: 25px;" align="right"
									class="blue12">
									邮箱地址：
								</td>
								<td align="left" class="f66">
									<s:textfield id="tb_mailaddress" name="paramMap.maildress"
										 theme="simple" value="%{paramMap.maildress}"></s:textfield>
									<span class="require-field" id="e_mailaddress">*</span>
								</td>
							</tr>	
							<tr>
                                 <td style="width: 100px; height: 25px;" align="right"
									class="blue12">
									邮箱密码：
								</td>
								<td align="left" class="f66">
								<!--  
									<s:password  id="tb_password" name="paramMap.password"
										 theme="simple" value="%{mailMap.mailpassword}" ></s:password>-->
										 <input type="password" name="#paramMap.password" id ="tb_password" value="${paramMap.password }"/>
									<span class="require-field" id="e_password">*</span>
								</td> 							
							
							</tr>
							<tr>
							       <td style="width: 100px; height: 25px;" align="right"
									class="blue12">
									发件人Email：
								</td>
								<td align="left" class="f66">
									<s:textfield id="tb_sendEmail" name="paramMap.sendEmail"
										 theme="simple" value="%{paramMap.sendEmail}"></s:textfield>
									<span class="require-field" id="e_sendEmail">*</span>
								</td> 	
							
							</tr>
							
								<tr>
							       <td style="width: 100px; height: 25px;" align="right"
									class="blue12">
									发件人昵称：
								</td>
								<td align="left" class="f66">
									<s:textfield id="tb_username" name="paramMap.username"
										 theme="simple"  value="%{paramMap.username}"></s:textfield>
									<span class="require-field" id="e_username">*</span>
								</td> 	
							
							</tr>
							
							
							<tr>
								<td height="36" align="right" class="blue12">
									<s:hidden name="action"></s:hidden>
									&nbsp;
								</td>
								<td>
									
									<button id="btn_tijiao"
										style="background-image: url('../images/admin/btn_queding.jpg'); width: 70px; height: 26px; border: 0px"></button>
									&nbsp; &nbsp;
								</td>
							</tr>
							<tr>
								<td colspan="2">
									<img id="img" src="../images/NoImg.GIF"
										style="display: none; width: 100px; height: 100px;" />
								</td>
							</tr>
						</table>
						
	<script>
	$(function(){
	  	 
	  	 $('#btn_tijiao').click(function(){
		  	 if($("#tb_host").val()==""){
		  		$("#e_host").html("服务器地址不能为空!");
				return false;
			  	}else{
			  		$("#e_host").html("*");
				  }
		  	 if($("#tb_port").val()==""){
			  		$("#e_port").html("端口号不能为空!");
					return false;
				  	}<%--else if (isNaN($("#tb_port").val())){
				  		$("#e_port").html("端口号只能为数字!");
				  		return false;
					  }--%>
					 else
						  {
						  $("#e_port").html("*");
						  }
		  	 if($("#tb_password").val()==""){
			  		$("#e_password").html("密码不能为空!");
					return false;
				  	}else{
				  		$("#e_password").html("*");
					  }
	  		if(!/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/.test($("#tb_mailaddress").val())){
				$("#e_mailaddress").html("请正确填写邮箱地址!");
				return false;
			}else{
				$("#e_mailaddress").html("*");
			}
	  		if(!/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/.test($("#tb_sendEmail").val())){
				$("#e_sendEmail").html("请正确填写发件人邮箱!");
				return false;
			}else{
				$("#e_sendEmail").html("*");
			}
	  	 	 if($("#tb_username").val()==""){
			  		$("#e_username").html("发件人昵称不能为空!");
					return false;
				  	}else{
				  		$("#e_username").html("*");
					  }
	  		
	  	   if (!confirm("你确认更改邮件参数设置吗?")){
			return false;
	  }
	   var param ={};
	  param["paramMap.maildress"] = $("#tb_mailaddress").val();
	  param["paramMap.password"] = $("#tb_password").val();
	  param["paramMap.sendEmail"] = $("#tb_sendEmail").val();
     param["paramMap.username"] = $("#tb_username").val();
     param["paramMap.port"] = $("#tb_port").val();
     param["paramMap.host"] = $("#tb_host").val();
     $.post("updateMailSet.do",param,function(data){
          var callBack = data.msg;  
           if(callBack == undefined || callBack == ''){
            $('#showcontent').html(data);
           }else{
             if(callBack == 1){
	          alert("操作成功!");
	          window.location.reload();
	          return false;
	          }
	          //alert(callBack);    
           }    
     });
	  	 

     });
	
   
	});
		
		
		
		</script>					
						
						
						