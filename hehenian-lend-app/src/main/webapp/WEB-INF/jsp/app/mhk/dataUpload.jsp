<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    
<!DOCTYPE html>    
<html>
	<head>
		<%@ include file="../include/top.jsp"%>
		<title>个人信息</title>
	</head>
	<body>
		<section>
			<h3 class="tip tip2">建议在wifi环境下传图</h3>
			<article class="p1">
				<section class="db_f p1">
					<article class="bf1 upload_item">
						<c:if test="${empty IDCARDZ}">
						<input type="file" class="upload_btn" accept="image/*" id="IDCARDZ" name="file" > 
						<span class="img_box" id="IDCARDZShow" style="display: none;"><img src="" alt="" id="IDCARDZImgSrc"><i id="IDCARDZi" style="display: none;" class="upload_ok pa"></i></span>
						<span class="uploading" id="IDCARDZing" style="display: none;">正在上传</span>
						</c:if>
						<c:if test="${!empty IDCARDZ}">
						<input type="file" class="upload_btn" accept="image/*" id="IDCARDZ" name="file" style="display: none;"> 
						<span class="img_box" id="IDCARDZShow" style=""><img src="<c:url value='/app/mhk/showImg.do?imgPath=${IDCARDZ.destFilePath }'/>" alt="" id="IDCARDZImgSrc"><i id="IDCARDZi" style="" class="upload_ok pa"></i></span>
						<span class="uploading" id="IDCARDZing" style="display: none;">正在上传</span>
						</c:if>
						<input type="hidden" id="IDCARDZCid" name="IDCARDZCid" value="${IDCARDZ.certificateId }">
						<input type="hidden" name="IDCARDZFilePath" id="IDCARDZFilePath" value="${IDCARDZ.filePath }">
						<input type="hidden" name="IDCARDZDestFilePath" id="IDCARDZDestFilePath" value="${IDCARDZ.destFilePath }">
						<span class="upload_title">身份证正面</span>
					</article>
					<article class="bf1 upload_item">
						<c:if test="${empty IDCARDF}">
						<input type="file" class="upload_btn" accept="image/*" id="IDCARDF" name="file"> 
						<span class="img_box" id="IDCARDFShow" style="display: none;"><img src="" alt="" id="IDCARDFImgSrc"><i id="IDCARDFi" style="display: none;" class="upload_ok pa"></i></span>
						<span class="uploading" id="IDCARDFing" style="display: none;">正在上传</span>
						</c:if>
						<c:if test="${!empty IDCARDF}">
						<input type="file" class="upload_btn" accept="image/*" id="IDCARDF" name="file" style="display: none;"> 
						<span class="img_box" id="IDCARDFShow" ><img src="<c:url value='/app/mhk/showImg.do?imgPath=${IDCARDF.destFilePath }'/>" alt="" id="IDCARDFImgSrc"><i id="IDCARDFi" class="upload_ok pa"></i></span>
						<span class="uploading" id="IDCARDFing" style="display: none;">正在上传</span>
						</c:if>
						<input type="hidden" id="IDCARDFCid" name="IDCARDFCid" value="${IDCARDF.certificateId }">
						<input type="hidden" name="IDCARDFFilePath" id="IDCARDFFilePath" value="${IDCARDF.filePath }">
						<input type="hidden" name="IDCARDFDestFilePath" id="IDCARDFDestFilePath" value="${IDCARDF.destFilePath }">
						<span class="upload_title">身份证反面</span>
					</article>
					<article class="bf1 upload_item">
						<c:if test="${empty CREDIT}">
						<input type="file" class="upload_btn" accept="image/*" name="file" id="CREDIT" > 
						<span class="img_box" id="CREDITShow" style="display: none;"><img src="" alt="" id="CREDITImgSrc"><i id="CREDITi" style="display: none;" class="upload_ok pa"></i></span>
						<span class="uploading" id="CREDITing" style="display: none;">正在上传</span>
						</c:if>
						<c:if test="${!empty CREDIT}">
						<input type="file" class="upload_btn" accept="image/*" name="file" id="CREDIT" style="display: none;"> 
						<span class="img_box" id="CREDITShow" ><img src="<c:url value='/app/mhk/showImg.do?imgPath=${CREDIT.destFilePath }'/>" alt="" id="CREDITImgSrc"><i id="CREDITi"  class="upload_ok pa"></i></span>
						<span class="uploading" id="CREDITing" style="display: none;">正在上传</span>
						</c:if>
						<input type="hidden" id="CREDITCid" name="CREDITCid" value="${CREDIT.certificateId }">
						<input type="hidden" name="CREDITFilePath" id="CREDITFilePath" value="${CREDIT.filePath }">
						<input type="hidden" name="CREDITDestFilePath" id="CREDITDestFilePath" value="${CREDIT.destFilePath }">
						<span class="upload_title">征信报告</span>
					</article>
				</section>
				<section class="db_f p1">
					<article class="bf1 upload_item">
						<c:if test="${empty INCOME}">
						<input type="file" class="upload_btn" accept="image/*" name="file" id="INCOME" > 
						<span class="img_box" id="INCOMEShow" style="display: none;"><img src="" alt="" id="INCOMEImgSrc"><i id="INCOMEi" style="display: none;" class="upload_ok pa"></i></span>
						<span class="uploading" id="INCOMEing" style="display: none;">正在上传</span>
						</c:if>
						<c:if test="${!empty INCOME}">
						<input type="file" class="upload_btn" accept="image/*" name="file" id="INCOME" style="display: none;"> 
						<span class="img_box" id="INCOMEShow" ><img src="<c:url value='/app/mhk/showImg.do?imgPath=${INCOME.destFilePath }'/>" alt="" id="INCOMEImgSrc"><i id="INCOMEi" class="upload_ok pa"></i></span>
						<span class="uploading" id="INCOMEing" style="display: none;">正在上传</span>
						</c:if>
						<input type="hidden" id="INCOMECid" name="INCOMECid" value="${INCOME.certificateId }">
						<input type="hidden" name="INCOMEFilePath" id="INCOMEFilePath" value="${INCOME.filePath }">
						<input type="hidden" name="INCOMEDestFilePath" id="INCOMEDestFilePath" value="${INCOME.destFilePath }">
						<span class="upload_title">收入流水</span>
					</article>
					<article class="bf1 upload_item">
						<c:if test="${empty JOB}">
						<input type="file" class="upload_btn" accept="image/*" name="file" id="JOB" > 
						<span class="img_box" id="JOBShow" style="display: none;"><img src="" alt="" id="JOBImgSrc"><i id="JOBi" style="display: none;" class="upload_ok pa"></i></span>
						<span class="uploading" id="JOBing" style="display: none;">正在上传</span>
						</c:if>
						<c:if test="${!empty JOB}">
						<input type="file" class="upload_btn" accept="image/*" name="file" id="JOB" style="display: none;"> 
						<span class="img_box" id="JOBShow" ><img src="<c:url value='/app/mhk/showImg.do?imgPath=${JOB.destFilePath }'/>" alt="" id="JOBImgSrc"><i id="JOBi" class="upload_ok pa"></i></span>
						<span class="uploading" id="JOBing" style="display: none;">正在上传</span>
						</c:if>
						<input type="hidden" id="JOBCid" name="JOBCid" value="${JOB.certificateId }">
						<input type="hidden" name="JOBFilePath" id="JOBFilePath" value="${JOB.filePath }">
						<input type="hidden" name="JOBDestFilePath" id="JOBDestFilePath" value="${JOB.destFilePath }">
						<span class="upload_title">工作证明</span>
					</article>
					<article class="bf1 upload_item" >
						<c:if test="${empty HOUSE}">
						<input type="file" class="upload_btn" accept="image/*" name="file" id="HOUSE" > 
						<span class="img_box" id="HOUSEShow" style="display: none;"><img src="" alt="" id="HOUSEImgSrc"><i id="HOUSEi" style="display: none;" class="upload_ok pa"></i></span>
						<span class="uploading" id="HOUSEing" style="display: none;">正在上传</span>
						</c:if>
						<c:if test="${!empty HOUSE}">
						<input type="file" class="upload_btn" accept="image/*" name="file" id="HOUSE" style="display: none;"> 
						<span class="img_box" id="HOUSEShow" ><img src="<c:url value='/app/mhk/showImg.do?imgPath=${HOUSE.destFilePath }'/>" alt="" id="HOUSEImgSrc"><i id="HOUSEi" class="upload_ok pa"></i></span>
						<span class="uploading" id="HOUSEing" style="display: none;">正在上传</span>
						</c:if>
						<input type="hidden" id="HOUSECid" name="HOUSECid" value="${HOUSE.certificateId }">
						<input type="hidden" name="HOUSEFilePath" id="HOUSEFilePath" value="${HOUSE.filePath }">
						<input type="hidden" name="HOUSEDestFilePath" id="HOUSEDestFilePath" value="${HOUSE.destFilePath }">
						<span class="upload_title">房产证明</span>
					</article>
				</section>
				<section class="db_f p1">
					<article class="bf1 upload_item">
						<c:if test="${empty OTHERFILE}">
						<input type="file" class="upload_btn" accept="image/*" name="file" id="OTHERFILE" > 
						<span class="img_box" id="OTHERFILEShow" style="display: none;"><img src="" alt="" id="OTHERFILEImgSrc"><i id="OTHERFILEi" style="display: none;" class="upload_ok pa"></i></span>
						<span class="uploading" id="OTHERFILEing" style="display: none;">正在上传</span>
						</c:if>
						<c:if test="${!empty OTHERFILE}">
						<input type="file" class="upload_btn" accept="image/*" name="file" id="OTHERFILE" style="display: none;"> 
						<span class="img_box" id="OTHERFILEShow" ><img src="<c:url value='/app/mhk/showImg.do?imgPath=${OTHERFILE.destFilePath }'/>" alt="" id="OTHERFILEImgSrc"><i id="OTHERFILEi"  class="upload_ok pa"></i></span>
						<span class="uploading" id="OTHERFILEing" style="display: none;">正在上传</span>
						</c:if>
						<input type="hidden" id="OTHERFILECid" name="OTHERFILECid" value="${OTHERFILE.certificateId }">
						<input type="hidden" name="OTHERFILEFilePath" id="OTHERFILEFilePath" value="${OTHERFILE.filePath }">
						<input type="hidden" name="OTHERFILEDestFilePath" id="OTHERFILEDestFilePath" value="${OTHERFILE.destFilePath }">
						<span class="upload_title">其它</span>
					</article>
					<article class="bf1 upload_item">
					</article>
					<article class="bf1 upload_item">
					</article>
				</section>
			</article>
			<div class="btn_box">
				<botton class="btn  w10" onclick="javascript:ajaxFu(0);">提交</botton>
			</div>
		</section>
		<input type="hidden" id="loanId" name="loanId" value="${certificateDo.loanId}">
		<input type="hidden" id="loanPersonId" name="loanPersonId" value="${certificateDo.loanPersonId}">
		<script src="${fileServerUrl }/app_res/js/libs/jquery.min.js" type="text/javascript"></script>
		<script src="${fileServerUrl }/app_res/js/libs/ajaxfileupload.js" type="text/javascript"></script>
		<script type="text/javascript">
		$('article input[name=file]').change(function(){
			var file = this.files[0];
			var id = this.id;
			var r = new FileReader();
			r.readAsDataURL(file); //Base64
			$(r).load(function(){
				$('#' + id).hide();
				$('#' + id + "Show").show();
				$("#" + id + "ImgSrc").attr("src",this.result);
			});
			
		});
		$('article .img_box').click(function(){
			if(confirm('确认修改证件图片？')){
				var id = this.id;
				var inputId = id.substring(0,id.length-4);
				$('#' + id).hide();
				$('#' + inputId+"i").hide();
				$('#' + inputId).show();
			}
		}); 
		
		
		function ajaxFu(idx){
			idx = parseInt(idx);
			var loanId = $('#loanId').val();
			var loanPersonId = $('#loanPersonId').val();
			var files = $("article input[name='file']");
			if(idx<files.length){
				var fid = files[idx].id;
				var fpath = $(files[idx]).val();
				if(fpath.length > 0){
					var certificateId = $('#'+fid+'Cid').val();
					var filePath=$('#'+fid+"FilePath").val();
					var destFilePath=$('#'+fid+"DestFilePath").val();
					var data = {
						certificateId:certificateId,		
						loanId : loanId,
						loanPersonId : loanPersonId,
						filePath:filePath,
						destFilePath:destFilePath,
						certificateType : fid
					};
					$('#' + fid + "Show").hide();
					$('#' + fid + "ing").show();
					$.ajaxFileUpload({
						url : '<c:url value="/app/mhk/saveCertificate.do"/>',
						secureuri : false,//安全协议  
						fileElementId : fid,//id  
						type : 'POST',
						dataType : 'json',
						data : data,
						async : false,
						error : function(data, status, e) {
							alert('Operate Failed!');
						},
						success : function(rdata) {
							if (rdata.status == 0) {
								$('#' + fid + "ing").hide();
								$('#' + fid + "Show").show();
								$("#" + fid + "ImgSrc").attr("src",
										"${fileAccessUrl}" + rdata.imgPath);
								$('#' + fid + "i").show();
								ajaxFu(idx+1);
							} else {
								alert(rdata.errorMsg);
							}
						}
					});
				}else{
					ajaxFu(idx+1);
				}
			}else{
				location.href = "<c:url value='/app/mhk/initPersonInfo.do?loanId="+loanId+"'/>";
			}
		}
		</script>
	</body>
</html>    