<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp"%>

         <s:iterator value="#request.investRecords" var="bean">
            <div class="toggle-2"><a href="#" class="deploy-toggle-2" style="background-color:#fff;border-top:0px;border-bottom:#ccc 1px solid; color:#333">借款标题：${bean.borrowTitle } </a>
              <div class="toggle-content">
                <ul>
                  <li>债权编号：${bean.invest_number } </li>
                  <li> 交易日期 ：${bean.investTime }</li>
                  <li>标题：${bean.borrowTitle } </li>
                  <li>年利率：${bean.annualRate }% </li>
                  <li>债权期限：${bean.deadline }个月 </li>
                  <li>投资金额：${bean.investAmount }元 </li>
                  <li>已收金额  ：${bean.hasPI } </li>
                  <li>待收金额  ：${bean.dsje } </li>
                </ul>
              </div>
            </div>
          </s:iterator>
       