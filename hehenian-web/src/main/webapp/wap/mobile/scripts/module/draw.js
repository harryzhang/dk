Zepto(function(){
	var bankList = $("#bankList"),bankSelect = null;
	var int_bankId = $("#int_bankId"),int_openAcctId = $("#int_openAcctId");
	bankList.on("click","a",function(){
		var thisParent = $(this).parent();
		if(bankSelect){
			bankSelect.removeClass("select");
			bankSelect.prev().removeClass("select-prev");
		}
		bankSelect = thisParent;
		bankSelect.addClass("select")
		bankSelect.prev().addClass("select-prev");
		var bankCard = bankSelect.find("input");
		bankCard.prop("checked",true);
		var idAndCartNo = bankCard.val().split("-");
		int_bankId.val(idAndCartNo[0]);
		int_openAcctId.val(idAndCartNo[1]);
		return false;
	});
	
	var doDraw = $("#doDraw"),input = $("#int_money")
	var form = new HHN.Form("form");
	doDraw.on("click",function(){
		if($("#form").length<=0){
			HHN.popup({type:"alert",content:"请新增或者选择提现银行卡信息",element:bankSelect});	
			return false;
		}
		var value = input[0].value;
		form.validate({
			error:function(text){
				HHN.popup({type:"alert",content:text,element:input});
				input.focus();
			},
			success:function($form){
				$form.submit();
			}
		},input);
		return false;
	});
});