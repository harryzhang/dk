<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<link rel="stylesheet" type="text/css" href="http://static.lufax.com/config/css/main_1018.css"/>
<link rel="stylesheet" type="text/css" href="http://static.lufax.com/config/css/layout.css"/>
<link rel="stylesheet" type="text/css" href="http://www.lufax.com/help/assets/css/help_center.css"/>
<style type="text/css">
    /*.pageTitle,.helpTabContent{text-align: left;}*/
    .operationDemo{padding-left: 20px;}
    #kfs p{width: 80%}
    #kfs img{width: 100%}
</style>
<script>
    $(function(){
        $(".helpTabWrap").children(".tab").click(function () {
            tabIndex = $(this).index();
            $(this).addClass("current").siblings().removeClass("current");
            $(".helpTabContent").children(".content").eq(tabIndex).removeClass("hidden").siblings(".content").addClass("hidden");
        });
    })

</script>