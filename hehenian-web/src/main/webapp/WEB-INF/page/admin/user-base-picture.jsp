<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="../css/css.css" />
<script src="../script/jquery-1.7.1.min.js" type="text/javascript"></script>
<script src="../layer/layer.mintest.js" type="text/javascript"></script>
<script src="../layer/layertest.js" type="text/javascript"></script>
<script>
//删除证件
function del(){
if(!window.confirm("确认删除这张证件吗？")){
return false;
}
var param = {};
param["paramMap.tmdid"] = '${map.tmdid}';//证件id
$.post("delcertificate.do",param,function(data){
window.location.reload();
history.go(-1);
});

}
</script>
		<script>
//控制复选框是否可见
$(function(){
if('${map.visiable}'=='1'){//可见
 $("#visiable").attr("checked",true);
}else{
 $("#visiable").attr("checked",false);
}
});
</script>
		<script>
$(function(){
  
 $("#visiable").click(function(){
 

 if($(this).attr("checked")=='checked'){
    $(this).attr("value","1");
 }
 if($(this).attr("checked")==undefined){
 $(this).attr("value","2");
 }
 
 
 });
  
  
  
  $("#but_yes").click(function(){
     var param = {};
     param["paramMap.valid"] = $("input[name='paramMap_valid']:checked").val();//
     param["paramMap.visiable"] = $("#visiable").val();// 
     param["paramMap.option"]  = $("#paramMap_content").val();
     param["paramMap.userId"] = '${map.id}';
     param["paramMap.tmdid"] = '${map.tmdid}';
     param["paramMap.tmid"] = '${map.tmid}';
     param["paramMap.materAuthTypeId"] = '${map.materAuthTypeId}';
     $.post("Updatematerialsauthindex.do",param,function(data){
         if(data==1){
           $("#u_tip").html("请勾选审核状态");
         }else if(data==0){
          $("#u_tip").html("审核观点不能为空");
         }else if(data==3){
           alert("审核失败");
         }else if(data==2){
           window.location.href="queryPerUserPicturMsginitindex.do?userId=${map.id}&materAuthTypeId=${map.materAuthTypeId}"
         }else if(data==4){
            window.location.href="queryPerUserPicturMsginitindex.do?userId=${map.id}&materAuthTypeId=${map.materAuthTypeId}"
         }
         else{
         alert("审核失败");
         }
         
     });
  });


});


</script>
	</head>
	<body>
	
			<div class="nymain">
				<div class="wdzh">
					<div class="r_main">
						<div class="box" >
							<h2 class="otherh2x" >
								认证图片资料审核
							</h2>
							<div class="boxmain2" style="padding-top: 0px;margin-top:0px; ">
								<div class="biaoge">
									<form>
										<table width="100%" border="0" cellspacing="0" cellpadding="0">
											<tr>
												<div>
													用户名:${map.username }
												</div>
												<hr />
											</tr>
											<tr>
												<div>
													认证种类:${map.tmyname}
												</div>
												<hr />
											</tr>
											<tr>
												<div>
													证件图片
													<a href="../${map.imagePath}" target="_blank" title="查看图片"><img
															src="../${map.imagePath}" width="62" height="62" />
													</a>

												</div>
												<hr />
											</tr>
											<tr>
												<hr />
												<div>

													证件审核
												</div>
											</tr>
											<tr>
												<div>

													文件是否可见：
													<input type="checkbox" name="param_visiable" value="2"
														id="visiable" />
													&nbsp;&nbsp;（选中时候为图片为可见，否则为不可见）

												</div>
											</tr>
											<tr>
												<div>
													审&nbsp;核&nbsp;状&nbsp;态&nbsp;：
													<input type="radio" name="paramMap_valid" value="1"
														onclick="javascript:$('#u_tip').html('')"
														<s:if test='#request.map.auditStatus == 3'>checked="checked"</s:if>
														<s:else></s:else> />
													有效
													<input type="radio" name="paramMap_valid" value="0"
														<s:if test='#request.map.auditStatus == 2'>checked="checked"</s:if>
														<s:else></s:else> />
													无效
												</div>
											</tr>
											<tr>
												<div>
													审&nbsp;核&nbsp;观&nbsp;点&nbsp;：
												</div>
											</tr>
											<tr>
												<div>
													&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;
													&nbsp;&nbsp;&nbsp;&nbsp;
													<s:textarea name="paramMap.content" cols="40" rows="5"
														id="paramMap_content" value="%{#request.map.tmdoption}" />
												</div>
											</tr>
											<tr>
												<br />
												<div align="center">
													<input type="button" value="审核此证件"
														style="background-image: url(../images/sqdk_07.jpg); background-repeat: no-repeat; height: 24px; width: 78px; margin-top: 0; background-position: left center"
														id="but_yes" />
													&nbsp;&nbsp;
													<input type="button" value="删除此证件"
														style="background-image: url(../images/sqdk_07.jpg); background-repeat: no-repeat; height: 24px; width: 78px; margin-top: 0; background-position: left center"
														id="but_del" onclick="del()" />
													<span style="color: red; float: none;" id="u_tip"
														class="formtips"></span>
												</div>
											</tr>

										</table>
									</form>
								</div>
							</div>
						</div>
					</div>
				</div>
		

			<script>
/*
$(function(){
var  ttt = ${map.auditStatus}
  if(ttt == '3' || ttt=='2'){
    $("#paramMap_content").attr("disabled","true");
  }
});
*/
</script>
	</body>
</html>
