<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
	<head>
		<%@ include file="include/top.jsp"%>
		<title>贷款进行中</title>
	</head>

	<body>
		<div class="eLoan">
           <div class="loan_state">
             <div class="state_icon"><img src="${fileServerUrl }/app_res/img/elend/success.png" class="boximg" /></div>
             <h3>恭喜，申请提交成功！</h3>
           </div>
           <p class="state_title">申请状态</p>
           <div class="loaninfo" style="margin-top:0;">
             <div class="state_list">
               <div class="state_step">
                 <div class="clearfix">
                   <span class="tag"><img src="${fileServerUrl }/app_res/img/elend/pass.png" class="boximg" /></span>
                   <p class="tag_txt pass_txt">
                   	 <jsp:useBean id="now" class="java.util.Date"></jsp:useBean>
                     <span><fmt:formatDate value="${now}"
							pattern="yyyy-MM-dd" /></span><br />
                     		申请提交成功
                   </p>
                 </div>
                 <div class="clearfix">
                   <span class="tag processing"><img src="${fileServerUrl }/app_res/img/elend/processing.png" class="boximg" /></span>
                   <p class="tag_txt processin_txt">
                     	审核中
                   </p>
                 </div>
                 <div class="clearfix">
                   <span class="tag"><img src="${fileServerUrl }/app_res/img/elend/untreated.png" class="boximg" /></span>
                   <p class="tag_txt untreated_txt">
                    	 放款成功
                   </p>
                 </div>
               </div>
             </div>
           </div>
           <div class="loan_bottom">
             <p>E贷款专享服务热线</p>
             <dl class="service_box">
               <dt>4008-303-737</dt>
               <dd>（服务时间：工作日8:30-17：30）</dd>
             </dl>
           </div>
		</div>
        <a href="<c:url value='/app/elend/personalCenter'/>" class="person_center"><img src="${fileServerUrl }/app_res/img/elend/personLink.png" class="boximg" /></a>
     <%@ include file="include/foot.jsp"%>   
	<script type="text/javascript">
	  function personCenter(){
		 var pos_h=$(window).height()-50;
		 $('.person_center').css('top',pos_h+'px');
	  }
	  window.onload=personCenter;
	  $(window).resize(function(){
		  personCenter();
	  })
    </script>
		
	</body>

</html>