<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/head.jsp" %>
<% request.setAttribute("menuIndex", 0); %>
	<title>${channel_name}-产品展示</title>
	<style>
		.banner{
			position: relative;
			width: 100%;
			overflow: hidden;
		}
		.banner li {
			display: none;      
			-webkit-transform: translate(0,0);
			-moz-transform: translate(0,0);
			transform: translate(0,0);
			width:100%;
		}
		.banner .current {
			position: absolute;
			z-index: 12;
			-webkit-transform: scale(1) translateX(0%);
			-webkit-transition: 1s;
			display: block;
		}

		.banner .prev {
			display: block;
			-webkit-transform: scale(1) translateX(-100%);
			position: absolute;
			-webkit-transition: ease 1s;
		}

		.banner .next {
			display: block;
			z-index: 1;
			-webkit-transform: scale(1) translateX(100%);
			position: absolute;
			-webkit-transition: ease 1s;
		}
		.banner a,.banner img{
			width: 100%;
			display: block;
		}
		.banner_num {
			display: inline-block;
			right: 10px;
			margin-top: -20px;
			font-size: 12px;
			z-index: 20;
			position: absolute;
		}

		.banner_num ul {
			height: 20px;
		}

		.banner_num li {
			width: 10px;
			height: 10px;
			-webkit-border-radius: 10px;
			-moz-border-radius: 10px;
			-ms-border-radius: 10px;
			border-radius: 10px;
			border-radius: 10px;
			margin: 5px;
			background: #fff;
			display: inline-block;
		}
		.banner_num li.current {
			background: #3d7cd4;
		}
	</style>
</head>
<body class="bg-2">
	<header class="top-bar">
		<p>合和年在线</p>
	</header>
	<article>
		<div class="banner">
			<ul >
				<li><a href="http://m.hehenian.com/about/introduce.do"><img src="http://static.hehenian.com/m/img/banner/investment-banner.jpg" alt=""></a></li>
				<li><a href="http://m.hehenian.com/product/plist.do"><img src="http://static.hehenian.com/m/img/banner/brand-banner.jpg" alt=""></a></li>
			</ul>
		</div>
		<div class="banner_num">
            <ul><li></li><li></li></ul>
        </div>
	</article>
	<article class="product-bar">
		<p class="product-name">爱定宝·12-M</p>
		<p class="product-feature">收益稳健，安全快捷</p>
		<a href="http://m.hehenian.com/product/detail.do?pid=2">
		<section class="product-detail">
			<p>预期年化收益率</p>
			<p class="pro-num">10%</p>
			<a href="http://m.hehenian.com/product/detail.do?pid=2" class="pro-more">
				查看详情 >>
			</a>
		</section>
		</a>
		<a href="http://m.hehenian.com/finance/prepay.do?pid=2">
			<div class="pro-bn">
				立即购买
			</div>
		</a>
	</article>

	<div class="ph-2">
	</div>
	<%@ include file="../common/tail.jsp" %>
	<script>
		$(function(){
			sbh();
		})
	</script>
	<script>
	window.onload = function () {
    var banner2d = document.querySelector(".banner");
    var browWidth = window.document.body.clientWidth;
    var banner_num = document.querySelector(".banner_num ul");
    var autoMove, banner, 
    bannerlen = $(".banner li").length, currentBanner, endTime = 0;
    var touchEvent = function () {
        var touchObj = {};
        banner2d.addEventListener('touchstart', function (e) {
            // if (endTime && new Date().getTime() - endTime < 1000) {
            //     return false;
            // }
            clearInterval(autoMove);
            var obj = e.changedTouches[0];
            touchObj.boxleft = parseInt(banner2d.style.left);
            touchObj.startx = parseInt(obj.clientX);
            touchObj.startTime = new Date().getTime();
            e.preventDefault();
        }, false);

        banner2d.addEventListener('touchmove', function (e) {
            e.preventDefault();
        }, false);

        banner2d.addEventListener('touchend', function (e) {
            // if (endTime && new Date().getTime() - endTime < 2000) {
            //     return false;
            // }
            var obj = e.changedTouches[0];
            touchObj.dist = obj.clientX - touchObj.startx;
            touchObj.elapsedTime = new Date().getTime() - touchObj.startTime;
            if (Math.abs(touchObj.dist) < 40) {
                var url = currentBanner.firstElementChild.href;
                window.location.href = url;
                return false;
            }
            handleswipe(touchObj);
            setTimes();
            endTime = new Date().getTime();
            e.preventDefault();
        }, false);
    };


    var swipLeft = function () {
        var nextBanner = currentBanner.nextElementSibling;
        var prevBanner, oldPrev = currentBanner.previousElementSibling;
        var currentNum = document.querySelector(".banner_num .current");
        var nextNum = currentNum.nextElementSibling;

        currentNum.removeAttribute("class");
        if (nextNum) {
            nextNum.className = "current";
        } else {
            banner_num.firstElementChild.className = "current";
        }

        var firstItem = banner.item(0);
        var item = firstItem.cloneNode(true);
        banner2d.firstElementChild.appendChild(item);
        banner2d.firstElementChild.removeChild(firstItem);
        firstItem = '';
        item = '';

        prevBanner = currentBanner;
        if (prevBanner) {
            prevBanner.className = "prev";
        }

        currentBanner = nextBanner;
        currentBanner.className = "current";

        nextBanner = currentBanner.nextElementSibling;
        if (nextBanner) {
            nextBanner.className = "next";
        }

        if (oldPrev) {
            oldPrev.removeAttribute("class");
        }

    };

    var swipRight = function () {
        var nextBanner = currentBanner.previousElementSibling;
        var prevBanner, oldPrev = currentBanner.nextElementSibling;
        var currentNum = document.querySelector(".banner_num .current");
        var nextNum = currentNum.previousElementSibling;

        bannerlen = bannerlen == 2 ? 4 : bannerlen;
        var lasttItem = banner.item(bannerlen - 1);
        var item = lasttItem.cloneNode(true);
        banner2d.firstElementChild.insertBefore(item, banner.item(0));
        banner2d.firstElementChild.removeChild(lasttItem);
        lasttItem = '';
        item = '';

        currentNum.removeAttribute("class");
        if (nextNum) {
            nextNum.className = "current";
        } else {
            banner_num.lastElementChild.className = "current";
        }

        prevBanner = currentBanner;
        if (prevBanner) {
            prevBanner.className = "next";
        }

        currentBanner = nextBanner;
        currentBanner.className = "current";

        nextBanner = currentBanner.previousElementSibling;
        if (nextBanner) {
            nextBanner.className = "prev";
        }

        if (oldPrev) {
            oldPrev.removeAttribute("class");
        }
    }

    var handleswipe = function (touchObj) {
        if (touchObj.dist > 40) {
            swipRight();
        } else if (touchObj.dist < -40) {
            swipLeft();
        }
    };

    function setTimes() {
        autoMove = setInterval(function () {
            swipLeft();
        }, 5000);
    };

    function main() {
        banner2d.style.height = (browWidth * 5 / 16) + 'px';
        if (bannerlen < 2) {
            banner.item(0).style.cssText = "display:block;";
            return false;
        }

        banner_num.firstElementChild.className = "current";
        if (bannerlen == 2) {
            var ul = banner2d.firstElementChild;
            var lastchild = ul.lastElementChild.cloneNode(true);
            ul.appendChild(ul.firstElementChild.cloneNode(true));
            ul.appendChild(lastchild);
        }

        currentBanner = banner.item(1);
        currentBanner.className = "current";
        currentBanner.previousElementSibling.classList.add("prev");
        currentBanner.nextElementSibling.classList.add("next");
        setTimes();
        touchEvent();
    };

    (function () {
        banner = banner2d.firstElementChild.children;
        main();
    }());
};
</script>
</body>
</html>