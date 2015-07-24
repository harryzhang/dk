<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    
<!DOCTYPE html>    
<html>
	<head>
		<%@ include file="../include/top.jsp"%>
		<title>个人信息</title>
	</head>
	<body>
		<header>
		    <div class="p1  enable_inf">
		    	<div class=" p1 loan_person" style="padding-top:70px;">
		    	  <h3 class="loan_person_name">冯亮</h3>
		    	  <span class="loan_person_phone">11345678932165498</span>
		    	</div>
		    </div>
		</header>
	<section>
			<article class="p1">
				<section class="db_f p1">
					<article class="bf1 upload_item">
						<!-- <input type="file" class="upload_btn" accept="image/*"> -->
						<span class="img_box"><img src="img/img.png" alt=""><i class="upload_ok pa"></i></span>
						<span class="upload_title">《借款咨询服务协议》</span>
					</article>
					<article class="bf1 upload_item">
						<input type="file" class="upload_btn" accept="image/*">
						<!-- <span class="uploading">正在上传</span> -->
						<span class="upload_title">其它</span>
					</article>
				</section>
			</article>
			<div class="btn_box">
				<botton class="btn">确认提交</botton>
			</div>
		</section>
		<script src="${fileServerUrl }/app_res/js/libs/zepto.js"></script>
		<script>
		$(function() {
				
		})
		</script>
	</body>
</html>