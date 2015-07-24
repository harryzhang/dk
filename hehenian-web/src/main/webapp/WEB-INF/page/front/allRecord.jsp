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
          <h2>资金记录</h2>
          <div class="tzjl_cgtz_box"  style=" width:100%">
            <table id="rounded-corner" summary="2007 Major IT Companies' Profit" align="center">
              <thead>
                <tr>
                  <td colspan="13" style=" background:#FFF"><span style="float: left;margin-left: -25px; margin-right:60px;"><span>最近7天：（<i style="color: red">${map.weeks }</i>）</span> <span>1个月（<i style="color: red">${map.months1 }</i>）</span><span>2个月（<i style="color: red">${map.months2 }</i>）</span><span>3个月（<i style="color: red">${map.months3 }</i>）</span> </span>
                    <form action="allRecord.do" method="post" id="form" style="float: left;">
                      <s:textfield type="text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})" id="startTime" name="startTime" />
                      到
                      <s:textfield type="text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})" id="endTime" name="endTime" />
                      <input type="button" value="搜索" id="search" />
                    </form></td>
                </tr>
                <tr align="center" >
                  <th  class="rounded-company" scope="col">类型</th>
                  <th class="rounded" scope="col">备注</th>
                  <th class="rounded" scope="col">收入</th>
                  <th  class="rounded" scope="col">支出</th>
                  <th scope="col" class="rounded">交易时间</th>
                  <%--<th width="64" class="rounded-q4" scope="col">状态</th>--%>
                </tr>
              </thead>
              <tfoot>
              </tfoot>
              <tbody>
                <s:if test="pageBean.page!=null && pageBean.page.size>0">
                  <s:set name="counts" value="#request.pageNum" />
                  <s:iterator value="pageBean.page" var="bean" status="s">
                    <tr>
                      <td>${bean.fundMode }</td>
                      <td style=" padding:10px "><%--<a href="financeDetail.do?id=${bean.borrow_id }">${bean.borrowTitle } </a>--%>
                        ${bean.remarks }</td>
                      <td><s:if test="#bean.income==0">0.00</s:if>
                        <s:else>${bean.income }</s:else></td>
                      <td><s:if test="#bean.spending==0">0.00</s:if>
                        <s:else>${bean.spending }</s:else></td>
                      <%--										<td bgcolor="#ffffff"><s:if test="#bean.cost==0">0.00</s:if> <s:else>${bean.cost }</s:else>--%>
                      <%--										</td>--%>
                      <td width="150"><s:date name="#bean.recordTime" format="yyyy-MM-dd HH:mm:ss" /></td>
                      <%--										<td bgcolor="#ffffff"><b class="tzjl_fwxy">查看协议</b></td>--%>
                      <%--<td style=" text-align:center"><img src="/images/neiye2_44.jpg" /></td>--%>
                    </tr>
                  </s:iterator>
                </s:if>
                <s:else>
                  <tr align="center" height="30">
                    <td colspan="8" align="center">暂无数据</td>
                  </tr>
                </s:else>
                <tr> 
                  <!--<td colspan="10">合计：总收入：￥${paramMap.SumincomeSum }&nbsp;&nbsp;&nbsp;&nbsp; 总支出：￥${paramMap.SumspendingSum }&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						  										 可用金额合计：￥${paramMap.SumusableSum }&nbsp;</td> --> 
                </tr>
              </tbody>
            </table>
          </div>
          <div class="mjd_fy_all" align="center" style=" margin-top:30px;">
            <p>
              <shove:page curPage="${pageBean.pageNum}" showPageCount="6" pageSize="${pageBean.pageSize }" theme="number" totalCount="${pageBean.totalNum}" url="allRecord.do">
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
		$(function() {
			//样式选中
			$(".wdzh_top_ul li").eq(2).addClass("wdzhxxk");
			
			
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
                window.location.href="allRecord.do?curPage="+curPage+"&startTime="+$("#startTime").val()+"&endTime="+$("#endTime").val();
			});

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
