function questionClass(defaultWidth, defaultHeight){
	$(".image_Class").each(function(){
		var $image = $(this);
		var $height = $image.css("height");
		
		var $width = $image.css("width");
		$width = $width.substring(0,$width.indexOf("px"));
		$height = $height.substring(0,$height.indexOf("px"));
		
		while(true){
			if(parseInt($width) < parseInt(defaultWidth)){
				break;
			}
			$width = $width / 2;
			$image.css("width",$width);
		}
		while(true){
			if(parseInt($height) < parseInt(defaultHeight)){
				break;
			}
			$height = $height / 2;
			$image.css("height",$height);
		}
	});
}