<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<title></title>
	<%@ include file="include/top.jsp"%>
		<style type="text/css">
	.sss{
		width: 90px;
  		height: 90px;
	}
	
  </style>
</head>
<body style="background:#fff; padding-bottom:60px;">
		<article class="upload-box">
        <div id="uploader-demo " class="p05">
        <div class="uploader" >
        	<div id="filePicker0"  <c:if test="${ENTRUST_PROTOCOL!=''}">style="display:none"</c:if> >选择图片</div>
        		<c:if test="${ENTRUST_PROTOCOL!=''}">
        			<img class ="sss" data-url ="${ENTRUST_PROTOCOLNAME}" src="<c:url value='/app/mhk/showImg.do?imgPath=${ENTRUST_PROTOCOL}'/>" alt="" >
        		</c:if>
        	<span class="up_name">委托划款协议</span>
        </div>
        <div class="uploader" >
        	<div id="filePicker1" <c:if test="${CREDIT_AUDITK_PROTOCOL!=''}">style="display:none"</c:if> >选择图片</div>
	        	<c:if test="${CREDIT_AUDITK_PROTOCOL!=''}">
	        		<img class ="sss" data-url ="${CREDIT_AUDITK_PROTOCOLNAME}" src="<c:url value='/app/mhk/showImg.do?imgPath=${CREDIT_AUDITK_PROTOCOL}'/>" alt="" >
	        	</c:if>
        	<span class="up_name">信用审核服务协议</span>
        </div>
        </div>
    <article class="shili bs">
    <p>示例：</p>
        <ul>
	          <li style="background: #fec56c url('<c:url value='${fileServerUrl }/app_res/img/elend/xthk.jpg'/>') center center no-repeat; background-size:100% 100%;" data-img="<c:url value='${fileServerUrl }/app_res/img/elend/xthk.jpg'/>"></li>
	          <li style="background: #fec56c url('<c:url value='${fileServerUrl }/app_res/img/elend/xysh.jpg'/>') center center no-repeat; background-size:100% 100%;" data-img="<c:url value='${fileServerUrl }/app_res/img/elend/xysh.jpg'/>"></li>
        </ul>
        <p style="color:#f00; clear:both; padding-top:10px;">(请务必上传真实、清晰的协议照片，签字并加盖指模， 否则将影响贷款审批， 长按已上传的照片可进行删除已上传照片并重新上传)</p>
    </article>
	<div class="p1 upload_tip">
		<div style="padding-bottom:15px;"><h5 style="display: inline-block;color: #4A4848;font-weight: 600; padding-right:5px;">[温馨提示]</h5>如在上传中出现问题，请联系客服。</div>
        <div><i class="upload-icon icon1"></i>400-830-3737</div>
        <div><i class="upload-icon icon2"></i>3092603671(朵朵)</div>
        <div><i class="upload-icon icon3"></i>daikuan@hehenian.com</div>
	</div>
	<section class="p1  db_f">
			<div class="p1 bf1"><a href="<c:url value='/app/elend/uploadFile?uploadImageStep=998'/>"  class="apply">返回</a></div>
			<div class="p1 bf1"><a href="<c:url value='/app/elend/uploadFile?uploadImageStep=999&loanId=${loanDo.loanId}'/>"  id ="apply" class="apply">确定</a></div>
	</section>
    
	<input type="hidden" name="uploadImageStep" value="${uploadImageStep}"/>
	<input type="hidden" name="loanId" value="${loanDo.loanId}"/>
	 <%@ include file="include/foot.jsp"%>

<script src="${fileServerUrl }/app_res/js/libs/jquery-2.1.3.js?v=${jsversion}"></script>
<script src="${fileServerUrl }/app_res/js/libs/webuploader.js?v=${jsversion}"></script>
<script src="${fileServerUrl }/app_res/js/libs/uploader.js?v=${jsversion}"></script>
<script>
$(function(){
    var shili = $(".shili li");
    var _width = shili.width();
    shili.height(_width);
    var loanId = "${loanDo.loanId}";

        $(".shili li").on('click', function(){
            var imgurl = $(this).attr('data-img');
            var str = 
            '<section class="dialog">'+
            '    <img src="'+ imgurl +'" style="  width: 100%; position: absolute;top: 50%;margin-top: -50%;">'+
            '    <span class="close-dialog"></span>'+
            '</section>';
            $("body").append(str);
        })
        $("body").on('click', '.close-dialog', function(){
            $(this).parent().remove();
        })

        $(".wang").bind('touchstart', function(){
        	var str="<div class='zhenxin'></div>";
        	$("body").append(str);
        	$(".apply").hide();
        })

         $("body").on('touchend',".zhenxin", function(){
        	$(this).remove();
        	$(".apply").show();
        })


        $("body").on('click', '.upload-state-done,.sss', function(){
            var that = $(this);
            $.get('<c:url value="/app/mhk/deleteCertificate.do"/>',{'certificateName':that.attr("data-url"),'loanId':${loanDo.loanId}},function(result){
                if(result){
                    that.prev().show();
                    that.remove();
                }
            })
        });

        var uploadArr = $(".uploader").length;
        $("#apply").on('click', function(){
        	 if($(".uperr").length){
                 return false;
             }
        	var imgArr = $(".upload-state-done,.sss");
        	if(imgArr.length < uploadArr){
        		$(this).parent().parent().before('<p class="uperr">请先上传所有资料。</p>');
        		return false;
        	}
        	$("form").submit();
        })
        
        var IMGSERVER = "<c:url value='/app/mhk/saveCertificate.do' />";

        Uploader({          
            domObj: '#filePicker0',  
            loanId: ${loanDo.loanId},
            ctype: 'ENTRUST_PROTOCOL'
        }, IMGSERVER, function(file){
            $("#filePicker0").find("div").css("display",'none').parent().append(file);
        });

        Uploader({          
            domObj: '#filePicker1',  
            loanId: ${loanDo.loanId},
            ctype: 'CREDIT_AUDITK_PROTOCOL'
        }, IMGSERVER, function(file){
            $("#filePicker1").find("div").css("display",'none').parent().append(file);
        });
       
	})
</script>
</body>
</html>