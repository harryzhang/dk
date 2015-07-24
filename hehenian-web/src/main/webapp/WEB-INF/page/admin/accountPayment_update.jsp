<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
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
		<script language="javascript" type="text/javascript" src="../My97DatePicker/WdatePicker.js"></script>
		
		<script>
			var editor_content;
			KindEditor.ready(function(K) {
				editor_content = K.create('textarea[name="paramMap.description"]', {
					cssPath : '../kindeditor/plugins/code/prettify.css',
					uploadJson : 'kindEditorUpload.do',
					fileManagerJson : '../kindEditorManager.do',
					allowFileManager : true
				});
			});
			
			 $(function(){
		      
		
		     
		      //提交表单
				$("#btn_save").click(function(){
					$("#tr_description").val(editor_content.html());
						$("#accountPay").submit();
				});
				
				//上传图片
				$("#btnUpImg").click(function(){
					var dir = getDirNum();
					var json = "{'fileType':'JPG,BMP,GIF,TIF,PNG','fileSource':'team/"+dir+"','fileLimitSize':0.5,'title':'上传图片','cfn':'uploadCall','cp':'img'}";
					json = encodeURIComponent(json);
					window.showModalDialog("uploadFileAction.do?obj="+json,window,"dialogWidth=500px;dialogHeight=400px");
				});
				
				if("${paramMap.imgPath}" != ""){
					$("#img").attr("src","../${paramMap.imgPath}");
				}
		  });
		   	function uploadCall(basepath,fileName,cp){
		  		if(cp == "img"){
		  			var path = "upload/"+basepath+"/"+fileName;
		  			$("#imgPath").val(path);
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
	<s:hidden id="sort_" name="paramMap.sort"></s:hidden>
		<form id="accountPay" name="accountPay" action="updateAccountPay.do" method="post">
			
			<s:hidden id="id" name="paramMap.id" />
			<div id="right">
				<div style="padding: 15px 10px 0px 10px;">
					<div>
						<table border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="100" height="28"  class="xxk_all_a">
									<a href="queryAccountPayInit.do">支付管理</a>
								</td>
								<td width="2">
									&nbsp;
								</td>
								<td width="100" align="center"  class="main_alll_h2">
									<a href="updateAccountPayInit.do?nid=${paramMap.nid }">编辑</a>
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
									银行名称：
								</td>
								<td align="left" class="f66">
									<s:textfield id="tb_title" name="paramMap.name"
										cssClass="in_text_250" theme="simple"></s:textfield>
									<span class="require-field">*<s:fielderror fieldName="paramMap['name']"></s:fielderror></span>
								</td>
							</tr>
							
							<tr>
								<td style="width: 100px; height: 25px;" align="right"
									class="blue12">
									缩略图：
								</td>
								<td align="left" class="f66">
									<img id="img" src="../${paramMap.litpic }" style="width: 200px;height: 100px;"/>
									<s:hidden id="imgPath" name="paramMap.litpic" />
									<input id="btnUpImg" value="上传" type="button"/>
									<span class="require-field"><s:fielderror fieldName="paramMap['litpic']"></s:fielderror></span>
								</td>
							</tr>
							<s:if test="%{paramMap.nid eq 'gopay'}">
							<tr>
								<td style="width: 100px; height: 25px;" align="right"
									class="blue12">
									商户ID：
								</td>
								<td align="left" class="f66">
									<s:textfield id="tb_title" name="paramMap.merchantID"
										cssClass="in_text_250" theme="simple"></s:textfield>
									<span class="require-field">*<s:fielderror fieldName="paramMap['merchantID']"></s:fielderror></span>
								</td>
							</tr>
							<tr>
								<td style="width: 100px; height: 25px;" align="right"
									class="blue12">
									国付宝账号：
								</td>
								<td align="left" class="f66">
									<s:textfield id="tb_title" name="paramMap.virCardNoIn"
										cssClass="in_text_250" theme="simple"></s:textfield>
									<span class="require-field">*<s:fielderror fieldName="paramMap['virCardNoIn']"></s:fielderror></span>
								</td>
							</tr>
							<tr>
								<td style="width: 100px; height: 25px;" align="right"
									class="blue12">
									识别码：
								</td>
								<td align="left" class="f66">
									<s:textfield id="tb_title" name="paramMap.VerficationCode"
										cssClass="in_text_250" theme="simple"></s:textfield>
									<span class="require-field">*<s:fielderror fieldName="paramMap['VerficationCode']"></s:fielderror></span>
								</td>
							</tr>
							</s:if>
							<s:if test="%{paramMap.nid eq 'IPS'}">
							<tr>
							<td style="width: 100px; height: 25px;" align="right"
									class="blue12">
									客户号：
								</td>
								<td align="left" class="f66">
									<s:textfield id="tb_title" name="paramMap.customerID"
										cssClass="in_text_250" theme="simple"></s:textfield>
									<span class="require-field">*<s:fielderror fieldName="paramMap['merchantID']"></s:fielderror></span>
								</td>
							</tr>
							<tr>
								<td style="width: 100px; height: 25px;" align="right"
									class="blue12">
									私钥：
								</td>
								<td align="left" class="f66">
									<s:textfield id="tb_title" name="paramMap.privatekey"
										cssClass="in_text_250" theme="simple"></s:textfield>
									<span class="require-field">*<s:fielderror fieldName="paramMap['virCardNoIn']"></s:fielderror></span>
								</td>
							</tr>
							</s:if>
							<tr>
								<td style="width: 100px; height: 25px;" align="right"
									class="blue12">
									排序：
								</td>
								<td align="left" class="f66">
									<s:textfield id="tb_title" name="paramMap.orders"
										cssClass="in_text_250" theme="simple"></s:textfield>
									<span class="require-field">*<s:fielderror fieldName="paramMap['order']"></s:fielderror></span>
								</td>
							</tr>
							<tr>
								<td style="height: 25px;" align="right" class="blue12">
									银行明细：
								</td>
								<td align="left" class="f66">
									<textarea id="tr_description" name="paramMap.description" rows="20" class="textareash"
											style="width: 670px; padding:5px;">
										<s:property value="paramMap.description" />
									</textarea>
									<span class="require-field"><s:fielderror fieldName="paramMap['description']"></s:fielderror></span>
								</td>
							</tr>
							
							<tr>
								<td height="36" align="right" class="blue12">
									<s:hidden name="paramMap.status"></s:hidden>
									<s:hidden name="paramMap.nid"></s:hidden>
									&nbsp;
									<input id="btn_save" type="button"
										style="background-image: url('../images/admin/btn_queding.jpg'); width: 70px; border: 0px; height: 26px"></input>
								</td>
								<td>
									&nbsp;
									<button type="reset"
										style="background-image: url('../images/admin/btn_chongtian.jpg'); width: 70px; height: 26px; border: 0px"></button>
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
						<br />
					</div>
				</div>
			</div>
		</form>
	</body>
</html>
