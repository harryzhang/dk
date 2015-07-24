<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<jsp:include page="/include/head.jsp"></jsp:include>
<link href="css/hhncss.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="script/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="script/jquery.shove-1.0.js"></script>
<script src="script/add_all.js" type="text/javascript"></script>
<script src="script/MSClass.js" type="text/javascript"></script>
<script type="text/javascript">
	$(function() {
//		hhn(2);
        hhnNew("topIndex-zqzr");
	});
</script>
</head><body>
<!-- 引用头部公共部分 --> 
<div style="position: absolute; height:54px; line-height:54px; background:#f3f3f3; width:100%; margin-top:151px !important; margin-top:177px;border-bottom:1px solid #e5e5e5;border-top:1px solid #e5e5e5;"></div>
<jsp:include page="/include/top.jsp"></jsp:include>
<div class="cle"></div>
<div class="center">
  <div class="wytz_center">
    <div class="mjd_tzlc_all">
      <ul>
        <li class="tzlc" onclick="javascript:window.location.href='queryFrontAllDebt.do'">已发布债权</li>
        <!--<li onclick="javascript:window.location.href='queryFrontRedayDebt.do'">即将发布债权</li>--> 
        <!--<li onclick="javascript:window.location.href='queryFrontCanDebt.do'">我要转让债权</li>-->
      </ul>
      <div class="cle"></div>
      <div class="mjd_tzlc_all_center" id="assignmentData"> 
        
        <!-- 已发布债权div -->
        <div class="mjd_tzlc_left" style="display:block;margin-top: 30px; border-right-color: rgb(230, 230, 230); border-right-width: 1px; border-right-style: solid; display: block;" >
        <div class="mjd_tzlc_left_oneh" style=" overflow:hidden; padding:10px 20px; text-align:center; background:#f3f3f3; border-top:1px dashed #e0e0e0; border-right:1px dashed #e0e0e0; border-left:1px dashed #e0e0e0; font-weight:bold">
            <div style=" float:left; width:150px; text-align:left">标题</b></div>
               
                <div style=" float:left; width:250px; padding-left:30px; text-align:left;">债权详情</div>
                <div style=" float:left; width:200px;padding-left:30px; text-align:left;margin-right:20px;">其他信息</div>
                 
                <div style=" float:left;">操作</div>
          </div>
          <s:if test="pageBean.page==null || pageBean.page.size==0">
            <div>暂无记录</div>
          </s:if>
          <s:else>
            <s:iterator value="pageBean.page" var="bean">
              <div class="mjd_tzlc_left_oneh" style=" overflow:hidden">
                <div style=" float:left; width:150px; line-height:26px;"><font style="color:#0cf">${borrowTitle}</font><!--${borrowTitle}--><br />
<font style="#999" size="-2">编号：${number }</font></div>
               
                <div style=" float:left; width:250px; line-height:26px;padding-left:30px;">
          
                 债权总额：<i>${debtSum }</i> 元 <br />

                  转让价格：<i>${auctionBasePrice}</i> 元<br />

                年利率：<i>${annualRate}%</i> <br />

           
                </div>
                <div style=" float:left; line-height:26px; width:200px;padding-left:30px; margin-right:20px;">
		                剩余时间：${remainDays }<br />
		        无逾期
                </div>
             
                <div style=" float:left;">
                	<c:choose>
                		<c:when test="${bean.debtStatus == 1 && bean.remainTimes > 0}">
                			<input type="button" value="申请中" class="mjd_tzlc_left_oneh_but1" style="cursor: default; width:150px; height:35px;background:#ccc"  />
                		</c:when>
                		<c:when test="${bean.debtStatus == 2 && bean.remainTimes > 0}">
                			<input type="button" value="转让中" style="background:#F60;width:150px; height:35px; color:#FFF" class="mjd_tzlc_left_oneh_but1" onclick="window.open('queryDebtDetailHHN.do?id=${id }','_blank')" />
                		</c:when>
                		<c:when test="${bean.debtStatus == 3}">
                			<input type="button" value="已转让" class="mjd_tzlc_left_oneh_but1" style="cursor: default; width:150px; height:35px;background:#ccc"  />
                		</c:when>
                		<c:otherwise>
							<input type="button" value="已过期" class="mjd_tzlc_left_oneh_but1" style="cursor: default; width:150px; height:35px;background:#ccc" onclick="window.open('queryDebtDetailHHN.do?id=${id }','_blank')" />
						</c:otherwise>
                	</c:choose>
                </div>
              </div>
            </s:iterator>
            <div class="mjd_fy_all" style=" line-height:40px; text-align:center;">
              <shove:page url="queryFrontAllDebt.do" curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="number" totalCount="${pageBean.totalNum}"> </shove:page>
            </div>
          </s:else>
        </div>
        
        
        
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
</div>
<!--右侧结束-->
      </div>
    </div>
    
    <div class="cle"></div>
  </div>
  	
</div>
<div class="cle"></div>
<script>

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
<!-- 引用底部公共部分 -->
<jsp:include page="/include/footer.jsp"></jsp:include>
</body>
</html>
