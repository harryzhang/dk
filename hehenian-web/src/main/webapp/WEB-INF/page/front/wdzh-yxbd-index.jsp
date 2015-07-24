<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<jsp:include page="/include/head.jsp"></jsp:include>
    <link href="css/inside.css"  rel="stylesheet" type="text/css" />

</head>

<body>
<!-- 引用头部公共部分 -->
<jsp:include page="/include/top.jsp"></jsp:include>	

<div class="nymain">
  <div class="wdzh">
    <div class="l_nav">
      <div class="box">
        <!-- 引用我的帐号主页左边栏 -->
         <%@include file="/include/left.jsp" %>
      </div>
    </div>
    <div class="r_main">
      <div class="tabtil">
        <ul><li class="on">邮箱设置</li>
        </ul>
        </div>
   
   
   
   
  <!-- 邮箱 -->
  <div class="box" >
        <div class="boxmain2" style="padding-top:10px;">
         <div class="biaoge2" style="margin-top:0px;">
    <table width="100%" border="0" cellspacing="0" cellpadding="0" style="line-height:42px;">
  <tr>
    <th colspan="2" align="left" style="padding-top:0px;"> 邮箱设置
    </th>
    </tr>
  <tr>
    <td align="right" style="padding-right:40px;">     
    邮箱地址：
    </td>
    <td>
      <s:if test="paramMap.flag==2">
     ${paramMap.email }<a style="color: red;">(该邮箱已绑定)</a>
     </s:if>
     <s:else>
     
     <input type="text" name="paramMap.email" class="inp188" id="mails" value="${paramMap.email }"/>
      <span style="color: red;">*<s:fielderror
											fieldName="paramMap.email" />
							</span><span><input type="button" class="chaxun" value="发送邮件" id="saveEmail"/><a  style="color: red;">(你还没绑定邮箱)</a></span>
							
	</s:else>	
    </td>
  </tr>
  <tr>
  	<div id="ok" style="color: red; height: 20px;" >${paramMap.msg }</div>
  </tr>
  <tr>
    <td align="right" style="padding-right:40px;">
    
    </td>
    </tr>
    </table>
    </div>
    </div>
</div>
  
  
  
 <!-- - --> 
   
   
    </div>
  </div>
</div>

 <!-- 引用底部公共部分 -->     
<jsp:include page="/include/footer.jsp"></jsp:include>
<script type="text/javascript" src="script/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="script/jquery.shove-1.0.js"></script>
<script type="text/javascript" src="script/jquery.zclip.min.js"></script>	
 <script>
$(function(){
    $("#zh_hover").attr('class','nav_first');
	$("#zh_hover div").removeClass('none');
	$('#li_16').addClass('on');
	$('#yq_address_btn').css('cursor','pointer');
    $('#yq_address_btn').click(function(){
        if($.browser.msie){
           var txt = '复制文本到剪贴板:\n\n';
           txt = txt+$('#yq_address_input').html();
           window.clipboardData.setData('text', $('#yq_address_input').html());
           alert(txt);
        }
    });
    $("#attention").click(function(){
            var param = {};
          param["paramMap.id"] =${user.id}
          param["paramMap.attention"] ="attention";
          $.shovePost("userFrends.do",param,function(data){
           $("#userfrends").html(data);
         });
    });
    
	$('.tabtil').find('li').click(function(){
	    $('.tabtil').find('li').removeClass('on');
	    $(this).addClass('on');
	    $('.tabtil').nextAll('div').hide();
         $('.tabtil').nextAll('div').eq($(this).index()).show();
	});
	init();
});
function init(){
  if(!$.browser.msie){
      $('#yq_address_btn').zclip({
         path:'script/ZeroClipboard.swf',
         copy:function(){return $('#yq_address_input').html();}
      });
  }
}
</script>
<script>
 $(function(){
 
 
 
 //提交邮箱添加
	$("#saveEmail").click(function(){
	
	
				    var param = {};
			       param["paramMap.email"] = $("#mails").val();
			       if(/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/.test($("#mails").val())){
						        $.post("SendUserEmailSet.do",param,function(data){
						        if(data.mailAddress=='0'){
						             alert("邮箱不能为空");
						        }else if(data.mailAddress=='1'){
						          $("#ok").html("该邮箱不存在");
						        }else if (data.mailAddress=='4'){
						        	 $("#ok").html("该邮箱已被绑定,请重新输入");
							    }else{
						          $("#ok").html("邮件已经发送到你的邮箱,请<a href=http://"+data.mailAddress+"  target='_blank'>登录</a>到你的邮箱验证");
						        }
						       });
			       }else{
			         alert("邮箱输入有误");
			       }
			       
       
       
       
       		/*
	        var param = {};
	        param["paramMap.email"] = $("#mails").val();
	        $.post("addEmails.do",param,function(data){
			        if(data.msg=="添加成功"){
			         alert("添加成功");
			         window.location.reload();
			        }else{
			          alert(data.msg);
			        }
	        });
			addEmails
			*/
       
			
			
		});
 
 
 
 
 });
	
	</script>
	<script>
  $(function(){
      var flag = '${paramMap.msg}';
      if(flag=='邮箱绑定成功'){
       alert(flag);
       window.location.href='emailManagerInit.do';
      }       
  });
	</script>
</body>
</html>

