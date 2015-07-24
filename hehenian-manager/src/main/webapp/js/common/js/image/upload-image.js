$(function (){
	$("#fileupload").fileupload({
	    url:httpUrl+"/user/userinfo/uploadimage.do",//文件上传地址，当然也可以直接写在input的data-url属性内
//	    formData:$("#userAddForm").serialize(),//如果需要额外添加参数可以在这里添加
	    formData: {"userInfoImage":$("#fileupload").val()},//如果需要额外添加参数可以在这里添加
//	    maxChunkSize: 2048,
	    dataType: 'json',
	    done:function(e,result){
	        //done方法就是上传完毕的回调函数，其他回调函数可以自行查看api
	        //注意result要和jquery的ajax的data参数区分，这个对象包含了整个请求信息
	        //返回的数据在result.result中，假设我们服务器返回了一个json对象
	        var data = result.result; 
	        if(data.executeStatus == 1){
	        	alert(result.result.errorMsg);
			}else{
				if($("#editPanel").attr("oper") == "create"){
					$("#employeeImage").val(data.image);
				}else{
					$("#newEmployeeImage").val(data.image);
				}
			}
	    },
	    success: function(data){ 
	    	// 设置图片名称
	    	$("#user_add_imgName").val(data.name);
	    }
	});
});