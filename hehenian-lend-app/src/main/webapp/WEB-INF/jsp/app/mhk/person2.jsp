<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="../include/top.jsp"%>
<title>个人信息</title>
</head>
<body>
	<section>
		<article>
			<form id="person2Form" action="<c:url value='/app/mhk/updatePersonJob.do'/>" method="post">
				<input type="hidden" name="jobId" value="${jobDo.jobId }"> <input
					type="hidden" name="loanId" value="${jobDo.loanId }"> <input
					type="hidden" name="loanPersonId" value="${jobDo.loanPersonId }">
				<h3 class="tip tip2">填写个人职业信息</h3>
				<div class="p1 db_f">
					<label class="lab lab2" for="jobs">工作类型</label> <select
						class="sel bf1 br1" name="jobType" id="jobType">
						<option value="SALARYMAN"
							<c:if test="${jobDo.jobType=='SALARYMAN' }">selected=selected </c:if>>工薪阶级</option>
						<option value="SELF_EMPLOYED"
							<c:if test="${jobDo.jobType=='SELF_EMPLOYED' }">selected=selected </c:if>>个体户</option>
						<option value="EMPLOYER"
							<c:if test="${jobDo.jobType=='EMPLOYER' }">selected=selected </c:if>>企业</option>
					</select>
				</div>
				<div class=" p1 db_f">
					<label class="lab lab2" for="companyName">工作单位</label> <input
						class="bf1 txt br1  must" type="text" id="companyName"
						name="companyName" value="${jobDo.companyName }" />
				</div>
				<div class="p1 db_f">
					<label class="lab lab2" for="worklevel">职位级别</label> <select
						class="sel bf1 br1" name="position" id="position">
						<option value="0"
							<c:if test="${jobDo.position==0 }">selected=selected </c:if>>法人</option>
						<option value="1"
							<c:if test="${jobDo.position==1 }">selected=selected </c:if>>总经理/总监</option>
						<option value="2"
							<c:if test="${jobDo.position==2 }">selected=selected </c:if>>经理/主管</option>
						<option value="3"
							<c:if test="${jobDo.position==3 }">selected=selected </c:if>>普通员工</option>
					</select>
				</div>
				<section>
					<article>
					<%-- 	<c:if test="${jobDo.jobType!='SALARYMAN' }"> class="dn"</c:if>> --%>
						<div class=" p1 db_f">
							<label class="lab lab2" for="jobIncome">税后收入</label>
							<div class="bf1 db_f">
								<input class="bf1 txt br_l1 " type="number" id="jobIncome"
									name="jobIncome"
									value="<fmt:formatNumber value="${jobDo.jobIncome }" pattern="##.##" minFractionDigits="2" />">
								<span class="br_r1 after_unit">万</span>
							</div>
						</div>
						<div class=" p1 db_f">
							<label class="lab lab2" for="jobYear">工作年限</label>
							<div class="bf1 db_f">
								<input class="bf1 txt br_l1 " type="number" id="jobYear"
									name="jobYear" value="${jobDo.jobYear }" /> <span
									class="br_r1 after_unit">年</span>
							</div>
						</div>
						<div class=" p1 db_f">
							<label class="lab" for="joinDate">入职时间</label>
							<div class="bf1 db_f">
								<label class="bf1 db_f "> <select class="sel year bf1 br1"
									name="companyInTime_year" id="companyInTime_year">
									<option value="${companyInTime_year }">${companyInTime_year }</option>
								</select> <em>年</em>
								</label> <label class="bf1 db_f"> <select class="sel month bf1 br1"
									name="companyInTime_month" id="companyInTime_month">
									<option value="${companyInTime_month }">${companyInTime_month }</option>
								</select> <em>月</em>
								</label> <label class="bf1 db_f"> <select class="sel day bf1 br1"
									name="companyInTime_day" id="companyInTime_day">
									<option value="${companyInTime_day }">${companyInTime_day }</option>
								</select> <em>日</em>
								</label>
							</div>
						</div>
					</article>
					<article id="workType">
						<%-- <c:if test="${jobDo.jobType=='SALARYMAN' }"> class="dn"</c:if> --%>
						<div class=" p1 db_f">
							<label class="lab lab2" for="jobIncome1">税后营业额</label>
							<div class="bf1 db_f">
								<input class="bf1 txt br_l1 must" type="number" id="jobIncome1"
									name="jobIncome1"
									value="<fmt:formatNumber value="${jobDo.jobIncome }" pattern="##.##" minFractionDigits="2" />">
								<span class="br_r1 after_unit">万</span>
							</div>
						</div>
						<div class=" p1 db_f">
							<label class="lab lab2" for="jobYear1">营业时长</label>
							<div class="bf1 db_f">
								<input class="bf1 txt br_l1 must" type="number" id="jobYear1"
									name="jobYear1" value="${jobDo.jobYear }" /> <span
									class="br_r1 after_unit">年</span>
							</div>
						</div>
						<div class=" p1 db_f">
							<label class="lab " for="joinDate">营业执照</label> <input
								class="bf1 txt br1" type="text" id="certNo" name="certNo"
								value="${jobDo.certNo }" />
						</div>
					</article>
				</section>
				<div class=" p1 db_f">
					<label class="lab" for="telNum">单位电话</label>
					<div class="bf1">
						<p class="db_f">
							<input class="txt area_code br1" type="number"
								name="companyPhone1" value="${companyPhone1 }" maxlength="4" /> <em>-</em> <input
								class="txt bf1 br1" type="number" id="companyPhone"
								name="companyPhone2" value="${companyPhone2 }" />
						</p>
					</div>
				</div>
				<div class=" p1 db_f">
					<label class="lab" for="address">单位地址</label> <input
						class="bf1 txt br1" type="text" id="companyAddr"
						name="companyAddr" value="${jobDo.companyAddr }" />
				</div>
				<div class="btn_box">
					<button class="btn w10" id="sub">提交</button>
				</div>
			</form>
		</article>
	</section>
	<%@ include file="../include/foot.jsp"%>
	<script>
	$(function() {
		var num = $("#jobType").val();
		var setJobType = function () {
			if (num != 'SALARYMAN') {
				$("#workType").removeClass("dn").prev().addClass("dn").find(".txt").removeClass("must");
				$("#workType").find(".lab2").next().find('.txt').addClass("must");
				return false;
			}
			$("#workType").addClass("dn").prev().removeClass("dn").find(".txt").addClass("must");
			$("#workType").find(".must").removeClass("must");
		}
		setJobType();
		$("#jobType").change(function() {
			num = $(this).val();
			setJobType();
		});
		
		var companyInTime_year = '${companyInTime_year }'; 
		var companyInTime_month = '${companyInTime_month }'; 
		var companyInTime_day = '${companyInTime_day }'; 
		
		setDateSelect(companyInTime_year,companyInTime_month,companyInTime_day,function(_strY, _strM, _strD){
			$("#companyInTime_year").html(_strY);
			$("#companyInTime_month").html(_strM);
			$("#companyInTime_day").html(_strD);
		});
	})
	</script>
</body>
</html>