<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
  <s:if test="#request.mapList.size>0">
    <s:iterator value="#request.mapList" var="finance">
    <li>
            <div class="list_tx"><a href="financeDetail.do?id=${finance.id}"><img src="${finance.imgPath}" width="80" height="79" /></a></div>
            <div class="list_txt">
              <table width="510" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <th colspan="3" align="left"><a href="financeDetail.do?id=${finance.id}" title="<s:property value="#finance.borrowTitle" default="---"/>"><s:property value="#finance.borrowTitle" default="---"/></a></th>
                </tr>
                <tr>
                  <td width="159">借款金额：<span >￥<s:property value="#finance.borrowAmount" default="---"/></span></td>
                  <td width="218">年利率：<span >￥<s:property value="#finance.annualRate" default="---"/>%</span></td>
                  <td width="125">借款期限：<span ><s:property value="#finance.deadline" default="---"/>
                  <s:if test="%{#finance.isDayThe ==1}">个月</s:if><s:else>天</s:else>
                  </span></td>
                </tr>
                <tr>
                  <td>信用等级：<img src="images/ico_13.jpg" title="<s:property value="#finance.credit" default="0"/>分" width="33" height="22" /></td>
                  <td>
                  <div>
    <div style="float:left;">借款进度：</div>
    <div style="float:left;text-align:left;tevertical-align:bottom;margin-right:5px;margin-top:5px;padding:3px;background-image: url(images/index9_57.jpg);width:100px;height:10px;"><img src="images/index9_56.jpg" width="<s:property value="#finance.schedules" default="0"/>" height="10"/>
    </div>
    <div style="float:left;"><span><s:property value="#finance.schedules" default="0"/>%</span></div>
    </div>
    </td>           
                  <td>还需：<span ><s:property value="#finance.investNum" default="---"/>元</span></td>
                </tr>
              </table>
            </div>
            <div class="clear"></div>
          </li>
  </s:iterator>
  </s:if>
  <s:else>
     <li style="text-align: center;">没有数据</li>
  </s:else>