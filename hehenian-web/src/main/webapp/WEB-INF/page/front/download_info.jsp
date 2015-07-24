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
  <ul><li class="on" style="padding:0 20px;">下载专区</li>
  </ul>
    </div>
    </div>
    <div class="line">
    </div>
  </div>
  <div class="lcmain">
    <div class="lcmain_l" style="width:675px">
    <div class="lctab" style="padding:0">
    <div class="gginfo" style="padding:0">
    <h2>${paramMap.title}</h2>
    <p class="time">发布者：${paramMap.userName }：-    发布时间：${paramMap.publishTime}    点击次数：${paramMap.visits} </p>
    <p class="zw" style="text-align:center;">
    <a href="downloads.do?id=${paramMap.id}" class="chaxun" style="text-indent:0em;">文件下载</a>
    </p>
    </div>
    <div class="sxnews">
    <ul>
    <li style="background:none;line-height:20px">
      <s:if test="#request.previousDate!=null" >
         上一条：<a style="max-width: 30px;overflow: hidden;" href="frontDownloadDetail.do?id=${request.previousDate.id}">${request.previousDate.title}</a>
      </s:if>  
      <s:else>
         <a>&nbsp;&nbsp;&nbsp;</a>
      </s:else>
    </li>
    <li  style="background:none; line-height:20px">
     <s:if test="#request.lastDate!=null" >
         下一条：<a style="max-width: 30px;overflow: hidden;"   href="frontDownloadDetail.do?id=${request.lastDate.id}">${request.lastDate.title}</a>
      </s:if> 
      <s:else>
        <a>&nbsp;&nbsp;&nbsp;</a>
      </s:else>
   
    </li>
    </ul>
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

<script type="text/javascript" src="script/jquery.shove-1.0.js"></script>
<script>
$(function(){
      //样式选中
  dqzt(0)
	 
	 initNewsListInfo(null);
	 
});

      function initNewsListInfo(praData){		
			$.shovePost("frontQueryNewsList.do",praData,function(data){
				$("#showDongtai").html(data);
			});
			
		}
</script>
</body>
</html>

