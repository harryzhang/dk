<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <jsp:include page="/include/head.jsp"></jsp:include>
</head>
<body>
<!-- 引用头部公共部分 -->
<jsp:include page="/include/top.jsp"></jsp:include>	
<form id="form" action="addCirculationBorrow.do" method="post">
<div class="nymain">
<div class="bigbox" style="border:none">
  <div class="sqdk" style="background:none;">
	<div class="l-nav">
    <ul>
    <li><a href="querBaseData.do?from=1">基本资料</a></li>
    <li><a href="userPassData.do?from=1">上传资料</a></li>
    <li class="on last"><a href="javascript:void(0);">发布贷款</a></li>
    </ul>
    </div>
    <div class="r-main">
    <%--<div class="til01">
    --%><div class="til01">
	<ul id="ul"><li class="on">发布贷款</li><span class="fred" style="color: red;font-size: 12px; padding-left: 80px;line-height: 50px;"><s:fielderror fieldName="enough"></s:fielderror></span></ul>
	</div>
	<%--<ul id="ul"><li><span class="fred"><s:fielderror fieldName="enough"></s:fielderror></span></li></ul>
	</div>
    --%><div class="rmainbox">
    <p class="tips"><span class="fred">*</span> 为必填项，所有资料均会严格保密。 </p>
    <div class="tab">
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <th colspan="2" align="left">借款基本信息</th>
    </tr>
  <tr>
    <td align="right">借款标题：<span class="fred">*</span></td>
    <td><input name="paramMap.title" type="text" class="inp280" maxlength="12" value="${paramMap.title}"/>
    <span class="fred"><s:fielderror fieldName="paramMap['title']"></s:fielderror></span>
    </td>
  </tr>
  <tr>
    <td align="right">借款图片：<span class="fred">*</span><input type="hidden" id="imgPath"  name="paramMap.imgPath" value="${paramMap.imgPath}"/>
    </td>
    <td>
    <input type="hidden" id="personalImg" value="${user.personalHead}"/>
        <label><input type="radio" name="radio" id="r_1" checked="checked" value="1" />上传借款图片</label> 
        <label><input type="radio" name="radio" id="r_2" value="2" />使用用户头像 </label>
        <label><input type="radio" name="radio" id="r_3" value="3" />使用系统头像</label>
        <span class="fred"><s:fielderror fieldName="paramMap['imgPath']"></s:fielderror></span>
        <input type="hidden" id="radioval" name="paramMap.radioval" value="${paramMap.radioval}"/>
        </td>
  </tr>
  <tr>
    <td align="right">&nbsp;</td>
    <td>
    <div id="tab_1"><a href="javascript:void(0);" id="btn_personalHead" class="scbtn">上传图片</a></div>
    <div id="tab_2" style="display:none;"></div>
    <div id="tab_3" style="display:none;">
    <table id="sysimg" style="width:400px;">
        <tr>
            <td class="tx">
            <s:iterator var="sysImage" value="sysImageList">
              <span><img src="${sysImage.selectName}" class="selimg"/></span>
            </s:iterator>
            </td>
        </tr>
    </table>
    </div>
    </td>
  </tr>
  <tr>
    <td align="right">&nbsp;</td>
     <td class="tx" >
  	  <shove:img src="${headImg}"   defaulImg="images/default-img.jpg" id="img" width="62" height="62"></shove:img>
     </td>
  </tr>
  <tr>
    <td align="right">&nbsp;</td>
    <td class="tishi">（推荐使用
    您的生活近照，或其他与借款用途相关的图片，<br />
      有助增加借款成功几率。严禁使用他人照片） </td>
  </tr>
  <tr>
    <td align="right">借款标的：</td>
    <td><span id="typeName" class="fred">流转标</span></td>
  </tr>
     <tr>
    <td align="right">担保机构：</td>
    <td >
 
       <input type="hidden" id="paramMap.paymentinsti"  name="paramMap.paymentinsti" value="instiList.selectValue"/>
    	${instiList.selectName}
    </td>
  </tr>
   <tr>
    <td align="right">反担保方式：</td>
    <td >
       <input type="hidden" id="paramMap.paymentcounter"  name="paramMap.paymentcounter" value="counterList.selectValue"/>
    	${counterList.selectName}
    </td>
  </tr>
  <tr>
    <td align="right">借款目的：<span class="fred">*</span></td>
    <td class="tishi">
    <s:select list="borrowPurposeList" id="purpose" name="paramMap.purpose" cssClass="sel_140" listKey="selectValue" listValue="selectName"  headerKey="" headerValue="--请选择--"></s:select>
    <span class="fred"><s:fielderror fieldName="paramMap['purpose']"></s:fielderror></span>
    </td>
  </tr>
  <tr>
    <td align="right">回购期限：<span class="fred">*</span></td>
    <td >
     <s:select list="borrowDeadlineMonthList" id="deadLine" name="paramMap.deadLine" cssClass="sel_140" listKey="key" listValue="value"   headerKey="" headerValue="--请选择--"></s:select>
    <s:select list="borrowDeadlineDayList" id="deadDay" cssStyle="display:none;" name="paramMap.deadDay" cssClass="sel_140" listKey="key" listValue="value"   headerKey="" headerValue="--请选择--"></s:select>
    <span class="fred"><s:fielderror fieldName="paramMap['deadLine']"></s:fielderror></span>
    </td>
  </tr>
  <tr>
    <td align="right">还款方式：</td>
    <td >
    		<span class="fred">一次性还款</span>
    <%--
     <s:select id="paymentMode" name="paramMap.paymentMode" list="borrowRepayWayList" cssClass="sel_140" listKey="id" listValue="name" headerKey="" headerValue="--请选择--"></s:select>
    <span class="fred"><s:fielderror fieldName="paramMap['paymentMode']"></s:fielderror></span>
    --%></td>
  </tr>

  <tr>
    <td align="right">借款总额：<span class="fred">*</span></td>
    <td>
    <input type="hidden" name="paramMap.borrowMoneyfirst" value="${paramMap.borrowMoneyfirst }" />
    <input type="hidden" name="paramMap.borrowMoneyend" value="${paramMap.borrowMoneyend }"/>
      <input type="hidden" name="paramMap.accountmultiple" value="${paramMap.accountmultiple }"/>
    <input type="text" id="amount" name="paramMap.amount" class="inp100x" value="${paramMap.amount}"/><span class="fred"><s:fielderror fieldName="paramMap['amount']"></s:fielderror></span>
    【借款总额】的金额范围<a style="color: red;">${paramMap.borrowMoneyfirst }~${paramMap.borrowMoneyend }元，而且是${paramMap.accountmultiple }的倍数</a>
    </td>
  </tr>
  <tr>
    <td align="right">年利率：<span class="fred">*</span></td>
    <td>
     <input type="hidden" name="paramMap.aprfirst" value="${paramMap.aprfirst }"/>
      <input type="hidden" name="paramMap.aprend" value="${paramMap.aprend }"/>
    <input type="text" name="paramMap.annualRate" maxlength="5" value="${paramMap.annualRate}" class="inp100x" />%<span class="fred"><s:fielderror fieldName="paramMap['annualRate']"></s:fielderror></span>
     【年利率】的比率范围<a style="color: red;">${paramMap.aprfirst }~${paramMap.aprend }%</a>
    </td>
  </tr>
  <tr>
    <td align="right">最小流转单位：<span class="fred">*</span></td>
    <td><input type="text" name="paramMap.smallestFlowUnit" maxlength="20" value="${paramMap.smallestFlowUnit}" class="inp100x" /><span class="fred"><s:fielderror fieldName="paramMap['smallestFlowUnit']"></s:fielderror></span></td>
  </tr>
   <!-- <tr>
    <td align="right">筹标期限：<span class="fred">*</span></td>
    <td>
    	<s:select list="borrowRaiseTermList" id="raiseTerm" name="paramMap.raiseTerm" cssClass="sel_140" listKey="key" listValue="value"  headerKey="" headerValue="--请选择--"></s:select>
    	<span class="fred"><s:fielderror fieldName="paramMap['raiseTerm']"></s:fielderror></span>
    </td>
  </tr> -->
  <tr>
    <td align="right">商业详情：<span class="fred">*</span></td>
    <td><textarea name="paramMap.businessDetail" class="txt420">${paramMap.businessDetail}</textarea></td>
  </tr>
  <tr>
     <td>&nbsp;</td>
     <td><span class="fred"><s:fielderror fieldName="paramMap['businessDetail']"></s:fielderror></span></td>
  </tr>
  <tr>
    <td align="right">资产情况：<span class="fred">*</span></td>
    <td><textarea name="paramMap.assets" class="txt420">${paramMap.assets}</textarea></td>
  </tr>
  <tr>
     <td>&nbsp;</td>
     <td><span class="fred"><s:fielderror fieldName="paramMap['assets']"></s:fielderror></span></td>
  </tr>
  <tr>
    <td align="right">资金用途：<span class="fred">*</span></td>
    <td><textarea name="paramMap.moneyPurposes" class="txt420">${paramMap.moneyPurposes}</textarea></td>
  </tr>
  <tr>
     <td>&nbsp;</td>
     <td><span class="fred"><s:fielderror fieldName="paramMap['moneyPurposes']"></s:fielderror></span></td>
  </tr>
  
	 <!--  -->
   <tr>
    <th colspan="2" align="left"> 投标奖励</th>
    </tr>
  <tr>
    <td align="right">&nbsp;<input type="hidden" id="excitation" name="paramMap.excitationType" value="${paramMap.excitationType}"/></td>
    <td><input type="radio" name="excitationType" checked="checked" id="radio_1" value="1" />
      不设置奖励</td>
  </tr>
  <tr>
    <td align="right">&nbsp;</td>
    <td>
            <input type="hidden" name="paramMap.accountfirst" value="${paramMap.accountfirst }"/>
         <input type="hidden" name="paramMap.accountend" value="${paramMap.accountend }"/>
    <input type="radio" name="excitationType" id="radio_2" value="2" />
      固定总额按投标比例分配奖励
      <input type="text" id="sum" name="paramMap.sum" value="${paramMap.sum}" class="inp100x gray" disabled="disabled"/>
      元<br />(奖励金额在<a style="color: red;">${paramMap.accountfirst }~${paramMap.accountend }</a>元区间【固定总额奖励】)<a style="color: red;"><s:fielderror fieldName="paramMap['sum']"></s:fielderror></a></td>
  </tr>
  <tr>
    <td align="right">&nbsp;<input type="hidden" id="excitationMode" name="paramMap.excitationMode" value="${paramMap.excitationMode}"/></td>
    <td><input type="radio" name="excitationType" id="radio_3" value="3" />
    借款总额百分比分配奖励
        <input type="hidden"  name="paramMap.scalefirst" value="${paramMap.scalefirst }"/>
         <input type="hidden" name="paramMap.scaleend" value="${paramMap.scaleend }"/>
      <input type="text" id="sumRate" name="paramMap.sumRate" maxlength="20" value="${paramMap.sumRate}" class="inp100x gray" disabled="disabled"/>
      % <br />(奖励比例在<a style="color: red;">${paramMap.scalefirst }~${paramMap.scaleend }%</a> 区间【借款总额比例奖励】)<a style="color: red"><a style="color: red;"><s:fielderror fieldName="paramMap['sumRate']"></s:fielderror></a>
      </td>
  </tr>
  
  <!--  -->
  <tr>
    <th colspan="2" align="left"> 提交借款信息</th>
    </tr>
  <tr>
    <td align="right"> 验证码：</td>
    <td><input type="text" class="inp100x" name="paramMap.code" id="code"/>
		 <img src="" title="点击更换验证码" style="cursor: pointer;"
		 	  id="codeNum" width="46" height="18" onclick="javascript:switchCode()" />
    <span class="fred"><s:fielderror fieldName="paramMap['code']"></s:fielderror></span>
    </td>
  </tr>
    <tr>
    <td align="right">&nbsp;</td>
    <td style="padding-top:20px;"><a id="bcbtn" style="cursor:pointer;" class="bcbtn">提交发布</a></td>
  </tr>
    </table>
    </div>
    </div>
    </div>
  </div>
  </div>
</div>
</div>
</form>
<!-- 引用底部公共部分 -->     
<jsp:include page="/include/footer.jsp"></jsp:include>
<script type="text/javascript" src="script/nav-jk.js"></script>
<script>
$(document).ready(function(){
	var sd=parseInt($(".l-nav").css("height"));
    var sdf=parseInt($(".r-main").css("height"));
	 $(".l-nav").css("height",sd>sdf?sd:sdf-15);
	
  /*   $('#imgPath').val('${paramMap.imgPath}');
     $("#img").attr("src",'${paramMap.imgPath}');
     $('#purpose').val('${paramMap.purpose}');
     $('#deadLine').val('${paramMap.deadLine}');
     $('#paymentMode').val('${paramMap.paymentMode}');
     $('#raiseTerm').val('${paramMap.raiseTerm}');
     $('#excitation').val('${paramMap.excitationType}');
     $('#excitationMode').val('${paramMap.excitationMode}');
     $('#radioval').val('${paramMap.radioval}');
     var excitation = $('#excitation').val();
     var mode = $('#excitationMode').val();
     var radioval = $('#radioval').val();
     if(radioval !=''){
          $('#r_'+radioval).attr('checked','true');
          if(radioval ==1){
             $('#tab_1').css('display','block');
          }
          if(radioval ==2){
             $('#tab_2').css('display','block');
             $('#tab_1').css('display','none');
          }
          if(radioval ==3){
             $('#tab_3').css('display','block');
             $('#tab_1').css('display','none');
          }
     }
     */
	 var img='${paramMap.imgPath}';
	 if(img.length==0 ){
		img = "images/default-img.jpg";
	}
	 $('#imgPath').val(img);
	 $("#img").attr("src",img);
    
     $('#purpose').val('${paramMap.purpose}');
     $('#deadLine').val('${paramMap.deadLine}');
     $('#raiseTerm').val('${paramMap.raiseTerm}');
     $('#excitation').val('${paramMap.excitationType}');
     $('#excitationMode').val('${paramMap.excitationMode}');
     $('#radioval').val('${paramMap.radioval}');
     var excitation = $('#excitation').val();
     var mode = $('#excitationMode').val();
     var radioval = $('#radioval').val();
     if(radioval !=''){
          $('#r_'+radioval).attr('checked','true');
          if(radioval ==1){
             $('#tab_1').css('display','block');
          }
          if(radioval ==2){
             $('#tab_2').css('display','block');
             $('#tab_1').css('display','none');
          }
          if(radioval ==3){
             $('#tab_3').css('display','block');
             $('#tab_1').css('display','none');
          }
     }
     if(excitation != ''){
        $('#radio_'+excitation).attr('checked','true');
        if(excitation == 2){
	       $('#sum').removeClass('gray');
	       $('#sum').removeAttr('disabled');
	       $('#sumRate').addClass('gray');
	       $('#sumRate').attr('disabled');
	       $('#sumRate').val('');
	    }else if(excitation == 3){
	       $('#sumRate').removeClass('gray');
	       $('#sumRate').removeAttr('disabled');
	       $('#sum').addClass('gray');
	       $('#sum').attr('disabled');
	       $('#sum').val('');
	    }
     }else{
        $('#excitation').val('1');
     }
     if(mode == '2'){
        $('#mode').attr('checked','true');
     }else{
        $('#excitationMode').val("1");
     }
     
     
     
});

$(function(){
	 dqzt(2);
     //样式选中
     $("#jk_hover").attr('class','nav_first');
	 $("#jk_hover div").removeClass('none');
	 var flag = true;
	 $('#bcbtn').click(function(){
	      if(flag){  
		       flag = false;
		       $('#form').submit();
	       }
	 });
	 //上传图片
	 $("#btn_personalHead").click(function(){
			var dir = getDirNum();
			var json = "{'fileType':'JPG,BMP,GIF,TIF,PNG','fileSource':'user/"+dir+"','fileLimitSize':0.5,'title':'上传图片','cfn':'uploadCall','cp':'img'}";
			json = encodeURIComponent(json);
			window.showModalDialog("uploadFileAction.do?obj="+json,window,"dialogWidth=500px;dialogHeight=400px");
			var headImgPath = $("#img").attr("src");
			if(headImgPath ==""){
				alert("上传失败！");	
			}
	  });
	  $('#sysimg img').click(function(){
	      $('#imgPath').val($(this).attr('src'));
	      $('#img').attr('src',$(this).attr('src'));
	  });
	  $('#r_1').click(function(){
	      $('#tab_1').css('display','block');
	      $('#tab_2').css('display','none');
	      $('#tab_3').css('display','none');
	      $('#radioval').val('1');
	  });
	  $('#r_2').click(function(){
	      var personalImg=$('#personalImg').val();
	      $('#tab_1').css('display','none');
	      $('#tab_2').css('display','block');
	      $('#tab_3').css('display','none');
	      $('#radioval').val('2');
	      $('#img').attr('src',personalImg);
	      $('#imgPath').val(personalImg);
	  });
	  $('#r_3').click(function(){
	      $('#tab_1').css('display','none');
	      $('#tab_2').css('display','none');
	      $('#tab_3').css('display','block');
	      $('#radioval').val('3');
	  });
      switchCode();
});
function switchCode(){
	    var timenow = new Date();
	    $('#codeNum').attr('src','admin/imageCode.do?pageId=borrow&d='+timenow);
};				     
</script>
<script>
function uploadCall(basepath,fileName,cp){
	if(cp == "img"){
		var path = "upload/"+basepath+"/"+fileName;
		$("#img").attr("src",path);
		$("#setImg").attr("src",path);
		$("#imgPath").val(path);
	}
}
function getDirNum(){
	var date = new Date();
	var m = date.getMonth()+1;
	var d = date.getDate();
	if(m<10){
		m = "0"+m;
    }
	if(d<10){
	   d = "0"+d;
	}
	var dirName = date.getFullYear()+""+m+""+d;
	return dirName; 
}
</script>


<script>
$(function(){
$('input[name="excitationType"]').click(function(){
	    if($(this).val() == 2){
	       $('#sum').removeClass('gray');
	       $('#sum').removeAttr('disabled');
	       $('#sumRate').addClass('gray');
	       $('#sumRate').attr('disabled');
	       $('#sumRate').val('');
	    }else if($(this).val() == 3){
	       $('#sumRate').removeClass('gray');
	       $('#sumRate').removeAttr('disabled');
	       $('#sum').addClass('gray');
	       $('#sum').attr('disabled');
	       $('#sum').val('');
	    }else{
	       $('#sumRate').addClass('gray');
	       $('#sumRate').attr('disabled');
	       $('#sumRate').val('');
	       $('#sum').addClass('gray');
	       $('#sum').attr('disabled');
	       $('#sum').val('');
	    }
	    $('#excitation').val($(this).val());
	 });
	 $('#mode').click(function(){
	    var check = $(this).attr('checked');
	    if(check == 'checked'){
	        $('#excitationMode').val('2');	    
	    }else{
	        $('#excitationMode').val('1');
	    }
	 });
});
</script>
</body>
</html>