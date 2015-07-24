<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<jsp:include page="/include/mobile/head.jsp"></jsp:include>
<title>注册汇付天下</title>
</head>
<body>
<jsp:include page="/include/mobile/loading.jsp"></jsp:include>
<h1 class="t title"><span>注册汇付天下</span></h1>
<div class="wrap animate-waves" id="wrap" style="display:none;">
  <div class="pd huifu">
    <div class="huifu-tip p" id="huifuTips">
      <p>汇付天下托管账户系统，是汇付天下为P2P平台提供的资金账户体系，全面管理平台资金，帮助平台实现收款、付款、用户账户间资金划拨以及账户资金理财功能；同时，汇付天下保障用户账户中的资金独立存放，用户可通过P2P平台对本人汇付账户进行充值、提现，商户在未经用户的许可、授权或法律的规定的情况下，无权动用，从而保证投资人资金安全。
        注册汇付天下</p>
      <div class="huifu-btn"> <a href="#" class="btn-a" id="btnReg">注册汇付天下</a> <a href="/webapp/webapp-index.do" class="btn-c">暂不注册，先逛逛</a> </div>
    </div>
    <div class="huifu-tj" id="huifuTj">
    <form action="/setMyReferee.do" id="form">
      <label class="text" >请填写推荐人</label>
      <div class="input-item">
        <input type="tel" value="" placeholder="推荐人ID或者手机号码" name="paramMap.refferee" id="tuijianren" validate="true" rule="{type:'number',empty:false}" tips="{number:'请输入正确的推荐人ID或者手机号码',empty:'请填写推荐人'}" />
      </div>
      <div class="huifu-btn"> <a href="#" class="btn-a" id="btnSave">保存</a> <a href="/registerChinaPnr.do" class="btn-c">跳过</a> </div>
    </form>
    </div>
  </div>
</div>
<jsp:include page="/include/mobile/bottom.jsp"></jsp:include>
<jsp:include page="/include/mobile/common-js.jsp"></jsp:include>
<script src="/wap/mobile/scripts/module/huifu.js" ></script>
</body>
</html>