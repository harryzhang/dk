<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="../../include/top.jsp"%>
	<title>逾期</title>
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
			<ul class=" db_f manage_nav">
				<li class="bf1 t_ac check"><em>逾期</em></li> 
				<li class="bf1 t_ac"><em>待还款</em></li> 
				<li class="bf1 t_ac"><em>已还款</em></li>
				<li class="bf1 t_ac"><em>已还清</em></li>
			</ul>
		</header>
		<article>
			<section id="dataBody">
			</section>
		</article>
	</section>
	
	<%@ include file="../../include/foot.jsp"%>
	<script>	
		$( function() {
			var curPage = 1;
			var pageSize = 10;
			var realName = '';
			var loanStatus = 1;

			sessionStorage.setItem("index", 1);	
			$(".manage_nav li").click(function(){
				$(this).addClass("check").siblings().removeClass("check");
				curPage=1;
				realName = '';
				loanStatus = $(this).index() + 1;
				getData();
			});

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
				$.post('<c:url value="/app/mhk/loanManage/overdueList.do"/>', 
					{curPage:curPage, pageSize: pageSize,realName:realName,loanStatus:loanStatus}, function(result) {
						var data = result.data;
						var box = $("#dataBody");
						var str = '';
						for(var index in data) {
							var item = data[index];
							var createDate = new Date(item.loanTime);
							str += '<article class="order_list">'+
							' 	<div class="db_f order_head p1">'+
							'		<date class="bf1">'+ item.loanTime +'</date>';

							switch(loanStatus) {
								case 1:
								str+='<span class="fcr">逾期'+ (item.overdue <= 3 ? "": item.overdeu <=15?"!":item.overdeu<=30?"!!":"!!!") +''+ item.overdue +'天</span>';
								break;
								case 2:
								str+='<span class="fcl">待还款</span>';
								break;
								case 3:
								str+='<span class="fcl">已还款</span>';
								break;
								case 4:
								str+='<span class="fcl">已还清</span>';
								break;
							}
							str+= '		</div>'+
							'		<div class="db_f order_item p1">'+
							'			<div class="bf1">'+
							'				<p class="db_f"><label>借款金额：</label><span class="bf1 fcl">'+ item.applyAmount +'</span></p>';
							if(loanStatus<4){
								str+=' 		<p class="db_f"><label>还款日期：</label><span class="bf1 fcl">'+ item.repayDate +' </span></p>';
							} 	


							str+='		</div>'+
							'			<div class="bf1">'+
							'				<p class="db_f"><label>还款期数：</label><span class="fcl">'+ item.loanPeriod +'</span>个月</p>';
							if(loanStatus<4){
								str+='		<p class="db_f"><label>还款金额：</label><span class="fcl">'+ item.stillPrincipal +'</span>元</p>';
							} 
							str+='		</div>'+
							'		</div>'+
							'<div class="db_f p1"><span class="bf1"> </span><a href="'+'<c:url value="/app/mhk/loanManage/repayDetail.do?detailType='+loanStatus+'&loanId='+ item.loanId+'"/>';
							if(loanStatus!=4){
								str+='&repayPeriod='+ item.repayPeriod;
							}
							str+='" class="btn">查看记录</a></div>'+
							'</article>';
						};
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