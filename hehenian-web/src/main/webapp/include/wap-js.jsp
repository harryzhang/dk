<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript" src="/wap/scripts/common/zepto.js" ></script> 
<script type="text/javascript" src="/wap/scripts/common/hhn.js" ></script>
<script type="text/javascript" src="/wap/scripts/module/main.js" ></script>
<script type="text/javascript" src="http://tajs.qq.com/stats?sId=35593653" charset="UTF-8"></script>
<script type="text/javascript">
setInterval(function(){
	$.post("/cf/heartbeat.do");
},120000);
</script>