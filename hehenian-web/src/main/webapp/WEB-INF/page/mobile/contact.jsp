<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<jsp:include page="/include/mobile/head.jsp"></jsp:include>
<title>意见反馈</title>
</head>
<body>
<jsp:include page="/include/mobile/loading.jsp"></jsp:include>
<div class="wrap animate-waves" id="wrap" style="display:none;">
  <div class="t title"><span>意见反馈</span></div>
  <div class="pd feedback">
  	<form action="#" id="form">
    	<label class="text">请留下您的联系方式，便于我们反馈</label>
    	<div class="input-item">
        <input type="text" name="contactEmailField" value="" /></div>
        <label class="text">反馈内容</label>
    	<div class="input-item"><textarea name="contactMessageTextarea" id="feedbackContent" validate="true" rule="{empty:false,length:'10'}" tips="{empty:'请输入反馈内容',length:'反馈内容不能少于10个字符'}"></textarea></div>
    </form>
    <a href="#" class="btn-a" id="doFeedback">提交给我们</a>
  </div>
</div>
<jsp:include page="/include/mobile/bottom.jsp"></jsp:include>
<jsp:include page="/include/mobile/common-js.jsp"></jsp:include>
<script src="/wap/mobile/scripts/module/feedback.js" ></script>
</body>
</html>