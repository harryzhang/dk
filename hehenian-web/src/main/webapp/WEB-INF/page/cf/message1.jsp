<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<jsp:include page="/include/cf-head.jsp"></jsp:include>
</head>
<body>
<jsp:include page="/include/cf-top.jsp"></jsp:include>
<div class="s_sur_ix main el_container" style=" overflow:hidden; margin-bottom:20px;">
   <!--  <div style=" margin:10px 0px;"><img src="/color/images/ad.jpg" width="974" height="80"  alt=""/></div> -->
    <!--左侧-->
    <div class=" nr_left"> 
    <div class="left_nr">
     <div class="one-half-responsive" style=" height:450px; text-align:center; padding-top:150px;">
        <p style=" padding:20px 20px 0px 20px;"><span style=" font-size:32px;" id="result"></span></p>
      </div>
    </div>
    </div>
    <!--右侧-->

  </div>
  <jsp:include page="/include/cf-footer.jsp"></jsp:include>
</body>
<script>
    var url = window.location+"";
    url = decodeURI(url);
    var start = url.indexOf("title=") + "title=".length;
    var ret = url.substring(start);
    $("#result").text("${title}");
</script>
</html>