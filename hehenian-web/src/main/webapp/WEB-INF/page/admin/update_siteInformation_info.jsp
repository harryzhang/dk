<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<title>借款管理</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="This is my page" />
		<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
		
		<script src="../script/jquery-1.7.1.min.js" type="text/javascript"></script>
		<script src="../layer/layer.mintest.js" type="text/javascript"></script>
		<script src="../layer/layertest.js" type="text/javascript"></script>
		<script>
		  $(function(){
		      //提交表单
				$("#btn_save").click(function(){
					 //验证手机号码
			           if($("#tb_cellphone").val()==""){
			              $("#error_id").html(" * 请填写手机号码！");  
			              return false;
	                   }else if((!/^1[3-8]\d{9}$/.test($("#tb_cellphone").val()))){ 
		                  $("#error_id").html(" * 请正确填写手机号码！");
		                  return false;
	                   }else{
		                  $("#error_id").html("");
	                   }
	                   //验证邮箱
			           if(!/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/.test($("#tb_emial").val())){
			                  $("#error_id").html(" * 请正确填写工作邮箱");
			                  return false;
			           }else{
			                $("#error_id").html("");
			           }
			         
			           //邮编验证
			           if(isNaN($("#tb_postcode").val()))
				       	{
				        	$("#error_id").html(" * 邮编只能为数字");
				       		return  false;
				       	}
				       	if($("#tb_postcode").val().indexOf(".")!= -1)//限制小数的输入
				       	{
				       	 $("#error_id").html(" * 邮编只能为数字");
				       		return false;
				      	}
				       	if($("#tb_postcode").val().length!=6)//
				       	{
				       	 $("#error_id").html(" * 邮编长度为6位");
				       		return false;
				      	}
				      	//QQ号码验证
				         if(isNaN($("#tb_qq").val()))
				       	{
				        	$("#error_id").html(" * QQ号码只能为数字");
				       		return  false;
				       	}
				       	if($("#tb_qq").val().indexOf(".")!= -1)//限制小数的输入
				       	{
				       	 $("#error_id").html(" * QQ号码只能为数字");
				       		return false;
				       	}
				       	if($("#tb_qq").val().substring(0, 1)==0)//输入的第一个数字不能为0
				       	{
				       	 $("#error_id").html(" * QQ号码只能不能以0开头");
				       		return  false;
				       	}
			           param["paramMap.id"]=$("#paramMap_id").val();
				       param["paramMap.siteName"]=$("#tb_siteName").val();
				       param["paramMap.companyName"]=$("#tb_companyName").val();
				       param["paramMap.postcode"]=$("#tb_postcode").val();
				       param["paramMap.address"]=$("#tb_address").val();
				       param["paramMap.principal"]=$("#tb_principal").val();
				       param["paramMap.contact"]=$("#tb_contact").val();
				       param["paramMap.telephone"]=$("#tb_telephone").val();
				       param["paramMap.cellphone"]=$("#tb_cellphone").val();
				       param["paramMap.fax"]=$("#tb_fax").val();
				       param["paramMap.emial"]=$("#tb_emial").val();
				       param["paramMap.qq"]=$("#tb_qq").val();
				       param["paramMap.servicePhone"]=$("#tb_servicePhone").val();
				       param["paramMap.certificate"]=$("#tb_certificate").val();
				       param["paramMap.regionName"]=$("#tb_regionName").val();
				       $.post("updateSiteWorkData.do",param,function(data){
				    	   if(data.msg=="请填写站点名称"){
				    		   $("#error_id").html(" * 请填写站点名称");
				    		   return  false;
					    	}
				    	   if(data.msg=="请填写公司名称"){
				    		   $("#error_id").html(" * 请填写站点名称");
				    		   return  false;
					    	}
				    	   if(data.msg=="请填写邮编"){
				    		   $("#error_id").html(" * 请填写邮编");
				    		   return  false;
					    	}
				    	   if(data.msg=="请填写地址"){
				    		   $("#error_id").html(" * 请填写地址");
				    		   return  false;
					    	}
				    	   if(data.msg=="请填写负责人"){
				    		   $("#error_id").html(" * 请填写负责人");
				    		   return  false;
					    	}
				    	   if(data.msg=="请填写联系人"){
				    		   $("#error_id").html(" * 请填写联系人");
				    		   return  false;
					    	}
				    	   if(data.msg=="请填写手机号码"){
				    		   $("#error_id").html(" * 请填写手机号码");
				    		   return  false;
					    	}
				    	   if(data.msg=="请填写站点名称"){
				    		   $("#error_id").html(" * 请填写站点名称");
				    		   return  false;
					    	}
				    	   if(data.msg=="请正确填写公司电话号码"){
				    		   $("#error_id").html(" *请正确填写公司电话号码");
				    		   return  false;
					    	}
				    	   if(data.msg=="请正确填写传真号码"){
				    		   $("#error_id").html(" * 请正确填写传真号码");
				    		   return  false;
					    	}
				    	   if(data.msg=="请填写邮箱号码"){
				    		   $("#error_id").html(" * 请填写邮箱号码");
				    		   return  false;
					    	}
				    	   if(data.msg=="请填写QQ号码"){
				    		   $("#error_id").html(" * 请填写QQ号码");
				    		   return  false;
					    	}
				    	   if(data.msg=="请正确填写服务电话号码"){
				    		   $("#error_id").html(" * 请正确填写服务电话号码");
				    		   return  false;
					    	}
				    	   if(data.msg=="请正确填写ICP证书号"){
				    		   $("#error_id").html(" * 请正确填写ICP证书号");
				    		   return  false;
					    	}
				    	   if(data.msg=="请填写站点域名"){
				    		   $("#error_id").html(" * 请填写站点域名");
				    		   return  false;
					    	}
				    	   else
				    	   {
				    		   $.post("updateSiteInfor.do",param,function(data)
				    			{
				    			   if(data=="1"){
						    		   alert("修改成功");
						    		   window.location.href="querySiteInforInit.do";
							    	}
							    	if(data=="2")
							    	{
								    	alert("修改失败");
							    	}
				    			});
				    	   }
				       });
					
				});
		  });
		   
		</script>
		
	</head>
	<body>
		<form>
			<div id="right"
				style="background-image: url(../images/admin/right_bg_top.jpg); background-position: top; background-repeat: repeat-x;">
					<div style="width: auto; background-color: #FFF; padding: 15px;">
						<table width="90%" border="0" cellspacing="1" cellpadding="3"  align="center" >
							<tr>
							<td style="width: 100px; height: 30px;" align="right"
									class="blue12">
									站点名称：
								</td>
								<td align="left" class="f66">
								<s:textfield id="tb_siteName" name="paramMap.siteName"
										cssClass="in_text_250" theme="simple"  cssStyle="height: 20px;line-height: 20px;" />
									<span class="require-field">*<s:fielderror fieldName="title"></s:fielderror></span>
								</td>
								<td style="width: 100px; height: 30px;" align="right"
									class="blue12">
									公司名称：
								</td>
								<td align="left" class="f66">
								 <s:textfield id="tb_companyName" name="paramMap.companyName"
										cssClass="in_text_250" theme="simple" cssStyle="height: 20px;line-height: 20px;" />
									<span class="require-field">*<s:fielderror fieldName="title"></s:fielderror></span>
								</td>
							</tr>
							<tr>
								<td style="width: 100px; height: 30px;" align="right"
									class="blue12">
									邮编：
								</td>
								<td align="left" class="f66">
									<s:textfield id="tb_postcode" name="paramMap.postcode"
										cssClass="in_text_250" theme="simple" cssStyle="height: 20px;line-height: 20px;" />
									<span class="require-field">*<s:fielderror fieldName="title"></s:fielderror></span>
								</td>
								<td style="width: 100px; height: 30px;" align="right"
									class="blue12">
									地址：
								</td>
								<td align="left" class="f66">
									<s:textfield id="tb_address" name="paramMap.address"
										cssClass="in_text_250" theme="simple"  cssStyle="height: 20px;line-height: 20px;"  />
									<span class="require-field">*<s:fielderror fieldName="title"></s:fielderror></span>
								</td>
							</tr>
							<tr>
								<td style="width: 100px; height: 30px;" align="right"
									class="blue12">
									负责人：
								</td>
								<td align="left" class="f66">
									<s:textfield id="tb_principal" name="paramMap.principal"
										cssClass="in_text_250" theme="simple" cssStyle="height: 20px;line-height: 20px;" />
									<span class="require-field">*<s:fielderror fieldName="title"></s:fielderror></span>
								</td>
									<td style="width: 100px; height: 30px;" align="right"
									class="blue12">
									联系人：
								</td>
								<td align="left" class="f66">
									<s:textfield id="tb_contact" name="paramMap.contact"
										cssClass="in_text_250" theme="simple" cssStyle="height: 20px;line-height: 20px;" />
									<span class="require-field">*<s:fielderror fieldName="title"></s:fielderror></span>
								</td>
							</tr>
							<tr>
								<td style="width: 100px; height: 30px;" align="right"
									class="blue12">
									电话号码：
								</td>
								<td align="left" class="f66">
									<s:textfield id="tb_telephone" name="paramMap.telephone"
										cssClass="in_text_250" theme="simple" cssStyle="height: 20px;line-height: 20px;" />
									<span class="require-field">*<s:fielderror fieldName="title"></s:fielderror></span>
								</td>
									<td style="width: 100px; height: 30px;" align="right"
									class="blue12">
									手机号码：
								</td>
								<td align="left" class="f66">
									<s:textfield id="tb_cellphone" name="paramMap.cellphone"
										cssClass="in_text_250" theme="simple" cssStyle="height: 20px;line-height: 20px;" />
									<span class="require-field">*<s:fielderror fieldName="title"></s:fielderror></span>
								</td>
							</tr>
							<tr>
								<td style="width: 100px; height: 30px;" align="right"
									class="blue12">
									传真：
								</td>
								<td align="left" class="f66">
									<s:textfield id="tb_fax" name="paramMap.fax"
										cssClass="in_text_250" theme="simple" cssStyle="height: 20px;line-height: 20px;" />
									<span class="require-field">*<s:fielderror fieldName="title"></s:fielderror></span>
								</td>
									<td style="width: 100px; height: 30px;" align="right"
									class="blue12">
									邮箱：
								</td>
								<td align="left" class="f66">
									<s:textfield id="tb_emial" name="paramMap.emial"
										cssClass="in_text_250" theme="simple" cssStyle="height: 20px;line-height: 20px;" />
									<span class="require-field">*<s:fielderror fieldName="title"></s:fielderror></span>
								</td>
							</tr>
								<tr>
								<td style="width: 100px; height: 30px;" align="right"
									class="blue12">
									QQ：
								</td>
								<td align="left" class="f66">
									<s:textfield id="tb_qq" name="paramMap.qq"
										cssClass="in_text_250" theme="simple" cssStyle="height: 20px;line-height: 20px;" />
									<span class="require-field">*<s:fielderror fieldName="title"></s:fielderror></span>
								</td>
									<td style="width: 100px; height: 30px;" align="right"
									class="blue12">
									服务电话：
								</td>
								<td align="left" class="f66">
									<s:textfield id="tb_servicePhone" name="paramMap.servicePhone"
										cssClass="in_text_250" theme="simple" cssStyle="height: 20px;line-height: 20px;" />
									<span class="require-field">*<s:fielderror fieldName="title"></s:fielderror></span>
								</td>
							</tr>
							<tr>
								<td style="width: 100px; height: 30px;" align="right"
									class="blue12">
									ICP证书号：
								</td>
								<td align="left" class="f66">
									<s:textfield id="tb_certificate" name="paramMap.certificate"
										cssClass="in_text_250" theme="simple" cssStyle="height: 20px;line-height: 20px;" />
									<span class="require-field">*<s:fielderror fieldName="title"></s:fielderror></span>
								</td>
									<td style="width: 100px; height: 30px;" align="right"
									class="blue12">
									站点域名：
								</td>
								<td align="left" class="f66">
									<s:textfield id="tb_regionName" name="paramMap.regionName"
										cssClass="in_text_250" theme="simple" cssStyle="height: 20px;line-height: 20px;" />
									<span class="require-field">*<s:fielderror fieldName="title"></s:fielderror></span>
								</td>
							</tr>
							<tr>
								<td colspan="4">
									<div class="require-field" id="error_id" style="padding-left: 100px; font-size: 12px;"> </div>
								</td>
							</tr>
							<tr>
								<td height="36" align="right" class="blue12">
									<s:hidden name="action"></s:hidden>
									<input type="hidden" value="${paramMap.id}" name="paramMap_id" id="paramMap_id" />
									&nbsp;
								</td>
								<td>
									<input id="btn_save" type="button"
										style="background-image: url('../images/admin/btn_queding.jpg'); width: 70px; border: 0; height: 26px"></input>
									&nbsp;
									&nbsp; &nbsp;
									<span class="require-field"><s:fielderror fieldName="actionMsg" theme="simple"></s:fielderror></span>
								</td>
							</tr>
							<tr>
								<td colspan="2">
									<img id="img" src="../images/NoImg.GIF"
										style="display: none; width: 100px; height: 100px;" />
								</td>
							</tr>
						</table>
						<br />	<br />
					</div>
				</div>
		</form>
	</body>
</html>
