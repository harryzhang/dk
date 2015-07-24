<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
	<head>
		<%@ include file="../include/top.jsp"%>
		<title>个人信息</title>
	</head>
	<body>
		<header>
		    <div class="p1  enable_inf" >
		    	<div class=" p1 loan_person" style="padding-top:70px;">
		    	  <h3 class="loan_person_name">${loanPersonDo.realName}</h3>
		    	  <span class="loan_person_phone">${loanPersonDo.idNo }</span>
		    	</div>
		    </div>
		</header>
		<section>
				<h3 class="tip">填写个人基本信息</h3>
				<input type="hidden" id="p" name="p" value="${message}"/>
				
		<form id="person1From" action='<c:url value="/app/mhk/updateLoanPerson.do"/>' method="post">
			<article>
				<input type="hidden" name="type" value="2">
				<input type="hidden" name="loanId" value="${loanPersonDo.loanDo.loanId }">
				<input type="hidden" name="loanPersonId" value="${loanPersonDo.loanPersonId }">
				<input type="hidden" name="realName" value="${loanPersonDo.realName }"/>
				<input type="hidden" name="mobile" value="${loanPersonDo.mobile }"/>
				<input type="hidden" name="idNo" value="${loanPersonDo.idNo }"/>
					<div class=" p1 db_f">
						<label class="lab lab2" for="age">年&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;龄</label>
						<div class="bf1 db_f">
							<input class="bf1 txt br_l1 must" type="number" id="age" name="age"  readonly value="${loanPersonDo.age}"/>
							<span class="br_r1 after_unit">岁</span>
						</div>
					</div>
					<div class="p1 db_f">
						<label class="lab lab2" for="jobs">性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别</label>
						 <select class="sel bf1 br1" name="sex" id="sex" >
							<option value="FEMALE" <c:if test="${loanPersonDo.sex=='FEMALE'}">selected=selected</c:if>  >女</option>
							<option value="MALE" <c:if test="${loanPersonDo.sex=='MALE'}">selected=selected</c:if>  >男</option>
						</select> 
					</div>
					<div class="p1 db_f">
						<label class="lab lab2" for="worklevel">教育程度</label>
						 <select class="sel bf1 br1" name="education" id="education">
							<option value="GRADE_SCHOOL" <c:if test="${loanPersonDo.education=='GRADE_SCHOOL'}" >selected=selected</c:if> >初中</option>
							<option value="HIGN_SCHOOL" <c:if test="${loanPersonDo.education=='HIGN_SCHOOL'}" >selected=selected</c:if>>高中</option>
							<option value="POLYTECH_SCHOOL" <c:if test="${loanPersonDo.education=='POLYTECH_SCHOOL'}" >selected=selected</c:if>>中技</option>
							<option value="VOCATION_SCHOOL" <c:if test="${loanPersonDo.education=='VOCATION_SCHOOL'}" >selected=selected</c:if>>中专</option>
							<option value="JUNIOR_COLLEGE" <c:if test="${loanPersonDo.education=='JUNIOR_COLLEGE'}" >selected=selected</c:if>>大专</option>
							<option value="BACHELOR" <c:if test="${loanPersonDo.education=='BACHELOR'}" >selected=selected</c:if>>本科</option>
							<option value="MASTER" <c:if test="${loanPersonDo.education=='MASTER'}" >selected=selected</c:if>>硕士</option>
							<option value="DOCTOR" <c:if test="${loanPersonDo.education=='DOCTOR'}" >selected=selected</c:if>>博士</option>
						</select> 
					</div>
					<div class="p1 db_f">
						<label class="lab lab2" for="jobs">婚姻状况</label>
						 <select class="sel bf1 br1" name="marriaged" id="marriaged">
							<option value="UNMARRIED" <c:if test="${loanPersonDo.marriaged=='UNMARRIED'}">selected=selected</c:if>>未婚</option>
							<option value="MARRIED" <c:if test="${loanPersonDo.marriaged=='MARRIED'}">selected=selected</c:if>>已婚</option>
							<option value="DIVORCE" <c:if test="${loanPersonDo.marriaged=='DIVORCE'}">selected=selected</c:if>>离异</option>
						</select> 
					</div>
					<div class=" p1 db_f">
						<label class="lab" for="telNum">住宅电话</label>
						<div class="bf1">
							<p class="db_f">
								<input class="txt area_code br1" type="number" name="familyPhone1" value="${familyPhone1 }"/>
								<em>-</em>
								<input class="txt bf1 br1" type="number" id="telNum" name="familyPhone" value="${loanPersonDo.familyPhone }"/>
							</p>
						</div>
					</div>
					<div class=" p1 db_f">
						<label class="lab lab2 " for="address">住宅地址</label>
						<input class="bf1 txt br1 must" type="text" id="caddress" name="caddress" value="${loanPersonDo.caddress}"/>
					</div>
					<div class=" p1 db_f">
						<label class="lab lab2 " for="address">电子邮箱</label>
						<input class="bf1 txt br1 must" type="email" id="email" name="email" value="${loanPersonDo.email}"/>
					</div>
					<div class=" p1 db_f">
						<label class="lab lab2 " for="address">借款用途</label>
						<input class="bf1 txt br1 must" type="text" id="loanUsage" name="loanUsage" value="${loanPersonDo.loanDo.loanUsage}"/>
					</div>
					<div class="btn_box">
						<button class="btn w10" id="sub">下一步</button>
					</div>
			</article>
				</form>
		</section>
		
		<%@ include file="../include/foot.jsp"%>
		<script>
		$(function() {
			var message = $("#p").val();
			if(message!=''){
				alert(message);
			}	
		})
		</script>
	</body>
</html>