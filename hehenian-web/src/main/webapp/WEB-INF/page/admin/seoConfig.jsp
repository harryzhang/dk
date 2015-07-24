<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<title>SEO设置</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="This is my page" />
		<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
		
		<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
		<link rel="stylesheet" href="../../../kindeditor/themes/default/default.css" />
		<link rel="stylesheet" href="../kindeditor/plugins/code/prettify.css" />
		<script charset="utf-8" src="../kindeditor/kindeditor.js"></script>
		<script charset="utf-8" src="../kindeditor/lang/zh_CN.js"></script>
		<script charset="utf-8" src="../kindeditor/plugins/code/prettify.js"></script>
		<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
		<script type="text/javascript" src="../common/dialog/popup.js"></script>
		
		<script>
		   $(function(){
		     $("#friendAward").click(function(){
		       
		        $.post("showCostType.do","type=3",function(data){
		             $("#showcontent").html("");
		            $("#feind").attr("bgcolor","#CC0000");
		            $("#showcontent").html(data);	        
		        });
		      
		     });
		    
		   });
	 
	
	</script>	

		
	</head>
	<body>
			<div id="right">
				<div style="padding: 15px 10px 0px 10px;">
					<div>
						<table width="10%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td  width="8%" height="28"  class="main_alll_h2">
									<a href="querySEOConfig.do">SEO设置</a>
								</td>
							</tr>
						</table>
					</div>
					 
					<div id="showcontent" style="width: auto; background-color: #FFF; padding: 10px;">
						<!-- 内容显示位置 -->
						
						
						<table width="80%" border="0" cellspacing="1" cellpadding="3" align="center">
							<tr style="height: 20px;"></tr>
							<tr>
								<td style="width: 400px; height: 25px;"
									class="blue12">
									<span style="font-weight: bold">标题附加字: </span><br/>
网页标题通常是搜索引擎关注的重点，本附加字设置将出现在标题中论坛名称的后面，如果有多个关键字，建议用 "|"、","(不含引号) 等符号分隔 
								</td>
								<td align="left" class="f66">
									<s:textfield id="title" name="paramMap.title"
										 theme="simple" value="%{#request.seoMap.title}"></s:textfield>
									<span class="require-field" id="e_host"></span>
								</td>
							</tr>	
								<tr>
								<td style="width: 400px; height: 25px;"
									class="blue12">
									<span style="font-weight: bold">Meta Keywords: </span><br />
Keywords 项出现在页面头部的 Meta 标签中，用于记录本页面的关键字，多个关键字间请用半角逗号 "," 隔开
								</td>
								<td align="left" class="f66">
									<s:textfield id="keywords" name="paramMap.keywords"
										 theme="simple" value="%{#request.seoMap.keywords}"></s:textfield>
									<span class="require-field" id="e_port"></span>
								</td>
							</tr>	
							<tr>
								<td style="width: 400px; height: 25px;"
									class="blue12">
									<span style="font-weight: bold">Meta Description:  </span><br />
Description 出现在页面头部的 Meta 标签中，用于记录本页面的概要与描述
								</td>
								<td align="left" class="f66">
									<s:textfield id="description" name="paramMap.description"
										 theme="simple" value="%{#request.seoMap.description}"></s:textfield>
									<span class="require-field" id="e_description"></span>
								</td>
							</tr>
							<tr>
								<td style="width: 400px; height: 25px;"
									class="blue12">
									<span style="font-weight: bold">其它头部信息: </span><br />
如需在 &lt;head&gt;&lt;/head&gt; 中添加其它的 HTML 代码，可以使用本设置，否则请留空
								</td>
								<td align="left" class="f66">
									<s:textarea id="otherTags" name="paramMap.otherTags"
										 cols="30" rows="8" value="%{#request.seoMap.otherTags}"></s:textarea>
									<span class="require-field" id="e_otherTags"></span>
								</td>
							</tr>	
				<!--  			<tr>
								<td style="width: 400px; height: 25px;"
									class="blue12">
									<span style="font-weight: bold">启用百度SiteMap:  </span><br/>
启用百度SiteMap会增加或者加快百度搜索对您网站的收录。
								</td>								
								<td align="left" class="f66">
								<s:if test='#request.seoMap.siteMap == 1'>
									 是：<input type="radio" name="paramMap_siteMap" value="1" id="siteMap"
								 	checked="checked" />&nbsp;&nbsp;&nbsp;&nbsp;
									 否：<input type="radio" name="paramMap_siteMap" value="0" id="siteMap"/></s:if>
									 <s:else>
									 是：<input type="radio" name="paramMap_siteMap" value="1" id="siteMap"/>
									 &nbsp;&nbsp;&nbsp;&nbsp;
									 否：<input type="radio" name="paramMap_siteMap" value="0" 
									 	checked="checked"  id="siteMap"/>
									 </s:else>
									<span class="require-field" id="e_siteMap"></span>
								</td>
							</tr>-->
							<tr>
								<td height="36" align="right" class="blue12">
									<s:hidden name="action"></s:hidden>
									&nbsp;
								</td>
								<td>
									
									<button id="btn_tijiao"
										style="background-image: url('../images/admin/btn_queding.jpg'); width: 70px; height: 26px; border: 0px"></button>
									&nbsp; &nbsp;
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
			<script>
		$(function(){
		  	 
	  	 $('#btn_tijiao').click(function(){
		/*		  	 if($("#title").val()==""){
			  		$("#e_host").html("标题不能为空!");
					return false;
				  	}else{
				  		$("#e_host").html("*");
					  }
			  	 if($("#keywords").val()==""){
				  		$("#e_port").html("关键字不能为空!");
						return false;
					  	}
						 else
						{
							 $("#e_port").html("*");
						}
			  	 if($("#description").val()==""){
				  		$("#e_description").html("描述不能为空!");
						return false;
					  	}else{
					  		$("#e_description").html("*");
					  }*/
		   if (!confirm("你确认更改SEO参数设置吗?")){
				return false;
		  }
		   var param ={};
		  param["paramMap.title"] = $("#title").val();
		  param["paramMap.keywords"] = $("#keywords").val();
		  param["paramMap.description"] = $("#description").val();
		/*  param["paramMap.siteMap"] = $("input[name='paramMap_siteMap']:checked").val();*/
		  param["paramMap.otherTags"] = $("#otherTags").val();		 
          $.post("updateSEOConfig.do",param,function(data){
               var callBack = data.msg;  
                if(callBack == undefined || callBack == ''){
                 $('#showcontent').html(data);
                }else{
                  if(callBack == 1){
		          alert("操作成功!");
		          window.location.reload();
		          return false;
		          }
                }    
          });
		  	 

          });
		
	    
		});
		</script>
	</body>
</html>
