<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
   <link id="skin" rel="stylesheet" href="css/jbox/Gray/jbox.css" />
    <jsp:include page="/include/head.jsp"></jsp:include>
</head>
<body>
<!-- 引用头部公共部分 -->
<jsp:include page="/include/top.jsp"></jsp:include>	
<input type="hidden" id="idStr" value="${idStr}"/>
<div class="nymain">
  <div class="ifbox1">
  <h2><span><a href="javascript:void(0);" id="sendmail">发送站内信</a> <a href="javascript:void(0);" id="reportuser">举报此人</a></span>标的详情 <img src="images/neiye2_06.jpg" width="4" height="7" /></h2>
  <div class="boxmain">
    <div class="pic-info">
      <div class="tx">
      <shove:img defaulImg="images/neiye2_11.jpg" src="${borrowDetailMap.imgPath}"  width="180" height="180"></shove:img>
    </div>
      <div class="guanzhu">
         <!-- Baidu Button BEGIN -->
<div id="bdshare" class="bdshare_t bds_tools get-codes-bdshare">
<a class="bds_qzone"></a>
<a class="bds_tsina"></a>
<a class="bds_tqq"></a>
<a class="bds_renren"></a>
<a class="bds_t163"></a>
<span class="bds_more">更多</span>
<a class="shareCount"></a>
</div>
<script type="text/javascript" id="bdshare_js" data="type=tools&amp;uid=6638061" ></script>
<script type="text/javascript" id="bdshell_js"></script>
<script type="text/javascript">
document.getElementById("bdshell_js").src = "http://bdimg.share.baidu.com/static/js/shell_v2.js?cdnversion=" + Math.ceil(new Date()/3600000)
</script>
<!-- Baidu Button END -->
      </div>
      <p><a id="focusonBorrow" href="javascript:void(0);">关注此借款</a></p>
      </div>
      <div class="xqbox">
      <h3><s:property value="#request.borrowDetailMap.borrowTitle" default="---"/>
      &nbsp;&nbsp;&nbsp;
      (<s:property value="#request.borrowWay" default="---"/>)
      </h3>
      <div class="xqboxmain">
      <div class="xqbottom">
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td>
        最小流转单位：${borrowDetailMap.smallestFlowUnit} 元
     </td>
    <td>年化利率：${borrowDetailMap.annualRate}%</td>
   
  </tr>
  <tr>
    <td>
        流转总金额：￥${borrowDetailMap.borrowAmount}
     </td>
    <td>已流转份数：${borrowDetailMap.hasCirculationNumber}份</td>
  
    
  </tr>
  <tr>
    <td>
   	    担保机构：${borrowDetailMap.agent}
     </td>
    <td>反担保方式： ${borrowDetailMap.counterAgent}</td>
    
  </tr>
    
  <tr>
   <td>
    <strong style="color: red">
    还款方式： <s:if test="%{#request.borrowDetailMap.paymentMode == 1}">按月分期还款</s:if>
    <s:elseif test="%{#request.borrowDetailMap.paymentMode == 2}">按月付息,到期还本</s:elseif>
    <s:elseif test="%{#request.borrowDetailMap.paymentMode == 3}">秒还</s:elseif>
     <s:elseif test="%{#request.borrowDetailMap.paymentMode == 4}">一次性还款</s:elseif>
   
    </strong>
    </td>

 <td>
      <p>投标奖励：<s:if test="%{#request.borrowDetailMap.excitationType == 1}">无</s:if>
    <s:elseif test="%{#request.borrowDetailMap.excitationType ==2}">按固定金额￥${borrowDetailMap.excitationSum}</s:elseif>
    <s:elseif test="%{#request.borrowDetailMap.excitationType ==3}">按借款金额比例${borrowDetailMap.excitationSum}%</s:elseif>
    </p>
     </td>
  </tr>
  <tr>
    <td>总投标数：<s:property value="#request.borrowDetailMap.investNum" default="0"/> 浏览量：<s:property value="#request.borrowDetailMap.visitors" default="---"/><br/></td>
    <td style="text-align: left;">还有：${borrowDetailMap.remainCirculationNumber}份</td>
    </tr>
  <tr><td colspan="2"><span>${earnAmount}</span></td></tr>
  <tr><td colspan="2"><span>剩余时间：<span id="remainTimeOne"></span></span></td></tr>
</table>
      </div>
      <div class="xqtop">
      </div>
         <div class="money">
        <div style="background-color: #FDFAE9; height: 75px; width: 480px; margin: auto;margin-top:15px;border: solid 1px #F9DDD1;">
          <table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tbody>
   <tr>
   <td height="10">
    </td>
  </tr>
     <tr>
     <!-- 
    <td width="51%" height="48" align="right"><div><input type="text" class="result_btn_detail_3" value="1" id="result"/>
份&nbsp;&nbsp; </div></td>
 -->
    <td width="100%" align="center"><div style="width:98px; height: 40px">
   			 <s:if test="%{#request.borrowDetailMap.borrowStatus == 1}">
            <img src="images/neiye2_15.jpg" width="97" height="31"/>&nbsp;
      	  	</s:if>
      	  	 <s:elseif test="%{#request.borrowDetailMap.borrowStatus == 2}">
             <input type="image" src="images/gogo_rg.gif" value="" id="invest" onclick="mycirculationPay(${borrowDetailMap.id})" class="bttn_2"/>
	        </s:elseif>
	        <s:elseif test="%{#request.borrowDetailMap.borrowStatus == 3}">
	            <img src="images/neiye2_16.jpg" width="97" height="31"/>&nbsp;
	        </s:elseif>
	        <s:elseif test="%{#request.borrowDetailMap.borrowStatus == 4}">
	            <img src="images/neiye1_08.jpg" title="流转标回购中" width="98" height="32" />&nbsp;
	        </s:elseif>
	        <s:elseif test="%{#request.borrowDetailMap.borrowStatus == 5}">
	           <img src="images/neiye1_637.jpg" width="97" height="31"/>&nbsp;
      		  </s:elseif>
		        <s:else>
		           <img src="images/neiye2_18.jpg" width="97" height="31"/>&nbsp;
		        </s:else>
	</div></td>
  </tr>
  <tr>  
    <td colspan="2" align="center">您的可投标金额为：<font id="account_use" style="color:#FF0000">${borrowDetailMap.investAmount}</font>元，最多可认购：<font style="color:#FF0000" id="roam_use">${borrowDetailMap.remainCirculationNumber}份</font>
    </td>
    </tr>
</tbody></table>          
</div>
        </div>
      </div>
      </div>
      <div class="reninfo">
        <div class="rinfomain">
        <div class="tx">
        <shove:img defaulImg="images/default-img.jpg" src="${borrowUserMap.personalHead}"  width="62" height="62"></shove:img>
        </div>
        <div class="jfico"><img src="images/ico_r_${borrowUserMap.rating}.gif"/></div>
        <div class="name">
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td>用户名：${borrowUserMap.username}
    <s:if test="#request.borrowUserMap.vipStatus ==2"></s:if>
    </td>
  </tr>
  <tr>
    <td>居住地： ${borrowUserMap.address} 
    </td>
  </tr>
  <tr>
    <td>信用指数： <img src="images/ico_13.jpg" title="${borrowUserMap.creditrating}分" width="33" height="22" /></td>
  </tr>
  <tr>
    <td>注册时间：${borrowUserMap.createTime} </td>
  </tr>
  <tr>
    <td>最后登录：${borrowUserMap.lastDate}</td>
  </tr>
  <tr>
    <td align="center"><img src="images/neiye2_22.jpg" width="16" height="16" /> <a id="focusonUser" href="javascript:void(0);">关注此人</a></td>
  </tr>
</table>
        </div>
        </div>
      </div>
  </div>
  </div>
  <div class="ifbox2">
    <div class="til" style="background-image: url(images/neiye2_32.jpg); background-repeat: repeat-x;">
    <ul><li class="on">相关信息</li>
    </ul>
    </div>
    <div class="boxmain">
    <p class="xydj">合和年在线信用等级：<img src="images/ico_13.jpg" title="${borrowUserMap.creditrating}分" width="33" height="22" /> <span>合和年信贷信用额度： ${borrowUserMap.creditLimit}</span></p>
    <div class="tips">
    以下基本信息资料，经用户同意披露。其中<span class="fred">红色</span>字体的信息，为通过合和年信贷审核之项目。<br/>
    </div>
    <div class="infotab">
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <th colspan="6" align="left">基本信息</th>
   </tr>
  <tr>
    <td>性别：<s:if test="#request.borrowUserMap.auditperson == 3"><strong>${borrowUserMap.sex}</strong></s:if><s:else>${borrowUserMap.sex}</s:else></td>
    <td>年龄：<s:if test="#request.borrowUserMap.auditperson == 3"><strong>${borrowUserMap.age}</strong></s:if><s:else>${borrowUserMap.age}</s:else></td>
    <td colspan="3">婚姻状况：<s:if test="#request.borrowUserMap.auditperson == 3"><strong>${borrowUserMap.maritalStatus}</strong></s:if><s:else>${borrowUserMap.maritalStatus}</s:else></td>
    <td colspan="3">工作城市：<s:if test="#request.borrowUserMap.auditwork == 3"><strong>${borrowUserMap.workPro}&nbsp;${borrowUserMap.workCity}</strong></s:if><s:else>${borrowUserMap.workPro}&nbsp;${borrowUserMap.workCity}</s:else></td>
    </tr>
  <tr>
    <td>公司行业：<s:if test="#request.borrowUserMap.auditwork == 3"><strong>${borrowUserMap.companyLine}</strong></s:if><s:else>${borrowUserMap.companyLine}</s:else></td>
    <td>公司规模：<s:if test="#request.borrowUserMap.auditwork == 3"><strong>${borrowUserMap.companyScale}</strong></s:if><s:else>${borrowUserMap.companyScale}</s:else></td>
    <td colspan="3">职位：<s:if test="#request.borrowUserMap.auditwork == 3"><strong>${borrowUserMap.job}</strong></s:if><s:else>${borrowUserMap.job}</s:else></td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td>毕业学校：<s:if test="#request.borrowUserMap.auditwork == 3"><strong>${borrowUserMap.school}</strong></s:if><s:else>${borrowUserMap.school}</s:else></td>
    <td>学历：<s:if test="#request.borrowUserMap.auditwork == 3"><strong>${borrowUserMap.highestEdu}</strong></s:if><s:else>${borrowUserMap.highestEdu}</s:else></td>
    <td colspan="3">入学年份：<s:if test="#request.borrowUserMap.auditwork == 3"><strong>${borrowUserMap.eduStartDay}</strong></s:if><s:else>${borrowUserMap.eduStartDay}</s:else></td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td>有无购房：<s:if test="#request.borrowUserMap.auditperson == 3"><strong>${borrowUserMap.hasHourse}</strong></s:if><s:else>${borrowUserMap.hasHourse}</s:else></td>
    <td>有无购车：<s:if test="#request.borrowUserMap.auditperson == 3"><strong>${borrowUserMap.hasCar}</strong></s:if><s:else>${borrowUserMap.hasCar}</s:else></td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td>有无房贷：<s:if test="#request.borrowUserMap.auditperson == 3"><strong>${borrowUserMap.hasHousrseLoan}</strong></s:if><s:else>${borrowUserMap.hasHousrseLoan}</s:else></td>
    <td>有无车贷：<s:if test="#request.borrowUserMap.auditperson == 3"><strong>${borrowUserMap.hasCarLoan}</strong></s:if><s:else>${borrowUserMap.hasCarLoan}</s:else></td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
</table>
    </div>
    <div class="infotab">
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <th colspan="5" align="left">合和年在线借款记录 </th>
    </tr>
  <tr>
    <td>发布借款标的：<s:if test="#request.borrowRecordMap.publishBorrow !=''">${borrowRecordMap.publishBorrow}</s:if><s:else>0</s:else></td>
    <td>成功借款笔数：<s:if test="#request.borrowRecordMap.successBorrow !=''">${borrowRecordMap.successBorrow}</s:if><s:else>0</s:else></td>
    <td>还清笔数：<s:if test="#request.borrowRecordMap.repayBorrow !=''">${borrowRecordMap.repayBorrow}</s:if><s:else>0</s:else></td>
    <td>逾期次数：0</td>
    <td>严重逾期次数：0</td>
    </tr>
  <tr>
    <td>共借入：<s:if test="#request.borrowRecordMap.borrowAmount !=''">${borrowRecordMap.borrowAmount}</s:if><s:else>0</s:else> </td>
    <td>待还金额：<s:if test="#request.borrowRecordMap.forRepayPI !=''">${borrowRecordMap.forRepayPI}</s:if><s:else>0</s:else></td>
    <td>逾期金额：0</td>
    <td>共借出：<s:if test="#request.borrowRecordMap.investAmount !=''">${borrowRecordMap.investAmount}</s:if><s:else>0</s:else></td>
    <td>待收金额：<s:if test="#request.borrowRecordMap.forPI !=''">${borrowRecordMap.forPI}</s:if><s:else>0</s:else></td>
  </tr>
</table>
    </div>
    </div>
  </div>
    <div class="ifbox2">
    <div class="til" style="background-image: url(images/neiye2_32.jpg); background-repeat: repeat-x;">
    <ul><li class="on">借款方商业概述</li>
    </ul>
    <div class="til_bot">
    </div>
    </div>
    <div class="boxmain">
		<p class="txt">${borrowDetailMap.businessDetail}</p>
    </div>
  </div>
      <div class="ifbox2">
    <div class="til" style="background-image: url(images/neiye2_32.jpg); background-repeat: repeat-x;">
    <ul><li class="on">借款方资产情况</li>
    </ul>
    <div class="til_bot">
    </div>
    </div>
    <div class="boxmain">
		<p class="txt">${borrowDetailMap.assets}</p>
    </div>
  </div>
       <div class="ifbox2">
    <div class="til" style="background-image: url(images/neiye2_32.jpg); background-repeat: repeat-x;">
    <ul><li class="on">借款方资金用途</li>
    </ul>
    <div class="til_bot">
    </div>
    </div>
    <div class="boxmain">
		<p class="txt">${borrowDetailMap.moneyPurposes}</p>
    </div>
  </div>
         <div class="ifbox2">
    <div class="til" style="background-image: url(images/neiye2_32.jpg); background-repeat: repeat-x;">
    <ul><li class="on">风险控制措施</li>
    </ul>
    <div class="til_bot">
    </div>
    </div>
    <div class="boxmain">
		<p class="txt">${borrowDetailMap.auditOpinion}</p>
    </div>
  </div>
  <div class="ifbox2">
    <div class="til" style="background-image: url(images/neiye2_32242.jpg); background-repeat: repeat-x; border:none;">
    <ul class="shtab"><li class="on" id="audit">审核记录</li>
    </ul>
    <div class="til_bot">
    </div>
    </div>
    <div class="boxmain">
    <div class="box">
    <div class="tips">合和年在线将以客观、公正的原则，最大程度地核实借入者信息的真实性，但不保证审核信息100%真实。如果借入者长期逾期，其提供的信息将被公布。 </div>
    <div id="contentList" class="tytab">
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <th align="center">审核项目</th>
    <th align="center">状态</th>
    <th align="center">通过时间</th>
    <th align="center">操作</th>
  </tr>
  <s:if test="%{#request.list !=null && #request.list.size()>0}">
      <s:iterator value="#request.list" id="bean">
      <tr>
          <td align="center">${bean.name}</td>
          <td align="center">
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
          <td align="center">${bean.passTime} </td>
          <td align="center" class="ck">
          <s:if test="#bean.auditStatus == 3">
              <a href="javascript:showImg('${bean.materAuthTypeId}','${bean.userId}');">查看</a>
          </s:if>
          <s:else>---
          </s:else>
          </td>
      </tr>
  </s:iterator>        
  </s:if>
  <s:else>
      <td colspan="4" align="center">没有数据</td>
  </s:else>
</table>
    </div>
    </div>
  </div>
  <div id="msg" class="ifbox2">
    <img src="images/load.gif" class="load" alt="加载中..." />
  </div>
  <div class="ifbox2">
    <div class="til">
    <ul><li class="on">投资记录</li>
    </ul>
    <div class="til_bot">
    </div>
    </div>
    <div class="boxmain">
    <div class="tzjl">
    <ul>
      <li>目前总投标金额：<strong>￥${borrowDetailMap.hasInvestAmount}</strong></li><li>剩余投标金额：<strong>￥${borrowDetailMap.investAmount}</strong></li>
       <!--  
       <li>剩余投标时间：<strong><span id="remainTimeTwo"></span></strong></li>
 		-->      
       </ul>
    </div>
    <div class="tytab">
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <th align="center">投资人</th>
    <th align="center">投资金额</th>
    <th align="center">投资时间 </th>
    </tr>
  <s:if test="%{#request.investList !=null && #request.investList.size()>0}">
      <s:iterator value="#request.investList" id="investList">
      <tr>
    <td align="center" class="name">
    <a href="userMeg.do?id=<s:property value="#investList.investor"/>" target="_blank">
    
    ${username }
    
    <s:if test="#investList.creditedStatus ==2"><img src="images/zrico.jpg" width="30" height="16"/></s:if>
    </a></td>
    <td align="center" class="fred">￥<s:property value="#investList.investAmount"/></td>
    <td align="center"><s:property value="#investList.investTime"/></td>
    </tr>
  </s:iterator>        
  </s:if>
  <s:else>
      <td colspan="3" align="center">没有数据</td>
  </s:else>
    </table>
    </div>
    </div>
  </div>
 </div>
</div>
<div id="remainSeconds"  style="display:none;">${borrowDetailMap.remainTime}</div>
<!-- 引用底部公共部分 -->     
<jsp:include page="/include/footer.jsp"></jsp:include>
<script type="text/javascript" src="script/nav-lc.js"></script>
<script type="text/javascript" src="css/popom.js"></script>
<script type="text/javascript" src="script/jquery.shove-1.0.js"></script>
<script type="text/javascript" src="script/jbox/jquery.jBox-2.3.min.js"></script>
<script type="text/javascript" src="script/jbox/jquery.jBox-zh-CN.js"></script>
<script>
$(function(){
    //样式选中
     $("#licai_hover").attr('class','nav_first');
	 $("#licai_hover div").removeClass('none');
	 $('.tabmain').find('li').click(function(){
	    $('.tabmain').find('li').removeClass('on');
	    $(this).addClass('on');
	    $('.lcmain_l').children('div').hide();
        $('.lcmain_l').children('div').eq($(this).index()).show();
	 });
	 $('#focusonBorrow').click(function(){
	      var username = '${borrowUserMap.username}';
	      var uname = '${user.username}';
	      if(username == uname){
	         alert("您不能关注自己发布的借款");
	         return false;
	      }
	     param['paramMap.id'] = '${borrowDetailMap.id}';
	     $.shovePost('focusonBorrow.do',param,function(data){
		   var callBack = data.msg;
		   alert(callBack);
		 });
	   });
	   $('#focusonUser').click(function(){ 
	      var username = '${borrowUserMap.username}';
	      var uname = '${user.username}';
	      if(username == uname){
	         alert("您不能关注自己");
	         return false;
	      }
	     param['paramMap.id'] = '${borrowUserMap.userId}';
	     $.shovePost('focusonUser.do',param,function(data){
	       var callBack = data.msg;
		   alert(callBack);
		 });
	   });
	   $('#sendmail').click(function(){
	      var id = '${borrowUserMap.userId}';
	      var username = '${borrowUserMap.username}';
	      var url = "mailInit.do?id="+id+"&username="+username;
	      var uname = '${user.username}';
	      if(username == uname){
	         alert("您不能给自己发送站内信");
	         return false;
	      }
	      $.shovePost('mailInit.do',param,function(data){
	        $.jBox("iframe:"+url, {
	    		title: "发送站内信",
	    		width: 500,
	    		height: 400,
	    		buttons: {  }
	    		});       
		  });
	   });
	   $('#reportuser').click(function(){
	      var id = '${borrowUserMap.userId}';
	      var username = '${borrowUserMap.username}';
	      var url = "reportInit.do?id="+id+"&username="+username;
	      var uname = '${user.username}';
	      if(username == uname){
	         alert("您不能举报自己");
	         return false;
	      }
	      $.shovePost('reportInit.do',param,function(data){
	        $.jBox("iframe:"+url, {
	    		title: "举报此人",
	    		width: 500,
	    		height: 400,
	    		buttons: {  }
	    		});       
		  });
	   });
	   $('#audit').click(function(){
	      var id = $('#idStr').val();
	      $(this).addClass('on');
	      $('#repay').removeClass('on');
	      $('#collection').removeClass('on');
	      param['paramMap.id']=id;
	      $.shovePost('financeAudit.do',param,function(data){
	        $('#contentList').html(data);	       
		  });
	   });
	   $('#repay').click(function(){
	      var id = $('#idStr').val();
	      $(this).addClass('on');
	      $('#audit').removeClass('on');
	      $('#collection').removeClass('on');
	      param['paramMap.id']=id;
	      $.shovePost('financeRepay.do',param,function(data){
	        $('#contentList').html(data);	       
		  });
	      
	   });
	   $('#collection').click(function(){
	      var id = $('#idStr').val();
	      $(this).addClass('on');
	      $('#audit').removeClass('on');
	      $('#repay').removeClass('on');
	      param['paramMap.id']=id;
	      $.shovePost('financeCollection.do',param,function(data){
	        $('#contentList').html(data);	       
		  });
	      
	   });
	   initListInfo(param);
});
function initListInfo(param){
    param['paramMap.id']='${borrowDetailMap.id}';
    $.shovePost('borrowmessage.do',param,function(data){
		   $('#msg').html(data);
    });
}
function showImg(typeId,userId){
          var url = "showImg.do?typeId="+typeId+"&userId="+userId;
          $.shovePost('showImg.do',param,function(data){
	      $.jBox("iframe:"+url, {
	    		title: "查看认证图片<strong style='color: red;'>(点击图片放大)</strong>",
	    		width: 600,
	    		height: 415,
	    		buttons: {  }
	    		});
		  });
}
function close(){
ClosePop();
}		     
</script>
<script type="text/javascript"> 
 var SysSecond; 
 var InterValObj; 
  
 $(document).ready(function() { 
  SysSecond = parseInt($("#remainSeconds").html()); //这里获取倒计时的起始时间 
  InterValObj = window.setInterval(SetRemainTime, 1000); //间隔函数，1秒执行 
 }); 
 
 //将时间减去1秒，计算天、时、分、秒 
 function SetRemainTime() { 
  if (SysSecond > 0) { 
   SysSecond = SysSecond - 1; 
   var second = Math.floor(SysSecond % 60);             // 计算秒     
   var minite = Math.floor((SysSecond / 60) % 60);      //计算分 
   var hour = Math.floor((SysSecond / 3600) % 24);      //计算小时 
   var day = Math.floor((SysSecond / 3600) / 24);        //计算天 
   var times = day + "天" + hour + "小时" + minite + "分" + second + "秒";
   $("#remainTimeOne").html(times);
   $("#remainTimeTwo").html(times); 
  } else {//剩余时间小于或等于0的时候，就停止间隔函数 
   window.clearInterval(InterValObj); 
   //这里可以添加倒计时时间为0后需要执行的事件 
  } 
 } 
</script>
<script>
    function mycirculationPay(ids){
    	 param["id"] = ids;
	     $.shovePost('financeInvestInit.do',param,function(data){
		   var callBack = data.msg;
		   if(callBack !=undefined){
		     alert(callBack);
		   }else{
     	  var url = "subscribeinit.do?borrowid="+ids;
     	 $.jBox("iframe:"+url, {
	    		title: "我要购买",
	    		width: 450,
	    		height: 450,
	    		buttons: {  }
	    		});
      	// ShowIframe("我要购买",url,450,450);
       }
    });}
    function closeMthod(){
    	window.location.reload();
    }

	var SysSecond;
	var InterValObj;

	$(document).ready(function() {
		SysSecond = parseInt($("#remainSeconds").html()); //这里获取倒计时的起始时间 
			InterValObj = window.setInterval(SetRemainTime, 1000); //间隔函数，1秒执行 
		});

	//将时间减去1秒，计算天、时、分、秒 
	function SetRemainTime() {
		if (SysSecond > 0) {
			SysSecond = SysSecond - 1;
			var second = Math.floor(SysSecond % 60); // 计算秒     
			var minite = Math.floor((SysSecond / 60) % 60); //计算分 
			var hour = Math.floor((SysSecond / 3600) % 24); //计算小时 
			var day = Math.floor((SysSecond / 3600) / 24); //计算天 
			var times = day + "天" + hour + "小时" + minite + "分" + second + "秒";
			$("#remainTimeOne").html(times);
			$("#remainTimeTwo").html(times);
		} else {//剩余时间小于或等于0的时候，就停止间隔函数 
			window.clearInterval(InterValObj);
			//这里可以添加倒计时时间为0后需要执行的事件 
		}
	}
</script>
</body>
</html>
