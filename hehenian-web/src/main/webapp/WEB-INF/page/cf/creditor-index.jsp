<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<jsp:include page="/include/cf-head.jsp"></jsp:include>
<link href="/newcss/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="script/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="script/jquery.shove-1.0.js"></script>
<script src="script/add_all.js" type="text/javascript"></script>
<script src="script/MSClass.js" type="text/javascript"></script>
<style>
.search_all1 { width:100%; border:1px solid #e3e3e3; padding:0px; overflow:hidden; background:#f9f9f9;}
.mjd_tzlc_left_oneh h2{ font-size:12px; font-weight:normal; float:left; margin-top:6px; margin-left:5px;}
.mjd_tzlc_left_oneh strong{ font-weight:normal; color:#44a0ff;}
.mjd_tzlc_left_oneh img{ vertical-align:-3px;}
.mjd_tzlc_left_oneh p{ margin-top:10px;}
.mjd_tzlc_left_oneh a{ margin-right:5px; color:#333;}
.mjd_tzlc_left_oneh a:hover{ color:#f07a05;}
.mjd_tzlc_left_oneh:hover{ background:#f8f8f8;}
.mjd_tzlc_left_oneh_but{ width:95px; height:27px; background:url(../images/pic_all.png?t=10) -97px -84px no-repeat; text-align:center; color:#fff; border:none; cursor:pointer;}
.mjd_tzlc_lefth{ width:975px; float:left; padding-bottom:15px; display:none; padding-left:8px; padding-right:8px;}
.mjd_tzlc_lefth h1{ width:975px; height:50px; display:block; font-size:12px; color:#666; border-bottom:1px solid #e0e0e0; line-height:50px;} 
.mjd_tzlc_lefth{ color:#666;}
.mjd_tzlc_left_oneh h4{ font-size:12px; font-weight:normal; color:#d02013;}
.mjd_tzlc_left_oneh u{ text-decoration:none; display:block; float:left; width:22px; height:37px; background:url(../images/hhn_djs_bg.png?t=10) no-repeat; text-align:center; line-height:31px; color:#e94515; font-size:18px; font-weight:bold; margin-right:3px;}
.mjd_tzlc_left_oneh span{ float:left; margin:10px 3px 0 0px;}
.mjd_tzlc_left_oneh b{ color:#0d79c1;}
.mjd_tzlc_left_oneh i{ font-style:normal; color:#0d79c1;}
.mjd_tzlc_left_oneh{ width:833px; border-bottom:1px dashed #e0e0e0; padding:20px;}
.mjd_tzlc_left_oneh_but1{ width:95px; height:27px; background:url(../images/pic_all.png?t=10) -1px -84px no-repeat; text-align:center; color:#fff; border:none; cursor:pointer;}
.mjd_tzlc_left_oneh_but1:hover{ background:url(../images/pic_all.png?t=10) -1px -112px no-repeat;}
/* 内页通用 页码翻页 */
.page{ text-align:center; font-size:12px; margin-top: 10px;margin-bottom: 5px; padding-bottom:5px}
.page a{  display:inline-block; height:20px; line-height:20px;border:1px solid #e0e0e0;color:#333;padding-left:5px; padding-right:7px;cursor:pointer; margin-right:5px; margin-top:5px;  }
.page a:hover{ text-decoration:none; }
.page_input{ width:30px; height:24px; margin-left:5px; margin-right:5px;}
.page_btn{ width:30px; height:24px; margin-left:5px; margin-right:5px; padding-left:5px; padding-right:5px; cursor:pointer;}

</style>
</head>
<body>
<!-- 头部 -->
<jsp:include page="/include/cf-top.jsp"></jsp:include>
<div class="s_sur_ix main el_container" style=" overflow:hidden; margin-bottom:20px;"> 
      <!--左侧-->
      <div class=" nr_left"> 
        <!--筛选栏-->
        <div class="search_all1">
         <div class="mjd_tzlc_all_center" id="assignmentData"> 
        <!-- 已发布债权div -->
        <div class="mjd_tzlc_left" style="display:block;margin-top: 20px; border-right-color: rgb(230, 230, 230); border-right-width: 1px; border-right-style: solid; display: block;" >
        <div class="mjd_tzlc_left_oneh" style=" overflow:hidden; padding:10px 20px; text-align:center; background:#f3f3f3; border-top:1px dashed #e0e0e0; border-right:1px dashed #e0e0e0; border-left:1px dashed #e0e0e0; font-weight:bold">
            	<div style=" float:left; width:150px; text-align:left">标题</div>
                <div style=" float:left; width:180px; padding-left:30px; text-align:left;">债权详情</div>
                <div style=" float:left; width:180px;padding-left:30px; text-align:left;margin-right:20px;">其他信息</div>
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
               
                <div style=" float:left; width:180px; line-height:26px;padding-left:30px;">
          
                 债权总额：<i>${debtSum }</i> 元 <br />

                  转让价格：<i>${auctionBasePrice}</i> 元<br />

                年利率：<i>${annualRate}%</i> <br />

           
                </div>
                <div style=" float:left; line-height:26px; width:180px;padding-left:30px; margin-right:20px;">
		                剩余时间：${remainDays }<br />
		        无逾期
                </div>
             
                <div style=" float:left;">
                	<c:choose>
                		<c:when test="${bean.debtStatus == 1 && bean.remainTimes > 0}">
                			<input type="button" value="申请中" class="mjd_tzlc_left_oneh_but1" style="cursor: default; width:100px; height:35px;background:#ccc"  />
                		</c:when>
                		<c:when test="${bean.debtStatus == 2 && bean.remainTimes > 0}">
                			<input type="button" value="转让中" style="background:#F60;width:100px; height:35px; color:#FFF" class="mjd_tzlc_left_oneh_but1" onclick="window.open('queryDebtDetailHHN.do?id=${id }','_blank')" />
                		</c:when>
                		<c:when test="${bean.debtStatus == 3}">
                			<input type="button" value="已转让" class="mjd_tzlc_left_oneh_but1" style="cursor: default; width:100px; height:35px;background:#ccc"  />
                		</c:when>
                		<c:otherwise>
							<input type="button" value="已过期" class="mjd_tzlc_left_oneh_but1" style="cursor: default; width:100px; height:35px;background:#ccc" onclick="window.open('queryDebtDetailHHN.do?id=${id }','_blank')" />
						</c:otherwise>
                	</c:choose>
                </div>
              </div>
            </s:iterator>
            <div class="page" style=" line-height:40px; text-align:center;">
              <shove:page url="queryFrontAllDebt.do" curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="number" totalCount="${pageBean.totalNum}"> </shove:page>
            </div>
          </s:else>
        </div>
<!--右侧结束-->
      </div>
        </div>
      </div>
      <!--右侧-->
      <jsp:include page="/include/cf-right.jsp"></jsp:include>
    </div>
    <jsp:include page="/include/cf-footer.jsp"></jsp:include>
    <input type="hidden" value="${idNo}" id="idNo"/>
    <form id="flr" target="_blank" action="" method="post" >
    </form>	
    <!--推荐人-->
    <div class="light-box" id="tuijian" style="display:none;">
    	<div class="light-title">推荐人信息<a id="close_x" class="close light-box-close" href="#">X</a></div>
    	<div class="light-content">
        	<div class="tuijian">
            	<span class="light-error" id="tuijianError"></span>
            	<p><label>推荐人：</label><input type="text" class="input" value="" name="paramMap.refferee" id="tuijianren" /></p>
                <div class="tuijian-btn"><a style=" margin:0px;" class="bt_green" href="automaticbid.do" id="tuijianSave"><span class="bt_green_lft"></span><strong>保存</strong><span class="bt_green_r"></span></a><a style=" margin:0px;" class="bt_blue" href="javascript:;" id="tuijianCancel"><span class="bt_blue_lft"></span><strong>跳过</strong><span class="bt_blue_r"></span></a></div>
                <div class="save-loading">保存中...,请稍候</div>
            </div>
        </div>
    </div>
     <!--推荐人 End-->
<script>
	 $(function(){
  	   $("#jumpPage").live('click',function() {
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
	           window.location.href="queryFrontAllDebt.do?curPage="+curPage+"&publishTimeStart="+$("#publishTimeStart").val()+"&publishTimeEnd="+$("#publishTimeEnd").val();
			});
     });
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
</body>
</html>
