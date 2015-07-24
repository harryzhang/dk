<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<title>借款管理</title>
		<link href="../css/admin/admin_css.css" rel="stylesheet"
			type="text/css" />
		<link href="../css/css.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
		<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
		<script type="text/javascript" src="../common/dialog/popup.js"></script>
		<script language="javascript" type="text/javascript" src="../script/jbox/jquery.jBox-2.3.min.js"></script>
		<script>
		  $(function(){
		      //提交表单
				$("#btn_save").click(function(){
					if($("#tb_title").val()==""){
						$("#title").html("  * 请填写名称");
						return false;
					}else{
						$("#title").html("");
					}
						//$("#updateShove").submit();
						//window.location.href="updateShoveBorrowStyle.do";
				});
				
		  });
		  
		  
		  //修改
	function updateShoveBorrow() {
		var id = $("#id").val();
		var status = $("input[name='status']:checked").val();
		var tb_title = $("#tb_title").val();
		var contents = $("#contents").val();
		param["paramMap.status"] = status;
		param["paramMap.id"] = id;
		param["paramMap.title"] = tb_title;
		param["paramMap.contents"] = contents;
		
		var size = $("input[name='status']:checked").val();
		if($("#tb_title").val()==""){
						$("#title").html("  * 请填写名称");
						return false;
					}else{
						$("#title").html("");
		}
		$.shovePost("updateShoveBorrowStyle.do", param, function(data) {
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
		<form id="updateShove" name="updateShove" action="updateShoveBorrowStyle.do" method="post">
			<div id="right">
			<input type="hidden" name="id" id="id" value="${paramMap.id}"/>
				<div style="padding: 15px 10px 0px 10px;">
					<!--<div>
										<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<  <td width="100" height="28"  class="main_alll_h2">
									<a href="updateShoveBorrowStyleInit.do?id=${paramMap.id}">修改还款方式</a>
								</td>
								
							</tr>
						</table>
						</div>
					-->
					<div style="width: auto; background-color: #FFF; padding: 20px;">
						<table width="800" border="0" cellspacing="1" cellpadding="3"  align="center" >
							<tr>
							<td style="width: 100px; height: 30px;" align="right"
									class="blue12">
									还款方式名称：
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
										cssClass="in_text_250" theme="simple" />
									<span class="require-field" id="title">*<s:fielderror fieldName="title"></s:fielderror></span>
								</td>
							</tr>
							<tr>
								<td style="width: 100px; height: 30px;" align="right"
									class="blue12">
									状态：
								</td>
								<td align="left" class="f66">
									<input type="radio" name="status" id="status1" value="1" 
									   		<s:if test='paramMap.status == 1'>checked="checked"</s:if>
																					<s:else></s:else>
									    />开启 
									 <input type="radio" name="status" id="status" value="2"
									    <s:if test='paramMap.status == 2'>checked="checked"</s:if>
																					<s:else></s:else>
									    />关闭
								</td>
							</tr>
							<tr>
								<td style="height: 35px;" align="right" class="blue12" valign="top">
									算法：
								</td>
								<td align="left" class="f66">
									<s:textarea name="paramMap.contents" cols="40" rows="5" id="contents"/>
									<span class="require-field"><s:fielderror fieldName="paramMap['contents']"></s:fielderror></span>
								</td>
							</tr>
							<tr>
								<td height="36" align="right" class="blue12">
									<s:hidden name="action"></s:hidden>
									<input type="hidden" value="${paramMap.id}" name="paramMap.id" id="paramMap.id" />
									&nbsp;
								</td>
								<td>
									<input id="btn_savess" type="button" onclick="updateShoveBorrow();"
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
					</div>
				</div>
			</div>
		</form>
	</body>
</html>
