<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<title>友情连接-添加</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="This is my page" />
		<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
		
		<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
		<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
		<script language="javascript" type="text/javascript" src="../My97DatePicker/WdatePicker.js"></script>
		
		<script>
			$(function(){
				//提交表单
				$("#btn_save").click(function(){
					$(this).hide();
					$("#addLinks").submit();
					return false;
				});
				
				//上传图片
				$("#btnUpImg").click(function(){
					var dir = getDirNum();
					var json = "{'fileType':'JPG,BMP,GIF,TIF,PNG','fileSource':'lists/"+dir+"','fileLimitSize':0.5,'title':'上传图片','cfn':'uploadCall','cp':'img'}";
					json = encodeURIComponent(json);
					window.showModalDialog("uploadFileAction.do?obj="+json,window,"dialogWidth=500px;dialogHeight=400px");
				});
				if("${paramMap.companyImg}"!=""){
					$("#img").attr("src","../${paramMap.companyImg}");
				}
				
			  });
			
				function uploadCall(basepath,fileName,cp){
			  		if(cp == "img"){
			  			var path = "upload/"+basepath+"/"+fileName;
			  			$("#companyImg").val(path);
						$("#img").attr("src","../"+path);
			  		}
				}
				
				function getDirNum(){
			      var date = new Date();
			 	  var m = date.getMonth()+1;
			 	  var d = date.getDate();
			 	  if(m<10){
			 	  	m = "0"+m;
			 	  }
			 	  if(d<10){
			 	  	d = "0"+d;
			 	  }
			 	  var dirName = date.getFullYear()+""+m+""+d;
			 	  return dirName; 
				}
		</script>
		
	</head>
	<body>
		<form id="addLinks" name="addLinks" action="addLinks.do" method="post">
			<div id="right">
				<div style="padding: 15px 10px 0px 10px;">
					<div>
						<table  border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="100" height="28" class="xxk_all_a">
									<a href="queryLinksListPageInit.do">友情链接管理</a>
								</td>
								<td width="2">
									&nbsp;
								</td>
								<td width="100"  class="main_alll_h2">
									<a href="addLinksInit.do">添加友情链接</a>
								</td>
								<td>
									&nbsp;
								</td>
							</tr>
						</table>
					</div>
					 
					<div style="width: auto; background-color: #FFF; padding: 10px;">
						<table width="100%" border="0" cellspacing="1" cellpadding="3">
							<tr>
								<td style="width: 100px; height: 25px;" align="right"
									class="blue12">
									公司名称：
								</td>
								<td align="left" class="f66">
									<s:textfield id="tb_title" name="paramMap.companyName"
										cssClass="in_text_2" cssStyle="width: 250px" theme="simple"></s:textfield>
									<span class="require-field">*<s:fielderror fieldName="paramMap['companyName']"></s:fielderror></span>
								</td>
							</tr>
							<tr>
								<td style="height: 25px;" align="right" class="blue12">
									图片：
								</td>								
								<td align="left" class="f66">
									<!--   <input type="file" name="pic" id="FileUp" size="26" onchange="checkData()"> -->
									<!-- <s:file label="浏览..." theme="simple" name="paramMap.companyImg"/>-->
									<img id="img" src="../images/NoImg.GIF" style="width: 100px;height: 100px;"/>
								     <s:hidden id="companyImg" name="paramMap.companyImg" />
									 <input id="btnUpImg" value="浏览..." type="button"/>
									<span class="require-field">*<s:fielderror fieldName="paramMap['companyImg']"></s:fielderror></span>
								</td>
							</tr>
							<tr>
								<td style="height: 25px;" align="right" class="blue12">
									网站地址：
								</td>
								<td align="left" class="f66">
									<s:textfield id="tr_content" name="paramMap.companyURL" 
											cssClass="in_text_2" cssStyle="width: 150px" theme="simple"></s:textfield>
									<span class="require-field">*<s:fielderror fieldName="paramMap['companyURL']"></s:fielderror></span>
								</td>
							</tr>
							<tr>
								<td style="width: 100px; height: 25px;" align="right"
									class="blue12">
									序号：
								</td>
								<td align="left" class="f66">
									<s:textfield id="tb_id" name="paramMap.serialCount" readonly="true"
										cssClass="in_text_2" cssStyle="width: 150px" theme="simple"></s:textfield>
									<span class="require-field"><s:fielderror fieldName="paramMap['serialCount']"></s:fielderror></span>
								</td>
							</tr>
							<tr>
								<td style="width: 100px; height: 25px;" align="right"
									class="blue12">
									发布时间：
								</td>
								<td align="left" class="f66">
									<s:textfield name="paramMap.publishTime" cssClass="in_text_2" cssStyle="width: 150px" theme="simple"  onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:'readOnly'})" ></s:textfield>
									<!-- <s:date name="paramMap.publishTime" format="dd/MM/yyyy" /> -->
									<span class="require-field">*<s:fielderror fieldName="paramMap['publishTime']"></s:fielderror></span>
								</td>
							</tr>
							<tr>
								<td height="36" align="right" class="blue12">
									<s:hidden name="action"></s:hidden>
									&nbsp;
								</td>
								<td>
									<button id="btn_save" 
										style="background-image: url('../images/admin/btn_queding.jpg'); width: 70px; border: 0; height: 26px"></button>
									&nbsp;
									<button type="reset"
										style="background-image: url('../images/admin/btn_chongtian.jpg'); width: 70px; height: 26px; border: 0px"></button>
									&nbsp; &nbsp;
									<span class="require-field"><s:fielderror fieldName="actionMsg" theme="simple"></s:fielderror></span>
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
