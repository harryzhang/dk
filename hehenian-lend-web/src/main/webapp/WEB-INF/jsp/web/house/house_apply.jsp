<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
	<%@include file="../include/taglib.jsp"%>
	<link href="<c:url value='web_res/css/dk/css/zzsc.css'/>" rel="stylesheet" />
	<script type="text/javascript" src="<c:url value='web_res/css/dk/js/jquery.js'/>"></script>
	<script type="text/javascript" src="<c:url value='web_res/css/dk/js/jquery.caroufredsel-6.0.4-packed.js'/>"></script>
	<script type="text/javascript" src="<c:url value='web_res/css/dk/js/focus.js'/>"></script>
	<script type="text/javascript" src="<c:url value='web_res/css/dk/js/jquery-1.4.4.min.js'/>"></script>
	<script type="text/javascript" src="<c:url value='web_res/css/dk/js/jquery.reveal.js'/>"></script>
	<style>
			*{
				margin: 0;
				padding: 0;
				list-style: none;
				text-decoration: none;
				font-style: normal;
				border: 0;
				-webkit-touch-callout:none;
				-webkit-text-size-adjust: none;
				-webkit-user-select: none;
				user-select: none;
				-webkit-box-sizing: border-box;
				box-sizing: border-box;
				font-weight: normal;
				font-family: "微软雅黑";
			}
			a{
				color: #696969;
			}
			body {
				font-family: "微软雅黑";
				font-size: 14px;
				color: #898989;
				background: #e8e8e8;
				margin: auto;
			}
			input{
			width:13px;
			height:auto;
			}
			.box img{
				width:100%;
			}
			.page{
				background: #fff;
				width: 100%;
			}
			.page>div{
				width: 1100px;
				height: 480px;
				margin: auto;
			}
			.page2{
				background: url(<c:url value='/web_res/img/2.png'/>) right center no-repeat;
				color: #898989;
 				padding: 180px 0;
			}
			.page3{
				background: url(<c:url value='/web_res/img/3.jpg'/>) left bottom no-repeat;
				color: #fff;
  				padding: 180px 0 150px 715px;
			}
			.page4{
				background: url(<c:url value='/web_res/img/4.png'/>) right bottom no-repeat;
				color: #fff;
                padding: 180px 0;
			}
			.page h2{
				font-size: 30px;
				padding: 10px 0;
			}
			.page p{
				font-size: 18px;
				padding: 10px 0 0;
			}
			.page5{
				height: 420px !important;
				padding: 140px 0;
			}
			.page5 h2{
				font-size: 24px;
				padding: 5px 0;
			}
			.page5 p{
				font-size: 14px;
				padding: 5px 0 0;
			}
			.form{
				 width: 330px;
				 height: auto;
				 background: url(<c:url value='/web_res/img/sfb_form_bg.png'/>);
				 position: absolute;
				 z-index: 50;
				 top:55px;
				 right: 20%;
			 }
			 .tip{
				 padding: 15px 20px;
				 color:#262626;
				 font-size:22px;
				 border-bottom: 1px solid #134ea0;
			 }
			 .tip em,#message em{
				  font-size: 12px;
				  padding-left: 10px;
				  color: #FC0B0B;
			  }
			 .form form{
				 padding: 20px;
				 color: #323232;
			 }
			 .form .lab{
				  width: 70px;
				  height: 30px;
				  display: inline-block;
				  line-height: 30px;
			 }
			 .form-item{
				padding: 0px 0 20px 0;
			}
			.txt{
				  height: 30px;
				  line-height: 24px;
				  padding: 2px;
				  border: 1px solid #e8e8e8;
				  width: 214px;
  				  vertical-align: top;
  				    font-size: 13px;
				}
			.readed{
			  height: 16px;
			  line-height: 16px;
			  display: inline-block;
			  color: #626262;
			  width: 60px;
			  font-size: 12px;
			}
			.readed input {
				  vertical-align: bottom;
				  margin-right: 10px;
			}
			.form-item a{
				  color: #97AAF5;
				  font-size: 12px;
			}
			.btn{
				  height: 40px;
				  line-height: 20px;
				  width: 100%;
				  padding: 10px 10px;
				  background: #fdee33;
				  font-size: 18px;
				  color: #5b5b5b;
				  display: block;
				  text-align: center;
				  border-radius: 5px;
				  cursor: pointer;
			}
			.btn:hover,.btn:active{
				background: #fdd933;
			}
			.err{
		  position: absolute;
		  color: #FF8181;
		  margin: -18px 0 0 75px;
		  font-size: 12px;
				}
		.dialog_bg{
			position: fixed;
			width: 100%;
			height: 100%;
			top: 0;
			left:0;
			z-index: 1000000;
			background: url(<c:url value='/web_res/img/dialog_bg.png'/>);
			display: none;
		}
		.dialog{
			width: 580px;
			background: #fff;
			position: absolute;
			left: 50%;
			top: 50%;
			margin: -200px 0 0 -300px;
			z-index: 999;
			padding:10px;
		}
		.dialog h3{
			height: 50px;
			line-height: 40px;
			text-align: center;
			font-size: 24px;
			color: #2f8dff;
		}
		.dialog .content{
			width: 500px;
			margin: auto;
		}
		.dialog_btnBox{
			padding: 20px 0 0 0;
			text-align: center;
		}
		.dialog .btn{
			width: 200px;
			display: inline-block;
			color: #fff;
			font-size: 18px;
			margin: 0 20px;
			background: #c7c7c7;
		}
		.yuan{
			width: 70px;
			height: 30px;
			display: block;
			line-height: 30px;
			text-align: center;
			position: absolute;
			margin: -30px 0 0 230px;
		}
		.table{
			margin: auto;
			background: #b5b5b5;
			max-height: 220px;
			border-collapse: collapse;
		    border-spacing: 0;
			border-right: 1px solid #b5b5b5;
		}
		.table td{
			text-align: center;
			height: 40px;
			line-height: 40px;
			background: #e7e3da;
			border-bottom: 1px solid #b5b5b5;
			border-left: 1px solid #b5b5b5;
		}
		.table.table2{
			width: 480px;
		}
		.table.table2 thead td{
			height: 0px;
		}
		.table thead td{
			background: #e7e7e7;
			color: #484848;
			height: 50px;
			line-height: 50px;
		}
		.table-box{
			margin: auto;
			max-height:210px;
			overflow-y: auto;
			overflow-x: hidden; 
			width: 480px; 
		}
		.total{
			display: block;
			width: 460px;
			padding: 5px 10px;
			text-align: right;
			color: #3782ff;
			font-size: 16px;
		}
		.total em{
			font-style: normal;
			font-size: 20px;
		}
				.btn.hui{
					background: #ccc;
				}
		</style>
	<title>首付宝</title>
	<meta charset="utf-8" />
	<meta name="viewport" content="width=device-width initial-scale=1.0 maximum-scale=1.0 user-scalable=0" />
</head>
<body>

	<!-- 引用头部公共部分 -->
	<jsp:include page="../include/top.jsp"></jsp:include>
	<div class="box">
			<div>
				<img src="<c:url value='/web_res/img/1.png'/>" style="  margin-bottom: -3px;" alt="">
				<div class="form">
					<h1 class="tip">首付宝</h1>
					<form action="#">
						<div class="form-item">
							<label for="" class="lab">真实姓名:</label>
							<input type="text" class="txt"  name="user.realName" id="realName" maxlength="6" placeholder="真实姓名"/>
						</div>
						<div class="form-item">
							<label for="" class="lab">身份证号:</label>
							<input type="text" name="user.idNo"   id="idNo" class="txt" maxlength="18" placeholder="身份证号"/>
						</div>
						<div class="form-item">
							<label for="" class="lab">手机号码:</label>
							<input type="text"  name="user.mobile" id="mobile" class="txt" maxlength="11" placeholder="手机号码"/>
						</div>
						<div class="form-item">
							<label for="" class="lab">每月收入:</label>
							<input type="text" name="user.income" id="income" class="txt" placeholder="每月收入"/>
						</div>
						<div class="form-item">
							<label for="" class="lab">贷款金额:</label>
							<input type="text" name="user.applyAmount" id="applyAmount" class="txt" placeholder="贷款金额"/>
						</div>
						<div class="form-item" style="padding-bottom: 6px;">
							<label for="" class="lab">首付类型:</label>
							<p style="display:inline-block;">
								<label class=""><input type="radio"  id="0" checked name="loanType">首付宝A(长期)</label>
							</p>
						</div>
						<div>	
							<div class="form-item date">
							<label for="" class="lab">还款时长:</label>
							<select class="txt" >
								<option value="3">三个月</option>
								<option value="6">六个月</option>
								<option value="12">一年</option>
								<option value="18">一年半</option>
								<option value="24">两年</option>
								<option value="36">三年</option>
							</select>
							</div>
						</div>
						<div class="form-item">
							<label for="" class="lab">楼盘名字:</label>
							<input type="text" id="cname" class="txt" placeholder="楼盘名字"/>
						</div>
						<div class="form-item">
							<label for="" class="lab">家人1电话:</label>
							<input type="text" id="tel1"  class="txt" placeholder="家人1电话"/>
						</div>
						<div class="form-item">
							<label for="" class="lab">家人2电话:</label>
							<input type="text"  id="tel2"  class="txt" placeholder="家人2电话"/>
						</div>
						<div class="form-item">
							<label for="" class="lab">朋友1电话:</label>
							<input type="text"  id="tel3"  class="txt" placeholder="朋友1电话"/>
						</div>
						<div class="form-item">
							<label for="" class="lab">朋友2电话:</label>
							<input type="text"  id="tel4"  class="txt" placeholder="朋友2电话"/>
						</div>
						<div class="form-item">
							<label class="readed"><input id='chkAgreement1' type="checkbox" />我同意</label>
							<a href="<c:url value='/web_res/word/concat.html'/>" target="_blank">《合和年在线借款协议》</a>
						</div>
						<div class="form-item">
							<label class="readed"><input id='chkAgreement2' type="checkbox" />我同意</label>
							<a href="<c:url value='/web_res/word/consult.html'/>" target="_blank">《借款咨询服务协议》</a>
						</div>
							<a class="btn hui" id="btnSubmit" disabled="disabled">提交申请</a>
					</form>
				</div>
			</div>
			<div class="page">
				<div class="page2">
						<h2>门槛低</h2>
						<p>看似遥远的理想其实可以很快成为现实。首付宝 ，最低两万购房</p>
				</div>
			</div>
			<div class="page" style="background:#ff6364;">
				<div class="page3">
						<h2>利率低</h2>
						<p>利率最低与公积金利率相同,轻松购房无压力</p>
				</div>
			</div>
			<div class="page " style="background:#59b5fe;">
				<div class="page4">
						<h2>速度快</h2>
						<p>流程简单快速申请,没钱照样买好房</p>
				</div>
			</div>
			<div class="page">
				<div class="page5">
						<h2>特别提醒</h2>
						<p>1.您的贷款申请最终能否得到核准，以合和年的最终审核结果为准。</p>
						<p>2.根据中国银行业监督管理委员会有关规定，贷款不可用于购买不动产、认购和购买股票或任何其他股本权益性投资。有任何疑问，敬请致电合和年服务热线4008-303-737。</p>
						<p>3.填写者同意并确认：其填写上述个人信息即表明其授权合和年致电填写者以核实所填信息的真实性；且其填写的个人信息将仅用于向合和年申请贷款之目的。</p>
				</div>
			</div>
		</div>
		<div class="dialog_bg">
		<div class="dialog">
			<h3><span id="message">还款计划表<em></em></span></h3>
			<div class="content">
				<table class="table">
					<thead>
						<tr>
							<td width="60px">期数</td>
							<td width="140px">应还本金</td>
							<td width="140px">应还利息</td>
							<td width="140px">应还总额</td>
						</tr>
					</thead>
				</table>
				<div class="table-box">
					<table class="table table2">
					<thead>
						<tr>
							<td width="60px"></td>
							<td width="140px"></td>
							<td width="140px"></td>
							<td width="140px"></td>
						</tr>
					</thead>
					<tbody>
						
					</tbody>
				</table>
				</div>
				<p class="total"> 总共应还：<em>100</em>元</p>
			</div>
			<p class="dialog_btnBox">
				<a class="btn close_dialog" href="###">返回</a>
				<a class="btn" id="subform" href="###" style="background:#3782ff;">确定</a>
			</p>
		</div>
	</div>
	<!-- 引用底部公共部分-->
	<jsp:include page="../include/footer.jsp"></jsp:include>
	</body>
	<script type="text/javascript">
	$(function () {
		/* var session ="${user}";
 		if(!session.length) {
			$(".tip").append('<em>请登录后，再填写以下内容</em>');	
			$(".txt").attr("disabled","disabled");
			return false;
		}  */
		$("input[type='radio']").click(function(){
			$(".date").eq(+$(this).attr("id")).show().siblings().hide();
		})
		
		/* var loanPeriod = 0;
		
		$("#loan_Period").change(function() {
			loanPeriod = $("#loan_Period").val()*1;
		}) */ 

		$(".close_dialog").click(function() {
			$(".dialog_bg").hide();
		});

		var alertText = function (obj,txt) {
			obj.addClass("disabled").parent().after('<span class="err">'+ txt +'</span>');
			$("#btnSubmit").attr("disabled",true).addClass("hui");
		};

		var showBtn = function (obj){
			obj.removeClass("disabled");
			if(!$(".disabled").length && $("input[type=checkbox]:checked").length == 2) {
				$("#btnSubmit").removeAttr("disabled").removeClass("hui");
			}
		};

		$('input[type=text],select').focus(function(){
			var err = $(this).parent().next('.err');
			if(err){
				err.remove();
			}
		});

		$("#realName").blur(function () {
			var realName = $(this).val().trim();
			if (realName == null || realName.length < 1) {
				alertText($(this), "真实姓名不能为空!");
				return false;
			}
			var re = /^[\u4E00-\u9FA5]{2,5}$/ig;
			if (!re.test(realName)) {
				alertText($(this), "请输入2-5字的中文!");
				return false;
			}
			showBtn($(this));
		});

		$("#cname").blur(function () {
			var realName = $(this).val().trim();
			if (realName == null || realName.length < 1) {
				alertText($(this), "楼盘名字不能为空!");
				return false;
			}
			var re = /^[\u4E00-\u9FA5]{1,20}$/ig;
			if (!re.test(realName)) {
				alertText($(this), "请输入中文!");
				return false;
			}
			showBtn($(this));
		});

		$("#idNo").blur(function () {
			var idNo = $(this).val().trim();
			if (idNo == null || idNo == "") {
				alertText($(this), "身份证号不能为空!");
				return false;
			}
			if (!isIdCardNo(idNo)) {
				alertText($(this), "身份证号格式有误!");
				return false;
			}
			showBtn($(this));
		});

		$("#mobile,#tel1,#tel2,#tel3,#tel4").blur(function () {
			var mobile = $(this).val().trim();
			var re = /^1[3,4,5,7,8]{1}\d{9}$/i;
			if (mobile == null || mobile == "") {
				alertText($(this), "手机号码不能为空!");
				return false;
			}
			else if (!re.test(mobile)) {
				alertText($(this), "手机号码格式有误!");
				return false;
			}
			showBtn($(this));
		});

		$("#income").blur(function () {
			var income = $(this).val().trim();
			var reg =  /^[1-9]\d*$/g ;
			if(income == null || income == "") {
				alertText($(this), '月收入不能为空!');
				return false;
			}else if(income.indexOf('.')>-1) {
				alertText($(this), '月收入不能输入小数!');
				return false;
			} else if (!reg.test(income)) {
                alertText($(this), '月收入输入有误！');
				return false;
			} 
			showBtn($(this));
			reg=0;
		});
		
		$("#chkAgreement1").change(function() {
			var isChecked = $(this).attr("checked");
			if(isChecked) {
				showBtn($(this));
			} else {
				$(this).addClass("disabled");
				$("#btnSubmit").attr("disabled",true).addClass("hui");
			}
		});
		$("#chkAgreement2").change(function() {
			var isChecked = $(this).attr("checked");
			if(isChecked) {
				showBtn($(this));
			} else {
				$(this).addClass("disabled");
				$("#btnSubmit").attr("disabled",true).addClass("hui");
			}
		});
		

		$("#applyAmount").blur(function () {
			var applyAmount = $(this).val().trim();
			var reg = /^[1-9]\d*$/g;
			if (applyAmount == null || applyAmount == "") {
				alertText($(this), "货款金额不能为空!");
				return false;
			}else if(applyAmount.indexOf('.')>-1) {
				alertText($(this), '货款金额不能输入小数!');
				return false;
			}else if (!reg.test(applyAmount)) {
				alertText($(this),"贷款金额输入有误!");
				return false;
			}else if (!(applyAmount %100 == 0)) {
				alertText($(this),"请输入100元的整数倍！");
				return false;
			} else if(applyAmount-10000<0){
				alertText($(this),'最低贷款金额须大于1万元!');
				return false;
			}else if(applyAmount-1000000>0){
				alertText($(this),'最高贷款金额不能超过100万元!');
				return false;
			}	
			showBtn($(this));
			reg=0;
		});
/* 		
		$("#loan_Period").change(function(){ 
			var loanPeriod1 = $(this).val().trim();
			$(this).parent().next('.err').remove();
			if(loanPeriod1==0){
				alertText($(this),'请选择首付贷产品类型');
				return false;
			} 
			$(this).parent().next('.err').remove();
			showBtn($(this));
		}); */

		$("#btnSubmit").bind("click", function() {
			if($(this).attr("disabled")) {
				return false;
			}
			var applyAmount = $("input[name='user.applyAmount']").val();
			if (applyAmount == "" || applyAmount <= 0) {
				return;
			}
			var loanProductType =  +$("input[type='radio']:checked").attr("id");
			var loanPeriod = $(".date").eq(loanProductType).find(".txt").val();

			$.get("calRepayDetailHouse.do",{applyAmount : applyAmount,loanPeriod : loanPeriod,  loanProductType : loanProductType},function(jsonArray) {
				var data = jsonArray;
				var str='';
				var len = data.length;
				for(var i=0;i<len;i++) {
					var item = data[i];
				str +=   ' <tr>'
					    +'	<td>'+ item.times +'/'+ len +'</td>'
						+'	<td>'+ item.principal +'</td>'
						+'	<td>'+ item.interest +'</td>'
						+'	<td>'+ item.repayAmount +'</td>'
						+'</tr>';
				}
				$(".total em").text(data[len-1].totalAmount);
				$(".table2 tbody").html(str);	
				$(".dialog_bg").show();
				
			});
		});
		
		$("#subform").bind("click", function() {
			var realName = $("input[name='user.realName']").val();
			var idNo = $("input[name='user.idNo']").val();
			var mobile = $("input[name='user.mobile']").val();
			var income = $("input[name='user.income']").val();
			var applyAmount = $("input[name='user.applyAmount']").val();
			var loanProductType =  +$("input[type='radio']:checked").attr("id");
			var loanPeriod = $(".date").eq(loanProductType).find(".txt").val();
			var cname = $("#cname").val();
			var tel1  = $("#tel1").val();
			var tel2  = $("#tel2").val();
			var tel3  = $("#tel3").val();
			var tel4  = $("#tel4").val();
			
			var that =$(this);
			that.attr('disabled','disabled');
			$.ajax({
					url:  'addLoanDetailHouse.do',
					type:'POST',
					data :{
						realName:encodeURI(realName),idNo:idNo,mobile:mobile, jobIncome:income,applyAmount:applyAmount,loanPeriod:loanPeriod,loanProductType : loanProductType,
						cname:cname,tel1:tel1,tel2:tel2,tel3:tel3,tel4:tel4
					},
			       success: function(result) {
			    	   	if(result.isSuccess){
			    	   		window.location.href="<c:url value='toSuccessHouse.do' />"
			    	   	}else{
			    	   		$("#message em").html(result.message);	
			    	   	}
			    	}
				}); 
		});
	})
	</script>
</html>
