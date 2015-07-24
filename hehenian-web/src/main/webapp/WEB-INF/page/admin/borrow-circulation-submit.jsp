<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="This is my page" />
		<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
		
		<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
		<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
		<script type="text/javascript" language="javascript">
	    	$(function(){
				$('#img').attr('src',"../"+$('#paramMap_img').val());
				//提交表单
				$("#btn_save").click(function(){
					$(this).hide();
					$("#addAdmin").submit();
					return false;
				});
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
});
function uploadCall(basepath,fileName,cp){
	if(cp == "img"){
		var path = "upload/"+basepath+"/"+fileName;
		$("#img").attr("src","../"+path);
		$("#paramMap_img").val(path);
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
<script >
  $(function(){
   $('#imgPath').val('${paramMap.imgPath}');
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

</script>
<script>
$(function(){
     /* add by lw*/
    $('#excitation').val('${paramMap.excitationType}');
     $('#excitationMode').val('${paramMap.excitationMode}');
    var excitation = $('#excitation').val();
     var mode = $('#excitationMode').val();
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
     /*-----------*/
</script>
</head>
	<body>
	<form id="addAdmin" action="addCirculationBorrow.do" method="post">
			<div id="right"
				style="background-image: url; background-position: top; background-repeat: repeat-x;">
				<div style="padding: 15px 10px 0px 10px;">
					<div>
						<table width="200" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="100" height="28" align="center" 
									class="xxk_all_a">
									<a href="publishCirculation.do">代发流转标</a>
								</td>
								<td width="2">
									&nbsp;
								</td>
								<td width="100" align="center"  class="main_alll_h2">
									添加代发流转标
								</td>
								<td>
									&nbsp;
								</td>
							</tr>
						</table>
					</div>
					<div style="width: auto; background-color: #FFF; padding: 10px;">
						<table width="100%" border="0" cellspacing="1" cellpadding="3">
							<tr>
								<td style="height: 25px;" align="right" class="blue12">
									借款标题：
								</td>
								<td align="left" class="f66">
								    <s:textfield name="paramMap.title" theme="simple"
										cssClass="in_text_2" cssStyle="width: 150px" ></s:textfield>
									<span style="color: red;">*<s:fielderror
											fieldName="paramMap['title']" />
									</span>
								</td>

							</tr>
							<tr>
								<td style="width: 100px; height: 25px;" align="right"
									class="blue12">
									借款图片：
								</td>
								<td align="left" class="f66">
								    <s:hidden name="paramMap.img"></s:hidden>
									<img id="img" src="${headImg}" width="62" height="62"/> <a href="javascript:void(0);" id="btn_personalHead" class="scbtn">上传图片</a>
								    <span style="color: red;">*<s:fielderror
											fieldName="paramMap['img']" />
									</span>
								</td>
							</tr>
							<tr>
								<td style="width: 100px; height: 25px;" align="right"
									class="blue12">
									借款标的：
								</td>
								<td align="left" class="f66">
									<span style="color: red;">流转标借款
									</span>
								</td>
							</tr>
							<tr>
								<td style="height: 25px;" align="right" class="blue12">
									借款目的：
								</td>
								<td align="left" class="f66">
									<s:select list="borrowPurposeList" id="purpose" name="paramMap.purpose" cssClass="sel_140" listKey="selectValue" listValue="selectName"  headerKey="" headerValue="--请选择--"></s:select>
									<span style="color: red;">*<s:fielderror
											fieldName="paramMap['purpose']" />
									</span>
								</td>
							</tr>
							<tr>
								<td style="height: 25px;" align="right" class="blue12">
									回购期限：
								</td>
								<td align="left" class="f66">
									<s:select list="borrowTurnlineList" id="deadLine" name="paramMap.deadLine" cssClass="sel_140" listKey="key" listValue="value"   headerKey="" headerValue="--请选择--"></s:select>
									<span style="color: red;"><s:fielderror
											fieldName="paramMap['deadLine']" />
									</span>
								</td>
							</tr>
							<tr>
								<td style="width: 100px; height: 25px;" align="right"
									class="blue12">
									还款方式：
								</td>
								<td align="left" class="f66">
									<span style="color: red;">*
										一次性 
									</span>
								</td>
							</tr>
							<tr>
								<td style="width: 100px; height: 25px;" align="right"
									class="blue12">
									借款总额：
								</td>
								<td align="left" class="f66">
								<input type="hidden" name="paramMap.borrowMoneyfirst" value="${paramMap.borrowMoneyfirst }" />
    <input type="hidden" name="paramMap.borrowMoneyend" value="${paramMap.borrowMoneyend }"/>
      <input type="hidden" name="paramMap.accountmultiple" value="${paramMap.accountmultiple }"/>
									<s:textfield name="paramMap.amount" theme="simple"
										cssClass="in_text_2" cssStyle="width: 150px" ></s:textfield>
									<span style="color: red;">*<s:fielderror
											fieldName="paramMap['amount']" />
									</span>
									 【借款总额】的金额范围<a style="color: red;">${paramMap.borrowMoneyfirst }~${paramMap.borrowMoneyend }元，而且是${paramMap.accountmultiple }的倍数</a>
								</td>
							</tr>
							<tr>
								<td style="width: 100px; height: 25px;" align="right"
									class="blue12">
									年利率：
								</td>
								<td align="left" class="f66">
								<input type="hidden" name="paramMap.aprfirst" value="${paramMap.aprfirst }"/>
      <input type="hidden" name="paramMap.aprend" value="${paramMap.aprend }"/>
									<s:textfield name="paramMap.annualRate" theme="simple"
										cssClass="in_text_2" cssStyle="width: 150px" ></s:textfield>
									<span style="color: red;">*<s:fielderror
											fieldName="paramMap['annualRate']" />
									</span>
									 【年利率】的比率范围<a style="color: red;">${paramMap.aprfirst }~${paramMap.aprend }%</a>
								</td>
							</tr>
							<tr>
								<td style="width: 100px; height: 25px;" align="right"
									class="blue12">
									最小流转单位：
								</td>
								<td align="left" class="f66">
									<s:textfield name="paramMap.smallestFlowUnit" theme="simple"
										cssClass="in_text_2" cssStyle="width: 150px" ></s:textfield>
									<span style="color: red;">*<s:fielderror
											fieldName="paramMap['smallestFlowUnit']" />
									</span>
								</td>
							</tr>
							<tr>
								<td style="width: 100px; height: 25px;" align="right"
									class="blue12">
									商业详情：
								</td>
								<td align="left" class="f66">
								    <s:textarea name="paramMap.businessDetail" theme="simple"
										cols="30" rows="10"> </s:textarea>
									<span style="color: red;">*<s:fielderror
											fieldName="paramMap['businessDetail']" />
									</span>
								</td>
							</tr>
							<tr>
								<td style="width: 100px; height: 25px;" align="right"
									class="blue12">
									资产情况：
								</td>
								<td align="left" class="f66">
								    <s:textarea name="paramMap.assets" theme="simple"
										cols="30" rows="10"> </s:textarea>
									<span style="color: red;">*<s:fielderror
											fieldName="paramMap['assets']" />
									</span>
								</td>
							</tr>
							<tr>
								<td style="width: 100px; height: 25px;" align="right"
									class="blue12">
									资金用途：
								</td>
								<td align="left" class="f66">
								    <s:textarea name="paramMap.moneyPurposes" theme="simple"
									 cols="30" rows="10"> </s:textarea>
									<span style="color: red;">*<s:fielderror
											fieldName="paramMap['moneyPurposes']" />
									</span>
								</td>
							</tr>
							
							  
  <!--  -->
   <tr>
    <th> 投标奖励</th>
    </tr>
       <!--  	<tr>
				<td style="width: 100px; height: 25px;" align="right"
									class="blue12">
									不设置奖励
								</td>
								<td align="left" class="f66">
								    <s:textarea name="paramMap.moneyPurposes" theme="simple"
									 cols="30" rows="10"> </s:textarea>
									<span style="color: red;">*<s:fielderror
											fieldName="paramMap['moneyPurposes']" />
									</span>
								</td>
			 	</tr>
       -->  
  
     <tr>
    <td align="right">&nbsp;<input type="hidden" id="excitation" name="paramMap.excitationType" value="${paramMap.excitationType}"/></td>
    <td><input type="radio" name="excitationType" checked="checked" id="radio_1" value="1" />
      不设置奖励</td>
  </tr>
  <tr>
    <td align="right">&nbsp;</td>
    <td><input type="radio" name="excitationType" id="radio_2" value="2" />
      固定总额按投标比例分配奖励
      <input type="hidden" name="paramMap.accountfirst" value="${paramMap.accountfirst }"/>
         <input type="hidden" name="paramMap.accountend" value="${paramMap.accountend }"/>
      <input type="text" id="sum" name="paramMap.sum" value="${paramMap.sum}" class="inp100x gray" disabled="disabled"/>
      元<br />(奖励金额在<a style="color: red;">${paramMap.accountfirst }~${paramMap.accountend }</a>元区间【固定总额奖励】)<a style="color: red;"><s:fielderror fieldName="paramMap['sum']"></s:fielderror></a>
      
      
      </td>
  </tr>
  <tr>
    <td align="right">&nbsp;<input type="hidden" id="excitationMode" name="paramMap.excitationMode" value="${paramMap.excitationMode}"/></td>
    <td><input type="radio" name="excitationType" id="radio_3" value="3" />
      借款总额百分比分配奖励
       <input type="hidden"  name="paramMap.scalefirst" value="${paramMap.scalefirst }"/>
         <input type="hidden" name="paramMap.scaleend" value="${paramMap.scaleend }"/>
      <input type="text" id="sumRate" name="paramMap.sumRate" maxlength="20" value="${paramMap.sumRate}" class="inp100x gray" disabled="disabled"/>
      %<br />(奖励比例在<a style="color: red;">${paramMap.scalefirst }~${paramMap.scaleend }%</a>区间【借款总额比例奖励】)<a style="color: red"><a style="color: red;"><s:fielderror fieldName="paramMap['sumRate']"></s:fielderror></a></td>
  </tr>
  
  <!--  -->
							
							<tr>
								<td height="25">
								</td>
								<td align="left" class="f66" style="color: Red;">
									<s:fielderror fieldName="paramMap.allError" />
								</td>
							</tr>
							<tr>
								<td height="36" align="right" class="blue12">
									&nbsp;
								</td>
								<td>
									&nbsp;
								</td>
							</tr>
							<tr>
								<td height="36" align="right" class="blue12">
									&nbsp;
								</td>
								<td>
                                <button id="btn_save" style="background-image: url('../images/admin/btn_queding.jpg');width: 70px;border: 0;height: 26px"  ></button>
                                &nbsp;<button type="reset" style="background-image: url('../images/admin/btn_chongtian.jpg');width: 70px;height: 26px;border: 0px"></button>&nbsp;
                                &nbsp;
                                <span style="color: red;">
                             	  <s:fielderror fieldName="actionMsg" theme="simple"></s:fielderror>
                                </span>
                            </td>
							</tr>
						</table>
						<br />
					</div>
				</div>
			</div>
		</form>
	</body>
</html>
