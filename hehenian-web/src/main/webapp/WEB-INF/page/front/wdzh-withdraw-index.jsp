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
      <div class="u-right"><div class="user-right" >
      <h2>提现记录</h2>
        <table id="rounded-corner" summary="2007 Major IT Companies' Profit" style=" margin-top:20px; width:100%">
        <tr align="center" height="30">
          <th>姓名</th>
          <th>时间</th>
          <th>电话</th>
          <th>提现账号</th>
          <th>提现金额</th>
          <th>手续费</th>
          <%--<th>状态</th>--%>
          <%--    <th>操作</th>--%>
        </tr>
        <s:if test="pageBean.page!=null && pageBean.page.size>0">
          <s:iterator value="pageBean.page"  var="bean">
            <tr>
              <td height="40" align="center"  bgcolor="#ffffff">${bean.name}</td>
              <td bgcolor="#ffffff" align="center"><s:date name="#bean.applyTime" format="yyyy-MM-dd HH:mm:ss" /></td>
              <td bgcolor="#ffffff" align="center">${bean.cellPhone}</td>
              <td bgcolor="#ffffff" align="center">${bean.acount}</td>
              <td bgcolor="#ffffff" align="center">${bean.sum}</td>
              <td bgcolor="#ffffff" align="center">${bean.poundage}</td>
              <%--
              <s:if test="#bean.status == 1">
                <td bgcolor="#ffffff" align="center">审核中</td>--%>
                <%--	       	  <td align="center"><a href="javascript:void(0);" onclick="deleteWithdraw(${bean.id},${bean.sum})">取消</a></td>--%>
                <%--
              </s:if>
              <s:else>
                <s:if test="#bean.status ==2">
                  <td bgcolor="#ffffff" align="center">已提现</td>
                  --%>
                  <%--	             <td align="center">--</td>--%>
                  <%--
                </s:if>
                <s:else>
                  <s:if test="#bean.status ==4">
                    <td  bgcolor="#ffffff" align="center">转账中</td>
                    --%>
                    <%--	              <td align="center">--</td>--%>
                    <%--
                  </s:if>
                    <s:if test="#bean.status ==5 && #bean.trxId !=null &&#bean.checkTime == null ">
                        <td  bgcolor="#ffffff" align="center">审核中</td>
                        --%>
                        <%--	              <td align="center">--</td>--%>
                        <%--
                    </s:if>
                  <s:else>
                    <td  bgcolor="#ffffff" align="center">失败</td>
                    --%>
                    <%--	               <td align="center">--</td>--%>
                    <%--
                  </s:else>
                </s:else>
              </s:else>
              --%>
            </tr>
          </s:iterator>
        </s:if>
        <s:else>
          <tr>
            <td colspan="9" align="center">暂无信息</td>
          </tr>
        </s:else>
      </table>
      <div class="mjd_fy_all" align="center" style=" margin-top:20px;">
		  <p>
		  	<shove:page curPage="${pageBean.pageNum}" showPageCount="6" pageSize="${pageBean.pageSize }" theme="number" totalCount="${pageBean.totalNum}" url="queryWithdrawList.do"></shove:page>
		  </p>
		</div>
<script>
   function deleteWithdraw(id,sum){
      param["paramMap.wId"] = id;
      param["paramMap.money"] = sum;
      if(!window.confirm("确定要取消提现记录")){
        return;
      }
      $.shovePost("deleteWithdraw.do",param,function(data){
           if(data.msg == 1){
             alert("取消成功");
             jumpUrl('withdrawLoad.do');
           }else{
             alert(data.msg);
             return;
           }
      });
   }
   $(function() {
	   $("#jumpPage").click(function() {
			var curPage = $("#curPageText").val();
			if (isNaN(curPage)) {
				alert("输入格式不正确!");
				return;
			}
           window.location.href="queryWithdrawList.do?curPage="+curPage+"&pageSize=6";
		});
   });
</script>
    </div></div>
    
    </div>
    
    <div style=" width:1170px; margin:0px auto">&nbsp;</div>
  </div>
</div>
<div class="cle"></div>


<jsp:include page="/include/footer.jsp"></jsp:include>
</body>
</html>