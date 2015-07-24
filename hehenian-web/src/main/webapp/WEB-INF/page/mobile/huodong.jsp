<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<jsp:include page="/include/mobile/head.jsp"></jsp:include>
<title>E理财</title>
</head>
<body>
<jsp:include page="/include/mobile/loading.jsp"></jsp:include>
<h1 class="t-a title"><strong>活动介绍</strong></h1>
<div class="wrap" id="wrap" style="display:none;">
  <div class="hd">
  	<div class="hd-img"><img src="/wap/mobile/images/ad/song100-app.jpg" /></div>
    <s:if test="#session.appstyle=='cf'">
    <div class="hd-ch">亲爱的会员:</div>
    <div class="hd-content">
    <p class="indent">金秋10月，是收获的季节，来e理财首次投资送30元，推荐小伙伴来投资也有奖励啦~  赶紧行动起来，金秋10月一起high，丰厚奖励滚滚来~ 活动详情如下： </p>
    <p><strong>一、活动时间：</strong><br>
    1、 首次投资奖励30元活动（10月1-31日）。<br>
    2、 推荐送0.5%的奖励活动（10月1日- 12月31日）。 <br>
    </p>
    <p><strong>二、活动规则：</strong><br>
    1、 活动期间，首次投资者（不包括首次投资时是接债权转让者），可获得30元现金奖励。<br>
    2、 推荐好友注册并成功投资，推荐人可以获得被推荐人每月新增投资额的0.5%的奖励，被推荐人10月31日前首次投资可获30元奖励； <br>
    </p>
    <p><strong>三、奖励发放：</strong><br>
    首次投资奖励的30元分别在10月10日、17日、24日、31日发放，推荐提成奖励每月10号发放。 <br>
    </p>
    <p>本活动最终解释权归e理财所有。 </p>
    </div>
    <p class="hd-mark">e理财运营团队</p>
    <p class="hd-mark">2014年9月29日</p>
    </s:if>
    <s:else>
    <div class="hd-ch">亲爱的合粉年糕们:</div>
    <div class="hd-content">
     <p class="indent">金秋10月，是收获的季节，来合和年在线首次投资送30元，推荐小伙伴来投资也有奖励啦~  赶紧行动起来，金秋10月一起high，丰厚奖励滚滚来~ 活动详情如下：</p>
    <p><strong>一、活动时间：</strong><br>
    1、 首次投资奖励30元活动（10月1-31日）。<br>
    2、 推荐送0.5%的奖励活动（10月1日- 12月31日）。 <br>
    </p>
    <p><strong>二、活动规则：</strong><br>
    1、 活动期间，首次投资者（不包括首次投资时是接债权转让者），可获得30元现金奖励。<br>
    2、 推荐好友注册并成功投资，推荐人可以获得被推荐人每月新增投资额的0.5%的奖励，被推荐人10月31日前首次投资可获30元奖励； <br>
    </p>
    <p><strong>三、奖励发放：</strong><br>
    首次投资奖励的30元分别在10月10日、17日、24日、31日发放，推荐提成奖励每月10号发放。 <br>
    </p>
    <p>本活动最终解释权归合和年在线所有。</p>
    </div>
    <p class="hd-mark">合和年在线运营团队</p>
    <p class="hd-mark">2014年9月29日</p>
    </s:else>
  </div>
</div>
<jsp:include page="/include/mobile/bottom.jsp"></jsp:include>
<jsp:include page="/include/mobile/common-js.jsp"></jsp:include>
</body>
</html>
