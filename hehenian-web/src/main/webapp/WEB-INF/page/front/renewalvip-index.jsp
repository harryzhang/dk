<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <jsp:include page="/include/head.jsp"></jsp:include>
    <link href="css/inside.css"  rel="stylesheet" type="text/css" />
</head>
<body>
<!-- 引用头部公共部分 -->
<jsp:include page="/include/top.jsp"></jsp:include>	
<div class="nymain">
  <div class="bigbox">
  <div class="til">会员续费</div>
  <div class="sqdk" style="background:none;">
    <div class="r-main" style="margin:0 auto; float:none; border:none;">
    <div class="rmainbox" >
    <div class="box01"  style="display:-none;">
    <p class="tips" style="color:#ff0000;">投资者：
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
    <td><s:if test="%{#request.renewalVIPMap.vipStatus ==2}">会员</s:if><s:else>非会员</s:else></td>
  </tr>
  <tr>
    <td align="right">用　户　名：</td>
    <td> ${renewalVIPMap.username}</td>
  </tr>
  <tr>
    <td align="right">姓　　　名：</td>
    <td>${renewalVIPMap.realName}</td>
  </tr>
  <tr>
    <td align="right">邮　　　箱：</td>
    <td>${renewalVIPMap.email}</td>
  </tr>
  <tr>
    <td align="right">会员到期时间：</td>
    <td>${renewalVIPMap.vipCreateTime}</td>
  </tr>
  <tr>
    <td align="right">验　证　码：</td>
    <td><input type="text" class="inp100x" id="code"/>
		 <img src="" title="点击更换验证码" style="cursor: pointer;"
		 	  id="codeNum" width="46" height="18" onclick="javascript:switchCode()" /></td>
  </tr>
  <tr>
    <td align="right">&nbsp;</td>
    <td style="padding-top:20px;"><a href="javascript:void(0);" id="savebtn" class="bcbtn">我要续费</a></td>
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
<script type="text/javascript" src="script/nav-zh.js"></script>
<script type="text/javascript" src="script/jquery.shove-1.0.js"></script>
<script>
$(function(){
    //样式选中
     $("#zh_hover").attr('class','nav_first');
	 $("#zh_hover div").removeClass('none');
	 $('#li_1').addClass('on');
	 $("#btn_img").click(function(){
		    var dir = getDirNum();
			var json = "{'fileType':'JPG,BMP,GIF,TIF,PNG','fileSource':'user/"+dir+"','fileLimitSize':0.5,'title':'上传图片','cfn':'uploadCall','cp':'img'}";
			json = encodeURIComponent(json);
			window.showModalDialog("uploadFileAction.do?obj="+json,window,"dialogWidth=500px;dialogHeight=400px");
			var headImgPath = $("#img").attr("src");
			if(headImgPath ==""){
				alert("上传失败！");	
			}
	  });
	  $("#savebtn").click(function(){
	       param['paramMap.code']=$('#code').val();
	       $.shovePost("renewalVIPSubmit.do",param,function(data){
		       if(data.msg = 'VIP续费成功'){
		    	   alert(data.msg);
					window.location.href = 'home.do';
					return ;
			    }
				alert(data.msg);
		   });
	  });
	  switchCode();
});		     
function uploadCall(basepath,fileName,cp){
	if(cp == "img"){
		var path = "upload/"+basepath+"/"+fileName;
		$("#img").attr("src",path);
		param['paramMap.imgPath']=path;
		$.shovePost("updatePersonImg.do",param,initCallBack);
	}
}
function initCallBack(data){
	alert(data.msg);
}
function getDirNum(){
	var date = new Date();
	var m = date.getMonth()+1;
	var d = date.getDate();
	if(m<10){
		m = "0"+m;
    }
	if(d<10){
	   d = "0"+d;
	}
	var dirName = date.getFullYear()+""+m+""+d;
	return dirName; 
}
function switchCode(){
	    var timenow = new Date();
	    $('#codeNum').attr('src','admin/imageCode.do?pageId=code&d='+timenow);
};
</script>
</body>
</html>
