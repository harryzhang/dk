<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<link href="css/css.css" rel="stylesheet" type="text/css" />

<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <th align="center">头像 </th>
    <th align="left" style="width: 150px;padding-left:25px;">标题 / 借款者 </th>
    <th align="center">金额 / 利率 / 期限 </th>
    <th align="center">信用指数 / 奖励 </th>
    <th align="left" style="padding-left: 35px;width: 130px;">进度</th>
  </tr>
  <s:if test="pageBean.page!=null || pageBean.page.size>0">
    <s:iterator value="pageBean.page" var="finance">
    <tr>
    <td align="center" class="tx">
    	<a href="financeDetail.do?id=${finance.id}" title="<s:property value="#finance.borrowTitle" default="---"/>"><img src="${finance.imgPath}" width="80" height="79" /></a></td>
    <td align="center" class="bt"><h2>
    <s:if test="#finance.hasPWD == 1">
    	<img src="images/lock.png" width="16" height="16" />
    </s:if>
    <a href="financeDetail.do?id=${finance.id}" title="<s:property value="#finance.borrowTitle" default="---"/>">
    <span id="tit"><s:property value="#finance.borrowTitle" default="---"/></span>
    <s:if test="#finance.borrowWay ==1">
    	<img src="images/neiye1_53.jpg" width="14" height="17" />
    </s:if><s:elseif test="#finance.borrowWay ==2">
    	<img src="images/neiye1_55.jpg" width="14" height="17" />
    </s:elseif><s:elseif test="#finance.borrowWay ==3">
    	
    </s:elseif><s:elseif test="#finance.borrowWay ==4">
    	<img src="images/tubiao2.png" width="70" height="23" />
    </s:elseif><s:elseif test="#finance.borrowWay ==5">
    	<img src="images/tubiao1.png" width="70" height="23" />
    </s:elseif>
    </a></h2>
      <p>发布者：<s:property value="#finance.username" default="---"/>
       <s:if test="#finance.vipStatus ==2"></s:if>
      </p>
    </td>
    <td align="center" class="lilv">￥
    <s:property value="#finance.borrowAmount" default="---"/>
    <br />
      <s:property value="#finance.annualRate" default="---"/>% <span style="margin-left:5px;"><s:property value="#finance.deadline" default="---"/>
      <s:if test="%{#finance.isDayThe ==1}">个月</s:if><s:else>天</s:else>
      </span><br /></td>
    <td align="center" class="xinyong">
    <img src="images/ico_<s:property value="#finance.credit"/>.jpg" title="<s:property value="#finance.creditrating" default="---"/>分" width="33" height="22" /><br />
        奖励：<br/><s:if test="%{#finance.excitationType ==1}">
                                无
        </s:if>
    <s:elseif test="%{#finance.excitationType ==2}">
                 固定金
    </s:elseif>
    <s:elseif test="%{#finance.excitationType ==3}">
             借款金比例
    </s:elseif>
    </td>
    <td align="left" class="jindu" >
    <!-- 
    <s:if test="%{#finance.borrowStatus ==4}">
      <img src="images/neiye1_636.jpg" width="97" height="31"/>&nbsp;
    </s:if>
    <s:elseif test="%{#finance.borrowStatus ==5}">
      <img src="images/neiye1_637.jpg" width="97" height="31"/>&nbsp;
    </s:elseif>
    <s:else>
    </s:else> -->
    
    <div><br/>
    <div style="float:left;">投标进度：</div><br/>
    <div style="float:left;text-align:left;tevertical-align:bottom;margin-right:5px;margin-top:5px;padding:3px;background-image: url(images/index9_57.jpg);width:100px;height:10px;"><img src="images/index9_56.jpg" width="<s:property value="#finance.schedules" default="0"/>" height="10" style="display: block;"/>
    </div>
    <div style="float:left;"><span><s:property value="#finance.schedules" default="0"/>%</span></div>
    </div>
    
    </td>
  </tr>
  </s:iterator>
  </s:if>
  <s:else>
      <tr><td colspan="5" align="center">没有数据</td></tr>
  </s:else>
    </table>
    <s:if test="pageBean.page!=null || pageBean.page.size>0">
    <div class="page" >
                    <p>
                       <shove:page url="finance.do" curPage="${pageBean.pageNum}" showPageCount="7" pageSize="${pageBean.pageSize }" theme="number" totalCount="${pageBean.totalNum}">
					        <s:param name="m">${m}</s:param>
					        <s:param name="title">${title}</s:param>
					        <s:param name="paymentMode">${paymentMode}</s:param>
					        <s:param name="purpose">${purpose}</s:param>
					        <s:param name="raiseTerm">${raiseTerm}</s:param>
					        <s:param name="reward">${reward}</s:param>
					        <s:param name="arStart">${arStart}</s:param>
					        <s:param name="arEnd">${arEnd}</s:param>
					        <s:param name="type">${type}</s:param>
					   </shove:page>
                    </p>
                </div>    
    
    </s:if>
<script type="text/javascript">
<!--
	$("span#tit").each(function(){
		if($(this).text().length > 6){
			$(this).text($(this).text().substring(0,6)+"..");
		}
	})


    $("#jumpPage").attr("href","javascript:void(null)");
    $("#curPageText").attr("class","cpage");
	$("#jumpPage").click(function(){
	     var curPage = $("#curPageText").val();
	    
		 if(isNaN(curPage)){
			alert("输入格式不正确!");
			return;
		 }
    window.location.href="finance.do?curPage="+curPage+"&pageSize=${pageBean.pageSize}";
	});
//-->
</script>