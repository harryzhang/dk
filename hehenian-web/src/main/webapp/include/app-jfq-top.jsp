<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!-- header -->
<div class="container"> <!-- <a href="#" class="next-quote"></a> <a href="#" class="prev-quote"></a> -->
    <div class="quote-slider" data-snap-ignore="true">
      <div>
        <div class="services-item"> <a href="money.do"><img src="/wap/images/general-nature/3s.jpg" alt="img"></a>
          <h4>我的账户</h4>
      </div>
      </div>
      <div>
        <div class="services-item"> <a href="finance.do"><img src="/wap/images/general-nature/4s.jpg" alt="img"></a>
          <h4>我要投资</h4>
        </div>
      </div>
      <div>
        <div class="services-item"> <a href="qa.do"><img src="/wap/images/general-nature/5s.jpg" alt="img"></a>
          <h4>常见问题</h4>
     </div>
      </div>
      <div>
        <div class="services-item"><input type=button value=刷新 onclick=history.go(-0)>
     </div>
      </div>
    </div>
    <!----> 
  </div>
<script>
	  setInterval(function(){
$.post("/cf/heartbeat.do");
},120000);
  </script>