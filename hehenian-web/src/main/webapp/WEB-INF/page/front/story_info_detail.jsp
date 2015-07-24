<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<jsp:include page="/include/head.jsp"></jsp:include>
    <link href="css/inside.css"  rel="stylesheet" type="text/css" />

</head>

<body>
<!-- 引用头部公共部分 -->
<jsp:include page="/include/top.jsp"></jsp:include>	
<div class="nymain">
  <div class="lcnav">
    <div class="tab">
<div class="tabmain">
  <ul><li class="on" style="padding:0 20px;">成功故事详情</li>
  </ul>
    </div>
    </div>
    <div class="line">
    </div>
  </div>
  <div class="lcmain">
    <div class="lcmain_l">
    <div class="lctab" style="padding:0 10px;">
    <div class="gginfo">
    <h2>${paramMap.title}</h2>
    <p class="time">发布者：${paramMap.publisher } -    发布时间：${paramMap.publishTime}     点击次数：${paramMap.browseNum} </p>
    <p class="zw">${paramMap.content }</p>
    </div>
    <div class="sxnews">
    <ul>
     <li>
       <s:if test="#request.previousDate!=null" >
          上一条：<a style="max-width: 30px;overflow: hidden;"  href="frontQuerySuccessStoryDetail.do?id=${request.previousDate.id}">${request.previousDate.title}</a>
       </s:if>  
       <s:else>
         <a>&nbsp;&nbsp;&nbsp;</a>
       </s:else>
    </li>
    <li>
      <s:if test="#request.lastDate!=null" >
         下一条：<a style="max-width: 30px;overflow: hidden;" href="frontQuerySuccessStoryDetail.do?id=${request.lastDate.id}">${request.lastDate.title}</a>
      </s:if> 
      <s:else>
        <a>&nbsp;&nbsp;&nbsp;</a>
      </s:else>  
    </li>
    </div>
    </div>
    </div>
   <div id="showDongtai" class="lcmain_r">
      <!-- 网站动态显示位置 -->
    </div>
  </div>
</div>

<!-- 引用底部公共部分 -->     
<jsp:include page="/include/footer.jsp"></jsp:include>
<script type="text/javascript" src="script/nav.js"></script>
<script src="script/jquery-1.7.1.min.js" type="text/javascript"></script>
<script type="text/javascript" src="script/jquery.shove-1.0.js"></script>
<script>
$(function(){
   

	initNewsListInfo(null);
         
   
    //样式选中
     $("#sy_hover").attr('class','nav_first');
	 $("#sy_hover div").removeClass('none');
});
	
	
		function initNewsListInfo(praData){		
			$.shovePost("frontQueryNewsList.do",praData,function(data){
				$("#showDongtai").html(data);
			});
			
		}
</script>
</body>
</html>

