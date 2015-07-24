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
		<link href="../css/admin/admin_css.css" rel="stylesheet"
			type="text/css" />

		<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
		<link rel="stylesheet" href="../kindeditor/themes/default/default.css" />
		<link rel="stylesheet" href="../kindeditor/plugins/code/prettify.css" />
		<script charset="utf-8" src="../kindeditor/kindeditor.js"></script>
		<script charset="utf-8" src="../kindeditor/lang/zh_CN.js"></script>
		<script charset="utf-8" src="../kindeditor/plugins/code/prettify.js"></script>
		<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
		<script type="text/javascript" src="../common/dialog/popup.js"></script>
		<script language="javascript" type="text/javascript"
			src="../My97DatePicker/WdatePicker.js"></script>

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
		      
		      //add by houli 判断序列号填写的是否唯一
		     $("#tb_sort").blur(function(){
		        var val = this.value;
		        //alert("sortId1 = "+val);
		        $.shovePost("isExistSortId.do",{sort:val},function(data){
		            if(data == 1){
		            	$("#sortId").html("该序列号已存在，请更换");
		            }else{
		              $("#sortId").html("");
		            }
		        });
		     });
		     
		     
		      //提交表单
				$("#btn_save").click(function(){
				  // alert("sortId = "+$("#sortId").text());
					if($("#sortId").html() == ""){//modify by houli 如果序列号填写错误，不进行提交
						$(this).hide();
						$("#tr_content").val(editor_content.html());
						$("#addTeam").submit();
						return false;
					}	
				});
				
				
					//单击预览
			$("#previewButton").click(function(){
			   $(this).hide();
			   $("#tr_content").val(editor_content.html());
				var title=$("#tb_title").val();
				
				var content=$("#tr_content").val();
				
				var sort=$("#tb_sort").val();
				var visist=$("#tb_visist").val();
				var time=$("#tb_Time").val();
				
				var url="PreviewNews.do?title="+title+"&sort="+sort+"&content="+content+"&publishTime="+time+"&visits="+visist;
				ShowIframe("网站公告信息", url, 800, 600);
				$(this).show();
				return false;
			});
		  });
		   
	    
		
			
		</script>

	</head>
	<body>
		<form id="addTeam" name="addTeam" action="addNews.do" method="post">
			<div id="right">
				<div style="padding: 15px 10px 0px 10px;">
					<div>
						<table border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="100" height="28" class="xxk_all_a">
									<a href="queryNewsListInit.do">网站公告管理</a>
								</td>
								<td width="2">
									&nbsp;
								</td>
								<td width="100" class="main_alll_h2">
									<a href="addNewsInit.do">添加网站公告</a>
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
									<span class="require-field">*<s:fielderror
											fieldName="paramMap['title']"></s:fielderror> </span>
								</td>
							</tr>
							<tr>
								<td style="height: 25px;" align="right" class="blue12">
									类型：
								</td>
								<td align="left" class="f66">
									<select name="paramMap.type">
										<option value="-请选择-" selected="">
											-请选择-
										</option>
										<option value="1">
											网站公告
										</option>
										<option value="2">
											团队介绍
										</option>
										<option value="3">
											条款协议
										</option>
										<option value="4">
											公司简介
										</option>
										<option value="5">
											平台原理
										</option>
										<option value="6">
											资费说明
										</option>
										<option value="7">
											关于我们
										</option>
										<option value="8">
											法律政策
										</option>
										<option value="9">
											合作伙伴
										</option>
										<option value="10">
											使用技巧
										</option>
										<option value="11">
											本金保障计划
										</option>
										<option value="12">
											风险备用金账户规则
										</option>
										<option value="13">
											 账号安全
										</option>
										<option value="14">
											隐私保护
										</option>
										<option value="15">
											严格的贷前审核
										</option>
										<option value="16">
											完善的贷后管理
										</option>
										<option value="17">
											交易安全保障
										</option>
										<option value="18">
											借款审核保障
										</option>
									</select>
								</td>
							</tr>
							<tr>
								<td style="height: 25px;" align="right" class="blue12">
									内容：
								</td>
								<td align="left" class="f66">
									<textarea id="tr_content" name="paramMap.content" rows="20"
										class="textareash" style="width: 670px; padding: 5px;">
										<s:property value="paramMap.content" />
									</textarea>
									<span class="require-field"><s:fielderror
											fieldName="paramMap['content']"></s:fielderror> </span>
								</td>
							</tr>
							

							<tr>
								<td style="width: 100px; height: 25px;" align="right"
									class="blue12">
									序号：
								</td>
								<td align="left" class="f66">
									<s:textfield id="tb_sort" name="paramMap.sort"
										cssClass="in_text_150" theme="simple"></s:textfield>
									<span id="sortId" class="require-field"><s:fielderror
											fieldName="paramMap['sort']"></s:fielderror> </span>
								</td>
							</tr>

							<tr>
								<td style="width: 100px; height: 25px;" align="right"
									class="blue12">
									浏览次数：
								</td>
								<td align="left" class="f66">
									<s:textfield id="tb_visist" name="paramMap.visits"
										cssClass="in_text_150" theme="simple"></s:textfield>
									<span class="require-field">&nbsp; &nbsp;<s:fielderror
											fieldName="paramMap['visits']"></s:fielderror> </span>
								</td>
							</tr>


							<tr>
								<td style="width: 100px; height: 25px;" align="right"
									class="blue12">
									发布时间：
								</td>
								<td align="left" class="f66">
									<input id="tb_Time" class="in_text_150" type="text"
										value="${paramMap.publishTime}" name="paramMap.publishTime"
										onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:'readOnly'})" />
									<span class="require-field">&nbsp; &nbsp;<s:fielderror
											fieldName="paramMap['publishTime']"></s:fielderror> </span>

								</td>
							</tr>


							<tr>
								<td height="36" align="right" class="blue12">
									<s:hidden name="action"></s:hidden>
									&nbsp;
								</td>
								<td>
									<input id="btn_save" type="button"
										style="background-image: url('../images/admin/btn_queding.jpg'); width: 70px; border: 0; height: 26px"></input>
									&nbsp;
<%--									<input id="previewButton" type="button"--%>
<%--										style="background-image: url('../images/admin/btn_yulan.jpg'); width: 70px; border: 0; height: 26px"></input>--%>
									<span id="messageInfo" class="blue12"></span> &nbsp; &nbsp;
									<span class="require-field"><s:fielderror
											fieldName="actionMsg" theme="simple"></s:fielderror> </span>
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
