<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
		<%@ include file="../include/top.jsp"%>
		<title>个人信息</title>
	</head>
	<body>
		<section>
			<article class="loaninit">
				<form id="contactForm" action="<c:url value='/app/mhk/updatePersonContact.do'/>" method="post">
				   <input type="hidden" name="loanId" value="${loanPersonDo.loanId }">
				   <input type="hidden" name="loanPersonId" value="${loanPersonDo.loanPersonId }">
					<article >
						<h3 class="tip tip2">家庭联系人</h3>
						<input type="hidden" name="loanRelationDoList[0].relationType" value="1">
						<input type="hidden" name="loanRelationDoList[0].ralationId" value="${relationFamily.ralationId }">
						<div class="p1 db_f">
							<label for="name" class="lab lab2">真实姓名</label>
							<input type="text" class="bf1 txt br1 must"  name="loanRelationDoList[0].ralationName" value="${relationFamily.ralationName }"/>
						</div>
						<div class="p1 db_f">
							<label for="link" class="lab lab2">与本人关系</label>
							<select name="loanRelationDoList[0].relationship" id="" class="sel bf1 br1 must" >
								<option value="0" <c:if test="${relationFamily.relationship==0 }">selected=selected</c:if> >-请选择-</option>
								<option value="1" <c:if test="${relationFamily.relationship==1 }">selected=selected</c:if> >父母</option>
								<option value="2" <c:if test="${relationFamily.relationship==2 }">selected=selected</c:if> >配偶</option>
								<option value="3" <c:if test="${relationFamily.relationship==3 }">selected=selected</c:if> >子女</option>
							</select>
						</div>
						<div class="p1 db_f">
							<label for="phone" class="lab lab2">手机号码</label>
							<input type="number" class="bf1 txt br1 must phone"  name="loanRelationDoList[0].mobile"  value="${relationFamily.mobile }"/>
						</div>
					</article>
					<article >
						<h3 class="tip">工作联系人</h3>
						<input type="hidden" name="loanRelationDoList[1].relationType" value="2">
						<input type="hidden" name="loanRelationDoList[1].ralationId" value="${relationWork.ralationId }">
						
						<div class="p1 db_f">
							<label for="name" class="lab">真实姓名</label>
							<input type="text" class="bf1 txt br1" name="loanRelationDoList[1].ralationName" value="${relationWork.ralationName }"/>
						</div>
						<div class="p1 db_f">
							<label for="link" class="lab">与本人关系</label>
							<select name="loanRelationDoList[1].relationship" id="" class="sel bf1 br1" >
								<option value="0" <c:if test="${relationWork.relationship==0 }">selected=selected</c:if> >-请选择-</option>
								<option value="1" <c:if test="${relationWork.relationship==1 }">selected=selected</c:if>>同事</option>
								<option value="2" <c:if test="${relationWork.relationship==2 }">selected=selected</c:if>>人事</option>
							</select>
						</div>
						<div class="p1 db_f">
							<label for="phone" class="lab">手机号码</label>
							<input type="number" class="bf1 txt br1 phone" name="loanRelationDoList[1].mobile" value="${relationWork.mobile }"/>
						</div>
					</article>
					<article>
						<h3 class="tip">其他联系人</h3>
						<input type="hidden" name="loanRelationDoList[2].relationType" value="3">
						<input type="hidden" name="loanRelationDoList[2].ralationId" value="${relationOther.ralationId }">
						<div class="p1 db_f">
							<label for="name" class="lab">真实姓名</label>
							<input type="text" class="bf1 txt br1 "  name="loanRelationDoList[2].ralationName" value="${relationOther.ralationName }"/>
						</div>
						<div class="p1 db_f">
							<label for="link" class="lab">与本人关系</label>
							<select id="" class="sel bf1 br1" name="loanRelationDoList[2].relationship">
								<option value="0" <c:if test="${relationOther.relationship==0 }">selected=selected</c:if> >-请选择-</option>
								<option value="1" <c:if test="${relationOther.relationship==1 }">selected=selected</c:if> >朋友</option>
								<option value="2" <c:if test="${relationOther.relationship==2 }">selected=selected</c:if> >同学</option>
							</select>
						</div>
						<div class="p1 db_f">
							<label for="phone" class="lab">手机号码</label>
							<input type="number" class="bf1 txt br1 phone" name="loanRelationDoList[2].mobile" value="${relationOther.mobile }"/>
						</div>
					</article>
					<div class="btn_box">
						<button class="btn w10" id="sub">提交</button>
					</div>
				
				</form>
			</article>
		</section>
		<%@ include file="../include/foot.jsp"%>
		<script>
		$(function() {
			 var message = "${message}";
			 if(message!=null && message!=""){
				 alert(message);
			 }
			$(".fangdai").change(function() {
				var num = $(this).val();
				if(num > 0) {
					$(this).parent().next().removeClass("dn");
					return false;
				}
					$(this).parent().next().addClass("dn");
			});
		})
		</script>
	</body>
</html>