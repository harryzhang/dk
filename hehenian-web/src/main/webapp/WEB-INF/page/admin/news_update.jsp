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
		<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" href="../css/jbox/Gray/jbox.css" type="text/css"></link>
		<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
		<link rel="stylesheet" href="../kindeditor/themes/default/default.css" />
		<link rel="stylesheet" href="../kindeditor/plugins/code/prettify.css" />
		<script charset="utf-8" src="../kindeditor/plugins/code/prettify.js"></script>
		<script charset="utf-8" src="../kindeditor/kindeditor.js"></script>
		<script charset="utf-8" src="../kindeditor/lang/zh_CN.js"></script>
		<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
		<script type="text/javascript" src="../common/dialog/popup.js"></script>
		<script language="javascript" type="text/javascript" src="../My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript" src="../css/popom.js"></script>
	<script type="text/javascript" src="../css/admin/popom.js"></script>

		
		<script language="javascript" type="text/javascript"
			src="../script/jbox/jquery.jBox-2.3.min.js"></script>
		<script>
		var num = 0;
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
		      
		      //add by houli 判断序列号填写的是否唯一
		     $("#tb_sort").blur(function(){
		        var val = this.value;
		        
		        param['paramMap.sortId'] = val;
		        param['paramMap.originalId'] = $("#sort_").val();
		        $.shovePost("isExistToUpdate.do",param,function(data){
		            if(data == 1){
		            	$("#sortId").html("该序列号已存在，请更换");
		            	num=1;
		            }else{
		              $("#sortId").html("");
		            }
		        });
		     });
		     
		      //提交表单
				$("#btn_save").click(function(){
					if($("#sortId").html() == ""){//modify by houli 如果序列号填写错误，不进行提交
						$(this).hide();
						$("#tr_content").val(editor_content.html());
						$("#updateTeam").submit();
						
						return false;
					}
				});
		 		//单击预览
			$("#previewButton").click(function(){
			   $(this).hide();
				var title = $("#tb_title").val(); 
				var sort=$("#tb_sort").val();
				var visist=$("#tb_visist").val();
				var time=$("#tb_Time").val();
				$("#tr_content").val(editor_content.html());
				var content=$("#tr_content").val();
				var url="PreviewNews.do?title="+title+"&sort="+sort+"&content="+content+"&publishTime="+time+"&visits="+visist;
				param["paramMap.title"]=title;
				param["paramMap.id"]=sort;
				param["paramMap.visist"]=visist;
				param["paramMap.time"]=time;
				param["paramMap.tr_content"]=tr_content;
				select_UserInfo(url);
				$(this).show();
				return false;
			});
		  });
		  function submitData() {
			$("#tr_content").val(editor_content.html());
		
			param["paramMap.title"]=$("#tb_title").val();
			param["paramMap.id"]=$("#id").val();
			param["paramMap.sort"]=$("#tb_sort").val();
			param["paramMap.content"]=$("#tr_content").val();
			param["paramMap.visits"]=$("#tb_visist").val();
			if(num==1){
				return false;
			}
			$.shovePost("updateNews.do", param, function(data) {
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
	<form action="">
			<s:hidden id="id" name="paramMap.id" />
			<div id="right">
				<div style="padding: 15px 10px 0px 10px;">
					<div style="width: auto; background-color: #FFF; padding: 10px;">
												<table width="100%" border="0" cellspacing="1" cellpadding="3">
							<tr>
								<td style="width: 100px; height: 25px;" align="right"
									class="blue12">
									公告标题：
								</td>
								<td align="left" class="f66">
									<s:textfield id="tb_title" name="paramMap.title"
										cssClass="in_text_250" theme="simple" value="%{#request.paramMap.title}"></s:textfield>
									<span class="require-field">*<s:fielderror fieldName="paramMap['title']"></s:fielderror></span>
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
								<td style="width: 100px; height: 25px;" align="right"
									class="blue12">
									序号：
								</td>
								<td align="left" class="f66">
								   <!--<s:hidden id="sort_" name="paramMap.id" />-->
									<s:textfield id="tb_sort" name="paramMap.sort"
										cssClass="in_text_150" theme="simple" ></s:textfield>
									<span id="sortId" class="require-field"><s:fielderror fieldName="paramMap['sort']"></s:fielderror></span>
								</td>
							</tr>
					
					        <tr>
							  <td style="width: 100px; height: 25px;" align="right"
									class="blue12">
									浏览次数：
								</td>
								<td align="left" class="f66">
									<s:textfield id="tb_visist" name="paramMap.visits"
										cssClass="in_text_150" theme="simple" ></s:textfield>
									<span class="require-field">&nbsp; &nbsp;<s:fielderror fieldName="paramMap['visits']"></s:fielderror></span>
								</td>
							</tr>
							
							<tr>
								<td style="width: 100px; height: 25px;" align="right"
									class="blue12">
									发布时间：
								</td>
								<td align="left" class="f66">
								    <input id="tb_Time" class="in_text_150" type="text"  value="${paramMap.publishTime}" name="paramMap.publishTime"  onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:'readOnly'})"/>
									
									
									<span class="require-field">&nbsp; &nbsp;<s:fielderror fieldName="paramMap['publishTime']"></s:fielderror></span>
									
								</td>
							</tr>
							
							
							<tr>
								<td height="36" align="right" class="blue12">
									<s:hidden name="action"></s:hidden>
									&nbsp;
								</td>
								<td>
									<input id="btn_save" type="button"
										style="background-image: url('../images/admin/btn_queding.jpg'); width: 70px; border: 0; height: 26px" onclick="submitData();"></input>
									&nbsp;
<%--									<button id="previewButton" style="background-image: url('../images/admin/btn_yulan.jpg');width: 70px;border: 0;height: 26px" ></button>--%>
                                   <span id="messageInfo" class="blue12"></span>
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
