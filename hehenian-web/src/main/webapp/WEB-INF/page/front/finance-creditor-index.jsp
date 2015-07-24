<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<jsp:include page="/include/head.jsp"></jsp:include>
</head>
<body>
	<!-- 引用头部公共部分 -->
	<jsp:include page="/include/top.jsp"></jsp:include>
	<div class="center">
		<div class="wytz_center">
			<div class="wytz_center_one">
				<a href="javascript:reggg()" class="wytz_a1">NO1. 注册合和年在线</a> <a href="rechargeInit.do" class="wytz_a2">NO2.账户充值</a> <a href="finance.do" class="wytz_a3">NO3.选择投资类型</a> <a
					href="finance.do" class="wytz_a4">NO4.立即投资</a> <a href="investSuccessedRecord.do" class="wytz_a5">NO5.回收本息</a>
			</div>
			<div class="cle"></div>
			<div class="mjd_tzlc_all">
				<ul>
					<li id="tab_1">已发布的借款</li>
					<li id="tab_2">即将发布的借款</li>
<%--					<li id="tab_3" class="tzlc">正在转让的债权</li>--%>
				</ul>
				<div class="cle"></div>
				<div class="mjd_tzlc_all_center">
					<div class="mjd_tzlc_left" style="display:block;">
						<h1>
							<table width="725" border="0">
								<tr height="40">
									<td width="100" align="center"><b>借款标题</b>
									</td>
									<td width="100" align="center"><b>下次收款日</b>
									</td>
									<td width="80" align="center"><b>借款人信用</b>
									</td>
									<td width="80" align="center"><b>待收本金</b>
									</td>
									<td width="140" align="center"><b>债权详情</b>
									</td>
									<td width="90" align="center"><b>转让价格</b>
									</td>
									<td width="135" align="center"><b>操作</b>
									</td>
								</tr>
							</table>
						</h1>
						<s:if test="pageBean.page!=null || pageBean.page.size>0">
							<s:iterator value="pageBean.page" var="finance">
								<div class="mjd_tzlc_left_one">
									<table width="725" border="0">
										<tr>
											<td width="100"><a href="javascript:buy(${finance.id})"> <shove:sub size="15" value="#finance.borrowNumber" suffix="..." /> </a></td>
											<td width="100" align="center">
												<p>${finance.repayDate}</p></td>
											<td width="80" align="center"><img src="images/ico_13.jpg"
												title="<s:property value="#finance.creditrating" default="0"/>分" /></td>
											<td width="80" align="center">${finance.repayPeriod }%</td>
											<td width="140" align="left">
												<p>债权总额：${finance.debtSum }</p>
												<p>剩余期数：${finance.num }</p>
												<p>年利率：${finance.annualRate }%</p></td>
											<td width="90" align="center">${finance.auctionBasePrice }</td>
											<td width="135" align="center"><s:if test="#finance.debtStatus==1">
													<input type="button" value="申请中" style="background: #ccc;cursor: default;" disabled="disabled" />
												</s:if> <s:elseif test="#finance.debtStatus==2">
													<input type="button" value="转让中" onclick="buy(${finance.id})" />
												</s:elseif> <s:elseif test="#finance.debtStatus==3">
													<input type="button" value="已转让" style="background: #ccc;cursor: default;" disabled="disabled" />
												</s:elseif></td>
										</tr>
									</table>
								</div>
								<div class="cle"></div>
							</s:iterator>
						</s:if>
						<s:else>
							<li style="text-align: center;padding-top: 20px;padding-bottom: 20px;">没有数据</li>
						</s:else>
						<div class="cle"></div>
						<s:if test="pageBean.page!=null || pageBean.page.size>0">
							<div class="mjd_fy_all">
								<shove:page url="finance.do" curPage="${pageBean.pageNum}" showPageCount="7" pageSize="${pageBean.pageSize }" theme="number" totalCount="${pageBean.totalNum}">
								</shove:page>
								<div class="cle"></div>
							</div>
						</s:if>
					</div>
				</div>
				<div class="mjd_tzlc_right">
					<div class="mjd_tzlc_right_two">
						<div class="mjd_tzlc_right_two_a">
							<strong>收益计算器</strong>
						</div>
						<table width="230" border="0">
							<tr height="50">
								<td width="80" align="right"><span>投标金额：</span>
								</td>
								<td><input type="text" id="borrowSum" />
								</td>
							</tr>
							<tr>
								<td width="80" align="right"><span>年利率：</span>
								</td>
								<td><input type="text" id="yearRate" />%</td>
							</tr>
							<tr height="50">
								<td width="80" align="right"><span>投资期限：</span>
								</td>
								<td><input type="text" id="borrowTime" />
								</td>
							</tr>
							<tr>
								<td width="80" align="right"><span>还款方式：</span>
								</td>
								<td><select id="repayWay"><option>按月还款</option>
										<option>先息后本</option>
								</select>
								</td>
							</tr>
							<tr height="5"></tr>
							<tr height="50">
								<td width="80" align="right">&nbsp;</td>
								<td><input type="button" value="计算" onclick="rateCalculate()" />
								</td>
							</tr>
							<tr>
								<td colspan="2"><strong><span style="color: red; float: none;" class="formtips" id="show_error"></span> </strong>
								</td>
							</tr>
						</table>
						<span id="showIncome"></span>
					</div>
				</div>
			</div>
			<div class="cle"></div>
		</div>
	</div>
	<div class="cle"></div>
	<!-- 引用底部公共部分 -->
	<jsp:include page="/include/footer.jsp"></jsp:include>
	<script type="text/javascript" src="script/jquery.shove-1.0.js"></script>
	<script type="text/javascript" src="script/jbox/jquery.jBox-2.3.min.js"></script>
	<script type="text/javascript" src="script/jbox/jquery.jBox-zh-CN.js"></script>
	<script type="text/javascript">
$(document).ready(function(){
	    var ck_type = "${paramMap.type}";
	    var no = ck_type.split(',');
	    if(no != ''){
	       for(var i=1;i<=no.length;i++){
	          $('#ck_mode_'+i).attr('checked','checked');
	       }
	    }	
	     $("#province").change(function(){
			var provinceId = $("#province").val();
			registedPlaceCity(provinceId, 2);
		});
});
  
function registedPlaceCity(parentId, regionType){
		var _array = [];
		_array.push("<option value='-1'>--请选择--</option>");
		var param = {};
		param["regionType"] = regionType;
		param["parentId"] = parentId;
		$.post("ajaxqueryRegion.do",param,function(data){
			for(var i = 0; i < data.length; i ++){
				_array.push("<option value='"+data[i].regionId+"'>"+data[i].regionName+"</option>");
			}
			$("#registedPlaceCity").html(_array.join(""));
		});
	}
function  checkTou(id,type){
	 var param = {};
	 param["id"] = id;
     $.shovePost('financeInvestInit.do',param,function(data){
	   var callBack = data.msg;
	   if(callBack !=undefined){
	     alert(callBack);
	   }else{
		   if(type==2){
				var url = "subscribeinit.do?borrowid="+id;
		     	 $.jBox("iframe:"+url, {
			    		title: "我要购买",
			    		width: 450,
			    		height: 450,
			    		buttons: {  }
			    		});
			}else{
				 window.location.href= 'financeInvestInit.do?id='+id;
		   }
	   }
	 });
}
function closeMthod(){
	window.jBox.close();
	window.location.reload();
}

function clearComponentVal(){
        param = {};
        $('#titles').val('');
        $('#paymentMode').get(0).selectedIndex=0;
        $('#purpose').get(0).selectedIndex=0;
        $('#deadline').get(0).selectedIndex=0;
        $('#reward').get(0).selectedIndex=0;
        $('#arStart').get(0).selectedIndex=0;
}


		function rateNumJudge(){//验证利息计算输入数字是否正确
	 	   if($("#borrowSum").val()==""){
	 	      $("#show_error").html("&nbsp;&nbsp;投资金额不能为空");
	 	      $("#showIncome").html("");
	 	   }else 
	 	   if(!/^(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-9]*))$/.test($("#borrowSum").val())){
	 	       $("#show_error").html("&nbsp;&nbsp;请输入正确的投资金额进行计算");
	 	       $("#showIncome").html("");
	 	   }else 
	 	   if($("#yearRate").val()==""){
	 	      $("#show_error").html("&nbsp;&nbsp;年利率不能为空");
	 	      $("#showIncome").html("");
	 	   }else 
	 	   if(!/^(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-9]*))$/.test($("#yearRate").val())){
	 	      $("#show_error").html("&nbsp;&nbsp;请输入正确的年利率");
	 	      $("#showIncome").html("");
	 	   }else 
	 	   if($("#borrowTime").val()==""){
	 	       $("#show_error").html("&nbsp;&nbsp;投资期限不能为空");
	 	       $("#showIncome").html("");
	 	   }else 
	 	   if(!/^[0-9]*[1-9][0-9]*$/.test($("#borrowTime").val())){
		 	    $("#show_error").html("&nbsp;&nbsp;请输入正确投资期限");
		 	    $("#showIncome").html("");
	 	   }else {
	 	    //if(!/^[0-9].*$/.test($("#bidReward").val())){
		 	//      $("#show_error").html("&nbsp;&nbsp;请输入正确的投资奖励");
		// 	      $("#showIncome").html("");
	 	//   }else 
		 //	if(!/^[0-9].*$/.test($("#bidRewardMoney").val())){
			 	//      $("#show_error").html("&nbsp;&nbsp;请输入正确的现金奖励 ");
			 	//      $("#showIncome").html("");
	 	   // }else{
	 	      $("#show_error").html("");
	 	   }
	 	}
	 	function buy(id){
	 		window.location.href="queryDebtDetailHHN.do?id="+id;
	 	}
	 	function rateCalculate(){//利息计算
	 	    var str = rateNumJudge();
	 	    param["paramMap.borrowSum"] = $("#borrowSum").val();
	        param["paramMap.yearRate"] = $("#yearRate").val();
	        param["paramMap.borrowTime"] = $("#borrowTime").val();
	        param["paramMap.repayWay"] = $("#repayWay").get(0).selectedIndex;
	        param["paramMap.bidReward"] = $("#bidReward").val();
	        param["paramMap.bidRewardMoney"] = $("#bidRewardMoney").val();
	        
	        var _array = [];
	        
			if($("#show_error").text()!=""){
			   return;
			}
			$.shovePost("incomeCalculate.do",param,function(data){
			   //只有一条记录
			   if(data == 1){
			     $("#show_error").html("请填写完整信息进行计算");
			     return;
			   }
			   _array.push("<table>");
			    for(var i = 0; i < data.length; i ++){
			    	data[i].income2year = data[i].income2year < 1 ? "0" + data[i].income2year : data[i].income2year;
			    	data[i].rateSum = data[i].rateSum < 1 ? "0" + data[i].rateSum : data[i].rateSum;
					_array.push("<tr><td style='padding-left:20px'><span>投标奖励：</span><span>"+data[i].reward+"元</span><br/>"
					+"<span>总计利息：</span><span>"+data[i].rateSum+"元</span><br/>"
					+"<span>每月还款：</span><span>"+data[i].monPay+"元</span><br/>"
					+"<span>总共收益：</span><span>"+data[i].allSum+"元</span></td></tr>");
					/*+"<span>年化收益：</span><span>"+data[i].income2year+"%</span><br/>" +"<span>总计收益：</span><span>"+data[i].netIncome+"元(扣除10%管理费)</span>
					_array.push("<p>投标奖励："+data[i].reward+"元</p><br /><br />"
					+"<p>年化收益："+data[i].income2year+"元</p><br /><br />"
					+"<p>总收益："+data[i].allSum+"元</p><br /><br />"
					+"<p>每月还款："+data[i].monPay+"元</p><br /><br />"
					+"<p>总计收益(扣除10%管理费)："+data[i].netIncome+"元</p>");*/
				}
				//_array.push("</table>");
				$("#showIncome").html(_array.join(""));
			});
	 	}

</script>
	<script type="text/javascript">
	function reggg(){
		if(${session.user!=null}){
			alert("你已是登录用户");
		}else{
			window.location.href="/account/reg.do";
		}
	}
		$(function(){
			hhn(1);
			$("#tab_1").click(function(){
			    window.location.href = "finance.do"
			});
			$("#tab_2").click(function(){
			   window.location.href = "soonPublishList.do"
			});
			$("#tab_3").click(function(){
               window.location.href = "queryPublishDebt.do"
			});
		});
</script>

</body>
</html>
