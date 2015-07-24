<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript" src="/wap/mobile/scripts/common/zepto.min.js?t=1" ></script>
<script type="text/javascript" src="/wap/mobile/scripts/common/hhn.min.js?t=1" ></script>
<script type="text/javascript" src="http://tajs.qq.com/stats?sId=35593653" charset="UTF-8"></script>
<script type="text/javascript">
setInterval(function(){
	$.post("/cf/heartbeat.do");
},120000);
</script>
