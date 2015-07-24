<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<jsp:include page="/include/head.jsp"></jsp:include>
<script type="text/javascript">
	$(function() {
		$(".header_two_right_ul li").hover(function() {
			$(this).find("ul").show();
		}, function() {
			$(this).find("ul").hide();
		})

		$(".fbjk_all_toux ul li:first").addClass(".inpp");
		$(".fbjk_all_toux ul li").click(function() {
			var ppin = $(".fbjk_all_toux ul li").index(this);
			$(".fbjk_all_toux ul li").removeClass(".inpp");
			$(this).addClass(".inpp");
			$(".fbjk_all_touxh").hide();
			$(".fbjk_all_touxh").eq(ppin).show();
		});
	});
	
</script>
</head>
<body>
	<jsp:include page="/include/top.jsp"></jsp:include>
	<div class="cle"></div>
	<div class="center">
		<div class="login_center" style="width:1000px; background:#f8f8f8 url(images/hhnimages/hhn/login_bg1.png) 0 20px no-repeat; margin:0 auto;">
			<div class="login_center_left">
				<table width="500" border="0" cellspacing="0" cellpadding="0" style="margin-left:130px;">
					<tr>
						<td><strong>*</strong><b>用户名：</b></td>
						<td>&nbsp;</td>
					</tr>
					<tr height="5"></tr>
					<tr>
						<td colspan="2"><input type="text" class="login_tex1" name="paramMap.userName" id="userName" value="<s:property value='#request.userName'/>"  /><br> <span style="color: red;margin-left: 32px;" id="s_userName"
								class="formtips"></span>
						</td>
					</tr>
					<tr height="5"></tr>
					<tr>
						<td><strong>*</strong><b>密码：</b></td>
						<td>&nbsp;</td>
					</tr>
					<tr height="5"></tr>
					<tr>
						<td colspan="2"><input type="password" class="login_tex1" name="paramMap.password" id="password" /><br> <span style="color: red;margin-left: 32px;" id="s_password"
								class="formtips"></span>
						</td>
					</tr>
					<tr height="5"></tr>
					<tr>
						<td><strong>*</strong><b>确认密码：</b></td>
						<td>&nbsp;</td>
					</tr>
					<tr height="5"></tr>
					<tr>
						<td colspan="2"><input type="password" class="login_tex1" name="paramMap.confirmPassword" id="confirmPassword" /><br> <span style="color: red;margin-left: 32px;"
								id="s_confirmPassword" class="formtips"></span>
						</td>
					</tr>
					<tr height="5"></tr>
					<tr>
						<td><strong>*</strong><b>手机号：</b></td>
						<td>&nbsp;</td>
					</tr>
					<tr height="5"></tr>
					<tr>
						<td colspan="2"><input type="text" class="login_tex1" name="paramMap.telephone" id="telephone" value="<s:property value='#request.telephone'/>" /> <%--                    <a href="javascript:void()" onclick="getTelephoneCode()" id="ida" disable="no"><strong id="message">获取验证码</strong> </a>--%>
							<br><span style="color: red;margin-left: 32px;" id="s_telephone" class="formtips">
						</td>
					</tr>
					<%--                <tr height="5"></tr>--%>
					<%--                <tr>--%>
					<%--                    <td><strong>*</strong><b>手机验证码：</b></td>--%>
					<%--                    <td>&nbsp;</td>--%>
					<%--                </tr>--%>
					<%--                <tr height="5"></tr>--%>
					<%--                <tr>--%>
					<%--                    <td colspan="2"><input type="text" class="login_tex2" name="paramMap.confirmTelephone" id="confirmTelephone" />--%>
					<%--                    <span style="margin-left:10px; float:left; margin-top:8px;">输入您收到的短信验证码</span>--%>
					<%--                    <br><span style="color: red;margin-left: 32px;" id="s_confirmTelephone" class="formtips"></td>--%>
					<%--                </tr>--%>
					<tr height="5"></tr>
					<tr>
						<td><b>推荐人(选填)：</b></td>
						<td>&nbsp;</td>
					</tr>
					<tr height="5"></tr>
					<tr>
						<td colspan="2"><input type="text" class="login_tex1" name="paramMap.refferee" id="refferee" value="<s:property value='#request.paramMap.userId'/>" /><br> <span style="color: red;margin-left: 32px;" id="s_refferee"
								class="formtips">
						</td>
					</tr>
					<tr height="20"></tr>
					<tr>
						<td><input type="checkbox" id="agre" checked="checked" /><span>我已经阅读并同意</span><a href="javascript:void()" id="show" style="color:#4aa3ff;">《注册协议》</a></td>
					</tr>
					<tr style="display: none">
						<td></td>
					</tr>
				</table>
				<div id="all" style="display: none;margin-left: 91px;">
					<textarea readonly="readonly" id="idTextarea" style="margin-left: 0px;font-size: 12px;margin-top: 5px;" cols="52" rows="10">
本服务协议由合和年在线（即本网站）的运营方深圳市彩付宝网络技术公司与本网站会员双方共同订立，对双方具有法律效力。
在会员注册成为本网站会员前，会员已充分阅读并理解本《合和年在线注册协议》（以下简称“本协议”）的所有条款。会员同意以下条款并注册后，将有权依据本协议的条款接受本网站的服务，同时有义务接受本协议条款的约束。若会员不接受以下条款，请会员立即停止注册或主动停止使用本网站的服务。本网站只接受持有中国有效身份证明的18周岁以上具有完全民事行为能力的自然人成为网站会员。如会员不符合资格，请勿注册，否则本网站有权随时中止或终止会员的用户资格。
&nbsp;&nbsp;&nbsp;&nbsp;第一章  本协议的文本
&nbsp;&nbsp;&nbsp;&nbsp;第一条 条款的法律效力
&nbsp;&nbsp;&nbsp;&nbsp;本协议的内容包括以下全部条款以及本网站已经发布的及将来可能发布的与会员有关的各项规则，该等规则均为本协议不可分割的一部分，与以下所列条款具有同等法律效力
本协议是由会员与本网站共同签订的，适用于会员在本网站的全部活动。在会员注册成为会员时，会员已经阅读、理解并接受本协议的全部条款及各类规则，如有违反而导致任何法律后果的发生，会员将以自己的名义独立承担所有相应的法律责任。
&nbsp;&nbsp;&nbsp;&nbsp;第二条 协议修改的权限
&nbsp;&nbsp;&nbsp;&nbsp;本网站有权根据需要修改本协议的内容或根据本协议制定、修改各类具体规则并在本网站相关系统板块发布，无需另行单独通知会员。如以下条款或本网站各项规则有任何变更，本网站将在网站上刊载公告。经修订的相关条款和规则一经公告，即于公告规定的特定生效日期自动生效。请会员适时关注本网站关于相关条款和规则的公告，若会员在本协议及具体规则内容公告变更后继续使用本服务的，表示会员已充分阅读、理解并接受修改后的协议和具体规则内容，也将遵循修改后的协议和具体规则使用本网站的服务；同时就会员在协议和具体规则修订前通过本网站进行的交易及其效力，视为会员已同意并已按照本协议及有关规则进行了相应的授权和追认。如不同意该等变更，请会员在该等变更的公告刊载之日起72小时内以会员在本网站注册时提供的个人邮箱向本网站的服务邮箱发送邮件表明希望终止本协议（“注册终止申请”），本网站确认收到会员的邮件后将与会员协商本协议终止后双方权利义务的履行。
&nbsp;&nbsp;&nbsp;&nbsp;第三条 法律效力的产生	
&nbsp;&nbsp;&nbsp;&nbsp;会员通过自行或授权有关方勾选位于注册页面下方的“我已经阅读并同意合和年在线注册协议”选项并按照本网站的流程成功注册后，本协议即产生法律效力。会员不得以未签署书面协议为由否认本协议的效力。
&nbsp;&nbsp;&nbsp;&nbsp;第二章 会员
&nbsp;&nbsp;&nbsp;&nbsp;第四条 会员及会员注册
&nbsp;&nbsp;&nbsp;&nbsp;会员必须是持有中国有效身份证明的18周岁以上具有完全民事行为能力的自然人。无民事行为能力人、限制民事行为能力人以及实体组织不能注册为合和年在线会员或从事超过其民事权利或行为能力范围交易，否则其与合和年在线会员之间的服务协议自始无效，合和年在线一经发现，有权立即注销该会员，并追究其使用合和年在线会员的一切相关法律责任。会员注册是指自然人登陆合和年在线，并按要求填写相关信息并确认同意履行相关会员协议的过程。
会员包括出借人与借款人，在某一借款过程中参与投资的会员即为借款的出借人，申请借款的会员即为借款人。
第五条  注册会员的承诺
&nbsp;&nbsp;&nbsp;&nbsp;会员承诺以下事项：
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1、会员必须依合和年在线要求提示提供真实、最新、有效及完整的资料。
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2、会员有义务确保其资料为真实、最新、有效及完整的资料。若会员提供任何错误、虚假、过时或不完整的资料，或者合和年在线依其独立判断怀疑资料为错误、虚假、过时或不完整，合和年在线有权暂停或终止会员的会员账户，并拒绝会员使用合和年在线服务的部份或全部功能。在此情况下，合和年在线不承担任何责任，并且会员同意负担因此所产生的直接或间接的任何支出或损失。
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3、如因会员未及时更新基本资料，导致合和年在线服务无法提供或提供服务时发生任何错误，会员不得将此作为取消交易或拒绝付款的理由，合和年在线亦不承担任何责任，所有后果应由会员承担。
在注册时和使用本网站服务的所有期间，会员应提供会员自身的真实资料和信息，并保证自会员注册之时起至会员终止履行本网站服务协议的期间，所提交的所有资料和信息（包括但不限于电子邮件地址、联系电话、联系地址、邮政编码、身份信息、征信信息等）真实、准确、完整，且是最新的。
&nbsp;&nbsp;&nbsp;&nbsp;第三章 本网站的服务内容
&nbsp;&nbsp;&nbsp;&nbsp;第六条  信用咨询服务 
&nbsp;&nbsp;&nbsp;&nbsp;合和年在线向出借人提供信用咨询服务。双方同意，合和年在线向出借人提供信用咨询服务应按如下范围和规则进行： 
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1、合和年在线应对在平台上展示的各项借款人基本信息和投资者拟转让的既有债权涉及的借款人基本信息中的借款人的合和年在线会员名和隐藏部分信息的身份证件扫描件（“借款人联络信息”），在适用法律和技术手段允许的范围内进行必要和合理的查验，以供出借人在决定是否向借款人借款时参考。在出借人与借款人之间债权债务尚未完全清偿前，合和年在线如获知借款人提供的借款人联络信息发生变更或存在不真实、不准确的情形，应及时以电子邮件的方式通知出借人。
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2、合和年在线可以选择是否通过网站向出借人提供平台自行收集或从其他第三方合作机构获得的除借款人联络信息之外的其他信息，如借款人的工作情况、收入情况、家庭情况、信用报告、历史偿债情况等，以供出借人在自行决定是否向借款人借款时参考，在出借人与借款人之间债权债务尚未完全清偿前，合和年在线如获知其提供的相关借款人信息发生变更或存在不真实、不准确的情形，应及时以电子邮件的方式通知出借人。
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3、合和年在线将根据内部制定的评级规则对借款人及其各笔特定借款需求等进行信用评级，并以适当方式在网上公示，以供出借人参考。合和年在线还有权根据其进一步获知的借款人相关信息和/或内部评级规则的调整而调整对借款人及各笔特定借款需求的信用评级。
&nbsp;&nbsp;&nbsp;&nbsp;第七条  资金管理服务 
&nbsp;&nbsp;&nbsp;&nbsp;合和年在线同意向会员提供资金管理服务，会员同意接受合和年在线提供的该项服务。双方同意，合和年在线向会员提供资金管理服务应按如下范围和规则进行： 
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1、资金保管： 会员在合和年在线上的资金由会与本网站合作的第三方支付公司保管，会员在第三方支付公司开立自己的托管账户，托管账户中的资金变动操作由会员本人与第三方支付公司另行约定。在托管账户中的资金不计利息。
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2、资金冻结：出借人同意并委托合和年在线通知第三方支付公司按照合和年在线的相关交易规则和本协议的规定冻结其在托管账户下的相应数额的资金，包括但不限于：(a) 出借人对借款人在合和年在线上所发布的借款标的发出投资指令成功后，出借人同意并委托合和年在线通知第三方支付公司在出借人托管账户中冻结等值于借款标的金额的资金。该项冻结款项将在借款交易成功后或失败时解除；以及(b)在新的债权人与原债权人签署《债权转让协议》后，新的债权人同意并委托合和年在线在通知第三方支付公司在出借人托管账户中冻结等值于债权转让价款数额的资金。该项冻结款项将在《债权转让协议》项下债权金额授权合和年在线通知第三方支付公司划转至原既有债权转让方时或《债权转让协议》失效时解除。
借款人同意并委托合和年在线按照合和年在线的相关交易规则和本协议的规定通知第三方支付公司冻结其在托管账户中相应数额的资金，包括但不限于：(a)借款人根据《合和年在线借款协议》（以下简称“借款协议”）《借款咨询服务协议》《债权转让协议》约定在每月还款日应还当期本息、咨询费和手续费等额的资金；（b）借款人在还款状态中，如未能在还款日全额归还当期应还本息、咨询费和手续费，借款人同意并委托合和年在线通知第三方支付公司冻结借款人托管账户所有资金直至借款人还清应还金额为止。资金冻结期间不计利息。
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3、资金代付：出借人同意由合和年在线按照合和年在线的相关交易规则和本协议的规定，通知其合作的第三方支付公司从出借人托管账户代为划扣按照合和年在线的相关交易规则和本协议的规定应支付的相应数额的资金，包括但不限于：(a) 根据出借人与借款人之间的借款协议应向借款人划转的借款本金；(b) 根据出借人与债权人之间的《债权转让协议》应向债权转让方支付的债权转让价款。
借款人同意由合和年在线按照合和年在线的相关交易规则和本协议的规定，通知其合作的第三方支付公司从借款人托管账户或其名下银行账户代为划扣按照合和年在线的相关交易规则、本协议的规定应支付的相应数额的资金，包括但不限于：（a）根据借款协议应向出借人划转的当期应还本息；（b）根据借款协议应向合和年在线划转的借款手续费；（c）根据借款人与咨询公司之间的《借款咨询服务协议》应向咨询公司划转的咨询费等。
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;4、资金代收：借款人同意合和年在线通过第三方支付公司代扣其在借款协议项下应支付给合和年在线的手续费、应支付给咨询公司的咨询费和应支付给出借人的本金和利息等相关费用，并将该等代收的资金直接划转至以上三方在第三方支付公司的托管账户。特殊情况下，借款人同意第三方支付公司将以上费用代扣至合和年在线在第三方支付公司的托管账户，再由合和年在线将合和年在线、咨询公司和出借人各自所应收的资金分配至各自托管账户。
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;5、资金取现：会员可在合和年在线平台向第三方支付公司提出取现要求，取现账户必须为会员注册时向第三方支付公司提供的个人身份信息相符的有效的中国境内银行账户，以提取其届时在第三方支付公司托管账户下所有未出借且未冻结的资金。根据第三方支付公司规定：
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;a. 普通取现：会员在工作日18：00前的取现申请，通过审核后一般可于下一个工作日到账；周末或节假日提交的取现申请在之后第2个工作日到账
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;b. 快速取现：支持除偏远地区外的大部分银行卡，工作日14:30分前发起取现当日到账，周末或节假日提交的取现申请在之后第1个工作日到账。
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;C. 即时取现：支持工行、招行银行卡，7*24小时到账。
实际到账时间以第三方支付公司最新规定为准，合和年在线不对该到账时间做任何形式的保证。除本条约定外，合和年在线不接受会员提出的其他任何提现方式。
借款人可在合和年在线的工作时间内向合和年在线提出提现要求，委托合和年在线向第三方支付公司发出提现指令，提现账户必须为借款人注册时向第三方支付公司提供的借款者本人的有效的中国境内银行账户信息，以提取其届时在第三方支付公司托管账户中下所有未冻结的资金。根据第三方支付公司规定：
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;a. 普通取现：会员在工作日18：00前的取现申请，通过审核后一般可于下一个工作日到账；周末或节假日提交的取现申请在之后第2个工作日到账
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;b. 快速取现：支持除偏远地区外的大部分银行卡，工作日14:30分前发起取现当日到账，周末或节假日提交的取现申请在之后第1个工作日到账。
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;C. 即时取现：支持工行、招行银行卡，7*24小时到账。
实际到账时间以第三方支付公司最新规定为准，合和年在线不对该到账时间做任何形式的保证。除本条约定外，合和年在线不接受会员提出的其他任何提现方式。
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;6、资金查询：会员有权在任何时间使用本人的会员名和密码登录合和年在线查询其在第三方支付公司托管账户名下资金的情况，包括充值、冻结、支付和提现记录等。会员应理解，会员最终收到款项的服务是由其提供的银行账户开户行提供的，需向该银行请求查证。会员同意，其登录合和年在线查询的任何信息不能够作为相关操作或借贷交易的证据或依据；如该等信息与合和年在线保存的记录存在任何不一致，应以合和年在线所提供的记录为准。
一经会员使用前述服务，即表示会员不可撤销地授权本网站进行相关操作，且该等操作是不可逆转的，会员不能以任何理由拒绝付款或要求取消交易。就前述服务，会员应按照有关文件及第三方支付机构或金融机构的规定支付第三方的费用，会员与第三方之间就费用支付事项产生的争议或纠纷，与本网站无关。
&nbsp;&nbsp;&nbsp;&nbsp;第八条 会员客户服务：
&nbsp;&nbsp;&nbsp;&nbsp;1、银行卡认证：为使用合和年在线或合和年在线合作的第三方支付公司提供的充值、取现、代扣等服务，会员应按照合和年在线平台规定的流程提交以会员本人名义登记的有效银行借记卡等信息，经由合和年在线审核通过后，合和年在线会将会员的账户与前述银行账户进行绑定。如会员未按照合和年在线规定提交相关信息或提交的信息错误、虚假、过时或不完整，或者合和年在线有合理的理由怀疑会员提交的信息为错误、虚假、过时或不完整，合和年在线有权拒绝为会员提供银行卡认证服务，会员因此未能使用充值、取现、代扣等服务而产生的损失自行承担。
会员了解，上述充值、代收/代付及取现服务涉及合和年在线与银行、代偿公司、回购公司、第三方支付公司等第三方的合作。会员同意：(a) 受银行、代偿回购公司、第三方支付公司等第三方仅在工作日进行资金代扣及划转的现状等各种原因所限，合和年在线不对前述服务的资金到账时间做任何承诺，也不承担与此相关的责任，包括但不限于由此产生的利息、货币贬值等损失；(b) 一经会员使用前述服务，即表示会员不可撤销地授权合和年在线进行相关操作，且该等操作是不可逆转的，会员不能以任何理由拒绝付款或要求取消交易。就前述服务，合和年在线暂不会向会员收取本协议约定之外的其他费用，但会员应按照第三方的规定向第三方支付费用，具体请见第三方网站的相关信息。与第三方之间就费用支付事项产生的争议或纠纷，与合和年在线无关。
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2、查询：合和年在线将对会员在合和年在线平台的所有操作进行记录，不论该操作之目的最终是否实现。会员可以通过会员账户实时查询会员账户名下的交易记录。会员理解并同意会员最终收到款项的服务是由会员经过认证的银行卡对应的银行提供的，需向该银行请求查证。会员理解并同意通过合和年在线平台查询的任何信息仅作为参考，不作为相关操作或交易的证据或依据；如该等信息与合和年在线记录存在任何不一致，应以合和年在线提供的书面记录为准。
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3、密码重置：合和年在线对会员提供密码重置服务在重置密码过程中，合和年在线有权要求会员提供身份证明的文件和其他证明，会员必须提供相应的证明文件和内容。合和年在线不对因为证明文件错误、不完整导致的密码重置失败担负责任。
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;4、本息保障：与合和年在线合作的代偿公司或回购公司对会员提供本息代偿或逾期借款回购服务，让会员的利益受到最大程度的保护。当借款逾期后，代偿公司或回购公司履行代偿或回购义务后有权对借款人进行本息追偿，包括且不限于到期应付未付之本金、利息、手续费、咨询费、逾期罚息等。 关于代偿及回购的具体方式根据会员所签署的借款协议予以约定。
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;5、会员每次使用合和年在线服务应直接登录合和年在线平台或使用合和年在线提供的链接登陆合和年在线平台（网址：http://www.hehenian.com, 如合和年在线以公告等形式发布新的网址，请届时登陆新的网址）。会员每次拨打合和年在线客户电话应拨打合和年在线官方网站提供的客服电话4008-303-737（如合和年在线以公告等形式发布新的客服电话，请届时拨打新的客服电话）。
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;6、会员同意，合和年在线有权在提供服务过程中以各种方式投放各种商业性广告或其他任何类型的商业信息（包括但不限于在合和年在线平台的任何页面上投放广告），并且，会员同意接受合和年在线通过电子邮件或其他方式向会员发送商品促销或其他相关商业信息。
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;7、会员同意，合和年在线可将其通过其他渠道所获得的会员信息予以完善，并根据所获得的信息对会员信用进行评级。合和年在线的会员在申请借款流程中点击“立即申请”即视为该会员同意并委托合和年在线为其在合和年在线平台上发布借款申请需求。  
&nbsp;&nbsp;&nbsp;&nbsp;第九条 合和年在线将为会员提供以下电子合同管理服务：
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1、在合和年在线平台交易需订立的合同采用电子合同方式会员使用会员账户登录合和年在线平台后，根据合和年在线的相关规则，以会员账户ID在合和年在线平台通过点击确认或类似方式签署的电子合同即视为会员本人真实意愿并以会员本人名义签署的合同，具有法律效力。会员应妥善保管自己的账户密码等账户信息，会员通过前述方式订立的电子合同对合同各方具有法律约束力，会员不得以其账户密码等账户信息被盗用或其他理由否认已订立的合同的效力或不按照该等合同履行相关义务。
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2、会员根据本协议以及合和年在线的相关规则签署电子合同后，不得擅自修改该合同。合和年在线向会员提供电子合同的备案、查看、核对服务，如对电子合同真伪或电子合同的内容有任何疑问，会员可向合和年在线电话或者邮件进行核对。如对此有任何争议，应以合和年在线记录的合同为准。
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3、会员不得私自仿制、伪造在合和年在线平台上签订的电子合同或印章，不得用伪造的合同进行招摇撞骗或进行其他非法使用，否则由会员自行承担责任。
&nbsp;&nbsp;&nbsp;&nbsp;第十条 除外责任
&nbsp;&nbsp;&nbsp;&nbsp;1、在任何情况下，对于会员使用合和年在线服务过程中涉及由第三方提供相关服务的责任由该第三方承担，合和年在线不承担该等责任。合和年在线不承担责任的情形包括但不限于：
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(1) 因银行、第三方支付公司、代偿公司、回购公司等第三方未按照会员和/或合和年在线指令进行操作引起的任何损失或责任；
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(2) 因银行、第三方支付公司、代偿公司、回购公司等第三方原因导致资金未能及时到账或未能到账引起的任何损失或责任；
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(3) 因银行、第三方支付公司、代偿公司、回购公司等第三方对交易限额或次数等方面的限制而引起的任何损失或责任；
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(4) 因其他第三方的行为或原因导致的任何损失或责任。
&nbsp;&nbsp;&nbsp;&nbsp;2、 因会员自身的原因导致的任何损失或责任，由会员自行负责，合和年在线不承担责任。合和年在线不承担责任的情形包括但不限于：
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(1) 会员未按照本协议或合和年在线平台不时公布的任何规则进行操作导致的任何损失或责任；
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(2) 因会员使用的银行卡的原因导致的损失或责任，包括会员使用未经认证的银行卡或使用非会员本人的银行卡或使用信用卡，会员的银行卡被冻结、挂失等导致的任何损失或责任；
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(3) 会员向合和年在线发送的指令信息不明确、或存在歧义、不完整等导致的任何损失或责任；
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(4) 会员账户内余额不足导致的任何损失或责任；
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(5) 其他因会员原因导致的任何损失或责任。
&nbsp;&nbsp;&nbsp;&nbsp;第十一条 当会员使用合和年在线服务时，合和年在线会向会员收取相关服务费用。各项服务费用详见会员使用合和年在线服务时合和年在线平台上所列之收费方式说明。合和年在线保留单方面制定及调整服务费用的权利。
&nbsp;&nbsp;&nbsp;&nbsp;第十二条 会员在使用合和年在线服务过程中（如充值或取现等）可能需要向第三方（如银行或第三方支付公司等）支付一定的费用，具体收费标准详见第三方网站相关页面，或合和年在线平台的提示。
&nbsp;&nbsp;&nbsp;&nbsp;第四章 会员的义务
&nbsp;&nbsp;&nbsp;&nbsp;第十三条 会员承诺绝不为任何非法目的或以任何非法方式使用合和年在线服务，并承诺遵守中国相关法律、法规及一切使用互联网之国际惯例，遵守所有与合和年在线服务有关的网络协议、规则和程序。
&nbsp;&nbsp;&nbsp;&nbsp;第十四条 会员同意并保证不得利用合和年在线服务从事侵害他人权益或违法之行为，若有违反者应负所有法律责任。上述行为包括但不限于：
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1、反对宪法所确定的基本原则，危害国家安全、泄漏国家秘密、颠覆国家政权、破坏国家统一的。
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2、侵害他人名誉、隐私权、商业秘密、商标权、著作权、专利权、其他知识产权及其他权益。
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3、违反依法律或合约所应负之保密义务。
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;4、冒用他人名义使用合和年在线服务。
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;5、从事任何不法交易行为，如贩卖枪支、毒品、禁药、盗版软件或其他违禁物。
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;6、提供赌博资讯或以任何方式引诱他人参与赌博。
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;7、涉嫌洗钱、套现或进行传销活动的。
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;8、从事任何可能含有电脑病毒或是可能侵害合和年在线服务系統、资料等行为。
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;9、 利用合和年在线服务系统进行可能对互联网或移动网正常运转造成不利影响之行为。
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;10、侵犯合和年在线的商业利益，包括但不限于发布非经合和年在线许可的商业广告。
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;11、利用合和年在线服务上传、展示或传播虚假的、骚扰性的、中伤他人的、辱骂性的、恐吓性的、庸俗淫秽的或其他任何非法的信息资料。
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;12、其他合和年在线有正当理由认为不适当之行为。
&nbsp;&nbsp;&nbsp;&nbsp;第十五条 合和年在线保有依其单独判断删除合和年在线平台内各类不符合法律政策或不真实或不适当的信息内容而无须通知会员的权利，并无需承担任何责任。若会员未遵守以上规定的，合和年在线有权作出独立判断并采取暂停或关闭会员账户等措施，而无需承担任何责任。
&nbsp;&nbsp;&nbsp;&nbsp;第十六条 会员同意，由于会员违反本协议，或违反通过援引并入本协议并成为本协议一部分的文件，或由于会员使用合和年在线服务违反了任何法律或第三方的权利而造成任何第三方进行或发起的任何补偿申请或要求（包括律师费用），会员会对合和年在线及其关联方、合作伙伴、董事以及雇员给予全额补偿并使之不受损害。
&nbsp;&nbsp;&nbsp;&nbsp;第十七条 会员承诺，其通过合和年在线平台上传或发布的信息均真实有效，其向合和年在线提交的任何资料均真实、有效、完整、详细、准确。如因违背上述承诺，造成合和年在线或合和年在线其他使用方损失的，会员将承担相应责任。
&nbsp;&nbsp;&nbsp;&nbsp;第五章 服务中断或故障
&nbsp;&nbsp;&nbsp;&nbsp;第十八条 会员同意，基于互联网的特殊性，合和年在线不担保服务不会中断，也不担保服务的及时性和/或安全性。系统因相关状况无法正常运作，使会员无法使用任何合和年在线服务或使用任何合和年在线服务时受到任何影响时，合和年在线对会员或第三方不负任何责任，前述状况包括但不限于：
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1、合和年在线系统停机维护期间。
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2、电信设备出现故障不能进行数据传输的。
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3、由于黑客攻击、网络供应商技术调整或故障、网站升级、银行方面的问题等原因而造成的合和年在线服务中断或延迟。
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;4、因台风、地震、海啸、洪水、停电、战争、恐怖袭击等不可抗力之因素，造成合和年在线系统障碍不能执行业务的。
&nbsp;&nbsp;&nbsp;&nbsp;第六章 会员账户管理
&nbsp;&nbsp;&nbsp;&nbsp;第十九条 会员了解并同意，确保会员账户及密码的机密安全是会员的责任。会员将对利用该会员账户及密码所进行的一切行动及言论，负完全的责任，并同意以下事项：
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1、 会员不对其他任何人泄露账户或密码，亦不可使用其他任何人的账户或密码。因黑客、病毒或会员的保管疏忽等非合和年在线原因导致会员的会员账户遭他人非法使用的，合和年在线不承担任何责任。
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2、 合和年在线通过会员的会员账户及密码来识别会员的指令，会员确认，使用会员账户和密码登陆后在合和年在线的一切行为均代表会员本人。会员账户操作所产生的电子信息记录均为会员行为的有效凭据，并由会员本人承担由此产生的全部责任。
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3、 冒用他人账户及密码的，合和年在线及其合法授权主体有权对会员本人及实际使用人追究连带责任。
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;4、 会员应根据合和年在线的相关规则以及合和年在线平台的相关提示创建一个安全密码，应避免选择过于明显的单词或日期，比如会员的姓名、昵称或者生日等。
&nbsp;&nbsp;&nbsp;&nbsp;第二十条 会员如发现有第三人冒用或盗用会员账户及密码，或其他任何未经合法授权的情形，应立即以有效方式通知合和年在线，要求合和年在线暂停相关服务，否则由此产生的一切责任由会员本人承担。同时，会员理解合和年在线对会员的请求采取行动需要合理期限，在此之前，合和年在线对第三人使用该服务所导致的损失不承担任何责任。
&nbsp;&nbsp;&nbsp;&nbsp;第二十一条 会员决定不再使用会员账户时，应首先清偿所有应付款项（包括但不限于借款本金、利息、违约金、手续费、服务费、咨询费等），再将托管账户中的可用款项（如有）全部取现或者向合和年在线发出其它合法的支付指令，并向合和年在线申请注销该会员账户，经合和年在线审核同意后可正式注销会员账户。
会员死亡或被宣告死亡的，其在本协议项下的各项权利义务由其继承人承担。若会员丧失全部或部分民事权利能力或民事行为能力，合和年在线或其授权的主体有权根据有效法律文书（包括但不限于生效的法院判决等）或其法定监护人的指示处置与会员账户相关的款项。
&nbsp;&nbsp;&nbsp;&nbsp;第二十二条 合和年在线有权基于单方独立判断，在其认为可能发生危害交易安全等情形时，不经通知而先行暂停、中断或终止向会员提供本协议项下的全部或部分会员服务（包括收费服务），并将注册资料移除或删除，且无需对会员或任何第三方承担任何责任。前述情形包括但不限于：
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1、合和年在线认为会员提供的资料不具有真实性、有效性或完整性；
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2、合和年在线发现异常交易或有疑义或有违法之虞时；
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3、合和年在线认为会员账户涉嫌洗钱、套现、传销、被冒用或其他合和年在线认为有风险之情形；
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;4、 合和年在线认为会员已经违反本协议中规定的各类规则及精神；
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;5、 会员在使用合和年在线收费服务时未按规定向合和年在线支付相应的服务费用；
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;6、 会员账户已连续三年内未实际使用且账户中余额为零；
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;7、 合和年在线基于交易安全等原因，根据其单独判断需先行暂停、中断或终止向会员提供本协议项下的全部或部分会员服务（包括收费服务），并将注册资料移除或删除的其他情形。
&nbsp;&nbsp;&nbsp;&nbsp;第二十三条 会员同意在必要时，合和年在线无需进行事先通知即有权终止提供会员账户服务，并可能立即暂停、关闭或删除会员账户及该会员账户中所有相关资料及档案。
&nbsp;&nbsp;&nbsp;&nbsp;第二十四条 会员同意，会员账户的暂停、中断或终止不代表会员责任的终止，会员仍应对使用合和年在线服务期间的行为承担可能的违约或损害赔偿责任，同时合和年在线仍可保有会员的相关信息。
&nbsp;&nbsp;&nbsp;&nbsp;第七章 平台责任及限制，风险
&nbsp;&nbsp;&nbsp;&nbsp;第二十五条 合和年在线不对任何合和年在线服务提供任何形式的保证，包括但不限于以下事项：
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1、 合和年在线服务将符合会员的需求。
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2、 合和年在线服务将不受干扰、及时提供或免于出错。
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3、 会员经由合和年在线服务购买或取得之任何产品、服务、资讯或其他资料将符合会员的期望。
&nbsp;&nbsp;&nbsp;&nbsp;第二十六条 合和年在线服务的合作单位所提供的服务品质及内容由该合作单位自行负责。合和年在线平台的内容可能涉及由第三方所有、控制或者运营的其他网站（以下简称“第三方网站”）。合和年在线不能保证也没有义务保证第三方网站上任何信息的真实性和有效性。会员确认按照第三方网站的服务协议使用第三方网站，而不是按照本协议。第三方网站不是合和年在线推荐或者介绍的，第三方网站的内容、产品、广告和其他任何信息均由会员自行判断并承担风险，而与合和年在线无关。会员经由合和年在线服务的使用下载或取得任何资料，应由会员自行考量且自负风险，因资料的下载而导致的任何损坏由会员自行承担。
&nbsp;&nbsp;&nbsp;&nbsp;第二十七条 会员自合和年在线及合和年在线工作人员或经由合和年在线服务取得的建议或资讯，无论其为书面或口头，均不构成合和年在线对合和年在线服务的任何保证。
&nbsp;&nbsp;&nbsp;&nbsp;第二十八条 合和年在线不保证为向会员提供便利而设置的外部链接的准确性、有效性、安全性和完整性，同时，对于该等外部链接指向的不由合和年在线实际控制的任何网页上的内容，合和年在线不承担任何责任。
&nbsp;&nbsp;&nbsp;&nbsp;第二十九条 在法律允许的情况下，合和年在线对于与本协议有关或由本协议引起的，或者，由于使用合和年在线平台、或由于其所包含的或以其它方式通过合和年在线平台提供给会员的全部信息、内容、材料、产品（包括软件）和服务、或购买和使用产品引起的任何间接的、惩罚性的、特殊的、派生的损失（包括但不限于业务损失、收益损失、利润损失、使用数据或其他经济利益的损失），不论是如何产生的，也不论是由对本协议的违约（包括违反保证）还是由侵权造成的，均不负有任何责任，即使其事先已被告知此等损失的可能性。另外即使本协议规定的排他性救济没有达到其基本目的，也应排除合和年在线对上述损失的责任。
&nbsp;&nbsp;&nbsp;&nbsp;第三十条 除本协议另有规定外，在任何情况下，合和年在线对本协议所承担的违约赔偿责任总额不超过向会员收取的当次合和年在线服务费用总额。
&nbsp;&nbsp;&nbsp;&nbsp;第三十一条 会员了解并认可，任何通过合和年在线进行的交易并不能避免以下风险的产生，合和年在线不能也没有义务为如下风险负责：
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1、 宏观经济风险：因宏观经济形势变化，可能引起价格等方面的异常波动，会员有可能遭受损失；
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2、 政策风险：有关法律、法规及相关政策、规则发生变化，可能引起价格等方面异常波动，会员有可能遭受损失；
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3、 违约风险：因其他交易方无力或无意愿按时足额履约，会员有可能遭受损失；
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;4、 利率风险：市场利率变化可能对购买或持有产品的实际收益产生影响；
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;5、 不可抗力因素导致的风险；
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;6、 因会员的过错导致的任何损失 ，该过错包括但不限于：决策失误、操作不当、遗忘或泄露密码、密码被他人破解、会员使用的计算机系统被第三方侵入、会员委托他人代理交易时他人恶意或不当操作而造成的损失。
&nbsp;&nbsp;&nbsp;&nbsp;第三十二条 合和年在线不对任何会员及/或任何交易提供任何担保或条件，无论是明示、默示或法定的。合和年在线不能也不试图对会员发布的信息进行控制，对该等信息，合和年在线不承担任何形式的证明、鉴定服务。合和年在线不能完全保证平台内容的真实性、充分性、可靠性、准确性、完整性和有效性，并且无需承担任何由此引起的法律责任。会员依赖于会员的独立判断进行交易，会员应对其做出的判断承担全部责任。
&nbsp;&nbsp;&nbsp;&nbsp;第三十三条 以上并不能揭示会员通过合和年在线进行交易的全部风险及市场的全部情形。会员在做出交易决策前，应全面了解相关交易，谨慎决策，并自行承担全部风险。
&nbsp;&nbsp;&nbsp;&nbsp;第八章 会员信息的保护及披露
&nbsp;&nbsp;&nbsp;&nbsp;第三十四条 合和年在线对于会员提供的、 第三方合作公司所提供的、合和年在线自行收集的、经认证的个人信息将按照本协议予以保护、使用或者披露。合和年在线无需会员同意即可向合和年在线关联实体转让与合和年在线平台有关的全部或部分权利和义务。未经合和年在线事先书面同意，会员不得转让其在本协议项下的任何权利和义务。
&nbsp;&nbsp;&nbsp;&nbsp;第三十五条 合和年在线可能自公开及私人资料来源收集会员的额外资料，以更好地掌握会员情况，并为会员度身订造合和年在线服务、解决争议并有助确保在合和年在线平台进行安全交易。
&nbsp;&nbsp;&nbsp;&nbsp;第三十六条 合和年在线按照会员在合和年在线平台上的行为自动追踪关于会员的某些资料。在不透露会员的隐私资料的前提下，合和年在线有权对整个会员数据库进行分析并对会员数据库进行商业上的利用。
&nbsp;&nbsp;&nbsp;&nbsp;第三十七条 会员同意，合和年在线可在合和年在线平台的某些网页上使用诸如“Cookies”的资料收集装置。
&nbsp;&nbsp;&nbsp;&nbsp;第三十八条 会员同意合和年在线可使用关于会员的相关资料（包括但不限于合和年在线持有的有关会员的档案中的资料，合和年在线从会员目前及以前在合和年在线平台上的活动所获取的其他资料以及合和年在线通过其他方式自行收集的资料）以解决争议、对纠纷进行调停。会员同意合和年在线可通过人工或自动程序对会员的资料进行评价。
&nbsp;&nbsp;&nbsp;&nbsp;第三十九条 合和年在线采用行业标准惯例以保护会员的资料。会员因履行本协议提供给合和年在线的信息，合和年在线不可以恶意出售或免费共享给任何第三方，以下情况除外：
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1、	提供独立的服务且仅要求服务相关的必要信息的供应商，如印刷厂、邮递公司等；
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2、	具有合法调阅信息权限并从合法渠道调阅信息的政府部门或其他机构，如公安机关、法院；
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3、合和年在线的关联实体；
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;4、经平台使用方或平台使用方授权代表同意的第三方。
&nbsp;&nbsp;&nbsp;&nbsp;第四十条 合和年在线有义务根据有关法律要求向司法机关和政府部门提供会员的资料。在会员未能按照与合和年在线签订的服务协议或者与合和年在线其他会员签订的协议等其他法律文本的约定履行自己应尽的义务时，合和年在线有权根据自己的判断，或者与该笔交易有关的其他会员的请求披露会员的个人信息和资料，并做出评论。会员严重违反合和年在线的相关规则（包括但不限于会员的借款逾期）的，合和年在线有权对会员提供的及合和年在线自行收集的会员的个人信息和资料编辑入网站黑名单，并将该黑名单对第三方披露，且合和年在线有权将会员提交或合和年在线自行收集的会员的资料和信息与任何第三方进行数据共享，以便网站和第三方催收逾期借款及对会员的其他申请进行审核之用，由此可能造成的会员的任何损失，合和年在线不承担法律责任。
&nbsp;&nbsp;&nbsp;&nbsp;第九章 知识产权的保护
&nbsp;&nbsp;&nbsp;&nbsp;第四十一条 合和年在线平台上所有内容，包括但不限于著作、图片、档案、资讯、资料、平台架构、平台画面的安排、网页设计，均由合和年在线或其他权利人依法拥有其知识产权，包括但不限于商标权、专利权、著作权、商业秘密等。
&nbsp;&nbsp;&nbsp;&nbsp;第四十二条 非经合和年在线或其他权利人书面同意，任何人不得擅自使用、修改、复制、公开传播、改变、散布、发行或公开发表合和年在线平台程序或内容。
&nbsp;&nbsp;&nbsp;&nbsp;第四十三条 会员未经合和年在线的明确书面同意不许下载（除了页面缓存）或修改平台或其任何部分。会员不得对合和年在线平台或其内容进行转售或商业利用；不得收集和利用产品目录、说明和价格；不得对合和年在线平台或其内容进行任何衍生利用；不得为其他商业利益而下载或拷贝账户信息或使用任何数据采集、Robots或类似的数据收集和摘录工具。未经合和年在线的书面许可，严禁对合和年在线平台的内容进行系统获取以直接或间接创建或编辑文集、汇编、数据库或人名地址录（无论是否通过Robots、Spiders、自动仪器或手工操作）。另外，严禁为任何未经本使用条件明确允许的目的而使用合和年在线平台上的内容和材料。
&nbsp;&nbsp;&nbsp;&nbsp;第四十四条 未经合和年在线明确书面同意，不得以任何商业目的对合和年在线网站或其任何部分进行复制、复印、仿造、出售、转售、访问、或以其他方式加以利用。未经合和年在线明确书面同意，会员不得用frame或运用frame技巧把合和年在线或其关联公司的商标、标识或其他专有信息（包括图像、文字、网页设计或形式）据为己有。未经合和年在线明确书面同意，会员不得以Meta Tags或任何其他"隐藏文本"方式使用合和年在线或其关联公司的名字和商标。任何未经授权的使用都会终止合和年在线所授予的允许或许可。
&nbsp;&nbsp;&nbsp;&nbsp;第四十五条 尊重知识产权是会员应尽的义务，如有违反，会员应对合和年在线承担损害赔偿等法律责任。
&nbsp;&nbsp;&nbsp;&nbsp;第十章 条款解释、法律适用性及争端解决
&nbsp;&nbsp;&nbsp;&nbsp;第四十六条 本协议是由会员与合和年在线共同签订的，适用于会员在合和年在线的全部活动。本协议内容包括但不限于协议正文条款及已经发布的或将来可能发布的各类规则，所有条款和规则为协议不可分割的一部分，与协议正文具有同等法律效力。
&nbsp;&nbsp;&nbsp;&nbsp;第四十七条 本协议不涉及会员与合和年在线的其他会员之间，因网上交易而产生的法律关系及法律纠纷。但会员在此同意将全面接受并履行与合和年在线其他会员在合和年在线签订的任何电子法律文本，并承诺按照该法律文本享有和（或）放弃相应的权利、承担和（或）豁免相应的义务。
&nbsp;&nbsp;&nbsp;&nbsp;第四十八条 如本协议中的任何条款无论因何种原因完全或部分无效或不具有执行力，则应认为该条款可与本协议相分割，并可被尽可能接近各方意图的、能够保留本协议要求的经济目的的、有效的新条款所取代，而且，在此情况下，本协议的其他条款仍然完全有效并具有约束力。
&nbsp;&nbsp;&nbsp;&nbsp;第四十九条 合和年在线对本服务协议拥有最终的解释权。
&nbsp;&nbsp;&nbsp;&nbsp;第五十条 本协议及其修订的有效性、履行与本协议及其修订效力有关的所有事宜，将受中国法律管辖，任何争议仅适用中国法律。
&nbsp;&nbsp;&nbsp;&nbsp;第五十一条 本协议签订地为中国深圳市。因本协议所引起的会员与合和年在线的任何纠纷或争议，首先应友好协商解决，协商不成的，会员在此完全同意将纠纷或争议提交深圳市彩付宝网络技术公司所在地有管辖权的人民法院诉讼解决。
                  	</textarea>
				</div>
				<table width="500" border="0" cellspacing="0" cellpadding="0" style="margin-left:150px;margin-top: 20px;">
					<tr>
						<td><input type="button" id="btn_register" value="注册" /></td>
					</tr>
					<tr height="20"></tr>
				</table>
			</div>
			<div class="dimenkan_right">
				<span>已是会员<a onclick="login()" class="zc_dengluan">立即登录</a> </span>
			</div>
			<div class="cle"></div>
		</div>
		<div class="cle"></div>
	</div>
	<%--	<div class="center">--%>
	<%--		<div class="wytz_center">--%>
	<%--			<div class="login_center">--%>
	<%--				<div class="login_center_top">会员注册</div>--%>
	<%--				<form>--%>
	<%--					<div class="login_center_left">--%>
	<%--						<table width="500" border="0">--%>
	<%--							<tr height="10">--%>
	<%--								<th align="right"></th>--%>
	<%--								<td><span style="color: red;margin-left: 32px;" id="s_email" class="formtips"></span></td>--%>
	<%--							</tr>--%>
	<%--							<tr>--%>
	<%--								<td width="150" align="right"><b>用户名：</b></td>--%>
	<%--								<td><strong>*</strong><input type="text" class="login_tex1" anme="paramMap.userName" id="userName" /></td>--%>
	<%--							</tr>--%>
	<%--							<tr height="5">--%>
	<%--								<th align="right"></th>--%>
	<%--								<td><span style="color: red;margin-left: 32px;" id="s_userName" class="formtips"></span></td>--%>
	<%--							</tr>--%>
	<%--							<tr>--%>
	<%--								<td width="150" align="right"><b>密码：</b></td>--%>
	<%--								<td><strong>*</strong><input type="password" class="login_tex1" name="paramMap.password" id="password" /></td>--%>
	<%--							</tr>--%>
	<%--							<tr height="5">--%>
	<%--								<th align="right"></th>--%>
	<%--								<td><span style="color: red;margin-left: 32px;" id="s_password" class="formtips"></span>--%>
	<%--							</tr>--%>
	<%--							<tr>--%>
	<%--								<td width="150" align="right"><b>确认密码：</b></td>--%>
	<%--								<td><strong>*</strong><input type="password" class="login_tex1" name="paramMap.confirmPassword" id="confirmPassword" /></td>--%>
	<%--							</tr>--%>
	<%--							<tr height="5">--%>
	<%--								<th align="right"></th>--%>
	<%--								<td><span style="color: red;margin-left: 32px;" id="s_confirmPassword" class="formtips"></span></td>--%>
	<%--							</tr>--%>
	<%--							<tr>--%>
	<%--								<td width="150" align="right"><b>手机号：</b></td>--%>
	<%--								<td><strong>*</strong><input type="text" class="login_tex1" name="paramMap.telephone" id="telephone" /><a href="javascript:void()" onclick="getTelephoneCode()" id="ida"--%>
	<%--									disable="no"><strong id="message">获取验证码</strong> </a>--%>
	<%--								</td>--%>
	<%--							</tr>--%>
	<%--							<tr height="5">--%>
	<%--								<th align="right"></th>--%>
	<%--								<td><span style="color: red;margin-left: 32px;" id="s_telephone" class="formtips"></span>--%>
	<%--								</td>--%>
	<%--							</tr>--%>
	<%--							<tr>--%>
	<%--								<td width="150" align="right"><b>手机验证码：</b></td>--%>
	<%--								<td><strong style=" float:left; margin-top:10px;">*</strong><input type="text" class="login_tex2" name="paramMap.confirmTelephone" id="confirmTelephone" /><span--%>
	<%--									style="margin-left:10px; float:left; margin-top:8px;">输入您收到的短信验证码</span></td>--%>
	<%--							</tr>--%>
	<%--							<tr height="5">--%>
	<%--								<th align="right"></th>--%>
	<%--								<td><span style="color: red;margin-left: 32px;" id="s_confirmTelephone" class="formtips"></span>--%>
	<%--								</td>--%>
	<%--							</tr>--%>
	<%--							<tr>--%>
	<%--								<td width="150" align="right"><b>推荐人(选填)：</b></td>--%>
	<%--								<td><input type="text" class="login_tex1" style="margin-left:25px;" name="paramMap.refferee" id="refferee" /> <span id="sp_refferee"></span></td>--%>
	<%--							</tr>--%>
	<%--							<tr height="5">--%>
	<%--								<th align="right"></th>--%>
	<%--								<td><span style="color: red;margin-left: 32px;" id="s_refferee" class="formtips"></span>--%>
	<%--							</tr>--%>
	<%--							<tr height="5">--%>
	<%--								<th align="right"></th>--%>
	<%--								<td><span style="color: red;margin-left: 32px;" id="s_code" class="formtips"></span></td>--%>
	<%--							</tr>--%>
	<%--							<tr>--%>
	<%--								<td>&nbsp;</td>--%>
	<%--								<td><input type="checkbox" id="agre" checked="checked" /><span>我已经阅读并同意</span><a href="javascript:void()" onclick="fff()" style="color:#f07a05;">《注册协议》</a></td>--%>
	<%--							</tr>--%>
	<%--							<tr height="5"></tr>--%>
	<%--							<tr>--%>
	<%--								<td>&nbsp;</td>--%>
	<%--								<td><textarea readonly="readonly" id="idTextarea">合和年信贷由北京弘合柏基信息科技有限公司的全资子公司南京弘合柏基信息科技有限公司成立并运营，以下所称"本网站"即包含了网站本身及网站运营商南京弘合柏基信息科技有限公司。在您注册成为本网站出借人用户前，您已充分阅读并理解本《注册协议（出借人）》（"本协议"）的所有条款。您同意以下条款并注册后，将有权依据本协议的条款接受本网站的服务，同时有义务接受本协议条款的约束。  一、本协议的文本 1.1 本协议的内容包括以下全部条款以及本网站已经发布的及将来可能发布的与出借人用户有关的各项规则，该等规则均为本协议不可分割的一部分，与以下所列条款具有同等法律效力。1.2 本网站有权根据需要修改本协议的内容。如以下条款或本网站各项规则有任何变更，本网站将在网站上刊载公告。经修订的相关条款和规则一经公告，即于公告规定的特定生效日期自动生效。请您适时关注本网站关于相关条款和规则的公告，如不同意该等变更，请您在该等变更的公告刊载之日起72小时内以您在本网站注册时提供的个人邮箱向本网站的服务邮箱【请插入本网站服务邮箱】发送邮件表明希望终止本协议（"注册终止申请"），本网站确认收到您的邮件后将与您协商本协议终止后双方义务的履行。如您在本条所述时限内未发送注册终止申请，则本条所述时限届满之时，视为您已经同意接受该等变更，经修订的相关条款和规则一经公告，即于公告规定的特定生效日期自动生效并对您产生法律约束力。1.3 您只要勾选位于注册页面下方的"我同意注册协议"选项并按照本网站的流程成功注册后，本协议即产生法律效力。您不得以未签署书面协议为由否认本协议的效力。本协议是由您与本网站共同签订的，适用于您在本网站的全部活动。在您注册成为用户时，您已经阅读、理解并接受本协议的全部条款及各类规则，如有违反而导致任何法律后果的发生，您将以自己的名义独立承担所有相应的法律责任。1.4 本协议不涉及您与本网站的其他用户之间因网上交易而产生的法律关系及法律纠纷。  二、注册用户的身份限制 2.1 作为本网站出借人用户，您必须是中国大陆公民，年龄在18周岁以上，且具有完全的民事权利能力及民事行为能力。如不具备上述资格，您应立即停止在本网站的注册程序、停止使用本网站服务，本网站有权随时终止您的注册进程及本网站服务，您应对您的注册给本网站带来的损失承担全额赔偿责任，且您的监护人（如您为限制民事行为能力的自然人）或您的实际控制人（如您为实体）应承担连带责任。2.2 在注册时和使用本网站服务的所有期间，您应提供您自身的真实资料和信息，并保证自您注册之时起至您使用本网站服务的所有期间，其所提交的所有资料和信息（包括但不限于电子邮件地址、联系电话、联系地址、邮政编码、个人身份信息、征信信息等）真实、准确、完整，且是最新的。  本网站的服务内容 3.1 本网站通过本网站以及其他渠道和方式等向您提供以下服务中的一项或多项：(1) 撮合您与借款人形成借贷交易的居间服务； (2) 您对借款人的既有债权的转让；(3) 信用咨询及贷后管理服务；及 (4) 其他相关服务。本网站向您提供服务的具体内容由本网站与您另行签署的《信用咨询及居间服务协议（借款人）》及其他协议约定[是否需加上债权转让协议HW：加上"其他协议"，以涵盖可能与出借人签署的所有合同。]。 3.2 本网站就向您提供的服务是否收取服务费及服务费的具体标准和规则由本网站与您另行签署的《信用咨询及居间服务协议（借款人）》及其他协议，以及本网站公布的规则确定。  用户使用限制 4.1 您不得利用本网站或本网站服务从事任何不符合中国法律法规或侵犯他人权益的活动。本网站在发现您从事该等活动时，有权不经通知而立即停止您对本网站的全部或部分功能的使用。4.2 在使用本网站提供的任何服务（包括但不限于站内信服务、群组服务、论坛服务或其他电子邮件转发服务）的过程中，您不得发送、公布或展示任何垃圾邮件、信息或其他可能违反中国法律法规及本协议的内容。本网站在发现您从事该等活动或发布该等内容时，有权不经您同意而删除该等内容，并有权不经通知而立即暂停或停止您对本网站的全部或部分功能的使用。4.3 您在注册时向本网站提交的电子邮箱、用户名、密码及安全问题答案是您在本网站的唯一识别信息。您注册成功后，不得将注册的电子邮箱、用户名、密码及安全问题答案转让或授权给第三方使用。您确认，使用您的用户名和密码登录本网站后在本网站的一切行为以及以您在本网站注册时提交的个人电子邮箱发送邮件的行为均代表您本人并由您承担相应的法律后果。4.4 本网站的所有内容，包括但不限于文本、数据、图片、音频、视频、源代码和其他所有信息，均由本网站享有知识产权。未经本网站事先书面同意，您或其他任何人不得复制、改编、传播、公布、展示或以任何其他方式侵犯本网站的知识产权。  用户信息的保护及披露 5.1 您同意本网站在业务运营中收集和储存您的用户信息，包括但不限于您自行提供的资料和信息，以及本网站自行收集、取得的您在本网站的交易记录和使用信息等。本网站收集和储存您的用户信息的主要目的在于提高为您提供服务的效率和质量。5.2 您同意本网站在业务运营中使用您的用户信息，包括但不限于(1)根据双方另行签署的《信用咨询及居间服务协议（借款人）》在本网站公示您的相关信息，(2)向本网站的合作机构（该合作机构仅限于本网站为了完成拟向您提供的服务而合作的机构）提供您的用户信息，(3)由人工或自动程序对您信息进行评估、分类、研究，(4)使用您的用户信息以改进本网站的推广；以及(5)使用您提供的联系方式与您联络并向您传递有关业务和管理方面的信息。本网站有时候可能调查多个用户以识别问题或解决争议，特别是本网站可审查您的资料以区分使用多个用户名或别名的用户。 </textarea>--%>
	<%--								</td>--%>
	<%--							</tr>--%>
	<%--							<tr height="5"></tr>--%>
	<%--							<tr>--%>
	<%--								<td>&nbsp;</td>--%>
	<%--								<td><input type="button" style="margin-left:25px;" id="btn_register" value="注  册" /></td>--%>
	<%--							</tr>--%>
	<%--						</table>--%>
	<%--					</div>--%>
	<%--				</form>--%>
	<%--				<div class="login_center_right">--%>
	<%--					<ul>--%>
	<%--						<li>帮助他人 快乐自己 收获利息</li>--%>
	<%--						<li>助您创业 资金周转 分期偿还</li>--%>
	<%--						<li>收益稳定可靠回报高</li>--%>
	<%--						<li>交易安全快捷有保障</li>--%>
	<%--						<li style="list-style-type:none; margin-top:30px;"><input type="button" value="马上登录" onclick="login()" /></li>--%>
	<%--					</ul>--%>
	<%--				</div>--%>
	<%--				<div class="cle"></div>--%>
	<%--			</div>--%>
	<%--			<div class="cle"></div>--%>
	<%--		</div>--%>
	<%--	</div>--%>
	<div class="cle"></div>
	<jsp:include page="/include/footer.jsp"></jsp:include>
	<script type="text/javascript" src="css/popom.js"></script>
	<script type="text/javascript" src="script/jquery.shove-1.0.js"></script>
	<script type="text/javascript" src="script/jbox/jquery.jBox-2.3.min.js"></script>
	<script type="text/javascript" src="script/jbox/jquery.jBox-zh-CN.js"></script>
	<script>
		function login() {
			window.location.href = "login.do";
		}
		//验证码
		function switchCode() {
			var timenow = new Date();
			$("#codeNum").attr("src", "admin/imageCode.do?pageId=userregister&d=" + timenow);
		}
		//回车登录
		document.onkeydown = function(e) {
			if (!e)
				e = window.event;
			if ((e.keyCode || e.which) == 13) {
				regg();
			}
		}

		//动态展示协议
		$(document).ready(function(){
			   $("#show").toggle(function(){
			    $("#all").fadeTo("slow",1);
			   },function(){
			    $("#all").fadeOut("slow");
			   });
		});
		
		//获取手机验证码
		function getTelephoneCode() {
			if ($("#ida").attr("disable") == "no") {
				if ($("#telephone").val() == "") {
					$("#s_telephone").attr("class", "formtips onError");
					$("#s_telephone").html("请输入手机号");
				} else if (!/^1[3|4|5|8][0-9]\d{4,8}$/.test($("#telephone").val())) {
					$("#s_telephone").attr("class", "formtips onError");
					$("#s_telephone").html("输入手机号的格式有误");
				} else {
					$("#s_telephone").attr("class", "formtips");
					$("#s_telephone").html("");
					param = {};
					param["paramMap.telephone"] = $("#telephone").val();
					$.post("telephoneCode.do", param, function(data) {
<%--						if (s_telephone == '8') {--%>
<%--							$('#btn_register').attr('value', '免费注册');--%>
<%--							$("#s_telephone").html("手机号码已经存在，请重新输入");--%>
<%--							falg = true;--%>
<%--						}--%>
						if (data == "-1") {
							$("#s_telephone").attr("class", "formtips onError");
							$("#s_telephone").html("验证码获取失败");
						}
						if (data == "5") {
							$("#s_telephone").attr("class", "formtips onError");
							$("#s_telephone").html("该手机号码已被使用");
						} else {
							$("#s_telephone").attr("class", "formtips");
							$("#s_telephone").html("");
							telephoneTimer();
						}
					});
				}
			}
		}
		//倒计时
		var count = 60;
		var timer;
		function telephoneTimer() {
			$("#ida").attr("disable", "yes");
			if (count == 1 || count == 2) {
				$("#message").text("获取成功..." + count + "s");
			} else {
				$("#message").text("获取中..." + count + "s");
			}
			timer = window.setTimeout("telephoneTimer()", 1000);
			count = count - 1;
			if (count == -1) {
				window.clearTimeout(timer);
				count = 10;
				$("#message").text("获取验证码");
				$("#ida").attr("disable", "no");
			}
		}
	</script>
	<script>
		$(document).ready(function() {
			$("#btn_register").click(function() {
				regg();
			});
			$("#refferee").focus(function(){
				$("#sp_refferee").html("");
				$("#sp_refferee").attr("class", "formtips");
			});
			//失去焦点
			$("form :input").blur(function() {
				//userName
				if ($(this).attr("id") == "userName") {
					if (this.value == "") {
						$("#s_userName").attr("class", "formtips onError");
						$("#s_userName").html("请输入登录用户名");
					} else if (this.value.length < 5 || this.value.length > 25) {
						$("#s_userName").attr("class", "formtips onError");
						$("#s_userName").html("用户名长度为5-25个字符");
					} else if (!/^[A-Za-z0-9_]+$/.test(this.value)) {
						$("#s_userName").attr("class", "formtips onError");
						$("#s_userName").html("用户名由字母数字下划线组成");
					} else {
						$("#s_userName").html("");
						checkRegister('userName');
						$("#s_userName").attr("class", "formtips");
					}
				}
				//password
				if ($(this).attr("id") == "password") {
					pwd = this.value;
					if (this.value == "") {
						$("#s_password").attr("class", "formtips onError");
						$("#s_password").html("请设置您的密码");
					} else if (this.value.length < 6) {
						$("#s_password").attr("class", "formtips onError");
						$("#s_password").html("密码长度为6-20个字符");
					} else {
						$("#s_password").html("");
						checkRegister('password');
						$("#s_password").attr("class", "formtips");
					}
				}
				//confirmPassword
				if ($(this).attr("id") == "confirmPassword") {
					if (this.value == "") {
						$("#s_confirmPassword").attr("class", "formtips onError");
						$("#s_confirmPassword").html("请再次输入密码确认");
					} else if (this.value != $("#password").val()) {
						$("#s_confirmPassword").attr("class", "formtips onError");
						$("#s_confirmPassword").html("两次输入的密码不一致");
					} else {
						$("#s_confirmPassword").attr("class", "formtips");
						$("#s_confirmPassword").html("");
					}
				}
				//telephone
				if ($(this).attr("id") == "telephone") {
					if (this.value == "") {
						$("#s_telephone").attr("class", "formtips onError");
						$("#s_telephone").html("请输入手机号");
					} else if (!/^1[3|4|5|8][0-9]\d{4,8}$/.test(this.value)) {
						$("#s_telephone").attr("class", "formtips onError");
						$("#s_telephone").html("输入手机号的格式有误");
					} else {
						$("#s_telephone").attr("class", "formtips");
						$("#s_telephone").html("");
					}
				}
				//confirmTelephone
<%--				if ($(this).attr("id") == "confirmTelephone") {--%>
<%--					if (this.value == "") {--%>
<%--						$("#s_confirmTelephone").attr("class", "formtips onError");--%>
<%--						$("#s_confirmTelephone").html("请输入手机验证码");--%>
<%--					} else {--%>
<%--						$("#s_confirmTelephone").attr("class", "formtips");--%>
<%--						$("#s_confirmTelephone").html("");--%>
<%--					}--%>
<%--				}--%>
			//----add by houli  推荐人 refferee
				if ($(this).attr("id") == "refferee") {
					if (this.value != "") {//如果推荐人不为null，则进行判断，判断经纪人是否有效
						if(isNaN(this.value)){
							$("#s_refferee").attr("class", "formtips onError");
							$("#s_refferee").html("推荐人为该推荐人的ID,由数字组成");
						}else{
							$.post("queryValidRecommer.do", {refferee : this.value}, function(data) {
								if (data == 1) {
									$("#s_refferee").attr("class", "formtips onError");
									$("#s_refferee").html("推荐人不存在");
								} else {
									$("#s_refferee").attr("class", "formtips");
									$("#s_refferee").html("");
								}
							});
						}
					} else {
						$("#s_refferee").attr("class", "formtips");
						$("#s_refferee").html("");
					}
				}
			});
		});

		//提交
		//验证数据
		function checkRegister(str) {
			var param = {};
			if (str == "userName") {
				param["paramMap.email"] = "";
				param["paramMap.userName"] = $("#userName").val();
			} else {
				param["paramMap.email"] = $("#email").val();
				param["paramMap.userName"] = "";
			}
			$.post("ajaxCheckRegister.do", param, function(data) {
				if (data == 3 || data == 4) {
					if (str == "userName") {
						$("#s_userName").html("该用户已存在");
					}
				}
			});

		}

		function regg() {
			var falg = true;
			if (falg) {
				falg = false;
				var errornum = $("form .onError").length;
				if(errornum>0){
					alert("请填写完整的信息");
					falg = false;
					return false;
				}
				if(isNaN($("#refferee").val())){
					alert("推荐人为该推荐人编号,由数字组成");
					falg = false;
					return false;
				}
			
				if ($("#userName").val() == "") {
					$("#s_userName").html("请输入登录用户名");
					falg = false;
					return false;
				}else if ($("#userName").val().length < 5 || $("#userName").val().length > 25) {
					$("#s_userName").html("用户名长度为5-25个字符");
					falg = false;
					return false;
				} else if (!/^[A-Za-z0-9_]+$/.test($("#userName").val())) {
					$("#s_userName").html("用户名只能有数字字母下划线组成");
					falg = false;
					return false;
				} else if ($("#password").val() == "") {
					$("#s_password").html("请设置您的密码");
					falg = false;
					return false;
				} else if ($("#confirmPassword").val() == "") {
					$("#s_confirmPassword").html("请再次输入密码确认");
					falg = false;
					return false;
				} else if ($("#telephone").val() == "") {
					$("#s_telephone").html("请输入手机号码");
					falg = false;
					return false;
				}else if (!/^1[3|4|5|8][0-9]\d{4,8}$/.test($("#telephone").val())) {
					$("#s_telephone").attr("class", "formtips onError");
					$("#s_telephone").html("输入手机号的格式有误");
					falg = false;
					return false;
				}
<%--				else if ($("#confirmTelephone").val() == "") {--%>
<%--					$("#s_confirmTelephone").html("请输入手机验证码");--%>
<%--					falg = false;--%>
<%--					return false;--%>
<%--				}--%>
				else if (errornum > 0) {
					alert("请正确填写注册信息");
					falg = false;
					return false;
				} else {
					if (!$("#agre").attr("checked")) {
						alert("请勾选我已阅读并同意《注册协议》");
						falg = false;
						return false;
					}
				}
				$('#btn_register').attr('value', '注册中...');
				var param = {};
				
				param["paramMap.passwords"] = "${passwords}"; //彩之云
				param["paramMap.userid"] = "${userid}"; //彩之云

				param["paramMap.pageId"] = "userregister";
				param["paramMap.email"] = $("#email").val();
				param["paramMap.userName"] = $("#userName").val();
				param["paramMap.password"] = $("#password").val();
				param["paramMap.confirmPassword"] = $("#confirmPassword").val();
				param["paramMap.telephone"] = $("#telephone").val();
				param["paramMap.confirmTelephone"] = $("#confirmTelephone").val();
				param["paramMap.refferee"] = $("#refferee").val();
				param["paramMap.code"] = $("#code").val();
				param["paramMap.param"] = $("#param").val();
				$.post("register.do", param, function(data){
					alert(data);
					if (data == "注册成功") {
						window.location.href="home.do";
					} else {
						$('#btn_register').attr("value", "免费注册");
					}
				});
			}
		}
	</script>
</body>
</html>
