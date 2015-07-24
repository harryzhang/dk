<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=utf-8"%>
<%@ include file="/include/taglib.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<base target="_self" />
		<title>${fileCommon.title}</title>
		<style>
body{overflow-x:hidden;width: 98%;}
.clear{ clear:both;}
.main{ margin:0 auto;  width:500px; height:400px ;  color:#2a2926; font-family:"宋体";font-size:14px; overflow-x:hidden;color:#333;   border:1px solid #ccc}
p{ text-align:center; margin:10px}
input{ width:250px; }
img{ border:0px; }
.txt{ float:left; height:31px; text-align:center; padding-top:2px; margin-right:10px; margin-left:10px; margin-top:10px}
.shangc{width:128px; margin-left:180px; margin-top:10px; }
</style>


		<script type="text/javascript" src="${basePath}/script/jquery-1.7.1.min.js"></script>
		<%
			if (request.getAttribute("errorMng") == null) {
				request.setAttribute("errorMng", " ");
			}
		%>
		<script type="text/javascript">
		
			//事件注册
			$(function(){
			    //上传完成后返回的上传调用的页面,取出文件名，路径调用页面有存储，不用传输
				if('${mark}' == 'success'){
				    var fileName = "${fileCommon.fileName}";
				    var fileRemark = "${files.filesRemark}";
				    var parentWin = window.dialogArguments;
				    var basePath = "${fileCommon.fileSource}";
				    if(parentWin==null){  
		        		parentWin  = opener.window;             
		     		}  
					var callBackParamsString = "${fileCommon.callBackParamsString}";
					if(callBackParamsString=="undefined"){
						callBackParamsString = null;
					}
					if(parentWin.showFileParam){
						alert(parentWin.showFileParam);
						parentWin.showFileParam(["${fileCommon.fileName}"] ,callBackParamsString);
					}
					var callBackFunctionName = "${fileCommon.callBackFunctionName}";
					if(callBackFunctionName!="undefined" || parentWin[callBackFunctionName]){
						parentWin[callBackFunctionName](basePath , ["${fileCommon.fileName}"] ,callBackParamsString);
					}
		      		window.close();
				}
		
			    //提交前验证是否选取了文件
				$("#sub").click(function(){
					var f=$("#filepath").val();
					  if(!f){
					  	alert("请选取文件后再上传!");
					  }else{//选取文件完成，检查文件名不存在后开始上传
						$("#main").show();
						$("#div1").hide();
						$("#form1").submit();
						return;
				      }
				});
				$("#main").hide();
			});
		</script>
		<style type="text/css">
			div {
				border: solid 0px red;
			}
		</style>
	</head>

	<body >
	<div class="main">
		<div id="main"  style="text-align: center">
			<div id="contentPart1">
				<img src="../images/load.gif" />
			</div>
			<div id="contentPart2">
				<span style="color: red;">文件正在上传，请您稍等！</span>
			</div>
		</div>
		<div
			style="width: 100%; text-align: center; padding-bottom: 0px; vertical-align: bottom;">
			<div id="div1" style="margin-left: auto; margin-right: auto; padding-bottom: 0px; vertical-align: bottom;">
				<form id="form1" action="uploadFiles.do"  enctype="multipart/form-data" method="post">
					<s:hidden value="%{fileCommon.fileName}" name="fileCommon.fileName" id="f_name"/>
					<s:hidden value="%{fileCommon.fileType}" name="fileCommon.fileType" id="f_type"/>
					<s:hidden value="%{fileCommon.notAllowFileType}" name="fileCommon.notAllowFileType"/>
					<s:hidden value="%{fileCommon.fileSource}" name="fileCommon.fileSource" id="f_source"/>
					<s:hidden value="%{fileCommon.fileLimitSize}" name="fileCommon.fileLimitSize" id="f_limit"/>
					<s:hidden value="%{fileCommon.sizeUnit}" name="fileCommon.sizeUnit"/>
					<s:hidden value="%{fileCommon.fileCount}" name="fileCommon.fileCount" id="f_count"/>
					<s:hidden value="%{fileCommon.fileRemark}" name="fileCommon.fileRemark" id="fileRemark"/>
					<s:hidden value="%{fileCommon.callBackFunctionName}" name="fileCommon.callBackFunctionName"/>
					<s:hidden value="%{fileCommon.callBackParamsString}" name="fileCommon.callBackParamsString"/>
					<table width="100px" border="0">
						<tr>
							<td align="center" colspan="2">
								浏览：<s:file theme="simple" cssStyle="height:30px;"  name="files.files" size="45" id="filepath"></s:file>
							</td>
						</tr>
						<s:if test="#request.fileRemark">
							<tr>
								<td align="center" colspan="2">
									<s:textarea label="文件描述" name="files.filesRemark" cols="45" rows="10" />
								</td>
							</tr>
						</s:if>
						<tr>
							<td align="center" colspan="2">
								<div class="shangc" id="sub"><a><img src="${basePath}/images/shangchuan.png" width="97" height="30" /></a> </div>
							</td>
						</tr>
					</table>
				</form>
			</div>
		</div>
		<table align="center" style="margin-top: 2px;">
			<tr align="center">
				<td>
<p>您上传的文件大小必须小于1M</p>
<p>您可以上传任意类型的文件JPG、BMP、GIF、TIF、PNG类型的文件</p>
			    </td>
			</tr>
		</table>
</div>
	</body>
</html>
