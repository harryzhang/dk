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
					if($("#tb_title").val()==""){
						$("#title").html(" * 请填写名称!");
						return false;
					}else{
						$("#title").html("*");
					}
					if($("#tb_description").val()==""){
						$("#description").html(" * 请填写描述信息!");
						return false;
					}else{
						$("#description").html("*");
					}
					//判断只能为数字
						if($("#init_credit").val()<0 || $("#init_credit").val()=='')
				       	{
				        	$("#init_credits").html(" * 请正确输入额度");
				       		return  false;
				       	}
				    	if(isNaN($("#init_credit").val()))
				       	{
				        	$("#init_credits").html(" * 请正确输入额度");
				       		return  false;
				       	}
					//提交表单
					$("#amountType").submit();
				});
		  });
		   
		</script>
		
	</head>
	<body>
		<form id="amountType" name="amountType" action="updatgeBorrowAmount.do" method="post">
			<div id="right">
				<div style="padding: 15px 10px 0px 10px;">
					<div>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="100" height="28" class="main_alll_h2">
									<a href="updatgeBorrowAmountInit.do?id=${paramMap.id}">修改额度类型</a>
								</td>
								<td>
									&nbsp;
								</td>
							</tr>
						</table>
					</div>
					 
					<div style="width: auto; background-color: #FFF; padding: 20px;">
						<table width="800" border="0" cellspacing="1" cellpadding="3"  align="center" >
							<tr>
							<td style="width: 120px; height: 30px;" align="right"
									class="blue12">
									借款额度类型名称：
								</td>
								
								<td align="left" class="f66">
									${paramMap.name}
								</td>
								
							</tr>
							<tr>
								<td style="width: 100px; height: 30px;" align="right"
									class="blue12">
									标识名：
								</td>
								<td align="left" class="f66">
									${paramMap.nid}
								</td>
							</tr>
								<tr>
								<td style="width: 100px; height: 30px;" align="right"
									class="blue12">
									名称：
								</td>
								<td align="left" class="f66">
									<s:textfield id="tb_title" name="paramMap.title"
										cssClass="in_text_250" theme="simple"/>
									<span class="require-field" id="title">*<s:fielderror fieldName="title"></s:fielderror></span>
								</td>
							</tr>
							<tr>
								<td style="width: 100px; height: 30px;" align="right"
									class="blue12">
									初始额度：
								</td>
								<td align="left" class="f66">
									<s:textfield id="init_credit" name="paramMap.init_credit"
										cssClass="in_text_250" theme="simple"/>
									<span class="require-field" id="init_credits">*<s:fielderror fieldName="title"></s:fielderror></span>
								</td>
							</tr>
							<tr>
								<td style="width: 100px; height: 30px;" align="right"
									class="blue12">
									状态：
								</td>
								<td align="left" class="f66">
									<input type="radio" name="paramMap_status" value="1" 
									   		<s:if test='paramMap.status == 1'>checked="checked"</s:if>
																					<s:else></s:else>
									    />开启 
									 <input type="radio" name="paramMap_status" value="2"
									    <s:if test='paramMap.status == 2'>checked="checked"</s:if>
																					<s:else></s:else>
									    />关闭
								</td>
							</tr>
							<tr>
								<td style="width: 100px; height: 30px;" align="right"
									class="blue12">
									描述：
								</td>
								<td align="left" class="f66">
									<s:textarea name="paramMap.description" cols="40" rows="5" id="tb_description"/>
									<span class="require-field"><s:fielderror fieldName="paramMap['tb_description']"></s:fielderror></span>
								</td>
							</tr>
							<tr>
								<td height="36" align="right" class="blue12">
									<s:hidden name="action"></s:hidden>
									<input type="hidden" value="${paramMap.id}" name="paramMap.id" id="paramMap.id" />
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
						<br />	<br />	<br />
					</div>
				</div>
		</form>
	</body>
</html>
