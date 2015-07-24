<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<jsp:include page="/include/head.jsp"></jsp:include>
<link href="css/inside.css"  rel="stylesheet" type="text/css" />
</head>
<body>
<!-- 引用头部公共部分 -->
<jsp:include page="/include/top.jsp"></jsp:include>	

<div class="nymain">
  <div class="bigbox">
  <div class="til">成为理财人</div>
  <div class="sqdk" style="padding-top:55px; padding-bottom:80px; padding-left:60px;">
    <div class="logintab">
    <form id="form1">
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td colspan="2" align="center">绑定身份证、手机号码即可成为合和年在线理财人</td></tr>
  <tr>
    <td align="right">姓名：<span style="color: red; float: none;">*</span></td>
    <td><input type="text" class="inp188" id="realName"/>
    <span style="color: red; float: none;" id="u_realName"	class="formtips"></span>
    </td>
  </tr>
  <tr>
    <td align="right">身份证号：<span style="color: red; float: none;">*</span></td>
    <td ><input type="text" class="inp188"  id="idNo"/>
    <span style="color: red; float: none;" id="u_idNo" class="formtips"></span>
    </td>
  </tr>
  <tr>
    <td align="right">确认身份证号：<span style="color: red; float: none;">*</span></td>
    <td><input type="text" class="inp188" id="idNo2"/>
    <span style="color: red; float: none;" id="u_idNo2" class="formtips"></span>
    </td>
  </tr>
  <tr>
    <td align="right">手机号码：<span style="color: red; float: none;">*</span></td>
    <td ><input type="text" class="inp188" id="cellPhone"/>
    <span style="color: red; float: none;" id="u_cellPhone" class="formtips"></span>
    </td>
  </tr>
  <tr>
    <th align="right">&nbsp;</th>
    <td><a id="clickCode" class="yzmbtn">发送手机验证码</a></td>
  </tr>
  <tr>
    <td align="right">验证码：<span style="color: red; float: none;">*</span></td>
    <td><input type="text" class="inp188" id="send_phoneCode" />
    <input id="type" name="type" value="tofinance" type="hidden"/>
    <span style="color: red; float: none;" id="u_code"
		class="formtips"></span>
    </td>
  </tr>
  <tr>
    <th align="right">&nbsp;</th>
    <td class="tyzc"><a href="rechargeInit.do">我要充值</a> <a href="queryFundrecordInit.do">账户余额</a></td>
  </tr>
  <tr>
    <th align="right">&nbsp;</th>
    <td><a href="javascript:void(0)" class="dlbtn" onclick="addBecomeFinancer();">成为理财人</a></td>
  </tr>
  <tr>
    <td colspan="2" align="left" style="line-height:26px; padding-top:15px; color:#888;">注意：若第一次验证失败，即姓名与身份证号码不匹配。   第二次验证，将按审核成本5元/次的标准收取验证费，请务必填写您的真实信息！进行手机绑定后，本手机号将作为合和年信贷和您确认贷款的途径之一，请使用您常用的手机号码进行绑定，您的手机号码不会以任何形式被泄露。</td>
    </tr>
    </table>
</form>
    </div>
    <div class="tip">
    <ul><li>帮助他人 快乐自己 收获利息</li>
<li>助您创业 资金周转 分期偿还</li>
<li>收益稳定可靠回报高</li>
<li>交易安全快捷有保障</li></ul>
    <div class="loginbtn">
    
    </div>
    </div>
    <div class="renpic" style="top:50px;">
    
    </div>
  </div>
  </div>
</div>
<!-- 引用底部公共部分 -->     
<jsp:include page="/include/footer.jsp"></jsp:include>
 <script type="text/javascript" src="${path}/script/jquery-1.7.1.min.js"></script>
 <script type="text/javascript" src="script/jquery.shove-1.0.js"></script>
<script type="text/javascript" src="script/nav.js"></script>
<script type="text/javascript" src="script/nav-lc.js"></script>
	<script>
	  //手机号码绑定
		  var timers=60;
		  var tipId;
		 
		   $(function(){
		       $("#clickCode").click(function(){
		           var phone=$("#cellPhone").val();
		           //验证手机号码是
		           if($("#cellPhone").val()==""){
                      $("#u_cellPhone").attr("class", "formtips onError");
		              $("#u_cellPhone").html("请填写手机号码！");
                   }else if((!/^1[3,5,8]\d{9}$/.test($("#cellPhone").val()))){ 
                      $("#u_cellPhone").attr("class", "formtips onError");
	                  $("#u_cellPhone").html("请正确填写手机号码！");
                   }else{
                      $("#u_cellPhone").attr("class", "formtips");
	                  $("#u_cellPhone").html(""); 
	                 
	                  
	                 if($("#clickCode").html()=="重新发送验证码"||$("#clickCode").html()=="发送手机验证码") {  
	                    $.post("sendSMS.do","phone="+phone,function(data){
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
$(function(){
	
	 //样式选中
     $("#licai_hover").attr('class','nav_first');
	 $("#licai_hover div").removeClass('none');
	 $('.tabmain').find('li').click(function(){
	    $('.tabmain').find('li').removeClass('on');
	    $(this).addClass('on');
	 });
	 
	 
	$("#form1 :input").blur(function(){
	    //验证手机号码
		      if($(this).attr("id")=="cellPhone"){
			      if( $(this).val() ==""){
			         $("#u_cellPhone").attr("class", "formtips onError");
					 $("#u_cellPhone").html("*请填写手机号码！");
			      }else if((!/^1[3,5,8]\d{9}$/.test($("#cellPhone").val()))){ 
			       $("#u_cellPhone").attr("class", "formtips onError");
				 $("#u_cellPhone").html("*请正确填写手机号码！");
			      }else{
			           $("#u_cellPhone").attr("class", "formtips");
				       $("#u_cellPhone").html(""); 
			      }
	     	}
	  //真实姓名
	  		if($(this).attr("id")=="realName"){
					if($(this).val() ==""){
				      	$("#u_realName").attr("class", "formtips onError");
				      	$("#u_realName").html("*请填写真实姓名！");
				    }else if($(this).val().length <2 || $(this).val().length> 20){   
		            	$("#u_realName").attr("class", "formtips onError");
		                $("#u_realName").html("*名字长度为2-20个字符"); 
		            }else{   
		            	$("#u_realName").attr("class", "formtips");
		                $("#u_realName").html(""); 
		            } 
	          }
			  //========
			  //身份号码
			  if($(this).attr("id")=="idNo"){
			     var isIDCard1 = new Object();
			     var isIDCard2 = new Object();
			     //身份证正则表达式(15位) 
			     isIDCard1=/^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$/; 
			     //身份证正则表达式(18位) 
			     isIDCard2=/^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])((\d{4})|\d{3}(x|X))$/; 
				    if($(this).val() ==""){
				     $("#u_idNo").html("*请填写身份证号码");
				    }else if(isNaN($(this).val())){
				      $("#u_idNo").html("*请填写正确的身份证号码");
				    }else if(isIDCard1.test($(this).val())||isIDCard2.test($(this).val())){
				        $("#u_idNo").html("");
				    }else {
				        $("#u_idNo").html("身份证号码不正确");
				    
				      //===
				    }
		  		}
		  		
		  		if($(this).attr("id")=="idNo2"){
				    if($(this).val() ==""){
				     $("#u_idNo2").html("*请填写确认身份证号码");
				    }else if($("#idNo").val()!=$("#idNo2").val()){
				      $("#u_idNo2").html("*两次身份证号码不一样");
				    }else {
				     $("#u_idNo2").html("");
				      //===
				    }
		  		}
		  		
		  		if($(this).attr("id")=="send_phoneCode"){
				    if($(this).val() ==""){
				     $("#u_code").html("*请输入验证码");
				    }else {
				     $("#u_code").html("");
				    }
		  		}
		  		
		  		/*$("#send_phoneCode").click(function(){
					var param = {"type":"tofinance"};
					$.shovePost("sendPhoneCode.do",param,function(data){
						if(data==1){
							alert("发送成功");
						}
						alert("验证码：" + data);
					});
	            });	*/
	   });
	
	});
	
	function addBecomeFinancer(){
	
	  if($("#realName").val()=="" || $("#cellPhone").val()=="" ||
          $("#idNo").val()=="" || $("#idNo2").val()=="" || $("#send_phoneCode").val()==""){
              alert("请先填写完整的信息");
              return;
          }
        //验证不通过
          if($("#u_realName").text()!="" ||
          $("#u_cellPhone").text()!="" ||$("#u_idNo").text()!="" || $("#u_idNo2").text()!=""||
          $("#u_code").text()!=""){
            alert("请先填写完整的信息");
            return;
          }
		param["paramMap.type"] = "tofinance";
		param["paramMap.cellPhone"] = $("#cellPhone").val();
		param["paramMap.realName"] = $("#realName").val();
		param["paramMap.idNo"] = $("#idNo").val();
		param["paramMap.send_phoneCode"] = $("#send_phoneCode").val();
		$.shovePost("addBecomeFinance.do",param,function(data){
		     if(data==7){
	        // $("#u_cellPhone").html("手机号码长度不对！")
	         alert("手机号码无效！");
	          return;
	       }
	       if(data==8){
	        // $("#u_cellPhone").html("手机号码长度不对！")
	         alert("请填写手机号");
	          return;
	       }
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
		    
			if(data==1){
				alert("成为理财人失败，请重新申请");
				return;
			}
			alert("恭喜你完整填写信息，请耐心等待审核");
			window.location.href='financerWaiting.do';
			/*$("#realName").attr("value","");
			$("#cellPhone").attr("value","");
			$("#idNo").attr("value","");
			$("#idNo2").attr("value","");
			$("#send_phoneCode").attr("value","");*/
		});
	}
</script>
</body>
</html>
