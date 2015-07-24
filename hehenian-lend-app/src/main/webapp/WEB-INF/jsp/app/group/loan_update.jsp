<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="include/top.jsp"%>
	<title>贷款金额</title>
</head>

<body>
	<form id="upd_loan" action="applyfor" method="post" >
		<input type="hidden" name="loanId" value="${loanId}"/>
		<input type="hidden" name="hasHouse" value="F" />
		<input type="hidden" id="loan_Date"  name="loanPeriod" value="3" />
		<div class="eLoan">  
			<dl class="loaninfo ">
				<dt>
					<label><span>*</span>借款金额：</label>
					<c:if test="${loanDo.applyAmount != null}">
					<c:set var="applyAmount" value="${loanDo.applyAmountString}"/>
					</c:if>
					<input type="number"  min="1"  name="applyAmount" value="${applyAmount}"  placeholder="输入100整数倍金额"  class="txtinput not_null money"/>
					<span>元</span>
				</dt>
				<dd>
					<c:if test="${resultCode eq 'money'}">
					${message}
					</c:if>
				</dd>
				<p class="line"></p>
				<dt class="radioBox">
					<label><span>*</span>借款期限：</label>
					<label><input type="radio"  id="0" name="0" value="3" checked="checked" >三个月</label>
				    <label><input type="radio"  id="0" name="0" value="6" >六个月</label>
				</dt>
				<dd></dd>
				<p class="line"></p>
				<dt>
					<span>&nbsp;</span>根据系统计算您最多可以借贷：<span id="creditAmountTip">0.00</span>元<br>
					<span>&nbsp;</span>到账金额请以最终放款金额为准。
				</dt>
				<dd></dd>
				<p class="line"></p>
				<dt class="radioBox">
					<input type="checkbox" id="chkAgreement1"  style="width: 52px; height: 15px;" /> 我已阅读并同意
					<a href="${fileServerUrl }/app_res/word/concat.html" target="_black">《合和年在线借款协议》</a>
				</dt>
				<dd style="padding-left: 25px;"></dd>
				<p class="line"></p>
				<dt class="radioBox">
					<input type="checkbox" id="chkAgreement2"  style="width: 52px; height: 15px;" /> 我已阅读并同意
					<a href="${fileServerUrl }/app_res/word/consult.html" target="_black">《借款咨询服务协议》</a>
				</dt>
			<dd style="padding-left: 25px;"></dd>
			</dl>
		<div class="submit_btn db_f">
			<div class="bf1"><a href="javascript:void(0);" class="apply look">查看每月还款金额</a></div>
			<div class="bf1"><a href="javascript:void(0);" id="apply" class="apply">提交申请</a></div>
		</div>
	</div>

	<div class="opacity" style="display:none">
		<div class="alertcontairn" style="padding:0 0 10px; background:#fff;">
			<h4 class="alerthead">还款计划：</h4>
			<span class="closeOpacity closeCcl"><img src="${fileServerUrl }/app_res/img/elend/alertClose.png" class="boximg" /></span>
			<div class="textinfo">
				<div class="loantable"  >
					<table>
						<thead>
							<tr>
								<th>第x个月</th>
								<th>月还款金额</th>
							</tr>
						</thead>
						<tbody></tbody>
					</table>
				</div>
			</div>
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
        	var money =0;

        	$('.money').focus(function(){
				 $(this).parent().next("dd").text("");
			 }).blur(function() {
        		var that = $(this);
        		money =that.val()*1;
				  if(money.toString().indexOf('') > 0){
				 	$(this).parents().next("dd").text("只能输入整数金额");
				 	return false;
				 } else if(money % 100) {
				 	$(this).parents().next("dd").text("只能输入100的整数倍金额");
				 	return false;
				 }
			  var maxLoanAmount = parseFloat($("#creditAmountTip").text());
    			if (money > maxLoanAmount) {
    				$(".money").parents().next("dd").text("您的最多可以借贷金额为" + maxLoanAmount + "元");
    				return false;
    			}
			})


        	$(".look").click(function(){
	        	money = $('.money').val()*1;
		  		  if(money.toString().indexOf('') > 0){
				 	$(".money").parents().next("dd").text("只能输入整数金额");
				 	return false;
				 } else if(money % 100) {
				 	$(".money").parents().next("dd").text("只能输入100的整数倍金额");
				 	return false;
				 }		
				getLoanType();
        	})

        	function getLoanType() {
        		if(money.toString().indexOf('') > 0){
					return false;
				} else if(money % 100) {
					return false;
				}	

				var loanDate = $("#loan_Date").val();
			 	$.get('<c:url value="calSettDetail.do"/>',{loanAmount: money,loanPeriod: loanDate}, function (result) {
				 	if(!result.success){
				 		$(".money").parents().next("dd").text(result.errorMessage);
				 		return false;
				 	}					
				 	var data =  result.model[0]; 
				 	var str='';
				 	for(var i in data) {
				 		str+='<tr><td>'+(i * 1+1) +'</td><td>'+ data[i] +'</td></tr>';
				 	}
				 	$(".loantable tbody").html(str);
				 	$('.opacity').show();
				 });
        	}

        	$("#apply").click(function(){
        		var a = 0 ;
    	  		  if(money<=0){
    	  			$(".money").parents().next("dd").text("借款金额有误");
    			 	a++;
    	  		  }else if(money.toString().indexOf('') > 0){
    			 	$(".money").parents().next("dd").text("只能输入整数金额");
    			 	a++;
    			 } else if(money % 100) {
    			 	$(".money").parents().next("dd").text("只能输入100的整数倍金额");
    			 	a++;
    			 }else{
    				 $(".money").parents().next("dd").text("");
    			 }
    	  		var maxLoanAmount = parseFloat($("#creditAmountTip").text());
    			if (money > maxLoanAmount) {
    				$(".money").parents().next("dd").text("您的最多可以借贷金额为" + maxLoanAmount + "元");
    				a++;
    			}else{
    				$(".money").parents().next("dd").text("");
    			}
    			if(!$("#chkAgreement1").attr("checked")){
      	  			$("#chkAgreement1").parents().next("dd").text("请先阅读并同意[合和年在线借款协议]");
      	  			a++;
      			}else{
      				$("#chkAgreement1").parents().next("dd").text("");
      			}
     			if(!$("#chkAgreement2").attr("checked")){
      	  			$("#chkAgreement2").parents().next("dd").text("请先阅读并同意[借款咨询服务协议]");
      	  			a++;
      			}else{
      				$("#chkAgreement2").parents().next("dd").text("");
      			}
     			if(a>0){
    				return false;
    			}else{
    				$("#upd_loan").submit();
    			}
        	})


		$("select").change(function(){
			$(this).parent().next("dd").text("");
		})

		$("input[type='radio']").change(function(){
			var btnVal = $(this).val();
			$("input[name='loanPeriod']").val(btnVal);
			calCreditAmount(${loanPersonDo.jobDo.jobIncome},btnVal)
		});
        	
        function calCreditAmount(income,loanPeriod){
   			$.get("calCreditAmountGroup.do", {
   				income : income,
   				loanPeriod : loanPeriod
   			}, function(result) {
   				if (!result.error) {
   					$("#creditAmountTip").text(result.creditAmount);
   				} else {
   					$("#creditAmountTip").text(result.errorMessage);
   				}
   				if (money > result.creditAmount) {
    				$(".money").parents().next("dd").text("您的最多可以借贷金额为" + result.creditAmount + "元");
    				return false;
	    		}else{
	    			$(".money").parents().next("dd").text("");
	    		}
   			});
   		};
   		calCreditAmount(${loanPersonDo.jobDo.jobIncome},$("input[name='loanPeriod']").val());
		  //关闭弹出框
		  $('.closeOpacity').click(function(){
		  	$('.opacity').hide();	
		  })
		</script>
		
	</body>

	</html>