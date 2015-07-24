<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<title>借款产品参数</title>
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
		
	<script>
	   $(function(){
	     
	            $.post("showCostType.do","type=3",function(data){
	               $("#showcontent").html(data);	  
	            });
	        
	     
	     //普通用户好友奖励
	     $("#friendAward").click(function(){
	       
	        $.post("showCostType.do","type=3",function(data){
	             $("#feind").attr("bgcolor","#CC0000");
	             if($("#shouxv").attr("bgcolor")=="#CC0000"){
	                   $("#shouxv").attr("bgcolor","#8594A9");
	             }
	             if($("#touzithing").attr("bgcolor")=="#CC0000"){
	                   $("#touzithing").attr("bgcolor","#8594A9");
	             }
	             if($("#vipfei").attr("bgcolor")=="#CC0000"){
	                   $("#vipfei").attr("bgcolor","#8594A9");
	             }
	             if($("#recommend").attr("bgcolor")=="#CC0000"){
	                   $("#recommend").attr("bgcolor","#8594A9");
	             }
	             
	             $("#showcontent").html("");
	           
	            $("#showcontent").html(data);	        
	        });
	      
	     });
	     
	     
	     
	     //投资信息管理
	        
	   /*  $("#investManger").click(function(){
	       
	        $.post("showCostType.do","type=2",function(data){
	             $("#touzithing").attr("bgcolor","#CC0000");
	             if($("#shouxv").attr("bgcolor")=="#CC0000"){
	                   $("#shouxv").attr("bgcolor","#8594A9");
	             }
	             if($("#feind").attr("bgcolor")=="#CC0000"){
	                   $("#feind").attr("bgcolor","#8594A9");
	             }
	             if($("#vipfei").attr("bgcolor")=="#CC0000"){
	                   $("#vipfei").attr("bgcolor","#8594A9");
	             }
	             if($("#recommend").attr("bgcolor")=="#CC0000"){
	                   $("#recommend").attr("bgcolor","#8594A9");
	             }
	             
	             $("#showcontent").html("");
	           
	            $("#showcontent").html(data);	        
	        });
	      
	     });
	     */
	     
	     //VIP会费设置
	       $("#vipmanager").click(function(){
	       
	        $.post("showCostType.do","type=4",function(data){
	             $("#vipfei").attr("bgcolor","#CC0000");
	             if($("#shouxv").attr("bgcolor")=="#CC0000"){
	                   $("#shouxv").attr("bgcolor","#8594A9");
	             }
	             if($("#feind").attr("bgcolor")=="#CC0000"){
	                   $("#feind").attr("bgcolor","#8594A9");
	             }
	             if($("#touzithing").attr("bgcolor")=="#CC0000"){
	                   $("#touzithing").attr("bgcolor","#8594A9");
	             }
	             if($("#recommend").attr("bgcolor")=="#CC0000"){
	                   $("#recommend").attr("bgcolor","#8594A9");
	             }
	             
	             $("#showcontent").html("");
	           
	            $("#showcontent").html(data);	        
	        });
	      
	     });
	     
	     
	     //好友推荐奖励设置
	      /* $("#referral").click(function(){
	       
	        $.post("referralBonusesList.do","",function(data){
	             $("#recommend").attr("bgcolor","#CC0000");
	             if($("#shouxv").attr("bgcolor")=="#CC0000"){
	                   $("#shouxv").attr("bgcolor","#8594A9");
	             }
	             if($("#feind").attr("bgcolor")=="#CC0000"){
	                   $("#feind").attr("bgcolor","#8594A9");
	             }
	             if($("#touzithing").attr("bgcolor")=="#CC0000"){
	                   $("#touzithing").attr("bgcolor","#8594A9");
	             }
	             if($("#vipfei").attr("bgcolor")=="#CC0000"){
	                   $("#vipfei").attr("bgcolor","#8594A9");
	             }
	             
	             $("#showcontent").html("");
	           
	            $("#showcontent").html(data);	        
	        });
	      
	     });
	     */
	     
	     
	     
	     
	     
	     
	     
	     
	     
	     
	     
	     
	     
	     
	     
	     
	     
	     
	    /* 
	        //手续费
	         $("#formalities").click(function(){
	            var number=$("#tb_produce").val();
                if((/^[1-4]{1,2}(\.\d{1,4})?$/.test(number))){
                  $.post("updateCostManager.do","number="+number+"&typeId=1",function(data){
                     if(data==1){
                      alert("修改成功");
                     }else if(data==2){
                     alert("修改失败");
                     }
           
                  });
               }else{
                  alert("请输入正确百分比数字,不能高于50%");
                }
	         });
	       */
	    
	   });
	   
	 
	   
	
	 
	
	</script>	

		
	</head>
	<body>
			<div id="right"
				style="background-image: url(../images/admin/right_bg_top.jpg); background-position: top; background-repeat: repeat-x;">
				<div style="padding: 15px 10px 0px 10px;">
					<div>
						<table width="30%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<!--  <td id="shouxv"  width="80" height="28" align="center" bgcolor="#CC0000"
									class="white12">
									<a href="">手续费</a>
								</td>
								<td width="2">
									&nbsp;
								</td>
								<td id="touzithing" width="80" align="center" bgcolor="#8594A9" class="white12">
									<a id="investManger">投资信息管理费</a>
								</td>
								<td width="2">
									&nbsp;
								</td>-->
								<td   width="80" id="feind" align="center" bgcolor="#CC0000" class="white12">
									<a id="friendAward">普通用户好友奖励</a>
								</td>
								<td width="2">
									&nbsp;
								</td>
								<td id="vipfei" width="80" align="center" bgcolor="#8594A9" class="white12">
									<a id="vipmanager">VIP会费设置</a>
								</td>
								<td width="2">
									&nbsp;
								</td>
								<!--  
								<td id="recommend" width="80" align="center" bgcolor="#8594A9" class="white12">
									<a id="referral">推荐好友奖励设置</a>
								</td>
								<td width="2">
									&nbsp;
								</td>-->
							</tr>
						</table>
					</div>
					 
					<div id="showcontent" style="width: auto; background-color: #FFF; padding: 10px;">
						<!-- 内容显示位置 -->
						
						
					<!--  <table width="100%" border="0" cellspacing="1" cellpadding="3">
							<tr>
								<td style="width: 100px; height: 25px;" align="right"
									class="blue12">
									手续费：
								</td>
								<td align="left" class="f66">
									<s:textfield id="tb_produce" name="paramMap.number"
										cssClass="in_text_250" theme="simple"></s:textfield>&nbsp;%
									<span class="require-field">*<s:fielderror fieldName="paramMap['number']"></s:fielderror></span>
								</td>
							</tr>	
							
							<tr>
								<td height="36" align="right" class="blue12">
									<s:hidden name="action"></s:hidden>
									&nbsp;
								</td>
								<td>
									
									<input type="button" id="formalities" 
										style="background-image: url('../images/admin/btn_queding.jpg'); width: 70px; height: 26px; border: 0px"/>
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
						-->	
						
						<br />
					</div>
				</div>
			</div>
		
	</body>
</html>
