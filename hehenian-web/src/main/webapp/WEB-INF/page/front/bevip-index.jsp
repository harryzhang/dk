<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <jsp:include page="/include/head.jsp"></jsp:include>
    <link href="css/inside.css"  rel="stylesheet" type="text/css" />
    <link id="skin" rel="stylesheet" href="css/jbox/Gray/jbox.css" />
    <script type="text/javascript" src="script/jquery-1.7.1.min.js"></script>
    <script type="text/javascript" src="css/popom.js"></script>
    <style>
    .rmainbox10 .tipd{
    background-color: #F0FAFF;
    line-height: 24px;
    margin-top: 20px;
    padding: 8px 16px}
    
    .rmainbox10 .tab {
    padding-left: 60px;
    padding-right: 60px;
    padding-top: 20px;
    line-height:40px;
}
    </style>

    <script>
    /*
    $(function(){
     var t = '${userMap.realName}'==''?'':'${userMap.realName}';
    if(t!=''){
     $("#xm").show();
    }
    });
    */
    </script>
    
    <script>
    
    	    function fff(){
    	    	   $.jBox("iframe:querykefu.do", {
   		    		title: "选择客服",
   		    		width: 600,
   		    		height: 400,
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
    
    </script>
    <script>
    	
		      function switchCode(){
			     var timenow = new Date();
			     $('#codeNum').attr('src','admin/imageCode.do?pageId=vip&d='+timenow);
		    };
    </script>
    <script>
    $(function(){
    
    //样式选中
     $("#zh_hover").attr('class','nav_first');
	 $("#zh_hover div").removeClass('none');
	 $('#li_1').addClass('on');
     $('#context').blur(function(){
		        if($('#context').val()==""){
		            $('#u_content').html("内容不能为空");
		              return ;
		         }else{
		          $('#u_content').html("");
		         }
		      }); 
		       $('#code').blur(function(){
		        if($('#code').val()==""){
		            $('#u_code').html("验证码不能为空");
		            return ;
		         }else{
		          $('#u_code').html("");
		         }
		         
		      }); 
    
    
    });
        
    </script>
    <script>
    $(function(){
    
          $("#vip_btn").click(function(){
		      var param= {};
		      param["paramMap.pageId"] = "vip";
		      param["paramMap.code"] = $("#code").val();
		     // param["paramMap.content"] = $("#context").val();
		      param["paramMap.kefu"] = $("#kefu").val();
		      $.post("updateUserVip.do",param,function(data){
		      	if(data.msg == "virtual"){
					window.location.href = "noPermission.do";
					return ;
	     		}
		        if(data.msg==1){
		          alert("申请vip成功");
		          window.location.href='home.do';
		          //window.location.href="jumptopiturepage.do";
		        }else{
							alert(data.msg);
			        }
		      });
		      });
    
    
    
    });
    
    
    </script>
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
      <div class="box" style="border-bottom:none;">
      <h2>申请vip</h2>
      </div>
      <div class="rmainbox10">
    <p class="tipd" style="color:#ff0000;">投资者：
      <br />
      网站合作商提供投资担保，享受100%本金保障。对于担保标、推荐标，还能100%保利息。（普通用户仅保障本金）
有专业客服跟踪服务，体验尊贵感受。
享有尊贵VIP身份标识。<br />
借款者：<br />享有借款资格，及时缓解资金压力。
参与网站举行的各种活动。</p>
    <div class="tab">
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td align="right">您的状态是：</td>
    <td>	<s:if test="#request.userMap.vipStatus==2">尊敬的vip会员</s:if>
											<s:else>普通会员</s:else></td>
  </tr>
  <tr>
    <td align="right">用　户　名：</td>
    <td> ${userMap.username }</td>
  </tr>
  <s:if test="#request.userMap.realName!=null">
  <tr>
    <td align="right">姓　　　名：</td>
    <td>${userMap.realName }</td>
  </tr>
  </s:if>
  <tr>
    <td align="right">邮　　　箱：</td>
    <td>	${userMap.email }</td>
  </tr>
  <tr>
    <td align="right"><span style="color: red; float: none;" 
														class="formtips">*</span>选择客服：</td>
    <td>		<a id = "kefuname">${userMap.kefuname }</a>
												<input type="hidden" value="${userMap.kfid }" name="paramMap.kefu" id="kefu"/>
											    <input type="button" id="btn_sp" class="scbtn"
									             style="cursor: pointer;" value="选择客服" onclick="fff()"/>
												
												<span style="color: red; float: none;" id="u_kefu"
														class="formtips"></span></td>
  </tr>
 <!--   <tr>
    <td align="right">	<span style="color: red; float: none;" 
														class="formtips">*</span>备 注：</td>
    <td>
    <textarea class="txt420" name="paramMap.content" id="context"></textarea>
												<span style="color: red; float: none;" id="u_content"
														class="formtips"></span>
    </td>
  </tr> -->
  <tr>
    <td align="right">	<span style="color: red; float: none;" 
														class="formtips">*</span>验 证 码：</td>
    <td>		<input type="text" class="inp100x" name="paramMap.code" id="code"/>
												<!--  <img src="admin/imageCode.do?pageId=vip" width="52" height="20" id="codeN"/>-->
												 <img src="admin/imageCode.do?pageId=vip" title="点击更换验证码" style="cursor: pointer;"
									id="codeNum" width="46" height="18" onclick="javascript:switchCode()" />
												
												
												<span style="color: red; float: none;" id="u_code"
														class="formtips"></span></td>
  </tr>
  <tr>
    <td align="right">&nbsp;</td>
    <td style="padding-top:20px;"> <input  type="button" id="vip_btn"  class="bcbtn" value="我要申请"/></td>
  </tr>
    </table>

    </div>
    </div>
    </div>


</div>
<!-- 引用底部公共部分 -->     
<jsp:include page="/include/footer.jsp"></jsp:include>
 <script type="text/javascript" src="script/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="script/jbox/jquery.jBox-2.3.min.js"></script>
<script type="text/javascript" src="script/jbox/jquery.jBox-zh-CN.js"></script>
</body>
</html>
