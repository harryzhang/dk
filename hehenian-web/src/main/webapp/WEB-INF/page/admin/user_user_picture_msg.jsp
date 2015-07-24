<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="../css/user_css.css" />
<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
<script src="../script/jquery-1.7.1.min.js" type="text/javascript"></script>
<script src="../layer/layer.mintest.js" type="text/javascript"></script>
<script src="../layer/layertest.js" type="text/javascript"></script>
<script>
 $(function(){
 	
 	
 	
 	
 var flag = true;
  $("#tb_yes").click(function(){
  if($("#score").val() == ""){
  flag = true;
     $("#s_tip_").html("(分数不能为空)");
     return;
  }
  
  if($("#s_tip_").html()!=""){
  flag = true;
     return;
  }
  if(flag){
    flag = false;
    var answer = confirm("你确认该项审核成功吗")
  if (!answer){
   flag = true;
  return false;
  }
  
  
  var param = {};
  param["paramMap.flag"] = 'a';
  param["paramMap.userId"] = '${userId}';
  param["paramMap.tmId"] = '${tmId}';
  param["paramMap.materAuthTypeIdStr"] = '${materAuthTypeIdStr}';
  param['paramMap.content'] = $("#content").val();
  param["paramMap.score"] = $("#score").val();
  $.post("updateallcheck.do",param,function(data){
  if(data==1){
      $("#s_tip").html("审核成功");
      window.location.href='queryPerUserPicturMsginitindex.do?userId=${userId}&materAuthTypeId=${materAuthTypeIdStr}'
       flag = true;
  }else if(data==0){
   $("#s_tip").html("审核失败");
     flag = true;
  }else if(data==3){
  $("#s_tip").html("分数不能为空");
     flag = true;
  }else if(data==4){
   $("#s_tip").html("审核观点不能为空");
      flag = true;
  }else if(data==5){
   alert("必须审核完证件明细才能进行此操作");
      flag = true;
  }else if(data==6){
    $("#s_tip").html("分数必须为数字");
       flag = true;
  }else if(data==130){
   $("#s_tip").html("(信用分在-10分到20分之间)");
   flag = true;
  }
  
  
   });
  }
  });
 
 });
</script>
<script>
 var flagn = true;
$(function(){
  //审核不通过
  $("#tb_no").click(function(){
  if($("#score").val() == ""){
  flagn = true;
     $("#s_tip_").html("(分数不能为空)");
     return;
  }
  
  if($("#s_tip_").html()!=""){
  $("#s_tip").html("(信用分在-10分到20分之间)");
  flagn = true;
     return;
  }
  
  if(flagn){
  flagn = false;
    var answer = confirm("你确认该项审核失败吗")
  if (!answer){
  flagn = true;
  return false;
  }
  
  var param = {};
   param["paramMap.flag"] = 'b';
  param["paramMap.userId"] = '${userId}';
  param["paramMap.tmId"] = '${tmId}';
  param["paramMap.materAuthTypeIdStr"] = '${materAuthTypeIdStr}';
  param['paramMap.content'] = $("#content").val();
  param["paramMap.score"] = $("#score").val();
  $.post("updateallcheck.do",param,function(data){
  if(data==1){
      $("#s_tip").html("审核成功............");
       flagn = true;
       window.location.href='queryPerUserPicturMsginitindex.do?userId=${userId}&materAuthTypeId=${materAuthTypeIdStr}'
  }else if(data==0){
   $("#s_tip").html("审核失败");
   flagn = true;
  }else if(data==130){
   $("#s_tip").html("(信用分在-10分到20分之间)");
   flagn = true;
  }else if(data==3){
  $("#s_tip").html("分数不能为空");
  flagn = true;
  }else if(data==4){
   $("#s_tip").html("审核观点不能为空");
   flagn = true;
  }else if(data==5){
   alert("必须审核完证件明细才能进行此操作");
    flagn = true;
  }else if(data==6){
    $("#s_tip").html("分数必须为数字......");
     flagn = true;
  }
  
  
  });
  }
  });
 
 
	  $("#score").blur(function(){
	     var s_val = $("#score").val();
	     if(s_val == ""){
	       $("#s_tip_").html("(请填写信用分)");
	       return;
	     }
	     if(s_val > 30 || s_val < -10){
	       $("#s_tip_").html("(信用分在-10分到20分之间)");
	       return;
	     }
	     $("#s_tip_").html("");
	 });
 });


</script>
</head>
<body>
<div class="">
<div class="nymain">
  <div class="wdzh">
    <div class="r_main" >
      <div class="box" style="margin-left:0px width:100%;">
       <h2 class="otherh2x">图片资料</h2>
      <div class="boxmain2" > 
          <div class="biaoge" style="width:100%;">
          <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
    <th colspan="4" class="gvItem" style="margin-left: 6px;">用户名： ${username }&nbsp;&nbsp;&nbsp;&nbsp;</th>
    </tr>
  <tr>
  <!-- 证件种类：<s:select list="#{-1:'--请选择--',1:'身份证',2:'工作证',3:'居住证',4:'信用报告',5:'收入验证'}" theme="simple" value="-1"></s:select> -->
    </tr>
  <tr>
     <div align="center">
        <table style="width: 100%; color: #333333;"
			cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="0">
          <tr align="center" class="gvHeader">
          <td>ID</td><td>用户名</td><td >真实姓名</td><td >证件种类</td><td>认证图片</td><td >跟踪审核</td><td >认证审核</td><td>审核观点</td><td>上传时间</td><td >操作</td>
          </tr>
        	<s:if test="pageBean.page==null">
					<tr align="center" class="gvItem">
						<td>1</td><td>${username}</td><td>${realName }<td colspan="7">暂无数据</td>
					</tr>
		  </s:if>
		  <s:else>
         <s:iterator value="pageBean.page" var="bean" status="sta">
          <tr align="center" class="gvItem">                                                                                         
          <td>${sta.count }</td><td>${username}</td><td>${realName }</td><td>${tmtname }</td><td width="80px" height="80px">
          
               <a href="../${imgPath }" target="_blank" title="查看图片"><img  src="../${imgPath }" 

                         width="80px" height="80px"/></a>
            
            </td>
          
          <td >${checkperson }</td><td>
             <s:if test="#bean.auditStatus==1">待审</s:if>
            <s:elseif test="#bean.auditStatus==2">无效</s:elseif>
            <s:elseif test="#bean.auditStatus==3">通过</s:elseif>
            <s:else>未申请</s:else>
          </td><td> 
             ${tmoption }
          </td><td >
            ${passTime }
          </td><td>
          <s:if test="#bean.auditStatus==1">
          <a href="querybasepicture.do?userId=${id }&TypeId=${materAuthTypeId}&adf=${tmdid }">审核</a>
          </s:if>
          <s:else><a href="querybasepicture.do?userId=${id }&TypeId=${materAuthTypeId}&adf=${tmdid }">查看</a></s:else>
          </td>
             </tr>
          </s:iterator>
          </s:else>
        </table>
     	<shove:page curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>
     <br />
     </div>
    </tr>
    <tr>该项证件现审核所得信用分为：<a style="color: red">${materialsauthMap.criditing } 分</a></tr>
  <tr>
   <div>
  信&nbsp;用&nbsp;分：<input type="text" name="paramMap.score"  id="score"
  />&nbsp;&nbsp;分&nbsp;&nbsp;&nbsp;&nbsp;<a style="color: red;">提示：负数为减少信用积分,正数为增加信用积分</a>
  <span id="s_tip_" style="color: red;"></span>
  </div>
    </tr>
  <tr>
  <div>
 审核情况：<span style="color: red;">${materialsauthMap.option }</span>
 </div>
    </tr>
    
    <tr>
    <td>审核观点：</td>
    </tr>
  <tr>
   <div id="g">
  &nbsp;&nbsp;&nbsp;&nbsp;
  &nbsp;&nbsp;&nbsp;&nbsp;
  <s:textarea name="paramMap.content" cols="40" rows="5" id="content"/>
  </div>
    </tr>
  <tr>
  <div align="center">
  <br />
  <input type="submit" value="审核通过" id="tb_yes"/>
 
  <input type="submit" value="审核不通过"  id="tb_no"/>
 
  <span style="color: red; float: none;" id="s_tip" class="formtips"></span>
  </div>
    </tr>
    <br />
  <tr> 
  <hr/>
  <div align="left">
  认证审核记录：
  <br/>
  
    <!--    <table border="3px"> -->
    <div class="biaoge" style="width:100%;">
          <table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr align="center" class="gvHeader">
          <td>修改时间</td><td>审核操作员</td><td>修改前的信用分数</td><td>修改后的信用分数</td>
          </tr>
          
          <s:if test="#request.checkList==null">
					<tr align="center" class="gvItem">
						<td colspan="4">暂无数据</td>
					</tr>
		  </s:if>
          <s:else>
           <s:iterator value="#request.checkList" var="bean2" status="sta3">
          <tr align="center" class="gvItem">
          <td>
          ${checkdate }
          </td><td>${adminName }</td><td>${perrecode }</td><td>${afterrecode }</td>
          </tr>
          </s:iterator>
          </s:else>
        </table>
    </div>
         </tr>
      
</table>

</body>
</html>
