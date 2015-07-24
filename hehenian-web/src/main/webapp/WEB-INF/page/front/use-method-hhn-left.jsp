<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<div class="wdzh_next_left">
    <h3 style="font-weight: bold;">投资操作指引</h3>
    <ul class="daohang">
        <li class="wdzh_next_left_ong"><a href="javascript:;" onclick="loadGuide(1,this);">注册及认证</a></li>
        <li><a href="javascript:;" onclick="loadGuide(2,this);">投资操作</a></li>
        <%--<li><a href="javascript:;" onclick="loadGuide(3);">转让操作</a></li>--%>
    </ul>

    <h3 style="font-weight: bold;">借款操作指引</h3>
    <ul class="daohang">
        <li class=""><a href="javascript:;" onclick="loadGuide(4,this);">注册及认证</a></li>
        <li><a href="javascript:;" onclick="loadGuide(5,this);">借款申请</a></li>
    </ul>
    <h3 style="font-weight: bold;">账户操作指引</h3>
    <ul class="daohang">
        <li class=""><a href="javascript:;" onclick="loadGuide(6,this);">充值</a></li>
        <li><a href="javascript:;" onclick="loadGuide(7,this);">取现</a></li>
        <li><a href="javascript:;" onclick="loadGuide(8,this);">忘记密码</a></li>
    </ul>

    <div class="bzzx_shou"></div>

</div>
<script>
    function loadGuide(type,elem){
    $(".daohang .wdzh_next_left_ong").removeClass("wdzh_next_left_ong");
    $(elem).parent().addClass("wdzh_next_left_ong");
        $("#kfs").load("/guide/use-method-hhn-"+type+".jsp",function(){
            $(".helpTabWrap").children(".tab").click(function () {
                tabIndex = $(this).index();
                $(this).addClass("current").siblings().removeClass("current");
                $(".helpTabContent").children(".content").eq(tabIndex).removeClass("hidden").siblings(".content").addClass("hidden");
            });
        });
    }
</script>