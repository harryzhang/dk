








		  var timers=180;
		  var tipId;
		 
		   $(function(){
		   
		       
		       //当用户再次输入手机号码时候 add by lw
		       	$("#cellPhone").change( function() {
		       var phone=$("#cellPhone").val();
		        if($("#cellPhone").val()==""){
		        $("#u_cellPhone").attr("class", "formtips onError");
		        $("#u_cellPhone").html("请填写手机号码！");
		        }else if((!/^1[3,5,8]\d{9}$/.test($("#cellPhone").val()))){ 
                      $("#u_cellPhone").attr("class", "formtips onError");
	                  $("#u_cellPhone").html("请正确填写手机号码！");
                }else{
                      $("#u_cellPhone").attr("class", "formtips");
	                  $("#u_cellPhone").html(""); 
	                   if($("#clickCode5").html()!="重新发送验证码"||$("#clickCode").html()!="点击获取验证码") { 
	                     //当处在发送中的状态时候
	                    window.clearInterval(tipId);
	                    $("#clickCode5").html("点击获取验证码");
		                $.post("removeCode.do","",function(data){});//删除手机验证码
		                 }
	                   }
		        
                });
		       //.......................
		       
		       
		   
		   
		   
		       $("#clickCode5").click(function(){
		           //验证手机号码是
		           if($("#cellPhone").val()==""){
                      $("#u_cellPhone").attr("class", "formtips onError");
		              $("#u_cellPhone").html("请填写手机号码！");
                   }else if((!/^1[3,5,8]\d{9}$/.test($("#cellPhone").val()))){ 
                      $("#u_cellPhone").attr("class", "formtips onError");
	                  $("#u_cellPhone").html("请正确填写手机号码！");
                   }else{
                      $("#u_cellPhone").attr("class", "formtips");
	                  $("#u_cellPhone").html(""); 
	                 
	                  
	                 if($("#clickCode5").html()=="重新发送验证码"||$("#clickCode5").html()=="点击获取验证码") {  
	                    $.post("sendSMS.do","phone=" + $("#cellPhone").val(),function(data){
	                       if(data==1){
	                          timers=180;
	                          tipId=window.setInterval(timer,1000);
	                       }else{
	                          alert("手机验证码发送失败");
	                       }
	                     
	                  
	                    });
	                  }
                   }
		       });
		   });
		   
		   //定时
		   function timer(){
		    
		       if(timers>=0){
		         $("#clickCode5").html("验证码获取："+timers+"秒");
		         timers--;
		       }else{
		          window.clearInterval(tipId);
		           $("#clickCode5").html("重新发送验证码");
		           $.post("removeCode.do","",function(data){});
		           
		       }
		   }


//======
$(document).ready(function(){
    $("#BaseDataform :input").blur(function(){
    //验证手机号码
      if($(this).attr("id")=="cellPhone"){
      if( $(this).val() ==""){
         $("#u_cellPhone").attr("class", "formtips onError");
		 $("#u_cellPhone").html("请填写手机号码！");
      }else if((!/^1[3,5,8]\d{9}$/.test($("#cellPhone").val()))){ 
       $("#u_cellPhone").attr("class", "formtips onError");
	     $("#u_cellPhone").html("请正确填写手机号码！");
      }else{
           $("#u_cellPhone").attr("class", "formtips");
	       $("#u_cellPhone").html(""); 
      }
  }
  //真实姓名
  		if($(this).attr("id")=="realName"){  
  		        var isName = new Object();
  		        isName = /^[a-zA-Z\u4e00-\u9fa5]+$/;
				if($(this).val() ==""){
			      	$("#u_realName").attr("class", "formtips onError");
			      	$("#u_realName").html("请填写真实姓名！");
			    }else if($(this).val().length <2 || $(this).val().length> 20){   
	            	$("#u_realName").attr("class", "formtips onError");
	                $("#u_realName").html("名字长度为2-20个字符"); 
	            }else if(!isName.test($(this).val())){
	                  $("#u_realName").html("真实姓名输入有误"); 
	            }
	            else{   
	            	$("#u_realName").attr("class", "formtips");
	                $("#u_realName").html(""); 
	            } 
          }
  //========
  //身份号码
  if($(this).attr("id")=="idNo"){
     var isIDCard1 = new Object();
     var isIDCard2 = new Object();
     //身份证正则表达式(15位) 
     isIDCard1=/^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$/; 
     //身份证正则表达式(18位) 
     isIDCard2=/^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])((\d{4})|\d{3}(x|X))$/; 
    if($(this).val() ==""){
       //$("#u_idNo").attr("class", "formtips onError");
      $("#u_idNo").html("请填写身份证号码");
    }else if(isIDCard1.test($(this).val())||isIDCard2.test($(this).val())){
           //验证身份证号码的有效性
      var parama = {};
      parama["paramMap.idNo"] = $(this).val();
      $.post("isIDNO.do",parama,function(data){
       if(data.putStr=="0"){
        $("#u_idNo").html("请填写身份证号码");
      }else if(data.putStr=="1"){
           $("#u_idNo").html("身份证号码不合法!");
      }else{
        $("#u_idNo").html("");
      }
    });
    }else {
        $("#u_idNo").html("身份证号码不正确");
    }
  }
  //========== 验证出生年月
      if($(this).attr("id")=="birthday"){
    if($(this).val() !=""){
       $("#u_birthday").html("");
    }
  }

  //===========验证毕业院校
      if($(this).attr("id")=="school"){
    if($(this).val() ==""){
    $("#u_school").attr("class", "formtips onError");
    $("#u_school").html("请填写毕业院校");
    }else{
    $("#u_school").attr("class", "formtips");
       $("#u_school").html("");
    }
  }
 //==手机验证码
       if($(this).attr("id")=="vilidataNum"){
    if($(this).val() ==""){
    $("#u_vilidataNum").attr("class", "formtips onError");
    $("#u_vilidataNum").html("请填写手机验证码");
    }else{
    $("#u_vilidataNum").attr("class", "formtips");
       $("#u_vilidataNum").html("");
    }
  }
 //=========最高学历
        if($(this).attr("id")=="highestEdu"){
    if($(this).val() ==""){
    $("#u_highestEdu").attr("class", "formtips onError");
    $("#u_highestEdu").html("请填写最高学历");
    }else{
    $("#u_highestEdu").attr("class", "formtips");
       $("#u_highestEdu").html("");
    }
  }
 //============入学年份
      if($(this).attr("id")=="eduStartDay"){
    if($(this).val() !=""){
       $("#u_eduStartDay").html("");
    }
  }
 //=====籍　　贯

    if($(this).attr("id")=="nativePlacePro"){
    if($(this).val() ==""){
    $("#u_Pro_City").attr("class", "formtips onError");
    $("#u_Pro_City").html("请填写籍贯");
    }else{
    $("#u_Pro_City").attr("class", "formtips");
       $("#u_Pro_City").html("");
    }
  }
         if($(this).attr("id")=="nativePlaceCity"){
    if($(this).val() ==""){
    $("#u_Pro_City").attr("class", "formtips onError");
    $("#u_Pro_City").html("请填写籍贯");
    }else{
    $("#u_Pro_City").attr("class", "formtips");
       $("#u_Pro_City").html("");
    }
  }

 //======户口所在地
       if($(this).attr("id")=="registedPlacePro"){
    if($(this).val() ==""){
    $("#u_reg_Pro_City").attr("class", "formtips onError");
    $("#u_reg_Pro_City").html("请填写户口所在地");
    }else{
    $("#u_reg_Pro_City").attr("class", "formtips");
       $("#u_reg_Pro_City").html("");
    }
  }
         if($(this).attr("id")=="registedPlaceCity"){
    if($(this).val() ==""){
    $("#u_reg_Pro_City").attr("class", "formtips onError");
    $("#u_reg_Pro_City").html("请填写户口所在地");
    }else{
    $("#u_reg_Pro_City").attr("class", "formtips");
       $("#u_reg_Pro_City").html("");
    }
  }
 
 //============居住地址
    if($(this).attr("id")=="address"){
		    if($(this).val() ==""){
			    $("#u_address").attr("class", "formtips onError");
			    $("#u_address").html("请填写居住地址");
		    }else{
		       var array = $(this).val();
		       var cur = array.substr(0,1);
		       if (/^\d+$/.test(cur)) {  
		          $("#u_address").html("居住地址不能以数字开头");
		          return;
		       }
		       
		       $("#u_address").attr("class", "formtips");
		       $("#u_address").html("");
		    }
  }
 //================居住电话
        if($(this).attr("id")=="telephone"){
        var mdd =/^((0\d{2,3})-)(\d{7,8})(-(\d{3,}))?$/;
    if($(this).val() ==""){
    $("#u_telephone").attr("class", "formtips onError");
    $("#u_telephone").html("请填写居住电话");
 }else if(!mdd.test($(this).val())){
       $("#u_telephone").attr("class", "formtips onError");
       $("#u_telephone").html("请填写正确的居住电话");
    }else{
    $("#u_telephone").attr("class", "formtips");
       $("#u_telephone").html("");
    }
  }
 //============
     });
      
     		$("#province").change(function(){
			var provinceId = $("#province").val();
			citySelectInit(provinceId, 2);
			//$("#area").html("<option value='-1'>--请选择--</option>");
		});
			 $("#registedPlacePro").change(function(){
			var provinceId = $("#registedPlacePro").val();
			registedPlaceCity(provinceId, 2);
			//$("#area").html("<option value='-1'>--请选择--</option>");
		});
		
     

});

	function registedPlaceCity(parentId, regionType){
		var _array = [];
		_array.push("<option value='-1'>--请选择--</option>");
		var param = {};
		param["regionType"] = regionType;
		param["parentId"] = parentId;
		$.post("ajaxqueryRegion.do",param,function(data){
			for(var i = 0; i < data.length; i ++){
				_array.push("<option value='"+data[i].regionId+"'>"+data[i].regionName+"</option>");
			}
			$("#registedPlaceCity").html(_array.join(""));
		});
	}




	function citySelectInit(parentId, regionType){
		var _array = [];
		_array.push("<option value='-1'>--请选择--</option>");
		var param = {};
		param["regionType"] = regionType;
		param["parentId"] = parentId;
		$.post("ajaxqueryRegion.do",param,function(data){
			for(var i = 0; i < data.length; i ++){
				_array.push("<option value='"+data[i].regionId+"'>"+data[i].regionName+"</option>");
			}
			$("#city").html(_array.join(""));
		});
	}
	
	
	//提交基础资料
	  $("#jc_btn").click(function(){
	     var tsex = '${map.sex}'==''?'':'${map.sex}';
	     if($("#realName").val()==""){
	    // $("#u_realName").html("请填写真实姓名！");
	     alert("请填写真实姓名！");
	     return false ;
	     }
	     if($("#idNo").val()==""){
	    // $("#u_idNo").html("请填写身份号码！");
	     alert("请填写身份号码！");
	     return false;
	      }
	     if($("#cellPhone").val()==""){
	     //$("#u_cellPhone").html("请填写手机号码！");
	      alert("请填写手机号码！");
	     return false ;
	     }
	     if($("#vilidataNum").val()==""){ 
	    // $("#u_vilidataNum").html("请填写手机验证码");
	      alert("请填写手机验证码！");
	      return false;
	      }
	      
	      
	     if(tsex==''){ 
         if(($("input[name='paramMap_sex']:checked").val())==undefined||($("input[name='paramMap_sex']:checked").val())==""){
	      //$("#u_sex").html("请填写你的性别");
	       alert("请勾选你的性别");
	      return false;
	      }
	      }else{
	      
	       if($("#sex").val()==""){ 
	    // $("#u_vilidataNum").html("请填写手机验证码");
	          alert("请勾选你的性别");
	      return false;
	      }
	      
	      
	      }

	       
	        
       if($("#birthday").val() ==""){
     $("#u_birthday").html("请填写出生年月");
       alert("请填写出生年月");
       return false;
     }else if(!(!/^[1,2][0-9]\d{2}$/.test($("#birthday").val()))){
       //  $("#u_birthday").attr("class", "formtips onError");
     $("#u_birthday").html("年份格式错误");
       alert("出生年月年份格式错误");
       return false;
    }
	     if($("#highestEdu").val()==""){
	     //$("#u_highestEdu").html("请填写最高学历！");
	      alert("请填写最高学历！");
	      return false ;
	     }

	     
	     
	  if($("#eduStartDay").val() ==""){
	  $("#u_eduStartDay").html("请填写入学年月");
       alert("请填写入学年月");
       return false;
     }else if(!(!/^[1,2][0-9]\d{2}$/.test($("#eduStartDay").val()))){
      $("#u_eduStartDay").html("入学年月格式错误");
       alert("入学年月格式错误");
       return false;
      }
	     
	     
	     
	     
	     if($("#school").val()==""){
	     //$("#u_school").html("请填写毕业院校!");
	     alert("请填写毕业院校!");
	     return false ;
	     }
	     if(($("input[name='paramMap_maritalStatus']:checked").val())==undefined||($("input[name='paramMap_maritalStatus']:checked").val())==""){
	      //$("#u_maritalStatus").html("请填写你结婚状态");
	      alert("请填写你的结婚状态!");
	      return false;
	     }
	      if(($("input[name='paramMap_hasChild']:checked").val())==undefined||($("input[name='paramMap_hasChild']:checked").val())==""){
	     // $("#u_hasChild").html("请填写你是否有孩子");
	      alert("请填写你是否有孩子");
	      return false;
	    }
	        if(($("input[name='paramMap_hasHourse']:checked").val())==undefined||($("input[name='paramMap_hasHourse']:checked").val())==""){
	      //$("#u_hasHourse").html("请填写你是否有房子");
	      alert("请填写你是否有房子");
	      return false;
	    }
	        if(($("input[name='paramMap_hasHousrseLoan']:checked").val())==undefined||($("input[name='paramMap_hasHousrseLoan']:checked").val())==""){
	    //  $("#u_hasHousrseLoan").html("请填写你是否有房贷");
	       alert("请填写你是否有房贷");
	      return false;
	    }
	     if(($("input[name='paramMap_hasCar']:checked").val())==undefined||($("input[name='paramMap_hasCar']:checked").val())==""){
	      //$("#u_hasCar").html("请填写你是否有车");
	        alert("请填写你是否有车");
	      return false;
	    }
	     if(($("input[name='paramMap_hasCarLoan']:checked").val())==undefined||($("input[name='paramMap_hasCarLoan']:checked").val())==""){
	     // $("#u_hasCarLoan").html("请填写你是否有车贷");
	      alert("请填写你是否有车贷");
	      return false;
	    }
	    
	    if($('#province').val()==-1){
	    //$('#u_Pro_City').html('请选择你的省份');
	     alert("请选择你的籍贯省份");
	     return false;
	     }
	    
	     if($('#city').val()==-1){
	    //$('#u_Pro_City').html('请选择你的城市') ;
	     alert("请选择你的籍贯城市");
	     return false;
	     }
	    if($('#registedPlacePro').val()==-1){
	    //$('#u_reg_Pro_City').html('请选择你的省份');
	     alert("请选择你的户口省份");
	     return false;}
	  if($('#registedPlaceCity').val()==-1){
	 // $('#u_reg_Pro_City').html('请选择你的城市');
	     alert("请选择你的户口城市");
	    return false;
	   }
	   
	 if($('#address').val()==""){
	     alert("请填写居住地址");
	    return false;
	   }
	   
	    if($('#u_address').html()!=""){
		     alert("请正确填写居住地址");
		    return false;
	   }
	   
	  	 if($('#telephone').val()==""){
	     alert("请填写居住电话");
	      return false;
	   }else{

	   var mddd =/^((0\d{2,3})-)(\d{7,8})(-(\d{3,}))?$/;
	   if(!mddd.test($('#telephone').val())){
	       alert("请按照格式正确填写居住电话");
	       return false;
	    }
	   }
	   
	   if($("#img").attr("src")==""){
	       alert("请上传你的个人头像");
	       return false;
	   }

	   var param = {};
	    param["paramMap.realName"]=$("#realName").val();
	    param["paramMap.idNo"]=$("#idNo").val();
	    param["paramMap.cellPhone"]=$("#cellPhone").val();
	    param["paramMap.vilidataNum"]=$("#vilidataNum").val();
	    
	    
	    if(tsex==''){
	    param["paramMap.sex"]=$("input[name='paramMap_sex']:checked").val();
	    }else{
	     param["paramMap.sex"]=$("#sex").val();
	    }
	   // alert($("input[name='paramMap_sex']:checked").val());
	   /* if(($("input[name='paramMap_sex']:checked").val())=='undefinded'){
	     alert("填写");
	    }
	    */
	    
	    param["paramMap.birthday"]=$("#birthday").val();
	    param["paramMap.highestEdu"]=$("#highestEdu").val();
	    param["paramMap.eduStartDay"]=$("#eduStartDay").val();
	    param["paramMap.school"]=$("#school").val();
	    param["paramMap.maritalStatus"]=$("input[name='paramMap_maritalStatus']:checked").val();
	    param["paramMap.hasChild"]=$("input[name='paramMap_hasChild']:checked").val();
	    param["paramMap.hasHourse"]=$("input[name='paramMap_hasHourse']:checked").val();
	    param["paramMap.hasHousrseLoan"]=$("input[name='paramMap_hasHousrseLoan']:checked").val();
	    param["paramMap.hasCar"]=$("input[name='paramMap_hasCar']:checked").val();
	    param["paramMap.hasCarLoan"]=$("input[name='paramMap_hasCarLoan']:checked").val();
	    param["paramMap.nativePlacePro"]=$("#province").val();
	    param["paramMap.nativePlaceCity"]=$("#city").val();
	   
	    param["paramMap.registedPlacePro"]=$("#registedPlacePro").val();
	    param["paramMap.registedPlaceCity"]=$("#registedPlaceCity").val();
	    
	    param["paramMap.address"]=$("#address").val();
	    param["paramMap.telephone"]=$("#telephone").val();
	    //用户头像路径
	    param["paramMap.personalHead"]=$("#img").attr("src");
	    $.post("updateBasedate.do",param,function(data){
	    	if(data == "virtual"){
				window.location.href = "noPermission.do";
				return ;
	    	}
	       if(data.msg=="保存成功"){
	            alert("保存成功"); 
	            window.clearInterval(tipId);
	            $("#clickCode5").html("点击获取验证码");
	            
	            /*$("#infomationdata").removeClass("on");
	        $("#workdate").addClass("on");
	      	$('.tabtil').nextAll('div').hide();
	      	$('.tabtil').nextAll('div').eq($("#workdate").index()).show();
	      	$.shovePost("queryWorkInit.do",null,function(data){
		        $("#workdi").html(data);
		    });*/
		    //不需要进行跳转，直接保存就可以
	            window.location.href = 'owerInformationInit.do?yy=c2';
	       }else{
	         //alert("请正确填写基本资料");
	         if(data.msg=="请正确填写真实名字"){
	         //$("#u_realName").html("请填写真实姓名！")
	         alert("请填写真实姓名！");
	       }
	            if(data.msg=="真实姓名的长度为不小于2和大于20"){
	            //$("#u_realName").html("真实姓名的长度为不小于2和大于20！")
	             alert("真实姓名的长度为不小于2和大于20！");
	       }
	            if(data.msg=="请正确身份证号码"){
	        // $("#u_idNo").html("请正确身份证号码！")
	         alert("请正确输入你的身份证号码");
	       }
	            if(data.msg=="请正确填写手机号码"){
	           // $("#u_cellPhone").html("请填写手机号码！");
	              alert("请填写手机号码!");
	       }
	       if(data.msg=="手机号码长度不对"){
	        // $("#u_cellPhone").html("手机号码长度不对！")
	         alert("手机号码长度不对！");
	       }
	       
	       if(data.msg=="手机已存在"){
	        alert("该手机号码已经被注册过了！");
	       }
	       
	       
	       
 //........................................... 
	       //手机验证码
	       if(data.msg=="与获取验证码手机号不一致"){
	        // $("#u_cellPhone").html("手机号码长度不对！")
	         alert("手机号码与获取验证码手机号不一致！");
	       }
	       if(data.msg=="请填写验证码"){
	        // $("#u_cellPhone").html("手机号码长度不对！")
	         alert("请填写验证码");
	       }
	       if(data.msg=="请重新发送验证码"){
	        // $("#u_cellPhone").html("手机号码长度不对！")
	         alert("请重新发送验证码");
	       }
	       
	        if(data.msg=="请输入正确的验证码"){
	        // $("#u_cellPhone").html("手机号码长度不对！")
	         alert("请输入正确的验证码");
	       }
	       
//.....................................
	       
	       
	       
	       }
	       if(data.msg=="请正确填写手机号码"){
	        // $("#u_cellPhone").html("手机号码长度不对！")
	         alert("手机验证码填写错误！");
	       }
	      if(data.msg=="请正确填写性别"){
	        // $("#u_cellPhone").html("手机号码长度不对！")
	         alert("请勾选填写性别！");
	       }
	       if(data.msg=="请正确填写出生日期"){
	         //$("#u_birthday").html("请正确填写出生日期！")
	         alert("请正确填写出生日期！");
	       }
	       if(data.msg=="请正确填写入学年份"){
	        // $("#u_eduStartDay").html("请正确填写入学年份！")
	         alert("请正确填写入学年份！");
	       }
	             if(data.msg=="请正确填写入毕业院校"){
	        // $("#u_school").html("请正确填写入毕业院校！")
	          alert("请正确填写入毕业院校！");
	       }
	             if(data.msg=="请正确填写入学年份"){
	       //  $("#u_eduStartDay").html("请正确填写入学年份！")
	        alert("请正确填写入学年份！");
	       }
	        if(data.msg=="请正确填写最高学历"){
	         //$("#u_highestEdu").html("请正确填写最高学历！")
	          alert("请正确填写最高学历！");
	       }
	         if(data.msg=="请正确填写入籍贯省份"){
	        // $("#u_nativePlacePro").html("请正确填写入籍贯省份！")
	         alert("请正确填写入籍贯省份！");
	       }
	       if(data.msg=="请正确填写入籍贯城市"){
	         //$("#u_nativePlaceCity").html("请正确填写入籍贯城市！")
	            alert("请正确填写入籍贯城市！");
	       }
             if(data.msg=="请正确填写入户口所在地省份"){
	       //  $("#u_registedPlacePro").html("请正确填写入户口所在地省份！")
	          alert("请正确填写入户口所在地省份!");
	       }
	                 if(data.msg=="请正确填写入户口所在地城市"){
	        // $("#u_registedPlaceCity").html("请正确填写入户口所在地城市！")
	           alert("请正确填写入户口所在地城市!");
	       }
	       if(data.msg=="居住地址不能以数字开头"){
	        // $("#u_registedPlaceCity").html("请正确填写入户口所在地城市！")
	           alert("居住地址不能以数字开头!");
	       }
	        if(data.msg=="请正确填写入你的家庭电话"){
	         //$("#u_telephone").html("请正确填写入你的家庭电话！")
	        alert("请正确填写入你的家庭电话！");
 
	       }
	      if(data.msg=="你的家庭电话输入不正确"){
	         //$("#u_telephone").html("请正确填写入你的家庭电话！")
	         alert("请正确填写入你的居住电话");
	       }
	      
	      if(data.msg=="你的居住电话输入长度不对"){
	         //$("#u_telephone").html("请正确填写入你的家庭电话！")
	         alert("你的居住电话输入长度不对");
	       }
	       
	    
	           if(data.msg=="请正确上传你的个人头像"){
	         //$("#u_img").html("个人头像不能为空！")
	          alert("个人头像不能为空！");
	       }
	       if(data.msg=="超时请重新登录"){
	          window.location.href='login.do';
	       }
	       if(data.msg=="身份证不合法"){
	        alert("你输入的身份证号码不合法,请重新输入");
	        $("#u_idNo").html("请输入正确身份证号码！")
	       }
	    });
	    
	});


	
			$(function(){
				//上传图片
				$("#btn_personalHead").click(function(){
					var dir = getDirNum();
					var json = "{'fileType':'JPG,BMP,GIF,TIF,PNG','fileSource':'user/"+dir+"','fileLimitSize':0.5,'title':'上传图片','cfn':'uploadCall','cp':'img'}";
					json = encodeURIComponent(json);
					 window.showModalDialog("uploadFileAction.do?obj="+json,window,"dialogWidth=500px;dialogHeight=400px");
					var headImgPath = $("#img").attr("src");
					if(headImgPath!=""){
						//window.location.href="updateHeadImg.do?userHeadImg="+headImgPath;	
						 $("#u_img").html("");
					}else{ 
						alert("上传失败！");	
					}
				});
				
			});
			
			function uploadCall(basepath,fileName,cp){
		  		if(cp == "img"){
		  			var path = "upload/"+basepath+"/"+fileName;
					$("#img").attr("src",path);
					$("#setImg").attr("src",path);
		  		}
			}
			
			function getDirNum(){
		      var date = new Date();
		 	  var m = date.getMonth()+1;
		 	  var d = date.getDate();
		 	  if(m<10){
		 	  	m = "0"+m;
		 	  }
		 	  if(d<10){
		 	  	d = "0"+d;
		 	  }
		 	  var dirName = date.getFullYear()+""+m+""+d;
		 	  return dirName; 
			}
			//======================工作认证
			
			//===============|~

			
		  $(function(){
		     //æ ·å¼éä¸­
    // $("#jk_hover").attr('class','nav_first');
	 $("#jk_hover div").removeClass('none');
		  });
