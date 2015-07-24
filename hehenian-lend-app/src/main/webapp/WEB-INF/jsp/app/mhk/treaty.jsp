<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    
<!DOCTYPE html> 
<html>
	<head>
		<%@ include file="../include/top.jsp"%>
		<title>合同上传</title>
	</head>
	<body>
		<section>
			<article class="p1 enable_inf" >
				<div class=" p1 loan_person" style="padding-top:10px;">
						<p class="db_f"><label class="lab" >真实姓名：</label><span class="lab">${loanPerson.realName }</span></p>
						<p class="db_f"><label class="lab" >手机号码：</label><span class="lab">${loanPerson.mobile }</span></p>
						<p class="db_f"><label class="lab">身份证号：</label><span class="lab">${loanPerson.idNo }</span></p>
				</div>
			</article>
			<article class="p1">
				<section class="db_f p1">
					<article class="bf1 upload_item">
						<c:if test="${empty treaty1}">
						<input type="file" class="upload_btn" accept="image/*" id="treaty1" name="file" > 
						<span class="img_box" id="treaty1Show" style="display: none;"><img src="" alt="" id="treaty1ImgSrc"><i id="treaty1i" style="display: none;" class="upload_ok pa"></i></span>
						<span class="uploading" id="treaty1ing" style="display: none;">正在上传</span>
						</c:if>
						<c:if test="${!empty treaty1}">
						<input type="file" class="upload_btn" accept="image/*" id="treaty1" name="file" style="display: none;"> 
						<span class="img_box" id="treaty1Show" style=""><img src="/app/mhk/showImg.do?imgPath=${treaty1.destFilePath }" alt="" id="treaty1ImgSrc"><i id="treaty1i" style="" class="upload_ok pa"></i></span>
						<span class="uploading" id="treaty1ing" style="display: none;">正在上传</span>
						</c:if>
						<span class="upload_title">《借款咨询服务协议》</span>
						<input type="hidden" id="treaty1Cid" name="treaty1Cid" value="${treaty1.certificateId }">
						<input type="hidden" name="treaty1FilePath" id="treaty1FilePath" value="${treaty1.filePath }">
						<input type="hidden" name="treaty1DestFilePath" id="treaty1DestFilePath" value="${treaty1.destFilePath }">
					</article>
					<article class="bf1 upload_item">
						<c:if test="${empty treaty2}">
						<input type="file" class="upload_btn" accept="image/*" id="treaty2" name="file" > 
						<span class="img_box" id="treaty2Show" style="display: none;"><img src="" alt="" id="treaty2ImgSrc"><i id="treaty2i" style="display: none;" class="upload_ok pa"></i></span>
						<span class="uploading" id="treaty2ing" style="display: none;">正在上传</span>
						</c:if>
						<c:if test="${!empty treaty2}">
						<input type="file" class="upload_btn" accept="image/*" id="treaty2" name="file" style="display: none;"> 
						<span class="img_box" id="treaty2Show" style=""><img src="/app/mhk/showImg.do?imgPath=${treaty2.destFilePath }" alt="" id="treaty2ImgSrc"><i id="treaty2i" style="" class="upload_ok pa"></i></span>
						<span class="uploading" id="treaty2ing" style="display: none;">正在上传</span>
						</c:if>
						<span class="upload_title">其它合同</span>
						<input type="hidden" id="treaty2Cid" name="treaty2Cid" value="${treaty2.certificateId }">
						<input type="hidden" name="treaty2FilePath" id="treaty2FilePath" value="${treaty2.filePath }">
						<input type="hidden" name="treaty2DestFilePath" id="treaty2DestFilePath" value="${treaty2.destFilePath }">
					</article>
				</section>
			</article>
			<div class="btn_box">
				<botton class="btn" onclick="javascript:ajaxFu(0);">确认提交</botton>
			</div>
		</section>
		<input type="hidden" value="${loanPerson.loanPersonId}" name="loanPersonId" id="loanPersonId"/>
        <input type="hidden" value="${loanPerson.loanDo.loanId}" name="loanId" id="loanId"/>
		<script src="${fileServerUrl }/app_res/js/libs/jquery-2.1.3.js" type="text/javascript"></script>
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
			if(confirm('确认修改合同图片？')){
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
						destFilePath:destFilePath
					};
					$('#' + fid + "Show").hide();
					$('#' + fid + "ing").show();
					$.ajaxFileUpload({
						url : '<c:url value="/app/mhk/saveTreaty.do"/>',
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