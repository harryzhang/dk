<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript" src="../xinshou/zzsc.js"></script>
<style type="text/css">
.divtest {
	margin-bottom: 20px;
	border: 1px solid #e5e5e5;
	padding: 10px 40px;
	background: url(../xinshou/images/new/faqbg.png);
}
.divtest:hover {
	background: url(../xinshou/images/new/faqbb.png);
	color: #000;
}
.classical-tag {
	width: 100%;
}
.classical-tag-detail {
	width: 890px;
	float: left;
}
.classical-tag-detail .img {
	display: block;
	width: 109px;
	height: 144px;
	float: left;
	margin-bottom: 20px
}
.classical-tag-detail dl {
	width: 151px;
	padding-left: 10px;
	float: left
}
.classical-tag-detail dt {
	margin-bottom: 6px
}
.classical-tag-detail dt a {
	font-size: 16px;
	font-weight: bold;
	line-height: 26px
}
.classical-tag-detail dd {
	margin-bottom: 8px;
	color: #333;
	width: 151px;
	white-space: nowrap;
	overflow: hidden;
	text-overflow: ellipsis;
	line-height: 14px
}
.classical-tag-detail dd.price {
*height:16px
}
.classical-tag-detail p {
	clear: both;
	color: #999;
	line-height: 24px;
	padding-top: 15px;
	border-top: 1px dashed #ccc;
	height: 149px;
	overflow: hidden
}
.classical-tag-detail p span {
	display: inline-block;
	width: 12px;
	height: 12px;
	background: url(../xinshou/images/bg-index-common.png) no-repeat;
	vertical-align: -2px;
*vertical-align:1px;
	overflow: hidden
}
.classical-tag-detail p span.begin {
	background-position: 0 -292px;
	margin-right: 5px
}
.classical-tag-detail p span.end {
	background-position: 0 -304px;
	margin-left: 5px
}
.classical-tag ul {
	width: 200px;
	float: right
}
.classical-tag li {
	margin-bottom: 1px;
	float: right;
	border: 1px solid #f1f1f1;
	position: relative
}
.classical-tag li span {
	background: url(../xinshou/images/jt.png);
	display: none;
	width: 20px;
	height: 20px;
	font-size: 0;
	position: absolute;
	left: -20px;
	top: 16px
}
.classical-tag li.on span {
	display: block
}
.classical-tag li a {
	display: block;
	height: 52px;
	width: 180px;
	line-height: 52px;
	padding-left: 10px;
	background: url(../xinshou/images/bg-index-common.png) 0 -240px repeat-x;
	overflow: hidden;
	white-space: nowrap;
	text-overflow: ellipsis
}
.classical-tag li.on a {
	background: #cb0f10;
	color: #fff
}
.classical-tag li.on a:hover {
	text-decoration: none
}
.divtest {
	margin-bottom: 20px;
	border: 1px solid #e5e5e5;
	padding: 10px 40px;
	background: url(images/new/faqbg.png);
}
.divtest:hover {
	background: url(images/new/faqbb.png);
	color: #000;
}
</style>
<script>
	var myData = {
	"l":[{
		"p":1001,"d":$("#classproject1")[0].outerHTML.replace("display: none;","").replace("display:none;","")
	}
	,{
		"p":1002,"d":$("#classproject2")[0].outerHTML.replace("display: none;","").replace("display:none;","")
	}
	,{
		"p":1003,"d":$("#classproject3")[0].outerHTML.replace("display: none;","").replace("display:none;","")
	}
	,{
		"p":1004,"d":$("#classproject4")[0].outerHTML.replace("display: none;","").replace("display:none;","")
	}
	,{
		"p":1005,"d":$("#classproject5")[0].outerHTML.replace("display: none;","").replace("display:none;","")
	}
	]
};
  </script>
<div class="classical-tag">
  <div id="classproject">
    <div class="classical-tag-detail">
      <div class="divtest">
        <div style=" color:#F30">如何注册？</div>
        <div>在首页点击注册按钮，并填写相应的资料和联系方式，完成注册</div>
      </div>
      <div class="divtest">
        <div style=" color:#F30">注册人的户籍、年龄等是否有限制？</div>
        <div>注册人限国内超过18岁，有独立民事能力的中国公民。</div>
      </div>
      <div class="divtest">
        <div style=" color:#F30">为什么要提供真实手机号码？</div>
        <div>提供真实可用的手机号码方便会员接受到准确的投资信息、资金变动信息和方便找回密码。</div>
      </div>
      <div class="divtest">
        <div style=" color:#F30">忘记会员名怎么办？</div>
        <div>可以注册用的手机拨打会员服务热线找回会员名，可能需要会员提供相应的证明文件</div>
      </div>
      <div class="divtest">
        <div style=" color:#F30">忘记密码怎么办？</div>
        <div>可以通过会员注册的邮箱和密码问题找回或者重置会员密码。</div>
      </div>
      <div class="divtest">
        <div style=" color:#F30">如何更改绑定的手机号码？</div>
        <div>可以在会员中心— —个人信息— —手机绑定，更改新的手机绑定。</div>
      </div>
      <div class="divtest">
        <div style=" color:#F30">为什么要做个人详细信息完善？</div>
        <div>个人详细信息完善是保证会员提供的信息是真实有效的，提供的身份信息是真实可靠的，用来提升客户账户的安全性。</div>
      </div>
      <div class="divtest">
        <div style=" color:#F30">为什么要绑定银行卡？</div>
        <div>绑定银行卡是第三方资金托管安全方式中重要的一环，也是实名认证的一种方式，资金流动在绑定银行卡内，同时对投资行为也较为方便。</div>
      </div>
      <div class="divtest">
        <div style=" color:#F30">我认证成功后，是否可以更改证件号码？</div>
        <div>不可以。</div>
      </div>
    </div>
  </div>
  <div id="classproject1"  style="display:none;">
    <div class="divtest">
      <div style=" color:#F30">如何注册？</div>
      <div>在首页点击注册按钮，并填写相应的资料和联系方式，完成注册</div>
    </div>
    <div class="divtest">
      <div style=" color:#F30">注册人的户籍、年龄等是否有限制？</div>
      <div>注册人限国内超过18岁，有独立民事能力的中国公民。</div>
    </div>
    <div class="divtest">
      <div style=" color:#F30">为什么要提供真实手机号码？</div>
      <div>提供真实可用的手机号码方便会员接受到准确的投资信息、资金变动信息和方便找回密码。</div>
    </div>
    <div class="divtest">
      <div style=" color:#F30">忘记会员名怎么办？</div>
      <div>可以注册用的手机拨打会员服务热线找回会员名，可能需要会员提供相应的证明文件</div>
    </div>
    <div class="divtest">
      <div style=" color:#F30">忘记密码怎么办？</div>
      <div>可以通过会员注册的邮箱和密码问题找回或者重置会员密码。</div>
    </div>
    <div class="divtest">
      <div style=" color:#F30">如何更改绑定的手机号码？</div>
      <div>可以在会员中心— —个人信息— —手机绑定，更改新的手机绑定。</div>
    </div>
    <div class="divtest">
      <div style=" color:#F30">为什么要做个人详细信息完善？</div>
      <div>个人详细信息完善是保证会员提供的信息是真实有效的，提供的身份信息是真实可靠的，用来提升客户账户的安全性。</div>
    </div>
    <div class="divtest">
      <div style=" color:#F30">为什么要绑定银行卡？</div>
      <div>绑定银行卡是第三方资金托管安全方式中重要的一环，也是实名认证的一种方式，资金流动在绑定银行卡内，同时对投资行为也较为方便。</div>
    </div>
    <div class="divtest">
      <div style=" color:#F30">我认证成功后，是否可以更改证件号码？</div>
      <div>不可以。</div>
    </div>
  </div>
  
  <!--style="display:none;"-->
  
  <div id="classproject2" style="display:none;" >
    <div class="divtest">
      <div style=" color:#F30">充值是否有限额？</div>
      <div>充值依据银行卡网上交易，依据不同银行规定有不同的限额。</div>
    </div>
    <div class="divtest">
      <div style=" color:#F30">充值的钱是充到什么账户里？</div>
      <div>充值的钱充到会员的第三方支付账户里。</div>
    </div>
    <div class="divtest">
      <div style=" color:#F30">是不是必须充到第三方支付账户里面？</div>
      <div>是的。</div>
    </div>
    <div class="divtest">
      <div style=" color:#F30">充值金额范围、到账时间和手续费收取规则分别是怎样的？</div>
      <div>充值金额范围由银行卡网络充值限额决定，每个银行的充值限额不一样；充值实时到账；充值免手续费。</div>
    </div>
    <div class="divtest">
      <div style=" color:#F30">为什么我的账户显示金额冻结？什么时候可以解冻？</div>
      <div>金额冻结是由于会员投资了债权，而该债权没有完全满标，所以投资者的投资额被冻结，当债权满标后成交冻结的余额会划拨至借款人账户，如果流标，金额则会解冻。</div>
    </div>
    <div class="divtest">
      <div style=" color:#F30">如何提现？</div>
      <div>点击会员中心，个人中心的提现按钮可以进行提现操作。</div>
    </div>
    <div class="divtest">
      <div style=" color:#F30">什么是绑定的银行卡？是否取现默认至绑定银行卡？</div>
      <div>绑定的银行卡是会员在银行卡设置时绑定的会员银行卡，默认提现至绑定银行。</div>
    </div>
    <div class="divtest">
      <div style=" color:#F30">可以提现到其他银行卡里面吗？</div>
      <div>可以，需要先将其他银行卡在【会员中心】-【银行卡设置】进行绑定，不能提现至未绑定的银行卡。</div>
    </div>
    <div class="divtest">
      <div style=" color:#F30">提现是否需要费用？</div>
      <div>需要，视第三方支付提现费用而定。</div>
    </div>
    <div class="divtest">
      <div style=" color:#F30">为什么提现需要审核？</div>
      <div>防止被不法分子盗取账户非法提现，造成不必要的损失。</div>
    </div>
  </div>
  <div id="classproject3" style="display:none;" >
    <div class="divtest">
      <div style=" color:#F30">什么是预期年化利率？</div>
      <div>预期年化利率仅是把当前利率换算成年利率来计算的，是一种理论利率，并不是真正的已取得的收益率。</div>
    </div>
    <div class="divtest">
      <div style=" color:#F30">为什么实际收益率比预期年化利率低？</div>
      <div>由于借款还款方式的不同导致每期都会有本金归还，相应的生息资本会逐渐减少，所以实际收益的利息会比预期年化利率低。</div>
    </div>
    <div class="divtest">
      <div style=" color:#F30">什么是等额本金还款？为什么要用等额本金还款？</div>
      <div>在整个还款周期内，每月偿还相同的本金，每月借款利息按月初剩余借款本金计算并逐月结清。因本金逐月返还，剩余本金减少，因此每月的还款金额逐渐减少。</div>
    </div>
    <div class="divtest">
      <div style=" color:#F30">什么是借款人的信用等级？如何评定的？</div>
      <div>借款人的信用等级是根据借款人提供的资料和可查询的交易记录用一定模型测算的。</div>
    </div>
    <div class="divtest">
      <div style=" color:#F30">投资金额有限制吗？</div>
      <div>投资金额为100以上，单笔借款金额为上限。</div>
    </div>
    <div class="divtest">
      <div style=" color:#F30">投资的时候为什么资金会冻结？</div>
      <div>在您确认投资后且借款项目还未满额时，您的资金将会被短暂冻结，当借款项目满标的时候，投资人的投资就会生效。</div>
    </div>
    <div class="divtest">
      <div style=" color:#F30">借款利息和还款时间从什么时候开始计算？</div>
      <div>借款利息和还款时间从借款满标时，借款资金从投资人的第三方托管账户借到的资金划出时候开始计算。</div>
    </div>
    <div class="divtest">
      <div style=" color:#F30">投资人是否要支付费用？</div>
      <div>投资人投资时暂时不支付任何费用。</div>
    </div>
    <div class="divtest">
      <div style=" color:#F30">投资为什么会失败？</div>
      <div>投资失败可能源于以下几种原因：<br>
        1. 投资人账户内资金不够，需要充值；<br>
        2. 有别的投资人先于您投资从而使此投资项目的进度达到了100%；<br>
        3. 此投资项目的投资进度在筹资时间内没有达到100%，出现这种情况时，投资人投资时被冻结的资金自动解冻。</div>
    </div>
    <div class="divtest">
      <div style=" color:#F30">我投资的收益是否可以得到保障？</div>
      <div>由合和年在线的借款项目推荐方100%本息回购。</div>
    </div>
  </div>
  <div id="classproject4" style="display:none;" >
    <div class="divtest">
      <div style=" color:#F30">如何通过网站借款？</div>
      <div>注册帐号后提交借款申请，上传资料，等待网站审核。审核完成发布供投资者投资完成借款流程。</div>
    </div>
    <div class="divtest">
      <div style=" color:#F30">我能借多少钱？</div>
      <div>5000-30万不等。</div>
    </div>
    <div class="divtest">
      <div style=" color:#F30">需要什么认证才能借款？</div>
      <div>完善个人详细信息。</div>
    </div>
    <div class="divtest">
      <div style=" color:#F30">需要提交什么资料？</div>
      <div>1、身份证明（身份证）；<br>
        2、收入证明（银行流水、公司收入证明、税单等）；<br>
        3、工作证明（公司开具的工作证明、劳动合同等）；<br>
        4、居住证明（水电煤账单、信用卡账单或者银行账单）；<br>
        5、担保公司视不同客户要求的其他证明（包括但不限于：自雇人士的经营证明、营业执照、经营场地租赁合同等）。</div>
    </div>
    <div class="divtest">
      <div style=" color:#F30">投资的时候为什么资金会冻结？</div>
      <div>在您确认投资后且借款项目还未满额时，您的资金将会被短暂冻结，当借款项目满标的时候，投资人的投资就会生效。</div>
    </div>
    <div class="divtest">
      <div style=" color:#F30">一般借款利率是多少？由谁决定借款利率？</div>
      <div>最高为银行基准利率的四倍,由网站依据借款人信用等级决定。</div>
    </div>
    <div class="divtest">
      <div style=" color:#F30">借款一般需要几天完成？</div>
      <div>3-5个工作日。</div>
    </div>
    <div class="divtest">
      <div style=" color:#F30">借款需要缴纳什么费用？</div>
      <div>缴纳借款管理费。</div>
    </div>
    <div class="divtest">
      <div style=" color:#F30">怎么评定我的信用等级？</div>
      <div>根据提交的个人收入证明、征信报告等资料综合评定借款人的信用等级。</div>
    </div>
    <div class="divtest">
      <div style=" color:#F30">有哪些还款方式可以选择？</div>
      <div>等额本息、等额本金、按期付息，一次还本。</div>
    </div>
  </div>
  <div id="classproject5" style="display:none;" >
    <div class="divtest">
      <div style=" color:#F30">合和年在线的借款利率是怎么设定的？</div>
      <div>不超过基准利率的四倍，按借款人的不同信用等级划定利率。</div>
    </div>
    <div class="divtest">
      <div style=" color:#F30">借款人的信用等级是如何评定的？</div>
      <div>根据提交的个人收入证明、征信报告等资料综合评定借款人的信用等级。</div>
    </div>
  </div>
  <ul>
    <li class="on"><span></span> <a   title="会员注册" projectid="1001"> 会员注册</a> </li>
    <li><span></span> <a  title="充值提现" projectid="1002"> 充值提现</a> </li>
    <li><span></span> <a  title="我要投资" projectid="1003"> 我要投资</a> </li>
    <li><span></span> <a  title="我要借款" projectid="1004"> 我要借款</a> </li>
    <li><span></span> <a title="其他帮助" projectid="1005"> 其他帮助</a> </li>
  </ul>
</div>
