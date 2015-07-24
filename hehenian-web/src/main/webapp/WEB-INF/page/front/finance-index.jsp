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
  
  <!--左侧-->
  <div class="mjd_tzlc_left" style="display:block; margin-top:30px;border-right:1px solid #e6e6e6; "> 
    <!--筛选-->
    <div class="search_all">
      <div class="search_lx">
        <ul>
          <li>借款类型</li>
          <li>借款时间</li>
          <li>借款金额</li>
        </ul>
      </div>
      <div class="search">
        <ul  style=" width:100%">
          <li class="purpose ${param.purpose ==null||param.purpose==''?'ok':''}" data-value=""><a href="#">全部</a></li>
          <li class="purpose ${param.purpose ==1?'ok':''}" data-value="1"><a href="#">薪金贷</a></li>
          <li class="purpose ${param.purpose ==2?'ok':''}" data-value="2"><a href="#">生意贷</a></li>
          <li class="purpose ${param.purpose ==3?'ok':''}" data-value="3"><a href="#" >业主贷</a></li>
        </ul>
        <ul  style=" width:100%">
          <li class="${param.deadline ==null||param.deadline==''?'ok':''} deadline" data-value=""><a href="#">全部</a></li>
          <li class="deadline ${param.deadline ==6?'ok':''}" data-value="6"><a href="#">6个月</a></li>
          <li class="deadline ${param.deadline ==12?'ok':''}" data-value="12"><a href="#">12个月</a></li>
          <li class="deadline ${param.deadline ==18?'ok':''}" data-value="18"><a href="#">18个月</a></li>
          <li class="deadline ${param.deadline ==24?'ok':''}" data-value="24"><a href="#">24个月</a></li>
          <li class="deadline ${param.deadline ==36?'ok':''}" data-value="36"><a href="#">36个月</a></li>
        </ul>
        <ul  style=" width:100%">
          <li class="borrowAmount ${param.arStart ==null||param.arStart==''?'ok':''}" data-start="" data-end=""><a href="#">全部</a></li>
          <li class="borrowAmount ${param.arStart ==0?'ok':''}" data-start="0" data-end="20000"><a href="#">2万以下</a></li>
          <li class="borrowAmount ${param.arStart ==20000?'ok':''}" data-start="20000" data-end="50000"><a href="#">2-5万</a></li>
          <li class="borrowAmount ${param.arStart ==50000?'ok':''}" data-start="50000" data-end="100000"><a href="#">5-10万</a></li>
          <li class="borrowAmount ${param.arStart ==100000?'ok':''}" data-start="100000" data-end="200000"><a href="#">10-20万</a></li>
          <li class="borrowAmount ${param.arStart ==200000?'ok':''}" data-start="200000" data-end=""><a href="#">20万以上</a></li>
        </ul>
      </div>
    </div>
    
    <!--内容-->
    <s:if test="pageBean.page!=null || pageBean.page.size>0">
    <s:iterator value="pageBean.page" var="finance">
    <div style=" width:905px; background:url(images/bbj.jpg) left center no-repeat; height:170px; overflow:hidden; margin-bottom:20px;">
      <div style=" width:78px; float:left; height:170px;"><img src="images/hot.png"/></div>
      <div class="shouyebiao">
        <ul>
          <li style=" font-size:16px;"><a href="financeDetail.do?id=${finance.id}" target="_blank">${finance.borrowTitle}</a><br />
            <font size="-2" color="#999999">编号：${finance.number }</font></li>
          <li style=" height:26px;"><img src="images/new/1.gif"  style=" float:left" />借款金额：
            <s:property value="#finance.borrowAmount" default="0" />
            元</li>
          <li style=" height:26px;"><img src="images/new/2.gif" style=" float:left" />借款期限：
            <s:property value="#finance.deadline" default="0" />
            <s:if test="%{#finance.isDayThe ==1}">个月</s:if>
            <s:else>天</s:else>
          </li>
          <li style=" height:26px; "><img src="images/new/3.gif"  style=" float:left" />年利率： <font size="3" color="#FF6600">
            <s:property value="#finance.annualRate" default="0" />
            %&nbsp;
            <s:if test="#finance.annualRate >= 12"></s:if>
           
            </font></li>
        </ul>
         <%--<ul style=" float:left">
          <li style=" line-height:52px;">&nbsp;</li>
          <li style=" height:26px; "><span>还款方式： <!-- modify by houli 如果是天标，则还款方式为  到期还本付息 -->
              <c:choose>
                  <c:when test="${finance.paymentMode == 1}">等额本金</c:when>
                  <c:when test="${finance.paymentMode == 2}">先息后本</c:when>
                  <c:when test="${finance.paymentMode == 3}">按月付息到期还本</c:when>
                  <c:when test="${finance.paymentMode == 4}">等额本息</c:when>
                  <c:when test="${finance.paymentMode == 5}">等额本金</c:when>
                  <c:otherwise>等额本金</c:otherwise>
              </c:choose>
            </span></li>
          <li style=" height:26px; "><span>已投人数：
              <s:property value="#finance.investNum1" default="0" />
            人</span></li>
          <li style=" height:26px;  "></li>
        </ul>--%>
      </div>
      <div style=" float:right;  width:275px;padding-right: 40px; padding-top:30px;">
        <div style=" padding-right:20px; text-align:right;" class="nei_button" >
          <s:if test="%{#finance.borrowStatus == 1}">
            <input type="button" value="初审中" style="background: #ccc;cursor:default;" />
          </s:if>
          <s:elseif test="%{#finance.borrowStatus == 2}">
            <div class="bottm"> <input type="button" onclick="window.location.href='financeDetail.do?id=${finance.id}'" value="立即投标" 
														
              <s:if test="%{#finance.schedules==100}"> disabled="disabled" style="background:#ccc;cursor:default;"</s:if>
              /> </div>
          </s:elseif>
          <s:elseif test="%{#finance.borrowStatus == 3}">
            <input type="button" value="满标" style="background: url(../images/pic_all.png) -174px -356px no-repeat ;cursor:default;" />
          </s:elseif>
          <s:elseif test="%{#finance.borrowStatus == 4}">
            <s:if test="%{#finance.borrowShow == 2}">
              <input type="button" value="回购中" style="background: #ccc;cursor:default;" />
            </s:if>
            <s:else>
              <input type="button" onclick="window.location.href='financeDetail.do?id=${finance.id}'" value="还款中" style="background: url(../images/pic_all.png) -174px -300px no-repeat" />
            </s:else>
          </s:elseif>
          <s:elseif test="%{#finance.borrowStatus == 5}">
            <input type="button" value="已还完" style="background:url(../images/pic_all.png) -348px -300px no-repeat ;cursor:default;" />
          </s:elseif>
          <s:elseif test="%{#finance.borrowStatus == 7}">
            <input type="button" value="复审中" style="background:background:url(../images/pic_all.png) -348px -354px no-repeat;cursor:default;" />
          </s:elseif>
          <s:else>
            <input type="button" value="流标" style="background:background:url(../images/pic_all.png) -348px -408px no-repeat ;cursor:default;" />
          </s:else>
        </div>
        <div>
         <div style=" float:left; width:70px;line-height:28px;">借款进度：</div>	<div class="index_jd">	<div style=" font-size:0px; line-height:0px;width: <s:property value="#finance.schedules" default="0"/>%;height:12px; background:url(images/pic_all.png) 0 -67px no-repeat; "></div>
        </div>
      </div>
      <div  style=" padding-right:20px; line-height:32px; text-align:right;" >已成功借款：
        <font color="#FF6600"><s:property value="#finance.hasInvestAmount" default="---" /></font>元
      </div>
      <div  style=" padding-right:20px; text-align:right;" >剩余借款金额：<font color="#006600">
        <s:property value="#finance.investNum" default="---" /></font>元
      </div>
    </div>
  </div>
  <div class="cle"></div>
  </s:iterator>
  </s:if>
  <s:else>
    <li style="text-align: center;padding-top: 20px;padding-bottom: 20px;">没有数据</li>
  </s:else>
  <div class="cle"></div>
  <s:if test="pageBean.page!=null || pageBean.page.size>0">
    <div class="mjd_fy_all" >
      <shove:page url="finance.do" curPage="${pageBean.pageNum}" showPageCount="7" pageSize="${pageBean.pageSize }" theme="number" totalCount="${pageBean.totalNum}">
        <s:param name="m">${paramMap.m}</s:param>
        <s:param name="title">${paramMap.title}</s:param>
        <s:param name="paymentMode">${paramMap.paymentMode}</s:param>
        <s:param name="purpose">${paramMap.purpose}</s:param>
        <s:param name="raiseTerm">${paramMap.raiseTerm}</s:param>
        <s:param name="reward">${paramMap.reward}</s:param>
        <s:param name="arStart">${paramMap.arStart}</s:param>
        <s:param name="arEnd">${paramMap.arEnd}</s:param>
        <s:param name="type">${paramMap.type}</s:param>
        <s:param name="province">${paramMap.province}</s:param>
        <s:param name="regCity">${paramMap.regCity}</s:param>
      </shove:page>
      <div class="cle"></div>
    </div>
  </s:if>
</div>
<!--左侧结束--> 
<!--右侧-->
 <div class="mjd_tzlc_right">
  <div class="center_one_right2"  style=" margin-top:20px;">
    <div class="center_one_right_a">
      <div style="width:94px; text-align:center;  float:left; font-size:18px;">收益计算器</div>
    </div>
  </div>
  <div class="mjd_tzlc_right_two" style=" margin-top:20px; padding-bottom:20px; padding-top:20px; text-align:center;">
    <table width="100%" border="0">
      <tr height="50">
        <td width="80" align="right"><span>投标金额：</span></td>
        <td><input type="text" id="borrowSum" />&nbsp;元</td>
      </tr>
      <tr>
        <td width="80" align="right"><span>年利率：</span></td>
        <td><input type="text" id="yearRate" />
          %</td>
      </tr>
      <tr height="50">
        <td width="80" align="right"><span>计息天数：</span></td>
        <td><input type="text" id="borrowTime" />&nbsp;天</td>
      </tr>
     <!--  <tr >
        <td width="80" align="right"><span>还款方式：</span></td>
        <td><select id="repayWay">
          <option>按月等额本金</option>
          <option>按月等额本金</option>
        </select></td>
      </tr> -->
      <tr height="50">
        <td colspan="2" align="center" ><input type="button" value="计算" onclick="rateCalculate()"  style=" background:#F60"/></td>
      </tr>
      <tr>
        <td colspan="2"><strong ><span style="color:#666; float: none; " class="formtips" id="show_error"></span> </strong></td>
      </tr>
    </table>
    <span id="showIncome" style="color:#666;"></span> </div>
    <div style=" line-height:30px; width:223px; padding:10px; margin-top:15px; border:#e5e5e5 solid 1px; font-size:12px;">注：p2p理财不等于银行存款，收益来自于借贷双方根据利率市场情况而定，而过往业绩不预示未来表现。</div>
</div> 
<!--右侧结束-->

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
