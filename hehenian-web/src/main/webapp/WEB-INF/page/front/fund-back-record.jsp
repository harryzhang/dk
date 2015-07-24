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
    <div style=" overflow:hidden">
      <div class="u-left">
        <jsp:include page="/include/menu_userManage.jsp"></jsp:include>
      </div>
      <div class="u-right">
        <div class="user-right">
          <h2>回收本息</h2>
          <div class="tzjl_cgtz_box" style=" width:100%">
            <table id="rounded-corner" summary="2007 Major IT Companies' Profit">
              <thead>
                <tr>
                  <td colspan="13" style=" background:#FFF"><span style="float: left;margin-left: -25px; margin-right:127px;"><span>最近7天：（<i style="color: red">${map.weeks }</i>）</span> <span>1个月（<i style="color: red">${map.months1 }</i>）</span><span>2个月（<i
										style="color: red">${map.months2 }</i>）</span><span>3个月（<i style="color: red">${map.months3 }</i>）</span></span>
                    <form action="queryFontFundrecordReback.do" method="post" id="form" style="float: left;">
                      <s:textfield type="text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})" id="startTime" name="startTime" />
                      到
                      <s:textfield type="text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})" id="endTime" name="endTime" />
                      <input type="button" value="搜索" id="search" />
                    </form></td>
                </tr>
                <tr align="center" >
                  <th style=" width:120px;" class="rounded-company" scope="col">类型</th>
                  <th scope="col" class="rounded">标题</th>
                  <th scope="col" class="rounded">收入</th>
                  <th scope="col" class="rounded">支出</th>
                  <th scope="col" class="rounded">总计</th>
                  <th scope="col" class="rounded" style=" width:140px;">交易时间</th>
       
                  <%--<th scope="col" class="rounded-q4">状态</th>--%>
                </tr>
              </thead>
              <tbody>
                <s:if test="pageBean.page!=null && pageBean.page.size>0">
                  <s:set name="counts" value="#request.pageNum" />
                  <s:iterator value="pageBean.page" var="bean" status="s">
                    <tr align="center" height="30">
                      <td>${bean.fundMode }</td>
                      <td><a href="financeDetail.do?id=${bean.borrow_id }">${bean.borrowTitle } </a></td>
                      <td><s:if test="#bean.income==0">0.00</s:if>
                        <s:else>${bean.income }</s:else></td>
                      <td><s:if test="#bean.spending==0">0.00</s:if>
                        <s:else>${bean.spending }</s:else></td>
                      <td>${bean.sum }</td>
                      <td><s:date name="#bean.recordTime" format="yyyy-MM-dd HH:mm:ss" /></td>
         
                      <%--<td style=" text-align:center"><img src="/images/neiye2_44.jpg" /></td>--%>
                    </tr>
                  </s:iterator>
                </s:if>
                <s:else>
                  <tr>
                    <td align="center" colspan="8">暂无数据</td>
                  </tr>
                </s:else>
              </tbody>
            </table>
          </div>
          <div class="mjd_fy_all" align="center" style=" margin-top:20px;">
            <p>
              <shove:page curPage="${pageBean.pageNum}" showPageCount="6" pageSize="${pageBean.pageSize }" theme="number" totalCount="${pageBean.totalNum}" url="queryFontFundrecordReback.do">
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
