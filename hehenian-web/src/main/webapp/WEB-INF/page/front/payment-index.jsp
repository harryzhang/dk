<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<jsp:include page="/include/head.jsp"></jsp:include>
    <link href="css/inside.css"  rel="stylesheet" type="text/css" />
<link id="skin" rel="stylesheet" href="css/jbox/Gray/jbox.css" />
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
        <ul><li class="on" onclick="borrowSuccessInit();">成功借款</li>
        <li onclick="borrowPayingInit();">正在还款的借款</li>
        <li onclick="borrowAllDetailsInit();">还款明细账</li>
        <li onclick="borrowInvestorDetailsInit();">借款明细账</li>
        <li onclick="borrowPayOffInit();">已还完的借款</li>
        </ul>
        </div>
      <div class="box">
        <div class="boxmain2">
           <span id="show_success_borrow"></span>
    </div>
</div>
<div class="box" style="display:none;">
        <div class="boxmain2">
        <span id="success_borrow_paying"></span>
         <span id="borrow_details"></span> 
    </div>
</div>
<div class="box" style="display:none;">
        <div class="boxmain2">
         <span id="success_borrow_all_details" ></span>
    </div>
</div>
<div class="box" style="display:none;">
        <div class="boxmain2">
         <span id="borrow_invest_list"></span>
    </div>
</div>
<div class="box" style="display:none;">
        <div class="boxmain2">
        <span id="borrow_payoff_list"></span>
        <span id="borrow_payoff_details"></span> 
    </div>
</div>
    </div>
  </div>
</div>
<!-- 引用底部公共部分 -->     
<jsp:include page="/include/footer.jsp"></jsp:include>

<script type="text/javascript" src="script/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="script/jquery.shove-1.0.js"></script>
<script language="javascript" type="text/javascript" src="My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="css/popom.js"></script>
<script type="text/javascript" src="script/jbox/jquery.jBox-2.3.min.js"></script>
<script type="text/javascript" src="script/jbox/jquery.jBox-zh-CN.js"></script>
<script>
$(function(){
    $("#zh_hover").attr('class','nav_first');
	$("#zh_hover div").removeClass('none');
	$('#li_7').addClass('on');
	$('.tabtil').find('li').click(function(){
	$('.tabtil').find('li').removeClass('on');
	$(this).addClass('on');
	$('.tabtil').nextAll('div').hide();
    $('.tabtil').nextAll('div').eq($(this).index()).show();
	})
	   borrowSuccessInit();
	});
	
	
	function borrowSuccessInit(){
	   param["pageBean.pageNum"]=1;
	   param["paramMap.borrowStatus"] = "-1";//查询所有的借款，不管是已还完还是未还完的
	   $.shovePost("mySuccessBorrowInit.do",param,function(data){
	      $("#show_success_borrow").html(data);
	   });
	}
	
	function borrowPayingInit(){
	  param["paramMap.borrowStatus"] = "4";
	  param["pageBean.pageNum"]=1;
	  $("#borrow_details").html("");
	  $.shovePost("queryMySuccessPayingInit.do",param,function(data){
	      $("#success_borrow_paying").html(data);
	   });
	}
	
	function borrowAllDetailsInit(){
	  param["pageBean.pageNum"]=1;
	  $.shovePost("queryAllDetailsInit.do",param,function(data){
	      $("#success_borrow_all_details").html(data);
	   });
	}
	
	function borrowInvestorDetailsInit(){
	  param["pageBean.pageNum"]=1;
	  $.shovePost("queryBorrowInvestorInit.do",param,function(data){
	      $("#borrow_invest_list").html(data);
	   });
	}
	
	function borrowPayOffInit(){
	  param["pageBean.pageNum"]=1;
	  $("#borrow_des").attr("value","");
	  $.shovePost("queryPayoffListInit.do",param,function(data){
	      $("#borrow_payoff_list").html(data);
	   });
	}
	
	function showAgree(){
	     var url = "getMessageBytypeId.do?typeId=1";
	     jQuery.jBox.open("post:"+url, "看协议书情", 600,400,{ buttons: { } });
	     
    }
</script>

</body>
</html>
