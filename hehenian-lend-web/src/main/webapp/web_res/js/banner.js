// JavaScript Document
jQuery(function(){    
    indexSlides.ini();
});
var indexSlides={};
indexSlides.timer=false;
indexSlides.total=jQuery('#slide-index .control a').length;
indexSlides.current=-1;
indexSlides.offScreenLeft=2000;
indexSlides.leaveScreenLeft=4000;
indexSlides.animating=false;
indexSlides.obj=jQuery('#slide-index .slide');
indexSlides.style=[];
indexSlides.style[0]={
    text:{left:'0px',top:'81px'},
    button:{left:'0px',top:'244px'},
    direction:'tb'
};
    
indexSlides.style[1]={
    text:{left:'0px',top:'81px'},
    button:{left:'0px',top:'244px'},
    direction:'tb'
};
    
indexSlides.style[2]={
    text:{left:'0px',top:'81px'},
    button:{left:'0px',top:'244px'},
    direction:'tb'
};
indexSlides.style[3]={
    text:{left:'0px',top:'81px'},
    button:{left:'0px',top:'244px'},
    direction:'tb'
};
