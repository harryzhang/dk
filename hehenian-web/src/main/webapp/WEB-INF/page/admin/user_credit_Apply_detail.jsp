<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<title>额度审核</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="This is my page" />
		<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
		<link href="../css/admin/admin_custom_css.css" rel="stylesheet" type="text/css" />
	</head>
	<body>
			<div id="right">
				<div style="padding: 0px 10px 0px 10px;">
					
					<div class="tab">
						<table cellspacing="1" cellpadding="3">
							<tr>
								<td class="blue12 left80">
									用户名：${map.username }
								</td>
							</tr>
							<tr>
								<td class="f66 left80">
									当前额度：${map.creditLimit }
								</td>
							</tr>
							<tr>
								<td colspan="2" class="blue12 left80">
								申请额度：${map.applyAmount }
								</td>
							</tr>
							<tr>
							<td colspan="2" class="blue12 left80">
								申请时间：${map.applyTime }
								</td></tr>
							<tr>
							<td colspan="2" class="blue12 left80">
								申请原因：${map.applyDetail }
								</td>
							</tr>
							<tr >
							    <td class="blue12 left" >
							  <span style="color: red;">额度申请</span> &nbsp;<input  type="button" value="查看认证详情" id="queryMsg"/>
								</td>
							</tr>
							
							<tr>
								<td class="blue12 left">
								<!--  
								审核状态：<input type="radio" name="pvalid" value="1" onclick="javascript:$('#u_tip').html('')"
   		<s:if test='#request.map.auditStatus == 3'>checked="checked"</s:if>
												<s:else></s:else>
    />成功 <input type="radio" name="pvalid" value="0"
    <s:if test='#request.map.auditStatus == 2'>checked="checked"</s:if>
												<s:else></s:else>
    />失败
    -->
    								审核状态：<input type="radio" name="pvalid" value="1"  id="k"
    								<s:if test='#request.map.tcstatus == 3'>checked="checked"</s:if>
												<s:else></s:else>
    								
    								/>成功 <input type="radio" name="pvalid" value="0"   <s:if test='#request.map.tcstatus == 2'>checked="checked"</s:if> id="k1" 
												<s:else></s:else>/>失败
    
								</td>
							</tr>
							<tr>
								<td class="blue12 left">
								   通过额度 ：<input type="text" name="paramMap.applyAmountt" id="applyAmount" value="${map.agreeAmount}"/>
								</td>
							</tr>
							<tr>
							  	<td class="blue12 left">
								   审核原因：<s:textarea name="paramMap.content" cols="40" rows="5" id="content" value="%{#request.map.checkMsg}" />
								</td>
							</tr>
							<tr>
							  	<td class="blue12 left">
								  审核员：${map.creditlimtor }
								</td>
							</tr>
								<tr>
							  	<td class="blue12">
							  	<a style="margin-left: 120px">
								  <input type="button"
								  style="background-image: url('../images/admin/btn_queding.jpg'); width: 70px; border: 0; height: 26px" class="scbtn" id="tb_yes"/>
								</a>
								<input type="button" value="重新审核" id="reset" style="display: none;"/><span style="color: red; float: none;" id="s_tip" class="formtips"></span>
								</td>
							</tr>
							
						</table>
					</div>
				</div>
			</div>
<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
<script type="text/javascript">
	$("#k1").click(function(){
		$("input#applyAmount").val("0");
		$("input#applyAmount").attr("disabled",true);
	})
	$("#k").click(function(){
		$("input#applyAmount").attr("disabled",false);
	})
	$("#reset").click(function(){
   		$("input#applyAmount").val("");
   		$("#content").val("");
		$("input#k").attr("disabled",false);
   		$("input#k1").attr("disabled",false);
   		$("input#applyAmount").attr("disabled",false);
   		$("#content").attr("disabled",false);
   		$("#reset").hide();
   		$("#tb_yes").show();
	})
</script>
<script>
$(function(){
  $("#queryMsg").click(function(){
<%--  	window.parent.closeMthodInfo(${map.id });--%>
    window.location.href='queryPerUserCreditAction.do?userId=${map.id }';
  });
   if(${request.map.tcstatus}==2||${request.map.tcstatus}==3){
   		$("input#k").attr("disabled",true);
   		$("input#k1").attr("disabled",true);
   		$("input#applyAmount").attr("disabled",true);
   		$("#content").attr("disabled",true);
   		$("#reset").show();
   		$("#tb_yes").hide();
   }
	
  
  $("#applyAmount").blur(function(){
   if( $("#applyAmount").val()==""){
     $("#s_tip").html("请填写同意申请金额");
   }else{
     $("#s_tip").html("");
   }
    });
     $("#content").blur(function(){
   if( $("#content").val()==""){
     $("#s_tip").html("请填写审核原因");
   }else{
     $("#s_tip").html("");
   }
  });
  
});

 var flag = true;
    $("#tb_yes").click(function(){
      if(flag){
      flag = false;
      }
    if(flag){
     flag = false;
     }
    var param = {};
    param["paramMap.validp"] = $("input[name='pvalid']:checked").val();
    param["paramMap.content"] = $("#content").val();
    param["paramMap.userId"] = '${map.id}';
    param["paramMap.ti"] = '${ti}';
    param["paramMap.applyAmount"] = $("#applyAmount").val();
    param["paramMap.preAmount"] = '${map.applyAmount }'
    $.post("updateUserCreditLimit.do",param,function(data){
       if(data==0){
       $("#s_tip").html("请选择审核状态");
        flag = true;
       }else if(data==1){
       $("#s_tip").html("请填写同意申请金额");
       flag = true;
       }else if(data==2){
        $("#s_tip").html("请填写审核原因");
        flag = true;
       }else if(data==6){
        alert('审核成功');
        flag = true;
         //审核成功
        window.parent.closeMthod();
       }else if(data==5){
       alert("同意金额不能大于申请金额");
       flag = true;
       }else if(data==7){
       flag = true;
       alert('审核失败');
       }else if(data==8){
        flag = true;
        alert('同意金额输入错误，请输入正确分数');
        }
       });
      });
</script>

<script>
/*
$(function(){
 if('${map.tcstatus}'==3){
 $("#applyAmount").attr("disabled","true");
 $("#k1").attr("disabled","true");
 $("#k2").attr("disabled","true");
 $("#content").attr("disabled","true");
 }
});
*/
</script>
	</body>	
</html>