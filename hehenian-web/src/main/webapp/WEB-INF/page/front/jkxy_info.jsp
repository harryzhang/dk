<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<jsp:include page="/include/head.jsp"></jsp:include>
    <link href="css/css.css"  rel="stylesheet" type="text/css" />

</head>

<body>
<div class="nymain" style="width:662px;">
  <div class="lcmain" style="width:662px;">
    <div class="lcmain_l" style="border:none;">
    <div class="lctab" style="padding:0 10px;">
    <div class="gginfo">
    <h2>${paramMap.columName}</h2>
    <p class="zw">${paramMap.content}</p>
    </div>
    </div>
    </div>
  </div>
</div>
</body>
</html>

