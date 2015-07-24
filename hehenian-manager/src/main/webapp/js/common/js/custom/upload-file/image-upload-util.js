/**
 *
 *docName   file组件的name
 *viewId  img的id
 *localId  div的id
 * imgWidth  img的宽度
 * imgHeight img的高度
 * divWidth div的宽度
 * divHeight div的高度
*/
function setImagePreview(docName , viewId , localId 
						,imgWidth ,imgHeight , divWidth , divHeight) { 
	 var docObj=document.getElementById(docName) ; //"doc");
	 var imgObjPreview=document.getElementById(viewId) ; //"preview");
      if(docObj.files && docObj.files[0]){
          //火狐下，直接设img属性
           imgObjPreview.style.display = 'block';
           imgObjPreview.style.width = imgWidth ; //'60px';
           imgObjPreview.style.height = imgHeight ; //'60px';                    
            //imgObjPreview.src = docObj.files[0].getAsDataURL();

		  //火狐7以上版本不能用上面的getAsDataURL()方式获取，需要一下方式  
			imgObjPreview.src = window.URL.createObjectURL(docObj.files[0]);
	   }else{
            //IE下，使用滤镜
             docObj.select();
            var imgSrc = document.selection.createRange().text;
            var localImagId = document.getElementById(localId) ; //"localImag");
            //必须设置初始大小
            localImagId.style.width = divWidth ; //"300px";
            localImagId.style.height = divHeight ; //"120px";
            //图片异常的捕捉，防止用户修改后缀来伪造图片
			try{
                 localImagId.style.filter="progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)";
                 localImagId.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = imgSrc;
             }catch(e){
                   alert("您上传的图片格式不正确，请重新选择!");
                   return false;
             }
            imgObjPreview.style.display = 'none';
            document.selection.empty();
        }
       return true;
}

//获取上传文件的名字
function getFullPath(obj,id){
	if(obj)
	{
		var path=obj.value;
		var index=path.lastIndexOf("\\")+1;
		if(index>0){
			$('#'+id).html(path.substr(index));
		}else{
			$('#'+id).html(path);
		}
	}
}

/**
 * 模拟点击file文本框
 */
$('#uploadFileZip').on('click',function(){
	$('#uploadFilet').click();
});

/**
 * 模拟点击file文本框
 */
$('#toUploadFile_zip').on('click',function(){
	$('#uploadFileZip').click();
});


