<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<script type="text/javascript">

  
  function bt_save(pa){
   
      var general=$("#general_"+pa).val();
      var fieldVisit=$("#fieldVisit_"+pa).val();
      var organization=$("#organization_"+pa).val();
      var netWorth=$("#netWorth_"+pa).val();
      var param={};
      param["paramMap.general"]=general;
      param["paramMap.fieldVisit"]=fieldVisit;
      param["paramMap.organization"]=organization;
      param["paramMap.netWorth"]=netWorth;
      param["paramMap.type"]=pa;
      if((/^\d{1,2}(\.\d{1,2})?$/.test(general))&&(/^\d{1,2}(\.\d{1,2})?$/.test(fieldVisit))&&(/^\d{1,2}(\.\d{1,2})?$/.test(organization))&&(/^\d{1,2}(\.\d{1,2})?$/.test(netWorth))){
           $.post("updateReferralBonuses.do",param,function(data){
              if(data==1){
                  alert("修改成功");
               }else if(data==2){
                  alert("修改失败");
               }
           
            });	
      }else{
         alert("请输入正确百分比数字,不能高于50%");
      }
      
 }
 
</script>
<table width="100%" border="0" cellspacing="1" cellpadding="3">
<tr>
 <s:iterator value="referralList" var="bean">
 <td>

  <table width="90%" border="0" >
							<tr>
								<td colspan="2" style="width: 100px; height: 25px;" align="center"
									class="blue12">
                                      <h3>${bean.typedesc}</h3>
                                      <p>${bean.desc}</p>
                                      <input type="hidden" id="type_${bean.type}" name="paramMap.type" value="${bean.type}"/>
								</td>						
							</tr>	
							<tr>
								<td style="width: px; height: 25px;" align="right"
									class="blue12">
                                       业主贷：
								</td>
								<td align="left" class="f66" width="50px">
									<input id="general_${bean.type}" style="width: 40px;" type="text" class="in_text_250" name="paramMap.general" value="${bean.general}"/>&nbsp;%
								</td>
							</tr>	
							
							<tr>
								<td style="width: 100px; height: 25px;" align="right"
									class="blue12">
                                      实地考察借款：
								</td>
								<td align="left" class="f66">
								  <input id="fieldVisit_${bean.type}" type="text" style="width: 40px;" class="in_text_250" name="paramMap.fieldVisit" value="${bean.fieldVisit}"/>&nbsp;%									
								</td>
							</tr>	
							<tr>
								<td style="width: 100px; height: 25px;" align="right"
									class="blue12">
                                      机构担保借款：
								</td>
								<td align="left" class="f66">
								  <input id="organization_${bean.type}" class="in_text_250" style="width: 40px;" type="text" name="paramMap.organization" value="${bean.organization}"/>&nbsp;%					
								</td>
							</tr>	
							<tr>
								<td style="width: 100px; height: 25px;" align="right"
									class="blue12">
                                     薪金贷：
								</td>
								<td align="left" class="f66">
								 <input id="netWorth_${bean.type}" style="width: 40px;" class="in_text_250" type="text" name="paramMap.netWorth" value="${bean.netWorth}"/>&nbsp;%					
									
								</td>
							</tr>
						
							
							<tr>
								<td height="36" align="right" class="blue12">
									
									&nbsp;
								</td>
								<td>
									
								<input type="button" onclick="bt_save(${bean.type})"
								style="background-image: url('../images/admin/btn_queding.jpg'); width: 70px; height: 26px; border: 0px"/>
								&nbsp; &nbsp;
								<span class="require-field"><s:fielderror fieldName="actionMsg" theme="simple"></s:fielderror></span>
								</td>
							</tr>
							<tr>
								<td colspan="2">
									<img id="img" src="../images/NoImg.GIF"
										style="display: none; width: 100px; height: 100px;" />
								</td>
							</tr>
						</table>
						
		</td>
		</s:iterator>
	
  </tr>						
</table>						