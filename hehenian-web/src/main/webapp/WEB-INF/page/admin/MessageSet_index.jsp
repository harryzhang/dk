<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<title>借款产品参数</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="This is my page" />
		<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
		
		<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
		<link rel="stylesheet" href="../kindeditor/themes/default/default.css" />
		<link rel="stylesheet" href="../kindeditor/plugins/code/prettify.css" />
		<script charset="utf-8" src="../kindeditor/kindeditor.js"></script>
		<script charset="utf-8" src="../kindeditor/lang/zh_CN.js"></script>
		<script charset="utf-8" src="../kindeditor/plugins/code/prettify.js"></script>
		<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
		<script type="text/javascript" src="../common/dialog/popup.js"></script>
		
	<script>
	   $(function(){
	     $("#friendAward").click(function(){
	       
	        $.post("showCostType.do","type=3",function(data){
	             $("#showcontent").html("");
	            $("#feind").attr("bgcolor","#CC0000");
	            $("#showcontent").html(data);	        
	        });
	      
	     });
	    
	   });
	 
	
	</script>	

		
	</head>
	<body>
			<div id="right">
				<div style="padding: 15px 10px 0px 10px;">
					<div>
				
						<table border="0" cellspacing="0" cellpadding="0">
							<tr>
									<td  width="5" height="28"  class="xxk_all_a">
									
									<a href="emailAndMessageindex.do">邮件设置</a>
								</td>
							
								<td width="5"  class="main_alll_h2">
									<a href="queryMessage.do">短信接口设置</a>
								</td>
								<td width="80">
									&nbsp;
								</td>
							</tr>
						</table>
					</div>
					 
					</div>
					<div id="showcontent" style=" background-color: #FFF; padding: 10px;">
						<!-- 内容显示位置 -->
						
							<table width="100%">
					            <tr>
					             <td width="50%" style="padding-left: 20px">
					              <div style="background-color: gray; width: 20px; text-align: center;">1</div>
						<table width="100%" border="0" cellspacing="1" cellpadding="3">
							<tr>
							
									<span style="color: red;" id="u_tip"	></span>
							 
								<td style="width: 100px; height: 25px;" align="right"
									class="blue12">
									是否开启：
								</td>
								<td align="left" class="f66">
								 是：<input type="radio" name="paramMap_kaiqi1" value="1"
								 	<s:if test='#request.Messagemap.enable == 1'>checked="checked"</s:if>
											<s:else></s:else>
								 />&nbsp;&nbsp;&nbsp;&nbsp;
								 否：<input type="radio" name="paramMap_kaiqi1" value="0"
								 	<s:if test='#request.Messagemap.enable == 0'>checked="checked"</s:if>
											<s:else></s:else>
								 />
									<span class="require-field">*</span>
								</td>
							</tr>	
							<tr>
                                 <td style="width: 100px; height: 25px;" align="right"
									class="blue12">
									用户名：
								</td>
								<td align="left" class="f66">
							<s:textfield id="tb_username1" name="paramMap.username1"
										 theme="simple" value="%{#request.Messagemap.username}"  maxlength="20"></s:textfield>
									<span class="require-field">*</span>
								</td> 							
							
							</tr>
							<tr>
							       <td style="width: 100px; height: 25px;" align="right"
									class="blue12" >
									密码：
								</td>
								<td align="left" class="f66">
								 <input type="password" id="tb_password1" name="paramMap.password1" value="${Messagemap.password }" maxlength="20"/>
									<span class="require-field">*</span>
								</td> 	
							</tr>
							
								<tr>
							       <td style="width: 100px; height: 25px;" align="right"
									class="blue12">
									短信接口地址：
								</td>
								<td align="left" class="f66">
								<s:textarea id="tb_url1" name="paramMap.url1" theme="simple" cols="30" rows="5" value="%{#request.Messagemap.url}"></s:textarea>
									<span class="require-field">*</span>
								</td> 	
							</tr>
							
							
							<tr>
								<td height="36" align="right" class="blue12">
									<s:hidden name="action"></s:hidden>
									&nbsp;
								</td>
								<td>
									
									<button id="btn_tijiao1"
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
						
						</td>
						<td width="50%">
						<!-- - -->
						<div style="background-color: gray; width: 20px; text-align: center;">2</div>
						<table width="100%" border="0" cellspacing="1" cellpadding="3">
							
							<tr>
									<span style="color: red;" id="u_tip2"	></span>
							 
								<td style="width: 100px; height: 25px;" align="right"
									class="blue12">
									是否开启：
								</td>
								<td align="left" class="f66">
								 是：<input type="radio" name="paramMap_kaiqi2" value="1"
								 		 	<s:if test='#request.Messagemap2.enable == 1'>checked="checked"</s:if>
											<s:else></s:else>
								 
								 />&nbsp;&nbsp;&nbsp;&nbsp;
								 否：<input type="radio" name="paramMap_kaiqi2" value="0"
								 	 	<s:if test='#request.Messagemap2.enable == 0'>checked="checked"</s:if>
											<s:else></s:else>
								 />
									<span class="require-field">*</span>
								</td>
							</tr>	
							<tr>
                                 <td style="width: 100px; height: 25px;" align="right"
									class="blue12">
									用户名：
								</td>
								<td align="left" class="f66">
							<s:textfield id="tb_username2" name="username2"
										 theme="simple"   maxlength="20" value="%{#request.Messagemap2.username}"></s:textfield>
									<span class="require-field">*</span>
								</td> 							
							
							</tr>
							<tr>
							       <td style="width: 100px; height: 25px;" align="right"
									class="blue12" >
									密码：
								</td>
								<td align="left" class="f66">
								 <input type="password" id="tb_password2" name="password2"  maxlength="20" value="${Messagemap2.password }"/>
									<span class="require-field">*</span>
								</td> 	
							</tr>
							
								<tr>
							       <td style="width: 100px; height: 25px;" align="right"
									class="blue12">
									短信接口地址：
								</td>
								<td align="left" class="f66">
								<s:textarea id="tb_url2" name="url2" theme="simple" cols="30" rows="5" value="%{#request.Messagemap2.url}"></s:textarea>
									<span class="require-field">*</span>
								</td> 	
							</tr>
							
							
							<tr>
								<td height="36" align="right" class="blue12">
									<s:hidden name="action2"></s:hidden>
									&nbsp;
								</td>
								<td>
									
									<button id="btn_tijiao2"
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
						 </td>
						</tr>
						</table>
						
										
						<div>
						
						<br />
					</div>
				</div>
			</div>
		<script>
		$(function(){
		  	 
		  	 
		 $('#btn_tijiao1').click(function(){
		  var param ={};
		  param["paramMap.id"] = 1;
		  param["paramMap.kaiqi1"] = $("input[name='paramMap_kaiqi1']:checked").val();
		  param["paramMap.password1"] = $("#tb_password1").val();
		  param["paramMap.username1"] = $("#tb_username1").val();
          param["paramMap.url1"] = $("#tb_url1").val();
           $.post("updateMessageSet.do",param,function(data){
           if(data==1){
           alert("操作成功");
            $("#u_tip").html("");
           }else if(data==0){
           alert("操作失败");
           }else if(data==3){
            $("#u_tip").html("用户名不能为空");
           }else if(data==4){
            $("#u_tip").html("用户名的长度为6到20");
           } else if(data==5){
            $("#u_tip").html("密码不能为空");
           } else if(data==6){
            $("#u_tip").html("密码的长度为6到20");
           } else if(data==7){
            $("#u_tip").html("接口地址不能为空");
           } else if(data==8){
            $("#u_tip").html("开启状态不能为空");
           } 
           
              });

          });
          
	    
		});
		
		
		</script>
		
				<script>
		$(function(){
		  	 
		  	  $('#btn_tijiao2').click(function(){
		  var paramd ={};
		 
		  paramd['paramMap.id'] = 2;
		  paramd['paramMap.kaiqi2'] = $("input[name='paramMap_kaiqi2']:checked").val();
		  paramd['paramMap.password2'] = $("#tb_password2").val();
		  paramd['paramMap.username2'] = $("#tb_username2").val();
          paramd['paramMap.url2'] = $("#tb_url2").val();
           $.post("updateMessageSet1.do",paramd,function(data){
           if(data==1){
           alert("操作成功");
            $("#u_tip2").html("");
           }else if(data==0){
           alert("操作失败");
           }else if(data==3){
            $("#u_tip2").html("用户名不能为空");
           }else if(data==4){
            $("#u_tip2").html("用户名的长度为6到20");
           } else if(data==5){
            $("#u_tip2").html("密码不能为空");
           } else if(data==6){
            $("#u_tip2").html("密码的长度为6到20");
           } else if(data==7){
            $("#u_tip2").html("接口地址不能为空");
           } else if(data==8){
            $("#u_tip2").html("开启状态不能为空");
           } 
           
              });
       
	           

          });
          
          
		
		
	    
		});
		
		
		</script>
		
		
	</body>
</html>
