<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<title></title>
	<%@ include file="include/top.jsp"%>
	<style>
#wrapper {
    width: 980px;
    margin: 0 auto;

    margin: 1em;
    width: auto;
}

#container {
    border: 1px solid #dadada;
    color: #838383;
    font-size: 12px;
    margin-top: 10px;
    background-color: #FFF;
}

#uploader .queueList {
    padding: 10px;
	overflow:hidden;
	clear:both;
}

.element-invisible {
    clip: rect(1px 1px 1px 1px); /* IE6, IE7 */
    clip: rect(1px,1px,1px,1px);
}

#uploader .placeholder {
    float:left;
    text-align: center;
    color: #cccccc;
    font-size: 18px;
    position: relative;
}

#uploader .placeholder .webuploader-pick {
    font-size: 0;
    background: url("${fileServerUrl }/app_res/img/elend/upload.png") no-repeat;
	background-size:cover;
    width:90px;
	height:90px;
    color: #fff;
    display: inline-block;
    float:left;
    cursor: pointer;
   /* box-shadow: 0 1px 1px rgba(0, 0, 0, 0.1);*/
}

#uploader .placeholder .webuploader-pick-hover {
    /*background-color: #00a2d4;*/
}

#uploader .placeholder .flashTip {
    color: #666666;
    font-size: 12px;
    position: absolute;
    width: 100%;
    text-align: center;
    bottom: 20px;
}
#uploader .placeholder .flashTip a {
    color: #0785d1;
    text-decoration: none;
}
#uploader .placeholder .flashTip a:hover {
    text-decoration: underline;
}

#uploader .placeholder.webuploader-dnd-over {
    border-color: #999999;
}

#uploader .placeholder.webuploader-dnd-over.webuploader-dnd-denied {
    border-color: red;
}

#uploader .filelist {
    list-style: none;
    margin: 0;
    padding: 0;
}

/*#uploader .filelist:after {
    content: '';
    display: block;
    width: 0;
    height: 0;
    overflow: hidden;
    clear: both;
}
*/
#uploader .filelist li {
    width: 90px;
    height: 90px;
    background: #fff;
    text-align: center;
    margin: 0 8px 10px 0;
    position: relative;
    display: inline;
    float: left;
    overflow: hidden;
    font-size: 12px;
}

#uploader .filelist li p.log {
    position: relative;
    top: -45px;
}

#uploader .filelist li p.title {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    overflow: hidden;
    white-space: nowrap;
    text-overflow : ellipsis;
    top: 5px;
    text-indent: 5px;
    text-align: left;
}

#uploader .filelist li p.progress {
    position: absolute;
    width: 100%;
    bottom: 0;
    left: 0;
    height: 8px;
    overflow: hidden;
    z-index: 50;
}
#uploader .filelist li p.progress span {
    display: none;
    overflow: hidden;
    width: 0;
    height: 100%;
    background: #1483d8 url("${fileServerUrl }/app_res/img/elend/progress.png") repeat-x;

    -webit-transition: width 200ms linear;
    -moz-transition: width 200ms linear;
    -o-transition: width 200ms linear;
    -ms-transition: width 200ms linear;
    transition: width 200ms linear;

    -webkit-animation: progressmove 2s linear infinite;
    -moz-animation: progressmove 2s linear infinite;
    -o-animation: progressmove 2s linear infinite;
    -ms-animation: progressmove 2s linear infinite;
    animation: progressmove 2s linear infinite;

    -webkit-transform: translateZ(0);
}

@-webkit-keyframes progressmove {
    0% {
       background-position: 0 0;
    }
    100% {
       background-position: 17px 0;
    }
}
@-moz-keyframes progressmove {
    0% {
       background-position: 0 0;
    }
    100% {
       background-position: 17px 0;
    }
}
@keyframes progressmove {
    0% {
       background-position: 0 0;
    }
    100% {
       background-position: 17px 0;
    }
}

#uploader .filelist li p.imgWrap {
    position: relative;
    z-index: 2;
    line-height: 90px;
    vertical-align: middle;
    overflow: hidden;
    width: 90px;
    height: 90px;

    -webkit-transform-origin: 50% 50%;
    -moz-transform-origin: 50% 50%;
    -o-transform-origin: 50% 50%;
    -ms-transform-origin: 50% 50%;
    transform-origin: 50% 50%;

    -webit-transition: 200ms ease-out;
    -moz-transition: 200ms ease-out;
    -o-transition: 200ms ease-out;
    -ms-transition: 200ms ease-out;
    transition: 200ms ease-out;
}

#uploader .filelist li img {
    width: 100%;
}

#uploader .filelist li p.error {
    background: #f43838;
    color: #fff;
    position: absolute;
    bottom: 0;
    left: 0;
    height: 28px;
    line-height: 28px;
    width: 100%;
    z-index: 100;
}

#uploader .filelist li .success {
    display: block;
    position: absolute;
    left: 0;
    bottom: 0;
    height: 40px;
    width: 100%;
    z-index: 200;
    background: url("${fileServerUrl }/app_res/img/elend/success1.png") no-repeat right bottom;
}

#uploader .filelist div.file-panel {
    position: absolute;
    height: 0;
    filter: progid:DXImageTransform.Microsoft.gradient(GradientType=0,startColorstr='#80000000', endColorstr='#80000000')\0;
    background: rgba( 0, 0, 0, 0.5 );
    width: 100%;
    top: 0;
    left: 0;
    overflow: hidden;
    z-index: 300;
}

#uploader .filelist div.file-panel span {
    width: 24px;
    height: 24px;
    display: inline;
    float: right;
    text-indent: -9999px;
    overflow: hidden;
    background: url("${fileServerUrl }/app_res/img/elend/icons.png") no-repeat;
    margin: 5px 1px 1px;
    cursor: pointer;
}

#uploader .filelist div.file-panel span.rotateLeft {
    background-position: 0 -24px;
}
#uploader .filelist div.file-panel span.rotateLeft:hover {
    background-position: 0 0;
}

#uploader .filelist div.file-panel span.rotateRight {
    background-position: -24px -24px;
}
#uploader .filelist div.file-panel span.rotateRight:hover {
    background-position: -24px 0;
}

#uploader .filelist div.file-panel span.cancel {
    background-position: -48px -24px;
}
#uploader .filelist div.file-panel span.cancel:hover {
    background-position: -48px 0;
}

#uploader .statusBar {
    height: 63px;
    line-height: 63px;
    vertical-align: middle;
    position:absolute;
	left:0;
	bottom:48px;
	width:100%;
	text-align:center;
}

#uploader .statusBar .progress {
    border: 1px solid #1483d8;
    width: 198px;
    background: #fff;
    height: 18px;
    position: relative;
    display: inline-block;
    text-align: center;
    line-height: 20px;
    color: #6dbfff;
    position: relative;
    margin-right: 10px;
}
#uploader .statusBar .progress span.percentage {
    width: 0;
    height: 100%;
    left: 0;
    top: 0;
    background: #1483d8;
    position: absolute;
}
#uploader .statusBar .progress span.text {
    position: relative;
    z-index: 10;
}

#uploader .statusBar .info {
    display: none;
    font-size: 14px;
    color: #666666;
}

#uploader .statusBar .btns {
    line-height: 40px;
	margin:10px auto;
	width:70%;
}

#filePicker2 {
    display: inline-block;
    float: left;
}

#uploader .statusBar .btns .webuploader-pick,
#uploader .statusBar .btns .uploadBtn,
#uploader .statusBar .btns .uploadBtn.state-uploading,
#uploader .statusBar .btns .uploadBtn.state-paused {
    background: #ffffff;
    border: 1px solid #cfcfcf;
    color: #565656;
    display: inline-block;
    border-radius: 3px;
    cursor: pointer;
    font-size: 14px;
    width:100%;
}
#uploader .statusBar .btns .webuploader-pick-hover,
#uploader .statusBar .btns .uploadBtn:hover,
#uploader .statusBar .btns .uploadBtn.state-uploading:hover,
#uploader .statusBar .btns .uploadBtn.state-paused:hover {
    background: #f0f0f0;
}

#uploader .statusBar .btns .uploadBtn {
    background: #e05d06;
    color: #fff;
    border-color: transparent;
}
#uploader .statusBar .btns .uploadBtn:hover {
    background: #00a2d4;
}

#uploader .statusBar .btns .uploadBtn.disabled {
    pointer-events: none;
    opacity: 0.6;
}
.webuploader-container {
	position: relative;
}
.webuploader-element-invisible {
	position: absolute !important;
	clip: rect(1px 1px 1px 1px); /* IE6, IE7 */
    clip: rect(1px,1px,1px,1px);
}
.webuploader-pick {
	position: relative;
	display: inline-block;
	cursor: pointer;
	background: #00b7ee;
	color: #fff;
	text-align: center;
	border-radius: 3px;
	overflow: hidden;
}
.webuploader-pick-hover {
	background: #00a2d4;
}

.webuploader-pick-disable {
	opacity: 0.6;
	pointer-events:none;
}
.wang{
  padding: 5px;
  position: absolute;
  background: #f00;
  right: 20px;
  margin-top: -26px;
  font-size: 14px;
  color: #fff;
  border-radius: 5px;
}
.zhenxin{
position: absolute;
width:100%;
height:100%;
background:#fff url("${fileServerUrl }/app_res/img/elend/zx1.jpg") 0 0 no-repeat;
background-size: contain;
top:0;
z-index:200000;
}
.closebtn{
	position: absolute;
	width:100%;
	padding: 10px;
	text-align: center;
	bottom:30px;
}
.closebtn a{
  padding: 10px;
  display: block;
  background: #f00;
  border-radius: 10px;
	color:#fff;
  }
	</style>
</head>
<body style="background:#fff; padding-bottom:60px;">
		 <article class="upload-box">
		 <div id="uploader" class="uploadimg_box">
                <div class="queueList">
                    <ul class="filelist">
                    <c:forEach var="certificateDo" items="${certificateDo}" varStatus="status">
                        <li class="state-complete" data-url="${certificateDo.certificateName}">
                            <p class="imgWrap"><img src="<c:url value='/app/mhk/showImg.do?imgPath=${certificateDo.destFilePath}'/>"></p>
                        </li>
                    </c:forEach>
                    </ul>
                    <div id="dndArea" class="placeholder">
                        <div id="filePicker"></div>
                    </div>
                </div>
              <!--   <div class="statusBar">
                    <div class="progress" style="display:none;">
                        <span class="text">0%</span>
                        <span class="percentage"></span>
                    </div><div class="info"></div>
                </div> -->
            </div>
			<article class="suc">
				<ul><li>银行流水</li></ul>
			</article>
            </article>
    <article class="shili bs">
    <p>示例：</p>
        <ul>
            <li style="background: #fec56c url('<c:url value='${fileServerUrl }/app_res/img/elend/yh.jpg'/>') center center no-repeat; background-size:100% 100%;" data-img="<c:url value='${fileServerUrl }/app_res/img/elend/yh.jpg'/>"></li>
        </ul>
    </article>
	<div class="p1 upload_tip">
		<div style="padding-bottom:15px;"><h5 style="display: inline-block;color: #4A4848;font-weight: 600; padding-right:5px;">[温馨提示]</h5>如在上传中出现问题，请联系客服。</div>
        <div><i class="upload-icon icon1"></i>400-830-3737</div>
        <div><i class="upload-icon icon2"></i>3092603671(朵朵)</div>
        <div><i class="upload-icon icon3"></i>daikuan@hehenian.com</div>
	</div>
	<section class="p1">
			<a href="<c:url value='/app/elend/uploadFile?uploadImageStep=${uploadImageStep}'/>"  class="apply">提交申请</a>
	</section>
    
	<input type="hidden" name="uploadImageStep" value="${uploadImageStep}"/>
	<input type="hidden" name="loanId" value="${loanPersonDo.loanId}"/>
	<input type="hidden" name="loanPersonId" value="${loanPersonDo.loanPersonId}"/>
	 <%@ include file="include/foot.jsp"%>
<script src="${fileServerUrl }/app_res/js/libs/jquery-2.1.3.js?v=${jsversion}"></script>
<script src="${fileServerUrl }/app_res/js/libs/upload.js?v=${jsversion}"></script>
<script src="${fileServerUrl }/app_res/js/libs/webuploader.js?v=${jsversion}"></script>
<script>

	var loanId = $("#loanId").val();
	var loanPersonId = $("#loanPersonId").val();
	var uploadStep = $("#uploadImageStep").val();
	
	$(function(){
		loanUpload({			
			uploadImageStep:"${uploadImageStep}",	
			loanId:"${loanPersonDo.loanId}",
			loanPersonId:"${loanPersonDo.loanPersonId}",
			ctype:'INCOME'
		},
		"<c:url value='/app/mhk/saveCertificate.do'/>");

        var shili = $(".shili li");
        var _width = shili.width();
        shili.height(_width);

        shili.bind('touchstart', function(){
            var imgurl = $(this).attr('data-img');
            var str = 
            '<section class="dialog">'+
            '    <img src="'+ imgurl +'" style="  width: 100%; position: absolute;top: 50%;margin-top: -50%;">'+
            '    <span class="close-dialog"></span>'+
            '</section>';
            $("body").append(str);
        })
        $("body").on('touchstart', '.close-dialog', function(){
            $(this).parent().remove();
        })


        $("body").on('click', '.state-complete', function(){
            if(!confirm("删除后不能再上传该图片，确定删除吗？")){
                return false;
            }
            var that = $(this);
            $.get('<c:url value="/app/mhk/deleteCertificate.do"/>',{'certificateName':that.attr("data-url"),'loanId':loanId},function(result){
                if(result.status==1){
                    that.remove();
                }
            })
        });
        
        $(".apply").on('click', function(){
            var imgArr = $(".state-complete");
            if($(".uperr").length){
                return false;
            }
            if(imgArr.length < 1){
                $(this).parent().before('<p class="uperr">请先上传资料。</p>');
                return false;
            }
            $("form").submit();
        })
	})
</script>
</body>
</html>