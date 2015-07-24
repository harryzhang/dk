<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<jsp:include page="/include/mobile/head.jsp"></jsp:include>
<title>常见问题</title>
</head>
<body>
<jsp:include page="/include/mobile/loading.jsp"></jsp:include>
<h1 class="t title"><span>常见问题</span></h1>
<div class="wrap animate-waves" id="wrap" style="display:none;">
  <div class="p fqa">
    <dl id="fqaList">
      <dt><span>1、如何达到年化利率10%？</span></dt>
      <dd>答:
        实际每月收益率0.833%，每月您收到本金和利息两部分。本金每个月是相等的，利息是剩余投资金额的0.833%。 例如：您投资了12万，12个月的标。第1个月您收到本金=12万/12=1万，利息=12万*10%/12=0.1万，您总共收到1万+0.1万=1.1万，剩余投资金额11万。 第2月您收到本金=120000/12=10000元，利息=110000*10%/12=916.67元，您总共收到10000+916.67=10916.67元。您要实现每年10%的收益，只需将每月返回到您账户的上的本金再选择标的投资即可。如果您将利息再投资，那么您每年的收益会超过10%。</dd>
      <dt><span>2、什么时候开始计息？</span></dt>
      <dd>答:
        满标放款当天开始计息。</dd>
      <dt><span>3、平台都收取哪些费用？</span></dt>
      <dd>答:
        平台不收取投资人任何费用。</dd>
      <dt><span>4、充值、提现是否收取手续费？</span></dt>
      <dd>答:
        第三方支付平台收取的充值、提现手续费全部由平台支付，投资人不需要承担任何费用。</dd>
      <dt><span>5、充值是否有充值上限？</span></dt>
      <dd>答:
        平台不设充值上限，但使用网银充值时，不同银行对各人有不同的限额。如单笔充值额度超过100万，请选择工、农、建、招四家银行进行充值。</dd>
      <dt><span>6、为什么账户显示金额处于冻结状态？</span></dt>
      <dd>答:
        资金冻结发生在投资人购买债权（该项目未放款）、提现（未审核）时，当满标放款或提现审核通过后，资金解冻。</dd>
      <dt><span>7、提现多久可以到账？</span></dt>
      <dd>答:
        工作日申请的提现，1-2个工作日之内到达提现的银行卡内；节假日提现的，节假日后的2个工作日到达提现的银行卡内。具体到账时间根据提现银行的处理速度。</dd>
      <dt><span>8、可以提现到他人的银行卡么？</span></dt>
      <dd>答:
        不能，提现必须提现到本人实名认证的银行卡中，绑定银行卡请在“会员中心”——“银行卡设置”进行绑定，不能提现至未绑定的银行卡。</dd>
      <dt><span>9、如果借款人不还钱怎么办？</span></dt>
      <dd>答:
        花样年集团承诺对所有逾期借款进行回购。</dd>
      <dt><span>10、投资后紧急需要资金怎么办？</span></dt>
      <dd>答:
        投资2个月后可以申请转让。</dd>
      <dt><span>11、开启自动投标对我有什么好处？</span></dt>
      <dd>答:
        开启自动投标功能后， 平台会保留您设置的保留金额，其它金额会全部投入到符合您条件的项目中，从而轻松达到较高的年化收益，又不必每月手动去投资操作，免去您的繁琐操作之扰 。</dd>
    </dl>
  </div>
</div>
<jsp:include page="/include/mobile/bottom.jsp"></jsp:include>
<jsp:include page="/include/mobile/common-js.jsp"></jsp:include>
<script src="/wap/mobile/scripts/module/fqa.js" ></script>
</body>
</html>