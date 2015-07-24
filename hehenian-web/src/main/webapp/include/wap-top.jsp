<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!-- header -->
<div id="fixed-header-slide">
  <div id="fixed-header-wrap">
    <div id="fixed-header">
      <div class="fixed-header-nav">
        <ul class="main-menu">
          <li style=" width:20%; padding-top:8px; padding-left:20px;"><a href="webapp-index.do" style="width: 24px;"><img src="/wap/images/home_24x24.png" ></a></li>
          <li style=" width:60%; text-align:center;">中国首家P2P社区金融服务平台</li>
           <li style=" width:20%; padding-right:20px;   padding-top:8px; text-align:right"><div style=" float:right"><a href="qa.do"><img src="/wap/images/chat_alt_fill_24x24.png" ></a></div></li>
        </ul>
      </div>
    </div>
  </div>
</div>
  <script>
	  setInterval(function(){
$.post("/cf/heartbeat.do");
},120000);
  </script>