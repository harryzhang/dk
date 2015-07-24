<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<style> p{margin:5px;} td{padding:5px;} </style>
	<title>合和年在线</title>
</head>
<body style='font-family:微软雅黑; text-align: left;font-size:12px;'>
	<div align='left'>
	<p align='center' style='text-align:center'>
		<b><span style='font-size:15.0pt;'>借款协议 </span></b>
	</p>
	<p style='text-align:right'>协议编号：${(borrow["number"])!''}</p>
	<p align='left'><b><span>甲方（出借人）： </span> </b></p>
	<table style='width:508px;border-collapse:collapse;text-align: center;font-size:12px;' border='1' cellpadding='0' cellspacing='0'>
	<tbody>
		<tr>
			<td align='center' ><p>合和年在线用户名</p></td>
			<td align='center' ><p>出借金额</p></td>
			<td align='center' ><p>借款期限（月）</p></td>
		</tr>
		<#assign visible = false/>
		<!-- 如果用户已登录，并选择的查看的借款标的已放款，且登录的用户为此借款标的借款人或投资人，则用户信息则显示-->
		<#if userId?? && borrow["borrowStatus"]?? && (borrow["borrowStatus"] == 4 || borrow["borrowStatus"] == 5)>
			<#if (borrow["publisher"])?? && borrow["publisher"] == userId>
				<#assign visible = true/>
			<#else>
				<#if investList??>
				<#list investList as invest>
					<#if (invest["userId"])?? && invest["userId"] == userId>
						<#assign visible = true/>
						<#break>
					</#if>
				</#list>
				</#if>
			</#if>
		</#if>
		
		<#if visible>
			<#if investList??>
			<#list investList as invest>
				<tr>
					<td align='center' ><p>${(invest["userName"])!''}</p></td>
					<td align='center' ><p>${(invest["investAmount"])!''}</p></td>
					<td align='center' ><p>${(invest["deadline"])!''}</p></td>
				</tr>
			</#list>
			<#else>
				<tr><td colspan='3' align='center'><p>暂无出借人</p></td></tr>
			</#if>
		<#else>
			<tr><td colspan='3'   align='center'>**成交之后才可查看**</td></tr>
		</#if>
	</tbody>
	</table>
	<p align='left'><b>乙方（借款人）：<#if visible>${(borrow["userName"])!''}<#else>**成交之后才可查看**</#if></b></p>
	<p align='left'><b>身份证：<#if visible>${(borrow["idNo"])!''}<#else>**成交之后才可查看**</#if></b></p>
	<p><b>丙方（借款管理服务方）：深圳市合和年投资咨询有限公司</b></p>
	<p><b>联系方式：0755-82044824</b></p>
	<p><b>丁方（网络服务方）：深圳市彩付宝网络技术有限公司</b></p>
	<p><b>联系方式：0755-82044824</b></p>
	<p></p><p><b>鉴于：</b></p>
	<p>1、丁方是一家在深圳市合法成立并有效存续的有限责任公司，拥有网站（www.hehenian.com）（以下简称“该网站”）的经营权，提供信用咨询，为交易提供信息服务；</p>
	<p>2、乙方已在该网站注册，并承诺其提供给丁方的信息是完全真实的；</p>
	<p>3、甲方承诺对本协议涉及的借款具有完全的支配能力，本协议所涉及的借款为其合法所得的自有资金；并承诺其提供给丁方的信息是完全真实的；</p>
	<p>4、乙方有借款需求，甲方亦同意向乙方提供借款，双方有意建立借贷关系；</p>
	<p>5、丙方是一家在广东省深圳市合法成立并有效存续的有限责任公司；负责为甲乙双方报告订立合同的机会、负责审查乙方的借款申请及相关文件，
	对乙方是否具备还款能力等情况进行判断、负责本合同项下借款的日常管理工作，并依据与乙方的约定代乙方对甲方的逾期借款进行无条件受让。</p>
	<p>6、乙方与丙方就乙方提供本次交易有关服务事宜已经或将要签署的《借款咨询服务协议》。</p>
	<p></p><p><b>各方经协商一致，于 [ <#if visible>${(borrow["auditTime"])?string("yyyy")}<#else>****</#if> ] 年 [ <#if visible>${(borrow["auditTime"])?string("MM")}<#else>**</#if> ] 月 [ <#if visible>${(borrow["auditTime"])?string("dd")}<#else>**</#if> ] 日签订如下协议，共同遵照履行：</b></p>
	<p></p><p><b>第一条借款基本信息</b></p>
	<table style='border-collapse:collapse;width:508px;font-size:12px;' border='1' cellpadding='0' cellspacing='0'>
		<tr>
			<td><p>借款本金数额</p></td><td><p>${(borrow["borrowAmount"])!''}</p></td>
		</tr>
		<tr>
			<td><p>还款方式</p></td>
			<td><p>按月付息到期还本</p></td>
		</tr>
		<tr>
			<td><p>借款年利率</p></td>
			<td><p>${(borrow["annualRate"])!''}<br/></p></td>
		</tr>
		<tr>
			<td><p>借款期限（月）</p></td><td><p>${(borrow["deadline"])!''}</p></td>
		</tr>
		<tr>
			<td><p>起息日（放款日）</p></td><td><p><#if visible>${(borrow["auditTime"])?string("yyyy-MM-dd")}<#else>********</#if></p></td>
		</tr>
		<tr>
			<td><p>还款日（遇节假日不顺延）</p></td><td><p><#if visible>${(borrow["auditTime"])?string("dd")}<#else>********</#if></p></td>
		</tr>
		<tr>
			<td><p>最终到期日</p></td><td><p><#if visible>${(borrow["endTime"])?string("yyyy-MM-dd")}<#else>********</#if></p></td>
		</tr>
		<tr>
			<td><p>乙方提现账号</p></td><td><p><#if visible>${(borrow["cardNo"])!''}<#else>********</#if></p></td>
		</tr>
		<tr>
			<td><p>借款用途</p></td><td><p>${(borrow["borrowTitle"])!''}</p></td>
		</tr>
	</table>
	<p>注：1、借款期限是指自起息日起至最终到期日（全部借款到期日）止的期间。</p>
	<p>2、起息日（放款日）是指借款资金由上海汇付数据服务有限公司支付平台（以下简称“汇付天下”）从甲方的托管账户成功划出的日期，以汇付天下凭证为准。</p>
	<p>3、最终到期日是指借款期限最后一个月的还款日。</p>
	<p></p><p><b>第二条各方权利和义务</b></p>
	<p><b>(一)<u>甲方的权利和义务</u></b>
	</p><p>1、甲方应按合同约定的借款日或乙丙双方另行约定的其他日期将足额的借款本金支付给乙方。</p>
	<p>2、甲方同意向乙方出借相应款项时，已委托丁方向汇付天下发出指令在本协议生效时将该笔借款由甲方托管账户直接划付至乙方托管账户。</p>
	<p>3、甲方保证其所用于出借的资金来源合法，甲方是该资金的合法所有人，如果第三人对资金归属、合法性问题发生争议，由甲方负责解决。如甲方未能解决，则放弃享有其所出借款项所带来的利息收益。</p>
	<p>4、甲方享有其所出借款项所带来的利息收益。</p>
	<p>5、如乙方违约，甲方有权要求丁方提供其已获得的乙方信息，乙方同意丁方向甲方提供该等信息。</p>
	<p>6、甲方应缴纳其因收取本协议项下的利息所需依法缴纳的税费。</p>
	<p>7、如乙方还款不足以偿还约定本金、利息及违约金的，各方均同意按照逾期罚息、提前结清违约金、手续费、咨询费、利息、本金的顺序冲销还款。</p>
	<p><b>(二)<u>乙方权利和义务</u></b></p>
	<p>1、乙方必须按期足额向甲方支付每期应还本金和利息。</p>
	<p>2、如乙方承诺以其应收账款或其他财产作为借款的还款担保/来源（乙方承诺的形式包括但不限于向丙方出具书面文件或其他形式），在本协议及《借款咨询服务协议》履行完毕之前，未经丙方同意，乙方不得对该应收账款或其他财产进行质押、抵押、转让或通过其他形式进行处置。 </p>
	<p>3、乙方按期向甲、丙、丁三方支付本合同项下的借款本金、利息、手续费等其他的相关费用，乙方可采取如下的还款方式进行还款：</p>
	<p>（1）自主还款，乙方将当期应还款项存入乙方在汇付天下的个人托管账户，并同意委托丁方根据《借款协议》、《债权转让协议》、《借款咨询服务协议》的约定向汇付天下发送指令将属于各方的费用划转至各自在汇付天下的托管账户；</p>
	<p>（2）以委托代扣方式还款（具体约定见《代扣授权书》）；</p>
	<p>3、乙方承诺所借款项不用于任何违法用途。</p>
	<p>5、乙方应确保其提供的信息和资料的真实性，不得提供虚假信息或隐瞒重要事实。</p>
	<p>6、乙方有权了解其在丁方的信用评审进度及结果。</p>
	<p>7、乙方不得将本协议项下的任何权利义务转让给任何其他方。</p>
	<p><b>(三)<u>丙方的权利和义务</u></b></p>
	<p>1、丙方应负责审批乙方的借款申请，对乙方是否具备还款能力等情况进行判断并负责本合同项下借款的日常管理工作。丙方有权代甲方在有必要时对乙方进行贷款的违约提醒及催收工作，包括但不限于电话通知、发律师函、对乙方提起诉
		讼等。甲方在此确认明确委托丙方为其进行以上工作，并授权丙方可以将此工作委托给其他方进行。乙方对前述委托的提醒、催收事项已明确知晓并应积极配合。</p><p>2、如乙方归还借款本息逾期，甲、乙、丙三方同意丙方代乙方在逾期后第3个工作日先行向甲方偿还乙方逾期归还之借款本息（即借款剩余本金加上当期应还利息）。
		甲、乙、丙三方同意在丙方完成先行代乙方向甲方偿还乙方逾期归还的借款本息后，丙方取代甲方成为乙方的债权人。乙方除了依据乙方及丙方所签署的《借款咨询服务协议》
	的约定向丙方支付相关费用外，乙方还应向丙方履行本协议项下其应向甲方履行的义务。</p>
	<p>3、丙方有义务为甲乙双方提供订立合同的机会，促成甲乙双方成立借贷关系。丙方有权就为本合同借款所提供的服务向乙方收取咨询费，咨询费的收取方式由乙丙双方另行约定。</p>
	<p>4、丙方有权了解乙方的信息和借款进展情况。</p>
	<p><b>(四)<u>丁方的权利和义务</u></b></p>
	<p>1、丁方为甲乙双方提供稳定、安全的金融服务网络平台。</p>
	<p>2、丁方有权就为本合同项下借款所提供的服务向乙方收取手续费：手续费按月计算，每月收取，每月手续费按借款金额的0.2%计算，
		本次借款的手续费为人民币（大写）：<u>${(borrow["feeAmtCn"])!''}</u>，（小写）：￥<u>${(borrow["feeAmt"])!''}</u>元。若还款账户内余额不足时，首先冲抵手续费。</p>
	<p>3、丁方应对甲方和乙方的信息及本协议内容保密；如任何一方违约，或因相关权力部门要求（包括但不限于法院、仲裁机构、金融监管机构等），丁方有权披露。</p>
	
	<p><b>第三条违约责任</b></p>
	<p>1、合同各方均应严格履行合同义务，非经各方协商一致或依照本协议约定，任何一方不得解除本协议。</p>
	<p>2、如出现以下任何一种情形，丙方有权单方决定提前解除本合同，并要求乙方立即向本协议的债权人偿还本协议的全部债务，包括但不限于未偿还的本金、利息、咨询费、逾期罚息、违约金等全部费用：</p>
	<p>（1）乙方未经丙方的书面同意，将其作为还款担保的应收账款或其他财产（如有)进行质押、抵押、转让或通过其他形式进行处置。</p>
	<p>（2）乙方、乙方控股的或被控股的公司或者其他关联公司发生重大财务亏损、资产损失、停业、歇业、被宣告破产、解散、被吊销营业执照、被撤销，或发生对其产生不利后果的任何诉讼、仲裁或刑事、行政处罚，丙方认为可能损害乙方的偿债能力。</p>
	<p>（3）、乙方逾期支付任何一期还款超过30天，或连续逾期三期以上（含三期），或累计逾期达五期以上（含五期），或乙方在逾期后出现逃避、拒绝沟通或拒绝承认欠款事实等恶意行为。</p>
	<p>（4）出现可能影响本协议履行的其他情形，丙方认为需要提前解除本协议。</p>
	
	<p>3、如本协议提前解除，甲方、丙方和丁方有权采取以下救济措施：</p>
	<p>（1）丙方有权代乙方向甲方偿还的借款本息，并取得甲方在本协议中对乙方的权利；</p>
	<p>（2）乙方在网站的账户里有任何余款，丁方有权按照本协议第三条第5款的清偿顺序将乙方的余款用于清偿，并要求乙方支付因此产生的相关费用；</p>
	<p>（3）如乙方对丙方或丁方的任何关联公司有应收账款或其他债权，无须乙方的另行同意，丙方和丁方有权直接要求其关联公司将应付乙方的款项直接支付给本协议及《借款咨询服务协议》项下的有关债权人，直至乙方在本协议及《借款咨询服务协议》项下的应付款项全部支付完毕为止。 </p>
	<p>4、任何一方违约，违约方应承担因违约使得其他各方产生的费用和损失，包括但不限于调查、诉讼费、律师费等，应由违约方承担。</p>
	<p>5、乙方的每期还款均应按照如下顺序清偿：</p>
	<p>（1）因乙方逾期还款时乙方向丙方支付的逾期罚息；</p>
	<p>（2）乙方提前结清应支付予丙方的提前结清违约金；</p>
	<p>（3）乙方应支付予丁方的手续费；</p>
	<p>（4）乙方应支付予丙方的咨询费；</p>
	<p>（5）本协议项下借款的利息；</p>
	<p>（6）借款本金。</p>
	<p>6、如果乙方逾期支付任何一期还款超过30天，或连续逾期三期以上（含三期），或累计逾期达五期以上（含五期），或乙方在逾期后出现逃避、拒绝沟通或拒绝承认欠款事实等恶意行为，丁方有权将乙方的“
		逾期记录”记入丁方的“信用信息库”，丁方不承担任何法律责任。</p>
	<p>7、如果乙方逾期支付任何一期还款超过30天，或连续逾期三期以上（含三期），或累计逾期达五期以上
		（含五期），或乙方在逾期后出现逃避、拒绝沟通或拒绝承认欠款事实等恶意行为，丁方有权将乙方违约失信的相关信息及乙方其他信息向媒体、用人单位、公安机关、检查机关
		、法律机关披露，丁方不承担任何责任。</p>
	<p>8、本借款协议中的所有甲方与乙方之间的借款均是相互独立的，一旦乙方逾期未归还借款本息，任何一个甲方有权单独向乙方追
		索或者提起诉讼。如乙方提供虚假信息的，丁方亦可单独向乙方追索或者提起诉讼。</p>
	<p><b>第五条 提前结清</b></p>
	<p>1、提前偿还全部剩余借款</p>
	<p>乙方提前清偿全部剩余借款时，如乙方提前清偿的要求得到甲方允许时，甲方委托丙方代为处理提前偿还全部剩余借款的相关事项，
		乙方除须还清截至还款当日为止的剩余本金及当期应还利息、手续费、咨询费（含逾期产生的所有滞纳金代偿费用）之外，还须向丙方支付提前结清违约金。
		因提前清偿所产生的提前结清违约金由乙丙双方另行决定，与甲方无关。</p>
	<p>2、出借人不接受部分提前结清。若乙方申请部分提前结清的，部分提前结清资金将由丁方代乙方保管，不提前冲减费用及本息，借款本息、咨询费及其他费用仍按期从中冲减；若余额不足，乙方应按约定的还款方式补足差额部分。</p><p></p>
	<p><b>第六条法律及争议解决</b></p>
	<p>本协议的签订、履行、终止、解释均适用中华人民共和国法律，如发生争议，各方应将争议提交丙方所在地的人民法院管辖。</p><p></p>
	<p><b>第七条附则</b></p>
	<p>1、本协议采用电子文本形式制成，并永久保存在丁方为此设立的专用服务器上备查，各方均认可该形式的协议效力。<br/></p>
	<p>2、本协议自文本最终生成之日生效。</p>
	<p>3、本协议签订之日起至借款全部清偿之日止，甲方或乙方的下列信息如发生变更的，其应当在相关信息发生变更三日内将更新后的信息提供给丁方：本人、本人的家庭联系人及紧急联系人、工作单位、居住地址、住所电话、手机号码、电子邮箱、银行账户的变更。若因任何一方不及时提供上述变更信息而带来的损失或额外费用应由该方承担。</p>
	<p>4、甲方、丙方、丁方有权将其在本合同项下的权利部分或全部转让给第三方而无须征得乙方同意。债权转让后，受让人取得与债权有关的从权利。 </p>
	<p>5、如果本协议中的任何一条或多条违反适用的法律法规，则该条将被视为无效，但该无效条款并不影响本协议其他条款的效力。 </p>
	</div>
	</body>
</html>