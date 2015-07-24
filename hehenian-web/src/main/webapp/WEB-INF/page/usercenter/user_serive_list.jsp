<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script src="script/jquery-1.7.1.min.js" type="text/javascript"></script>
<script type="text/javascript" src="script/jbox/jquery.jBox-2.3.min.js"></script>
<script type="text/javascript" src="script/jbox/jquery.jBox-zh-CN.js"></script>
<script type="text/javascript">
  $(function(){
/*
    $("#ok").click(function(){
          var rt = new Array(2);
          rt[0] = "hello";
          rt[1] = "word";
          window.returnValue = rt;
          window.close();
    
    });
    */
   // window.parent.ffff(111);
  });

</script>



</head>

<body >
<div class="nymain" style="float:none;width:800px;" >
  <div class="kfbox" style="float:none;width:800px;" >
    <div class="r_main" style="float:none;width:800px;">
      <div class="box" style="padding-bottom:20px;width:750px; margin:0px auto;">
      <h2>联系客服</h2>
      <div class="boxmain">
      <ul class="kefu">
      
      <s:iterator value="#request.map" var="bean" status="sta">
            <li><a  class="tx">
            <shove:img src="${kefuImage}" defaulImg="images/default-img.jpg" width="72" height="72"></shove:img>
          </a><br/>
      <a>${name }</a><br/>
      <a  href="javascript:void(0);">
         <input style="cursor: pointer;" type="button" value="选择客服"   onclick="javascript:window.parent.ffff('${name}',${id});" class="scbtn" />
      </a></li>
      </s:iterator>
      </ul>
      </div>
      </div> 
    </div>
  </div>
</div>
<script type="text/javascript">
  function  click_b(data,data2){
       var rt = new Array(2);
        rt[0] =   data;
        rt[1] = data2;
         window.returnValue = rt;
         window.close();
  }
</script>
</body>
</html>
