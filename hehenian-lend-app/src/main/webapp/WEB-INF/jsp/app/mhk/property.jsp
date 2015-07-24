<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
		<%@ include file="../include/top.jsp"%>
		<title>个人信息</title>
	</head>
	<body>
		<section>
			<article>
				<form id="propertyForm" action="<c:url value='/app/mhk/saveOrUpdateProperty.do'/>" method="post">
				<input type="hidden" name="propertyId" value="${propertyDo.propertyId}">
				<input type="hidden" name="loanId" value="${propertyDo.loanId }">
				<input type="hidden" name="loanPersonId" value="${propertyDo.loanPersonId }">
				<h3 class="tip tip2">房产信息填写</h3>
					<div class=" p1 db_f">
						<label class="lab lab2" for="houseAddress">房产地址</label>
						<input class="bf1 txt br1 must" type="text" id="houseAddress" name="houseAddress" value="${propertyDo.houseAddress }"/>
					</div>
					<div class=" p1 db_f">
						<label class="lab lab2" for="housePrice">购买价格</label>
						<div class="bf1 db_f">
							<input class="bf1 txt br_l1 must" type="number" id="housePrice" name="housePrice" value="${propertyDo.housePriceString }"/>
							<span class="br_r1 after_unit">万</span>
						</div>
					</div>
					<div class=" p1 db_f">
						<label class="lab lab2" for="workName">购买日期</label>
						<div class="bf1 db_f">
							<label class="bf1 db_f">
								<select class="sel year bf1 br1" name="hyear" id="hyear">
									<option value="${propertyDo.hyear }">${propertyDo.hyear }</option>
								</select>
								<em>年</em>
							</label>
							<label class="bf1 db_f">
								<select class="sel month bf1 br1" name="hmonth" id="hmonth">
									<option value="${propertyDo.hmonth }">${propertyDo.hmonth }</option>
								</select>
								<em>月</em>
							</label>
							<label class="bf1 db_f">
								<select class="sel day bf1 br1" name="hday" id="hday">
									<option value="${propertyDo.hday }">${propertyDo.hday }</option>
								</select>
								<em>日</em>
							</label>
						</div>
					</div>
					<div class=" p1 db_f">
						<label class="lab lab2" for="workName">建筑面积</label>
						<div class="bf1 db_f">
							<input class="bf1 txt br_l1 must" type="number" id="coveredArea" name="coveredArea" value="${propertyDo.coveredAreaString }"/>
							<span class="br_r1 after_unit">㎡</span>
						</div>
					</div>
					<div class="p1 db_f">
						<label for="name" class="lab">共有权人</label>
						<input type="text" class="txt bf1 br1" name="advisedPeople" value="${propertyDo.advisedPeople }"/>
					</div>
					<div class="p1 db_f">
						<label for="name" class="lab lab2">是否抵押他人</label>
						<div class="bf1 db_f" style="width:100px">
							<label class="db_f" >
								<input class="rbtn" type="radio" value="1"  checked  name="houseDy" ${propertyDo.houseDy == 1 ? "checked" : "" }/>
								<em class="bf1">是</em>
							</label>
							<label class="db_f" >
								<input class="rbtn" type="radio" value="0" name="houseDy" ${propertyDo.houseDy == 0 ? "checked" : ""}/>
								<em class="bf1">否</em>
							</label>
						</div>
					</div>
					<div class="p1 db_f">
						<label class="lab lab2" for="houstWay">购买方式</label>
						<select class="sel bf1 br1 fangdai" name="houstWay">
							<option value="0" ${propertyDo.houstWay == 0 ? "selected" : ""}>一次性</option>
							<option value="1" ${propertyDo.houstWay == 1 ? "selected" : ""}>按揭</option>
						</select>
					</div>
					<article class="dn ">
						<div class="p1 db_f">
							<label for="houseReDur" class="lab lab2">已按揭时长</label>
							<input type="number"  class="bf1 txt br_l1 " name="houseReDur" value="${propertyDo.houseReDur }">
							<span class="br_r1 after_unit">月</span>
						</div>
						<div class=" p1 db_f">
							<label class="lab lab2" for="houseMthOwing">每月还贷</label>
							<div class="bf1 db_f">
								<input class="bf1 txt br_l1 " type="number" id="houseMthOwing" name="houseMthOwing" value="${propertyDo.houseMthOwingString }"/>
								<span class="br_r1 after_unit">元</span>
							</div>
						</div>
						<div class="p1 db_f">
							<label for="houseReMtg" class="lab lab2">剩余房贷</label>
							<div class="bf1 db_f">
								<input class="bf1 txt br_l1" type="number" id="houseReMtg" name="houseReMtg" value="${propertyDo.houseReMtgString }"/>
								<span class="br_r1 after_unit">元</span>
							</div>
						</div>
					</article>
					<h3 class="tip">车产信息填写</h3>
					<div class="p1 db_f">
						<label for="carNo" class="lab ">车牌号码</label>
						<input type="text" class="txt br1 bf1 " name="carNo" value="${propertyDo.carNo }">
					</div>
					<div class="p1 db_f">
						<label for="carBrand" class="lab ">品牌型号</label>
						<input type="text" class="txt br1 bf1 " name="carBrand" value="${propertyDo.carBrand }">
					</div>
					<div class=" p1 db_f">
						<label class="lab " for="workName">购买价格</label>
						<div class="bf1 db_f">
							<input class="bf1 txt br_l1 " type="number" id="carPrice" name="carPrice" value="${propertyDo.carPriceString }"/>
							<span class="br_r1 after_unit">万</span>
						</div>
					</div>
					<div class=" p1 db_f">
						<label class="lab" for="workName">购买日期</label>
						<div class="bf1 db_f">
							<label class="bf1 db_f">
								<select class="sel year bf1 br1" name="cyear" id="cyear">
									<option value="${propertyDo.cyear }">${propertyDo.cyear }</option>
								</select>
								<em>年</em>
							</label>
							<label class="bf1 db_f">
								<select class="sel month bf1 br1" name="cmonth" id="cmonth">
									<option value="${propertyDo.cmonth }">${propertyDo.cmonth }</option>
								</select>
								<em>月</em>
							</label>
							<label class="bf1 db_f">
								<select class="sel day bf1 br1" name="cday" id="cday">
									<option value="${propertyDo.cday }">${propertyDo.cday }</option>
								</select>
								<em>日</em>
							</label>
						</div>
					</div>
					<div class="p1 db_f">
						<label for="name" class="lab">是否抵押他人</label>
						<div class="bf1 db_f" style="width:100px">
							<label class="db_f" >
								<input class="rbtn" type="radio" value="1" name="carDy"  checked  ${propertyDo.carDy == 1 ? "checked" : ""}/>
								<em class="bf1">是</em>
							</label>
							<label class="db_f" >
								<input class="rbtn" type="radio" value="0" name="carDy" ${propertyDo.carDy == 0 ? "checked" : ""}/>
								<em class="bf1">否</em>
							</label>
						</div>
					</div>
					<div class="p1 db_f">
						<label class="lab" for="">购买方式</label>
						<select class="sel bf1 br1 chedai" name="carWay">
							<option value="0" ${propertyDo.carWay == 0 ? "selected" : ""}>一次性</option>
							<option value="1" ${propertyDo.carWay == 1 ? "selected" : ""}>按揭</option>
						</select>
					</div>
					<article class="dn ">
						<div class="p1 db_f">
							<label for="name" class="lab lab2">已按揭时长</label>
							<input type="number" class="bf1 txt br_l1" name="carReDur" value="${propertyDo.carReDur }">
							<span class="br_r1 after_unit">月</span>
						</div>
						<div class=" p1 db_f">
							<label class="lab lab2" for="workName">每月还贷</label>
							<div class="bf1 db_f">
								<input class="bf1 txt br_l1" type="number" id="carMthOwing" name="carMthOwing" value="${propertyDo.carMthOwingString }"/>
								<span class="br_r1 after_unit">元</span>
							</div>
						</div>
						<div class="p1 db_f">
							<label for="name" class="lab lab2">剩余车贷</label>
							<div class="bf1 db_f">
								<input class="bf1 txt br_l1" type="number" id="carReMtg" name="carReMtg" value="${propertyDo.carReMtgString }"/>
								<span class="br_r1 after_unit">元</span>
							</div>
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
			$(".fangdai,.chedai").change(function() {
				var num = $(this).val();
				if(num > 0) {
					$(this).parent().next().removeClass("dn").find(".txt").addClass("must");
					return false;
				}
			   $(this).parent().next().addClass("dn").find(".txt").removeClass("must");
			});
			var hyear = '${propertyDo.hyear }'; 
			var hmonth = '${propertyDo.hmonth }';
			var hday = '${propertyDo.hday }';
			var cyear = '${propertyDo.cyear }';
			var cmonth = '${propertyDo.cmonth }';
			var cday = '${propertyDo.cday }';

			setDateSelect(hyear, hmonth, hday, function(_strY, _strM, _strD){
				$("#hyear").html(_strY);
				$("#hmonth").html(_strM);
				$("#hday").html(_strD);
				
			});
			
			setDateSelect(cyear, cmonth, cday, function(_strY, _strM, _strD){
				$("#cyear").html(_strY);
				$("#cmonth").html(_strM);
				$("#cday").html(_strD);
				
			});
			
			/* $(".chedai").change(function() {
				var num = $(this).val();
				if(num > 0) {
					$(this).parent().next().removeClass("dn");//.find(".txt").addClass("must");
					return false;
				}
					$(this).parent().next().addClass("dn");//.find(".txt").removeClass("must");
			}); */
			
			(function() {
				var num = $(".fangdai").val();
				if(num > 0) {
					$(".fangdai").parent().next().removeClass("dn").find(".txt").addClass("must");
				}
				var num2 = $(".chedai").val();
				if(num2 > 0) {
					$(".chedai").parent().next().removeClass("dn").find(".txt").addClass("must");
				}
			})();
		})
		
		</script>
	</body>
</html>