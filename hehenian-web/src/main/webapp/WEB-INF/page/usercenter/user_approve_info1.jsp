<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp"%>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<th colspan="8">信用等级及信用对应分数</th>
	</tr>
	<tr>
		<td align="center">等级</td>
<%--		<td align="center">AA</td>--%>
		<td align="center">A</td>
		<td align="center">B</td>
		<td align="center">C</td>
		<td align="center">D</td>
		<td align="center">E</td>
		<td align="center">HR</td>
	</tr>
	<tr>
		<td align="center">分数</td>
<%--		<td align="center">100以上</td>--%>
		<td align="center">99以上</td>
		<td align="center">89-80</td>
		<td align="center">79-70</td>
		<td align="center">69-50</td>
		<td align="center">49-30</td>
		<td align="center">30以下</td>
	</tr>
	<tr>
		<td align="center">标志</td>
<%--		<td align="center"><img src="images/ico_15.jpg" width="34" height="22" /></td>--%>
<%--		<td align="center"><img src="images/ico_13.jpg" width="34" height="22" /></td>--%>
		<td align="center"><img src="images/ico_13.jpg" width="34" height="22" /></td>
		<td align="center"><img src="images/ico_11.jpg" width="34" height="22" /></td>
		<td align="center"><img src="images/ico_09.jpg" width="34" height="22" /></td>
		<td align="center"><img src="images/ico_07.jpg" width="34" height="22" /></td>
		<td align="center"><img src="images/ico_05.jpg" width="34" height="22" /></td>
		<td align="center"><img src="images/ico_03.jpg" width="34" height="22" /></td>
	</tr>
</table>

<div class="biaoge">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
<%--			<th colspan="4">信用总分：${userMsg.creditrating}分&nbsp;<img src="images/ico_${criditMap.credit}.jpg" title="${userMsg.creditrating}分" width="33" height="22" />--%>
<%--			</th>--%>
				<th colspan="4">信用总分：${userMsg.creditrating}分&nbsp;<img src="images/ico_13.jpg" title="${userMsg.creditrating}分" width="33" height="22" />
			</th>
		</tr>
		<tr>
			<td align="center">&nbsp;</td>
			<td align="center">项目</td>
			<td align="center">状态</td>
			<td align="center">信用分数</td>
		</tr>
		<tr>
			<td align="center" rowspan="2">基本信息</td>
			<td align="center">个人详细信息</td>
			<td align="center"><s:if test="#request.userMsg.personStatus==1">待审核</s:if> <s:elseif test="#request.userMsg.personStatus==2">
					<a href="owerInformationInit.do">不通过</a>
				</s:elseif> <s:elseif test="#request.userMsg.personStatus==3">
					通过
				</s:elseif> <s:else>
					<a href="owerInformationInit.do">未填写</a>
				</s:else>
			</td>
			<td align="center">0</td>
		</tr>
		<tr>
			<td align="center">工作信息</td>
			<td align="center"><s:if test="#request.userMsg.workStatus==1">待审核</s:if> <s:elseif test="#request.userMsg.workStatus==2">
					<a href="querWorkData.do">不通过</a>
				</s:elseif> <s:elseif test="#request.userMsg.workStatus==3">
				通过
				</s:elseif> <s:else>
					<a href="querWorkData.do">未填写</a>
				</s:else>
			</td>
			<td align="center">0</td>
		</tr>

		<tr>
			<td rowspan="5" align="center">必要信用认证</td>
			<td align="center">身份认证</td>
			<td align="center"><s:if test="#request.userMsg.identityStatus==3">
         通过
      </s:if> <s:elseif test="#request.userMsg.identityStatus==2">
					<a href="userPassData.do">不通过</a>
				</s:elseif> <s:elseif test="#request.userMsg.identityStatus==1">待审核</s:elseif> <s:elseif test="#request.userMsg.identityStatus==3">通过</s:elseif> <s:else>
					<a href="userPassData.do">待上传</a>
				</s:else>
			</td>
			<td align="center">${userMsg.identiyCriditing }</td>
		</tr>
		<tr>
			<td align="center">工作认证</td>
			<td align="center"><s:if test="#request.userMsg.worksStatus==3">
         通过
      </s:if> <s:elseif test="#request.userMsg.worksStatus==2">
					<a href="userPassData.do">不通过</a>
				</s:elseif> <s:elseif test="#request.userMsg.worksStatus==1">待审核</s:elseif> <s:else>
					<a href="userPassData.do">待上传</a>
				</s:else>
			</td>
			<td align="center">${userMsg.workCriditing }</td>
		</tr>
		<tr>
			<td align="center">居住地认证</td>
			<td align="center"><s:if test="#request.userMsg.addressStatus==3">
         通过
      </s:if> <s:elseif test="#request.userMsg.addressStatus==2">
					<a href="userPassData.do">不通过</a>
				</s:elseif> <s:elseif test="#request.userMsg.addressStatus==1">待审核</s:elseif> <s:else>
					<a href="userPassData.do">待上传</a>
				</s:else>
			</td>
			<td align="center">${userMsg.addressCriditing }</td>
		</tr>
		<tr>
			<td align="center">信用报告认证</td>
			<td align="center"><s:if test="#request.userMsg.creditStatus==3">
         通过
      </s:if> <s:elseif test="#request.userMsg.creditStatus==2">
					<a href="userPassData.do">不通过</a>
				</s:elseif> <s:elseif test="#request.userMsg.creditStatus==1">待审核</s:elseif> <s:else>
					<a href="userPassData.do">待上传</a>
				</s:else>
			</td>
			<td align="center">${userMsg.creditCriditing }</td>
		</tr>
		<tr>
			<td align="center">收入认证</td>
			<td align="center"><s:if test="#request.userMsg.incomeStatus==3">
         通过
      </s:if> <s:elseif test="#request.userMsg.incomeStatus==2">
					<a href="userPassData.do">不通过</a>
				</s:elseif> <s:elseif test="#request.userMsg.incomeStatus==1">待审核</s:elseif> <s:else>
					<a href="userPassData.do">待上传</a>
				</s:else>
			</td>
			<td align="center">${userMsg.incomeCriditing }</td>
		</tr>


		<tr>
			<td rowspan="<s:property value="#request.map.size" />" align="center">可选认证</td>
			<s:if test="#request.map==null || #request.map.size==0">
				<td colspan="3">暂无数据</td>
			</s:if>
			<s:else>
				<s:iterator value="#request.map" var="bean">
					<td align="center">${bean.materName }</td>
					<td align="center"><s:if test="#bean.statu==3">
        				 通过
     				 </s:if> <s:elseif test="#bean.statu==2">
							<a href="userPassData.do">不通过</a>
						</s:elseif> <s:elseif test="#bean.statu==1">待审核</s:elseif> <s:else>
							<a href="userPassData.do">待上传</a>
						</s:else></td>
					<td align="center">${bean.criditing }</td>
		</tr>
		<tr>
			</s:iterator>
			</s:else>
		</tr>

	</table>
</div>