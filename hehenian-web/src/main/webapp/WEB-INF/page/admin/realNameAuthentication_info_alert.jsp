<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.sp2p.util.DateUtil"%>
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
		<link href="../css/css.css" rel="stylesheet" type="text/css" />
		
		<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
		<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
		<script type="text/javascript" src="../common/dialog/popup.js"></script>
		
		<script>
		    $(function(){
				
				show(1);
			    if($("#img").val() == ''){
			    	$("#img").attr("src","../images/NoImg.GIF");
				    }	        

				$("#comm").click(function(){

					var param = {};
					var z = $('input[name="realNameRadio"]:checked').val();
					   param["paramMap.flag"] = z;
					   param["paramMap.id"] = $("#userId").text();
					    $.post("userBaseDataCheck.do",param,function(data){
					       if(data.msg=="保存成功"){
					       //window.location.href='againJumpToUserInf.do';
					        alert("审核成功");               
					        window.parent.closeMthod();
					       }else{
					         alert("审核失败");
					       }
					    });
					
					//userBaseDataCheck
					//window.parent.window.jBox.close();
				});
		       }); 
		       $("#li_work").click(function() {
		      		alert("3435435");
					window.location.href = 'queryUserMangework.do?uid=${map.userId}';
				});
				function closeJbox(){
					window.parent.closeJbox();
				}
				function show(id){
					for(var i =1;i<=3;i++){
						if(id == i){
							$("#table_"+i).css("display","block");
						}else{	
							$("#table_"+i).css("display","none");
						}
					}
					
				}
		</script>

		<style type="text/css">
			#id_a{font-size:14px;}
			#id_a ul li{float:left; width:100px;margin-left:50px;cursor: pointer;}
			table tr td{width:80px;border:1px solid #666666; padding:6px 12px;}
			#table_3 tr td{width:100px;line-height:1.8;}
			.userManage_left{padding-left:30px;}
			.realName_div_01{width:470px;margin-left: 40px; margin-top: 20px; display: block; font-family: 'tahoma'; font-size: 14px; border:1px solid #666666;}
			.realName_div_02{width:458px;padding:5px 6px;border-bottom: 1px solid #666666;}
			.realName_div_03{width:470px;margin-left: 40px; margin-top: 20px; display: block; font-family: 'tahoma'; font-size: 14px;text-align: center; padding:4px 20px;}
		</style>
	</head>
	<body >
		<table style="margin-left:40px;margin-top:20px; display: none; block;font-family:'tahoma';font-size:14px;" id="table_1">
			<tr>
				<td>用户名：</td>
				<td>${ UserMsgmap.username}</td>
				<td>会员编号：</td>
				<td><span id="userId">${ UserMsgmap.id}</span></td>
			</tr>
			<tr>
				<td>真实姓名：</td>
				<td>${UserMsgmap.realName}</td>
				<td>推荐人</td>
				<td>${UserMsgmap.refferee }</td>
			</tr>
			<tr>
				<td>身份证号：</td>
				<td>${map.idNo }</td>
				<td>用户来源：</td>
				<td>
					<s:if test="#request.UserMsgmap.source == 0">
						自动导入
					</s:if>
					<s:if test="#request.UserMsgmap.source == 1">
						网站注册
					</s:if></td>
			</tr>
			<tr>
				<td>手机号码：</td>
				<td>${map.cellPhone }</td>
				<td rowspan="4">认证情况</td>
				<td rowspan="4">
				<s:if test='#request.map_rez.identifyv=="审核通过"'>
					<img id="img_identiy" width="25" height="20" src="../images/baseimage_1.jpg" />
				</s:if>
				<s:else>
					<img id="img_identiy" width="25" height="20" src="../images/baseimage_1h.jpg" />
				</s:else>
				
				<s:if test='#request.map_rez.workv=="审核通过"'>
					<img id="img_identiy" width="25" height="20" src="../images/baseimage_2.jpg" />
				</s:if>
				<s:else>
					<img id="img_identiy" width="25" height="20" src="../images/baseimage_2h.jpg" />
				</s:else>
				<s:if test='#request.map_rez.addressv=="审核通过"'>
					<img id="img_identiy" width="25" height="20" src="../images/baseimage_3.jpg" />
				</s:if>
				<s:else>
					<img id="img_identiy" width="25" height="20" src="../images/baseimage_3h.jpg" />
				</s:else>
				<s:if test='#request.map_rez.responsev=="审核通过"'>
					<img id="img_identiy" width="25" height="20" src="../images/baseimage_4.jpg" />
				</s:if>
				<s:else>
					<img id="img_identiy" width="25" height="20" src="../images/baseimage_4h.jpg" />
				</s:else>
				<s:if test='#request.map_rez.incomev=="审核通过"'>
					<img id="img_identiy" width="25" height="20" src="../images/baseimage_5.jpg" />
				</s:if> 
				<s:else>
					<img id="img_identiy" width="25" height="20" src="../images/baseimage_5h.jpg" />
				</s:else>
				</td>
			</tr>
			<tr>
				<td>邮箱：</td>
				<td>${ UserMsgmap.email}</td>
			</tr>
			<tr>
				<td>居住地址：</td>
				<td>${ map.address}</td>
				
			</tr>
			<tr>
				<td>银行卡：</td>
				<td>${map.bankCar}</td>
			</tr>
			
	</table>
	
	<div class="realName_div_01" >
		<div class="realName_div_02">认证资料审核</div>
		<div class="realName_div_02">
			<div>审核意见: <input type="radio" checked="checked" name="realNameRadio" value="3" />审核通过<input type="radio" name="realNameRadio" value="2" />审核不通过 </div>
			<div><span>审核说明:</span>
			<span><textarea style="width:300px;height:100px;"></textarea>
			</span>
				
			</div>
		</div>
	</div>
	
	<div class="realName_div_03" >
		<input id="comm" type="button" value="确认" /><input type="button" value="取消" />
	</div>
		
	</body>
</html>
