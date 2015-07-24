<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<title>用户手机变更</title>
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
				<div style="padding: 15px 10px 0px 10px;">
					
					 
					<div class="tab">
						<table cellspacing="1" cellpadding="3">
							<tr>
								<td class="blue12 left">
									用户名：${map.username }
								</td>
							</tr>
							<tr>
								<td colspan="2" class="blue12 left">
								原手机号码：${map.cellPhone }
								</td>
							</tr>
							<tr>
								<td colspan="2" class="blue12 left">
  							新手机号码：${map.mobilePhone }
								</td>
							</tr>
							<tr>
							    <td class="blue12 left">
							    申请时间：${map.requsetTime }
								</td>
							</tr>
							<tr>
								<td class="blue12 left">
								申请原因：${map.reason }
								</td>
							</tr>
							<%--<tr>
								<td class="blue12 left">
					           变更审核：
								</td>
							</tr>
							<tr>
								<td class="blue12 left">
								审核状态:
								</td>
							</tr>
							--%><tr>
	                            <td class="blue12 left">
	
	                          
								  审核状态  :<input type="radio" name="paramMap_valid" value="1" onclick="javascript:$('#u_tip').html('')"
   		<s:if test='#request.map.tpistatus == 1'>checked="checked"</s:if>
												<s:else></s:else>
    />成功  <input type="radio" name="paramMap_valid" value="4"
    <s:if test='#request.map.tpistatus == 4'>checked="checked"</s:if>
												<s:else></s:else>
    />失败
   
	
	
	
								</td>
							</tr>
							   <tr>
								<td class="blue12 left">
					          审批原因： 
					          <s:textarea name="paramMap.content" cols="40" rows="5" id="content" value="%{#request.map.option}"/>
								</td>
							</tr>
								 <tr>
								<td class="blue12 left1000" >
								<input  type="button"  style="background-image: url('../images/admin/btn_queding.jpg'); width: 70px; border: 0; height: 26px" class="scbtn" id="bt_yes"/><span style="color: red; float: none;" id="u_tip"
														class="formtips"></span>
								</td>
							</tr>
							
						</table>	
					</div>
				</div>
			</div>
<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
<script>
  $(function(){
  
  $("#bt_yes").click(function(){
  	
     var param = {};
     param["paramMap.content"] = $("#content").val();
     param["paramMap.vali"] = $("input[name='paramMap_valid']:checked").val();
     param["paramMap.id"] = '${map.id}';
      param["paramMap.tpiid"] = '${map.tpiid}';
     param["paramMap.newPhone"] = '${map.mobilePhone }';
  $.post("updateUserPhone.do",param,function(data){
  if(data==4){
    alert("审核成功");
    window.parent.CloseMethod();
     //history.go(-1);
  }else if(data==1){
   $("#u_tip").html("请选择审核状态");
  }else if(data==2){
    $("#u_tip").html("请选择审批原因");
  }else{
    alert("审核不成功");
    window.parent.CloseMethod();
  }
    
  });
  
  
  });
  });
</script>
	</body>	
</html>