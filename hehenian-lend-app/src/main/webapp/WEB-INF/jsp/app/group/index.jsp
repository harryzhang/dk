<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
	<head>
		<%@ include file="include/top.jsp"%>
		<title>集团员工贷</title>
	</head>

	<body>
<article>
  <div>
<%--     <img src="<c:url value='/app_res/img/group/banner.png'/>" alt=""> --%>
    <img src="${fileServerUrl }/app_res/img/group/banner.png" alt="">
    <!-- <p class="db_f index-tip bs">
	    <span class="bf1 db bs">花样年集团员工贷</span>
	 	<span class="bf1 db bs">专为集团员工解决资金应急、周转需求</span>
    </p> -->
    <img src="${fileServerUrl }/app_res/img/group/nav_bg.jpg" alt="">
  </div>
  <div class="p1" style="margin-top:60px;">
    <a class="apply" href="##">申请贷款</a>
  </div>
</article>
        <!--弹出框-->
        <div class="opacity" style="display:none;">
           <div class="alertcontairn">
             <span class="closeOpacity closeCcl"><img src="${fileServerUrl }/app_res/img/elend/alertClose.png" class="boximg" /></span>
             <div class="textinfo">
               <div class="justword">
                 <p>注册用户可申请贷款，</p>
                 <p style="font-size:18px;">去注册吧！</p>
               </div>
             </div>
             <a href="" class="register_btn" style="margin-bottom:20px;">立即注册</a>
           </div>
        </div>
        <!--弹出框-->
        <%@ include file="include/foot.jsp"%>
		<script type="text/javascript">
		function personCenter(){
           var pos_h=$(window).height()-60;
		   $('.person_center').css('top',pos_h+'px');
		   
		}
		window.onload=personCenter;
		$(window).resize(function(){
			personCenter();
		})
			
		//关闭弹出框
		$('.closeOpacity').click(function(){
			$('.opacity').hide();	
		 })	
		 
		//链接到贷款页面或是弹出弹出框提示用户去注册
		$('.apply').click(function(){
			var isregister=true;//自定义的虚拟变量false为用户还没注册，true用户已经注册过
			if(isregister){
			  window.location.href="<c:url value='/app/group/personalInfo.do?'/>"//如果已经注册跳转到贷款页面
			}
			else{
			  $('.opacity').show();//没有注册弹出弹框提示用户去注册	
			}
			
		})

		</script>
		<c:if test="${message!= null}"><script>alert(${message})</script></c:if>
	</body>

</html>