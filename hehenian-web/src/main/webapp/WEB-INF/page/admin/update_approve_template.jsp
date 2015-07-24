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
					if($("#tb_name").val()==""){
						$("#title").html(" * 名称不能为空!");
					return false;
					}else{
						$("#title").html("");
					}
					if(isNaN($("#bt_sort").val()))
			       	{
			        	$("#sort").html(" * 只能输入数字");
			       		return  false;
			       	}
			       	if($("#bt_sort").val().indexOf(".")!= -1)//限制小数的输入
			       	{
			       	 $("#sort").html(" * 只能为数字");
			       		return false;
			       	}
						$("#updatetenplate").submit();
				});
		  });
		   
		</script>
		
	</head>
	<body>
		<form id="updatetenplate" name="updatetenplate" action="updateNoticeTemplateInfo.do" method="post">
		<s:iterator var="bean" value="pageBean.page" status="st">
			<div id="right">
				<div style="padding: 15px 10px 0px 10px;">
					<div>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="100" height="28"  class="main_alll_h2">
									<a href="updateNoticeTemplateInit.do?id=${bean.id}&&notice_style=${bean.notice_style}">编辑类型提醒</a>
								</td>
								<td>
									&nbsp;
								</td>
							</tr>
						</table>
					</div>
					 
					</div>
					<div style="width: auto; background-color: #FFF; padding: 20px;">
						<table width="800" border="0" cellspacing="1" cellpadding="3"  align="center" >
								<tr>
								<td style="width: 100px; height: 30px;" align="right"
									class="blue12">
									提醒类型：
								</td>
								<td align="left" class="f66">
									${bean.title }
								</td>
							</tr>
							<tr>
								<td style="width: 100px; height: 30px;" align="right"
									class="blue12">
									标示名：
								</td>
								<td align="left" class="f66">
									${bean.nid }
								</td>
							</tr>
								<tr>
								<td style="width: 100px; height: 30px;" align="right"
									class="blue12">
									名称：
								</td>
								<td align="left" class="f66">
									<input type="text"  id="tb_name" name="bean_name" class="in_text_250" value="${bean.name }"/>
									<span class="require-field" id="title">*<s:fielderror fieldName="title"></s:fielderror></span>
								</td>
							</tr>
								<tr>
								<td style="width: 100px; height: 30px;" align="right"
									class="blue12" valign="top">
									模板：
								</td>
								<td align="left" class="f66">
									<textarea rows="5" cols="40" name="bean_template">${bean.template }</textarea>
									<span class="require-field">*带有“[]”字符的为系统调用字符，不可修改。谢谢配合！<s:fielderror fieldName="title"></s:fielderror></span>
								</td>
							</tr>
								<tr>
								<td style="width: 100px; height: 30px;" align="right"
									class="blue12">
									排序：
								</td>
								<td align="left" class="f66">
									<input type="text" id="bt_sort" name="bean_sort" class="in_text_250" value="${bean.sort }"/>
									<span class="require-field" id="sort">*<s:fielderror fieldName="title"></s:fielderror></span>
								</td>
							</tr>
							<tr>
								<td height="36" align="right" class="blue12">
									<s:hidden name="action"></s:hidden>
									<input type="hidden" value="${bean.id}" name="id" id="id" />
									<input type="hidden" value="${bean.notice_id}" name="notice_id" id="notice_id" />
									<input type="hidden" value="${bean.notice_style}" name="notice_style" id="notice_style" />
									<input type="hidden" value="${bean.sid}" name="sid" id="sid" />
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
			</s:iterator>
		</form>
	</body>
</html>
