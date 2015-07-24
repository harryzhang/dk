<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<html>
	<head>
		<title>资料下载-内容维护</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="This is my page" />
		<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
		<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
		<script type="text/javascript" src="script/nav-jk.js"></script>
		<script type="text/javascript">
			$(function(){
				
				param["pageBean.pageNum"] = 1;
				initListInfo(param);
				$("#bt_search").click(function(){
		     		var dir = getDirNum();
				var json = "{'fileType':'JPG,BMP,GIF,TIF,PNG','fileSource':'user/"+dir+"','fileLimitSize':0.5,'title':'上传图片','cfn':'uploadCall','cp':'img'}";
				json = encodeURIComponent(json);
				window.showModalDialog("uploadFileAction.do?obj="+json,window,"dialogWidth=500px;dialogHeight=400px");
				var headImgPath = $("#img").attr("value");
				if(headImgPath!=""){
				//alert(headImgPath);
				var param1 = {};
				param["pageBean.pageNum"] = 1;
				param1["paramMap.titleName"] = headImgPath;
					$.post("addSysImage.do",param1,function(data){
					if(data==0){
					alert("操作失败");
					}else if(data==1){
					alert("操作成功");
					window.location.reload();
					}
					});
				}
					initListInfo(param);
				});
				
					
			});
			
			function initListInfo(praData){
				praData["paramMap.title"] = $("#titleName").val();
			    
		 		$.post("queryImageInfo.do",praData,initCallBack);
		 	}
		 	
		 	function initCallBack(data){
		 	  
				$("#dataInfo").html(data);
			}
			
			
		 	function del(id){
		 		if(!window.confirm("确定要删除吗?")){
		 			return;
		 		}
		 		
	 			window.location.href= "deleteDownload.do?id="+id;
		 	}
		 	
		 	function checkAll(e){
		   		if(e.checked){
		   			$(".downloadId").attr("checked","checked");
		   		}else{
		   			$(".downloadId").removeAttr("checked");
		   		}
   			}
		 	
		</script>
	</head>
	<body>
		<div id="right">
			<div style="padding: 15px 10px 0px 10px;">
				<div>
					<table border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="100" height="28" class="xxk_all_a">
								<a href="linkageinit.do">借款目的</a>
							</td>
							<td width="2">
								&nbsp;
							</td>
							<td width="100"   class="xxk_all_a">
								<a href="queryDeadlineIndex.do">借款期限</a>
							</td>
							<td width="2">
								&nbsp;
							</td>
							<td width="100"   class="xxk_all_a">
								<a href="queryMomeyIndex.do">金额范围</a>
							</td>
							<td width="2">
								&nbsp;
							</td>
							<td width="100"   class="xxk_all_a">
								<a href="queryAcountIndex.do">担保机构</a>
							</td>
							<td width="2">
								&nbsp;
							</td>
								<td width="100"   class="xxk_all_a">
								<a href="queryInversIndex.do">反担保方式</a>
							</td>
							<td width="2">
								&nbsp;
							</td>
							<td width="100"   class="main_alll_h2">
								<a href="queryImageIndex.do">系统头像设置</a>
							</td>
							<td>
								&nbsp;
							</td>
						</tr>
					</table>
					<div
						style="padding-right: 10px; padding-left: 10px; padding-bottom: 10px; width: 1120px; padding-top: 10px; background-color: #fff;">
						<table style="margin-bottom: 8px;" cellspacing="0" cellpadding="0"
							width="100%" border="0">
							<tbody>
								<tr>
									<td class="f66" align="left" width="50%" height="36px">
									     <input type="hidden" value="" id="img">
										<!-- <input id="titleName" name="paramMap.title" type="text"/> -->
										<input id="bt_search" type="button" value="添加"  />
									</td>
								</tr>
							</tbody>
						</table>
						<span id="dataInfo"> </span>
					</div>
				</div>
			</div>
			<script>
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
					$("#img").attr("value",path);
					//$("#setImg").attr("src",path);
		  		}
			}
			</script>
	</body>
</html>
