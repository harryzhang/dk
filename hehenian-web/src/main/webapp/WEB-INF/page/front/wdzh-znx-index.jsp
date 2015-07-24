<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="css/hhncss.css"></link>

<script src="script/jquery-1.2.6.pack.js" type="text/javascript"></script>
<script type="text/javascript" src="script/jbox/jquery.jBox-2.3.min.js"></script>
<script type="text/javascript" src="script/jbox/jquery.jBox-zh-CN.js"></script> 
<script src="script/add_all.js" type="text/javascript"></script>
<script src="script/MSClass.js" type="text/javascript"></script>
<jsp:include page="/include/head.jsp"></jsp:include>

<style>
.wdzh_next_right .nd_jqgl_yfbdjk_one input{
margin-top: 10px;
margin-bottom: 10px;
}
</style>
<script type="text/javascript">
	$(function(){
		$(".header_two_right_ul li").hover(function(){
			$(this).find("ul").show();
		},function(){
			$(this).find("ul").hide();
		})
		
		$(".wdzh_top_ul li").eq(4).addClass("wdzhxxk");
		$(".wdzh_top_ul li").click(function(){
			var ppain=$(".wdzh_top_ul li").index(this);
			$(".wdzh_top_ul li").removeClass("wdzhxxk");
			$(this).addClass("wdzhxxk");
<%--			$(".wdzh_next").hide();--%>
<%--			$(".wdzh_next").eq(ppain).show();--%>
		})
		$(".tzjl_fwxy").click(function(){
			$(".tzjl_fwxyh").show();
		})
		$(".tzjl_fwxy1").click(function(){
			$(".tzjl_fwxy1h").show();
		})
		$(".grxx_aqzx_ul li:first").addClass("aqzxxk");
		$(".grxx_aqzx_ul li").click(function(){
			var wd= $(".grxx_aqzx_ul li").index(this);
			$(".grxx_aqzx_ul li").removeClass("aqzxxk");
			$(this).addClass("aqzxxk");
			
			$(".grxx_aqzx").hide();
			$(".grxx_aqzx").eq(wd).show();
		})
	})
</script>
</head>

<body>
<div style="position: absolute; height:54px; line-height:54px; background:#f3f3f3; width:100%; margin-top:151px !important; margin-top:177px;border-bottom:1px solid #e5e5e5;border-top:1px solid #e5e5e5;"></div>
<jsp:include page="/include/top.jsp"></jsp:include>	
<div class="cle"></div>
	<div class="wytz_center">
    <div class="mjd_tzlc_all"><jsp:include page="/include/menu_userManage.jsp"></jsp:include></div>
    	<div class="wdzh_top">
        
            <div class="cle"></div>
            <div class="wdzh_next" style="display:block;">
            	<div class="wdzh_next_left">
                	<ul>
<%--                        <li class="wdzh_next_left_ong"><a href="mailNoticeInit.do" style=" color:#e94718;">站内信</a></li>--%>
<%--                        <li><a href="userrrjc.do">网站积分</a></li>--%>
                      <li><a href="friendManagerInit.do">推广链接</a></li>
                        <li><a href="myReferee.do">我的推荐人</a></li>
                    </ul>
                </div>
                <div class="wdzh_next_right">
                    <div class="nd_jqgl_yfbdjk_one">
                        <input type="button" value="发送消息" class="scbtn" style="margin-left:0;" onclick="showWriteMail();"/>
                        <input type="button" value="发件箱" class="scbtn" onclick="showSendMails();"/>
                        <input type="button" value="收件箱" class="scbtn" onclick="showReceiveMails();"/>
                    </div>
                    <div class="box">
       				    <span id="sysInfo"></span>
     				</div>
	  				<div class="box" style="display:none;" id="receiveInfos">
        				<span id="receiveInfo"></span>
    				</div>
      				<div class="box" style="display:none;" id="sendInfos">
        				<span id="sendInfo"></span>
    			    </div>
      				<jsp:include page="wdzh-znx-sendMail.jsp"></jsp:include>
                </div>
                <div class="cle"></div>
            </div>
            <div class="cle"></div>
        </div>
    </div>
<div class="cle"></div>
<jsp:include page="/include/footer.jsp"></jsp:include>
<script type="text/javascript">
$(function(){
 	   initListInfo();
	});
	function initListInfo(){
		var param={};
		 param["pageBean.pageNum"]=1;
	     $.post("queryReceiveMailsInit.do",param,function(data){
	       $("#receiveInfo").html(data);
	       $("#receiveInfos").css('display','block');
	    });
	 }

	
	function switchCode(){
		var timenow = new Date();
		$("#codeNum").attr("src","admin/imageCode.do?pageId=userlogin&d="+timenow);
	}
	
	
	
    //收件人
     $("#receiver").blur(function(){
	     if($("#receiver").val()==""){
	       $("#s_receiver").html("*收件人不能为空");
	     }else{
	       checkRegister();
	     }
	  });
	  //标题
     $("#title").blur(function(){
	     if($("#title").val()==""){
	       $("#s_title").html("*标题不能为空");
	     }else{
	       $("#s_title").html("");
	     }
	  });
	  //验证码
     $("#code").blur(function(){
	     if($("#code").val()==""){
	       $("#s_code").html("*验证码不能为空");
	     }else{
	       $("#s_code").html("");
	     }
	  });
     
     //内容框
     $("#content").blur(function(){
	     if($("#content").val()==""){
	       $("#s_content").html("*内容不能为空");
	     }else{
	       $("#s_content").html("");
	     }
	  });
      
 //检查用户名是否有效
  function checkRegister(){
      param["paramMap.receiver"] = $("#receiver").val();
		$.post("judgeUserName.do",param,function(data){
            if(data == 1 ){
               $("#s_receiver").html("*收件人不存在或者还不是您的好友！");
            }else{
               $("#s_receiver").html("");
            }
		});
   }
     function returnToPage_(pNum,type){
     if(type == 2){ //系统邮件
       returnToPage_s(pNum);
       return;
     }else if(type == 1){//收件箱
       returnToPage_r(pNum);
       return;
     }else if(type ==100){//发件箱
        returnToPage_ss(pNum);
        return;
     }
	}
   
   function addMail(){
   	param["paramMap.receiver"] = $("#receiver").val();
   	param["paramMap.title"] = $("#title_s").val();
   	param["paramMap.content"] = $("#content").val();
   	param["paramMap.code"] = $("#code").val();
   	param["paramMap.pageId"] = "userlogin";
   	if($("#receiver").val()==""){
	       $("#s_receiver").html("*收件人不能为空");
	       return;
	     }
	     if($("#title_s").val()==""){
	       $("#s_title").html("*标题不能为空");
	       return;
	     }
	     if($("#content").val()==""){
	       $("#s_content").html("*内容不能为空");
	       return;
	     }
	     if($("#code").val()==""){
	       $("#s_code").html("*验证码不能为空");
	       return;
	     }
	     //有错误提示的时候不提交
	     if($("#s_receiver").text()!="" || $("#s_title").text()!="" ||$("#s_content").text()!=""
	       ||$("#s_code").text()!=""){
	          return;
	       }
   	$.post("addMail.do",param,function(data){
   	   if(data == 5){
   	     $("#s_code").html("验证码错误");
   	     return;
   	   }else if(data == 1){
   	     alert("邮件发送失败，请重新发送");
   	     return;
   	   }else if(data == 8){
   	     alert("你是黑名单用户不能发生站内信");
   	     return;
   	   }else{
   	   	 alert("邮件发送成功");
   	   	 $("#title_s").attr("value","");
   	   	 $("#code").attr("value","");
   	   	 $("#receiver").attr("value","");
   	   	 $("#content").attr("value","");
   	   }
   	});
   }

   function showWriteMail(){
		 $("#sendInfos").css('display','none');
         $("#writeMail").css('display','block');
         $("#receiveInfos").css('display','none');
    }
   
   function showReceiveMails(){
	  var param={};
      param["pageBean.pageNum"]=1;
      $.post("queryReceiveMailsInit.do",null,function(data){
         $("#receiveInfo").html(data);
         $("#sendInfos").css('display','none');
         $("#writeMail").css('display','none');
         $("#receiveInfos").css('display','block');
      });
   }
   
   function showSendMails(){
	  var param={};
      param["pageBean.pageNum"]=1;
      $.post("querySendMailsInit.do",null,function(data){
         $("#sendInfo").html(data);
         $("#receiveInfos").css('display','none');
         $("#writeMail").css('display','none');
         $("#sendInfos").css('display','block');
      });
   }
   //显示系统消息
   function showSysMails(){
      param["pageBean.pageNum"]=1;
      $.post("querySysMailsInit.do",null,function(data){
         $("#sysInfo").html(data);
      });
   }
   
   //收件箱全选
   function checkAll_Receive(e){
 		if(e.checked){
 			$(".re").attr("checked","checked");
 		}else{
 			$(".re").removeAttr("checked");
 		}
 	 }
 	 
 	 function checkAll_Send(e){
 		if(e.checked){
 			$(".se").attr("checked","checked");
 		}else{
 			$(".se").removeAttr("checked");
 		}
 	 }
 	 
 	 function checkAll_Sys(e){
 		if(e.checked){
 			$(".sys").attr("checked","checked");
 		}else{
 			$(".sys").removeAttr("checked");
 		}
 	 }
 	 
	//弹出窗口关闭
 		function close(){
 			 ClosePop();  			  
 		}
</script>
</body>
</html>

