<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp"%>
<s:iterator value="pageBean.page" var="bean" status="s" >
        <dd>
        <span>${bean.invest_number }</span><span>${bean.investTime }</span><span>${bean.whbj }</span>
         <s:if test="#bean.borrowStatus!=4"><span>--</span></s:if> 
	    <s:elseif
			test="#bean.debtStatus==null||#bean.debtStatus ==4||#bean.debtStatus ==5||#bean.debtStatus ==6||#bean.debtStatus ==7 || (#bean.debtStatus==3 && #bean.currentUser == #bean.auctionerId)">
			<span style="padding-left: 0;cursor: pointer;color: #333;"  data-xx="${bean.whbj }" onclick="window.location.href='webapp-invest-invoke.do?borrowId=${bean.borrowId}&blanceP=${bean.realAmount }&debtLimit=${bean.deadline-hasDeadline}&investId=${bean.investId}&anna=${bean.annualRate}&num=${bean.num }&whbj=${bean.whbj }&annualRate=${bean.annualRate }&debtId=${bean.debtId}'">
				转让</span>
		</s:elseif>
		<s:elseif test="#bean.debtStatus==1">
			<span style="padding-left: 0;cursor: pointer;color: #333;" onclick="window.location.href='cancelApplyDebt.do?debtId=${debtId}'">撤回</span>
		</s:elseif> 
		<s:elseif test="#bean.debtStatus==2">
			<span style="padding-left: 0;color: #333;">转让中</span>
		</s:elseif> <s:elseif test="#bean.debtStatus==3 && #bean.currentUser == #bean.alienatorId">
			<span style="padding-left: 0;color: #333;">已转让</span>
		</s:elseif>
		</dd>
</s:iterator>
