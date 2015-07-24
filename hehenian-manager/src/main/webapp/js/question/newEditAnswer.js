var childrenId = -1;
	$(".addModules").live("click",function(){
		var _this = $(this);
		//alert("hello");
		//默认添加出一个父级节点出来
		$.ajax({
			url : '/question/queryModulesinfo.html',
			type:'post',
			dataType:'json',
			data : {
				"isChild" : 1
			},
			success:function(data){
				if(data != null && data.length > 0){
					var modulesVal = $(".modules").size();
					var selectObj = "<div class='modules'><select class='parentModules' name='modulesView["+modulesVal+"].modules[0].questionnaireId'>"
					var childId = 0;
					for(var i = 0; i < data[0].modules.length; i++){
						if(i == 0){
							childId = data[0].modules[i].questionnaireId;
						}
						selectObj += "<option value='"+data[0].modules[i].questionnaireId+"'>"+data[0].modules[i].content+"</option>"
					}
					selectObj += "</select>"
					
					selectObj += "&nbsp;<select class='childModules'  name='modulesView["+modulesVal+"].modules[1].questionnaireId'>"
					
					for(var i = 0; i < data[1].modules.length; i++){
						selectObj += "<option value='"+data[1].modules[i].questionnaireId+"'>"+data[1].modules[i].content+"</option>"
					}
					selectObj += "</select>"
					selectObj += "&nbsp;&nbsp;<a class='addModules' style='cursor:hand'>添加模块</a>&nbsp;&nbsp;<a class='deleteModules'  style='cursor:hand'>删除模块</a></div>"
					//$(selectObj).insertAfter($(this).parent())
					_this.parent().after(selectObj);
				}
			}
		});
	});
	//删除一个模块
	$(".deleteModules").live("click", function(){
		$(this).parent().remove();
	});
	//更换父节点
	$(".parentModules").live("change", function(){
		var _parent = $(this);
		var _parentVal = $(this).val();
		//获取该父节点的子节点数据
		$.ajax({
			url : '/question/queryModulesinfo.html',
			type:'post',
			dataType:'json',
			data : {
				"moduleId" : _parentVal
			},
			success:function(data){
				var obj = _parent.next(".childModules").next(".defaultQuestionnaireId").val();
				//alert(obj);
				var selectObj = "";
				if(data.modules != null && data.modules.length > 0){
					
					for(var i = 0; i < data.modules.length; i++){
						var selected = false;
						if(data.modules[i].questionnaireId == obj){
							selected = true;
						}
						selectObj += "<option value='"+data.modules[i].questionnaireId+"' "+ (selected ? "selected='select'" : "") + ">"+data.modules[i].content+"</option>"
					}
					//$(selectObj).insertAfter($(this).parent())
				}else{
					selectObj += "<option value='-1'>请选择</option>"
				}
				_parent.next(".childModules").html(selectObj);
			}
		});
		//
		
		//alert(_parentVal);
	});
	$(".minusMinChoice").live("click", function(){
		var minObj = $("#minChoice");
		var maxObj = $("#maxChoice");
		
		var minChoice = (minObj.val() == null || minObj.val() == "") ? 1 : minObj.val();
		var maxChoice = (maxObj.val() == null || maxObj.val() == "") ? 1 : maxObj.val();
		if(minChoice > 1){
			--minChoice;
			minObj.val(minChoice);
		}
	});
	$(".minusMaxChoice").live("click", function(){
		var minObj = $("#minChoice");
		var maxObj = $("#maxChoice");
		
		var minChoice = (minObj.val() == null || minObj.val() == "") ? 1 : minObj.val();
		var maxChoice = (maxObj.val() == null || maxObj.val() == "") ? 1 : maxObj.val();
		if(maxChoice > 0){
			--maxChoice;
			if(minChoice < maxChoice){
				maxObj.val(maxChoice);
			}else{
				alert("不能设置小于 minChoice=" + minChoice + "的值");
			}
		}
	});
	$(".addMinChoice").live("click", function(){
		var minObj = $("#minChoice");
		var maxObj = $("#maxChoice");
		
		var minChoice = (minObj.val() == null || minObj.val() == "") ? 0 : minObj.val();
		var maxChoice = (maxObj.val() == null || maxObj.val() == "") ? 0 : maxObj.val();
		if(minChoice >= 0){
			++minChoice;
			if(minChoice <= maxChoice){
				minObj.val(minChoice);
			}else{
				alert("不能设置大于 maxChoice=" + maxChoice + "的值");
			}
		}
	});
	$(".addMaxChoice").live("click", function(){
		var maxObj = $("#maxChoice");
		var maxChoice = (maxObj.val() == null || maxObj.val() == "") ? 0 : maxObj.val();
		if(maxChoice >= 0){
			++maxChoice;
			maxObj.val(maxChoice);
		}
	});
	$(".questionsTypes").live("change", function(){
		var value = $(this).val();
		//alert(value);
		//选择的是选择题
		if(value == 1){
			var html = '<div class="minMaxChoice">答案下限：&nbsp;<a class="minusMinChoice" style="cursor:hand">减小</a>&nbsp;&nbsp;<input type="text" value="1" id="minChoice"/><a class=""/>&nbsp;&nbsp;<a class=""/><a class="addMinChoice" style="cursor:hand">增加</a></br></br>'+
				'答案上限：&nbsp;<a class="minusMaxChoice" style="cursor:hand">减小</a>&nbsp;&nbsp;<input type="text" value="1" id="maxChoice"/><a class=""/>&nbsp;&nbsp;<a class=""/><a class="addMaxChoice" style="cursor:hand">增加</a></div>';
			$(this).parent().after(html);
		}else{
			$(".minMaxChoice").remove();
			
		}
	});
	$(".addAnswer").live("click", function(){
		var contentVal = $(".answerContent").size();
		var maxOrders = 0;
		$(".answerOrders").each(function() {
		    if(this.value != null && this.value != ""){
		    	if(parseInt(this.value) > parseInt(maxOrders)){
		    		maxOrders = parseInt(this.value);
		    	}
		    }
		})
		
		//alert(contentVal);
		//var ordersVal = $(".answerOrders").size();
		//var sexStatusVal = $(".answerSexStatus").size() / 3;
		//var isUseVal = $(".answerIsUse").size() / 2;
		//var relationIdsVal = $(".answerRelationIds").size();
		
		var html = '<tr class="row">'+
			'<td><input class="answerContent" value="" name="answers['+contentVal+'].content"/></td>'+
			'<td><input class="answerOrders" value="'+(parseInt(maxOrders)+1)+'" name="answers['+contentVal+'].orders"/></td>'+
			'<td>'+
			'<input type="radio" class="sexStatus" name="answers['+contentVal+'].sexStatus" value="-1" checked="checked"/>男女通用&nbsp;&nbsp;'+
			'<input type="radio" class="sexStatus" name="answers['+contentVal+'].sexStatus" value="0"  />男性用户&nbsp;&nbsp;'+
			'<input type="radio" class="sexStatus" name="answers['+contentVal+'].sexStatus" value="1"  />女性用户'+
			'</td>'+
			'<td>'+
			'<input type="radio" class="isUse" name="answers['+contentVal+'].isUse" value="0" />停用&nbsp;&nbsp;'+
			'<input type="radio" class="isUse" name="answers['+contentVal+'].isUse" value="1" checked="checked"/>启用'+
			'</td>'+
			'<td>'+
				'<input type="text" class="relationIds" name="answers['+contentVal+'].relationIdsStr" value=""/>'+
			'</td>'+
			'<td>&nbsp;&nbsp;<a class="deleteAnswer" style="cursor: hand">删除该项</a></td>'
	    '</tr>';
	    var text = $("#tableBody").text();
		$("#tableBody").append(text + html);
	});
	$(".deleteAnswer").live("click", function(){
		$(this).parent().parent().remove();
	});
	$("#button").live("click", function(){
		var _content = $(".content").val();
		var _questionType = $(".questionsTypes").val();
		if(_questionType == -1){
			alert("请选择问题类型");
			return;
		}
		if(_content == ""){
			alert("请填写内容");
			return;
		}
		if($(".parentModules").length > 0){
			var val = new Array();
			var j = 0;
			$(".parentModules").each(function(){
				val[j++] = $(this).val();
			});
			
			var _value = val[0];
			var flag = false;
			for(var i = 1; i < val.length; i++){
				if(_value == val[i]){
					flag = true;
					break;
				}
			}
			if(flag){
				alert("父模块不能存在相同项！");
				return ;
			}
		}
		if($(".childModules").length > 0){
			var val = new Array();
			var j = 0;
			$(".childModules").each(function(){
				val[j++] = $(this).val();
			});
			
			var _value = val[0];
			var flag = false;
			for(var i = 1; i < val.length; i++){
				if(_value == val[i]){
					flag = true;
					break;
				}
			}
			if(flag){
				alert("模块不能存在相同项！");
				return;
			}
		}
		
			$(".submitForm").submit();
		
		
	});