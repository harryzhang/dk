<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<jsp:include page="/include/head.jsp"></jsp:include>
<link id="skin" rel="stylesheet" href="css/jbox/Gray/jbox.css" />
<link href="css/user.css" rel="stylesheet" type="text/css" />
<script language="javascript" type="text/javascript"
	src="My97DatePicker/WdatePicker.js"></script>
</head>
<body>
	<%--<!-- 引用头部公共部分 -->--%>



	<jsp:include page="/include/top.jsp"></jsp:include>
	<div class="user-all">
		<div class="user-center">
			<div style="width: 1170px; margin: 0px auto; padding-bottom: 24px;">
				<img src="/images/v1/detail_logo_bg.jpg" width="1170" height="6"
					alt="" />
			</div>
			<div>
				<div class="u-left">
					<jsp:include page="/include/menu_userManage.jsp"></jsp:include>
				</div>
				<%-- <div class="u-right">
					<div class="user-right">
						<h2>基本资料</h2>

						<table width="100%" border="0">
							<tr height="5"></tr>
							<tr height="20">
								<td align="center" colspan="2"><span style="color: red;">身份信息直接影响交易操作，请务必如实填写</span>
								</td>
							</tr>
							<tr height="45">
								<td width="380" height="60" align="right"><strong>*</strong>真实姓名：</td>
								<td><s:if test='#session.user.usrCustId>0'> ${map.realName }
              <input type="hidden" name="paramMap.realName"
											id="realName" value="${map.realName}" />
									</s:if> <s:else>
										<input type="text" class="inp188" name="paramMap.realName"
											id="realName" value="${map.realName}" />
										<span style="color: red; float: none;" id="u_realName"
											class="formtips"></span>
									</s:else></td>
							</tr>
							<tr>
								<td width="100" height="60" align="right"><strong>*</strong>身份证号：</td>
								<td><s:if test='#session.user.usrCustId>0'>
										<a id='idNo1'>${map.idNo}</a>
										<img src="images/neiye2_44.jpg" alt="ok"
											style="margin-left: 20px;" />
										<input type="hidden" class="inp188" name="paramMap.idNo"
											id="idNo" value="${map.idNo}" />
									</s:if> <s:else>
										<input type="text" class="inp188" name="paramMap.idNo"
											id="idNo" value="${map.idNo}" />
										<span style="color: red; float: none;" id="u_idNo"
											class="formtips"></span>
									</s:else></td>
							</tr>
							<tr height="45">
								<td width="100" height="60" align="right"><strong>*</strong>电子邮箱：</td>
								<td><input type="text" class="inp188" name="paramMap.email"
									id="email" value="${map.email}" /> <span
									style="color: red; float: none;" id="u_email" class="formtips"></span></td>
							</tr>


							<tr height="10"></tr>

							<tr height="10">
								<td align="center" colspan="2"><span style="color: red;">注：身份信息保存后将无法修改</span>
								</td>
							</tr>
							<tr height="5"></tr>
							<tr>
								<td colspan="2" align="center"><input type="button"
									value="保存" class="bcbtn" id="jc_btn"
									style="${request.realName!=null && request.realName!='' ?'display: none;':''}" />
								</td>
							</tr>

						</table>
					</div>
				</div> --%>

				<div class="u-right">
					<div class="right-col">
						<div class="income-bar">
							<p class="left">
								<span class="left">本月已获得奖励</span> <i class="right">${not empty recommondRewardDo.reward_amount ? recommondRewardDo.reward_amount : 0}元</i> <b></b>
							</p>
							<p class="right">
								<span class="left">累计已获得奖励</span> <i class="right">${not empty sumRecommondRewardDo.reward_amount ? sumRecommondRewardDo.reward_amount : 0}元</i>
							</p>
						</div>
						<div class="activity-info">
							<h3 class="col-title" style="margin:0"></h3>
							<p>邀请好友活动再度开启。据历次活动统计，平均每位推荐人邀请了5位好友投资，获得的返现奖励达6千元。好友投资越多，返现越多，上无封顶。快来参加吧！</p>
						</div>
					</div>
					<div class="right-col mt16">
						<h3 class="col-title">如何获得奖励</h3>
						<img class="step-img" src="images/step.jpg" height="102"
							width="867" alt="">
					</div>

					<div class="right-col mt16">
						<h3 class="col-title">邀请好友</h3>
						<div class="clearfix">
							<div class="invite-item">
								<img src="images/icon-weixin.png" height="69" width="69"
									alt=""> <label>微信</label>
									<div class="pop-layer pop-layer-code">
										<i></i> <img src="<c:out value="showImage.do" />" height="138"
											width="138" alt="">
									</div>
							</div>
							<div class="invite-item">
								<img src="images/icon-sns.png" height="65" width="64" alt="">
									<label>短信</label>
									<div class="pop-layer pop-sms">
										<i></i>
										<h3>短信邀请好友</h3>
										<p class="tip">填写好友电话号码，每个空格填写一个</p>
										<label>手机号码</label> 
											<input type="text" id="mobile1">
											<input type="text" id="mobile2"> 
											<input type="text" id="mobile3"> 
											<input type="text" id="mobile4">
											<input type="text" id="mobile5"> 
										<label>短信内容</label>
															<textarea id="smsContent">还把钱存银行？快跟我一起去合和年在线（http://m.hehenian.com/account/regIndex.do）乐享零风险+高收益!年化收益可达银行定存4倍，一般人我不告诉他~</textarea> <label>验证码</label>
															<div class="code clearfix">
																<!-- <input type="text" name="" id=""><img src=""
																	alt=""> -->
																	<input type="text" name="paramMap.code" id="code" />
          															<img src="admin/imageCode.do?pageId=userlogin" title="点击更换验证码" style="cursor: pointer;" id="codeNum" onclick="javascript:switchCode()" />
															</div>
															<button class="btn" id="sendSMS">发送短信</button>
									</div>
							</div>
							<div class="invite-item">
								<img src="images/icon-link.png" height="66" width="66"
									alt=""> <label>复制连接</label>
							</div>
						</div>
					</div>

					<div class="right-col mt16">
						<h3 class="col-title">好友列表</h3>
						<div class="user-table">
							<table>
								<thead>
									<tr>
										<th>好友用户名</th>
										<!-- <th>好友姓名</th> -->
										<th>好友手机号码</th>
										<th>注册时间</th>
										<th>投资金额</th>
										<th>奖励金额</th>
									</tr>
								</thead>
								<c:if test="${not empty inviteFriendList}">
									<tbody>
										<c:forEach var="inviteFriend" items="${inviteFriendList}">
											<tr>
												<td>${inviteFriend.friendUserInfo.username}</td>
												<%-- <td>${inviteFriend.friendPersonInfo.realName}</td> --%>
												<td>${inviteFriend.friendUserInfo.mobilePhone}</td>
												<td><fmt:formatDate value="${inviteFriend.friendUserInfo.createTime}" pattern="yyyy-MM-dd"/></td>
												<td>${inviteFriend.sunInvestMoney gt 0 ? hhn:insertComma(inviteFriend.sunInvestMoney, 2) : "0.00"}</td>
												<td>${inviteFriend.awardMoney gt 0 ? inviteFriend.awardMoney : "0.00"}</td>
											</tr>
										</c:forEach>
									</tbody>
								</c:if>
								<c:if test="${empty inviteFriendList}">
									<tr>
										<td align="center" height="30" colspan="11">暂无数据</td>
									</tr>
								</c:if>
							</table>
							<div class="mjd_fy_all">
								<c:if test="${not empty inviteFriendList}">
									<div class="page" style=" padding-top:20px;">
										<p>
											<shove:page url="investCurrentRecord.do" curPage="${pageBean.pageNum}" showPageCount="6" pageSize="${pageBean.pageSize}" theme="number" totalCount="${pageBean.totalNum}">
											</shove:page>
										</p>
									</div>
								</c:if>
							</div>
						</div>
					</div>
				</div>

			</div>
			<div style="width: 1170px; margin: 0px auto">&nbsp;</div>
		</div>
	</div>
	<div class="cle"></div>
	<!-- 引用底部公共部分 -->
	<jsp:include page="/include/footer.jsp"></jsp:include>
	<script type="text/javascript" src="script/jquery-1.7.1.min.js"></script>
	<script type="text/javascript" src="script/jquery.shove-1.0.js"></script>
	<script type="text/javascript" src="common/date/calendar.js"></script>
	<script type="text/javascript" src="css/popom.js"></script>
	<script type="text/javascript">
		function switchCode() {
			var timenow = new Date();
			$("#codeNum").attr("src", "admin/imageCode.do?pageId=userlogin&d=" + timenow);
		}
	    var invite = $('.invite-item'),
	        hideTimeout = null,
	        hidePop = function(){
	        invite.removeClass('pop-show');
	    };
	    invite.on('mouseover', function(){
	        $(this).data('time') && clearTimeout($(this).data('time'));
	        $(this).addClass('pop-show');
	    }).on('mouseout', function(){
	        var that = $(this);
	        that.data('time', setTimeout(function(){
	            that.removeClass('pop-show');
	        },600));
	    });
	
	    $('.pop-layer').on('mouseover', function(){
	        
	    })
	    
	    $(function() {
	    	$("#sendSMS").click(function() {
	    		var inviteePhone = [];
	    		for (var i = 1; i < 6; i++) {
	    			var mobilePhone = $("#mobile" + i).val().trim();
	    			if (mobilePhone != null && '' != mobilePhone) {
	    				inviteePhone.push(mobilePhone);
	    			}
	    		}
	    		if (inviteePhone == null || inviteePhone.length == 0) {
	    			alert("请输入邀请人手机号码");
	    			return;
	    		}
	    		var validateCode = $("#code").val().trim();
	    		if (validateCode == null || '' == validateCode) {
	    			alert("请输入验证码");
	    			return;
	    		}
	    		var param = {};
	    		param["inviteDetail.invitee.mobilePhone"] = inviteePhone.join(",");
	    		param["inviteDetail.content"] = $("#smsContent").val();
	    		param["paramMap.code"] = validateCode;
	    		param["paramMap.pageId"] = "userlogin";
	    		$.post('<c:url value="smsInvite.do" />', param, function(data){
	    			if (data['msg']) {
	    				switchCode();
	    				$("#code").val("");
	    				alert(data['msg']);
	    				return;
	    			}
    				for (var i = 1; i < 6; i++) {
    	    			$("#mobile" + i).val("");
    	    		}
    				alert("邀请信息发送成功");
    				switchCode();
    				$("#code").val("");
    				//$("#smsContent").val("");
	    			// $("#workCity").html(_array.join(""));
	    		});
	    	});
	    })
    </script>
</body>
</html>
