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
     param["paramMap.valid"] = $("input[name='paramMap_valid']:checked").val();//
     param["paramMap.visiable"] = $("#visiable").val();// 
     param["paramMap.option"]  = $("#paramMap_content").val();
     param["paramMap.userId"] = ${map.id};
     param["paramMap.tmdid"] = ${map.tmdid};
     param["paramMap.tmid"] = ${map.tmid};
     param["paramMap.materAuthTypeId"] = ${map.materAuthTypeId}
     $.post("Updatematerialsauthindex.do",param,function(data){
         if(data==1){
           $("#u_tip").html("请勾选审核状态");
         }else if(data==0){
          $("#u_tip").html("审核观点不能为空");
         }else if(data==3){
           alert("审核失败");
         }else if(data==2){
           //window.location.href='queryPerUserPicturMsginitindex.do?userId=${map.id}'
           javascript:history.go(-1);
         }else if(data==4){
            //window.location.href='queryselect.do?userId=${map.id}'
             javascript:history.go(-1);
         }
         else{
         alert("审核失败");
         }
         
     });
  });

   


});


</script>
</head>
<body>


<div class="">
<div class="nymain">
  <div class="wdzh">
    <div class="r_main">
      <div class="box">
       <h2 class="otherh2x">基本资料证件审核</h2>
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
  认证种类:${map.tmyname}
  </div>
   <hr/>
    </tr>
  <tr>
  <div>
  证件图片<img src="../${map.imagePath}" width="200px" height="200px"/>
  
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
      &nbsp;&nbsp;&nbsp;&nbsp; 
  <s:if test="#request.map.visiable == 1">
  文件是否可见：<input type="checkbox" name="param_visiable" value="1" id="visiable"/>勾选则可见：
  </s:if>
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
    审核观点 : <s:textarea name="paramMap.content" cols="40" rows="5" id="paramMap_content" value="%{#request.map.tmdoption}"  
   
    />
      </div>
         </tr>  
         
                  <tr>
          <div>
          <s:if test='#request.map.auditStatus == 3'>
          </s:if>
        <s:elseif test="#request.map.auditStatus == 2"></s:elseif>
        <s:elseif test="#request.map.auditStatus == 1"><input type="button" value="审核此证件" class="scbtn" id="but_yes"/></s:elseif>
           	<span style="color: red; float: none;" id="u_tip"
														class="formtips"></span>
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

<script>
$(function(){
var  ttt = ${map.auditStatus}
  if(ttt == '3' || ttt=='2'){
      
    $("#paramMap_content").attr("disabled","true");
  }
});

</script>
</body>
</html>
