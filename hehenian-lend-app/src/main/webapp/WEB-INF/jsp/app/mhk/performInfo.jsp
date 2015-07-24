<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8" />
	<%@ include file="../include/top.jsp"%>
	<title>
		<c:if test="${status=='PENDING' }">新订单</c:if>
		<c:if test="${status=='BORROWING' }" >还款中</c:if>
		<c:if test="${status=='BORROWED' }" >已还清</c:if>
		<c:if test="${status=='NOPASS' }" >已拒绝</c:if>
	</title>
</head>
<body>
	<section>
		<header>
			<div class="p1">
				<form id="searchForm">
					<input type="search" placeholder="姓名/手机号码" class="txt w10 br05" name="realName" id="realName"/>
					<span class="pa icon_search"></span>
				</form>
			</div>
		</header>

		<section>
			<c:if test="${status=='PENDING'}"> 
			<article class="p1">
				<div class="db_f order_item" style="border:0">
					<div class="bf1" style="width:80px">
						<p>新增订单：</p>
						<p>借款金额：</p>
					</div>
					<div class="bf1 t_ar ">
						<p class="db w10"><span class="fcl dib plr05">${sumIds }</span>笔</p>
						<p class="db w10"><span class="fcl dib plr05"><fmt:formatNumber value="${sumApplyAmount}" pattern="#,#00.00#"  /></span>元</p>
					</div>
				</div>
			</article>
		</c:if>
		<c:if test="${status=='BORROWING' }" >
		<article class="p1">
			<div class="db_f order_item" style="border:0">
				<div class="bf1" style="width:80px">
					<p>还款中订单数：</p>
					<p>已还金额：</p>
					<p>未还金额：</p>
				</div>
				<div class="bf1 t_ar ">
					<p class="db w10"><span class="fcl dib plr05">${sumIds }</span>笔</p>
					<p class="db w10"><span class="fcl dib plr05"><fmt:formatNumber value="${sumBorrowedAmount}" pattern="#,#00.00#"  /></span>元</p>
					<p class="db w10"><span class="fcl dib plr05"><fmt:formatNumber value="${sumBorrowingAmount}" pattern="#,#00.00#"  /></span>元</p>
				</div>
			</div>
		</article>
	</c:if>
	<c:if test="${status=='BORROWED' }" >
	<article class="p1">
		<div class="db_f order_item" style="border:0">
			<div class="bf1" style="width:80px">
				<p>已还清：</p>
				<p>已还金额：</p>
			</div>
			<div class="bf1 t_ar ">
				<p class="db w10"><span class="fcl dib plr05">${sumIds }</span>笔</p>
				<p class="db w10"><span class="fcl dib plr05"><fmt:formatNumber value="${sumBorrowAmount}" pattern="#,#00.00#"  /></span>元</p>
			</div>
		</div>
	</article>
</c:if>
<c:if test="${status=='NOPASS' }" > 
<article class="p1">
	<div class="db_f order_item" style="border:0">
		<div class="bf1" style="width:80px">
			<p>已拒绝：</p>
			<p>借款金额：</p>
		</div>
		<div class="bf1 t_ar ">
			<p class="db w10"><span class="fcl dib plr05">${sumIds }</span>笔</p>
			<p class="db w10"><span class="fcl dib plr05"><fmt:formatNumber value="${sumApplyAmount}" pattern="#,#00.00#"  /></span>元</p>
		</div>
	</div>
</article>
</c:if>
</section>	

<article>
	<section id="perform">

	</section>
</article>		
</section>

<%@ include file="../include/foot.jsp"%>
<script>	
	$( function() {
		var curPage = 1;
		var pageSize = 10;
		var realName = '';
		var loanStatus = "${status}";

		$("#searchForm").submit(function() {
			curPage=1;
			realName = $("#realName").val();
			//loanStatus = '';
			getData();
			return false;
		});

		scrollLoad(function () {
			++curPage;
			getData();
		});

		function getData() {
			$.post('<c:url value="/app/mhk/getPerformAjax.do"/>', 
				{curPage:curPage, pageSize: pageSize,realName:realName,status:loanStatus}, function(result) {
					var data = result.list;
					var box = $("#perform");
					var str = '';
					for(var index in data) {
						var item = data[index];
						var createDate = new Date(item.createTime);
						str += '<article class="order_list">'+
						' 	<div class="db_f order_head p1">'+
						'		<date class="bf1">'+ createDate.getFullYear() +'-'+ (createDate.getMonth() + 1) +'-'+ createDate.getDate() +' </date>'+
						'			<span class="fcl">';

						switch(item.loanStatus) {
							case 'PENDING':
							str+='<span class="fcl">受理中 </span>';
							break;
							case 'PROCESSING':
							str+='<span class="fcl">审核中 </span>';
							break;
							case 'AUDITED':
							str+='<span class="fcl">待签约 </span>';
							break;
							case 'TREATY':
							str+='<span class="fcl">待放款 </span>';
							break;
							case 'BORROWING':
							str+='<span class="fcr">'+ (item.lateDay > 0 ? "逾期"+ item.lateDay +"天":"") + '</span>';
							break;
							case 'BORROWED':
							str+='<span class="fcl">已还清 </span>';
							break;
							case 'NOPASS':
							str+='<span class="fcl">已拒绝 </span>';
							break;
						}

						str+='		</div>';
						if(loanStatus =='PENDING' || loanStatus =='NOPASS'){
							str+= '		<div class="db_f order_item p1">'+
							'			<div class="bf1">'+
							'				<p class="db_f"><label>借款人：</label><span class="bf1 fcl">'+ item.realName +'</span></p>'+
							' 				<p class="db_f"><label>手机号码：</label><span class="bf1 fcl">'+ item.mobile +' </span></p>'+
							'			</div>'+
							'			<div class="bf1">'+
							'				<p class="db_f"><label>借款金额：</label><span class="fcl">'+ item.applyAmount +'</span>元</p>'+
							'				<p class="db_f"><label>借款期数：</label><span class="fcl">'+ item.loanPeriod +'</span>个月</p>'+
							'			</div>'+
							'		</div>';

							if(item.loanStatus =='PENDING'){
								str+=  '     <div class="db_f p1"><span class="bf1"> </span><a href="'+'<c:url value="/app/mhk/initPersonInfo.do?loanId='+ item.loanDo.loanId+'"/>' +'" class="btn">受理</a></div>';
							} else {
								/* str+=  '     <div class="db_f p1"><span class="bf1"> </span><a href="'+'<c:url value="/app/mhk/getReason.do?loanId='+ item.loanDo.loanId+ '"/>' +'" class="btn">查看原因</a></div>'; */
							}

						}
						else if(loanStatus =='BORROWING' || loanStatus =='BORROWED') {
							createDate = new Date(item.loanTime);
							str+= '		<div class="db_f order_item p1">'+
							'			<div class="bf1">'+
							'				<p class="db_f"><label>借款人：</label><span class="bf1 fcl">'+ item.realName +'</span></p>'+
							' 				<p class="db_f"><label>借款期数：</label><span class="bf1 fcl">'+ item.loanPeriod +' </span></p>'+
							'			</div>'+
							'			<div class="bf1">'+
							'				<p class="db_f"><label>借款金额：</label><span class="fcl">'+ item.loanAmount +'</span>元</p>'+
							'				<p class="db_f"><label>放款日期：</label><span class="fcl">'+ createDate.getFullYear() +'-'+ (createDate.getMonth() + 1) +'-'+ createDate.getDate() +' </span></p>'+
							'			</div>'+
							'		</div>'+
							'    ';
/* 							'     <div class="db_f p1"><span class="bf1"> </span><a href="'+'<c:url value="/app/mhk/getRecord.do?loanId='+ item.loanId+'"/>' +'" class="btn">查看记录</a></div>'; */
						}

					}
					str+= '</article>';
					if(curPage==1) {
						box.html(str);
					} else {
						box.append(str);
					}
		})
}
getData();
} )

</script>
</body>
</html>