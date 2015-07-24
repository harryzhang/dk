<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
	<head>
		<%@ include file="../include/top.jsp"%>
		<title>个人信息</title>
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
				<div class="plr1 sel_order" >
					<select name="loanStatus"  id="loanStatus" class="sel w10" >
					    <option value="">订单状态</option>
						<option value="PENDING">受理中</option>
						<option value="PROCESSING">审核中</option>
						<option value="AUDITED">待签约</option>
						<option value="NOPASS">已拒绝</option>
						<option value="TREATY">待放款</option>
						<option value="SUBJECTED">已上标</option>
					</select>
				</div>
			</header>
			
			<article>
				<section id="orderList">
				   
				</section>
			</article>	
		</section>
		
		<%@ include file="../include/foot.jsp"%>
			<script>	
				$( function() {
					var curPage = 1;
					var pageSize = 10;
					var realName = '';
					var loanStatus = '';
					
					sessionStorage.setItem("index", 0);	
					$("#loanStatus").change(function(){
						curPage=1;
						realName = '';
						loanStatus = $(this).val();
						getData();
					});
					
					$("#searchForm").submit(function() {
						curPage=1;
						realName = $("#realName").val();
						loanStatus = '';
						getData();
						return false;
					});
					scrollLoad(function () {
						++curPage;
						getData();
					});
					function getData() {
						$.post('<c:url value="getLoanPersonAjax.do"/>', 
								{curPage:curPage, pageSize: pageSize,realName:realName,loanStatus:loanStatus}, function(result) {
							var data = result.list;
							var box = $("#orderList");
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
										    	str+='受理中';
										    	break;
										  	case 'PROCESSING':
										  		str+='审核中';
										  		break;
										  	case 'AUDITED':
										  		str+='待签约';
										  		break;
										  	case 'NOPASS':
										  		str+='已拒绝';
										  		break;
										  	case 'SUBJECTED':
										  		str+='已上标';
										  		break;
										  	case 'TREATY':
										  		str+='待放款';
										  		break;
										  }
									str+= '         </span>'+
										  '		</div>'+
										  '		<div class="db_f order_item p1">'+
										  '			<div class="bf1">'+
										  '				<p class="db_f"><label>借款人：</label><span class="bf1 fcl">'+ item.realName +'</span></p>'+
										  ' 				<p class="db_f"><label>手机号码：</label><span class="bf1 fcl">'+ item.mobile +' </span></p>'+
										  '			</div>'+
										  '			<div class="bf1">'+
										  '				<p class="db_f"><label>借款金额：</label><span class="fcl">'+ item.applyAmount +'</span>元</p>'+
										  '				<p class="db_f"><label>借款期数：</label><span class="fcl">'+ item.loanPeriod +'</span>个月</p>'+
										  '			</div>'+
										  '		</div>';
										  switch(item.loanStatus) {
										  case 'PENDING':
											  str+='<div class="db_f p1"><span class="bf1"> </span><a href="'+'<c:url value="/app/mhk/initPersonInfo.do?loanId='+ item.loanDo.loanId +'"/> ' +'" class="btn">受理</a></div>';
											  break;
										  	case 'PROCESSING':
										  		str+='';
										  		break;
										  	case 'AUDITED':
										  		/* str+='<div class="db_f p1"><span class="bf1"> </span><a href="'+'<c:url value="/app/mhk/initTreatyData.do?loanId='+ item.loanDo.loanId +'"/> '+'" class="btn">签约</a></div>'; */
										  		break;
										  	case 'NOPASS':
										  		/* str+='<div class="db_f p1"><span class="bf1"> </span><a href="'+'<c:url value="/app/mhk/getReason.do?loanId='+ item.loanDo.loanId +'"/> '+'" class="btn">查看原因</a></div>'; */
										  		break;
										  	case 'TREATY':
										  		str+='';
										  		break;
										  }
									str+= '</article>';
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