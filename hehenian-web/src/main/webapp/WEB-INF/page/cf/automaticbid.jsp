<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<jsp:include page="/include/cf-head.jsp"></jsp:include>

<link href="/newcss/style.css" rel="stylesheet" type="text/css" />

</head>
<body>
<jsp:include page="/include/cf-top.jsp"></jsp:include>
<div class="s_sur_ix main el_container" style=" overflow:hidden; margin-bottom:20px;">
    <!--左侧-->
    <div class=" nr_left"> 
          <div class="u-right">
        <div class="user-right">
          <h2>自动投标</h2>
          <div class="tzjl_cgtz_box"  style=" width:100%">
            <div class="sidebar_box">
              <div class="sidebar_box_top"></div>
              <div style="  line-height:36px; background:#ecf8fd; padding:20px 0px;"> 
                <script>
function zhankai(){
$("#class1content").slideToggle();
if($("#stateBut").text().indexOf("展开")>0){
	$("#stateBut").text("↑ 收起更多设置");
}else{
	$("#stateBut").text("↓ 展开更多设置");
}
}
</script>

               <div>
                	<ul>
                   	  <li style=" width:359px; margin-right:20px; float:left; text-align:right">自动投标状态：</li>
                      <li style=" float:left; width:359px; padding-top:7px;"><span><s:if test="%{automaticBidMap.bidStatus ==2}"><img src="/newimg/kai.png" width="44" height="22"  alt=""/></s:if><s:else><img src="/newimg/guan.png" width="44" height="22"  alt=""/></s:else>
                  </span></li>
                  </ul>
              </div>
              <!---->
              <div>
              	<ul>
               	  <li style=" width:359px; margin-right:20px; float:left; text-align:right">您的账户余额：</li>
                  <li style=" float:left; width:359px;"><span>${automaticBidMap.usableSum}元
                  <input type="hidden" value="${automaticBidMap.usableSum}" id="usableSum" name="usableSum" />
                  </span></li>
                </ul>
             </div>
             <!---->
             <div>
             	<ul>
               	  <li style=" width:359px; margin-right:20px; float:left; text-align:right">账户保留金额：</li>
                  <li style=" float:left; width:359px;"><input type="text" class="inp100x" id="remandAmount"  value="${automaticBidMap.remandAmount==''?0:automaticBidMap.remandAmount}" />
                  &nbsp;元</li>
                </ul>
             </div>
             <!---->
             <div id="class1content" style="display:none">
                <div>
                	<ul>
                   	  <li style=" width:359px; margin-right:20px; float:left; text-align:right">每次（最大）投标金额：</li>
                      <li style=" float:left; width:359px;"><input disabled="disabled" type="text" class="inp100x" id="bidAmt" maxlength="20" value="100000" />
                    &nbsp;元</li>
                  	</ul>
                </div>
                <!---->
                <div>
                	<ul>
                   	  <li style=" width:359px; margin-right:20px; float:left; text-align:right">利率范围：</li>
                   	  <li style=" float:left; width:359px;"><input type="text" onfocus="this.select()" class="inp100x" id="rateStart" maxlength="20" value="${automaticBidMap.rateStart == '' ? 0:automaticBidMap.rateStart}" style="width: 50px;" />% &nbsp;-- &nbsp;
                    <input onfocus="this.select()"  type="text" class="inp100x" id="rateEnd" maxlength="20" value="${automaticBidMap.rateEnd== '' ? 24:automaticBidMap.rateEnd}" style="width: 50px;" />%</li>
                    </ul>
                </div>
                <!---->
                <div>
                	<ul>
<li style=" width:359px; margin-right:20px; float:left; text-align:right">借款期限：</li>
<li style=" float:left; width:359px;"><s:select cssStyle="width: 70px; border:1px solid #e5e5e5"
									list="#{1:'1个月',2:'2个月',3:'3个月',4:'4个月',5:'5个月',6:'6个月',7:'7个月',8:'8个月',9:'9个月',10:'10个月',11:'11个月',12:'12个月',18:'18个月',24:'24个月',30:'30个月',36:'36个月'}"
									id="deadlineStart" cssClass="sel_70" listKey="key" listValue="value" name="automaticBidMap.deadlineStart"></s:select>
                    --&nbsp;
                    <s:select
								cssStyle="width: 70px; border:1px solid #e5e5e5"	list="#{1:'1个月',2:'2个月',3:'3个月',4:'4个月',5:'5个月',6:'6个月',7:'7个月',8:'8个月',9:'9个月',10:'10个月',11:'11个月',12:'12个月',18:'18个月',24:'24个月',30:'30个月',36:'36个月'}"
									id="deadlineEnd" cssClass="sel_70" listKey="key" listValue="value" name="automaticBidMap.deadlineEnd" headerKey="36" headerValue="36个月"></s:select></li>
									</ul>
                  </div>
                </div>
                <div style=" text-align:center; width:100%; padding:15px 0px;">
                <a id="stateBut" onclick="zhankai();" style=" font-size:18px;cursor: pointer;">↓&nbsp;展开更多设置</a>
                </div>
				<div style=" overflow:hidden; padding-left:310px;">
                  <s:if test="%{automaticBidMap.bidStatus ==2}"> <a href="javascript:;" class="bt_red"><span class="bt_red_lft"></span><strong id="closebtn">关闭自动投标</strong><span class="bt_red_r"></span></a> 
                    <!--<input type="button" id="closebtn" value="关闭自动投标" />--> 
                  </s:if>
                  <s:else> <a href="javascript:;" class="bt_green" id="savebtn"><span class="bt_green_lft"></span><strong id="savebtn">开启自动投标</strong><span class="bt_green_r"></span></a> 
                    <!-- <input type="button" id="savebtn" value="开启自动投标" />--> 
                  </s:else>
                </div>
              </div>
<div class="sidebar_box_bottom"></div>
            </div>
            <div class="sidebar_box">
              <div class="sidebar_box_top"></div>
              <div class="sidebar_box_content">
                <h3>自动投标工具说明</h3>
                <img src="/newimages/info.png" alt="" title="" class="sidebar_icon_right" />
                <ul>
                  <li>自动投标设置完成后即生效；</li>
                  <li>自动投标工具投资规则如下：
                    <ul>
                      <li>投标序列按照开启自动投标的时间先后进行排序;</li>
                      <li>帐户可用余额&lt;100时，不执行自动投标；</li>
                      <li>每次投标金额为100的整数倍； </li>
                    </ul>
                  </li>
                  <li>每次（最大）投标金额是默认值，不需要设置；</li>
                </ul>
              </div>
              <div class="sidebar_box_bottom"></div>
            </div>
          </div>
        </div>
      </div>
      
        </div>
    <div id="autoDiv" style="display: none;"></div>
    <!--右侧-->
    <jsp:include page="/include/cf-right.jsp"></jsp:include>
  </div>
  <jsp:include page="/include/cf-footer.jsp"></jsp:include>
</body>
<script>
$(function(){
	$("#closebtn").click(function() {//关闭自动投标
		param = {};
		param["paramMap.close"] = "close";
		param["retUrl"] = "http://hehenian.colourlife.com/frontChinaPnrUrl.do";
		$.post("/automaticBidModify.do", param, function(data) {
			if (data.length < 30) {
				alert(data);
				return false;
			}
			$("#autoDiv").html(data);
		});
	});
	$("#savebtn").click(function() {//开启自动投标
		var maxAmt = $("#maxAmt").val();
		var bidAmt = $("#bidAmt").val();
		var rateStart = $("#rateStart").val();
		var rateEnd = $("#rateEnd").val();
		var deadlineStart = $("#deadlineStart").val();
		var deadlineEnd = $("#deadlineEnd").val();
		var type = $("input[name=type]:checked").val();
		if (type == '') {
			alert("请选择投标计划类型");
			return false;
		}
		if (type == 'P' && (isNaN(maxAmt) || maxAmt < 100)) {
			alert("请填写投标计划金额");
			return false;
		}
		/* if (isNaN(bidAmt) || bidAmt < 100) {
			alert("请填写每次投标金额");
			return false;
		} */
		var remandAmount=$("#remandAmount").val();
		 if (remandAmount==""||isNaN(remandAmount) ) {
				alert("请填写保留金额");
				return false;
			}
		param={};
		param["paramMap.maxAmt"] = maxAmt;
		param["paramMap.remandAmount"] = remandAmount;
		param["paramMap.bidAmt"] = bidAmt;
		param["paramMap.rateStart"] = rateStart;
		param["paramMap.rateEnd"] = rateEnd;
		param["paramMap.deadlineStart"] = deadlineStart;
		param["paramMap.deadlineEnd"] = deadlineEnd;
		param["paramMap.type"] = type;
		$.post("/automaticBidModify.do", param, function(data) {
			if (data.length < 20) {
				alert(data);
				return false;
			}
			$("#autoDiv").html(data);
		});
	});
})
</script>
</html>