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
               <c:set var="realName" value="${loanDo.loanPersonDo.realName}"/>
               <c:if test="${realName== null && loanChannelDo.loanUserDo.name != null}">
               		<c:set var="realName" value="${loanChannelDo.loanUserDo.name}"/>
               </c:if>
               <input type="text" name="realName" value="${realName}" class="txtinput not_null chinese"/>
             </dt>
             <dd></dd>
             <p class="line"></p>
             <dt>
               <label><span>*</span>手机号码：</label>
               <c:set var="mobile" value="${loanDo.loanPersonDo.mobile}"/>
               <c:if test="${mobile== null && loanChannelDo.loanUserDo.mobile != null}">
               		<c:set var="mobile" value="${loanChannelDo.loanUserDo.mobile}"/>
               </c:if>
               <input type="number" name="mobile" value="${mobile}" class="txtinput not_null tel" />
             </dt>
             <dd></dd>
             <p class="line"></p>
             <dt>
               <label><span>*</span>身份证号：</label>
               <c:set var="idNo" value="${loanDo.loanPersonDo.idNo}"/>
               <c:if test="${idNo== null && loanChannelDo.loanUserDo.idNo != null}">
               		<c:set var="idNo" value="${loanChannelDo.loanUserDo.idNo}"/>
               </c:if>
               <input type="text" name="idNo" value="${idNo}" class="txtinput not_null identity" />
             </dt>
             <dd></dd>
           </dl>
           <dl class="loaninfo" style="margin:0 8px 25px;">
             <dt>
               <label style="width:110px;">推荐人手机号码：</label>
               <input type="number" name="referenceMobile" value="${loanDo.loanPersonDo.referenceMobile}" class="txtinput tel2" />
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
		 
		 $(".tel").blur(mobileNum);
		 $(".identity").blur(identityCard);
		 $(".chinese").blur(chineseWord);
		 
		 
		 
		  $('.apply').click(function(){
			 //   if(isNull()) {
				 // if(chineseWord()&&mobileNum()&&identityCard()){
					//  $("#form_update_person").submit();
				 // };
			 //   } else {
				// alert("请填写完带*必填项");
			 //   }

			 // chineseWord();
			 // mobileNum();
			 // identityCard();
			 // $("#form_update_person").submit();

			 if(!chineseWord() || !mobileNum() || !identityCard()) {
			 	return false;
			 }

			var tel2 = $(".tel2");
			if(tel2.val().length){
				var reg = /(1[3,4,5,7,8]\d{9}$)/;
				 if (!reg.test(tel2.val())){
					tel2.parents('dt').next('dd').text('推荐人手机号码不正确');
					 return false;
				 }

				 if(tel2.val()==$(".tel").val()){
					tel2.parents('dt').next('dd').text('推荐人手机号码不能和申请手机号码相同。');
					 return false;
				 }
			}

			 $("#form_update_person").submit();

		  })
		</script>	
	</body>
</html>