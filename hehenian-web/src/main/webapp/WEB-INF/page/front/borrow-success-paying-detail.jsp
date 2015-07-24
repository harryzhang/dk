 <%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<div class="biaoge2" id="biaoge2_details">
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <th colspan="3" align="left" style="padding-top:0px;">还款明细</th>
    </tr>
  <tr>
    <td align="left">标题：${borrowTitle }</td>
    <td align="left">借款金额：￥${borrowAmount } </td>
    <td align="left">&nbsp;</td>
  </tr>
  <tr>
    <td align="left">借款利率：${annualRate }% </td>
    <td align="left">借款期限：${deadline }
    <s:if test="#request.isDayThe == 1">
    	个月
    </s:if>
    <s:else>
   		 天
    </s:else>
     </td>
    <td align="left">&nbsp;</td>
  </tr>
  <tr>
    <td align="left">还款方式：
   <s:if test="#request.isDayThe == 1">
    ${paymentMode }
    </s:if>
    <s:else>
    到期还本付息
    </s:else>
    </td>
    <td align="left">发布时间：${publishTime }</td>
    <td align="left">借款时间：${auditTime} </td>
  </tr>
    </table>

    </div>
    <div class="biaoge">
          <table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <th>序号</th>
    <th>计划还款日期</th>
    <th>计划还款本息</th>
    <th>实还日期</th>
    <th>逾期天数</th>
    <th>实还本息</th>
    <th>逾期罚息</th>
    <th>总还款金额</th>
    <th>状态</th>
   <%-- <th>操作</th>--%>
    
    </tr>
    
    <s:if test="pageBean.page==null || pageBean.page.size<=0">
    <tr><td align="center" colspan="11">暂无记录</td></tr>
  </s:if>
  
  <s:else>
	    <s:iterator value="pageBean.page"  var="bean" status="sta"> 
		    <tr>
			    <td align="center">${bean.id} </td>
			    <td align="center"><s:date name="#bean.repayDate" format="yyyy-MM-dd" /></td>
			    <td align="center">￥${bean.repayTotal }</td>
			    <td align="center"><s:date name="#bean.realRepayDate" format="yyyy-MM-dd" /></td>
			    <td align="center">${bean.lateDay }天</td>
			    <td align="center">￥${bean.hasPI }</td>
			    <td align="center">￥0.00</td>
			    <td align="center">￥${bean.total }</td>
			    <s:if test="#bean.repayStatus=='已偿还'">
			       <td align="center">${bean.repayStatus }</td>
			       <td align="center">-</td>
			    </s:if>
			    <s:else>
			       <td align="center">${bean.repayStatus }</td>
			       <%--<td align="center"><a href="javascript:myPay(${bean.id});">还款</a></td>--%>
			    </s:else>
		    </tr>
	    </s:iterator>
    </s:else>
          </table>
<shove:page curPage="${pageBean.pageNum}" showPageCount="6" pageSize="${pageBean.pageSize }" 
theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>	
          </div>    
   <span id="my_pay"></span>       
<script type="text/javascript" src="script/jbox/jquery.jBox-2.3.min.js"></script>
<script type="text/javascript" src="script/jbox/jquery.jBox-zh-CN.js"></script>
 <script>	
    function myPay(id){//还款
       //$("#id").css('display','block');
       var url = "queryMyPayData.do?payId="+id;
       jQuery.jBox.open("post:"+url, "还款", 450,450,{ buttons: { } });
       
       //return false;
       /*$.shovePost("queryMyPayData.do",{payId:id},function(data){
	       $("#my_pay").html(data);	       
	    });*/
    }
 </script>