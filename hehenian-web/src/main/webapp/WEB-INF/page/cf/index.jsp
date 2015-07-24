<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<jsp:include page="/include/cf-head.jsp"></jsp:include>
</head>
<body>
<jsp:include page="/include/cf-top.jsp"></jsp:include>
<div class="s_sur_ix main el_container" style=" overflow:hidden; margin-bottom:20px;">
   <div style=" margin:10px 0px;">
       <a href="/hhn_web/termFinance.do" target="_blank">
           <img src="/images/ad.jpg" width="974" height="308"  alt=""/>
       </a>
   </div>
    <!--左侧-->
    <div class=" nr_left"> 
          <!--筛选栏-->
          <!-- 
          <div class="search_all">
        <div class="search_lx">
              <ul>
            <li>借款类型</li>
            <li>借款时间</li>
            <li>借款金额</li>
          </ul>
            </div>
        <div class="search">
              <ul  style=" width:100%">
            <li class="ok" data-value=""><a href="#">全部</a></li>
            <li class="purpose ${param.purpose ==1?'ok':''}" data-value="1"><a href="#">薪金贷</a></li>
            <li class="purpose ${param.purpose ==2?'ok':''}" data-value="2"><a href="#">业主贷</a></li>
            <li class="purpose ${param.purpose ==3?'ok':''}" data-value="3"><a href="#" >生意贷</a></li>
          </ul>
              <ul  style=" width:100%">
            <li class="ok" data-value=""><a href="#">全部</a></li>
            <li class="deadline ${param.deadline ==6?'ok':''}" data-value="6"><a href="#">6个月</a></li>
            <li class="deadline ${param.deadline ==12?'ok':''}" data-value="12"><a href="#">12个月</a></li>
            <li class="deadline ${param.deadline ==18?'ok':''}" data-value="18"><a href="#">18个月</a></li>
            <li class="deadline ${param.deadline ==24?'ok':''}" data-value="24"><a href="#">24个月</a></li>
            <li class="deadline ${param.deadline ==36?'ok':''}" data-value="36"><a href="#">36个月</a></li>
          </ul>
              <ul  style=" width:100%">
            <li class="ok" data-start="" data-end=""><a href="#">全部</a></li>
            <li class="borrowAmount ${param.arStart ==0?'ok':''}" data-start="0" data-end="20000"><a href="#">2万以下</a></li>
            <li class="borrowAmount ${param.arStart ==20000?'ok':''}" data-start="20000" data-end="50000"><a href="#">2-5万</a></li>
            <li class="borrowAmount ${param.arStart ==50000?'ok':''}" data-start="50000" data-end="100000"><a href="#">5-10万</a></li>
            <li class="borrowAmount ${param.arStart ==100000?'ok':''}" data-start="100000" data-end="200000"><a href="#">10-20万</a></li>
            <li class="borrowAmount ${param.arStart ==200000?'ok':''}" data-start="200000" data-end=""><a href="#">20万以上</a></li>
          </ul>
            </div>
      </div>
       -->
       <s:iterator value="pageBean.page" var="finance">
          <div class="left_nr">
        <div class="left_nr_pic"><img src="/color/images/cshzx.png" width="24" height="76"  alt=""/></div>
        <div class="left_nr_xq">
              <ul>
            <li style=" width:100%; border-bottom:0px; font-size:16px; color:#F60; font-weight:bold;"><s:property value="#finance.borrowTitle" default="---"/></li>
            <li style=" width:100%; line-height:32px; color:#999">编号：${finance.number }</li>
          </ul>
              <ul style=" line-height:26px;">
            <li>借款金额： <s:property value="#finance.borrowAmount" default="0" /> 元</li>
            <li>借款期限：<s:property value="#finance.deadline" default="0" />个月</li>
            <li>年利率：<s:property value="#finance.annualRate" default="0" /> % </li>
            <li>还款方式： <s:if test="%{#finance.isDayThe ==2}">到期还本付息</s:if>
            <s:elseif test="%{#finance.paymentMode == 1}">按月分期还款</s:elseif>
            <s:elseif test="%{#finance.paymentMode == 2}">按月付息,到期还本</s:elseif>
            <s:elseif test="%{#finance.paymentMode == 3}">秒还</s:elseif>
            <s:elseif test="%{#finance.paymentMode == 4}">一次性还款</s:elseif></li>
            <li>已投人数： <s:property value="#finance.investNum1" default="0" /> 人</li>
            <li>&nbsp;</li>
          </ul>
            </div>
        <div class="left_nr_jd">
              <div class="bottm">
            <s:if test="%{#finance.borrowStatus == 2}">
            <input type="button" onclick="window.location.href='cf-financeDetail.do?id=${finance.id}'" value="立即投标" />
          </s:if>
          <s:elseif test="%{#finance.borrowStatus == 3}">
            <input type="button" value="满标" onclick="window.location.href='cf-financeDetail.do?id=${finance.id}'" style="background: url(/images/pic_all.png) -174px -356px no-repeat ;cursor:default;" />
          </s:elseif>
          <s:elseif test="%{#finance.borrowStatus == 4}">
            <s:if test="%{#finance.borrowShow == 2}">
              <input type="button" value="回购中" style="background: #ccc;cursor:default;" />
            </s:if>
            <s:else>
              <input type="button" onclick="window.location.href='cf-financeDetail.do?id=${finance.id}'" value="还款中" style="background: url(/images/pic_all.png) -174px -300px no-repeat" />
            </s:else>
          </s:elseif>
          <s:elseif test="%{#finance.borrowStatus == 5}">
            <input type="button" value="已还完" style="background: #ccc;cursor:default;" />
          </s:elseif>
          </div>
              <div style=" float:right;">
            <div style=" float:left; line-height:28px; text-align:left; ">借款进度：</div>
            <div class="index_jd">
                  <div style=" font-size:0px; line-height:0px;width: <s:property value="#finance.schedules" default="0"/>%;height:12px; background:url(/images/pic_all.png) 0 -67px no-repeat; "></div>
                </div>
          </div>
              <div  style=" line-height:32px;text-align:right; margin-top:35px;" >已成功借款：<font color="#FF6600"> <s:property value="#finance.hasInvestAmount" default="---" /> </font>元</div>
              <div  style="text-align:right; width:100%" >剩余借款金额：<font color="#006600">  <s:property value="#finance.investNum" default="---" /> </font>元</div>
            </div>
      </div>
          </s:iterator>
          
          
        </div>
    <!--右侧-->
     <jsp:include page="/include/cf-right.jsp"></jsp:include>
  </div>
  <jsp:include page="/include/cf-footer.jsp"></jsp:include>
<script type="text/javascript">
   $.get("/updateUserUsableSum.do?_="+new Date().getTime());
</script>
</body>
</html>