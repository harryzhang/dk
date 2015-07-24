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
<form id="form" action="addBorrowSeconds.do" method="post">
<div class="nymain">
  <div class="bigbox" style="border:none">

  <div class="sqdk" style="background:none;">
	<div class="l-nav">
    <ul>
    <li><a href="querBaseData.do?from=1"><span>step1 </span> 基本资料</a></li>
    <li><a href="userPassData.do?from=1"><span>step2 </span> 上传资料</a></li>
    <li class="on last"><a href="javascript:void(0);"><span>step3 </span> 发布贷款</a></li>
    </ul>
    </div>
    <div class="r-main">
    <div class="til01">
	<ul id="ul"><li class="on">发布贷款</li></ul>
		<span class="fred" style="color: red;font-size: 12px; padding-left: 80px;line-height: 50px;"><s:fielderror fieldName="enough"></s:fielderror></span>
	</div>
    <div class="rmainbox">
  
    <p class="tips"><span class="fred">*</span> 为必填项，所有资料均会严格保密。 </p>
    <div class="tab">
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <th colspan="2" align="left">借款基本信息</th>
    </tr>
  <tr>
    <td align="right">借款标题：<span class="fred">*</span></td>
    <td><s:textfield name="paramMap.title" cssClass="inp280"/>
        <span class="fred"><s:fielderror fieldName="paramMap['title']"></s:fielderror></span>
    </td>
  </tr>
  <tr>
    <td align="right">借款图片：<span class="fred">*</span>
    <s:hidden name="paramMap.imgPath" id="imgPath"/>
    </td>
    <td>
    <input type="hidden" id="personalImg" value="${user.personalHead}"/>
    <input type="radio" name="radio" id="r_1" checked="checked" value="1" />
      上传借款图片 
        <input type="radio" name="radio" id="r_2" value="2" />
        使用用户头像 
        <input type="radio" name="radio" id="r_3" value="3" />
        <input type="hidden" id="radioval" name="paramMap.radioval" value="${paramMap.radioval}"/>
        使用系统头像<span class="fred"><s:fielderror fieldName="paramMap['imgPath']"></s:fielderror></span></td>
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
     <td class="tx"><img id="img" src="" width="62" height="62"/></td>
  </tr>
  <tr>
    <td align="right">&nbsp;</td>
    <td class="tishi">（推荐使用您的生活近照，或其他与借款用途相关的图片，<br />
      有助增加借款成功几率。严禁使用他人照片） </td>
  </tr>
  <tr>
    <td align="right">借款标的：</td>
    <td><span id="typeName" class="fred">${session.typeName}</span></td>
  </tr>
  <tr>
    <td align="right">借款目的：</td>
    <td class="tishi">
    <s:select list="borrowPurposeList" id="purpose" name="paramMap.purpose" cssClass="sel_140" listKey="selectValue" listValue="selectName"  value="1"></s:select>
    </td>
  </tr>
  <tr>
    <td align="right">还款方式：</td>
    <td >
                    秒还<s:hidden name="paramMap.borrowWay" value="3"/>
    </td>
  </tr>
  <tr>
    <td align="right">借款总额：<span class="fred">*</span></td>
    <td>
    <s:textfield name="paramMap.amount" cssClass="inp100x"/>
    <span class="fred"><s:fielderror fieldName="paramMap['amount']"></s:fielderror></span></td>
  </tr>
  <tr>
    <td align="right">年利率：<span class="fred">*</span></td>
    <td>
    <s:textfield name="paramMap.annualRate" cssClass="inp100x"/>%
    <span class="fred"><s:fielderror fieldName="paramMap['annualRate']"></s:fielderror></span></td>
  </tr>
  <s:if test="#request.subscribeStatus!=1">
  <tr>
    <td align="right">最低投标金额：</td>
    <td>
   
     <s:select list="borrowMinAmountList" name="paramMap.minTenderedSum" cssClass="sel_140" listKey="key" listValue="value"  ></s:select> 
    <span class="fred"><s:fielderror fieldName="paramMap['minTenderedSum']"></s:fielderror></span>
    </td>
  </tr>
  <tr>
    <td align="right">最多投标金额：${subscribe_status }</td>
    <td>
    <s:select list="borrowMaxAmountList" name="paramMap.maxTenderedSum" cssClass="sel_140" listKey="key" listValue="value" headerKey="" headerValue="没有限制" ></s:select>
    </td>
  </tr>
  </s:if>
  <s:else>
  	 <td align="right">最小认购单位：<span class="fred">*</span></td>
    <td>
    <s:textfield name="paramMap.subscribe"  cssClass="inp100x"/>元
    <span class="fred"><s:fielderror fieldName="paramMap['subscribe']"></s:fielderror></span>
    	<input name="subscribeStatus" type="hidden" id="subscribeStatus"  value="${subscribeStatus}"/>
  
    </td>
  </s:else>
  <tr>
    <td align="right">筹标期限：</td>
    <td>
     <input type="hidden" name="validateDay" value="${validateDay }" />
    <s:if test="#request.validateDay==1">
    	<input type="hidden" name="paramMap.raiseTerm" value="0" />无限制
    </s:if>
    <s:else>
    	<s:select list="borrowRaiseTermList" id="raiseTerm" name="paramMap.raiseTerm" cssClass="sel_140" listKey="key" listValue="value"   headerKey="" headerValue="--请选择--"></s:select>
    	<span class="fred"><s:fielderror fieldName="paramMap['raiseTerm']"></s:fielderror></span>
    </s:else>
    </td>
  </tr>
  <tr>
    <td align="right">借款详情：</td>
    <td>
    <s:textarea name="paramMap.detail" cssClass="txt420"/></td>
  </tr>
  <tr>
     <td>&nbsp;</td>
     <td><s:fielderror fieldName="paramMap['detail']"></s:fielderror></td>
  </tr>
  <tr>
    <th colspan="2" align="left"> 设置投标密码</th>
    </tr>
  <tr>
    <td align="right">&nbsp;</td>
    <td>投标密码：
    <input type="password" name="paramMap.investPWD" value="${paramMap.investPWD}" id="investPWD" class="inp100x" disabled="disabled"/>&nbsp;
    <label><s:checkbox id="hasPWD" name="paramMap.hasPWD" />&nbsp;有密码</label>
    </td>
  </tr>
  <tr><td></td><td><span class="fred"><s:fielderror fieldName="paramMap['investPWD']"></s:fielderror></span></td></tr>
  <tr>
    <th colspan="2" align="left"> 提交借款信息</th>
    </tr>
  <tr>
    <td align="right"> 验证码：</td>
    <td>
    <s:textfield name="paramMap.code" id="code" cssClass="inp100x"/>
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
</form>
<!-- 引用底部公共部分 -->     
<jsp:include page="/include/footer.jsp"></jsp:include>
<script>
var flag = true;
$(document).ready(function(){ 
   var sd=parseInt($(".l-nav").css("height"));
    var sdf=parseInt($(".r-main").css("height"));
	 $(".l-nav").css("height",sd>sdf?sd:sdf-15);
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
});
$(function(){
     //样式选中
 
	 if($('#imgPath').val()==""){
		 $('#imgPath').val("images/default-img.jpg");
		 }
	 $('#img').attr('src',$('#imgPath').val());
	 $('#bcbtn').click(function(){
		 
		  if($("#subscribeStatus").val()!=1){
		       var arStart = $('#paramMap_minTenderedSum').val();
		       var amount = $('#amount').val();
			   arStart = parseFloat(arStart);
			   amount = parseFloat(amount);
			   var arEnd = $('#paramMap_maxTenderedSum').val();
			   arEnd = parseFloat(arEnd);
			   if(arStart > arStart){
				   alert('最多投标金额不能小于最低投标金额!');
				   $('#paramMap_maxTenderedSum')[0].selectedIndex = 0;
			      return false;
			   }
			   if(arStart > amount){
				   alert('最低投标金额不能超过借款金额!');
				   $('#paramMap_minTenderedSum')[0].selectedIndex = 0;
				   return false;
			   }
			   if(arEnd > amount){
				   alert('最多投标金额不能超过借款金额!');
				   $('#paramMap_maxTenderedSum')[0].selectedIndex = 0;
				   return false;
			   }
		   }
		   if(flag){
	          $('#form').submit();
	          flag = false;
		   }
	 });
	 $("#paramMap_maxTenderedSum").change(function(){
	       var arStart = $('#paramMap_minTenderedSum').val();
		   arStart = parseFloat(arStart);
		   var arEnd = $(this).val();
		   arEnd = parseFloat(arEnd);
		   if(arEnd < arStart){
			   alert('最多投标金额不能小于最低投标金额!');
			   $('#paramMap_maxTenderedSum')[0].selectedIndex = 0;
		   }
	 });
	 $("#paramMap_minTenderedSum").change(function(){
	       $('#paramMap_maxTenderedSum')[0].selectedIndex = 0;
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
	  $('#hasPWD').click(function(){
	    var hasPWD = $('#hasPWD').attr('checked');
        if(hasPWD =='checked'){
          $('#investPWD').attr('disabled',false);
        }else{
          $('#investPWD').attr('disabled',true);
        }
        $('#investPWD').val('');
	  });
      switchCode();
});
function switchCode(){
        var hasPWD = $('#hasPWD').attr('checked');
        if(hasPWD =='checked'){
          $('#investPWD').attr('disabled',false);
        }else{
          $('#investPWD').attr('disabled',true);
        }
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
</body>
</html>