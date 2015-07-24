<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<!--=======================-->
		<jsp:include page="/include/head.jsp"></jsp:include>
		<link id="skin" rel="stylesheet" href="css/jbox/Gray/jbox.css" />
	</head>

	<body>
		<!-- 引用头部公共部分 -->
		<jsp:include page="/include/top.jsp"></jsp:include>
		<div class="nymain">
		<div class="bigbox" style="border:none">
				
				<div class="sqdk" style="background: none;">
					<div class="l-nav">
						<ul>
								<li class="on">
								<a><span>step1 </span> 基本资料</a>
							</li>
							<li>
							<s:if test="#request.from != null && #request.from!='' && #session.user.authStep>=2">
							  <a href="userPassData.do?from=${from }"><span>step2 </span> 上传资料</a>
							</s:if>
							
							<s:else>
								<s:if test="#session.user.authStep>=4">
									<a href="userPassData.do"><span>step2 </span> 上传资料</a>
								</s:if>	
								<s:else>
								     <a><span>step2 </span> 上传资料</a>
								</s:else>
							</s:else>
							</li>
							<li>
							<s:if test="#request.from != null && #request.from!='' && #session.user.authStep>=2">
								<s:if test="#session.stepMode ==1">
								     <a href="addBorrowInit.do?t=${session.borrowWay}"><span>step3 </span> 发布贷款</a>							  
								  </s:if>
								  <s:elseif test="#session.stepMode ==2">
								     <a href="creditingInit.do"><span>step3 </span>  申请额度</a>
								  </s:elseif>
								  <s:else>
								    <a href="addBorrowInit.do?t=0"><span>step3 </span> 发布贷款</a>	
								  </s:else>
							</s:if>
							<s:else>
								<s:if test="#session.user.authStep>=5">
								  <s:if test="#session.stepMode ==1">
								     <a href="addBorrowInit.do?t=${session.borrowWay}"><span>step3 </span> 发布贷款</a>							  
								  </s:if>
								  <s:elseif test="#session.stepMode ==2">
								     <a href="creditingInit.do"><span>step3 </span> 申请额度</a>
								  </s:elseif>
								  <s:else>
								    <a href="addBorrowInit.do?t=0"><span>step3 </span> 发布贷款</a>	
								  </s:else>
								</s:if>	
								<s:else>
								  <s:if test="#session.stepMode ==1">
								       <a><span>step3 </span> 发布贷款</a>							  
								  </s:if>
								  <s:elseif test="#session.stepMode ==2">
								     <a><span>step3 </span> 申请额度</a>
								  </s:elseif>
								</s:else>
							</s:else>
							</li>
						</ul>
					</div>
					<div class="r-main">
						<div class="til01">
							<ul>
								<li  id="li_geren">
									个人详细信息
								</li>
								<li  id="li_work" >
									工作认证信息
								</li>
								<li  id="li_vip" class="on">
									申请vip
								</li>
							</ul>
						</div>
						<div class="rmainbox">
                       
							<div class="box01"   id="div_vip">
								<p class="tips" style="color: #ff0000;">
									投资者：
									<br />
									网站合作商提供投资担保，享受100%本金保障。对于担保标、推荐标，还能100%保利息。（普通用户仅保障本金）
									有专业客服跟踪服务，体验尊贵感受。 享有尊贵VIP身份标识。<br /> 借款者：<br />享有借款资格，及时缓解资金压力。
									参与网站举行的各种活动。
								</p>
								<div class="tab">
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
										<tr>
											<td align="right">
												您的状态是：
											</td>
											<td>
											<s:if test="#request.vippagemap.vipStatus==2">尊敬的vip会员</s:if>
											<s:else>普通会员</s:else>
											</td>
										</tr>
										<tr>
											<td align="right">
												用 户 名：
											</td>
											<td>
												${vippagemap.username }
											</td>
										</tr>
										<tr>
											<td align="right">
												姓 名：
											</td>
											<td>
												${vippagemap.realName }
											</td>
										</tr>
										<tr>
											<td align="right">
												邮 箱：
											</td>
											<td>
												${vippagemap.email }
											</td>
										</tr>
										<tr>
											<td align="right">
													<span style="color: red; float: none;" 
														class="formtips">*</span>选择客服：
											</td>
											<td>
												<a id = "kefuname">${vippagemap.kefuname }</a>
												<input type="hidden" value="${vippagemap.kfid }" name="paramMap.kefu" id="kefu"/>
											    <input type="button" id="btn_sp" class="scbtn"
									             style="cursor: pointer;" value="选择客服" onclick="fff()"/>
												
												<span style="color: red; float: none;" id="u_kefu"
														class="formtips"></span>
											</td>
										</tr>
										<s:if test="%{#request.isVip eq 'false'}">
										<tr>
											<td align="right">
													<span style="color: red; float: none;" 
														class="formtips">*</span>验 证 码：
											</td>
											<td>
												<input type="text" class="inp100x" name="paramMap.code" id="code"/>
											
												 <img src="admin/imageCode.do?pageId=vip" title="点击更换验证码" style="cursor: pointer;"
									id="codeNum" width="46" height="18" onclick="javascript:switchCode()" />
												
												
												<span style="color: red; float: none;" id="u_code"
														class="formtips"></span>
											</td>
										</tr>
										</s:if>
										<tr>
											<td align="right">&nbsp;
												
											</td>
											<td style="padding-top: 20px;">
											  <input  type="button" id="vip_btn"  class="bcbtn" value="我要申请"/>
											</td>
										</tr>
									</table>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		
		
		<!-- 引用底部公共部分 -->
		<jsp:include page="/include/footer.jsp"></jsp:include>
<script type="text/javascript" src="script/jbox/jquery.jBox-2.3.min.js"></script>
<script type="text/javascript" src="script/jbox/jquery.jBox-zh-CN.js"></script>
		<script type="text/javascript">
		  $(function(){
		     //样式选中
          dqzt(2);
	  var sd=parseInt($(".l-nav").css("height"));
    var sdf=parseInt($(".r-main").css("height"));
	 $(".l-nav").css("height",sd>sdf?sd:sdf-15);
		  });
		</script>
			<script type="text/javascript">
			
		      function switchCode(){
			     var timenow = new Date();
			     $('#codeNum').attr('src','admin/imageCode.do?pageId=vip&d='+timenow);
		    };
		    
		    
		</script>
		<script type="text/javascript">
		  /*
		   $(function(){
		   $("#btn_sp").click(function(){
		      //window.showModalDialog("uploadFileAction.do?obj="+json,window,"dialogWidth=500px;dialogHeight=400px");
		      var   rv   =   showModalDialog("querykefu.do");
               $("#kefuname").html(rv[0]);
               $("#kefu").attr("value",rv[1]);
		   });
		   })
		   */
		   $(function(){
		   
		   //--------------add by houli 判断用户是否已经申请了vip，如果申请了则该页面显示灰色
		      var isVip = '${isVip}';
		      if(isVip == "true"){//页面操作按钮变灰色
		          $("#vip_btn").attr('style','display:none;');
		          $("#btn_sp").attr('style','display:none;');
		          $("#context").attr('disabled','disabled');
		          $("#code").attr('disabled','disabled'); 
		          $("#codeNum").attr('style','display:none;');
		          
		      }
		   
	
		       $('#code').blur(function(){
		        if($('#code').val()==""){
		            $('#u_code').html("验证码不能为空");
		            return ;
		         }else{
		          $('#u_code').html("");
		         }
		         
		      }); 
		      
		      
		      
		      $('#btn_sp').click(function(){
		                   if($("#kefu").val()==""){
		         $("#u_kefu").html("客服不能为空");
		         return ;
		      }else{
		       $("#u_kefu").html("");
		      }
		         
		         });
		      
		   });
		   
		
		</script>
		
		<script type="text/javascript" src="css/popom.js"></script>
		<script type="text/javascript">
			//初始化
		     $(function(){
		          $("#li_geren").click(function(){
		          var from = '${from}';
		            window.location.href='querBaseData.do?from='+from;
		        });
		        
		        
		               $("#li_work").click(function(){
		               var from = '${from}';
		           window.location.href='querWorkData.do?from='+from;
		        });
		        
		               $("#li_vip").click(function(){
		               var from = '${from}';
		       window.location.href='quervipData.do?from='+from;
		    });
		        
		 });
		 
		    
		     $(function(){
			      $("#vip_btn").click(function(){
			      var param= {};
			      param["paramMap.pageId"] = "vip";
			      param["paramMap.code"] = $("#code").val();
			      param["paramMap.content"] = $("#context").val();
			      param["paramMap.kefu"] = $("#kefu").val();
			      //----add by houli
			      param["paramMap.from"] = '${from}';
			      var from = '${from}';
			      var btype = '${btype}';
			      if(btype == ''){
			         btype = '${session.borrowWay}';
			      }
			      //-----
			      $.post("updataUserVipStatus.do",param,function(data){
			        if(data.msg==1){
			          alert("申请vip成功!");
			          window.location.href="userPassData.do?from="+from+"&btype="+btype;
			        }else if(data.msg==5){
			         $("#u_code").html("验证码错误");
			          switchCode();
			        }else if(data.msg==2){
			          $("#u_kefu").html("跟踪人不能为空");
			           switchCode();
			        }else if(data.msg==13){
			         window.location.href="querBaseData.do";
			        }else if(data.msg==14){
			         window.location.href="querWorkData.do?btype="+btype;
			        }else{
			        	alert(data.msg);
			        	switchCode();
			        }
			      });
			      });
			    });
		    //===
		    function fff(){
		    	$.jBox("iframe:querykefu.do", {
		    		title: "选择客服",
		    		width: 800,
		    		height: 500,
		    		buttons: {  }
		    		});
		    }
		    
		    function ffff(f,d){
		    
		    	 $("#kefuname").html(f);
               $("#kefu").attr("value",d);
               window.parent.window.jBox.close() ;
		    	if($('#kefu').val() != ''){
		    	   $('#u_kefu').html('');
		    	}
		    }
		    //-===
		
		</script>
		
	</body>
</html>
