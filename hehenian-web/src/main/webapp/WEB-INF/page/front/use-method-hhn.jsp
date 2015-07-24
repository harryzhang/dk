<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<jsp:include page="/include/head.jsp"></jsp:include>
<jsp:include page="use-method-hhn-head.jsp"></jsp:include>
</head>

<body>
	<!-- 引用头部公共部分 -->
	<jsp:include page="/include/top.jsp"></jsp:include>

	<div class="wytz_center" style="background:url();">
		<div class="clear"></div>
        <div class="wdzh_top" style="border: 1px solid #CCC;">
            <jsp:include page="use-method-hhn-left.jsp"></jsp:include>

            <div class="wdzh_next_right" id="kfs">

            </div>
            <div class="cle"></div>
        </div>

	</div>
	<div class="clear"></div>
	<!-- 引用底部公共部分 -->
	<jsp:include page="/include/footer.jsp"></jsp:include>
	<script type="text/javascript">
	function reggg(){
		if(${session.user!=null}){
			alert("你已是登录用户");
		}else{
			window.location.href="/account/reg.do";
		}
	}
        $(function(){
            loadGuide(1);
        })
	</script>
</body>
</html>
