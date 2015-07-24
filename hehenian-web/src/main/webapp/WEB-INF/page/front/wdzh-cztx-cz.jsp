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
      <div class="u-right"><div class="user-right">
      <h2>充值记录</h2>
      <table id="rounded-corner" summary="2007 Major IT Companies' Profit" style=" margin-top:20px; width:100%">
          <thead>
        <tr align="center" height="30">
          <th scope="col" class="rounded-company">订单号</th>
          <th scope="col" class="rounded">用户名</th>
         <%-- <th scope="col" class="rounded">类型</th>--%>
          <th scope="col" class="rounded">充值金额</th>
          <th scope="col" class="rounded">手续费</th>
        <%--  <th scope="col" class="rounded">到账金额</th>--%>
          <th scope="col" class="rounded">充值时间</th>
          <th scope="col" class="rounded-q4">状态</th>
        </tr>
            </thead>
        <s:if test="pageBean.page==null ||pageBean.page.size==0">
          <tr align="center" class="gvItem">
            <td colspan="9">暂无数据</td>
          </tr>
        </s:if>
        <s:else>
          <s:iterator value="pageBean.page" var="bean" status="st">
            <tr>
              <td height="40" align="center" bgcolor="#ffffff">${bean.id }</td>
              <td bgcolor="#ffffff" align="center">${bean.userId }</td>
             <%-- <td bgcolor="#ffffff" align="center">
                  <s:if test="#bean.rechargeType==1">汇付天下</s:if>
                <s:elseif test="#bean.rechargeType==2">汇付天下</s:elseif>
                <s:elseif test="#bean.rechargeType==3">汇付天下</s:elseif>
                <s:elseif
							test="#bean.rechargeType==4">手动充值</s:elseif>
              </td>--%>
              <td bgcolor="#ffffff" align="center">${bean.rechargeMoney }</td>
              <td bgcolor="#ffffff" align="center">0.00</td>
             <%-- <td bgcolor="#ffffff" align="center">${bean.rechargeMoney - bean.cost }</td>--%>
              <td bgcolor="#ffffff" align="center"><s:date name="#bean.rechargeTime" format="yyyy-MM-dd HH:mm:ss" /></td>
              <td bgcolor="#ffffff" align="center">${bean.status }</td>
            </tr>
          </s:iterator>
        </s:else>
      </table>
      <input type="hidden" name="curPage" id="pageNum" />
      <div class="page" style="margin-left: 300px; margin-top:30px;">
        <p>
          <shove:page url="queryFontRechargeHistory.do" curPage="${pageBean.pageNum}" showPageCount="6" pageSize="${pageBean.pageSize }" 
          theme="number" totalCount="${pageBean.totalNum}"></shove:page>
   
        </p>
      </div>
    </div></div>
    </div>
    <div style=" width:1170px; margin:0px auto">&nbsp;</div>
  </div>
</div>
<div class="cle"></div>


<jsp:include page="/include/footer.jsp"></jsp:include>
</body>
<script>
$(function(){
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
        window.location.href="queryFontRechargeHistory.do?pageBean.pageNum="+curPage;
	});

})
</script>
</html>