<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<head>
<link href="css/css.css" rel="stylesheet" type="text/css" />
<style>
.rmainbox .tcbox .tcmain .ysctab table tr td {
 font-size:  12px;
}
.nymain a{
 font-size:  12px;
}
.scbtn{
 font-size:  12px;
}
</style>
<script src="script/jquery-1.7.1.min.js" type="text/javascript"></script>
  <script>
$(function(){
	$('.tabmain').find('li').click(function(){
	$('.tabmain').find('li').removeClass('on');
	$(this).addClass('on');
	$('.lcmain_l').children('div').hide();
    $('.lcmain_l').children('div').eq($(this).index()).show();
	})
	})
</script>
<script>
$(function(){
	$('.til01 li').click(
	function(){
		$('.til01 li').removeClass('on');
		$(this).addClass('on');
		$('.rmainbox').children('div').hide();
		$('.rmainbox').children('div').eq($(this).index()).show();
	}
	)
})
</script>
<script>
$(function(){
	$('#scbtn01').click(
	function(){
		$('.tcbox').show();
	}
	)
	$('#gbtck').click(
	function(){
		$('.tcbox').hide();
	}
	)
})
</script>
</head>
<body>
<div class="nymain" style="width: 540px;margin:0px;">
  <div class="bigbox" style="border:none;">
  <div class="sqdk" style="background:none;height:auto;padding: 0px;">
    <div class="r-main" style="border:none; float:none; width:auto;">
    <div class="rmainbox" style="padding-left:0px; padding-right:0px; padding:0px;">
    <div class="tcbox" style="display:-none; position:static; margin:0px auto; padding:0px;">
      <div class="tcmain">
      
         <h3>视频认证：</h3>
      <p>什么是视频认证？只有通过视频您才能获得贷款。</p>
      <strong>认证说明：</strong><br />
1、认证说明资料更新中，敬请期待...

      
     
    
    
    
      <div class="xzzl">
     
       <table width="100%" border="0" cellspacing="0" cellpadding="0">
       提交视频认证申请：<input type="checkbox" name="ccc" id="ccc" value="1"/>
  <tr>
    <td width="18%">&nbsp;</td>
    <td width="82%" style="padding-top:20px;"><a href="javascript:void(0)" class="bcbtn" id="ggg">提交审核</a></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td class="tishi" style="color: red;">合和年在线是一个注重诚信的网络平台。如果我们发现您上传的资料系伪造或有人工修改痕迹，合和年信贷会将你加入系统黑名单，永久取消您在合和年信贷网的借款资格。</td>
  </tr>
      </table>
       
     
         
      
  
      </div>
      </div>
    </div>
    </div>
    </div>
  </div>
  </div>
</div>
</body>
<script>
$(function(){
   $('#ggg').click(function(){
       var param = {};
    param["paramMap.ccc"] = $("input[name='ccc']:checked").val();
    if(($("input[name='ccc']:checked").val())== undefined ||($("input[name='ccc']:checked").val())==''){
     alert('请选择视频认证申请');
     return ;
    }
    param["paramMap.tmid"] =${tmid };
    $.post("updateShiping.do",param,function(data){
    	if(data == "virtual"){
			window.location.href = "noPermission.do";
			return ;
	    }
      if(data==1){
      alert('提交审核申请失败');
      }else if(data==2){
      alert('请选择视频认证申请');
      }
      else if(data==3){
           alert('提交审核申请失败')
      }
      else if(data==4){
        alert('提交视频认证成功')
        window.parent.shipingcheckf();
      }
      else if(data==5){
        alert('提交审核申请失败');
      }else{
        alert('提交审核申请失败');
      }
    });
   
   });

});

</script>



    
    
