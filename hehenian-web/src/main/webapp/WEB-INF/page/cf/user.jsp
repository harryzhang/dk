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
       <div style=" margin:10px 0px;">
           <a href="/hhn_web/termFinance.do" target="_blank">
           <img src="/images/ad.jpg" width="974" height="308"  alt=""/>
           </a>
       </div>

      <!--左侧-->
      <div class=" nr_left"> 
        <!--筛选栏-->
        <div class="search_all">
          <div style=" border-bottom:1px solid #e5e5e5; padding-bottom:10px; margin-bottom:15px;"><font style=" color:#F60; font-size:20px;">个人信息</font>&nbsp;&nbsp;&nbsp;&nbsp;<font style=" font-size:12px; color:#666">用户名：${homeMap.username}</font></div>
          <div style=" width:100%; margin:0px auto; line-height:24px;">
            <div style=" overflow:hidden; padding:0px 20px 10px 10px; font-size:14px; width:450px; float:left; border-right:1px dashed #CCCCCC;" >
       
              <div style=" line-height:42px; border-bottom:1px dashed #ccc">汇付编号：
                <s:if test="#request.homeMap.usrCustId>0">${homeMap.usrCustId }
                  <input onclick="loginHF();" type="button" id="loginHF" value="登录汇付"  style="width: 100px; height: 28px; font-size: 12px; float:right ;border:0px; background:#999; color:#FFF" />
                </s:if>
                <s:else> <span style="color:#e94718;font-size: 12px;">您还不是汇付天下会员</span>
                  <input type="button" id="registerChinaPnr" value="点击注册" onclick="regChinaPnr();" style="cursor: pointer;width: 100px; height: 28px;font-size: 12px;float:right; border:0px; background:#999; color:#FFF" />
                </s:else>
              </div>
              <div style="  border-bottom:1px dashed #ccc">
               <s:if test="#request.usrCustId>0">
                <p>充值总额：</p>
                <font size="+3" style="color:#F60; line-height:60px;">
                <s:if test="#request.accmountStatisMap.rechargeTotal !=''">${accmountStatisMap.rechargeTotal}</s:if>
                <s:else>0.00</s:else>
                </font>元
                <input type="button" class="input_a" value="充值" onclick="window.location.href='cf-recharge.do'" style="width: 70px; height: 28px; float:right; background:#F90; color:#fff; border:0px;" />
           </s:if>
              </div>
              <s:if test="#session.user.authStep>2">
              <div style=" line-height:42px; border-bottom:1px dashed #ccc">
                <p>提现总额：</p>
                <p><font size="+3" style="color:#360; line-height:60px;">
                <s:if test="#request.accmountStatisMap.usableAmount !=''">${accmountStatisMap.usableAmount}</s:if>
                <s:else>0.00</s:else>
                </font>元
                
                <input type="button" class="input_b" value="提现" onclick="window.location.href='cf-cash.do';"  style="width: 70px; height: 28px;float:right;border:0px; background:#999; color:#FFF" />
                
                </p>
              </div>
              </s:if>
            </div>
            
           	<div style=" width:200px; float:left; padding-left:20px;">
            	  <div style=" line-height:36px;">资产总额：<font size="+1" style="color:#666">
                <s:if test="#request.accmountStatisMap.accountSum !=''">${accmountStatisMap.accountSum}</s:if>
                <s:else>0.00</s:else>
                </font>元</div>
              <div style=" line-height:36px;">冻结总额：<font size="+1" style="color:#666">
                <s:if test="#request.accmountStatisMap.freezeAmount !=''">${accmountStatisMap.freezeAmount}</s:if>
                <s:else>0.00</s:else>
                </font>元</div>
              <div style=" line-height:36px;">可用余额：<font size="+1" style="color:#666">
                <s:if test="#request.accmountStatisMap.usableAmount !=''">${accmountStatisMap.usableAmount}</s:if>
                <s:else>0.00</s:else>
                </font>元</div>
                
              <div style=" line-height:36px; margin-top:30px;">
               <s:if test="#request.homeMap.usrCustId>0">
              <a  href="automaticbid.do" class="bt_green" style=" margin:0px;">
                  <span class="bt_green_lft"></span>
                  <strong>设置自动投标</strong>
                  <span class="bt_green_r"></span>
                  </a>
              </s:if> 
              
          </div>
            
            </div>
          </div>
        </div>
        <div class="search_all" style=" margin-top:20px;">
          <div style=" border-bottom:1px solid #e5e5e5; padding-bottom:10px; margin-bottom:15px;"><font style=" color:#F60; font-size:20px;">收益统计 </font></div>
          <div style=" width:100%; margin:0px auto; line-height:24px;">
            <div class="user_grzx_nr" style=" overflow:hidden">
              <div style=" width:200px; height:130px; margin:10px 35px 10px 10px; background:url(/images/new/user/sy.gif); float:left; text-align:center; padding-top:70px; ">
                <p> <span>您的累计收益</span> </p>
                <p style=" font-size:24px;"> <strong>
                  <s:if test="#request.accmountStatisMap.earnSum !=''">${accmountStatisMap.earnSum}</s:if>
                  <s:else>0.00</s:else>
                  </strong> <font size="-4">元</font></p>
              </div>
              <div style=" width:200px; height:130px;margin:10px 35px 10px 10px; background:url(/images/new/user/sy.gif); float:left; text-align:center; padding-top:70px; ">
                <p> <span>待收利息</span> </p>
                <p style=" font-size:24px;"> <strong>
                  <s:if test="#request.accmountStatisMap.forPayInterest !=''">${accmountStatisMap.forPayInterest}</s:if>
                  <s:else>0</s:else>
                  </strong> <font size="-4">元</font></p>
              </div>
              <div style=" width:200px; height:130px; margin:10px; background:url(/images/new/user/sy.gif); float:left; text-align:center; padding-top:70px; ">
                <p> <span>待收本金</span> </p>
                <p style=" font-size:24px;"> <strong>
                  <s:if test="#request.accmountStatisMap.forPayPrincipal !=''">${accmountStatisMap.forPayPrincipal}</s:if>
                  <s:else>0</s:else>
                  </strong> <font size="-4">元</font></p>
              </div>
            </div>
          </div>
        </div>
        <div class="search_all" style=" margin-top:20px;" id="record"> </div>
        <script>
      $("#record").load("cf-investrecord.do?curPage=${param.curPage}&pageSize=${param.pageSize}");
      </script> 
      </div>
      <!--右侧-->
      <jsp:include page="/include/cf-right.jsp"></jsp:include>
    </div>
    <jsp:include page="/include/cf-footer.jsp"></jsp:include>
    <input type="hidden" value="${idNo}" id="idNo"/>
    <form id="flr" target="_blank" action="" method="post" >
    </form>	
    <!--推荐人-->
    <div class="light-box" id="tuijian" style="display:none;">
    	<div class="light-title">推荐人信息<a id="close_x" class="close light-box-close" href="#">X</a></div>
    	<div class="light-content">
        	<div class="tuijian">
            	<span class="light-error" id="tuijianError"></span>
            	<p><label>推荐人：</label><input type="text" class="input" value="" name="paramMap.refferee" id="tuijianren" /></p>
                <div class="tuijian-btn"><a style=" margin:0px;" class="bt_green" href="automaticbid.do" id="tuijianSave"><span class="bt_green_lft"></span><strong>保存</strong><span class="bt_green_r"></span></a><a style=" margin:0px;" class="bt_blue" href="javascript:;" id="tuijianCancel"><span class="bt_blue_lft"></span><strong>跳过</strong><span class="bt_blue_r"></span></a></div>
                <div class="save-loading">保存中...,请稍候</div>
            </div>
        </div>
    </div>
     <!--推荐人 End-->
</body>
    <script>
function regChinaPnr(){
	//window.open("/registerChinaPnr.do");
	}
function loginHF(){
    window.open("/loginHF.do");
//    location.href="/loginHF.do";
}
	$(function(){

            $("#registerChinaPnr").click(function() {
                if(('${session.user.refferee}'-0)>0) {
                    window.open("/registerChinaPnr.do");
                }else{
                    $("#tuijian").lightbox_me({
                        centered: true,
                        onLoad: function() {
                            function hfSubmit(){
//						location.href="/loginHF.do";
                                location.href="/registerChinaPnr.do";
                            }
                            var save = $("#tuijianSave"),
                                    cancel = $("#tuijianCancel"),
                                    error = $("#tuijianError"),
                                    ren = $("#tuijianren"),
                                    btn = $("div.tuijian-btn"),
                                    loading = $("div.save-loading");
                            save.off().on("click",function(){
                                var refferee = $.trim(ren.val());
                                if(refferee==""){
                                    ren.focus();
                                    error.html("请填写推荐人").show();
                                    return false;
                                }
                                $.ajax({
                                    type: 'POST',
                                    url: "/setMyReferee.do?_="+new Date().getTime(),
                                    data:"paramMap.refferee="+refferee,
                                    beforeSend: function(){
                                        btn.hide();
                                        loading.show();
                                    },
                                    success: function(data){
                                        if(data>0){
                                            alert('保存成功!');
                                            hfSubmit();
                                        }else if(data=="-3"){
                                            error.html("推荐人不能为自己").show();
                                        }else{
                                            error.html("推荐人不存在").show();
                                        }
                                        btn.show();loading.hide();
                                    },
                                    error: function(xhr, type){
                                        error.html("网络错误！").show();
                                        btn.show();loading.hide();
                                    }
                                });
                                return false;
                            });
                            ren.on("focus change",function(){
                                error.html("").hide();
                            });
                            cancel.off().on("click",function(){
                                hfSubmit();
                                return false;
                            });
                        }
                    });
                }


                return false;
            });

	})


</script>
</html>