/* <![CDATA[ */
window.addEvent('domready',function(){									
	imgstore = new Array();			
	var allBumps = $$('.bumpbox');	
	i=0;
	allBumps.each(function(e){
	var content = e.get('href');
		
		if(content.indexOf(".jpg") != -1 || content.indexOf(".gif") != -1 || content.indexOf(".png") != -1){
			var img = new Element('img');
			img.src = content;	
			img.setStyle('display','none');
			img.inject(document.body);
		}						   
						   
		if(e.get('href').indexOf('^') == 0){
			var id = e.get('href').replace("^",'');
			$(id).setStyle('display','none');
		}
		e.set('id',i);
		i++;
})

var f = "";
i = 0;

$$('.bumpbox').addEvent('click',function(e){
	e = new Event(e).stop();
	if($('grow') != null){
		$('grow').dispose();
	}
	if(this.get('rev') != null){
		var rev = this.get('rev')
	}
	var content = this.get('href');
	var actualID = this.get('id');
	maxw = 0;
	maxh = 0;
	var title = "";
	
	if(this.get('rel')!= null){
		var tmp = this.get('rel').split("-");	
		maxw = tmp[0];
		maxh = tmp[1];
	}
	
	if(this.get('title') != null){
		title = this.get('title');
	}
	
	if(this.get('href') != null){
		hr = this.get('href');
	}
	
	if(maxw == 0){	maxw = 640;	}
	if(maxh == 0){	maxh = 480;	}
	
		
	if(content.indexOf(".jpg") != -1 || content.indexOf(".gif") != -1 || content.indexOf(".png") != -1){

		img = new Image();
		img.src = content;
		maxw = img.width;
		maxh = img.height;
				
	}
	
	w = window.getSize().x.toInt();
	h = window.getSize().y.toInt();
	s = window.getScrollTop();
	var middleH = (w) / 2;
	var middleV = (h) / 2;
	var endleft = (w-maxw) / 2;
	var endtop = ((h - maxh) / 2) + s;

	var el = new Element('div', {
		 'styles':{
			 width: '1px',
			 height: '1px',
			 position:'absolute',
			 border:'2px solid #676767',
			 padding:'4px',
			 background:'#000 url(ajax-loader.gif) no-repeat center center',
			 left:middleH ,
			 top:middleV,
			 cursor:'pointer',
			 display:'block',
			 'z-index':'100000'
		},
		'id': 'grow'
	})

	$(el).setStyles({
			'-moz-border-radius':'10px',
			'-webkit-border-radius':'10px',
			'border-radius':'10px'
			 });

	var t = window.getScrollTop();
	
	bg = new Element('div',{
	'styles':{
		background:'#000',
		width:'100%',
		height:'100%',
		opacity:'0.7',
		position:'absolute',
		top: t,
		left: 0
	},
	'id':'bg'
	})
	
	/* bg ie 6 fix */
	if(Browser.Engine.name == 'trident' && Browser.Engine.version == 4){
		var bh = window.getHeight();
		$(bg).setStyle('height',bh);
	}
	
	
	var cl = new Element('img',{
		'styles':{
			width:'24px',
			height:'24px',
			position:'absolute',
			top:'-16px',
			right:'-16px',
			'z-index':'100000'
		},
		'src':'http://www.haascnc.com//scripts/js/bumpbox/closed.png',
		'id':'nycloser'
	})	
	
	cl.addEvent('click',function(e){
		
			bg.dispose();
			
			$(el).getChildren().dispose();
			eff2.start({
			  'width':[maxw,1],
			  'height':[maxh,1],
			  'left': [endleft,middleH],
			  'top':  [endtop, middleV+s]
			 })
	})	
	
	if(Browser.Engine.name != 'trident'){
		window.addEvent('keyup',function(e){
			  
			   if(e.key == 'esc'){
					bg.dispose();
					$(el).getChildren().dispose();
					eff2.start({
					  'width':[maxw,1],
					  'height':[maxh,1],
					  'left': [endleft,middleH],
					  'top':  [endtop, middleV+s]
					 })
			   }
		})
		
	}else{
		document.addEvent('keyup',function(e){
			   if(e.key == 'esc'){
				   bg.dispose();
					$(el).getChildren().dispose();
					eff2.start({
					  'width':[maxw,1],
					  'height':[maxh,1],
					  'left': [endleft,middleH],
					  'top':  [endtop, middleV+s]
					 })
			   }
			  
		})
	}
	
	bg.inject(document.body);
	el.injectInside(document.body);
	cl.injectInside(el);
	
	window.addEvent('scroll',function(){
		$(bg).setStyle('top',window.getScrollTop());
	})	
	
	var eff3 = new Fx.Morph($('grow'), { transition: Fx.Transitions.linear, duration: 2000, wait:'link',onComplete:function(){
		el.dispose();
  	}});
	
	var eff2 = new Fx.Morph($('grow'), { transition: Fx.Transitions.Bounce.easeOut, duration: 300, wait:'link', onComplete:function(){
		el.dispose();
	}});
		
	var eff = new Fx.Morph($('grow'), { transition: Fx.Transitions.Bounce.easeOut, duration: 1200, wait:'link', onComplete:function(){
		$(el).setStyle('background','#000');		
		
		if($('prev') != null){
			$('prev').setStyle('display','block');
		}
		
		if($('next') != null){
			$('next').setStyle('display','block');
		}
		
		$('grow').setStyle('-moz-box-shadow','3px 3px #000');
	
		
					  
	if(content.indexOf(".jpg") != -1 || content.indexOf(".gif") != -1 || content.indexOf(".png") != -1){
		 var img = new Element('img');
		 img.src = content;
		 maxw = img.width;
		 maxh = img.height;
		 $(img).inject(el);
	}	
		

		
		else if(content.indexOf('.flv') == -1 && content.indexOf('.mp3') == -1 && content.indexOf(".pdf") == -1 && content.indexOf(".swf") == -1 && content.indexOf(".jpg") == -1 && content.indexOf(".gif") == -1 && content.indexOf(".png") == -1 && content.indexOf("^") == -1){
			
				var p = new Element('div');
				p.setStyle('display','block');
				p.setStyle('overflow','hidden');
				p.setStyle('padding','20px');
				p.setStyle('height',maxh-40);
				p.setStyle('width',maxw-40);
				p.setAttribute('id','htmlframe');
				p.inject(el);
				
				var x = new IFrame();
				x.setStyle('overflow','auto');
				x.set('frameborder','0');
				x.setStyle('width',maxw-40);
				x.setStyle('height',maxh-40);
				x.src = content;
				x.inject(p);
		}
		
		else if(content.indexOf(".pdf") != -1){
		
		var div = new Element('div',{
				'styles':{
					padding:'20px',
					height: maxh-40,
					width:maxw-40
				}
			})
			
			div.inject(el);
			var x = new IFrame();
			x.src = content;
			x.setStyle('width',maxw-40);
			x.setStyle('height',maxh-40);
			x.inject(div);
			
		}
		
		else if(content.indexOf(".swf") != -1){
			var div = new Element('div',{
				'styles':{
					padding:'20px',
					height: maxh-40,
					width:maxw-40
				},
				id: 'swf'
			})
			
		div.inject(el);
			
			var obj = new Swiff(content, {
				id: 'video',
				width: maxw-40,
				height: maxh-40,
				container: div
			})	
		}
		
		else if(content.indexOf(".flv") != -1){
			
			var div = new Element('div',{
				'styles':{
					padding:'20px',
					height: maxh-40,
					width:maxw-40
				}
			})
			
			div.inject(el);
		
			var f =flowplayer(div, "flowplayer.swf", { 
   			 buffering : true,      
			 autoplay: true,
			 clip: content,
			 wmode: 'transparent',
			 id: 'player'
    		});
		}
		
		else if(content.indexOf(".mp3") != -1){
			
			var div = new Element('div',{
				'styles':{
					padding:'20px',
					height: maxh-50,
					width:maxw-40,
					'z-index':'1',
					id:'flowplay'
					
				}
			})
			
			div.inject(el);
		
			 f =flowplayer(div, "flowplayer.swf", { 
			 buffering : true,      
			 autoplay: true,
			 clip: content,
			 wmode: 'transparent',
			 plugins: { 
 	   		 myContent: { 
		 
				url: 'flowplayer.content.swf', 
		 
				bottom: 0, 
				width: maxw-20, 
				left:0,
				borderRadius: 10, 
				html: title, 
				onClick: function() { 
					this.hide(); 
				} 
			 }
			},
			 
			 playlist:[{url:content,autoPlay:true}]
			 
    		});
		}
		
		else if(content.indexOf("^") == 0){
		
			content = content.replace("^","");
			
			var div = new Element('div',{
				'styles':{
					padding:'20px',
					height: 'auto',
					width:'auto',
					color:'#fff',
					overflow:'hidden'
				}
			})
			var c = $(content).get('html');
			div.set('html',c);
			div.inject(el);
		}
		
		if(title != ""){
			var t = new Element('div',{
			'styles':{
				'height':'20px',
				
				'position':'absolute',
				'top':'-41px',
				'left':'20px',
				'color':'#eee',
				'opacity':'0.7',
				'z-index':'10000',
				'background':'#000',
				'-webkit-border-radius':'5px',
				'-moz-border-radius':'5px',
				'-border-radius':'5px',
				'display':'block',
				'padding':'10px',
				'border-top':'2px solid #676767',
				'border-left':'2px solid #676767',
				'border-right':'2px solid #676767'

			}
		})
			t.set('html',title);			
			t.inject(el);
			t.set('id','maindesc');
		}
		
	if(rev != null){

			var x1 = new Element('div',{
			'styles':{
			'width':'auto',
			'height':'20px',
			'position':'absolute',
			'left': '20px',
			'bottom': '-41px',
			'color':'#eee',
			'text-decoration':'none',
			'background':'#000',
			'-webkit-border-radius':'5px',
			'-moz-border-radius':'5px',
			'-border-radius':'5px',
			'display':'block',
			'padding':'10px',
			'border-bottom':'2px solid #303132',
			'border-left':'2px solid #303132',
			'border-right':'2px solid #303132'
			}})
			x1.inject(el);
			x1.set('html', rev);
			x1.set('id','addondesc');
		
	}
	}});

	$(bg).addEvent('click',function(e){
		bg.dispose();
		$(el).getChildren().dispose();									
		eff2.start({
				   
					  'width':[maxw,1],
					  'height':[maxh,1],
					  'left': [endleft,middleH],
					  'top':  [endtop, middleV+s]

					 })
	})
	
	
	var nextID = this.get('id').toInt() +1;
	
	if(allBumps[nextID] != null){
		var middle = (maxh - 30) / 2;
		var nx = new Element('a',{
		  'styles':{
		  //Changed width and height to 0 to remove previous and next buttons	 - JNGUYEN  
					width:'0px',
					height:'0px',
					background:'url(/scripts/js/bumpbox/next.png) no-repeat center center',
					position:'absolute',
					right:'-20px',
					bottom: middle,
					'display':'none'
		  },
		  
			  id:'next'
		  
		})
			
		nx.addEvent('click',function(e){
		
		if(Browser.Engine.trident){
			if($('video') != null){
					$('video').stop();
			}
		
			 
			 if($('swf') != null){
				 $('swf').dispose();
		 	 }	
		 }
		 
		 $('bg').dispose();										 
		 $('grow').dispose();
		 
		
		 
		 var nextID = actualID.toInt();
		 nextID+=1;
		 nextID = nextID.toString();
		 $(nextID).fireEvent('click',this);
   	    })
			
				nx.inject(el);
		}
		
		var prevID = this.get('id').toInt() -1;
		
		if(allBumps[prevID] != null){
			var middle = (maxh - 30) / 2;
			var nx2 = new Element('a',{
			  'styles':{
		//Changed width and height to 0 to remove previous and next buttons	 - JNGUYEN  
						width:'0px',
						height:'0px',
						background:'url(/scripts/js/bumpbox/prev.png) no-repeat center center',
						position:'absolute',
						left:'-20px',
						bottom:middle,
						'display':'none'
			  },
				  id:'prev'
			})
			
			nx2.addEvent('click',function(e){
										  
			if(Browser.Engine.trident){
				if($('video') != null){
						$('video').stop();
				}
			
				 
				 if($('swf') != null){
					 $('swf').dispose();
				 }	
		 	}					  
										  
			 $('bg').dispose();										 
			 $('grow').dispose();
			 
			
			 var nextID = actualID.toInt();
			 nextID-=1;
			 nextID = nextID.toString();
			 $(nextID).fireEvent('click',this);
   		    })
			
			nx2.inject(el);
	
		}
	
	eff.start({
		  'width':[1,maxw],
		  'height':[1,maxh],
		  'left': [middleH,endleft],
		  'top':  [middleV+s, endtop]		  
		 })
 	});


})
/* ]]> */

