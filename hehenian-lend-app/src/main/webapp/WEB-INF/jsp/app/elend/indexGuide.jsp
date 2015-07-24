<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8"/>
    	<meta name="viewport" content="width=device-width initial-scale=1.0 maximum-scale=1.0 user-scalable=0">
    	<style>
			*{
				margin: 0;padding: 0;list-style: none;;
			}
			.show ul{
				height: 100%;
				width: 100%;
				overflow: hidden;
				position: absolute;
			}
			.show li{
				position: absolute;
				width: 100%;
				height: 100%;
			}
			.show li:nth-child(1){
				background: url(${fileServerUrl }/app_res/img/elend/indexGuide1.png) 50% 50% no-repeat;
				background-size: 100%;
				z-index: 5;
			}
			.show li:nth-child(2){
				background: url(${fileServerUrl }/app_res/img/elend/indexGuide2.png) 50% 50% no-repeat;
				background-size: 80% ;
				z-index: 4;
			}
			.show li:nth-child(3){
				background: url(${fileServerUrl }/app_res/img/elend/indexGuide3.png) 50% 50% no-repeat;
				background-size: 80% ;
				z-index: 3;
			}
			.show li:nth-child(4){
				background: url(${fileServerUrl }/app_res/img/elend/indexGuide4.png) 50% 50% no-repeat;
				background-size: 80% ;
				z-index: 2;
			}
			.show a{
				position: absolute;
				width: 100%;
				height: 100%;
			}
			.point{
				position: absolute;
				z-index: 10;
				bottom: 20px;
			    width: 100%;
			    text-align: center;
			}
			.point ul{

			}
			.point li{
				width: 10px;
				height: 10px;
				border-radius: 10px;
				border: 1px solid #894c8d;
				display: inline-block;
			}
			.point li.checked{
				background: #c490bf;
			}
    	</style>
	</head>
	<body>
		<article class="show">
			<ul>
				<li></li>
				<li></li>
				<li></li>
				<li id="in"><a href="<c:url value='/app/elend/welcome'/>"></a></li>
			</ul>
		</article>
		<article class="point">
			<ul>
				<li class="checked"></li>
				<li></li>
				<li></li>
				<li></li>
			</ul>
		</article>

		<script>
			window.onload = function () {
				var yin = document.querySelector(".show");
				var _width = screen.width;
				var ul = yin.children[0];
				var lis = ul.children;
				var ulChildCount = ul.childElementCount;
				var points = document.querySelector(".point");
				var pointli = points.children[0].children;
				// for(var i=0,len=lis.length;i<len;i++) {
				// 	lis[i].style.cssText = '-webkit-transition:400ms ease;-moz-transition:400ms ease;transition:400ms ease;-webkit-transform:translate3d('+ _width * i +'px,0px, 0px);-moz-transform:translate3d('+ _width * i +'px,0px, 0px);transform:translate3d('+ _width * i +'px,0px, 0px)';
				// };

				var touchObj={num:0};

				yin.addEventListener('touchstart', function(e){
					 e.preventDefault();
					var obj = e.changedTouches[0];
					touchObj.startX = obj.clientX;
					touchObj.startY = obj.clientY;
					touchObj.startDate = new Date();
				}, false)

				yin.addEventListener('touchmove', function(e){
					e.preventDefault();
				}, false)

				yin.addEventListener('touchend', function(e){
					 e.preventDefault();
					if(touchObj.num == -3){
						window.location.href="<c:url value='/app/elend/welcome'/>";
					}
					var obj = e.changedTouches[0];
					touchObj.endX = obj.clientX;
					touchObj.endY = obj.clientY;
					touchObj.endDate = new Date();

					direction();
				}, false)

				function direction () {
					 if((touchObj.endDate - touchObj.startDate) < 100) {
					 	return false;
					 }
					if((touchObj.endX - touchObj.startX) > 0) {
						++touchObj.num;
						if(touchObj.num>0){
							touchObj.num--;
							return false;
						}
						moveToLeft();
					} else {
						touchObj.num--;
						if( touchObj.num <= ulChildCount  *-1){
							touchObj.num++;
						 return false;
						}
						moveToRight();
					}

					if(touchObj.num==-3){
						points.style.display="none";
						return false;
					}
					points.style.display="block";
					for(var i=0;i<4;i++){
						pointli[i].className='';
					}
					pointli[touchObj.num*-1].className = 'checked';
				}

				function moveToLeft(){
					var index = touchObj.num;
					for(var i=0,len=lis.length;i<len;i++) {
						if(index>0){
							index = 0;
						}
						var _offsetLeft = _width * index;
						lis[i].style.cssText = '-webkit-transition:400ms ease;-moz-transition:400ms ease;transition:400ms ease;-webkit-transform:translate3d('+ _offsetLeft +'px,0px, 0px);-moz-transform:translate3d('+ _offsetLeft +'px,0px, 0px);transform:translate3d('+ _offsetLeft +'px,0px, 0px)';
						index++;
					};

				}

				function moveToRight(){
					var index = touchObj.num;
					for(var i=0,len=lis.length;i<len;i++) {
						if(index>0){
							index = 0;
						}
						var _offsetLeft = _width * index;
						lis[i].style.cssText = '-webkit-transition:400ms ease;-moz-transition:400ms ease;transition:400ms ease;-webkit-transform:translate3d('+ _offsetLeft +'px,0px, 0px);-moz-transform:translate3d('+ _offsetLeft +'px,0px, 0px);transform:translate3d('+ _offsetLeft +'px,0px, 0px)';
						index++;
					};
				}
				
				
			}
		</script>
	</body>
</html>