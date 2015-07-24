   <%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
   <%@include file="/include/taglib.jsp" %>
   <div class="biaoge"> 
       <table width="100%" border="0" cellspacing="0" cellpadding="0">
       <tr>
		    <th>真实姓名<br /></th>
		    <th>开户行<br /></th>
		    <th>支行<br /></th>
		    <th>卡号<br /></th>
		    <th>状态<br /></th>
		    <th>操作<br /></th>
		</tr>
		<s:if test="#request.lists!=null && #request.lists.size>0">
		       <s:iterator value="#request.lists" id="queryBankInfoInit" var="bean">
			    <tr>
			      <td align="center">${bean.cardUserName}<br /></td>
			      <td align="center">${bean.bankName}<br /></td>
			      <td align="center">${bean.branchBankName}<br /></td>
			      <td align="center">${bean.cardNo}<br /></td>
			      <s:if test="#bean.modifiedCardStatus==2">
				      <td align="center">正在变更<br /></td>
				      <td align="center"><a href="javascript:changeBankCancel(${bean.id})">取消<br /></a></td>
			      </s:if>
			      <s:else>
			         <s:if test="#bean.cardStatus==1">
				      <td align="center">已绑定<br /></td>
				      <td align="center"><a href="javascript:changeBankInfos(${bean.id})">申请变更<br /></a></td>
			          </s:if>
			         <s:else>
			         <s:if test="#bean.cardStatus==3">
			              <td align="center">失败<br /></td>
					      <td align="center"><a href="javascript:del(${bean.id})">取消<br /></a></td>
			         </s:if>
			         <s:else>
				          <td align="center">审核中<br /></td>
					      <td align="center"><a href="javascript:del(${bean.id})">取消<br /></a></td>
				      </s:else>
				      </s:else>
			      </s:else>
			    </tr>
			 </s:iterator>
		</s:if>
		<s:else>
		    <tr><td align="center" colspan="6">暂无信息</td></tr>
		</s:else>
        </table>
      </div>
<script type="text/javascript" src="script/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="script/jquery.shove-1.0.js"></script>
<script type="text/javascript" src="script/jbox/jquery.jBox-2.3.min.js"></script>
<script type="text/javascript" src="script/jbox/jquery.jBox-zh-CN.js"></script> 