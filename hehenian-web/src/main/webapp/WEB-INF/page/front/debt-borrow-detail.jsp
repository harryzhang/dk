<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script type="text/javascript" src="script/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="script/jquery.shove-1.0.js"></script>
<script src="script/add_all.js" type="text/javascript"></script>
<script src="script/MSClass.js" type="text/javascript"></script>
<script type="text/javascript">
	$(function() {
		$(".nd_bdxq_jkxq_three_ul li:first").addClass("jdd");
	      param['paramMap.id']=$('#idStr').val();
	      $.shovePost('financeRepay.do',param,function(data){
	        $('#contentRepay').html(data);	       
		  });
	      param['paramMap.id']=$('#idStr').val();
	      $.shovePost('financeCollection.do',param,function(data){
	        $('#contentColl').html(data);	       
		  });

		$(".header_two_right_ul li").hover(function() {
			$(this).find("ul").show();
		}, function() {
			$(this).find("ul").hide();
		})
		<%--标的详情XXK--%>
		$(".nd_bdxq_jkxq_three_ul li").click(function() {
			var s = $(".nd_bdxq_jkxq_three_ul li").index(this);
			$(".nd_bdxq_jkxq_three_ul li").removeClass("jdd");//失去焦点
			$(this).addClass("jdd");//设为焦点
			$(".nd_bdxq_jkxq_three_a").hide();
			$(".nd_bdxq_jkxq_three_a").eq(s).show();
		})
		$(".jbcr_a").click(function() {
			$(".jb_taczq").show();
		})
		$(".jbcr_xxa").click(function() {
			$(".jb_taczq").hide();
		})
		$(".l_jkxq_ljtb").click(function() {
			if ($(".l_jkxq_ljtb_dh").is(":hidden")) {
				$(".l_jkxq_ljtb_dh").show();
			} else
				($(function() {
					$(".l_jkxq_ljtb_dh").hide();
				}))
		})
	})
</script>
<div class="nd_bdxq_jkxq_three">
	<ul class="nd_bdxq_jkxq_three_ul">
		<li>相关信息</li>
		<li>审核记录</li>
		<!-- <li>还款详情</li>
		<li>催收记录</li>
		<li>风险评定</li> -->
		<li>投资记录</li>
	</ul>
	
	<!-- 相关信息div -->
	<div class="nd_bdxq_jkxq_three_a" style="display:block;">
		<div class="nd_bdxq_jkxq_next_max" style="border:none;">
			<table width="1060" border="0">

				<tr height="10"></tr>
				<tr height="30">
					<td colspan="5">
						<div class="l_bdxq_zs" style=" width:100%">
							<p>
								以下基本信息资料，经用户同意披露。其中<span class="red"> 红色字体 </span>的信息，为通过网站审核之项目。
							</p>
							<p>
								<span class="red">审核意见：网站将以客观、公正的原则，最大程度地核实借入者信息的真实性，但不保证审核信息100%真实。如果借入者长期逾期，其提供的信息将被公布。</span>
							</p>
						</div></td>
				</tr>
				<tr height="35">
					<td>性别：
						<s:if test="#request.borrowUserMap.auditperson == 3">
						<span class="red">${borrowUserMap.sex}</span>
						</s:if>
						<s:else>${borrowUserMap.sex}</s:else>
					</td>
					<td>年龄：
							<s:if test="#request.borrowUserMap.auditperson == 3">
								<span class="red">${borrowUserMap.age}</span>
							</s:if>
							<s:else>${borrowUserMap.age}</s:else>
					</td>
					<td>学历：<s:if test="#request.borrowUserMap.auditwork == 3">
										<span class="red">${borrowUserMap.highestEdu}</span>
									</s:if>
									<s:else>${borrowUserMap.highestEdu}</s:else></td>
					<td>工作收入：10000以上 </td>
					<td>现单位工作时间：5年</td>
				</tr>
				<tr>
					<td>公司行业：<s:if test="#request.borrowUserMap.auditwork == 3">
										<span class="red">${borrowUserMap.companyLine}</span>
									</s:if>
									<s:else>${borrowUserMap.companyLine}</s:else>
					</td>
					<td>公司规模：<s:if test="#request.borrowUserMap.auditwork == 3">
										<span class="red">${borrowUserMap.companyScale}</span>
									</s:if>
									<s:else>${borrowUserMap.companyScale}</s:else>
					</td>
					<td>职位：<s:if test="#request.borrowUserMap.auditwork == 3">
										<span class="red">${borrowUserMap.job}</span>
									</s:if>
									<s:else>${borrowUserMap.job}</s:else>
					</td>
					<td></td>
					<td></td>
				</tr>
				<tr height="35">
					<td>有无购房：<s:if test="#request.borrowUserMap.auditperson == 3">
										<span class="red">${borrowUserMap.hasHourse}</span>
									</s:if>
									<s:else>${borrowUserMap.hasHourse}</s:else></td>
					<td>有无购车：
									<s:if test="#request.borrowUserMap.auditperson == 3">
										<span class="red">${borrowUserMap.hasCar}</span>
									</s:if>
									<s:else>${borrowUserMap.hasCar}</s:else></td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td>有无房贷：<s:if test="#request.borrowUserMap.auditperson == 3">
										<span class="red">${borrowUserMap.hasHousrseLoan}</span>
									</s:if>
									<s:else>${borrowUserMap.hasHousrseLoan}</s:else></td>
					<td>有无车贷：<s:if test="#request.borrowUserMap.auditperson == 3">
										<span class="red">${borrowUserMap.hasCarLoan}</span>
									</s:if>
									<s:else>${borrowUserMap.hasCarLoan}</s:else></td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
				<tr height="20"></tr>
				<tr height="35">
					<td><b>网站借贷记录</b>
					</td>
					<td></td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
				<tr height="35">
					<td>发布借款标：<s:if test="#request.borrowRecordMap.publishBorrow !=''">${borrowRecordMap.publishBorrow}</s:if>
									<s:else>0</s:else></td>
					<td>成功借款笔数：<s:if test="#request.borrowRecordMap.successBorrow !=''">${borrowRecordMap.successBorrow}</s:if>
									<s:else>0</s:else></td>
					<td>还清笔数：<s:if test="#request.borrowRecordMap.repayBorrow !=''">${borrowRecordMap.repayBorrow}</s:if>
									<s:else>0</s:else></td>
					<td>逾期次数：0<%-- <s:if test="#request.borrowRecordMap.hasFICount !=''">${borrowRecordMap.hasFICount}</s:if>
									<s:else>0</s:else> --%></td>
					<td>严重逾期次数：0<%-- <s:if test="#request.borrowRecordMap.badFICount !=''">${borrowRecordMap.badFICount}</s:if>
									<s:else>0</s:else> --%></td>
				</tr>
				<tr height="35">
					<td>共借入：<s:if test="#request.borrowRecordMap.borrowAmount !=''">${borrowRecordMap.borrowAmount}</s:if>
									<s:else>0</s:else></td>
					<%-- <td>待还金额：<s:if test="#request.borrowRecordMap.forRepayPI !=''">${borrowRecordMap.forRepayPI}</s:if>
									<s:else>0</s:else></td> --%>
					<td>逾期金额：0<%-- <s:if test="#request.borrowRecordMap.hasI !=''">${borrowRecordMap.hasI}</s:if>
									<s:else>0</s:else> --%></td>
					<td>共借出：<s:if test="#request.borrowRecordMap.investAmount !=''">${borrowRecordMap.investAmount}</s:if>
									<s:else>0</s:else></td>
					<td>待收金额：<s:if test="#request.borrowRecordMap.forPI !=''">${borrowRecordMap.forPI}</s:if>
									<s:else>0</s:else></td>
				</tr>
			</table>
		</div>
	</div>

	<!-- 审核记录div -->
	<div class="nd_bdxq_jkxq_three_a">
		<div class="nd_bdxq_jkxq_three_a_1">
			<p>本站以公正、客观为基本原则，以客户资金安全为服务宗旨，尽最大限度的保证借款者的真实信息。如遇还款问题，由民民贷风险备用金全额垫付本金及利息。以达到投资者投资无风险的最终目标。</p>
		</div>
		<table width="680" border="0" cellpadding="0" cellspacing="1"
			bgcolor="#e5e5e5" style="margin-left:10px; margin-bottom:15px;">
<s:if test="%{#request.list !=null && #request.list.size()>0}">
    <tbody><tr height="32">
        <td width="310" align="center" bgcolor="#ffffff">审核项目</td>
        <td width="190" align="center" bgcolor="#ffffff">状态</td>
        <td width="180" align="center" bgcolor="#ffffff">通过时间</td>
    </tr>

    <tr height="30">
        <td bgcolor="#ffffff" align="center">
            身份认证</td>
        <td bgcolor="#ffffff" align="center">
            <img src="images/neiye2_44.jpg" width="14" height="15">
        </td>
        <td bgcolor="#ffffff" align="center">
                ${list[0].passTime}</td>
    </tr>
    <tr height="30">
        <td bgcolor="#ffffff" align="center">
            工作认证</td>
        <td bgcolor="#ffffff" align="center">
            <img src="images/neiye2_44.jpg" width="14" height="15">
        </td>
        <td bgcolor="#ffffff" align="center">
                ${list[0].passTime}</td>
    </tr>
    <tr height="30">
        <td bgcolor="#ffffff" align="center">
            居住地认证</td>
        <td bgcolor="#ffffff" align="center">
            <img src="images/neiye2_44.jpg" width="14" height="15">
        </td>
        <td bgcolor="#ffffff" align="center">
                ${list[0].passTime}</td>
    </tr>
    <tr height="30">
        <td bgcolor="#ffffff" align="center">
            信用报告</td>
        <td bgcolor="#ffffff" align="center">
            <img src="images/neiye2_44.jpg" width="14" height="15">
        </td>
        <td bgcolor="#ffffff" align="center">
                ${list[0].passTime}</td>
    </tr>
    <tr height="30">
        <td bgcolor="#ffffff" align="center">
            收入认证</td>
        <td bgcolor="#ffffff" align="center">
            <img src="images/neiye2_44.jpg" width="14" height="15">
        </td>
        <td bgcolor="#ffffff" align="center">
                ${list[0].passTime}</td>
    </tr>
    <tr height="30">
        <td bgcolor="#ffffff" align="center">
            房产证</td>
        <td bgcolor="#ffffff" align="center">
            <img src="images/neiye2_44.jpg" width="14" height="15">
        </td>
        <td bgcolor="#ffffff" align="center">
                ${list[0].passTime}</td>
    </tr>
    <tr height="30">
        <td bgcolor="#ffffff" align="center">
            房屋租赁合同</td>
        <td bgcolor="#ffffff" align="center">
            <img src="images/neiye2_44.jpg" width="14" height="15">
        </td>
        <td bgcolor="#ffffff" align="center">
                ${list[0].passTime}</td>
    </tr>
    <tr height="30">
        <td bgcolor="#ffffff" align="center">
            水电单据</td>
        <td bgcolor="#ffffff" align="center">
            <img src="images/neiye2_44.jpg" width="14" height="15">
        </td>
        <td bgcolor="#ffffff" align="center">
                ${list[0].passTime}</td>
    </tr>
    <tr height="30">
        <td bgcolor="#ffffff" align="center">
            工作证明</td>
        <td bgcolor="#ffffff" align="center">
            <img src="images/neiye2_44.jpg" width="14" height="15">
        </td>
        <td bgcolor="#ffffff" align="center">
                ${list[0].passTime}</td>
    </tr>
    <tr height="30">
        <td bgcolor="#ffffff" align="center">
            银行流水</td>
        <td bgcolor="#ffffff" align="center">
            <img src="images/neiye2_44.jpg" width="14" height="15">
        </td>
        <td bgcolor="#ffffff" align="center">
                ${list[0].passTime}</td>
    </tr>
    <tr height="30">
        <td bgcolor="#ffffff" align="center">
            信用卡账单</td>
        <td bgcolor="#ffffff" align="center">
            <img src="images/neiye2_44.jpg" width="14" height="15">
        </td>
        <td bgcolor="#ffffff" align="center">
                ${list[0].passTime}</td>
    </tr>
    <tr height="30">
        <td bgcolor="#ffffff" align="center">
            车产证</td>
        <td bgcolor="#ffffff" align="center">
            <img src="images/neiye2_44.jpg" width="14" height="15">
        </td>
        <td bgcolor="#ffffff" align="center">
                ${list[0].passTime}</td>
    </tr>
    <tr height="30">
        <td bgcolor="#ffffff" align="center">
            社保</td>
        <td bgcolor="#ffffff" align="center">
            <img src="images/neiye2_44.jpg" width="14" height="15">
        </td>
        <td bgcolor="#ffffff" align="center">
                ${list[0].passTime}</td>
    </tr>
    <tr height="30">
        <td bgcolor="#ffffff" align="center">
            营业执照</td>
        <td bgcolor="#ffffff" align="center">
            <img src="images/neiye2_44.jpg" width="14" height="15">
        </td>
        <td bgcolor="#ffffff" align="center">
                ${list[0].passTime}</td>
    </tr>
    <tr height="30">
        <td bgcolor="#ffffff" align="center">
            租赁合同</td>
        <td bgcolor="#ffffff" align="center">
            <img src="images/neiye2_44.jpg" width="14" height="15">
        </td>
        <td bgcolor="#ffffff" align="center">
                ${list[0].passTime}</td>
    </tr>
    <tr height="30">
        <td bgcolor="#ffffff" align="center">
            其它资产证明</td>
        <td bgcolor="#ffffff" align="center">
            <img src="images/neiye2_44.jpg" width="14" height="15">
        </td>
        <td bgcolor="#ffffff" align="center">
                ${list[0].passTime}</td>
    </tr>
    <tr height="30">
        <td bgcolor="#ffffff" align="center">
            征信报告</td>
        <td bgcolor="#ffffff" align="center">
            <img src="images/neiye2_44.jpg" width="14" height="15">
        </td>
        <td bgcolor="#ffffff" align="center">
                ${list[0].passTime}</td>
    </tr>
    <tr height="32">
        <td bgcolor="#ffffff" align="center">银行流水清单</td>
        <td bgcolor="#ffffff" align="center"><img src="images/nd_sh_tg.png">
        </td>
        <td bgcolor="#ffffff" align="center">${list[0].passTime}</td>
    </tr>
    </tbody>
        </s:if>
<s:else>
    <td colspan="3" align="center">
        没有数据
    </td>
</s:else>
			<%--<tr height="32">
				<td width="310" align="center" bgcolor="#ffffff">审核项目</td>
				<td width="190" align="center" bgcolor="#ffffff">状态</td>
				<td width="180" align="center" bgcolor="#ffffff">通过时间</td>
			</tr>
			
			<s:if test="%{#request.list !=null && #request.list.size()>0}">
					<s:iterator value="#request.list" var="bean">
						<tr height="30">
							<td bgcolor="#ffffff" align="center">
								${bean.name}
							</td>
							<td bgcolor="#ffffff" align="center">
								<s:if test="#bean.auditStatus == 1">
                                  				审核中
     										 </s:if>
								<s:elseif test="#bean.auditStatus == 2">
                                			   审核失败
     										 </s:elseif>
								<s:elseif test="#bean.auditStatus == 3">
									<img src="images/neiye2_44.jpg" width="14" height="15" />
								</s:elseif>
								<s:else>
                                	等待资料上传
     							</s:else>
							</td>
							<td bgcolor="#ffffff" align="center">
								${bean.passTime}
							</td>
						</tr>
					</s:iterator>
				</s:if>
				<s:else>
					<td colspan="3" align="center">
						没有数据
					</td>
				</s:else>
			
			<tr height="32">
				<td bgcolor="#ffffff" align="center">银行流水清单</td>
				<td bgcolor="#ffffff" align="center"><img
					src="images/nd_sh_tg.png" />
				</td>
				<td bgcolor="#ffffff" align="center">2013-06-29</td>
			</tr>--%>
		</table>
	</div>
	
	<!-- 还款详情div -->
	<!-- <div class="nd_bdxq_jkxq_three_a" id="contentRepay"></div>
	<div class="cle"></div> -->
	
	<!-- 催收详情div -->
	<!-- <div class="nd_bdxq_jkxq_three_a" id="contentColl">	</div> -->
	
	<!-- 风险评定div -->
	<!-- <div class="nd_bdxq_jkxq_three_a">
		<div class="nd_bdxq_jkxq_three_a_p">风险评定信息，客户按需填写</div>
	</div> -->
	
	<!-- 投资记录div -->
	<div class="nd_bdxq_jkxq_three_a">
	
	
	
		<div class="nd_bdxq_jkxq_next_max">
			<table width="940" border="0">
				<tr>
					<td>目前总投标金额：<span class="red">￥${borrowDetailMap.hasInvestAmount}</span>
					</td>
					<td>剩余投标金额：<span class="red">￥${borrowDetailMap.investAmount}</span>
					</td>
					<td>剩余投标时间：<span class="red">0小时0分0秒</span>
					</td>
				</tr>
				<tr>
					<td colspan="3">
						<table width="920" border="0" cellpadding="0" cellspacing="1"
							bgcolor="#e5e5e5" style="margin-left:0px;">
							<tr height="30">
								<th bgcolor="#faf9f9" scope="col">投资人</th>
								<th bgcolor="#faf9f9" scope="col">投资金额</th>
								<th bgcolor="#faf9f9" scope="col">投资时间</th>
							</tr>
							
							
							<s:if test="%{#request.investList !=null && #request.investList.size()>0}">
								<s:iterator value="#request.investList" id="investList">
									<tr height="30">
										<td bgcolor="#ffffff" align="center">
												<s:property value="#investList.username" default="---" /> 
												<!--   creditedStatus==2 代表该用户在转让债权 --> 
												<s:if test="#investList.creditedStatus !=null && #investList.creditedStatus==2 ">
													<img src="images/zrico.jpg" width="30" height="16" />
												</s:if> 
										</td>
										<td bgcolor="#ffffff" align="center">￥<s:property
												value="#investList.investAmount" />
										</td>
										<td bgcolor="#ffffff" align="center"><s:property
												value="#investList.investTime" />
										</td>
									</tr>
								</s:iterator>
							</s:if>
							<s:else>
								<td colspan="3" align="center"  height="30">没有数据</td>
							</s:else>
							
						</table></td>
				</tr>
			</table>
			<div class="cle"></div>
			<div class="cle"></div>
		</div>
	</div>

</div>
<div class="cle"></div>