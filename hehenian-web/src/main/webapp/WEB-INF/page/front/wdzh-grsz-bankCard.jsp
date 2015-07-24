<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<jsp:include page="/include/head.jsp"></jsp:include>
	</head>

	<body>
		<!-- 引用头部公共部分 -->
		<jsp:include page="/include/top.jsp"></jsp:include>
		<div class="nymain">
			<div class="wdzh">
				<div class="l_nav">
					<div class="box">
						<!-- 引用我的帐号主页左边栏 -->
						<%@include file="/include/left.jsp"%>
					</div>
				</div>
				<div class="r_main">
					<div class="tabtil">
						<ul>
							<li id="infomationdata">
								个人详细信息
							</li><%-- 
							<li id="workdate">
								工作认证信息
							</li>--%>
							<li>
								修改密码
							</li>
							<li>
								手机设置
							</li>
<%--							<li onclick="loadNotesInfo()">--%>
<%--								通知设置--%>
<%--							</li>--%>
							<li id="li_tx" class="on"
								onclick="jumpUrl('queryBankInfoInit.do');">
								银行卡设置
							</li>
						</ul>
					</div>

					<!-- 个人详细信息位置 开始 -->
					<div class="box" id="baseinfo" style="display: none;">
						<jsp:include page="user_basicinfo.jsp"></jsp:include>
					</div>
					<!-- 个人详细信息位置 结束 -->

					<!-- 工作详细信息 开始 -->
					<div class="box" id="workdi" style="display: none;"></div>

					<!-- 工作详细信息 结束 -->

					<div class="box" style="display: none;">
						<div class="boxmain2" style="padding-top: 10px;">
							<div class="biaoge2" style="margin-top: 0px;">
								<table width="100%" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<th colspan="2" align="left" style="padding-top: 0px;">
											会员登录密码修改
										</th>
									</tr>
									<tr>
										<td width="18%" align="right">
											原密码：
										</td>
										<td width="83%">
											<input type="password" class="inp188" id="oldPassword" />
											<span class="txt">输入您现在的帐号密码</span>
										</td>
									</tr>
									<tr>
										<td align="right">
											新密码：
										</td>
										<td>
											<input type="password" class="inp188" id="newPassword" />
											<span class="txt">输入您的新密码</span>
										</td>
									</tr>
									<tr>
										<td align="right">
											确认新密码：
										</td>
										<td>
											<input type="password" class="inp188" id="confirmPassword" />
											<span class="txt">请再次输入您的新密码</span>
										</td>
									</tr>
									<tr>
										<td align="right">
											&nbsp;
										</td>
										<td style="padding-top: 10px;">
											<a href="javascript:void(0);" class="bcbtn"
												onclick="javascript:updateLoginPassword();">提交</a>
										</td>
									</tr>
									<tr>
										<td colspan="2">
											<span style="color: red; float: none;" id="s_tip"
												class="formtips"></span>
										</td>
									</tr>
								</table>

							</div>
							<div class="biaoge2">
								<table width="100%" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<th colspan="2" align="left" style="padding-top: 0px;">
											会员交易密码修改
										</th>
									</tr>
									<tr>
										<td width="18%" align="right">
											原密码：
										</td>
										<td width="83%">
											<input type="password" class="inp188" id="oldDealpwd" />
											<span class="txt">输入您现在的帐号密码</span>
										</td>
									</tr>
									<tr>
										<td align="right">
											新密码：
										</td>
										<td>
											<input type="password" class="inp188" id="newDealpwd" />
											<span class="txt">输入您的新密码</span>
										</td>
									</tr>
									<tr>
										<td align="right">
											确认新密码：
										</td>
										<td>
											<input type="password" class="inp188" id="confirmDealpwd" />
											<span class="txt">请再次输入您的新密码</span>
										</td>
									</tr>

									<tr>
										<td align="right">
											&nbsp;
										</td>
										<td style="padding-top: 10px;">
											<a href="javascript:void(0);" class="bcbtn"
												onclick="javascript:updateDealPassword();">提交</a>
										</td>
									</tr>
									<tr>
										<td colspan="2">
											<span style="color: red; float: none;" id="d_tip"
												class="formtips"></span>
										</td>
									</tr>
								</table>

							</div>
						</div>
					</div>
					<div class="box" style="display: none;">
						<div class="boxmain2" style="padding-top: 10px;">
							<div class="biaoge2" style="margin-top: 0px;">
								<table width="100%" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<th colspan="2" align="left" style="padding-top: 0px;">
											手机绑定
										</th>
									</tr>
									<tr>
										<td width="18%" align="right">
											手机号码：
										</td>
										<td width="83%">
											<input type="text" class="inp188" id="mobile" />
											<a id="clickCode" class="yzmbtn" href="javascript:void(0);">发送手机验证码</a>
										</td>
									</tr>
									<tr>
										<td align="right">
											验证码：
										</td>
										<td>
											<input type="text" class="inp188" id="code" />
											<span class="txt">输入您获取的手机验证码</span>
										</td>
									</tr>
									<tr>
										<td align="right">
											申请原因：
										</td>
										<td>
											<textarea class="txt420" id="content"></textarea>
										</td>
									</tr>
									<tr>
										<td align="right">
											&nbsp;
										</td>
										<td style="padding-top: 10px;">
											<a href="javascript:void(0);" class="bcbtn"
												onclick="javascript:addBindingMobile();">手机绑定</a>
										</td>
									</tr>
									<tr>
										<td colspan="2">
											<span style="color: red; float: none;" id="mobile_tip"
												class="formtips"></span>
										</td>
									</tr>
								</table>

							</div>

							<div class="biaoge2" style="margin-top: 0px;">
								<table width="100%" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<th colspan="2" align="left" style="padding-top: 0px;">
											手机变更
										</th>
									</tr>
									<tr>
										<td width="18%" align="right">
											手机号码：
										</td>
										<td width="83%">
											<input type="text" class="inp188" id="mobile2" />
											<a id="reClickCode" class="yzmbtn" href="javascript:void(0);">发送手机验证码</a>
										</td>
									</tr>
									<tr>
										<td align="right">
											验证码：
										</td>
										<td>
											<input type="text" class="inp188" id="code2" />
											<span class="txt">输入您获取的手机验证码</span>
										</td>
									</tr>
									<tr>
										<td align="right">
											变更原因：
										</td>
										<td>
											<textarea class="txt420" id="content2"></textarea>
										</td>
									</tr>
									<tr>
										<td align="right">
											&nbsp;
										</td>
										<td style="padding-top: 10px;">
											<a href="javascript:addChangeBindingMobile();" class="bcbtn">手机变更</a>
										</td>
									</tr>
									<tr>
										<td colspan="2">
											<span style="color: red; float: none;" id="mobile2_tip"
												class="formtips"></span>
										</td>
									</tr>
								</table>

							</div>

						</div>
					</div>
					<div class="box" style="display: none;">
						<div class="boxmain2" style="padding-top: 10px;">
							<div class="biaoge2" style="margin-top: 0px;">
								<table width="100%" border="0" cellspacing="0" cellpadding="0"
									style="line-height: 42px;">
									<tr>
										<th colspan="2" align="left" style="padding-top: 0px;">
											选择通知方式
										</th>
									</tr>
									<tr>
										<td align="right" style="padding-right: 40px;">
											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											<input type="checkbox" id="message"
												onclick="checkAll_MG(this); " />
											站内信通知
										</td>
										<td>
											<input type="checkbox" id="messageReceive" class="mg"
												onclick="mgcheck1(this);" />
											收到还款
											<input type="checkbox" id="messageDeposit" class="mg"
												onclick="mgcheck1(this);" />
											提现成功
											<input type="checkbox" id="messageBorrow" class="mg"
												onclick="mgcheck1(this);" />
											借款成功
											<input type="checkbox" id="messageRecharge" class="mg"
												onclick="mgcheck1(this);" />
											充值成功
											<input type="checkbox" id="messageChange" class="mg"
												onclick="mgcheck1(this);" />
											资金变化
										</td>
									</tr>
									<tr>
										<td align="right" style="padding-right: 40px;">
											<input type="checkbox" id="mail"
												onclick="checkAll_ML(this); " />
											邮件通知
										</td>
										<td>
											<input type="checkbox" id="mailReceive" class="ml"
												onclick="mlcheck1(this);" />
											收到还款
											<input type="checkbox" id="mailDeposit" class="ml"
												onclick="mlcheck1(this);" />
											提现成功
											<input type="checkbox" id="mailBorrow" class="ml"
												onclick="mlcheck1(this);" />
											借款成功
											<input type="checkbox" id="mailRecharge" class="ml"
												onclick="mlcheck1(this);" />
											充值成功
											<input type="checkbox" id="mailChange" class="ml"
												onclick="mlcheck1(this);" />
											资金变化
										</td>
									</tr>
									<tr>
										<td align="right" style="padding-right: 40px;">
											<input type="checkbox" id="note"
												onclick="checkAll_NT(this); " />
											短信通知
										</td>
										<td>
											<input type="checkbox" id="noteReceive" class="nt"
												onclick="ntcheck1(this);" />
											收到还款
											<input type="checkbox" id="noteDeposit" class="nt"
												onclick="ntcheck1(this);" />
											提现成功
											<input type="checkbox" id="noteBorrow" class="nt"
												onclick="ntcheck1(this);" />
											借款成功
											<input type="checkbox" id="noteRecharge" class="nt"
												onclick="ntcheck1(this);" />
											充值成功
											<input type="checkbox" id="noteChange" class="nt"
												onclick="ntcheck1(this);" />
											资金变化
										</td>
									</tr>
									<tr>
										<td align="right">
											&nbsp;
										</td>
										<td class="txt">
											<strong>注：尊敬的用户，您在合和年在线网的相关操作， 合和年信贷网会用以上三种方式<br />通知您，请您选择适合自己的通知方式！
												(系统默认以邮件通知)短信通知每条0.1元。</strong>
										</td>
									</tr>
									<tr>
										<td align="right">
											&nbsp;
										</td>
										<td style="padding-top: 10px;">
											<a href="javascript:void(0);" class="bcbtn"
												onclick="addNoteSetting();">确认</a>
										</td>
									</tr>
								</table>

							</div>
						</div>
					</div>

					<div class="box">
						<div class="boxmain2" style="padding-top: 10px;">
							<div class="biaoge2" style="margin-top: 0px;">
								<table width="100%" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<th colspan="2" align="left" style="padding-top: 0px;">
											提现银行
											<br />
										</th>
									</tr>
									<tr>
										<td width="18%" align="right">
											用户名：
											<br />
										</td>
										<td width="83%">
											<span class="txt" id="cardUserName1"> <s:property
													value="#request.realName" default="---"></s:property>
											</span>
											<br />
										</td>
									</tr>
									<tr>
										<td align="right">
											开户行：
											<br />
										</td>
										<td>
											<input type="text" class="inp188" id="bankName1" />
											<span class="txt">输入您的开户银行名称</span>
											<br />
										</td>
									</tr>
									<tr>
										<td align="right">
											支行：
											<br />
										</td>
										<td>
											<input type="text" class="inp188" id="subBankName1" />
											<span class="txt">输入您的开户支行</span>
											<br />
										</td>
									</tr>
									<tr>
										<td align="right">
											卡号：
											<br />
										</td>
										<td>
											<input type="text" class="inp188" id="bankCard1" />
											<span class="txt">输入您的卡号</span>
											<br />
										</td>
									</tr>
									<tr>
										<td align="right">
											&nbsp;
											<br />
										</td>
										<td style="padding-top: 10px;">
											<a href="javascript:void(0);" class="bcbtn"
												onclick="addBankInfo()">提交</a>
											<br />
										</td>
									</tr>
									<tr>
										<td colspan="2">
											<span style="color: red; float: none;" id="bk1_tip"
												class="formtips"></span>
										</td>
									</tr>
								</table>

							</div>
							<div class="biaoge2">
								<table width="100%" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<th colspan="2" align="left" style="padding-top: 0px;">
											提现银行
											<br />
										</th>
									</tr>
									<tr>
										<td width="18%" align="right">
											用户名：
											<br />
										</td>
										<td width="83%">
											<span class="txt" id="cardUserName2"> <s:property
													value="#request.realName" default="---"></s:property>
											</span>
											<br />
										</td>
									</tr>
									<tr>
										<td align="right">
											开户行：
											<br />
										</td>
										<td>
											<input type="text" class="inp188" id="bankName2" />
											<span class="txt">输入您的开户银行名称</span>
											<br />
										</td>
									</tr>
									<tr>
										<td align="right">
											支行：
											<br />
										</td>
										<td>
											<input type="text" class="inp188" id="subBankName2" />
											<span class="txt">输入您的开户支行</span>
											<br />
										</td>
									</tr>
									<tr>
										<td align="right">
											卡号：
											<br />
										</td>
										<td>
											<input type="text" class="inp188" id="bankCard2" />
											<span class="txt">输入您的卡号</span>
											<br />
										</td>
									</tr>
									<tr>
										<td align="right">
											&nbsp;
											<br />
										</td>
										<td style="padding-top: 10px;">
											<a href="javascript:void(0);" class="bcbtn"
												onclick="addBankInfo2()">提交</a>
											<br />
										</td>
									</tr>
									<tr>
										<td colspan="2">
											<span style="color: red; float: none;" id="bk2_tip"
												class="formtips"></span>
										</td>
									</tr>
								</table>


							</div>
							<span id="bankInfo"></span>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- 引用底部公共部分 -->
		<!-- 引用底部公共部分 -->
		<jsp:include page="/include/footer.jsp"></jsp:include>

		<script type="text/javascript"
			src="${path}/script/jquery-1.7.1.min.js"></script>
		<script type="text/javascript" src="script/jquery.shove-1.0.js"></script>
		<script type="text/javascript" src="common/date/calendar.js"></script>
		<script type="text/javascript" src="css/popom.js"></script>
		<script type="text/javascript" src="script/jbox/jquery.jBox-2.3.min.js"></script>
		<script type="text/javascript" src="script/jbox/jquery.jBox-zh-CN.js"></script> 
		<!-- 基本信息，工作认证js-->
		<script type="text/javascript" src="script/user_check.js"></script>
		<script type="text/javascript" src="script/user_work.js"></script>
		<script>
   /* $(function(){
    if(${map.auditStatus}==3){
       $("#province").attr("disabled","true");
       $("#registedPlacePro").attr("disabled","true");
       $("#city").attr("disabled","true");
       $("#registedPlaceCity").attr("disabled","true");
       $("#clickCode").hide();
        $("#clickCode").hide();;
    }
  });*/
  
  function jumpUrl(obj){
          window.location.href=obj;
       }
  
</script>
		<script>
	  //手机号码绑定
		  var timers=60;
		  var tipId;
		 
		   $(function(){
		       $("#clickCode").click(function(){
		           var phone=$("#mobile").val();
		           //验证手机号码是
		           if($("#mobile").val()==""){
                      $("#mobile_tip").attr("class", "formtips onError");
		              $("#mobile_tip").html("请填写手机号码！");
                   }else if((!/^1[3-8]\d{9}$/.test($("#mobile").val()))){ 
                      $("#mobile_tip").attr("class", "formtips onError");
	                  $("#mobile_tip").html("请正确填写手机号码！");
                   }else{
                      $("#mobile_tip").attr("class", "formtips");
	                  $("#mobile_tip").html(""); 
	                 
	                  
	                 if($("#clickCode").html()=="重新发送验证码"||$("#clickCode").html()=="发送手机验证码") {  
	                    $.post("sendSMS.do","phone="+phone,function(data){
	                    	if(data == "virtual"){
	     						window.location.href = "noPermission.do";
	     						return ;
	     					}
	                       if(data==1){
	                          timers=60;
	                          tipId=window.setInterval(timer,1000);
	                       }else{
	                          alert("手机验证码发送失败");
	                       }
	                     
	                  
	                    });
	                  }
                   }
		       });
		   });
		   
		   //定时
		   function timer(){
		    
		       if(timers>=0){
		         $("#clickCode").html("验证码获取："+timers+"秒");
		         timers--;
		       }else{
		          window.clearInterval(tipId);
		           $("#clickCode").html("重新发送验证码");
		           $.post("removeCode.do","",function(data){});
		           
		       }
		   }
		</script>


		<script>
		      //手机号码变更
		      
		  var rtimers=60;
		  var rtipId;
		 
        $(function(){
		         $("#reClickCode").click(function(){
		           var phone=$("#mobile2").val();
		           //验证手机号码是
		           if($("#mobile2").val()==""){
                      $("#mobile2_tip").attr("class", "formtips onError");
		              $("#mobile2_tip").html("请填写手机号码！");
                   }else if((!/^1[3,5,8]\d{9}$/.test($("#mobile2").val()))){ 
                      $("#mobile2_tip").attr("class", "formtips onError");
	                  $("#mobile2_tip").html("请正确填写手机号码！");
                   }else{
                      $("#mobile2_tip").attr("class", "formtips");
	                  $("#mobile2_tip").html(""); 
	                 
	                  
	                 if($("#reClickCode").html()=="重新发送验证码"||$("#reClickCode").html()=="发送手机验证码") {  
	                    $.post("sendSMS.do","phone="+phone,function(data){
	                       if(data==1){
	                          rtimers=60;
	                          rtipId=window.setInterval(rtimer,1000);
	                       }else{
	                          alert("手机验证码发送失败");
	                       }
	                     
	                  
	                    });
	                  }
                   }
		       });
		   });
		   
		   //定时
		   function rtimer(){
		    
		       if(rtimers>=0){
		         $("#reClickCode").html("验证码获取："+rtimers+"秒");
		         rtimers--;
		       }else{
		          window.clearInterval(rtipId);
		           $("#reClickCode").html("重新发送验证码");
		           $.post("removeCode.do","",function(data){});
		           
		       }
		   }
		</script>
		<script>	
	$(function(){
	   
		var aa = '${tab_type}';
		if(aa=='txload'){//跳转到银行卡设置选项
			$('.tabtil').nextAll('div').hide();
			$('.tabtil').find('li').removeClass('on');
			$('.tabtil').nextAll('div').eq($('#li_tx').index()).show();
			$('#li_tx').addClass('on'); 
		    loadBankInfo();
		}
		
	    $("#zh_hover").attr('class','nav_first');
	    $("#zh_hover div").removeClass('none');
	    $('#li_5').addClass('on'); 
		$('.tabtil').find('li').click(function(){
		   $('.tabtil').find('li').removeClass('on');
		   $(this).addClass('on');
		   $('.tabtil').nextAll('div').hide();
	       $('.tabtil').nextAll('div').eq($(this).index()).show();
		});
		
		$("#workdate").click(function(){
		   $.post("queryWorkInit.do",null,function(data){
		       $("#workdi").html(data);
		  });

		});
		
			$("#infomationdata").click(function(){
			 
		       $.post("queryBasicInit.do",null,function(data){
		       
		          $("#baseinfo").html("");
		          $("#baseinfo").html(data);
		       });

	       });
		
		
			//登录密码修改
			$("#oldPassword").blur(function(){
			     if($("#oldPassword").val()==""){
			       $("#s_tip").html("原密码不能为空");
			     }else{
			       $("#s_tip").html("");
			     }
		    });
		    
		    $("#newPassword").blur(function(){
		     if($("#newPassword").val()==""){
		       $("#s_tip").html("新密码不能为空");
		     }else if($("#newPassword").val().length<6 || $("#newPassword").val().length>20){
		        $("#s_tip").html("密码长度必须为6-20个字符"); 
		     }else{
		       $("#s_tip").html("");
		     }
		   });
		   
		   $("#confirmPassword").blur(function(){
		      if($("#confirmPassword").val()==""){
		          $("#s_tip").html("确认密码不能为空");
		      }if($("#newPassword").val()!=$("#confirmPassword").val()){
		        $("#s_tip").html("两次密码不一致");
		      }else{
		       $("#s_tip").html("");
		      }
	       });
	       
	       //交易密码修改
	       $("#oldDealpwd").blur(function(){
			     if($("#oldDealpwd").val()==""){
			       $("#d_tip").html("原密码不能为空");
			     }else{
			       $("#d_tip").html("");
			     }
		    });
		    
		    $("#newDealpwd").blur(function(){
		     if($("#newDealpwd").val()==""){
		       $("#d_tip").html("新密码不能为空");
		     }else if($("#newDealpwd").val().length<6 || $("#newDealpwd").val().length>20){
		        $("#d_tip").html("密码长度必须为6-20个字符"); 
		     }else{
		       $("#d_tip").html("");
		     }
		   });
		   
		   $("#confirmDealpwd").blur(function(){
		      if($("#confirmDealpwd").val()==""){
		          $("#d_tip").html("确认密码不能为空");
		      }if($("#newDealpwd").val()!=$("#confirmDealpwd").val()){
		        $("#d_tip").html("两次密码不一致");
		      }else{
		       $("#d_tip").html("");
		      }
	       });
	       
	       //提现银行卡设置1
	       $("#bankName1").blur(function(){
			     if($("#bankName1").val()==""){
			       $("#bk1_tip").html("开户银行名称不能为空");
			     }else{
			       $("#bk1_tip").html("");
			     }
		    });
		    
		    $("#subBankName1").blur(function(){
			     if($("#subBankName1").val()==""){
			       $("#bk1_tip").html("开户支行不能为空");
			     }else{
			       $("#bk1_tip").html("");
			     }
		    });
		    
		    $("#bankCard1").blur(function(){
			     if($("#bankCard1").val()==""){
			       $("#bk1_tip").html("卡号不能为空");
			     }else if(isNaN($("#bankCard1").val())){
			       $("#bk1_tip").html("请输入正确的银行卡号");
			     }else{
			       $("#bk1_tip").html("");
			     }
		    });
	       
	       //提现银行卡设置2
	       $("#bankName2").blur(function(){
			     if($("#bankName2").val()==""){
			       $("#bk2_tip").html("开户银行名称不能为空");
			     }else{
			       $("#bk2_tip").html("");
			     }
		    });
		    
		    $("#subBankName2").blur(function(){
			     if($("#subBankName2").val()==""){
			       $("#bk2_tip").html("开户支行不能为空");
			     }else{
			       $("#bk2_tip").html("");
			     }
		    });
		    
		    $("#bankCard2").blur(function(){
			     if($("#bankCard2").val()==""){
			       $("#bk2_tip").html("卡号不能为空");
			     }else if(isNaN($("#bankCard2").val())){
			       $("#bk2_tip").html("请输入正确的银行卡号");
			     }else{
			       $("#bk2_tip").html("");
			     }
		    });
		});
	</script>

		<script>
	//登录密码修改
	   function updateLoginPassword(){
		   	param["paramMap.oldPassword"] = $("#oldPassword").val();
	        param["paramMap.newPassword"] = $("#newPassword").val();
	        param["paramMap.confirmPassword"] = $("#confirmPassword").val();
	        param["paramMap.type"] = "login";
	        if($("#oldPassword").val()=="" || $("#newPassword").val()=="" ||$("#confirmPassword").val()=="" ){
	           $("#s_tip").html("请完整填写密码信息");
	           return;
	        }else if($("#newPassword").val().length<6 ||$("#newPassword").val().length >20){
	           $("#s_tip").html("密码长度必须为6-20个字符");
	           return;
	        }else if($("#newPassword").val()!=$("#confirmPassword").val()){
	           $("#s_tip").html("两次密码不一致");
	           return;
	        }
	        $.shovePost("updateLoginPass.do",param,function(data){
	            if(data == 1){
	              alert("两次密码输入不一致");
	               $("#newPassword").attr("value","");
	               $("#confirmPassword").attr("value","");
	            }else if(data==3){
	               alert("新密码修改失败");
	               $("#oldPassword").attr("value","");
	               $("#newPassword").attr("value","");
	               $("#confirmPassword").attr("value","");
	            }else if(data == 2){
	               alert("旧密码错误");
	               $("#oldPassword").attr("value","");
	               $("#newPassword").attr("value","");
	               $("#confirmPassword").attr("value","");
	            }else{//密码修改成功，跳到登录页面
	            	alert("修改密码成功,新密码为:"+$("#newPassword").val());
	            	$("#oldPassword").attr("value","");
	                $("#newPassword").attr("value","");
	                $("#confirmPassword").attr("value","");
	            	//window.location.href='login.do';
	            }
	        });
	   }
	   
	   //交易密码修改
	   function updateDealPassword(){
		   	param["paramMap.oldPassword"] = $("#oldDealpwd").val();
	        param["paramMap.newPassword"] = $("#newDealpwd").val();
	        param["paramMap.confirmPassword"] = $("#confirmDealpwd").val();
	        param["paramMap.type"] = "deal";
	        if($("#oldDealpwd").val()=="" || $("#newDealpwd").val()=="" ||$("#confirmDealpwd").val()=="" ){
	           $("#d_tip").html("请完整填写密码信息");
	           return;
	        }else if($("#newDealpwd").val().length<6 ||$("#newDealpwd").val().length >20){
	           $("#d_tip").html("密码长度必须为6-20个字符");
	           return;
	        }else if($("#newDealpwd").val()!=$("#confirmDealpwd").val()){
	           $("#d_tip").html("两次密码不一致");
	           return;
	        }
	        $.shovePost("updateLoginPass.do",param,function(data){
	            if(data == 1){
	              alert("两次密码输入不一致");
	               $("#newDealpwd").attr("value","");
	               $("#confirmDealpwd").attr("value","");
	            }else if(data==3){
	               alert("新密码修改失败");
	               $("#oldDealpwd").attr("value","");
	               $("#newDealpwd").attr("value","");
	               $("#confirmDealpwd").attr("value","");
	            }else if(data == 2){
	               alert("旧密码错误");
	               $("#oldDealpwd").attr("value","");
	               $("#newDealpwd").attr("value","");
	               $("#confirmDealpwd").attr("value","");
	            }else{//密码修改成功，跳到登录页面
	            	alert("修改密码成功,新密码为:"+$("#newDealpwd").val());
	            	//window.location.href='login.do';
	            	$("#oldDealpwd").attr("value","");
	                $("#newDealpwd").attr("value","");
	                $("#confirmDealpwd").attr("value","");
	            }
	        });
	   }
	   
	   //添加提现银行信息
	   function addBankInfo(){
	    if($("#bankName1").val()=="" || $("#subBankName1").val()=="" ||$("#bankCard1").val()=="" ){
           $("#bk1_tip").html("请完整填写信息");
           return;
         }else if(isNaN($("#bankCard1").val())){
           $("#bk1_tip").html("请输入正确的银行卡号");
           return;
         }
	     param["paramMap.bankName"] = $("#bankName1").val();
         param["paramMap.subBankName"] = $("#subBankName1").val();
         param["paramMap.bankCard"] = $("#bankCard1").val();
         param["paramMap.cardUserName"] = $("#cardUserName1").text();
         $.shovePost("addBankInfo.do",param,function(data){
               if(data == 1){
               	$("#bk1_tip").html("请输入有效的银行卡信息");
               	$("#bankCard1").attr("value","");//银行卡信息错误，只清空银行卡信息
               	return;
               }else if(data == 2){
                  alert("你已经添加了两张银行卡信息，未绑定的银行卡信息可以删除\n否则需要申请更换银行");
               }else{
 			    $("#bankInfo").html(data);
 			   }
 			   $("#bankName1").attr("value","");
 			   $("#subBankName1").attr("value","");
 			   $("#bankCard1").attr("value","");
 			});
	   }
	   
	   function addBankInfo2(){
	    if($("#bankName2").val()=="" || $("#subBankName2").val()=="" ||$("#bankCard2").val()=="" ){
           $("#bk2_tip").html("请完整填写信息");
           return;
         }else if(isNaN($("#bankCard2").val())){
           $("#bk2_tip").html("请输入正确的银行卡号");
           return;
         }
	     param["paramMap.bankName"] = $("#bankName2").val();
         param["paramMap.subBankName"] = $("#subBankName2").val();
         param["paramMap.bankCard"] = $("#bankCard2").val();
         param["paramMap.cardUserName"] = $("#cardUserName2").text();
         $.shovePost("addBankInfo.do",param,function(data){
               if(data == 1){
               	$("#bk2_tip").html("请输入有效的银行卡信息");
               	$("#bankCard2").attr("value","");//银行卡信息错误，只清空银行卡信息
               	return ;
               }else if(data == 2){
                  alert("你已经添加了两张银行卡信息，未绑定的银行卡信息可以删除\n否则需要申请更换银行");
               }else{
 			    $("#bankInfo").html(data);
 			   }
 			   $("#bankName2").attr("value","");
 			   $("#subBankName2").attr("value","");
 			   $("#bankCard2").attr("value","");
 			});
	   }
	   
	   //加载该用户提现银行卡信息
	   function loadBankInfo(){
	      $.shovePost("queryBankInfoInit.do",null,function(data){
	       /* alert(${li_type});
	        if(${li_type}=="1"){
		        $.post("queryBasicInit.do",null,aa);
	        }else{*/
 			    $("#bankInfo").html(data);
 			 //}
 		  });
	   }
	   
	   function aa(data){
	     $("#baseinfo").html("");
			          $("#baseinfo").html(data);
	   }
	   
	   //删除添加的银行卡信息
	   function del(id){
	 		if(!window.confirm("确定要删除吗?")){
	 			return;
	 		}
 			$.shovePost("deleteBankInfo.do",{bankId:id},function(data){
 			    $("#bankInfo").html(data);
 			});
	 	}
	 	
	 	 function changeBankCancel(id){
	 	 if(!window.confirm("确定要取消变更吗?")){
	 			return;
	 		}
	       $.shovePost("bankChangeCancel.do",{bankId:id},function(data){
	        if(data == 1){
	          alert("取消失败，请重新取消");
	          return;
	        }
	         $("#bankInfo").html(data);
	       });
	     }
	 	
	 	//添加手机绑定信息
	 	function addBindingMobile(){
	 	   if($("#mobile").val()==""){
	 	     $("#mobile_tip").html("手机号码不能为空");
	 	      return;
	 	    }else if($("#code").val()==""){
	 	       $("#mobile_tip").html("验证码不能为空");
	 	       return;
		 	}else if($("#content").val()==""){
	 	      $("#mobile_tip").html("申请原因不能为空");
	 	      return;
	 	     }else{
		 	    $("#mobile_tip").html("");
		 	 }
		 	 param["paramMap.mobile"] = $("#mobile").val();
         	 param["paramMap.code"] = $("#code").val();
         	 param["paramMap.content"] = $("#content").val();
		 	 $.shovePost("addBindingMobile.do",param,function(data){
		 	 
 //........................................... 
	       //手机验证码
	       if(data==10){
	        // $("#u_cellPhone").html("手机号码长度不对！")
	         alert("手机号码与获取验证码手机号不一致！");
	          return;
	       }
	       if(data==12){
	        // $("#u_cellPhone").html("手机号码长度不对！")
	         alert("请填写验证码");
	          return;
	       }
	       if(data==11){
	        // $("#u_cellPhone").html("手机号码长度不对！")
	         alert("请重新发送验证码");
	          return;
	       }
	       
	        if(data==13){
	        // $("#u_cellPhone").html("手机号码长度不对！")
	         alert("请输入正确的验证码");
	          return;
	       }
	       
//.....................................
		 	    if(data == 1){
		 	       $("#mobile_tip").html("手机号码无效");
		 	       $("#mobile").attr("value","");
		 	       return;
		 	   
		 	    }else if(data == 3){//该用户可以进行手机号码绑定，但是绑定的手机号码已经被别人绑定了
		 	      alert("手机号码已经被绑定，请更换绑定手机号码");
		 	      $("#mobile").attr("value","");
 			      $("#code").attr("value","");
 			      $("#content").attr("value","");
 			      return;
		 	    }else if(data ==4){
		 	       alert("手机号码已经被绑定审核，请更换绑定手机号码");
		 	       $("#mobile").attr("value","");
 			       $("#code").attr("value","");
 			       $("#content").attr("value","");
 			       return;
		 	    }else if(data == 5){
		 	      alert("手机号码绑定失败，请重新绑定");
		 	      $("#mobile").attr("value","");
 			       $("#code").attr("value","");
 			       $("#content").attr("value","");
		 	      return;
		 	    }else if(data == 6){
		 	      alert("您已经绑定了手机号码，请申请更换手机号码");
		 	      $("#mobile").attr("value","");
 			       $("#code").attr("value","");
 			       $("#content").attr("value","");
		 	      return;
		 	    }else if(data == 7){
		 	      alert("您已经绑定了手机号码，请申请更换手机号码");
		 	      $("#mobile").attr("value","");
 			       $("#code").attr("value","");
 			       $("#content").attr("value","");
		 	      return;
		 	    }else if(data == 8){
		 	      alert("你已经申请了绑定手机号码，请等待后台审核");
		 	      $("#mobile").attr("value","");
 			       $("#code").attr("value","");
 			       $("#content").attr("value","");
		 	      return;
		 	    }else if(data == 9){
		 	      alert("您的手机审核不通过，请联系客服人员");
		 	      $("#mobile").attr("value","");
 			       $("#code").attr("value","");
 			       $("#content").attr("value","");
		 	      return;
		 	    }
 			    alert("已经设置，请等待审核，号码为： "+$("#mobile").val());
 			    $("#mobile").attr("value","");
 			    $("#code").attr("value","");
 			    $("#content").attr("value","");
 			 });
	 	}
	 	
	 	//变更手机绑定信息
	 	function addChangeBindingMobile(){
	 	   if($("#mobile2").val()==""){
	 	     $("#mobile2_tip").html("手机号码不能为空");
	 	      return;
	 	    }else if($("#code2").val()==""){
	 	       $("#mobile2_tip").html("验证码不能为空");
	 	       return;
		 	}else if($("#content2").val()==""){
	 	      $("#mobile2_tip").html("变更原因不能为空");
	 	      return;
	 	     }else{
		 	    $("#mobile2_tip").html("");
		 	 }
		 	 param["paramMap.mobile"] = $("#mobile2").val();
         	 param["paramMap.code"] = $("#code2").val();
         	 param["paramMap.content"] = $("#content2").val();
		 	 $.shovePost("addChangeBindingMobile.do",param,function(data){
		 	      
 //........................................... 
	       //手机验证码
	       if(data==10){
	        // $("#u_cellPhone").html("手机号码长度不对！")
	         alert("手机号码与获取验证码手机号不一致！");
	         return;
	       }
	       if(data==12){
	        // $("#u_cellPhone").html("手机号码长度不对！")
	         alert("请填写验证码");
	          return;
	       }
	       if(data==11){
	        // $("#u_cellPhone").html("手机号码长度不对！")
	         alert("请重新发送验证码");
	          return;
	       }
	       
	        if(data==13){
	        // $("#u_cellPhone").html("手机号码长度不对！")
	         alert("请输入正确的验证码");
	          return;
	       }
	       
//.....................................
		 	   
		 	    if(data == 1){
		 	       $("#mobile2_tip").html("手机号码无效");
		 	       $("#mobile2").attr("value","");
		 	       return;
		 	    }else if(data == 2){
		 	       $("#mobile2_tip").html("验证码错误");
		 	       $("#code2").attr("value","");
		 	       return;
		 	    }else if(data == 3){//该用户可以进行手机号码绑定，但是绑定的手机号码已经被别人绑定了
		 	      alert("您还没有进行手机绑定，请先申请手机绑定");
		 	      $("#mobile2").attr("value","");
 			      $("#code2").attr("value","");
 			      $("#content2").attr("value","");
 			      return;
		 	    }else if(data ==4){
		 	       alert("您已经申请的手机信息还在审核，请等待后台审核");
		 	       $("#mobile2").attr("value","");
 			       $("#code2").attr("value","");
 			       $("#content2").attr("value","");
 			       return;
		 	    }else if(data == 5){
		 	      alert("手机号码绑定失败，请重新绑定");
		 	      $("#mobile2").attr("value","");
 			       $("#code2").attr("value","");
 			       $("#content2").attr("value","");
		 	      return;
		 	    }else if(data == 6){
		 	      alert("您变更的手机号码已经被绑定了，请重新输入变更的手机号码");
		 	      $("#mobile2").attr("value","");
 			       $("#code2").attr("value","");
 			       $("#content2").attr("value","");
		 	      return;
		 	    }else if(data == 7){
		 	      alert("您变更的手机号码已经在申请审核，请重新输入变更的手机号码");
		 	      $("#mobile2").attr("value","");
 			       $("#code2").attr("value","");
 			       $("#content2").attr("value","");
		 	      return;
		 	    }else if(data == 8){
		 	      alert("您的手机变更不通过，请联系客服人员");
		 	      $("#mobile").attr("value","");
 			       $("#code").attr("value","");
 			       $("#content").attr("value","");
		 	      return;
		 	    }else if(data == 9){
		 	      alert("您的手机变更不通过，请联系客服人员");
		 	      $("#mobile").attr("value","");
 			       $("#code").attr("value","");
 			       $("#content").attr("value","");
		 	      return;
		 	    }
 			    alert("您要变更的手机号码为 "+$("#mobile2").val());
 			    $("#mobile2").attr("value","");
 			    $("#code2").attr("value","");
 			    $("#content2").attr("value","");
 			 });
	 	}
	 	
	 	//通知设置
	 	function addNoteSetting(){
	 	 //alert($("#message").attr("checked"));
	 	  //站内信
	 	  param["paramMap.message"] = $("#message").attr("checked");
	      param["paramMap.messageReceive"] = $("#messageReceive").attr("checked");
	      param["paramMap.messageDeposit"] = $("#messageDeposit").attr("checked");
	      param["paramMap.messageBorrow"] = $("#messageBorrow").attr("checked");
	      param["paramMap.messageRecharge"] = $("#messageRecharge").attr("checked");
	      param["paramMap.messageChange"] = $("#messageChange").attr("checked");
	      //邮件
	      param["paramMap.mail"] = $("#mail").attr("checked");
	      param["paramMap.mailReceive"] = $("#mailReceive").attr("checked");
	      param["paramMap.mailDeposit"] = $("#mailDeposit").attr("checked");
	      param["paramMap.mailBorrow"] = $("#mailBorrow").attr("checked");
	      param["paramMap.mailRecharge"] = $("#mailRecharge").attr("checked");
	      param["paramMap.mailChange"] = $("#mailChange").attr("checked");
	      //短信
	      param["paramMap.note"] = $("#note").attr("checked");
	      param["paramMap.noteReceive"] = $("#noteReceive").attr("checked");
	      param["paramMap.noteDeposit"] = $("#noteDeposit").attr("checked");
	      param["paramMap.noteBorrow"] = $("#noteBorrow").attr("checked");
	      param["paramMap.noteRecharge"] = $("#noteRecharge").attr("checked");
	      param["paramMap.noteChange"] = $("#noteChange").attr("checked");
	      
	      $.shovePost("addNotesSetting.do",param,function(data){
	           if(data == 1){
	              alert("通知设置失败");
	           }else{
	              alert("通知设置成功");
	           }
	      });
	 	}
	 	
	 	//加载该用户通知设置信息
	   function loadNotesInfo(){
	      $.shovePost("queryNotesSettingInit.do",null,function(data){
 			    if(data == 1){
 			      return ;
 			    }
 			    var message = false,mail = false,notes = false;
 			    for(var i = 0; i < data.length; i ++){
			      if(i == 0){
			         message = data[i].message;
			         mail = data[i].mail;
			         note = data[i].note;
			      }
			      if(data[i].noticeMode =="1"){//邮件通知
			         if(data[i].reciveRepayEnable =="2"){
			         	$("#mailReceive").attr("checked","checked")
			         }if(data[i].showSucEnable =="2"){
			         	$("#mailDeposit").attr("checked","checked")
			         }if(data[i].loanSucEnable =="2"){
			         	$("#mailBorrow").attr("checked","checked")
			         }if(data[i].rechargeSucEnable =="2"){
			         	$("#mailRecharge").attr("checked","checked")
			         }if(data[i].capitalChangeEnable =="2"){
			         	$("#mailChange").attr("checked","checked")
			         }
			      }else if(data[i].noticeMode =="2"){//站内信通知
			      	if(data[i].reciveRepayEnable =="2"){
			         	$("#messageReceive").attr("checked","checked")
			         }if(data[i].showSucEnable =="2"){
			         	$("#messageDeposit").attr("checked","checked")
			         }if(data[i].loanSucEnable =="2"){
			         	$("#messageBorrow").attr("checked","checked")
			         }if(data[i].rechargeSucEnable =="2"){
			         	$("#messageRecharge").attr("checked","checked")
			         }if(data[i].capitalChangeEnable =="2"){
			         	$("#messageChange").attr("checked","checked")
			         }
			      }else{//短信通知
			      	if(data[i].reciveRepayEnable =="2"){
			         	$("#noteReceive").attr("checked","checked")
			         }if(data[i].showSucEnable =="2"){
			         	$("#noteDeposit").attr("checked","checked")
			         }if(data[i].loanSucEnable =="2"){
			         	$("#noteBorrow").attr("checked","checked")
			         }if(data[i].rechargeSucEnable =="2"){
			         	$("#noteRecharge").attr("checked","checked")
			         }if(data[i].capitalChangeEnable =="2"){
			         	$("#noteChange").attr("checked","checked")
			         }
			      }
			    }
			    if(message){//只要分类有一个被选中，则父类就选中
			      $("#message").attr("checked","checked");
			      //$(".mg").attr("checked","checked");
			    }
			    if(mail){
			      $("#mail").attr("checked","checked");
			      //$(".ml").attr("checked","checked");
			    }
			    if(note){
			      $("#note").attr("checked","checked");
			      //$(".nt").attr("checked","checked");
			    }
 		  });
	   }
	 	
	 	function checkAll_MG(e){
	   		if(e.checked){
	   			$(".mg").attr("checked","checked");
	   		}else{
	   			$(".mg").removeAttr("checked");
	   		}
   		}
   			
   		function checkAll_ML(e){
	   		if(e.checked){
	   			$(".ml").attr("checked","checked");
	   		}else{
	   			$(".ml").removeAttr("checked");
	   		}
   		}
   			
   		function checkAll_NT(e){
	   		if(e.checked){
	   			$(".nt").attr("checked","checked");
	   		}else{
	   			$(".nt").removeAttr("checked");
	   		}
   		}
   		
   		function mgcheck1(e){
   		  var len = $("input[class='mg']:checked").length;
   		  if(len>0){
   		    $("#message").attr("checked","checked");
   		  }else{
   		    $("#message").removeAttr("checked");
   		  }
   		}
   		
   		function mlcheck1(e){
   		  var len = $("input[class='ml']:checked").length;
   		  if(len>0){
   		    $("#mail").attr("checked","checked");
   		  }else{
   		    $("#mail").removeAttr("checked");
   		  }
   		}
   		
   		function ntcheck1(e){
   		  var len = $("input[class='nt']:checked").length;
   		  if(len>0){
   		    $("#note").attr("checked","checked");
   		  }else{
   		    $("#note").removeAttr("checked");
   		  }
   		}
   		
   		//弹出窗口关闭
   		function close(){
   			 ClosePop();  			  
   		}
   		
   		
   		
   		//工作认证提交按钮
   		function work(){
   	  
   	        var param = {};
			       param["paramMap.orgName"]=$("#orgName").val();
			        param["paramMap.occStatus"]=$("#occStatus").val();
			         param["paramMap.workPro"]=$("#workPro").val();
			          param["paramMap.workCity"]=$("#workCity").val();
			           param["paramMap.companyType"]=$("#companyType").val();
			           param["paramMap.companyLine"]=$("#companyLine").val();
			            param["paramMap.companyScale"]=$("#companyScale").val();
			             param["paramMap.job"]=$("#job").val();
			              param["paramMap.monthlyIncome"]=$("#monthlyIncome").val();
			               param["paramMap.workYear"]=$("#workYear").val();
			                param["paramMap.companyTel"]=$("#companyTel").val();
			                 param["paramMap.workEmail"]=$("#workEmail").val();
			                  param["paramMap.companyAddress"]=$("#companyAddress").val();
			                  param["paramMap.directedName"]=$("#directedName").val();
			                  param["paramMap.directedRelation"]=$("#directedRelation").val();
			                  param["paramMap.directedTel"]=$("#directedTel").val();
			                   param["paramMap.otherName"]=$("#otherName").val();
			                    param["paramMap.otherRelation"]=$("#otherRelation").val();
			                     param["paramMap.otherTel"]=$("#otherTel").val();
			                      param["paramMap.moredName"]=$("#moredName").val();
			                      param["paramMap.moredRelation"]=$("#moredRelation").val();
			                      param["paramMap.moredTel"]=$("#moredTel").val();
			                      param["paramMap.moredName"]=$("#moredName").val();
			                    $.post("updatework.do",param,function(data){
			                          if(data.msg=="保存成功"){
			                            alert("保存成功");
			                            window.location.href='home.do';
			                          }
			                            if(data.msg=="请正确填写公司名字"){
			                              $("#u_orgName").html("请正确填写单位名字");
			                           // alert("请正确填写单位名字");
			                          }
			                              if(data.msg=="真实姓名的长度为不小于2和大于50"){
			                               // $("#u_orgName").html("真实姓名的长度为不小于2和大于50")
			                            alert("真实姓名的长度为不小于2和大于50");
			                          }
			                              if(data.msg=="请填写职业状态"){
			                              $("#u_occStatus").html("请填写职业状态");
			                            //alert("请填写职业状态");
			                          }
			                              if(data.msg=="请填写工作城市省份"){
			                               $("#u_workPro").html("请填写工作城市省份");
			                           // alert("请填写工作城市省份");
			                          }
			                              if(data.msg=="请填写工作城市"){
			                               $("#u_workPro").html("请填写工作城市");
			                            //alert("请填写工作城市");
			                          }
			                            if(data.msg=="直系人姓名长度为不小于2和大于50"){
			                            alert("直系人姓名长度为不小于2和大于50");
			                          }
			                            if(data.msg=="请填写公司类别"){
			                            //alert("请填写公司类别");
			                              $("#u_companyType").html("请填写公司类别");
			                          }
			                              if(data.msg=="请填写公司行业"){
			                                $("#u_companyLine").html("请填写公司行业");
			                            //alert("请填写公司行业");
			                          }
			                              if(data.msg=="请填写公司规模"){
			                               $("#u_companyScale").html("请填写公司规模");
			                           // alert("请填写公司规模");
			                          }
			                              if(data.msg=="请填写职位"){
			                              $("#u_job").html("请填写职位");
			                            //alert("请填写职位");
			                          }
			                              if(data.msg=="请填写月收入"){
			                              $("#u_monthlyIncome").html("请填写月收入");
			                            //alert("请填写月收入");
			                          }
			                          
			                              if(data.msg=="请填写现单位工作年限"){
			                               $("#u_workYear").html("请填写现单位工作年限");
			                           // alert("请填写现单位工作年限");
			                          }
			                               if(data.msg=="请正确填写公司电话"){
			                                $("#u_companyTel").html("请填写公司电话");
			                            //alert("请填写公司电话");
			                          }
			                                 if(data.msg=="请填写工作邮箱"){
			                                $("#u_workEmail").html("请填写工作邮箱");
			                            //alert("请填写工作邮箱");
			                          }
			                              if(data.msg=="请填写公司地址"){
			                                 $("#u_companyAddress").html("请填写公司地址");
			                           // alert("请填写公司地址");
			                          }
			                              if(data.msg=="请填写直系人姓名"){
			                                 $("#u_directedName").html("请填写直系人姓名");
			                           // alert("请填写直系人姓名");
			                          }
			                              if(data.msg=="请填写直系人关系"){
			                                 $("#u_directedRelation").html("请填写直系人关系");
			                            //alert("请填写直系人关系");
			                          }
			                           if(data.msg=="请正确填写直系人电话"){
			                            //alert("请正确填写直系人手机");
			                              $("#u_directedTel").html("请正确填写直系人手机");
			                          }
			                              if(data.msg=="请填写其他人姓名"){
			                            //alert("请填写其他人姓名");
			                              $("#u_otherName").html("请填写其他人姓名");
			                          }
			                              if(data.msg=="请填写其他人关系"){
			                          //  alert("请填写其他人关系");
			                            $("#u_otherRelation").html("请填写其他人关系");
			                            
			                          }
			                             if(data.msg=="请正确填写其他人联系电话"){
			                            //alert("请正确填写其他人联系手机");
			                              $("#u_otherTel").html("请正确填写其他人联系手机");
			                          }
			                          if(data.msg=="moretel"){
			                            //alert("请正确填写其他联系手机");
			                              $("#u_moredTel").html("请正确填写其他联系手机");
			                          }
			                             if(data.msg=="morename"){
			                            //alert("请正确填写其他联系人名字");
			                              $("#u_moredName").html("请正确填写其他联系人名字");
			                          }
			                           if(data.msg=="morereation"){
			                            //alert("请正确填写其他联系人关系");
			                              $("#u_moredRelation").html("请正确填写其他联系人关系");
			                          }
			                         if(data.msg=="请正确填写直系人电话长度错误"){
			                            //alert("请真确填写直系人电话长度");
			                              $("#u_directedTel").html("请正确填写直系人电话长度");
			                          }
			                         if(data.msg=="其他人姓名长度为不小于2和大于50"){
			                           // alert(" 其他人姓名长度为不小于2和大于50");
			                             $("#u_otherName").html("其他人姓名长度为不小于2和大于50");
			                          }
			                          if(data.msg=="更多联系人姓名长度为不小于2和大于50"){
			                            //alert(" 其他人姓名长度为不小于2和大于50");
			                              $("#u_moredName").html("其他人姓名长度为不小于2和大于50");
			                          }
			                          
			                    });  
             	  
   		}
   		
   		

	</script>

	</body>
</html>
