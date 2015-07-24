<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
	<head>
		<%@ include file="include/top.jsp"%>
		<title>借款人个人信息</title>
	</head>
	
	<body>
		<form id="form_update_person" action="<c:url value='personalInfo.do'/>" method="post" >
		<input type="hidden" name="loanId" value="${loanId}"/>
		<div class="eLoan">
           <dl class="loaninfo">
             <dt>
               <label><span>*</span>真实姓名：</label>
               <input type="text" name="realName" value="${loanChannelDo.loanUserDo.name}" class="txtinput not_null chinese"/>
             </dt>
             <dd></dd>
             <p class="line"></p>
             <dt>
               <label><span>*</span>手机号码：</label>
               <c:set var="mobile" value="${loanPersonDo.mobile}"/>
               <c:if test="${mobile== null && loanChannelDo.loanUserDo.mobile != null}">
               		<c:set var="mobile" value="${loanChannelDo.loanUserDo.mobile}"/>
               </c:if>
               <input type="number" name="mobile" value="${mobile}" class="txtinput not_null tel" />
             </dt>
             <dd></dd>
             <p class="line"></p>
             <dt>
               <label><span>*</span>身份证号：</label>
               <input type="text" name="idNo" value="${loanChannelDo.loanUserDo.idNo}" class="txtinput not_null identity" />
             </dt>
             <dd></dd>
             <p class="line"></p>
             <dt>
               <label><span>*</span>每月收入：</label>
               <input type="number"  placeholder="请真实填写月收入 "  min="1" name="jobDo.jobIncome" id="jobIncome" value="" class="txtinput not_null" />
             </dt>
             <dd></dd>
             <p class="line"></p>
             <dt>
               	<label><span>*</span>所在公司：</label>
             		<select name="company" id="company">
						<option value="0" >选择公司</option>
						<option value="地产集团">地产集团</option>
						<option value="文旅集团">文旅集团</option>
						<option value="物业国际">物业国际</option>
						<option value="商管公司">商管公司</option>
						<option value="金融集团">金融集团</option>
						<option value="彩生活集团">彩生活集团</option>
						<option value="福泰年">福泰年</option>
						<option value="花样年教育">花样年教育</option>
						<option value="邻里乐">邻里乐</option>
					</select> 
             </dt>
             <dd></dd>
             <p class="line"></p>
             <dt>
             	<label><span>*</span>所在部门：</label>
               	<input type="text" name="dept" id="dept"  value="" placeholder="输入所在部门" class="txtinput not_null " />
               	<input type="hidden" name="remark" id="remark"  value="${loanPersonDo.remark}"  />
             </dt>
             <dd></dd>
	         </dl>
	         <div class="submit_btn">
	            <a href="javascript:void(0);" class="apply">提交借款人信息</a>
	         </div>
           </div>
		</form>
        <%@ include file="include/foot.jsp"%>
		<script type="text/javascript">
		  $('.txtinput').focus(function(){
			 $(this).addClass('write');
			 $(this).parents('dt').next('dd').text('');
		   }).blur(function(){
		     $(this).removeClass('write');
		   })
		  
		  function isNull(){//验证必填是否非空
			  var flag=true;
			  $('.not_null').each(function() {
				  if ($(this).val() == "") {
					  flag=false;
					  return flag; 
				  }
			  });
			  return flag;
		  }
		  
		  function mobileNum(){//验证手机号码
			  var reg = /(1[3,4,5,7,8]\d{9}$)/;
				 var tel=$('.tel');
				 if (!reg.test(tel.val())){
					 tel.parents('dt').next('dd').text('请填写正确的手机号');
					 return false;
				 }
			  return true;
		  }
		  
		  function chineseWord(){//验证名字是否中文
			  var reg =/^[\u4e00-\u9fa5]+$/;
				 var chinese=$('.chinese');
				 if (!reg.test(chinese.val())){
					 chinese.parents('dt').next('dd').text('请填写中文名字');
					 return false;
				 }
			  	return true;
		  }
		  
		 function identityCard(){//验证15位或18位身份证号
			  var reg =/(^\d{15}$)|(^\d{17}(\d|x|X)$)/;
				 var identity= $('.identity');
				 if (!reg.test(identity.val())){
					 identity.parents('dt').next('dd').text('请填写正确的身份证号');
					 return false;
				 }
			  return true;
		  }
		 function checkJobIncome(){
			var jobIncome= $('#jobIncome').val();
			if (jobIncome == "" || jobIncome.length==0) {
				$('#jobIncome').parents().next("dd").text("请输入每月收入");
				return false;
			}
		    if(!/^\+?[1-9][0-9]*$/.test(jobIncome)){
		    	$('#jobIncome').parents().next("dd").text("每月收入只能输入数字");
			 	return false;
			 } 
			 return true;
		  }
		 function checkCompany(){
			var company= $('#company').val();
			if (company == "" || company==0 ) {
				 $('#company').parents('dt').next('dd').text("请选择所在公司");
				 return false;
			 }else{
				 $('#company').parents('dt').next('dd').text("");
			 }
			 return true;
		  }
		 function checkDept(){
			var dept= $('#dept').val();
			if (dept == "" || dept.length==0) {
				 $('#dept').parents('dt').next('dd').text("请输入部门信息");
				 return false;
			 }
			 return true;
		  }
		 
		 $(".tel").blur(mobileNum);
		 $(".identity").blur(identityCard);
		 $(".chinese").blur(chineseWord);
		 
		 $("#jobIncome").blur(checkJobIncome);
		 $("#company").blur(checkCompany);
		 $("#dept").blur(checkDept);
		 
		 
		 
		  $('.apply').click(function(){
			var temp= 0;
			 if(!chineseWord() ) {
				 temp++;
			 }
			 if( !mobileNum() ) {
				 temp++;
			 }
			 if( !identityCard() ) {
				 temp++;
			 }
			 if(!checkCompany() ) {
				 temp++;
			 }
			 if( !checkDept()) {
				 temp++;
			 }
			 if( !checkJobIncome()) {
				 temp++;
			 }
			 if( temp>0){
				 return false;
			 }else{
				 $('#remark').val($('#company').val()+":"+$('#dept').val())
				 $("#form_update_person").submit();
			 }
		  })
		</script>	
	</body>
</html>