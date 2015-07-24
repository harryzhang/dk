<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
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
  <ul><li class="on" style="padding:0 20px;">团队介绍</li>
  </ul>
    </div>
    </div>
    <div class="line">
    </div>
  </div>
  <div class="lcmain">
     <div class="lcmain_lptyl">    
    <div class="gginfo">
    <h2>团队介绍</h2>
    <p class="zw" style="padding-top:20px;">
    <ul>
      <s:iterator value="#request.teamList" var="teams" status="status">
        <li>
          <div class="pic">
           <img src="${teams.imgPath}" width="139" height="181" />
          </div>
          <div class="info">
            <h3>${teams.userName}</h3>
             <p>
               ${teams.intro}
             </p>
          </div>
       </li>
     </s:iterator>
    </ul>
    </div>

    </div>
    <div id="showDongtai" class="lcmain_r">
      <!-- 网站动态显示位置 -->
    </div>
  </div>
</div>
<!-- 引用底部公共部分 -->     
<jsp:include page="/include/footer.jsp"></jsp:include>
<script src="script/jquery-1.7.1.min.js" type="text/javascript"></script>
<script type="text/javascript" src="script/jquery.shove-1.0.js"></script>

<script>

$(function(){
    
  	param["pageBean.pageNum"] = 1;
	
	initNewsListInfo(null);
	
dqzt(0)	

});
	function initNewsListInfo(praData){		
			$.shovePost("frontQueryNewsList.do",praData,function(data){
				$("#showDongtai").html(data);
			});
			
	}
</script>
</body>
</html>
