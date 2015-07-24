//检查图片的格式是否正确,同时实现预览  
function setImagePreview(obj, localImagId, imgObjPreview) {  
    var array = new Array('gif', 'jpeg', 'png', 'jpg', 'bmp'); //可以上传的文件类型  
    if (obj.value == '') {  
        $.messager.alert("让选择要上传的图片!");  
        return false;  
    }  
    else {  
        var fileContentType = obj.value.match(/^(.*)(\.)(.{1,8})$/)[3]; //这个文件类型正则很有用   
        ////布尔型变量  
        var isExists = false;  
        //循环判断图片的格式是否正确  
        for (var i in array) {  
            if (fileContentType.toLowerCase() == array[i].toLowerCase()) {  
                //图片格式正确之后，根据浏览器的不同设置图片的大小  
                if (obj.files && obj.files[0]) {  
                    //火狐下，直接设img属性   
                    imgObjPreview.style.display = 'block';  
                    imgObjPreview.style.width = '400px';  
                    imgObjPreview.style.height = '400px';  
                    //火狐7以上版本不能用上面的getAsDataURL()方式获取，需要一下方式   
                    imgObjPreview.src = window.URL.createObjectURL(obj.files[0]);  
                }  
                else {  
                    //IE下，使用滤镜   
                    obj.select();  
                    var imgSrc = document.selection.createRange().text;  
                    //必须设置初始大小   
                    localImagId.style.width = "400px";  
                    localImagId.style.height = "400px";  
                    //图片异常的捕捉，防止用户修改后缀来伪造图片   
                    try {  
                        localImagId.style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)";  
                        localImagId.filters.item("DXImageTransform.Microsoft.AlphaImageLoader=").src = imgSrc;  
                    }  
                    catch (e) {  
                        $.messager.alert("您上传的图片格式不正确，请重新选择!");  
                        return false;  
                    }  
                    imgObjPreview.style.display = 'none';  
                    document.selection.empty();  
                }  
                isExists = true;  
                return true;  
            }  
        }  
        if (isExists == false) {  
            $.messager.alert("上传图片类型不正确!");  
            return false;  
        }  
        return false;  
    }  
}  

    //显示图片    
function over(imgid, obj, imgbig) {
    return;
    //大图显示的最大尺寸  4比3的大小  400 300    
    maxwidth = 400;  
    maxheight = 300;  

    //显示    
    obj.style.display = "";  
    imgbig.src = imgid.src;  

    //1、宽和高都超过了，看谁超过的多，谁超的多就将谁设置为最大值，其余策略按照2、3    
    //2、如果宽超过了并且高没有超，设置宽为最大值    
    //3、如果宽没超过并且高超过了，设置高为最大值    

    if (img.width > maxwidth && img.height > maxheight) {  
        pare = (img.width - maxwidth) - (img.height - maxheight);  
        if (pare >= 0)  
            img.width = maxwidth;  
        else  
            img.height = maxheight;  
    }  
    else if (img.width > maxwidth && img.height <= maxheight) {  
        img.width = maxwidth;  
    }  
    else if (img.width <= maxwidth && img.height > maxheight) {  
        img.height = maxheight;  
    }  
}