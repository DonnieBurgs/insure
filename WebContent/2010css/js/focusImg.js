/**
 * slide (Version 1.1)
 * @author lv ming (akm107@163.com)
 *
 * Create a Slide
 * @example new Slide(container,options);
 * on Jquery
 *
 */
function Slide(container,options){
	this.container=$(container);
	this.list=$(container+' .J_slide_list');
	this.handle=$(container+' .J_slide_trigger li');
	this.item=$(container+' .J_slide_item');
	this.itemWH=0;
	this.count=this.handle.length;
	this.timer=null;
	this.eTime=null;
	this.options=$.extend({
		auto:true,
		delay:4,
		duration:500,
		effect:'fade',
		event:'mouseover',
		index:1,
		vertical:true
		},options);
	this.init();
	}
Slide.prototype={
	init:function(){
		var slideClip, itemW, itemH, itemWH,
			that=this,
			list=this.list, item=this.item,
			op=this.options, auto=!!op.auto, vertical=!!op.vertical;
		if(op.effect==='fade'){
			list.css({position:'relative'});
			item.css({position:'absolute'});
			}
		if(op.effect==='slide'){
			list.css({position:'absolute'});
			if(!list.parent().hasClass('J_slide_clip')){
				list.wrap('<div class="J_slide_clip"></div>');
				}
			itemW=item.outerWidth(true);
			itemH=item.outerHeight(true);
			this.container.find('.J_slide_clip').css({position:'relative',overflow:'hidden',height:itemH,width:itemW});
			this.itemWH=vertical?itemH:itemW;
			}
		this.handle.bind(op.event, this._trigger(this));
		if(op.index>this.count||op.index<1){
			op.index=1;};
		this._showFirst(op.index);
		if(auto){
			this._auto();
			this.container.hover(function(){that._stop();},function(){that._auto();});
			}
		},
	_trigger:function(o){
		return function(e){
			var index, op=o.options, handle=o.handle;
			if(op.index===(handle.index(this)+1)){
				return;
				}
			index=op.index=handle.index(this)+1;
			o._show(index);
			};
		},
	_show:function(i){
		var that=this, op=this.options, vertical=!!op.vertical;
		this.handle.removeClass('cur').eq(i-1).addClass('cur');
		if(op.effect==='fade'){
			clearTimeout(this.eTime);
			this.eTime=setTimeout(function(){that.item.not(that).css({zIndex:1}).eq(i-1).css({zIndex:9}).animate({opacity:1},that.options.duration,function(){
				that.item.not(this).css({opacity:0})
				});},150);
			}
		if(op.effect==='slide'){
			itemWH=this.itemWH;
			this.list.stop().animate({top:-itemWH*(i-1)},this.options.duration);
			}
		},
	_showFirst:function(i){
		var op=this.options, vertical=!!op.vertical;
		this.handle.removeClass('cur').eq(i-1).addClass('cur');
		if(op.effect==='fade'){
			this.item.not(this).css({zIndex:1, opacity:0}).eq(i-1).css({zIndex:9, opacity:1});
			}
		if(op.effect==='slide'){
			itemWH=this.itemWH;
			this.list.css({top:-itemWH*(i-1)});
			}
		},
	_auto:function(){
		var that=this,
			op=that.options;
		this.timer=setTimeout(function(){
			op.index = op.index< that.count? ++op.index: 1;
			that._show(op.index);
			that._auto();
			},op.delay*1000);
		},
	_stop:function(){
		clearTimeout(this.timer);
		}
	};