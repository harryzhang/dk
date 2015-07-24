<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
        <div style=" border-bottom:1px solid #e5e5e5; padding-bottom:10px; margin-bottom:15px;"><font style=" color:#F60; font-size:20px;">投资记录 </font></div>
        
         <div style=" width:100%; margin:0px auto; line-height:24px;">
                        <table width="100%" border="0" >
                            <tr height="30">
                                <td ><span>借出本息总额：</span><strong>￥<s:if test="#request.map.realAmount == ''">0.00</s:if> <s:else>${request.map.realAmount }</s:else></strong></td>
                                <td><span>应收本息总额：</span><strong>￥<s:if test="#request.map.shouldGetAmount == ''">0.00</s:if> <s:else>${request.map.shouldGetAmount }</s:else></strong></td>
                            </tr>
                            <tr height="30">
                                <td><span>已收本息总额：</span><strong>￥<s:if test="#request.map.hasGetAmount == ''">0.00</s:if> <s:else>${request.map.hasGetAmount }</s:else></strong></td>
                                <td><span>未收本息总额：</span><strong>￥<s:if test="#request.map.hasGetAmount == ''">0.00</s:if> <s:else>${request.map.shouldGetAmount-request.map.hasGetAmount }</s:else></strong></td>
                            </tr>
                        </table>
       <div class="tzjl_cgtz_box">
                    	<h2><span>债权明细</span></h2>
                        <table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#e5e5e5">
                            
                            <tr align="center" height="30">
                                <!-- <td bgcolor="#ffffff" width="30">全选</td> -->
                                <td bgcolor="#ffffff" width="60">债权编号</td>
                                <td bgcolor="#ffffff" width="60">交易日期</td>
                                <td bgcolor="#ffffff" width="50">借款者</td>
                                <td bgcolor="#ffffff" width="70">标题</td>
                                <td bgcolor="#ffffff" width="60">年利率</td>
                                <td bgcolor="#ffffff" width="70">债权期限</td>
                                <td bgcolor="#ffffff" width="60">投资金额</td>
                                <td bgcolor="#ffffff" width="60">已收金额</td>
                                <td bgcolor="#ffffff" width="60">待收金额</td>
                                <td bgcolor="#ffffff" width="60">回收阶段</td>
                               <!--  <td bgcolor="#ffffff" width="60">债权协议</td> -->
                                <!-- <td bgcolor="#ffffff" width="50">操作</td> -->
                            </tr>
                            <s:if test="pageBean.page!=null && pageBean.page.size>0">
								<s:iterator value="pageBean.page" var="bean">
                            <tr align="center" height="30">
                                <%-- <td bgcolor="#ffffff"><input type="checkbox" name="check" class="downloadId" value="${bean.id }" /></td> --%>
                                <td bgcolor="#ffffff">${bean.id }</td>
                                <td bgcolor="#ffffff">${bean.investTime }</td>
                                <td bgcolor="#ffffff">${bean.username }</td>
                                <td bgcolor="#ffffff"><a href="cf-finance.do?${bean.borrowId }">${bean.borrowTitle }</a></td>
                                <td bgcolor="#ffffff">${bean.annualRate }%</td>
                                <td bgcolor="#ffffff">${bean.debtLimit }</td>
                                <td bgcolor="#ffffff">${bean.realAmount }</td>
                                <td bgcolor="#ffffff">${bean.hasPI }</td>
                                <td bgcolor="#ffffff">${bean.notPI }</td>
                                <td bgcolor="#ffffff">${bean.hasDeadline }/${bean.deadline }</td>
                                <!-- <td bgcolor="#ffffff"><b class="tzjl_fwxy">查看</b></td> -->
                               <!--  <td bgcolor="#ffffff"><u class="tzjl_fwxy1">转让</u></td> -->
                            </tr>
                            </s:iterator>
                            </s:if>
                            <s:else>
                            	<tr align="center" height="30" colspan="13">暂无数据</tr>
                            </s:else>
                        </table>
            
                    </div>
       
       </div>
          
