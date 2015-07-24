<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<jsp:include page="/include/head.jsp"></jsp:include>
<script>
function reggg(){
	if(${session.user!=null}){
		alert("你已是登录用户");
	}else{
		window.location.href="/account/reg.do";
	}
}
</script>
</head>
<body>
<!-- 引用头部公共部分 -->
<div style="position: absolute; height:54px; line-height:54px; background:#f3f3f3; width:100%; margin-top:151px !important; margin-top:177px;border-bottom:1px solid #e5e5e5;border-top:1px solid #e5e5e5;"></div>
<jsp:include page="/include/top.jsp"></jsp:include>
<div class="center">
  <div class="wytz_center">
    <div class="mjd_tzlc_all">
      <ul>
        <li id="tab_1" class="tzlc">已发布的借款</li>
        <li id="tab_2">即将发布的借款</li>
        <%--<li id="tab_3">正在转让的债权</li>--%>
      </ul>
    </div>
  </div>
  =============================================
</div>
<div class="cle"></div>
<!-- 引用底部公共部分 -->
<jsp:include page="/include/footer.jsp"></jsp:include>
<script type="text/javascript" src="script/jquery.shove-1.0.js"></script> 
<script type="text/javascript" src="script/jbox/jquery.jBox-2.3.min.js"></script> 
<script type="text/javascript" src="script/jbox/jquery.jBox-zh-CN.js"></script> 
<script type="text/javascript">
		$(document).ready(function() {
			var ck_type = "${paramMap.type}";
			var no = ck_type.split(',');
			if (no != '') {
				for ( var i = 1; i <= no.length; i++) {
					$('#ck_mode_' + i).attr('checked', 'checked');
				}
			}
			$("#province").change(function() {
				var provinceId = $("#province").val();
				registedPlaceCity(provinceId, 2);
			});
		});

		function registedPlaceCity(parentId, regionType) {
			var _array = [];
			_array.push("<option value='-1'>--请选择--</option>");
			var param = {};
			param["regionType"] = regionType;
			param["parentId"] = parentId;
			$.post("ajaxqueryRegions.do", param, function(data) {
				for ( var i = 0; i < data.length; i++) {
					_array.push("<option value='"+data[i].regionId+"'>" + data[i].regionName + "</option>");
				}
				$("#registedPlaceCity").html(_array.join(""));
			});
		}
		function checkTou(id, type) {
			var param = {};
			param["id"] = id;
			$.shovePost('financeInvestInit.do', param, function(data) {
				var callBack = data.msg;
				if (callBack != undefined) {
					alert(callBack);
				} else {
					if (type == 2) {
						var url = "subscribeinit.do?borrowid=" + id;
						$.jBox("iframe:" + url, {
							title : "我要购买",
							width : 450,
							height : 450,
							buttons : {}
						});
					} else {
						window.location.href = 'financeInvestInit.do?id=' + id;
					}
				}
			});
		}
		function closeMthod() {
			window.jBox.close();
			window.location.reload();
		}

		function clearComponentVal() {
			param = {};
			$('#titles').val('');
			$('#paymentMode').get(0).selectedIndex = 0;
			$('#purpose').get(0).selectedIndex = 0;
			$('#deadline').get(0).selectedIndex = 0;
			$('#reward').get(0).selectedIndex = 0;
			$('#arStart').get(0).selectedIndex = 0;
		}

		function rateNumJudge() {//验证利息计算输入数字是否正确
			if ($("#borrowSum").val() == "") {
				$("#show_error").html("&nbsp;&nbsp;投标金额不能为空");
				$("#showIncome").html("");
			} else if (!/^(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-9]*))$/.test($("#borrowSum").val())) {
				$("#show_error").html("&nbsp;&nbsp;请输入正确的投标金额进行计算");
				$("#showIncome").html("");
			} else if ($("#yearRate").val() == "") {
				$("#show_error").html("&nbsp;&nbsp;年利率不能为空");
				$("#showIncome").html("");
			} else if (!/^(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-9]*))$/.test($("#yearRate").val())) {
				$("#show_error").html("&nbsp;&nbsp;请输入正确的年利率");
				$("#showIncome").html("");
			} else if ($("#borrowTime").val() == "") {
				$("#show_error").html("&nbsp;&nbsp;计息天数不能为空");
				$("#showIncome").html("");
			} else if (!/^[0-9]*[1-9][0-9]*$/.test($("#borrowTime").val())) {
				$("#show_error").html("&nbsp;&nbsp;请输入正确计息天数");
				$("#showIncome").html("");
			} else {
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

		function rateCalculate() {//利息计算
			var str = rateNumJudge();
			var borrowSum = $("#borrowSum").val();
			var yearRate = $("#yearRate").val();
			var borrowTime = $("#borrowTime").val();
			if ($("#show_error").text() != "") {
				return;
			}
			
			var income = borrowSum*yearRate*borrowTime/36500;
			$("#showIncome").html("预计收益："+income.toFixed(2)+"元<br>注：实现预计收益需开启“自动投标”或将每月返还的本金即时投标。");
		
		}
	</script> 
<script type="text/javascript">
		$(function() {
//			hhn(1);
            hhnNew("topIndex-finance");
			$("span#tit").each(function() {
				if ($(this).text().length > 6) {
					$(this).text($(this).text().substring(0, 8) + "..");
				}
			});

			var m = '${paramMap.m}';
			if (m == '') {
				m = 1;
			}
			$("#tab_" + m).addClass("on");
			$("#tab_1").click(function() {
				window.location.href = "finance.do"
			});
			$("#tab_2").click(function() {
				window.location.href = "soonPublishList.do"
			});
			$("#tab_3").click(function() {
				window.location.href = "queryPublishDebt.do"
			});

			$("#searchLink").click(function() {
				window.location.href = "finance.do?title=" + $("#titles").val();
			});
			$("#search").click(function() {
				$("#searchForm").submit();
			});
			$("#arEnd").change(function() {
				var arStart = $('#arStart').val();
				arStart = parseInt(arStart);
				var arEnd = $(this).val();
				arEnd = parseInt(arEnd);
				if (arEnd < arStart) {
					alert('金额范围不能小于开始!');
				}
			});
			$("#jumpPage").click(function() {
				var curPage = $("#curPageText").val();

				if (isNaN(curPage)) {
					alert("输入格式不正确!");
					return;
				}
				$("#pageNum").val(curPage);
				var chk_value = [];
				$('input[name="ck_mode"]:checked').each(function() {
					chk_value.push($(this).val());
				});
				if (chk_value.length != 0) {
					param['paramMap.m'] = '1';
					$("#type").val(chk_value);
				} else {
					$("#type").val("");
				}
				//$("#searchForm").submit();
				var purpose=$(".purpose.ok").data("value");
                var deadline=$(".deadline.ok").data("value");
                var start=$(".borrowAmount.ok").data("start");
                var end=$(".borrowAmount.ok").data("end");
                window.location.href="finance.do?purpose="+purpose+"&deadline="+deadline+"&arStart="+start+"&arEnd="+end+"&curPage="+curPage;
			});

            $(".search li").click(function(){
                var $this=$(this);
//                if($this.hasClass("purpose")){
                    $this.parent().find(".ok").removeClass("ok");
                    $this.addClass("ok");
//                }
                var purpose=$(".purpose.ok").data("value");
                var deadline=$(".deadline.ok").data("value");
                var start=$(".borrowAmount.ok").data("start");
                var end=$(".borrowAmount.ok").data("end");
                window.location.href="finance.do?purpose="+purpose+"&deadline="+deadline+"&arStart="+start+"&arEnd="+end;
            });
		});
	</script>
    
</body>
</html>
