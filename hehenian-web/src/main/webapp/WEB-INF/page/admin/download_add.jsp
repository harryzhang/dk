<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<title>资料下载-添加</title>
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
				editor_content = K.create('textarea[name="paramMap.content"]', {
					cssPath : '../kindeditor/plugins/code/prettify.css',
					uploadJson : 'kindEditorUpload.do',
					fileManagerJson : 'kindEditorManager.do',
					allowFileManager : true
				});
			});
			
			$(function(){
				//提交表单
				$("#btn_save").click(function(){
					$(this).hide();
					$("#tr_content").val(editor_content.html());
					$("#addDownload").submit();
					return false;
				});
				
				$("#typeId").change(function(){
					var typeId = $("#typeId").val();
					if(typeId >= 0){
						tspecialtySelectInit(typeId);
						downloadTypeSelectInit(typeId);
					} else {
						$("#specialtyId").html("<option value='-1'>--请选择--</option>");
						$("#subjectId").html("<option value='-1'>--请选择--</option>");
					}
				});
				
				$("#specialtyId").change(function(){
					var specialtyId = $("#specialtyId").val();
					if(specialtyId >= 0){
						subjectSelectInit(specialtyId);
					} else {
						$("#subjectId").html("<option value='-1'>--请选择--</option>");
					}
				});
				
				$("#bt_down").click(function(){
					
				
					var fileType = "JPG,BMP,GIF,TIF,PNG,DOC,DOCX,WPS,PPT,PPTX,DPS,XLS,XLSX,ET,TXT,PDF,RAR,ZIP";//默认为1，那么1就不需要进行判断了
					
					var dir = getDirNum();
					var json = "{'fileType':'"+fileType+"','fileSource':'download/"+dir+"','fileLimitSize':10,'title':'上传文件','cfn':'uploadCall','cp':'img'}";
					json = encodeURIComponent(json);
					window.showModalDialog("uploadFileAction.do?obj="+json,window,"dialogWidth=500px;dialogHeight=400px");
				});
				
			});
			
			function changeDownloadType(downloadTypeId){
				$("#downloadType").val(downloadTypeId);
			}
			
			function downloadTypeSelectInit(typeId){
				var _array = [];
				_array.push("<option onclick='changeDownloadType(-1)'  value='-1'>--请选择--</option>");
				var param = {};
				param["typeId"] = typeId;
				$.post("ajaxDownloadTypeByTypeId.do",param,function(data){
					for(var i = 0; i < data.length; i ++){
						var name = '';
						if(data[i].downloadType == 1){
							name = '讲义下载';
						} else if(data[i].downloadType == 2){
							name = '电子书下载';
						} else if(data[i].downloadType == 3){
							name = '试卷下载';
						}
						_array.push("<option class='downloadType' onclick='changeDownloadType("+data[i].downloadType+")' value='"+data[i].downloadType+"'>"+name+"</option>");
					}
					$("#downloadTypeId").html(_array.join(""));
				});
			}
			
			function tspecialtySelectInit(typeId){
				var _array = [];
				_array.push("<option value='-1'>--请选择--</option>");
				var param = {};
				param["typeId"] = typeId;
				$.post("ajaxSpecialtyByTypeId.do",param,function(data){
					for(var i = 0; i < data.length; i ++){
						_array.push("<option value='"+data[i].id+"'>"+data[i].name+"</option>");
					}
					$("#specialtyId").html(_array.join(""));
				});
			}
			
			function subjectSelectInit(specialtyId){
				var _array = [];
				_array.push("<option value='-1'>--请选择--</option>");
				var param = {};
				param["specialtyId"] = specialtyId;
				$.post("ajaxSubjectBySpecialtyId.do",param,function(data){
					for(var i = 0; i < data.length; i ++){
						_array.push("<option value='"+data[i].id+"'>"+data[i].subjectName+"</option>");
					}
					$("#subjectId").html(_array.join(""));
				});
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
			
			function uploadCall(basepath,fileName,cp){
		  		if(cp == "img"){
		  			var path = "upload/"+basepath+"/"+fileName;
		  			$("#downloadPath").val(path);
		  			$("#sp_down").html("*文件已上传");
		  		}
			}
			
		</script>
		
	</head>
	<body>
		<form id="addDownload" name="addDownload" action="adddownload.do" method="post">
			<div id="right">
				<div style="padding: 15px 10px 0px 10px;">
					<div>
						<table border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td height="28"  class="xxk_all_a">
									<a href="downloadlistinit.do">资料列表</a>
								</td>
								<td width="2">
									&nbsp;
								</td>
								<td width="100" class="main_alll_h2">
									<a href="adddownloadinit.do">添加资料</a>
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
									标题：
								</td>
								<td align="left" class="f66">
									<s:textfield id="tb_title" name="paramMap.title"
										cssClass="in_text_250" theme="simple"></s:textfield>
									<span class="require-field">*<s:fielderror fieldName="paramMap['title']"></s:fielderror></span>
								</td>
							</tr>
							<tr>
								<td style="width: 100px; height: 25px;" align="right"
									class="blue12">
									浏览次数：
								</td>
								<td align="left" class="f66">
									<s:textfield id="tb_browseCount" name="paramMap.visits"
										cssClass="in_text_150" theme="simple"></s:textfield>
									<span class="require-field"><s:fielderror fieldName="paramMap['visits']"></s:fielderror></span>
								</td>
							</tr>
							
							<tr>
								<td style="width: 100px; height: 25px;" align="right"
									class="blue12">
									序号：
								</td>
								<td align="left" class="f66">
									<s:textfield id="tb_price" name="paramMap.seqNum"
										cssClass="in_text_150" theme="simple"></s:textfield>
									<span class="require-field">&nbsp; &nbsp;<s:fielderror fieldName="paramMap['seqNum']"></s:fielderror></span>
								</td>
							</tr>
					
							<tr>
								<td style="height: 25px;" align="right" class="blue12">
									是否显示
								</td>
								<td align="left" class="f66">
									<s:radio list="#{1:'显示',2:'不显示'}"  name="paramMap.state" />
									<span class="require-field">*<s:fielderror fieldName="paramMap['state']"></s:fielderror></span>
								</td>
							</tr>
							<tr>
								<td style="width: 100px; height: 25px;" align="right"
									class="blue12">
									发布时间：
								</td>
								<td align="left" class="f66">
								    <input class="in_text_150" type="text"  value="${paramMap.publishTime}" name="paramMap.publishTime"  onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:'readOnly'})"/>
									
									
									<span class="require-field">&nbsp; &nbsp;<s:fielderror fieldName="paramMap['publishTime']"></s:fielderror></span>
									
								</td>
							</tr>
							<tr>
								<td style="height: 25px;" align="right" class="blue12">
									内容：
								</td>
								<td align="left" class="f66">
									<textarea id="tr_content" name="paramMap.content" rows="20" class="textareash"
											style="width: 670px; padding:5px;">
										<s:property value="paramMap.content"/>
									</textarea>
									<span class="require-field"><s:fielderror fieldName="paramMap['content']"></s:fielderror></span>
								</td>
							</tr>
							<tr>
							<td style="height: 25px;" align="right" class="blue12">资料文件：</td>
								<td>
									<input id="downloadPath" name="paramMap.attachment" type="text" value="${paramMap.attachment }" style="width: 350px"/>
									<input id="bt_down" value="上传附件" type="button"  />
									<span class="require-field" id="sp_down"><s:fielderror fieldName="paramMap['attachment']">*</s:fielderror></span>
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
