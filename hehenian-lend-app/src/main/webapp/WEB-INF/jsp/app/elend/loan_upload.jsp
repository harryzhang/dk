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

        <div  class="p05 uploadlist">
        <div class="uploader">
        	<div id="filePicker0"  <c:if test="${IDCARDZ!=''}">style="display:none"</c:if> >选择图片</div>
        	<c:if test="${IDCARDZ!=''}">
        		<img class ="sss" data-url ="${IDCARDZNAME}" src="<c:url value='/app/mhk/showImg.do?imgPath=${IDCARDZ}'/>" alt="" >
        	</c:if>
        	<span class="up_name">身份证正面</span>
        </div>
        <div class="uploader">
        	<div id="filePicker1"  <c:if test="${IDCARDF!=''}">style="display:none"</c:if> >选择图片</div>
        	<c:if test="${IDCARDF!=''}">
        		<img class ="sss" data-url ="${IDCARDFNAME}" src="<c:url value='/app/mhk/showImg.do?imgPath=${IDCARDF}'/>" alt="" >
        	</c:if>
        	<span class="up_name">身份证反面</span>
        </div>
        <c:if test="${uploadImageXType<4 }">
        	<div class="uploader" >
        		<div id="filePicker2"  <c:if test="${HOUSE!=''}">style="display:none"</c:if> >选择图片</div>
        		<c:if test="${HOUSE!=''}">
	        		<img class ="sss" data-url ="${HOUSENAME}" src="<c:url value='/app/mhk/showImg.do?imgPath=${HOUSE}'/>" alt="" >
	        	</c:if>
        		<span class="up_name">房产证</span>
        	</div>
        </c:if>
        <c:if test="${uploadImageXType>3 }">
        	<div class="uploader" >
        		<div id="filePicker2"  <c:if test="${ASSETS!=''}">style="display:none"</c:if> >选择图片</div>
        		<c:if test="${ASSETS!=''}">
	        		<img class ="sss" data-url ="${ASSETSNAME}" src="<c:url value='/app/mhk/showImg.do?imgPath=${ASSETS}'/>" alt="" >
	        	</c:if>
        		<span class="up_name">资产相关证明</span>
        	</div>
        </c:if>
       	<div class="uploader" >
       		<div id="filePicker3"  <c:if test="${IDCARDZS!=''}">style="display:none"</c:if> >选择图片</div>
       			<c:if test="${IDCARDZS!=''}">
	        		<img class ="sss" data-url ="${IDCARDZSNAME}" src="<c:url value='/app/mhk/showImg.do?imgPath=${IDCARDZS}'/>" alt="" >
	        	</c:if>
       		<span class="up_name">本人手持身份证正面</span>
       	</div>
        <c:if test="${uploadImageXType>1 }">
        	<div class="uploader" >
        		<div id="filePicker4"  <c:if test="${JOB!=''}">style="display:none"</c:if> >选择图片</div>
	       			<c:if test="${JOB!=''}">
		        		<img class ="sss" data-url ="${JOBNAME}" src="<c:url value='/app/mhk/showImg.do?imgPath=${JOB}'/>" alt="" >
		        	</c:if>
        		<span class="up_name">工作证明或经营许可证</span>
        	</div>
        </c:if>
        <c:if test="${uploadImageXType>3 && loanPersonDo.propertyDo.carDy==1 }">
        	<div class="uploader" >
        		<div id="filePicker5"  <c:if test="${DRIVERCARD!=''}">style="display:none"</c:if> >选择图片</div>
	       			<c:if test="${DRIVERCARD!=''}">
		        		<img class ="sss" data-url ="${DRIVERCARDNAME}" src="<c:url value='/app/mhk/showImg.do?imgPath=${DRIVERCARD}'/>" alt="" >
		        	</c:if>
        		<span class="up_name">行驶证</span>
        	</div>
        </c:if>
        </div>

    <article class="shili bs">
    <p>示例：</p>

        <ul>
                
	          <li style="background: #fec56c url('<c:url value='${fileServerUrl }/app_res/img/elend/CID.png'/>') center center no-repeat; background-size:100% 100%;" data-img="<c:url value='${fileServerUrl }/app_res/img/elend/CID.png'/>"></li>
	          <li style="background: #fec56c url('<c:url value='${fileServerUrl }/app_res/img/elend/fm.jpg'/>') center center no-repeat; background-size:100% 100%;" data-img="<c:url value='${fileServerUrl }/app_res/img/elend/fm.jpg'/>"></li>
	          <c:if test="${uploadImageXType<4 }"><li style="background: #fec56c url('<c:url value='${fileServerUrl }/app_res/img/elend/fc.jpg'/>') center center no-repeat; background-size:100% 100%;" data-img="<c:url value='${fileServerUrl }/app_res/img/elend/fc.jpg'/>"></li></c:if>
		      <c:if test="${uploadImageXType>3 }"><li style="background: #fec56c url('<c:url value='${fileServerUrl }/app_res/img/elend/zcxg.jpg'/>') center center no-repeat; background-size:100% 100%;" data-img="<c:url value='${fileServerUrl }/app_res/img/elend/zcxg.jpg'/>"></li></c:if>
		      <li style="background: #fec56c url('<c:url value='${fileServerUrl }/app_res/img/elend/CID2.png'/>') center center no-repeat; background-size:100% 100%;" data-img="<c:url value='${fileServerUrl }/app_res/img/elend/CID2.png'/>"></li>
		      <c:if test="${uploadImageXType>1 }"><li style="background: #fec56c url('<c:url value='${fileServerUrl }/app_res/img/elend/gz.jpg'/>') center center no-repeat; background-size:100% 100%;" data-img="<c:url value='${fileServerUrl }/app_res/img/elend/gz.jpg'/>"></li></c:if>
		      <c:if test="${uploadImageXType>3 && loanPersonDo.propertyDo.carDy==1 }"><li style="background: #fec56c url('<c:url value='${fileServerUrl }/app_res/img/elend/xsz.jpg'/>') center center no-repeat; background-size:100% 100%;" data-img="<c:url value='${fileServerUrl }/app_res/img/elend/xsz.jpg'/>"></li></c:if>
        </ul>
        <p style="color:#f00; clear:both; padding-top:10px;">(请务必上传真实、清晰的资料照片，否则将影响贷款审批 长按已上传的照片可进行删除已上传照片并重新上传)</p>
    </article>
	<div class="p1 upload_tip">
		<div style="padding-bottom:15px;"><h5 style="display: inline-block;color: #4A4848;font-weight: 600; padding-right:5px;">[温馨提示]</h5>如在上传中出现问题，请联系客服。</div>
        <div><i class="upload-icon icon1"></i>400-830-3737</div>
        <div><i class="upload-icon icon2"></i>3092603671(朵朵)</div>
        <div><i class="upload-icon icon3"></i>daikuan@hehenian.com</div>
	</div>
	<section class="p1">
			<a href="<c:url value='/app/elend/uploadFile?uploadImageStep=${uploadImageStep}&loanId=${loanPersonDo.loanId}'/>"  class="apply"><c:if test="${uploadImageYType==1}">下一步</c:if><c:if test="${uploadImageYType==0}">提交申请</c:if></a>
	</section>
    
	<input type="hidden" name="uploadImageStep" value="${uploadImageStep}"/>
	<input type="hidden" name="uploadImageXtype" value="${uploadImageXType}"/>
	<input type="hidden" name="uploadImageYtype" value="${uploadImageYType}"/>
	<input type="hidden" name="loanId" value="${loanPersonDo.loanId}"/>
	<input type="hidden" name="loanPersonId" value="${loanPersonDo.loanPersonId}"/>

	 <%@ include file="include/foot.jsp"%>

<script src="${fileServerUrl }/app_res/js/libs/jquery-2.1.3.js?v=${jsversion}"></script>
<script src="${fileServerUrl }/app_res/js/libs/webuploader.js?v=${jsversion}"></script>
<script src="${fileServerUrl }/app_res/js/libs/uploader.js?v=${jsversion}"></script>
<script>
$(function(){
    var shili = $(".shili li");
    var _width = shili.width();
    shili.height(_width);
    var loanId = "${loanPersonDo.loanId}";
	var loanPersonId = "${loanPersonDo.loanPersonId}";

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
        
        //

        var uploadArr = $(".uploader").length;
        $(".apply").on('click', function(){
        	var imgArr = $(".upload-state-done,.sss");
            if($(".uperr").length){
                return false;
            }
        	if(imgArr.length < uploadArr){
        		$(this).parent().before('<p class="uperr">请先上传所有资料。</p>');
        		return false;
        	}
        	$("form").submit();
        })

        $("body").on('click', '.upload-state-done,.sss', function(){
            if(!confirm("删除后不能再上传该图片，确定删除吗？")){
                return false;
            }
            var that = $(this);

            $.get('<c:url value="/app/mhk/deleteCertificate.do"/>',{'certificateName':that.attr("data-url"),'loanId':loanId},function(result){
                if(result.status==1){
                    that.prev().show();
                    that.remove();
                }
            })
        });

        var IMGSERVER = "<c:url value='/app/mhk/saveCertificate.do' />";

        Uploader({          
            domObj: '#filePicker0',  
            loanId: loanId,
            ctype: 'IDCARDZ',
            loanPersonId: loanPersonId
        }, IMGSERVER, function(file){
            $("#filePicker0").find("div").css("display",'none').parent().append(file);
        });

        Uploader({          
            domObj: '#filePicker1',  
            loanId: loanId,
            ctype: 'IDCARDF',
            loanPersonId:loanPersonId
        }, IMGSERVER, function(file){
            $("#filePicker1").find("div").css("display",'none').parent().append(file);
        });
        <c:if test="${uploadImageXType<4 }">
         Uploader({          
            domObj: '#filePicker2',  
            loanId: loanId,
            ctype: 'HOUSE',
            loanPersonId:loanPersonId
        }, IMGSERVER, function(file){
            $("#filePicker2").find("div").css("display",'none').parent().append(file);
        });
         </c:if>
        <c:if test="${uploadImageXType>3 }">
         Uploader({          
            domObj: '#filePicker2',  
            loanId: loanId,
            ctype: 'ASSETS',
            loanPersonId:loanPersonId
        }, IMGSERVER, function(file){
            $("#filePicker2").find("div").css("display",'none').parent().append(file);
        });
         </c:if>
         Uploader({          
            domObj: '#filePicker3',  
            loanId: loanId,
            ctype: 'IDCARDZS',
            loanPersonId:loanPersonId
        }, IMGSERVER, function(file){
            $("#filePicker3").find("div").css("display",'none').parent().append(file);
        });
         Uploader({          
             domObj: '#filePicker4',  
             loanId: loanId,
             ctype: 'JOB',
             loanPersonId:loanPersonId
         }, IMGSERVER, function(file){
             $("#filePicker4").find("div").css("display",'none').parent().append(file);
         });
         Uploader({          
             domObj: '#filePicker5',  
             loanId: loanId,
             ctype: 'DRIVERCARD',
             loanPersonId:loanPersonId
         }, IMGSERVER, function(file){
             $("#filePicker5").find("div").css("display",'none').parent().append(file);
         });         
	})
</script>
</body>
</html>