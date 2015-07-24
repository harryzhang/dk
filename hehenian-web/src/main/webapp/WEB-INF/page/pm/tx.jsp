<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
    <jsp:include page="/include/cf-head.jsp"></jsp:include>
    </head>
    <body>
    <jsp:include page="/include/cf-top.jsp"></jsp:include>
    <div class="s_sur_ix main el_container" style=" overflow:hidden; margin-bottom:20px;"> 
      <!-- <div style=" margin:10px 0px;"><img src="images/ad.jpg" width="974" height="80"  alt=""/></div> --> 
      <!--左侧-->
      <div class=" nr_left" style=" line-height:36px;">
        <div style="padding:15px 30px;border:1px solid #CCC; background:#f9f9f9;">
          <div>真实姓名：
            <s:property value="#request.realName" default="---"></s:property>
          </div>
          <div>电话号码：
            <s:if test="#request.bindingPhone==null||#request.bindingPhone==''"> <font color="red">尚未绑定手机</font> </s:if>
            <s:else>
              <s:property value="#request.bindingPhone" default="---"></s:property>
            </s:else>
          </div>
          <div>账户余额：<span>
            <s:property value="#request.handleSum" default="---"></s:property>
            元 </span></div>
        
          <div>冻结金额：<span>
            <s:property value="#request.freezeSum" default="---"></s:property>
            元 </span></div>
              <div style=" color:#F60; font-size:16px;">最大可提现金额：<span>
            <s:property value="#request.usableSum" default="---"></s:property>
            <input type="hidden" value="${usableSum}" id="kyye"></input>
            元 </span></div>
          <div>提现金额：
            <input type="text" class="inp140" id="dealMoney" style=" width:280px; height:30px; padding-left:10px;"/>
            元 <span style=" float: none;" id="money_tip" class="formtips"></span>
            
            <s:if test="#request.banks!=null && #request.banks.size>0"> </s:if>
            <s:else> </s:else>
          </div>
          <div>提现银行信息：<span style="float: none;" class="formtips"> * 以下是绑定的银行卡信息，如果没有银行卡请先进行提现银行卡设置</span> </div>
           <s:if test="#request.banks!=null && #request.banks.size>0">
          <div>
           
              <s:iterator value="#request.banks" var="bean" status="sta"> 
                <!-- 并且银行卡的状态为绑定状态 -->
                <s:if test="#bean.cardStatus==1"> 开户行：
                  <input type="radio" name="wbank" value="${bean.id}-${bean.cardNo}" />
                  ${bean.bankName}&nbsp;&nbsp;&nbsp;&nbsp;银行账号：
                  <s:property
													value="#request.bean.cardNo.substring(0,4)+' '+'****' +' ' + #request.bean.cardNo.substring(#request.bean.cardNo.length() - 4)" />
                </s:if>
              </s:iterator>
            
          </div>
          </s:if>
          <s:else>
          <div style=" text-align:center; margin-bottom:30px; margin-top:30px;">
          <div style=" width:90%; margin:0px auto; line-height:42px;border:1px solid #CCC; background:#FFF ; font-size:16px;">
          <a target="_blank" href="/bindCardInit.do" style=" color:#F30 ; font-weight:bold">
          暂未设置提现银行，点此设置提现银行卡</a> 
          </div>
          </div>
          </s:else>
          
          <div>
          </div>
          <div style=" text-align:center">
            <input type="button" id="btn_submit" onclick="addWithdraw();" value="确认提交" style="width: 238px; height: 40px; border: 0px; background: #892E92; font-size: 16px; color: #FFF; margin-left: 0px;" />
          </div>
         <!--  <div  style=" text-align:center"><span>* 温馨提示：禁止信用卡套现</span> </div> -->
          <div></div>
          <div></div>
          <div></div>
          <div></div>
          <div></div>
        </div>
        <table width="100%" style=" line-height:26px; font-size:14px; margin-top:30px;">
          <tr>
            <td><strong style=" color:#F90">温馨提示：</strong></td>
          </tr>
          <tr>
            <td>（1）会员须开通第三方托管账户、绑定银行卡才能进行取现操作，请确保您绑定的银行户名与您在网站填写的真实姓名一致。</td>
          </tr>
          <tr>
            <td>（2）为防止套现，所充资金必须经投标回款后才能取现。</td>
          </tr>
          <tr>
            <td>（3）到账时间规定：<br />
              &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;a. 普通取现：会员在工作日18：00前的取现申请，通过审核后一般可于下1个工作日到账；周末或节假日提交的取现申请，在之后第1个工作日到账。<br /></td>
          </tr>
          <tr>
            <td>（4）您目前能取现的最高额度是
              <s:property value="#request.usableSum" default="---"></s:property>
              元。</td>
          </tr>
        </table>
        <form id="f_addWithdraw" target="_blank" action="/addWithdraw.do" method="post" >
          <input type="hidden" id="int_dealpwd" name="dealpwd" />
          <input type="hidden" id="int_money" name="money" />
          <input type="hidden" id="int_cellPhone" value="${bindingPhone}" name="cellPhone" />
          <input type="hidden" id="int_code" name="code" />
          <input type="hidden" id="int_bankId" name="bankId" />
          <input type="hidden" id="int_openAcctId" name="openAcctId" />
          <input type="hidden" id="int_type" name="type" />
        </form>
        <div class="biaoge" id="biaoge" style="margin-top:25px;margin-right: 10px;"> <span id="withdraw"></span> </div>
        
        <!--右侧--></div>
      <jsp:include page="/include/cf-right.jsp"></jsp:include>
    </div>
    <jsp:include page="/include/cf-footer.jsp"></jsp:include>
</body>
<script>
function addWithdraw() {
	var bank = $("input:radio[name='wbank'][checked]").val();
	if (bank == undefined || bank == '') {
		alert("请设置或者选择提现银行卡信息");
		return;
	} else if ($("#dealMoney").val() == "") {
		alert("请填写提现金额");
		return;
	}else  {
		var dealMoney=$("#dealMoney").val()-0;
		var kyye=$("#kyye").val()-0;
		if(dealMoney>kyye){
			alert("提现金额不能超过最大可提现金额");
			return;
		}
	}
	
	if (!window.confirm("确定进行申请提现?")) {
		return;
	}

	var b = bank.indexOf("-");
	var bankId = bank.substring(0, b);
	var openAcctId = bank.substring(b + 1);

	$("#int_dealpwd").val($("#dealpwd").val());
	$("#int_money").val($("#dealMoney").val());
	$("#int_code").val($("#code").val());
	$("#int_bankId").val(bankId);
	$("#int_openAcctId").val(openAcctId);
	$("#int_type").val($("#type").val());
	
	$("#f_addWithdraw").submit();
	
}
</script>
</html>