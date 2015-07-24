<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="../css/css.css" />
<script src="../script/jquery-1.7.1.min.js" type="text/javascript"></script>
<script>
$(function(){

  $("#but_yes").click(function(){
  var param = {};
     param["paramMap.valid"] = $("input[name='paramMap_valid']:checked").val();// 0,1
     param["paramMap.option"]  = $("#paramMap_content").val();
     param["paramMap.userId"] = ${map.id};
     param["paramMap.materAuthTypeId"] = ${map.materAuthTypeId}
     $.post("Updatematerialsauthindex.do",param,function(data){
         if(data==1){
           $("#u_tip").html("请勾选审核状态");
         }else if(data==0){
          $("#u_tip").html("审核观点不能为空");
         }else if(data==3){
           alert("审核失败");
         }else if(data==2){
           window.location.href='queryPerUserPicturMsginitindex.do?userId='+${userId}
         }else if(data==4){
            window.location.href='queryselect.do?userId='+${userId};
         }
         else{
         alert("审核失败");
         }
         
     });
  });

   


});

</script>
<script>


</script>
</head>
<body>
<div class="">
<div class="nymain">
  <div class="wdzh">
    <div class="r_main">
      <div class="box">
       <h2 class="otherh2x">证件查看</h2>
      <div class="boxmain2">
       <div class="biaoge" style="margin-top:0px;">
          </div>
          <div class="biaoge" >
          <form>
          <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
            <div>用户名:${map.username }</div>
            <hr/>
        </tr>
  <tr>
  <div>
  认证种类:${map.tmtname}
  </div>
   <hr/>
    </tr>
  <tr>
  <div>
  证件图片<img src="../${map.imgPath}" width="200px" height="200px"/>
  
 </div>
  <hr/>
    </tr>
  <tr>
     <hr/>
  <div>
  
  证件审核
  </div>
    </tr>
  <tr>
  <div>
  文件是否可见：    &nbsp;&nbsp;&nbsp;&nbsp; <input type="checkbox"/>勾选则可见：
  </div>
    </tr>
  <tr>
   <div>
    审核状态  :<input type="radio" name="paramMap_valid" value="1" onclick="javascript:$('#u_tip').html('')"
   		<s:if test='#request.map.auditStatus == 3'>checked="checked"</s:if>
												<s:else></s:else>
    />有效  <input type="radio" name="paramMap_valid" value="0"
    <s:if test='#request.map.auditStatus == 2'>checked="checked"</s:if>
												<s:else></s:else>
    />无效
   </div>
         </tr>
         <tr>
          <div>
    审核观点 : <s:textarea name="paramMap.content" cols="40" rows="5" id="paramMap_content" value="%{#request.map.tmoption}"/>
      </div>
         </tr>  
         
                  <tr>
          <div>
      </div>
         </tr>  
         
          </table>
      </form>
          </div>
       </div>
</div>
    </div>
  </div>
</div>
  <div class="footer_bottom"> 版权所有 © 2013 合和年在线  sp2p.demo.eimslab.cn  备案号：<span>粤ICP备11094446号</span><br/>
  客服热线（9：00-18：00）: 4000-521-600 </div>
</body>
</html>
