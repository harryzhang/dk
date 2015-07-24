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
  <div class="wdzh">
    <div class="l_nav">
      <div class="box">
     	<!-- 引用我的帐号主页左边栏 -->
         <%@include file="/include/left.jsp" %>
      </div>
    </div>
    <div class="r_main">
    <div class="tabtil">
        <ul>
        <li class="on" onclick="jumpUrl('queryFundrecordInit.do');">资金记录</li>
        <li  onclick="jumpUrl('rechargeInit.do');">充值</li>
        <li  onclick="jumpUrl('withdrawLoad.do');">提现</li>
        </ul>
        </div>
      <div class="box" id="zjjl">
   </div>
      
    </div>
  </div>
</div>
<!-- 引用底部公共部分 -->     
<jsp:include page="/include/footer.jsp"></jsp:include>
<script type="text/javascript" src="/script/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="script/jquery.shove-1.0.js"></script>
<script language="javascript" type="text/javascript" src="My97DatePicker/WdatePicker.js"></script>
<script>
$(function(){
//样式选中
      $("#zh_hover").attr('class','nav_first');
      $('#li_2').addClass('on');
	  $('.tabmain').find('li').click(function(){
	  $('.tabmain').find('li').removeClass('on');
        window.location.href='queryFundrecordInit.do';
     });  
     });
          
       function jumpUrl(obj){
          window.location.href=obj;
       }
    
</script>
<script>
   function fundRecordList(){
      if($("#startTime").val()=="" || $("#endTime").val()==""){
         alert("请选择查询日期");
         return;
      }else if($("#startTime").val() >$("#endTime").val() ){
         alert("结束日期要大于开始日期");
         return;
      }
      param["pageBean.pageNum"] = 1;
      param["paramMap.startTime"]=$("#startTime").val();
      param["paramMap.endTime"]=$("#endTime").val();
      $.shovePost("queryFundrecordList.do",param,function(data){
         $("#fundRecord").html(data);
      });
   }
   
   function addRechargeInfo(){
      if($("#money").val() == ""){
        $("#money_tip").html("请输入充值金额");
        return;
      }else if(!/^(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-9]*))$/.test($("#money").val())){
	       $("#money_tip").html("请输入正确的提现金额，必须为大于0的数字"); 
	       return;
	  }else if($("#money").val() < 0.01){
	      $("#money_tip").html("最小金额不能低于0.01"); 
	       return;
	  }else{
	     $("#money_tip").html("");
	  }
	  
	  if(!window.confirm("确定进行帐户充值")){
        return;
      }
      var money = $("#money").val();
      window.open("alipayPayment.do?divType=li_2&money="+money);
      
      /*param["paramMap.money"] = $("#money").val();
      $.shovePost("addRechargeInfo.do",param,function(data){
         if(data == 0){
            alert("充值失败");
            return ;
         }
         alert("充值成功");
         $("#money").attr("value","");
         $("#rechargeInfo").html(data);
      });*/
   }
</script>
</body>
</html>
