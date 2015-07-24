<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<jsp:include page="/include/head.jsp"></jsp:include>
<link href="css/user.css" rel="stylesheet" type="text/css" />
</head>
<body>
<!-- 引用头部公共部分 -->
<jsp:include page="/include/top.jsp"></jsp:include>
<div class="user-all">
  <div class="user-center">
    <div style=" width:1170px; margin:0px auto; padding-bottom:24px;"><img src="/images/v1/detail_logo_bg.jpg" width="1170" height="6"  alt=""/></div>
    <jsp:include page="/include/menu_userManage.jsp"></jsp:include>
    <div class="user-right" style=" height:553px;">
      <div style=" background:url(../../../images/main-bg.png); height:41px; text-align:center; line-height:41px; color:#333; margin-bottom:30px; font-size:16px;">回收本息</div>
      <div class="tzjl_cgtz_box"  style=" width:100%">
        <table width="884" border="0" cellpadding="0" cellspacing="1" bgcolor="#e5e5e5" class="hhzx_syjl">
          <tr height="60">
              <td colspan="13" bgcolor="#ffffff">
            <span style="float: left;margin-left: -25px; margin-right:130px;"><span>最近7天：（<i style="color: red">${map.weeks }</i>）</span> <span>1个月（<i style="color: red">${map.months1 }</i>）</span><span>2个月（<i
										style="color: red">${map.months2 }</i>）</span><span>3个月（<i style="color: red">${map.months3 }</i>）</span></span>
            <form action="queryFontFundrecordInvest.do" method="post" id="form" style="float: left;">
              <s:textfield type="text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})" id="startTime" name="startTime" />
              到
              <s:textfield type="text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})" id="endTime" name="endTime" />
              <input type="button" value="搜索" id="search" />
               
            </form> </td>
          </tr>
          <tr align="center" height="30">
            <td bgcolor="#faf9f9" width="96">类型</td>
            <td bgcolor="#faf9f9" width="136">标题</td>
            <td bgcolor="#faf9f9" width="86">收入</td>
            <td bgcolor="#faf9f9" width="96">支出</td>
            <td bgcolor="#faf9f9" width="86">总计</td>
            <td bgcolor="#faf9f9" width="116">交易时间</td>
            <td bgcolor="#faf9f9" width="76">协议</td>
            <td bgcolor="#faf9f9" width="76">状态</td>
          </tr>
          <s:if test="pageBean.page!=null && pageBean.page.size>0">
            <s:set name="counts" value="#request.pageNum" />
            <s:iterator value="pageBean.page" var="bean" status="s">
              <tr align="center" height="30">
                <td bgcolor="#ffffff">${bean.fundMode }</td>
                <td bgcolor="#ffffff"><a href="financeDetail.do?id=${bean.borrow_id }">${bean.borrowTitle } </a></td>
                <td bgcolor="#ffffff"><s:if test="#bean.income==0">0.00</s:if>
                  <s:else>${bean.income }</s:else></td>
                <td bgcolor="#ffffff"><s:if test="#bean.spending==0">0.00</s:if>
                  <s:else>${bean.spending }</s:else></td>
                <td bgcolor="#ffffff">${bean.sum }</td>
                <td bgcolor="#ffffff" width="70px;"><s:date name="#bean.recordTime" format="yyyy-MM-dd HH:mm:ss" /></td>
                <td bgcolor="#ffffff"><b class="tzjl_fwxy">查看协议</b></td>
                <td bgcolor="#ffffff">成功</td>
              </tr>
            </s:iterator>
          </s:if>
          <s:else>
            <tr>
              <td align="center" colspan="8">暂无数据</td>
            </tr>
          </s:else>
          <tr> 
            <!--<td colspan="10">合计：总收入：￥${paramMap.SumincomeSum }&nbsp;&nbsp;&nbsp;&nbsp; 总支出：￥${paramMap.SumspendingSum }&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						  										 可用金额合计：￥${paramMap.SumusableSum }&nbsp;</td> --> 
          </tr>
        </table>
      </div>
      <div class="mjd_fy_all" align="center">
        <p>
          <shove:page curPage="${pageBean.pageNum}" showPageCount="6" pageSize="${pageBean.pageSize }" theme="number" totalCount="${pageBean.totalNum}" url="queryFontFundrecordInvest.do">
            <s:param name="startTime">
              <s:property value="startTime" />
            </s:param>
            <s:param name="endTime">
              <s:property value="endTime" />
            </s:param>
          </shove:page>
        </p>
      </div>
    </div>
  </div>
  <div style=" width:1170px; margin:0px auto">&nbsp;</div>
</div>
</div>
<div class="cle"></div>

<!-- 引用底部公共部分 -->
<jsp:include page="/include/footer.jsp"></jsp:include>
<script type="text/javascript" src="script/jquery.shove-1.0.js"></script> 
<script type="text/javascript" src="script/jquery-1.7.1.min.js"></script> 
<script type="text/javascript" src="My97DatePicker/WdatePicker.js"></script> 
<script type="text/javascript">
		$(function(){
			//样式选中
			$(".wdzh_top_ul li").eq(2).addClass("wdzhxxk");
		})
			$(".tzjl_fwxy").click(function() {
				$(".tzjl_fwxyh").show();
			});
			$("#btn").click(function() {
				$(".tzjl_fwxyh").hide();
			})
			$("#search").click(function() {
				var startTime = $.trim($("#startTime").val());
				var endTime = $.trim($("#endTime").val());
				if (startTime == "") {
					alert("请选择开始时间");
				} else if (endTime == "") {
					alert("请选择结束时间");
				} else if (startTime > endTime) {
					alert("开始时间不能大于结束时间");
				} else {
					$("#form").submit();
				}
			});
		</script>
</body>
</html>
