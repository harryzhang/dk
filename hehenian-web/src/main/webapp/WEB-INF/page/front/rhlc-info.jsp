<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<jsp:include page="/include/head.jsp"></jsp:include>
</head>

<body>
<!-- 引用头部公共部分 -->
<jsp:include page="/include/top.jsp"></jsp:include>	

<div class="nymain">
  <div class="lcnav">
    <div class="tab">
	<div class="tabmain">
  		<ul><li class="on" style="padding:0 20px;">如何理财</li></ul>
    </div>
    </div>
    <div class="line">
    </div>
  </div>
  <div class="lcmain">
    <div class="lcmain_l">
    <div class="lctab" style="padding:0 10px;">
    <div class="gginfo">
    <h2>${paramMap.columName}</h2> 
    <p class="zw" style="text-indent:0em;">
     ${paramMap.content}
    </p>
    
    </div>
    <div class="sxnews">
    
    </div>
    </div>
    </div>
    <div id="showDongtai" class="lcmain_r">
      <!-- 网站动态显示位置 -->
    </div>
  </div>
</div>
<jsp:include page="/include/footer.jsp"></jsp:include>
<script type="text/javascript" src="script/nav.js"></script>
<script src="script/jquery-1.7.1.min.js" type="text/javascript"></script>
<script type="text/javascript" src="script/jquery.shove-1.0.js"></script>
<script type="text/javascript" src="script/nav-lc.js"></script>
<script>
$(function(){

    param["pageBean.pageNum"] = 1;
	
	initNewsListInfo(null);
	
	 
	 
    //样式选中
     $("#licai_hover").attr('class','nav_first');
	<%-- $("#licai_hover div").removeClass('none');
	 $('.tabmain').find('li').click(function(){
	    $('.tabmain').find('li').removeClass('on');
	    $(this).addClass('on');
	 });--%>
});	

 function initNewsListInfo(praData){		
		$.shovePost("frontQueryNewsList.do",praData,function(data){
			$("#showDongtai").html(data);
		});
			
	  }	     
</script>
</body>
</html>
