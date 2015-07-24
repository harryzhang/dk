<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <jsp:include page="/include/head.jsp"></jsp:include>
</head>
<body>
<input type="hidden" id="idStr" value="${id}"/>
<div id="mainCon">
<!-- 引用头部公共部分 -->
<jsp:include page="/include/top.jsp"></jsp:include>	
<div class="nymain">
<div class="bigbox">
  <div class="til">投标密码</div>
  <div class="sqdk" style="padding-top:55px; padding-bottom:80px; padding-left:300px; background:none;">
    <div class="logintab" id="ok">
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <th align="right">请输入投标密码：</th>
    <td><input type="password" class="inp90" id="investPWD"/><span class="fred"><s:fielderror fieldName="paramMap['investPWD']"></s:fielderror></span>
    </td>
  </tr>
  <tr>
    <td colspan="2" align="center">
    <input type="button" value="确认" class="chaxun" id="btnpwd"/>
    </td>
  </tr>
</table>
    </div>
  </div>
  </div>
</div>
<!-- 引用底部公共部分 -->     
<jsp:include page="/include/footer.jsp"></jsp:include>
<script type="text/javascript" src="script/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="script/jquery.shove-1.0.js"></script>
<script type="text/javascript" src="script/nav-lc.js"></script>
<script type="text/javascript">
$(function(){
 //样式选中
     $("#licai_hover").attr('class','nav_first');
	 $("#licai_hover div").removeClass('none');
	 $('.tabmain').find('li').click(function(){
	    $('.tabmain').find('li').removeClass('on');
	    $(this).addClass('on');
	    $('.lcmain_l').children('div').hide();
        $('.lcmain_l').children('div').eq($(this).index()).show();
	 });
$('#btnpwd').click(function(){
	      var username = '${borrowUserMap.username}';
	      var uname = '${user.username}';
	      var investPWD = $('#investPWD').val();
	      if(username == uname){
	         alert("无效操作");
	         return false;
	      }
	     param['paramMap.id'] = $('#idStr').val();
	     param['paramMap.investPWD'] = investPWD;
	     $.shovePost('financeInvestLoad.do',param,function(data){
		   var callBack = data.msg;
		   if(callBack !=undefined){
		     alert(callBack);
		   }else{
             $('#mainCon').html(data);
		   }
		 });
	 });
});
</script>
</div>
</body>
</html>